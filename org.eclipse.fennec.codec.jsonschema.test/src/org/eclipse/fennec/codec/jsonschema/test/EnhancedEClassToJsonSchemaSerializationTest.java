/**
 * Copyright (c) 2012 - 2025 Data In Motion and others.
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fennec.ai.nsc.trend.analysis.model.trendanalysis.TrendAnalysisPackage;
import org.eclipse.fennec.codec.info.CodecModelInfo;
import org.eclipse.fennec.codec.jsonschema.options.CodecJsonSchemaOptionsBuilder;
import org.eclipse.fennec.codec.jsonschema.resource.JsonSchemaGenerator;
import org.eclipse.fennec.lorawan.uplink.model.lorawan.LorawanPackage;
import org.gecko.emf.osgi.annotation.require.RequireEMF;
import org.gecko.emf.osgi.constants.EMFNamespaces;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.common.service.ServiceAware;
import org.osgi.test.junit5.context.BundleContextExtension;
import org.osgi.test.junit5.service.ServiceExtension;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

/**
 * Tests for EnhancedEClassToJsonSchemaSerializer that serializes individual EClass instances to JSON Schema.
 *
 * @author Claude Code
 * @since Dec 12, 2025
 */
@RequireEMF
@ExtendWith(BundleContextExtension.class)
@ExtendWith(ServiceExtension.class)
public class EnhancedEClassToJsonSchemaSerializationTest {

	@InjectService(filter="("+EMFNamespaces.EMF_MODEL_CONTENT_TYPE + "=application/schema+json)")
	ResourceSet resourceSet;

	@InjectService
	CodecModelInfo codecModelInfo;

	private String outputFile;

	@BeforeEach()
	public void beforeEach() throws InterruptedException {
		assertNotNull(resourceSet);
	}

	@AfterEach()
	public void afterEach() throws IOException {
		if(outputFile != null) {
			Files.deleteIfExists(Path.of(outputFile));
		}
	}

	/**
	 * Test serializing the RxInfo EClass which has:
	 * - Multiple attributes (gatewayId, uplinkId, rssi, snr, etc.)
	 * - Two containment references (location, metadata)
	 * Should collect all referenced classes recursively.
	 */
	@Test
	public void testRxInfoEClassWithMultipleReferences() throws IOException {
		outputFile = System.getProperty("data")+"rxinfo-eclass.jsonschema";

		// Get the RxInfo EClass
		EClass rxInfoEClass = LorawanPackage.eINSTANCE.getRxInfo();
		assertNotNull(rxInfoEClass);

		// Create options for EClass serialization
		Map<String, Object> options = CodecJsonSchemaOptionsBuilder.create()
				
				.rootObject(EcorePackage.Literals.ECLASS)
				.serializeType(false)
				
				
				.forClass(EcorePackage.Literals.ECLASS)
					.schemaFeatureKey("definitions")
				.build();

		// Serialize EClass to JSON Schema
		Resource res = resourceSet.createResource(URI.createURI(outputFile), "application/schema+json");
		EClass copy = EcoreUtil.copy(rxInfoEClass);
		res.getContents().add(copy);
		res.save(options);

		// Verify the file was created
		assertTrue(Files.exists(Path.of(outputFile)));

		// Load and verify the JSON Schema structure
		ObjectMapper mapper = new ObjectMapper();
		JsonNode schema = mapper.readTree(new File(outputFile));

		// Verify top-level metadata
		assertThat(schema.get("title").asString()).isEqualTo("RxInfo");
		assertThat(schema.get("type").asString()).isEqualTo("object");

		// Verify properties
		JsonNode properties = schema.get("properties");
		assertTrue(properties.has("gatewayId"), "Should have gatewayId");
		assertTrue(properties.has("rssi"), "Should have rssi");
		assertTrue(properties.has("snr"), "Should have snr");
		assertTrue(properties.has("location"), "Should have location reference");
		assertTrue(properties.has("metadata"), "Should have metadata reference");

		// Verify both referenced classes are in definitions
		JsonNode definitions = schema.get("definitions");
		assertTrue(definitions.has("Location"), "definitions should contain Location");
		assertTrue(definitions.has("Metadata"), "definitions should contain Metadata");

		// Verify Location structure
		JsonNode location = definitions.get("Location");
		assertThat(location.get("type").asString()).isEqualTo("object");
		JsonNode locationProps = location.get("properties");
		assertTrue(locationProps.has("latitude"), "Location should have latitude");
		assertTrue(locationProps.has("longitude"), "Location should have longitude");
		assertTrue(locationProps.has("altitude"), "Location should have altitude");
	}

