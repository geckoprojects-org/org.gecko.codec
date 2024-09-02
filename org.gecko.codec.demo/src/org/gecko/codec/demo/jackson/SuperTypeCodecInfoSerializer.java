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
package org.gecko.codec.demo.jackson;

import static java.util.Objects.nonNull;

import java.io.IOException;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emfcloud.jackson.databind.EMFContext;
import org.gecko.codec.info.CodecModelInfo;
import org.gecko.codec.info.codecinfo.CodecInfoHolder;
import org.gecko.codec.info.codecinfo.CodecValueWriter;
import org.gecko.codec.info.codecinfo.EClassCodecInfo;
import org.gecko.codec.info.codecinfo.InfoType;
import org.gecko.codec.info.codecinfo.TypeInfo;
import org.gecko.codec.jackson.CodecGeneratorBase;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.impl.StringArraySerializer;

/**
 * 
 * @author ilenia
 * @since Aug 22, 2024
 */
public class SuperTypeCodecInfoSerializer implements CodecInfoSerializer {
	
	private final JsonSerializer<String[]> serializer = StringArraySerializer.instance;
	private final static Logger LOGGER = Logger.getLogger(SuperTypeCodecInfoSerializer.class.getName());
	
	private CodecModule codecModule;
	private CodecModelInfo codecModelInfoService;
	private EClassCodecInfo eObjCodecInfo;
	private TypeInfo typeCodecInfo;
	
	public SuperTypeCodecInfoSerializer(final CodecModule codecMoule, final CodecModelInfo codecModelInfoService, 
			final EClassCodecInfo eObjCodecInfo, final TypeInfo typeCodecInfo) {
		this.codecModule = codecMoule;
		this.codecModelInfoService = codecModelInfoService;
		this.eObjCodecInfo = eObjCodecInfo;
		this.typeCodecInfo = typeCodecInfo;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.demo.jackson.CodecInfoSerializer#serialize(org.eclipse.emf.ecore.EObject, com.fasterxml.jackson.core.JsonGenerator, com.fasterxml.jackson.databind.SerializerProvider)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void serialize(EObject rootObj, JsonGenerator gen, SerializerProvider provider) throws IOException {
		EMFContext.setParent(provider, rootObj);
		EClass objectType = rootObj.eClass();
		EReference containment = rootObj.eContainmentFeature();
		// check for our implementation with additional callbacks 
		CodecGeneratorBase cgb = null;
		if (gen instanceof CodecGeneratorBase) {
			cgb = (CodecGeneratorBase) gen;
		}

		if (isRoot(rootObj) || shouldSaveType(objectType, containment.getEReferenceType(), containment)) {
			CodecInfoHolder holder = codecModelInfoService.getCodecInfoHolderByType(InfoType.REFERENCE);
			CodecValueWriter<EClass, String[]> writer = holder.getWriterByName("URIS_WRITER");

			String[] values = writer.writeValue(rootObj.eClass(), provider);

			if (nonNull(values) && values.length > 0) {
				gen.writeFieldName(codecModule.getSuperTypeKey());
				/*
				 * We use our custom callback, if possible.
				 */
				if (!codecModule.isSerializeSuperTypesAsArray()) {
					if (nonNull(cgb) && cgb.canWriteSuperTypes()) {
						cgb.writeSuperTypes(values);
					} else {
						if (gen.canWriteTypeId()) {
							gen.writeTypeId(values);
						} else {
							//							Comma separated string...?
							StringBuilder sb = new StringBuilder();
							for(String s : values) {
								sb.append(s);
								sb.append(",");
							}
							sb.replace(sb.length()-1, sb.length(), "");
							gen.writeString(sb.toString());
						}

					}        		 
				} else if (codecModule.isSerializeArrayBatched() && 
						nonNull(cgb) && 
						cgb.canWriteOneShotArray()) {
					cgb.writeArray(values, 0, values.length, String.class);
				} else {
					serializer.serialize(values, gen, provider);
				}
			}
		}
	}
	
	private boolean shouldSaveType(final EClass objectType, final EClass featureType, final EStructuralFeature feature) {
		return objectType != featureType && objectType != EcorePackage.Literals.EOBJECT;
	}
	
	private boolean isRoot(final EObject bean) {
		EObject container = bean.eContainer();
		Resource.Internal resource = ((InternalEObject) bean).eDirectResource();

		return container == null || resource != null && resource != ((InternalEObject) container).eDirectResource();
	}

}