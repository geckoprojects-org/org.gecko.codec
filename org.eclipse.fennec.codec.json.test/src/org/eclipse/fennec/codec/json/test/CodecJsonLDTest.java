/**
 * Copyright (c) 2012 - 2023 Data In Motion and others.
 * All rights reserved. 
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Data In Motion - initial API and implementation
 */
package org.eclipse.fennec.codec.json.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.fennec.codec.configurator.CodecFactoryConfigurator;
import org.eclipse.fennec.codec.configurator.CodecModuleConfigurator;
import org.eclipse.fennec.codec.configurator.ObjectMapperConfigurator;
import org.eclipse.fennec.codec.constants.CodecModelInfoOptions;
import org.eclipse.fennec.codec.constants.CodecModuleOptions;
import org.eclipse.fennec.codec.constants.CodecResourceOptions;
import org.eclipse.fennec.codec.constants.ObjectMapperOptions;
import org.eclipse.fennec.codec.info.codecinfo.CodecDeserializer;
import org.eclipse.fennec.codec.info.codecinfo.CodecSerializer;
import org.eclipse.fennec.jsonld.model.jsonld.ContextObject;
import org.eclipse.fennec.jsonld.model.jsonld.ContextStringValue;
import org.eclipse.fennec.jsonld.model.jsonld.ContextValue;
import org.eclipse.fennec.jsonld.model.jsonld.JsonLD;
import org.eclipse.fennec.jsonld.model.jsonld.JsonLDFactory;
import org.eclipse.fennec.jsonld.model.jsonld.JsonLDPackage;
import org.gecko.emf.osgi.annotation.require.RequireEMF;
import org.gecko.emf.osgi.constants.EMFNamespaces;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.osgi.framework.BundleContext;
import org.osgi.test.common.annotation.InjectBundleContext;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.common.annotation.Property;
import org.osgi.test.common.annotation.Property.Type;
import org.osgi.test.common.annotation.config.WithFactoryConfiguration;
import org.osgi.test.common.service.ServiceAware;
import org.osgi.test.junit5.cm.ConfigurationExtension;
import org.osgi.test.junit5.context.BundleContextExtension;
import org.osgi.test.junit5.service.ServiceExtension;

import tools.jackson.core.JsonGenerator;
import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.SerializationFeature;

@RequireEMF
@ExtendWith(BundleContextExtension.class)
@ExtendWith(ServiceExtension.class)
@ExtendWith(ConfigurationExtension.class)
@WithFactoryConfiguration(factoryPid = "DefaultCodecFactoryConfigurator", location = "?", name = "test", properties = {
		@Property(key = "type", value = "json") })
@WithFactoryConfiguration(factoryPid = "DefaultObjectMapperConfigurator", location = "?", name = "test", properties = {
		@Property(key = "type", value = "json"),
		@Property(key = "disableFeatures", value={"JsonWriteFeature.ESCAPE_FORWARD_SLASHES"}, type = Type.Array)		
})
@WithFactoryConfiguration(factoryPid = "DefaultCodecModuleConfigurator", location = "?", name = "test", properties = {
		@Property(key = "type", value = "json") })
public class CodecJsonLDTest {

	@InjectService(filter="("+EMFNamespaces.EMF_CONFIGURATOR_NAME + "=CodecJson)")
	ResourceSet resourceSet;

	@InjectService(cardinality = 0, filter = "(type=json)")
	ServiceAware<CodecFactoryConfigurator> codecFactoryAware;

	@InjectService(cardinality = 0, filter = "(type=json)")
	ServiceAware<ObjectMapperConfigurator> mapperAware;

	@InjectService(cardinality = 0, filter = "(type=json)")
	ServiceAware<CodecModuleConfigurator> codecModuleAware;

	@InjectBundleContext
	BundleContext ctx;

	private String mapFileName;
	