	/**
	 * Test serializing the UplinkMessage EClass which:
	 * - Is abstract
	 * - Has multiple attributes and references
	 * - Has a reference to DeviceInfo which has a reference to Tags (multi-level)
	 * Should collect all transitively referenced classes.
	 */
	@Test
	public void testUplinkMessageWithTransitiveReferences() throws IOException {
		outputFile = System.getProperty("data")+"uplinkmessage-eclass.jsonschema";

		// Get the UplinkMessage EClass
		EClass uplinkMessageEClass = LorawanPackage.eINSTANCE.getUplinkMessage();
		assertNotNull(uplinkMessageEClass);
		assertTrue(uplinkMessageEClass.isAbstract(), "UplinkMessage should be abstract");

		// Create options for EClass serialization
		Map<String, Object> options = CodecJsonSchemaOptionsBuilder.create()
				.rootObject(EcorePackage.Literals.ECLASS)
				.serializeType(false)
				.forClass(EcorePackage.Literals.ECLASS)
					.schemaFeatureKey("definitions")
				.build();

		// Serialize EClass to JSON Schema
		Resource res = resourceSet.createResource(URI.createURI(outputFile), "application/schema+json");
		EClass copy = EcoreUtil.copy(uplinkMessageEClass);
		res.getContents().add(copy);
		res.save(options);

		// Verify the file was created
		assertTrue(Files.exists(Path.of(outputFile)));

		// Load and verify the JSON Schema structure
		ObjectMapper mapper = new ObjectMapper();
		JsonNode schema = mapper.readTree(new File(outputFile));

		// Verify top-level metadata
		assertThat(schema.get("title").asString()).isEqualTo("UplinkMessage");
		assertThat(schema.get("type").asString()).isEqualTo("object");

		// Verify properties
		JsonNode properties = schema.get("properties");
		assertTrue(properties.has("deduplicationId"), "Should have deduplicationId");
		assertTrue(properties.has("time"), "Should have time");
		assertTrue(properties.has("deviceInfo"), "Should have deviceInfo reference");
		assertTrue(properties.has("rxInfo"), "Should have rxInfo reference");
		assertTrue(properties.has("txInfo"), "Should have txInfo reference");

		// Verify rxInfo is an array reference
		JsonNode rxInfo = properties.get("rxInfo");
		assertThat(rxInfo.get("type").asString()).isEqualTo("array");
		assertTrue(rxInfo.has("items"), "rxInfo should have items");
		assertThat(rxInfo.get("items").get("$ref").asString()).isEqualTo("#/definitions/RxInfo");

		// Verify required properties (deviceInfo has lowerBound=1)
		assertTrue(schema.has("required"), "Should have required array");
		JsonNode required = schema.get("required");
		boolean hasDeviceInfo = false;
		for (JsonNode req : required) {
			if ("deviceInfo".equals(req.asString())) {
				hasDeviceInfo = true;
				break;
			}
		}
		assertTrue(hasDeviceInfo, "deviceInfo should be in required array");

		// Verify all transitively referenced classes are in definitions
		JsonNode definitions = schema.get("definitions");
		assertTrue(definitions.has("DeviceInfo"), "Should have DeviceInfo");
		assertTrue(definitions.has("Tags"), "Should have Tags (transitive from DeviceInfo)");
		assertTrue(definitions.has("RxInfo"), "Should have RxInfo");
		assertTrue(definitions.has("Location"), "Should have Location (transitive from RxInfo)");
		assertTrue(definitions.has("Metadata"), "Should have Metadata (transitive from RxInfo)");
		assertTrue(definitions.has("TxInfo"), "Should have TxInfo");
		assertTrue(definitions.has("Modulation"), "Should have Modulation (transitive from TxInfo)");
		assertTrue(definitions.has("LoraInfo"), "Should have LoraInfo (transitive from Modulation)");
	}

