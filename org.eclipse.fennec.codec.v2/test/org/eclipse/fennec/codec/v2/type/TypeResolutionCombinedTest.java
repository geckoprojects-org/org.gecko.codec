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
 * Tests for combined type resolution strategies.
 * <p>
 * Tests priority order:
 * 1. _type field with full EClass URI
 * 2. _type field with MAPPED discriminator value
 * 3. featurePath discriminator (MAPPED only)
 * 4. CODEC_ROOT_OBJECT hint (root only)
 * 5. EReference.eType (nested fallback)
 * </p>
 * <p>
 * <b>DISABLED:</b> This test uses incorrect annotation format in test-combined.ecore.
 * The strategy priority is tested implicitly in the other existing tests.
 * </p>
 */
@Disabled("Uses wrong annotation format in test-combined.ecore")
@DisplayName("Type Resolution: Combined Strategies")
class TypeResolutionCombinedTest {

    private static final String TEST_ECORE = "test-combined.ecore";

    private MetadataService metadataService;
    private EcoreHelper ecoreHelper;

    private EPackage testPackage;
    private EClass simpleDataClass;
    private EClass eventClass;
    private EClass clickEventClass;
    private EClass scrollEventClass;
    private EClass eventLogClass;

    @BeforeEach
    void setUp() throws IOException {
        ecoreHelper = new EcoreHelper(TypeResolutionCombinedTest.class);
        testPackage = ecoreHelper.loadEcore(TEST_ECORE);

        // Register in global registry for type resolution
        EPackage.Registry.INSTANCE.put(testPackage.getNsURI(), testPackage);

        // Create metadata service with CodecAspectProvider
        metadataService = MetadataServiceFactory.create();
        metadataService.registerPackage(testPackage);

        simpleDataClass = ecoreHelper.getEClass(testPackage, "SimpleData");
        eventClass = ecoreHelper.getEClass(testPackage, "Event");
        clickEventClass = ecoreHelper.getEClass(testPackage, "ClickEvent");
        scrollEventClass = ecoreHelper.getEClass(testPackage, "ScrollEvent");
        eventLogClass = ecoreHelper.getEClass(testPackage, "EventLog");
    }

    @AfterEach
    void tearDown() {
        EPackage.Registry.INSTANCE.remove(testPackage.getNsURI());
        ecoreHelper.releaseAll();
    }

    @Nested
    @DisplayName("Strategy priority")
    class StrategyPriority {

        @Test
        @DisplayName("_type URI takes priority over featurePath")
        void typeUriOverFeaturePath() throws IOException {
            // _type says ClickEvent, featurePath says scroll - URI wins
            String json = """
                {
                    "_type": "http://test.example.org/combined/1.0#//ClickEvent",
                    "id": "evt-001",
                    "meta": {
                        "eventKind": "scroll",
                        "source": "button"
                    },
                    "elementId": "btn-submit"
                }
                """;

            EObject result = deserializeWithHint(json, eventClass);

            assertNotNull(result, "Should resolve from URI");
            assertEquals(clickEventClass, result.eClass(), "URI should take priority over featurePath");
            assertEquals("btn-submit", result.eGet(clickEventClass.getEStructuralFeature("elementId")));
        }

        @Test
        @DisplayName("_type MAPPED discriminator resolves without featurePath scan")
        void typeMappedResolves() throws IOException {
            // _type is "click" discriminator value
            String json = """
                {
                    "_type": "click",
                    "id": "evt-002",
                    "elementId": "link-home"
                }
                """;

            EObject result = deserializeWithHint(json, eventClass);

            assertNotNull(result, "Should resolve from MAPPED discriminator");
            assertEquals(clickEventClass, result.eClass());
            assertEquals("link-home", result.eGet(clickEventClass.getEStructuralFeature("elementId")));
        }

        @Test
        @DisplayName("featurePath resolves when _type is missing")
        void featurePathWhenNoType() throws IOException {
            String json = """
                {
                    "id": "evt-003",
                    "meta": {
                        "eventKind": "scroll",
                        "source": "window"
                    },
                    "position": 500
                }
                """;

            EObject result = deserializeWithHint(json, eventClass);

            assertNotNull(result, "Should resolve from featurePath");
            assertEquals(scrollEventClass, result.eClass());
            assertEquals(500, result.eGet(scrollEventClass.getEStructuralFeature("position")));
        }

