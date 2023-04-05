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
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.gecko.codec.CodecConstants;
import org.gecko.codec.CodecException;
import org.gecko.codec.CodecType;
import org.gecko.codec.CodecValue;
import org.gecko.codec.codecs.BasicCodecValue;
import org.gecko.codec.descriptors.ClassifierDescriptor;
import org.gecko.codec.descriptors.FeatureDescriptor;
import org.gecko.codec.descriptors.PackageDescriptor;
import org.gecko.codec.helper.PropertiesHelper;
import org.gecko.emf.codec.cache.EMFDescriptorCache;

/**
 * Descriptor for an {@link EStructuralFeature}
 * @author Mark Hoffmann
 * @since 24.03.2023
 */
public class EFeatureDescriptor extends EMFCodecDescriptor<EStructuralFeature> implements FeatureDescriptor<EStructuralFeature, EObject> {

	/**
	 * Creates a new instance.
	 */
	public EFeatureDescriptor(EStructuralFeature feature, Map<String, Object> properties, EMFDescriptorCache cache) {
		super(feature, properties, cache);
		getCache().addFeatureDescriptor(this);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.descriptors.FeatureDescriptor#isReference()
	 */
	@Override
	public boolean isReference() {
		requireNonNull(getSource());
		return getSource() instanceof EReference;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.descriptors.FeatureDescriptor#getReferenceURI()
	 */
	@Override
	public String getReferenceURI(CodecValue<?> value) {
		requireNonNull(getSource());
		return null;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.descriptors.FeatureDescriptor#isContainmentReference()
	 */
	@Override
	public boolean isContainmentReference() {
		requireNonNull(getSource());
		if (getSource() instanceof EReference) {
			return ((EReference)getSource()).isContainment();
		}
		return false;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.descriptors.FeatureDescriptor#isIdFeature()
	 */
	@Override
	public boolean isIdFeature() {
		requireNonNull(getSource());
		return getSource() instanceof EAttribute && ((EAttribute)getSource()).isID();
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.descriptors.FeatureDescriptor#isMultiValue()
	 */
	@Override
	public boolean isMultiValue() {
		requireNonNull(getSource());
		return getSource().getUpperBound() > 1 || getSource().getUpperBound() < 0;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.descriptors.FeatureDescriptor#isNullable()
	 */
	@Override
	public boolean isNullable() {
		requireNonNull(getSource());
		return getSource().getLowerBound() == 0;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.descriptors.FeatureDescriptor#isTransient()
	 */
	@Override
	public boolean isTransient() {
		requireNonNull(getSource());
		return getSource().isTransient();
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.descriptors.FeatureDescriptor#isDerived()
	 */
	@Override
	public boolean isDerived() {
		requireNonNull(getSource());
		return getSource().isDerived();
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.descriptors.FeatureDescriptor#getType()
	 */
	@Override
	public CodecType getType() {
		requireNonNull(getSource());
		requireNonNull(getTypeProvider());
		return getTypeProvider().getTypeFrom(getSource().getEType()).orElse(null);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.descriptors.FeatureDescriptor#isProjected()
	 */
	@Override
	public boolean isProjected() {
		List<Object> projectionFeatures = PropertiesHelper.getObjectPlusValue(getProperties(), CodecConstants.PROJECTION_FEATURES_PROPERTY_KEY);
		if (nonNull(projectionFeatures) && !testProjection(projectionFeatures, false)) {
			return false;
		}
		List<Object> projectionNotFeatures = PropertiesHelper.getObjectPlusValue(getProperties(), CodecConstants.PROJECTION_NOT_FEATURES_PROPERTY_KEY);
		if (nonNull(projectionNotFeatures) && !testProjection(projectionNotFeatures, true)) {
			return false;
		}
		return true;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.descriptors.FeatureDescriptor#isCompatible(java.lang.Object)
	 */
	@Override
	public boolean isCompatible(CodecValue<?> value) {
		if (isNull(getSource())) {
			return false;
		}
		if (isNull(value) || isNull(value.getValue())) {
			return isNullable();
		} else if (getSource().getEType().equals(EcorePackage.Literals.ESTRING)) {
			return true;
		} else if (getType().equals(value.getCodecType())) {
			return true;
		} else if(nonNull(getSource().getEType().getInstanceClassName())) {
			return getSource().getEType().getInstanceClassName().equals(value.getClass().getName());
		}
		return false;
	}


	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.descriptors.FeatureDescriptor#getClassifier()
	 */
	@Override
	public ClassifierDescriptor<?, EObject> getClassifier() {
		requireNonNull(getSource());
		requireNonNull(getCache());
		return getCache().getClassifierDescriptor(getSource().getEContainingClass()).orElse(null);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.descriptors.FeatureDescriptor#getPackage()
	 */
	@Override
	public PackageDescriptor<EPackage, EObject> getPackage() {
		requireNonNull(getSource());
		requireNonNull(getCache());
		return getCache().getPackageDescriptor(getSource().getEContainingClass().getEPackage()).orElse(null);
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.gecko.emf.codec.EFeatureDescriptor#getValue(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public CodecValue<?> getValue(EObject target) throws CodecException {
		requireNonNull(getSource());
		if (isNull(target)) {
			return BasicCodecValue.NULL_VALUE;
		}
		return BasicCodecValue.createCodecValue(target.eGet(getSource()), getTypeProvider());
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.descriptors.FeatureDescriptor#setValue(org.eclipse.emf.ecore.EObject, org.gecko.codec.CodecValue)
	 */
	@Override
	public void setValue(EObject target, CodecValue<?> value) throws CodecException {
		requireNonNull(getSource());
		requireNonNull(value);
		if (isCompatible(value) && nonNull(value.getValue())) {
			target.eSet(getSource(), value.getValue());
		} else {
			throw new AssertionError(String.format("The value type '%s' is not compatible with the feature type '%s'", value.getClass().getName(), getSource().getEType().getInstanceClassName()));
		}
	}

	/**
	 * Test if this feature is in the projection list for 'invert=<code>false</code>'.
	 * For positive projection it returns <code>true</code> if the feature is in the list.
	 * If it is not in the projection list, <code>false</code>, will returned.
	 * The parameter 'invert=<code>true</code>' tests for negative projection. So the method returns <code>true</code>, if
	 * this feature is not in the list and <code>false</code>, if it is in the notPorjection list and otherwise <code>true</code>
	 * @param projectionList list to check for {@link EStructuralFeature}
	 * @param notProject <code>true</code> for projection NOT and <code>false</code> for positive projection
	 * @return the value like explained above
	 */
	private boolean testProjection(List<Object> projectionList, boolean notProject) {
		Stream<Object> projected = projectionList.
				stream().
				filter(EStructuralFeature.class::isInstance);
		if (projected.count() > 0) {
			Optional<Object> p = projectionList.
					stream().
					filter(EStructuralFeature.class::isInstance).
					filter(f->getSource().equals(f)).
					findAny();
			return notProject ? p.isEmpty() : p.isPresent();
		}
		return !notProject;
	}

}