	@BeforeEach()
	public void beforeEach() throws InterruptedException {
		mapFileName = "schema_".concat(UUID.randomUUID().toString()).concat(".json");
		codecFactoryAware.waitForService(2000l);
		mapperAware.waitForService(2000l);
		codecModuleAware.waitForService(2000l);
	}
	
//	@AfterEach
	public void afterEach() throws IOException {
		if(mapFileName != null) {
			Path path = Paths.get(mapFileName);
			if(Files.exists(path)) {
				Files.delete(path);
			}
		}
	}
	

	
	@Test
	public void testSaveJsonLD() throws IOException {
		
		JsonLD obj = createContextObject();
		Resource resource = resourceSet.createResource(URI.createURI("test_save_jsonld_after.json"));
		Map<String, Object> options = new HashMap<>();
		options.put(CodecResourceOptions.CODEC_ROOT_OBJECT, JsonLDPackage.eINSTANCE.getJsonLD());
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_DEFAULT_VALUE, true);
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_TYPE, false);
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		
		Map<EClass, Map<String, Object>> classOptions = new HashMap<>();
		Map<String, Object> ctxObjOptions = new HashMap<>();
		ctxObjOptions.put(CodecModelInfoOptions.CODEC_CUSTOM_SERIALIZERS_MAP, Map.of(JsonLDPackage.Literals.CONTEXT_OBJECT, CTXT_OBJ_SER));
		classOptions.put(JsonLDPackage.eINSTANCE.getContextObject(), ctxObjOptions);
		options.put(CodecResourceOptions.CODEC_OPTIONS, classOptions);
		
		resource.getContents().add(obj);
		resource.save(options);
	}
	
	@Test
	public void testReadJsonLD() throws IOException {
		Resource resource = resourceSet.createResource(URI.createURI("test_save_jsonld_after.json"));
		Map<String, Object> options = new HashMap<>();
		options.put(CodecResourceOptions.CODEC_ROOT_OBJECT, JsonLDPackage.eINSTANCE.getJsonLD());
		Map<EClass, Map<String, Object>> classOptions = new HashMap<>();
		Map<String, Object> ctxObjOptions = new HashMap<>();
		ctxObjOptions.put(CodecModelInfoOptions.CODEC_CUSTOM_DESERIALIZERS_MAP, Map.of(JsonLDPackage.Literals.CONTEXT_OBJECT, CTXT_OBJ_DES));
		classOptions.put(JsonLDPackage.eINSTANCE.getContextObject(), ctxObjOptions);
		options.put(CodecResourceOptions.CODEC_OPTIONS, classOptions);
		resource.load(options);
		assertFalse(resource.getContents().isEmpty());
		EObject obj = resource.getContents().get(0);
		assertNotNull(obj);
		assertThat(obj).isInstanceOf(JsonLD.class);
		JsonLD jsonLD = (JsonLD) obj;
		assertThat(jsonLD.getContext()).isNotNull();
		assertThat(jsonLD.getContext().getContext().containsKey("Person"));
		assertThat(jsonLD.getContext().getContext().get("Person")).isInstanceOf(ContextStringValue.class);
		ContextStringValue strValue = (ContextStringValue) jsonLD.getContext().getContext().get("Person");
		assertThat(strValue.getValue()).isEqualTo("http://xmlns.com/foaf/0.1/Person");
		assertThat(jsonLD.getContext().getContext().containsKey("image"));
		assertThat(jsonLD.getContext().getContext().get("image")).isInstanceOf(ContextObject.class);
		ContextObject imgValue = (ContextObject) jsonLD.getContext().getContext().get("image");
		assertThat(imgValue.getContext().containsKey("@id"));
		assertThat(imgValue.getContext().get("@id")).isInstanceOf(ContextStringValue.class);
		strValue = (ContextStringValue) imgValue.getContext().get("@id");
		assertThat(strValue.getValue()).isEqualTo("http://xmlns.com/foaf/0.1/img");
		assertThat(imgValue.getContext().containsKey("@type"));
		assertThat(imgValue.getContext().get("@type")).isInstanceOf(ContextStringValue.class);
		strValue = (ContextStringValue) imgValue.getContext().get("@type");
		assertThat(strValue.getValue()).isEqualTo("@id");
	}
	
	private JsonLD createContextObject() {
		
		Map<String, String> simpleValuesMap = Map.ofEntries(Map.entry("Person", "http://xmlns.com/foaf/0.1/Person"),
				Map.entry("xsd", "http://www.w3.org/2001/XMLSchema#"),
				Map.entry("name", "http://xmlns.com/foaf/0.1/name"),
				Map.entry("nickname", "http://xmlns.com/foaf/0.1/nick"),
				Map.entry("affiliation", "http://schema.org/affiliation"),
				Map.entry("familyName", "http://xmlns.com/foaf/0.1/familyName"),
				Map.entry("givenName", "http://xmlns.com/foaf/0.1/givenName")
				);
		
		Map<String, Map<String, String>> complexValuesMap = Map.ofEntries(
				Map.entry("depiction", Map.ofEntries(Map.entry("@id", "http://xmlns.com/foaf/0.1/depiction"), Map.entry("@type", "@id"))),
				Map.entry("image", Map.ofEntries(Map.entry("@id", "http://xmlns.com/foaf/0.1/img"), Map.entry("@type", "@id"))),
				Map.entry("born", Map.ofEntries(Map.entry("@id", "http://schema.org/birthDate"), Map.entry("@type", "xsd:date"))),
				Map.entry("child", Map.ofEntries(Map.entry("@id", "http://schema.org/children"), Map.entry("@type", "@id"))),
				Map.entry("colleague", Map.ofEntries(Map.entry("@id", "http://schema.org/colleagues"), Map.entry("@type", "@id"))),
				Map.entry("knows", Map.ofEntries(Map.entry("@id", "http://xmlns.com/foaf/0.1/knows"), Map.entry("@type", "@id")))
				);
		
		JsonLDFactory factory = JsonLDFactory.eINSTANCE;

        // Create the root context object
        ContextObject contextObject = factory.createContextObject();       
        
        simpleValuesMap.forEach((k,v) -> {
        	ContextStringValue value = factory.createContextStringValue();
        	value.setValue(v);
            contextObject.getContext().put(k, value);
        });
        
        complexValuesMap.forEach((k,v) -> {
        	 ContextObject value = factory.createContextObject();   
        	 v.forEach((k1, v1) -> {
        		 ContextStringValue innerMap = factory.createContextStringValue();
        		 innerMap.setValue(v1);
        		 value.getContext().put(k1, innerMap);
        	 });            
             contextObject.getContext().put(k, value);
        });

        JsonLD jsonLD = factory.createJsonLD();
        jsonLD.setContext(contextObject);
        return jsonLD;
	}
	
	
	public static final CodecSerializer<ContextObject> CTXT_OBJ_SER = new CodecSerializer<ContextObject>() {

		@Override
		public String getName() {
			return "CTXT_OBJ_SER";
		}

		@Override
		public void serialize(ContextObject value, JsonGenerator gen, SerializationContext ctxt) {	    	
	        gen.writeStartObject();
	        for (Entry<String, ContextValue> entry : value.getContext().entrySet()) {
	        	 gen.writeName(entry.getKey());
	        	 if(entry.getValue() instanceof ContextStringValue strValue) {
	        		 ctxt.writeValue(gen, strValue.getValue());
	        	 } else {
	        		 ctxt.writeValue(gen, entry.getValue());
	        	 }  	 	            
	        }
	        gen.writeEndObject();
	    }		
	}; 

	
	public static final CodecDeserializer<ContextObject> CTXT_OBJ_DES = new CodecDeserializer<ContextObject>() {
		
		@Override
		public String getName() {
			return "CTXT_OBJ_DES";
		}
		
		@Override
		public ContextObject deserialize(JsonParser parser, DeserializationContext ctxt) {
			ContextObject ctxtObj = JsonLDFactory.eINSTANCE.createContextObject();
			if (parser.currentToken() != JsonToken.START_OBJECT) {
	            throw new IllegalArgumentException("Expected JsonToken.StART_OBJECT instead got " + parser.currentToken());
	        }
			while (parser.nextToken() != JsonToken.END_OBJECT) {
	            String key = parser.currentName();
	            parser.nextToken();

	            JsonToken token = parser.currentToken();
	            switch (token) {
	            case VALUE_STRING:
	            	ContextStringValue ctxtStrValue = JsonLDFactory.eINSTANCE.createContextStringValue();
	            	ctxtStrValue.setValue(parser.getString());
	            	ctxtObj.getContext().put(key, ctxtStrValue);
	            	break;
	            case START_OBJECT:
	            	ContextObject ctxtObjValue = this.deserialize(parser, ctxt);
	            	ctxtObj.getContext().put(key, ctxtObjValue);
	            	break;
	            default:
	            	throw new IllegalArgumentException("Expected either VALUE_STRING or START_OBJECT but got " + token);
	            }
	            
			}
			return ctxtObj;
		}
	};
}
