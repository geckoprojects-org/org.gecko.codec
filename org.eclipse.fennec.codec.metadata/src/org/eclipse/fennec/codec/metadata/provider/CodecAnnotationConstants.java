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
package org.eclipse.fennec.codec.metadata.provider;

/**
 * Constants for codec-related EAnnotation sources and detail keys.
 * <p>
 * These constants define the annotation source and detail keys used in EMF models
 * to configure codec serialization and deserialization behavior. EAnnotations are
 * the standard EMF mechanism for attaching metadata to model elements (EPackage,
 * EClass, EStructuralFeature).
 * </p>
 *
 * <h2>Unified Annotation Source</h2>
 * <p>
 * All codec annotations use a single source URI: {@code http://eclipse.org/fennec/codec}.
 * Configuration is specified entirely through detail key-value pairs. This allows
 * combining multiple configurations (ID, type, supertype) in a single annotation.
 * </p>
 *
 * <h2>Example in Ecore XML</h2>
 * <pre>{@code
 * <eClassifiers xsi:type="ecore:EClass" name="Person">
 *   <eAnnotations source="http://eclipse.org/fennec/codec">
 *     <!-- ID configuration -->
 *     <details key="idStrategy" value="ID_FIELD"/>
 *     <details key="idKey" value="_id"/>
 *     <!-- Type configuration -->
 *     <details key="typeStrategy" value="URI"/>
 *     <details key="typeKey" value="_type"/>
 *   </eAnnotations>
 * </eClassifiers>
 * }</pre>
 *
 * <h2>MAPPED Type Strategy Example</h2>
 * <pre>{@code
 * <!-- Base class - defines the mapping -->
 * <eClassifiers xsi:type="ecore:EClass" name="Device" abstract="true">
 *   <eAnnotations source="http://eclipse.org/fennec/codec">
 *     <details key="typeMapId" value="iot-sensors"/>
 *     <details key="typeKey" value="_type"/>
 *   </eAnnotations>
 * </eClassifiers>
 *
 * <!-- Concrete class - registers with the mapping -->
 * <eClassifiers xsi:type="ecore:EClass" name="TemperatureSensor" eSuperTypes="#//Device">
 *   <eAnnotations source="http://eclipse.org/fennec/codec">
 *     <details key="typeMapId" value="iot-sensors"/>
 *     <details key="typeDiscriminator" value="temp-sensor"/>
 *   </eAnnotations>
 * </eClassifiers>
 * }</pre>
 *
 * <h2>Annotation Hierarchy</h2>
 * <p>
 * Annotations can be placed at different levels:
 * </p>
 * <ul>
 *   <li><b>EClass level:</b> Defines default behavior for all instances of that class</li>
 *   <li><b>EStructuralFeature level:</b> Overrides or extends class-level settings for specific features</li>
 *   <li><b>EReference level:</b> Can define type resolution for polymorphic references</li>
 * </ul>
 *
 * @author Mark Hoffmann
 * @since 2025-12-04
 */
public final class CodecAnnotationConstants {

    private CodecAnnotationConstants() {
        // Constants class - prevent instantiation
    }

    // ========================================================================
    // ANNOTATION SOURCE
    // Single unified source for all codec annotations
    // ========================================================================

    /**
     * The unified annotation source for all codec configuration.
     * <p>
     * All codec-related configuration is placed in a single EAnnotation with this
     * source URI. Configuration is specified through detail key-value pairs.
     * </p>
     *
     * <h3>Example</h3>
     * <pre>{@code
     * <eAnnotations source="http://eclipse.org/fennec/codec">
     *   <!-- ID configuration -->
     *   <details key="idStrategy" value="ID_FIELD"/>
     *   <details key="idKey" value="_id"/>
     *   <!-- Type configuration -->
     *   <details key="typeStrategy" value="URI"/>
     *   <details key="typeKey" value="_type"/>
     *   <!-- SuperType configuration -->
     *   <details key="superTypeSerialize" value="true"/>
     * </eAnnotations>
     * }</pre>
     */
    public static final String CODEC_SOURCE = "http://eclipse.org/fennec/codec";

