/**
 * Copyright (c) 2012 - 2024 Data In Motion and others.
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
package org.gecko.codec.jpa.punit.configurator.impl;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.jpa.EntityManagerFactoryBuilder;

import jakarta.persistence.EntityManagerFactory;

/**
 * 
 * @author ilenia
 * @since Nov 20, 2024
 */
@Component
public class TestComponent {

	@Reference(target = "(" + EntityManagerFactoryBuilder.JPA_UNIT_NAME + "=Codec)")
	EntityManagerFactory emf;
	
//	@Reference(target = "(" + EntityManagerFactoryBuilder.JPA_UNIT_NAME + "=person)")
//	EntityManagerFactory emf2;
	
	@Activate
	public void activate() {
		System.out.println("Test!");
	}
	
}
