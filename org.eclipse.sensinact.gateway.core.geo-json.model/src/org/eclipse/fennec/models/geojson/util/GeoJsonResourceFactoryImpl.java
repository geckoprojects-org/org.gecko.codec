/*
 */
package org.eclipse.fennec.models.geojson.util;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;
import org.eclipse.fennec.models.geojson.GeoJsonPackage;
import org.gecko.emf.osgi.constants.EMFNamespaces;
import org.osgi.service.component.annotations.Component;

/**
 * <!-- begin-user-doc -->
 * The <b>Resource Factory</b> associated with the package.
 * <!-- end-user-doc -->
 * @see org.eclipse.fennec.models.geojson.util.GeoJsonResourceImpl
 * @generated
 */
@Component(property = {
		EMFNamespaces.EMF_CONFIGURATOR_NAME + "=" + GeoJsonPackage.eNAME,
		EMFNamespaces.EMF_MODEL_FILE_EXT + "=" + "geojson",
		EMFNamespaces.EMF_MODEL_VERSION + "=" + "1.0"
})
public class GeoJsonResourceFactoryImpl extends ResourceFactoryImpl {
	/**
	 * Creates an instance of the resource factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GeoJsonResourceFactoryImpl() {
		super();
	}

	/**
	 * Creates an instance of the resource.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Resource createResource(URI uri) {
		Resource result = new GeoJsonResourceImpl(uri);
		return result;
	}

	/**
	 * A method providing the Properties the services around this ResourceFactory should be registered with.
	 * @generated
	 */
	public Map<String, Object> getServiceProperties() {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(EMFNamespaces.EMF_CONFIGURATOR_NAME, GeoJsonPackage.eNAME);
		properties.put(EMFNamespaces.EMF_MODEL_FILE_EXT, "geojson");
		properties.put(EMFNamespaces.EMF_MODEL_VERSION, "1.0");
		return properties;
	}

} //GeoJsonResourceFactoryImpl
