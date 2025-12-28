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
package org.eclipse.fennec.codec.v2.util;

import org.eclipse.fennec.codec.metadata.provider.CodecAspectProvider;
import org.eclipse.fennec.model.metadata.api.MetadataService;
import org.eclipse.fennec.model.metadata.service.MetadataServiceImpl;

/**
 * Factory for creating MetadataService instances configured for codec.v2.
 * <p>
 * This factory ensures that the {@link CodecAspectProvider} is registered
 * with the MetadataService so that EAnnotations from Ecore models are
 * properly parsed into codec aspects.
 * </p>
 *
 * @author Mark Hoffmann
 * @since 2025-12-17
 */
public final class MetadataServiceFactory {

    private MetadataServiceFactory() {
        // Utility class
    }

    /**
     * Creates a new MetadataService with the CodecAspectProvider registered.
     * <p>
     * This is the recommended way to create a MetadataService for codec.v2
     * when not running in an OSGi environment. In OSGi, the MetadataService
     * is typically provided via DS and the CodecAspectProvider is registered
     * separately.
     * </p>
     *
     * @return a new MetadataService configured for codec serialization
     */
    public static MetadataService create() {
        MetadataServiceImpl service = new MetadataServiceImpl();
        service.registerAspectProvider(new CodecAspectProvider());
        return service;
    }

    /**
     * Registers the CodecAspectProvider with an existing MetadataService.
     * <p>
     * Use this method when you have an existing MetadataService and need
     * to add codec aspect support to it.
     * </p>
     *
     * @param service the MetadataService to configure
     * @return the same MetadataService for chaining
     */
    public static MetadataService configureForCodec(MetadataService service) {
        service.registerAspectProvider(new CodecAspectProvider());
        return service;
    }
}
