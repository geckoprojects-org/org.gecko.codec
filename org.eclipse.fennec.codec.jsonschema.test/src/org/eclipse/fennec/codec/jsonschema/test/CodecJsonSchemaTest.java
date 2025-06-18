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
package org.eclipse.fennec.codec.jsonschema.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.fennec.ai.jsonschema.model.jsonschema.ArrayJsonSchema;
import org.eclipse.fennec.ai.jsonschema.model.jsonschema.ArtificialEClassJsonSchema;
import org.eclipse.fennec.ai.jsonschema.model.jsonschema.EClassJsonSchema;
import org.eclipse.fennec.ai.jsonschema.model.jsonschema.EDataTypeJsonSchema;
import org.eclipse.fennec.ai.jsonschema.model.jsonschema.EnumJsonSchema;
import org.eclipse.fennec.ai.jsonschema.model.jsonschema.JsonSchema;
import org.eclipse.fennec.ai.jsonschema.model.jsonschema.JsonSchemaDefinition;
import org.eclipse.fennec.ai.jsonschema.model.jsonschema.JsonSchemaFactory;
import org.eclipse.fennec.ai.jsonschema.model.jsonschema.JsonSchemaPackage;
import org.eclipse.fennec.ai.jsonschema.model.jsonschema.ReferenceJsonSchema;
import org.eclipse.fennec.codec.configurator.CodecFactoryConfigurator;
import org.eclipse.fennec.codec.configurator.CodecModuleConfigurator;
import org.eclipse.fennec.codec.configurator.ObjectMapperConfigurator;
import org.eclipse.fennec.codec.constants.CodecModelInfoOptions;
import org.eclipse.fennec.codec.constants.CodecResourceOptions;
import org.eclipse.fennec.codec.info.codecinfo.CodecDeserializer;
import org.eclipse.fennec.codec.jsonschema.test.helper.JsonSchemaToEPackageDeserializer;
import org.eclipse.fennec.qvt.osgi.api.ModelTransformator;
import org.gecko.emf.osgi.annotation.require.RequireEMF;
import org.gecko.emf.osgi.constants.EMFNamespaces;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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

import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.node.JsonNodeType;

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
@WithFactoryConfiguration(factoryPid = "QVTModelTransformator", location = "?", name = "jsonschemaecore", properties = {
		@Property(key = "transformator.id", value = "jsonschemaecore"),
		@Property(key = "qvt.template.path", value = "org.eclipse.fennec.ai.ecore.jsonschema.mmt/transforms/JsonSchemaToEcore.qvto"),
		@Property(key = "qvt.model.target", value = "(emf.name=jsonschema)")
})
public class CodecJsonSchemaTest {

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
	
	
	private static final String GEN_MODEL_ANNOTATION_SOURCE = "http://www.eclipse.org/emf/2002/GenModel";
	private static final String JSONSCHEMA_ANNOTATION_SOURCE = "http://fennec.eclipse.org/jsonschema";

	
	private String ePackageFileName;


	@BeforeEach()
	public void beforeEach() throws InterruptedException {
		ePackageFileName = "ePackage".concat(UUID.randomUUID().toString()).concat(".ecore");
		codecFactoryAware.waitForService(2000l);
		mapperAware.waitForService(2000l);
		codecModuleAware.waitForService(2000l);
	}
	
	@AfterEach
	public void afterEach() throws IOException {
		if(ePackageFileName != null) {
			Path path = Paths.get(ePackageFileName);
			if(Files.exists(path)) {
				Files.delete(path);
			}
		}
	}
	
	private Map<String, Object> getLoadOptions() {
		Map<String, Object> options = new HashMap<>();
		options.put(CodecResourceOptions.CODEC_ROOT_OBJECT, EcorePackage.eINSTANCE.getEPackage());
		Map<EClass, Map<String, Object>> classOptions = new HashMap<>();		
		Map<String, Object> schemaDefOptions = new HashMap<>();
		schemaDefOptions.put(CodecModelInfoOptions.CODEC_CUSTOM_DESERIALIZERS_MAP, Map.of(EcorePackage.Literals.EPACKAGE, new JsonSchemaToEPackageDeserializer()));
		classOptions.put(EcorePackage.Literals.EPACKAGE, schemaDefOptions);
		options.put(CodecResourceOptions.CODEC_OPTIONS, classOptions);
		return options;
	}
	
	private EPackage extractEPackageFromLoadedResource(Resource resource) {
		assertFalse(resource.getContents().isEmpty());
		EObject obj = resource.getContents().get(0);
		assertNotNull(obj);
		assertThat(obj).isInstanceOf(EPackage.class);
		EPackage ePackage = (EPackage) obj;
		assertThat(ePackage).isNotNull();
		return ePackage;
	}

	@Test
	public void topLevelEClass() throws IOException {
		Resource resource = resourceSet.createResource(URI.createURI("test-data/top-level-eclass.json"));
		resource.load(getLoadOptions());
		EPackage ePackage = extractEPackageFromLoadedResource(resource);
		
		assertThat(ePackage.getEClassifiers()).hasSize(1);
		assertThat(ePackage.getEClassifiers().get(0)).isInstanceOf(EClass.class);
		EClass eClass = (EClass) ePackage.getEClassifiers().get(0);
		assertThat(eClass.getName()).isEqualTo("BlobResourceContents");
		assertThat(eClass.getEStructuralFeatures()).hasSize(3);
		EAttribute att1 = null, att2 = null, att3 = null;
		for(EStructuralFeature feature : eClass.getEStructuralFeatures()) {
			if(feature instanceof EAttribute att) {
				if("blob".equals(att.getName())) {
					att1 = att;
				} else if("mimeType".equals(att.getName())) {
					att2 = att;
				} else if("uri".equals(att.getName())) {
					att3 = att;
				} 
			}
		}
		assertThat(att1).isNotNull();
		assertThat(att2).isNotNull();
		assertThat(att3).isNotNull();
		
		assertThat(att1.getEType()).isEqualTo(EcorePackage.Literals.ESTRING);
		assertThat(att2.getEType()).isEqualTo(EcorePackage.Literals.ESTRING);
		assertThat(att3.getEType()).isEqualTo(EcorePackage.Literals.ESTRING);
		
		assertThat(att1.getLowerBound()).isEqualTo(1);
		assertThat(att2.getLowerBound()).isEqualTo(0);
		assertThat(att3.getLowerBound()).isEqualTo(1);
		
		assertThat(att1.getUpperBound()).isEqualTo(1);
		assertThat(att2.getUpperBound()).isEqualTo(1);
		assertThat(att3.getUpperBound()).isEqualTo(1);
		
		assertThat(att1.getEAnnotation(GEN_MODEL_ANNOTATION_SOURCE).getDetails().get("documentation")).isNotNull();
		assertThat(att2.getEAnnotation(GEN_MODEL_ANNOTATION_SOURCE).getDetails().get("documentation")).isNotNull();
		assertThat(att3.getEAnnotation(GEN_MODEL_ANNOTATION_SOURCE).getDetails().get("documentation")).isNotNull();
		
		assertThat(att1.getEAnnotation(JSONSCHEMA_ANNOTATION_SOURCE).getDetails().get("format")).isEqualTo("byte");
		assertThat(att3.getEAnnotation(JSONSCHEMA_ANNOTATION_SOURCE).getDetails().get("format")).isEqualTo("uri");
	}
	
