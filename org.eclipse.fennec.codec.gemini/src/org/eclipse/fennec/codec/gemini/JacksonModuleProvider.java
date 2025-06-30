// --- 2. JacksonModuleProvider.java (OSGi Service Interface) ---
// This interface allows other OSGi bundles to register custom Jackson modules.
// The JacksonExtenderService will discover and apply these modules.

package org.eclipse.fennec.codec.gemini;

import tools.jackson.databind.module.SimpleModule;

/**
 * OSGi service interface for providing Jackson Module instances.
 * Bundles wishing to extend the Jackson ObjectMapper (e.g., with custom serializers/deserializers)
 * should register implementations of this service.
 */
public interface JacksonModuleProvider {

    /**
     * Provides a Jackson Module to be registered with an ObjectMapper.
     *
     * @return A Jackson Module instance.
     */
	SimpleModule getModule();

    /**
     * Returns an optional name for this module provider.
     * If a name is provided, the module will only be applied to ObjectMappers
     * requested with that specific name using getObjectMapper(name).
     * If null or empty, the module will be applied to the default ObjectMapper
     * and any unnamed ObjectMapper instances.
     * @return The name of the module provider, or null for default application.
     */
    String getName();
}