	/**
	 * Test serializing with $defs instead of definitions
	 */
	@Test
	public void testEClassSerializationWithDefs() throws IOException {
		outputFile = System.getProperty("data")+"deviceinfo-eclass-defs.jsonschema";

		// Get the DeviceInfo EClass
		EClass deviceInfoEClass = LorawanPackage.eINSTANCE.getDeviceInfo();
		assertNotNull(deviceInfoEClass);

		// Create options with $defs instead of definitions
		Map<String, Object> options = CodecJsonSchemaOptionsBuilder.create()
				.rootObject(EcorePackage.Literals.ECLASS)
				.serializeType(false)
				.forClass(EcorePackage.Literals.ECLASS)
					.schemaFeatureKey("$defs")
				.build();

		// Serialize EClass to JSON Schema
		Resource res = resourceSet.createResource(URI.createURI(outputFile), "application/schema+json");
		EClass copy = EcoreUtil.copy(deviceInfoEClass);
		res.getContents().add(copy);
		res.save(options);

		// Verify the file was created
		assertTrue(Files.exists(Path.of(outputFile)));

		// Load and verify the JSON Schema structure
		ObjectMapper mapper = new ObjectMapper();
		JsonNode schema = mapper.readTree(new File(outputFile));

		// Verify $defs is used instead of definitions
		assertFalse(schema.has("definitions"), "Should NOT have definitions");
		assertTrue(schema.has("$defs"), "Should have $defs");

		// Verify $ref uses $defs path
		JsonNode tagsProperty = schema.get("properties").get("tags");
		assertThat(tagsProperty.get("$ref").asString()).isEqualTo("#/$defs/Tags");

		// Verify Tags is in $defs
		JsonNode defs = schema.get("$defs");
		assertTrue(defs.has("Tags"), "$defs should contain Tags");
	}

	/**
	 * Test that EClass with no references produces empty definitions
	 */
	@Test
	public void testEClassWithNoReferences() throws IOException {
		outputFile = System.getProperty("data")+"location-eclass.jsonschema";

		// Get the Location EClass (has only attributes, no references)
		EClass locationEClass = LorawanPackage.eINSTANCE.getLocation();
		assertNotNull(locationEClass);

		// Create options for EClass serialization
		Map<String, Object> options = CodecJsonSchemaOptionsBuilder.create()
				.rootObject(EcorePackage.Literals.ECLASS)
				.serializeType(false)
				.forClass(EcorePackage.Literals.ECLASS)
					.schemaFeatureKey("definitions")
				.build();

		// Serialize EClass to JSON Schema
		Resource res = resourceSet.createResource(URI.createURI(outputFile), "application/schema+json");
		EClass copy = EcoreUtil.copy(locationEClass);
		res.getContents().add(copy);
		res.save(options);

		// Verify the file was created
		assertTrue(Files.exists(Path.of(outputFile)));

		// Load and verify the JSON Schema structure
		ObjectMapper mapper = new ObjectMapper();
		JsonNode schema = mapper.readTree(new File(outputFile));

		// Verify structure
		assertThat(schema.get("title").asString()).isEqualTo("Location");
		assertThat(schema.get("type").asString()).isEqualTo("object");

		// Verify properties
		JsonNode properties = schema.get("properties");
		assertTrue(properties.has("latitude"), "Should have latitude");
		assertTrue(properties.has("longitude"), "Should have longitude");
		assertTrue(properties.has("altitude"), "Should have altitude");

		// Verify empty definitions
		assertTrue(schema.has("definitions"), "Should have definitions section");
		JsonNode definitions = schema.get("definitions");
		assertThat(definitions.size()).isEqualTo(0);
	}

	/**
	 * Test that the jsonschema.all.required option marks all fields as required,
	 * regardless of their lowerBound in the Ecore model.
	 * Location EClass has latitude, longitude, altitude all with lowerBound=0.
	 */
	@Test
	public void testAllFieldsRequiredOption() throws IOException {
		outputFile = System.getProperty("data") + "location-eclass-all-required.jsonschema";

		EClass locationEClass = LorawanPackage.eINSTANCE.getLocation();
		assertNotNull(locationEClass);

		Map<String, Object> options = CodecJsonSchemaOptionsBuilder.create()
				.rootObject(EcorePackage.Literals.ECLASS)
				.serializeType(false)
				.forClass(EcorePackage.Literals.ECLASS)
					.schemaFeatureKey("definitions")
					.allFieldsRequired(true)
				.build();

		Resource res = resourceSet.createResource(URI.createURI(outputFile), "application/schema+json");
		EClass copy = EcoreUtil.copy(locationEClass);
		res.getContents().add(copy);
		res.save(options);

		assertTrue(Files.exists(Path.of(outputFile)));

		ObjectMapper mapper = new ObjectMapper();
		JsonNode schema = mapper.readTree(new File(outputFile));

		// All properties should be in required even though lowerBound=0
		assertTrue(schema.has("required"), "Should have required array");
		JsonNode required = schema.get("required");
		assertThat(required.size()).isGreaterThanOrEqualTo(3);

		boolean hasLatitude = false, hasLongitude = false, hasAltitude = false;
		for (JsonNode req : required) {
			switch (req.asString()) {
			case "latitude" -> hasLatitude = true;
			case "longitude" -> hasLongitude = true;
			case "altitude" -> hasAltitude = true;
			}
		}
		assertTrue(hasLatitude, "latitude should be required");
		assertTrue(hasLongitude, "longitude should be required");
		assertTrue(hasAltitude, "altitude should be required");
	}

