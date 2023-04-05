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
package org.gecko.codec.diagnostic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * 
 * @author mark
 * @since 22.03.2023
 */
public class AggregatedDiagnosticTest {
	
	@Test
	public void testAggregatedParameters() {
		AggregatedDiagnostic aggregated = Diagnostics.createAggregated(null, null, null, null, null);
		assertNotNull(aggregated);
		assertEquals(Severity.ERROR, aggregated.getSeverity());
		assertEquals(AggregatedDiagnostic.NO_MESSAGE, aggregated.getMessage());
		assertNull(aggregated.getError());
		assertFalse(aggregated.isStopper());
		assertTrue(aggregated.getDiagnostics().isEmpty());
		
		aggregated = Diagnostics.createAggregated(null, null, null, null, true);
		assertNotNull(aggregated);
		assertEquals(Severity.ERROR, aggregated.getSeverity());
		assertEquals(AggregatedDiagnostic.NO_MESSAGE, aggregated.getMessage());
		assertNull(aggregated.getError());
		assertTrue(aggregated.isStopper());
		assertTrue(aggregated.getDiagnostics().isEmpty());
		
		aggregated = Diagnostics.createAggregated(null, null, null, new IllegalStateException("Fooxception"), true);
		assertNotNull(aggregated);
		assertEquals(Severity.ERROR, aggregated.getSeverity());
		assertEquals("Fooxception", aggregated.getMessage());
		assertNotNull(aggregated.getError());
		assertTrue(aggregated.isStopper());
		assertTrue(aggregated.getDiagnostics().isEmpty());
		
		aggregated = Diagnostics.createAggregated(null, null, "Foo", new IllegalStateException("Fooxception"), true);
		assertNotNull(aggregated);
		assertEquals(Severity.ERROR, aggregated.getSeverity());
		assertEquals("Foo", aggregated.getMessage());
		assertNotNull(aggregated.getError());
		assertTrue(aggregated.isStopper());
		assertTrue(aggregated.getDiagnostics().isEmpty());
		
		aggregated = Diagnostics.createAggregated(null, Severity.WARNING, "Foo", new IllegalStateException("Fooxception"), true);
		assertNotNull(aggregated);
		assertEquals(Severity.WARNING, aggregated.getSeverity());
		assertEquals("Foo", aggregated.getMessage());
		assertNotNull(aggregated.getError());
		assertTrue(aggregated.isStopper());
		assertTrue(aggregated.getDiagnostics().isEmpty());
		
		List<Diagnostic> diagnostics = Collections.emptyList();
		aggregated = Diagnostics.createAggregated(diagnostics, Severity.WARNING, "Foo", null, true);
		assertNotNull(aggregated);
		assertEquals(Severity.WARNING, aggregated.getSeverity());
		assertEquals("Foo", aggregated.getMessage());
		assertNull(aggregated.getError());
		assertTrue(aggregated.isStopper());
		assertTrue(aggregated.getDiagnostics().isEmpty());
	}
	
