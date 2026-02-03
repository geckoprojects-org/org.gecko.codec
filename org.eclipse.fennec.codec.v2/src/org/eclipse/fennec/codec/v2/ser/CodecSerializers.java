/**
 * Copyright (c) 2012 - 2026 Data In Motion and others.
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
package org.eclipse.fennec.codec.v2.ser;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fennec.codec.v2.config.effective.EffectiveCodecConfig;

import com.fasterxml.jackson.annotation.JsonFormat.Value;

import tools.jackson.databind.BeanDescription;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.SerializationConfig;
import tools.jackson.databind.ValueSerializer;
import tools.jackson.databind.ser.Serializers;

/**
 * Registry of serializers for EMF codec v2.
 * <p>
 * This class provides serializers for EMF types to Jackson. It is registered
 * with the ObjectMapper through the codec module.
 * </p>
 * <p>
 * Uses {@link EffectiveCodecConfig} which contains pre-merged configuration
 * from all sources (load/save options, factory defaults, module config, model aspects).
 * </p>
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#9-jackson-module-integration">Spec 9: Jackson Module Integration</a>
 * @author Mark Hoffmann
 * @since 2025-12-16
  * @deprecated Migrated to {@link org.eclipse.fennec.codec.deser.CodecSerializers}.
 */
@Deprecated
public class CodecSerializers extends Serializers.Base {

    private final CodecEObjectSerializer eObjectSerializer;

    /**
     * Creates a new CodecSerializers with the effective codec configuration.
     *
     * @param config the effective (pre-merged) codec configuration
     */
    public CodecSerializers(EffectiveCodecConfig config) {
        this.eObjectSerializer = new CodecEObjectSerializer(config);
    }

    @Override
    public ValueSerializer<?> findSerializer(
            SerializationConfig config,
            JavaType type,
            BeanDescription.Supplier beanDescSupplier,
            Value formatOverrides) {

        // Check if the type is an EObject
        if (type.isTypeOrSubTypeOf(EObject.class)) {
            return eObjectSerializer;
        }

        return null;
    }
}
