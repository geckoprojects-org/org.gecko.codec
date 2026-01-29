
/*
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
package org.eclipse.fennec.codec.config.spec;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.fennec.codec.diagnostic.DiagnosticCollector;
import org.eclipse.fennec.codec.config.TypeConfig;
import org.eclipse.fennec.model.metadata.SerializationFormat;
import org.eclipse.fennec.model.metadata.TypeStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Spec-based tests for {@link TypeConfig}.
 * <p>
 * These tests are derived directly from the codec-v2 specification documents:
 * <ul>
 *   <li>{@code docs/codec-v2-spec/06-type.md} - Type Serialization specification</li>
 *   <li>{@code docs/codec-v2-spec/16-annotation-reference.md} - Annotation Reference</li>
 * </ul>
 * <p>
 * Test organization:
 * <ul>
 *   <li>Section 1: Default values (from spec section 4)</li>
 *   <li>Section 2: Validation rules (from spec section 7)</li>
 *   <li>Section 3: TypeStrategy behavior</li>
 *   <li>Section 4: Format × Strategy combinations</li>
 * </ul>
 */
@DisplayName("TypeConfig Spec Tests")
class TypeConfigSpecTest {

    // ========================================================================
    // Section 1: Default Values
    // Spec reference: 06-type.md section 4 "Default Type Settings"
    // ========================================================================

    @Nested
    @DisplayName("1. Default Values (spec section 4)")
    class DefaultValues {

        /**
         * Spec: "Format | codec.typeFormat | PLAIN"
         * Spec section 4: Default Output (PLAIN + URI)
         */
        @Test
        @DisplayName("1.1 typeFormat defaults to PLAIN")
        void typeFormat_defaultsToPlain() {
            TypeConfig config = TypeConfig.defaults();
            assertEquals(SerializationFormat.PLAIN, config.getFormat());
        }

        /**
         * Spec: "Strategy | codec.typeStrategy | URI"
         *
         * @claude ISSUE: ConfigProperty.TYPE_STRATEGY has default "NAME" but spec says "URI"!
         *         This test documents the spec requirement. Implementation needs to be fixed.
         */
        @Test
        @DisplayName("1.2 typeStrategy defaults to URI (SPEC SAYS URI)")
        void typeStrategy_defaultsToUri() {
            TypeConfig config = TypeConfig.defaults();
            // Spec says: URI is the default
            // Current implementation may have NAME - this test verifies spec compliance
            assertEquals(TypeStrategy.URI, config.getStrategy(),
                "Spec section 4 says: Strategy | codec.typeStrategy | URI");
        }

        /**
         * Spec: "Type Key | codec.typeKey | _type"
         */
        @Test
        @DisplayName("1.3 typeKey defaults to '_type'")
        void typeKey_defaultsToUnderscore() {
            TypeConfig config = TypeConfig.defaults();
            assertEquals("_type", config.getTypeKey());
        }

        /**
         * Spec: "Schema Key | codec.typeSchemaKey | schema"
         */
        @Test
        @DisplayName("1.4 typeSchemaKey defaults to 'schema'")
        void typeSchemaKey_defaultsToSchema() {
            TypeConfig config = TypeConfig.defaults();
            assertEquals("schema", config.getSchemaKey());
        }

        /**
         * Spec: "Name Key | codec.typeNameKey | type"
         */
        @Test
        @DisplayName("1.5 typeNameKey defaults to 'type'")
        void typeNameKey_defaultsToType() {
            TypeConfig config = TypeConfig.defaults();
            assertEquals("type", config.getNameKey());
        }

        /**
         * Spec section 1.4: "TypeStrategy.NONE replaces the deprecated typeInclude=false"
         * Spec: typeInclude is DEPRECATED - use typeStrategy=NONE instead
         *
         * When include=true (default), type serialization is enabled.
         */
        @Test
        @DisplayName("1.6 include defaults to true (type serialization enabled)")
        void include_defaultsToTrue() {
            TypeConfig config = TypeConfig.defaults();
            assertTrue(config.isInclude());
        }

