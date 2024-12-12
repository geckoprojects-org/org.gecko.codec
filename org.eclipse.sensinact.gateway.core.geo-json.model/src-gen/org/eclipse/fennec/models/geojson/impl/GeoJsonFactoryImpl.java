/**
 */
package org.eclipse.fennec.models.geojson.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.fennec.models.geojson.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class GeoJsonFactoryImpl extends EFactoryImpl implements GeoJsonFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static GeoJsonFactory init() {
		try {
			GeoJsonFactory theGeoJsonFactory = (GeoJsonFactory)EPackage.Registry.INSTANCE.getEFactory(GeoJsonPackage.eNS_URI);
			if (theGeoJsonFactory != null) {
				return theGeoJsonFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new GeoJsonFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GeoJsonFactoryImpl() {
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
			case GeoJsonPackage.COORDINATES: return createCoordinates();
			case GeoJsonPackage.POINT: return createPoint();
			case GeoJsonPackage.GEO_JSON_OBJECT: return createGeoJsonObject();
			case GeoJsonPackage.LINE_STRING: return createLineString();
			case GeoJsonPackage.SIMPLE_LINE_STRING: return createSimpleLineString();
			case GeoJsonPackage.FEATURE: return createFeature();
			case GeoJsonPackage.FEATURE_COLLECTION: return createFeatureCollection();
			case GeoJsonPackage.GEOMETRY_COLLECTION: return createGeometryCollection();
			case GeoJsonPackage.MULTI_POINT: return createMultiPoint();
			case GeoJsonPackage.POLYGON: return createPolygon();
			case GeoJsonPackage.RING: return createRing();
			case GeoJsonPackage.HOLE: return createHole();
			case GeoJsonPackage.MULTI_POLYGON: return createMultiPolygon();
			case GeoJsonPackage.SIMPLE_POLYGON: return createSimplePolygon();
			case GeoJsonPackage.MULTI_LINE_STRING: return createMultiLineString();
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
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case GeoJsonPackage.DOUBLE_ARRAY1_D:
				return createDoubleArray1DFromString(eDataType, initialValue);
			case GeoJsonPackage.DOUBLE_ARRAY2_D:
				return createDoubleArray2DFromString(eDataType, initialValue);
			case GeoJsonPackage.DOUBLE_ARRAY3_D:
				return createDoubleArray3DFromString(eDataType, initialValue);
			case GeoJsonPackage.DOUBLE_ARRAY4_D:
				return createDoubleArray4DFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case GeoJsonPackage.DOUBLE_ARRAY1_D:
				return convertDoubleArray1DToString(eDataType, instanceValue);
			case GeoJsonPackage.DOUBLE_ARRAY2_D:
				return convertDoubleArray2DToString(eDataType, instanceValue);
			case GeoJsonPackage.DOUBLE_ARRAY3_D:
				return convertDoubleArray3DToString(eDataType, instanceValue);
			case GeoJsonPackage.DOUBLE_ARRAY4_D:
				return convertDoubleArray4DToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Coordinates createCoordinates() {
		CoordinatesImpl coordinates = new CoordinatesImpl();
		return coordinates;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Point createPoint() {
		PointImpl point = new PointImpl();
		return point;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GeoJsonObject createGeoJsonObject() {
		GeoJsonObjectImpl geoJsonObject = new GeoJsonObjectImpl();
		return geoJsonObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LineString createLineString() {
		LineStringImpl lineString = new LineStringImpl();
		return lineString;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SimpleLineString createSimpleLineString() {
		SimpleLineStringImpl simpleLineString = new SimpleLineStringImpl();
		return simpleLineString;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Feature createFeature() {
		FeatureImpl feature = new FeatureImpl();
		return feature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureCollection createFeatureCollection() {
		FeatureCollectionImpl featureCollection = new FeatureCollectionImpl();
		return featureCollection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GeometryCollection createGeometryCollection() {
		GeometryCollectionImpl geometryCollection = new GeometryCollectionImpl();
		return geometryCollection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MultiPoint createMultiPoint() {
		MultiPointImpl multiPoint = new MultiPointImpl();
		return multiPoint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Polygon createPolygon() {
		PolygonImpl polygon = new PolygonImpl();
		return polygon;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Ring createRing() {
		RingImpl ring = new RingImpl();
		return ring;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Hole createHole() {
		HoleImpl hole = new HoleImpl();
		return hole;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MultiPolygon createMultiPolygon() {
		MultiPolygonImpl multiPolygon = new MultiPolygonImpl();
		return multiPolygon;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SimplePolygon createSimplePolygon() {
		SimplePolygonImpl simplePolygon = new SimplePolygonImpl();
		return simplePolygon;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MultiLineString createMultiLineString() {
		MultiLineStringImpl multiLineString = new MultiLineStringImpl();
		return multiLineString;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double[] createDoubleArray1D(String literal) {
		return (double[])super.createFromString(literal);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double[] createDoubleArray1DFromString(EDataType eDataType, String initialValue) {
		return createDoubleArray1D(initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertDoubleArray1D(double[] instanceValue) {
		return super.convertToString(instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDoubleArray1DToString(EDataType eDataType, Object instanceValue) {
		return convertDoubleArray1D((double[])instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double[][] createDoubleArray2D(String literal) {
		return (double[][])super.createFromString(literal);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double[][] createDoubleArray2DFromString(EDataType eDataType, String initialValue) {
		return createDoubleArray2D(initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertDoubleArray2D(double[][] instanceValue) {
		return super.convertToString(instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDoubleArray2DToString(EDataType eDataType, Object instanceValue) {
		return convertDoubleArray2D((double[][])instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double[][][] createDoubleArray3D(String literal) {
		return (double[][][])super.createFromString(literal);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double[][][] createDoubleArray3DFromString(EDataType eDataType, String initialValue) {
		return createDoubleArray3D(initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertDoubleArray3D(double[][][] instanceValue) {
		return super.convertToString(instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDoubleArray3DToString(EDataType eDataType, Object instanceValue) {
		return convertDoubleArray3D((double[][][])instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double[][][][] createDoubleArray4D(String literal) {
		return (double[][][][])super.createFromString(literal);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double[][][][] createDoubleArray4DFromString(EDataType eDataType, String initialValue) {
		return createDoubleArray4D(initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertDoubleArray4D(double[][][][] instanceValue) {
		return super.convertToString(instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDoubleArray4DToString(EDataType eDataType, Object instanceValue) {
		return convertDoubleArray4D((double[][][][])instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GeoJsonPackage getGeoJsonPackage() {
		return (GeoJsonPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static GeoJsonPackage getPackage() {
		return GeoJsonPackage.eINSTANCE;
	}

} //GeoJsonFactoryImpl
