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
package org.gecko.codec.info;

/**
 * 
 * @author ilenia
 * @since Aug 6, 2024
 */
public interface ObjectMapperOptions {
	
	String OBJ_MAPPER_DATE_FORMAT = "obj.mapper.date.format";
	
	String OBJ_MAPPER_LOCALE = "obj.mapper.locale";
	
	String OBJ_MAPPER_TIME_ZONE = "obj.mapper.time.zone";
	
	String OBJ_MAPPER_INDENT_OUTPUT = "obj.mapper.indent.output";
	
	String OBJ_MAPPER_SERIALIZATION_FEATURES_WITH = "obj.mapper.serialization.features.with";
	
	String OBJ_MAPPER_SERIALIZATION_FEATURES_WITHOUT = "obj.mapper.serialization.features.without";

}