    // ========================================================================
    // DETAIL KEYS - Unified format
    // All keys use consistent prefixes: id*, type*, superType*, value*
    // ========================================================================

    // ------------------------------------------------------------------------
    // General detail keys
    // ------------------------------------------------------------------------

    /**
     * Detail key for marking a feature as transient (not serialized).
     * Value: "true" or "false". Default: "false".
     */
    public static final String KEY_TRANSIENT = "transient";

    /**
     * Detail key for enabling cross-package annotation inheritance.
     * Value: "true" or "false". Default: "false".
     */
    public static final String KEY_INHERIT = "inherit";

    // ------------------------------------------------------------------------
    // ID configuration detail keys (prefix: id*)
    // ------------------------------------------------------------------------

    /**
     * Detail key for ID serialization strategy.
     * Values: "ID_FIELD", "COMBINED", "NONE". Default: "ID_FIELD".
     */
    public static final String KEY_ID_STRATEGY = "idStrategy";

    /**
     * Detail key for ID property name in serialized output.
     * Default: "_id".
     */
    public static final String KEY_ID_KEY = "idKey";

    /**
     * Detail key for separator in combined IDs.
     * Used when idStrategy is COMBINED. Default: "-".
     */
    public static final String KEY_ID_SEPARATOR = "idSeparator";

    /**
     * Detail key for features used in combined ID.
     * Comma-separated list of feature names. Used when idStrategy is COMBINED.
     */
    public static final String KEY_ID_FEATURES = "idFeatures";

    /**
     * Detail key for custom ID value reader name.
     */
    public static final String KEY_ID_VALUE_READER_NAME = "idValueReaderName";

    /**
     * Detail key for custom ID value writer name.
     */
    public static final String KEY_ID_VALUE_WRITER_NAME = "idValueWriterName";

    // ------------------------------------------------------------------------
    // Type configuration detail keys (prefix: type*)
    // ------------------------------------------------------------------------

    /**
     * Detail key for type serialization strategy.
     * Values: "URI", "NAME", "CLASS", "NUMERIC", "MAPPED". Default: "URI".
     */
    public static final String KEY_TYPE_STRATEGY = "typeStrategy";

    /**
     * Detail key for type property name in serialized output.
     * Default: "_type".
     */
    public static final String KEY_TYPE_KEY = "typeKey";

    /**
     * Detail key for whether to include type information.
     * Value: "true" or "false". Default: "true".
     */
    public static final String KEY_TYPE_INCLUDE = "typeInclude";

    /**
     * Detail key for type mapping ID (MAPPED strategy).
     * <p>
     * Identifies a specific type discrimination context. Both base class and
     * concrete classes must use the same mapId to participate in the mapping.
     * </p>
     *
     * <h3>Example</h3>
     * <pre>{@code
     * <!-- Base class defines the mapping -->
     * <details key="typeMapId" value="iot-sensors"/>
     *
     * <!-- Concrete class registers with the same mapping -->
     * <details key="typeMapId" value="iot-sensors"/>
     * <details key="typeDiscriminator" value="temp-sensor"/>
     * }</pre>
     */
    public static final String KEY_TYPE_MAP_ID = "typeMapId";

    /**
     * Detail key for type discriminator value (MAPPED strategy).
     * <p>
     * Registers a concrete class with a discriminator value in the mapping
     * identified by {@link #KEY_TYPE_MAP_ID}.
     * </p>
     */
    public static final String KEY_TYPE_DISCRIMINATOR = "typeDiscriminator";

    /**
     * Detail key for feature path to discriminator value (MAPPED strategy).
     * <p>
     * Specifies which feature (or nested path like "info.sensorType") contains
     * the discriminator value. Used on base class.
     * </p>
     */
    public static final String KEY_TYPE_DISCRIMINATOR_PATH = "typeDiscriminatorPath";