	/**
	 * Test that when useNamesFromExtendedMetadata=true, the required array at root level
	 * uses extended metadata names instead of the feature names.
	 * DocumentMetadata.id has lowerBound=1 and extended metadata name "document_id".
	 */
	@Test
	public void testExtendedMetadataNamesInRootRequired(@InjectService ServiceAware<TrendAnalysisPackage> packAware) throws IOException, InterruptedException {
		outputFile = System.getProperty("data") + "documentmetadata-eclass.jsonschema";

		TrendAnalysisPackage model = packAware.waitForService(2000l);
		assertNotNull(model);
		EClass documentMetadataEClass = (EClass) model.getEClassifier("DocumentMetadata");
		assertNotNull(documentMetadataEClass);

		Map<String, Object> options = CodecJsonSchemaOptionsBuilder.create()
				.rootObject(EcorePackage.Literals.ECLASS)
				.useNamesFromExtendedMetadata(true)
				.serializeType(false)
				.forClass(EcorePackage.Literals.ECLASS)
					.schemaFeatureKey("definitions")
				.build();

		Resource res = resourceSet.createResource(URI.createURI(outputFile), "application/schema+json");
		EClass copy = EcoreUtil.copy(documentMetadataEClass);
		res.getContents().add(copy);
		res.save(options);

		assertTrue(Files.exists(Path.of(outputFile)));

		ObjectMapper mapper = new ObjectMapper();
		JsonNode schema = mapper.readTree(new File(outputFile));

		// Properties should use extended metadata names
		assertTrue(schema.has("properties"), "Should have properties");
		JsonNode properties = schema.get("properties");
		assertTrue(properties.has("document_id"), "Should have 'document_id' (extended metadata name for 'id')");
		assertFalse(properties.has("id"), "Should NOT have 'id' (plain feature name)");

		// Required array should also use extended metadata names
		assertTrue(schema.has("required"), "Should have required array since id has lowerBound=1");
		JsonNode required = schema.get("required");
		boolean hasDocumentId = false;
		for (JsonNode req : required) {
			if ("document_id".equals(req.asString())) {
				hasDocumentId = true;
				break;
			}
		}
		assertTrue(hasDocumentId, "'document_id' should be in required (not 'id')");
	}

	/**
	 * Test that jsonschema.all.required=true combined with useNamesFromExtendedMetadata=true
	 * produces a required array with extended metadata names for all features.
	 * TrendAnalysis has documentMetadata/executive_summary/trends, first two with extended metadata names.
	 */
	@Test
	public void testAllFieldsRequiredWithExtendedMetadataNames(@InjectService ServiceAware<TrendAnalysisPackage> packAware) throws IOException, InterruptedException {
		outputFile = System.getProperty("data") + "trendanalysis-all-required.jsonschema";

		TrendAnalysisPackage model = packAware.waitForService(2000l);
		assertNotNull(model);
		EClass trendAnalysisEClass = (EClass) model.getEClassifier("TrendAnalysis");
		assertNotNull(trendAnalysisEClass);

		Map<String, Object> options = CodecJsonSchemaOptionsBuilder.create()
				.rootObject(EcorePackage.Literals.ECLASS)
				.useNamesFromExtendedMetadata(true)
				.serializeType(false)
				.forClass(EcorePackage.Literals.ECLASS)
					.schemaFeatureKey("definitions")
					.allFieldsRequired(true)
				.build();

		Resource res = resourceSet.createResource(URI.createURI(outputFile), "application/schema+json");
		EClass copy = EcoreUtil.copy(trendAnalysisEClass);
		res.getContents().add(copy);
		res.save(options);

		assertTrue(Files.exists(Path.of(outputFile)));

		ObjectMapper mapper = new ObjectMapper();
		JsonNode schema = mapper.readTree(new File(outputFile));

		// All features should be in required
		assertTrue(schema.has("required"), "Should have required array");
		JsonNode required = schema.get("required");

		// documentMetadata → extended metadata name: document_metadata
		// executiveSummary → extended metadata name: executive_summary
		// trends → no extended metadata name → stays "trends"
		boolean hasDocumentMetadata = false, hasExecutiveSummary = false, hasTrends = false;
		for (JsonNode req : required) {
			switch (req.asString()) {
			case "document_metadata" -> hasDocumentMetadata = true;
			case "executive_summary" -> hasExecutiveSummary = true;
			case "trends" -> hasTrends = true;
			}
		}
		assertTrue(hasDocumentMetadata, "'document_metadata' should be required (extended metadata name)");
		assertTrue(hasExecutiveSummary, "'executive_summary' should be required (extended metadata name)");
		assertTrue(hasTrends, "'trends' should be required");
	}

