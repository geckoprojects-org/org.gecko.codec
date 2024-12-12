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
package org.eclipse.fennec.models.geojson.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emfcloud.jackson.annotations.EcoreTypeInfo;
import org.eclipse.emfcloud.jackson.resource.JsonResource;
import org.eclipse.fennec.models.geojson.Coordinates;
import org.eclipse.fennec.models.geojson.Feature;
import org.eclipse.fennec.models.geojson.Hole;
import org.eclipse.fennec.models.geojson.LineString;
import org.eclipse.fennec.models.geojson.MultiLineString;
import org.eclipse.fennec.models.geojson.MultiPoint;
import org.eclipse.fennec.models.geojson.Point;
import org.eclipse.fennec.models.geojson.Polygon;
import org.eclipse.fennec.models.geojson.Ring;
import org.eclipse.fennec.models.geojson.impl.CoordinatesImpl;
import org.eclipse.fennec.models.geojson.impl.FeatureImpl;
import org.eclipse.fennec.models.geojson.impl.HoleImpl;
import org.eclipse.fennec.models.geojson.impl.LineStringImpl;
import org.eclipse.fennec.models.geojson.impl.MultiLineStringImpl;
import org.eclipse.fennec.models.geojson.impl.MultiPointImpl;
import org.eclipse.fennec.models.geojson.impl.PointImpl;
import org.eclipse.fennec.models.geojson.impl.PolygonImpl;
import org.eclipse.fennec.models.geojson.impl.RingImpl;
import org.eclipse.fennec.models.geojson.util.GeoJsonHelper;
import org.gecko.emf.json.annotation.RequireEMFJson;
import org.gecko.emf.json.constants.EMFJs;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.junit5.context.BundleContextExtension;
import org.osgi.test.junit5.service.ServiceExtension;

//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;

/**
 * See documentation here: 
 * 	https://github.com/osgi/osgi-test
 * 	https://github.com/osgi/osgi-test/wiki
 * Examples: https://github.com/osgi/osgi-test/tree/main/examples
 */
@ExtendWith(BundleContextExtension.class)
@ExtendWith(ServiceExtension.class)
@RequireEMFJson
public class GeoJsonTest {
	
	private Coordinates createCoordinate(double latitude, double longitude) {
		return createCoordinate(latitude, longitude, Double.NaN);
	}
	private Coordinates createCoordinate(double latitude, double longitude, double elevation) {
		Coordinates coords = new CoordinatesImpl();
		coords.setLatitude(latitude);
		coords.setLongitude(longitude);
		if(Double.isNaN(elevation)) {
			coords.setElevation(120);
		}
		return coords;
	}
	
	@Test
	public void testRing() throws IOException {
		System.err.println("Running Test Point");
		
		Coordinates coords = new CoordinatesImpl();
		coords.setLatitude(12);
		coords.setLongitude(52);
		coords.setElevation(120);
		
		Ring ring = new RingImpl();
		
		ring.getCoordinates().add(createCoordinate(1, 1));
		ring.getCoordinates().add(createCoordinate(2, 2));
		ring.getCoordinates().add(createCoordinate(3, 3));
		ring.getCoordinates().add(createCoordinate(4, 4));
		ring.getCoordinates().add(createCoordinate(5, 2));
		ring.getCoordinates().add(createCoordinate(6, 6));
		
		assertFalse(GeoJsonHelper.checkRingClosed(ring));
		
		ring.getCoordinates().add(createCoordinate(1, 1));
		
		assertTrue(GeoJsonHelper.checkRingClosed(ring));
	}

	@Test
	public void testRingAutoclose() throws IOException {
		System.err.println("Running Test Point");
		
		Coordinates coords = new CoordinatesImpl();
		coords.setLatitude(12);
		coords.setLongitude(52);
		coords.setElevation(120);
		
		Ring ring = new RingImpl();
		
		ring.getCoordinates().add(createCoordinate(1, 1));
		ring.getCoordinates().add(createCoordinate(2, 2));
		ring.getCoordinates().add(createCoordinate(3, 3));
		ring.getCoordinates().add(createCoordinate(4, 4));
		ring.getCoordinates().add(createCoordinate(5, 2));
		ring.getCoordinates().add(createCoordinate(6, 6));
		
		assertFalse(GeoJsonHelper.checkRingClosed(ring));
		
		ring.getCoordinates().add(createCoordinate(1, 1));
		
		assertTrue(GeoJsonHelper.checkRingClosed(ring));
	}