	@Test
	public void topLevelEEnum() throws IOException {
		Resource resource = resourceSet.createResource(URI.createURI("test-data/top-level-enum.json"));
		resource.load(getLoadOptions());
		EPackage ePackage = extractEPackageFromLoadedResource(resource);
		
		assertThat(ePackage.getEClassifiers()).hasSize(1);
		assertThat(ePackage.getEClassifiers().get(0)).isInstanceOf(EEnum.class);
		EEnum eEnum = (EEnum) ePackage.getEClassifiers().get(0);
		
		assertThat(eEnum.getName()).isEqualTo("LoggingLevel");
		assertThat(eEnum.getEAnnotation(GEN_MODEL_ANNOTATION_SOURCE).getDetails().get("documentation")).isNotNull();
		
		assertThat(eEnum.getELiterals()).hasSize(3);
		EEnumLiteral lit1 = null, lit2 = null, lit3 = null;
		for(EEnumLiteral lit : eEnum.getELiterals()) {
			if("alert".equals(lit.getName())) {
				lit1 = lit;
			} else if("critical".equals(lit.getName())) {
				lit2 = lit;
			} else if("debug".equals(lit.getName())) {
				lit3 = lit;
			} 
		}
		assertThat(lit1).isNotNull();
		assertThat(lit2).isNotNull();
		assertThat(lit3).isNotNull();
		
		assertThat(lit1.getLiteral()).isEqualTo("alert");
		assertThat(lit1.getValue()).isEqualTo(0);
		
		assertThat(lit2.getLiteral()).isEqualTo("critical");
		assertThat(lit2.getValue()).isEqualTo(1);
		
		assertThat(lit3.getLiteral()).isEqualTo("debug");
		assertThat(lit3.getValue()).isEqualTo(2);		
	}
	
	@Test
	public void topLevelObjectEDataType() throws IOException {
		Resource resource = resourceSet.createResource(URI.createURI("test-data/top-level-object-edatatype.json"));
		resource.load(getLoadOptions());
		EPackage ePackage = extractEPackageFromLoadedResource(resource);
		
		assertThat(ePackage.getEClassifiers()).hasSize(1);
		assertThat(ePackage.getEClassifiers().get(0)).isInstanceOf(EDataType.class);
		EDataType eDataType = (EDataType) ePackage.getEClassifiers().get(0);
		
		assertThat(eDataType.getName()).isEqualTo("RequestId");
		assertThat(eDataType.getEAnnotation(GEN_MODEL_ANNOTATION_SOURCE).getDetails().get("documentation")).isNotNull();
		
		assertThat(eDataType.getInstanceClass()).isEqualTo(Object.class);
		assertThat(eDataType.getInstanceClassName()).isEqualTo("java.lang.Object");
	}
	
	@Test
	public void topLevelSimpleEDataType() throws IOException {
		Resource resource = resourceSet.createResource(URI.createURI("test-data/top-level-simple-edatatype.json"));
		resource.load(getLoadOptions());
		EPackage ePackage = extractEPackageFromLoadedResource(resource);
		
		assertThat(ePackage.getEClassifiers()).hasSize(1);
		assertThat(ePackage.getEClassifiers().get(0)).isInstanceOf(EDataType.class);
		EDataType eDataType = (EDataType) ePackage.getEClassifiers().get(0);
		
		assertThat(eDataType.getName()).isEqualTo("RequestId");
		assertThat(eDataType.getEAnnotation(GEN_MODEL_ANNOTATION_SOURCE).getDetails().get("documentation")).isNotNull();
		
		assertThat(eDataType.getInstanceClass()).isEqualTo(String.class);
		assertThat(eDataType.getInstanceClassName()).isEqualTo("java.lang.String");
	}
	
	@Test
	public void topLevelArray() throws IOException {
		Resource resource = resourceSet.createResource(URI.createURI("test-data/top-level-array.json"));
		resource.load(getLoadOptions());
		EPackage ePackage = extractEPackageFromLoadedResource(resource);
		
		assertThat(ePackage.getEClassifiers()).hasSize(1);
		assertThat(ePackage.getEClassifiers().get(0)).isInstanceOf(EClass.class);
		EClass eClass = (EClass) ePackage.getEClassifiers().get(0);
		
		assertThat(eClass.getName()).isEqualTo("JSONRPCBatchRequest");
		assertThat(eClass.getEAnnotation(GEN_MODEL_ANNOTATION_SOURCE).getDetails().get("documentation")).isNotNull();
		assertThat(eClass.getEAnnotation(JSONSCHEMA_ANNOTATION_SOURCE).getDetails().get("source")).isEqualTo("TopLevelArray");
		assertThat(eClass.getEAnnotation(JSONSCHEMA_ANNOTATION_SOURCE).getDetails().get("artificial")).isEqualTo("true");
		
		assertThat(eClass.getEStructuralFeatures()).hasSize(1);
		assertThat(eClass.getEStructuralFeatures().get(0)).isInstanceOf(EAttribute.class);
		
		EAttribute att = (EAttribute) eClass.getEStructuralFeatures().get(0);
		assertThat(att.getName()).isEqualTo("items");
		assertThat(att.getEType()).isEqualTo(EcorePackage.Literals.ESTRING);
		assertTrue(att.isMany());
	}

