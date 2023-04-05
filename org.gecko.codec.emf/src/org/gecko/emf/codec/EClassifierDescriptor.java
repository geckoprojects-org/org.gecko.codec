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

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.gecko.codec.descriptors.ClassifierDescriptor;
import org.gecko.codec.descriptors.FeatureDescriptor;
import org.gecko.codec.descriptors.PackageDescriptor;
import org.gecko.emf.codec.cache.EMFDescriptorCache;

/**
 * Abstract {@link EClassifier} {@link ClassifierDescriptor} for EMF as base for {@link EClass}, {@link EDataType} and {@link EEnum}
 * @author Mark Hoffmann
 * @since 23.03.2023
 */
public abstract class EClassifierDescriptor<T extends EClassifier> extends EMFCodecDescriptor<T> implements ClassifierDescriptor<T, EObject> {
	
	@SuppressWarnings("unchecked")
	public EClassifierDescriptor(T classifier, Map<String, Object> properties, EMFDescriptorCache cache) {
		super(classifier, properties, cache);
		getCache().addClassifierDescriptor((ClassifierDescriptor<EClassifier, EObject>)this);
	}
	
	/**
	 * Creates a new instance.
	 */
	public EClassifierDescriptor(T classifier, Map<String, Object> properties) {
		this(classifier, properties, new EMFDescriptorCache());
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.descriptors.ClassifierDescriptor#getPackage()
	 */
	@Override
	public PackageDescriptor<EPackage, EObject> getPackage() {
		requireNonNull(getSource());
		return getOrCreatePackageDescriptor(getSource().getEPackage());
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.descriptors.ClassifierDescriptor#getFeatures()
	 */
	@Override
	public List<FeatureDescriptor<?, EObject>> getFeatures() {
		return Collections.emptyList();
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.descriptors.ClassifierDescriptor#getSuperClassifiers()
	 */
	@Override
	public List<ClassifierDescriptor<T, EObject>> getSuperClassifiers() {
		requireNonNull(getSource());
		return Collections.emptyList();
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.descriptors.ClassifierDescriptor#getIdFeature()
	 */
	@Override
	public Optional<FeatureDescriptor<?, EObject>> getIdFeature() {
		return Optional.empty();
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.descriptors.ClassifierDescriptor#createInstance()
	 */
	@Override
	public EObject createInstance() {
		return null;
	}

}
