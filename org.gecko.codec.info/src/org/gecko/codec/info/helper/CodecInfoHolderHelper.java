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
package org.gecko.codec.info.helper;

import static org.eclipse.emfcloud.jackson.databind.EMFContext.findEClass;
import static org.eclipse.emfcloud.jackson.databind.EMFContext.getURI;
import static org.gecko.codec.jackson.databind.CodecContext.getURIs;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emfcloud.jackson.databind.EMFContext;
import org.eclipse.emfcloud.jackson.resource.JsonResource;
import org.gecko.codec.info.codecinfo.CodecInfoFactory;
import org.gecko.codec.info.codecinfo.CodecInfoHolder;
import org.gecko.codec.info.codecinfo.CodecValueReader;
import org.gecko.codec.info.codecinfo.CodecValueWriter;
import org.gecko.codec.info.codecinfo.InfoType;
import org.gecko.codec.io.ValueReader;
import org.gecko.codec.io.ValueWriter;


/**
 * 
 * @author ilenia
 * @since Jul 31, 2024
 */
public class CodecInfoHolderHelper {


	private static final ValueReader<Object, String> DEFAULT_ID_VALUE_READER = (context, value) -> value.toString();			

	private static final ValueWriter<EObject, Object> DEFAULT_ID_VALUE_WRITER = (object, context) -> {
		Resource resource = EMFContext.getResource(context, object);
		Object id;
		if (resource instanceof JsonResource) {
			id = ((JsonResource) resource).getID(object);
		} else {
			id = EMFContext.getURI(context, object).fragment();
		}
		return id;
	};

	private static final ValueWriter<EObject, Object> IDFIELD_VALUE_WRITER = (object, context) -> EcoreUtil.getID(object);

	private static final ValueReader<String, EClass> DEFAULT_ECLASS_READER = (value, context) -> findEClass(context, value);

	private static final ValueWriter<EClass, String[]> URIS_WRITER = (value, context) -> getURIs(context, value);

	private static final ValueWriter<EClass, String> URI_WRITER = (value, context) -> getURI(context, value).toString();

	private static final ValueReader<String, EClass> READ_BY_NAME = (value, context) -> EMFContext.findEClassByName(context, value);

	private static final ValueWriter<EClass, String> WRITE_BY_NAME = (value, context) -> value != null ? value.getName() : null;

	private static final ValueReader<String, EClass> READ_BY_CLASS = (value, context) -> EMFContext.findEClassByQualifiedName(context, value);

	private static final ValueWriter<EClass, String> WRITE_BY_CLASS_NAME = (value, context) -> value != null ? value.getInstanceClassName() : null;




	public static CodecInfoHolder createCodecInfoHolderForType(InfoType codecType) {
		CodecInfoHolder codecInfoHolder = CodecInfoFactory.eINSTANCE.createCodecInfoHolder();
		codecInfoHolder.setInfoType(codecType);

		CodecValueReader reader = CodecInfoFactory.eINSTANCE.createCodecValueReader();
		CodecValueWriter writer = CodecInfoFactory.eINSTANCE.createCodecValueWriter();

		switch(codecType) {
		case FEATURE:
			break;
		case IDENTITY:
			reader.setName("DEFAULT_ID_READER");
			reader.setReader(DEFAULT_ID_VALUE_READER);
			codecInfoHolder.getReaders().add(reader);

			writer.setName("DEFAULT_ID_WRITER");
			writer.setWriter(DEFAULT_ID_VALUE_WRITER);
			codecInfoHolder.getWriters().add(writer);

			writer = CodecInfoFactory.eINSTANCE.createCodecValueWriter();
			writer.setName("ID_FIELD_WRITER");
			writer.setWriter(IDFIELD_VALUE_WRITER);
			codecInfoHolder.getWriters().add(writer);			
			break;
		case OBJECT:
			break;
		case OTHER:
			break;
		case REFERENCE:
			reader = CodecInfoFactory.eINSTANCE.createCodecValueReader();
			reader.setName("DEFAULT_ECLASS_READER");
			reader.setReader(DEFAULT_ECLASS_READER);
			codecInfoHolder.getReaders().add(reader);

			writer = CodecInfoFactory.eINSTANCE.createCodecValueWriter();
			writer.setName("URIS_WRITER");
			writer.setWriter(URIS_WRITER);
			codecInfoHolder.getWriters().add(writer);		
			break;
		case TYPE:
			reader = CodecInfoFactory.eINSTANCE.createCodecValueReader();
			reader.setName("DEFAULT_ECLASS_READER");
			reader.setReader(DEFAULT_ECLASS_READER);
			codecInfoHolder.getReaders().add(reader);

			reader = CodecInfoFactory.eINSTANCE.createCodecValueReader();
			reader.setName("READ_BY_NAME");
			reader.setReader(READ_BY_NAME);
			codecInfoHolder.getReaders().add(reader);

			reader = CodecInfoFactory.eINSTANCE.createCodecValueReader();
			reader.setName("READ_BY_CLASS");
			reader.setReader(READ_BY_CLASS);
			codecInfoHolder.getReaders().add(reader);

			writer = CodecInfoFactory.eINSTANCE.createCodecValueWriter();
			writer.setName("URI_WRITER");
			writer.setWriter(URI_WRITER);
			codecInfoHolder.getWriters().add(writer);		

			writer = CodecInfoFactory.eINSTANCE.createCodecValueWriter();
			writer.setName("WRITE_BY_NAME");
			writer.setWriter(WRITE_BY_NAME);
			codecInfoHolder.getWriters().add(writer);		

			writer = CodecInfoFactory.eINSTANCE.createCodecValueWriter();
			writer.setName("WRITE_BY_CLASS_NAME");
			writer.setWriter(WRITE_BY_CLASS_NAME);
			codecInfoHolder.getWriters().add(writer);		
			break;
		default:
			break;

		}
		return codecInfoHolder;
	}

}
