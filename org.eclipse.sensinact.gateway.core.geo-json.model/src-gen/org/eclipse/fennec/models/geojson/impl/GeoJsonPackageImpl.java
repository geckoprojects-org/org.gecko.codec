/**
 */
package org.eclipse.fennec.models.geojson.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.fennec.models.geojson.Coordinates;
import org.eclipse.fennec.models.geojson.Feature;
import org.eclipse.fennec.models.geojson.FeatureCollection;
import org.eclipse.fennec.models.geojson.GeoJsonFactory;
import org.eclipse.fennec.models.geojson.GeoJsonObject;
import org.eclipse.fennec.models.geojson.GeoJsonPackage;
import org.eclipse.fennec.models.geojson.Geometry;
import org.eclipse.fennec.models.geojson.GeometryCollection;
import org.eclipse.fennec.models.geojson.Hole;
import org.eclipse.fennec.models.geojson.LineString;
import org.eclipse.fennec.models.geojson.MultiLineString;
import org.eclipse.fennec.models.geojson.MultiPoint;
import org.eclipse.fennec.models.geojson.MultiPolygon;
import org.eclipse.fennec.models.geojson.Point;
import org.eclipse.fennec.models.geojson.Polygon;
import org.eclipse.fennec.models.geojson.Ring;
import org.eclipse.fennec.models.geojson.SimpleLineString;
import org.eclipse.fennec.models.geojson.SimplePolygon;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class GeoJsonPackageImpl extends EPackageImpl implements GeoJsonPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass coordinatesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass pointEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass geoJsonObjectEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass geometryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass lineStringEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass simpleLineStringEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass featureEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass featureCollectionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass geometryCollectionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass multiPointEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass polygonEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ringEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass holeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass multiPolygonEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass simplePolygonEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass multiLineStringEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType doubleArray1DEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType doubleArray2DEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType doubleArray3DEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType doubleArray4DEDataType = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.fennec.models.geojson.GeoJsonPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private GeoJsonPackageImpl() {
		super(eNS_URI, GeoJsonFactory.eINSTANCE);
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link GeoJsonPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static GeoJsonPackage init() {
		if (isInited) return (GeoJsonPackage)EPackage.Registry.INSTANCE.getEPackage(GeoJsonPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredGeoJsonPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		GeoJsonPackageImpl theGeoJsonPackage = registeredGeoJsonPackage instanceof GeoJsonPackageImpl ? (GeoJsonPackageImpl)registeredGeoJsonPackage : new GeoJsonPackageImpl();

		isInited = true;

		// Create package meta-data objects
		theGeoJsonPackage.createPackageContents();

		// Initialize created meta-data
		theGeoJsonPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theGeoJsonPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(GeoJsonPackage.eNS_URI, theGeoJsonPackage);
		return theGeoJsonPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCoordinates() {
		return coordinatesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCoordinates_Longitude() {
		return (EAttribute)coordinatesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCoordinates_Latitude() {
		return (EAttribute)coordinatesEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCoordinates_Elevation() {
		return (EAttribute)coordinatesEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getPoint() {
		return pointEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getPoint_Data() {
		return (EAttribute)pointEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getPoint_Coordinates() {
		return (EReference)pointEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getPoint__SetData__double() {
		return pointEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getGeoJsonObject() {
		return geoJsonObjectEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getGeoJsonObject_Bbox() {
		return (EAttribute)geoJsonObjectEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getGeoJsonObject_BoundingBox() {
		return (EReference)geoJsonObjectEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getGeometry() {
		return geometryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLineString() {
		return lineStringEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSimpleLineString() {
		return simpleLineStringEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSimpleLineString_Data() {
		return (EAttribute)simpleLineStringEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSimpleLineString_Coordinates() {
		return (EReference)simpleLineStringEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFeature() {
		return featureEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFeature_Id() {
		return (EAttribute)featureEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFeature_Geometry() {
		return (EReference)featureEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFeature_Properties() {
		return (EReference)featureEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFeatureCollection() {
		return featureCollectionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFeatureCollection_Features() {
		return (EReference)featureCollectionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getGeometryCollection() {
		return geometryCollectionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getGeometryCollection_Geometries() {
		return (EReference)geometryCollectionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getMultiPoint() {
		return multiPointEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMultiPoint_Data() {
		return (EAttribute)multiPointEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getMultiPoint_Coordinates() {
		return (EReference)multiPointEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getPolygon() {
		return polygonEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getRing() {
		return ringEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getRing_Coordinates() {
		return (EReference)ringEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getHole() {
		return holeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getMultiPolygon() {
		return multiPolygonEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMultiPolygon_Data() {
		return (EAttribute)multiPolygonEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getMultiPolygon_Polygons() {
		return (EReference)multiPolygonEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getMultiPolygon__SetData__double() {
		return multiPolygonEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSimplePolygon() {
		return simplePolygonEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSimplePolygon_Data() {
		return (EAttribute)simplePolygonEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSimplePolygon_ExteriorRing() {
		return (EReference)simplePolygonEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSimplePolygon_InteriorHoles() {
		return (EReference)simplePolygonEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getSimplePolygon__SetData__double() {
		return simplePolygonEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getMultiLineString() {
		return multiLineStringEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMultiLineString_Data() {
		return (EAttribute)multiLineStringEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getMultiLineString_LinesStrings() {
		return (EReference)multiLineStringEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getMultiLineString__SetData__double() {
		return multiLineStringEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getDoubleArray1D() {
		return doubleArray1DEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getDoubleArray2D() {
		return doubleArray2DEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getDoubleArray3D() {
		return doubleArray3DEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getDoubleArray4D() {
		return doubleArray4DEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GeoJsonFactory getGeoJsonFactory() {
		return (GeoJsonFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		coordinatesEClass = createEClass(COORDINATES);
		createEAttribute(coordinatesEClass, COORDINATES__LONGITUDE);
		createEAttribute(coordinatesEClass, COORDINATES__LATITUDE);
		createEAttribute(coordinatesEClass, COORDINATES__ELEVATION);

		pointEClass = createEClass(POINT);
		createEAttribute(pointEClass, POINT__DATA);
		createEReference(pointEClass, POINT__COORDINATES);
		createEOperation(pointEClass, POINT___SET_DATA__DOUBLE);

		geoJsonObjectEClass = createEClass(GEO_JSON_OBJECT);
		createEAttribute(geoJsonObjectEClass, GEO_JSON_OBJECT__BBOX);
		createEReference(geoJsonObjectEClass, GEO_JSON_OBJECT__BOUNDING_BOX);

		geometryEClass = createEClass(GEOMETRY);

		lineStringEClass = createEClass(LINE_STRING);

		simpleLineStringEClass = createEClass(SIMPLE_LINE_STRING);
		createEAttribute(simpleLineStringEClass, SIMPLE_LINE_STRING__DATA);
		createEReference(simpleLineStringEClass, SIMPLE_LINE_STRING__COORDINATES);

		featureEClass = createEClass(FEATURE);
		createEAttribute(featureEClass, FEATURE__ID);
		createEReference(featureEClass, FEATURE__GEOMETRY);
		createEReference(featureEClass, FEATURE__PROPERTIES);

		featureCollectionEClass = createEClass(FEATURE_COLLECTION);
		createEReference(featureCollectionEClass, FEATURE_COLLECTION__FEATURES);

		geometryCollectionEClass = createEClass(GEOMETRY_COLLECTION);
		createEReference(geometryCollectionEClass, GEOMETRY_COLLECTION__GEOMETRIES);

		multiPointEClass = createEClass(MULTI_POINT);
		createEAttribute(multiPointEClass, MULTI_POINT__DATA);
		createEReference(multiPointEClass, MULTI_POINT__COORDINATES);

		polygonEClass = createEClass(POLYGON);

		ringEClass = createEClass(RING);
		createEReference(ringEClass, RING__COORDINATES);

		holeEClass = createEClass(HOLE);

		multiPolygonEClass = createEClass(MULTI_POLYGON);
		createEAttribute(multiPolygonEClass, MULTI_POLYGON__DATA);
		createEReference(multiPolygonEClass, MULTI_POLYGON__POLYGONS);
		createEOperation(multiPolygonEClass, MULTI_POLYGON___SET_DATA__DOUBLE);

		simplePolygonEClass = createEClass(SIMPLE_POLYGON);
		createEAttribute(simplePolygonEClass, SIMPLE_POLYGON__DATA);
		createEReference(simplePolygonEClass, SIMPLE_POLYGON__EXTERIOR_RING);
		createEReference(simplePolygonEClass, SIMPLE_POLYGON__INTERIOR_HOLES);
		createEOperation(simplePolygonEClass, SIMPLE_POLYGON___SET_DATA__DOUBLE);

		multiLineStringEClass = createEClass(MULTI_LINE_STRING);
		createEAttribute(multiLineStringEClass, MULTI_LINE_STRING__DATA);
		createEReference(multiLineStringEClass, MULTI_LINE_STRING__LINES_STRINGS);
		createEOperation(multiLineStringEClass, MULTI_LINE_STRING___SET_DATA__DOUBLE);

		// Create data types
		doubleArray1DEDataType = createEDataType(DOUBLE_ARRAY1_D);
		doubleArray2DEDataType = createEDataType(DOUBLE_ARRAY2_D);
		doubleArray3DEDataType = createEDataType(DOUBLE_ARRAY3_D);
		doubleArray4DEDataType = createEDataType(DOUBLE_ARRAY4_D);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		pointEClass.getESuperTypes().add(this.getGeometry());
		geometryEClass.getESuperTypes().add(this.getGeoJsonObject());
		lineStringEClass.getESuperTypes().add(this.getGeometry());
		lineStringEClass.getESuperTypes().add(this.getSimpleLineString());
		featureEClass.getESuperTypes().add(this.getGeoJsonObject());
		featureCollectionEClass.getESuperTypes().add(this.getGeoJsonObject());
		geometryCollectionEClass.getESuperTypes().add(this.getGeometry());
		multiPointEClass.getESuperTypes().add(this.getGeometry());
		polygonEClass.getESuperTypes().add(this.getGeometry());
		polygonEClass.getESuperTypes().add(this.getSimplePolygon());
		holeEClass.getESuperTypes().add(this.getRing());
		multiPolygonEClass.getESuperTypes().add(this.getGeometry());
		multiLineStringEClass.getESuperTypes().add(this.getGeometry());

		// Initialize classes, features, and operations; add parameters
		initEClass(coordinatesEClass, Coordinates.class, "Coordinates", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCoordinates_Longitude(), ecorePackage.getEDouble(), "longitude", null, 1, 1, Coordinates.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCoordinates_Latitude(), ecorePackage.getEDouble(), "latitude", null, 1, 1, Coordinates.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCoordinates_Elevation(), ecorePackage.getEDouble(), "elevation", "0.0d", 0, 1, Coordinates.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(pointEClass, Point.class, "Point", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPoint_Data(), this.getDoubleArray1D(), "data", null, 0, 1, Point.class, !IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPoint_Coordinates(), this.getCoordinates(), null, "coordinates", null, 0, 1, Point.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		EOperation op = initEOperation(getPoint__SetData__double(), null, "setData", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getDoubleArray1D(), "data", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(geoJsonObjectEClass, GeoJsonObject.class, "GeoJsonObject", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getGeoJsonObject_Bbox(), this.getDoubleArray1D(), "bbox", null, 0, -1, GeoJsonObject.class, !IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGeoJsonObject_BoundingBox(), this.getCoordinates(), null, "boundingBox", null, 0, -1, GeoJsonObject.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(geometryEClass, Geometry.class, "Geometry", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(lineStringEClass, LineString.class, "LineString", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(simpleLineStringEClass, SimpleLineString.class, "SimpleLineString", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSimpleLineString_Data(), this.getDoubleArray1D(), "data", null, 0, -1, SimpleLineString.class, !IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSimpleLineString_Coordinates(), this.getCoordinates(), null, "coordinates", null, 0, -1, SimpleLineString.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(featureEClass, Feature.class, "Feature", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFeature_Id(), ecorePackage.getEString(), "id", null, 1, 1, Feature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFeature_Geometry(), this.getGeometry(), null, "geometry", null, 0, 1, Feature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFeature_Properties(), ecorePackage.getEObject(), null, "properties", null, 0, 1, Feature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(featureCollectionEClass, FeatureCollection.class, "FeatureCollection", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFeatureCollection_Features(), this.getFeature(), null, "features", null, 0, -1, FeatureCollection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(geometryCollectionEClass, GeometryCollection.class, "GeometryCollection", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getGeometryCollection_Geometries(), this.getGeometry(), null, "geometries", null, 0, -1, GeometryCollection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(multiPointEClass, MultiPoint.class, "MultiPoint", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMultiPoint_Data(), this.getDoubleArray1D(), "data", null, 0, -1, MultiPoint.class, !IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMultiPoint_Coordinates(), this.getCoordinates(), null, "coordinates", null, 0, -1, MultiPoint.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(polygonEClass, Polygon.class, "Polygon", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(ringEClass, Ring.class, "Ring", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRing_Coordinates(), this.getCoordinates(), null, "coordinates", null, 0, -1, Ring.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(holeEClass, Hole.class, "Hole", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(multiPolygonEClass, MultiPolygon.class, "MultiPolygon", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMultiPolygon_Data(), this.getDoubleArray4D(), "data", null, 0, 1, MultiPolygon.class, !IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMultiPolygon_Polygons(), this.getSimplePolygon(), null, "polygons", null, 0, -1, MultiPolygon.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = initEOperation(getMultiPolygon__SetData__double(), null, "setData", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getDoubleArray4D(), "data", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(simplePolygonEClass, SimplePolygon.class, "SimplePolygon", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSimplePolygon_Data(), this.getDoubleArray3D(), "data", null, 0, 1, SimplePolygon.class, !IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSimplePolygon_ExteriorRing(), this.getRing(), null, "exteriorRing", null, 0, 1, SimplePolygon.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSimplePolygon_InteriorHoles(), this.getHole(), null, "interiorHoles", null, 0, -1, SimplePolygon.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = initEOperation(getSimplePolygon__SetData__double(), null, "setData", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getDoubleArray3D(), "data", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(multiLineStringEClass, MultiLineString.class, "MultiLineString", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMultiLineString_Data(), this.getDoubleArray3D(), "data", null, 0, 1, MultiLineString.class, !IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMultiLineString_LinesStrings(), this.getSimpleLineString(), null, "linesStrings", null, 0, -1, MultiLineString.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = initEOperation(getMultiLineString__SetData__double(), null, "setData", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getDoubleArray3D(), "data", 0, 1, IS_UNIQUE, IS_ORDERED);

		// Initialize data types
		initEDataType(doubleArray1DEDataType, double[].class, "DoubleArray1D", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(doubleArray2DEDataType, double[][].class, "DoubleArray2D", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(doubleArray3DEDataType, double[][][].class, "DoubleArray3D", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(doubleArray4DEDataType, double[][][][].class, "DoubleArray4D", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// Version
		createVersionAnnotations();
		// http://www.eclipse.org/emf/2002/GenModel
		createGenModelAnnotations();
		// http:///org/eclipse/emf/ecore/util/ExtendedMetaData
		createExtendedMetaDataAnnotations();
	}

	/**
	 * Initializes the annotations for <b>Version</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createVersionAnnotations() {
		String source = "Version";
		addAnnotation
		  (this,
		   source,
		   new String[] {
			   "value", "1.0"
		   });
	}

	/**
	 * Initializes the annotations for <b>http://www.eclipse.org/emf/2002/GenModel</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createGenModelAnnotations() {
		String source = "http://www.eclipse.org/emf/2002/GenModel";
		addAnnotation
		  (this,
		   source,
		   new String[] {
			   "publicConstructors", "true",
			   "contentTypeIdentifier", "application/geo+json",
			   "oSGiCompatible", "true",
			   "modelName", "GeoJson",
			   "basePackage", "org.eclipse.fennec.models",
			   "dataTypeConverters", "true"
		   });
		addAnnotation
		  (pointEClass,
		   source,
		   new String[] {
			   "documentation", "A GeoJSON point object as defined in\n<a href=\"https://tools.ietf.org/html/rfc7946#section-3.1\">the GeoJSON\nspecification</a>"
		   });
		addAnnotation
		  (getPoint__SetData__double(),
		   source,
		   new String[] {
			   "body", "setCoordinates(org.eclipse.fennec.models.geojson.util.GeoJsonHelper.toCoordinates(data));",
			   "suppressedVisibility", "true"
		   });
		addAnnotation
		  (getPoint_Data(),
		   source,
		   new String[] {
			   "suppressedGetVisibility", "true",
			   "suppressedIsSetVisibility", "true",
			   "suppressedSetVisibility", "true",
			   "suppressedUnsetVisibility", "true",
			   "get", "return org.eclipse.fennec.models.geojson.util.GeoJsonHelper.convertCoordinates(getCoordinates());"
		   });
		addAnnotation
		  (getGeoJsonObject_Bbox(),
		   source,
		   new String[] {
			   "suppressedGetVisibility", "true",
			   "suppressedIsSetVisibility", "false",
			   "suppressedSetVisibility", "true",
			   "suppressedUnsetVisibility", "true",
			   "get", "return new org.eclipse.fennec.models.geojson.util.GenericConvertingList<double[], Coordinates>(getBoundingBox(), org.eclipse.fennec.models.geojson.util.GeoJsonHelper::toCoordinates, org.eclipse.fennec.models.geojson.util.GeoJsonHelper::convertCoordinates);"
		   });
		addAnnotation
		  (lineStringEClass,
		   source,
		   new String[] {
			   "documentation", " A GeoJSON line string object as defined in\n<a href=\"https://tools.ietf.org/html/rfc7946#section-3.1\">the GeoJSON specification</a>"
		   });
		addAnnotation
		  (getSimpleLineString_Data(),
		   source,
		   new String[] {
			   "suppressedGetVisibility", "true",
			   "suppressedIsSetVisibility", "false",
			   "suppressedSetVisibility", "true",
			   "suppressedUnsetVisibility", "true",
			   "get", "return new org.eclipse.fennec.models.geojson.util.GenericConvertingList<double[], Coordinates>(getCoordinates(), org.eclipse.fennec.models.geojson.util.GeoJsonHelper::toCoordinates, org.eclipse.fennec.models.geojson.util.GeoJsonHelper::convertCoordinates);"
		   });
		addAnnotation
		  (geometryCollectionEClass,
		   source,
		   new String[] {
			   "documentation", "A GeoJSON geometry collection object as defined in\n<a href=\"https://tools.ietf.org/html/rfc7946#section-3.1\">the GeoJSON\nspecification</a>"
		   });
		addAnnotation
		  (multiPointEClass,
		   source,
		   new String[] {
			   "documentation", " A GeoJSON multi point object as defined in\n<a href=\"https://tools.ietf.org/html/rfc7946#section-3.1\">the GeoJSON specification</a>"
		   });
		addAnnotation
		  (getMultiPoint_Data(),
		   source,
		   new String[] {
			   "suppressedGetVisibility", "true",
			   "suppressedIsSetVisibility", "false",
			   "suppressedSetVisibility", "true",
			   "suppressedUnsetVisibility", "true",
			   "get", "return new org.eclipse.fennec.models.geojson.util.GenericConvertingList<double[], Coordinates>(getCoordinates(), org.eclipse.fennec.models.geojson.util.GeoJsonHelper::toCoordinates, org.eclipse.fennec.models.geojson.util.GeoJsonHelper::convertCoordinates);"
		   });
		addAnnotation
		  (polygonEClass,
		   source,
		   new String[] {
			   "documentation", " A GeoJSON Polygone object as defined in\n<a href=\"https://tools.ietf.org/html/rfc7946#section-3.1.6\">the GeoJSON specification</a>"
		   });
		addAnnotation
		  (ringEClass,
		   source,
		   new String[] {
			   "documentation", "Represents a List of Coordinates that follow the right-hand rule and run counterclockwise"
		   });
		addAnnotation
		  (holeEClass,
		   source,
		   new String[] {
			   "documentation", "Represents a List of Coordinates that follow the right-hand rule and run clockwise."
		   });
		addAnnotation
		  (multiPolygonEClass,
		   source,
		   new String[] {
			   "documentation", " A GeoJSON Polygone object as defined in\n<a href=\"https://tools.ietf.org/html/rfc7946#section-3.1.6\">the GeoJSON specification</a>"
		   });
		addAnnotation
		  (getMultiPolygon__SetData__double(),
		   source,
		   new String[] {
			   "body", "org.eclipse.fennec.models.geojson.util.GeoJsonHelper.setMultiPolygonData(this, data);",
			   "suppressedVisibility", "true"
		   });
		addAnnotation
		  (getMultiPolygon_Data(),
		   source,
		   new String[] {
			   "suppressedGetVisibility", "true",
			   "suppressedIsSetVisibility", "false",
			   "suppressedSetVisibility", "true",
			   "suppressedUnsetVisibility", "true",
			   "get", "return org.eclipse.fennec.models.geojson.util.GeoJsonHelper.getMultiPolygonData(this);"
		   });
		addAnnotation
		  (simplePolygonEClass,
		   source,
		   new String[] {
			   "documentation", " A GeoJSON Polygone object as defined in\n<a href=\"https://tools.ietf.org/html/rfc7946#section-3.1.6\">the GeoJSON specification</a>"
		   });
		addAnnotation
		  (getSimplePolygon__SetData__double(),
		   source,
		   new String[] {
			   "body", "org.eclipse.fennec.models.geojson.util.GeoJsonHelper.setSimplePolygonData(this, data);",
			   "suppressedVisibility", "true"
		   });
		addAnnotation
		  (getSimplePolygon_Data(),
		   source,
		   new String[] {
			   "suppressedGetVisibility", "true",
			   "suppressedIsSetVisibility", "false",
			   "suppressedSetVisibility", "true",
			   "suppressedUnsetVisibility", "true",
			   "get", "return org.eclipse.fennec.models.geojson.util.GeoJsonHelper.getSimplePolygonData(this);"
		   });
		addAnnotation
		  (multiLineStringEClass,
		   source,
		   new String[] {
			   "documentation", " A GeoJSON Polygone object as defined in\n<a href=\"https://tools.ietf.org/html/rfc7946#section-3.1.6\">the GeoJSON specification</a>"
		   });
		addAnnotation
		  (getMultiLineString__SetData__double(),
		   source,
		   new String[] {
			   "body", "org.eclipse.fennec.models.geojson.util.GeoJsonHelper.setMultiLineStringData(this, data);",
			   "suppressedVisibility", "true"
		   });
		addAnnotation
		  (getMultiLineString_Data(),
		   source,
		   new String[] {
			   "suppressedGetVisibility", "true",
			   "suppressedIsSetVisibility", "false",
			   "suppressedSetVisibility", "true",
			   "suppressedUnsetVisibility", "true",
			   "get", "return org.eclipse.fennec.models.geojson.util.GeoJsonHelper.getMultiLineStringData(this);"
		   });
	}

	/**
	 * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createExtendedMetaDataAnnotations() {
		String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData";
		addAnnotation
		  (getPoint_Data(),
		   source,
		   new String[] {
			   "name", "coordinates"
		   });
		addAnnotation
		  (getGeoJsonObject_Bbox(),
		   source,
		   new String[] {
			   "name", "bbox"
		   });
		addAnnotation
		  (getSimpleLineString_Data(),
		   source,
		   new String[] {
			   "name", "coordinates"
		   });
		addAnnotation
		  (getMultiPoint_Data(),
		   source,
		   new String[] {
			   "name", "coordinates"
		   });
		addAnnotation
		  (getMultiPolygon_Data(),
		   source,
		   new String[] {
			   "name", "coordinates"
		   });
		addAnnotation
		  (getSimplePolygon_Data(),
		   source,
		   new String[] {
			   "name", "coordinates"
		   });
		addAnnotation
		  (getMultiLineString_Data(),
		   source,
		   new String[] {
			   "name", "coordinates"
		   });
	}

} //GeoJsonPackageImpl
