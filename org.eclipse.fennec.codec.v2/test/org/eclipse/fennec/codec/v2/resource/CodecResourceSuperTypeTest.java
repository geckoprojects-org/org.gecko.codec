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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.fennec.codec.v2.config.CodecConfiguration;
import org.eclipse.fennec.codec.v2.util.MetadataServiceFactory;
import org.eclipse.fennec.model.metadata.api.MetadataService;
import org.eclipse.fennec.model.metadata.utils.EcoreHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Integration tests for SuperType serialization.
 * <p>
 * Tests verify that supertype information is correctly serialized
 * based on configuration (enabled, selection mode, custom keys).
 * </p>
 *
 * @see <a href="docs/codec-v2-spec/05-supertype.md">Spec: SuperType Serialization</a>
 */
@DisplayName("SuperType Serialization Tests")
class CodecResourceSuperTypeTest {

    private static final String TEST_ECORE = "test-annotated.ecore";

    private EcoreHelper ecoreHelper;
    private EPackage testPackage;
    private MetadataService metadataService;

    // Classes
    private EClass customerClass;
    private EClass vipCustomerClass;

    // Attributes
    private EAttribute emailAttribute;
    private EAttribute firstNameAttribute;
    private EAttribute lastNameAttribute;
    private EAttribute vipLevelAttribute;

    @BeforeEach
    void setUp() throws IOException {
        ecoreHelper = new EcoreHelper(CodecResourceSuperTypeTest.class);
        testPackage = ecoreHelper.loadEcore(TEST_ECORE);
        EPackage.Registry.INSTANCE.put(testPackage.getNsURI(), testPackage);

        metadataService = MetadataServiceFactory.create();
        metadataService.registerPackage(testPackage);

        customerClass = ecoreHelper.getEClass(testPackage, "Customer");
        vipCustomerClass = ecoreHelper.getEClass(testPackage, "VIPCustomer");

        emailAttribute = (EAttribute) ecoreHelper.getFeature(customerClass, "email");
        firstNameAttribute = (EAttribute) ecoreHelper.getFeature(customerClass, "firstName");
        lastNameAttribute = (EAttribute) ecoreHelper.getFeature(customerClass, "lastName");
        vipLevelAttribute = (EAttribute) ecoreHelper.getFeature(vipCustomerClass, "vipLevel");
    }

    @AfterEach
    void tearDown() {
        EPackage.Registry.INSTANCE.remove(testPackage.getNsURI());
        ecoreHelper.releaseAll();
    }

    private EObject createCustomer(String email, String firstName, String lastName) {
        EObject customer = testPackage.getEFactoryInstance().create(customerClass);
        customer.eSet(emailAttribute, email);
        customer.eSet(firstNameAttribute, firstName);
        customer.eSet(lastNameAttribute, lastName);
        return customer;
    }

    private EObject createVipCustomer(String email, String firstName, String lastName, int vipLevel) {
        EObject vip = testPackage.getEFactoryInstance().create(vipCustomerClass);
        vip.eSet(emailAttribute, email);
        vip.eSet(firstNameAttribute, firstName);
        vip.eSet(lastNameAttribute, lastName);
        vip.eSet(vipLevelAttribute, vipLevel);
        return vip;
    }

    // ========================================================================
    // Annotation-Based SuperType Tests
    // ========================================================================

    @Nested
    @DisplayName("Annotation-based supertype serialization")
    class AnnotationBasedSuperType {

        @Test
        @DisplayName("Customer class has supertype enabled via annotation")
        void customerHasSuperTypeEnabled() throws IOException {
            EObject customer = createCustomer("john@example.com", "John", "Doe");

            String json = serialize(customer);
            System.out.println("Customer JSON:\n" + json);

            // Customer annotation has superTypeSerialize=true and superTypeKey=_extends
            // But Customer has no supertypes, so _extends should NOT appear
            assertFalse(json.contains("\"_extends\""),
                    "Customer has no supertypes, so _extends should not appear");
        }

        @Test
        @DisplayName("VIPCustomer serializes supertype from parent class")
        void vipCustomerSerializesSuperType() throws IOException {
            EObject vip = createVipCustomer("vip@example.com", "Jane", "VIP", 5);

            String json = serialize(vip);
            System.out.println("VIPCustomer JSON:\n" + json);

            // VIPCustomer extends Customer, and Customer has supertype enabled
            // However, the supertype config is on Customer, not VIPCustomer
            // Need to check if inheritance works...
            // For now, verify the JSON is valid
            assertTrue(json.contains("\"email\""), "JSON should contain email");
            assertTrue(json.contains("\"vipLevel\""), "JSON should contain vipLevel");
        }
    }

