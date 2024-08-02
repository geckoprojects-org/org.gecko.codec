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

/**
 * 
 * @author ilenia
 * @since Aug 2, 2024
 */
public @interface CodecModuleConfig {
	
	boolean useId() default true;
		
	boolean useIdField() default true;
	
	boolean idOnTop() default true;
	
	boolean useTypeField() default true;
	
	boolean serializeIdField() default false;
	
	boolean idFeatureAsPrimaryKey() default true;
	
	String defaultIdKey() default "_id";
	
	
	boolean serializeType() default true;
	
	boolean serializeSuperTypes() default false;
	
	boolean serailizeSuperTypesAsArray() default true;
	
	String defaultTypeKey() default "_type";
	
	
	String defaultRefKey() default "$ref";

}
