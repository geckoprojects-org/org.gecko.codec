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
 * @since Aug 13, 2024
 */
public @interface CodecObjectMapperConfig {
	
	String dateFormat() default "yyyy-MM-dd'T'HH:mm:ss";
	
	String locale() default "en-US";
	
	String timeZone() default "Europe/Berlin";
	
	boolean indentOutput() default true;
	
	String[] enabledGeneratorFeatures() default {};
	
	String[] disabledGeneratorFeatures() default {};

	String[] enabledMapperFeatures() default {};
	
	String[] disabledMapperFeatures() default {};
}