	@Test
	public void point(@InjectService ResourceSet set) throws IOException {
		System.err.println("Running Test Point");
		
		Point point = new PointImpl();
		Coordinates coords = new CoordinatesImpl();
		coords.setLatitude(12);
		coords.setLongitude(52);
		coords.setElevation(120);
		
		point.setCoordinates(coords);
		
		JsonResource resource = (JsonResource) set.createResource(URI.createURI("test.json"));
		resource.getContents().add(point);
		Map<String, Object> map = new HashMap<>();
		map.put(EMFJs.OPTION_SERIALIZE_DEFAULT_VALUE, true);
		map.put(EMFJs.OPTION_TYPE_FIELD, "type");
		map.put(EMFJs.OPTION_TYPE_USE, EcoreTypeInfo.USE.NAME);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		resource.save(baos, map);
		
		System.out.println(new String(baos.toByteArray()));
		
		
		String test = """
				{
  "type" : "Point",
  "coordinates" : [ 52.0, 12.0, 120.0 ]
				}
				""";
		
		System.err.println("Trying to load Point");
		
		JsonResource loadResource = (JsonResource) set.createResource(URI.createURI("test2.json"));
		loadResource.load(new ByteArrayInputStream(test.getBytes()), map);
		
		System.err.println(loadResource.getContents().isEmpty());
		assertFalse(loadResource.getContents().isEmpty());
		Point result = (Point) loadResource.getContents().get(0);
		System.err.println(result.getCoordinates());
	}

	@Test
	public void pointWithBoundingBox(@InjectService ResourceSet set) throws IOException {
		System.err.println("Running Test Point with BoundingBox");
		
		Point point = new PointImpl();
		Coordinates coords = new CoordinatesImpl();
		coords.setLatitude(12);
		coords.setLongitude(52);
		coords.setElevation(120);
		
		point.setCoordinates(coords);
		
		point.getBoundingBox().add(EcoreUtil.copy(coords));
		point.getBoundingBox().add(EcoreUtil.copy(coords));
		
		JsonResource resource = (JsonResource) set.createResource(URI.createURI("test.json"));
		resource.getContents().add(point);
		Map<String, Object> map = new HashMap<>();
		map.put(EMFJs.OPTION_SERIALIZE_DEFAULT_VALUE, true);
		map.put(EMFJs.OPTION_TYPE_FIELD, "type");
		map.put(EMFJs.OPTION_TYPE_USE, EcoreTypeInfo.USE.NAME);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		resource.save(baos, map);
		
		System.out.println(new String(baos.toByteArray()));
		
		
		String test = """
				{
  "type" : "Point",
  "bbox" : [ [ 52.0, 12.0, 120.0 ], [ 52.0, 12.0, 120.0 ] ],
  "coordinates" : [ 52.0, 12.0, 120.0 ]
				}
				""";
		
		System.err.println("Trying to load Point with BoundingBox");
		
		JsonResource loadResource = (JsonResource) set.createResource(URI.createURI("test2.json"));
		loadResource.load(new ByteArrayInputStream(test.getBytes()), map);
		
		System.err.println(loadResource.getContents().isEmpty());
		assertFalse(loadResource.getContents().isEmpty());
		assertFalse(loadResource.getContents().isEmpty());
		Point result = (Point) loadResource.getContents().get(0);
		System.err.println(result.getCoordinates());
		assertFalse(result.getBoundingBox().isEmpty());
		assertThat(result.getBoundingBox()).hasSize(2);
	}

	@Test
	public void lineString(@InjectService ResourceSet set) throws IOException {
		System.err.println("Running Test");
		
		LineString point = new LineStringImpl();
		Coordinates coords = new CoordinatesImpl();
		coords.setLatitude(12);
		coords.setLongitude(52);
		coords.setElevation(120);
		
		point.getCoordinates().add(coords);
		point.getCoordinates().add(EcoreUtil.copy(coords));
		
		JsonResource resource = (JsonResource) set.createResource(URI.createURI("test.json"));
		resource.getContents().add(point);
		Map<String, Object> map = new HashMap<>();
		map.put(EMFJs.OPTION_SERIALIZE_DEFAULT_VALUE, true);
		map.put(EMFJs.OPTION_TYPE_FIELD, "type");
		map.put(EMFJs.OPTION_TYPE_USE, EcoreTypeInfo.USE.NAME);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		resource.save(baos, map);

		System.out.println(new String(baos.toByteArray()));
		
		
		String test = """
				{
  "type" : "LineString",
  "coordinates" : [ [ 52.0, 12.0, 120.0 ], [ 52.0, 12.0, 120.0 ] ]
}
				""";
		
		System.err.println("Trying to load LineString");
		
		JsonResource loadResource = (JsonResource) set.createResource(URI.createURI("test2.json"));
		loadResource.load(new ByteArrayInputStream(test.getBytes()), map);
		
		System.err.println(loadResource.getContents().isEmpty());
		assertFalse(loadResource.getContents().isEmpty());
		LineString result = (LineString) loadResource.getContents().get(0);
		result.getCoordinates().forEach(System.err::println);;
	}