	/**
	 * Test that a wrapper schema is generated when rootArrayName + oneOfEClasses are provided.
	 * Two EClasses (TrendAnalysis and Trend) become $ref items in the oneOf, all their
	 * transitive dependencies land in definitions.
	 *
	 * Expected top-level structure:
	 *   type: object
	 *   properties.results.type: array
	 *   properties.results.items.oneOf: [$ref TrendAnalysis, $ref Trend]
	 *   definitions: TrendAnalysis, Trend, DocumentMetadata, ExecutiveSummary, StempelCategoryType
	 */
	@Test
	public void testMultiEClassOneOfWrapperSchema(@InjectService ServiceAware<TrendAnalysisPackage> packAware) throws IOException, InterruptedException {
		outputFile = System.getProperty("data") + "trend-analysis-search.jsonschema";

		TrendAnalysisPackage model = packAware.waitForService(2000l);
		assertNotNull(model);

		EClass trendAnalysisEClass = model.getTrendAnalysis();
		EClass trendEClass = model.getTrend();
		assertNotNull(trendAnalysisEClass);
		assertNotNull(trendEClass);

		Map<String, Object> options = CodecJsonSchemaOptionsBuilder.create()
				.rootObject(EcorePackage.Literals.ECLASS)
				.useNamesFromExtendedMetadata(false)
				.serializeType(false)
				.forClass(EcorePackage.Literals.ECLASS)
					.schemaFeatureKey("definitions")
				.and()
				.rootArrayName("results")
				.schemaTitle("TrendAnalysisSearchResult")
				.schemaId("http://eclipse.org/fennec/ai/trendanalysis/1.0/search-schema")
				.schemaDescription("Schema for Trend Analysis search results")
				.oneOfEClasses(trendAnalysisEClass, trendEClass)
				.build();

		Resource res = resourceSet.createResource(URI.createURI(outputFile), "application/schema+json");
		// Put one EClass in the resource to satisfy the doSave mechanics
		res.getContents().add(EcoreUtil.copy(trendAnalysisEClass));		
		res.save(options);

		assertTrue(Files.exists(Path.of(outputFile)));

		ObjectMapper mapper = new ObjectMapper();
		JsonNode schema = mapper.readTree(new File(outputFile));

		// Top-level metadata
		assertThat(schema.get("title").asString()).isEqualTo("TrendAnalysisSearchResult");
		assertThat(schema.get("$id").asString()).isEqualTo("http://eclipse.org/fennec/ai/trendanalysis/1.0/search-schema");
		assertThat(schema.get("description").asString()).isEqualTo("Schema for Trend Analysis search results");
		assertThat(schema.get("type").asString()).isEqualTo("object");

		// Root array property
		assertTrue(schema.has("properties"), "Should have properties");
		JsonNode properties = schema.get("properties");
		assertTrue(properties.has("results"), "Should have 'results' property");

		JsonNode resultsProperty = properties.get("results");
		assertThat(resultsProperty.get("type").asString()).isEqualTo("array");
		assertTrue(resultsProperty.has("items"), "results should have items");

		JsonNode items = resultsProperty.get("items");
		assertTrue(items.has("oneOf"), "items should have oneOf");

		JsonNode oneOf = items.get("oneOf");
		assertThat(oneOf.size()).isEqualTo(2);
		assertThat(oneOf.get(0).get("$ref").asString()).isEqualTo("#/definitions/TrendAnalysis");
		assertThat(oneOf.get(1).get("$ref").asString()).isEqualTo("#/definitions/Trend");

		// required: ["results"]
		assertTrue(schema.has("required"), "Should have required");
		JsonNode required = schema.get("required");
		assertThat(required.size()).isEqualTo(1);
		assertThat(required.get(0).asString()).isEqualTo("results");

		// definitions section
		assertTrue(schema.has("definitions"), "Should have definitions");
		JsonNode definitions = schema.get("definitions");

		// Both oneOf EClasses are in definitions
		assertTrue(definitions.has("TrendAnalysis"), "definitions should contain TrendAnalysis");
		assertTrue(definitions.has("Trend"), "definitions should contain Trend");

		// Transitive deps of TrendAnalysis
		assertTrue(definitions.has("DocumentMetadata"), "definitions should contain DocumentMetadata (dep of TrendAnalysis)");
		assertTrue(definitions.has("ExecutiveSummary"), "definitions should contain ExecutiveSummary (dep of TrendAnalysis)");

		// Transitive deps of Trend (enum attribute)
		assertTrue(definitions.has("StempelCategoryType"), "definitions should contain StempelCategoryType (enum dep of Trend)");

		// The TrendAnalysis definition has the right structure
		JsonNode trendAnalysisDef = definitions.get("TrendAnalysis");
		assertThat(trendAnalysisDef.get("type").asString()).isEqualTo("object");
		assertTrue(trendAnalysisDef.has("properties"));
	}

