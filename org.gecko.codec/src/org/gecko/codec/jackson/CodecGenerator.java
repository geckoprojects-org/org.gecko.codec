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
package org.gecko.codec.jackson;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.eclipse.emf.ecore.EObject;

import com.fasterxml.jackson.core.Base64Variant;

/**
 * 
 * @author mark
 * @since 12.01.2024
 */
public interface CodecGenerator {
	
	void doStartWriteRootEObject(EObject object) throws IOException;
	void doEndWriteRootEObject(EObject object) throws IOException;

	void doWriteType(int index, String fieldName, Object object) throws IOException;
	void doWriteSuperTypes(int index, String fieldName, String[] superTypes) throws IOException;
	void doWriteObjectId(int index, String fieldName, Object object) throws IOException;

	void doStartWriteEObject(int index, String fieldName, EObject object) throws IOException;
	void doEndWriteEObject(int index, String fieldName, EObject object) throws IOException;

	<T> void doWriteArray(T[] array, int offset, int length, Class<T> clazz) throws IOException;
	void doStartWriteArray(int index, String fieldName, Object object) throws IOException;
	void doEndWriteArray(int index, String fieldName, Object object) throws IOException;
	
	void doWriteString(int index, String fieldName, String value) throws IOException;
	void doWriteShort(int index, String fieldName, short value) throws IOException;
	void doWriteLong(int index, String fieldName, long value) throws IOException;
	void doWriteInt(int index, String fieldName, int value) throws IOException;
	void doWriteBigInt(int index, String fieldName, BigInteger value) throws IOException;
	void doWriteBigDecimal(int index, String fieldName, BigDecimal value) throws IOException;
	void doWriteFloat(int index, String fieldName, float value) throws IOException;
	void doWriteDouble(int index, String fieldName, double value) throws IOException;
	void doWriteChar(int index, String fieldName, char value) throws IOException;
	void doWriteChars(int index, String fieldName, char[] values) throws IOException;
	void doWriteBoolean(int index, String fieldName, boolean value) throws IOException;
	void doWriteStringNumber(int index, String fieldName, String value) throws IOException;
	void doWriteBinary(int index, String fieldName, Base64Variant b64variant,
            byte[] values, int offset, int len) throws IOException;
	void doWriteNull(int index, String fieldName) throws IOException;
//	protected abstract void doWriteByte(int index, String fieldName, byte value) throws IOException;
//	protected abstract void doWriteStringBytes(int index, String fieldName, byte[] values) throws IOException;

}