	@Test
	public void anyOfWithRef() throws IOException {
		Resource resource = resourceSet.createResource(URI.createURI("test-data/anyOf-with-ref.json"));
		resource.load(getLoadOptions());
		EPackage ePackage = extractEPackageFromLoadedResource(resource);
		
		assertThat(ePackage.getEClassifiers()).hasSize(4);
		EClass ec1 = null, ec2 = null, ec3 = null, ec4 = null;
		for(EClassifier cl : ePackage.getEClassifiers()) {
			if(cl instanceof EClass ec) {
				if("ReadResourceResult".equals(ec.getName())) {
					ec1 = ec;
				} else if("TextResourceContents".equals(ec.getName())) {
					ec2 = ec;
				} else if("BlobResourceContents".equals(ec.getName())) {
					ec3 = ec;
				} else if("ResourceContents".equals(ec.getName())) {
					ec4 = ec;
				} 
			}
		}
		assertThat(ec1).isNotNull();
		assertThat(ec2).isNotNull();
		assertThat(ec3).isNotNull();
		assertThat(ec4).isNotNull();
		
		assertThat(ec1.getEStructuralFeatures()).hasSize(1);
		assertThat(ec1.getEStructuralFeatures().get(0)).isInstanceOf(EReference.class);
		EReference ref = (EReference) ec1.getEStructuralFeatures().get(0);
		assertThat(ref.getEAnnotation(JSONSCHEMA_ANNOTATION_SOURCE)).isNotNull();
		assertThat(ref.getEAnnotation(JSONSCHEMA_ANNOTATION_SOURCE).getDetails().get("source")).isEqualTo("anyOf");
		
		assertThat(ref.getName()).isEqualTo("contents");
		assertFalse(ref.isContainment());
		assertTrue(ref.isMany());
		assertThat(ref.getEType()).isEqualTo(ec4);
		
		assertThat(ec2.getEStructuralFeatures()).hasSize(1);
		assertThat(ec2.getEStructuralFeatures().get(0).getName()).isEqualTo("text");
		assertThat(ec2.getESuperTypes()).hasSize(1);
		assertThat(ec2.getESuperTypes().get(0)).isEqualTo(ec4);
		
		assertThat(ec3.getEStructuralFeatures()).hasSize(1);
		assertThat(ec3.getEStructuralFeatures().get(0).getName()).isEqualTo("blob");
		assertThat(ec3.getESuperTypes()).hasSize(1);
		assertThat(ec3.getESuperTypes().get(0)).isEqualTo(ec4);
		
		assertThat(ec4.getEAnnotation(JSONSCHEMA_ANNOTATION_SOURCE)).isNotNull();
		assertThat(ec4.getEAnnotation(JSONSCHEMA_ANNOTATION_SOURCE).getDetails().get("artificial")).isEqualTo("true");
		assertThat(ec4.getEStructuralFeatures()).hasSize(2);
		EAttribute att1 = null, att2 = null;
		for(EStructuralFeature feature : ec4.getEStructuralFeatures()) {
			if(feature instanceof EAttribute att) {
				if("mimeType".equals(att.getName())) {
					att1 = att;
				} else if("uri".equals(att.getName())) {
					att2 = att;
				}
			}
		}
		assertThat(att1).isNotNull();
		assertThat(att2).isNotNull();
		
		assertThat(att1.getLowerBound()).isEqualTo(0);
		assertThat(att2.getLowerBound()).isEqualTo(1);		
	}
	
	@Disabled
	@Test
	public void anyOfWithObj() throws IOException {
		
	}
	
	@Disabled
	@Test 
	public void anyOfMixed() throws IOException {
		
	}
	
	@Test
	public void containedRef() throws IOException {
		Resource resource = resourceSet.createResource(URI.createURI("test-data/contained-ref.json"));
		resource.load(getLoadOptions());
		EPackage ePackage = extractEPackageFromLoadedResource(resource);
		
		assertThat(ePackage.getEClassifiers()).hasSize(2);
		assertThat(ePackage.getEClassifiers().get(0)).isInstanceOf(EClass.class);
		
		EClass ec1 = null, ec2 = null;
		for(EClassifier cl : ePackage.getEClassifiers()) {
			if(cl instanceof EClass ec) {
				if("CompleteResult".equals(ec.getName())) {
					ec1 = ec;
				} else if("ArtificialClassifier0".equals(ec.getName())) {
					ec2 = ec;
				} 
			}
		}
		assertThat(ec1).isNotNull();
		assertThat(ec2).isNotNull();
		
		assertThat(ec1.getEStructuralFeatures()).hasSize(1);
		assertThat(ec1.getEStructuralFeatures().get(0)).isInstanceOf(EReference.class);
		EReference ref = (EReference) ec1.getEStructuralFeatures().get(0);
		assertThat(ref.getName()).isEqualTo("_meta");
		assertThat(ref.getEType()).isEqualTo(ec2);
		assertTrue(ref.isContainment());
		
		assertThat(ec2.getEAnnotation(GEN_MODEL_ANNOTATION_SOURCE)).isNotNull();
		assertThat(ec2.getEAnnotation(GEN_MODEL_ANNOTATION_SOURCE).getDetails().get("documentation")).isNotNull();
		assertThat(ec2.getEAnnotation(JSONSCHEMA_ANNOTATION_SOURCE)).isNotNull();
		assertThat(ec2.getEAnnotation(JSONSCHEMA_ANNOTATION_SOURCE).getDetails().get("artificial")).isEqualTo("true");
		assertThat(ec2.getEStructuralFeatures()).hasSize(0);
	}
	
