/*
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
 * 
 */
package org.eclipse.fennec.codec.metadata.model.codec;


import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.fennec.model.metadata.MetadataPackage;

import org.gecko.emf.osgi.annotation.provide.EPackage;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.fennec.codec.metadata.model.codec.CodecFactory
 * @model kind="package"
 *        annotation="Version value='1.0'"
 * @generated
 */
@ProviderType
@EPackage(uri = CodecPackage.eNS_URI, genModel = "/model/codec.genmodel", genModelSourceLocations = {"model/codec.genmodel","org.eclipse.fennec.codec.metadata/model/codec.genmodel"}, ecore="/model/codec.ecore", ecoreSourceLocations="/model/codec.ecore")
public interface CodecPackage extends org.eclipse.emf.ecore.EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "codec";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "https://eclipse.org/fennec/codec/1.0.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "codec";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CodecPackage eINSTANCE = org.eclipse.fennec.codec.metadata.model.codec.impl.CodecPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.codec.metadata.model.codec.impl.TypeSerializationConfigImpl <em>Type Serialization Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.codec.metadata.model.codec.impl.TypeSerializationConfigImpl
	 * @see org.eclipse.fennec.codec.metadata.model.codec.impl.CodecPackageImpl#getTypeSerializationConfig()
	 * @generated
	 */
	int TYPE_SERIALIZATION_CONFIG = 0;

	/**
	 * The feature id for the '<em><b>Strategy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_SERIALIZATION_CONFIG__STRATEGY = MetadataPackage.BASE_TYPE_CONFIG__STRATEGY;

	/**
	 * The feature id for the '<em><b>Include</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_SERIALIZATION_CONFIG__INCLUDE = MetadataPackage.BASE_TYPE_CONFIG__INCLUDE;

	/**
	 * The feature id for the '<em><b>Type Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_SERIALIZATION_CONFIG__TYPE_KEY = MetadataPackage.BASE_TYPE_CONFIG__TYPE_KEY;

	/**
	 * The feature id for the '<em><b>Schema Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_SERIALIZATION_CONFIG__SCHEMA_KEY = MetadataPackage.BASE_TYPE_CONFIG__SCHEMA_KEY;

	/**
	 * The feature id for the '<em><b>Name Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_SERIALIZATION_CONFIG__NAME_KEY = MetadataPackage.BASE_TYPE_CONFIG__NAME_KEY;

	/**
	 * The feature id for the '<em><b>Discriminator Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_SERIALIZATION_CONFIG__DISCRIMINATOR_PATH = MetadataPackage.BASE_TYPE_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Discriminator Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_SERIALIZATION_CONFIG__DISCRIMINATOR_VALUE = MetadataPackage.BASE_TYPE_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Type Serialization Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_SERIALIZATION_CONFIG_FEATURE_COUNT = MetadataPackage.BASE_TYPE_CONFIG_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Type Serialization Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_SERIALIZATION_CONFIG_OPERATION_COUNT = MetadataPackage.BASE_TYPE_CONFIG_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.codec.metadata.model.codec.impl.IdSerializationConfigImpl <em>Id Serialization Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.codec.metadata.model.codec.impl.IdSerializationConfigImpl
	 * @see org.eclipse.fennec.codec.metadata.model.codec.impl.CodecPackageImpl#getIdSerializationConfig()
	 * @generated
	 */
	int ID_SERIALIZATION_CONFIG = 1;

	/**
	 * The feature id for the '<em><b>Strategy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ID_SERIALIZATION_CONFIG__STRATEGY = MetadataPackage.BASE_ID_CONFIG__STRATEGY;

	/**
	 * The feature id for the '<em><b>Key Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ID_SERIALIZATION_CONFIG__KEY_MODE = MetadataPackage.BASE_ID_CONFIG__KEY_MODE;

	/**
	 * The feature id for the '<em><b>Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ID_SERIALIZATION_CONFIG__FORMAT = MetadataPackage.BASE_ID_CONFIG__FORMAT;

	/**
	 * The feature id for the '<em><b>Id Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ID_SERIALIZATION_CONFIG__ID_KEY = MetadataPackage.BASE_ID_CONFIG__ID_KEY;

	/**
	 * The feature id for the '<em><b>Separator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ID_SERIALIZATION_CONFIG__SEPARATOR = MetadataPackage.BASE_ID_CONFIG__SEPARATOR;

	/**
	 * The feature id for the '<em><b>Id Features</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ID_SERIALIZATION_CONFIG__ID_FEATURES = MetadataPackage.BASE_ID_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Id Value Writer Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ID_SERIALIZATION_CONFIG__ID_VALUE_WRITER_NAME = MetadataPackage.BASE_ID_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Id Value Reader Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ID_SERIALIZATION_CONFIG__ID_VALUE_READER_NAME = MetadataPackage.BASE_ID_CONFIG_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Id Serialization Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ID_SERIALIZATION_CONFIG_FEATURE_COUNT = MetadataPackage.BASE_ID_CONFIG_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Id Serialization Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ID_SERIALIZATION_CONFIG_OPERATION_COUNT = MetadataPackage.BASE_ID_CONFIG_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.codec.metadata.model.codec.impl.ReferenceSerializationConfigImpl <em>Reference Serialization Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.codec.metadata.model.codec.impl.ReferenceSerializationConfigImpl
	 * @see org.eclipse.fennec.codec.metadata.model.codec.impl.CodecPackageImpl#getReferenceSerializationConfig()
	 * @generated
	 */
	int REFERENCE_SERIALIZATION_CONFIG = 2;

	/**
	 * The feature id for the '<em><b>Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_SERIALIZATION_CONFIG__FORMAT = MetadataPackage.BASE_REFERENCE_CONFIG__FORMAT;

	/**
	 * The feature id for the '<em><b>Type Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_SERIALIZATION_CONFIG__TYPE_KEY = MetadataPackage.BASE_REFERENCE_CONFIG__TYPE_KEY;

	/**
	 * The feature id for the '<em><b>Ref Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_SERIALIZATION_CONFIG__REF_KEY = MetadataPackage.BASE_REFERENCE_CONFIG__REF_KEY;

	/**
	 * The feature id for the '<em><b>Include Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_SERIALIZATION_CONFIG__INCLUDE_TYPE = MetadataPackage.BASE_REFERENCE_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Expand</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_SERIALIZATION_CONFIG__EXPAND = MetadataPackage.BASE_REFERENCE_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Reference Serialization Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_SERIALIZATION_CONFIG_FEATURE_COUNT = MetadataPackage.BASE_REFERENCE_CONFIG_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Reference Serialization Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_SERIALIZATION_CONFIG_OPERATION_COUNT = MetadataPackage.BASE_REFERENCE_CONFIG_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.codec.metadata.model.codec.impl.SuperTypeSerializationConfigImpl <em>Super Type Serialization Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.codec.metadata.model.codec.impl.SuperTypeSerializationConfigImpl
	 * @see org.eclipse.fennec.codec.metadata.model.codec.impl.CodecPackageImpl#getSuperTypeSerializationConfig()
	 * @generated
	 */
	int SUPER_TYPE_SERIALIZATION_CONFIG = 3;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUPER_TYPE_SERIALIZATION_CONFIG__ENABLED = MetadataPackage.BASE_SUPER_TYPE_CONFIG__ENABLED;

	/**
	 * The feature id for the '<em><b>Selection</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUPER_TYPE_SERIALIZATION_CONFIG__SELECTION = MetadataPackage.BASE_SUPER_TYPE_CONFIG__SELECTION;

	/**
	 * The feature id for the '<em><b>Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUPER_TYPE_SERIALIZATION_CONFIG__FORMAT = MetadataPackage.BASE_SUPER_TYPE_CONFIG__FORMAT;

	/**
	 * The feature id for the '<em><b>As Array</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUPER_TYPE_SERIALIZATION_CONFIG__AS_ARRAY = MetadataPackage.BASE_SUPER_TYPE_CONFIG__AS_ARRAY;

	/**
	 * The feature id for the '<em><b>Separator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUPER_TYPE_SERIALIZATION_CONFIG__SEPARATOR = MetadataPackage.BASE_SUPER_TYPE_CONFIG__SEPARATOR;

	/**
	 * The feature id for the '<em><b>Super Type Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUPER_TYPE_SERIALIZATION_CONFIG__SUPER_TYPE_KEY = MetadataPackage.BASE_SUPER_TYPE_CONFIG__SUPER_TYPE_KEY;

	/**
	 * The feature id for the '<em><b>Schema Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUPER_TYPE_SERIALIZATION_CONFIG__SCHEMA_KEY = MetadataPackage.BASE_SUPER_TYPE_CONFIG__SCHEMA_KEY;

	/**
	 * The feature id for the '<em><b>Name Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUPER_TYPE_SERIALIZATION_CONFIG__NAME_KEY = MetadataPackage.BASE_SUPER_TYPE_CONFIG__NAME_KEY;

	/**
	 * The feature id for the '<em><b>Use Smart Compression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUPER_TYPE_SERIALIZATION_CONFIG__USE_SMART_COMPRESSION = MetadataPackage.BASE_SUPER_TYPE_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Super Type Serialization Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUPER_TYPE_SERIALIZATION_CONFIG_FEATURE_COUNT = MetadataPackage.BASE_SUPER_TYPE_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Super Type Serialization Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUPER_TYPE_SERIALIZATION_CONFIG_OPERATION_COUNT = MetadataPackage.BASE_SUPER_TYPE_CONFIG_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.codec.metadata.model.codec.impl.FeatureSerializationConfigImpl <em>Feature Serialization Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.codec.metadata.model.codec.impl.FeatureSerializationConfigImpl
	 * @see org.eclipse.fennec.codec.metadata.model.codec.impl.CodecPackageImpl#getFeatureSerializationConfig()
	 * @generated
	 */
	int FEATURE_SERIALIZATION_CONFIG = 4;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_SERIALIZATION_CONFIG__KEY = MetadataPackage.BASE_FEATURE_CONFIG__KEY;

	/**
	 * The feature id for the '<em><b>Serialize</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_SERIALIZATION_CONFIG__SERIALIZE = MetadataPackage.BASE_FEATURE_CONFIG__SERIALIZE;

	/**
	 * The feature id for the '<em><b>Serialize Null</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_SERIALIZATION_CONFIG__SERIALIZE_NULL = MetadataPackage.BASE_FEATURE_CONFIG__SERIALIZE_NULL;

	/**
	 * The feature id for the '<em><b>Serialize Empty</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_SERIALIZATION_CONFIG__SERIALIZE_EMPTY = MetadataPackage.BASE_FEATURE_CONFIG__SERIALIZE_EMPTY;

	/**
	 * The feature id for the '<em><b>Serialize Defaults</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_SERIALIZATION_CONFIG__SERIALIZE_DEFAULTS = MetadataPackage.BASE_FEATURE_CONFIG__SERIALIZE_DEFAULTS;

	/**
	 * The feature id for the '<em><b>Enum Serialization</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_SERIALIZATION_CONFIG__ENUM_SERIALIZATION = MetadataPackage.BASE_FEATURE_CONFIG__ENUM_SERIALIZATION;

	/**
	 * The feature id for the '<em><b>Feature Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_SERIALIZATION_CONFIG__FEATURE_NAME = MetadataPackage.BASE_FEATURE_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Value Writer Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_SERIALIZATION_CONFIG__VALUE_WRITER_NAME = MetadataPackage.BASE_FEATURE_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Value Reader Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_SERIALIZATION_CONFIG__VALUE_READER_NAME = MetadataPackage.BASE_FEATURE_CONFIG_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Expand</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_SERIALIZATION_CONFIG__EXPAND = MetadataPackage.BASE_FEATURE_CONFIG_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Reference Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_SERIALIZATION_CONFIG__REFERENCE_CONFIG = MetadataPackage.BASE_FEATURE_CONFIG_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Type Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_SERIALIZATION_CONFIG__TYPE_CONFIG = MetadataPackage.BASE_FEATURE_CONFIG_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Feature Serialization Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_SERIALIZATION_CONFIG_FEATURE_COUNT = MetadataPackage.BASE_FEATURE_CONFIG_FEATURE_COUNT + 6;

	/**
	 * The number of operations of the '<em>Feature Serialization Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_SERIALIZATION_CONFIG_OPERATION_COUNT = MetadataPackage.BASE_FEATURE_CONFIG_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.codec.metadata.model.codec.impl.ClassCodecAspectImpl <em>Class Codec Aspect</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.codec.metadata.model.codec.impl.ClassCodecAspectImpl
	 * @see org.eclipse.fennec.codec.metadata.model.codec.impl.CodecPackageImpl#getClassCodecAspect()
	 * @generated
	 */
	int CLASS_CODEC_ASPECT = 5;

	/**
	 * The feature id for the '<em><b>Type Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_CODEC_ASPECT__TYPE_ID = MetadataPackage.CLASS_ASPECT__TYPE_ID;

	/**
	 * The feature id for the '<em><b>Type Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_CODEC_ASPECT__TYPE_CONFIG = MetadataPackage.CLASS_ASPECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Id Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_CODEC_ASPECT__ID_CONFIG = MetadataPackage.CLASS_ASPECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Super Type Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_CODEC_ASPECT__SUPER_TYPE_CONFIG = MetadataPackage.CLASS_ASPECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Inherit From Parent</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_CODEC_ASPECT__INHERIT_FROM_PARENT = MetadataPackage.CLASS_ASPECT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Discriminator Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_CODEC_ASPECT__DISCRIMINATOR_VALUE = MetadataPackage.CLASS_ASPECT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Class Codec Aspect</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_CODEC_ASPECT_FEATURE_COUNT = MetadataPackage.CLASS_ASPECT_FEATURE_COUNT + 5;

	/**
	 * The number of operations of the '<em>Class Codec Aspect</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_CODEC_ASPECT_OPERATION_COUNT = MetadataPackage.CLASS_ASPECT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.codec.metadata.model.codec.impl.FeatureCodecAspectImpl <em>Feature Codec Aspect</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.codec.metadata.model.codec.impl.FeatureCodecAspectImpl
	 * @see org.eclipse.fennec.codec.metadata.model.codec.impl.CodecPackageImpl#getFeatureCodecAspect()
	 * @generated
	 */
	int FEATURE_CODEC_ASPECT = 6;

	/**
	 * The feature id for the '<em><b>Type Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_CODEC_ASPECT__TYPE_ID = MetadataPackage.FEATURE_ASPECT__TYPE_ID;

	/**
	 * The feature id for the '<em><b>Effective Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_CODEC_ASPECT__EFFECTIVE_KEY = MetadataPackage.FEATURE_ASPECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Serialize</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_CODEC_ASPECT__SERIALIZE = MetadataPackage.FEATURE_ASPECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Serialize Null</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_CODEC_ASPECT__SERIALIZE_NULL = MetadataPackage.FEATURE_ASPECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Serialize Empty</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_CODEC_ASPECT__SERIALIZE_EMPTY = MetadataPackage.FEATURE_ASPECT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Serialize Defaults</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_CODEC_ASPECT__SERIALIZE_DEFAULTS = MetadataPackage.FEATURE_ASPECT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Value Writer Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_CODEC_ASPECT__VALUE_WRITER_NAME = MetadataPackage.FEATURE_ASPECT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Value Reader Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_CODEC_ASPECT__VALUE_READER_NAME = MetadataPackage.FEATURE_ASPECT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Enum Serialization</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_CODEC_ASPECT__ENUM_SERIALIZATION = MetadataPackage.FEATURE_ASPECT_FEATURE_COUNT + 7;

	/**
	 * The number of structural features of the '<em>Feature Codec Aspect</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_CODEC_ASPECT_FEATURE_COUNT = MetadataPackage.FEATURE_ASPECT_FEATURE_COUNT + 8;

	/**
	 * The number of operations of the '<em>Feature Codec Aspect</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_CODEC_ASPECT_OPERATION_COUNT = MetadataPackage.FEATURE_ASPECT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.codec.metadata.model.codec.impl.ReferenceCodecAspectImpl <em>Reference Codec Aspect</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.codec.metadata.model.codec.impl.ReferenceCodecAspectImpl
	 * @see org.eclipse.fennec.codec.metadata.model.codec.impl.CodecPackageImpl#getReferenceCodecAspect()
	 * @generated
	 */
	int REFERENCE_CODEC_ASPECT = 7;

	/**
	 * The feature id for the '<em><b>Type Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_CODEC_ASPECT__TYPE_ID = FEATURE_CODEC_ASPECT__TYPE_ID;

	/**
	 * The feature id for the '<em><b>Effective Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_CODEC_ASPECT__EFFECTIVE_KEY = FEATURE_CODEC_ASPECT__EFFECTIVE_KEY;

	/**
	 * The feature id for the '<em><b>Serialize</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_CODEC_ASPECT__SERIALIZE = FEATURE_CODEC_ASPECT__SERIALIZE;

	/**
	 * The feature id for the '<em><b>Serialize Null</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_CODEC_ASPECT__SERIALIZE_NULL = FEATURE_CODEC_ASPECT__SERIALIZE_NULL;

	/**
	 * The feature id for the '<em><b>Serialize Empty</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_CODEC_ASPECT__SERIALIZE_EMPTY = FEATURE_CODEC_ASPECT__SERIALIZE_EMPTY;

	/**
	 * The feature id for the '<em><b>Serialize Defaults</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_CODEC_ASPECT__SERIALIZE_DEFAULTS = FEATURE_CODEC_ASPECT__SERIALIZE_DEFAULTS;

	/**
	 * The feature id for the '<em><b>Value Writer Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_CODEC_ASPECT__VALUE_WRITER_NAME = FEATURE_CODEC_ASPECT__VALUE_WRITER_NAME;

	/**
	 * The feature id for the '<em><b>Value Reader Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_CODEC_ASPECT__VALUE_READER_NAME = FEATURE_CODEC_ASPECT__VALUE_READER_NAME;

	/**
	 * The feature id for the '<em><b>Enum Serialization</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_CODEC_ASPECT__ENUM_SERIALIZATION = FEATURE_CODEC_ASPECT__ENUM_SERIALIZATION;

	/**
	 * The feature id for the '<em><b>Reference Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_CODEC_ASPECT__REFERENCE_CONFIG = FEATURE_CODEC_ASPECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Type Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_CODEC_ASPECT__TYPE_CONFIG = FEATURE_CODEC_ASPECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Inherit Type From Target</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_CODEC_ASPECT__INHERIT_TYPE_FROM_TARGET = FEATURE_CODEC_ASPECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Expand</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_CODEC_ASPECT__EXPAND = FEATURE_CODEC_ASPECT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Reference Codec Aspect</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_CODEC_ASPECT_FEATURE_COUNT = FEATURE_CODEC_ASPECT_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Reference Codec Aspect</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_CODEC_ASPECT_OPERATION_COUNT = FEATURE_CODEC_ASPECT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.codec.metadata.model.codec.impl.CodecConfigImpl <em>Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.codec.metadata.model.codec.impl.CodecConfigImpl
	 * @see org.eclipse.fennec.codec.metadata.model.codec.impl.CodecPackageImpl#getCodecConfig()
	 * @generated
	 */
	int CODEC_CONFIG = 8;

	/**
	 * The feature id for the '<em><b>Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CODEC_CONFIG__FORMAT = 0;

	/**
	 * The feature id for the '<em><b>Use Numeric Ids</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CODEC_CONFIG__USE_NUMERIC_IDS = 1;

	/**
	 * The feature id for the '<em><b>Type Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CODEC_CONFIG__TYPE_CONFIG = 2;

	/**
	 * The feature id for the '<em><b>Containment Type Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CODEC_CONFIG__CONTAINMENT_TYPE_CONFIG = 3;

	/**
	 * The feature id for the '<em><b>Reference Type Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CODEC_CONFIG__REFERENCE_TYPE_CONFIG = 4;

	/**
	 * The feature id for the '<em><b>Id Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CODEC_CONFIG__ID_CONFIG = 5;

	/**
	 * The feature id for the '<em><b>Reference Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CODEC_CONFIG__REFERENCE_CONFIG = 6;

	/**
	 * The feature id for the '<em><b>Super Type Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CODEC_CONFIG__SUPER_TYPE_CONFIG = 7;

	/**
	 * The feature id for the '<em><b>Feature Configs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CODEC_CONFIG__FEATURE_CONFIGS = 8;

	/**
	 * The feature id for the '<em><b>Expand</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CODEC_CONFIG__EXPAND = 9;

	/**
	 * The feature id for the '<em><b>Expand Depth</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CODEC_CONFIG__EXPAND_DEPTH = 10;

	/**
	 * The feature id for the '<em><b>Expand Ignore Bidirectional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CODEC_CONFIG__EXPAND_IGNORE_BIDIRECTIONAL = 11;

	/**
	 * The feature id for the '<em><b>Serialize Null</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CODEC_CONFIG__SERIALIZE_NULL = 12;

	/**
	 * The feature id for the '<em><b>Serialize Empty</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CODEC_CONFIG__SERIALIZE_EMPTY = 13;

	/**
	 * The feature id for the '<em><b>Serialize Defaults</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CODEC_CONFIG__SERIALIZE_DEFAULTS = 14;

	/**
	 * The number of structural features of the '<em>Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CODEC_CONFIG_FEATURE_COUNT = 15;

	/**
	 * The number of operations of the '<em>Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CODEC_CONFIG_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.codec.metadata.model.codec.TypeSerializationConfig <em>Type Serialization Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type Serialization Config</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.TypeSerializationConfig
	 * @generated
	 */
	EClass getTypeSerializationConfig();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.codec.metadata.model.codec.TypeSerializationConfig#getDiscriminatorPath <em>Discriminator Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Discriminator Path</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.TypeSerializationConfig#getDiscriminatorPath()
	 * @see #getTypeSerializationConfig()
	 * @generated
	 */
	EAttribute getTypeSerializationConfig_DiscriminatorPath();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.codec.metadata.model.codec.TypeSerializationConfig#getDiscriminatorValue <em>Discriminator Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Discriminator Value</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.TypeSerializationConfig#getDiscriminatorValue()
	 * @see #getTypeSerializationConfig()
	 * @generated
	 */
	EAttribute getTypeSerializationConfig_DiscriminatorValue();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.codec.metadata.model.codec.IdSerializationConfig <em>Id Serialization Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Id Serialization Config</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.IdSerializationConfig
	 * @generated
	 */
	EClass getIdSerializationConfig();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.fennec.codec.metadata.model.codec.IdSerializationConfig#getIdFeatures <em>Id Features</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Id Features</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.IdSerializationConfig#getIdFeatures()
	 * @see #getIdSerializationConfig()
	 * @generated
	 */
	EAttribute getIdSerializationConfig_IdFeatures();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.codec.metadata.model.codec.IdSerializationConfig#getIdValueWriterName <em>Id Value Writer Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id Value Writer Name</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.IdSerializationConfig#getIdValueWriterName()
	 * @see #getIdSerializationConfig()
	 * @generated
	 */
	EAttribute getIdSerializationConfig_IdValueWriterName();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.codec.metadata.model.codec.IdSerializationConfig#getIdValueReaderName <em>Id Value Reader Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id Value Reader Name</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.IdSerializationConfig#getIdValueReaderName()
	 * @see #getIdSerializationConfig()
	 * @generated
	 */
	EAttribute getIdSerializationConfig_IdValueReaderName();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.codec.metadata.model.codec.ReferenceSerializationConfig <em>Reference Serialization Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Reference Serialization Config</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.ReferenceSerializationConfig
	 * @generated
	 */
	EClass getReferenceSerializationConfig();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.codec.metadata.model.codec.ReferenceSerializationConfig#isIncludeType <em>Include Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Include Type</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.ReferenceSerializationConfig#isIncludeType()
	 * @see #getReferenceSerializationConfig()
	 * @generated
	 */
	EAttribute getReferenceSerializationConfig_IncludeType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.codec.metadata.model.codec.ReferenceSerializationConfig#isExpand <em>Expand</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Expand</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.ReferenceSerializationConfig#isExpand()
	 * @see #getReferenceSerializationConfig()
	 * @generated
	 */
	EAttribute getReferenceSerializationConfig_Expand();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.codec.metadata.model.codec.SuperTypeSerializationConfig <em>Super Type Serialization Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Super Type Serialization Config</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.SuperTypeSerializationConfig
	 * @generated
	 */
	EClass getSuperTypeSerializationConfig();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.codec.metadata.model.codec.SuperTypeSerializationConfig#isUseSmartCompression <em>Use Smart Compression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Use Smart Compression</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.SuperTypeSerializationConfig#isUseSmartCompression()
	 * @see #getSuperTypeSerializationConfig()
	 * @generated
	 */
	EAttribute getSuperTypeSerializationConfig_UseSmartCompression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.codec.metadata.model.codec.FeatureSerializationConfig <em>Feature Serialization Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Feature Serialization Config</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.FeatureSerializationConfig
	 * @generated
	 */
	EClass getFeatureSerializationConfig();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.codec.metadata.model.codec.FeatureSerializationConfig#getFeatureName <em>Feature Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Feature Name</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.FeatureSerializationConfig#getFeatureName()
	 * @see #getFeatureSerializationConfig()
	 * @generated
	 */
	EAttribute getFeatureSerializationConfig_FeatureName();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.codec.metadata.model.codec.FeatureSerializationConfig#getValueWriterName <em>Value Writer Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value Writer Name</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.FeatureSerializationConfig#getValueWriterName()
	 * @see #getFeatureSerializationConfig()
	 * @generated
	 */
	EAttribute getFeatureSerializationConfig_ValueWriterName();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.codec.metadata.model.codec.FeatureSerializationConfig#getValueReaderName <em>Value Reader Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value Reader Name</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.FeatureSerializationConfig#getValueReaderName()
	 * @see #getFeatureSerializationConfig()
	 * @generated
	 */
	EAttribute getFeatureSerializationConfig_ValueReaderName();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.codec.metadata.model.codec.FeatureSerializationConfig#getExpand <em>Expand</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Expand</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.FeatureSerializationConfig#getExpand()
	 * @see #getFeatureSerializationConfig()
	 * @generated
	 */
	EAttribute getFeatureSerializationConfig_Expand();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fennec.codec.metadata.model.codec.FeatureSerializationConfig#getReferenceConfig <em>Reference Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Reference Config</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.FeatureSerializationConfig#getReferenceConfig()
	 * @see #getFeatureSerializationConfig()
	 * @generated
	 */
	EReference getFeatureSerializationConfig_ReferenceConfig();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fennec.codec.metadata.model.codec.FeatureSerializationConfig#getTypeConfig <em>Type Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Type Config</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.FeatureSerializationConfig#getTypeConfig()
	 * @see #getFeatureSerializationConfig()
	 * @generated
	 */
	EReference getFeatureSerializationConfig_TypeConfig();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.codec.metadata.model.codec.ClassCodecAspect <em>Class Codec Aspect</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Class Codec Aspect</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.ClassCodecAspect
	 * @generated
	 */
	EClass getClassCodecAspect();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fennec.codec.metadata.model.codec.ClassCodecAspect#getTypeConfig <em>Type Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Type Config</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.ClassCodecAspect#getTypeConfig()
	 * @see #getClassCodecAspect()
	 * @generated
	 */
	EReference getClassCodecAspect_TypeConfig();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fennec.codec.metadata.model.codec.ClassCodecAspect#getIdConfig <em>Id Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Id Config</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.ClassCodecAspect#getIdConfig()
	 * @see #getClassCodecAspect()
	 * @generated
	 */
	EReference getClassCodecAspect_IdConfig();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fennec.codec.metadata.model.codec.ClassCodecAspect#getSuperTypeConfig <em>Super Type Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Super Type Config</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.ClassCodecAspect#getSuperTypeConfig()
	 * @see #getClassCodecAspect()
	 * @generated
	 */
	EReference getClassCodecAspect_SuperTypeConfig();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.codec.metadata.model.codec.ClassCodecAspect#isInheritFromParent <em>Inherit From Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Inherit From Parent</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.ClassCodecAspect#isInheritFromParent()
	 * @see #getClassCodecAspect()
	 * @generated
	 */
	EAttribute getClassCodecAspect_InheritFromParent();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.codec.metadata.model.codec.ClassCodecAspect#getDiscriminatorValue <em>Discriminator Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Discriminator Value</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.ClassCodecAspect#getDiscriminatorValue()
	 * @see #getClassCodecAspect()
	 * @generated
	 */
	EAttribute getClassCodecAspect_DiscriminatorValue();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.codec.metadata.model.codec.FeatureCodecAspect <em>Feature Codec Aspect</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Feature Codec Aspect</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.FeatureCodecAspect
	 * @generated
	 */
	EClass getFeatureCodecAspect();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.codec.metadata.model.codec.FeatureCodecAspect#getEffectiveKey <em>Effective Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Effective Key</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.FeatureCodecAspect#getEffectiveKey()
	 * @see #getFeatureCodecAspect()
	 * @generated
	 */
	EAttribute getFeatureCodecAspect_EffectiveKey();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.codec.metadata.model.codec.FeatureCodecAspect#isSerialize <em>Serialize</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Serialize</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.FeatureCodecAspect#isSerialize()
	 * @see #getFeatureCodecAspect()
	 * @generated
	 */
	EAttribute getFeatureCodecAspect_Serialize();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.codec.metadata.model.codec.FeatureCodecAspect#isSerializeNull <em>Serialize Null</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Serialize Null</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.FeatureCodecAspect#isSerializeNull()
	 * @see #getFeatureCodecAspect()
	 * @generated
	 */
	EAttribute getFeatureCodecAspect_SerializeNull();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.codec.metadata.model.codec.FeatureCodecAspect#isSerializeEmpty <em>Serialize Empty</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Serialize Empty</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.FeatureCodecAspect#isSerializeEmpty()
	 * @see #getFeatureCodecAspect()
	 * @generated
	 */
	EAttribute getFeatureCodecAspect_SerializeEmpty();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.codec.metadata.model.codec.FeatureCodecAspect#isSerializeDefaults <em>Serialize Defaults</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Serialize Defaults</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.FeatureCodecAspect#isSerializeDefaults()
	 * @see #getFeatureCodecAspect()
	 * @generated
	 */
	EAttribute getFeatureCodecAspect_SerializeDefaults();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.codec.metadata.model.codec.FeatureCodecAspect#getValueWriterName <em>Value Writer Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value Writer Name</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.FeatureCodecAspect#getValueWriterName()
	 * @see #getFeatureCodecAspect()
	 * @generated
	 */
	EAttribute getFeatureCodecAspect_ValueWriterName();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.codec.metadata.model.codec.FeatureCodecAspect#getValueReaderName <em>Value Reader Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value Reader Name</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.FeatureCodecAspect#getValueReaderName()
	 * @see #getFeatureCodecAspect()
	 * @generated
	 */
	EAttribute getFeatureCodecAspect_ValueReaderName();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.codec.metadata.model.codec.FeatureCodecAspect#getEnumSerialization <em>Enum Serialization</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Enum Serialization</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.FeatureCodecAspect#getEnumSerialization()
	 * @see #getFeatureCodecAspect()
	 * @generated
	 */
	EAttribute getFeatureCodecAspect_EnumSerialization();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.codec.metadata.model.codec.ReferenceCodecAspect <em>Reference Codec Aspect</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Reference Codec Aspect</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.ReferenceCodecAspect
	 * @generated
	 */
	EClass getReferenceCodecAspect();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fennec.codec.metadata.model.codec.ReferenceCodecAspect#getReferenceConfig <em>Reference Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Reference Config</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.ReferenceCodecAspect#getReferenceConfig()
	 * @see #getReferenceCodecAspect()
	 * @generated
	 */
	EReference getReferenceCodecAspect_ReferenceConfig();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fennec.codec.metadata.model.codec.ReferenceCodecAspect#getTypeConfig <em>Type Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Type Config</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.ReferenceCodecAspect#getTypeConfig()
	 * @see #getReferenceCodecAspect()
	 * @generated
	 */
	EReference getReferenceCodecAspect_TypeConfig();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.codec.metadata.model.codec.ReferenceCodecAspect#isInheritTypeFromTarget <em>Inherit Type From Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Inherit Type From Target</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.ReferenceCodecAspect#isInheritTypeFromTarget()
	 * @see #getReferenceCodecAspect()
	 * @generated
	 */
	EAttribute getReferenceCodecAspect_InheritTypeFromTarget();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.codec.metadata.model.codec.ReferenceCodecAspect#isExpand <em>Expand</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Expand</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.ReferenceCodecAspect#isExpand()
	 * @see #getReferenceCodecAspect()
	 * @generated
	 */
	EAttribute getReferenceCodecAspect_Expand();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.codec.metadata.model.codec.CodecConfig <em>Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Config</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.CodecConfig
	 * @generated
	 */
	EClass getCodecConfig();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.codec.metadata.model.codec.CodecConfig#getFormat <em>Format</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Format</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.CodecConfig#getFormat()
	 * @see #getCodecConfig()
	 * @generated
	 */
	EAttribute getCodecConfig_Format();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.codec.metadata.model.codec.CodecConfig#isUseNumericIds <em>Use Numeric Ids</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Use Numeric Ids</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.CodecConfig#isUseNumericIds()
	 * @see #getCodecConfig()
	 * @generated
	 */
	EAttribute getCodecConfig_UseNumericIds();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fennec.codec.metadata.model.codec.CodecConfig#getTypeConfig <em>Type Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Type Config</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.CodecConfig#getTypeConfig()
	 * @see #getCodecConfig()
	 * @generated
	 */
	EReference getCodecConfig_TypeConfig();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fennec.codec.metadata.model.codec.CodecConfig#getContainmentTypeConfig <em>Containment Type Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Containment Type Config</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.CodecConfig#getContainmentTypeConfig()
	 * @see #getCodecConfig()
	 * @generated
	 */
	EReference getCodecConfig_ContainmentTypeConfig();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fennec.codec.metadata.model.codec.CodecConfig#getReferenceTypeConfig <em>Reference Type Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Reference Type Config</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.CodecConfig#getReferenceTypeConfig()
	 * @see #getCodecConfig()
	 * @generated
	 */
	EReference getCodecConfig_ReferenceTypeConfig();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fennec.codec.metadata.model.codec.CodecConfig#getIdConfig <em>Id Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Id Config</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.CodecConfig#getIdConfig()
	 * @see #getCodecConfig()
	 * @generated
	 */
	EReference getCodecConfig_IdConfig();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fennec.codec.metadata.model.codec.CodecConfig#getReferenceConfig <em>Reference Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Reference Config</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.CodecConfig#getReferenceConfig()
	 * @see #getCodecConfig()
	 * @generated
	 */
	EReference getCodecConfig_ReferenceConfig();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fennec.codec.metadata.model.codec.CodecConfig#getSuperTypeConfig <em>Super Type Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Super Type Config</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.CodecConfig#getSuperTypeConfig()
	 * @see #getCodecConfig()
	 * @generated
	 */
	EReference getCodecConfig_SuperTypeConfig();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fennec.codec.metadata.model.codec.CodecConfig#getFeatureConfigs <em>Feature Configs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Feature Configs</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.CodecConfig#getFeatureConfigs()
	 * @see #getCodecConfig()
	 * @generated
	 */
	EReference getCodecConfig_FeatureConfigs();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.codec.metadata.model.codec.CodecConfig#isExpand <em>Expand</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Expand</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.CodecConfig#isExpand()
	 * @see #getCodecConfig()
	 * @generated
	 */
	EAttribute getCodecConfig_Expand();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.codec.metadata.model.codec.CodecConfig#getExpandDepth <em>Expand Depth</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Expand Depth</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.CodecConfig#getExpandDepth()
	 * @see #getCodecConfig()
	 * @generated
	 */
	EAttribute getCodecConfig_ExpandDepth();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.codec.metadata.model.codec.CodecConfig#isExpandIgnoreBidirectional <em>Expand Ignore Bidirectional</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Expand Ignore Bidirectional</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.CodecConfig#isExpandIgnoreBidirectional()
	 * @see #getCodecConfig()
	 * @generated
	 */
	EAttribute getCodecConfig_ExpandIgnoreBidirectional();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.codec.metadata.model.codec.CodecConfig#isSerializeNull <em>Serialize Null</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Serialize Null</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.CodecConfig#isSerializeNull()
	 * @see #getCodecConfig()
	 * @generated
	 */
	EAttribute getCodecConfig_SerializeNull();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.codec.metadata.model.codec.CodecConfig#isSerializeEmpty <em>Serialize Empty</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Serialize Empty</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.CodecConfig#isSerializeEmpty()
	 * @see #getCodecConfig()
	 * @generated
	 */
	EAttribute getCodecConfig_SerializeEmpty();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.codec.metadata.model.codec.CodecConfig#isSerializeDefaults <em>Serialize Defaults</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Serialize Defaults</em>'.
	 * @see org.eclipse.fennec.codec.metadata.model.codec.CodecConfig#isSerializeDefaults()
	 * @see #getCodecConfig()
	 * @generated
	 */
	EAttribute getCodecConfig_SerializeDefaults();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	CodecFactory getCodecFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.codec.metadata.model.codec.impl.TypeSerializationConfigImpl <em>Type Serialization Config</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.codec.metadata.model.codec.impl.TypeSerializationConfigImpl
		 * @see org.eclipse.fennec.codec.metadata.model.codec.impl.CodecPackageImpl#getTypeSerializationConfig()
		 * @generated
		 */
		EClass TYPE_SERIALIZATION_CONFIG = eINSTANCE.getTypeSerializationConfig();

		/**
		 * The meta object literal for the '<em><b>Discriminator Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPE_SERIALIZATION_CONFIG__DISCRIMINATOR_PATH = eINSTANCE.getTypeSerializationConfig_DiscriminatorPath();

		/**
		 * The meta object literal for the '<em><b>Discriminator Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPE_SERIALIZATION_CONFIG__DISCRIMINATOR_VALUE = eINSTANCE.getTypeSerializationConfig_DiscriminatorValue();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.codec.metadata.model.codec.impl.IdSerializationConfigImpl <em>Id Serialization Config</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.codec.metadata.model.codec.impl.IdSerializationConfigImpl
		 * @see org.eclipse.fennec.codec.metadata.model.codec.impl.CodecPackageImpl#getIdSerializationConfig()
		 * @generated
		 */
		EClass ID_SERIALIZATION_CONFIG = eINSTANCE.getIdSerializationConfig();

		/**
		 * The meta object literal for the '<em><b>Id Features</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ID_SERIALIZATION_CONFIG__ID_FEATURES = eINSTANCE.getIdSerializationConfig_IdFeatures();

		/**
		 * The meta object literal for the '<em><b>Id Value Writer Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ID_SERIALIZATION_CONFIG__ID_VALUE_WRITER_NAME = eINSTANCE.getIdSerializationConfig_IdValueWriterName();

		/**
		 * The meta object literal for the '<em><b>Id Value Reader Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ID_SERIALIZATION_CONFIG__ID_VALUE_READER_NAME = eINSTANCE.getIdSerializationConfig_IdValueReaderName();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.codec.metadata.model.codec.impl.ReferenceSerializationConfigImpl <em>Reference Serialization Config</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.codec.metadata.model.codec.impl.ReferenceSerializationConfigImpl
		 * @see org.eclipse.fennec.codec.metadata.model.codec.impl.CodecPackageImpl#getReferenceSerializationConfig()
		 * @generated
		 */
		EClass REFERENCE_SERIALIZATION_CONFIG = eINSTANCE.getReferenceSerializationConfig();

		/**
		 * The meta object literal for the '<em><b>Include Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REFERENCE_SERIALIZATION_CONFIG__INCLUDE_TYPE = eINSTANCE.getReferenceSerializationConfig_IncludeType();

		/**
		 * The meta object literal for the '<em><b>Expand</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REFERENCE_SERIALIZATION_CONFIG__EXPAND = eINSTANCE.getReferenceSerializationConfig_Expand();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.codec.metadata.model.codec.impl.SuperTypeSerializationConfigImpl <em>Super Type Serialization Config</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.codec.metadata.model.codec.impl.SuperTypeSerializationConfigImpl
		 * @see org.eclipse.fennec.codec.metadata.model.codec.impl.CodecPackageImpl#getSuperTypeSerializationConfig()
		 * @generated
		 */
		EClass SUPER_TYPE_SERIALIZATION_CONFIG = eINSTANCE.getSuperTypeSerializationConfig();

		/**
		 * The meta object literal for the '<em><b>Use Smart Compression</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SUPER_TYPE_SERIALIZATION_CONFIG__USE_SMART_COMPRESSION = eINSTANCE.getSuperTypeSerializationConfig_UseSmartCompression();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.codec.metadata.model.codec.impl.FeatureSerializationConfigImpl <em>Feature Serialization Config</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.codec.metadata.model.codec.impl.FeatureSerializationConfigImpl
		 * @see org.eclipse.fennec.codec.metadata.model.codec.impl.CodecPackageImpl#getFeatureSerializationConfig()
		 * @generated
		 */
		EClass FEATURE_SERIALIZATION_CONFIG = eINSTANCE.getFeatureSerializationConfig();

		/**
		 * The meta object literal for the '<em><b>Feature Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE_SERIALIZATION_CONFIG__FEATURE_NAME = eINSTANCE.getFeatureSerializationConfig_FeatureName();

		/**
		 * The meta object literal for the '<em><b>Value Writer Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE_SERIALIZATION_CONFIG__VALUE_WRITER_NAME = eINSTANCE.getFeatureSerializationConfig_ValueWriterName();

		/**
		 * The meta object literal for the '<em><b>Value Reader Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE_SERIALIZATION_CONFIG__VALUE_READER_NAME = eINSTANCE.getFeatureSerializationConfig_ValueReaderName();

		/**
		 * The meta object literal for the '<em><b>Expand</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE_SERIALIZATION_CONFIG__EXPAND = eINSTANCE.getFeatureSerializationConfig_Expand();

		/**
		 * The meta object literal for the '<em><b>Reference Config</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FEATURE_SERIALIZATION_CONFIG__REFERENCE_CONFIG = eINSTANCE.getFeatureSerializationConfig_ReferenceConfig();

		/**
		 * The meta object literal for the '<em><b>Type Config</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FEATURE_SERIALIZATION_CONFIG__TYPE_CONFIG = eINSTANCE.getFeatureSerializationConfig_TypeConfig();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.codec.metadata.model.codec.impl.ClassCodecAspectImpl <em>Class Codec Aspect</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.codec.metadata.model.codec.impl.ClassCodecAspectImpl
		 * @see org.eclipse.fennec.codec.metadata.model.codec.impl.CodecPackageImpl#getClassCodecAspect()
		 * @generated
		 */
		EClass CLASS_CODEC_ASPECT = eINSTANCE.getClassCodecAspect();

		/**
		 * The meta object literal for the '<em><b>Type Config</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CLASS_CODEC_ASPECT__TYPE_CONFIG = eINSTANCE.getClassCodecAspect_TypeConfig();

		/**
		 * The meta object literal for the '<em><b>Id Config</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CLASS_CODEC_ASPECT__ID_CONFIG = eINSTANCE.getClassCodecAspect_IdConfig();

		/**
		 * The meta object literal for the '<em><b>Super Type Config</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CLASS_CODEC_ASPECT__SUPER_TYPE_CONFIG = eINSTANCE.getClassCodecAspect_SuperTypeConfig();

		/**
		 * The meta object literal for the '<em><b>Inherit From Parent</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CLASS_CODEC_ASPECT__INHERIT_FROM_PARENT = eINSTANCE.getClassCodecAspect_InheritFromParent();

		/**
		 * The meta object literal for the '<em><b>Discriminator Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CLASS_CODEC_ASPECT__DISCRIMINATOR_VALUE = eINSTANCE.getClassCodecAspect_DiscriminatorValue();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.codec.metadata.model.codec.impl.FeatureCodecAspectImpl <em>Feature Codec Aspect</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.codec.metadata.model.codec.impl.FeatureCodecAspectImpl
		 * @see org.eclipse.fennec.codec.metadata.model.codec.impl.CodecPackageImpl#getFeatureCodecAspect()
		 * @generated
		 */
		EClass FEATURE_CODEC_ASPECT = eINSTANCE.getFeatureCodecAspect();

		/**
		 * The meta object literal for the '<em><b>Effective Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE_CODEC_ASPECT__EFFECTIVE_KEY = eINSTANCE.getFeatureCodecAspect_EffectiveKey();

		/**
		 * The meta object literal for the '<em><b>Serialize</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE_CODEC_ASPECT__SERIALIZE = eINSTANCE.getFeatureCodecAspect_Serialize();

		/**
		 * The meta object literal for the '<em><b>Serialize Null</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE_CODEC_ASPECT__SERIALIZE_NULL = eINSTANCE.getFeatureCodecAspect_SerializeNull();

		/**
		 * The meta object literal for the '<em><b>Serialize Empty</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE_CODEC_ASPECT__SERIALIZE_EMPTY = eINSTANCE.getFeatureCodecAspect_SerializeEmpty();

		/**
		 * The meta object literal for the '<em><b>Serialize Defaults</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE_CODEC_ASPECT__SERIALIZE_DEFAULTS = eINSTANCE.getFeatureCodecAspect_SerializeDefaults();

		/**
		 * The meta object literal for the '<em><b>Value Writer Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE_CODEC_ASPECT__VALUE_WRITER_NAME = eINSTANCE.getFeatureCodecAspect_ValueWriterName();

		/**
		 * The meta object literal for the '<em><b>Value Reader Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE_CODEC_ASPECT__VALUE_READER_NAME = eINSTANCE.getFeatureCodecAspect_ValueReaderName();

		/**
		 * The meta object literal for the '<em><b>Enum Serialization</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE_CODEC_ASPECT__ENUM_SERIALIZATION = eINSTANCE.getFeatureCodecAspect_EnumSerialization();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.codec.metadata.model.codec.impl.ReferenceCodecAspectImpl <em>Reference Codec Aspect</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.codec.metadata.model.codec.impl.ReferenceCodecAspectImpl
		 * @see org.eclipse.fennec.codec.metadata.model.codec.impl.CodecPackageImpl#getReferenceCodecAspect()
		 * @generated
		 */
		EClass REFERENCE_CODEC_ASPECT = eINSTANCE.getReferenceCodecAspect();

		/**
		 * The meta object literal for the '<em><b>Reference Config</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REFERENCE_CODEC_ASPECT__REFERENCE_CONFIG = eINSTANCE.getReferenceCodecAspect_ReferenceConfig();

		/**
		 * The meta object literal for the '<em><b>Type Config</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REFERENCE_CODEC_ASPECT__TYPE_CONFIG = eINSTANCE.getReferenceCodecAspect_TypeConfig();

		/**
		 * The meta object literal for the '<em><b>Inherit Type From Target</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REFERENCE_CODEC_ASPECT__INHERIT_TYPE_FROM_TARGET = eINSTANCE.getReferenceCodecAspect_InheritTypeFromTarget();

		/**
		 * The meta object literal for the '<em><b>Expand</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REFERENCE_CODEC_ASPECT__EXPAND = eINSTANCE.getReferenceCodecAspect_Expand();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.codec.metadata.model.codec.impl.CodecConfigImpl <em>Config</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.codec.metadata.model.codec.impl.CodecConfigImpl
		 * @see org.eclipse.fennec.codec.metadata.model.codec.impl.CodecPackageImpl#getCodecConfig()
		 * @generated
		 */
		EClass CODEC_CONFIG = eINSTANCE.getCodecConfig();

		/**
		 * The meta object literal for the '<em><b>Format</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CODEC_CONFIG__FORMAT = eINSTANCE.getCodecConfig_Format();

		/**
		 * The meta object literal for the '<em><b>Use Numeric Ids</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CODEC_CONFIG__USE_NUMERIC_IDS = eINSTANCE.getCodecConfig_UseNumericIds();

		/**
		 * The meta object literal for the '<em><b>Type Config</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CODEC_CONFIG__TYPE_CONFIG = eINSTANCE.getCodecConfig_TypeConfig();

		/**
		 * The meta object literal for the '<em><b>Containment Type Config</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CODEC_CONFIG__CONTAINMENT_TYPE_CONFIG = eINSTANCE.getCodecConfig_ContainmentTypeConfig();

		/**
		 * The meta object literal for the '<em><b>Reference Type Config</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CODEC_CONFIG__REFERENCE_TYPE_CONFIG = eINSTANCE.getCodecConfig_ReferenceTypeConfig();

		/**
		 * The meta object literal for the '<em><b>Id Config</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CODEC_CONFIG__ID_CONFIG = eINSTANCE.getCodecConfig_IdConfig();

		/**
		 * The meta object literal for the '<em><b>Reference Config</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CODEC_CONFIG__REFERENCE_CONFIG = eINSTANCE.getCodecConfig_ReferenceConfig();

		/**
		 * The meta object literal for the '<em><b>Super Type Config</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CODEC_CONFIG__SUPER_TYPE_CONFIG = eINSTANCE.getCodecConfig_SuperTypeConfig();

		/**
		 * The meta object literal for the '<em><b>Feature Configs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CODEC_CONFIG__FEATURE_CONFIGS = eINSTANCE.getCodecConfig_FeatureConfigs();

		/**
		 * The meta object literal for the '<em><b>Expand</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CODEC_CONFIG__EXPAND = eINSTANCE.getCodecConfig_Expand();

		/**
		 * The meta object literal for the '<em><b>Expand Depth</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CODEC_CONFIG__EXPAND_DEPTH = eINSTANCE.getCodecConfig_ExpandDepth();

		/**
		 * The meta object literal for the '<em><b>Expand Ignore Bidirectional</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CODEC_CONFIG__EXPAND_IGNORE_BIDIRECTIONAL = eINSTANCE.getCodecConfig_ExpandIgnoreBidirectional();

		/**
		 * The meta object literal for the '<em><b>Serialize Null</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CODEC_CONFIG__SERIALIZE_NULL = eINSTANCE.getCodecConfig_SerializeNull();

		/**
		 * The meta object literal for the '<em><b>Serialize Empty</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CODEC_CONFIG__SERIALIZE_EMPTY = eINSTANCE.getCodecConfig_SerializeEmpty();

		/**
		 * The meta object literal for the '<em><b>Serialize Defaults</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CODEC_CONFIG__SERIALIZE_DEFAULTS = eINSTANCE.getCodecConfig_SerializeDefaults();

	}

} //CodecPackage
