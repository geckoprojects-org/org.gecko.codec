/**
 * Copyright (c) 2012 - 2023 Data In Motion and others.
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
package org.gecko.codec;

import java.util.Map;

import org.eclipse.emfcloud.jackson.module.EMFModule;
import org.gecko.codec.jackson.module.CodecFeature;

/**
 * Constant interface
 * @author Mark Hoffmann
 * @since 21.03.2023
 */
public interface CodecConstants {
	
	static final String ANNOTATION_NAMESPACE = "http://geckoprojects.org/codec/1.0.0";
	
	/** The properties key for a custom annotation source*/
	static final String ANNOTATION_PROPERTY_SOURCE_KEY = "codec.annotation.source";
	/** The properties key for a custom annotation source*/
	static final String ANNOTATION_PROPERTY_NAME_KEY = "codec.annotation.name";
	
	/** The codec annotation name key  */
	static final String ANNOTATION_NAME_KEY = "name";
	/** The codec annotation type key for an uri */
	static final String ANNOTATION_TYPE_KEY = "type";
	
	/** The {@link ExtendedMetaData} name key  */
	static final String EXTENDED_METADATA_NAME_KEY = "name";
	
	/** 
	 * The Object+ property to define a list of objects of features to be projected. All others are not projected. 
	 * If also {@link CodecConstants#PROJECTION_NOT_FEATURES_PROPERTY_KEY} is set. This property will evaluated first.
	 * After that {@link CodecConstants#PROJECTION_NOT_FEATURES_PROPERTY_KEY}, will evaluated based on the resulting entries   
	 */
	static final String PROJECTION_FEATURES_PROPERTY_KEY = "codec.projection";
	
	/** 
	 * The Object+ property to define a list of objects of features to be not projected, All others are projected.  
	 * If also {@link CodecConstants#PROJECTION_FEATURE_PPROPERTY_KEY} is set, that property will evaluated first.
	 * After that {@link CodecConstants#PROJECTION_NOT_FEATURES_PROPERTY_KEY}, will evaluated based on the resulting entries   
	 */
	static final String PROJECTION_NOT_FEATURES_PROPERTY_KEY = "codec.projection.not";

	/**
	 * If it is set to Boolean.TRUE and the ID was not specified in the URI, the value of the ID
	 * attribute will be used as the MongoDB _id if it exists.
	 * 
	 * Value type: Boolean, default is <code>true</code>
	 * @deprecated Use {@link CodecFeature#OPTION_ID_FEATURE_AS_PRIMARY_KEY} instead
	 */
	String OPTION_ID_FEATURE_AS_PRIMARY_KEY = "OPTION_ID_FEATURE_AS_PRIMARY_KEY";

	
	/**
	 * This option can be set to tell the serializer and de-serializer to use the enum literal instead of the default enum name.
	 * This property only work with generated enum that also implement {@link Enumerator}. Otherwise this property will be ignored.
	 * The {@link DBObjectBuilderImpl} and {@link EObjectBuilderImpl} are using this option
	 * 
	 * value type: Boolean, default is <code>false</code>
	 * @deprecated Use {@link CodecFeature#OPTION_ENCODE_ENUM_LITERAL} instead
	 */
	static final String ENCODE_ENUM_LITERAL = "ENCODE_ENUM_LITERAL";
	
	/**
	 * Returns <code>true</code>, if the {@link CodecConstants#ENCODE_ENUM_LITERAL}
	 * was set to <code>true</code>, otherwise <code>false</code>
	 * @param options the options
	 * @return <code>true</code>, if the option was set
	 */
	public static boolean isEncodeEnumLiteral(Map<?, ?> options) {
		if (options == null) {
			return false;
		}
		Object result = options.get(ENCODE_ENUM_LITERAL);
		return Boolean.TRUE.equals(result);
	}
	
	/**
	 * EMF's default serialization is designed to conserve space by not serializing attributes that
	 * are set to their default value. This is a problem when attempting to query objects by an
	 * attributes default value. By setting this option to Boolean.TRUE, all attribute values will be stored to
	 * MongoDB.
	 * 
	 * Value type: Boolean, default is <code>false</code>
	 * @deprecated Use {@link EMFModule.Feature#OPTION_SERIALIZE_DEFAULT_VALUE} instead
	 */
	static final String ENCODE_DEFAULT_VALUES = "ENCODE_DEFAULT_VALUES";

