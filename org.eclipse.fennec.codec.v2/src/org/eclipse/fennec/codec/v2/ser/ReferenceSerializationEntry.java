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
package org.eclipse.fennec.codec.v2.ser;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fennec.codec.v2.config.effective.EffectiveCodecConfig;
import org.eclipse.fennec.codec.v2.config.effective.EffectiveFeatureConfig;
import org.eclipse.fennec.codec.v2.context.CodecWriteContext;
import org.eclipse.fennec.codec.v2.context.ContextHelper;
import org.eclipse.fennec.codec.api.value.CodecValueRegistry;
import org.eclipse.fennec.codec.api.value.CodecValueWriter;
import org.eclipse.fennec.codec.api.value.ReferenceValueWriter;

import tools.jackson.core.JsonGenerator;
import tools.jackson.core.TokenStreamContext;
import tools.jackson.databind.SerializationContext;

/**
 * Serialization entry for EReference values.
 * <p>
 * Handles the serialization of reference values based on the effective
 * (pre-merged) feature configuration. No fallback logic is needed as all
 * configuration resolution happens in the {@link org.eclipse.fennec.codec.v2.config.effective.ConfigurationMerger}.
 * Distinguishes between containment references (serialized inline) and
 * non-containment references (serialized as refs).
 * </p>
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#65-reference-serialization">Spec 6.5: Reference Serialization</a>
 * @author Mark Hoffmann
 * @since 2025-12-16
 */
/**
 * @deprecated Migrated to {@link org.eclipse.fennec.codec.ser.ReferenceSerializationEntry}.
 */
@Deprecated
public class ReferenceSerializationEntry implements SerializationEntry {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(ReferenceSerializationEntry.class.getName());

    private final EffectiveFeatureConfig config;
    private final EReference reference;
    private final String refKey;
    private final boolean smartCompression;
    private final EffectiveCodecConfig codecConfig;
    /** Custom writer for containment references - writes EObject in custom format */
    private final ReferenceValueWriter<?> containmentWriter;
    /** Custom writer for non-containment reference URIs - writes custom URI format */
    private final CodecValueWriter<EObject, EReference> uriWriter;

    /**
     * Creates a new ReferenceSerializationEntry with the effective feature configuration.
     *
     * @param config the effective (pre-merged) feature configuration
     * @param reference the EReference to serialize
     * @param refKey the JSON key to use for non-containment reference URIs
     */
    public ReferenceSerializationEntry(EffectiveFeatureConfig config, EReference reference, String refKey) {
        this(config, reference, refKey, false, null, null);
    }

    /**
     * Creates a new ReferenceSerializationEntry with smart compression support.
     *
     * @param config the effective (pre-merged) feature configuration
     * @param reference the EReference to serialize
     * @param refKey the JSON key to use for non-containment reference URIs
     * @param smartCompression whether smart compression is enabled
     */
    public ReferenceSerializationEntry(EffectiveFeatureConfig config, EReference reference,
            String refKey, boolean smartCompression) {
        this(config, reference, refKey, smartCompression, null, null);
    }

    /**
     * Creates a new ReferenceSerializationEntry with full configuration support.
     *
     * @param config the effective (pre-merged) feature configuration
     * @param reference the EReference to serialize
     * @param refKey the JSON key to use for non-containment reference URIs
     * @param smartCompression whether smart compression is enabled
     * @param codecConfig the effective codec configuration (for expand settings)
     */
    public ReferenceSerializationEntry(EffectiveFeatureConfig config, EReference reference,
            String refKey, boolean smartCompression, EffectiveCodecConfig codecConfig) {
        this(config, reference, refKey, smartCompression, codecConfig, null);
    }