        /**
         * Spec: valueReaderName and valueWriterName default to null (no custom reader/writer)
         */
        @Test
        @DisplayName("1.7 valueReaderName and valueWriterName default to null")
        void valueReaderWriter_defaultToNull() {
            TypeConfig config = TypeConfig.defaults();
            assertNull(config.getValueReaderName());
            assertNull(config.getValueWriterName());
        }
    }

    // ========================================================================
    // Section 2: Validation Rules
    // Spec reference: 06-type.md section 7 "Configuration Validation Rules"
    // ========================================================================

    @Nested
    @DisplayName("2. Validation Rules (spec section 7)")
    class ValidationRules {

        /**
         * Spec rule T-V20: "typeNameKey set + format=PLAIN → WARNING"
         * "typeNameKey is only used in STRUCTURED format; value ignored"
         */
        @Test
        @DisplayName("2.1 T-V20: typeNameKey with PLAIN format produces WARNING")
        void typeNameKey_plainFormat_producesWarning() {
            TypeConfig config = TypeConfig.builder()
                    .format(SerializationFormat.PLAIN)
                    .nameKey("customType")  // non-default value
                    .build();

            DiagnosticCollector diagnostics = new DiagnosticCollector();
            config.validate(diagnostics);

            assertTrue(diagnostics.hasWarnings(),
                "Expected WARNING: typeNameKey is ignored when typeFormat is not STRUCTURED");
            assertTrue(diagnostics.getWarnings().stream()
                    .anyMatch(d -> d.getMessage().contains("typeNameKey")));
        }

        /**
         * Spec rule T-V20: Default nameKey with PLAIN format should NOT produce warning
         */
        @Test
        @DisplayName("2.2 T-V20: Default typeNameKey with PLAIN format produces no WARNING")
        void typeNameKey_plainFormat_defaultValue_noWarning() {
            TypeConfig config = TypeConfig.builder()
                    .format(SerializationFormat.PLAIN)
                    // nameKey keeps default "type"
                    .build();

            DiagnosticCollector diagnostics = new DiagnosticCollector();
            config.validate(diagnostics);

            assertFalse(diagnostics.hasWarnings(),
                "Default nameKey with PLAIN format should not produce warning");
        }

        /**
         * Spec rule T-V21: "typeSchemaKey set + format=PLAIN + strategy≠SCHEMA_AND_TYPE → WARNING"
         * "typeSchemaKey is only used in STRUCTURED format or PLAIN+SCHEMA_AND_TYPE; value ignored"
         */
        @Test
        @DisplayName("2.3 T-V21: typeSchemaKey with PLAIN format (non-SCHEMA_AND_TYPE) produces WARNING")
        void typeSchemaKey_plainFormat_nonSchemaAndType_producesWarning() {
            TypeConfig config = TypeConfig.builder()
                    .format(SerializationFormat.PLAIN)
                    .strategy(TypeStrategy.URI)  // NOT SCHEMA_AND_TYPE
                    .schemaKey("customSchema")   // non-default value
                    .build();

            DiagnosticCollector diagnostics = new DiagnosticCollector();
            config.validate(diagnostics);

            assertTrue(diagnostics.hasWarnings(),
                "Expected WARNING: typeSchemaKey is ignored when not STRUCTURED or SCHEMA_AND_TYPE");
            assertTrue(diagnostics.getWarnings().stream()
                    .anyMatch(d -> d.getMessage().contains("typeSchemaKey")));
        }