	@Test
	public void containedReusedRef() throws IOException {
		Resource resource = resourceSet.createResource(URI.createURI("test-data/contained-reused-ref.json"));
		resource.load(getLoadOptions());
		EPackage ePackage = extractEPackageFromLoadedResource(resource);
		
		assertThat(ePackage.getEClassifiers()).hasSize(3);
		assertThat(ePackage.getEClassifiers().get(0)).isInstanceOf(EClass.class);
		
		EClass ec1 = null, ec2 = null, ec3 = null;
		for(EClassifier cl : ePackage.getEClassifiers()) {
			if(cl instanceof EClass ec) {
				if("CompleteResult".equals(ec.getName())) {
					ec1 = ec;
				} else if("ArtificialClassifier0".equals(ec.getName())) {
					ec2 = ec;
				} else if("PartialResult".equals(ec.getName())) {
					ec3 = ec;
				} 
			}
		}
		assertThat(ec1).isNotNull();
		assertThat(ec2).isNotNull();
		assertThat(ec3).isNotNull();
		
		assertThat(ec1.getEStructuralFeatures()).hasSize(1);
		assertThat(ec1.getEStructuralFeatures().get(0)).isInstanceOf(EReference.class);
		EReference ref = (EReference) ec1.getEStructuralFeatures().get(0);
		assertThat(ref.getName()).isEqualTo("_meta");
		assertThat(ref.getEType()).isEqualTo(ec2);
		assertTrue(ref.isContainment());
		
		assertThat(ec3.getEStructuralFeatures()).hasSize(1);
		assertThat(ec3.getEStructuralFeatures().get(0)).isInstanceOf(EReference.class);
		ref = (EReference) ec3.getEStructuralFeatures().get(0);
		assertThat(ref.getName()).isEqualTo("meta");
		assertThat(ref.getEType()).isEqualTo(ec2);
		assertTrue(ref.isContainment());
		
		assertThat(ec2.getEAnnotation(GEN_MODEL_ANNOTATION_SOURCE)).isNotNull();
		assertThat(ec2.getEAnnotation(GEN_MODEL_ANNOTATION_SOURCE).getDetails().get("documentation")).isNotNull();
		assertThat(ec2.getEAnnotation(JSONSCHEMA_ANNOTATION_SOURCE)).isNotNull();
		assertThat(ec2.getEAnnotation(JSONSCHEMA_ANNOTATION_SOURCE).getDetails().get("artificial")).isEqualTo("true");
		assertThat(ec2.getEStructuralFeatures()).hasSize(0);
	}
	
	@Test
	public void manyContainedRef() throws IOException {
		Resource resource = resourceSet.createResource(URI.createURI("test-data/many-contained-ref.json"));
		resource.load(getLoadOptions());
		EPackage ePackage = extractEPackageFromLoadedResource(resource);
		
		assertThat(ePackage.getEClassifiers()).hasSize(2);
		
		EClass ec1 = null, ec2 = null;
		for(EClassifier cl : ePackage.getEClassifiers()) {
			if(cl instanceof EClass ec) {
				if("CompleteResult".equals(ec.getName())) {
					ec1 = ec;
				} else if("ArtificialClassifier0".equals(ec.getName())) {
					ec2 = ec;
				} 
			}
		}
		assertThat(ec1).isNotNull();
		assertThat(ec2).isNotNull();
		
		assertThat(ec1.getEStructuralFeatures()).hasSize(1);
		assertThat(ec1.getEStructuralFeatures().get(0)).isInstanceOf(EReference.class);
		EReference ref = (EReference) ec1.getEStructuralFeatures().get(0);
		assertThat(ref.getName()).isEqualTo("_meta");
		assertThat(ref.getEType()).isEqualTo(ec2);
		assertTrue(ref.isContainment());
		assertTrue(ref.isMany());
		
		assertThat(ec2.getEAnnotation(JSONSCHEMA_ANNOTATION_SOURCE)).isNotNull();
		assertThat(ec2.getEAnnotation(JSONSCHEMA_ANNOTATION_SOURCE).getDetails().get("artificial")).isEqualTo("true");
		assertThat(ec2.getEStructuralFeatures()).hasSize(1);
		assertThat(ec2.getEStructuralFeatures().get(0)).isInstanceOf(EAttribute.class);
		EAttribute att = (EAttribute) ec2.getEStructuralFeatures().get(0);
		assertThat(att.getName()).isEqualTo("uri");
		assertThat(att.getEType()).isEqualTo(EcorePackage.Literals.ESTRING);
	}
	
	@Test
	public void nonContainedRef() throws IOException {
		Resource resource = resourceSet.createResource(URI.createURI("test-data/non-contained-ref.json"));
		resource.load(getLoadOptions());
		EPackage ePackage = extractEPackageFromLoadedResource(resource);
		
		assertThat(ePackage.getEClassifiers()).hasSize(2);
		
		EClass ec1 = null;
		EEnum ec2 = null;
		for(EClassifier cl : ePackage.getEClassifiers()) {
			if(cl instanceof EClass ec) {
				if("CreateMessageResult".equals(ec.getName())) {
					ec1 = ec;
				} 
			} else if(cl instanceof EEnum en) {
				if("Role".equals(en.getName())) {
					ec2 = en;
				}
			}
		}
		assertThat(ec1).isNotNull();
		assertThat(ec2).isNotNull();
		
		assertThat(ec1.getEStructuralFeatures()).hasSize(1);
		assertThat(ec1.getEStructuralFeatures().get(0)).isInstanceOf(EReference.class);
		EReference ref = (EReference) ec1.getEStructuralFeatures().get(0);
		assertFalse(ref.isContainment());
		assertFalse(ref.isMany());
		assertThat(ref.getEType()).isEqualTo(ec2);
	}
	
	@Test
	public void manyNonContainedRef() throws IOException {
		Resource resource = resourceSet.createResource(URI.createURI("test-data/many-non-contained-ref.json"));
		resource.load(getLoadOptions());
		EPackage ePackage = extractEPackageFromLoadedResource(resource);
		
		assertThat(ePackage.getEClassifiers()).hasSize(2);
		
		EClass ec1 = null;
		EEnum ec2 = null;
		for(EClassifier cl : ePackage.getEClassifiers()) {
			if(cl instanceof EClass ec) {
				if("CreateMessageResult".equals(ec.getName())) {
					ec1 = ec;
				} 
			} else if(cl instanceof EEnum en) {
				if("Role".equals(en.getName())) {
					ec2 = en;
				}
			}
		}
		assertThat(ec1).isNotNull();
		assertThat(ec2).isNotNull();
		
		assertThat(ec1.getEStructuralFeatures()).hasSize(1);
		assertThat(ec1.getEStructuralFeatures().get(0)).isInstanceOf(EReference.class);
		EReference ref = (EReference) ec1.getEStructuralFeatures().get(0);
		assertFalse(ref.isContainment());
		assertTrue(ref.isMany());
		assertThat(ref.getEType()).isEqualTo(ec2);
	}
	
