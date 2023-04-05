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

/**
 * Descriptor for package related elements
 * @param <P> package type
 * @author Mark Hoffmann
 * @since 20.03.2023
 */
public interface PackageDescriptor<P, T> extends CodecDescriptor<P> {
	
	/**
	 * Returns all classifiers of the package or an empty list
	 * @return all classifiers of the package or an empty list
	 */
	List<ClassifierDescriptor<?, T>> getClassifiers();
	
	/**
	 * Returns all sub-packages of the package or an empty list
	 * @return all sub-packages of the package or an empty list
	 */
	List<PackageDescriptor<P, T>> getSubPackages();
	
	/**
	 * Returns the parent package or <code>null</code>, if it the root package
	 * @return the parent package or <code>null</code>, if it the root package
	 */
	PackageDescriptor<P, T> getParent();
	
}
