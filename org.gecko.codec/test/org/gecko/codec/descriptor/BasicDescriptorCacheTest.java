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
package org.gecko.codec.descriptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;

import org.gecko.codec.descriptors.BasicDescriptorCache;
import org.gecko.codec.descriptors.ClassifierDescriptor;
import org.gecko.codec.descriptors.CodecDescriptor;
import org.gecko.codec.descriptors.FeatureDescriptor;
import org.gecko.codec.descriptors.PackageDescriptor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * 
 * @author mark
 * @since 24.03.2023
 */
@ExtendWith(MockitoExtension.class)
public class BasicDescriptorCacheTest {

	private BasicDescriptorCache<Date, Long, Integer, Object> cache;
	
	@BeforeEach
	public void beforeEach() {
		cache = new BasicDescriptorCache<>();
	}
	
	@AfterEach
	public void afterEach() {
		cache.clear();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testCachePackagesSimple() {
		PackageDescriptor<Date, Object> d01 = createDescriptorMock(PackageDescriptor.class, "pd01");
		PackageDescriptor<Date, Object> d02 = createDescriptorMock(PackageDescriptor.class, "pd02");
		assertThrows(NullPointerException.class, ()->cache.addPackageDescriptor(d01));
		assertTrue(cache.getPackageDescriptor(null).isEmpty());
		
		Date date01 = new Date();
		Date date02 = new Date(System.currentTimeMillis() - 1000);
		Date date03 = new Date(System.currentTimeMillis() - 2000);
		when(d01.getSource()).thenReturn(date01);
		when(d02.getSource()).thenReturn(date02);
		cache.addPackageDescriptor(d01);
		assertTrue(cache.getPackageDescriptor(date01).isPresent());
		assertEquals(d01, cache.getPackageDescriptor(date01).get());
		assertTrue(cache.getPackageDescriptor(date03).isEmpty());
		
		cache.addPackageDescriptor(d02);
		assertTrue(cache.getPackageDescriptor(date01).isPresent());
		assertEquals(d01, cache.getPackageDescriptor(date01).get());
		assertTrue(cache.getPackageDescriptor(date02).isPresent());
		assertEquals(d02, cache.getPackageDescriptor(date02).get());
		assertTrue(cache.getPackageDescriptor(date03).isEmpty());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testCachePackagesByNameSimple() {
		PackageDescriptor<Date, Object> d01 = createDescriptorMock(PackageDescriptor.class, "pd01");
		PackageDescriptor<Date, Object> d02 = createDescriptorMock(PackageDescriptor.class, "pd02");
		assertThrows(NullPointerException.class, ()->cache.addPackageDescriptor(d01));
		assertTrue(cache.getPackageDescriptorByName(null).isEmpty());
		
		Date date01 = new Date();
		Date date02 = new Date(System.currentTimeMillis() - 1000);
		when(d01.getSource()).thenReturn(date01);
		when(d02.getSource()).thenReturn(date02);
		cache.addPackageDescriptor(d01);
		assertTrue(cache.getPackageDescriptorByName("pd01").isPresent());
		assertEquals(d01, cache.getPackageDescriptorByName("pd01").get());
		assertTrue(cache.getPackageDescriptorByName("foo").isEmpty());
		
		cache.addPackageDescriptor(d02);
		assertTrue(cache.getPackageDescriptorByName("pd01").isPresent());
		assertEquals(d01, cache.getPackageDescriptorByName("pd01").get());
		assertTrue(cache.getPackageDescriptorByName("pd02").isPresent());
		assertEquals(d02, cache.getPackageDescriptorByName("pd02").get());
		assertTrue(cache.getPackageDescriptorByName("foo").isEmpty());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testCacheClassifiersSimple() {
		ClassifierDescriptor<Long, Object> d01 = createDescriptorMock(ClassifierDescriptor.class, "cd01");
		ClassifierDescriptor<Long, Object> d02 = createDescriptorMock(ClassifierDescriptor.class, "cd02");
		assertThrows(NullPointerException.class, ()->cache.addClassifierDescriptor(d01));
		assertTrue(cache.getClassifierDescriptor(null).isEmpty());

		when(d01.getSource()).thenReturn(Long.valueOf(42));
		when(d02.getSource()).thenReturn(Long.valueOf(24));
		cache.addClassifierDescriptor(d01);
		assertTrue(cache.getClassifierDescriptor(Long.valueOf(42)).isPresent());
		assertEquals(d01, cache.getClassifierDescriptor(Long.valueOf(42)).get());
		assertTrue(cache.getClassifierDescriptor(Long.valueOf(43)).isEmpty());
		
		cache.addClassifierDescriptor(d02);
		assertTrue(cache.getClassifierDescriptor(Long.valueOf(42)).isPresent());
		assertEquals(d01, cache.getClassifierDescriptor(Long.valueOf(42)).get());
		assertTrue(cache.getClassifierDescriptor(Long.valueOf(24)).isPresent());
		assertEquals(d02, cache.getClassifierDescriptor(Long.valueOf(24)).get());
		assertTrue(cache.getClassifierDescriptor(Long.valueOf(43)).isEmpty());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testCacheClassifiersByNameSimple() {
		ClassifierDescriptor<Long, Object> d01 = createDescriptorMock(ClassifierDescriptor.class, "cd01");
		ClassifierDescriptor<Long, Object> d02 = createDescriptorMock(ClassifierDescriptor.class, "cd02");
		assertThrows(NullPointerException.class, ()->cache.addClassifierDescriptor(d01));
		assertTrue(cache.getClassifierDescriptorByName(null).isEmpty());
		
		when(d01.getSource()).thenReturn(Long.valueOf(42));
		when(d02.getSource()).thenReturn(Long.valueOf(24));
		cache.addClassifierDescriptor(d01);
		assertTrue(cache.getClassifierDescriptorByName("cd01").isPresent());
		assertEquals(d01, cache.getClassifierDescriptorByName("cd01").get());
		assertTrue(cache.getClassifierDescriptorByName("foo").isEmpty());
		
		cache.addClassifierDescriptor(d02);
		assertTrue(cache.getClassifierDescriptorByName("cd01").isPresent());
		assertEquals(d01, cache.getClassifierDescriptorByName("cd01").get());
		assertTrue(cache.getClassifierDescriptorByName("cd02").isPresent());
		assertEquals(d02, cache.getClassifierDescriptorByName("cd02").get());
		assertTrue(cache.getClassifierDescriptorByName("foo").isEmpty());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testCacheFeatures() {
		FeatureDescriptor<Integer, Object> d01 = createDescriptorMock(FeatureDescriptor.class, "fd01");
		FeatureDescriptor<Integer, Object> d02 = createDescriptorMock(FeatureDescriptor.class, "fd02");
		assertThrows(NullPointerException.class, ()->cache.addFeatureDescriptor(d01));
		assertTrue(cache.getFeatureDescriptor(null).isEmpty());

		when(d01.getSource()).thenReturn(Integer.valueOf(42));
		when(d02.getSource()).thenReturn(Integer.valueOf(24));
		cache.addFeatureDescriptor(d01);
		assertTrue(cache.getFeatureDescriptor(Integer.valueOf(42)).isPresent());
		assertEquals(d01, cache.getFeatureDescriptor(Integer.valueOf(42)).get());
		assertTrue(cache.getFeatureDescriptor(Integer.valueOf(43)).isEmpty());
		
		cache.addFeatureDescriptor(d02);
		assertTrue(cache.getFeatureDescriptor(Integer.valueOf(42)).isPresent());
		assertEquals(d01, cache.getFeatureDescriptor(Integer.valueOf(42)).get());
		assertTrue(cache.getFeatureDescriptor(Integer.valueOf(24)).isPresent());
		assertEquals(d02, cache.getFeatureDescriptor(Integer.valueOf(24)).get());
		assertTrue(cache.getFeatureDescriptor(Integer.valueOf(43)).isEmpty());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testCacheFeaturesByName() {
		FeatureDescriptor<Integer, Object> d01 = createDescriptorMock(FeatureDescriptor.class, "fd01");
		FeatureDescriptor<Integer, Object> d02 = createDescriptorMock(FeatureDescriptor.class, "fd02");
		assertThrows(NullPointerException.class, ()->cache.addFeatureDescriptor(d01));
		assertTrue(cache.getFeatureDescriptorByName(null).isEmpty());
		
		when(d01.getSource()).thenReturn(Integer.valueOf(42));
		when(d02.getSource()).thenReturn(Integer.valueOf(24));
		cache.addFeatureDescriptor(d01);
		assertTrue(cache.getFeatureDescriptorByName("fd01").isPresent());
		assertEquals(d01, cache.getFeatureDescriptorByName("fd01").get());
		assertTrue(cache.getFeatureDescriptorByName("foo").isEmpty());
		
		cache.addFeatureDescriptor(d02);
		assertTrue(cache.getFeatureDescriptorByName("fd01").isPresent());
		assertEquals(d01, cache.getFeatureDescriptorByName("fd01").get());
		assertTrue(cache.getFeatureDescriptorByName("fd02").isPresent());
		assertEquals(d02, cache.getFeatureDescriptorByName("fd02").get());
		assertTrue(cache.getFeatureDescriptorByName("foo").isEmpty());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testPackageRecursive() {
		FeatureDescriptor<Integer, Object> fd01 = createDescriptorMock(FeatureDescriptor.class, "fd01");
		FeatureDescriptor<Integer, Object> fd02 = createDescriptorMock(FeatureDescriptor.class, "fd02");
		FeatureDescriptor<Integer, Object> fd03 = createDescriptorMock(FeatureDescriptor.class, "fd03");
		FeatureDescriptor<Integer, Object> fd04 = createDescriptorMock(FeatureDescriptor.class, "fd04");
		lenient().when(fd01.getSource()).thenReturn(Integer.valueOf(42));
		lenient().when(fd02.getSource()).thenReturn(Integer.valueOf(24));
		lenient().when(fd03.getSource()).thenReturn(Integer.valueOf(222));
		lenient().when(fd04.getSource()).thenReturn(Integer.valueOf(555));
		ClassifierDescriptor<Long, Object> cd01 = createDescriptorMock(ClassifierDescriptor.class, "cd01");
		ClassifierDescriptor<Long, Object> cd02 = createDescriptorMock(ClassifierDescriptor.class, "cd02");
		ClassifierDescriptor<Long, Object> cd03 = createDescriptorMock(ClassifierDescriptor.class, "cd03");
		lenient().when(cd01.getSource()).thenReturn(Long.valueOf(142));
		lenient().when(cd02.getSource()).thenReturn(Long.valueOf(124));
		lenient().when(cd03.getSource()).thenReturn(Long.valueOf(123));
		lenient().when(cd01.getFeatures()).thenReturn(List.of(fd01, fd02));
		lenient().when(cd02.getFeatures()).thenReturn(List.of(fd03));
		lenient().when(cd03.getFeatures()).thenReturn(List.of(fd04));
		
		PackageDescriptor<Date, Object> pd01 = createDescriptorMock(PackageDescriptor.class, "pd01");
		PackageDescriptor<Date, Object> pd02 = createDescriptorMock(PackageDescriptor.class, "pd02");
		Date date01 = new Date();
		Date date02 = new Date(System.currentTimeMillis() - 1000);
		lenient().when(pd01.getSource()).thenReturn(date01);
		lenient().when(pd02.getSource()).thenReturn(date02);
		lenient().when(pd01.getClassifiers()).thenReturn(List.of(cd01, cd02));
		lenient().when(pd02.getClassifiers()).thenReturn(List.of(cd03));
		
		/*
		 * Package: pd01
		 * 		Classifies: cd01
		 * 			Feature: fd01
		 * 			Feature: fd02
		 * 		Classifier: cd02
		 * 			Feature: fd03
		 * 
		 * Package: pd02
		 * 		Classifier: cd03
		 * 			Feature: fd04
		 */

		cache.addPackageDescriptor(pd02, true);
		assertTrue(cache.getPackageDescriptor(date01).isEmpty());
		assertTrue(cache.getPackageDescriptor(date02).isPresent());
		
		assertTrue(cache.getClassifierDescriptor(Long.valueOf(123)).isPresent());
		assertTrue(cache.getClassifierDescriptor(Long.valueOf(142)).isEmpty());
		assertTrue(cache.getClassifierDescriptor(Long.valueOf(124)).isEmpty());
		
		assertTrue(cache.getFeatureDescriptor(Integer.valueOf(555)).isPresent());
		assertTrue(cache.getFeatureDescriptor(Integer.valueOf(42)).isEmpty());
		assertTrue(cache.getFeatureDescriptor(Integer.valueOf(24)).isEmpty());
		assertTrue(cache.getFeatureDescriptor(Integer.valueOf(222)).isEmpty());
		
		cache.clear();
		
		assertTrue(cache.getPackageDescriptor(date01).isEmpty());
		assertTrue(cache.getPackageDescriptor(date02).isEmpty());
		
		assertTrue(cache.getClassifierDescriptor(Long.valueOf(123)).isEmpty());
		assertTrue(cache.getClassifierDescriptor(Long.valueOf(142)).isEmpty());
		assertTrue(cache.getClassifierDescriptor(Long.valueOf(124)).isEmpty());
		
		assertTrue(cache.getFeatureDescriptor(Integer.valueOf(555)).isEmpty());
		assertTrue(cache.getFeatureDescriptor(Integer.valueOf(42)).isEmpty());
		assertTrue(cache.getFeatureDescriptor(Integer.valueOf(24)).isEmpty());
		assertTrue(cache.getFeatureDescriptor(Integer.valueOf(222)).isEmpty());
		
		cache.addPackageDescriptor(pd01, true);

		assertTrue(cache.getPackageDescriptor(date01).isPresent());
		assertTrue(cache.getPackageDescriptor(date02).isEmpty());
		
		assertTrue(cache.getClassifierDescriptor(Long.valueOf(123)).isEmpty());
		assertTrue(cache.getClassifierDescriptor(Long.valueOf(142)).isPresent());
		assertTrue(cache.getClassifierDescriptor(Long.valueOf(124)).isPresent());
		
		assertTrue(cache.getFeatureDescriptor(Integer.valueOf(555)).isEmpty());
		assertTrue(cache.getFeatureDescriptor(Integer.valueOf(42)).isPresent());
		assertTrue(cache.getFeatureDescriptor(Integer.valueOf(24)).isPresent());
		assertTrue(cache.getFeatureDescriptor(Integer.valueOf(222)).isPresent());
		
		cache.addPackageDescriptor(pd02, true);
		
		assertTrue(cache.getPackageDescriptor(date01).isPresent());
		assertTrue(cache.getPackageDescriptor(date02).isPresent());
		
		assertTrue(cache.getClassifierDescriptor(Long.valueOf(123)).isPresent());
		assertTrue(cache.getClassifierDescriptor(Long.valueOf(142)).isPresent());
		assertTrue(cache.getClassifierDescriptor(Long.valueOf(124)).isPresent());
		
		assertTrue(cache.getFeatureDescriptor(Integer.valueOf(555)).isPresent());
		assertTrue(cache.getFeatureDescriptor(Integer.valueOf(42)).isPresent());
		assertTrue(cache.getFeatureDescriptor(Integer.valueOf(24)).isPresent());
		assertTrue(cache.getFeatureDescriptor(Integer.valueOf(222)).isPresent());
		
		cache.clear();
		
		cache.addPackageDescriptor(pd01, false);
		
		assertTrue(cache.getPackageDescriptor(date01).isPresent());
		assertTrue(cache.getPackageDescriptor(date02).isEmpty());
		
		assertTrue(cache.getClassifierDescriptor(Long.valueOf(123)).isEmpty());
		assertTrue(cache.getClassifierDescriptor(Long.valueOf(142)).isEmpty());
		assertTrue(cache.getClassifierDescriptor(Long.valueOf(124)).isEmpty());
		
		assertTrue(cache.getFeatureDescriptor(Integer.valueOf(555)).isEmpty());
		assertTrue(cache.getFeatureDescriptor(Integer.valueOf(42)).isEmpty());
		assertTrue(cache.getFeatureDescriptor(Integer.valueOf(24)).isEmpty());
		assertTrue(cache.getFeatureDescriptor(Integer.valueOf(222)).isEmpty());
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testClassifierPackageRecursive() {
		FeatureDescriptor<Integer, Object> fd01 = createDescriptorMock(FeatureDescriptor.class, "fd01");
		FeatureDescriptor<Integer, Object> fd02 = createDescriptorMock(FeatureDescriptor.class, "fd02");
		FeatureDescriptor<Integer, Object> fd03 = createDescriptorMock(FeatureDescriptor.class, "fd03");
		FeatureDescriptor<Integer, Object> fd04 = createDescriptorMock(FeatureDescriptor.class, "fd04");
		lenient().when(fd01.getSource()).thenReturn(Integer.valueOf(42));
		lenient().when(fd02.getSource()).thenReturn(Integer.valueOf(24));
		lenient().when(fd03.getSource()).thenReturn(Integer.valueOf(222));
		lenient().when(fd04.getSource()).thenReturn(Integer.valueOf(555));
		ClassifierDescriptor<Long, Object> cd01 = createDescriptorMock(ClassifierDescriptor.class, "cd01");
		ClassifierDescriptor<Long, Object> cd02 = createDescriptorMock(ClassifierDescriptor.class, "cd02");
		ClassifierDescriptor<Long, Object> cd03 = createDescriptorMock(ClassifierDescriptor.class, "cd03");
		lenient().when(cd01.getSource()).thenReturn(Long.valueOf(142));
		lenient().when(cd02.getSource()).thenReturn(Long.valueOf(124));
		lenient().when(cd03.getSource()).thenReturn(Long.valueOf(123));
		
		lenient().when(cd01.getFeatures()).thenReturn(List.of(fd01, fd02));
		lenient().when(cd02.getFeatures()).thenReturn(List.of(fd03));
		lenient().when(cd03.getFeatures()).thenReturn(List.of(fd04));
		
		/*
		 * 		Classifies: cd01
		 * 			Feature: fd01
		 * 			Feature: fd02
		 * 		Classifier: cd02
		 * 			Feature: fd03
		 * 		Classifier: cd03
		 * 			Feature: fd04
		 */
		
		cache.addClassifierDescriptor(cd03, true);
		
		assertTrue(cache.getClassifierDescriptor(Long.valueOf(123)).isPresent());
		assertTrue(cache.getClassifierDescriptorByName("cd03").isPresent());
		assertTrue(cache.getClassifierDescriptor(Long.valueOf(142)).isEmpty());
		assertTrue(cache.getClassifierDescriptorByName("cd01").isEmpty());
		assertTrue(cache.getClassifierDescriptor(Long.valueOf(124)).isEmpty());
		assertTrue(cache.getClassifierDescriptorByName("cd01").isEmpty());
		
		assertTrue(cache.getFeatureDescriptor(Integer.valueOf(555)).isPresent());
		assertTrue(cache.getFeatureDescriptorByName("fd04").isPresent());
		assertTrue(cache.getFeatureDescriptor(Integer.valueOf(42)).isEmpty());
		assertTrue(cache.getFeatureDescriptorByName("fd01").isEmpty());
		assertTrue(cache.getFeatureDescriptor(Integer.valueOf(24)).isEmpty());
		assertTrue(cache.getFeatureDescriptorByName("fd02").isEmpty());
		assertTrue(cache.getFeatureDescriptor(Integer.valueOf(222)).isEmpty());
		assertTrue(cache.getFeatureDescriptorByName("fd03").isEmpty());
		
		cache.clear();
		
		assertTrue(cache.getClassifierDescriptor(Long.valueOf(123)).isEmpty());
		assertTrue(cache.getClassifierDescriptor(Long.valueOf(142)).isEmpty());
		assertTrue(cache.getClassifierDescriptor(Long.valueOf(124)).isEmpty());
		
		assertTrue(cache.getFeatureDescriptor(Integer.valueOf(555)).isEmpty());
		assertTrue(cache.getFeatureDescriptor(Integer.valueOf(42)).isEmpty());
		assertTrue(cache.getFeatureDescriptor(Integer.valueOf(24)).isEmpty());
		assertTrue(cache.getFeatureDescriptor(Integer.valueOf(222)).isEmpty());
		
		cache.addClassifierDescriptor(cd02, true);
		
		assertTrue(cache.getClassifierDescriptor(Long.valueOf(123)).isEmpty());
		assertTrue(cache.getClassifierDescriptor(Long.valueOf(142)).isEmpty());
		assertTrue(cache.getClassifierDescriptor(Long.valueOf(124)).isPresent());
		
		assertTrue(cache.getFeatureDescriptor(Integer.valueOf(555)).isEmpty());
		assertTrue(cache.getFeatureDescriptor(Integer.valueOf(42)).isEmpty());
		assertTrue(cache.getFeatureDescriptor(Integer.valueOf(24)).isEmpty());
		assertTrue(cache.getFeatureDescriptor(Integer.valueOf(222)).isPresent());
		
		cache.addClassifierDescriptor(cd01, true);
		
		assertTrue(cache.getClassifierDescriptor(Long.valueOf(123)).isEmpty());
		assertTrue(cache.getClassifierDescriptor(Long.valueOf(142)).isPresent());
		assertTrue(cache.getClassifierDescriptor(Long.valueOf(124)).isPresent());
		
		assertTrue(cache.getFeatureDescriptor(Integer.valueOf(555)).isEmpty());
		assertTrue(cache.getFeatureDescriptor(Integer.valueOf(42)).isPresent());
		assertTrue(cache.getFeatureDescriptor(Integer.valueOf(24)).isPresent());
		assertTrue(cache.getFeatureDescriptor(Integer.valueOf(222)).isPresent());
		
		cache.addClassifierDescriptor(cd03, false);
		
		assertTrue(cache.getClassifierDescriptor(Long.valueOf(123)).isPresent());
		assertTrue(cache.getClassifierDescriptor(Long.valueOf(142)).isPresent());
		assertTrue(cache.getClassifierDescriptor(Long.valueOf(124)).isPresent());
		
		assertTrue(cache.getFeatureDescriptor(Integer.valueOf(555)).isEmpty());
		assertTrue(cache.getFeatureDescriptorByName("fd04").isEmpty());
		assertTrue(cache.getFeatureDescriptor(Integer.valueOf(42)).isPresent());
		assertTrue(cache.getFeatureDescriptorByName("fd01").isPresent());
		assertTrue(cache.getFeatureDescriptor(Integer.valueOf(24)).isPresent());
		assertTrue(cache.getFeatureDescriptorByName("fd02").isPresent());
		assertTrue(cache.getFeatureDescriptor(Integer.valueOf(222)).isPresent());
		assertTrue(cache.getFeatureDescriptorByName("fd03").isPresent());
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testByNameCache() {
		PackageDescriptor<Date, Object> pd01 = createDescriptorMock(PackageDescriptor.class, "pd01");
		PackageDescriptor<Date, Object> pd02 = createDescriptorMock(PackageDescriptor.class, "pd02");
		Date date01 = new Date();
		Date date02 = new Date(System.currentTimeMillis() - 1000);
		when(pd01.getSource()).thenReturn(date01);
		when(pd02.getSource()).thenReturn(date02);
		cache.addPackageDescriptor(pd01);
		cache.addPackageDescriptor(pd02);
		ClassifierDescriptor<Long, Object> cd01 = createDescriptorMock(ClassifierDescriptor.class, "cd01");
		ClassifierDescriptor<Long, Object> cd02 = createDescriptorMock(ClassifierDescriptor.class, "cd02");
		when(cd01.getSource()).thenReturn(Long.valueOf(142));
		when(cd02.getSource()).thenReturn(Long.valueOf(124));
		cache.addClassifierDescriptor(cd01);
		cache.addClassifierDescriptor(cd02);
		FeatureDescriptor<Integer, Object> fd01 = createDescriptorMock(FeatureDescriptor.class, "fd01");
		FeatureDescriptor<Integer, Object> fd02 = createDescriptorMock(FeatureDescriptor.class, "fd02");
		when(fd01.getSource()).thenReturn(Integer.valueOf(42));
		when(fd02.getSource()).thenReturn(Integer.valueOf(24));
		cache.addFeatureDescriptor(fd01);
		cache.addFeatureDescriptor(fd02);
		
		assertTrue(cache.getPackageDescriptor(date01).isPresent());
		assertTrue(cache.getPackageDescriptorByName("pd01").isPresent());
		assertEquals(cache.getPackageDescriptor(date01).get(), cache.getPackageDescriptorByName("pd01").get());
		
		assertTrue(cache.getPackageDescriptor(date02).isPresent());
		assertTrue(cache.getPackageDescriptorByName("pd02").isPresent());
		assertEquals(cache.getPackageDescriptor(date02).get(), cache.getPackageDescriptorByName("pd02").get());
		
		assertTrue(cache.getClassifierDescriptor(Long.valueOf(142)).isPresent());
		assertTrue(cache.getClassifierDescriptorByName("cd01").isPresent());
		assertEquals(cache.getClassifierDescriptor(Long.valueOf(142)).get(), cache.getClassifierDescriptorByName("cd01").get());
		
		assertTrue(cache.getClassifierDescriptor(Long.valueOf(124)).isPresent());
		assertTrue(cache.getClassifierDescriptorByName("cd02").isPresent());
		assertEquals(cache.getClassifierDescriptor(Long.valueOf(124)).get(), cache.getClassifierDescriptorByName("cd02").get());
		
		assertTrue(cache.getFeatureDescriptor(Integer.valueOf(42)).isPresent());
		assertTrue(cache.getFeatureDescriptorByName("fd01").isPresent());
		assertEquals(cache.getFeatureDescriptor(Integer.valueOf(42)).get(), cache.getFeatureDescriptorByName("fd01").get());
		
		assertTrue(cache.getFeatureDescriptor(Integer.valueOf(24)).isPresent());
		assertTrue(cache.getFeatureDescriptorByName("fd02").isPresent());
		assertEquals(cache.getFeatureDescriptor(Integer.valueOf(24)).get(), cache.getFeatureDescriptorByName("fd02").get());
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testClearCache() {
		PackageDescriptor<Date, Object> pd01 = createDescriptorMock(PackageDescriptor.class, "pd01");
		PackageDescriptor<Date, Object> pd02 = createDescriptorMock(PackageDescriptor.class, "pd02");
		Date date01 = new Date();
		Date date02 = new Date(System.currentTimeMillis() - 1000);
		when(pd01.getSource()).thenReturn(date01);
		when(pd02.getSource()).thenReturn(date02);
		cache.addPackageDescriptor(pd01);
		cache.addPackageDescriptor(pd02);
		ClassifierDescriptor<Long, Object> cd01 = createDescriptorMock(ClassifierDescriptor.class, "cd01");
		ClassifierDescriptor<Long, Object> cd02 = createDescriptorMock(ClassifierDescriptor.class, "cd02");
		when(cd01.getSource()).thenReturn(Long.valueOf(142));
		when(cd02.getSource()).thenReturn(Long.valueOf(124));
		cache.addClassifierDescriptor(cd01);
		cache.addClassifierDescriptor(cd02);
		FeatureDescriptor<Integer, Object> fd01 = createDescriptorMock(FeatureDescriptor.class, "fd01");
		FeatureDescriptor<Integer, Object> fd02 = createDescriptorMock(FeatureDescriptor.class, "fd02");
		when(fd01.getSource()).thenReturn(Integer.valueOf(42));
		when(fd02.getSource()).thenReturn(Integer.valueOf(24));
		cache.addFeatureDescriptor(fd01);
		cache.addFeatureDescriptor(fd02);
		
		assertTrue(cache.getPackageDescriptor(date01).isPresent());
		assertTrue(cache.getPackageDescriptorByName("pd01").isPresent());
		assertTrue(cache.getPackageDescriptor(date02).isPresent());
		assertTrue(cache.getPackageDescriptorByName("pd02").isPresent());
		assertTrue(cache.getClassifierDescriptor(Long.valueOf(142)).isPresent());
		assertTrue(cache.getClassifierDescriptorByName("cd01").isPresent());
		assertTrue(cache.getClassifierDescriptor(Long.valueOf(124)).isPresent());
		assertTrue(cache.getClassifierDescriptorByName("cd02").isPresent());
		assertTrue(cache.getFeatureDescriptor(Integer.valueOf(42)).isPresent());
		assertTrue(cache.getFeatureDescriptorByName("fd01").isPresent());
		assertTrue(cache.getFeatureDescriptor(Integer.valueOf(24)).isPresent());
		assertTrue(cache.getFeatureDescriptorByName("fd02").isPresent());
		
		cache.clear();
		
		assertTrue(cache.getPackageDescriptor(date01).isEmpty());
		assertTrue(cache.getPackageDescriptorByName("pd01").isEmpty());
		assertTrue(cache.getPackageDescriptor(date02).isEmpty());
		assertTrue(cache.getPackageDescriptorByName("pd02").isEmpty());
		assertTrue(cache.getClassifierDescriptor(Long.valueOf(142)).isEmpty());
		assertTrue(cache.getClassifierDescriptorByName("cd01").isEmpty());
		assertTrue(cache.getClassifierDescriptor(Long.valueOf(124)).isEmpty());
		assertTrue(cache.getClassifierDescriptorByName("cd02").isEmpty());
		assertTrue(cache.getFeatureDescriptor(Integer.valueOf(42)).isEmpty());
		assertTrue(cache.getFeatureDescriptorByName("fd01").isEmpty());
		assertTrue(cache.getFeatureDescriptor(Integer.valueOf(24)).isEmpty());
		assertTrue(cache.getFeatureDescriptorByName("fd02").isEmpty());
		
	}
	
	private <P, C, T, F, D extends CodecDescriptor<?>> D createDescriptorMock(Class<D> mockClass, String name) {
		D mock = Mockito.mock(mockClass);
		when(mock.getName()).thenReturn(name);
		return mock;
	}

}