	@Test
	public void singleAttribute() throws IOException {
		Resource resource = resourceSet.createResource(URI.createURI("test-data/single-attribute.json"));
		resource.load(getLoadOptions());
		EPackage ePackage = extractEPackageFromLoadedResource(resource);
		
		assertThat(ePackage.getEClassifiers()).hasSize(1);
		assertThat(ePackage.getEClassifiers().get(0)).isInstanceOf(EClass.class);
		EClass eClass = (EClass) ePackage.getEClassifiers().get(0);
		
		assertThat(eClass.getEStructuralFeatures()).hasSize(1);
		assertThat(eClass.getEStructuralFeatures().get(0)).isInstanceOf(EAttribute.class);
		EAttribute att = (EAttribute) eClass.getEStructuralFeatures().get(0);
		assertFalse(att.isMany());
		assertThat(att.getEType()).isEqualTo(EcorePackage.Literals.EDOUBLE);
	}
	
	@Test
	public void manyAttribute() throws IOException {
		Resource resource = resourceSet.createResource(URI.createURI("test-data/many-attribute.json"));
		resource.load(getLoadOptions());
		EPackage ePackage = extractEPackageFromLoadedResource(resource);
		
		assertThat(ePackage.getEClassifiers()).hasSize(1);
		assertThat(ePackage.getEClassifiers().get(0)).isInstanceOf(EClass.class);
		EClass eClass = (EClass) ePackage.getEClassifiers().get(0);
		
		assertThat(eClass.getEStructuralFeatures()).hasSize(1);
		assertThat(eClass.getEStructuralFeatures().get(0)).isInstanceOf(EAttribute.class);
		EAttribute att = (EAttribute) eClass.getEStructuralFeatures().get(0);
		assertTrue(att.isMany());
		assertThat(att.getEType()).isEqualTo(EcorePackage.Literals.ESTRING);
	}
	
	@Test
	public void constProperty() throws IOException {
		Resource resource = resourceSet.createResource(URI.createURI("test-data/const-property.json"));
		resource.load(getLoadOptions());
		EPackage ePackage = extractEPackageFromLoadedResource(resource);
		
		assertThat(ePackage.getEClassifiers()).hasSize(1);
		assertThat(ePackage.getEClassifiers().get(0)).isInstanceOf(EClass.class);
		EClass eClass = (EClass) ePackage.getEClassifiers().get(0);
		
		assertThat(eClass.getEStructuralFeatures()).hasSize(1);
		assertThat(eClass.getEStructuralFeatures().get(0)).isInstanceOf(EAttribute.class);
		EAttribute att = (EAttribute) eClass.getEStructuralFeatures().get(0);
		assertThat(att.getEType()).isEqualTo(EcorePackage.Literals.ESTRING);
		assertThat(att.getEAnnotation(JSONSCHEMA_ANNOTATION_SOURCE)).isNotNull();
		assertThat(att.getEAnnotation(JSONSCHEMA_ANNOTATION_SOURCE).getDetails().get("const")).isEqualTo("ref/prompt");
	}
	
	@Test
	public void constWoTypeProperty() throws IOException {
		Resource resource = resourceSet.createResource(URI.createURI("test-data/const-wo-type-property.json"));
		resource.load(getLoadOptions());
		EPackage ePackage = extractEPackageFromLoadedResource(resource);
		
		assertThat(ePackage.getEClassifiers()).hasSize(1);
		assertThat(ePackage.getEClassifiers().get(0)).isInstanceOf(EClass.class);
		EClass eClass = (EClass) ePackage.getEClassifiers().get(0);
		
		assertThat(eClass.getEStructuralFeatures()).hasSize(1);
		assertThat(eClass.getEStructuralFeatures().get(0)).isInstanceOf(EAttribute.class);
		EAttribute att = (EAttribute) eClass.getEStructuralFeatures().get(0);
		assertThat(att.getEType()).isEqualTo(EcorePackage.Literals.ESTRING);
		assertThat(att.getEAnnotation(JSONSCHEMA_ANNOTATION_SOURCE)).isNotNull();
		assertThat(att.getEAnnotation(JSONSCHEMA_ANNOTATION_SOURCE).getDetails().get("const")).isEqualTo("ref/prompt");
	}
	
	@Test
	public void constArray() throws IOException {
		Resource resource = resourceSet.createResource(URI.createURI("test-data/const-array.json"));
		resource.load(getLoadOptions());
		EPackage ePackage = extractEPackageFromLoadedResource(resource);
		
		assertThat(ePackage.getEClassifiers()).hasSize(1);
		assertThat(ePackage.getEClassifiers().get(0)).isInstanceOf(EClass.class);
		EClass eClass = (EClass) ePackage.getEClassifiers().get(0);
		
		assertThat(eClass.getEStructuralFeatures()).hasSize(1);
		assertThat(eClass.getEStructuralFeatures().get(0)).isInstanceOf(EAttribute.class);
		EAttribute att = (EAttribute) eClass.getEStructuralFeatures().get(0);
		assertThat(att.getEType()).isEqualTo(EcorePackage.Literals.EINT);
		assertThat(att.getEAnnotation(JSONSCHEMA_ANNOTATION_SOURCE)).isNotNull();
		assertThat(att.getEAnnotation(JSONSCHEMA_ANNOTATION_SOURCE).getDetails().get("const")).isEqualTo("[ 1, 2 ]");
	}
	
