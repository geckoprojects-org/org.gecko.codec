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

import java.util.logging.Logger;

import org.gecko.codec.info.codecinfo.CodecInfoFactory;
import org.gecko.codec.info.codecinfo.CodecInfoHolder;
import org.gecko.codec.info.codecinfo.CodecValueReader;
import org.gecko.codec.info.codecinfo.CodecValueWriter;
import org.gecko.codec.info.codecinfo.InfoType;


/**
 * 
 * @author ilenia
 * @since Jul 31, 2024
 */
public class CodecInfoHolderHelper {
	
	private static final Logger LOGGER = Logger.getLogger(CodecInfoHolderHelper.class.getName());

	public static CodecInfoHolder createCodecInfoHolderForType(InfoType codecType) {
		CodecInfoHolder codecInfoHolder = CodecInfoFactory.eINSTANCE.createCodecInfoHolder();
		codecInfoHolder.setInfoType(codecType);

		switch(codecType) {
		case ATTRIBUTE: case REFERENCE: case OBJECT: case OPERATION: case OTHER:
			break;
		case IDENTITY:
			codecInfoHolder.getReaders().add(CodecIOHelper.DEFAULT_ID_VALUE_READER);
			codecInfoHolder.getWriters().add(CodecIOHelper.IDFIELD_VALUE_WRITER);			
			codecInfoHolder.getWriters().add(CodecIOHelper.DEFAULT_ID_VALUE_WRITER);
			break;
		case SUPER_TYPE:
			codecInfoHolder.getWriters().add(CodecIOHelper.ALL_SUPERTYPE_WRITER);	
			codecInfoHolder.getWriters().add(CodecIOHelper.SINGLE_SUPERTYPE_WRITER);	
			break;
		case TYPE: 
			codecInfoHolder.getReaders().add(CodecIOHelper.DEFAULT_ECLASS_READER);
			codecInfoHolder.getReaders().add(CodecIOHelper.READ_BY_NAME);
			codecInfoHolder.getReaders().add(CodecIOHelper.READ_BY_CLASS);

			codecInfoHolder.getWriters().add(CodecIOHelper.URI_WRITER);		
			codecInfoHolder.getWriters().add(CodecIOHelper.WRITE_BY_NAME);		
			codecInfoHolder.getWriters().add(CodecIOHelper.WRITE_BY_CLASS_NAME);		
			break;
		default:
			break;

		}
		return codecInfoHolder;
	}
	
	public static void addCodecReader(CodecInfoHolder infoHolder, CodecValueReader<?,?> reader) {
		boolean alreadyInThere = false;
		for(CodecValueReader<?,?> r : infoHolder.getReaders()) {
			if(r.getName().equals(reader.getName())) {
				alreadyInThere = true;
				break;
			}
		}
		if(alreadyInThere) {
			LOGGER.warning(String.format("A CodecValueReader with name %s already exists. Change name or yours won't be added.", reader.getName()));
			return;
		}
		infoHolder.getReaders().add(reader);
	}
	
	public static void addCodecWriter(CodecInfoHolder infoHolder, CodecValueWriter<?,?> writer) {
		boolean alreadyInThere = false;
		for(CodecValueWriter<?,?> w : infoHolder.getWriters()) {
			if(w.getName().equals(writer.getName())) {
				alreadyInThere = true;
				break;
			}
		}
		if(alreadyInThere) {
			LOGGER.warning(String.format("A CodecValueWriter with name %s already exists. Change name or yours won't be added.", writer.getName()));
			return;
		}
		infoHolder.getWriters().add(writer);
	}

}
