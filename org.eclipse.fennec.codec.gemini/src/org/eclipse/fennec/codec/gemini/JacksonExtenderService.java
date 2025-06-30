// --- 1. JacksonExtenderService.java (OSGi Service Interface) ---
// This interface defines the contract for a service that provides ObjectMapper instances.
// Other bundles can consume this service to get a pre-configured ObjectMapper.

package org.eclipse.fennec.codec.gemini;

import tools.jackson.databind.ObjectMapper;

/**
 * OSGi service interface for providing and managing Jackson ObjectMapper instances.
 * Bundles can register their own JacksonModuleProvider services to customize the ObjectMapper
 * provided by this service.
 */
public interface JacksonExtenderService {

    /**
     * Retrieves a default configured Jackson ObjectMapper.
     * The ObjectMapper returned by this method will be configured with any
     * JacksonModuleProvider services and ObjectMapperCustomizer services currently registered
     * in the OSGi service registry that apply to the default configuration.
     *
     * @return A configured ObjectMapper instance.
     */
    ObjectMapper getObjectMapper();

    /**
     * Retrieves a Jackson ObjectMapper configured with a specific name.
     * This allows for scenarios where different configurations of ObjectMapper are needed.
     * The ObjectMapper will be configured with any JacksonModuleProvider services and
     * ObjectMapperCustomizer services currently registered in the OSGi service registry
     * that apply to the specified name.
     *
     * @param name The name of the desired ObjectMapper configuration.
     * @return A configured ObjectMapper instance, or the default one if no named configuration exists.
     */
    ObjectMapper getObjectMapper(String name);
}