    /**
     * Detail key prefix for static type discriminator mappings.
     * <p>
     * Used to define complete type mappings upfront on the base class.
     * Format: typeDiscriminator.{value} = {EClass URI}
     * </p>
     *
     * <h3>Example</h3>
     * <pre>{@code
     * <details key="typeDiscriminator.temp-sensor" value="http://example.org#//TemperatureSensor"/>
     * <details key="typeDiscriminator.humidity-sensor" value="http://example.org#//HumiditySensor"/>
     * }</pre>
     */
    public static final String KEY_TYPE_DISCRIMINATOR_PREFIX = KEY_TYPE_DISCRIMINATOR + ".";

    /**
     * Detail key for custom type value reader name.
     */
    public static final String KEY_TYPE_VALUE_READER_NAME = "typeValueReaderName";

    /**
     * Detail key for custom type value writer name.
     */
    public static final String KEY_TYPE_VALUE_WRITER_NAME = "typeValueWriterName";

    // ------------------------------------------------------------------------
    // SuperType configuration detail keys (prefix: superType*)
    // ------------------------------------------------------------------------

    /**
     * Detail key for enabling supertype serialization.
     * Value: "true" or "false". Default: "false".
     */
    public static final String KEY_SUPERTYPE_SERIALIZE = "superTypeSerialize";

    /**
     * Detail key for supertype property name in serialized output.
     * Default: "_superTypes".
     */
    public static final String KEY_SUPERTYPE_KEY = "superTypeKey";

    /**
     * Detail key for supertype serialization strategy.
     * Values: "ALL", "ALL_EMF", "SINGLE", "NONE". Default: "ALL".
     */
    public static final String KEY_SUPERTYPE_STRATEGY = "superTypeStrategy";

    /**
     * Detail key for serializing supertypes as array vs string.
     * Value: "true" or "false". Default: "true".
     */
    public static final String KEY_SUPERTYPE_AS_ARRAY = "superTypeAsArray";

    /**
     * Detail key for separator when serializing supertypes as string.
     * Default: ",".
     */
    public static final String KEY_SUPERTYPE_SEPARATOR = "superTypeSeparator";

    /**
     * Detail key for custom supertype value writer name.
     */
    public static final String KEY_SUPERTYPE_WRITER_NAME = "superTypeWriterName";

    // ------------------------------------------------------------------------
    // Feature configuration detail keys (prefix: value*)
    // ------------------------------------------------------------------------

    /**
     * Detail key for custom value reader name on a feature.
     */
    public static final String KEY_VALUE_READER_NAME = "valueReaderName";

    /**
     * Detail key for custom value writer name on a feature.
     */
    public static final String KEY_VALUE_WRITER_NAME = "valueWriterName";

    // ========================================================================
    // STRATEGY VALUES
    // These are valid values for the strategy detail keys
    // ========================================================================

    /**
     * ID strategy: Use a single designated ID field from the EClass.
     * <p>
     * The codec looks for an EAttribute marked as ID (via ecore:id="true")
     * or the first suitable attribute.
     * </p>
     */
    public static final String STRATEGY_ID_FIELD = "ID_FIELD";

    /**
     * ID strategy: Combine multiple feature values to create a composite ID.
     * <p>
     * Feature values are concatenated using the separator (default: "-").
     * Useful when no single field uniquely identifies an object.
     * </p>
     */
    public static final String STRATEGY_COMBINED = "COMBINED";

    /**
     * ID strategy: No ID serialization.
     * <p>
     * The object is serialized without an _id field. Use when identity
     * is not needed or is handled externally.
     * </p>
     */
    public static final String STRATEGY_NONE = "NONE";

    /**
     * Type strategy: Use the simple EClass name.
     * <p>
     * Produces compact output like {@code "_type": "Person"}.
     * Requires all class names to be unique within the context.
     * </p>
     */
    public static final String STRATEGY_NAME = "NAME";

