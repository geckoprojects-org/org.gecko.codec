/**
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
 */
package org.eclipse.fennec.codec.v2.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.fennec.codec.v2.config.CodecConfiguration;
import org.eclipse.fennec.codec.v2.util.MetadataServiceFactory;
import org.eclipse.fennec.model.metadata.TypeStrategy;
import org.eclipse.fennec.model.metadata.api.MetadataService;
import org.geojson.Coordinates;
import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.GeoJsonFactory;
import org.geojson.GeoJsonPackage;
import org.geojson.Hole;
import org.geojson.LineString;
import org.geojson.Point;
import org.geojson.Polygon;
import org.geojson.Ring;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Tests for real GeoJSON serialization and deserialization using the generated org.geojson.model.
 * <p>
 * This tests actual GeoJSON format with coordinates as arrays:
 * <ul>
 *   <li>Point: {@code "coordinates": [lng, lat]} or {@code [lng, lat, elev]}</li>
 *   <li>LineString: {@code "coordinates": [[lng, lat], [lng, lat], ...]}</li>
 *   <li>Polygon: {@code "coordinates": [[[lng, lat], ...], [[lng, lat], ...]]}</li>
 * </ul>
 * </p>
 *
 * @see <a href="https://github.com/geckoprojects-org/org.gecko.emf.models/tree/main/org.geojson.model">org.geojson.model</a>
 */
@DisplayName("Real GeoJSON Round-Trip Tests")
class RealGeoJsonRoundTripTest {

    private MetadataService metadataService;
    private GeoJsonPackage geoPackage;
    private GeoJsonFactory geoFactory;

    @BeforeEach
    void setUp() {
        geoPackage = GeoJsonPackage.eINSTANCE;
        geoFactory = GeoJsonFactory.eINSTANCE;
        EPackage.Registry.INSTANCE.put(geoPackage.getNsURI(), geoPackage);

        metadataService = MetadataServiceFactory.create();
        metadataService.registerPackage(geoPackage);
    }

    @AfterEach
    void tearDown() {
        EPackage.Registry.INSTANCE.remove(geoPackage.getNsURI());
    }

    private CodecConfiguration createGeoJsonConfig() {
        return CodecConfiguration.builder()
                .typeKey("type")  // GeoJSON uses "type" not "_type"
                .typeStrategy(TypeStrategy.NAME)  // GeoJSON uses simple names like "Point", "Feature"
                .useNamesFromExtendedMetaData(true)  // GeoJSON uses ExtendedMetaData for "coordinates" mapping
                .forceSerialize("data", "bbox")  // GeoJSON model has volatile attributes that must be serialized
                .build();
    }