	@Test
	public void constArrayWOType() throws IOException {
		Resource resource = resourceSet.createResource(URI.createURI("test-data/const-wo-type-array.json"));
		resource.load(getLoadOptions());
		EPackage ePackage = extractEPackageFromLoadedResource(resource);
		
		assertThat(ePackage.getEClassifiers()).hasSize(1);
		assertThat(ePackage.getEClassifiers().get(0)).isInstanceOf(EClass.class);
		EClass eClass = (EClass) ePackage.getEClassifiers().get(0);
		
		assertThat(eClass.getEStructuralFeatures()).hasSize(1);
		assertThat(eClass.getEStructuralFeatures().get(0)).isInstanceOf(EAttribute.class);
		EAttribute att = (EAttribute) eClass.getEStructuralFeatures().get(0);
		assertThat(att.getEType()).isEqualTo(EcorePackage.Literals.EINT);
		assertThat(att.getEAnnotation(JSONSCHEMA_ANNOTATION_SOURCE)).isNotNull();
		assertThat(att.getEAnnotation(JSONSCHEMA_ANNOTATION_SOURCE).getDetails().get("const")).isEqualTo("[ 1, 2 ]");
	}

	
	@Disabled
	@Test
	public void testReadSimpleJsonSchema() throws IOException {
		Resource resource = resourceSet.createResource(URI.createURI("test-data/simple-test-jsonschema.json"));
		Map<String, Object> options = new HashMap<>();
		options.put(CodecResourceOptions.CODEC_ROOT_OBJECT, JsonSchemaPackage.eINSTANCE.getJsonSchemaDefinition());
		
		Map<EClass, Map<String, Object>> classOptions = new HashMap<>();		
		Map<String, Object> schemaDefOptions = new HashMap<>();
		schemaDefOptions.put(CodecModelInfoOptions.CODEC_CUSTOM_DESERIALIZERS_MAP, Map.of(JsonSchemaPackage.Literals.JSON_SCHEMA_DEFINITION, CODEC_JSON_SCHEMA_DEF_DES));
		classOptions.put(JsonSchemaPackage.Literals.JSON_SCHEMA_DEFINITION, schemaDefOptions);
		options.put(CodecResourceOptions.CODEC_OPTIONS, classOptions);
		resource.load(options);
		assertFalse(resource.getContents().isEmpty());
		EObject obj = resource.getContents().get(0);
		assertNotNull(obj);
		assertThat(obj).isInstanceOf(JsonSchemaDefinition.class);
		JsonSchemaDefinition jsonSchema = (JsonSchemaDefinition) obj;
		assertThat(jsonSchema).isNotNull();
		assertThat(jsonSchema.getDefinitions()).hasSize(1);
		assertTrue(jsonSchema.getDefinitions().containsKey("Annotations"));		
		assertThat(jsonSchema.getDefinitions().get("Annotations")).isInstanceOf(EClassJsonSchema.class);
		EClassJsonSchema classSchema = (EClassJsonSchema) jsonSchema.getDefinitions().get("Annotations");
		assertThat(classSchema.getType()).isEqualTo("object");
		assertThat(classSchema.getDescription()).isNotNull();
		assertThat(classSchema.getProperties()).hasSize(4);
		assertTrue(classSchema.getProperties().containsKey("audience"));
		assertTrue(classSchema.getProperties().containsKey("priority"));
		assertTrue(classSchema.getProperties().containsKey("includeContext"));
		assertTrue(classSchema.getProperties().containsKey("stopSequences"));
		
		assertThat(classSchema.getProperties().get("audience")).isInstanceOf(ArrayJsonSchema.class);
		ArrayJsonSchema audienceSchema = (ArrayJsonSchema) classSchema.getProperties().get("audience");
		assertThat(audienceSchema.getDescription()).isNotNull();
		assertThat(audienceSchema.getType()).isEqualTo("array");
		assertThat(audienceSchema.getItems()).isInstanceOf(ReferenceJsonSchema.class);
		assertThat(((ReferenceJsonSchema)audienceSchema.getItems()).getRef()).isEqualTo("#/definitions/Role");
		
		assertThat(classSchema.getProperties().get("priority")).isInstanceOf(EDataTypeJsonSchema.class);
		assertThat(classSchema.getProperties().get("priority").getDescription()).isNotNull();
		assertThat(classSchema.getProperties().get("priority").getType()).isEqualTo("number");

		assertThat(classSchema.getProperties().get("includeContext")).isInstanceOf(EnumJsonSchema.class);
		assertThat(classSchema.getProperties().get("includeContext").getDescription()).isNotNull();
		assertThat(classSchema.getProperties().get("includeContext").getType()).isEqualTo("string");
		assertThat(((EnumJsonSchema)classSchema.getProperties().get("includeContext")).getEnum()).contains("allServers", "none", "thisServer");
		
		assertThat(classSchema.getProperties().get("stopSequences")).isInstanceOf(ArrayJsonSchema.class);
		assertThat(classSchema.getProperties().get("stopSequences").getDescription()).isNull();
		assertThat(((ArrayJsonSchema)classSchema.getProperties().get("stopSequences")).getItems()).isInstanceOf(EDataTypeJsonSchema.class);
		assertThat(((ArrayJsonSchema)classSchema.getProperties().get("stopSequences")).getItems().getType()).isEqualTo("string");
	}

