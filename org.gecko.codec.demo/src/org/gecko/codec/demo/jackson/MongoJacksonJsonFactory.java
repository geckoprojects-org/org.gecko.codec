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

import org.gecko.codec.demo.database.MongoDatabase;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.fasterxml.jackson.core.JsonFactory;

/**
 * 
 * @author mark
 * @since 02.08.2024
 */
@Component(service = JsonFactory.class, property = "codecType=MONGO", immediate = true)
public class MongoJacksonJsonFactory extends JsonFactory {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	@Reference
	private MongoDatabase database;

}
