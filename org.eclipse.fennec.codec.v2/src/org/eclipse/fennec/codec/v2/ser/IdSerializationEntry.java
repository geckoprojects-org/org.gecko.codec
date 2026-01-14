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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.fennec.codec.v2.config.effective.EffectiveIdConfig;
import org.eclipse.fennec.model.metadata.IdKeyMode;
import org.eclipse.fennec.model.metadata.SerializationFormat;

import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;

/**
 * Serialization entry for EObject ID field.
 * <p>
 * Handles the serialization of the ID property based on the effective
 * (pre-merged) ID configuration. Supports:
 * <ul>
 *   <li>PLAIN format: combined string value with separator</li>
 *   <li>STRUCTURED format: nested object with individual fields</li>
 *   <li>Multiple ID features (combined ID)</li>
 *   <li>KeyMode: ID_ONLY, BOTH, FEATURE_ONLY</li>
 * </ul>
 * </p>
 *
 * @see <a href="docs/codec-v2-spec/06-id.md">Spec: ID Serialization</a>
 * @author Mark Hoffmann
 * @since 2025-12-16
 */
public class IdSerializationEntry implements SerializationEntry {

    private final EffectiveIdConfig config;
    private final EClass eClass;

    /**
     * Creates a new IdSerializationEntry with the effective ID configuration.
     *
     * @param config the effective (pre-merged) ID configuration
     * @param eClass the EClass being serialized (used to resolve features)
     */
    public IdSerializationEntry(EffectiveIdConfig config, EClass eClass) {
        this.config = config;
        this.eClass = eClass;
    }

    @Override
    public String getKey() {
        return config.getKey();
    }

    @Override
    public boolean shouldSerialize(SerializationState state) {
        if (!config.isEnabled()) {
            return false;
        }
        // For FEATURE_ONLY mode, we don't serialize the _id field
        if (config.getKeyMode() == IdKeyMode.FEATURE_ONLY) {
            return false;
        }
        Map<String, Object> idValues = resolveIdValues(state.getEObject());
        return !idValues.isEmpty();
    }

    @Override
    public void serialize(SerializationState state, JsonGenerator gen, SerializationContext ctxt) {
        Map<String, Object> idValues = resolveIdValues(state.getEObject());
        if (idValues.isEmpty()) {
            return;
        }

        if (config.getFormat() == SerializationFormat.STRUCTURED) {
            serializeStructured(gen, idValues);
        } else {
            serializePlain(gen, idValues);
        }
    }

    /**
     * Serializes ID in PLAIN format (combined string with separator).
     * <p>
     * Output format (with serializeSeparator=true, multiple features):
     * <pre>
     * "_id": "John-Doe",
     * "_separator": "-"
     * </pre>
     * Output format (with serializeSeparator=false or single feature):
     * <pre>
     * "_id": "John-Doe"
     * </pre>
     * </p>
     */
    private void serializePlain(JsonGenerator gen, Map<String, Object> idValues) {
        String combinedValue = combineValues(idValues);
        if (combinedValue != null) {
            gen.writeStringProperty(config.getKey(), combinedValue);

            // Write separator field if enabled and multiple features
            if (config.isSerializeSeparator() && idValues.size() > 1) {
                gen.writeStringProperty(config.getEffectiveSeparatorKey(), config.getSeparator());
            }
        }
    }

    /**
     * Serializes ID in STRUCTURED format (nested object).
     * <p>
     * Output format (with serializeSeparator=true, multiple features):
     * <pre>
     * "_id": {
     *   "separator": "-",
     *   "firstName": "John",
     *   "lastName": "Doe"
     * }
     * </pre>
     * Output format (with serializeSeparator=false or single feature):
     * <pre>
     * "_id": {
     *   "firstName": "John",
     *   "lastName": "Doe"
     * }
     * </pre>
     * </p>
     */
    private void serializeStructured(JsonGenerator gen, Map<String, Object> idValues) {
        gen.writeName(config.getKey());
        gen.writeStartObject();

        // Write separator first if enabled and multiple features
        if (config.isSerializeSeparator() && idValues.size() > 1) {
            gen.writeStringProperty(config.getEffectiveSeparatorKey(), config.getSeparator());
        }

        // Write each ID feature
        for (Map.Entry<String, Object> entry : idValues.entrySet()) {
            writeValue(gen, entry.getKey(), entry.getValue());
        }

        gen.writeEndObject();
    }

