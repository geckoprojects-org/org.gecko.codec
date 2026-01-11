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
package org.eclipse.fennec.codec.v2.ser;

import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fennec.codec.v2.config.effective.EffectiveCodecConfig;
import org.eclipse.fennec.codec.v2.config.effective.EffectiveSuperTypeConfig;
import org.eclipse.fennec.codec.v2.config.effective.EffectiveTypeConfig;
import org.eclipse.fennec.codec.v2.context.ContextHelper;
import org.eclipse.fennec.model.metadata.SerializationFormat;
import org.eclipse.fennec.model.metadata.TypeStrategy;

import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;

/**
 * Serialization entry for EObject type information.
 * <p>
 * Handles the serialization of the type property based on the effective
 * (pre-merged) type configuration. No fallback logic is needed as all
 * configuration resolution happens in the {@link org.eclipse.fennec.codec.v2.config.effective.ConfigurationMerger}.
 * </p>
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#3-type-serialization">Spec 3: Type Serialization</a>
 * @author Mark Hoffmann
 * @since 2025-12-16
 */
public class TypeSerializationEntry implements SerializationEntry {

    private final EffectiveTypeConfig config;
    private final EffectiveCodecConfig codecConfig;
    private final EClass eClass;
    private final String typeValue;
    private final SuperTypeSerializationEntry superTypeEntry;

    /**
     * Creates a new TypeSerializationEntry with the effective type configuration.
     *
     * @param config the effective (pre-merged) type configuration
     * @param eClass the EClass being serialized (for computing type value if no discriminator)
     */
    public TypeSerializationEntry(EffectiveTypeConfig config, EClass eClass) {
        this(config, eClass, null, null);
    }

    /**
     * Creates a new TypeSerializationEntry with the effective type configuration
     * and optional supertype entry for STRUCTURED format embedding.
     *
     * @param config the effective (pre-merged) type configuration
     * @param eClass the EClass being serialized (for computing type value if no discriminator)
     * @param superTypeEntry optional supertype entry to embed in STRUCTURED format
     */
    public TypeSerializationEntry(EffectiveTypeConfig config, EClass eClass, SuperTypeSerializationEntry superTypeEntry) {
        this(config, eClass, superTypeEntry, null);
    }

    /**
     * Creates a new TypeSerializationEntry with the effective type configuration,
     * optional supertype entry, and codec configuration for smart compression.
     *
     * @param config the effective (pre-merged) type configuration
     * @param eClass the EClass being serialized (for computing type value if no discriminator)
     * @param superTypeEntry optional supertype entry to embed in STRUCTURED format
     * @param codecConfig the codec configuration for smart compression (may be null)
     */
    public TypeSerializationEntry(EffectiveTypeConfig config, EClass eClass,
            SuperTypeSerializationEntry superTypeEntry, EffectiveCodecConfig codecConfig) {
        this.config = Objects.requireNonNull(config, "config must not be null");
        this.eClass = Objects.requireNonNull(eClass, "eClass must not be null");
        this.typeValue = resolveTypeValue(eClass);
        this.superTypeEntry = superTypeEntry;
        this.codecConfig = codecConfig;
    }

    @Override
    public String getKey() {
        return config.getTypeKey();
    }

    @Override
    public boolean shouldSerialize(SerializationState state) {
        // Don't serialize _type when discriminatorPath is configured
        // because the type info is already embedded in the content at the feature path
        if (hasDiscriminatorPath()) {
            return false;
        }
        return config.isEnabled();
    }

    @Override
    public boolean shouldSerialize(SerializationState state, SerializationContext ctxt) {
        // Check for smart compression suppress flag (instance type == reference type)
        if (ContextHelper.isSuppressType(ctxt)) {
            // Clear the flag so it doesn't affect subsequent objects
            ContextHelper.clearSuppressType(ctxt);
            return false;
        }
        return shouldSerialize(state);
    }

    /**
     * Checks if a discriminator path is configured.
     * <p>
     * When a discriminator path is set (e.g., "info.profileName"), the type
     * information is already present in the content at that path. In this case,
     * we should NOT write a separate "_type" field.
     * </p>
     *
     * @return true if discriminatorPath is configured and non-empty
     */
    private boolean hasDiscriminatorPath() {
        String path = config.getDiscriminatorPath();
        return path != null && !path.isEmpty();
    }

    @Override
    public void serialize(SerializationState state, JsonGenerator gen, SerializationContext ctxt) {
        SerializationFormat format = config.getFormat();

        if (format == SerializationFormat.STRUCTURED) {
            serializeStructured(gen, ctxt);
        } else {
            serializePlain(gen, ctxt);
        }
    }