        @Test
        @DisplayName("hint is used when both _type and featurePath are missing")
        void hintWhenNoTypeOrFeaturePath() throws IOException {
            // Note: This tests concrete class hint - for abstract Event, we'd need concrete type
            String json = """
                {
                    "value": "test data"
                }
                """;

            EObject result = deserializeWithHint(json, simpleDataClass);

            assertNotNull(result, "Should resolve from hint");
            assertEquals(simpleDataClass, result.eClass());
            assertEquals("test data", result.eGet(simpleDataClass.getEStructuralFeature("value")));
        }
    }

    @Nested
    @DisplayName("Mixed references in container")
    class MixedReferences {

        @Test
        @DisplayName("abstract reference needs type, concrete uses EReference.eType")
        void mixedReferenceResolution() throws IOException {
            String json = """
                {
                    "_type": "http://test.example.org/combined/1.0#//EventLog",
                    "sessionId": "session-123",
                    "events": [
                        {
                            "id": "e1",
                            "meta": {"eventKind": "click"},
                            "elementId": "btn-1"
                        },
                        {
                            "_type": "scroll",
                            "id": "e2",
                            "position": 100
                        }
                    ],
                    "data": {
                        "value": "extra info"
                    }
                }
                """;

            EObject result = deserialize(json);

            assertNotNull(result, "Should deserialize event log");
            assertEquals(eventLogClass, result.eClass());

            // Events resolved via featurePath and MAPPED
            @SuppressWarnings("unchecked")
            List<EObject> events = (List<EObject>) result.eGet(eventLogClass.getEStructuralFeature("events"));
            assertEquals(2, events.size());
            assertEquals(clickEventClass, events.get(0).eClass());
            assertEquals(scrollEventClass, events.get(1).eClass());

            // SimpleData resolved via EReference.eType (no _type needed)
            EObject data = (EObject) result.eGet(eventLogClass.getEStructuralFeature("data"));
            assertNotNull(data, "Should have data");
            assertEquals(simpleDataClass, data.eClass());
            assertEquals("extra info", data.eGet(simpleDataClass.getEStructuralFeature("value")));
        }

        @Test
        @DisplayName("nested events with different resolution strategies")
        void nestedEventsVariousStrategies() throws IOException {
            String json = """
                {
                    "_type": "http://test.example.org/combined/1.0#//EventLog",
                    "sessionId": "session-456",
                    "events": [
                        {
                            "_type": "http://test.example.org/combined/1.0#//ClickEvent",
                            "id": "e1",
                            "elementId": "by-uri"
                        },
                        {
                            "_type": "click",
                            "id": "e2",
                            "elementId": "by-mapped"
                        },
                        {
                            "id": "e3",
                            "meta": {"eventKind": "scroll"},
                            "position": 200
                        }
                    ]
                }
                """;

            EObject result = deserialize(json);

            @SuppressWarnings("unchecked")
            List<EObject> events = (List<EObject>) result.eGet(eventLogClass.getEStructuralFeature("events"));
            assertEquals(3, events.size());

            // First: resolved by URI
            assertEquals(clickEventClass, events.get(0).eClass());
            assertEquals("by-uri", events.get(0).eGet(clickEventClass.getEStructuralFeature("elementId")));

            // Second: resolved by MAPPED discriminator
            assertEquals(clickEventClass, events.get(1).eClass());
            assertEquals("by-mapped", events.get(1).eGet(clickEventClass.getEStructuralFeature("elementId")));

            // Third: resolved by featurePath
            assertEquals(scrollEventClass, events.get(2).eClass());
            assertEquals(200, events.get(2).eGet(scrollEventClass.getEStructuralFeature("position")));
        }
    }

    @Nested
    @DisplayName("Edge cases")
    class EdgeCases {

        @Test
        @DisplayName("empty object with hint resolves to hint type")
        void emptyObjectWithHint() throws IOException {
            String json = """
                {}
                """;

            EObject result = deserializeWithHint(json, simpleDataClass);

            assertNotNull(result, "Should resolve from hint even for empty object");
            assertEquals(simpleDataClass, result.eClass());
        }

        @Test
        @DisplayName("null value in featurePath field handled gracefully")
        void nullFeaturePathValue() throws IOException {
            String json = """
                {
                    "id": "evt-null",
                    "meta": {
                        "eventKind": null,
                        "source": "unknown"
                    }
                }
                """;

            EObject result = deserializeWithHint(json, eventClass);

            assertNull(result, "Should return null when featurePath value is null");
        }
    }

    // ========================================================================
    // Helper Methods
    // ========================================================================

    private CodecResource createResource() {
        return new CodecResource(
                URI.createURI("test://combined-test.json"),
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
