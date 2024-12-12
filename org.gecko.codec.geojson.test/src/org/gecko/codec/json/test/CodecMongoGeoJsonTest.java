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
package org.gecko.codec.json.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fennec.models.geojson.Coordinates;
import org.eclipse.fennec.models.geojson.Feature;
import org.eclipse.fennec.models.geojson.GeoJsonFactory;
import org.eclipse.fennec.models.geojson.Hole;
import org.eclipse.fennec.models.geojson.LineString;
import org.eclipse.fennec.models.geojson.MultiLineString;
import org.eclipse.fennec.models.geojson.MultiPoint;
import org.eclipse.fennec.models.geojson.Point;
import org.eclipse.fennec.models.geojson.Polygon;
import org.eclipse.fennec.models.geojson.Ring;
import org.eclipse.fennec.models.geojson.impl.CoordinatesImpl;
import org.eclipse.fennec.models.geojson.impl.HoleImpl;
import org.eclipse.fennec.models.geojson.impl.MultiLineStringImpl;
import org.eclipse.fennec.models.geojson.impl.PolygonImpl;
import org.eclipse.fennec.models.geojson.impl.RingImpl;
import org.gecko.codec.configurator.CodecFactoryConfigurator;
import org.gecko.codec.configurator.CodecModuleConfigurator;
import org.gecko.codec.configurator.ObjectMapperConfigurator;
import org.gecko.emf.osgi.annotation.require.RequireEMF;
import org.gecko.emf.osgi.constants.EMFNamespaces;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.osgi.framework.BundleContext;
import org.osgi.test.common.annotation.InjectBundleContext;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.common.annotation.Property;
import org.osgi.test.common.annotation.config.WithFactoryConfiguration;
import org.osgi.test.common.service.ServiceAware;
import org.osgi.test.junit5.cm.ConfigurationExtension;
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
@RequireEMF
@ExtendWith(BundleContextExtension.class)
@ExtendWith(ServiceExtension.class)
@ExtendWith(MockitoExtension.class)
@ExtendWith(ConfigurationExtension.class)
@WithFactoryConfiguration(name = "mongoClient", location = "?", factoryPid = "MongoClientProvider", properties = {
		@Property(key = "client_id", value = "test"), @Property(key = "uri", value = "mongodb://localhost:27017") })
@WithFactoryConfiguration(name = "mongoDatabase", location = "?", factoryPid = "MongoDatabaseProvider", properties = {
		@Property(key = "alias", value = "TestDB"), @Property(key = "database", value = "test") })
@WithFactoryConfiguration(factoryPid = "DefaultCodecFactoryConfigurator", location = "?", name = "test", properties = {
		@Property(key = "type", value="mongo"),
		@Property(key = "genFactory.target", value="(type=mongo)"), 
		@Property(key = "parserFactory.target", value="(type=mongo)")
})
@WithFactoryConfiguration(factoryPid = "DefaultObjectMapperConfigurator", location = "?", name = "test", properties = {
		@Property(key = "codecFactoryConfigurator.target", value="(type=mongo)"),
		@Property(key = "type", value="mongo")
})
@WithFactoryConfiguration(factoryPid = "DefaultCodecModuleConfigurator", location = "?", name = "test", properties = {
		@Property(key = "type", value="mongo")
})
public class CodecMongoGeoJsonTest extends MongoEMFSetting{
	
	@InjectService(cardinality = 0, filter = "(&(" + EMFNamespaces.EMF_CONFIGURATOR_NAME + "=mongo)("
			+ EMFNamespaces.EMF_MODEL_NAME + "=collection)("+ EMFNamespaces.EMF_MODEL_NAME + "=geojson))")
	ServiceAware<ResourceSet> rsAware;
	
	@InjectService(cardinality = 0, filter = "(type=mongo)")
	ServiceAware<CodecFactoryConfigurator> codecFactoryAware;
	
	@InjectService(cardinality = 0, filter = "(type=mongo)")
	ServiceAware<ObjectMapperConfigurator> mapperAware;
	
	@InjectService(cardinality = 0, filter = "(type=mongo)")
	ServiceAware<CodecModuleConfigurator> codecModuleAware;
	
	private ResourceSet resourceSet;	
	
	@BeforeEach() 
	public void beforeEach(@InjectBundleContext BundleContext ctx) throws Exception{
		super.doBefore(ctx);
		codecFactoryAware.waitForService(2000l);
		mapperAware.waitForService(2000l);
		codecModuleAware.waitForService(2000l);	
		resourceSet = rsAware.waitForService(2000l);
		assertNotNull(resourceSet);
		
	}
	
	@AfterEach() 
	public void afterEach() {
		super.doAfter();	
	}
	

