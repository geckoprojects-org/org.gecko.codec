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
package org.gecko.codec.descriptors;

import org.gecko.codec.CodecConstants;
import org.gecko.codec.CodecException;
import org.gecko.codec.CodecType;
import org.gecko.codec.CodecValue;

/**
 * Descriptor for attributes and references
 * @author Mark Hoffmann
 * @since 20.03.2023
 */
public interface FeatureDescriptor<F, T> extends CodecDescriptor<F> {
	
	/**
	 * Returns <code>true</code>, if this feature is a reference and no simple type
	 * @return <code>true</code>, if this feature is a reference and no simple type
	 */
	boolean isReference();
	
	/**
	 * Returns the reference URI or <code>null</code>
	 * @param value the value to return the reference uri from
	 * @return the reference URI or <code>null</code>
	 */
	String getReferenceURI(CodecValue<?> value);
	
	/**
	 * Returns <code>true</code>, if the reference value is a contained element
	 * @return <code>true</code>, if the reference value is a contained element
	 */
	boolean isContainmentReference();
	
	/**
	 * Returns <code>true</code>, if this is an id feature
	 * @return <code>true</code>, if this is an id feature
	 */
	boolean isIdFeature();
	
	/**
	 * Returns <code>true</code>, if this feature is a multi-valued feature
	 * @return <code>true</code>, if this feature is a multi-valued feature
	 */
	boolean isMultiValue();
	
	/**
	 * Return <code>true</code>, if this feature can be <code>null</code>
	 * @return <code>true</code>, if this feature can be <code>null</code>
	 */
	boolean isNullable();
	
	/**
	 * Returns <code>true</code>, if the field is transient and should not be stored
	 * @return <code>true</code>, if the field is transient and should not be stored
	 */
	boolean isTransient();
	
	/**
	 * Returns <code>true</code>, if the field is a calculated field
	 * @return <code>true</code>, if the field is a calculated field
	 */
	boolean isDerived();
	
	/**
	 * Returns <code>true</code>, if this feature is marked as projected. This can be configured using
	 * {@link CodecConstants#PROJECTION_FEATURES_PROPERTY_KEY} or {@link CodecConstants#PROJECTION_NOT_FEATURES_PROPERTY_KEY}
	 * @return <code>true</code>, if this feature is marked as projected
	 */
	boolean isProjected();
	
	/**
	 * Returns the type of this feature
	 * @return the type of this feature
	 */
	CodecType getType();
	
	/**
	 * Returns the feature value
	 * @return the feature value
	 * @throws CodecException thrown if the get operation fails
	 */
	CodecValue<?> getValue(T target) throws CodecException;
	
	/**
	 * Sets the value for this feature
	 * @param value the value to set
	 * @throws CodecException thrown if the set operation fails
	 */
	void setValue(T target, CodecValue<?> value) throws CodecException;
	
	/**
	 * Returns <code>true</code>, if the given value is compatible to this feature
	 * @param value the value to check
	 * @return <code>true</code>, if the value is compatible
	 */
	boolean isCompatible(CodecValue<?> value);
	
	/**
	 * Returns the classifier of this feature belongs to. Must not be <code>null</code>.
	 * @return the classifier of this feature belongs to
	 */
	ClassifierDescriptor<?, T> getClassifier();
	
	/**
	 * Returns the package, this feature belongs to. Must not be <code>null</code>.
	 * @return the package, this feature belongs to
	 */
	PackageDescriptor<?, T> getPackage();

}
