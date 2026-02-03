/**
 * Copyright (c) 2012 - 2026 Data In Motion and others.
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
package org.eclipse.fennec.codec.v2.config.effective;

import org.eclipse.fennec.model.metadata.SerializationFormat;
import org.eclipse.fennec.model.metadata.SuperTypeSelection;

/**
 * Fully resolved, immutable supertype serialization configuration.
 * <p>
 * All properties are final and represent the effective merged values from
 * all configuration sources (load/save options, module config, model aspects).
 * No resolution logic is needed at serialization time.
 * </p>
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#7-supertype-serialization">Spec 7: SuperType Serialization</a>
 * @author Mark Hoffmann
 * @since 2025-12-16
 * @deprecated Use {@link org.eclipse.fennec.codec.config.SuperTypeConfig} instead.
 *             This class will be removed in a future release.
 */
@Deprecated
public final class EffectiveSuperTypeConfig {

    private final boolean enabled;
    private final SuperTypeSelection selection;
    private final SerializationFormat format;
    private final boolean asArray;
    private final String separator;
    private final String superTypeKey;
    private final String schemaKey;
    private final String nameKey;
    private final boolean useSmartCompression;
    private final boolean validateSuperTypeHierarchy;

    private EffectiveSuperTypeConfig(Builder builder) {
        this.enabled = builder.enabled;
        this.selection = builder.selection;
        this.format = builder.format;
        this.asArray = builder.asArray;
        this.separator = builder.separator;
        this.superTypeKey = builder.superTypeKey;
        this.schemaKey = builder.schemaKey;
        this.nameKey = builder.nameKey;
        this.useSmartCompression = builder.useSmartCompression;
        this.validateSuperTypeHierarchy = builder.validateSuperTypeHierarchy;
    }

    /**
     * Returns whether supertype serialization is enabled.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Returns which supertypes to include (ALL, ALL_EMF, SINGLE, NONE).
     */
    public SuperTypeSelection getSelection() {
        return selection;
    }

    /**
     * Returns the serialization format (PLAIN, STRUCTURED).
     * <p>
     * Note: SuperType format follows Type format. When Type is STRUCTURED,
     * supertype is written inside the _type object.
     * </p>
     */
    public SerializationFormat getFormat() {
        return format;
    }

    /**
     * Returns whether to serialize supertypes as JSON array (true) or
     * as separator-joined string (false).
     * <p>
     * Default is true (array presentation).
     * </p>
     */
    public boolean isAsArray() {
        return asArray;
    }

    /**
     * Returns the separator character for STRING presentation (when asArray=false).
     * <p>
     * Default is comma (",").
     * </p>
     */
    public String getSeparator() {
        return separator;
    }

    /**
     * Returns the JSON property key for supertype information.
     */
    public String getSuperTypeKey() {
        return superTypeKey;
    }

    /**
     * Returns the key for schema in STRUCTURED format.
     */
    public String getSchemaKey() {
        return schemaKey;
    }

    /**
     * Returns the key for name in STRUCTURED format.
     */
    public String getNameKey() {
        return nameKey;
    }

    /**
     * Returns whether to use smart compression (plain names for same-schema supertypes).
     */
    public boolean isUseSmartCompression() {
        return useSmartCompression;
    }

    /**
     * Returns whether to validate supertype hierarchy during deserialization.
     * <p>
     * When true, the deserializer validates that declared supertypes in JSON
     * match the resolved EClass's actual supertypes. Deserialization fails
     * if there's a mismatch.
     * </p>
     * <p>
     * Default is false (no validation - supertypes are ignored during deserialization).
     * </p>
     */
    public boolean isValidateSuperTypeHierarchy() {
        return validateSuperTypeHierarchy;
    }

    /**
     * Creates a new builder with default values.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for EffectiveSuperTypeConfig.
     */
    public static final class Builder {
        private boolean enabled = false;
        private SuperTypeSelection selection = SuperTypeSelection.SINGLE;
        private SerializationFormat format = SerializationFormat.PLAIN;
        private boolean asArray = true;
        private String separator = ",";
        private String superTypeKey = "_supertype";
        private String schemaKey = "schema";
        private String nameKey = "supertype";  // Spec default: "supertype" (not "name" - that's for typeNameKey)
        private boolean useSmartCompression = false;
        private boolean validateSuperTypeHierarchy = false;

        private Builder() {}

        public Builder enabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public Builder selection(SuperTypeSelection selection) {
            this.selection = selection;
            return this;
        }

        public Builder format(SerializationFormat format) {
            this.format = format;
            return this;
        }

        public Builder asArray(boolean asArray) {
            this.asArray = asArray;
            return this;
        }

        public Builder separator(String separator) {
            this.separator = separator;
            return this;
        }

        public Builder superTypeKey(String superTypeKey) {
            this.superTypeKey = superTypeKey;
            return this;
        }

        public Builder schemaKey(String schemaKey) {
            this.schemaKey = schemaKey;
            return this;
        }

        public Builder nameKey(String nameKey) {
            this.nameKey = nameKey;
            return this;
        }

        public Builder useSmartCompression(boolean useSmartCompression) {
            this.useSmartCompression = useSmartCompression;
            return this;
        }

        public Builder validateSuperTypeHierarchy(boolean validateSuperTypeHierarchy) {
            this.validateSuperTypeHierarchy = validateSuperTypeHierarchy;
            return this;
        }

        public EffectiveSuperTypeConfig build() {
            return new EffectiveSuperTypeConfig(this);
        }
    }
}
