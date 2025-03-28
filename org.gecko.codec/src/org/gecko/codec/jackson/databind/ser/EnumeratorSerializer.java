/**
 * Copyright (c) 2012 - 2024 Data In Motion and others.
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
package org.gecko.codec.jackson.databind.ser;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emfcloud.jackson.databind.EMFContext;
import org.gecko.codec.info.codecinfo.FeatureCodecInfo;
import org.gecko.codec.jackson.databind.CodecWriteContext;
import org.gecko.codec.jackson.module.CodecModule;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Codec Enumerator serializer
 * 
 * @author ilenia
 * @since Oct 28, 2024
 */
public class EnumeratorSerializer implements CodecInfoSerializer {

	private static final Logger LOGGER = Logger.getLogger(EnumeratorSerializer.class.getName());

	private CodecModule codecModule;
	private FeatureCodecInfo featureCodecInfo;

	public EnumeratorSerializer(final CodecModule codecMoule, final FeatureCodecInfo featureCodecInfo) {
		this.codecModule = codecMoule;
		this.featureCodecInfo = featureCodecInfo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void serialize(EObject rootObj, JsonGenerator gen, SerializerProvider provider) throws IOException {
		if (featureCodecInfo.isIgnore())
			return;
		if (featureCodecInfo.getFeatures().size() != 1) {
			LOGGER.warning(
					"Currently no support for multiple EStructuralFeature in CodecInfoObject which is not a CodecIdInfo");
			return;
		}
		EStructuralFeature feature = (EStructuralFeature) featureCodecInfo.getFeatures().get(0);

		EMFContext.setParent(provider, rootObj);
		EMFContext.setFeature(provider, feature);

		if (gen.getOutputContext() instanceof CodecWriteContext cwt) {
			cwt.setFeature(feature);
		}

		if (rootObj.eIsSet(feature)) {
			if (feature.isMany()) {
				List<Object> values = (List<Object>) rootObj.eGet(feature);
				serializeManyAttribute(values, feature, gen);
			} else {
				Object value = rootObj.eGet(feature);
				serializeSingleAttribute(value, feature, gen);
			}
		} else if (codecModule.isSerializeDefaultValue()) {
			if (feature.isMany()) {
				List<Object> values = (List<Object>) rootObj.eGet(feature);
				serializeManyAttribute(values, feature, gen);
			} else {
				Object value = feature.getDefaultValue();
				serializeSingleAttribute(value, feature, gen);
			}
		}
	}

	private void serializeSingleAttribute(Object value, EStructuralFeature feature, JsonGenerator gen) throws IOException {
		if (value == null && !codecModule.isSerializeNullValue()) {
			return;
		}
		if (value instanceof String str && str.isEmpty() && !codecModule.isSerializeEmptyValue()) {
			return;
		}
		if (codecModule.isUseNamesFromExtendedMetaData()) {
			gen.writeFieldName(featureCodecInfo.getKey());
		} else {
			gen.writeFieldName(feature.getName());
		}
		serializeSingleAttributeValue(value, gen);
	}

	private void serializeSingleAttributeValue(Object value, JsonGenerator gen) throws IOException {

		if (codecModule.isWriteEnumLiterals()) {
			gen.writeString(((Enumerator) value).getLiteral());
		} else {
			gen.writeString(((Enumerator) value).getName());
		}
	}

	private void serializeManyAttribute(List<Object> values, EStructuralFeature feature, JsonGenerator gen) throws IOException {
		if (values.isEmpty() && (!codecModule.isSerializeDefaultValue() || !codecModule.isSerializeEmptyValue()))
			return;
		if (codecModule.isUseNamesFromExtendedMetaData()) {
			gen.writeFieldName(featureCodecInfo.getKey());
		} else {
			gen.writeFieldName(feature.getName());
		}

//		TODO: check serailized array batched here...?
		gen.writeStartArray();
		values.forEach(value -> {
			try {
				serializeSingleAttributeValue(value, gen);
			} catch (IOException e) {
				LOGGER.log(Level.SEVERE,
						String.format("Error while serialization of single attribute value %s.", value), e);
			}
		});
		gen.writeEndArray();
	}
}
