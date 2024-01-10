package org.gecko.codec.jackson.module;

/**
 * Enumeration that defines all possible options that can be used
 * to customize the behavior of the EMF Module.
 * @author Mark Hoffmann
 * @since 10.01.2024
 */
public enum CodecFeature {

	NONE(false),
	
    /**
     * Option used to indicate the module to use the default ID serializer if
     * none are provided. The ID serializer used by default is IdSerializer.
     */
    OPTION_USE_ID(false),

    /**
     * Option used to indicate the module to use the default type serializer if
     * none are provided. The type serializer used by default is ETypeSerializer.
     */
    OPTION_SERIALIZE_TYPE(true),

    /**
     * Option used to indicate the module to serialize default attributes values.
     * Default values are not serialized by default.
     */
    OPTION_SERIALIZE_DEFAULT_VALUE(false),

    /**
     * Option used to indicate whether feature names specified in
     * {@link org.eclipse.emf.ecore.util.ExtendedMetaData} annotations should
     * be respected.
     */
    OPTION_USE_NAMES_FROM_EXTENDED_META_DATA(true),

	/**
	 * When you load an object with cross-document references, they will be proxies. When you access
	 * the reference, EMF will resolve the proxy and you can then access the attributes. This can
	 * cause performance problems for example when expanding a tree where you only need a name 
	 * attribute to display the children and then only resolve the next child to be expanded. 
	 * Setting this option to Boolean.TRUE will cause the proxy instance to have its attribute 
	 * values populated so that you can display the child names in the tree without resolving the proxy.
	 * 
	 * Value type: Boolean
	 */
	OPTION_DECODE_PROXY_ATTRIBUTES(false),

	/**
	 * To avoid writing unnecessary URIs in the result format, we write eClassUris only for the root 
	 * class and for EReferences, where the actual value does not equal but inherit from the 
	 * stated reference type. 
	 * By setting this option to Boolean.TRUE, all eClass URIs will be written regardless. 
	 * 
	 */
	OPTION_ENCODE_INHERITANCE_CLASSIFIERS(false),
	
	/**
	 * If it is set to Boolean.TRUE and the ID was not specified in the URI, the value of the ID
	 * attribute will be used as the primary key if it exists.
	 */
	OPTION_ID_FEATURE_AS_PRIMARY_KEY(true),
	
	/**
	 * This option can be set to tell the serializer and de-serializer to use the enum literal instead of 
	 * the default enum name. This property only works with generated enums that 
	 * also implement {@link Enumerator}. Otherwise this property will be ignored.
	 */
	OPTION_ENCODE_ENUM_LITERAL(false);

	private final boolean defaultState;
	private final int mask;

	public static int collectDefaults() {
		int flags = 0;
		for (CodecFeature f : values()) {
			if (f.enabledByDefault()) {
				flags |= f.getMask();
			}
		}
		return flags;
	}

	CodecFeature(final Boolean defaultState) {
		this.defaultState = defaultState;
		this.mask = (1 << ordinal());
	}

	public boolean enabledIn(final int flags) {
		return (flags & mask) != 0;
	}

	public boolean enabledByDefault() {
		return defaultState;
	}

	public int getMask() { return mask; }
}