    /**
     * Type strategy: Use the fully qualified Java class name.
     * <p>
     * Produces output like {@code "_type": "org.example.model.impl.PersonImpl"}.
     * Useful for Java-centric serialization.
     * </p>
     */
    public static final String STRATEGY_CLASS = "CLASS";

    /**
     * Type strategy: Use the EClass URI.
     * <p>
     * Produces output like {@code "_type": "http://example.org/model#//Person"}.
     * Most precise and portable across different runtime environments.
     * Enables deserialization without prior knowledge of the EPackage.
     * </p>
     */
    public static final String STRATEGY_URI = "URI";

    /**
     * Type strategy: Use a mapped discriminator value.
     * <p>
     * Instead of storing type information explicitly, uses an existing field
     * value to determine the concrete type. Configuration uses:
     * </p>
     * <ul>
     *   <li>{@link #KEY_TYPE_MAP_ID} - identifies the mapping context</li>
     *   <li>{@link #KEY_TYPE_DISCRIMINATOR_PATH} - which field contains the discriminator (base class)</li>
     *   <li>{@link #KEY_TYPE_DISCRIMINATOR} - the discriminator value (concrete classes)</li>
     *   <li>{@link #KEY_TYPE_DISCRIMINATOR_PREFIX} - static mappings (base class)</li>
     * </ul>
     * <p>
     * The MetadataService builds a reverse lookup map from discriminator values
     * to EClasses, enabling dynamic polymorphic deserialization.
     * </p>
     *
     * <h3>Example</h3>
     * <pre>{@code
     * <!-- Base class -->
     * <eAnnotations source="http://eclipse.org/fennec/codec">
     *   <details key="typeMapId" value="iot-sensors"/>
     *   <details key="typeKey" value="_type"/>
     * </eAnnotations>
     *
     * <!-- Concrete class -->
     * <eAnnotations source="http://eclipse.org/fennec/codec">
     *   <details key="typeMapId" value="iot-sensors"/>
     *   <details key="typeDiscriminator" value="temp-sensor"/>
     * </eAnnotations>
     * }</pre>
     */
    public static final String STRATEGY_MAPPED = "MAPPED";

    /**
     * Supertype strategy: Serialize all supertypes in the hierarchy.
     * <p>
     * For a class hierarchy A → B → C, serializing C would include [B, A].
     * This enables queries at any level of the hierarchy in document databases.
     * </p>
     */
    public static final String STRATEGY_SUPERTYPE_ALL = "ALL";

    /**
     * Supertype strategy: Serialize only the immediate (direct) supertype.
     * <p>
     * For a class hierarchy A → B → C, serializing C would only include [B].
     * More compact but limits querying to direct parents only.
     * </p>
     */
    public static final String STRATEGY_SUPERTYPE_SINGLE = "SINGLE";

    // ========================================================================
    // HELPER METHODS
    // ========================================================================

    /**
     * Checks if an annotation source is the codec source.
     *
     * @param source the annotation source to check
     * @return true if the source equals {@link #CODEC_SOURCE}
     */
    public static boolean isCodecAnnotation(String source) {
        return CODEC_SOURCE.equals(source);
    }

    /**
     * Extracts the discriminator value from a static type discriminator key.
     * <p>
     * For example, given {@code "typeDiscriminator.Dragino_LSE01"}, returns {@code "Dragino_LSE01"}.
     * </p>
     *
     * @param key the detail key (e.g., "typeDiscriminator.Dragino_LSE01")
     * @return the discriminator value, or null if the key is not a static discriminator key
     */
    public static String extractStaticDiscriminatorValue(String key) {
        if (key == null || !key.startsWith(KEY_TYPE_DISCRIMINATOR_PREFIX)) {
            return null;
        }
        String value = key.substring(KEY_TYPE_DISCRIMINATOR_PREFIX.length());
        return value.isEmpty() ? null : value;
    }
}
