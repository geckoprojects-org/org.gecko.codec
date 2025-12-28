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
package org.eclipse.fennec.model.metadata.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.fennec.model.metadata.AttributeMetadata;
import org.eclipse.fennec.model.metadata.ClassAspect;
import org.eclipse.fennec.model.metadata.ClassMetadata;
import org.eclipse.fennec.model.metadata.FeatureAspect;
import org.eclipse.fennec.model.metadata.FeatureMetadata;
import org.eclipse.fennec.model.metadata.PackageMetadata;
import org.eclipse.fennec.model.metadata.ReferenceMetadata;
import org.eclipse.fennec.model.metadata.api.AspectProvider;
import org.eclipse.fennec.model.metadata.api.MetadataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link MetadataServiceImpl}.
 */
class MetadataServiceImplTest {

    private MetadataService service;
    private EPackage testPackage;
    private EClass personClass;
    private EClass addressClass;
    private EAttribute nameAttr;
    private EAttribute idAttr;
    private EReference addressRef;
    private EReference personRef;

    @BeforeEach
    void setUp() {
        service = new MetadataServiceImpl();
        createTestPackage();
    }

    private void createTestPackage() {
        // Create test package
        testPackage = EcoreFactory.eINSTANCE.createEPackage();
        testPackage.setName("test");
        testPackage.setNsURI("http://test.example.org/1.0");
        testPackage.setNsPrefix("test");

        // Create Person class
        personClass = EcoreFactory.eINSTANCE.createEClass();
        personClass.setName("Person");
        testPackage.getEClassifiers().add(personClass);

        // Create ID attribute
        idAttr = EcoreFactory.eINSTANCE.createEAttribute();
        idAttr.setName("id");
        idAttr.setEType(EcorePackage.Literals.ESTRING);
        idAttr.setID(true);
        personClass.getEStructuralFeatures().add(idAttr);

        // Create name attribute
        nameAttr = EcoreFactory.eINSTANCE.createEAttribute();
        nameAttr.setName("name");
        nameAttr.setEType(EcorePackage.Literals.ESTRING);
        personClass.getEStructuralFeatures().add(nameAttr);

        // Create Address class
        addressClass = EcoreFactory.eINSTANCE.createEClass();
        addressClass.setName("Address");
        testPackage.getEClassifiers().add(addressClass);

        // Create bidirectional references
        addressRef = EcoreFactory.eINSTANCE.createEReference();
        addressRef.setName("address");
        addressRef.setEType(addressClass);
        addressRef.setContainment(true);
        personClass.getEStructuralFeatures().add(addressRef);

        personRef = EcoreFactory.eINSTANCE.createEReference();
        personRef.setName("person");
        personRef.setEType(personClass);
        addressClass.getEStructuralFeatures().add(personRef);

        // Set opposites
        addressRef.setEOpposite(personRef);
        personRef.setEOpposite(addressRef);
    }

    // ========================================================================
    // Package Registration Tests
    // ========================================================================

    @Test
    void testRegisterPackage() {
        PackageMetadata pkgMetadata = service.registerPackage(testPackage);

        assertNotNull(pkgMetadata);
        assertEquals("http://test.example.org/1.0", pkgMetadata.getNsURI());
        assertEquals(testPackage, pkgMetadata.getEPackage());
        assertEquals(2, pkgMetadata.getClasses().size());
    }

    @Test
    void testRegisterPackageNull() {
        PackageMetadata pkgMetadata = service.registerPackage(null);
        assertNull(pkgMetadata);
    }

    @Test
    void testRegisterPackageTwice() {
        PackageMetadata first = service.registerPackage(testPackage);
        PackageMetadata second = service.registerPackage(testPackage);

        assertSame(first, second, "Registering same package twice should return same metadata");
    }

    @Test
    void testUnregisterPackage() {
        service.registerPackage(testPackage);
        assertNotNull(service.getPackageMetadata("http://test.example.org/1.0"));

        service.unregisterPackage(testPackage);
        assertNull(service.getPackageMetadata("http://test.example.org/1.0"));
    }

    // ========================================================================
    // Package Lookup Tests
    // ========================================================================

