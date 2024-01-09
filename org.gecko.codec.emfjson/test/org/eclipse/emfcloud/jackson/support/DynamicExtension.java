/*
 * Copyright (c) 2019 Guillaume Hillairet and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 *
 */

package org.eclipse.emfcloud.jackson.support;

import org.eclipse.emf.ecore.EPackage;

import static org.eclipse.emf.common.util.URI.createURI;

public class DynamicExtension extends StandardExtension {

	@Override
	protected void before() throws Exception {
		super.before();

		resourceSet.getURIConverter().getURIMap().put(
				createURI("http://emfjson/dynamic/model"),
				createURI("test-data/model/dynamic/model.json"));

		EPackage ePackage = (EPackage) resourceSet.getEObject(
				createURI("http://emfjson/dynamic/model#/"), true);

		resourceSet.getPackageRegistry().put(ePackage.getNsURI(), ePackage);
	}

}

