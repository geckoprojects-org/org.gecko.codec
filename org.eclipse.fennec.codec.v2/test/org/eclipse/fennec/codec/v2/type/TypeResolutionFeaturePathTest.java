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
package org.eclipse.fennec.codec.v2.type;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.fennec.codec.v2.config.CodecConfiguration;
import org.eclipse.fennec.codec.v2.resource.CodecResource;
import org.eclipse.fennec.codec.v2.util.MetadataServiceFactory;
import org.eclipse.fennec.model.metadata.api.MetadataService;
import org.eclipse.fennec.model.metadata.utils.EcoreHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Tests for featurePath-based type discrimination.
 * <p>
 * The discriminator value is read from a content field (e.g., info.sensorType)
 * instead of the _type field. This is only applicable for MAPPED strategy.
 * </p>
 * <p>
 * <b>DISABLED:</b> This test uses incorrect annotation format in test-featurepath.ecore.
 * The existing {@code CodecResourceFeaturePathTypeTest} provides equivalent coverage
 * with correct annotations.
 * </p>
 *
 * @see org.eclipse.fennec.codec.v2.resource.CodecResourceFeaturePathTypeTest
 */
@Disabled("Uses wrong annotation format - see CodecResourceFeaturePathTypeTest for working tests")
@DisplayName("Type Resolution: FeaturePath Strategy")
class TypeResolutionFeaturePathTest {

    private static final String TEST_ECORE = "test-featurepath.ecore";

    private MetadataService metadataService;
    private EcoreHelper ecoreHelper;

    private EPackage testPackage;
    private EClass sensorReadingClass;
    private EClass temperatureReadingClass;
    private EClass humidityReadingClass;
    private EClass pressureReadingClass;
    private EClass sensorBatchClass;
    private EClass sensorInfoClass;

    @BeforeEach
    void setUp() throws IOException {
        ecoreHelper = new EcoreHelper(TypeResolutionFeaturePathTest.class);
        testPackage = ecoreHelper.loadEcore(TEST_ECORE);

        // Register in global registry for type resolution
        EPackage.Registry.INSTANCE.put(testPackage.getNsURI(), testPackage);

        // Create metadata service with CodecAspectProvider
        metadataService = MetadataServiceFactory.create();
        metadataService.registerPackage(testPackage);

        sensorReadingClass = ecoreHelper.getEClass(testPackage, "SensorReading");
        temperatureReadingClass = ecoreHelper.getEClass(testPackage, "TemperatureReading");
        humidityReadingClass = ecoreHelper.getEClass(testPackage, "HumidityReading");
        pressureReadingClass = ecoreHelper.getEClass(testPackage, "PressureReading");
        sensorBatchClass = ecoreHelper.getEClass(testPackage, "SensorBatch");
        sensorInfoClass = ecoreHelper.getEClass(testPackage, "SensorInfo");
    }

    @AfterEach
    void tearDown() {
        EPackage.Registry.INSTANCE.remove(testPackage.getNsURI());
        ecoreHelper.releaseAll();
    }

    @Nested
    @DisplayName("Root object with featurePath discriminator")
    class RootWithFeaturePath {

        @Test
        @DisplayName("temperature reading resolves from info.sensorType")
        void temperatureReadingResolves() throws IOException {
            String json = """
                {
                    "timestamp": 1704067200000,
                    "info": {
                        "sensorType": "temperature",
                        "manufacturer": "Acme Sensors"
                    },
                    "celsius": 23.5
                }
                """;

            EObject result = deserializeWithHint(json, sensorReadingClass);

            assertNotNull(result, "Should resolve from featurePath");
            assertEquals(temperatureReadingClass, result.eClass());
            assertEquals(1704067200000L, result.eGet(temperatureReadingClass.getEStructuralFeature("timestamp")));
            assertEquals(23.5, result.eGet(temperatureReadingClass.getEStructuralFeature("celsius")));

            EObject info = (EObject) result.eGet(sensorReadingClass.getEStructuralFeature("info"));
            assertNotNull(info, "Should have info");
            assertEquals("temperature", info.eGet(sensorInfoClass.getEStructuralFeature("sensorType")));
            assertEquals("Acme Sensors", info.eGet(sensorInfoClass.getEStructuralFeature("manufacturer")));
        }

        @Test
        @DisplayName("humidity reading resolves from info.sensorType")
        void humidityReadingResolves() throws IOException {
            String json = """
                {
                    "timestamp": 1704067200001,
                    "info": {
                        "sensorType": "humidity",
                        "manufacturer": "HumiTech"
                    },
                    "percentage": 65.2
                }
                """;

            EObject result = deserializeWithHint(json, sensorReadingClass);

            assertNotNull(result, "Should resolve from featurePath");
            assertEquals(humidityReadingClass, result.eClass());
            assertEquals(65.2, result.eGet(humidityReadingClass.getEStructuralFeature("percentage")));
        }