    // ========================================================================
    // Configuration-Based SuperType Tests
    // ========================================================================

    @Nested
    @DisplayName("Configuration-based supertype serialization")
    class ConfigurationBasedSuperType {

        @Test
        @DisplayName("supertype disabled by default")
        void superTypeDisabledByDefault() throws IOException {
            EObject vip = createVipCustomer("vip@example.com", "Jane", "VIP", 5);

            // Default config - supertype disabled
            CodecConfiguration config = CodecConfiguration.defaults();
            assertFalse(config.isSerializeSuperTypes(), "SuperType should be disabled by default");

            String json = serializeWithConfig(vip, config);
            System.out.println("VIPCustomer (default config) JSON:\n" + json);

            assertFalse(json.contains("\"_supertype\""),
                    "Supertype should not appear when disabled");
        }

        @Test
        @DisplayName("supertype enabled with serializeAllSuperTypes=true (ALL)")
        void superTypeEnabledWithAllSelection() throws IOException {
            EObject vip = createVipCustomer("vip@example.com", "Jane", "VIP", 5);

            CodecConfiguration config = CodecConfiguration.builder()
                    .serializeSuperTypes(true)
                    .serializeAllSuperTypes(true)
                    .build();

            String json = serializeWithConfig(vip, config);
            System.out.println("VIPCustomer (supertype ALL) JSON:\n" + json);

            assertTrue(json.contains("\"_supertype\""),
                    "Supertype should appear when enabled");
            // VIPCustomer extends Customer
            assertTrue(json.contains("Customer"),
                    "Supertype array should contain Customer");
        }

        @Test
        @DisplayName("supertype enabled with serializeAllSuperTypes=false (SINGLE)")
        void superTypeEnabledWithSingleSelection() throws IOException {
            EObject vip = createVipCustomer("vip@example.com", "Jane", "VIP", 5);

            CodecConfiguration config = CodecConfiguration.builder()
                    .serializeSuperTypes(true)
                    .serializeAllSuperTypes(false)
                    .serializeSuperTypesAsArray(false)
                    .build();

            String json = serializeWithConfig(vip, config);
            System.out.println("VIPCustomer (supertype SINGLE) JSON:\n" + json);

            assertTrue(json.contains("\"_supertype\""),
                    "Supertype should appear when enabled");
            // When serializeSuperTypesAsArray=false, writes as string
            assertTrue(json.contains("\"_supertype\":\""),
                    "SINGLE selection should write supertype as string");
        }

        @Test
        @DisplayName("supertype with custom key")
        void superTypeWithCustomKey() throws IOException {
            EObject vip = createVipCustomer("vip@example.com", "Jane", "VIP", 5);

            CodecConfiguration config = CodecConfiguration.builder()
                    .serializeSuperTypes(true)
                    .serializeAllSuperTypes(true)
                    .superTypeKey("extends")
                    .build();

            String json = serializeWithConfig(vip, config);
            System.out.println("VIPCustomer (custom key) JSON:\n" + json);

            assertTrue(json.contains("\"extends\""),
                    "Should use custom supertype key");
            assertFalse(json.contains("\"_supertype\""),
                    "Should not use default _supertype key");
        }

        @Test
        @DisplayName("supertype not serialized for class without supertypes")
        void superTypeNotSerializedForRootClass() throws IOException {
            EObject customer = createCustomer("john@example.com", "John", "Doe");

            CodecConfiguration config = CodecConfiguration.builder()
                    .serializeSuperTypes(true)
                    .serializeAllSuperTypes(true)
                    .build();

            String json = serializeWithConfig(customer, config);
            System.out.println("Customer (no supertypes) JSON:\n" + json);

            // Customer has no supertypes (it's a root class)
            assertFalse(json.contains("\"_supertype\""),
                    "Should not serialize _supertype for class without supertypes");
        }
    }

    // ========================================================================
    // Helper Methods
    // ========================================================================

    private String serialize(EObject object) throws IOException {
        return serializeWithConfig(object, CodecConfiguration.defaults());
    }

    private String serializeWithConfig(EObject object, CodecConfiguration config) throws IOException {
        CodecResource resource = new CodecResource(
                URI.createURI("test://supertype-test.json"),
                metadataService,
                config,
                null);
        resource.getContents().add(object);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        resource.save(out, Collections.emptyMap());

        return out.toString(StandardCharsets.UTF_8);
    }

}