	@Test
	public void multiLineString(@InjectService ResourceSet set) throws IOException {
		System.err.println("Running Test MultiLineString");
		
		LineString line = new LineStringImpl();
		Coordinates coords = new CoordinatesImpl();
		coords.setLatitude(12);
		coords.setLongitude(52);
		coords.setElevation(120);
		
		line.getCoordinates().add(createCoordinate(12, 52, 120));
		line.getCoordinates().add(createCoordinate(11, 53, 123));
		
		MultiLineString multiLineString = new MultiLineStringImpl();
		
		multiLineString.getLinesStrings().add(line);
		multiLineString.getLinesStrings().add(EcoreUtil.copy(line));
		
		JsonResource resource = (JsonResource) set.createResource(URI.createURI("test.json"));
		resource.getContents().add(multiLineString);
		Map<String, Object> map = new HashMap<>();
		map.put(EMFJs.OPTION_SERIALIZE_DEFAULT_VALUE, true);
		map.put(EMFJs.OPTION_TYPE_FIELD, "type");
		map.put(EMFJs.OPTION_TYPE_USE, EcoreTypeInfo.USE.NAME);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		resource.save(baos, map);
		
		System.out.println(new String(baos.toByteArray()));
		
		
		
		System.err.println("Trying to load MultiLineString");
		
		JsonResource loadResource = (JsonResource) set.createResource(URI.createURI("test2.json"));
		loadResource.load(new ByteArrayInputStream(baos.toByteArray()), map);
		
		System.err.println(loadResource.getContents().isEmpty());
		assertFalse(loadResource.getContents().isEmpty());
		MultiLineString result = (MultiLineString) loadResource.getContents().get(0);
		
		assertThat(result.getLinesStrings()).hasSameSizeAs(multiLineString.getLinesStrings());
		
	}

	@Test
	public void multiPointString(@InjectService ResourceSet set) throws IOException {
		System.err.println("Running Test");
		
		MultiPoint point = new MultiPointImpl();
		Coordinates coords = new CoordinatesImpl();
		coords.setLatitude(12);
		coords.setLongitude(52);
		coords.setElevation(120);
		
		point.getCoordinates().add(coords);
		point.getCoordinates().add(EcoreUtil.copy(coords));
		
		JsonResource resource = (JsonResource) set.createResource(URI.createURI("test.json"));
		resource.getContents().add(point);
		Map<String, Object> map = new HashMap<>();
		map.put(EMFJs.OPTION_SERIALIZE_DEFAULT_VALUE, true);
		map.put(EMFJs.OPTION_TYPE_FIELD, "type");
		map.put(EMFJs.OPTION_TYPE_USE, EcoreTypeInfo.USE.NAME);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		resource.save(baos, map);
		
		System.out.println(new String(baos.toByteArray()));
		
		
		String test = """
				{
  "type" : "MultiPoint",
  "coordinates" : [ [ 52.0, 12.0, 120.0 ], [ 52.0, 12.0, 120.0 ] ]
				}
				""";
		
		System.err.println("Trying to load MultiPoint");
		
		JsonResource loadResource = (JsonResource) set.createResource(URI.createURI("test2.json"));
		loadResource.load(new ByteArrayInputStream(test.getBytes()), map);
		
		System.err.println(loadResource.getContents().isEmpty());
		assertFalse(loadResource.getContents().isEmpty());
		MultiPoint result = (MultiPoint) loadResource.getContents().get(0);
		result.getCoordinates().forEach(System.err::println);;
	}

