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
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.fennec.ai.jsonschema.model.jsonschema.JsonSchema;
import org.eclipse.fennec.ai.jsonschema.model.jsonschema.JsonSchemaPackage;
import org.eclipse.fennec.ai.test.model.test.Book;
import org.eclipse.fennec.ai.test.model.test.TestPackage;
import org.eclipse.fennec.codec.configurator.CodecFactoryConfigurator;
import org.eclipse.fennec.codec.configurator.CodecModuleConfigurator;
import org.eclipse.fennec.codec.configurator.ObjectMapperConfigurator;
import org.eclipse.fennec.codec.constants.CodecModuleOptions;
import org.eclipse.fennec.codec.constants.CodecResourceOptions;
import org.eclipse.fennec.codec.constants.ObjectMapperOptions;
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
	public void testLoadJsonSchema() throws IOException {
		Resource resource = resourceSet.createResource(URI.createURI(ctx.getBundle().getEntry("test-data/BookModel.json").toString()));
		Map<String, Object> options = new HashMap<>();
		options.put(CodecResourceOptions.CODEC_ROOT_OBJECT, JsonSchemaPackage.eINSTANCE.getJsonSchema());
		resource.load(options);

		assertThat(resource.getContents()).hasSize(1);
		assertThat(resource.getContents().get(0)).isInstanceOf(JsonSchema.class);

		JsonSchema schema = (JsonSchema) resource.getContents().get(0);

		assertThat(schema.getProperties()).hasSize(5);

		EMap<String, JsonSchema> mapValues = schema.getProperties();

		JsonSchema prop1 = mapValues.get("title");
		assertThat(prop1.getType()).isEqualTo("string");
	}
	
	@Test
	public void testSaveJsonSchema() throws IOException {
		Resource resource = resourceSet.createResource(URI.createURI(ctx.getBundle().getEntry("test-data/BookModel.json").toString()));
		Map<String, Object> options = new HashMap<>();
		options.put(CodecResourceOptions.CODEC_ROOT_OBJECT, JsonSchemaPackage.eINSTANCE.getJsonSchema());
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_DEFAULT_VALUE, true);
		options.put(CodecModuleOptions.CODEC_MODULE_SERIALIZE_TYPE, false);
		options.put(ObjectMapperOptions.OBJ_MAPPER_SERIALIZATION_FEATURES_WITH, List.of(SerializationFeature.INDENT_OUTPUT));

		resource.load(options);

		assertThat(resource.getContents()).hasSize(1);
		assertThat(resource.getContents().get(0)).isInstanceOf(JsonSchema.class);

		JsonSchema schema = (JsonSchema) resource.getContents().get(0);
		assertThat(schema.getProperties()).hasSize(5);

		EMap<String, JsonSchema> mapValues = schema.getProperties();

		JsonSchema prop1 = mapValues.get("title");
		assertThat(prop1.getType()).isEqualTo("string");
		
		resource.unload();
		resourceSet.getResources().clear();
		
		resource = resourceSet.createResource(URI.createURI("test_save_jsonschema.json"));
		resource.getContents().add(schema);
		resource.save(options);
		
	}
			
	@Test
	public void testLoadObjectFromAIOutput() throws IOException {
		
		// load ecore
		Resource resource = resourceSet.createResource(URI.createURI(ctx.getBundle().getEntry("test-data/book_ai_response.json").toString()));
		Map<String, Object> options = new HashMap<>();
		options.put(CodecResourceOptions.CODEC_ROOT_OBJECT, TestPackage.eINSTANCE.getBook());
		resource.load(options);

		assertThat(resource.getContents()).hasSize(1);
		assertThat(resource.getContents().get(0)).isInstanceOf(Book.class);
		Book book = (Book) resource.getContents().get(0);
		assertNotNull(book);
		assertThat(book.getTitle()).isEqualTo("Machine Learning in Java");
		assertThat(book.getAuthor()).isEqualTo("Bostjan Kaluza");
		assertThat(book.getPublicationYear()).isEqualTo(2014);

	}
}
