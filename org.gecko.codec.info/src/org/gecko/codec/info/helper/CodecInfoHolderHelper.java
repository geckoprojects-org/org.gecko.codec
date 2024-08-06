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

import org.eclipse.emf.ecore.EObject;
import org.gecko.codec.info.codecinfo.CodecInfoFactory;
import org.gecko.codec.info.codecinfo.CodecInfoHolder;
import org.gecko.codec.info.codecinfo.InfoType;


/**
 * 
 * @author ilenia
 * @since Jul 31, 2024
 */
public class CodecInfoHolderHelper {

	public static CodecInfoHolder createCodecInfoHolderForType(InfoType codecType) {
		CodecInfoHolder codecInfoHolder = CodecInfoFactory.eINSTANCE.createCodecInfoHolder();
		codecInfoHolder.setInfoType(codecType);

		switch(codecType) {
		case FEATURE:
			break;
		case IDENTITY:
			codecInfoHolder.getReaders().add(CodecIOHelper.DEFAULT_ID_VALUE_READER);
			codecInfoHolder.getWriters().add(CodecIOHelper.IDFIELD_VALUE_WRITER);			
			codecInfoHolder.getWriters().add(CodecIOHelper.DEFAULT_ID_VALUE_WRITER);
			break;
		case OBJECT:
			break;
		case OTHER:
			break;
		case REFERENCE:
			codecInfoHolder.getReaders().add(CodecIOHelper.DEFAULT_ECLASS_READER);
			codecInfoHolder.getWriters().add(CodecIOHelper.URIS_WRITER);		
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

}
