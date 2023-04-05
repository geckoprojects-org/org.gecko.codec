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

import java.util.Optional;

/**
 * Cache to hold descriptors
 * @param <P> Package type
 * @param <C> Classifier type
 * @param <T> Type classifier type
 * @param <F> Feature type
 * @param <O> Object / Instance type
 * @author Mark Hoffmann
 * @since 28.03.2023
 */
public interface DescriptorCache<P, C, F, O> {
	
	/**
	 * Returns the package descriptor for the given package
	 * @param pakkage the package to get the descriptor for
	 * @return {@link Optional} with the {@link PackageDescriptor}
	 */
	Optional<PackageDescriptor<P, O>> getPackageDescriptor(P pakkage);
	
	/**
	 * Adds a {@link PackageDescriptor} to the cache. It is the same like {@link DescriptorCache#addPackageDescriptor(PackageDescriptor, boolean)} with parameter <code>true</code>
	 * @param descriptor the descriptor to add
	 */
	void addPackageDescriptor(PackageDescriptor<P, O> descriptor);
	
	/**
	 * Adds a {@link PackageDescriptor} to the cache 
	 * @param descriptor the descriptor to add
	 * @param recursive set to <code>true</code>, when all sub-sequent classifiers and features should be cached as well. Set to <code>false</code>, when just cache this instance
	 */
	void addPackageDescriptor(PackageDescriptor<P, O> descriptor, boolean recursive);
	
	/**
	 * Returns the classifier descriptor for the given classifier
	 * @param classifier the classifier to get the descriptor for
	 * @return {@link Optional} with the {@link ClassifierDescriptor}
	 */
	Optional<ClassifierDescriptor<C, O>> getClassifierDescriptor(C classifier);
	
	/**
	 * Adds a {@link ClassifierDescriptor} to the cache. It is the same like {@link DescriptorCache#addClassifierDescriptor(ClassifierDescriptor, boolean)} with parameter <code>true</code>
	 * @param descriptor the descriptor to add
	 */
	void addClassifierDescriptor(ClassifierDescriptor<C, O> descriptor);
	
	/**
	 * Adds a {@link ClassifierDescriptor} to the cache 
	 * @param descriptor the descriptor to add
	 * @param recursive set to <code>true</code>, when all sub-sequent features should be cached as well. Set to <code>false</code>, when just cache this instance
	 */
	void addClassifierDescriptor(ClassifierDescriptor<C, O> descriptor, boolean recursive);
	
	/**
	 * Returns the featuer descriptor for the given feature
	 * @param feature the feature to get the descriptor for
	 * @return {@link Optional} with the {@link FeatureDescriptor}
	 */
	Optional<FeatureDescriptor<F, O>> getFeatureDescriptor(F feature);
	
	/**
	 * Adds a {@link FeatureDescriptor} to the cache 
	 * @param descriptor the descriptor to add
	 */
	void addFeatureDescriptor(FeatureDescriptor<F, O> descriptor);
	
	/**
	 * Clears this cache
	 */
	void clear();
	
}