    /**
     * Writes a single value to the generator with appropriate type handling.
     */
    private void writeValue(JsonGenerator gen, String key, Object value) {
        if (value == null) {
            gen.writeNullProperty(key);
        } else if (value instanceof String s) {
            gen.writeStringProperty(key, s);
        } else if (value instanceof Integer i) {
            gen.writeNumberProperty(key, i);
        } else if (value instanceof Long l) {
            gen.writeNumberProperty(key, l);
        } else if (value instanceof Double d) {
            gen.writeNumberProperty(key, d);
        } else if (value instanceof Boolean b) {
            gen.writeBooleanProperty(key, b);
        } else {
            gen.writeStringProperty(key, value.toString());
        }
    }

    /**
     * Combines multiple ID values into a single string with separator.
     */
    private String combineValues(Map<String, Object> idValues) {
        if (idValues.isEmpty()) {
            return null;
        }
        if (idValues.size() == 1) {
            Object value = idValues.values().iterator().next();
            return value != null ? value.toString() : null;
        }

        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Object value : idValues.values()) {
            if (!first) {
                sb.append(config.getSeparator());
            }
            sb.append(value != null ? value.toString() : "");
            first = false;
        }
        return sb.toString();
    }

    /**
     * Resolves the ID values for the given EObject.
     * <p>
     * Resolution order:
     * <ol>
     *   <li>If idFeatures is configured → use those features in order</li>
     *   <li>Otherwise → use all features marked with eID="true"</li>
     * </ol>
     * If no ID features are found, an empty map is returned and no _id field
     * will be serialized. This matches the old codec behavior and makes sense
     * because there would be nothing meaningful to deserialize back.
     * </p>
     *
     * @param eObject the EObject to get the ID from
     * @return map of feature name to value (preserves order), empty if no ID features
     */
    private Map<String, Object> resolveIdValues(EObject eObject) {
        Map<String, Object> result = new LinkedHashMap<>();

        // 1. Check for configured idFeatures
        List<String> configuredFeatures = config.getIdFeatures();
        if (configuredFeatures != null && !configuredFeatures.isEmpty()) {
            for (String featureName : configuredFeatures) {
                EStructuralFeature feature = eClass.getEStructuralFeature(featureName);
                if (feature != null) {
                    Object value = eObject.eGet(feature);
                    if (value != null) {
                        result.put(featureName, value);
                    }
                }
            }
            if (!result.isEmpty()) {
                return result;
            }
        }

        // 2. Look for eID="true" features
        List<EStructuralFeature> idFeatures = findIdFeatures();
        for (EStructuralFeature feature : idFeatures) {
            Object value = eObject.eGet(feature);
            if (value != null) {
                result.put(feature.getName(), value);
            }
        }

        // No fallback - if no ID features exist, return empty map
        // This is intentional: there's no meaningful way to deserialize
        // a fragment back when there's no ID attribute to set it to
        return result;
    }

    /**
     * Finds all features marked with eID="true" in the EClass.
     */
    private List<EStructuralFeature> findIdFeatures() {
        List<EStructuralFeature> result = new ArrayList<>();

        // Check for single ID attribute
        EStructuralFeature idAttribute = eClass.getEIDAttribute();
        if (idAttribute != null) {
            result.add(idAttribute);
        }

        return result;
    }

    /**
     * Returns the configured key mode.
     *
     * @return the IdKeyMode
     */
    public IdKeyMode getKeyMode() {
        return config.getKeyMode();
    }

    /**
     * Returns whether this entry is for BOTH mode (needs feature values serialized too).
     *
     * @return true if keyMode is BOTH
     */
    public boolean isBothMode() {
        return config.getKeyMode() == IdKeyMode.BOTH;
    }

    /**
     * Returns the list of ID feature names being used.
     *
     * @return list of feature names
     */
    public List<String> getIdFeatureNames() {
        List<String> configuredFeatures = config.getIdFeatures();
        if (configuredFeatures != null && !configuredFeatures.isEmpty()) {
            return configuredFeatures;
        }
        List<EStructuralFeature> idFeatures = findIdFeatures();
        return idFeatures.stream().map(EStructuralFeature::getName).toList();
    }
}
