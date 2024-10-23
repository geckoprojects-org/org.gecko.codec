///*******************************************************************************
// * Copyright (c) 2019-2021 Guillaume Hillairet and others.
// *
// * This program and the accompanying materials are made available under the
// * terms of the Eclipse Public License v. 2.0 which is available at
// * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
// * available at https://opensource.org/licenses/MIT.
// *
// * SPDX-License-Identifier: EPL-2.0 OR MIT
// *******************************************************************************/
//
//package org.gecko.codec.jackson.databind.property;
//
//import static java.util.Objects.nonNull;
//import static org.gecko.codec.jackson.module.CodecFeature.OPTION_SERIALIZE_ARRAY_BATCHED;
//import static org.gecko.codec.jackson.module.CodecFeature.OPTION_SERIALIZE_SUPERTYPE;
//import static org.gecko.codec.jackson.module.CodecFeature.OPTION_SERIALIZE_SUPERTYPE_AS_ARRAY;
//
//import java.io.IOException;
//
//import org.eclipse.emf.ecore.EClass;
//import org.eclipse.emf.ecore.EObject;
//import org.eclipse.emf.ecore.EReference;
//import org.eclipse.emf.ecore.EStructuralFeature;
//import org.eclipse.emf.ecore.EcorePackage;
//import org.eclipse.emf.ecore.InternalEObject;
//import org.eclipse.emf.ecore.resource.Resource;
//import org.eclipse.emf.ecore.util.EcoreUtil;
//import org.eclipse.emfcloud.jackson.databind.property.EObjectProperty;
//import org.eclipse.emfcloud.jackson.utils.ValueReader;
//import org.eclipse.emfcloud.jackson.utils.ValueWriter;
//import org.gecko.codec.jackson.CodecGeneratorBase;
//import org.gecko.codec.jackson.databind.annotations.CodecSuperTypeInfo;
//
//import com.fasterxml.jackson.core.JsonGenerator;
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.core.JsonToken;
//import com.fasterxml.jackson.databind.DeserializationContext;
//import com.fasterxml.jackson.databind.JsonDeserializer;
//import com.fasterxml.jackson.databind.JsonSerializer;
//import com.fasterxml.jackson.databind.SerializerProvider;
//import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
//import com.fasterxml.jackson.databind.ser.impl.StringArraySerializer;
//
//public class CodecSuperTypeProperty extends EObjectProperty {
//
//	private final JsonSerializer<String[]> serializer = StringArraySerializer.instance;
//	private final JsonDeserializer<String> deserializer = StringDeserializer.instance;
//
//	private final ValueReader<String, EClass> valueReader;
//	private final ValueWriter<EClass, String[]> valueWriter;
//	private final int features;
//
//	public CodecSuperTypeProperty(final CodecSuperTypeInfo info, final int features) {
//		super(info.getProperty());
//
//		this.valueReader = info.getValueReader();
//		this.valueWriter = info.getValueWriter();
//		this.features = features;
//	}
//
//	@Override
//	public void serialize(final EObject bean, final JsonGenerator jg, final SerializerProvider provider)
//			throws IOException {
//		// No need to serialize supertype
//		if (!OPTION_SERIALIZE_SUPERTYPE.enabledIn(features)) {
//			return;
//		}
//		EClass objectType = bean.eClass();
//		EReference containment = bean.eContainmentFeature();
//
//		// check for our implementation with additional callbacks 
//		CodecGeneratorBase cgb = null;
//		if (jg instanceof CodecGeneratorBase) {
//			cgb = (CodecGeneratorBase) jg;
//		}
//
//		if (isRoot(bean) || shouldSaveType(objectType, containment.getEReferenceType(), containment)) {
//			String[] values = valueWriter.writeValue(bean.eClass(), provider);
//
//			if (nonNull(values) && values.length > 0) {
//				jg.writeFieldName(getFieldName());
//				/*
//				 * We use out custom callback, if possible.
//				 */
//				if (!OPTION_SERIALIZE_SUPERTYPE_AS_ARRAY.enabledIn(features)) {
//					if (nonNull(cgb) && cgb.canWriteSuperTypes()) {
//						cgb.writeSuperTypes(values);
//					} else {
//						jg.writeTypeId(values);
//					}        		 
//				} else if (OPTION_SERIALIZE_ARRAY_BATCHED.enabledIn(features) && 
//						nonNull(cgb) && 
//						cgb.canWriteOneShotArray()) {
//					cgb.writeArray(values, 0, values.length, String.class);
//				} else {
//					serializer.serialize(values, jg, provider);
//				}
//			}
//		}
//	}
//
//	private boolean isRoot(final EObject bean) {
//		EObject container = bean.eContainer();
//		Resource.Internal resource = ((InternalEObject) bean).eDirectResource();
//
//		return container == null || resource != null && resource != ((InternalEObject) container).eDirectResource();
//	}
//
//	@Override
//	public EObject deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException {
//		if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {
//			jp.nextToken();
//		}
//
//		return create(deserializer.deserialize(jp, ctxt), ctxt);
//	}
//
//	public EObject create(final String value, final DeserializationContext ctxt) {
//		EClass eClass = valueReader.readValue(value, ctxt);
//
//		return eClass != null ? EcoreUtil.create(eClass) : null;
//	}
//
//	@Override
//	public void deserializeAndSet(final JsonParser jp, final EObject current, final DeserializationContext ctxt,
//			final Resource resource)
//					throws IOException {
//		// do nothing
//	}
//
//	private boolean shouldSaveType(final EClass objectType, final EClass featureType, final EStructuralFeature feature) {
//		return objectType != featureType && objectType != EcorePackage.Literals.EOBJECT;
//	}
//}
