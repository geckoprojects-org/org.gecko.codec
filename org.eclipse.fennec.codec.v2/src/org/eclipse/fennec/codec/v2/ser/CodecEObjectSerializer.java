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

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.fennec.codec.v2.config.effective.EffectiveClassConfig;
import org.eclipse.fennec.codec.v2.config.effective.EffectiveCodecConfig;
import org.eclipse.fennec.codec.v2.config.effective.EffectiveFeatureConfig;
import org.eclipse.fennec.codec.v2.context.EMFCodecWriteContext;

import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

/**
 * Jackson serializer for EMF EObjects using effective configuration.
 * <p>
 * This serializer orchestrates the serialization of EObjects by delegating to
 * specialized {@link SerializationEntry} implementations for each concern:
 * <ul>
 *   <li>{@link IdSerializationEntry} - ID field</li>
 *   <li>{@link TypeSerializationEntry} - Type information</li>
 *   <li>{@link SuperTypeSerializationEntry} - Supertype information</li>
 *   <li>{@link AttributeSerializationEntry} - Attribute values</li>
 *   <li>{@link ReferenceSerializationEntry} - Reference values</li>
 * </ul>
 * </p>
 * <p>
 * The serialization order follows the spec:
 * <ol>
 *   <li>ID (if enabled and idOnTop)</li>
 *   <li>Type (if enabled)</li>
 *   <li>SuperTypes (if enabled)</li>
 *   <li>Features (attributes and references)</li>
 * </ol>
 * </p>
 * <p>
 * Configuration is provided via {@link EffectiveCodecConfig} which contains
 * pre-merged settings from all configuration sources. No fallback logic is
 * needed as all resolution happens in the
 * {@link org.eclipse.fennec.codec.v2.config.effective.ConfigurationMerger}.
 * </p>
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#6-eobject-serialization">Spec 6: EObject Serialization</a>
 * @see <a href="docs/codec-v2-serialization-spec.md#165-field-ordering">Spec 16.5: Field Ordering</a>
 * @author Mark Hoffmann
 * @since 2025-12-16
 */
public class CodecEObjectSerializer extends ValueSerializer<EObject> {

    private final EffectiveCodecConfig config;

    /**
     * Creates a new CodecEObjectSerializer with the effective codec configuration.
     *
     * @param config the effective (pre-merged) codec configuration
     */
    public CodecEObjectSerializer(EffectiveCodecConfig config) {
        this.config = config;
    }

    @Override
    public Class<EObject> handledType() {
        return EObject.class;
    }

    @Override
    public void serialize(EObject value, JsonGenerator gen, SerializationContext ctxt) {
        if (value == null) {
            gen.writeNull();
            return;
        }

        // Update context if available
        if (gen.streamWriteContext() instanceof EMFCodecWriteContext emfContext) {
            emfContext.setCurrentEObject(value);
        }

        EClass eClass = value.eClass();

        // Get effective class configuration (cached)
        EffectiveClassConfig classConfig = config.getClassConfig(eClass);

        // Build ordered serialization entries
        Map<String, SerializationEntry> entries = buildSerializationEntries(eClass, classConfig);

        // Apply ordering
        entries = applyOrdering(entries, classConfig);

        // Create serialization state with value cache
        SerializationState state = new SerializationState(value);

        // Write object
        gen.writeStartObject(value);
        for (Map.Entry<String, SerializationEntry> entry : entries.entrySet()) {
            SerializationEntry serEntry = entry.getValue();
            if (serEntry.shouldSerialize(state, ctxt)) {
                serEntry.serialize(state, gen, ctxt);
            }
        }
        gen.writeEndObject();
    }

    /**
     * Builds the serialization entries for the given EClass.
     *
     * @param eClass the EClass of the object
     * @param classConfig the effective class configuration
     * @return map of key to serialization entry
     */
    private Map<String, SerializationEntry> buildSerializationEntries(
            EClass eClass,
            EffectiveClassConfig classConfig) {

        Map<String, SerializationEntry> entries = new LinkedHashMap<>();

        // Add ID entry (if enabled in effective config)
        if (classConfig.isIdEnabled()) {
            IdSerializationEntry idEntry = new IdSerializationEntry(classConfig.getIdConfig(), eClass);
            entries.put(idEntry.getKey(), idEntry);
        }

        // Add type entry (if enabled in effective config)
        if (classConfig.isTypeEnabled()) {
            TypeSerializationEntry typeEntry = new TypeSerializationEntry(
                    classConfig.getTypeConfig(), eClass);
            entries.put(typeEntry.getKey(), typeEntry);
        }

        // Add supertype entry (if enabled in effective config)
        if (classConfig.isSuperTypeEnabled()) {
            SuperTypeSerializationEntry superTypeEntry = new SuperTypeSerializationEntry(
                    classConfig.getSuperTypeConfig(), eClass);
            entries.put(superTypeEntry.getKey(), superTypeEntry);
        }

        // Add feature entries
        for (EStructuralFeature feature : eClass.getEAllStructuralFeatures()) {
            // Get effective feature configuration (cached)
            EffectiveFeatureConfig featureConfig = config.getFeatureConfig(feature);

            SerializationEntry featureEntry;
            if (feature instanceof EAttribute attribute) {
                featureEntry = new AttributeSerializationEntry(featureConfig, attribute);
            } else if (feature instanceof EReference reference) {
                featureEntry = new ReferenceSerializationEntry(
                        featureConfig, reference, config.getRefKey(), config.isSmartCompression(), config);
            } else {
                continue;
            }

            entries.put(featureEntry.getKey(), featureEntry);
        }

        return entries;
    }

    /**
     * Applies ordering rules to the serialization entries.
     * <p>
     * Handles:
     * <ul>
     *   <li>Alphabetical sorting (if configured)</li>
     *   <li>ID on top (if configured)</li>
     * </ul>
     * </p>
     *
     * @param entries the unordered entries
     * @param classConfig the effective class configuration
     * @return ordered entries
     */
    private Map<String, SerializationEntry> applyOrdering(
            Map<String, SerializationEntry> entries,
            EffectiveClassConfig classConfig) {

        boolean sortAlphabetically = config.isSortPropertiesAlphabetically();
        boolean idOnTop = classConfig.isIdEnabled() && classConfig.getIdConfig().isOnTop();
        String idKey = classConfig.isIdEnabled() ? classConfig.getIdConfig().getKey() : null;

        if (!sortAlphabetically && !idOnTop) {
            return entries;
        }

        LinkedHashMap<String, SerializationEntry> ordered = new LinkedHashMap<>();

        if (sortAlphabetically) {
            TreeMap<String, SerializationEntry> sorted = new TreeMap<>(entries);
            if (idOnTop && idKey != null && sorted.containsKey(idKey)) {
                ordered.put(idKey, sorted.remove(idKey));
            }
            ordered.putAll(sorted);
        } else if (idOnTop && idKey != null && entries.containsKey(idKey)) {
            ordered.put(idKey, entries.get(idKey));
            for (Map.Entry<String, SerializationEntry> entry : entries.entrySet()) {
                if (!entry.getKey().equals(idKey)) {
                    ordered.put(entry.getKey(), entry.getValue());
                }
            }
        } else {
            return entries;
        }

        return ordered;
    }
}
