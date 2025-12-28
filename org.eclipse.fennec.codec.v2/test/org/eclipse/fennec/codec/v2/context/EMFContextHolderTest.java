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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.fennec.model.metadata.api.MetadataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link EMFContextHolder}.
 */
@DisplayName("EMFContextHolder")
class EMFContextHolderTest {

    private EMFContextHolder holder;
    private MetadataService metadataService;

    @BeforeEach
    void setUp() {
        metadataService = mock(MetadataService.class);
        holder = new EMFContextHolder(metadataService);
    }

    @Test
    @DisplayName("default constructor creates holder with null metadata service")
    void defaultConstructorNullMetadataService() {
        EMFContextHolder defaultHolder = new EMFContextHolder();
        assertNull(defaultHolder.getMetadataService());
    }

    @Test
    @DisplayName("constructor with metadata service stores service")
    void constructorWithMetadataService() {
        assertSame(metadataService, holder.getMetadataService());
    }

    @Test
    @DisplayName("setCurrentEObject and getCurrentEObject")
    void setAndGetCurrentEObject() {
        EObject eObject = mock(EObject.class);
        holder.setCurrentEObject(eObject);
        assertSame(eObject, holder.getCurrentEObject());
    }

    @Test
    @DisplayName("getCurrentEObject returns null by default")
    void getCurrentEObjectDefaultNull() {
        assertNull(holder.getCurrentEObject());
    }

    @Test
    @DisplayName("setCurrentFeature and getCurrentFeature")
    void setAndGetCurrentFeature() {
        EStructuralFeature feature = mock(EStructuralFeature.class);
        holder.setCurrentFeature(feature);
        assertSame(feature, holder.getCurrentFeature());
    }

    @Test
    @DisplayName("getCurrentFeature returns null by default")
    void getCurrentFeatureDefaultNull() {
        assertNull(holder.getCurrentFeature());
    }

    @Test
    @DisplayName("resetFeature sets current feature to null")
    void resetFeatureSetsNull() {
        EStructuralFeature feature = mock(EStructuralFeature.class);
        holder.setCurrentFeature(feature);
        holder.resetFeature();
        assertNull(holder.getCurrentFeature());
    }

    @Test
    @DisplayName("setResource and getResource")
    void setAndGetResource() {
        Resource resource = mock(Resource.class);
        holder.setResource(resource);
        assertSame(resource, holder.getResource());
    }

    @Test
    @DisplayName("getResource returns null by default")
    void getResourceDefaultNull() {
        assertNull(holder.getResource());
    }

    @Test
    @DisplayName("setMetadataService and getMetadataService")
    void setAndGetMetadataService() {
        MetadataService newService = mock(MetadataService.class);
        holder.setMetadataService(newService);
        assertSame(newService, holder.getMetadataService());
    }

    @Test
    @DisplayName("setCurrentTypeHint and getCurrentTypeHint")
    void setAndGetCurrentTypeHint() {
        EClass typeHint = mock(EClass.class);
        holder.setCurrentTypeHint(typeHint);
        assertSame(typeHint, holder.getCurrentTypeHint());
    }

    @Test
    @DisplayName("getCurrentTypeHint returns null by default")
    void getCurrentTypeHintDefaultNull() {
        assertNull(holder.getCurrentTypeHint());
    }
}
