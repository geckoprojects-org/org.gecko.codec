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
package org.gecko.codec.scope;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.gecko.codec.CodecConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * 
 * @author mark
 * @since 23.03.2023
 */
@ExtendWith(MockitoExtension.class)
public class BasicScopeTest {
	
	private BasicScope scope;
	
	@BeforeEach
	public void beforeEach() {
		scope = createAbstractMock(BasicScope.class);
	}
	
	@Test
	public void testValidation() {
		assertTrue(scope.validate());
		scope.getProperties().put("Foo", "Bar");
		assertTrue(scope.validate());
	}
	
	@Test
	public void testClassifierType() {
		scope.getProperties().put(CodecConstants.ENCODE_TYPE, "Foo");
		assertTrue(scope.validate());
		assertEquals(CodecConstants.FEATURE_TYPE_DEFAULT, scope.getNamespaceKey());
		assertEquals("Foo", scope.getNamespace());
		
		scope.getProperties().put(CodecConstants.KEY_CLASSIFIER_TYPE, "myType");
		assertTrue(scope.validate());
		assertEquals("myType", scope.getNamespaceKey());
		assertEquals("Foo", scope.getNamespace());

		scope.getProperties().put(CodecConstants.ENCODE_TYPE, null);
		assertTrue(scope.validate());
		assertFalse(scope.isWriteNamespace());
		assertEquals("myType", scope.getNamespaceKey());
		assertNull(scope.getNamespace());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSuperClassifierType() {
		scope.getProperties().put(CodecConstants.ENCODE_SUPERTYPES, "Foo");
		assertTrue(scope.validate());
		assertEquals(CodecConstants.FEATURE_CLASSIFIER_SUPERTYPE_DEFAULT, scope.getSuperNamespaceKey());
		assertInstanceOf(List.class, scope.getSuperNamespace());
		List<String> supers = (List<String>) scope.getSuperNamespace();
		assertEquals(1, supers.size());
		assertEquals("Foo", supers.get(0));
		
		scope.getProperties().put(CodecConstants.KEY_CLASSIFIER_SUPERTYPE, "mySuperType");
		assertTrue(scope.validate());
		assertEquals("mySuperType", scope.getSuperNamespaceKey());
		supers = (List<String>) scope.getSuperNamespace();
		assertEquals(1, supers.size());
		assertEquals("Foo", supers.get(0));
		
		scope.getProperties().put(CodecConstants.ENCODE_SUPERTYPES, null);
		assertTrue(scope.validate());
		assertFalse(scope.isWriteSuperNamespaces());
		assertEquals("mySuperType", scope.getSuperNamespaceKey());
		assertNull(scope.getSuperNamespace());
		
		scope.getProperties().put(CodecConstants.ENCODE_SUPERTYPES, List.of("Fizz", "Buzz"));
		assertTrue(scope.validate());
		assertTrue(scope.isWriteSuperNamespaces());
		assertEquals("mySuperType", scope.getSuperNamespaceKey());
		supers = (List<String>) scope.getSuperNamespace();
		assertEquals(2, supers.size());
		assertEquals("Fizz", supers.get(0));
		assertEquals("Buzz", supers.get(1));
		
		scope.getProperties().put(CodecConstants.ENCODE_SUPERTYPES, new String[] {"Fizz", "Buzz"});
		assertTrue(scope.validate());
		assertTrue(scope.isWriteSuperNamespaces());
		assertEquals("mySuperType", scope.getSuperNamespaceKey());
		supers = (List<String>) scope.getSuperNamespace();
		assertEquals(2, supers.size());
		assertEquals("Fizz", supers.get(0));
		assertEquals("Buzz", supers.get(1));
		
		scope.getProperties().put(CodecConstants.ENCODE_SUPERTYPES, new Integer[] {42, 24});
		assertTrue(scope.validate());
		assertTrue(scope.isWriteSuperNamespaces());
		assertEquals("mySuperType", scope.getSuperNamespaceKey());
		supers = (List<String>) scope.getSuperNamespace();
		assertEquals(2, supers.size());
		assertEquals("42", supers.get(0));
		assertEquals("24", supers.get(1));
		
		scope.getProperties().put(CodecConstants.ENCODE_SUPERTYPES, List.of(42, 24));
		assertTrue(scope.validate());
		assertTrue(scope.isWriteSuperNamespaces());
		assertEquals("mySuperType", scope.getSuperNamespaceKey());
		supers = (List<String>) scope.getSuperNamespace();
		assertEquals(2, supers.size());
		assertEquals("42", supers.get(0));
		assertEquals("24", supers.get(1));
	}
	
	@Test
	public void testTimestamp() {
		scope.getProperties().put(CodecConstants.ENCODE_TIMESTAMP, "Foo");
		assertFalse(scope.validate());
		assertEquals(1, scope.getDiagnostics().size());
		assertInstanceOf(IllegalStateException.class, scope.getDiagnostics().get(0).getError());
		
		scope.getDiagnostics().clear();
		
		long now = System.currentTimeMillis();
		scope.getProperties().put(CodecConstants.ENCODE_TIMESTAMP, now);
		assertTrue(scope.validate());
		
		assertEquals(CodecConstants.FEATURE_TIMESTAMP_DEFAULT, scope.getTimestampKey());
		assertEquals(now, scope.getTimestamp());
		
		scope.getProperties().put(CodecConstants.KEY_FEATURE_TIMESTAMP, "myTimestamp");
		assertTrue(scope.validate());
		assertEquals("myTimestamp", scope.getTimestampKey());
		assertEquals(now, scope.getTimestamp());
		
		scope.getProperties().put(CodecConstants.ENCODE_TIMESTAMP, null);
		assertTrue(scope.validate());
		assertEquals("myTimestamp", scope.getTimestampKey());
		assertNull(scope.getTimestamp());
	}
	
	@Test
	public void testIdField() {
		scope.getProperties().put(CodecConstants.ENCODE_TIMESTAMP, "Foo");
		assertFalse(scope.validate());
		assertEquals(1, scope.getDiagnostics().size());
		assertInstanceOf(IllegalStateException.class, scope.getDiagnostics().get(0).getError());
		
		scope.getDiagnostics().clear();
		
		long now = System.currentTimeMillis();
		scope.getProperties().put(CodecConstants.ENCODE_TIMESTAMP, now);
		assertTrue(scope.validate());
		
		assertEquals(CodecConstants.FEATURE_TIMESTAMP_DEFAULT, scope.getTimestampKey());
		assertEquals(now, scope.getTimestamp());
		
		scope.getProperties().put(CodecConstants.KEY_FEATURE_TIMESTAMP, "myTimestamp");
		assertTrue(scope.validate());
		assertEquals("myTimestamp", scope.getTimestampKey());
		assertEquals(now, scope.getTimestamp());
		
		scope.getProperties().put(CodecConstants.ENCODE_TIMESTAMP, null);
		assertTrue(scope.validate());
		assertEquals("myTimestamp", scope.getTimestampKey());
		assertNull(scope.getTimestamp());
	}

	public <T> T createAbstractMock(Class<T> mockClass) {
		return mock(mockClass, 
				Mockito.withSettings().
				useConstructor().
				defaultAnswer(Mockito.CALLS_REAL_METHODS));
	}
	
}