    @Test
    void testGetPackageMetadata() {
        service.registerPackage(testPackage);

        PackageMetadata found = service.getPackageMetadata("http://test.example.org/1.0");
        assertNotNull(found);
        assertEquals(testPackage, found.getEPackage());
    }

    @Test
    void testGetPackageMetadataNotFound() {
        PackageMetadata found = service.getPackageMetadata("http://nonexistent.org/1.0");
        assertNull(found);
    }

    // ========================================================================
    // Class Metadata Tests
    // ========================================================================

    @Test
    void testClassMetadataCreation() {
        service.registerPackage(testPackage);

        ClassMetadata personMeta = service.getClassMetadata(personClass);
        assertNotNull(personMeta);
        assertEquals("Person", personMeta.getName());
        assertEquals(personClass, personMeta.getEClass());
        assertTrue(personMeta.isHasId());
        assertEquals(3, personMeta.getFeatures().size()); // id, name, address
    }

    @Test
    void testClassMetadataIdFeatures() {
        service.registerPackage(testPackage);

        ClassMetadata personMeta = service.getClassMetadata(personClass);
        assertEquals(1, personMeta.getIdFeatures().size());

        FeatureMetadata idFeatureMeta = personMeta.getIdFeatures().get(0);
        assertEquals("id", idFeatureMeta.getName());
        assertTrue(idFeatureMeta instanceof AttributeMetadata);
        assertTrue(((AttributeMetadata) idFeatureMeta).isIsId());
    }

    @Test
    void testGetClassMetadataByURI() {
        service.registerPackage(testPackage);

        ClassMetadata personMeta = service.getClassMetadata(personClass);
        String uri = personMeta.getTypeURI();

        ClassMetadata found = service.getClassMetadataByURI(uri);
        assertSame(personMeta, found);
    }

    @Test
    void testGetClassMetadataByName() {
        service.registerPackage(testPackage);

        ClassMetadata found = service.getClassMetadataByName("Person", "http://test.example.org/1.0");
        assertNotNull(found);
        assertEquals("Person", found.getName());
    }

    @Test
    void testGetClassMetadataByNameNotFound() {
        service.registerPackage(testPackage);

        ClassMetadata found = service.getClassMetadataByName("NonExistent", "http://test.example.org/1.0");
        assertNull(found);
    }

    // ========================================================================
    // Feature Metadata Tests
    // ========================================================================

    @Test
    void testAttributeMetadataCreation() {
        service.registerPackage(testPackage);

        FeatureMetadata nameMeta = service.getFeatureMetadata(nameAttr);
        assertNotNull(nameMeta);
        assertTrue(nameMeta instanceof AttributeMetadata);
        assertEquals("name", nameMeta.getName());
        assertEquals(nameAttr, nameMeta.getEFeature());
    }

    @Test
    void testReferenceMetadataCreation() {
        service.registerPackage(testPackage);

        FeatureMetadata addressMeta = service.getFeatureMetadata(addressRef);
        assertNotNull(addressMeta);
        assertTrue(addressMeta instanceof ReferenceMetadata);
        assertEquals("address", addressMeta.getName());

        ReferenceMetadata refMeta = (ReferenceMetadata) addressMeta;
        assertTrue(refMeta.isContainment());
        assertTrue(refMeta.isHasBidirectional());
    }

    @Test
    void testGetFeatureMetadataByName() {
        service.registerPackage(testPackage);

        FeatureMetadata found = service.getFeatureMetadataByName("name", "Person", "http://test.example.org/1.0");
        assertNotNull(found);
        assertEquals("name", found.getName());
    }

    @Test
    void testGetFeatureMetadataFromClass() {
        service.registerPackage(testPackage);

        ClassMetadata personMeta = service.getClassMetadata(personClass);
        FeatureMetadata found = service.getFeatureMetadataFromClass("address", personMeta);
        assertNotNull(found);
        assertEquals("address", found.getName());
    }

    // ========================================================================
    // Reference Resolution Tests
    // ========================================================================

    @Test
    void testReferenceTargetResolution() {
        service.registerPackage(testPackage);

        FeatureMetadata addressMeta = service.getFeatureMetadata(addressRef);
        assertTrue(addressMeta instanceof ReferenceMetadata);

        ReferenceMetadata refMeta = (ReferenceMetadata) addressMeta;
        assertNotNull(refMeta.getTargetClassMetadata());
        assertEquals("Address", refMeta.getTargetClassMetadata().getName());
    }