	@Disabled
	@Test
	public void testReadComplexJsonSchema(@InjectService ServiceAware<ModelTransformator> transformatorAware) throws IOException {
		Resource resource = resourceSet.createResource(URI.createURI("test-data/test-jsonschema.json"));
		Map<String, Object> options = new HashMap<>();
		options.put(CodecResourceOptions.CODEC_ROOT_OBJECT, JsonSchemaPackage.eINSTANCE.getJsonSchemaDefinition());
		
		Map<EClass, Map<String, Object>> classOptions = new HashMap<>();		
		Map<String, Object> schemaDefOptions = new HashMap<>();
		schemaDefOptions.put(CodecModelInfoOptions.CODEC_CUSTOM_DESERIALIZERS_MAP, Map.of(JsonSchemaPackage.Literals.JSON_SCHEMA_DEFINITION, CODEC_JSON_SCHEMA_DEF_DES));
		classOptions.put(JsonSchemaPackage.Literals.JSON_SCHEMA_DEFINITION, schemaDefOptions);
		options.put(CodecResourceOptions.CODEC_OPTIONS, classOptions);
		resource.load(options);
		assertFalse(resource.getContents().isEmpty());
		EObject obj = resource.getContents().get(0);
		assertNotNull(obj);
		assertThat(obj).isInstanceOf(JsonSchemaDefinition.class);
		JsonSchemaDefinition jsonSchema = (JsonSchemaDefinition) obj;
		assertThat(jsonSchema).isNotNull();
		
		assertThat(transformatorAware.getService()).isNotNull();
		ModelTransformator modelTransformator = transformatorAware.getService();
		EPackage ePackage = modelTransformator.doTransformation(jsonSchema);
		resource = resourceSet.createResource(URI.createURI("test-data/emf-jsonschema-ser.json"));
		resource.getContents().add(jsonSchema);
		resource.save(null);
		
		resource = resourceSet.createResource(URI.createURI("test-data/emf-jsonschema-ser.ecore"));
		resource.getContents().add(ePackage);
		resource.save(null);
	}
	
	
	@Disabled
	@Test
	public void testJsonSchemaToEPackage(@InjectService ServiceAware<ModelTransformator> transformatorAware) throws IOException {
		Resource resource = resourceSet.createResource(URI.createURI("test-data/test-jsonschema.json"));
		Map<String, Object> options = new HashMap<>();
		options.put(CodecResourceOptions.CODEC_ROOT_OBJECT, EcorePackage.eINSTANCE.getEPackage());
		
		Map<EClass, Map<String, Object>> classOptions = new HashMap<>();		
		Map<String, Object> schemaDefOptions = new HashMap<>();
		schemaDefOptions.put(CodecModelInfoOptions.CODEC_CUSTOM_DESERIALIZERS_MAP, Map.of(EcorePackage.Literals.EPACKAGE, new JsonSchemaToEPackageDeserializer()));
		classOptions.put(EcorePackage.Literals.EPACKAGE, schemaDefOptions);
		options.put(CodecResourceOptions.CODEC_OPTIONS, classOptions);
		resource.load(options);
		assertFalse(resource.getContents().isEmpty());
		EObject obj = resource.getContents().get(0);
		assertNotNull(obj);
		assertThat(obj).isInstanceOf(EPackage.class);
		EPackage ePackage = (EPackage) obj;
		assertThat(ePackage).isNotNull();
		ePackage.setName("test");
		ePackage.setNsPrefix("test");
		resource = resourceSet.createResource(URI.createURI("test-data/direct-emf-jsonschema-ser.ecore"));
		resource.getContents().add(ePackage);
		resource.save(null);
		
	}

	

