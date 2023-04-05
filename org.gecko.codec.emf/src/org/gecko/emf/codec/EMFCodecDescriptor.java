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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.gecko.codec.CodecConstants;
import org.gecko.codec.descriptors.ClassifierDescriptor;
import org.gecko.codec.descriptors.CodecDescriptor;
import org.gecko.codec.descriptors.FeatureDescriptor;
import org.gecko.codec.descriptors.PackageDescriptor;
import org.gecko.emf.codec.cache.EMFCodecTypeProvider;
import org.gecko.emf.codec.cache.EMFDescriptorCache;
import org.gecko.emf.codec.helper.EMFHelper;

/**
 * Default descriptor implementation for EMF
 * @author Mark Hoffmann
 * @since 28.03.2023
 */
public abstract class EMFCodecDescriptor<T extends ENamedElement> implements CodecDescriptor<T> {
	
	private final Map<String, Object> properties = new HashMap<>();
	private final EMFDescriptorCache cache;
	private final T element;
	
	/**
	 * Creates a new instance.
	 */
	public EMFCodecDescriptor(T element, Map<String, Object> properties, EMFDescriptorCache cache) {
		requireNonNull(element);
		requireNonNull(cache);
		this.element = element;
		this.cache = cache;
		if (properties != null) {
			this.properties.putAll(properties);
		}
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.descriptors.CodecDescriptor#getNamespace()
	 */
	@Override
	public String getNamespace() {
		requireNonNull(getSource());
		return EcoreUtil.getURI(getSource()).toString();
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.descriptors.CodecDescriptor#getName()
	 */
	@Override
	public String getName() {
		requireNonNull(getSource());
		return element.getName();
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.descriptors.CodecDescriptor#getProcessedName()
	 */
	@Override
	public String getProcessedName() {
		requireNonNull(getSource());
		requireNonNull(getProperties());
		String name = getName();
		String annotation = null;
		String source = (String) getProperties().get(CodecConstants.ANNOTATION_PROPERTY_SOURCE_KEY);
		if (nonNull(source)) {
			String nameKey = (String) getProperties().getOrDefault(CodecConstants.ANNOTATION_PROPERTY_NAME_KEY, CodecConstants.ANNOTATION_NAME_KEY);
			annotation = EMFHelper.getElementAnnotationValue(element, source, nameKey);
		}
		if (isNull(annotation)) {
			annotation = EMFHelper.getElementCodecAnnotationName(element);
		}
		if (isNull(annotation)) {
			annotation = EMFHelper.getElementEMDAnnotationName(element);
		}
		if (nonNull(annotation)) {
			name = annotation;
		}
		return name;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.descriptors.CodecDescriptor#getProperties()
	 */
	@Override
	public Map<String, Object> getProperties() {
		return properties;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.descriptors.CodecDescriptor#getSource()
	 */
	@Override
	public T getSource() {
		return element;
	}
	
	/**
	 * Returns the EMF codec type provider 
	 * @return the EMF codec type provider
	 */
	EMFCodecTypeProvider getTypeProvider() {
		return cache.getTypeProvider();
	}
	
	/**
	 * Returns the cache.
	 * @return the cache
	 */
	EMFDescriptorCache getCache() {
		return cache;
	}
	
	/**
	 * Looks in the cache to find the {@link PackageDescriptor}. If it is not found a new one will be created and added to the cache.
	 * @param epackage the package to get or create the {@link PackageDescriptor} from
	 * @return the cached or a new {@link PackageDescriptor}
	 */
	PackageDescriptor<EPackage, EObject> getOrCreatePackageDescriptor(EPackage epackage) {
		requireNonNull(epackage);
		return getOrCreateDescriptor(epackage, getCache()::getPackageDescriptor, ()->new EPackageDescriptor(epackage, getProperties(), getCache()), getCache()::addPackageDescriptor);
	}
	
	/**
	 * Looks in the cache to find the {@link FeatureDescriptor}. If it is not found a new one will be created and added to the cache.
	 * @param efeature the {@link EStructuralFeature} to get or create the {@link FeatureDescriptor} from
	 * @return the cached or a new {@link FeatureDescriptor}
	 */
	FeatureDescriptor<EStructuralFeature, EObject> getOrCreateFeatureDescriptor(EStructuralFeature efeature) {
		requireNonNull(efeature);
		return getOrCreateDescriptor(efeature, getCache()::getFeatureDescriptor, ()->new EFeatureDescriptor(efeature, getProperties(), getCache()), getCache()::addFeatureDescriptor);
	}
	
	/**
	 * Looks in the cache to find the {@link ClassifierDescriptor}. If it is not found a new one will be created and added to the cache.
	 * @param eclass the {@link EClass} to get or create the {@link ClassifierDescriptor} from
	 * @return the cached or a new {@link ClassifierDescriptor}
	 */
	@SuppressWarnings("unchecked")
	<V extends EClassifier> ClassifierDescriptor<V, EObject> getOrCreateClassifierDescriptor(V eclass) {
		requireNonNull(eclass);
		requireNonNull(getCache());
		return getOrCreateDescriptor(eclass, (ec)->getCache().getClassifierDescriptor(ec).map(d->(ClassifierDescriptor<V ,EObject>)d), ()->(ClassifierDescriptor<V, EObject>)new EClassDescriptor((EClass)eclass, getProperties(), getCache()), (cd)->getCache().addClassifierDescriptor((ClassifierDescriptor<EClassifier, EObject>)cd));
	}
	
	/**
	 * Looks in the cache to find the {@link CodecDe}. If it is not found a new one will be created and added to the cache.
	 * @param epackage the package to get or create the {@link PackageDescriptor} from
	 * @return the cached or a new {@link PackageDescriptor}
	 */
	static <V, D extends CodecDescriptor<V>> D getOrCreateDescriptor(V source, Function<V, Optional<D>> cacheLookupFunction, Supplier<D> createDesciptorSupplier, Consumer<D> addToCacheConsumer) {
		requireNonNull(source);
		requireNonNull(cacheLookupFunction);
		Optional<D> cached = cacheLookupFunction.apply(source);
		return cached.orElse(createDescriptor(source, true, createDesciptorSupplier, addToCacheConsumer));
	}
	
	/**
	 * Creates a {@link CodecDescriptor} instance and caches it, if the doCache parameter is set to <code>true</code>.
	 * Otherwise the descriptor will not be cached.
	 * @param source the instance to create a descriptor from
	 * @param doCache set to <code>true</code>, to cache the descriptor
	 * @return the created {@link CodecDescriptor}
	 */
	static <V, D extends CodecDescriptor<V>> D createDescriptor(V source, boolean doCache, Supplier<D> createDescriptorSupplier, Consumer<D> addToCacheConsumer) {
		requireNonNull(source);
		requireNonNull(createDescriptorSupplier);
		D descriptor = createDescriptorSupplier.get();
		if (doCache) {
			requireNonNull(addToCacheConsumer);
			addToCacheConsumer.accept(descriptor);
		}
		return descriptor;
	}

}