	@Test
	public void testSerializationPoint() throws InterruptedException, IOException {
		collection = client.getDatabase("test").getCollection("Point");
		URI uri = URI.createURI("mongodb://"+ mongoHost + ":27017/test/Point/");
		Resource resource = resourceSet.createResource(uri);
		Point point = GeoJsonFactory.eINSTANCE.createPoint();
		Coordinates coord = GeoJsonFactory.eINSTANCE.createCoordinates();
		coord.setLatitude(50.17);
		coord.setLongitude(11.24);
		coord.setElevation(104);
		point.setCoordinates(coord);
				
		resource.getContents().add(point);
		Map<String, Object> options = new HashMap<>();
		
		resource.save(options);
		
		resource.getContents().clear();
		resource.unload();
		/*
		 * Find person in the collection
		 */
		// long start = System.currentTimeMillis();
		Resource findResource = resourceSet.createResource(
				URI.createURI("mongodb://" + mongoHost + ":27017/test/Point/"));
		findResource.load(options);
		
		assertNotNull(findResource);
		assertFalse(findResource.getContents().isEmpty());
		assertEquals(1, findResource.getContents().size());
		
		Point result = (Point) findResource.getContents().get(0);
		coord = result.getCoordinates();
		assertEquals(11.24, coord.getLongitude());
		assertEquals(50.17, coord.getLatitude());
		assertEquals(104.0, coord.getElevation());
	}
	
	
	@Test
	public void testSerializationPointWithBoundingBox() throws InterruptedException, IOException {
		collection = client.getDatabase("test").getCollection("Point");
		URI uri = URI.createURI("mongodb://"+ mongoHost + ":27017/test/Point/");
		Resource resource = resourceSet.createResource(uri);
		Point point = GeoJsonFactory.eINSTANCE.createPoint();
		Coordinates coord = GeoJsonFactory.eINSTANCE.createCoordinates();
		coord.setLatitude(50.17);
		coord.setLongitude(11.24);
		coord.setElevation(104);
		point.setCoordinates(coord);
		
		point.getBoundingBox().add(EcoreUtil.copy(coord));
		point.getBoundingBox().add(EcoreUtil.copy(coord));
				
		resource.getContents().add(point);
		Map<String, Object> options = new HashMap<>();
		
		resource.save(options);
		
		resource.getContents().clear();
		resource.unload();
		/*
		 * Find person in the collection
		 */
		// long start = System.currentTimeMillis();
		Resource findResource = resourceSet.createResource(
				URI.createURI("mongodb://" + mongoHost + ":27017/test/Point/"));
		findResource.load(options);
		
		assertNotNull(findResource);
		assertFalse(findResource.getContents().isEmpty());
		assertEquals(1, findResource.getContents().size());
		
		Point result = (Point) findResource.getContents().get(0);
		coord = result.getCoordinates();
		assertEquals(11.24, coord.getLongitude());
		assertEquals(50.17, coord.getLatitude());
		assertEquals(104.0, coord.getElevation());
		assertFalse(result.getBoundingBox().isEmpty());
		assertThat(result.getBoundingBox()).hasSize(2);
	}
	
	@Test
	public void testSerializationLineString() throws InterruptedException, IOException {
		collection = client.getDatabase("test").getCollection("LineString");
		URI uri = URI.createURI("mongodb://"+ mongoHost + ":27017/test/LineString/");
		Resource resource = resourceSet.createResource(uri);
		LineString lineString = GeoJsonFactory.eINSTANCE.createLineString();
		Coordinates coords = GeoJsonFactory.eINSTANCE.createCoordinates();
		coords.setLatitude(12);
		coords.setLongitude(52);
		coords.setElevation(120);
		
		lineString.getCoordinates().add(coords);
		lineString.getCoordinates().add(EcoreUtil.copy(coords));
		
		resource.getContents().add(lineString);
		Map<String, Object> options = new HashMap<>();
		
		resource.save(options);
		
		resource.getContents().clear();
		resource.unload();
		/*
		 * Find person in the collection
		 */
		// long start = System.currentTimeMillis();
		Resource findResource = resourceSet.createResource(
				URI.createURI("mongodb://" + mongoHost + ":27017/test/LineString/"));
		findResource.load(options);
		
		assertNotNull(findResource);
		assertFalse(findResource.getContents().isEmpty());
		assertEquals(1, findResource.getContents().size());
		
		LineString result = (LineString) findResource.getContents().get(0);
		assertThat(result.getCoordinates()).hasSize(2);
		for(Coordinates coord : result.getCoordinates()) {
			assertEquals(52.0, coord.getLongitude());
			assertEquals(12.0, coord.getLatitude());
			assertEquals(120.0, coord.getElevation());
		}
	}
	
