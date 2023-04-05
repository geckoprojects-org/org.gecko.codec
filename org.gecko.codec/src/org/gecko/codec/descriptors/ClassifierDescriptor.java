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

import java.util.List;
import java.util.Optional;

/**
 * Type / classifier related descriptors
 * @param <P> package type
 * @param <C> classifier type
 * @param <F> feature type
 * @author Mark Hoffmann
 * @since 20.03.2023
 */
public interface ClassifierDescriptor<C, T> extends CodecDescriptor<C> {
	
	/**
	 * Returns <code>true</code>, if this classifier represents a simple data type
	 * @return <code>true</code>, if this classifier represents a simple data type
	 */
	boolean isSimpleType();
	
	/**
	 * Returns the package this classifier belongs to. Must not be <code>null</code>
	 * @return the package this classifier belongs to. Must not be <code>null</code>
	 */
	PackageDescriptor<?, T> getPackage();
	
	/**
	 * Returns all attributes and references of this classifier or an empty {@link List}
	 * @return all attributes and references of this classifier or an empty {@link List}
	 */
	List<FeatureDescriptor<?, T>> getFeatures();
	
	/**
	 * Returns all super-classifier in the inheritance order or an empty {@link List}
	 * @return all super-classifier in the inheritance order or an empty {@link List}
	 */
	List<ClassifierDescriptor<C, T>> getSuperClassifiers();
	
	/**
	 * Returns an optional for a feature that contains an id for the instance, if available
	 * @return an optional for a feature that contains an id for the instance, if available
	 */
	Optional<FeatureDescriptor<?, T>> getIdFeature();
	
	/**
	 * Creates an instance of this classifier
	 * @return an instance of this classifier
	 */
	T createInstance();
	
}
