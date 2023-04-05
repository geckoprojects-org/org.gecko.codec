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
package org.gecko.emf.codec.cache;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.gecko.codec.descriptors.BasicDescriptorCache;

/**
 * EMF Descriptor cache implementation that contains the {@link EMFCodecTypeProvider}
 * @author Mark Hoffmann
 * @since 03.04.2023
 */
public class EMFDescriptorCache extends BasicDescriptorCache<EPackage, EClassifier, EStructuralFeature, EObject> {

	private final EMFCodecTypeProvider typeProvider;
	
	/**
	 * Creates a new instance.
	 */
	public EMFDescriptorCache() {
		typeProvider = new EMFCodecTypeProvider();
	}

	/**
	 * Creates a new instance.
	 * @param typeProvider a 
	 */
	public EMFDescriptorCache(EMFCodecTypeProvider typeProvider) {
		this.typeProvider = typeProvider == null ? new EMFCodecTypeProvider() : typeProvider;
	}
	
	/**
	 * Returns the typeProvider.
	 * @return the typeProvider
	 */
	public EMFCodecTypeProvider getTypeProvider() {
		return typeProvider;
	}
}
