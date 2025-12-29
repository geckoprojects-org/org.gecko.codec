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
 * Tests for MAPPED type strategy with discriminator-based resolution.
 * <p>
 * Tests that _type field with discriminator value (e.g., "text", "image")
 * resolves to the correct concrete EClass via TypeDiscriminatorService.
 * </p>
 * <p>
 * <b>DISABLED:</b> This test uses incorrect annotation format in test-mapped.ecore.
 * The existing {@code CodecResourceMappedTypeTest} provides equivalent coverage
 * with correct annotations. This test should be removed or fixed when the new
 * type tests are consolidated.
 * </p>
 *
 * @see org.eclipse.fennec.codec.v2.resource.CodecResourceMappedTypeTest
 */
@Disabled("Uses wrong annotation format - see CodecResourceMappedTypeTest for working tests")
@DisplayName("Type Resolution: MAPPED Strategy")
class TypeResolutionMappedTest {

    private static final String TEST_ECORE = "test-mapped.ecore";

    private MetadataService metadataService;
    private EcoreHelper ecoreHelper;

    private EPackage testPackage;
    private EClass messageClass;
    private EClass textMessageClass;
    private EClass imageMessageClass;
    private EClass videoMessageClass;
    private EClass conversationClass;

    @BeforeEach
    void setUp() throws IOException {
        ecoreHelper = new EcoreHelper(TypeResolutionMappedTest.class);
        testPackage = ecoreHelper.loadEcore(TEST_ECORE);

        // Register in global registry for type resolution
        EPackage.Registry.INSTANCE.put(testPackage.getNsURI(), testPackage);

        // Create metadata service with CodecAspectProvider
        metadataService = MetadataServiceFactory.create();
        metadataService.registerPackage(testPackage);

        messageClass = ecoreHelper.getEClass(testPackage, "Message");
        textMessageClass = ecoreHelper.getEClass(testPackage, "TextMessage");
        imageMessageClass = ecoreHelper.getEClass(testPackage, "ImageMessage");
        videoMessageClass = ecoreHelper.getEClass(testPackage, "VideoMessage");
        conversationClass = ecoreHelper.getEClass(testPackage, "Conversation");
    }

    @AfterEach
    void tearDown() {
        EPackage.Registry.INSTANCE.remove(testPackage.getNsURI());
        ecoreHelper.releaseAll();
    }

    @Nested
    @DisplayName("Root object resolution")
    class RootObjectResolution {

        @Test
        @DisplayName("text message resolves via discriminator")
        void textMessageResolves() throws IOException {
            String json = """
                {
                    "_type": "text",
                    "messageType": "text",
                    "content": "Hello world"
                }
                """;

            EObject result = deserializeWithHint(json, messageClass);

            assertNotNull(result, "Should resolve text message");
            assertEquals(textMessageClass, result.eClass());
            assertEquals("Hello world", result.eGet(textMessageClass.getEStructuralFeature("content")));
        }

        @Test
        @DisplayName("image message resolves via discriminator")
        void imageMessageResolves() throws IOException {
            String json = """
                {
                    "_type": "image",
                    "messageType": "image",
                    "url": "https://example.org/photo.jpg",
                    "width": 800,
                    "height": 600
                }
                """;

            EObject result = deserializeWithHint(json, messageClass);

            assertNotNull(result, "Should resolve image message");
            assertEquals(imageMessageClass, result.eClass());
            assertEquals("https://example.org/photo.jpg", result.eGet(imageMessageClass.getEStructuralFeature("url")));
            assertEquals(800, result.eGet(imageMessageClass.getEStructuralFeature("width")));
            assertEquals(600, result.eGet(imageMessageClass.getEStructuralFeature("height")));
        }

        @Test
        @DisplayName("video message resolves via discriminator")
        void videoMessageResolves() throws IOException {
            String json = """
                {
                    "_type": "video",
                    "messageType": "video",
                    "url": "https://example.org/clip.mp4",
                    "duration": 120
                }
                """;

            EObject result = deserializeWithHint(json, messageClass);

            assertNotNull(result, "Should resolve video message");
            assertEquals(videoMessageClass, result.eClass());
            assertEquals("https://example.org/clip.mp4", result.eGet(videoMessageClass.getEStructuralFeature("url")));
            assertEquals(120, result.eGet(videoMessageClass.getEStructuralFeature("duration")));
        }

        @Test
        @DisplayName("unknown discriminator returns null")
        void unknownDiscriminatorReturnsNull() throws IOException {
            String json = """
                {
                    "_type": "unknown",
                    "messageType": "unknown"
                }
                """;

            EObject result = deserializeWithHint(json, messageClass);

            assertNull(result, "Should return null for unknown discriminator");
        }
    }

    @Nested
    @DisplayName("Nested object resolution")
    class NestedObjectResolution {

        @Test
        @DisplayName("nested messages resolve via discriminator")
        void nestedMessagesResolve() throws IOException {
            String json = """
                {
                    "_type": "http://test.example.org/mapped/1.0#//Conversation",
                    "id": "conv-001",
                    "messages": [
                        {
                            "_type": "text",
                            "messageType": "text",
                            "content": "First message"
                        },
                        {
                            "_type": "image",
                            "messageType": "image",
                            "url": "https://example.org/photo.jpg",
                            "width": 640,
                            "height": 480
                        }
                    ]
                }
                """;

            EObject result = deserialize(json);

            assertNotNull(result, "Should deserialize conversation");
            assertEquals(conversationClass, result.eClass());
            assertEquals("conv-001", result.eGet(conversationClass.getEStructuralFeature("id")));

            @SuppressWarnings("unchecked")
            List<EObject> messages = (List<EObject>) result.eGet(conversationClass.getEStructuralFeature("messages"));
            assertEquals(2, messages.size());

            // First message is TextMessage
            EObject textMsg = messages.get(0);
            assertEquals(textMessageClass, textMsg.eClass());
            assertEquals("First message", textMsg.eGet(textMessageClass.getEStructuralFeature("content")));

            // Second message is ImageMessage
            EObject imageMsg = messages.get(1);
            assertEquals(imageMessageClass, imageMsg.eClass());
            assertEquals("https://example.org/photo.jpg", imageMsg.eGet(imageMessageClass.getEStructuralFeature("url")));
        }

        @Test
        @DisplayName("mixed message types in array")
        void mixedMessageTypes() throws IOException {
            String json = """
                {
                    "_type": "http://test.example.org/mapped/1.0#//Conversation",
                    "id": "conv-002",
                    "messages": [
                        {"_type": "text", "content": "Hello"},
                        {"_type": "video", "url": "clip.mp4", "duration": 30},
                        {"_type": "image", "url": "pic.jpg", "width": 100, "height": 100},
                        {"_type": "text", "content": "Goodbye"}
                    ]
                }
                """;

            EObject result = deserialize(json);

            @SuppressWarnings("unchecked")
            List<EObject> messages = (List<EObject>) result.eGet(conversationClass.getEStructuralFeature("messages"));
            assertEquals(4, messages.size());

            assertEquals(textMessageClass, messages.get(0).eClass());
            assertEquals(videoMessageClass, messages.get(1).eClass());
            assertEquals(imageMessageClass, messages.get(2).eClass());
            assertEquals(textMessageClass, messages.get(3).eClass());
        }
    }

    // ========================================================================
    // Helper Methods
    // ========================================================================

    private CodecResource createResource() {
        return new CodecResource(
                URI.createURI("test://mapped-test.json"),
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