    /**
     * Creates a new ReferenceSerializationEntry with full configuration and custom writer support.
     *
     * @param config the effective (pre-merged) feature configuration
     * @param reference the EReference to serialize
     * @param refKey the JSON key to use for non-containment reference URIs
     * @param smartCompression whether smart compression is enabled
     * @param codecConfig the effective codec configuration (for expand settings)
     * @param valueRegistry the registry for custom value writers (may be null)
     */
    public ReferenceSerializationEntry(EffectiveFeatureConfig config, EReference reference,
            String refKey, boolean smartCompression, EffectiveCodecConfig codecConfig,
            CodecValueRegistry valueRegistry) {
        this.config = config;
        this.reference = reference;
        this.refKey = refKey;
        this.smartCompression = smartCompression;
        this.codecConfig = codecConfig;

        // Pre-resolve the custom writer at construction time
        // We support two types of writers:
        // 1. ReferenceValueWriter<T extends EObject> for containment references
        // 2. CodecValueWriter<EObject, EReference> for non-containment URI transformation
        String writerName = config.getValueWriterName();
        if (writerName != null && !writerName.isEmpty() && valueRegistry != null) {
            CodecValueWriter<?, ?> writer = valueRegistry.getWriter(writerName).orElse(null);

            if (writer instanceof ReferenceValueWriter<?> refWriter) {
                // ReferenceValueWriter for containment - writes EObject in custom format
                if (refWriter.canHandle(reference)) {
                    this.containmentWriter = refWriter;
                    this.uriWriter = null;
                } else {
                    LOGGER.warning("ReferenceValueWriter '" + writerName + "' cannot handle reference '" +
                            reference.getName() + "' of type " + reference.getEReferenceType().getName());
                    this.containmentWriter = null;
                    this.uriWriter = null;
                }
            } else if (writer != null) {
                // Generic CodecValueWriter for URI transformation
                @SuppressWarnings("unchecked")
                CodecValueWriter<EObject, EReference> refUriWriter =
                        (CodecValueWriter<EObject, EReference>) writer;
                this.containmentWriter = null;
                this.uriWriter = refUriWriter;
            } else {
                this.containmentWriter = null;
                this.uriWriter = null;
            }
        } else {
            this.containmentWriter = null;
            this.uriWriter = null;
        }
    }

    @Override
    public String getKey() {
        return config.getKey();
    }

    @Override
    public boolean shouldSerialize(SerializationState state) {
        // Config already includes: global ignore, derived, transient, aspect.serialize checks
        if (!config.isSerialize()) {
            return false;
        }

        // Check value conditions - use cached value
        Object value = state.getValue(reference);

        // Null check
        if (value == null) {
            return config.isSerializeNull();
        }

        // Empty collection check
        if (reference.isMany() && value instanceof EList<?> list && list.isEmpty()) {
            return config.isSerializeEmpty();
        }

        return true;
    }

    @Override
    public void serialize(SerializationState state, JsonGenerator gen, SerializationContext ctxt) {
        // Use cached value from shouldSerialize call
        Object value = state.getValue(reference);

        if (value == null) {
            gen.writeNullProperty(config.getKey());
            return;
        }

        gen.writeName(config.getKey());

        if (reference.isMany() && value instanceof EList<?> list) {
            // Check if this is an EMap reference (Map.Entry type)
            if (isMapEntryReference()) {
                serializeEMap(list, gen, ctxt);
            } else {
                gen.writeStartArray();
                for (Object item : list) {
                    if (item instanceof EObject target) {
                        serializeReference(target, gen, ctxt);
                    }
                }
                gen.writeEndArray();
            }
        } else if (value instanceof EObject target) {
            serializeReference(target, gen, ctxt);
        }
    }

    /**
     * Serializes a single reference target.
     * <p>
     * Containment references are serialized inline (as nested objects),
     * unless they are cross-document containments (target in different resource).
     * Non-containment references are serialized as URI references, unless expand
     * is enabled for this reference.
     * </p>
     * <p>
     * When expand is enabled for a non-containment reference, the referenced object
     * is serialized inline (like a containment) if it is resolved (not a proxy).
     * </p>
     * <p>
     * When smart compression is enabled, type information is suppressed if the
     * instance type matches the declared reference type.
     * </p>
     *
     * @param target the target EObject
     * @param gen the JSON generator
     * @param ctxt the serialization context
     * @see <a href="docs/codec-v2-spec/07-reference.md#6-cross-document-containment">Spec: Cross-Document Containment</a>
     * @see <a href="docs/codec-v2-spec/07-reference.md#42-expand-inline-serialization">Spec: Expand Inline Serialization</a>
     */
    private void serializeReference(EObject target, JsonGenerator gen, SerializationContext ctxt) {
        // Check for cross-document containment: containment reference where target is in different resource
        if (reference.isContainment() && isCrossDocument(gen, target)) {
            // Cross-document containment: serialize as reference (like non-containment)
            writeReferenceObject(target, gen, true, ctxt);
        } else if (reference.isContainment()) {
            // Check for custom containment writer first - allows special conversion logic
            // (e.g., EPackage to JSON Schema for OpenAPI components/schemas)
            if (containmentWriter != null) {
                writeWithContainmentWriter(target, gen, ctxt);
            } else {
                // Standard containment: serialize inline
                // Smart compression is handled by TypeSerializationEntry (same-schema simple names)
                ctxt.writeValue(gen, target);
            }
        } else if (shouldExpandReference(target)) {
            // Non-containment with expand enabled: serialize inline (like containment)
            // Only if the target is resolved (not a proxy)
            serializeExpandedReference(target, gen, ctxt);
        } else {
            // Non-containment: serialize as reference
            writeReferenceObject(target, gen, false, ctxt);
        }
    }

