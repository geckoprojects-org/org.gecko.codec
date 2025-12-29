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
package org.eclipse.fennec.codec.v2.context;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.SerializationContext;

/**
 * Tests for {@link ContextHelper}.
 */
@DisplayName("ContextHelper")
class ContextHelperTest {

    private DeserializationContext deserCtxt;
    private SerializationContext serCtxt;
    private EClass testEClass;

    @BeforeEach
    void setUp() {
        deserCtxt = mock(DeserializationContext.class);
        serCtxt = mock(SerializationContext.class);
        testEClass = EcoreFactory.eINSTANCE.createEClass();
        testEClass.setName("TestClass");
    }

    @Nested
    @DisplayName("Constants")
    class ConstantsTest {

        @Test
        @DisplayName("EXPECTED_TYPE constant is defined")
        void expectedTypeConstant() {
            assertEquals("CODEC_EXPECTED_TYPE", ContextHelper.EXPECTED_TYPE);
        }

        @Test
        @DisplayName("UNRESOLVED_REFERENCES constant is defined")
        void unresolvedReferencesConstant() {
            assertEquals("CODEC_UNRESOLVED_REFERENCES", ContextHelper.UNRESOLVED_REFERENCES);
        }
    }

    @Nested
    @DisplayName("getExpectedType(DeserializationContext)")
    class GetExpectedTypeDeserializationTest {

        @Test
        @DisplayName("returns null when attribute is not set")
        void returnsNullWhenNotSet() {
            when(deserCtxt.getAttribute(ContextHelper.EXPECTED_TYPE)).thenReturn(null);

            EClass result = ContextHelper.getExpectedType(deserCtxt);

            assertNull(result);
            verify(deserCtxt).getAttribute(ContextHelper.EXPECTED_TYPE);
        }

        @Test
        @DisplayName("returns EClass when attribute is EClass")
        void returnsEClassWhenSet() {
            when(deserCtxt.getAttribute(ContextHelper.EXPECTED_TYPE)).thenReturn(testEClass);

            EClass result = ContextHelper.getExpectedType(deserCtxt);

            assertSame(testEClass, result);
        }

        @Test
        @DisplayName("returns EcorePackage EClass")
        void returnsEcorePackageEClass() {
            EClass eObjectClass = EcorePackage.eINSTANCE.getEObject();
            when(deserCtxt.getAttribute(ContextHelper.EXPECTED_TYPE)).thenReturn(eObjectClass);

            EClass result = ContextHelper.getExpectedType(deserCtxt);

            assertSame(eObjectClass, result);
        }

        @Test
        @DisplayName("throws IllegalStateException when attribute is wrong type")
        void throwsExceptionWhenWrongType() {
            when(deserCtxt.getAttribute(ContextHelper.EXPECTED_TYPE)).thenReturn("NotAnEClass");

            IllegalStateException exception = assertThrows(
                    IllegalStateException.class,
                    () -> ContextHelper.getExpectedType(deserCtxt));

            assertTrue(exception.getMessage().contains("CODEC_EXPECTED_TYPE"));
            assertTrue(exception.getMessage().contains("EClass"));
            assertTrue(exception.getMessage().contains("String"));
        }
    }

    @Nested
    @DisplayName("getExpectedType(SerializationContext)")
    class GetExpectedTypeSerializationTest {

        @Test
        @DisplayName("returns null when attribute is not set")
        void returnsNullWhenNotSet() {
            when(serCtxt.getAttribute(ContextHelper.EXPECTED_TYPE)).thenReturn(null);

            EClass result = ContextHelper.getExpectedType(serCtxt);

            assertNull(result);
            verify(serCtxt).getAttribute(ContextHelper.EXPECTED_TYPE);
        }

        @Test
        @DisplayName("returns EClass when attribute is EClass")
        void returnsEClassWhenSet() {
            when(serCtxt.getAttribute(ContextHelper.EXPECTED_TYPE)).thenReturn(testEClass);

            EClass result = ContextHelper.getExpectedType(serCtxt);

            assertSame(testEClass, result);
        }

        @Test
        @DisplayName("throws IllegalStateException when attribute is wrong type")
        void throwsExceptionWhenWrongType() {
            when(serCtxt.getAttribute(ContextHelper.EXPECTED_TYPE)).thenReturn(42);

            IllegalStateException exception = assertThrows(
                    IllegalStateException.class,
                    () -> ContextHelper.getExpectedType(serCtxt));

            assertTrue(exception.getMessage().contains("CODEC_EXPECTED_TYPE"));
            assertTrue(exception.getMessage().contains("EClass"));
            assertTrue(exception.getMessage().contains("Integer"));
        }
    }

    @Nested
    @DisplayName("setExpectedType(DeserializationContext, EClass)")
    class SetExpectedTypeDeserializationTest {

        @Test
        @DisplayName("sets attribute on context")
        void setsAttribute() {
            when(deserCtxt.setAttribute(any(), any())).thenReturn(deserCtxt);

            ContextHelper.setExpectedType(deserCtxt, testEClass);

            verify(deserCtxt).setAttribute(ContextHelper.EXPECTED_TYPE, testEClass);
        }

        @Test
        @DisplayName("throws IllegalArgumentException when eClass is null")
        void throwsExceptionWhenNull() {
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> ContextHelper.setExpectedType(deserCtxt, null));

            assertEquals("eClass must not be null", exception.getMessage());
            verify(deserCtxt, never()).setAttribute(any(), any());
        }
    }

    @Nested
    @DisplayName("setExpectedType(SerializationContext, EClass)")
    class SetExpectedTypeSerializationTest {

        @Test
        @DisplayName("sets attribute on context")
        void setsAttribute() {
            when(serCtxt.setAttribute(any(), any())).thenReturn(serCtxt);

            ContextHelper.setExpectedType(serCtxt, testEClass);

            verify(serCtxt).setAttribute(ContextHelper.EXPECTED_TYPE, testEClass);
        }

        @Test
        @DisplayName("throws IllegalArgumentException when eClass is null")
        void throwsExceptionWhenNull() {
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> ContextHelper.setExpectedType(serCtxt, null));

            assertEquals("eClass must not be null", exception.getMessage());
            verify(serCtxt, never()).setAttribute(any(), any());
        }
    }

    @Nested
    @DisplayName("clearExpectedType(DeserializationContext)")
    class ClearExpectedTypeDeserializationTest {

        @Test
        @DisplayName("sets attribute to null")
        void clearsAttribute() {
            when(deserCtxt.setAttribute(any(), any())).thenReturn(deserCtxt);

            ContextHelper.clearExpectedType(deserCtxt);

            verify(deserCtxt).setAttribute(ContextHelper.EXPECTED_TYPE, null);
        }
    }

    @Nested
    @DisplayName("clearExpectedType(SerializationContext)")
    class ClearExpectedTypeSerializationTest {

        @Test
        @DisplayName("sets attribute to null")
        void clearsAttribute() {
            when(serCtxt.setAttribute(any(), any())).thenReturn(serCtxt);

            ContextHelper.clearExpectedType(serCtxt);

            verify(serCtxt).setAttribute(ContextHelper.EXPECTED_TYPE, null);
        }
    }
}
