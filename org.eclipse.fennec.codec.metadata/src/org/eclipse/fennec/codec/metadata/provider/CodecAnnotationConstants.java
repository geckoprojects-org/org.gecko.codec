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
 * These constants define the annotation sources used in EMF models to configure
 * codec serialization and deserialization behavior. EAnnotations are the standard
 * EMF mechanism for attaching metadata to model elements (EPackage, EClass,
 * EStructuralFeature).
 * </p>
 *
 * <h2>How EAnnotations Work</h2>
 * <p>
 * An EAnnotation has a {@code source} (a URI-like string identifier) and a
 * {@code details} map (key-value pairs). The source identifies what type of
 * annotation it is, while details provide configuration values.
 * </p>
 *
 * <h2>Example in Ecore XML</h2>
 * <pre>{@code
 * <eClassifiers xsi:type="ecore:EClass" name="Person">
 *   <eAnnotations source="codec.id">
 *     <details key="strategy" value="ID_FIELD"/>
 *     <details key="key" value="_id"/>
 *   </eAnnotations>
 *   <eAnnotations source="codec.type">
 *     <details key="strategy" value="URI"/>
 *     <details key="typeKey" value="_type"/>
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
    // ANNOTATION SOURCES
    // These are the 'source' values used in EAnnotation elements
    // ========================================================================

    /**
     * Annotation source for inheritance control.
     * <p>
     * By default, codec annotations are only inherited within the same EPackage.
     * When this annotation is present on an EClass, codec annotations from the
     * direct parent EClass are inherited even if the parent belongs to a different
     * EPackage.
     * </p>
     *
     * <h3>Use Case</h3>
     * <p>
     * When you have a base class in a shared model (e.g., a common "Entity" class)
     * and concrete classes in domain-specific models that should inherit the
     * serialization configuration from the base class.
     * </p>
     *
     * <h3>Example</h3>
     * <pre>{@code
     * <!-- In domain model, inheriting from shared model's Entity class -->
     * <eClassifiers xsi:type="ecore:EClass" name="Customer" eSuperTypes="shared.ecore#//Entity">
     *   <eAnnotations source="codec.inherit"/>
     * </eClassifiers>
     * }</pre>
     */
    public static final String CODEC_INHERIT = "codec.inherit";

    /**
     * Annotation source for marking features as transient (not serialized).
     * <p>
     * When this annotation is present on an EStructuralFeature, the feature
     * is completely skipped during serialization. This is useful for:
     * </p>
     * <ul>
     *   <li>Computed/derived values that shouldn't be persisted</li>
     *   <li>Sensitive data that shouldn't be exposed</li>
     *   <li>Internal implementation details</li>
     *   <li>Circular references that would cause infinite loops</li>
     * </ul>
     *
     * <h3>Note</h3>
     * <p>
     * This annotation is model-level. For runtime override, use the
     * {@code CODEC_IGNORE_FEATURES_LIST} and {@code CODEC_IGNORE_NOT_FEATURES_LIST}
     * options when calling Resource.save() or Resource.load().
     * </p>
     *
     * <h3>Example</h3>
     * <pre>{@code
     * <eStructuralFeatures xsi:type="ecore:EAttribute" name="passwordHash">
     *   <eAnnotations source="codec.transient"/>
     * </eStructuralFeatures>
     * }</pre>
     */
    public static final String CODEC_TRANSIENT = "codec.transient";

    /**
     * Annotation source for identity (ID) configuration at the EClass level.
     * <p>
     * Configures how object identity is serialized. The ID appears as a special
     * field in the serialized output (typically "_id") and is used for:
     * </p>
     * <ul>
     *   <li>Database primary keys (e.g., MongoDB _id)</li>
     *   <li>Cross-references between objects</li>
     *   <li>Object lookup and caching</li>
     * </ul>
     *
     * <h3>Detail Keys</h3>
     * <ul>
     *   <li>{@link #KEY_KEY} - Property name in serialized output (default: "_id")</li>
     *   <li>{@link #KEY_STRATEGY} - How to determine the ID value:
     *       {@link #STRATEGY_ID_FIELD}, {@link #STRATEGY_COMBINED}, {@link #STRATEGY_NONE}</li>
     *   <li>{@link #KEY_SEPARATOR} - Separator for COMBINED strategy (default: "-")</li>
     *   <li>{@link #KEY_ID_FEATURES} - Comma-separated feature URIs for COMBINED strategy</li>
     *   <li>{@link #KEY_ID_VALUE_READER_NAME} - Custom reader for deserialization</li>
     *   <li>{@link #KEY_ID_VALUE_WRITER_NAME} - Custom writer for serialization</li>
     * </ul>
     *
     * <h3>Example</h3>
     * <pre>{@code
     * <eAnnotations source="codec.id">
     *   <details key="strategy" value="COMBINED"/>
     *   <details key="key" value="_id"/>
     *   <details key="separator" value="-"/>
     *   <details key="idFeatures" value="firstName,lastName"/>
     * </eAnnotations>
     * }</pre>
     */
    public static final String CODEC_ID = "codec.id";

    /**
     * Annotation source for supertype serialization configuration at the EClass level.
     * <p>
     * Configures how supertype information is serialized. When enabled, the type
     * hierarchy is stored alongside the object, enabling queries by interface or
     * superclass in document databases like MongoDB.
     * </p>
     *
     * <h3>Use Cases</h3>
     * <ul>
     *   <li>Query all objects implementing a specific interface</li>
     *   <li>Find all subclasses of a base class</li>
     *   <li>Create polymorphic indexes in MongoDB</li>
     * </ul>
     *
     * <h3>Detail Keys</h3>
     * <ul>
     *   <li>{@link #KEY_SUPERTYPE_SERIALIZE} - Whether to serialize supertypes (default: "false")</li>
     *   <li>{@link #KEY_SUPERTYPE_KEY} - Property name in serialized output (default: "_superTypes")</li>
     *   <li>{@link #KEY_SUPERTYPE_STRATEGY} - Which supertypes to include:
     *       {@link #STRATEGY_SUPERTYPE_ALL}, {@link #STRATEGY_SUPERTYPE_SINGLE}</li>
     *   <li>{@link #KEY_SUPERTYPE_AS_ARRAY} - Serialize as array or combined string (default: "true")</li>
     *   <li>{@link #KEY_SUPERTYPE_SEPARATOR} - Separator for combined string mode (default: ",")</li>
     *   <li>{@link #KEY_SUPERTYPE_WRITER_NAME} - Custom writer for supertype serialization</li>
     * </ul>
     *
     * <h3>Example - Basic Configuration</h3>
     * <pre>{@code
     * <eAnnotations source="codec.supertype">
     *   <details key="serialize" value="true"/>
     *   <details key="strategy" value="ALL"/>
     * </eAnnotations>
     * <!-- Produces: {"_superTypes": ["http://example.org#//Person", "http://example.org#//Entity"], ...} -->
     * }</pre>
     *
     * <h3>Example - Custom Key and Single Supertype</h3>
     * <pre>{@code
     * <eAnnotations source="codec.supertype">
     *   <details key="serialize" value="true"/>
     *   <details key="key" value="_parents"/>
     *   <details key="strategy" value="SINGLE"/>
     * </eAnnotations>
     * <!-- Produces: {"_parents": ["http://example.org#//Person"], ...} -->
     * }</pre>
     */
    public static final String CODEC_SUPERTYPE = "codec.supertype";

    /**
     * Annotation source for type information configuration.
     * <p>
     * Configures how type information is serialized and used for polymorphic
     * deserialization. This is crucial when the exact type of an object needs
     * to be preserved and restored, especially for:
     * </p>
     * <ul>
     *   <li>Abstract base classes with multiple concrete implementations</li>
     *   <li>Interfaces with multiple implementing classes</li>
     *   <li>Generic containers holding heterogeneous objects</li>
     * </ul>
     *
     * <p>
     * This annotation can be placed on:
     * </p>
     * <ul>
     *   <li><b>EClass:</b> Defines how instances of this class report their type</li>
     *   <li><b>EReference:</b> Defines how to resolve types for referenced objects</li>
     * </ul>
     *
     * <h3>Detail Keys</h3>
     * <ul>
     *   <li>{@link #KEY_STRATEGY} - Type serialization strategy:
     *       {@link #STRATEGY_NAME}, {@link #STRATEGY_CLASS}, {@link #STRATEGY_URI},
     *       {@link #STRATEGY_MAPPED}</li>
     *   <li>{@link #KEY_INCLUDE} - Whether to include type info (default: "true")</li>
     *   <li>{@link #KEY_TYPE_KEY} - Property name in serialized output (default: "_type")</li>
     *   <li>{@link #KEY_TYPE_VALUE_READER_NAME} - Custom reader for type deserialization</li>
     *   <li>{@link #KEY_TYPE_VALUE_WRITER_NAME} - Custom writer for type serialization</li>
     * </ul>
     *
     * <h3>Type Mapping</h3>
     * <p>
     * Additional key-value pairs in the details map serve as a type mapping table.
     * During deserialization, if the type value matches a key, the corresponding
     * value (an EClass name or URI) is used.
     * </p>
     *
     * <h3>Example - URI Strategy</h3>
     * <pre>{@code
     * <eAnnotations source="codec.type">
     *   <details key="strategy" value="URI"/>
     *   <details key="typeKey" value="_type"/>
     * </eAnnotations>
     * <!-- Produces: {"_type": "http://example.org/model#//Person", ...} -->
     * }</pre>
     *
     * <h3>MAPPED Strategy</h3>
     * <p>
     * For MAPPED type strategy, see {@link #CODEC_TYPE_PREFIX} which uses mapId-scoped
     * annotation sources like {@code codec.type.lorawan-dynamic}.
     * </p>
     */
    public static final String CODEC_TYPE = "codec.type";

    /**
     * Annotation source prefix for mapId-scoped type discrimination configuration.
     * <p>
     * When using the MAPPED type strategy, the mapId is embedded in the annotation
     * source itself following the pattern: {@code codec.type.{mapId}}
     * </p>
     *
     * <h3>MapId Concept</h3>
     * <p>
     * A mapId identifies a specific type discrimination context. Multiple mapIds
     * allow a class to participate in different type resolution scenarios. The
     * mapId defines:
     * </p>
     * <ul>
     *   <li>The feature path to the discriminator value ({@link #KEY_TYPE_KEY_FEATURE_PATH})</li>
     *   <li>Static type mappings ({@code typeDiscriminator.{value}})</li>
     *   <li>Dynamic type registration ({@link #KEY_TYPE_DISCRIMINATOR})</li>
     * </ul>
     *
     * <h3>Static Mode</h3>
     * <p>
     * Base class defines complete mapping table upfront:
     * </p>
     * <pre>{@code
     * <eClassifiers name="UplinkMessage" abstract="true">
     *   <eAnnotations source="codec.type.lorawan-static">
     *     <details key="typeKeyFeaturePath" value="deviceInfo.deviceProfileName"/>
     *     <details key="typeDiscriminator.Dragino_LSE01" value="https://eclipse.org/fennec/lorawan/dragino#//DraginoLSE01Uplink"/>
     *     <details key="typeDiscriminator.EM310-UDL" value="http://www.example.org/lorawan/specific/em310udl#//EM310UDLUplink"/>
     *   </eAnnotations>
     * </eClassifiers>
     * }</pre>
     *
     * <h3>Dynamic Mode</h3>
     * <p>
     * Base class defines feature path, concrete classes register themselves:
     * </p>
     * <pre>{@code
     * <!-- Base class -->
     * <eClassifiers name="UplinkMessage" abstract="true">
     *   <eAnnotations source="codec.type.lorawan-dynamic">
     *     <details key="typeKeyFeaturePath" value="deviceInfo.deviceProfileName"/>
     *   </eAnnotations>
     * </eClassifiers>
     *
     * <!-- Concrete class registers itself -->
     * <eClassifiers name="DraginoLSE01Uplink" eSuperTypes="#//UplinkMessage">
     *   <eAnnotations source="codec.type.lorawan-dynamic">
     *     <details key="typeDiscriminator" value="Dragino_LSE01"/>
     *   </eAnnotations>
     * </eClassifiers>
     * }</pre>
     *
     * <h3>Mixed Mode</h3>
     * <p>
     * Combines static and dynamic modes. Static mappings in base class can be
     * extended by concrete classes that also use the same mapId:
     * </p>
     * <pre>{@code
     * <!-- Base class with some static mappings -->
     * <eClassifiers name="UplinkMessage" abstract="true">
     *   <eAnnotations source="codec.type.lorawan-static">
     *     <details key="typeKeyFeaturePath" value="deviceInfo.deviceProfileName"/>
     *     <details key="typeDiscriminator.Dragino_LSE01" value="...#//DraginoLSE01Uplink"/>
     *   </eAnnotations>
     * </eClassifiers>
     *
     * <!-- Another model extends the same mapId dynamically -->
     * <eClassifiers name="BlubIOUplink" eSuperTypes="...#//UplinkMessage">
     *   <eAnnotations source="codec.type.lorawan-static">
     *     <details key="typeDiscriminator" value="BlubIO-01"/>
     *   </eAnnotations>
     * </eClassifiers>
     * }</pre>
     *
     * <h3>Multiple MapId Participation</h3>
     * <p>
     * A class can participate in multiple mapIds by having multiple annotations:
     * </p>
     * <pre>{@code
     * <eClassifiers name="DraginoLSE01Uplink">
     *   <eAnnotations source="codec.type.lorawan-static">
     *     <details key="typeDiscriminator" value="Dragino_LSE01"/>
     *   </eAnnotations>
     *   <eAnnotations source="codec.type.sensor-type">
     *     <details key="typeDiscriminator" value="SOIL"/>
     *   </eAnnotations>
     * </eClassifiers>
     * }</pre>
     * <p>
     * The order of annotations determines priority for fallback during decoding.
     * </p>
     *
     * @see #extractMapId(String) to extract mapId from annotation source
     */
    public static final String CODEC_TYPE_PREFIX = CODEC_TYPE + ".";

    /**
     * Annotation source for specifying a custom value writer at the feature level.
     * <p>
     * When this annotation is present on an EStructuralFeature, the named
     * CodecValueWriter is used to transform the feature value during serialization.
     * </p>
     *
     * <h3>Use Cases</h3>
     * <ul>
     *   <li>Custom date/time formatting</li>
     *   <li>Encryption of sensitive values</li>
     *   <li>Unit conversion (e.g., meters to feet)</li>
     *   <li>Enum to string mapping</li>
     * </ul>
     *
     * <h3>Example</h3>
     * <pre>{@code
     * <eStructuralFeatures xsi:type="ecore:EAttribute" name="birthDate" eType="ecore:EDataType ...#//EDate">
     *   <eAnnotations source="codec.value.writer.name">
     *     <details key="name" value="ISO8601DateWriter"/>
     *   </eAnnotations>
     * </eStructuralFeatures>
     * }</pre>
     *
     * <h3>Note</h3>
     * <p>
     * The actual CodecValueWriter implementation must be registered with the codec
     * or passed via Resource.save() options.
     * </p>
     */
    public static final String CODEC_VALUE_WRITER_NAME = "codec.value.writer.name";

    /**
     * Annotation source for specifying a custom value reader at the feature level.
     * <p>
     * When this annotation is present on an EStructuralFeature, the named
     * CodecValueReader is used to transform the serialized value during deserialization.
     * </p>
     *
     * <h3>Use Cases</h3>
     * <ul>
     *   <li>Parsing multiple date formats</li>
     *   <li>Decryption of sensitive values</li>
     *   <li>Legacy data format migration</li>
     *   <li>String to enum parsing with fallback</li>
     * </ul>
     *
     * <h3>Example</h3>
     * <pre>{@code
     * <eStructuralFeatures xsi:type="ecore:EAttribute" name="birthDate" eType="ecore:EDataType ...#//EDate">
     *   <eAnnotations source="codec.value.reader.name">
     *     <details key="name" value="FlexibleDateReader"/>
     *   </eAnnotations>
     * </eStructuralFeatures>
     * }</pre>
     *
     * <h3>Note</h3>
     * <p>
     * The actual CodecValueReader implementation must be registered with the codec
     * or passed via Resource.load() options.
     * </p>
     */
    public static final String CODEC_VALUE_READER_NAME = "codec.value.reader.name";

    // ========================================================================
    // DETAIL KEYS
    // These are the 'key' values used in EAnnotation details maps
    // ========================================================================

    /**
     * Detail key for specifying the property name in serialized output.
     * <p>
     * Used in {@link #CODEC_ID} annotation to set the JSON/XML property name
     * for the identity field. Default is "_id".
     * </p>
     */
    public static final String KEY_KEY = "key";

    /**
     * Detail key for specifying the serialization strategy.
     * <p>
     * Used in both {@link #CODEC_ID} and {@link #CODEC_TYPE} annotations to
     * define how the value should be serialized/deserialized.
     * </p>
     */
    public static final String KEY_STRATEGY = "strategy";

    /**
     * Detail key for specifying the separator in combined IDs.
     * <p>
     * Used in {@link #CODEC_ID} annotation when strategy is COMBINED.
     * Multiple feature values are joined using this separator.
     * Default is "-".
     * </p>
     */
    public static final String KEY_SEPARATOR = "separator";

    /**
     * Detail key for specifying features used in combined ID.
     * <p>
     * Used in {@link #CODEC_ID} annotation when strategy is COMBINED.
     * Value is a comma-separated list of feature names or URIs.
     * The order determines the order in the combined ID string.
     * </p>
     */
    public static final String KEY_ID_FEATURES = "idFeatures";

    /**
     * Detail key for specifying a custom ID value reader name.
     * <p>
     * Used in {@link #CODEC_ID} annotation to specify a CodecValueReader
     * for custom ID parsing during deserialization.
     * </p>
     */
    public static final String KEY_ID_VALUE_READER_NAME = "idValueReaderName";

    /**
     * Detail key for specifying a custom ID value writer name.
     * <p>
     * Used in {@link #CODEC_ID} annotation to specify a CodecValueWriter
     * for custom ID formatting during serialization.
     * </p>
     */
    public static final String KEY_ID_VALUE_WRITER_NAME = "idValueWriterName";

    /**
     * Detail key for specifying the type property name in serialized output.
     * <p>
     * Used in {@link #CODEC_TYPE} annotation to set the JSON/XML property name
     * for the type field. Default is "_type".
     * </p>
     */
    public static final String KEY_TYPE_KEY = "typeKey";

    /**
     * Detail key for specifying whether to include type information.
     * <p>
     * Used in {@link #CODEC_TYPE} annotation. When "false", type information
     * is not serialized. Default is "true".
     * </p>
     */
    public static final String KEY_INCLUDE = "include";

    /**
     * Detail key for specifying a custom type value reader name.
     * <p>
     * Used in {@link #CODEC_TYPE} annotation to specify a CodecValueReader
     * for custom type resolution during deserialization.
     * </p>
     */
    public static final String KEY_TYPE_VALUE_READER_NAME = "typeValueReaderName";

    /**
     * Detail key for specifying a custom type value writer name.
     * <p>
     * Used in {@link #CODEC_TYPE} annotation to specify a CodecValueWriter
     * for custom type formatting during serialization.
     * </p>
     */
    public static final String KEY_TYPE_VALUE_WRITER_NAME = "typeValueWriterName";

    /**
     * Detail key for specifying the feature path for MAPPED type strategy.
     * <p>
     * Used in {@link #CODEC_TYPE_PREFIX}{@code {mapId}} annotations at the base class
     * level to define which feature (or nested feature path like "deviceInfo.deviceProfileName")
     * contains the discriminator value for type resolution.
     * </p>
     *
     * <h3>Example</h3>
     * <pre>{@code
     * <eAnnotations source="codec.type.lorawan-dynamic">
     *   <details key="typeKeyFeaturePath" value="deviceInfo.deviceProfileName"/>
     * </eAnnotations>
     * }</pre>
     * <p>
     * With this configuration, the deserializer navigates to the nested field
     * and uses its value to look up the target EClass in the registry.
     * </p>
     */
    public static final String KEY_TYPE_KEY_FEATURE_PATH = "typeKeyFeaturePath";

    /**
     * Detail key for dynamic type discriminator registration.
     * <p>
     * Used in {@link #CODEC_TYPE_PREFIX}{@code {mapId}} annotations at the concrete
     * class level to register this EClass with a discriminator value. During
     * deserialization, when the value at typeKeyFeaturePath matches this value,
     * this EClass is instantiated.
     * </p>
     *
     * <h3>Example</h3>
     * <pre>{@code
     * <eAnnotations source="codec.type.lorawan-dynamic">
     *   <details key="typeDiscriminator" value="Dragino_LSE01"/>
     * </eAnnotations>
     * }</pre>
     * <p>
     * This registers DraginoLSE01Uplink class to be used when "Dragino_LSE01"
     * is found at the feature path defined by the base class.
     * </p>
     */
    public static final String KEY_TYPE_DISCRIMINATOR = "typeDiscriminator";

    /**
     * Detail key prefix for static type discriminator mappings.
     * <p>
     * Used in {@link #CODEC_TYPE_PREFIX}{@code {mapId}} annotations at the base class
     * level to define complete type mappings upfront. The discriminator value is
     * appended to this prefix as the key, and the EClass URI is the value.
     * </p>
     *
     * <h3>Example</h3>
     * <pre>{@code
     * <eAnnotations source="codec.type.lorawan-static">
     *   <details key="typeKeyFeaturePath" value="deviceInfo.deviceProfileName"/>
     *   <details key="typeDiscriminator.Dragino_LSE01" value="https://eclipse.org/fennec/lorawan/dragino#//DraginoLSE01Uplink"/>
     *   <details key="typeDiscriminator.EM310-UDL" value="http://www.example.org/lorawan/specific/em310udl#//EM310UDLUplink"/>
     * </eAnnotations>
     * }</pre>
     * <p>
     * This defines a complete mapping table where "Dragino_LSE01" maps to
     * DraginoLSE01Uplink and "EM310-UDL" maps to EM310UDLUplink.
     * </p>
     */
    public static final String KEY_TYPE_DISCRIMINATOR_PREFIX = KEY_TYPE_DISCRIMINATOR + ".";

    // ------------------------------------------------------------------------
    // Supertype annotation detail keys
    // ------------------------------------------------------------------------

    /**
     * Detail key for enabling/disabling supertype serialization.
     * <p>
     * Used in {@link #CODEC_SUPERTYPE} annotation. Value should be "true" or "false".
     * Default is "false".
     * </p>
     */
    public static final String KEY_SUPERTYPE_SERIALIZE = "serialize";

    /**
     * Detail key for specifying the supertype field name in serialized output.
     * <p>
     * Used in {@link #CODEC_SUPERTYPE} annotation to set the JSON/XML property name
     * for the supertype field. Default is "_superTypes".
     * </p>
     */
    public static final String KEY_SUPERTYPE_KEY = "key";

    /**
     * Detail key for specifying the supertype serialization strategy.
     * <p>
     * Used in {@link #CODEC_SUPERTYPE} annotation to define which supertypes
     * to include. Valid values are {@link #STRATEGY_SUPERTYPE_ALL} and
     * {@link #STRATEGY_SUPERTYPE_SINGLE}.
     * </p>
     */
    public static final String KEY_SUPERTYPE_STRATEGY = "strategy";

    /**
     * Detail key for specifying whether to serialize supertypes as an array.
     * <p>
     * Used in {@link #CODEC_SUPERTYPE} annotation. When "true", supertypes are
     * serialized as a JSON array. When "false", they are serialized as a
     * combined string using the separator. Default is "true".
     * </p>
     */
    public static final String KEY_SUPERTYPE_AS_ARRAY = "asArray";

    /**
     * Detail key for specifying the separator when serializing supertypes as a string.
     * <p>
     * Used in {@link #CODEC_SUPERTYPE} annotation when {@link #KEY_SUPERTYPE_AS_ARRAY}
     * is "false". Default is ",".
     * </p>
     */
    public static final String KEY_SUPERTYPE_SEPARATOR = "separator";

    /**
     * Detail key for specifying a custom supertype value writer name.
     * <p>
     * Used in {@link #CODEC_SUPERTYPE} annotation to specify a CodecValueWriter
     * for custom supertype formatting during serialization.
     * </p>
     */
    public static final String KEY_SUPERTYPE_WRITER_NAME = "superTypeWriterName";

    // ========================================================================
    // STRATEGY VALUES
    // These are valid values for the 'strategy' detail key
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

    /**
     * Type strategy: Use a mapped discriminator value.
     * <p>
     * Instead of storing type information explicitly, uses an existing field
     * value to determine the concrete type. Configuration uses mapId-scoped
     * annotations ({@link #CODEC_TYPE_PREFIX}{@code {mapId}}):
     * </p>
     * <ul>
     *   <li>Base class: defines {@link #KEY_TYPE_KEY_FEATURE_PATH} - which field to look at</li>
     *   <li>Concrete classes: define {@link #KEY_TYPE_DISCRIMINATOR} - what value maps to them</li>
     *   <li>Static mode: base class defines {@link #KEY_TYPE_DISCRIMINATOR_PREFIX}{@code {value}} mappings</li>
     * </ul>
     * <p>
     * The ModelInfoService builds a reverse lookup map from discriminator values
     * to EClasses, enabling dynamic polymorphic deserialization.
     * </p>
     *
     * <h3>Advantages</h3>
     * <ul>
     *   <li>No additional type field needed in JSON</li>
     *   <li>Works with existing data formats that use discriminator patterns</li>
     *   <li>Easy to add new types without changing serialization format</li>
     *   <li>Supports multiple mapIds for different type resolution contexts</li>
     * </ul>
     *
     * @see #CODEC_TYPE_PREFIX for detailed usage examples
     */
    public static final String STRATEGY_MAPPED = "MAPPED";

    // ========================================================================
    // HELPER METHODS
    // ========================================================================

    /**
     * Checks if an annotation source represents a mapId-scoped type annotation.
     *
     * @param source the annotation source to check
     * @return true if the source starts with {@link #CODEC_TYPE_PREFIX}
     */
    public static boolean isTypeMapAnnotation(String source) {
        return source != null && source.startsWith(CODEC_TYPE_PREFIX);
    }

    /**
     * Extracts the mapId from a mapId-scoped type annotation source.
     * <p>
     * For example, given {@code "codec.type.lorawan-dynamic"}, returns {@code "lorawan-dynamic"}.
     * </p>
     *
     * @param source the annotation source (e.g., "codec.type.lorawan-dynamic")
     * @return the mapId, or null if the source is not a valid mapId-scoped annotation
     */
    public static String extractMapId(String source) {
        if (!isTypeMapAnnotation(source)) {
            return null;
        }
        String mapId = source.substring(CODEC_TYPE_PREFIX.length());
        return mapId.isEmpty() ? null : mapId;
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