    /**
     * Writes a containment reference value using a custom containment writer.
     *
     * @param target the target EObject
     * @param gen the JSON generator
     * @param ctxt the serialization context
     */
    @SuppressWarnings("unchecked")
    private void writeWithContainmentWriter(EObject target, JsonGenerator gen, SerializationContext ctxt) {
        try {
            ((ReferenceValueWriter<EObject>) containmentWriter).write(target, reference, gen, ctxt);
        } catch (IOException e) {
            throw new UncheckedIOException(
                    "Custom containment writer failed for reference: " + reference.getName(), e);
        }
    }

    /**
     * Checks if this non-containment reference should be expanded.
     * <p>
     * A reference is expanded if:
     * <ul>
     *   <li>The codecConfig is available AND</li>
     *   <li>The reference is configured for expansion (globally or specifically) AND</li>
     *   <li>The target is resolved (not a proxy)</li>
     *   <li>For bi-directional references: expandIgnoreBidirectional is false OR ref has no opposite</li>
     * </ul>
     * </p>
     *
     * @param target the target EObject
     * @return true if the reference should be expanded
     */
    private boolean shouldExpandReference(EObject target) {
        if (codecConfig == null) {
            return false;
        }

        // Don't expand proxies - they need to be serialized as references
        if (target.eIsProxy()) {
            return false;
        }

        // Check if this reference is configured for expansion
        if (!codecConfig.shouldExpand(reference)) {
            return false;
        }

        // Check bi-directional handling
        if (codecConfig.isExpandIgnoreBidirectional() && reference.getEOpposite() != null) {
            return false;
        }

        return true;
    }

    /**
     * Serializes an expanded non-containment reference as an inline object.
     * <p>
     * The object is serialized similarly to a containment, with smart compression
     * handled by TypeSerializationEntry (same-schema simple names).
     * </p>
     *
     * @param target the target EObject (must not be a proxy)
     * @param gen the JSON generator
     * @param ctxt the serialization context
     */
    private void serializeExpandedReference(EObject target, JsonGenerator gen, SerializationContext ctxt) {
        // Smart compression is handled by TypeSerializationEntry (same-schema simple names)
        ctxt.writeValue(gen, target);
    }

    /**
     * Checks if the target object is in a different resource than the source (cross-document).
     * <p>
     * This detects cross-document containment where the contained object has been
     * moved to or stored in a different EMF Resource.
     * </p>
     *
     * @param gen the JSON generator (to access source resource from context)
     * @param target the target EObject
     * @return true if target is in a different resource (cross-document)
     */
    private boolean isCrossDocument(JsonGenerator gen, EObject target) {
        TokenStreamContext ctx = gen.streamWriteContext();
        if (!(ctx instanceof CodecWriteContext codecCtx)) {
            return false;
        }

        Resource sourceResource = codecCtx.getResource();

        // Handle proxy targets
        if (target.eIsProxy() && target instanceof InternalEObject internalEObject) {
            URI proxyUri = internalEObject.eProxyURI();
            return sourceResource != null && sourceResource.getURI() != null
                    && !sourceResource.getURI().equals(proxyUri.trimFragment());
        }

        // Compare resources
        Resource targetResource = target.eResource();
        return sourceResource == null || sourceResource != targetResource;
    }

