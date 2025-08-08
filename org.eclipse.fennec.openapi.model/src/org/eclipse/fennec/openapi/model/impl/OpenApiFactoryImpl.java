/**
 */
package org.eclipse.fennec.openapi.model.impl;

import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.fennec.openapi.model.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class OpenApiFactoryImpl extends EFactoryImpl implements OpenApiFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static OpenApiFactory init() {
		try {
			OpenApiFactory theOpenApiFactory = (OpenApiFactory)EPackage.Registry.INSTANCE.getEFactory(OpenApiPackage.eNS_URI);
			if (theOpenApiFactory != null) {
				return theOpenApiFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new OpenApiFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OpenApiFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case OpenApiPackage.OPEN_API: return createOpenApi();
			case OpenApiPackage.INFO: return createInfo();
			case OpenApiPackage.CONTACT: return createContact();
			case OpenApiPackage.LICENSE: return createLicense();
			case OpenApiPackage.DOCS: return createDocs();
			case OpenApiPackage.TAG: return createTag();
			case OpenApiPackage.SERVER: return createServer();
			case OpenApiPackage.PATH: return createPath();
			case OpenApiPackage.PARAMETER: return createParameter();
			case OpenApiPackage.SECURITY: return createSecurity();
			case OpenApiPackage.PUT: return createPut();
			case OpenApiPackage.POST: return createPost();
			case OpenApiPackage.DELETE: return createDelete();
			case OpenApiPackage.GET: return createGet();
			case OpenApiPackage.REQUEST_BODY: return createRequestBody();
			case OpenApiPackage.ITEMS: return createItems();
			case OpenApiPackage.ADDITIONAL_PROPERTIES: return createAdditionalProperties();
			case OpenApiPackage.COMPONENTS: return createComponents();
			case OpenApiPackage.SECURITY_SCHEMES: return createSecuritySchemes();
			case OpenApiPackage.BEARER_AUTH: return createBearerAuth();
			case OpenApiPackage.BASIC_AUTH: return createBasicAuth();
			case OpenApiPackage.RESPONSE: return createResponse();
			case OpenApiPackage.MIME_TYPE: return createMimeType();
			case OpenApiPackage.OPEN_API_CLASS: return createOpenApiClass();
			case OpenApiPackage.PROPERTY: return createProperty();
			case OpenApiPackage.STRING_TO_PATH_MAP: return (EObject)createStringToPathMap();
			case OpenApiPackage.STRING_TO_PROPERTY_MAP: return (EObject)createStringToPropertyMap();
			case OpenApiPackage.STRING_TO_RESPONSE_MAP: return (EObject)createStringToResponseMap();
			case OpenApiPackage.STRING_TO_MIME_TYPE_MAP: return (EObject)createStringToMimeTypeMap();
			case OpenApiPackage.STRING_TO_OPEN_API_CLASS_MAP: return (EObject)createStringToOpenApiClassMap();
			case OpenApiPackage.MIME_TYPE_MAP: return (EObject)createMimeTypeMap();
			case OpenApiPackage.STRING_CONSTRAINT: return createStringConstraint();
			case OpenApiPackage.NUMERIC_CONSTRAINT: return createNumericConstraint();
			case OpenApiPackage.ARRAY_CONSTRAINT: return createArrayConstraint();
			case OpenApiPackage.EXTENSION: return createExtension();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public OpenApi createOpenApi() {
		OpenApiImpl openApi = new OpenApiImpl();
		return openApi;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Info createInfo() {
		InfoImpl info = new InfoImpl();
		return info;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Contact createContact() {
		ContactImpl contact = new ContactImpl();
		return contact;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public License createLicense() {
		LicenseImpl license = new LicenseImpl();
		return license;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Docs createDocs() {
		DocsImpl docs = new DocsImpl();
		return docs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Tag createTag() {
		TagImpl tag = new TagImpl();
		return tag;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Server createServer() {
		ServerImpl server = new ServerImpl();
		return server;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Path createPath() {
		PathImpl path = new PathImpl();
		return path;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Parameter createParameter() {
		ParameterImpl parameter = new ParameterImpl();
		return parameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Security createSecurity() {
		SecurityImpl security = new SecurityImpl();
		return security;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Put createPut() {
		PutImpl put = new PutImpl();
		return put;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Post createPost() {
		PostImpl post = new PostImpl();
		return post;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Delete createDelete() {
		DeleteImpl delete = new DeleteImpl();
		return delete;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Get createGet() {
		GetImpl get = new GetImpl();
		return get;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public RequestBody createRequestBody() {
		RequestBodyImpl requestBody = new RequestBodyImpl();
		return requestBody;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Items createItems() {
		ItemsImpl items = new ItemsImpl();
		return items;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AdditionalProperties createAdditionalProperties() {
		AdditionalPropertiesImpl additionalProperties = new AdditionalPropertiesImpl();
		return additionalProperties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Components createComponents() {
		ComponentsImpl components = new ComponentsImpl();
		return components;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SecuritySchemes createSecuritySchemes() {
		SecuritySchemesImpl securitySchemes = new SecuritySchemesImpl();
		return securitySchemes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BearerAuth createBearerAuth() {
		BearerAuthImpl bearerAuth = new BearerAuthImpl();
		return bearerAuth;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BasicAuth createBasicAuth() {
		BasicAuthImpl basicAuth = new BasicAuthImpl();
		return basicAuth;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Response createResponse() {
		ResponseImpl response = new ResponseImpl();
		return response;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MimeType createMimeType() {
		MimeTypeImpl mimeType = new MimeTypeImpl();
		return mimeType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public OpenApiClass createOpenApiClass() {
		OpenApiClassImpl openApiClass = new OpenApiClassImpl();
		return openApiClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Property createProperty() {
		PropertyImpl property = new PropertyImpl();
		return property;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<String, Path> createStringToPathMap() {
		StringToPathMapImpl stringToPathMap = new StringToPathMapImpl();
		return stringToPathMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<String, Property> createStringToPropertyMap() {
		StringToPropertyMapImpl stringToPropertyMap = new StringToPropertyMapImpl();
		return stringToPropertyMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<String, Response> createStringToResponseMap() {
		StringToResponseMapImpl stringToResponseMap = new StringToResponseMapImpl();
		return stringToResponseMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<String, MimeType> createStringToMimeTypeMap() {
		StringToMimeTypeMapImpl stringToMimeTypeMap = new StringToMimeTypeMapImpl();
		return stringToMimeTypeMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<String, OpenApiClass> createStringToOpenApiClassMap() {
		StringToOpenApiClassMapImpl stringToOpenApiClassMap = new StringToOpenApiClassMapImpl();
		return stringToOpenApiClassMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<String, MimeType> createMimeTypeMap() {
		MimeTypeMapImpl mimeTypeMap = new MimeTypeMapImpl();
		return mimeTypeMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public StringConstraint createStringConstraint() {
		StringConstraintImpl stringConstraint = new StringConstraintImpl();
		return stringConstraint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NumericConstraint createNumericConstraint() {
		NumericConstraintImpl numericConstraint = new NumericConstraintImpl();
		return numericConstraint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ArrayConstraint createArrayConstraint() {
		ArrayConstraintImpl arrayConstraint = new ArrayConstraintImpl();
		return arrayConstraint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Extension createExtension() {
		ExtensionImpl extension = new ExtensionImpl();
		return extension;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public OpenApiPackage getOpenApiPackage() {
		return (OpenApiPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static OpenApiPackage getPackage() {
		return OpenApiPackage.eINSTANCE;
	}

} //OpenApiFactoryImpl
