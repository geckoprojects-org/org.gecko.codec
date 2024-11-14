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
package org.gecko.codec.constants;

/**
 * 
 * @author ilenia
 * @since Nov 12, 2024
 */
public interface CodecResourceOptions {
	
	/** CODEC_ROOT_OBJECT 
	 * This option should be passed with {@link Resource#load} to specify the root object.
	 * This is mandatory if the type information is not available in the serialized document.
	 * */
	String CODEC_ROOT_OBJECT = "codec.root.object";

}
