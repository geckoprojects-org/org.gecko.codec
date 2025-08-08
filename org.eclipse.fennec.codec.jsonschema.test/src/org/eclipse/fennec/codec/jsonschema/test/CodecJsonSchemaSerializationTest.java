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
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.DifferenceKind;
import org.eclipse.emf.compare.EMFCompare;
import org.eclipse.emf.compare.scope.DefaultComparisonScope;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.fennec.codec.configurator.CodecFactoryConfigurator;
import org.eclipse.fennec.codec.configurator.CodecModuleConfigurator;
import org.eclipse.fennec.codec.configurator.ObjectMapperConfigurator;
import org.eclipse.fennec.codec.options.CodecResourceOptions;
import org.eclipse.fennec.openapi.model.OpenApi;
import org.eclipse.fennec.openapi.model.OpenApiPackage;
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

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@RequireEMF
@ExtendWith(BundleContextExtension.class)
@ExtendWith(ServiceExtension.class)
@ExtendWith(ConfigurationExtension.class)
@WithFactoryConfiguration(factoryPid = "DefaultCodecFactoryConfigurator", location = "?", name = "test", properties = {
		@Property(key = "type", value = "json") })
@WithFactoryConfiguration(factoryPid = "DefaultObjectMapperConfigurator", location = "?", name = "test", properties = {
		@Property(key = "type", value = "json"),
		@Property(key = "disableFeatures", value={"JsonWriteFeature.ESCAPE_FORWARD_SLASHES"}, type = Type.Array),
		@Property(key = "enableFeatures", value={"SerializationFeature.INDENT_OUTPUT"}, type = Type.Array)		
})
@WithFactoryConfiguration(factoryPid = "DefaultCodecModuleConfigurator", location = "?", name = "test", properties = {
		@Property(key = "type", value = "json"),
		@Property(key = "serializers.target", value = "(component.name=JsonSchemaCodecEMFSerializers)"),
		@Property(key = "deserializers.target", value = "(component.name=JsonSchemaCodecEMFDeserializers)")
		
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
	
	private static final String JSONSCHEMA_ANNOTATION_SOURCE = "http://fennec.eclipse.org/jsonschema";

	private String file2;


	@BeforeEach()
	public void beforeEach() throws InterruptedException {
		codecFactoryAware.waitForService(2000l);
		mapperAware.waitForService(2000l);
		codecModuleAware.waitForService(2000l);
	}

	@AfterEach()
	public void afterEach() throws IOException {
//		if(file2 != null) Files.deleteIfExists(Path.of(file2));
	}
	
	
//	@Test
	public void topLevelEClass() throws IOException {
		String file1 = "test-data/top-level-eclass.json";
		file2 = "test-data/ser_top-level-eclass.json";
		executeTest(file1, file2);
	}
	
	@Test
	public void openAPIJsonSchema() throws IOException {
		String file1 = "test-data/open-api.json";
		file2 = "test-data/ser_open-api.json";
		executeTest(file1, file2);
	}
	
	@Test
	public void openAPIComplete() throws IOException {
		String file1 = "test-data/openapi-complete.json";
		file2 = "test-data/ser_openapi-complete.json";
		
		Resource res = resourceSet.createResource(URI.createURI(file1));
		Map<String, Object> options = new HashMap<>();
		options.put(CodecResourceOptions.CODEC_ROOT_OBJECT, OpenApiPackage.Literals.OPEN_API);
		
		res.load(options);		
		assertFalse(res.getContents().isEmpty());
		EObject obj = res.getContents().get(0);
		assertNotNull(obj);
		assertThat(obj).isInstanceOf(OpenApi.class);
		OpenApi openApi = (OpenApi) res.getContents().get(0);

		res = resourceSet.createResource(URI.createURI(file2));
		res.getContents().add(openApi);
		res.save(options);
		
		assertTrue(areJsonFilesTheSame(file1, file2));
	}

//	@Test
//	public void topLevelEEnum() throws IOException {
//
//		String file1 = "test-data/top-level-enum.json";
//		file2 = "test-data/ser_top-level-enum.json";
//		executeTest(file1, file2);
//	}
//	
//	@Test
//	public void topLevelArray() throws IOException {
//
//		String file1 = "test-data/top-level-array.json";
//		file2 = "test-data/ser_top-level-array.json";
//		executeTest(file1, file2);
//	}
//	
//	@Test
//	public void topLevelSimpleEDataType() throws IOException {
//
//		String file1 = "test-data/top-level-simple-edatatype.json";
//		file2 = "test-data/ser_top-level-simple-edatatype.json";
//		executeTest(file1, file2);
//	}
//	
//	@Test
//	public void topLevelObjectEDataType() throws IOException {
//
//		String file1 = "test-data/top-level-object-edatatype.json";
//		file2 = "test-data/ser_top-level-object-edatatype.json";
//		executeTest(file1, file2);
//	}
//	
//	@Test
//	public void singleAttribute() throws IOException {
//
//		String file1 = "test-data/single-attribute.json";
//		file2 = "test-data/ser_single-attribute.json";
//		executeTest(file1, file2);
//	}
//	
//	@Test
//	public void singleAttributeArrayType() throws IOException {
//
//		String file1 = "test-data/single-attribute-array-type.json";
//		file2 = "test-data/ser_single-attribute-array-type.json";
//		executeTest(file1, file2);
//	}
//	
//	@Test
//	public void singleAttributeEnum() throws IOException {
//
//		String file1 = "test-data/single-attribute-enum.json";
//		file2 = "test-data/ser_single-attribute-enum.json";
//		executeTest(file1, file2);
//	}
//	
//	@Test
//	public void singleAttributeEnumWOType() throws IOException {
//
//		String file1 = "test-data/single-attribute-enum-wo-type.json";
//		file2 = "test-data/ser_single-attribute-enum-wo-type.json";
//		executeTest(file1, file2);
//	}
//	
//	@Test
//	public void manyAttribute() throws IOException {
//
//		String file1 = "test-data/many-attribute.json";
//		file2 = "test-data/ser_many-attribute.json";
//		executeTest(file1, file2);
//	}
//	
//	@Test
//	public void manyAttributeConst() throws IOException {
//
//		String file1 = "test-data/many-attribute-const.json";
//		file2 = "test-data/ser_many-attribute-const.json";
//		executeTest(file1, file2);
//	}
//	
//	@Test
//	public void manyAttributeConstWOType() throws IOException {
//
//		String file1 = "test-data/many-attribute-const-wo-type.json";
//		file2 = "test-data/ser_many-attribute-const-wo-type.json";
//		executeTest(file1, file2);
//	}
//	
//	@Test
//	public void manyAttributeEnumWOType() throws IOException {
//
//		String file1 = "test-data/many-attribute-enum-wo-type.json";
//		file2 = "test-data/ser_many-attribute-enum-wo-type.json";
//		executeTest(file1, file2);
//	}
//	
//	@Test
//	public void manyAttributeEnum() throws IOException {
//
//		String file1 = "test-data/many-attribute-enum.json";
//		file2 = "test-data/ser_many-attribute-enum.json";
//		executeTest(file1, file2);
//	}
//	
//	@Test
//	public void singleAttributeConst() throws IOException {
//
//		String file1 = "test-data/single-attribute-const.json";
//		file2 = "test-data/ser_single-attribute-const.json";
//		executeTest(file1, file2);
//	}
//	
//	@Test
//	public void singleAttributeConstWOType() throws IOException {
//
//		String file1 = "test-data/single-attribute-const-wo-type.json";
//		file2 = "test-data/ser_single-attribute-const-wo-type.json";
//		executeTest(file1, file2);
//	}
//	
//	@Test
//	public void containedRef() throws IOException {
//
//		String file1 = "test-data/contained-ref.json";
//		file2 = "test-data/ser_contained-ref.json";
//		executeTest(file1, file2);
//	}
//	
//	@Test
//	public void manyContainedRef() throws IOException {
//
//		String file1 = "test-data/many-contained-ref.json";
//		file2 = "test-data/ser_many-contained-ref.json";
//		executeTest(file1, file2);
//	}
//	
//	@Test
//	public void containedReusedRef() throws IOException {
//
//		String file1 = "test-data/contained-reused-ref.json";
//		file2 = "test-data/ser_many-contained-reused-ref.json";
//		executeTest(file1, file2);
//	}
//	
//	@Test
//	public void nonContainedRef() throws IOException {
//
//		String file1 = "test-data/non-contained-ref.json";
//		file2 = "test-data/ser_non-contained-ref.json";
//		executeTest(file1, file2);
//	}
//	
//	@Test
//	public void manyNonContainedRef() throws IOException {
//
//		String file1 = "test-data/many-non-contained-ref.json";
//		file2 = "test-data/ser_many-non-contained-ref.json";
//		executeTest(file1, file2);
//	}
//	
//	@Test
//	public void anyOfWithRef() throws IOException {
//
//		String file1 = "test-data/anyOf-with-ref.json";
//		file2 = "test-data/ser_anyOf-with-ref.json";
//		executeTest(file1, file2);
//	}
	
	private void executeTest(String file1, String file2) throws IOException {
		Resource res = resourceSet.createResource(URI.createURI(file1));
		res.load(getLoadOptions());		
		EPackage ePackage = extractEPackageFromLoadedResource(res);

		res = resourceSet.createResource(URI.createURI(file2));
		res.getContents().add(ePackage);
		res.save(getSaveOptions());
		
		assertTrue(areJsonFilesTheSame(file1, file2));
	}
	
	
	
	
//	@Test
//	public void ePackageSimpleAttribute() throws IOException {
//		
//		EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
//		ePackage.setName("test");
//		ePackage.setNsURI("www.test.org");
//		addEAnnotation(ePackage, JSONSCHEMA_ANNOTATION_SOURCE, "schema", "jsonschema/json.org");
//		
//		EClass eClass = EcoreFactory.eINSTANCE.createEClass();
//		eClass.setName("Person");
//		EAttribute eAttribute = EcoreFactory.eINSTANCE.createEAttribute();
//		eAttribute.setName("firstName");
//		eAttribute.setEType(EcorePackage.Literals.ESTRING);
//		eClass.getEStructuralFeatures().add(eAttribute);
//		ePackage.getEClassifiers().add(eClass);
//		
//		Resource res = resourceSet.createResource(URI.createURI("epackage.json"));
//		res.getContents().add(ePackage);
//		res.save(getSaveOptions());
//		
//		res = resourceSet.createResource(URI.createURI("epackage.json"));
//		res.load(getLoadOptions());
//		
//		assertThat(res.getContents()).isNotEmpty();
//		assertThat(res.getContents().get(0)).isInstanceOf(EPackage.class);
//		EPackage desEpackage = (EPackage) res.getContents().get(0);
//		assertTrue(areEPackagesTheSame(ePackage, desEpackage));
//		
//	}
//	
//	@Test
//	public void ePackageAllOf() throws IOException {
//		
//		EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
//		ePackage.setName("test");
//		ePackage.setNsURI("www.test.org");
//		addEAnnotation(ePackage, JSONSCHEMA_ANNOTATION_SOURCE, "schema", "jsonschema/json.org");
//		
//		EClass eClass = createEClass("Parent");
//		eClass.getEStructuralFeatures().add(createEAttribute("firstName", EcorePackage.Literals.ESTRING));
//		ePackage.getEClassifiers().add(eClass);
//		
//		EClass eClass2 = createEClass("Child1");
//		eClass2.getESuperTypes().add(eClass);
//		eClass2.getEStructuralFeatures().add(createEAttribute("lastName", EcorePackage.Literals.ESTRING));
//		ePackage.getEClassifiers().add(eClass2);
//		
//		EClass eClass3 = createEClass("Child2");
//		eClass3.getESuperTypes().add(eClass);
//		eClass3.getEStructuralFeatures().add(createEAttribute("middleName", EcorePackage.Literals.ESTRING));
//		ePackage.getEClassifiers().add(eClass3);
//		
//		Resource res = resourceSet.createResource(URI.createURI("epackage.json"));
//		res.getContents().add(ePackage);
//		res.save(getSaveOptions());
//		
//		res = resourceSet.createResource(URI.createURI("epackage.json"));
//		res.load(getLoadOptions());
//		
//		assertThat(res.getContents()).isNotEmpty();
//		assertThat(res.getContents().get(0)).isInstanceOf(EPackage.class);
//		EPackage desEpackage = (EPackage) res.getContents().get(0);
//		assertTrue(areEPackagesTheSame(ePackage, desEpackage));		
//	}
	
	@Disabled("We need to understand which behaviour we want here!")
	@Test
	public void ePackageAnyOf() throws IOException {
		
		EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
		ePackage.setName("test");
		ePackage.setNsURI("www.test.org");
		addEAnnotation(ePackage, JSONSCHEMA_ANNOTATION_SOURCE, "schema", "jsonschema/json.org");
		
		EClass eClass = createEClass("Parent");
		eClass.getEStructuralFeatures().add(createEAttribute("firstName", EcorePackage.Literals.ESTRING));
		ePackage.getEClassifiers().add(eClass);
		
		EClass eClass2 = createEClass("Child1");
		eClass2.getESuperTypes().add(eClass);
		eClass2.getEStructuralFeatures().add(createEAttribute("lastName", EcorePackage.Literals.ESTRING));
		ePackage.getEClassifiers().add(eClass2);
		
		EClass eClass3 = createEClass("Child2");
		eClass3.getESuperTypes().add(eClass);
		eClass3.getEStructuralFeatures().add(createEAttribute("middleName", EcorePackage.Literals.ESTRING));
		ePackage.getEClassifiers().add(eClass3);
		
		EClass eClass4 = createEClass("Test");
		eClass4.getEStructuralFeatures().add(createEReference("ref", eClass));
		ePackage.getEClassifiers().add(eClass4);
		
		Resource res = resourceSet.createResource(URI.createURI("epackage.json"));
		res.getContents().add(ePackage);
		res.save(getSaveOptions());
		
		res = resourceSet.createResource(URI.createURI("epackage.json"));
		res.load(getLoadOptions());
		
		assertThat(res.getContents()).isNotEmpty();
		assertThat(res.getContents().get(0)).isInstanceOf(EPackage.class);
		EPackage desEpackage = (EPackage) res.getContents().get(0);
		assertTrue(areEPackagesTheSame(ePackage, desEpackage));		
	}
	
	
	private Map<String, Object> getSaveOptions() {
		Map<String, Object> options = new HashMap<>();
		options.put(CodecResourceOptions.CODEC_ROOT_OBJECT, EcorePackage.eINSTANCE.getEPackage());
		return options;
	}

	private Map<String, Object> getLoadOptions() {
		Map<String, Object> options = new HashMap<>();
		options.put(CodecResourceOptions.CODEC_ROOT_OBJECT, EcorePackage.eINSTANCE.getEPackage());
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
	
	private EClass createEClass(String name) {
		EClass eClass = EcoreFactory.eINSTANCE.createEClass();
		eClass.setName(name);
		return eClass;
	}
	
	private EAttribute createEAttribute(String name, EDataType type) {
		EAttribute eAttribute = EcoreFactory.eINSTANCE.createEAttribute();
		eAttribute.setName(name);
		eAttribute.setEType(type);
		return eAttribute;
	}
	
	private EReference createEReference(String name, EClassifier type) {
		EReference eReference = EcoreFactory.eINSTANCE.createEReference();
		eReference.setName(name);
		eReference.setEType(type);
		return eReference;
	}
	
	private EAnnotation createEAnnotation(String source, String key, String value) {
		EAnnotation eAnnotation = EcoreFactory.eINSTANCE.createEAnnotation();
		eAnnotation.setSource(source);
		eAnnotation.getDetails().put(key, value);
		return eAnnotation;
	}
	
	private void addEAnnotation(EModelElement element, String source, String detailKey, String detailValue) {
		if(element.getEAnnotation(source) != null) {
			element.getEAnnotation(source).getDetails().put(detailKey, detailValue);
		} else {
			element.getEAnnotations().add(createEAnnotation(source, detailKey, detailValue));
		}
	}


	private boolean areJsonFilesTheSame(String file1, String file2) {
		ObjectMapper mapper = new ObjectMapper();
		// Load JSON files as tree structures
		JsonNode json1 = mapper.readTree(new File(file1));
		JsonNode json2 = mapper.readTree(new File(file2));
		// Compare the two JSON objects
		return json1.equals(json2);
	}
	
	private boolean areEPackagesTheSame(EPackage ePackage1, EPackage ePackage2) {
		Comparison comparison = EMFCompare.builder().build().compare(
				new DefaultComparisonScope(ePackage1, ePackage2, null)
			);
		return comparison.getDifferences().stream().filter(d -> !DifferenceKind.MOVE.equals(d.getKind())).toList().isEmpty();
	}


}