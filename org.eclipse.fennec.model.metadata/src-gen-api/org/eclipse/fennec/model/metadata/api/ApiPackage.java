/*
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
 * 
 */
package org.eclipse.fennec.model.metadata.api;


import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;

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
 * @see org.eclipse.fennec.model.metadata.api.ApiFactory
 * @model kind="package"
 *        annotation="Version value='1.0'"
 * @generated
 */
@ProviderType
@EPackage(uri = ApiPackage.eNS_URI, genModel = "/model/metadata-api.genmodel", genModelSourceLocations = {"model/metadata-api.genmodel","org.eclipse.fennec.model.metadata/model/metadata-api.genmodel"}, ecore="/model/metadata-api.ecore", ecoreSourceLocations="/model/metadata-api.ecore")
public interface ApiPackage extends org.eclipse.emf.ecore.EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "api";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "https://eclipse.org/fennec/metadata/api/1.0.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "metadata.api";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ApiPackage eINSTANCE = org.eclipse.fennec.model.metadata.api.impl.ApiPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.model.metadata.api.MetadataService <em>Metadata Service</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.model.metadata.api.MetadataService
	 * @see org.eclipse.fennec.model.metadata.api.impl.ApiPackageImpl#getMetadataService()
	 * @generated
	 */
	int METADATA_SERVICE = 0;

	/**
	 * The number of structural features of the '<em>Metadata Service</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METADATA_SERVICE_FEATURE_COUNT = 0;

	/**
	 * The operation id for the '<em>Register Package</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METADATA_SERVICE___REGISTER_PACKAGE__EPACKAGE = 0;

	/**
	 * The operation id for the '<em>Unregister Package</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METADATA_SERVICE___UNREGISTER_PACKAGE__EPACKAGE = 1;

	/**
	 * The operation id for the '<em>Get Package Metadata</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METADATA_SERVICE___GET_PACKAGE_METADATA__STRING = 2;

	/**
	 * The operation id for the '<em>Get Class Metadata</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METADATA_SERVICE___GET_CLASS_METADATA__ECLASS = 3;

	/**
	 * The operation id for the '<em>Get Class Metadata By URI</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METADATA_SERVICE___GET_CLASS_METADATA_BY_URI__STRING = 4;

	/**
	 * The operation id for the '<em>Get Class Metadata By Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METADATA_SERVICE___GET_CLASS_METADATA_BY_NAME__STRING_STRING = 5;

	/**
	 * The operation id for the '<em>Get Feature Metadata</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METADATA_SERVICE___GET_FEATURE_METADATA__ESTRUCTURALFEATURE = 6;

	/**
	 * The operation id for the '<em>Get Feature Metadata By URI</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METADATA_SERVICE___GET_FEATURE_METADATA_BY_URI__STRING = 7;

	/**
	 * The operation id for the '<em>Get Feature Metadata By Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METADATA_SERVICE___GET_FEATURE_METADATA_BY_NAME__STRING_STRING_STRING = 8;

	/**
	 * The operation id for the '<em>Get Feature Metadata From Class</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METADATA_SERVICE___GET_FEATURE_METADATA_FROM_CLASS__STRING_CLASSMETADATA = 9;

	/**
	 * The operation id for the '<em>Get Class Aspect</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METADATA_SERVICE___GET_CLASS_ASPECT__ECLASS_STRING = 10;

	/**
	 * The operation id for the '<em>Get Feature Aspect</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METADATA_SERVICE___GET_FEATURE_ASPECT__ESTRUCTURALFEATURE_STRING = 11;

	/**
	 * The operation id for the '<em>Get Registry</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METADATA_SERVICE___GET_REGISTRY = 12;

	/**
	 * The operation id for the '<em>Register Aspect Provider</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METADATA_SERVICE___REGISTER_ASPECT_PROVIDER__ASPECTPROVIDER = 13;

	/**
	 * The operation id for the '<em>Unregister Aspect Provider</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METADATA_SERVICE___UNREGISTER_ASPECT_PROVIDER__ASPECTPROVIDER = 14;

	/**
	 * The operation id for the '<em>Get Aspect Providers</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METADATA_SERVICE___GET_ASPECT_PROVIDERS = 15;

	/**
	 * The number of operations of the '<em>Metadata Service</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METADATA_SERVICE_OPERATION_COUNT = 16;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.model.metadata.api.AspectProvider <em>Aspect Provider</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.model.metadata.api.AspectProvider
	 * @see org.eclipse.fennec.model.metadata.api.impl.ApiPackageImpl#getAspectProvider()
	 * @generated
	 */
	int ASPECT_PROVIDER = 1;

	/**
	 * The number of structural features of the '<em>Aspect Provider</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASPECT_PROVIDER_FEATURE_COUNT = 0;

	/**
	 * The operation id for the '<em>Get Aspect Type Id</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASPECT_PROVIDER___GET_ASPECT_TYPE_ID = 0;

	/**
	 * The operation id for the '<em>Build Class Aspect</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASPECT_PROVIDER___BUILD_CLASS_ASPECT__ECLASS = 1;

	/**
	 * The operation id for the '<em>Build Feature Aspect</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASPECT_PROVIDER___BUILD_FEATURE_ASPECT__ESTRUCTURALFEATURE = 2;

	/**
	 * The operation id for the '<em>Build Attribute Aspect</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASPECT_PROVIDER___BUILD_ATTRIBUTE_ASPECT__EATTRIBUTE = 3;

	/**
	 * The operation id for the '<em>Build Reference Aspect</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASPECT_PROVIDER___BUILD_REFERENCE_ASPECT__EREFERENCE = 4;

	/**
	 * The number of operations of the '<em>Aspect Provider</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASPECT_PROVIDER_OPERATION_COUNT = 5;


	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.model.metadata.api.MetadataService <em>Metadata Service</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Metadata Service</em>'.
	 * @see org.eclipse.fennec.model.metadata.api.MetadataService
	 * @generated
	 */
	EClass getMetadataService();

	/**
	 * Returns the meta object for the '{@link org.eclipse.fennec.model.metadata.api.MetadataService#registerPackage(org.eclipse.emf.ecore.EPackage) <em>Register Package</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Register Package</em>' operation.
	 * @see org.eclipse.fennec.model.metadata.api.MetadataService#registerPackage(org.eclipse.emf.ecore.EPackage)
	 * @generated
	 */
	EOperation getMetadataService__RegisterPackage__EPackage();

	/**
	 * Returns the meta object for the '{@link org.eclipse.fennec.model.metadata.api.MetadataService#unregisterPackage(org.eclipse.emf.ecore.EPackage) <em>Unregister Package</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Unregister Package</em>' operation.
	 * @see org.eclipse.fennec.model.metadata.api.MetadataService#unregisterPackage(org.eclipse.emf.ecore.EPackage)
	 * @generated
	 */
	EOperation getMetadataService__UnregisterPackage__EPackage();

	/**
	 * Returns the meta object for the '{@link org.eclipse.fennec.model.metadata.api.MetadataService#getPackageMetadata(java.lang.String) <em>Get Package Metadata</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Package Metadata</em>' operation.
	 * @see org.eclipse.fennec.model.metadata.api.MetadataService#getPackageMetadata(java.lang.String)
	 * @generated
	 */
	EOperation getMetadataService__GetPackageMetadata__String();

	/**
	 * Returns the meta object for the '{@link org.eclipse.fennec.model.metadata.api.MetadataService#getClassMetadata(org.eclipse.emf.ecore.EClass) <em>Get Class Metadata</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Class Metadata</em>' operation.
	 * @see org.eclipse.fennec.model.metadata.api.MetadataService#getClassMetadata(org.eclipse.emf.ecore.EClass)
	 * @generated
	 */
	EOperation getMetadataService__GetClassMetadata__EClass();

	/**
	 * Returns the meta object for the '{@link org.eclipse.fennec.model.metadata.api.MetadataService#getClassMetadataByURI(java.lang.String) <em>Get Class Metadata By URI</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Class Metadata By URI</em>' operation.
	 * @see org.eclipse.fennec.model.metadata.api.MetadataService#getClassMetadataByURI(java.lang.String)
	 * @generated
	 */
	EOperation getMetadataService__GetClassMetadataByURI__String();

	/**
	 * Returns the meta object for the '{@link org.eclipse.fennec.model.metadata.api.MetadataService#getClassMetadataByName(java.lang.String, java.lang.String) <em>Get Class Metadata By Name</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Class Metadata By Name</em>' operation.
	 * @see org.eclipse.fennec.model.metadata.api.MetadataService#getClassMetadataByName(java.lang.String, java.lang.String)
	 * @generated
	 */
	EOperation getMetadataService__GetClassMetadataByName__String_String();

	/**
	 * Returns the meta object for the '{@link org.eclipse.fennec.model.metadata.api.MetadataService#getFeatureMetadata(org.eclipse.emf.ecore.EStructuralFeature) <em>Get Feature Metadata</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Feature Metadata</em>' operation.
	 * @see org.eclipse.fennec.model.metadata.api.MetadataService#getFeatureMetadata(org.eclipse.emf.ecore.EStructuralFeature)
	 * @generated
	 */
	EOperation getMetadataService__GetFeatureMetadata__EStructuralFeature();

	/**
	 * Returns the meta object for the '{@link org.eclipse.fennec.model.metadata.api.MetadataService#getFeatureMetadataByURI(java.lang.String) <em>Get Feature Metadata By URI</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Feature Metadata By URI</em>' operation.
	 * @see org.eclipse.fennec.model.metadata.api.MetadataService#getFeatureMetadataByURI(java.lang.String)
	 * @generated
	 */
	EOperation getMetadataService__GetFeatureMetadataByURI__String();

	/**
	 * Returns the meta object for the '{@link org.eclipse.fennec.model.metadata.api.MetadataService#getFeatureMetadataByName(java.lang.String, java.lang.String, java.lang.String) <em>Get Feature Metadata By Name</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Feature Metadata By Name</em>' operation.
	 * @see org.eclipse.fennec.model.metadata.api.MetadataService#getFeatureMetadataByName(java.lang.String, java.lang.String, java.lang.String)
	 * @generated
	 */
	EOperation getMetadataService__GetFeatureMetadataByName__String_String_String();

	/**
	 * Returns the meta object for the '{@link org.eclipse.fennec.model.metadata.api.MetadataService#getFeatureMetadataFromClass(java.lang.String, org.eclipse.fennec.model.metadata.ClassMetadata) <em>Get Feature Metadata From Class</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Feature Metadata From Class</em>' operation.
	 * @see org.eclipse.fennec.model.metadata.api.MetadataService#getFeatureMetadataFromClass(java.lang.String, org.eclipse.fennec.model.metadata.ClassMetadata)
	 * @generated
	 */
	EOperation getMetadataService__GetFeatureMetadataFromClass__String_ClassMetadata();

	/**
	 * Returns the meta object for the '{@link org.eclipse.fennec.model.metadata.api.MetadataService#getClassAspect(org.eclipse.emf.ecore.EClass, java.lang.String) <em>Get Class Aspect</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Class Aspect</em>' operation.
	 * @see org.eclipse.fennec.model.metadata.api.MetadataService#getClassAspect(org.eclipse.emf.ecore.EClass, java.lang.String)
	 * @generated
	 */
	EOperation getMetadataService__GetClassAspect__EClass_String();

	/**
	 * Returns the meta object for the '{@link org.eclipse.fennec.model.metadata.api.MetadataService#getFeatureAspect(org.eclipse.emf.ecore.EStructuralFeature, java.lang.String) <em>Get Feature Aspect</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Feature Aspect</em>' operation.
	 * @see org.eclipse.fennec.model.metadata.api.MetadataService#getFeatureAspect(org.eclipse.emf.ecore.EStructuralFeature, java.lang.String)
	 * @generated
	 */
	EOperation getMetadataService__GetFeatureAspect__EStructuralFeature_String();

	/**
	 * Returns the meta object for the '{@link org.eclipse.fennec.model.metadata.api.MetadataService#getRegistry() <em>Get Registry</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Registry</em>' operation.
	 * @see org.eclipse.fennec.model.metadata.api.MetadataService#getRegistry()
	 * @generated
	 */
	EOperation getMetadataService__GetRegistry();

	/**
	 * Returns the meta object for the '{@link org.eclipse.fennec.model.metadata.api.MetadataService#registerAspectProvider(org.eclipse.fennec.model.metadata.api.AspectProvider) <em>Register Aspect Provider</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Register Aspect Provider</em>' operation.
	 * @see org.eclipse.fennec.model.metadata.api.MetadataService#registerAspectProvider(org.eclipse.fennec.model.metadata.api.AspectProvider)
	 * @generated
	 */
	EOperation getMetadataService__RegisterAspectProvider__AspectProvider();

	/**
	 * Returns the meta object for the '{@link org.eclipse.fennec.model.metadata.api.MetadataService#unregisterAspectProvider(org.eclipse.fennec.model.metadata.api.AspectProvider) <em>Unregister Aspect Provider</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Unregister Aspect Provider</em>' operation.
	 * @see org.eclipse.fennec.model.metadata.api.MetadataService#unregisterAspectProvider(org.eclipse.fennec.model.metadata.api.AspectProvider)
	 * @generated
	 */
	EOperation getMetadataService__UnregisterAspectProvider__AspectProvider();

	/**
	 * Returns the meta object for the '{@link org.eclipse.fennec.model.metadata.api.MetadataService#getAspectProviders() <em>Get Aspect Providers</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Aspect Providers</em>' operation.
	 * @see org.eclipse.fennec.model.metadata.api.MetadataService#getAspectProviders()
	 * @generated
	 */
	EOperation getMetadataService__GetAspectProviders();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.model.metadata.api.AspectProvider <em>Aspect Provider</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Aspect Provider</em>'.
	 * @see org.eclipse.fennec.model.metadata.api.AspectProvider
	 * @generated
	 */
	EClass getAspectProvider();

	/**
	 * Returns the meta object for the '{@link org.eclipse.fennec.model.metadata.api.AspectProvider#getAspectTypeId() <em>Get Aspect Type Id</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Aspect Type Id</em>' operation.
	 * @see org.eclipse.fennec.model.metadata.api.AspectProvider#getAspectTypeId()
	 * @generated
	 */
	EOperation getAspectProvider__GetAspectTypeId();

	/**
	 * Returns the meta object for the '{@link org.eclipse.fennec.model.metadata.api.AspectProvider#buildClassAspect(org.eclipse.emf.ecore.EClass) <em>Build Class Aspect</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Build Class Aspect</em>' operation.
	 * @see org.eclipse.fennec.model.metadata.api.AspectProvider#buildClassAspect(org.eclipse.emf.ecore.EClass)
	 * @generated
	 */
	EOperation getAspectProvider__BuildClassAspect__EClass();

	/**
	 * Returns the meta object for the '{@link org.eclipse.fennec.model.metadata.api.AspectProvider#buildFeatureAspect(org.eclipse.emf.ecore.EStructuralFeature) <em>Build Feature Aspect</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Build Feature Aspect</em>' operation.
	 * @see org.eclipse.fennec.model.metadata.api.AspectProvider#buildFeatureAspect(org.eclipse.emf.ecore.EStructuralFeature)
	 * @generated
	 */
	EOperation getAspectProvider__BuildFeatureAspect__EStructuralFeature();

	/**
	 * Returns the meta object for the '{@link org.eclipse.fennec.model.metadata.api.AspectProvider#buildAttributeAspect(org.eclipse.emf.ecore.EAttribute) <em>Build Attribute Aspect</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Build Attribute Aspect</em>' operation.
	 * @see org.eclipse.fennec.model.metadata.api.AspectProvider#buildAttributeAspect(org.eclipse.emf.ecore.EAttribute)
	 * @generated
	 */
	EOperation getAspectProvider__BuildAttributeAspect__EAttribute();

	/**
	 * Returns the meta object for the '{@link org.eclipse.fennec.model.metadata.api.AspectProvider#buildReferenceAspect(org.eclipse.emf.ecore.EReference) <em>Build Reference Aspect</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Build Reference Aspect</em>' operation.
	 * @see org.eclipse.fennec.model.metadata.api.AspectProvider#buildReferenceAspect(org.eclipse.emf.ecore.EReference)
	 * @generated
	 */
	EOperation getAspectProvider__BuildReferenceAspect__EReference();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ApiFactory getApiFactory();

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
		 * The meta object literal for the '{@link org.eclipse.fennec.model.metadata.api.MetadataService <em>Metadata Service</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.model.metadata.api.MetadataService
		 * @see org.eclipse.fennec.model.metadata.api.impl.ApiPackageImpl#getMetadataService()
		 * @generated
		 */
		EClass METADATA_SERVICE = eINSTANCE.getMetadataService();

		/**
		 * The meta object literal for the '<em><b>Register Package</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation METADATA_SERVICE___REGISTER_PACKAGE__EPACKAGE = eINSTANCE.getMetadataService__RegisterPackage__EPackage();

		/**
		 * The meta object literal for the '<em><b>Unregister Package</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation METADATA_SERVICE___UNREGISTER_PACKAGE__EPACKAGE = eINSTANCE.getMetadataService__UnregisterPackage__EPackage();

		/**
		 * The meta object literal for the '<em><b>Get Package Metadata</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation METADATA_SERVICE___GET_PACKAGE_METADATA__STRING = eINSTANCE.getMetadataService__GetPackageMetadata__String();

		/**
		 * The meta object literal for the '<em><b>Get Class Metadata</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation METADATA_SERVICE___GET_CLASS_METADATA__ECLASS = eINSTANCE.getMetadataService__GetClassMetadata__EClass();

		/**
		 * The meta object literal for the '<em><b>Get Class Metadata By URI</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation METADATA_SERVICE___GET_CLASS_METADATA_BY_URI__STRING = eINSTANCE.getMetadataService__GetClassMetadataByURI__String();

		/**
		 * The meta object literal for the '<em><b>Get Class Metadata By Name</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation METADATA_SERVICE___GET_CLASS_METADATA_BY_NAME__STRING_STRING = eINSTANCE.getMetadataService__GetClassMetadataByName__String_String();

		/**
		 * The meta object literal for the '<em><b>Get Feature Metadata</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation METADATA_SERVICE___GET_FEATURE_METADATA__ESTRUCTURALFEATURE = eINSTANCE.getMetadataService__GetFeatureMetadata__EStructuralFeature();

		/**
		 * The meta object literal for the '<em><b>Get Feature Metadata By URI</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation METADATA_SERVICE___GET_FEATURE_METADATA_BY_URI__STRING = eINSTANCE.getMetadataService__GetFeatureMetadataByURI__String();

		/**
		 * The meta object literal for the '<em><b>Get Feature Metadata By Name</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation METADATA_SERVICE___GET_FEATURE_METADATA_BY_NAME__STRING_STRING_STRING = eINSTANCE.getMetadataService__GetFeatureMetadataByName__String_String_String();

		/**
		 * The meta object literal for the '<em><b>Get Feature Metadata From Class</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation METADATA_SERVICE___GET_FEATURE_METADATA_FROM_CLASS__STRING_CLASSMETADATA = eINSTANCE.getMetadataService__GetFeatureMetadataFromClass__String_ClassMetadata();

		/**
		 * The meta object literal for the '<em><b>Get Class Aspect</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation METADATA_SERVICE___GET_CLASS_ASPECT__ECLASS_STRING = eINSTANCE.getMetadataService__GetClassAspect__EClass_String();

		/**
		 * The meta object literal for the '<em><b>Get Feature Aspect</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation METADATA_SERVICE___GET_FEATURE_ASPECT__ESTRUCTURALFEATURE_STRING = eINSTANCE.getMetadataService__GetFeatureAspect__EStructuralFeature_String();

		/**
		 * The meta object literal for the '<em><b>Get Registry</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation METADATA_SERVICE___GET_REGISTRY = eINSTANCE.getMetadataService__GetRegistry();

		/**
		 * The meta object literal for the '<em><b>Register Aspect Provider</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation METADATA_SERVICE___REGISTER_ASPECT_PROVIDER__ASPECTPROVIDER = eINSTANCE.getMetadataService__RegisterAspectProvider__AspectProvider();

		/**
		 * The meta object literal for the '<em><b>Unregister Aspect Provider</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation METADATA_SERVICE___UNREGISTER_ASPECT_PROVIDER__ASPECTPROVIDER = eINSTANCE.getMetadataService__UnregisterAspectProvider__AspectProvider();

		/**
		 * The meta object literal for the '<em><b>Get Aspect Providers</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation METADATA_SERVICE___GET_ASPECT_PROVIDERS = eINSTANCE.getMetadataService__GetAspectProviders();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.model.metadata.api.AspectProvider <em>Aspect Provider</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.model.metadata.api.AspectProvider
		 * @see org.eclipse.fennec.model.metadata.api.impl.ApiPackageImpl#getAspectProvider()
		 * @generated
		 */
		EClass ASPECT_PROVIDER = eINSTANCE.getAspectProvider();

		/**
		 * The meta object literal for the '<em><b>Get Aspect Type Id</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ASPECT_PROVIDER___GET_ASPECT_TYPE_ID = eINSTANCE.getAspectProvider__GetAspectTypeId();

		/**
		 * The meta object literal for the '<em><b>Build Class Aspect</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ASPECT_PROVIDER___BUILD_CLASS_ASPECT__ECLASS = eINSTANCE.getAspectProvider__BuildClassAspect__EClass();

		/**
		 * The meta object literal for the '<em><b>Build Feature Aspect</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ASPECT_PROVIDER___BUILD_FEATURE_ASPECT__ESTRUCTURALFEATURE = eINSTANCE.getAspectProvider__BuildFeatureAspect__EStructuralFeature();

		/**
		 * The meta object literal for the '<em><b>Build Attribute Aspect</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ASPECT_PROVIDER___BUILD_ATTRIBUTE_ASPECT__EATTRIBUTE = eINSTANCE.getAspectProvider__BuildAttributeAspect__EAttribute();

		/**
		 * The meta object literal for the '<em><b>Build Reference Aspect</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ASPECT_PROVIDER___BUILD_REFERENCE_ASPECT__EREFERENCE = eINSTANCE.getAspectProvider__BuildReferenceAspect__EReference();

	}

} //ApiPackage