	/**
	 * To avoid writing unnecessary URIs in the DB, mongo emf writes eClassUris only for the root class and for 
	 * EReferences, where the actual value does not equal but inherit from the stated reference type. 
	 * default value. By setting this option to Boolean.TRUE, all eClass URIs will be written regardless. 
	 * 
	 * Value type: Boolean. default is <code>false</code>
	 * @deprecated Use {@link CodecFeature#OPTION_ENCODE_INHERITANCE_CLASSIFIERS} instead
	 */
	static final String ENCODE_INHERITANCE_CLASSIFIERS = "ENCODE_INHERITANCE_CLASSIFIERS";
	
	/**
	 * When you load an object with cross-document references, they will be proxies. When you access
	 * the reference, EMF will resolve the proxy and you can then access the attributes. This can
	 * cause
	 * performance problems for example when expanding a tree where you only need a name attribute to
	 * display the children and then only resolve the next child to be expanded. Setting this option
	 * to
	 * Boolean.TRUE will cause the proxy instance to have its attribute values populated so that you
	 * can display the child names in the tree without resolving the proxy.
	 * 
	 * Value type: Boolean
	 * @deprecated Use {@link CodecFeature#OPTION_DECODE_PROXY_ATTRIBUTES} instead
	 */
	static final String DECODE_PROXY_ATTRIBUTES = "DECODE_PROXY_ATTRIBUTES";
	
	/**
	 * This option may be used when you wish to provide the EClass type information as URI string via load or save option
	 * You will need that when e.g. loading data from a table that does not contain {@link EClass} type
	 * information. This option is useful whenever you are not able to provide a EClass instance, when e.g. using Java annotation values
	 * 
	 * Value type: {@link String}
	 */
	String ENCODE_TYPE = "ENCODE_TYPE";
	
	/**
	 * This option may be used when you wish to provide the EClass type information as URI string via load or save option
	 * You will need that when e.g. loading data from a table that does not contain {@link EClass} type
	 * information. This option is useful whenever you are not able to provide a EClass instance, when e.g. using Java annotation values
	 * 
	 * Value type: {@link String[]}
	 */
	String ENCODE_SUPERTYPES = "ENCODE_SUPERTYPE";

	String ENCODE_TIMESTAMP = "ENCODE_TIMESTAMP";
	
	/**
	 * This option may be used when you wish to customize serialization and/or de-serialization, using an
	 * alternative name for the EClass Uri type column. The default is {@link Keywords#ECLASS_TYPE_KEY}
	 * 
	 * Value type: {@link String}
	 */
	String KEY_CLASSIFIER_TYPE = "KEY_CLASSIFIER_TYPE";
	
	/**
	 * This option may be used when you wish to customize serialization and/or de-serialization, using an
	 * alternative name for the EClass Uri type column. The default is {@link Keywords#ECLASS_TYPE_KEY}
	 * 
	 * Value type: {@link String}
	 */
	/** Default feature name for a object-type feature */
	String KEY_CLASSIFIER_SUPERTYPE = "KEY_CLASSIFIER_SUPERTYPE";

	String KEY_FEATURE_TIMESTAMP = "KEY_FEATURE_TIMESTAMP";
	
	String KEY_FEATURE_ID = "KEY_FEATURE_ID";
	
	// FEATURE default keys
	
	/** Default feature name for a object-type feature */
	String FEATURE_TYPE_DEFAULT = "_type";
	
	/** Default feature name for a super-type feature */
	String FEATURE_CLASSIFIER_SUPERTYPE_DEFAULT = "_supertypes";
	
	/** Default feature name for a non-containment proxy feature */
	String FEATURE_PROXY_DEFAULT = "_proxy";
	
	/** Default feature name for a timestamp feature */
	String FEATURE_TIMESTAMP_DEFAULT = "_timestamp";
	
	/** Default feature name for an id feature */
	String FEATURE_IDFIELD_DEFAULT = "_id";

	/**
	 * Returns <code>true</code>, if the given key
	 * was set to <code>true</code> in the options map, otherwise <code>false</code>
	 * @param options the options
	 * @return <code>true</code>, if the option was set
	 */
	public static boolean isPropertyTrue(Map<?, ?> options, String key) {
		if (options == null || key == null) {
			return false;
		}
		return Boolean.TRUE.equals(options.get(key));
	}
	
}
