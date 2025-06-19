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

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.fennec.codec.configurator.CodecFactoryConfigurator;
import org.eclipse.fennec.codec.configurator.CodecModuleConfigurator;
import org.eclipse.fennec.codec.configurator.ObjectMapperConfigurator;
import org.eclipse.fennec.codec.constants.CodecModelInfoOptions;
import org.eclipse.fennec.codec.constants.CodecResourceOptions;
import org.eclipse.fennec.codec.constants.ObjectMapperOptions;
import org.eclipse.fennec.codec.jsonschema.test.helper.EPackageToJsonSchemaSerializer;
import org.eclipse.fennec.codec.jsonschema.test.helper.JsonSchemaToEPackageDeserializer;
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

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
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
@WithFactoryConfiguration(factoryPid = "QVTModelTransformator", location = "?", name = "jsonschemaecore", properties = {
		@Property(key = "transformator.id", value = "jsonschemaecore"),
		@Property(key = "qvt.template.path", value = "org.eclipse.fennec.ai.ecore.jsonschema.mmt/transforms/JsonSchemaToEcore.qvto"),
		@Property(key = "qvt.model.target", value = "(emf.name=jsonschema)")
})
public class CodecJsonSchemaSerializationTest {

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



	@BeforeEach()
	public void beforeEach() throws InterruptedException {
		codecFactoryAware.waitForService(2000l);
		mapperAware.waitForService(2000l);
		codecModuleAware.waitForService(2000l);
	}


