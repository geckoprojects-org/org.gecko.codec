/**
 * Copyright (c) 2012 - 2023 Data In Motion and others.
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
package org.gecko.codec;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Describes a codec type. Whenever a Java class is not sufficient to describe
 * a type
 * @author mark
 * @since 21.03.2023
 */
@ProviderType
public interface CodecType {
	
	/**
	 * Tests if the given object is of the codec type
	 * @param typeToTest the object to be tested 
	 * @return <code>true</code>m if the object is valid for this codec
	 */
	boolean isType(Object typeToTest);
	
	/**
	 * Returns the code identifier
	 * @return the codec indentifier
	 */
	List<String> getIdentifier();

}