        /**
         * Spec rule T-V21: schemaKey with PLAIN + SCHEMA_AND_TYPE should NOT produce warning
         */
        @Test
        @DisplayName("2.4 T-V21: typeSchemaKey with PLAIN format + SCHEMA_AND_TYPE produces no WARNING")
        void typeSchemaKey_plainFormat_schemaAndType_noWarning() {
            TypeConfig config = TypeConfig.builder()
                    .format(SerializationFormat.PLAIN)
                    .strategy(TypeStrategy.SCHEMA_AND_TYPE)
                    .schemaKey("customSchema")
                    .build();

            DiagnosticCollector diagnostics = new DiagnosticCollector();
            config.validate(diagnostics);

            assertFalse(diagnostics.hasWarnings(),
                "schemaKey with SCHEMA_AND_TYPE strategy should not produce warning");
        }

        /**
         * Spec rule T-V21: schemaKey with STRUCTURED format should NOT produce warning
         */
        @Test
        @DisplayName("2.5 T-V21: typeSchemaKey with STRUCTURED format produces no WARNING")
        void typeSchemaKey_structuredFormat_noWarning() {
            TypeConfig config = TypeConfig.builder()
                    .format(SerializationFormat.STRUCTURED)
                    .strategy(TypeStrategy.URI)
                    .schemaKey("customSchema")
                    .build();

            DiagnosticCollector diagnostics = new DiagnosticCollector();
            config.validate(diagnostics);

            assertFalse(diagnostics.hasWarnings(),
                "schemaKey with STRUCTURED format should not produce warning");
        }

        /**
         * Spec: Valid configuration should produce no warnings
         */
        @Test
        @DisplayName("2.6 Valid STRUCTURED + SCHEMA_AND_TYPE config produces no warnings")
        void validConfig_noWarnings() {
            TypeConfig config = TypeConfig.builder()
                    .format(SerializationFormat.STRUCTURED)
                    .strategy(TypeStrategy.SCHEMA_AND_TYPE)
                    .typeKey("_type")
                    .schemaKey("schema")
                    .nameKey("type")
                    .build();

            DiagnosticCollector diagnostics = new DiagnosticCollector();
            config.validate(diagnostics);

            assertFalse(diagnostics.hasWarnings());
            assertFalse(diagnostics.hasErrors());
        }
    }

    // ========================================================================
    // Section 3: TypeStrategy Behavior
    // Spec reference: 06-type.md section 1 "Format × Strategy for Type"
    // ========================================================================

    @Nested
    @DisplayName("3. TypeStrategy Values (spec section 1)")
    class TypeStrategyValues {

        /**
         * Spec section 1.4: "NONE strategy indicates that no type information should be written"
         * "TypeStrategy.NONE replaces the deprecated typeInclude=false"
         */
        @Test
        @DisplayName("3.1 NONE strategy disables type serialization")
        void noneStrategy_disablesTypeSerialization() {
            TypeConfig config = TypeConfig.builder()
                    .strategy(TypeStrategy.NONE)
                    .build();

            assertEquals(TypeStrategy.NONE, config.getStrategy());
            // Note: include is independent - NONE is the preferred way to disable
        }

        /**
         * Spec section 1.1 PLAIN format examples show all strategies
         */
        @Test
        @DisplayName("3.2 All TypeStrategy values are valid for PLAIN format")
        void allStrategies_validForPlainFormat() {
            for (TypeStrategy strategy : TypeStrategy.values()) {
                TypeConfig config = TypeConfig.builder()
                        .format(SerializationFormat.PLAIN)
                        .strategy(strategy)
                        .build();

                DiagnosticCollector diagnostics = new DiagnosticCollector();
                config.validate(diagnostics);

                // No errors should be produced for any strategy with PLAIN format
                assertFalse(diagnostics.hasErrors(),
                    "Strategy " + strategy + " should be valid for PLAIN format");
            }
        }