	/**
	 * Test that JsonSchemaGenerator.generateSchema(EClass) returns valid JSON.
	 */
	@Test
	public void testJsonSchemaGeneratorForEClass(
			@InjectService ServiceAware<JsonSchemaGenerator> generatorAware,
			@InjectService ServiceAware<TrendAnalysisPackage> packAware) throws IOException, InterruptedException {
		JsonSchemaGenerator generator = generatorAware.waitForService(2000L);
		assertNotNull(generator);

		TrendAnalysisPackage model = packAware.waitForService(2000L);
		assertNotNull(model);

		EClass trendAnalysisEClass = model.getTrendAnalysis();
		assertNotNull(trendAnalysisEClass);

		String schema = generator.generateSchema(trendAnalysisEClass);
		assertNotNull(schema);
		assertFalse(schema.isEmpty());

		ObjectMapper mapper = new ObjectMapper();
		JsonNode schemaNode = mapper.readTree(schema);
		assertThat(schemaNode.get("title").asString()).isEqualTo("TrendAnalysis");
		assertThat(schemaNode.get("type").asString()).isEqualTo("object");
		assertTrue(schemaNode.has("properties"), "Should have properties");
	}

	/**
	 * Test that JsonSchemaGenerator.generateSchema(String) with nsURI#ClassName returns valid JSON.
	 */
	@Test
	public void testJsonSchemaGeneratorForUri(
			@InjectService ServiceAware<JsonSchemaGenerator> generatorAware,
			@InjectService ServiceAware<TrendAnalysisPackage> packAware) throws IOException, InterruptedException {
		JsonSchemaGenerator generator = generatorAware.waitForService(2000L);
		assertNotNull(generator);

		// Ensure the package is registered
		TrendAnalysisPackage model = packAware.waitForService(2000L);
		assertNotNull(model);

		String schema = generator.generateSchema(TrendAnalysisPackage.eNS_URI + "#TrendAnalysis");
		assertNotNull(schema);
		assertFalse(schema.isEmpty());

		ObjectMapper mapper = new ObjectMapper();
		JsonNode schemaNode = mapper.readTree(schema);
		assertThat(schemaNode.get("title").asString()).isEqualTo("TrendAnalysis");
		assertThat(schemaNode.get("type").asString()).isEqualTo("object");
	}

	/**
	 * Test that JsonSchemaGenerator.generateSchema(EPackage) returns valid JSON.
	 */
	@Test
	public void testJsonSchemaGeneratorForEPackage(
			@InjectService ServiceAware<JsonSchemaGenerator> generatorAware,
			@InjectService ServiceAware<TrendAnalysisPackage> packAware) throws IOException, InterruptedException {
		JsonSchemaGenerator generator = generatorAware.waitForService(2000L);
		assertNotNull(generator);

		TrendAnalysisPackage model = packAware.waitForService(2000L);
		assertNotNull(model);

		String schema = generator.generateSchema(model);
		assertNotNull(schema);
		assertFalse(schema.isEmpty());

		ObjectMapper mapper = new ObjectMapper();
		JsonNode schemaNode = mapper.readTree(schema);
		assertNotNull(schemaNode, "Result should be valid JSON");
		assertTrue(schemaNode.has("$id"), "Should have $id");
		assertThat(schemaNode.get("$id").asString()).isEqualTo(TrendAnalysisPackage.eNS_URI);
	}


}