    /**
     * Serializes type information in PLAIN format.
     * <p>
     * Output format varies by strategy:
     * <ul>
     *   <li>URI: {@code "_type": "http://example.org/1.0#//Person"}</li>
     *   <li>NAME: {@code "_type": "Person"}</li>
     *   <li>CLASS: {@code "_type": "org.example.Person"}</li>
     *   <li>MAPPED: {@code "_type": "customer"}</li>
     *   <li>NUMERIC: {@code "_type": "3"}</li>
     *   <li>SCHEMA_AND_TYPE: {@code "_schema": "http://...", "_type": "Person"} (two fields)</li>
     * </ul>
     * </p>
     * <p>
     * When smart compression is enabled and the type belongs to the same schema
     * as the root object, a simple name is written instead of a full URI.
     * </p>
     *
     * @param gen the JSON generator
     * @param ctxt the serialization context (for smart compression)
     * @see <a href="docs/codec-v2-spec/04-global-options.md#1-smart-compression">Spec: Smart Compression</a>
     */
    private void serializePlain(JsonGenerator gen, SerializationContext ctxt) {
        TypeStrategy strategy = config.getStrategy();
        if (strategy == TypeStrategy.SCHEMA_AND_TYPE) {
            // SCHEMA_AND_TYPE in PLAIN format writes TWO separate fields
            EPackage ePackage = eClass.getEPackage();
            if (ePackage != null) {
                // For PLAIN format, ensure schema key has underscore prefix
                String plainSchemaKey = getPlainSchemaKey();
                gen.writeStringProperty(plainSchemaKey, ePackage.getNsURI());
            }
            gen.writeStringProperty(config.getTypeKey(), eClass.getName());
        } else {
            // All other strategies: single field with typeValue
            // Apply smart compression if enabled and same schema
            String effectiveTypeValue = applySmartCompression(typeValue, ctxt);
            gen.writeStringProperty(config.getTypeKey(), effectiveTypeValue);
        }
    }

    /**
     * Gets the schema key for PLAIN format.
     * <p>
     * PLAIN format keys should have underscore prefix at root level.
     * If the configured schemaKey doesn't start with underscore, prefix it.
     * </p>
     *
     * @return the schema key with underscore prefix for PLAIN format
     */
    private String getPlainSchemaKey() {
        String schemaKey = config.getSchemaKey();
        if (schemaKey.startsWith("_") || schemaKey.startsWith("@")) {
            // Already has prefix (underscore or custom like @vocab)
            return schemaKey;
        }
        return "_" + schemaKey;
    }

    /**
     * Serializes type information in STRUCTURED format.
     * <p>
     * Output format varies by strategy (all use unified "type" key except NUMERIC):
     * <ul>
     *   <li>URI: {@code {"type": "http://example.org/1.0#//Person"}}</li>
     *   <li>NAME: {@code {"type": "Person"}}</li>
     *   <li>CLASS: {@code {"type": "org.example.Person"}}</li>
     *   <li>MAPPED: {@code {"type": "customer"}}</li>
     *   <li>NUMERIC: {@code {"schema": "http://...", "classifier": 3}}</li>
     *   <li>SCHEMA_AND_TYPE: {@code {"schema": "http://...", "type": "Person"}}</li>
     * </ul>
     * </p>
     * <p>
     * When supertype is enabled and Type format is STRUCTURED, supertype is included
     * inside the _type object:
     * <ul>
     *   <li>ARRAY: {@code {"schema": "...", "type": "Person", "supertype": ["Entity", "..."]}}</li>
     *   <li>STRING: {@code {"schema": "...", "type": "Person", "supertype": "Entity,..."}}</li>
     * </ul>
     * </p>
     * <p>
     * When smart compression is enabled and the type belongs to the same schema
     * as the root object, a simple name is written instead of a full URI.
     * </p>
     *
     * @param gen the JSON generator
     * @param ctxt the serialization context (for smart compression)
     * @see <a href="docs/codec-v2-spec/05-type.md#14-structured-strategies">Spec: STRUCTURED Strategy</a>
     * @see <a href="docs/codec-v2-spec/06-supertype.md#3-structured-format">Spec: SuperType STRUCTURED</a>
     * @see <a href="docs/codec-v2-spec/04-global-options.md#1-smart-compression">Spec: Smart Compression</a>
     */
    private void serializeStructured(JsonGenerator gen, SerializationContext ctxt) {
        gen.writeName(config.getTypeKey());
        gen.writeStartObject();

        TypeStrategy strategy = config.getStrategy();
        if (strategy == null) {
            strategy = TypeStrategy.URI;
        }

        switch (strategy) {
            case NUMERIC:
                // NUMERIC: {"schema": "...", "classifier": N}
                EPackage numericPkg = eClass.getEPackage();
                if (numericPkg != null) {
                    gen.writeStringProperty(config.getSchemaKey(), numericPkg.getNsURI());
                }
                gen.writeNumberProperty("classifier", eClass.getClassifierID());
                break;

            case SCHEMA_AND_TYPE:
                // SCHEMA_AND_TYPE: {"schema": "...", "type": "..."}
                EPackage schemaPkg = eClass.getEPackage();
                if (schemaPkg != null) {
                    gen.writeStringProperty(config.getSchemaKey(), schemaPkg.getNsURI());
                }
                gen.writeStringProperty(config.getNameKey(), eClass.getName());
                break;

            default:
                // URI, NAME, CLASS, MAPPED: {"type": "<value>"}
                // All use the unified "type" key with the pre-computed typeValue
                // Apply smart compression if enabled and same schema
                String effectiveTypeValue = applySmartCompression(typeValue, ctxt);
                gen.writeStringProperty(config.getNameKey(), effectiveTypeValue);
                break;
        }

        // Include supertype inside the _type object when STRUCTURED format
        serializeSuperTypeInStructured(gen);

        gen.writeEndObject();
    }

