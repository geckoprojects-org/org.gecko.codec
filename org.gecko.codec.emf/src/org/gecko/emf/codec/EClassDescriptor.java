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
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.gecko.codec.descriptors.ClassifierDescriptor;
import org.gecko.codec.descriptors.FeatureDescriptor;
import org.gecko.emf.codec.cache.EMFDescriptorCache;

/**
 * Descriptor for {@link EClass}
 * @author Mark Hoffmann
 * @since 23.03.2023
 */
public class EClassDescriptor extends EClassifierDescriptor<EClass> {


	public EClassDescriptor(EClass classifier, Map<String, Object> properties, EMFDescriptorCache cache) {
		super(classifier, properties, cache);
	}
	
	public EClassDescriptor(EClass classifier, Map<String, Object> properties) {
		this(classifier, properties, new EMFDescriptorCache());
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.descriptors.ClassifierDescriptor#isSimpleType()
	 */
	@Override
	public boolean isSimpleType() {
		return false;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.descriptors.ClassifierDescriptor#getFeatures()
	 */
	@Override
	public List<FeatureDescriptor<?, EObject>> getFeatures() {
		requireNonNull(getSource());
		return getSource().
				getEAllStructuralFeatures().
				stream().
				map(this::getOrCreateFeatureDescriptor).
				collect(Collectors.toList());
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.descriptors.ClassifierDescriptor#getSuperClassifiers()
	 */
	@Override
	public List<ClassifierDescriptor<EClass, EObject>> getSuperClassifiers() {
		requireNonNull(getSource());
		return getSource().
				getEAllSuperTypes().
				stream().
				map(this::getOrCreateClassifierDescriptor).
				collect(Collectors.toList());
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.descriptors.ClassifierDescriptor#getIdFeature()
	 */
	@Override
	public Optional<FeatureDescriptor<?, EObject>> getIdFeature() {
		requireNonNull(getSource());
		requireNonNull(getCache());
		requireNonNull(getProperties());
		EClass eClass = getSource();
		EAttribute idAttribute = eClass.getEIDAttribute();
		return isNull(idAttribute) ? Optional.empty() : Optional.of(getOrCreateFeatureDescriptor(idAttribute));
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.descriptors.ClassifierDescriptor#createInstance()
	 */
	@Override
	public EObject createInstance() {
		return EcoreUtil.create(getSource());
	}
	
}
