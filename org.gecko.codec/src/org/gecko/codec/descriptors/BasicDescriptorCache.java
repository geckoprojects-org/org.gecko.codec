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

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Basic implementation of the {@link DescriptorCache}
 * @author Mark Hoffmann
 * @since 24.03.2023
 */
public class BasicDescriptorCache<P, C, F, T> implements NamespaceDescriptorCache<P, C, F, T> {

	private final Map<P, PackageDescriptor<P,T>> packageCache = new ConcurrentHashMap<>();
	private final Map<String, P> packageNameCache = new ConcurrentHashMap<>();
	private final Map<C, ClassifierDescriptor<C, T>> classifierCache = new ConcurrentHashMap<>();
	private final Map<String, C> classifierNameCache = new ConcurrentHashMap<>();
	private final Map<F, FeatureDescriptor<F, T>> featureCache = new ConcurrentHashMap<>();
	private final Map<String, F> featureNameCache = new ConcurrentHashMap<>();
	private String namespace;

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.descriptors.NamespaceDescriptorCache#getNamespace()
	 */
	@Override
	public String getNamespace() {
		return namespace;
	}
	
	void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.descriptors.DescriptorCache#getPackageDescriptor(java.lang.Object)
	 */
	@Override
	public Optional<PackageDescriptor<P, T>> getPackageDescriptor(P p) {
		if (nonNull(p) && packageCache.containsKey(p)) {
			return Optional.of(packageCache.get(p));
		}
		return Optional.empty();
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.descriptors.NamespaceDescriptorCache#getPackageDescriptorByName(java.lang.String)
	 */
	@Override
	public Optional<PackageDescriptor<P, T>> getPackageDescriptorByName(String packageName) {
		if (nonNull(packageName) && packageNameCache.containsKey(packageName)) {
			return Optional.of(packageCache.get(packageNameCache.get(packageName)));
		}
		return Optional.empty();
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.descriptors.DescriptorCache#addPackageDescriptor(org.gecko.codec.descriptors.PackageDescriptor)
	 */
	@Override
	public void addPackageDescriptor(PackageDescriptor<P, T> descriptor) {
		addPackageDescriptor(descriptor, false);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.descriptors.DescriptorCache#addPackageDescriptor(org.gecko.codec.descriptors.PackageDescriptor, boolean)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void addPackageDescriptor(PackageDescriptor<P, T> descriptor, boolean recursive) {
		requireNonNull(descriptor);
		requireNonNull(descriptor.getSource());
		requireNonNull(descriptor.getName());
		packageCache.putIfAbsent(descriptor.getSource(), descriptor);
		packageNameCache.putIfAbsent(descriptor.getName(), descriptor.getSource());
		if (recursive && nonNull(descriptor.getClassifiers())) {
			descriptor.getClassifiers().forEach(c->this.addClassifierDescriptor((ClassifierDescriptor<C, T>) c, recursive));
		}
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.descriptors.DescriptorCache#getClassifierDescriptor(java.lang.Object)
	 */
	@Override
	public Optional<ClassifierDescriptor<C, T>> getClassifierDescriptor(C classifier) {
		if (nonNull(classifier) && classifierCache.containsKey(classifier)) {
			return Optional.of(classifierCache.get(classifier));
		}
		return Optional.empty();
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.descriptors.NamespaceDescriptorCache#getClassifierDescriptorByName(java.lang.String)
	 */
	@Override
	public Optional<ClassifierDescriptor<C, T>> getClassifierDescriptorByName(String classifierName) {
		if (nonNull(classifierName) && classifierNameCache.containsKey(classifierName)) {
			return Optional.of(classifierCache.get(classifierNameCache.get(classifierName)));
		}
		return Optional.empty();
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.descriptors.DescriptorCache#addClassifierDescriptor(org.gecko.codec.descriptors.ClassifierDescriptor)
	 */
	@Override
	public void addClassifierDescriptor(ClassifierDescriptor<C, T> descriptor) {
		addClassifierDescriptor(descriptor, false);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.descriptors.DescriptorCache#addClassifierDescriptor(org.gecko.codec.descriptors.ClassifierDescriptor, boolean)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void addClassifierDescriptor(ClassifierDescriptor<C, T> descriptor, boolean recursive) {
		requireNonNull(descriptor);
		requireNonNull(descriptor.getSource());
		requireNonNull(descriptor.getName());
		classifierCache.putIfAbsent(descriptor.getSource(), descriptor);
		classifierNameCache.putIfAbsent(descriptor.getName(), descriptor.getSource());
		if (recursive && nonNull(descriptor.getFeatures())) {
			descriptor.getFeatures().forEach(f->this.addFeatureDescriptor((FeatureDescriptor<F, T>) f));
		}
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.descriptors.DescriptorCache#getFeatureDescriptor(java.lang.Object)
	 */
	@Override
	public Optional<FeatureDescriptor<F, T>> getFeatureDescriptor(F feature) {
		if (nonNull(feature) && featureCache.containsKey(feature)) {
			return Optional.of(featureCache.get(feature));
		}
		return Optional.empty();
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.descriptors.NamespaceDescriptorCache#getFeatureDescriptorByName(java.lang.String)
	 */
	@Override
	public Optional<FeatureDescriptor<F, T>> getFeatureDescriptorByName(String featureName) {
		if (nonNull(featureName) && featureNameCache.containsKey(featureName)) {
			return Optional.of(featureCache.get(featureNameCache.get(featureName)));
		}
		return Optional.empty();
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.descriptors.DescriptorCache#addFeatureDescriptor(org.gecko.codec.descriptors.FeatureDescriptor)
	 */
	@Override
	public void addFeatureDescriptor(FeatureDescriptor<F, T> descriptor) {
		requireNonNull(descriptor);
		requireNonNull(descriptor.getSource());
		requireNonNull(descriptor.getName());
		featureCache.putIfAbsent(descriptor.getSource(), descriptor);
		featureNameCache.putIfAbsent(descriptor.getName(), descriptor.getSource());
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.descriptors.DescriptorCache#clear()
	 */
	@Override
	public void clear() {
		synchronized (packageCache) {
			packageCache.clear();
			packageNameCache.clear();
		}
		synchronized (classifierCache) {
			classifierCache.clear();
			classifierNameCache.clear();
		}
		synchronized (featureCache) {
			featureCache.clear();
			featureNameCache.clear();
		}
	}

}