    /**
     * Serializes supertype information inside the STRUCTURED _type object.
     * <p>
     * Only writes supertype if:
     * <ul>
     *   <li>SuperTypeSerializationEntry is provided</li>
     *   <li>SuperType is enabled in config</li>
     *   <li>There are actual supertypes to serialize</li>
     * </ul>
     * </p>
     *
     * @param gen the JSON generator
     */
    private void serializeSuperTypeInStructured(JsonGenerator gen) {
        if (superTypeEntry == null) {
            return;
        }

        List<String> superTypes = superTypeEntry.getSuperTypeValues();
        if (superTypes.isEmpty()) {
            return;
        }

        EffectiveSuperTypeConfig superTypeConfig = superTypeEntry.getConfig();
        String superTypeKey = superTypeConfig.getSuperTypeKey();

        if (superTypeConfig.isAsArray()) {
            // ARRAY presentation: "supertype": ["Entity", "http://audit.org/1.0#//Auditable"]
            gen.writeArrayPropertyStart(superTypeKey);
            for (String superType : superTypes) {
                gen.writeString(superType);
            }
            gen.writeEndArray();
        } else {
            // STRING presentation: "supertype": "Entity,http://audit.org/1.0#//Auditable"
            String joined = String.join(superTypeConfig.getSeparator(), superTypes);
            gen.writeStringProperty(superTypeKey, joined);
        }
    }

    /**
     * Resolves the type value to serialize based on the configured strategy.
     * <p>
     * Handles different type strategies:
     * <ul>
     *   <li>MAPPED - Uses discriminator value if available</li>
     *   <li>NAME - Simple EClass name</li>
     *   <li>CLASS - Java instance class name</li>
     *   <li>URI - Full EClass URI (default)</li>
     *   <li>NUMERIC - EClass classifier ID</li>
     *   <li>SCHEMA_AND_TYPE - Schema URI + type name</li>
     * </ul>
     * </p>
     *
     * @param eClass the EClass
     * @return the type value string
     */
    private String resolveTypeValue(EClass eClass) {
        // Use discriminator if configured (for MAPPED strategy)
        String discriminator = config.getDiscriminatorValue();
        if (discriminator != null && !discriminator.isEmpty()) {
            return discriminator;
        }

        // Resolve based on strategy
        TypeStrategy strategy = config.getStrategy();
        if (strategy == null) {
            strategy = TypeStrategy.URI;
        }

        switch (strategy) {
            case NAME:
                return eClass.getName();
            case CLASS:
                Class<?> instanceClass = eClass.getInstanceClass();
                return instanceClass != null ? instanceClass.getName() : eClass.getName();
            case NUMERIC:
                return String.valueOf(eClass.getClassifierID());
            case URI:
            default:
                // Full EMF URI: nsURI#//className
                return EcoreUtil.getURI(eClass).toString();
        }
    }

    /**
     * Applies smart compression to a type value if enabled.
     * <p>
     * When smart compression is enabled and the type belongs to the same schema
     * as the context (root object), the full URI is replaced with a simple name.
     * </p>
     * <p>
     * The root object always uses a full URI to establish the context schema.
     * Only contained objects (after root serialization) use simple names.
     * </p>
     *
     * @param typeValue the original type value (may be a full URI)
     * @param ctxt the serialization context
     * @return the compressed type value (simple name) or original value
     * @see <a href="docs/codec-v2-spec/04-global-options.md#1-smart-compression">Spec: Smart Compression</a>
     */
    private String applySmartCompression(String typeValue, SerializationContext ctxt) {
        // Check if smart compression is enabled
        if (codecConfig == null || !codecConfig.isSmartCompression()) {
            return typeValue;
        }

        // Root object must use full URI to establish context
        // Only apply compression after root is serialized
        if (!ContextHelper.isRootSerialized(ctxt)) {
            // Mark root as serialized for subsequent objects
            ContextHelper.setRootSerialized(ctxt);
            return typeValue;
        }

        // Check if type belongs to same schema as context
        if (ContextHelper.isSameSchema(ctxt, typeValue)) {
            return ContextHelper.extractSimpleName(typeValue);
        }

        return typeValue;
    }
}
