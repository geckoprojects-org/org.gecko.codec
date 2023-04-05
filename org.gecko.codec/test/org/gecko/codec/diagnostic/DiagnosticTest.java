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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * 
 * @author mark
 * @since 22.03.2023
 */
public class DiagnosticTest {
	
	@Test
	public void testInfoDiagnostic() {
		assertThrows(NullPointerException.class, ()->Diagnostics.createInfoDiagnostic(null));
		Diagnostic d = Diagnostics.createInfoDiagnostic("Foo");
		assertNotNull(d);
		assertEquals(Severity.INFO, d.getSeverity());
		assertEquals("Foo", d.getMessage());
		assertNull(d.getError());
		assertFalse(d.isStopper());
		
		assertThrows(NullPointerException.class, ()->Diagnostics.createInfoDiagnostic(null, null));
		assertThrows(NullPointerException.class, ()->Diagnostics.createInfoDiagnostic(null, new IllegalStateException()));
		
		d = Diagnostics.createInfoDiagnostic("Foo", null);
		assertNotNull(d);
		assertEquals(Severity.INFO, d.getSeverity());
		assertEquals("Foo", d.getMessage());
		assertNull(d.getError());
		assertFalse(d.isStopper());
		
		d = Diagnostics.createInfoDiagnostic("Foo", new IllegalStateException("Fooxception"));
		assertNotNull(d);
		assertEquals(Severity.INFO, d.getSeverity());
		assertEquals("Foo", d.getMessage());
		assertNotNull(d.getError());
		assertEquals("Fooxception", d.getError().getMessage());
		assertFalse(d.isStopper());
	}
	
	@Test
	public void testWarnDiagnostic() {
		assertThrows(NullPointerException.class, ()->Diagnostics.createWarningDiagnostic(null));
		Diagnostic d = Diagnostics.createWarningDiagnostic("Foo");
		assertNotNull(d);
		assertEquals(Severity.WARNING, d.getSeverity());
		assertEquals("Foo", d.getMessage());
		assertNull(d.getError());
		assertFalse(d.isStopper());
		
		assertThrows(NullPointerException.class, ()->Diagnostics.createWarningDiagnostic(null, null));
		assertThrows(NullPointerException.class, ()->Diagnostics.createWarningDiagnostic(null, new IllegalStateException()));
		
		d = Diagnostics.createWarningDiagnostic("Foo", null);
		assertNotNull(d);
		assertEquals(Severity.WARNING, d.getSeverity());
		assertEquals("Foo", d.getMessage());
		assertNull(d.getError());
		assertFalse(d.isStopper());
		
		d = Diagnostics.createWarningDiagnostic("Foo", new IllegalStateException("Fooxception"));
		assertNotNull(d);
		assertEquals(Severity.WARNING, d.getSeverity());
		assertEquals("Foo", d.getMessage());
		assertNotNull(d.getError());
		assertEquals("Fooxception", d.getError().getMessage());
		assertFalse(d.isStopper());
	}
	
