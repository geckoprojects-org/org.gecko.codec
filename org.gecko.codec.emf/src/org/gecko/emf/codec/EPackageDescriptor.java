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
package org.gecko.emf.codec;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.gecko.codec.descriptors.ClassifierDescriptor;
import org.gecko.codec.descriptors.PackageDescriptor;
import org.gecko.emf.codec.cache.EMFDescriptorCache;


/**
 * 
 * @author mark
 * @since 24.03.2023
 */
public class EPackageDescriptor extends EMFCodecDescriptor<EPackage> implements PackageDescriptor<EPackage, EObject> {
	
	/**
	 * Creates a new instance.
	 */
	public EPackageDescriptor(EPackage ePackage, Map<String, Object> properties, EMFDescriptorCache cache) {
		super(ePackage, properties, cache);
		getCache().addPackageDescriptor(this);
	}
	
	/**
	 * Creates a new instance.
	 */
	public EPackageDescriptor(EPackage ePackage, Map<String, Object> properties) {
		this(ePackage, properties, new EMFDescriptorCache());
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.descriptors.CodecDescriptor#getNamespace()
	 */
	@Override
	public String getNamespace() {
		requireNonNull(getSource());
		return getSource().getNsURI();
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.descriptors.PackageDescriptor2#getClassifiers()
	 */
	@Override
	public List<ClassifierDescriptor<?, EObject>> getClassifiers() {
		requireNonNull(getSource());
		return getSource().
				getEClassifiers().
				stream().
				map(this::getOrCreateClassifierDescriptor).
				collect(Collectors.toList());
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.descriptors.PackageDescriptor2#getSubPackages()
	 */
	@Override
	public List<PackageDescriptor<EPackage, EObject>> getSubPackages() {
		requireNonNull(getSource());
		return getSource().
				getESubpackages().
				stream().
				map(this::getOrCreatePackageDescriptor).
				collect(Collectors.toList());
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.descriptors.PackageDescriptor2#getParent()
	 */
	@Override
	public PackageDescriptor<EPackage, EObject> getParent() {
		requireNonNull(getSource());
		EPackage parent = getSource().getESuperPackage();
		return isNull(parent) ? null :  getOrCreatePackageDescriptor(parent);
	}
	
}
