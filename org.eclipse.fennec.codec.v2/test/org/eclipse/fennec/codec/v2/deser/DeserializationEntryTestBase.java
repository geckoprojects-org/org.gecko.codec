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
package org.eclipse.fennec.codec.v2.deser;

import java.io.IOException;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.fennec.model.metadata.utils.EcoreHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import tools.jackson.core.JsonParser;
import tools.jackson.core.json.JsonFactory;
import tools.jackson.databind.ObjectMapper;

/**
 * Base class for deserialization entry tests.
 * <p>
 * Provides common setup for loading the test ecore model and accessing
 * EClasses, EAttributes, and EReferences for testing deserialization entries.
 * Uses real EMF objects and real JSON parsing for more realistic testing.
 * </p>
 */
abstract class DeserializationEntryTestBase {

    protected static final String TEST_ECORE = "test-deserialization.ecore";

    protected EcoreHelper ecoreHelper;
    protected EPackage testPackage;

    // EClasses
    protected EClass personClass;
    protected EClass addressClass;

    // EAttributes on Person
    protected EAttribute nameAttribute;
    protected EAttribute ageAttribute;
    protected EAttribute activeAttribute;
    protected EAttribute scoreAttribute;
    protected EAttribute tagsAttribute;

    // EReferences on Person
    protected EReference addressRef;       // containment, single
    protected EReference managerRef;       // non-containment, single
    protected EReference colleaguesRef;    // non-containment, multi

    // Jackson objects for real parsing
    protected ObjectMapper objectMapper;

    @BeforeEach
    void setUpBase() throws IOException {
        ecoreHelper = new EcoreHelper(DeserializationEntryTestBase.class);
        testPackage = ecoreHelper.loadEcore(TEST_ECORE);

        // Load EClasses
        personClass = ecoreHelper.getEClass(testPackage, "Person");
        addressClass = ecoreHelper.getEClass(testPackage, "Address");

        // Load EAttributes
        nameAttribute = (EAttribute) ecoreHelper.getFeature(personClass, "name");
        ageAttribute = (EAttribute) ecoreHelper.getFeature(personClass, "age");
        activeAttribute = (EAttribute) ecoreHelper.getFeature(personClass, "active");
        scoreAttribute = (EAttribute) ecoreHelper.getFeature(personClass, "score");
        tagsAttribute = (EAttribute) ecoreHelper.getFeature(personClass, "tags");

        // Load EReferences
        addressRef = (EReference) ecoreHelper.getFeature(personClass, "address");
        managerRef = (EReference) ecoreHelper.getFeature(personClass, "manager");
        colleaguesRef = (EReference) ecoreHelper.getFeature(personClass, "colleagues");

        // Create real Jackson objects
        objectMapper = new ObjectMapper(new JsonFactory());
    }

    @AfterEach
    void tearDownBase() {
        ecoreHelper.releaseAll();
    }

    /**
     * Creates a new Person EObject instance.
     *
     * @return a new Person instance
     */
    protected EObject createPerson() {
        return testPackage.getEFactoryInstance().create(personClass);
    }

    /**
     * Creates a new Address EObject instance.
     *
     * @return a new Address instance
     */
    protected EObject createAddress() {
        return testPackage.getEFactoryInstance().create(addressClass);
    }

    /**
     * Creates a new Address EObject with the given street and city.
     *
     * @param street the street address
     * @param city the city
     * @return a new Address instance with the values set
     */
    protected EObject createAddress(String street, String city) {
        EObject address = createAddress();
        address.eSet(addressClass.getEStructuralFeature("street"), street);
        address.eSet(addressClass.getEStructuralFeature("city"), city);
        return address;
    }

    /**
     * Creates a DeserializationState for the given EClass.
     *
     * @param eClass the EClass for the state
     * @return a new DeserializationState with the EClass set
     */
    protected DeserializationState createState(EClass eClass) {
        DeserializationState state = new DeserializationState(null);
        state.setResolvedEClass(eClass);
        return state;
    }

    /**
     * Creates a DeserializationState with an EObject already created.
     *
     * @param eObject the EObject
     * @return a new DeserializationState with the EObject set
     */
    protected DeserializationState createStateWithObject(EObject eObject) {
        DeserializationState state = new DeserializationState(null);
        state.setResolvedEClass(eObject.eClass());
        state.setEObject(eObject);
        return state;
    }

    /**
     * Creates a JsonParser from a JSON string and advances to the first value token.
     * <p>
     * For simple values like "hello", 42, true, etc., it parses the value directly.
     * The parser is positioned at the value token after this call.
     * </p>
     *
     * @param json the JSON string to parse
     * @return a JsonParser positioned at the first value
     */
    protected JsonParser createParser(String json) {
        try {
            JsonParser parser = objectMapper.createParser(json);
            parser.nextToken(); // Advance to first token
            return parser;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create parser for: " + json, e);
        }
    }
}