        @Test
        @DisplayName("pressure reading resolves from info.sensorType")
        void pressureReadingResolves() throws IOException {
            String json = """
                {
                    "timestamp": 1704067200002,
                    "info": {
                        "sensorType": "pressure",
                        "manufacturer": "BaroMetrics"
                    },
                    "hectopascal": 1013.25
                }
                """;

            EObject result = deserializeWithHint(json, sensorReadingClass);

            assertNotNull(result, "Should resolve from featurePath");
            assertEquals(pressureReadingClass, result.eClass());
            assertEquals(1013.25, result.eGet(pressureReadingClass.getEStructuralFeature("hectopascal")));
        }

        @Test
        @DisplayName("unknown discriminator in featurePath returns null")
        void unknownDiscriminatorReturnsNull() throws IOException {
            String json = """
                {
                    "timestamp": 1704067200003,
                    "info": {
                        "sensorType": "unknown_sensor"
                    }
                }
                """;

            EObject result = deserializeWithHint(json, sensorReadingClass);

            assertNull(result, "Should return null for unknown discriminator");
        }

        @Test
        @DisplayName("missing featurePath field returns null")
        void missingFeaturePathReturnsNull() throws IOException {
            String json = """
                {
                    "timestamp": 1704067200004,
                    "celsius": 20.0
                }
                """;

            EObject result = deserializeWithHint(json, sensorReadingClass);

            assertNull(result, "Should return null when featurePath field is missing");
        }
    }

    @Nested
    @DisplayName("Nested objects with featurePath discriminator")
    class NestedWithFeaturePath {

        @Test
        @DisplayName("batch with mixed sensor readings resolves each from featurePath")
        void batchWithMixedReadings() throws IOException {
            String json = """
                {
                    "_type": "http://test.example.org/featurepath/1.0#//SensorBatch",
                    "batchId": "BATCH-001",
                    "readings": [
                        {
                            "timestamp": 1704067200000,
                            "info": {"sensorType": "temperature"},
                            "celsius": 22.0
                        },
                        {
                            "timestamp": 1704067200001,
                            "info": {"sensorType": "humidity"},
                            "percentage": 55.0
                        },
                        {
                            "timestamp": 1704067200002,
                            "info": {"sensorType": "pressure"},
                            "hectopascal": 1015.0
                        }
                    ]
                }
                """;

            EObject result = deserialize(json);

            assertNotNull(result, "Should deserialize batch");
            assertEquals(sensorBatchClass, result.eClass());
            assertEquals("BATCH-001", result.eGet(sensorBatchClass.getEStructuralFeature("batchId")));

            @SuppressWarnings("unchecked")
            List<EObject> readings = (List<EObject>) result.eGet(sensorBatchClass.getEStructuralFeature("readings"));
            assertEquals(3, readings.size());

            // Verify each reading type
            assertEquals(temperatureReadingClass, readings.get(0).eClass());
            assertEquals(22.0, readings.get(0).eGet(temperatureReadingClass.getEStructuralFeature("celsius")));

            assertEquals(humidityReadingClass, readings.get(1).eClass());
            assertEquals(55.0, readings.get(1).eGet(humidityReadingClass.getEStructuralFeature("percentage")));

            assertEquals(pressureReadingClass, readings.get(2).eClass());
            assertEquals(1015.0, readings.get(2).eGet(pressureReadingClass.getEStructuralFeature("hectopascal")));
        }

        @Test
        @DisplayName("nested reading with discriminator at various positions")
        void discriminatorAtVariousPositions() throws IOException {
            // Discriminator info field after other fields (tests TokenBuffer replay)
            String json = """
                {
                    "_type": "http://test.example.org/featurepath/1.0#//SensorBatch",
                    "batchId": "BATCH-002",
                    "readings": [
                        {
                            "celsius": 25.0,
                            "timestamp": 1704067200005,
                            "info": {"sensorType": "temperature", "manufacturer": "TempCo"}
                        }
                    ]
                }
                """;

            EObject result = deserialize(json);

            @SuppressWarnings("unchecked")
            List<EObject> readings = (List<EObject>) result.eGet(sensorBatchClass.getEStructuralFeature("readings"));
            assertEquals(1, readings.size());

            EObject reading = readings.get(0);
            assertEquals(temperatureReadingClass, reading.eClass());
            assertEquals(25.0, reading.eGet(temperatureReadingClass.getEStructuralFeature("celsius")));
            assertEquals(1704067200005L, reading.eGet(temperatureReadingClass.getEStructuralFeature("timestamp")));
        }
    }

    // ========================================================================
    // Helper Methods
    // ========================================================================

    private CodecResource createResource() {
        return new CodecResource(
                URI.createURI("test://featurepath-test.json"),
                metadataService,
                CodecConfiguration.defaults(),
                null);
    }

    private EObject deserialize(String json) throws IOException {
        CodecResource resource = createResource();
        Map<String, Object> options = new HashMap<>();

        ByteArrayInputStream in = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
        resource.load(in, options);

        return resource.getContents().isEmpty() ? null : resource.getContents().get(0);
    }

    private EObject deserializeWithHint(String json, EClass hint) throws IOException {
        CodecResource resource = createResource();
        Map<String, Object> options = new HashMap<>();
        options.put(CodecResource.CODEC_ROOT_OBJECT, hint);

        ByteArrayInputStream in = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
        resource.load(in, options);

        return resource.getContents().isEmpty() ? null : resource.getContents().get(0);
    }
}