    /**
     * Writes a reference object with the configured reference key.
     * <p>
     * For cross-document references, generates an absolute or relative URI
     * depending on whether the target is in a different resource.
     * </p>
     * <p>
     * Type is always written. When smart compression is enabled and the type
     * is from the same schema as the root, a simple name is used.
     * </p>
     * <p>
     * If a custom value writer is configured, it writes the reference value
     * (the _ref field content) instead of the default URI logic.
     * </p>
     *
     * @param target the target EObject
     * @param gen the JSON generator
     * @param crossDocument true if this is a cross-document reference
     * @param ctxt the serialization context for smart compression
     * @see <a href="docs/codec-v2-spec/10-custom-values.md#5-reference-value-readerswriters">Spec: Reference Value Writers</a>
     */
    private void writeReferenceObject(EObject target, JsonGenerator gen, boolean crossDocument,
            SerializationContext ctxt) {
        gen.writeStartObject();

        // @CLAUDE: type serialization will need to be integrated with TypeSerializationEntry
        // For now, write the type (with smart compression if enabled)
        String typeUri = EcoreUtil.getURI(target.eClass()).toString();
        String effectiveType = applySmartCompressionToRef(typeUri, ctxt);
        gen.writeStringProperty("_type", effectiveType);

        // Write the reference value - use custom writer if configured
        gen.writeName(refKey);
        writeReferenceValue(target, gen, crossDocument, ctxt);

        gen.writeEndObject();
    }

    /**
     * Writes the reference value (the _ref field content).
     * <p>
     * If a URI writer is configured, it is used to write the reference value.
     * Otherwise, the default URI logic is used.
     * </p>
     *
     * @param target the target EObject
     * @param gen the JSON generator (positioned after the field name)
     * @param crossDocument true if this is a cross-document reference
     * @param ctxt the serialization context
     */
    private void writeReferenceValue(EObject target, JsonGenerator gen, boolean crossDocument,
            SerializationContext ctxt) {
        // Use URI writer for non-containment reference transformation if configured
        if (uriWriter != null) {
            try {
                uriWriter.write(target, reference, gen, ctxt);
            } catch (IOException e) {
                throw new UncheckedIOException(
                        "Custom URI writer failed for reference: " + reference.getName(), e);
            }
            return;
        }

        // Default: write URI string
        String uri = getReferenceUri(gen, target, crossDocument);
        gen.writeString(uri);
    }

    /**
     * Applies smart compression to a type value for reference serialization.
     * <p>
     * Similar to TypeSerializationEntry.applySmartCompression but simplified
     * for non-containment references.
     * </p>
     *
     * @param typeUri the full type URI
     * @param ctxt the serialization context
     * @return the simple name if same schema, otherwise the full URI
     */
    private String applySmartCompressionToRef(String typeUri, SerializationContext ctxt) {
        if (!smartCompression) {
            return typeUri;
        }
        // For references, root is already serialized so smart compression can apply
        if (ContextHelper.isSameSchema(ctxt, typeUri)) {
            return ContextHelper.extractSimpleName(typeUri);
        }
        return typeUri;
    }

    /**
     * Gets the URI for a reference target.
     * <p>
     * For same-document references, returns the fragment path (e.g., "//@employees.0").
     * For cross-document references, returns a relative or absolute URI
     * (e.g., "other.json#//@employees.0").
     * </p>
     *
     * @param gen the JSON generator (to access source resource from context)
     * @param target the target EObject
     * @param crossDocument true if this is a cross-document reference
     * @return the reference URI
     */
    private String getReferenceUri(JsonGenerator gen, EObject target, boolean crossDocument) {
        if (crossDocument) {
            // Cross-document: generate relative URI from source to target
            TokenStreamContext ctx = gen.streamWriteContext();
            if (ctx instanceof CodecWriteContext codecCtx) {
                Resource sourceResource = codecCtx.getResource();
                if (sourceResource != null && sourceResource.getURI() != null) {
                    URI targetUri = EcoreUtil.getURI(target);
                    URI sourceUri = sourceResource.getURI();
                    // Deresolve to get relative URI
                    URI relativeUri = targetUri.deresolve(sourceUri);
                    return relativeUri.toString();
                }
            }
            // Fallback: use absolute URI
            return EcoreUtil.getURI(target).toString();
        }

        // Same-document: use fragment only
        if (target.eResource() != null) {
            return target.eResource().getURIFragment(target);
        }
        // Fallback for objects not in a resource
        return target.eClass().getEPackage().getNsURI() + "#//" + target.eClass().getName();
    }

