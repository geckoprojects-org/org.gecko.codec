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
package org.eclipse.fennec.codec.v2.deser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fennec.codec.api.value.AttributeValueReader;
import org.eclipse.fennec.codec.api.value.CodecValueRegistry;
import org.eclipse.fennec.codec.v2.config.effective.EffectiveFeatureConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;

/**
 * Tests for canHandle() validation in AttributeDeserializationEntry.
 *
 * @see <a href="docs/codec-v2-spec/10-custom-values.md#24-the-canhandle-method">Spec: canHandle()</a>
 */
@DisplayName("AttributeDeserializationEntry canHandle() validation")
@Disabled("Migrated to org.eclipse.fennec.codec.deser.AttributeDeserializationEntryCanHandleTest")
@Deprecated
class AttributeDeserializationEntryCanHandleTest extends DeserializationEntryTestBase {

    private TestLogHandler logHandler;
    private Logger logger;

    @BeforeEach
    void setUpLogger() {
        logger = Logger.getLogger(AttributeDeserializationEntry.class.getName());
        logHandler = new TestLogHandler();
        logger.addHandler(logHandler);
        logger.setLevel(Level.ALL);
    }

    @AfterEach
    void tearDownLogger() {
        logger.removeHandler(logHandler);
    }

    @Nested
    @DisplayName("AttributeValueReader canHandle validation")
    class AttributeValueReaderCanHandle {

        @Test
        @DisplayName("accepts reader when canHandle returns true")
        void acceptsReaderWhenCanHandleReturnsTrue() {
            // Reader that accepts String attributes
            AttributeValueReader<String> stringReader = new AttributeValueReader<>() {
                @Override
                public boolean canHandle(EAttribute attribute) {
                    return attribute.getEAttributeType().getInstanceClass() == String.class;
                }

                @Override
                public String read(JsonParser parser, EAttribute attr, DeserializationContext ctxt)
                        throws IOException {
                    return "custom:" + parser.getString();
                }
            };

            CodecValueRegistry registry = new CodecValueRegistry();
            registry.registerReader("stringReader", stringReader);

            EffectiveFeatureConfig config = EffectiveFeatureConfig.builder()
                    .feature(nameAttribute) // String attribute
                    .key("name")
                    .serialize(true)
                    .valueReaderName("stringReader")
                    .build();

            // Should not log warning - reader is compatible
            AttributeDeserializationEntry entry = new AttributeDeserializationEntry(
                    config, nameAttribute, registry);

            assertNotNull(entry);
            assertEquals(0, logHandler.getWarningCount(),
                    "No warning should be logged when canHandle returns true");
        }

        @Test
        @DisplayName("rejects reader and logs warning when canHandle returns false")
        void rejectsReaderWhenCanHandleReturnsFalse() {
            // Reader that only handles Integer attributes
            AttributeValueReader<Integer> intReader = new AttributeValueReader<>() {
                @Override
                public boolean canHandle(EAttribute attribute) {
                    Class<?> type = attribute.getEAttributeType().getInstanceClass();
                    return type == Integer.class || type == int.class;
                }

                @Override
                public Integer read(JsonParser parser, EAttribute attr, DeserializationContext ctxt)
                        throws IOException {
                    return parser.getIntValue() * 2;
                }
            };

            CodecValueRegistry registry = new CodecValueRegistry();
            registry.registerReader("intReader", intReader);

            EffectiveFeatureConfig config = EffectiveFeatureConfig.builder()
                    .feature(nameAttribute) // String attribute, not Integer!
                    .key("name")
                    .serialize(true)
                    .valueReaderName("intReader")
                    .build();

            // Should log warning - reader cannot handle String attribute
            AttributeDeserializationEntry entry = new AttributeDeserializationEntry(
                    config, nameAttribute, registry);

            assertNotNull(entry);
            assertEquals(1, logHandler.getWarningCount(),
                    "Warning should be logged when canHandle returns false");
            String message = logHandler.getLastWarningMessage();
            assertNotNull(message);
            assertTrue(message.contains("intReader") || message.contains("name"),
                    "Warning should mention reader or attribute name");
        }

        @Test
        @DisplayName("falls back to default when reader incompatible")
        void fallsBackToDefaultWhenIncompatible() {
            // Reader that rejects all attributes
            AttributeValueReader<Object> rejectingReader = new AttributeValueReader<>() {
                @Override
                public boolean canHandle(EAttribute attribute) {
                    return false;
                }

                @Override
                public Object read(JsonParser parser, EAttribute attr, DeserializationContext ctxt) {
                    throw new AssertionError("Should not be called");
                }
            };

            CodecValueRegistry registry = new CodecValueRegistry();
            registry.registerReader("rejectingReader", rejectingReader);

            EffectiveFeatureConfig config = EffectiveFeatureConfig.builder()
                    .feature(nameAttribute)
                    .key("name")
                    .serialize(true)
                    .valueReaderName("rejectingReader")
                    .build();

            AttributeDeserializationEntry entry = new AttributeDeserializationEntry(
                    config, nameAttribute, registry);

            // The entry should still work - it falls back to default behavior
            EObject person = createPerson();
            DeserializationState state = createStateWithObject(person);

            // Deserialize a string value - should use default (not custom reader)
            try (JsonParser parser = createParser("\"John Doe\"")) {
                entry.deserialize(state, parser, null);

                // Value should be set using default deserialization
                assertEquals("John Doe", person.eGet(nameAttribute));
            }
        }
    }

    @Nested
    @DisplayName("Generic CodecValueReader handling")
    class GenericCodecValueReaderHandling {

        @Test
        @DisplayName("accepts generic CodecValueReader and uses it for transformation")
        void acceptsGenericReaderAndUsesIt() {
            // Generic reader (not AttributeValueReader) - transforms the value
            org.eclipse.fennec.codec.api.value.CodecValueReader<String, EAttribute> prefixReader =
                    (parser, attr, ctxt) -> "PREFIX_" + parser.getString();

            CodecValueRegistry registry = new CodecValueRegistry();
            registry.registerReader("prefixReader", prefixReader);

            EffectiveFeatureConfig config = EffectiveFeatureConfig.builder()
                    .feature(nameAttribute)
                    .key("name")
                    .serialize(true)
                    .valueReaderName("prefixReader")
                    .build();

            // Should not log warning - generic readers have no canHandle() to check
            AttributeDeserializationEntry entry = new AttributeDeserializationEntry(
                    config, nameAttribute, registry);

            assertNotNull(entry);
            assertEquals(0, logHandler.getWarningCount(),
                    "No warning should be logged for generic reader");

            // Verify the reader is actually used
            EObject person = createPerson();
            DeserializationState state = createStateWithObject(person);

            try (JsonParser parser = createParser("\"John\"")) {
                entry.deserialize(state, parser, null);
                assertEquals("PREFIX_John", person.eGet(nameAttribute));
            }
        }
    }

    /**
     * Test handler to capture log messages.
     */
    private static class TestLogHandler extends Handler {
        private int warningCount = 0;
        private String lastWarningMessage;

        @Override
        public void publish(LogRecord record) {
            if (record.getLevel() == Level.WARNING) {
                warningCount++;
                lastWarningMessage = record.getMessage();
            }
        }

        @Override
        public void flush() {
        }

        @Override
        public void close() throws SecurityException {
        }

        public int getWarningCount() {
            return warningCount;
        }

        public String getLastWarningMessage() {
            return lastWarningMessage;
        }
    }
}