        /**
         * Spec section 1.2 STRUCTURED format examples show all strategies
         */
        @Test
        @DisplayName("3.3 All TypeStrategy values are valid for STRUCTURED format")
        void allStrategies_validForStructuredFormat() {
            for (TypeStrategy strategy : TypeStrategy.values()) {
                TypeConfig config = TypeConfig.builder()
                        .format(SerializationFormat.STRUCTURED)
                        .strategy(strategy)
                        .build();

                DiagnosticCollector diagnostics = new DiagnosticCollector();
                config.validate(diagnostics);

                // No errors should be produced for any strategy with STRUCTURED format
                assertFalse(diagnostics.hasErrors(),
                    "Strategy " + strategy + " should be valid for STRUCTURED format");
            }
        }
    }

    // ========================================================================
    // Section 4: Format × Strategy Combinations
    // Spec reference: 06-type.md sections 1.1 and 1.2
    // ========================================================================

    @Nested
    @DisplayName("4. Format × Strategy Combinations (spec sections 1.1, 1.2)")
    class FormatStrategyCombinations {

        /**
         * Spec section 1.6: SCHEMA_AND_TYPE strategy
         * PLAIN format: two separate top-level fields (_schema, _type)
         *
         * Configuration should allow custom keys for schema field.
         */
        @Test
        @DisplayName("4.1 PLAIN + SCHEMA_AND_TYPE uses schemaKey for separate field")
        void plainFormat_schemaAndType_usesSchemaKey() {
            TypeConfig config = TypeConfig.builder()
                    .format(SerializationFormat.PLAIN)
                    .strategy(TypeStrategy.SCHEMA_AND_TYPE)
                    .typeKey("_type")
                    .schemaKey("_schema")
                    .build();

            assertEquals(SerializationFormat.PLAIN, config.getFormat());
            assertEquals(TypeStrategy.SCHEMA_AND_TYPE, config.getStrategy());
            assertEquals("_type", config.getTypeKey());
            assertEquals("_schema", config.getSchemaKey());
        }

        /**
         * Spec section 1.6: STRUCTURED format with SCHEMA_AND_TYPE
         * Nested object with schema, type fields inside
         */
        @Test
        @DisplayName("4.2 STRUCTURED + SCHEMA_AND_TYPE uses all keys")
        void structuredFormat_schemaAndType_usesAllKeys() {
            TypeConfig config = TypeConfig.builder()
                    .format(SerializationFormat.STRUCTURED)
                    .strategy(TypeStrategy.SCHEMA_AND_TYPE)
                    .typeKey("_type")
                    .schemaKey("schema")
                    .nameKey("type")
                    .build();

            assertEquals(SerializationFormat.STRUCTURED, config.getFormat());
            assertEquals(TypeStrategy.SCHEMA_AND_TYPE, config.getStrategy());
            assertEquals("_type", config.getTypeKey());    // outer key
            assertEquals("schema", config.getSchemaKey()); // inner key
            assertEquals("type", config.getNameKey());     // inner key
        }

        /**
         * Spec section 3.3: Java Builder example with custom keys
         * Shows @context, @vocab, @type for JSON-LD style output
         */
        @Test
        @DisplayName("4.3 Custom keys for JSON-LD style output")
        void customKeys_jsonLdStyle() {
            TypeConfig config = TypeConfig.builder()
                    .format(SerializationFormat.STRUCTURED)
                    .strategy(TypeStrategy.SCHEMA_AND_TYPE)
                    .typeKey("@context")
                    .schemaKey("@vocab")
                    .nameKey("@type")
                    .build();

            assertEquals("@context", config.getTypeKey());
            assertEquals("@vocab", config.getSchemaKey());
            assertEquals("@type", config.getNameKey());
        }
    }

    // ========================================================================
    // Section 5: Merge Behavior
    // Spec reference: 16-annotation-reference.md "Configuration Resolution"
    // ========================================================================

    @Nested
    @DisplayName("5. Merge Behavior (spec: Configuration Resolution)")
    class MergeBehavior {

