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
 * @param <P> Package type
 * @param <C> Classifier type
 * @param <T> Type classifier type
 * @param <F> Feature type
 * @param <O> Object / Instance type
 * Cache for searching by name
 * @author Mark Hoffmann
 * @since 24.03.2023
 */
public interface NamespaceDescriptorCache<P, C, F, T> extends DescriptorCache<P, C, F, T> {
	
	/**
	 * Returns the namespace for this cache, which is usually the package namespace
	 * @return the namespace for this cache, which is usually the package namespace
	 */
	String getNamespace();
	
	/**
	 * Returns the {@link PackageDescriptor} by package name. 
	 * @param packageName the package name
	 * @return the {@link PackageDescriptor}
	 */
	Optional<PackageDescriptor<P, T>> getPackageDescriptorByName(String packageName);
	
	/**
	 * Returns the {@link ClassifierDescriptor} by classifier name. 
	 * @param classifierName the classifier name
	 * @return the {@link ClassifierDescriptor}
	 */
	Optional<ClassifierDescriptor<C, T>> getClassifierDescriptorByName(String classifierName);
	
	/**
	 * Returns the {@link FeatureDescriptor} by feature name. 
	 * @param featureName the feature name
	 * @return the {@link FeatureDescriptor}
	 */
	Optional<FeatureDescriptor<F, T>> getFeatureDescriptorByName(String featureName);
	

}