	@Test
	public void testSerializationMultiLineString() throws InterruptedException, IOException {
		collection = client.getDatabase("test").getCollection("MultiLineString");
		URI uri = URI.createURI("mongodb://"+ mongoHost + ":27017/test/MultiLineString/");
		Resource resource = resourceSet.createResource(uri);
		LineString lineString = GeoJsonFactory.eINSTANCE.createLineString();
		Coordinates coords = GeoJsonFactory.eINSTANCE.createCoordinates();
		coords.setLatitude(12);
		coords.setLongitude(52);
		coords.setElevation(120);
		
		lineString.getCoordinates().add(coords);
		lineString.getCoordinates().add(EcoreUtil.copy(coords));
		
		MultiLineString multiLineString = new MultiLineStringImpl();
		
		multiLineString.getLinesStrings().add(lineString);
		multiLineString.getLinesStrings().add(EcoreUtil.copy(lineString));
		
		resource.getContents().add(multiLineString);
		Map<String, Object> options = new HashMap<>();
		
		resource.save(options);
		
		resource.getContents().clear();
		resource.unload();
				
		Resource loadResource = resourceSet.createResource(uri);
		loadResource.load(options);
		
		assertFalse(loadResource.getContents().isEmpty());
		MultiLineString result = (MultiLineString) loadResource.getContents().get(0);		
		assertThat(result.getLinesStrings()).hasSameSizeAs(multiLineString.getLinesStrings());
	}
	
	@Test
	public void testSerializationMultiPoint() throws IOException {
		
		collection = client.getDatabase("test").getCollection("MultiPoint");
		URI uri = URI.createURI("mongodb://"+ mongoHost + ":27017/test/MultiPoint/");
		Resource resource = resourceSet.createResource(uri);
		MultiPoint point = GeoJsonFactory.eINSTANCE.createMultiPoint();
		Coordinates coords = GeoJsonFactory.eINSTANCE.createCoordinates();
		coords.setLatitude(12);
		coords.setLongitude(52);
		coords.setElevation(120);
		
		point.getCoordinates().add(coords);
		point.getCoordinates().add(EcoreUtil.copy(coords));
		
		resource.getContents().add(point);
	
		Map<String, Object> options = new HashMap<>();
		
		resource.save(options);
		resource.getContents().clear();
		resource.unload();
				
		Resource loadResource = resourceSet.createResource(uri);
		loadResource.load(options);
		
		assertFalse(loadResource.getContents().isEmpty());
		MultiPoint result = (MultiPoint) loadResource.getContents().get(0);
		assertThat(result.getCoordinates()).hasSize(2);
		for(Coordinates coord : result.getCoordinates()) {
			assertEquals(52.0, coord.getLongitude());
			assertEquals(12.0, coord.getLatitude());
			assertEquals(120.0, coord.getElevation());
		}
	}
	
	@Test
	public void testSerializationFeature(@InjectService ResourceSet set) throws IOException {
		
		Feature feature = GeoJsonFactory.eINSTANCE.createFeature();
		Coordinates coords = GeoJsonFactory.eINSTANCE.createCoordinates();
		coords.setLatitude(12);
		coords.setLongitude(52);
		coords.setElevation(120);
		
		feature.getBoundingBox().add(coords);
		feature.getBoundingBox().add(EcoreUtil.copy(coords));
		feature.setProperties(EcoreUtil.copy(coords));		
		
		collection = client.getDatabase("test").getCollection("Feature");
		URI uri = URI.createURI("mongodb://"+ mongoHost + ":27017/test/Feature/");
		Resource resource = resourceSet.createResource(uri);
		resource.getContents().add(feature);
		Map<String, Object> options = new HashMap<>();
		
		resource.save(options);
		resource.getContents().clear();
		resource.unload();
		
		Resource loadResource = resourceSet.createResource(uri);
		loadResource.load(options);
		
		assertFalse(loadResource.getContents().isEmpty());
		Feature result = (Feature) loadResource.getContents().get(0);
		assertThat(result.getBoundingBox()).hasSize(2);
		assertThat(feature.getProperties()).isInstanceOf(Coordinates.class);
		
	}
	
	@Test
	public void testSerializationPolygon(@InjectService ResourceSet set) throws IOException {
		
		
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
		
		collection = client.getDatabase("test").getCollection("Polygon");
		URI uri = URI.createURI("mongodb://"+ mongoHost + ":27017/test/Polygon/");
		Resource resource = resourceSet.createResource(uri);
		resource.getContents().add(polygon);
		
		Map<String, Object> options = new HashMap<>();
		
		resource.save(options);
		resource.getContents().clear();
		resource.unload();
				
		Resource loadResource = resourceSet.createResource(uri);
		loadResource.load(options);
		
		assertFalse(loadResource.getContents().isEmpty());
		Polygon result = (Polygon) loadResource.getContents().get(0);
	
		assertThat(result.getBoundingBox()).hasSize(2);
		assertThat(result.getExteriorRing()).isNotNull();
		assertThat(result.getExteriorRing().getCoordinates()).hasSize(polygon.getExteriorRing().getCoordinates().size() + 1);
		assertThat(result.getInteriorHoles()).hasSize(2);
		
	}
	
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
	
}
