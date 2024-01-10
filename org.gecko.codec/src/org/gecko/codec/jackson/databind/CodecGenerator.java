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
package org.gecko.codec.jackson.databind;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.eclipse.emf.ecore.EObject;
import org.gecko.codec.jackson.databind.ser.CodecGeneratorBase;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.io.IOContext;

/**
 * 
 * @author mark
 * @since 10.01.2024
 */
public class CodecGenerator extends CodecGeneratorBase {

	/**
	 * Creates a new instance.
	 * @param features
	 * @param codec
	 * @param ctxt
	 */
	protected CodecGenerator(int features, ObjectCodec codec, IOContext ctxt) {
		super(features, codec, ctxt);
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.databind.ser.CodecGeneratorBase#doWriteString(int, java.lang.String, java.lang.String)
	 */
	@Override
	protected void doWriteString(int index, String fieldName, String value) throws IOException {
		EObject parent = (EObject) getOutputContext().getParent().getCurrentValue();
		System.out.println("Write '" + parent.eClass().getName() + "' - [" + index + "]: " + fieldName + ":" + value);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.databind.ser.CodecGeneratorBase#doWriteShort(int, java.lang.String, short)
	 */
	@Override
	protected void doWriteShort(int index, String fieldName, short value) throws IOException {
		EObject parent = (EObject) getOutputContext().getParent().getCurrentValue();
		System.out.println("Write '" + parent.eClass().getName() + "' - [" + index + "]: " + fieldName + ":" + value);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.databind.ser.CodecGeneratorBase#doWriteLong(int, java.lang.String, long)
	 */
	@Override
	protected void doWriteLong(int index, String fieldName, long value) throws IOException {
		EObject parent = (EObject) getOutputContext().getParent().getCurrentValue();
		System.out.println("Write '" + parent.eClass().getName() + "' - [" + index + "]: " + fieldName + ":" + value);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.databind.ser.CodecGeneratorBase#doWriteInt(int, java.lang.String, int)
	 */
	@Override
	protected void doWriteInt(int index, String fieldName, int value) throws IOException {
		EObject parent = (EObject) getOutputContext().getParent().getCurrentValue();
		System.out.println("Write '" + parent.eClass().getName() + "' - [" + index + "]: " + fieldName + ":" + value);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.databind.ser.CodecGeneratorBase#doWriteBigInt(int, java.lang.String, java.math.BigInteger)
	 */
	@Override
	protected void doWriteBigInt(int index, String fieldName, BigInteger value) throws IOException {
		EObject parent = (EObject) getOutputContext().getParent().getCurrentValue();
		System.out.println("Write '" + parent.eClass().getName() + "' - [" + index + "]: " + fieldName + ":" + value);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.databind.ser.CodecGeneratorBase#doWriteBigDecimal(int, java.lang.String, java.math.BigDecimal)
	 */
	@Override
	protected void doWriteBigDecimal(int index, String fieldName, BigDecimal value) throws IOException {
		EObject parent = (EObject) getOutputContext().getParent().getCurrentValue();
		System.out.println("Write '" + parent.eClass().getName() + "' - [" + index + "]: " + fieldName + ":" + value);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.databind.ser.CodecGeneratorBase#doWriteFloat(int, java.lang.String, float)
	 */
	@Override
	protected void doWriteFloat(int index, String fieldName, float value) throws IOException {
		EObject parent = (EObject) getOutputContext().getParent().getCurrentValue();
		System.out.println("Write '" + parent.eClass().getName() + "' - [" + index + "]: " + fieldName + ":" + value);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.databind.ser.CodecGeneratorBase#doWriteDouble(int, java.lang.String, double)
	 */
	@Override
	protected void doWriteDouble(int index, String fieldName, double value) throws IOException {
		EObject parent = (EObject) getOutputContext().getParent().getCurrentValue();
		System.out.println("Write '" + parent.eClass().getName() + "' - [" + index + "]: " + fieldName + ":" + value);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.databind.ser.CodecGeneratorBase#doWriteChar(int, java.lang.String, char)
	 */
	@Override
	protected void doWriteChar(int index, String fieldName, char value) throws IOException {
		EObject parent = (EObject) getOutputContext().getParent().getCurrentValue();
		System.out.println("Write '" + parent.eClass().getName() + "' - [" + index + "]: " + fieldName + ":" + value);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.databind.ser.CodecGeneratorBase#doWriteChars(int, java.lang.String, char[])
	 */
	@Override
	protected void doWriteChars(int index, String fieldName, char[] values) throws IOException {
		EObject parent = (EObject) getOutputContext().getParent().getCurrentValue();
		System.out.println("Write '" + parent.eClass().getName() + "' - [" + index + "]: " + fieldName + ":" + String.valueOf(values));
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.databind.ser.CodecGeneratorBase#doWriteBoolean(int, java.lang.String, boolean)
	 */
	@Override
	protected void doWriteBoolean(int index, String fieldName, boolean value) throws IOException {
		EObject parent = (EObject) getOutputContext().getParent().getCurrentValue();
		System.out.println("Write '" + parent.eClass().getName() + "' - [" + index + "]: " + fieldName + ":" + value);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.databind.ser.CodecGeneratorBase#doWriteStringNumber(int, java.lang.String, java.lang.String)
	 */
	@Override
	protected void doWriteStringNumber(int index, String fieldName, String value) throws IOException {
		EObject parent = (EObject) getOutputContext().getParent().getCurrentValue();
		System.out.println("Write '" + parent.eClass().getName() + "' - [" + index + "]: " + fieldName + ":" + value);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.databind.ser.CodecGeneratorBase#doWriteNull(int, java.lang.String)
	 */
	@Override
	protected void doWriteNull(int index, String fieldName) throws IOException {
		EObject parent = (EObject) getOutputContext().getParent().getCurrentValue();
		System.out.println("Write '" + parent.eClass().getName() + "' - [" + index + "]: " + fieldName + ":NULL");
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.databind.ser.CodecGeneratorBase#doWriteBinary(int, java.lang.String, com.fasterxml.jackson.core.Base64Variant, byte[], int, int)
	 */
	@Override
	protected void doWriteBinary(int index, String fieldName, Base64Variant b64variant, byte[] values, int offset,
			int len) throws IOException {
		EObject parent = (EObject) getOutputContext().getParent().getCurrentValue();
		System.out.println("Write '" + parent.eClass().getName() + "' - [" + index + "]: " + fieldName + ":NULL");
		
	}

}