    /**
     * Loads JSON using "type" as type discriminator (GeoJSON style).
     */
    private <T extends EObject> T loadGeoJson(String json, Class<T> expectedType) throws IOException {
        CodecConfiguration config = createGeoJsonConfig();

        CodecResource resource = new CodecResource(
                URI.createURI("test://geo.json"),
                metadataService,
                config,
                null);

        Map<String, Object> options = new HashMap<>();
        options.put(CodecResource.CODEC_ROOT_SCHEMA, geoPackage.getNsURI());

        try (var is = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8))) {
            resource.load(is, options);
        }

        if (!resource.getErrors().isEmpty()) {
            fail("Deserialization errors: " + resource.getErrors());
        }

        assertEquals(1, resource.getContents().size(), "Should have exactly one root object");
        EObject result = resource.getContents().get(0);

        if (!expectedType.isInstance(result)) {
            fail("Expected " + expectedType.getSimpleName() + " but got " + result.eClass().getName());
        }

        return expectedType.cast(result);
    }

    /**
     * Saves an EObject to JSON using GeoJSON style configuration.
     */
    private String saveGeoJson(EObject object) throws IOException {
        CodecConfiguration config = createGeoJsonConfig();

        CodecResource resource = new CodecResource(
                URI.createURI("test://geo.json"),
                metadataService,
                config,
                null);

        resource.getContents().add(object);

        Map<String, Object> options = new HashMap<>();

        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            resource.save(os, options);

            if (!resource.getErrors().isEmpty()) {
                fail("Serialization errors: " + resource.getErrors());
            }

            return os.toString(StandardCharsets.UTF_8);
        }
    }

    /**
     * Creates a Coordinates object with longitude and latitude.
     */
    private Coordinates createCoordinates(double longitude, double latitude) {
        Coordinates coords = geoFactory.createCoordinates();
        coords.setLongitude(longitude);
        coords.setLatitude(latitude);
        return coords;
    }

    /**
     * Creates a Coordinates object with longitude, latitude, and elevation.
     */
    private Coordinates createCoordinates(double longitude, double latitude, double elevation) {
        Coordinates coords = createCoordinates(longitude, latitude);
        coords.setElevation(elevation);
        return coords;
    }

    // ========================================================================
    // Deserialization Tests
    // ========================================================================

    @Nested
    @DisplayName("Deserialization")
    class DeserializationTests {

        @Nested
        @DisplayName("Point Geometry")
        class PointTests {

            @Test
            @DisplayName("2D point with array coordinates")
            void point2D() throws IOException {
                String json = """
                    {
                        "type": "Point",
                        "coordinates": [8.6821, 50.1109]
                    }
                    """;

                Point point = loadGeoJson(json, Point.class);
                assertNotNull(point);

                Coordinates coords = point.getCoordinates();
                assertNotNull(coords, "Coordinates should be set");
                assertEquals(8.6821, coords.getLongitude(), 0.0001);
                assertEquals(50.1109, coords.getLatitude(), 0.0001);
            }

            @Test
            @DisplayName("3D point with elevation")
            void point3D() throws IOException {
                String json = """
                    {
                        "type": "Point",
                        "coordinates": [13.405, 52.52, 34.5]
                    }
                    """;

                Point point = loadGeoJson(json, Point.class);

                Coordinates coords = point.getCoordinates();
                assertEquals(13.405, coords.getLongitude(), 0.0001);
                assertEquals(52.52, coords.getLatitude(), 0.0001);
                assertEquals(34.5, coords.getElevation(), 0.0001);
            }

            @Test
            @DisplayName("point with negative coordinates")
            void pointNegativeCoordinates() throws IOException {
                String json = """
                    {
                        "type": "Point",
                        "coordinates": [-122.4194, 37.7749]
                    }
                    """;

                Point point = loadGeoJson(json, Point.class);

                Coordinates coords = point.getCoordinates();
                assertEquals(-122.4194, coords.getLongitude(), 0.0001);
                assertEquals(37.7749, coords.getLatitude(), 0.0001);
            }
        }

        @Nested
        @DisplayName("LineString Geometry")
        class LineStringTests {

            @Test
            @DisplayName("simple linestring with 3 points")
            void simpleLineString() throws IOException {
                String json = """
                    {
                        "type": "LineString",
                        "coordinates": [
                            [0.0, 0.0],
                            [10.0, 10.0],
                            [20.0, 20.0]
                        ]
                    }
                    """;

                LineString line = loadGeoJson(json, LineString.class);
                assertNotNull(line);

                assertEquals(3, line.getCoordinates().size());

                Coordinates c0 = line.getCoordinates().get(0);
                assertEquals(0.0, c0.getLongitude(), 0.0001);
                assertEquals(0.0, c0.getLatitude(), 0.0001);

                Coordinates c2 = line.getCoordinates().get(2);
                assertEquals(20.0, c2.getLongitude(), 0.0001);
                assertEquals(20.0, c2.getLatitude(), 0.0001);
            }

            @Test
            @DisplayName("linestring with real-world coordinates")
            void realWorldLineString() throws IOException {
                String json = """
                    {
                        "type": "LineString",
                        "coordinates": [
                            [8.6821, 50.1109],
                            [8.6831, 50.1115],
                            [8.6845, 50.1120],
                            [8.6860, 50.1118]
                        ]
                    }
                    """;

                LineString line = loadGeoJson(json, LineString.class);
                assertEquals(4, line.getCoordinates().size());

                assertEquals(8.6821, line.getCoordinates().get(0).getLongitude(), 0.0001);
                assertEquals(8.6860, line.getCoordinates().get(3).getLongitude(), 0.0001);
            }
        }

        @Nested
        @DisplayName("Polygon Geometry")
        class PolygonTests {

            @Test
            @DisplayName("simple polygon (triangle)")
            void simplePolygon() throws IOException {
                String json = """
                    {
                        "type": "Polygon",
                        "coordinates": [
                            [
                                [0.0, 0.0],
                                [10.0, 0.0],
                                [5.0, 10.0],
                                [0.0, 0.0]
                            ]
                        ]
                    }
                    """;

                Polygon polygon = loadGeoJson(json, Polygon.class);
                assertNotNull(polygon);

                assertNotNull(polygon.getExteriorRing());
                assertEquals(4, polygon.getExteriorRing().getCoordinates().size());

                Coordinates first = polygon.getExteriorRing().getCoordinates().get(0);
                Coordinates last = polygon.getExteriorRing().getCoordinates().get(3);
                assertEquals(first.getLongitude(), last.getLongitude(), 0.0001);
                assertEquals(first.getLatitude(), last.getLatitude(), 0.0001);
            }

            @Test
            @DisplayName("polygon with hole")
            void polygonWithHole() throws IOException {
                String json = """
                    {
                        "type": "Polygon",
                        "coordinates": [
                            [
                                [0.0, 0.0],
                                [20.0, 0.0],
                                [20.0, 20.0],
                                [0.0, 20.0],
                                [0.0, 0.0]
                            ],
                            [
                                [5.0, 5.0],
                                [15.0, 5.0],
                                [15.0, 15.0],
                                [5.0, 15.0],
                                [5.0, 5.0]
                            ]
                        ]
                    }
                    """;

                Polygon polygon = loadGeoJson(json, Polygon.class);

                assertNotNull(polygon.getExteriorRing());
                assertEquals(5, polygon.getExteriorRing().getCoordinates().size());

                assertEquals(1, polygon.getInteriorHoles().size());
                assertEquals(5, polygon.getInteriorHoles().get(0).getCoordinates().size());

                Coordinates holeFirst = polygon.getInteriorHoles().get(0).getCoordinates().get(0);
                assertEquals(5.0, holeFirst.getLongitude(), 0.0001);
                assertEquals(5.0, holeFirst.getLatitude(), 0.0001);
            }
        }

        @Nested
        @DisplayName("Feature")
        class FeatureTests {

            @Test
            @DisplayName("feature with point geometry")
            void featureWithPoint() throws IOException {
                String json = """
                    {
                        "type": "Feature",
                        "id": "berlin",
                        "geometry": {
                            "type": "Point",
                            "coordinates": [13.405, 52.52]
                        },
                        "properties": null
                    }
                    """;

                Feature feature = loadGeoJson(json, Feature.class);
                assertEquals("berlin", feature.getId());

                assertNotNull(feature.getGeometry());
                assertInstanceOf(Point.class, feature.getGeometry());

                Point point = (Point) feature.getGeometry();
                assertEquals(13.405, point.getCoordinates().getLongitude(), 0.0001);
            }

            @Test
            @DisplayName("feature with polygon geometry")
            void featureWithPolygon() throws IOException {
                String json = """
                    {
                        "type": "Feature",
                        "id": "area-1",
                        "geometry": {
                            "type": "Polygon",
                            "coordinates": [
                                [
                                    [0.0, 0.0],
                                    [10.0, 0.0],
                                    [10.0, 10.0],
                                    [0.0, 10.0],
                                    [0.0, 0.0]
                                ]
                            ]
                        },
                        "properties": null
                    }
                    """;

                Feature feature = loadGeoJson(json, Feature.class);
                assertEquals("area-1", feature.getId());

                assertNotNull(feature.getGeometry());
                assertInstanceOf(Polygon.class, feature.getGeometry());
            }
        }

        @Nested
        @DisplayName("FeatureCollection")
        class FeatureCollectionTests {

            @Test
            @DisplayName("collection with multiple features")
            void featureCollection() throws IOException {
                String json = """
                    {
                        "type": "FeatureCollection",
                        "features": [
                            {
                                "type": "Feature",
                                "id": "city-1",
                                "geometry": {
                                    "type": "Point",
                                    "coordinates": [2.3522, 48.8566]
                                },
                                "properties": null
                            },
                            {
                                "type": "Feature",
                                "id": "city-2",
                                "geometry": {
                                    "type": "Point",
                                    "coordinates": [-0.1276, 51.5074]
                                },
                                "properties": null
                            }
                        ]
                    }
                    """;

                FeatureCollection collection = loadGeoJson(json, FeatureCollection.class);
                assertEquals(2, collection.getFeatures().size());

                Feature paris = collection.getFeatures().get(0);
                assertEquals("city-1", paris.getId());

                Feature london = collection.getFeatures().get(1);
                assertEquals("city-2", london.getId());

                Point londonPoint = (Point) london.getGeometry();
                assertEquals(-0.1276, londonPoint.getCoordinates().getLongitude(), 0.0001);
                assertEquals(51.5074, londonPoint.getCoordinates().getLatitude(), 0.0001);
            }
        }

        @Nested
        @DisplayName("BoundingBox")
        class BBoxTests {

            @Test
            @DisplayName("polygon with bbox")
            void polygonWithBbox() throws IOException {
                String json = """
                    {
                        "type": "Polygon",
                        "bbox": [0.0, 0.0, 10.0, 10.0],
                        "coordinates": [
                            [
                                [0.0, 0.0],
                                [10.0, 0.0],
                                [10.0, 10.0],
                                [0.0, 10.0],
                                [0.0, 0.0]
                            ]
                        ]
                    }
                    """;

                Polygon polygon = loadGeoJson(json, Polygon.class);

                assertNotNull(polygon.getBoundingBox());
                assertNotNull(polygon.getBoundingBox().getSouthwest());
                assertNotNull(polygon.getBoundingBox().getNortheast());

                assertEquals(0.0, polygon.getBoundingBox().getSouthwest().getLongitude(), 0.0001);
                assertEquals(0.0, polygon.getBoundingBox().getSouthwest().getLatitude(), 0.0001);

                assertEquals(10.0, polygon.getBoundingBox().getNortheast().getLongitude(), 0.0001);
                assertEquals(10.0, polygon.getBoundingBox().getNortheast().getLatitude(), 0.0001);
            }
        }

        @Nested
        @DisplayName("Real-World Examples")
        class RealWorldTests {

            @Test
            @DisplayName("Jena polygon from test.json")
            void jenaPolygon() throws IOException {
                String json = """
                    {
                        "type": "Polygon",
                        "bbox": [11.504092, 50.895368, 11.566935, 50.913474],
                        "coordinates": [
                            [
                                [11.554532, 50.90168],
                                [11.554127, 50.901214],
                                [11.554528, 50.900991],
                                [11.554196, 50.900806],
                                [11.554532, 50.90168]
                            ]
                        ]
                    }
                    """;

                Polygon polygon = loadGeoJson(json, Polygon.class);

                assertNotNull(polygon.getBoundingBox());
                assertEquals(11.504092, polygon.getBoundingBox().getSouthwest().getLongitude(), 0.0001);
                assertEquals(50.895368, polygon.getBoundingBox().getSouthwest().getLatitude(), 0.0001);

                assertEquals(5, polygon.getExteriorRing().getCoordinates().size());

                Coordinates first = polygon.getExteriorRing().getCoordinates().get(0);
                assertEquals(11.554532, first.getLongitude(), 0.0001);
                assertEquals(50.90168, first.getLatitude(), 0.0001);
            }
        }
    }

    // ========================================================================
    // Serialization Tests
    // ========================================================================

    @Nested
    @DisplayName("Serialization")
    class SerializationTests {

        @Test
        @DisplayName("Point serialization")
        void serializePoint() throws IOException {
            Point point = geoFactory.createPoint();
            point.setCoordinates(createCoordinates(8.6821, 50.1109));

            String json = saveGeoJson(point);

            assertNotNull(json);
            // Debug output
            System.out.println("Serialized Point JSON: " + json);
            assertTrue(json.contains("\"type\""), "Should contain type field, got: " + json);
            assertTrue(json.contains("\"Point\""), "Should contain Point value, got: " + json);
            assertTrue(json.contains("\"coordinates\""), "Should contain coordinates field, got: " + json);
            assertTrue(json.contains("8.6821"), "Should contain longitude, got: " + json);
            assertTrue(json.contains("50.1109"), "Should contain latitude, got: " + json);
        }

        @Test
        @DisplayName("LineString serialization")
        void serializeLineString() throws IOException {
            LineString line = geoFactory.createLineString();
            line.getCoordinates().add(createCoordinates(0.0, 0.0));
            line.getCoordinates().add(createCoordinates(10.0, 10.0));
            line.getCoordinates().add(createCoordinates(20.0, 20.0));

            String json = saveGeoJson(line);

            assertNotNull(json);
            assertTrue(json.contains("\"type\""));
            assertTrue(json.contains("\"LineString\""));
            assertTrue(json.contains("\"coordinates\""));
        }

        @Test
        @DisplayName("Polygon serialization")
        void serializePolygon() throws IOException {
            Polygon polygon = geoFactory.createPolygon();

            Ring exteriorRing = geoFactory.createRing();
            exteriorRing.getCoordinates().add(createCoordinates(0.0, 0.0));
            exteriorRing.getCoordinates().add(createCoordinates(10.0, 0.0));
            exteriorRing.getCoordinates().add(createCoordinates(10.0, 10.0));
            exteriorRing.getCoordinates().add(createCoordinates(0.0, 10.0));
            exteriorRing.getCoordinates().add(createCoordinates(0.0, 0.0));

            polygon.setExteriorRing(exteriorRing);

            String json = saveGeoJson(polygon);

            assertNotNull(json);
            assertTrue(json.contains("\"type\""));
            assertTrue(json.contains("\"Polygon\""));
        }

        @Test
        @DisplayName("Feature with Point serialization")
        void serializeFeatureWithPoint() throws IOException {
            Point point = geoFactory.createPoint();
            point.setCoordinates(createCoordinates(13.405, 52.52));

            Feature feature = geoFactory.createFeature();
            feature.setId("berlin");
            feature.setGeometry(point);

            String json = saveGeoJson(feature);

            assertNotNull(json);
            assertTrue(json.contains("\"type\""));
            assertTrue(json.contains("\"Feature\""));
            assertTrue(json.contains("\"id\""));
            assertTrue(json.contains("\"berlin\""));
            assertTrue(json.contains("\"geometry\""));
        }

        @Test
        @DisplayName("FeatureCollection serialization")
        void serializeFeatureCollection() throws IOException {
            Point parisPoint = geoFactory.createPoint();
            parisPoint.setCoordinates(createCoordinates(2.3522, 48.8566));

            Feature paris = geoFactory.createFeature();
            paris.setId("paris");
            paris.setGeometry(parisPoint);

            Point londonPoint = geoFactory.createPoint();
            londonPoint.setCoordinates(createCoordinates(-0.1276, 51.5074));

            Feature london = geoFactory.createFeature();
            london.setId("london");
            london.setGeometry(londonPoint);

            FeatureCollection collection = geoFactory.createFeatureCollection();
            collection.getFeatures().add(paris);
            collection.getFeatures().add(london);

            String json = saveGeoJson(collection);

            assertNotNull(json);
            assertTrue(json.contains("\"type\""));
            assertTrue(json.contains("\"FeatureCollection\""));
            assertTrue(json.contains("\"features\""));
            assertTrue(json.contains("\"paris\""));
            assertTrue(json.contains("\"london\""));
        }
    }

    // ========================================================================
    // Round-Trip Tests
    // ========================================================================

    @Nested
    @DisplayName("Round-Trip")
    class RoundTripTests {

        @Test
        @DisplayName("Point round-trip")
        void pointRoundTrip() throws IOException {
            // Create
            Point original = geoFactory.createPoint();
            original.setCoordinates(createCoordinates(8.6821, 50.1109));

            // Serialize
            String json = saveGeoJson(original);

            // Deserialize
            Point loaded = loadGeoJson(json, Point.class);

            // Verify
            assertNotNull(loaded.getCoordinates());
            assertEquals(original.getCoordinates().getLongitude(), loaded.getCoordinates().getLongitude(), 0.0001);
            assertEquals(original.getCoordinates().getLatitude(), loaded.getCoordinates().getLatitude(), 0.0001);
        }

        @Test
        @DisplayName("Point with elevation round-trip")
        void pointWithElevationRoundTrip() throws IOException {
            // Create
            Point original = geoFactory.createPoint();
            original.setCoordinates(createCoordinates(13.405, 52.52, 34.5));

            // Serialize
            String json = saveGeoJson(original);

            // Deserialize
            Point loaded = loadGeoJson(json, Point.class);

            // Verify
            assertNotNull(loaded.getCoordinates());
            assertEquals(original.getCoordinates().getLongitude(), loaded.getCoordinates().getLongitude(), 0.0001);
            assertEquals(original.getCoordinates().getLatitude(), loaded.getCoordinates().getLatitude(), 0.0001);
            assertEquals(original.getCoordinates().getElevation(), loaded.getCoordinates().getElevation(), 0.0001);
        }

        @Test
        @DisplayName("LineString round-trip")
        void lineStringRoundTrip() throws IOException {
            // Create
            LineString original = geoFactory.createLineString();
            original.getCoordinates().add(createCoordinates(8.6821, 50.1109));
            original.getCoordinates().add(createCoordinates(8.6831, 50.1115));
            original.getCoordinates().add(createCoordinates(8.6845, 50.1120));

            // Serialize
            String json = saveGeoJson(original);

            // Deserialize
            LineString loaded = loadGeoJson(json, LineString.class);

            // Verify
            assertEquals(original.getCoordinates().size(), loaded.getCoordinates().size());
            for (int i = 0; i < original.getCoordinates().size(); i++) {
                assertEquals(original.getCoordinates().get(i).getLongitude(),
                        loaded.getCoordinates().get(i).getLongitude(), 0.0001);
                assertEquals(original.getCoordinates().get(i).getLatitude(),
                        loaded.getCoordinates().get(i).getLatitude(), 0.0001);
            }
        }

        @Test
        @DisplayName("Polygon round-trip")
        void polygonRoundTrip() throws IOException {
            // Create
            Polygon original = geoFactory.createPolygon();

            Ring exteriorRing = geoFactory.createRing();
            exteriorRing.getCoordinates().add(createCoordinates(0.0, 0.0));
            exteriorRing.getCoordinates().add(createCoordinates(10.0, 0.0));
            exteriorRing.getCoordinates().add(createCoordinates(10.0, 10.0));
            exteriorRing.getCoordinates().add(createCoordinates(0.0, 10.0));
            exteriorRing.getCoordinates().add(createCoordinates(0.0, 0.0));

            original.setExteriorRing(exteriorRing);

            // Serialize
            String json = saveGeoJson(original);

            // Deserialize
            Polygon loaded = loadGeoJson(json, Polygon.class);

            // Verify
            assertNotNull(loaded.getExteriorRing());
            assertEquals(original.getExteriorRing().getCoordinates().size(),
                    loaded.getExteriorRing().getCoordinates().size());

            for (int i = 0; i < original.getExteriorRing().getCoordinates().size(); i++) {
                assertEquals(original.getExteriorRing().getCoordinates().get(i).getLongitude(),
                        loaded.getExteriorRing().getCoordinates().get(i).getLongitude(), 0.0001);
                assertEquals(original.getExteriorRing().getCoordinates().get(i).getLatitude(),
                        loaded.getExteriorRing().getCoordinates().get(i).getLatitude(), 0.0001);
            }
        }

        @Test
        @DisplayName("Polygon with hole round-trip")
        void polygonWithHoleRoundTrip() throws IOException {
            // Create
            Polygon original = geoFactory.createPolygon();

            // Exterior ring
            Ring exteriorRing = geoFactory.createRing();
            exteriorRing.getCoordinates().add(createCoordinates(0.0, 0.0));
            exteriorRing.getCoordinates().add(createCoordinates(20.0, 0.0));
            exteriorRing.getCoordinates().add(createCoordinates(20.0, 20.0));
            exteriorRing.getCoordinates().add(createCoordinates(0.0, 20.0));
            exteriorRing.getCoordinates().add(createCoordinates(0.0, 0.0));
            original.setExteriorRing(exteriorRing);

            // Interior hole
            Hole hole = geoFactory.createHole();
            hole.getCoordinates().add(createCoordinates(5.0, 5.0));
            hole.getCoordinates().add(createCoordinates(15.0, 5.0));
            hole.getCoordinates().add(createCoordinates(15.0, 15.0));
            hole.getCoordinates().add(createCoordinates(5.0, 15.0));
            hole.getCoordinates().add(createCoordinates(5.0, 5.0));
            original.getInteriorHoles().add(hole);

            // Serialize
            String json = saveGeoJson(original);

            // Deserialize
            Polygon loaded = loadGeoJson(json, Polygon.class);

            // Verify exterior
            assertNotNull(loaded.getExteriorRing());
            assertEquals(original.getExteriorRing().getCoordinates().size(),
                    loaded.getExteriorRing().getCoordinates().size());

            // Verify holes
            assertEquals(original.getInteriorHoles().size(), loaded.getInteriorHoles().size());
            assertEquals(original.getInteriorHoles().get(0).getCoordinates().size(),
                    loaded.getInteriorHoles().get(0).getCoordinates().size());
        }

        @Test
        @DisplayName("Feature with Point round-trip")
        void featureWithPointRoundTrip() throws IOException {
            // Create
            Point point = geoFactory.createPoint();
            point.setCoordinates(createCoordinates(13.405, 52.52));

            Feature original = geoFactory.createFeature();
            original.setId("berlin");
            original.setGeometry(point);

            // Serialize
            String json = saveGeoJson(original);

            // Deserialize
            Feature loaded = loadGeoJson(json, Feature.class);

            // Verify
            assertEquals(original.getId(), loaded.getId());
            assertNotNull(loaded.getGeometry());
            assertInstanceOf(Point.class, loaded.getGeometry());

            Point loadedPoint = (Point) loaded.getGeometry();
            assertEquals(point.getCoordinates().getLongitude(), loadedPoint.getCoordinates().getLongitude(), 0.0001);
            assertEquals(point.getCoordinates().getLatitude(), loadedPoint.getCoordinates().getLatitude(), 0.0001);
        }

        @Test
        @DisplayName("FeatureCollection round-trip")
        void featureCollectionRoundTrip() throws IOException {
            // Create
            Point parisPoint = geoFactory.createPoint();
            parisPoint.setCoordinates(createCoordinates(2.3522, 48.8566));
            Feature paris = geoFactory.createFeature();
            paris.setId("paris");
            paris.setGeometry(parisPoint);

            Point londonPoint = geoFactory.createPoint();
            londonPoint.setCoordinates(createCoordinates(-0.1276, 51.5074));
            Feature london = geoFactory.createFeature();
            london.setId("london");
            london.setGeometry(londonPoint);

            FeatureCollection original = geoFactory.createFeatureCollection();
            original.getFeatures().add(paris);
            original.getFeatures().add(london);

            // Serialize
            String json = saveGeoJson(original);

            // Deserialize
            FeatureCollection loaded = loadGeoJson(json, FeatureCollection.class);

            // Verify
            assertEquals(original.getFeatures().size(), loaded.getFeatures().size());

            Feature loadedParis = loaded.getFeatures().get(0);
            assertEquals("paris", loadedParis.getId());
            assertInstanceOf(Point.class, loadedParis.getGeometry());

            Feature loadedLondon = loaded.getFeatures().get(1);
            assertEquals("london", loadedLondon.getId());
            assertInstanceOf(Point.class, loadedLondon.getGeometry());

            Point loadedLondonPoint = (Point) loadedLondon.getGeometry();
            assertEquals(-0.1276, loadedLondonPoint.getCoordinates().getLongitude(), 0.0001);
            assertEquals(51.5074, loadedLondonPoint.getCoordinates().getLatitude(), 0.0001);
        }
    }
}