    @Test
    void testReferenceOppositeResolution() {
        service.registerPackage(testPackage);

        ReferenceMetadata addressMeta = (ReferenceMetadata) service.getFeatureMetadata(addressRef);
        ReferenceMetadata personMeta = (ReferenceMetadata) service.getFeatureMetadata(personRef);

        assertNotNull(addressMeta.getOppositeMetadata());
        assertSame(personMeta, addressMeta.getOppositeMetadata());

        assertNotNull(personMeta.getOppositeMetadata());
        assertSame(addressMeta, personMeta.getOppositeMetadata());
    }

    // ========================================================================
    // AspectProvider Tests
    // ========================================================================

    @Test
    void testRegisterAspectProvider() {
        TestAspectProvider provider = new TestAspectProvider();

        service.registerAspectProvider(provider);
        assertEquals(1, service.getAspectProviders().size());
        assertSame(provider, service.getAspectProviders().get(0));
    }

    @Test
    void testAspectProviderAppliedOnRegistration() {
        // Register provider before package
        TestAspectProvider provider = new TestAspectProvider();
        service.registerAspectProvider(provider);

        // Now register package
        service.registerPackage(testPackage);

        // Check that aspects were created
        ClassAspect classAspect = service.getClassAspect(personClass, "test");
        assertNotNull(classAspect);
        assertEquals("test", classAspect.getTypeId());

        FeatureAspect featureAspect = service.getFeatureAspect(nameAttr, "test");
        assertNotNull(featureAspect);
        assertEquals("test", featureAspect.getTypeId());
    }

    @Test
    void testAspectProviderAppliedToExistingMetadata() {
        // Register package first
        service.registerPackage(testPackage);

        // Now register provider - should be applied to existing metadata
        TestAspectProvider provider = new TestAspectProvider();
        service.registerAspectProvider(provider);

        ClassAspect classAspect = service.getClassAspect(personClass, "test");
        assertNotNull(classAspect);
    }

    @Test
    void testUnregisterAspectProvider() {
        TestAspectProvider provider = new TestAspectProvider();
        service.registerAspectProvider(provider);
        service.registerPackage(testPackage);

        // Verify aspect exists
        assertNotNull(service.getClassAspect(personClass, "test"));

        // Unregister provider
        service.unregisterAspectProvider(provider);

        // Aspect should be removed
        assertNull(service.getClassAspect(personClass, "test"));
        assertEquals(0, service.getAspectProviders().size());
    }

    // ========================================================================
    // Registry Tests
    // ========================================================================

    @Test
    void testGetRegistry() {
        service.registerPackage(testPackage);

        assertNotNull(service.getRegistry());
        assertEquals(1, service.getRegistry().getPackages().size());
    }

    // ========================================================================
    // Test AspectProvider Implementation
    // ========================================================================

    /**
     * Simple test AspectProvider that creates dummy aspects.
     */
    private static class TestAspectProvider implements AspectProvider {

        @Override
        public String getAspectTypeId() {
            return "test";
        }

        @Override
        public ClassAspect buildClassAspect(EClass eClass) {
            // Create a simple aspect - use a concrete implementation
            // For testing, we'll create an anonymous subclass
            return new TestClassAspect();
        }

        @Override
        public FeatureAspect buildFeatureAspect(org.eclipse.emf.ecore.EStructuralFeature feature) {
            return new TestFeatureAspect();
        }

        @Override
        public FeatureAspect buildAttributeAspect(org.eclipse.emf.ecore.EAttribute attribute) {
            return buildFeatureAspect(attribute);
        }

        @Override
        public FeatureAspect buildReferenceAspect(org.eclipse.emf.ecore.EReference reference) {
            return buildFeatureAspect(reference);
        }
    }

    /**
     * Test ClassAspect implementation.
     */
    private static class TestClassAspect extends org.eclipse.fennec.model.metadata.impl.ClassAspectImpl {
        // Uses default implementation
    }

    /**
     * Test FeatureAspect implementation.
     */
    private static class TestFeatureAspect extends org.eclipse.fennec.model.metadata.impl.FeatureAspectImpl {
        // Uses default implementation
    }
}