	@Test
	public void testAggregatedDelegates() {
		AggregatedDiagnostic aggregated = Diagnostics.createAggregated(null, null);
		assertNotNull(aggregated);
		assertEquals(Severity.ERROR, aggregated.getSeverity());
		assertEquals(AggregatedDiagnostic.NO_MESSAGE, aggregated.getMessage());
		assertNull(aggregated.getError());
		assertFalse(aggregated.isStopper());
		assertTrue(aggregated.getDiagnostics().isEmpty());
		
		aggregated = Diagnostics.createAggregated(null, null, null);
		assertNotNull(aggregated);
		assertEquals(Severity.ERROR, aggregated.getSeverity());
		assertEquals(AggregatedDiagnostic.NO_MESSAGE, aggregated.getMessage());
		assertNull(aggregated.getError());
		assertFalse(aggregated.isStopper());
		assertTrue(aggregated.getDiagnostics().isEmpty());
		
		aggregated = Diagnostics.createAggregated(null, null, new IllegalStateException("Fooxception"));
		assertNotNull(aggregated);
		assertEquals(Severity.ERROR, aggregated.getSeverity());
		assertEquals("Fooxception", aggregated.getMessage());
		assertNotNull(aggregated.getError());
		assertFalse(aggregated.isStopper());
		assertTrue(aggregated.getDiagnostics().isEmpty());
		
		aggregated = Diagnostics.createAggregated(null, "Foo", new IllegalStateException("Fooxception"));
		assertNotNull(aggregated);
		assertEquals(Severity.ERROR, aggregated.getSeverity());
		assertEquals("Foo", aggregated.getMessage());
		assertNotNull(aggregated.getError());
		assertFalse(aggregated.isStopper());
		assertTrue(aggregated.getDiagnostics().isEmpty());
		
		Diagnostic d01 = Diagnostics.createDiagnostic(Severity.WARNING, "Bar-isto", new IllegalStateException("Fooxception"), false);
		Diagnostic d02 = Diagnostics.createDiagnostic(Severity.INFO, "Bar-isto", null, true);
		
		aggregated = Diagnostics.createAggregated(List.of(d01,  d02), "Bar", null);
		assertNotNull(aggregated);
		assertEquals(Severity.WARNING, aggregated.getSeverity());
		assertEquals("Bar", aggregated.getMessage());
		assertNull(aggregated.getError());
		assertTrue(aggregated.isStopper());
		assertEquals(2, aggregated.getDiagnostics().size());
		
		d01 = Diagnostics.createDiagnostic(Severity.WARNING, "Bar-isto", new IllegalStateException("Fooxception"), false);
		d02 = Diagnostics.createDiagnostic(Severity.INFO, "Bar-isto", null, true);
		
		aggregated = Diagnostics.createAggregated(List.of(d01,  d02), "Bar");
		assertNotNull(aggregated);
		assertEquals(Severity.WARNING, aggregated.getSeverity());
		assertEquals("Bar", aggregated.getMessage());
		assertNull(aggregated.getError());
		assertTrue(aggregated.isStopper());
		assertEquals(2, aggregated.getDiagnostics().size());
		
		d01 = Diagnostics.createDiagnostic(new IllegalStateException("Fooxception"));
		d02 = Diagnostics.createDiagnostic(Severity.INFO, "Bar-isto", null, true);
		
		aggregated = Diagnostics.createAggregated(List.of(d01,  d02), "Bar", new IllegalStateException("Barxception"));
		assertNotNull(aggregated);
		assertEquals(Severity.ERROR, aggregated.getSeverity());
		assertEquals("Bar", aggregated.getMessage());
		assertNotNull(aggregated.getError());
		assertEquals("Barxception", aggregated.getError().getMessage());
		assertTrue(aggregated.isStopper());
		assertEquals(2, aggregated.getDiagnostics().size());
		
		aggregated = Diagnostics.createAggregated(List.of(d01,  d02), null, new IllegalStateException("Barxception"));
		assertNotNull(aggregated);
		assertEquals(Severity.ERROR, aggregated.getSeverity());
		assertEquals("Barxception", aggregated.getMessage());
		assertNotNull(aggregated.getError());
		assertEquals("Barxception", aggregated.getError().getMessage());
		assertTrue(aggregated.isStopper());
		assertEquals(2, aggregated.getDiagnostics().size());
		
	}
	
	/**
	 * Test calculated values
	 */
	@Test
	public void testAggregatedCalculated() {
		Diagnostic d01 = Diagnostics.createDiagnostic(new IllegalStateException("Fooxception"));
		Diagnostic d02 = Diagnostics.createDiagnostic(Severity.INFO, "Bar-isto", null, true);
		AggregatedDiagnostic aggregated = Diagnostics.createAggregated(List.of(d01,  d02), Severity.WARNING, "Foo", null, false);
		assertNotNull(aggregated);
		assertEquals(Severity.WARNING, aggregated.getSeverity());
		assertEquals("Foo", aggregated.getMessage());
		assertNull(aggregated.getError());
		assertFalse(aggregated.isStopper());
		assertEquals(2, aggregated.getDiagnostics().size());
		
		aggregated = Diagnostics.createAggregated(List.of(d01,  d02), null, "Foo", null, false);
		assertNotNull(aggregated);
		assertEquals(Severity.ERROR, aggregated.getSeverity());
		assertEquals("Foo", aggregated.getMessage());
		assertNull(aggregated.getError());
		assertFalse(aggregated.isStopper());
		assertEquals(2, aggregated.getDiagnostics().size());
		
		aggregated = Diagnostics.createAggregated(List.of(d01,  d02), null, "Foo", null, null);
		assertNotNull(aggregated);
		assertEquals(Severity.ERROR, aggregated.getSeverity());
		assertEquals("Foo", aggregated.getMessage());
		assertNull(aggregated.getError());
		assertTrue(aggregated.isStopper());
		assertEquals(2, aggregated.getDiagnostics().size());
		
		aggregated = Diagnostics.createAggregated(List.of(d01,  d02), null, null, null, null);
		assertNotNull(aggregated);
		assertEquals(Severity.ERROR, aggregated.getSeverity());
		assertEquals(AggregatedDiagnostic.NO_MESSAGE, aggregated.getMessage());
		assertNull(aggregated.getError());
		assertTrue(aggregated.isStopper());
		assertEquals(2, aggregated.getDiagnostics().size());
		
		d01 = Diagnostics.createDiagnostic(Severity.WARNING, "Bar-isto", new IllegalStateException("Fooxception"), false);
		d02 = Diagnostics.createDiagnostic(Severity.INFO, "Bar-isto", null, true);
		
		aggregated = Diagnostics.createAggregated(List.of(d01,  d02), null, "Bar", null, null);
		assertNotNull(aggregated);
		assertEquals(Severity.WARNING, aggregated.getSeverity());
		assertEquals("Bar", aggregated.getMessage());
		assertNull(aggregated.getError());
		assertTrue(aggregated.isStopper());
		assertEquals(2, aggregated.getDiagnostics().size());
		
	}

}