	private Map<String, Object> getSaveOptions() {
		Map<String, Object> options = new HashMap<>();
		options.put(CodecResourceOptions.CODEC_ROOT_OBJECT, EcorePackage.eINSTANCE.getEPackage());
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));
		Map<EClass, Map<String, Object>> classOptions = new HashMap<>();		
		Map<String, Object> schemaDefOptions = new HashMap<>();
		schemaDefOptions.put(CodecModelInfoOptions.CODEC_CUSTOM_SERIALIZERS_MAP, Map.of(EcorePackage.Literals.EPACKAGE, new EPackageToJsonSchemaSerializer()));
		classOptions.put(EcorePackage.Literals.EPACKAGE, schemaDefOptions);
		options.put(CodecResourceOptions.CODEC_OPTIONS, classOptions);
		return options;
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
		String file1 = "test-data/top-level-eclass.json";
		String file2 = "test-data/ser_top-level-eclass.json";
		Resource res = resourceSet.createResource(URI.createURI(file1));
		res.load(getLoadOptions());		
		EPackage ePackage = extractEPackageFromLoadedResource(res);

		res = resourceSet.createResource(URI.createURI(file2));
		res.getContents().add(ePackage);
		res.save(getSaveOptions());
		
		assertTrue(areJsonFilesTheSame(file1, file2));
	}

	@Test
	public void topLevelEEnum() throws IOException {

		String file1 = "test-data/top-level-enum.json";
		String file2 = "test-data/ser_top-level-enum.json";
		Resource res = resourceSet.createResource(URI.createURI(file1));
		res.load(getLoadOptions());		
		EPackage ePackage = extractEPackageFromLoadedResource(res);

		res = resourceSet.createResource(URI.createURI(file2));
		res.getContents().add(ePackage);
		res.save(getSaveOptions());
		
		assertTrue(areJsonFilesTheSame(file1, file2));
	}
	
	@Test
	public void singleAttribute() throws IOException {

		String file1 = "test-data/single-attribute.json";
		String file2 = "test-data/ser_single-attribute.json";
		Resource res = resourceSet.createResource(URI.createURI(file1));
		res.load(getLoadOptions());		
		EPackage ePackage = extractEPackageFromLoadedResource(res);

		res = resourceSet.createResource(URI.createURI(file2));
		res.getContents().add(ePackage);
		res.save(getSaveOptions());
		
		assertTrue(areJsonFilesTheSame(file1, file2));
	}
	
	@Test
	public void singleAttributeArrayType() throws IOException {

		String file1 = "test-data/single-attribute-array-type.json";
		String file2 = "test-data/ser_single-attribute-array-type.json";
		Resource res = resourceSet.createResource(URI.createURI(file1));
		res.load(getLoadOptions());		
		EPackage ePackage = extractEPackageFromLoadedResource(res);

		res = resourceSet.createResource(URI.createURI(file2));
		res.getContents().add(ePackage);
		res.save(getSaveOptions());
		
		assertTrue(areJsonFilesTheSame(file1, file2));
	}
	
	@Test
	public void singleAttributeEnum() throws IOException {

		String file1 = "test-data/single-attribute-enum.json";
		String file2 = "test-data/ser_single-attribute-enum.json";
		Resource res = resourceSet.createResource(URI.createURI(file1));
		res.load(getLoadOptions());		
		EPackage ePackage = extractEPackageFromLoadedResource(res);

		res = resourceSet.createResource(URI.createURI(file2));
		res.getContents().add(ePackage);
		res.save(getSaveOptions());
		
		assertTrue(areJsonFilesTheSame(file1, file2));
	}
	
	@Test
	public void singleAttributeEnumWOType() throws IOException {

		String file1 = "test-data/single-attribute-enum-wo-type.json";
		String file2 = "test-data/ser_single-attribute-enum-wo-type.json";
		Resource res = resourceSet.createResource(URI.createURI(file1));
		res.load(getLoadOptions());		
		EPackage ePackage = extractEPackageFromLoadedResource(res);

		res = resourceSet.createResource(URI.createURI(file2));
		res.getContents().add(ePackage);
		res.save(getSaveOptions());
		
		assertTrue(areJsonFilesTheSame(file1, file2));
	}
	
	@Test
	public void manyAttribute() throws IOException {

		String file1 = "test-data/many-attribute.json";
		String file2 = "test-data/ser_many-attribute.json";
		Resource res = resourceSet.createResource(URI.createURI(file1));
		res.load(getLoadOptions());		
		EPackage ePackage = extractEPackageFromLoadedResource(res);

		res = resourceSet.createResource(URI.createURI(file2));
		res.getContents().add(ePackage);
		res.save(getSaveOptions());
		
		assertTrue(areJsonFilesTheSame(file1, file2));
	}
	
	@Test
	public void manyAttributeConst() throws IOException {

		String file1 = "test-data/many-attribute-const.json";
		String file2 = "test-data/ser_many-attribute-const.json";
		Resource res = resourceSet.createResource(URI.createURI(file1));
		res.load(getLoadOptions());		
		EPackage ePackage = extractEPackageFromLoadedResource(res);

		res = resourceSet.createResource(URI.createURI(file2));
		res.getContents().add(ePackage);
		res.save(getSaveOptions());
		
		assertTrue(areJsonFilesTheSame(file1, file2));
	}
	
	@Test
	public void manyAttributeConstWOType() throws IOException {

		String file1 = "test-data/many-attribute-const-wo-type.json";
		String file2 = "test-data/ser_many-attribute-const-wo-type.json";
		Resource res = resourceSet.createResource(URI.createURI(file1));
		res.load(getLoadOptions());		
		EPackage ePackage = extractEPackageFromLoadedResource(res);

		res = resourceSet.createResource(URI.createURI(file2));
		res.getContents().add(ePackage);
		res.save(getSaveOptions());
		
		assertTrue(areJsonFilesTheSame(file1, file2));
	}
	
	@Test
	public void manyAttributeEnumWOType() throws IOException {

		String file1 = "test-data/many-attribute-enum-wo-type.json";
		String file2 = "test-data/ser_many-attribute-enum-wo-type.json";
		Resource res = resourceSet.createResource(URI.createURI(file1));
		res.load(getLoadOptions());		
		EPackage ePackage = extractEPackageFromLoadedResource(res);

		res = resourceSet.createResource(URI.createURI(file2));
		res.getContents().add(ePackage);
		res.save(getSaveOptions());
		
		assertTrue(areJsonFilesTheSame(file1, file2));
	}
	
	@Test
	public void manyAttributeEnum() throws IOException {

		String file1 = "test-data/many-attribute-enum.json";
		String file2 = "test-data/ser_many-attribute-enum.json";
		Resource res = resourceSet.createResource(URI.createURI(file1));
		res.load(getLoadOptions());		
		EPackage ePackage = extractEPackageFromLoadedResource(res);

		res = resourceSet.createResource(URI.createURI(file2));
		res.getContents().add(ePackage);
		res.save(getSaveOptions());
		
		assertTrue(areJsonFilesTheSame(file1, file2));
	}
	
	@Test
	public void singleAttributeConst() throws IOException {

		String file1 = "test-data/single-attribute-const.json";
		String file2 = "test-data/ser_single-attribute-const.json";
		Resource res = resourceSet.createResource(URI.createURI(file1));
		res.load(getLoadOptions());		
		EPackage ePackage = extractEPackageFromLoadedResource(res);

		res = resourceSet.createResource(URI.createURI(file2));
		res.getContents().add(ePackage);
		res.save(getSaveOptions());
		
		assertTrue(areJsonFilesTheSame(file1, file2));
	}
	
	@Test
	public void singleAttributeConstWOType() throws IOException {

		String file1 = "test-data/single-attribute-const-wo-type.json";
		String file2 = "test-data/ser_single-attribute-const-wo-type.json";
		Resource res = resourceSet.createResource(URI.createURI(file1));
		res.load(getLoadOptions());		
		EPackage ePackage = extractEPackageFromLoadedResource(res);

		res = resourceSet.createResource(URI.createURI(file2));
		res.getContents().add(ePackage);
		res.save(getSaveOptions());
		
		assertTrue(areJsonFilesTheSame(file1, file2));
	}

	private boolean areJsonFilesTheSame(String file1, String file2) {
		ObjectMapper mapper = new ObjectMapper();
		// Load JSON files as tree structures
		JsonNode json1 = mapper.readTree(new File(file1));
		JsonNode json2 = mapper.readTree(new File(file2));
		// Compare the two JSON objects
		return json1.equals(json2);
	}


}
