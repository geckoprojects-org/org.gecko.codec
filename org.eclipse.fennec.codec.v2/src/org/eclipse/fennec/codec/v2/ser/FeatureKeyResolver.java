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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.fennec.codec.metadata.model.codec.ClassCodecAspect;
import org.eclipse.fennec.codec.metadata.model.codec.FeatureCodecAspect;
import org.eclipse.fennec.codec.metadata.model.codec.IdSerializationConfig;
import org.eclipse.fennec.codec.metadata.model.codec.SuperTypeSerializationConfig;
import org.eclipse.fennec.codec.metadata.model.codec.TypeSerializationConfig;
import org.eclipse.fennec.codec.v2.module.CodecModule;

/**
 * Helper class for resolving JSON property keys and URIs during serialization.
 * <p>
 * This class centralizes the key resolution logic that was previously duplicated
 * across multiple SerializationEntry implementations. It handles:
 * <ul>
 *   <li>Feature key resolution (from aspect or feature name)</li>
 *   <li>ID key resolution</li>
 *   <li>Type key resolution</li>
 *   <li>SuperType key resolution</li>
 *   <li>EClass URI generation</li>
 *   <li>Reference URI generation</li>
 * </ul>
 * </p>
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#6-eobject-serialization">Spec 6: EObject Serialization</a>
 * @author Mark Hoffmann
 * @since 2025-12-16
 */
public class FeatureKeyResolver {

    private final CodecModule codecModule;

    /**
     * Creates a new FeatureKeyResolver.
     *
     * @param codecModule the codec module for configuration access
     */
    public FeatureKeyResolver(CodecModule codecModule) {
        this.codecModule = codecModule;
    }

    // ========================================================================
    // Feature Key Resolution
    // ========================================================================

    /**
     * Resolves the JSON property key for a structural feature.
     * <p>
     * Resolution order:
     * <ol>
     *   <li>FeatureCodecAspect effectiveKey (if non-empty)</li>
     *   <li>ExtendedMetaData name (if configured)</li>
     *   <li>Feature name (default)</li>
     * </ol>
     * </p>
     *
     * @param feature the structural feature
     * @param featureAspect the feature codec aspect, may be null
     * @return the resolved key, never null
     */
    public String resolveFeatureKey(EStructuralFeature feature, FeatureCodecAspect featureAspect) {
        // Check for effective key in aspect
        if (featureAspect != null && isNonEmpty(featureAspect.getEffectiveKey())) {
            return featureAspect.getEffectiveKey();
        }

        // Use ExtendedMetaData name if configured
        if (codecModule.isUseNamesFromExtendedMetaData()) {
            // TODO: Get name from ExtendedMetaData annotation
            return feature.getName();
        }

        return feature.getName();
    }

    // ========================================================================
    // ID Key Resolution
    // ========================================================================

    /**
     * Resolves the JSON property key for the ID field.
     * <p>
     * Resolution order:
     * <ol>
     *   <li>ClassCodecAspect IdSerializationConfig idKey (if non-empty)</li>
     *   <li>Module default idKey</li>
     * </ol>
     * </p>
     *
     * @param classAspect the class codec aspect, may be null
     * @return the resolved ID key, never null
     */
    public String resolveIdKey(ClassCodecAspect classAspect) {
        if (classAspect != null) {
            IdSerializationConfig config = classAspect.getIdConfig();
            if (config != null && isNonEmpty(config.getIdKey())) {
                return config.getIdKey();
            }
        }
        return codecModule.getIdKey();
    }

    // ========================================================================
    // Type Key Resolution
    // ========================================================================

    /**
     * Resolves the JSON property key for the type field.
     * <p>
     * Resolution order:
     * <ol>
     *   <li>ClassCodecAspect TypeSerializationConfig typeKey (if non-empty)</li>
     *   <li>Module default typeKey</li>
     * </ol>
     * </p>
     *
     * @param classAspect the class codec aspect, may be null
     * @return the resolved type key, never null
     */
    public String resolveTypeKey(ClassCodecAspect classAspect) {
        if (classAspect != null) {
            TypeSerializationConfig config = classAspect.getTypeConfig();
            if (config != null && isNonEmpty(config.getTypeKey())) {
                return config.getTypeKey();
            }
        }
        return codecModule.getTypeKey();
    }

    /**
     * Resolves the type value (discriminator or EClass URI).
     * <p>
     * Resolution order:
     * <ol>
     *   <li>ClassCodecAspect discriminatorValue (if non-empty)</li>
     *   <li>EClass URI</li>
     * </ol>
     * </p>
     *
     * @param eClass the EClass
     * @param classAspect the class codec aspect, may be null
     * @return the resolved type value, never null
     */
    public String resolveTypeValue(EClass eClass, ClassCodecAspect classAspect) {
        if (classAspect != null && isNonEmpty(classAspect.getDiscriminatorValue())) {
            return classAspect.getDiscriminatorValue();
        }
        return getEClassUri(eClass);
    }

    // ========================================================================
    // SuperType Key Resolution
    // ========================================================================

    /**
     * Resolves the JSON property key for the supertype field.
     * <p>
     * Resolution order:
     * <ol>
     *   <li>ClassCodecAspect SuperTypeSerializationConfig superTypeKey (if non-empty)</li>
     *   <li>Module default superTypeKey</li>
     * </ol>
     * </p>
     *
     * @param classAspect the class codec aspect, may be null
     * @return the resolved supertype key, never null
     */
    public String resolveSuperTypeKey(ClassCodecAspect classAspect) {
        if (classAspect != null) {
            SuperTypeSerializationConfig config = classAspect.getSuperTypeConfig();
            if (config != null && isNonEmpty(config.getSuperTypeKey())) {
                return config.getSuperTypeKey();
            }
        }
        return codecModule.getSuperTypeKey();
    }

    // ========================================================================
    // URI Generation
    // ========================================================================

    /**
     * Gets the URI for an EClass.
     * <p>
     * Format: {@code nsURI#//className}
     * </p>
     *
     * @param eClass the EClass
     * @return the EClass URI
     */
    public String getEClassUri(EClass eClass) {
        return eClass.getEPackage().getNsURI() + "#//" + eClass.getName();
    }

    /**
     * Gets the URI for a referenced EObject.
     * <p>
     * If the target has a resource, returns: {@code resourceURI#fragment}
     * Otherwise, returns: {@code #className}
     * </p>
     *
     * @param target the target EObject
     * @return the reference URI
     */
    public String getReferenceUri(EObject target) {
        Resource resource = target.eResource();
        if (resource != null) {
            return resource.getURI().toString() + "#" + resource.getURIFragment(target);
        }
        return "#" + target.eClass().getName();
    }

    // ========================================================================
    // Utility Methods
    // ========================================================================

    /**
     * Checks if a string is non-null and non-empty.
     *
     * @param value the string to check
     * @return true if the string is non-null and non-empty
     */
    private boolean isNonEmpty(String value) {
        return value != null && !value.isEmpty();
    }
}
