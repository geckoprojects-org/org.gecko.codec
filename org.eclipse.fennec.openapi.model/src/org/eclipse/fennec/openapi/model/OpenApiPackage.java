/*
 * Copyright (c) 2012 - 2024 Data In Motion and others.
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
package org.eclipse.fennec.openapi.model;


import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;

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
 * @see org.eclipse.fennec.openapi.model.OpenApiFactory
 * @model kind="package"
 * @generated
 */
@ProviderType
@EPackage(uri = OpenApiPackage.eNS_URI, genModel = "/model/openapi.genmodel", genModelSourceLocations = {"model/openapi.genmodel","org.eclipse.fennec.openapi.model/model/openapi.genmodel"}, ecore="/model/openapi.ecore", ecoreSourceLocations="/model/openapi.ecore")
public interface OpenApiPackage extends org.eclipse.emf.ecore.EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "model";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "https://spec.openapis.org/oas/3.0.1/schema";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "openapi";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	OpenApiPackage eINSTANCE = org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.openapi.model.impl.OpenApiImpl <em>Open Api</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.openapi.model.impl.OpenApiImpl
	 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getOpenApi()
	 * @generated
	 */
	int OPEN_API = 0;

	/**
	 * The feature id for the '<em><b>Openapi</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPEN_API__OPENAPI = 0;

	/**
	 * The feature id for the '<em><b>Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPEN_API__INFO = 1;

	/**
	 * The feature id for the '<em><b>Servers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPEN_API__SERVERS = 2;

	/**
	 * The feature id for the '<em><b>Paths</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPEN_API__PATHS = 3;

	/**
	 * The feature id for the '<em><b>Components</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPEN_API__COMPONENTS = 4;

	/**
	 * The feature id for the '<em><b>External Docs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPEN_API__EXTERNAL_DOCS = 5;

	/**
	 * The feature id for the '<em><b>Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPEN_API__TAGS = 6;

	/**
	 * The number of structural features of the '<em>Open Api</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPEN_API_FEATURE_COUNT = 7;

	/**
	 * The number of operations of the '<em>Open Api</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPEN_API_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.openapi.model.impl.InfoImpl <em>Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.openapi.model.impl.InfoImpl
	 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getInfo()
	 * @generated
	 */
	int INFO = 1;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFO__TITLE = 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFO__DESCRIPTION = 1;

	/**
	 * The feature id for the '<em><b>Terms Of Service</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFO__TERMS_OF_SERVICE = 2;

	/**
	 * The feature id for the '<em><b>License</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFO__LICENSE = 3;

	/**
	 * The feature id for the '<em><b>Contact</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFO__CONTACT = 4;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFO__VERSION = 5;

	/**
	 * The number of structural features of the '<em>Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFO_FEATURE_COUNT = 6;

	/**
	 * The number of operations of the '<em>Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFO_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.openapi.model.impl.ContactImpl <em>Contact</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.openapi.model.impl.ContactImpl
	 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getContact()
	 * @generated
	 */
	int CONTACT = 2;

	/**
	 * The feature id for the '<em><b>Email</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT__EMAIL = 0;

	/**
	 * The number of structural features of the '<em>Contact</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Contact</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.openapi.model.impl.LicenseImpl <em>License</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.openapi.model.impl.LicenseImpl
	 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getLicense()
	 * @generated
	 */
	int LICENSE = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LICENSE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LICENSE__URL = 1;

	/**
	 * The number of structural features of the '<em>License</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LICENSE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>License</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LICENSE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.openapi.model.impl.DocsImpl <em>Docs</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.openapi.model.impl.DocsImpl
	 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getDocs()
	 * @generated
	 */
	int DOCS = 4;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCS__DESCRIPTION = 0;

	/**
	 * The feature id for the '<em><b>Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCS__URL = 1;

	/**
	 * The number of structural features of the '<em>Docs</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCS_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Docs</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCS_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.openapi.model.impl.TagImpl <em>Tag</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.openapi.model.impl.TagImpl
	 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getTag()
	 * @generated
	 */
	int TAG = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG__NAME = 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG__DESCRIPTION = 1;

	/**
	 * The feature id for the '<em><b>External Docs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG__EXTERNAL_DOCS = 2;

	/**
	 * The number of structural features of the '<em>Tag</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Tag</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.openapi.model.impl.ServerImpl <em>Server</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.openapi.model.impl.ServerImpl
	 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getServer()
	 * @generated
	 */
	int SERVER = 6;

	/**
	 * The feature id for the '<em><b>Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVER__URL = 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVER__DESCRIPTION = 1;

	/**
	 * The number of structural features of the '<em>Server</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVER_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Server</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.openapi.model.impl.PathImpl <em>Path</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.openapi.model.impl.PathImpl
	 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getPath()
	 * @generated
	 */
	int PATH = 7;

	/**
	 * The feature id for the '<em><b>Put</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATH__PUT = 0;

	/**
	 * The feature id for the '<em><b>Get</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATH__GET = 1;

	/**
	 * The feature id for the '<em><b>Post</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATH__POST = 2;

	/**
	 * The feature id for the '<em><b>Delete</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATH__DELETE = 3;

	/**
	 * The number of structural features of the '<em>Path</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATH_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Path</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATH_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.openapi.model.impl.ParameterImpl <em>Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.openapi.model.impl.ParameterImpl
	 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getParameter()
	 * @generated
	 */
	int PARAMETER = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__NAME = 0;

	/**
	 * The feature id for the '<em><b>In</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__IN = 1;

	/**
	 * The feature id for the '<em><b>Required</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__REQUIRED = 2;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__SCHEMA = 3;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__DESCRIPTION = 4;

	/**
	 * The number of structural features of the '<em>Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.openapi.model.impl.SecurityImpl <em>Security</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.openapi.model.impl.SecurityImpl
	 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getSecurity()
	 * @generated
	 */
	int SECURITY = 9;

	/**
	 * The feature id for the '<em><b>Bearer Auth</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY__BEARER_AUTH = 0;

	/**
	 * The feature id for the '<em><b>Basic Auth</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY__BASIC_AUTH = 1;

	/**
	 * The feature id for the '<em><b>Api Key</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY__API_KEY = 2;

	/**
	 * The number of structural features of the '<em>Security</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Security</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.openapi.model.impl.MethodImpl <em>Method</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.openapi.model.impl.MethodImpl
	 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getMethod()
	 * @generated
	 */
	int METHOD = 10;

	/**
	 * The feature id for the '<em><b>Tags</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD__TAGS = 0;

	/**
	 * The feature id for the '<em><b>Summary</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD__SUMMARY = 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD__DESCRIPTION = 2;

	/**
	 * The feature id for the '<em><b>Operation Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD__OPERATION_ID = 3;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD__PARAMETERS = 4;

	/**
	 * The feature id for the '<em><b>Responses</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD__RESPONSES = 5;

	/**
	 * The feature id for the '<em><b>Security</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD__SECURITY = 6;

	/**
	 * The number of structural features of the '<em>Method</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_FEATURE_COUNT = 7;

	/**
	 * The number of operations of the '<em>Method</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.openapi.model.impl.PutImpl <em>Put</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.openapi.model.impl.PutImpl
	 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getPut()
	 * @generated
	 */
	int PUT = 11;

	/**
	 * The feature id for the '<em><b>Tags</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PUT__TAGS = METHOD__TAGS;

	/**
	 * The feature id for the '<em><b>Summary</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PUT__SUMMARY = METHOD__SUMMARY;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PUT__DESCRIPTION = METHOD__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Operation Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PUT__OPERATION_ID = METHOD__OPERATION_ID;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PUT__PARAMETERS = METHOD__PARAMETERS;

	/**
	 * The feature id for the '<em><b>Responses</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PUT__RESPONSES = METHOD__RESPONSES;

	/**
	 * The feature id for the '<em><b>Security</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PUT__SECURITY = METHOD__SECURITY;

	/**
	 * The feature id for the '<em><b>Request Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PUT__REQUEST_BODY = METHOD_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Put</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PUT_FEATURE_COUNT = METHOD_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Put</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PUT_OPERATION_COUNT = METHOD_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.openapi.model.impl.PostImpl <em>Post</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.openapi.model.impl.PostImpl
	 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getPost()
	 * @generated
	 */
	int POST = 12;

	/**
	 * The feature id for the '<em><b>Tags</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POST__TAGS = METHOD__TAGS;

	/**
	 * The feature id for the '<em><b>Summary</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POST__SUMMARY = METHOD__SUMMARY;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POST__DESCRIPTION = METHOD__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Operation Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POST__OPERATION_ID = METHOD__OPERATION_ID;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POST__PARAMETERS = METHOD__PARAMETERS;

	/**
	 * The feature id for the '<em><b>Responses</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POST__RESPONSES = METHOD__RESPONSES;

	/**
	 * The feature id for the '<em><b>Security</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POST__SECURITY = METHOD__SECURITY;

	/**
	 * The feature id for the '<em><b>Request Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POST__REQUEST_BODY = METHOD_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Post</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POST_FEATURE_COUNT = METHOD_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Post</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POST_OPERATION_COUNT = METHOD_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.openapi.model.impl.DeleteImpl <em>Delete</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.openapi.model.impl.DeleteImpl
	 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getDelete()
	 * @generated
	 */
	int DELETE = 13;

	/**
	 * The feature id for the '<em><b>Tags</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELETE__TAGS = METHOD__TAGS;

	/**
	 * The feature id for the '<em><b>Summary</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELETE__SUMMARY = METHOD__SUMMARY;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELETE__DESCRIPTION = METHOD__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Operation Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELETE__OPERATION_ID = METHOD__OPERATION_ID;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELETE__PARAMETERS = METHOD__PARAMETERS;

	/**
	 * The feature id for the '<em><b>Responses</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELETE__RESPONSES = METHOD__RESPONSES;

	/**
	 * The feature id for the '<em><b>Security</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELETE__SECURITY = METHOD__SECURITY;

	/**
	 * The number of structural features of the '<em>Delete</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELETE_FEATURE_COUNT = METHOD_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Delete</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELETE_OPERATION_COUNT = METHOD_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.openapi.model.impl.GetImpl <em>Get</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.openapi.model.impl.GetImpl
	 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getGet()
	 * @generated
	 */
	int GET = 14;

	/**
	 * The feature id for the '<em><b>Tags</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GET__TAGS = METHOD__TAGS;

	/**
	 * The feature id for the '<em><b>Summary</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GET__SUMMARY = METHOD__SUMMARY;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GET__DESCRIPTION = METHOD__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Operation Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GET__OPERATION_ID = METHOD__OPERATION_ID;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GET__PARAMETERS = METHOD__PARAMETERS;

	/**
	 * The feature id for the '<em><b>Responses</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GET__RESPONSES = METHOD__RESPONSES;

	/**
	 * The feature id for the '<em><b>Security</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GET__SECURITY = METHOD__SECURITY;

	/**
	 * The number of structural features of the '<em>Get</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GET_FEATURE_COUNT = METHOD_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Get</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GET_OPERATION_COUNT = METHOD_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.openapi.model.impl.RequestBodyImpl <em>Request Body</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.openapi.model.impl.RequestBodyImpl
	 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getRequestBody()
	 * @generated
	 */
	int REQUEST_BODY = 15;

	/**
	 * The feature id for the '<em><b>Content</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUEST_BODY__CONTENT = 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUEST_BODY__DESCRIPTION = 1;

	/**
	 * The feature id for the '<em><b>Required</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUEST_BODY__REQUIRED = 2;

	/**
	 * The number of structural features of the '<em>Request Body</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUEST_BODY_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Request Body</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUEST_BODY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.openapi.model.impl.ItemsImpl <em>Items</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.openapi.model.impl.ItemsImpl
	 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getItems()
	 * @generated
	 */
	int ITEMS = 16;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITEMS__TYPE = 0;

	/**
	 * The feature id for the '<em><b>Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITEMS__FORMAT = 1;

	/**
	 * The feature id for the '<em><b>Enum</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITEMS__ENUM = 2;

	/**
	 * The number of structural features of the '<em>Items</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITEMS_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Items</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITEMS_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.openapi.model.impl.AdditionalPropertiesImpl <em>Additional Properties</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.openapi.model.impl.AdditionalPropertiesImpl
	 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getAdditionalProperties()
	 * @generated
	 */
	int ADDITIONAL_PROPERTIES = 17;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDITIONAL_PROPERTIES__TYPE = 0;

	/**
	 * The feature id for the '<em><b>Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDITIONAL_PROPERTIES__FORMAT = 1;

	/**
	 * The number of structural features of the '<em>Additional Properties</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDITIONAL_PROPERTIES_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Additional Properties</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDITIONAL_PROPERTIES_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.openapi.model.impl.ComponentsImpl <em>Components</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.openapi.model.impl.ComponentsImpl
	 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getComponents()
	 * @generated
	 */
	int COMPONENTS = 18;

	/**
	 * The feature id for the '<em><b>Security Schemes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENTS__SECURITY_SCHEMES = 0;

	/**
	 * The feature id for the '<em><b>Schemas</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENTS__SCHEMAS = 1;

	/**
	 * The number of structural features of the '<em>Components</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENTS_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Components</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENTS_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.openapi.model.impl.SecuritySchemesImpl <em>Security Schemes</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.openapi.model.impl.SecuritySchemesImpl
	 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getSecuritySchemes()
	 * @generated
	 */
	int SECURITY_SCHEMES = 19;

	/**
	 * The feature id for the '<em><b>Bearer Auth</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_SCHEMES__BEARER_AUTH = 0;

	/**
	 * The feature id for the '<em><b>Basic Auth</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_SCHEMES__BASIC_AUTH = 1;

	/**
	 * The feature id for the '<em><b>Api Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_SCHEMES__API_KEY = 2;

	/**
	 * The number of structural features of the '<em>Security Schemes</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_SCHEMES_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Security Schemes</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_SCHEMES_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.openapi.model.impl.BearerAuthImpl <em>Bearer Auth</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.openapi.model.impl.BearerAuthImpl
	 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getBearerAuth()
	 * @generated
	 */
	int BEARER_AUTH = 20;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEARER_AUTH__TYPE = 0;

	/**
	 * The feature id for the '<em><b>Scheme</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEARER_AUTH__SCHEME = 1;

	/**
	 * The feature id for the '<em><b>Bearer Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEARER_AUTH__BEARER_FORMAT = 2;

	/**
	 * The number of structural features of the '<em>Bearer Auth</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEARER_AUTH_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Bearer Auth</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEARER_AUTH_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.openapi.model.impl.BasicAuthImpl <em>Basic Auth</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.openapi.model.impl.BasicAuthImpl
	 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getBasicAuth()
	 * @generated
	 */
	int BASIC_AUTH = 21;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_AUTH__TYPE = 0;

	/**
	 * The feature id for the '<em><b>Scheme</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_AUTH__SCHEME = 1;

	/**
	 * The number of structural features of the '<em>Basic Auth</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_AUTH_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Basic Auth</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_AUTH_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.openapi.model.impl.ResponseImpl <em>Response</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.openapi.model.impl.ResponseImpl
	 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getResponse()
	 * @generated
	 */
	int RESPONSE = 22;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESPONSE__DESCRIPTION = 0;

	/**
	 * The feature id for the '<em><b>Content</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESPONSE__CONTENT = 1;

	/**
	 * The number of structural features of the '<em>Response</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESPONSE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Response</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESPONSE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.openapi.model.impl.MimeTypeImpl <em>Mime Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.openapi.model.impl.MimeTypeImpl
	 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getMimeType()
	 * @generated
	 */
	int MIME_TYPE = 23;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MIME_TYPE__SCHEMA = 0;

	/**
	 * The number of structural features of the '<em>Mime Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MIME_TYPE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Mime Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MIME_TYPE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.openapi.model.impl.OpenApiClassImpl <em>Class</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.openapi.model.impl.OpenApiClassImpl
	 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getOpenApiClass()
	 * @generated
	 */
	int OPEN_API_CLASS = 24;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPEN_API_CLASS__TYPE = 0;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPEN_API_CLASS__PROPERTIES = 1;

	/**
	 * The number of structural features of the '<em>Class</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPEN_API_CLASS_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Class</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPEN_API_CLASS_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.openapi.model.impl.PropertyImpl <em>Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.openapi.model.impl.PropertyImpl
	 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getProperty()
	 * @generated
	 */
	int PROPERTY = 25;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__TYPE = 0;

	/**
	 * The feature id for the '<em><b>Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__FORMAT = 1;

	/**
	 * The feature id for the '<em><b>Items</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__ITEMS = 2;

	/**
	 * The feature id for the '<em><b>Unique Items</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__UNIQUE_ITEMS = 3;

	/**
	 * The feature id for the '<em><b>Enum</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__ENUM = 4;

	/**
	 * The feature id for the '<em><b>Ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__REF = 5;

	/**
	 * The feature id for the '<em><b>Default</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__DEFAULT = 6;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__PROPERTIES = 7;

	/**
	 * The feature id for the '<em><b>Additional Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__ADDITIONAL_PROPERTIES = 8;

	/**
	 * The number of structural features of the '<em>Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_FEATURE_COUNT = 9;

	/**
	 * The number of operations of the '<em>Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.openapi.model.impl.StringToPathMapImpl <em>String To Path Map</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.openapi.model.impl.StringToPathMapImpl
	 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getStringToPathMap()
	 * @generated
	 */
	int STRING_TO_PATH_MAP = 26;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_PATH_MAP__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_PATH_MAP__VALUE = 1;

	/**
	 * The number of structural features of the '<em>String To Path Map</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_PATH_MAP_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>String To Path Map</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_PATH_MAP_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.openapi.model.impl.StringToPropertyMapImpl <em>String To Property Map</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.openapi.model.impl.StringToPropertyMapImpl
	 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getStringToPropertyMap()
	 * @generated
	 */
	int STRING_TO_PROPERTY_MAP = 27;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_PROPERTY_MAP__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_PROPERTY_MAP__VALUE = 1;

	/**
	 * The number of structural features of the '<em>String To Property Map</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_PROPERTY_MAP_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>String To Property Map</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_PROPERTY_MAP_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.openapi.model.impl.StringToResponseMapImpl <em>String To Response Map</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.openapi.model.impl.StringToResponseMapImpl
	 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getStringToResponseMap()
	 * @generated
	 */
	int STRING_TO_RESPONSE_MAP = 28;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_RESPONSE_MAP__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_RESPONSE_MAP__VALUE = 1;

	/**
	 * The number of structural features of the '<em>String To Response Map</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_RESPONSE_MAP_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>String To Response Map</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_RESPONSE_MAP_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.openapi.model.impl.StringToMimeTypeMapImpl <em>String To Mime Type Map</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.openapi.model.impl.StringToMimeTypeMapImpl
	 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getStringToMimeTypeMap()
	 * @generated
	 */
	int STRING_TO_MIME_TYPE_MAP = 29;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_MIME_TYPE_MAP__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_MIME_TYPE_MAP__VALUE = 1;

	/**
	 * The number of structural features of the '<em>String To Mime Type Map</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_MIME_TYPE_MAP_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>String To Mime Type Map</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_MIME_TYPE_MAP_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.openapi.model.impl.StringToOpenApiClassMapImpl <em>String To Open Api Class Map</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.openapi.model.impl.StringToOpenApiClassMapImpl
	 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getStringToOpenApiClassMap()
	 * @generated
	 */
	int STRING_TO_OPEN_API_CLASS_MAP = 30;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_OPEN_API_CLASS_MAP__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_OPEN_API_CLASS_MAP__VALUE = 1;

	/**
	 * The number of structural features of the '<em>String To Open Api Class Map</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_OPEN_API_CLASS_MAP_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>String To Open Api Class Map</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_OPEN_API_CLASS_MAP_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.openapi.model.impl.MimeTypeMapImpl <em>Mime Type Map</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.openapi.model.impl.MimeTypeMapImpl
	 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getMimeTypeMap()
	 * @generated
	 */
	int MIME_TYPE_MAP = 31;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MIME_TYPE_MAP__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MIME_TYPE_MAP__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Mime Type Map</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MIME_TYPE_MAP_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Mime Type Map</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MIME_TYPE_MAP_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.openapi.model.impl.ConstraintImpl <em>Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.openapi.model.impl.ConstraintImpl
	 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getConstraint()
	 * @generated
	 */
	int CONSTRAINT = 32;

	/**
	 * The number of structural features of the '<em>Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.openapi.model.impl.StringConstraintImpl <em>String Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.openapi.model.impl.StringConstraintImpl
	 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getStringConstraint()
	 * @generated
	 */
	int STRING_CONSTRAINT = 33;

	/**
	 * The number of structural features of the '<em>String Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_CONSTRAINT_FEATURE_COUNT = CONSTRAINT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>String Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_CONSTRAINT_OPERATION_COUNT = CONSTRAINT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.openapi.model.impl.NumericConstraintImpl <em>Numeric Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.openapi.model.impl.NumericConstraintImpl
	 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getNumericConstraint()
	 * @generated
	 */
	int NUMERIC_CONSTRAINT = 34;

	/**
	 * The number of structural features of the '<em>Numeric Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERIC_CONSTRAINT_FEATURE_COUNT = CONSTRAINT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Numeric Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERIC_CONSTRAINT_OPERATION_COUNT = CONSTRAINT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.openapi.model.impl.ArrayConstraintImpl <em>Array Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.openapi.model.impl.ArrayConstraintImpl
	 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getArrayConstraint()
	 * @generated
	 */
	int ARRAY_CONSTRAINT = 35;

	/**
	 * The number of structural features of the '<em>Array Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_CONSTRAINT_FEATURE_COUNT = CONSTRAINT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Array Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_CONSTRAINT_OPERATION_COUNT = CONSTRAINT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.openapi.model.impl.ExtensionImpl <em>Extension</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.openapi.model.impl.ExtensionImpl
	 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getExtension()
	 * @generated
	 */
	int EXTENSION = 36;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTENSION__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTENSION__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Extension</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTENSION_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Extension</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTENSION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fennec.openapi.model.impl.ApiKeyImpl <em>Api Key</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fennec.openapi.model.impl.ApiKeyImpl
	 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getApiKey()
	 * @generated
	 */
	int API_KEY = 37;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int API_KEY__TYPE = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int API_KEY__NAME = 1;

	/**
	 * The feature id for the '<em><b>In</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int API_KEY__IN = 2;

	/**
	 * The number of structural features of the '<em>Api Key</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int API_KEY_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Api Key</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int API_KEY_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.openapi.model.OpenApi <em>Open Api</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Open Api</em>'.
	 * @see org.eclipse.fennec.openapi.model.OpenApi
	 * @generated
	 */
	EClass getOpenApi();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.openapi.model.OpenApi#getOpenapi <em>Openapi</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Openapi</em>'.
	 * @see org.eclipse.fennec.openapi.model.OpenApi#getOpenapi()
	 * @see #getOpenApi()
	 * @generated
	 */
	EAttribute getOpenApi_Openapi();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fennec.openapi.model.OpenApi#getInfo <em>Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Info</em>'.
	 * @see org.eclipse.fennec.openapi.model.OpenApi#getInfo()
	 * @see #getOpenApi()
	 * @generated
	 */
	EReference getOpenApi_Info();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fennec.openapi.model.OpenApi#getServers <em>Servers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Servers</em>'.
	 * @see org.eclipse.fennec.openapi.model.OpenApi#getServers()
	 * @see #getOpenApi()
	 * @generated
	 */
	EReference getOpenApi_Servers();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.fennec.openapi.model.OpenApi#getPaths <em>Paths</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Paths</em>'.
	 * @see org.eclipse.fennec.openapi.model.OpenApi#getPaths()
	 * @see #getOpenApi()
	 * @generated
	 */
	EReference getOpenApi_Paths();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fennec.openapi.model.OpenApi#getComponents <em>Components</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Components</em>'.
	 * @see org.eclipse.fennec.openapi.model.OpenApi#getComponents()
	 * @see #getOpenApi()
	 * @generated
	 */
	EReference getOpenApi_Components();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fennec.openapi.model.OpenApi#getExternalDocs <em>External Docs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>External Docs</em>'.
	 * @see org.eclipse.fennec.openapi.model.OpenApi#getExternalDocs()
	 * @see #getOpenApi()
	 * @generated
	 */
	EReference getOpenApi_ExternalDocs();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fennec.openapi.model.OpenApi#getTags <em>Tags</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Tags</em>'.
	 * @see org.eclipse.fennec.openapi.model.OpenApi#getTags()
	 * @see #getOpenApi()
	 * @generated
	 */
	EReference getOpenApi_Tags();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.openapi.model.Info <em>Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Info</em>'.
	 * @see org.eclipse.fennec.openapi.model.Info
	 * @generated
	 */
	EClass getInfo();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.openapi.model.Info#getTitle <em>Title</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Title</em>'.
	 * @see org.eclipse.fennec.openapi.model.Info#getTitle()
	 * @see #getInfo()
	 * @generated
	 */
	EAttribute getInfo_Title();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.openapi.model.Info#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.fennec.openapi.model.Info#getDescription()
	 * @see #getInfo()
	 * @generated
	 */
	EAttribute getInfo_Description();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.openapi.model.Info#getTermsOfService <em>Terms Of Service</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Terms Of Service</em>'.
	 * @see org.eclipse.fennec.openapi.model.Info#getTermsOfService()
	 * @see #getInfo()
	 * @generated
	 */
	EAttribute getInfo_TermsOfService();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fennec.openapi.model.Info#getLicense <em>License</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>License</em>'.
	 * @see org.eclipse.fennec.openapi.model.Info#getLicense()
	 * @see #getInfo()
	 * @generated
	 */
	EReference getInfo_License();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fennec.openapi.model.Info#getContact <em>Contact</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Contact</em>'.
	 * @see org.eclipse.fennec.openapi.model.Info#getContact()
	 * @see #getInfo()
	 * @generated
	 */
	EReference getInfo_Contact();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.openapi.model.Info#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see org.eclipse.fennec.openapi.model.Info#getVersion()
	 * @see #getInfo()
	 * @generated
	 */
	EAttribute getInfo_Version();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.openapi.model.Contact <em>Contact</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Contact</em>'.
	 * @see org.eclipse.fennec.openapi.model.Contact
	 * @generated
	 */
	EClass getContact();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.openapi.model.Contact#getEmail <em>Email</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Email</em>'.
	 * @see org.eclipse.fennec.openapi.model.Contact#getEmail()
	 * @see #getContact()
	 * @generated
	 */
	EAttribute getContact_Email();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.openapi.model.License <em>License</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>License</em>'.
	 * @see org.eclipse.fennec.openapi.model.License
	 * @generated
	 */
	EClass getLicense();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.openapi.model.License#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.fennec.openapi.model.License#getName()
	 * @see #getLicense()
	 * @generated
	 */
	EAttribute getLicense_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.openapi.model.License#getUrl <em>Url</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Url</em>'.
	 * @see org.eclipse.fennec.openapi.model.License#getUrl()
	 * @see #getLicense()
	 * @generated
	 */
	EAttribute getLicense_Url();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.openapi.model.Docs <em>Docs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Docs</em>'.
	 * @see org.eclipse.fennec.openapi.model.Docs
	 * @generated
	 */
	EClass getDocs();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.openapi.model.Docs#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.fennec.openapi.model.Docs#getDescription()
	 * @see #getDocs()
	 * @generated
	 */
	EAttribute getDocs_Description();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.openapi.model.Docs#getUrl <em>Url</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Url</em>'.
	 * @see org.eclipse.fennec.openapi.model.Docs#getUrl()
	 * @see #getDocs()
	 * @generated
	 */
	EAttribute getDocs_Url();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.openapi.model.Tag <em>Tag</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Tag</em>'.
	 * @see org.eclipse.fennec.openapi.model.Tag
	 * @generated
	 */
	EClass getTag();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.openapi.model.Tag#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.fennec.openapi.model.Tag#getName()
	 * @see #getTag()
	 * @generated
	 */
	EAttribute getTag_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.openapi.model.Tag#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.fennec.openapi.model.Tag#getDescription()
	 * @see #getTag()
	 * @generated
	 */
	EAttribute getTag_Description();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fennec.openapi.model.Tag#getExternalDocs <em>External Docs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>External Docs</em>'.
	 * @see org.eclipse.fennec.openapi.model.Tag#getExternalDocs()
	 * @see #getTag()
	 * @generated
	 */
	EReference getTag_ExternalDocs();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.openapi.model.Server <em>Server</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Server</em>'.
	 * @see org.eclipse.fennec.openapi.model.Server
	 * @generated
	 */
	EClass getServer();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.openapi.model.Server#getUrl <em>Url</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Url</em>'.
	 * @see org.eclipse.fennec.openapi.model.Server#getUrl()
	 * @see #getServer()
	 * @generated
	 */
	EAttribute getServer_Url();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.openapi.model.Server#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.fennec.openapi.model.Server#getDescription()
	 * @see #getServer()
	 * @generated
	 */
	EAttribute getServer_Description();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.openapi.model.Path <em>Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Path</em>'.
	 * @see org.eclipse.fennec.openapi.model.Path
	 * @generated
	 */
	EClass getPath();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fennec.openapi.model.Path#getPut <em>Put</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Put</em>'.
	 * @see org.eclipse.fennec.openapi.model.Path#getPut()
	 * @see #getPath()
	 * @generated
	 */
	EReference getPath_Put();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fennec.openapi.model.Path#getGet <em>Get</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Get</em>'.
	 * @see org.eclipse.fennec.openapi.model.Path#getGet()
	 * @see #getPath()
	 * @generated
	 */
	EReference getPath_Get();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fennec.openapi.model.Path#getPost <em>Post</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Post</em>'.
	 * @see org.eclipse.fennec.openapi.model.Path#getPost()
	 * @see #getPath()
	 * @generated
	 */
	EReference getPath_Post();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fennec.openapi.model.Path#getDelete <em>Delete</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Delete</em>'.
	 * @see org.eclipse.fennec.openapi.model.Path#getDelete()
	 * @see #getPath()
	 * @generated
	 */
	EReference getPath_Delete();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.openapi.model.Parameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parameter</em>'.
	 * @see org.eclipse.fennec.openapi.model.Parameter
	 * @generated
	 */
	EClass getParameter();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.openapi.model.Parameter#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.fennec.openapi.model.Parameter#getName()
	 * @see #getParameter()
	 * @generated
	 */
	EAttribute getParameter_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.openapi.model.Parameter#getIn <em>In</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>In</em>'.
	 * @see org.eclipse.fennec.openapi.model.Parameter#getIn()
	 * @see #getParameter()
	 * @generated
	 */
	EAttribute getParameter_In();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.openapi.model.Parameter#isRequired <em>Required</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Required</em>'.
	 * @see org.eclipse.fennec.openapi.model.Parameter#isRequired()
	 * @see #getParameter()
	 * @generated
	 */
	EAttribute getParameter_Required();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fennec.openapi.model.Parameter#getSchema <em>Schema</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Schema</em>'.
	 * @see org.eclipse.fennec.openapi.model.Parameter#getSchema()
	 * @see #getParameter()
	 * @generated
	 */
	EReference getParameter_Schema();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.openapi.model.Parameter#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.fennec.openapi.model.Parameter#getDescription()
	 * @see #getParameter()
	 * @generated
	 */
	EAttribute getParameter_Description();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.openapi.model.Security <em>Security</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Security</em>'.
	 * @see org.eclipse.fennec.openapi.model.Security
	 * @generated
	 */
	EClass getSecurity();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.fennec.openapi.model.Security#getBearerAuth <em>Bearer Auth</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Bearer Auth</em>'.
	 * @see org.eclipse.fennec.openapi.model.Security#getBearerAuth()
	 * @see #getSecurity()
	 * @generated
	 */
	EAttribute getSecurity_BearerAuth();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.fennec.openapi.model.Security#getBasicAuth <em>Basic Auth</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Basic Auth</em>'.
	 * @see org.eclipse.fennec.openapi.model.Security#getBasicAuth()
	 * @see #getSecurity()
	 * @generated
	 */
	EAttribute getSecurity_BasicAuth();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.fennec.openapi.model.Security#getApiKey <em>Api Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Api Key</em>'.
	 * @see org.eclipse.fennec.openapi.model.Security#getApiKey()
	 * @see #getSecurity()
	 * @generated
	 */
	EAttribute getSecurity_ApiKey();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.openapi.model.Method <em>Method</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Method</em>'.
	 * @see org.eclipse.fennec.openapi.model.Method
	 * @generated
	 */
	EClass getMethod();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.fennec.openapi.model.Method#getTags <em>Tags</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Tags</em>'.
	 * @see org.eclipse.fennec.openapi.model.Method#getTags()
	 * @see #getMethod()
	 * @generated
	 */
	EAttribute getMethod_Tags();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.openapi.model.Method#getSummary <em>Summary</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Summary</em>'.
	 * @see org.eclipse.fennec.openapi.model.Method#getSummary()
	 * @see #getMethod()
	 * @generated
	 */
	EAttribute getMethod_Summary();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.openapi.model.Method#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.fennec.openapi.model.Method#getDescription()
	 * @see #getMethod()
	 * @generated
	 */
	EAttribute getMethod_Description();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.openapi.model.Method#getOperationId <em>Operation Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operation Id</em>'.
	 * @see org.eclipse.fennec.openapi.model.Method#getOperationId()
	 * @see #getMethod()
	 * @generated
	 */
	EAttribute getMethod_OperationId();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fennec.openapi.model.Method#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see org.eclipse.fennec.openapi.model.Method#getParameters()
	 * @see #getMethod()
	 * @generated
	 */
	EReference getMethod_Parameters();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.fennec.openapi.model.Method#getResponses <em>Responses</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Responses</em>'.
	 * @see org.eclipse.fennec.openapi.model.Method#getResponses()
	 * @see #getMethod()
	 * @generated
	 */
	EReference getMethod_Responses();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fennec.openapi.model.Method#getSecurity <em>Security</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Security</em>'.
	 * @see org.eclipse.fennec.openapi.model.Method#getSecurity()
	 * @see #getMethod()
	 * @generated
	 */
	EReference getMethod_Security();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.openapi.model.Put <em>Put</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Put</em>'.
	 * @see org.eclipse.fennec.openapi.model.Put
	 * @generated
	 */
	EClass getPut();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fennec.openapi.model.Put#getRequestBody <em>Request Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Request Body</em>'.
	 * @see org.eclipse.fennec.openapi.model.Put#getRequestBody()
	 * @see #getPut()
	 * @generated
	 */
	EReference getPut_RequestBody();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.openapi.model.Post <em>Post</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Post</em>'.
	 * @see org.eclipse.fennec.openapi.model.Post
	 * @generated
	 */
	EClass getPost();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fennec.openapi.model.Post#getRequestBody <em>Request Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Request Body</em>'.
	 * @see org.eclipse.fennec.openapi.model.Post#getRequestBody()
	 * @see #getPost()
	 * @generated
	 */
	EReference getPost_RequestBody();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.openapi.model.Delete <em>Delete</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Delete</em>'.
	 * @see org.eclipse.fennec.openapi.model.Delete
	 * @generated
	 */
	EClass getDelete();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.openapi.model.Get <em>Get</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Get</em>'.
	 * @see org.eclipse.fennec.openapi.model.Get
	 * @generated
	 */
	EClass getGet();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.openapi.model.RequestBody <em>Request Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Request Body</em>'.
	 * @see org.eclipse.fennec.openapi.model.RequestBody
	 * @generated
	 */
	EClass getRequestBody();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.fennec.openapi.model.RequestBody#getContent <em>Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Content</em>'.
	 * @see org.eclipse.fennec.openapi.model.RequestBody#getContent()
	 * @see #getRequestBody()
	 * @generated
	 */
	EReference getRequestBody_Content();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.openapi.model.RequestBody#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.fennec.openapi.model.RequestBody#getDescription()
	 * @see #getRequestBody()
	 * @generated
	 */
	EAttribute getRequestBody_Description();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.openapi.model.RequestBody#isRequired <em>Required</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Required</em>'.
	 * @see org.eclipse.fennec.openapi.model.RequestBody#isRequired()
	 * @see #getRequestBody()
	 * @generated
	 */
	EAttribute getRequestBody_Required();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.openapi.model.Items <em>Items</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Items</em>'.
	 * @see org.eclipse.fennec.openapi.model.Items
	 * @generated
	 */
	EClass getItems();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.openapi.model.Items#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.fennec.openapi.model.Items#getType()
	 * @see #getItems()
	 * @generated
	 */
	EAttribute getItems_Type();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.openapi.model.Items#getFormat <em>Format</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Format</em>'.
	 * @see org.eclipse.fennec.openapi.model.Items#getFormat()
	 * @see #getItems()
	 * @generated
	 */
	EAttribute getItems_Format();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.fennec.openapi.model.Items#getEnum <em>Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Enum</em>'.
	 * @see org.eclipse.fennec.openapi.model.Items#getEnum()
	 * @see #getItems()
	 * @generated
	 */
	EAttribute getItems_Enum();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.openapi.model.AdditionalProperties <em>Additional Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Additional Properties</em>'.
	 * @see org.eclipse.fennec.openapi.model.AdditionalProperties
	 * @generated
	 */
	EClass getAdditionalProperties();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.openapi.model.AdditionalProperties#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.fennec.openapi.model.AdditionalProperties#getType()
	 * @see #getAdditionalProperties()
	 * @generated
	 */
	EAttribute getAdditionalProperties_Type();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.openapi.model.AdditionalProperties#getFormat <em>Format</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Format</em>'.
	 * @see org.eclipse.fennec.openapi.model.AdditionalProperties#getFormat()
	 * @see #getAdditionalProperties()
	 * @generated
	 */
	EAttribute getAdditionalProperties_Format();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.openapi.model.Components <em>Components</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Components</em>'.
	 * @see org.eclipse.fennec.openapi.model.Components
	 * @generated
	 */
	EClass getComponents();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fennec.openapi.model.Components#getSecuritySchemes <em>Security Schemes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Security Schemes</em>'.
	 * @see org.eclipse.fennec.openapi.model.Components#getSecuritySchemes()
	 * @see #getComponents()
	 * @generated
	 */
	EReference getComponents_SecuritySchemes();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fennec.openapi.model.Components#getSchemas <em>Schemas</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Schemas</em>'.
	 * @see org.eclipse.fennec.openapi.model.Components#getSchemas()
	 * @see #getComponents()
	 * @generated
	 */
	EReference getComponents_Schemas();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.openapi.model.SecuritySchemes <em>Security Schemes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Security Schemes</em>'.
	 * @see org.eclipse.fennec.openapi.model.SecuritySchemes
	 * @generated
	 */
	EClass getSecuritySchemes();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fennec.openapi.model.SecuritySchemes#getBearerAuth <em>Bearer Auth</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Bearer Auth</em>'.
	 * @see org.eclipse.fennec.openapi.model.SecuritySchemes#getBearerAuth()
	 * @see #getSecuritySchemes()
	 * @generated
	 */
	EReference getSecuritySchemes_BearerAuth();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fennec.openapi.model.SecuritySchemes#getBasicAuth <em>Basic Auth</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Basic Auth</em>'.
	 * @see org.eclipse.fennec.openapi.model.SecuritySchemes#getBasicAuth()
	 * @see #getSecuritySchemes()
	 * @generated
	 */
	EReference getSecuritySchemes_BasicAuth();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fennec.openapi.model.SecuritySchemes#getApiKey <em>Api Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Api Key</em>'.
	 * @see org.eclipse.fennec.openapi.model.SecuritySchemes#getApiKey()
	 * @see #getSecuritySchemes()
	 * @generated
	 */
	EReference getSecuritySchemes_ApiKey();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.openapi.model.BearerAuth <em>Bearer Auth</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Bearer Auth</em>'.
	 * @see org.eclipse.fennec.openapi.model.BearerAuth
	 * @generated
	 */
	EClass getBearerAuth();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.openapi.model.BearerAuth#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.fennec.openapi.model.BearerAuth#getType()
	 * @see #getBearerAuth()
	 * @generated
	 */
	EAttribute getBearerAuth_Type();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.openapi.model.BearerAuth#getScheme <em>Scheme</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Scheme</em>'.
	 * @see org.eclipse.fennec.openapi.model.BearerAuth#getScheme()
	 * @see #getBearerAuth()
	 * @generated
	 */
	EAttribute getBearerAuth_Scheme();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.openapi.model.BearerAuth#getBearerFormat <em>Bearer Format</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Bearer Format</em>'.
	 * @see org.eclipse.fennec.openapi.model.BearerAuth#getBearerFormat()
	 * @see #getBearerAuth()
	 * @generated
	 */
	EAttribute getBearerAuth_BearerFormat();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.openapi.model.BasicAuth <em>Basic Auth</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Basic Auth</em>'.
	 * @see org.eclipse.fennec.openapi.model.BasicAuth
	 * @generated
	 */
	EClass getBasicAuth();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.openapi.model.BasicAuth#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.fennec.openapi.model.BasicAuth#getType()
	 * @see #getBasicAuth()
	 * @generated
	 */
	EAttribute getBasicAuth_Type();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.openapi.model.BasicAuth#getScheme <em>Scheme</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Scheme</em>'.
	 * @see org.eclipse.fennec.openapi.model.BasicAuth#getScheme()
	 * @see #getBasicAuth()
	 * @generated
	 */
	EAttribute getBasicAuth_Scheme();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.openapi.model.Response <em>Response</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Response</em>'.
	 * @see org.eclipse.fennec.openapi.model.Response
	 * @generated
	 */
	EClass getResponse();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.openapi.model.Response#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.fennec.openapi.model.Response#getDescription()
	 * @see #getResponse()
	 * @generated
	 */
	EAttribute getResponse_Description();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.fennec.openapi.model.Response#getContent <em>Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Content</em>'.
	 * @see org.eclipse.fennec.openapi.model.Response#getContent()
	 * @see #getResponse()
	 * @generated
	 */
	EReference getResponse_Content();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.openapi.model.MimeType <em>Mime Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Mime Type</em>'.
	 * @see org.eclipse.fennec.openapi.model.MimeType
	 * @generated
	 */
	EClass getMimeType();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fennec.openapi.model.MimeType#getSchema <em>Schema</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Schema</em>'.
	 * @see org.eclipse.fennec.openapi.model.MimeType#getSchema()
	 * @see #getMimeType()
	 * @generated
	 */
	EReference getMimeType_Schema();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.openapi.model.OpenApiClass <em>Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Class</em>'.
	 * @see org.eclipse.fennec.openapi.model.OpenApiClass
	 * @generated
	 */
	EClass getOpenApiClass();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.openapi.model.OpenApiClass#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.fennec.openapi.model.OpenApiClass#getType()
	 * @see #getOpenApiClass()
	 * @generated
	 */
	EAttribute getOpenApiClass_Type();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.fennec.openapi.model.OpenApiClass#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Properties</em>'.
	 * @see org.eclipse.fennec.openapi.model.OpenApiClass#getProperties()
	 * @see #getOpenApiClass()
	 * @generated
	 */
	EReference getOpenApiClass_Properties();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.openapi.model.Property <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property</em>'.
	 * @see org.eclipse.fennec.openapi.model.Property
	 * @generated
	 */
	EClass getProperty();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.openapi.model.Property#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.fennec.openapi.model.Property#getType()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_Type();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.openapi.model.Property#getFormat <em>Format</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Format</em>'.
	 * @see org.eclipse.fennec.openapi.model.Property#getFormat()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_Format();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fennec.openapi.model.Property#getItems <em>Items</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Items</em>'.
	 * @see org.eclipse.fennec.openapi.model.Property#getItems()
	 * @see #getProperty()
	 * @generated
	 */
	EReference getProperty_Items();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.openapi.model.Property#isUniqueItems <em>Unique Items</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Items</em>'.
	 * @see org.eclipse.fennec.openapi.model.Property#isUniqueItems()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_UniqueItems();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.fennec.openapi.model.Property#getEnum <em>Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Enum</em>'.
	 * @see org.eclipse.fennec.openapi.model.Property#getEnum()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_Enum();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.openapi.model.Property#getRef <em>Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ref</em>'.
	 * @see org.eclipse.fennec.openapi.model.Property#getRef()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_Ref();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.openapi.model.Property#getDefault <em>Default</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default</em>'.
	 * @see org.eclipse.fennec.openapi.model.Property#getDefault()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_Default();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.fennec.openapi.model.Property#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Properties</em>'.
	 * @see org.eclipse.fennec.openapi.model.Property#getProperties()
	 * @see #getProperty()
	 * @generated
	 */
	EReference getProperty_Properties();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fennec.openapi.model.Property#getAdditionalProperties <em>Additional Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Additional Properties</em>'.
	 * @see org.eclipse.fennec.openapi.model.Property#getAdditionalProperties()
	 * @see #getProperty()
	 * @generated
	 */
	EReference getProperty_AdditionalProperties();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>String To Path Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String To Path Map</em>'.
	 * @see java.util.Map.Entry
	 * @model keyDataType="org.eclipse.emf.ecore.EString"
	 *        valueType="org.eclipse.fennec.openapi.model.Path"
	 * @generated
	 */
	EClass getStringToPathMap();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getStringToPathMap()
	 * @generated
	 */
	EAttribute getStringToPathMap_Key();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getStringToPathMap()
	 * @generated
	 */
	EReference getStringToPathMap_Value();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>String To Property Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String To Property Map</em>'.
	 * @see java.util.Map.Entry
	 * @model keyDataType="org.eclipse.emf.ecore.EString"
	 *        valueType="org.eclipse.fennec.openapi.model.Property"
	 * @generated
	 */
	EClass getStringToPropertyMap();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getStringToPropertyMap()
	 * @generated
	 */
	EAttribute getStringToPropertyMap_Key();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getStringToPropertyMap()
	 * @generated
	 */
	EReference getStringToPropertyMap_Value();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>String To Response Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String To Response Map</em>'.
	 * @see java.util.Map.Entry
	 * @model keyDataType="org.eclipse.emf.ecore.EString"
	 *        valueType="org.eclipse.fennec.openapi.model.Response" valueContainment="true"
	 * @generated
	 */
	EClass getStringToResponseMap();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getStringToResponseMap()
	 * @generated
	 */
	EAttribute getStringToResponseMap_Key();

	/**
	 * Returns the meta object for the containment reference '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getStringToResponseMap()
	 * @generated
	 */
	EReference getStringToResponseMap_Value();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>String To Mime Type Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String To Mime Type Map</em>'.
	 * @see java.util.Map.Entry
	 * @model keyDataType="org.eclipse.emf.ecore.EString"
	 *        valueType="org.eclipse.fennec.openapi.model.MimeType"
	 * @generated
	 */
	EClass getStringToMimeTypeMap();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getStringToMimeTypeMap()
	 * @generated
	 */
	EAttribute getStringToMimeTypeMap_Key();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getStringToMimeTypeMap()
	 * @generated
	 */
	EReference getStringToMimeTypeMap_Value();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>String To Open Api Class Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String To Open Api Class Map</em>'.
	 * @see java.util.Map.Entry
	 * @model keyDataType="org.eclipse.emf.ecore.EString"
	 *        valueType="org.eclipse.fennec.openapi.model.OpenApiClass" valueContainment="true"
	 * @generated
	 */
	EClass getStringToOpenApiClassMap();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getStringToOpenApiClassMap()
	 * @generated
	 */
	EAttribute getStringToOpenApiClassMap_Key();

	/**
	 * Returns the meta object for the containment reference '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getStringToOpenApiClassMap()
	 * @generated
	 */
	EReference getStringToOpenApiClassMap_Value();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>Mime Type Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Mime Type Map</em>'.
	 * @see java.util.Map.Entry
	 * @model keyDataType="org.eclipse.emf.ecore.EString"
	 *        valueType="org.eclipse.fennec.openapi.model.MimeType"
	 * @generated
	 */
	EClass getMimeTypeMap();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getMimeTypeMap()
	 * @generated
	 */
	EAttribute getMimeTypeMap_Key();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getMimeTypeMap()
	 * @generated
	 */
	EReference getMimeTypeMap_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.openapi.model.Constraint <em>Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Constraint</em>'.
	 * @see org.eclipse.fennec.openapi.model.Constraint
	 * @generated
	 */
	EClass getConstraint();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.openapi.model.StringConstraint <em>String Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String Constraint</em>'.
	 * @see org.eclipse.fennec.openapi.model.StringConstraint
	 * @generated
	 */
	EClass getStringConstraint();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.openapi.model.NumericConstraint <em>Numeric Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Numeric Constraint</em>'.
	 * @see org.eclipse.fennec.openapi.model.NumericConstraint
	 * @generated
	 */
	EClass getNumericConstraint();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.openapi.model.ArrayConstraint <em>Array Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Array Constraint</em>'.
	 * @see org.eclipse.fennec.openapi.model.ArrayConstraint
	 * @generated
	 */
	EClass getArrayConstraint();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.openapi.model.Extension <em>Extension</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Extension</em>'.
	 * @see org.eclipse.fennec.openapi.model.Extension
	 * @generated
	 */
	EClass getExtension();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.openapi.model.Extension#getKey <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see org.eclipse.fennec.openapi.model.Extension#getKey()
	 * @see #getExtension()
	 * @generated
	 */
	EAttribute getExtension_Key();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fennec.openapi.model.Extension#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see org.eclipse.fennec.openapi.model.Extension#getValue()
	 * @see #getExtension()
	 * @generated
	 */
	EReference getExtension_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fennec.openapi.model.ApiKey <em>Api Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Api Key</em>'.
	 * @see org.eclipse.fennec.openapi.model.ApiKey
	 * @generated
	 */
	EClass getApiKey();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.openapi.model.ApiKey#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.fennec.openapi.model.ApiKey#getType()
	 * @see #getApiKey()
	 * @generated
	 */
	EAttribute getApiKey_Type();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.openapi.model.ApiKey#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.fennec.openapi.model.ApiKey#getName()
	 * @see #getApiKey()
	 * @generated
	 */
	EAttribute getApiKey_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fennec.openapi.model.ApiKey#getIn <em>In</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>In</em>'.
	 * @see org.eclipse.fennec.openapi.model.ApiKey#getIn()
	 * @see #getApiKey()
	 * @generated
	 */
	EAttribute getApiKey_In();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	OpenApiFactory getOpenApiFactory();

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
		 * The meta object literal for the '{@link org.eclipse.fennec.openapi.model.impl.OpenApiImpl <em>Open Api</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.openapi.model.impl.OpenApiImpl
		 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getOpenApi()
		 * @generated
		 */
		EClass OPEN_API = eINSTANCE.getOpenApi();

		/**
		 * The meta object literal for the '<em><b>Openapi</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OPEN_API__OPENAPI = eINSTANCE.getOpenApi_Openapi();

		/**
		 * The meta object literal for the '<em><b>Info</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OPEN_API__INFO = eINSTANCE.getOpenApi_Info();

		/**
		 * The meta object literal for the '<em><b>Servers</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OPEN_API__SERVERS = eINSTANCE.getOpenApi_Servers();

		/**
		 * The meta object literal for the '<em><b>Paths</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OPEN_API__PATHS = eINSTANCE.getOpenApi_Paths();

		/**
		 * The meta object literal for the '<em><b>Components</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OPEN_API__COMPONENTS = eINSTANCE.getOpenApi_Components();

		/**
		 * The meta object literal for the '<em><b>External Docs</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OPEN_API__EXTERNAL_DOCS = eINSTANCE.getOpenApi_ExternalDocs();

		/**
		 * The meta object literal for the '<em><b>Tags</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OPEN_API__TAGS = eINSTANCE.getOpenApi_Tags();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.openapi.model.impl.InfoImpl <em>Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.openapi.model.impl.InfoImpl
		 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getInfo()
		 * @generated
		 */
		EClass INFO = eINSTANCE.getInfo();

		/**
		 * The meta object literal for the '<em><b>Title</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INFO__TITLE = eINSTANCE.getInfo_Title();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INFO__DESCRIPTION = eINSTANCE.getInfo_Description();

		/**
		 * The meta object literal for the '<em><b>Terms Of Service</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INFO__TERMS_OF_SERVICE = eINSTANCE.getInfo_TermsOfService();

		/**
		 * The meta object literal for the '<em><b>License</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INFO__LICENSE = eINSTANCE.getInfo_License();

		/**
		 * The meta object literal for the '<em><b>Contact</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INFO__CONTACT = eINSTANCE.getInfo_Contact();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INFO__VERSION = eINSTANCE.getInfo_Version();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.openapi.model.impl.ContactImpl <em>Contact</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.openapi.model.impl.ContactImpl
		 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getContact()
		 * @generated
		 */
		EClass CONTACT = eINSTANCE.getContact();

		/**
		 * The meta object literal for the '<em><b>Email</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONTACT__EMAIL = eINSTANCE.getContact_Email();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.openapi.model.impl.LicenseImpl <em>License</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.openapi.model.impl.LicenseImpl
		 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getLicense()
		 * @generated
		 */
		EClass LICENSE = eINSTANCE.getLicense();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LICENSE__NAME = eINSTANCE.getLicense_Name();

		/**
		 * The meta object literal for the '<em><b>Url</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LICENSE__URL = eINSTANCE.getLicense_Url();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.openapi.model.impl.DocsImpl <em>Docs</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.openapi.model.impl.DocsImpl
		 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getDocs()
		 * @generated
		 */
		EClass DOCS = eINSTANCE.getDocs();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCS__DESCRIPTION = eINSTANCE.getDocs_Description();

		/**
		 * The meta object literal for the '<em><b>Url</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCS__URL = eINSTANCE.getDocs_Url();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.openapi.model.impl.TagImpl <em>Tag</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.openapi.model.impl.TagImpl
		 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getTag()
		 * @generated
		 */
		EClass TAG = eINSTANCE.getTag();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TAG__NAME = eINSTANCE.getTag_Name();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TAG__DESCRIPTION = eINSTANCE.getTag_Description();

		/**
		 * The meta object literal for the '<em><b>External Docs</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TAG__EXTERNAL_DOCS = eINSTANCE.getTag_ExternalDocs();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.openapi.model.impl.ServerImpl <em>Server</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.openapi.model.impl.ServerImpl
		 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getServer()
		 * @generated
		 */
		EClass SERVER = eINSTANCE.getServer();

		/**
		 * The meta object literal for the '<em><b>Url</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SERVER__URL = eINSTANCE.getServer_Url();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SERVER__DESCRIPTION = eINSTANCE.getServer_Description();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.openapi.model.impl.PathImpl <em>Path</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.openapi.model.impl.PathImpl
		 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getPath()
		 * @generated
		 */
		EClass PATH = eINSTANCE.getPath();

		/**
		 * The meta object literal for the '<em><b>Put</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PATH__PUT = eINSTANCE.getPath_Put();

		/**
		 * The meta object literal for the '<em><b>Get</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PATH__GET = eINSTANCE.getPath_Get();

		/**
		 * The meta object literal for the '<em><b>Post</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PATH__POST = eINSTANCE.getPath_Post();

		/**
		 * The meta object literal for the '<em><b>Delete</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PATH__DELETE = eINSTANCE.getPath_Delete();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.openapi.model.impl.ParameterImpl <em>Parameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.openapi.model.impl.ParameterImpl
		 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getParameter()
		 * @generated
		 */
		EClass PARAMETER = eINSTANCE.getParameter();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER__NAME = eINSTANCE.getParameter_Name();

		/**
		 * The meta object literal for the '<em><b>In</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER__IN = eINSTANCE.getParameter_In();

		/**
		 * The meta object literal for the '<em><b>Required</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER__REQUIRED = eINSTANCE.getParameter_Required();

		/**
		 * The meta object literal for the '<em><b>Schema</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PARAMETER__SCHEMA = eINSTANCE.getParameter_Schema();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER__DESCRIPTION = eINSTANCE.getParameter_Description();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.openapi.model.impl.SecurityImpl <em>Security</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.openapi.model.impl.SecurityImpl
		 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getSecurity()
		 * @generated
		 */
		EClass SECURITY = eINSTANCE.getSecurity();

		/**
		 * The meta object literal for the '<em><b>Bearer Auth</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SECURITY__BEARER_AUTH = eINSTANCE.getSecurity_BearerAuth();

		/**
		 * The meta object literal for the '<em><b>Basic Auth</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SECURITY__BASIC_AUTH = eINSTANCE.getSecurity_BasicAuth();

		/**
		 * The meta object literal for the '<em><b>Api Key</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SECURITY__API_KEY = eINSTANCE.getSecurity_ApiKey();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.openapi.model.impl.MethodImpl <em>Method</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.openapi.model.impl.MethodImpl
		 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getMethod()
		 * @generated
		 */
		EClass METHOD = eINSTANCE.getMethod();

		/**
		 * The meta object literal for the '<em><b>Tags</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute METHOD__TAGS = eINSTANCE.getMethod_Tags();

		/**
		 * The meta object literal for the '<em><b>Summary</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute METHOD__SUMMARY = eINSTANCE.getMethod_Summary();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute METHOD__DESCRIPTION = eINSTANCE.getMethod_Description();

		/**
		 * The meta object literal for the '<em><b>Operation Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute METHOD__OPERATION_ID = eINSTANCE.getMethod_OperationId();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference METHOD__PARAMETERS = eINSTANCE.getMethod_Parameters();

		/**
		 * The meta object literal for the '<em><b>Responses</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference METHOD__RESPONSES = eINSTANCE.getMethod_Responses();

		/**
		 * The meta object literal for the '<em><b>Security</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference METHOD__SECURITY = eINSTANCE.getMethod_Security();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.openapi.model.impl.PutImpl <em>Put</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.openapi.model.impl.PutImpl
		 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getPut()
		 * @generated
		 */
		EClass PUT = eINSTANCE.getPut();

		/**
		 * The meta object literal for the '<em><b>Request Body</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PUT__REQUEST_BODY = eINSTANCE.getPut_RequestBody();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.openapi.model.impl.PostImpl <em>Post</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.openapi.model.impl.PostImpl
		 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getPost()
		 * @generated
		 */
		EClass POST = eINSTANCE.getPost();

		/**
		 * The meta object literal for the '<em><b>Request Body</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POST__REQUEST_BODY = eINSTANCE.getPost_RequestBody();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.openapi.model.impl.DeleteImpl <em>Delete</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.openapi.model.impl.DeleteImpl
		 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getDelete()
		 * @generated
		 */
		EClass DELETE = eINSTANCE.getDelete();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.openapi.model.impl.GetImpl <em>Get</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.openapi.model.impl.GetImpl
		 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getGet()
		 * @generated
		 */
		EClass GET = eINSTANCE.getGet();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.openapi.model.impl.RequestBodyImpl <em>Request Body</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.openapi.model.impl.RequestBodyImpl
		 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getRequestBody()
		 * @generated
		 */
		EClass REQUEST_BODY = eINSTANCE.getRequestBody();

		/**
		 * The meta object literal for the '<em><b>Content</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REQUEST_BODY__CONTENT = eINSTANCE.getRequestBody_Content();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REQUEST_BODY__DESCRIPTION = eINSTANCE.getRequestBody_Description();

		/**
		 * The meta object literal for the '<em><b>Required</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REQUEST_BODY__REQUIRED = eINSTANCE.getRequestBody_Required();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.openapi.model.impl.ItemsImpl <em>Items</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.openapi.model.impl.ItemsImpl
		 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getItems()
		 * @generated
		 */
		EClass ITEMS = eINSTANCE.getItems();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ITEMS__TYPE = eINSTANCE.getItems_Type();

		/**
		 * The meta object literal for the '<em><b>Format</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ITEMS__FORMAT = eINSTANCE.getItems_Format();

		/**
		 * The meta object literal for the '<em><b>Enum</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ITEMS__ENUM = eINSTANCE.getItems_Enum();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.openapi.model.impl.AdditionalPropertiesImpl <em>Additional Properties</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.openapi.model.impl.AdditionalPropertiesImpl
		 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getAdditionalProperties()
		 * @generated
		 */
		EClass ADDITIONAL_PROPERTIES = eINSTANCE.getAdditionalProperties();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADDITIONAL_PROPERTIES__TYPE = eINSTANCE.getAdditionalProperties_Type();

		/**
		 * The meta object literal for the '<em><b>Format</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADDITIONAL_PROPERTIES__FORMAT = eINSTANCE.getAdditionalProperties_Format();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.openapi.model.impl.ComponentsImpl <em>Components</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.openapi.model.impl.ComponentsImpl
		 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getComponents()
		 * @generated
		 */
		EClass COMPONENTS = eINSTANCE.getComponents();

		/**
		 * The meta object literal for the '<em><b>Security Schemes</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPONENTS__SECURITY_SCHEMES = eINSTANCE.getComponents_SecuritySchemes();

		/**
		 * The meta object literal for the '<em><b>Schemas</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPONENTS__SCHEMAS = eINSTANCE.getComponents_Schemas();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.openapi.model.impl.SecuritySchemesImpl <em>Security Schemes</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.openapi.model.impl.SecuritySchemesImpl
		 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getSecuritySchemes()
		 * @generated
		 */
		EClass SECURITY_SCHEMES = eINSTANCE.getSecuritySchemes();

		/**
		 * The meta object literal for the '<em><b>Bearer Auth</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SECURITY_SCHEMES__BEARER_AUTH = eINSTANCE.getSecuritySchemes_BearerAuth();

		/**
		 * The meta object literal for the '<em><b>Basic Auth</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SECURITY_SCHEMES__BASIC_AUTH = eINSTANCE.getSecuritySchemes_BasicAuth();

		/**
		 * The meta object literal for the '<em><b>Api Key</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SECURITY_SCHEMES__API_KEY = eINSTANCE.getSecuritySchemes_ApiKey();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.openapi.model.impl.BearerAuthImpl <em>Bearer Auth</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.openapi.model.impl.BearerAuthImpl
		 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getBearerAuth()
		 * @generated
		 */
		EClass BEARER_AUTH = eINSTANCE.getBearerAuth();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BEARER_AUTH__TYPE = eINSTANCE.getBearerAuth_Type();

		/**
		 * The meta object literal for the '<em><b>Scheme</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BEARER_AUTH__SCHEME = eINSTANCE.getBearerAuth_Scheme();

		/**
		 * The meta object literal for the '<em><b>Bearer Format</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BEARER_AUTH__BEARER_FORMAT = eINSTANCE.getBearerAuth_BearerFormat();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.openapi.model.impl.BasicAuthImpl <em>Basic Auth</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.openapi.model.impl.BasicAuthImpl
		 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getBasicAuth()
		 * @generated
		 */
		EClass BASIC_AUTH = eINSTANCE.getBasicAuth();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BASIC_AUTH__TYPE = eINSTANCE.getBasicAuth_Type();

		/**
		 * The meta object literal for the '<em><b>Scheme</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BASIC_AUTH__SCHEME = eINSTANCE.getBasicAuth_Scheme();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.openapi.model.impl.ResponseImpl <em>Response</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.openapi.model.impl.ResponseImpl
		 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getResponse()
		 * @generated
		 */
		EClass RESPONSE = eINSTANCE.getResponse();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RESPONSE__DESCRIPTION = eINSTANCE.getResponse_Description();

		/**
		 * The meta object literal for the '<em><b>Content</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RESPONSE__CONTENT = eINSTANCE.getResponse_Content();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.openapi.model.impl.MimeTypeImpl <em>Mime Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.openapi.model.impl.MimeTypeImpl
		 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getMimeType()
		 * @generated
		 */
		EClass MIME_TYPE = eINSTANCE.getMimeType();

		/**
		 * The meta object literal for the '<em><b>Schema</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MIME_TYPE__SCHEMA = eINSTANCE.getMimeType_Schema();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.openapi.model.impl.OpenApiClassImpl <em>Class</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.openapi.model.impl.OpenApiClassImpl
		 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getOpenApiClass()
		 * @generated
		 */
		EClass OPEN_API_CLASS = eINSTANCE.getOpenApiClass();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OPEN_API_CLASS__TYPE = eINSTANCE.getOpenApiClass_Type();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OPEN_API_CLASS__PROPERTIES = eINSTANCE.getOpenApiClass_Properties();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.openapi.model.impl.PropertyImpl <em>Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.openapi.model.impl.PropertyImpl
		 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getProperty()
		 * @generated
		 */
		EClass PROPERTY = eINSTANCE.getProperty();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY__TYPE = eINSTANCE.getProperty_Type();

		/**
		 * The meta object literal for the '<em><b>Format</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY__FORMAT = eINSTANCE.getProperty_Format();

		/**
		 * The meta object literal for the '<em><b>Items</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY__ITEMS = eINSTANCE.getProperty_Items();

		/**
		 * The meta object literal for the '<em><b>Unique Items</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY__UNIQUE_ITEMS = eINSTANCE.getProperty_UniqueItems();

		/**
		 * The meta object literal for the '<em><b>Enum</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY__ENUM = eINSTANCE.getProperty_Enum();

		/**
		 * The meta object literal for the '<em><b>Ref</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY__REF = eINSTANCE.getProperty_Ref();

		/**
		 * The meta object literal for the '<em><b>Default</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY__DEFAULT = eINSTANCE.getProperty_Default();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY__PROPERTIES = eINSTANCE.getProperty_Properties();

		/**
		 * The meta object literal for the '<em><b>Additional Properties</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY__ADDITIONAL_PROPERTIES = eINSTANCE.getProperty_AdditionalProperties();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.openapi.model.impl.StringToPathMapImpl <em>String To Path Map</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.openapi.model.impl.StringToPathMapImpl
		 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getStringToPathMap()
		 * @generated
		 */
		EClass STRING_TO_PATH_MAP = eINSTANCE.getStringToPathMap();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRING_TO_PATH_MAP__KEY = eINSTANCE.getStringToPathMap_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STRING_TO_PATH_MAP__VALUE = eINSTANCE.getStringToPathMap_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.openapi.model.impl.StringToPropertyMapImpl <em>String To Property Map</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.openapi.model.impl.StringToPropertyMapImpl
		 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getStringToPropertyMap()
		 * @generated
		 */
		EClass STRING_TO_PROPERTY_MAP = eINSTANCE.getStringToPropertyMap();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRING_TO_PROPERTY_MAP__KEY = eINSTANCE.getStringToPropertyMap_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STRING_TO_PROPERTY_MAP__VALUE = eINSTANCE.getStringToPropertyMap_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.openapi.model.impl.StringToResponseMapImpl <em>String To Response Map</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.openapi.model.impl.StringToResponseMapImpl
		 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getStringToResponseMap()
		 * @generated
		 */
		EClass STRING_TO_RESPONSE_MAP = eINSTANCE.getStringToResponseMap();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRING_TO_RESPONSE_MAP__KEY = eINSTANCE.getStringToResponseMap_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STRING_TO_RESPONSE_MAP__VALUE = eINSTANCE.getStringToResponseMap_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.openapi.model.impl.StringToMimeTypeMapImpl <em>String To Mime Type Map</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.openapi.model.impl.StringToMimeTypeMapImpl
		 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getStringToMimeTypeMap()
		 * @generated
		 */
		EClass STRING_TO_MIME_TYPE_MAP = eINSTANCE.getStringToMimeTypeMap();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRING_TO_MIME_TYPE_MAP__KEY = eINSTANCE.getStringToMimeTypeMap_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STRING_TO_MIME_TYPE_MAP__VALUE = eINSTANCE.getStringToMimeTypeMap_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.openapi.model.impl.StringToOpenApiClassMapImpl <em>String To Open Api Class Map</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.openapi.model.impl.StringToOpenApiClassMapImpl
		 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getStringToOpenApiClassMap()
		 * @generated
		 */
		EClass STRING_TO_OPEN_API_CLASS_MAP = eINSTANCE.getStringToOpenApiClassMap();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRING_TO_OPEN_API_CLASS_MAP__KEY = eINSTANCE.getStringToOpenApiClassMap_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STRING_TO_OPEN_API_CLASS_MAP__VALUE = eINSTANCE.getStringToOpenApiClassMap_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.openapi.model.impl.MimeTypeMapImpl <em>Mime Type Map</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.openapi.model.impl.MimeTypeMapImpl
		 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getMimeTypeMap()
		 * @generated
		 */
		EClass MIME_TYPE_MAP = eINSTANCE.getMimeTypeMap();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MIME_TYPE_MAP__KEY = eINSTANCE.getMimeTypeMap_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MIME_TYPE_MAP__VALUE = eINSTANCE.getMimeTypeMap_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.openapi.model.impl.ConstraintImpl <em>Constraint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.openapi.model.impl.ConstraintImpl
		 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getConstraint()
		 * @generated
		 */
		EClass CONSTRAINT = eINSTANCE.getConstraint();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.openapi.model.impl.StringConstraintImpl <em>String Constraint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.openapi.model.impl.StringConstraintImpl
		 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getStringConstraint()
		 * @generated
		 */
		EClass STRING_CONSTRAINT = eINSTANCE.getStringConstraint();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.openapi.model.impl.NumericConstraintImpl <em>Numeric Constraint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.openapi.model.impl.NumericConstraintImpl
		 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getNumericConstraint()
		 * @generated
		 */
		EClass NUMERIC_CONSTRAINT = eINSTANCE.getNumericConstraint();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.openapi.model.impl.ArrayConstraintImpl <em>Array Constraint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.openapi.model.impl.ArrayConstraintImpl
		 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getArrayConstraint()
		 * @generated
		 */
		EClass ARRAY_CONSTRAINT = eINSTANCE.getArrayConstraint();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.openapi.model.impl.ExtensionImpl <em>Extension</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.openapi.model.impl.ExtensionImpl
		 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getExtension()
		 * @generated
		 */
		EClass EXTENSION = eINSTANCE.getExtension();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXTENSION__KEY = eINSTANCE.getExtension_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXTENSION__VALUE = eINSTANCE.getExtension_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.fennec.openapi.model.impl.ApiKeyImpl <em>Api Key</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fennec.openapi.model.impl.ApiKeyImpl
		 * @see org.eclipse.fennec.openapi.model.impl.OpenApiPackageImpl#getApiKey()
		 * @generated
		 */
		EClass API_KEY = eINSTANCE.getApiKey();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute API_KEY__TYPE = eINSTANCE.getApiKey_Type();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute API_KEY__NAME = eINSTANCE.getApiKey_Name();

		/**
		 * The meta object literal for the '<em><b>In</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute API_KEY__IN = eINSTANCE.getApiKey_In();

	}

} //OpenApiPackage
