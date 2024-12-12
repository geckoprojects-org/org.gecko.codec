/**
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
 */
package org.eclipse.fennec.models.geojson.util;

import java.util.List;

import org.eclipse.fennec.models.geojson.Coordinates;
import org.eclipse.fennec.models.geojson.GeoJsonFactory;
import org.eclipse.fennec.models.geojson.GeoJsonPackage;
import org.eclipse.fennec.models.geojson.Hole;
import org.eclipse.fennec.models.geojson.MultiLineString;
import org.eclipse.fennec.models.geojson.MultiPolygon;
import org.eclipse.fennec.models.geojson.Ring;
import org.eclipse.fennec.models.geojson.SimpleLineString;
import org.eclipse.fennec.models.geojson.SimplePolygon;
import org.eclipse.fennec.models.geojson.impl.CoordinatesImpl;
import org.eclipse.fennec.models.geojson.impl.SimpleLineStringImpl;
import org.eclipse.fennec.models.geojson.impl.SimplePolygonImpl;

/**
 * 
 * @author Juergen Albert
 * @since 29 Nov 2024
 */
public class GeoJsonHelper {

	public static double[][][][] getMultiPolygonData(MultiPolygon multiPoygon) {
		double[][][][] result = new double[multiPoygon.getPolygons().size()][][][];
		for (int i = 0; i < multiPoygon.getPolygons().size(); i++) {
			SimplePolygonImpl polygon = (SimplePolygonImpl) multiPoygon.getPolygons().get(i);
			result[i] =  polygon.getData();
		}
		return result;
	}
	
	public static void setMultiPolygonData(MultiPolygon multiPoygon, double[][][][] data) {
		for (int i = 0; i < data.length; i++) {
			double[][][] d = data[i];
			SimplePolygonImpl polygon = (SimplePolygonImpl) GeoJsonFactory.eINSTANCE.createSimplePolygon();
			polygon.setData(d);
			multiPoygon.getPolygons().add(polygon);
		}
	}
	
	public static double[][][] getSimplePolygonData(SimplePolygon polygon) {
		double[][][] result = new double[1 + polygon.getInteriorHoles().size()][][];
		result[0] = GeoJsonHelper.convertRing(polygon.getExteriorRing());
		for (int i = 0; i < polygon.getInteriorHoles().size(); i++) {
			Hole hole = polygon.getInteriorHoles().get(i);
			result[i + 1] = GeoJsonHelper.convertRing(hole);
		}
		return result;
	}

	public static void setSimplePolygonData(SimplePolygon polygon, double[][][] data) {
		for (int i = 0; i < data.length; i++) {
			double[][] d = data[i];
			if (i == 0) {
				polygon.setExteriorRing(GeoJsonHelper.toRing(d));
			} else {
				polygon.getInteriorHoles().add(GeoJsonHelper.toHole(d));
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static double[][][] getMultiLineStringData(MultiLineString multiLineString) {
		double[][][] result = new double[multiLineString.getLinesStrings().size()][][];
		for (int i = 0; i < multiLineString.getLinesStrings().size(); i++) {
			SimpleLineString line = multiLineString.getLinesStrings().get(i);
			result[i] = ((List<SimplePolygon>) line.eGet(GeoJsonPackage.Literals.SIMPLE_LINE_STRING__DATA)).toArray(new double[line.getCoordinates().size()][]);
		}
		return result;
	}

	
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static void setMultiLineStringData(MultiLineString multiLineString, double[][][] data) {
		for (int i = 0; i < data.length; i++) {
			double[][] d = data[i];
			SimpleLineStringImpl line = (SimpleLineStringImpl) GeoJsonFactory.eINSTANCE.createSimpleLineString();
			for (double[] e : d) {
				line.getData().add(e);
			}
			multiLineString.getLinesStrings().add(line);
		}
	}

	public static double[] convertCoordinates(Coordinates coordinates) {
		return convertCoordinates(coordinates, false);
	}

	public static double[] convertCoordinates(Coordinates coordinates, boolean ignoreElevation) {
		if (coordinates == null) {
			return new double[0];
		}
		double[] array = Double.isNaN(coordinates.getElevation()) || ignoreElevation
				? new double[] { coordinates.getLongitude(), coordinates.getLatitude() }
				: new double[] { coordinates.getLongitude(), coordinates.getLatitude(), coordinates.getElevation() };
		return array;
	}

	public static Coordinates toCoordinates(double[] data) {
		if (data == null) {
			return null;
		}
		Coordinates c = new CoordinatesImpl();
		c.setLongitude(data[0]);
		c.setLatitude(data[1]);
		if (data.length >= 3) {
			c.setElevation(data[2]);
		}
		return c;
	}

	public static double[][] convertRing(Ring ring) {
		if (ring == null || ring.getCoordinates().isEmpty()) {
			return new double[0][0];
		}
		boolean ringClosed = checkRingClosed(ring);
		int size = ringClosed ? ring.getCoordinates().size() : ring.getCoordinates().size() + 1;
		double[][] result = new double[size][2];

		for (int i = 0; i < ring.getCoordinates().size(); i++) {
			Coordinates coordinates = ring.getCoordinates().get(i);
			result[i] = convertCoordinates(coordinates, true);
		}
		if (!ringClosed) {
			Coordinates coordinates = ring.getCoordinates().get(0);
			result[ring.getCoordinates().size()] = convertCoordinates(coordinates, true);
		}

		return result;
	}

	public static boolean checkRingClosed(Ring ring) {
		Coordinates start = ring.getCoordinates().get(0);
		Coordinates end = ring.getCoordinates().get(ring.getCoordinates().size() - 1);
		return start.getLatitude() == end.getLatitude() && start.getLongitude() == end.getLongitude();
	}

	public static Ring toRing(double[][] data) {
		if (data == null) {
			return null;
		}
		Ring ring = GeoJsonFactory.eINSTANCE.createRing();
		for (int i = 0; i < data.length; i++) {
			ring.getCoordinates().add(toCoordinates(data[i]));
		}
		return ring;
	}

	public static Hole toHole(double[][] data) {
		if (data == null) {
			return null;
		}
		Hole hole = GeoJsonFactory.eINSTANCE.createHole();
		for (int i = 0; i < data.length; i++) {
			hole.getCoordinates().add(toCoordinates(data[i]));
		}
		return hole;
	}
}
