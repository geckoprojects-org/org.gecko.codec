/**
 * Copyright (c) 2012 - 2025 Data In Motion and others.
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
package org.eclipse.fennec.codec.gemini;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//	import tools.jackson.datatype.jsr310.JavaTimeModule;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * 
 * @author ilenia
 * @since Jun 27, 2025
 */
//--- 4. DefaultJacksonExtender.java (Implementation of JacksonExtenderService) ---
//This class implements the JacksonExtenderService. It dynamically
//discovers and applies JacksonModuleProvider and ObjectMapperCustomizer services.

import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;

/**
 * Default implementation of the JacksonExtenderService.
 * This component tracks JacksonModuleProvider and ObjectMapperCustomizer services
 * and configures ObjectMapper instances based on the registered services.
 */
@Component(service = JacksonExtenderService.class, immediate = true)
public class DefaultJacksonExtender implements JacksonExtenderService {

	private final Map<String, ObjectMapper> objectMappers = new ConcurrentHashMap<>();
	private final Map<String, JacksonModuleProvider> moduleProviders = new ConcurrentHashMap<>();
	private final Map<String, ObjectMapperCustomizer> customizerProviders = new ConcurrentHashMap<>(); // New map for customizers
	private BundleContext bundleContext;

	@Activate
	protected void activate(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
		System.out.println("DefaultJacksonExtender activated.");
		// Initialize the default ObjectMapper upon activation
		initializeObjectMapper(null);
	}

	@Deactivate
	protected void deactivate() {
		this.bundleContext = null;
		this.objectMappers.clear();
		this.moduleProviders.clear();
		this.customizerProviders.clear(); // Clear customizers
		System.out.println("DefaultJacksonExtender deactivated.");
	}

	/**
	 * Dynamically track JacksonModuleProvider services.
	 * When a new module provider is registered, we reconfigure the affected ObjectMappers.
	 * @param provider The JacksonModuleProvider service being added.
	 */
	@Reference(
			service = JacksonModuleProvider.class,
			cardinality = ReferenceCardinality.MULTIPLE,
			policy = ReferencePolicy.DYNAMIC,
			bind = "bindJacksonModuleProvider",
			unbind = "unbindJacksonModuleProvider"
			)
	protected void bindJacksonModuleProvider(JacksonModuleProvider provider) {
		String name = provider.getName();
		moduleProviders.put(name != null ? name : "default", provider);
		System.out.println("JacksonModuleProvider bound: " + (name != null ? name : "default"));
		// Re-initialize relevant ObjectMapper(s) when a new module is bound
		objectMappers.remove(name != null ? name : "default"); // Invalidate cache
		initializeObjectMapper(name);
	}

	protected void unbindJacksonModuleProvider(JacksonModuleProvider provider) {
		String name = provider.getName();
		moduleProviders.remove(name != null ? name : "default");
		System.out.println("JacksonModuleProvider unbound: " + (name != null ? name : "default"));
		// Re-initialize relevant ObjectMapper(s) when a module is unbound
		objectMappers.remove(name != null ? name : "default"); // Invalidate cache
		initializeObjectMapper(name);
	}

	/**
	 * Dynamically track ObjectMapperCustomizer services. (NEW)
	 * When a new customizer is registered, we reconfigure the affected ObjectMappers.
	 * @param customizer The ObjectMapperCustomizer service being added.
	 */
	@Reference(
			service = ObjectMapperCustomizer.class,
			cardinality = ReferenceCardinality.MULTIPLE,
			policy = ReferencePolicy.DYNAMIC,
			bind = "bindObjectMapperCustomizer",
			unbind = "unbindObjectMapperCustomizer"
			)
	protected void bindObjectMapperCustomizer(ObjectMapperCustomizer customizer) {
		String name = customizer.getName();
		customizerProviders.put(name != null ? name : "default", customizer);
		System.out.println("ObjectMapperCustomizer bound: " + (name != null ? name : "default"));
		// Re-initialize relevant ObjectMapper(s) when a new customizer is bound
		objectMappers.remove(name != null ? name : "default"); // Invalidate cache
		initializeObjectMapper(name);
	}

	protected void unbindObjectMapperCustomizer(ObjectMapperCustomizer customizer) {
		String name = customizer.getName();
		customizerProviders.remove(name != null ? name : "default");
		System.out.println("ObjectMapperCustomizer unbound: " + (name != null ? name : "default"));
		// Re-initialize relevant ObjectMapper(s) when a customizer is unbound
		objectMappers.remove(name != null ? name : "default"); // Invalidate cache
		initializeObjectMapper(name);
	}

	private synchronized ObjectMapper initializeObjectMapper(String name) {
		// Start with the builder for Jackson 3
		JsonMapper.Builder builder = JsonMapper.builder();

		// Apply general default configurations
		builder.configure(SerializationFeature.INDENT_OUTPUT, true);
		//	        builder.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		//	        builder.addModule(new JavaTimeModule()); // Always include JavaTimeModule for convenience

		// Apply customizer configurations from registered providers (NEW)
		for (Map.Entry<String, ObjectMapperCustomizer> entry : customizerProviders.entrySet()) {
			String customizerName = entry.getKey();
			ObjectMapperCustomizer customizer = entry.getValue();
			if (name == null && customizerName.equals("default")) {
				// Apply to default mapper builder
				customizer.customize(builder);
				System.out.println("Applied default customizer.");
			} else if (name != null && name.equals(customizerName)) {
				// Apply to named mapper builder
				customizer.customize(builder);
				System.out.println("Applied named customizer '" + name + "'.");
			}
		}



		// Apply modules from registered providers
		for (Map.Entry<String, JacksonModuleProvider> entry : moduleProviders.entrySet()) {
			String moduleName = entry.getKey();
			JacksonModuleProvider provider = entry.getValue();
			if (name == null && moduleName.equals("default")) {
				// Apply to default mapper
				builder.addModule(provider.getModule());
				System.out.println("Applied default module: " + provider.getModule().getModuleName());
			} else if (name != null && name.equals(moduleName)) {
				// Apply to named mapper
				builder.addModule(provider.getModule());
				System.out.println("Applied named module '" + name + "': " + provider.getModule().getModuleName());
			}
		}
		// Build the ObjectMapper instance
		ObjectMapper mapper = builder.build();
		objectMappers.put(name != null ? name : "default", mapper);
		return mapper;
	}

	@Override
	public ObjectMapper getObjectMapper() {
		return objectMappers.computeIfAbsent("default", this::initializeObjectMapper);
	}

	@Override
	public ObjectMapper getObjectMapper(String name) {
		if (name == null || name.trim().isEmpty()) {
			return getObjectMapper();
		}
		return objectMappers.computeIfAbsent(name, this::initializeObjectMapper);
	}
}