	public static final CodecDeserializer<JsonSchemaDefinition> CODEC_JSON_SCHEMA_DEF_DES = new CodecDeserializer<JsonSchemaDefinition>() {

		private int artificialSchemaCounter = 0;
		
		@Override
		public String getName() {
			return "CODEC_JSON_SCHEMA_DEF_DES";
		}

		@Override
		public JsonSchemaDefinition deserialize(JsonParser parser, DeserializationContext ctxt) {
			JsonSchemaDefinition schemaDef = JsonSchemaFactory.eINSTANCE.createJsonSchemaDefinition();
			JsonNode node = ctxt.readTree(parser);
			JsonNode defNode = node.get("definitions");
			if(defNode != null &&  JsonNodeType.OBJECT.equals(defNode.getNodeType())) {
				for(String property : defNode.propertyNames()) {				
					JsonNode classNode = defNode.get(property);
					
//					We have an Enum at the level of the EPackage
					if(classNode.get("enum") != null) {
						EnumJsonSchema enumJsonSchema = JsonSchemaFactory.eINSTANCE.createEnumJsonSchema();
						enumJsonSchema.setType("string");
						enumJsonSchema.setTitle(classNode.get("title") == null ? null : classNode.get("title").asString());
						enumJsonSchema.setDescription(classNode.get("description") == null ? null : classNode.get("description").asString());
						for(int e = 0; e < classNode.get("enum").size(); e++) {
							enumJsonSchema.getEnum().add(classNode.get("enum").get(e).asString());
						}
						schemaDef.getDefinitions().put(property, enumJsonSchema);
					} else if(classNode.get("type") != null) {
						JsonNode typeNode = classNode.get("type");
//						Then we have an EClass
						if(typeNode.isString() && "object".equals(typeNode.asString())) {
							EClassJsonSchema classSchema = JsonSchemaFactory.eINSTANCE.createEClassJsonSchema();
							classSchema.setType("object");
							classSchema.setTitle(classNode.get("title") == null ? null : classNode.get("title").asString());
							classSchema.setDescription(classNode.get("description") == null ? null : classNode.get("description").asString());
							if(classNode.get("required") != null) {
								for(int r = 0; r < classNode.get("required").size(); r++) {
									classSchema.getRequired().add(classNode.get("required").get(r).asString());
									classSchema.getXEMFRequired().add(classNode.get("required").get(r).asString());
								}
							}
							JsonNode propertiesNode = classNode.get("properties");
							if(propertiesNode != null) {
								for(String p : propertiesNode.propertyNames()) {
									JsonSchema propertySchema = createPropertySchema(propertiesNode.get(p), schemaDef);
									if(propertySchema == null) {
										System.out.println(String.format("PropertySchema null for property %s of class %s", p, property));
									} else {
										classSchema.getProperties().put(p, propertySchema);
									}
									
								}						
							}			
							schemaDef.getDefinitions().put(property, classSchema);
						
						} else if(typeNode.isString() && "array".equals(typeNode.asString())) { //case as array at the top level 
							ArtificialEClassJsonSchema artificialSchema = JsonSchemaFactory.eINSTANCE.createArtificialEClassJsonSchema();
							artificialSchema.setType("object");
							artificialSchema.setTitle(classNode.get("title") == null ? null : classNode.get("title").asString());
							artificialSchema.setDescription(classNode.get("description") == null ? null : classNode.get("description").asString());
							artificialSchema.setXEMFArtificialType("array");
							artificialSchema.getProperties().put(property, createPropertySchema(classNode, schemaDef));		
							schemaDef.getDefinitions().put(property, artificialSchema);
						} else { //We should have a EDataType
							EDataTypeJsonSchema dataTypeJsonSchema = JsonSchemaFactory.eINSTANCE.createEDataTypeJsonSchema();
							dataTypeJsonSchema.setTitle(classNode.get("title") == null ? null : classNode.get("title").asString());
							dataTypeJsonSchema.setDescription(classNode.get("description") == null ? null : classNode.get("description").asString());
							if(typeNode.isArray()) { //we build an Object as DataType
								for(int t = 0; t < typeNode.size(); t++) {
									dataTypeJsonSchema.getTypeList().add(typeNode.get(t).asString());
									dataTypeJsonSchema.setXEMFInstanceClassName("java.lang.Object");
								}								
							} else {
								String type = typeNode.asString();
								switch(type) {
								case "boolean":
									dataTypeJsonSchema.setXEMFInstanceClassName("boolean");
									break;
								case "integer":
									dataTypeJsonSchema.setXEMFInstanceClassName("int");
									break;
								case "number":
									dataTypeJsonSchema.setXEMFInstanceClassName("double");
									break;
								case "string":
									dataTypeJsonSchema.setXEMFInstanceClassName("string");
									break;
								default:
									System.out.println(String.format("Unknown type for EDataTypeJsonSchema %s for property %s", type, property));
								}
								dataTypeJsonSchema.setType(typeNode.asString());								
							}
							schemaDef.getDefinitions().put(property, dataTypeJsonSchema);
						}
					}
				}
			}
		return schemaDef;
	}

	private JsonSchema createPropertySchema(JsonNode propertyNode, JsonSchemaDefinition schemaDef) {
		JsonSchema propertySchema = null;
		if(propertyNode.get("$ref") != null) {
			propertySchema = JsonSchemaFactory.eINSTANCE.createReferenceJsonSchema();
			propertySchema.setXEMFIsReference(true);
			propertySchema.setXEMFContained(false);
			((ReferenceJsonSchema) propertySchema).setRef(propertyNode.get("$ref").asString());
		} else if(propertyNode.get("type") != null) {
			String type = propertyNode.get("type").asString();
			switch(type) {
			case "array":
				propertySchema = JsonSchemaFactory.eINSTANCE.createArrayJsonSchema();
				JsonSchema itemsSchema = createPropertySchema(propertyNode.get("items"), schemaDef);
				((ArrayJsonSchema) propertySchema).setItems(itemsSchema);
//				decide wether this is an EAttribute or an EReference based on what items is
				if(itemsSchema instanceof EDataTypeJsonSchema || itemsSchema instanceof EnumJsonSchema) {
					propertySchema.setXEMFIsReference(false);
				} else {
					propertySchema.setXEMFIsReference(true);
				}
				
				break;
			case "number", "boolean", "integer":
				propertySchema = JsonSchemaFactory.eINSTANCE.createEDataTypeJsonSchema();
			break;
			case "string":
				if(propertyNode.get("enum") != null) {
					propertySchema = JsonSchemaFactory.eINSTANCE.createEnumJsonSchema();
					for(int e = 0; e < propertyNode.get("enum").size(); e++) {
						((EnumJsonSchema) propertySchema).getEnum().add(propertyNode.get("enum").get(e).asString());
					}
				} else {
					propertySchema = JsonSchemaFactory.eINSTANCE.createEDataTypeJsonSchema();
				}
				break;
			case "object":
//				If we have an object inside a property means we have to create a contained ref
				propertySchema = JsonSchemaFactory.eINSTANCE.createReferenceJsonSchema();
				propertySchema.setXEMFIsReference(true);
				propertySchema.setXEMFContained(true);
				ArtificialEClassJsonSchema refClassSchema = JsonSchemaFactory.eINSTANCE.createArtificialEClassJsonSchema();
				refClassSchema.setXEMFArtificialType("object");
				JsonNode propertiesNode = propertyNode.get("properties");
				if(propertiesNode != null) {
					for(String p : propertiesNode.propertyNames()) {
						JsonSchema innerPropertySchema = createPropertySchema(propertiesNode.get(p), schemaDef);
						refClassSchema.getProperties().put(p, innerPropertySchema);
					}						
				}		
				schemaDef.getDefinitions().put("ArtificialSchema"+artificialSchemaCounter, refClassSchema);
				((ReferenceJsonSchema)propertySchema).setRef("#/definitions/"+"ArtificialSchema"+artificialSchemaCounter);
				artificialSchemaCounter++;
				break;
			default:
				System.out.println(String.format("Type of jsonschema not known %s", type));
				propertySchema = JsonSchemaFactory.eINSTANCE.createJsonSchema();
				break;
			}
			propertySchema.setType(type);
		} else if(propertyNode.get("const") != null) { //if there is no type but a const, we infer the type from the const value
			JsonNode constNode = propertyNode.get("const");
			if(constNode.isArray()) {
				propertySchema = JsonSchemaFactory.eINSTANCE.createArrayJsonSchema();
				propertySchema.setXEMFIsReference(false);
			} else if(constNode.isObject()) {
//				this is quite tricky because this should result in another class. But what if a class with those properties already exists?
				
			}
			
		}
		else if(propertyNode.get("anyOf") != null) {
			ArtificialEClassJsonSchema refClassSchema = JsonSchemaFactory.eINSTANCE.createArtificialEClassJsonSchema();
			refClassSchema.setXEMFArtificialType("anyOf");
			for(int r = 0; r < propertyNode.get("anyOf").size(); r++) {
				refClassSchema.getXEMFSubSchemas().add(propertyNode.get("anyOf").get(r).get("$ref").asString());
			}	
			propertySchema = JsonSchemaFactory.eINSTANCE.createReferenceJsonSchema();
			propertySchema.setXEMFIsReference(true);
			propertySchema.setXEMFContained(false);
			schemaDef.getDefinitions().put("ArtificialSchema"+artificialSchemaCounter, refClassSchema);
			((ReferenceJsonSchema)propertySchema).setRef("#/definitions/"+"ArtificialSchema"+artificialSchemaCounter);
			artificialSchemaCounter++;					
		}
		if(propertySchema != null) {
			propertySchema.setDescription(propertyNode.get("description") == null ? null : propertyNode.get("description").asString());
			propertySchema.setTitle(propertyNode.get("title") == null ? null : propertyNode.get("title").asString());
		}
		
		return propertySchema;
	}

	@Override
	public void deserializeInto(JsonParser parser, DeserializationContext ctxt, JsonSchemaDefinition schema) {
	}
};




}
