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
import org.eclipse.fennec.model.metadata.TypeStrategy;

/**
 * Fully resolved, immutable type serialization configuration.
 * <p>
 * All properties are final and represent the effective merged values from
 * all configuration sources (load/save options, module config, model aspects).
 * No resolution logic is needed at serialization time.
 * </p>
 * <p>
 * Type serialization uses two orthogonal dimensions:
 * <ul>
 *   <li><b>Format</b> (PLAIN | STRUCTURED) - how data is presented</li>
 *   <li><b>Strategy</b> (URI | NAME | CLASS | NUMERIC | MAPPED | SCHEMA_AND_TYPE) - what information is transported</li>
 * </ul>
 * </p>
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#3-type-serialization">Spec 3: Type Serialization</a>
 * @author Mark Hoffmann
 * @since 2025-12-16
 * @deprecated Use {@link org.eclipse.fennec.codec.config.TypeConfig} instead.
 *             This class will be removed in a future release.
 */
@Deprecated
public final class EffectiveTypeConfig {

    private final boolean enabled;
    private final SerializationFormat format;
    private final TypeStrategy strategy;
    private final String typeKey;
    private final String schemaKey;
    private final String nameKey;
    private final String discriminatorPath;
    private final String discriminatorValue;

    private EffectiveTypeConfig(Builder builder) {
        this.enabled = builder.enabled;
        this.format = builder.format;
        this.strategy = builder.strategy;
        this.typeKey = builder.typeKey;
        this.schemaKey = builder.schemaKey;
        this.nameKey = builder.nameKey;
        this.discriminatorPath = builder.discriminatorPath;
        this.discriminatorValue = builder.discriminatorValue;
    }

    /**
     * Returns whether type serialization is enabled.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Returns the serialization format (PLAIN or STRUCTURED).
     */
    public SerializationFormat getFormat() {
        return format;
    }

    /**
     * Returns the type serialization strategy.
     */
    public TypeStrategy getStrategy() {
        return strategy;
    }

    /**
     * Returns the JSON property key for type information.
     */
    public String getTypeKey() {
        return typeKey;
    }

    /**
     * Returns the key for schema in STRUCTURED/SCHEMA_AND_TYPE formats.
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
     * Returns the discriminator path for MAPPED strategy.
     */
    public String getDiscriminatorPath() {
        return discriminatorPath;
    }

    /**
     * Returns the discriminator value for this class (MAPPED strategy).
     */
    public String getDiscriminatorValue() {
        return discriminatorValue;
    }

    /**
     * Creates a new builder with default values.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for EffectiveTypeConfig.
     */
    public static final class Builder {
        private boolean enabled = true;
        private SerializationFormat format = SerializationFormat.PLAIN;
        private TypeStrategy strategy = TypeStrategy.URI;
        private String typeKey = "_type";
        private String schemaKey = "schema";
        private String nameKey = "type";  // Inner type key in STRUCTURED format (default: "type")
        private String discriminatorPath;
        private String discriminatorValue;

        private Builder() {}

        public Builder enabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public Builder format(SerializationFormat format) {
            this.format = format;
            return this;
        }

        public Builder strategy(TypeStrategy strategy) {
            this.strategy = strategy;
            return this;
        }

        public Builder typeKey(String typeKey) {
            this.typeKey = typeKey;
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

        public Builder discriminatorPath(String discriminatorPath) {
            this.discriminatorPath = discriminatorPath;
            return this;
        }

        public Builder discriminatorValue(String discriminatorValue) {
            this.discriminatorValue = discriminatorValue;
            return this;
        }

        public EffectiveTypeConfig build() {
            return new EffectiveTypeConfig(this);
        }
    }
}