    // ========================================================================
    // EMap Serialization Support
    // ========================================================================

    /**
     * Checks if this reference is an EMap reference (reference type is a Map.Entry).
     * <p>
     * EMap references are detected by:
     * <ul>
     *   <li>instanceClassName equals "java.util.Map$Entry", OR</li>
     *   <li>The reference type has "key" and "value" features</li>
     * </ul>
     * </p>
     *
     * @return true if this is an EMap reference
     * @see <a href="docs/codec-v2-spec/11-emap.md">Spec: EMap Serialization</a>
     */
    private boolean isMapEntryReference() {
        EClass entryClass = reference.getEReferenceType();
        if (entryClass == null) {
            return false;
        }

        // Check instanceClassName for Map.Entry
        String instanceClassName = entryClass.getInstanceClassName();
        if ("java.util.Map$Entry".equals(instanceClassName)) {
            return true;
        }

        // Fallback: check for key and value features
        return entryClass.getEStructuralFeature("key") != null
                && entryClass.getEStructuralFeature("value") != null;
    }

    /**
     * Serializes an EMap as a JSON object.
     * <p>
     * Each map entry is serialized with its key as the JSON field name
     * and its value as the JSON field value:
     * <pre>
     * {
     *   "key1": value1,
     *   "key2": value2
     * }
     * </pre>
     * </p>
     *
     * @param entries the list of map entries (EObjects with key/value features)
     * @param gen the JSON generator
     * @param ctxt the serialization context
     */
    private void serializeEMap(List<?> entries, JsonGenerator gen, SerializationContext ctxt) {
        EClass entryClass = reference.getEReferenceType();
        var keyFeature = entryClass.getEStructuralFeature("key");
        var valueFeature = entryClass.getEStructuralFeature("value");

        gen.writeStartObject();

        for (Object item : entries) {
            if (item instanceof EObject entry) {
                // Get the key - convert to string
                Object keyValue = entry.eGet(keyFeature);
                String key = keyValue != null ? keyValue.toString() : "";

                // Write the key as field name
                gen.writeName(key);

                // Serialize the value based on its type
                Object value = entry.eGet(valueFeature);
                serializeMapEntryValue(value, valueFeature, gen, ctxt);
            }
        }

        gen.writeEndObject();
    }

    /**
     * Serializes the value of an EMap entry.
     * <p>
     * If the value is an EObject (reference), it is serialized inline.
     * If the value is a primitive/data type, it is serialized directly.
     * </p>
     *
     * @param value the value to serialize
     * @param valueFeature the value feature (to determine if it's a reference or attribute)
     * @param gen the JSON generator
     * @param ctxt the serialization context
     */
    private void serializeMapEntryValue(Object value, org.eclipse.emf.ecore.EStructuralFeature valueFeature,
            JsonGenerator gen, SerializationContext ctxt) {
        if (value == null) {
            gen.writeNull();
        } else if (value instanceof EObject eObject) {
            // Reference value - serialize inline
            ctxt.writeValue(gen, eObject);
        } else if (valueFeature instanceof EAttribute) {
            // Attribute value - serialize as primitive
            serializeAttributeValue(value, gen);
        } else {
            // Unknown - write as string
            gen.writeString(value.toString());
        }
    }

    /**
     * Serializes a primitive attribute value.
     *
     * @param value the value to serialize (non-null)
     * @param gen the JSON generator
     */
    private void serializeAttributeValue(Object value, JsonGenerator gen) {
        if (value instanceof String s) {
            gen.writeString(s);
        } else if (value instanceof Integer i) {
            gen.writeNumber(i);
        } else if (value instanceof Long l) {
            gen.writeNumber(l);
        } else if (value instanceof Double d) {
            gen.writeNumber(d);
        } else if (value instanceof Float f) {
            gen.writeNumber(f);
        } else if (value instanceof Boolean b) {
            gen.writeBoolean(b);
        } else if (value instanceof Number n) {
            gen.writeNumber(n.doubleValue());
        } else {
            gen.writeString(value.toString());
        }
    }
}