        /**
         * Spec: "Higher priority sources override lower ones"
         * Property map values should override existing config values.
         */
        @Test
        @DisplayName("5.1 Property map overrides existing values")
        void propertyMap_overridesExistingValues() {
            TypeConfig base = TypeConfig.builder()
                    .format(SerializationFormat.PLAIN)
                    .strategy(TypeStrategy.URI)
                    .typeKey("_type")
                    .build();

            Map<String, Object> override = new HashMap<>();
            override.put("codec.typeStrategy", "NAME");
            override.put("codec.typeKey", "@type");

            TypeConfig merged = base.mergeWith(override);

            assertEquals(SerializationFormat.PLAIN, merged.getFormat());  // unchanged
            assertEquals(TypeStrategy.NAME, merged.getStrategy());        // overridden
            assertEquals("@type", merged.getTypeKey());                   // overridden
        }

        /**
         * Spec: Empty or null source should not change config
         */
        @Test
        @DisplayName("5.2 Empty property map returns same config")
        void emptyPropertyMap_returnsSameConfig() {
            TypeConfig config = TypeConfig.builder()
                    .strategy(TypeStrategy.NAME)
                    .build();

            TypeConfig merged = config.mergeWith(new HashMap<>());
            assertSame(config, merged);

            merged = config.mergeWith(null);
            assertSame(config, merged);
        }

        /**
         * Spec: String values for enums should be accepted (case-insensitive)
         */
        @Test
        @DisplayName("5.3 Enum values accepted as strings (case-insensitive)")
        void enumValues_acceptedAsStrings() {
            TypeConfig base = TypeConfig.defaults();

            Map<String, Object> override = new HashMap<>();
            override.put("codec.typeStrategy", "name");  // lowercase
            override.put("codec.typeFormat", "STRUCTURED");

            TypeConfig merged = base.mergeWith(override);

            assertEquals(TypeStrategy.NAME, merged.getStrategy());
            assertEquals(SerializationFormat.STRUCTURED, merged.getFormat());
        }
    }

    // ========================================================================
    // Section 6: Deprecation
    // Spec reference: 06-type.md section 8 "Deprecated: typeInclude"
    // ========================================================================

    @Nested
    @DisplayName("6. Deprecation (spec section 8)")
    class Deprecation {

        /**
         * Spec section 8.1: "typeInclude=false → typeStrategy=NONE"
         *
         * Note: This is documentation that include=false should be treated
         * as equivalent to strategy=NONE. The actual translation may happen
         * at annotation parsing level, not in TypeConfig itself.
         */
        @Test
        @DisplayName("6.1 include=false is equivalent to strategy=NONE conceptually")
        void includesFalse_equivalentToNone() {
            // Spec says: typeInclude=false has identical behavior to typeStrategy=NONE
            // Both result in no type field being written

            TypeConfig withIncludeFalse = TypeConfig.builder()
                    .include(false)
                    .build();

            TypeConfig withStrategyNone = TypeConfig.builder()
                    .strategy(TypeStrategy.NONE)
                    .build();

            // Both configurations mean "no type information"
            assertFalse(withIncludeFalse.isInclude());
            assertEquals(TypeStrategy.NONE, withStrategyNone.getStrategy());
        }

        /**
         * Spec section 8.3: "If both typeInclude=false AND typeStrategy are set, typeStrategy takes precedence"
         *
         * This behavior should be validated at annotation parsing level.
         * TypeConfig can hold both values - precedence logic is elsewhere.
         */
        @Test
        @DisplayName("6.2 Both include and strategy can be set (precedence handled elsewhere)")
        void bothIncludeAndStrategy_canBeSet() {
            TypeConfig config = TypeConfig.builder()
                    .include(false)
                    .strategy(TypeStrategy.NAME)
                    .build();

            // Both values can be stored
            assertFalse(config.isInclude());
            assertEquals(TypeStrategy.NAME, config.getStrategy());
            // Note: Higher-level code determines which one takes precedence
        }
    }
}
