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
package org.gecko.codec;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.eclipse.emf.ecore.EObject;

import tools.jackson.core.Base64Variant;

/**
 * 
 * @author mark
 * @since 12.01.2024
 */
public interface CodecGenerator {
	
	void doStartWriteRootEObject(EObject object) ;
	void doEndWriteRootEObject(EObject object) ;

	void doWriteType(int index, String fieldName, Object object) ;
	void doWriteSuperTypes(int index, String fieldName, String[] superTypes) ;
	void doWriteObjectId(int index, String fieldName, Object object) ;

	void doStartWriteEObject(int index, String fieldName, EObject object) ;
	void doEndWriteEObject(int index, String fieldName, EObject object) ;

	void doStartWriteArray(int index, String fieldName, Object object) ;
	void doEndWriteArray(int index, String fieldName, Object object) ;
	
	void doWriteString(int index, String fieldName, String value) ;
	void doWriteShort(int index, String fieldName, short value) ;
	void doWriteLong(int index, String fieldName, long value) ;
	void doWriteInt(int index, String fieldName, int value) ;
	void doWriteBigInt(int index, String fieldName, BigInteger value) ;
	void doWriteBigDecimal(int index, String fieldName, BigDecimal value) ;
	void doWriteFloat(int index, String fieldName, float value) ;
	void doWriteDouble(int index, String fieldName, double value) ;
	void doWriteChar(int index, String fieldName, char value) ;
	void doWriteChars(int index, String fieldName, char[] values) ;
	void doWriteBoolean(int index, String fieldName, boolean value) ;
	void doWriteStringNumber(int index, String fieldName, String value) ;
	void doWriteBinary(int index, String fieldName, Base64Variant b64variant,
            byte[] values, int offset, int len) ;
	void doWriteNull(int index, String fieldName) ;
}
