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
package org.gecko.codec.demo;

import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.gecko.codec.info.CodecModelInfo;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author mark
 * @since 02.08.2024
 */
public class MongoResource extends ResourceImpl {
	
	/**
	 * Creates a new instance.
	 * @param modelInfo
	 * @param mapper
	 */
	public MongoResource(CodecModelInfo modelInfo, ObjectMapper mapper) {
		// TODO Auto-generated constructor stub
	}

}