	@Test
	public void feature(@InjectService ResourceSet set) throws IOException {
		System.err.println("Running Feature Test");
		
		Feature feature = new FeatureImpl();
		Coordinates coords = new CoordinatesImpl();
		coords.setLatitude(12);
		coords.setLongitude(52);
		coords.setElevation(120);
		
		feature.getBoundingBox().add(coords);
		feature.getBoundingBox().add(EcoreUtil.copy(coords));
		feature.setProperties(EcoreUtil.copy(coords));		
		
		JsonResource resource = (JsonResource) set.createResource(URI.createURI("test.json"));
		resource.getContents().add(feature);
		Map<String, Object> map = new HashMap<>();
		map.put(EMFJs.OPTION_SERIALIZE_DEFAULT_VALUE, true);
		map.put(EMFJs.OPTION_TYPE_FIELD, "type");
		map.put(EMFJs.OPTION_TYPE_USE, EcoreTypeInfo.USE.NAME);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		resource.save(baos, map);
		
		System.out.println(new String(baos.toByteArray()));
		
		
		String test = """
				{
  "type" : "Feature",
  "bbox" : [ [ 52.0, 12.0, 120.0 ], [ 52.0, 12.0, 120.0 ] ]
				}
				""";
		
		System.err.println("Trying to load Feature");
		
		JsonResource loadResource = (JsonResource) set.createResource(URI.createURI("test2.json"));
		loadResource.load(new ByteArrayInputStream(test.getBytes()), map);
		
		System.err.println(loadResource.getContents().isEmpty());
		assertFalse(loadResource.getContents().isEmpty());
		Feature result = (Feature) loadResource.getContents().get(0);
		assertThat(result.getBoundingBox()).hasSize(2);
		assertThat(feature.getProperties()).isInstanceOf(Coordinates.class);
		
	}
	
	@Test
	public void polygon(@InjectService ResourceSet set) throws IOException {
		System.err.println("Running Test Polygon");
		
		
		Polygon polygon = new PolygonImpl();
		Coordinates coords = new CoordinatesImpl();
		coords.setLatitude(12);
		coords.setLongitude(52);
		coords.setElevation(120);
		
		Ring ring = new RingImpl();
		
		ring.getCoordinates().add(createCoordinate(1, 1));
		ring.getCoordinates().add(createCoordinate(2, 2));
		ring.getCoordinates().add(createCoordinate(3, 3));
		ring.getCoordinates().add(createCoordinate(4, 4));
		ring.getCoordinates().add(createCoordinate(5, 2));
		ring.getCoordinates().add(createCoordinate(6, 6));

		Hole hole = new HoleImpl();
		
		hole.getCoordinates().add(createCoordinate(1, 1));
		hole.getCoordinates().add(createCoordinate(2, 2));
		hole.getCoordinates().add(createCoordinate(3, 3));
		hole.getCoordinates().add(createCoordinate(4, 4));
		hole.getCoordinates().add(createCoordinate(5, 2));
		hole.getCoordinates().add(createCoordinate(6, 6));
		
		polygon.setExteriorRing(ring);
		polygon.getInteriorHoles().add(hole);
		polygon.getInteriorHoles().add(EcoreUtil.copy(hole));
		
		polygon.getBoundingBox().add(EcoreUtil.copy(coords));
		polygon.getBoundingBox().add(EcoreUtil.copy(coords));
		
		JsonResource resource = (JsonResource) set.createResource(URI.createURI("test.json"));
		resource.getContents().add(polygon);
		Map<String, Object> map = new HashMap<>();
		map.put(EMFJs.OPTION_SERIALIZE_DEFAULT_VALUE, true);
		map.put(EMFJs.OPTION_TYPE_FIELD, "type");
		map.put(EMFJs.OPTION_TYPE_USE, EcoreTypeInfo.USE.NAME);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		resource.save(baos, map);

		System.out.println(new String(baos.toByteArray()));
		
		System.err.println("Trying to load Polygon");
		
		JsonResource loadResource = (JsonResource) set.createResource(URI.createURI("test2.json"));
		loadResource.load(new ByteArrayInputStream(baos.toByteArray()), map);
		
		System.err.println(loadResource.getContents().isEmpty());
		assertFalse(loadResource.getContents().isEmpty());
		Polygon result = (Polygon) loadResource.getContents().get(0);
		baos = new ByteArrayOutputStream();
		loadResource.save(baos, map);
		System.out.println(new String(baos.toByteArray()));
		
		assertThat(result.getBoundingBox()).hasSize(2);
		assertThat(result.getExteriorRing()).isNotNull();
		assertThat(result.getExteriorRing().getCoordinates()).hasSize(polygon.getExteriorRing().getCoordinates().size() + 1);
		assertThat(result.getInteriorHoles()).hasSize(2);
		
	}

}
