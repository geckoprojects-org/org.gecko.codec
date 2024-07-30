/**
 * Copyright (c) 2012 - 2024 Data In Motion and others.
 * All rights reserved. 
 * 
 * This program and the accompanying materials are made available under the terms of the 
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Data In Motion - initial API and implementation
 */
package org.gecko.codec.mongo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xml.namespace.XMLNamespacePackage;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.emfcloud.jackson.resource.JsonResourceFactory;
import org.gecko.emf.osgi.example.model.basic.BasicFactory;
import org.gecko.emf.osgi.example.model.basic.BasicPackage;
import org.gecko.emf.osgi.example.model.basic.Contact;
import org.gecko.emf.osgi.example.model.basic.ContactContextType;
import org.gecko.emf.osgi.example.model.basic.ContactType;
import org.gecko.emf.osgi.example.model.basic.GenderType;
import org.gecko.emf.osgi.example.model.basic.Person;
import org.junit.jupiter.api.Test;
import org.osgi.framework.BundleException;
import org.osgi.framework.InvalidSyntaxException;

/**
 * 
 * @author grune
 * @since Apr 9, 2024
 */
public class PlainJsonEmfTest {
	@Test
	public void testCreateContainmentSingle2()
			throws BundleException, InvalidSyntaxException, IOException, InterruptedException {
		ResourceSet resourceSet = new ResourceSetImpl();
		initResourceSet(resourceSet);
		
		// create contacts
		Contact c1 = BasicFactory.eINSTANCE.createContact();
		c1.setContext(ContactContextType.PRIVATE);
		c1.setType(ContactType.SKYPE);
		c1.setValue("charles-brown");
		Contact c2 = BasicFactory.eINSTANCE.createContact();
		c2.setContext(ContactContextType.WORK);
		c2.setType(ContactType.EMAIL);
		c2.setValue("mark.hoffmann@tests.de");
		
		Resource resource = resourceSet.createResource(URI.createURI("test.json"));
		
		Person person = BasicFactory.eINSTANCE.createPerson();
		person.setFirstName("Mark");
		person.setLastName("Hoffmann");
		person.setGender(GenderType.MALE);
		person.getContact().add(EcoreUtil.copy(c1));
		person.getContact().add(EcoreUtil.copy(c2));
		resource.getContents().add(person);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		resource.save(os, null);
		
		System.out.println(new String(os.toByteArray()));
		
		ByteArrayInputStream bais = new ByteArrayInputStream(os.toByteArray());
		
		resource.getContents().clear();
		resource.unload();
		
		Resource findResource = resourceSet.createResource(URI.createURI("test_load.json"));
		findResource.load(bais, null);

		// get the person
		assertNotNull(findResource);
		assertFalse(findResource.getContents().isEmpty());
		assertEquals(1, findResource.getContents().size());
		
		// doing some object checks
		Person p = (Person) findResource.getContents().get(0);
		assertEquals("Mark", p.getFirstName());
		assertEquals("Hoffmann", p.getLastName());
		assertEquals(GenderType.MALE, p.getGender());
		assertEquals(2, p.getContact().size());
		assertEquals("charles-brown", p.getContact().get(0).getValue());
		assertEquals("mark.hoffmann@tests.de", p.getContact().get(1).getValue());
	}
	private void initResourceSet(ResourceSet resourceSet) {
		resourceSet.getPackageRegistry().put(EcorePackage .eNS_URI, EcorePackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(XMLNamespacePackage.eNS_URI, XMLNamespacePackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(XMLTypePackage.eNS_URI, XMLTypePackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(BasicPackage.eNS_URI, BasicPackage.eINSTANCE);
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("json", new JsonResourceFactory());
	}
}
