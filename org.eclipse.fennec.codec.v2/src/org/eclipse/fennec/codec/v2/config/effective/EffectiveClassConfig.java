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

import java.util.Objects;

import org.eclipse.emf.ecore.EClass;

/**
 * Fully resolved, immutable class-level codec configuration.
 * <p>
 * This class combines all configuration for serializing an EClass:
 * <ul>
 *   <li>ID configuration</li>
 *   <li>Type configuration</li>
 *   <li>SuperType configuration</li>
 * </ul>
 * All properties are final and represent the effective merged values from
 * all configuration sources. No resolution logic is needed at serialization time.
 * </p>
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#6-eobject-serialization">Spec 6: EObject Serialization</a>
 * @author Mark Hoffmann
 * @since 2025-12-16
 * @deprecated This class uses deprecated config classes (EffectiveIdConfig, EffectiveTypeConfig, EffectiveSuperTypeConfig).
 *             Use the new config classes in org.eclipse.fennec.codec.config package instead.
 *             This class will be removed in a future release.
 */
@Deprecated
public final class EffectiveClassConfig {

    private final EClass eClass;
    private final EffectiveIdConfig idConfig;
    private final EffectiveTypeConfig typeConfig;
    private final EffectiveSuperTypeConfig superTypeConfig;

    private EffectiveClassConfig(Builder builder) {
        this.eClass = Objects.requireNonNull(builder.eClass, "eClass must not be null");
        this.idConfig = Objects.requireNonNull(builder.idConfig, "idConfig must not be null");
        this.typeConfig = Objects.requireNonNull(builder.typeConfig, "typeConfig must not be null");
        this.superTypeConfig = Objects.requireNonNull(builder.superTypeConfig, "superTypeConfig must not be null");
    }

    /**
     * Returns the EClass this configuration applies to.
     */
    public EClass getEClass() {
        return eClass;
    }

    /**
     * Returns the effective ID configuration.
     */
    public EffectiveIdConfig getIdConfig() {
        return idConfig;
    }

    /**
     * Returns the effective type configuration.
     */
    public EffectiveTypeConfig getTypeConfig() {
        return typeConfig;
    }

    /**
     * Returns the effective supertype configuration.
     */
    public EffectiveSuperTypeConfig getSuperTypeConfig() {
        return superTypeConfig;
    }

    // ========================================================================
    // Convenience methods for common checks
    // ========================================================================

    /**
     * Returns whether ID serialization is enabled.
     */
    public boolean isIdEnabled() {
        return idConfig.isEnabled();
    }

    /**
     * Returns whether type serialization is enabled.
     */
    public boolean isTypeEnabled() {
        return typeConfig.isEnabled();
    }

    /**
     * Returns whether supertype serialization is enabled.
     * Note: SuperType serialization depends on type serialization being enabled.
     */
    public boolean isSuperTypeEnabled() {
        return typeConfig.isEnabled() && superTypeConfig.isEnabled();
    }

    /**
     * Returns the ID key.
     */
    public String getIdKey() {
        return idConfig.getKey();
    }

    /**
     * Returns the type key.
     */
    public String getTypeKey() {
        return typeConfig.getTypeKey();
    }

    /**
     * Returns the supertype key.
     */
    public String getSuperTypeKey() {
        return superTypeConfig.getSuperTypeKey();
    }

    /**
     * Creates a new builder.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for EffectiveClassConfig.
     */
    public static final class Builder {
        private EClass eClass;
        private EffectiveIdConfig idConfig;
        private EffectiveTypeConfig typeConfig;
        private EffectiveSuperTypeConfig superTypeConfig;

        private Builder() {}

        public Builder eClass(EClass eClass) {
            this.eClass = eClass;
            return this;
        }

        public Builder idConfig(EffectiveIdConfig idConfig) {
            this.idConfig = idConfig;
            return this;
        }

        public Builder typeConfig(EffectiveTypeConfig typeConfig) {
            this.typeConfig = typeConfig;
            return this;
        }

        public Builder superTypeConfig(EffectiveSuperTypeConfig superTypeConfig) {
            this.superTypeConfig = superTypeConfig;
            return this;
        }

        public EffectiveClassConfig build() {
            return new EffectiveClassConfig(this);
        }
    }
}