	@Test
	public void testErrorDiagnostic() {
		assertThrows(NullPointerException.class, ()->Diagnostics.createErrorDiagnostic(null));
		Diagnostic d = Diagnostics.createErrorDiagnostic("Foo");
		assertNotNull(d);
		assertEquals(Severity.ERROR, d.getSeverity());
		assertEquals("Foo", d.getMessage());
		assertNull(d.getError());
		assertFalse(d.isStopper());
		
		assertThrows(NullPointerException.class, ()->Diagnostics.createErrorDiagnostic(null, null));
		assertThrows(NullPointerException.class, ()->Diagnostics.createErrorDiagnostic(null, new IllegalStateException()));
		assertThrows(NullPointerException.class, ()->Diagnostics.createErrorDiagnostic(null, new IllegalStateException(), true));
		assertThrows(NullPointerException.class, ()->Diagnostics.createErrorDiagnostic(null, null, false));
		
		d = Diagnostics.createErrorDiagnostic("Foo", null);
		assertNotNull(d);
		assertEquals(Severity.ERROR, d.getSeverity());
		assertEquals("Foo", d.getMessage());
		assertNull(d.getError());
		assertFalse(d.isStopper());
		
		d = Diagnostics.createErrorDiagnostic("Foo", new IllegalStateException("Fooxception"));
		assertNotNull(d);
		assertEquals(Severity.ERROR, d.getSeverity());
		assertEquals("Foo", d.getMessage());
		assertNotNull(d.getError());
		assertEquals("Fooxception", d.getError().getMessage());
		assertFalse(d.isStopper());
		
		d = Diagnostics.createErrorDiagnostic("Foo", new IllegalStateException("Fooxception"), true);
		assertNotNull(d);
		assertEquals(Severity.ERROR, d.getSeverity());
		assertEquals("Foo", d.getMessage());
		assertNotNull(d.getError());
		assertEquals("Fooxception", d.getError().getMessage());
		assertTrue(d.isStopper());
		
		d = Diagnostics.createErrorDiagnostic("Foo", new IllegalStateException("Fooxception"), false);
		assertNotNull(d);
		assertEquals(Severity.ERROR, d.getSeverity());
		assertEquals("Foo", d.getMessage());
		assertNotNull(d.getError());
		assertEquals("Fooxception", d.getError().getMessage());
		assertFalse(d.isStopper());
	}
	
	@Test
	public void testDiagnostic() {
		assertThrows(NullPointerException.class, ()->Diagnostics.createDiagnostic(null));
		assertThrows(NullPointerException.class, ()->Diagnostics.createDiagnostic(null, null, null, true));
		Diagnostic d = Diagnostics.createDiagnostic(Severity.ERROR, "Foo", null, true);
		assertNotNull(d);
		assertEquals(Severity.ERROR, d.getSeverity());
		assertEquals("Foo", d.getMessage());
		assertNull(d.getError());
		assertTrue(d.isStopper());
		
		assertThrows(NullPointerException.class, ()->Diagnostics.createDiagnostic(null, "Foo", new IllegalStateException(), true));
		assertThrows(NullPointerException.class, ()->Diagnostics.createDiagnostic(Severity.INFO, null, new IllegalStateException(), true));
		assertThrows(NullPointerException.class, ()->Diagnostics.createDiagnostic(Severity.INFO, null, null, true));
		assertThrows(NullPointerException.class, ()->Diagnostics.createDiagnostic(null, "foo", null, true));
		
		d = Diagnostics.createDiagnostic(Severity.INFO, "Foo", null, false);
		assertNotNull(d);
		assertEquals(Severity.INFO, d.getSeverity());
		assertEquals("Foo", d.getMessage());
		assertNull(d.getError());
		assertFalse(d.isStopper());
		
		d = Diagnostics.createDiagnostic(Severity.WARNING, "Foo", new IllegalStateException("Fooxception"), false);
		assertNotNull(d);
		assertEquals(Severity.WARNING, d.getSeverity());
		assertEquals("Foo", d.getMessage());
		assertNotNull(d.getError());
		assertEquals("Fooxception", d.getError().getMessage());
		assertFalse(d.isStopper());
		
		d = Diagnostics.createDiagnostic(Severity.INFO, "Foo", null, true);
		assertNotNull(d);
		assertEquals(Severity.INFO, d.getSeverity());
		assertEquals("Foo", d.getMessage());
		assertNull(d.getError());
		assertTrue(d.isStopper());
		
		d = Diagnostics.createDiagnostic(Severity.INFO, "Foo", new IllegalStateException("Fooxception"), false);
		assertNotNull(d);
		assertEquals(Severity.INFO, d.getSeverity());
		assertEquals("Foo", d.getMessage());
		assertNotNull(d.getError());
		assertEquals("Fooxception", d.getError().getMessage());
		assertFalse(d.isStopper());
		
		d = Diagnostics.createDiagnostic(new IllegalStateException("Fooxception"));
		assertNotNull(d);
		assertEquals(Severity.ERROR, d.getSeverity());
		assertEquals("Fooxception", d.getMessage());
		assertNotNull(d.getError());
		assertEquals("Fooxception", d.getError().getMessage());
		assertFalse(d.isStopper());
	}
}
