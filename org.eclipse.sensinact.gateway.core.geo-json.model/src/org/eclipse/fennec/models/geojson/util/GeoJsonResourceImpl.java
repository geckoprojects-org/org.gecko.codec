/**
 */
package org.eclipse.fennec.models.geojson.util;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emfcloud.jackson.annotations.EcoreTypeInfo;
import org.gecko.emf.json.configuration.ConfigurableJsonResource;
import org.gecko.emf.json.constants.EMFJs;

/**
 * <!-- begin-user-doc -->
 * The <b>Resource </b> associated with the package.
 * <!-- end-user-doc -->
 * @see org.eclipse.fennec.models.geojson.util.GeoJsonResourceFactoryImpl
 * @generated
 */
public class GeoJsonResourceImpl extends ConfigurableJsonResource {
	
	private static Map<String, Object> GEOJSON_DEFAULT_OPTIONS = new HashMap<>();
	static {
		GEOJSON_DEFAULT_OPTIONS.put(EMFJs.OPTION_SERIALIZE_DEFAULT_VALUE, true);
		GEOJSON_DEFAULT_OPTIONS.put(EMFJs.OPTION_TYPE_FIELD, "type");
		GEOJSON_DEFAULT_OPTIONS.put(EMFJs.OPTION_TYPE_USE, EcoreTypeInfo.USE.NAME);
	}
	
	/**
	 * Creates an instance of the resource.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param uri the URI of the new resource.
	 * @generated
	 */
	public GeoJsonResourceImpl(URI uri) {
		super(uri, new ConfigurableJsonResource(uri).configureMapper(GEOJSON_DEFAULT_OPTIONS));
	}

} //GeoJsonResourceImpl
