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
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.gecko.codec.jackson.databind.ser.CodecGeneratorBaseImpl;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.io.IOContext;

/**
 * 
 * @author mark
 * @since 10.01.2024
 */
public class CodecGeneratorImpl extends CodecGeneratorBaseImpl {

	/**
	 * Creates a new instance.
	 * @param features
	 * @param codec
	 * @param ctxt
	 */
	protected CodecGeneratorImpl(int features, ObjectCodec codec, IOContext ctxt) {
		super(features, codec, ctxt);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.databind.ser.CodecGeneratorBase#doWriteString(int, java.lang.String, java.lang.String)
	 */
	@Override
	public void doWriteString(int index, String fieldName, String value) throws IOException {
		Object parent = getOutputContext().getParent().getCurrentValue();
		EStructuralFeature feature = getOutputContext().getFeature();
		fieldName = "feature: " + (feature != null ? feature.getName() : "null") + " field: " + fieldName;
		System.out.println("[FIELD] Write '" + parent.getClass().getSimpleName() + "' - [" + index + "]: " + fieldName + ":" + value);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.databind.ser.CodecGeneratorBase#doWriteShort(int, java.lang.String, short)
	 */
	@Override
	public void doWriteShort(int index, String fieldName, short value) throws IOException {
		EObject parent = (EObject) getOutputContext().getParent().getCurrentValue();
		EStructuralFeature feature = getOutputContext().getFeature();
		fieldName = "feature: " + (feature != null ? feature.getName() : "null") + " field: " + fieldName;
		System.out.println("[FIELD] Write '" + parent.eClass().getName() + "' - [" + index + "]: " + fieldName + ":" + value);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.databind.ser.CodecGeneratorBase#doWriteLong(int, java.lang.String, long)
	 */
	@Override
	public void doWriteLong(int index, String fieldName, long value) throws IOException {
		EObject parent = (EObject) getOutputContext().getParent().getCurrentValue();
		EStructuralFeature feature = getOutputContext().getFeature();
		fieldName = "feature: " + (feature != null ? feature.getName() : "null") + " field: " + fieldName;
		System.out.println("[FIELD] Write '" + parent.eClass().getName() + "' - [" + index + "]: " + fieldName + ":" + value);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.databind.ser.CodecGeneratorBase#doWriteInt(int, java.lang.String, int)
	 */
	@Override
	public void doWriteInt(int index, String fieldName, int value) throws IOException {
		EObject parent = (EObject) getOutputContext().getParent().getCurrentValue();
		EStructuralFeature feature = getOutputContext().getFeature();
		fieldName = "feature: " + (feature != null ? feature.getName() : "null") + " field: " + fieldName;
		System.out.println("[FIELD] Write '" + parent.eClass().getName() + "' - [" + index + "]: " + fieldName + ":" + value);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.databind.ser.CodecGeneratorBase#doWriteBigInt(int, java.lang.String, java.math.BigInteger)
	 */
	@Override
	public void doWriteBigInt(int index, String fieldName, BigInteger value) throws IOException {
		EObject parent = (EObject) getOutputContext().getParent().getCurrentValue();
		EStructuralFeature feature = getOutputContext().getFeature();
		fieldName = "feature: " + (feature != null ? feature.getName() : "null") + " field: " + fieldName;
		System.out.println("[FIELD] Write '" + parent.eClass().getName() + "' - [" + index + "]: " + fieldName + ":" + value);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.databind.ser.CodecGeneratorBase#doWriteBigDecimal(int, java.lang.String, java.math.BigDecimal)
	 */
	@Override
	public void doWriteBigDecimal(int index, String fieldName, BigDecimal value) throws IOException {
		EObject parent = (EObject) getOutputContext().getParent().getCurrentValue();
		EStructuralFeature feature = getOutputContext().getFeature();
		fieldName = "feature: " + (feature != null ? feature.getName() : "null") + " field: " + fieldName;
		System.out.println("[FIELD] Write '" + parent.eClass().getName() + "' - [" + index + "]: " + fieldName + ":" + value);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.databind.ser.CodecGeneratorBase#doWriteFloat(int, java.lang.String, float)
	 */
	@Override
	public void doWriteFloat(int index, String fieldName, float value) throws IOException {
		EObject parent = (EObject) getOutputContext().getParent().getCurrentValue();
		EStructuralFeature feature = getOutputContext().getFeature();
		fieldName = "feature: " + (feature != null ? feature.getName() : "null") + " field: " + fieldName;
		System.out.println("[FIELD] Write '" + parent.eClass().getName() + "' - [" + index + "]: " + fieldName + ":" + value);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.databind.ser.CodecGeneratorBase#doWriteDouble(int, java.lang.String, double)
	 */
	@Override
	public void doWriteDouble(int index, String fieldName, double value) throws IOException {
		EObject parent = (EObject) getOutputContext().getParent().getCurrentValue();
		EStructuralFeature feature = getOutputContext().getFeature();
		fieldName = "feature: " + (feature != null ? feature.getName() : "null") + " field: " + fieldName;
		System.out.println("[FIELD] Write '" + parent.eClass().getName() + "' - [" + index + "]: " + fieldName + ":" + value);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.databind.ser.CodecGeneratorBase#doWriteChar(int, java.lang.String, char)
	 */
	@Override
	public void doWriteChar(int index, String fieldName, char value) throws IOException {
		EObject parent = (EObject) getOutputContext().getParent().getCurrentValue();
		EStructuralFeature feature = getOutputContext().getFeature();
		fieldName = "feature: " + (feature != null ? feature.getName() : "null") + " field: " + fieldName;
		System.out.println("[FIELD] Write '" + parent.eClass().getName() + "' - [" + index + "]: " + fieldName + ":" + value);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.databind.ser.CodecGeneratorBase#doWriteChars(int, java.lang.String, char[])
	 */
	@Override
	public void doWriteChars(int index, String fieldName, char[] values) throws IOException {
		EObject parent = (EObject) getOutputContext().getParent().getCurrentValue();
		EStructuralFeature feature = getOutputContext().getFeature();
		fieldName = "feature: " + (feature != null ? feature.getName() : "null") + " field: " + fieldName;
		System.out.println("[FIELD] Write '" + parent.eClass().getName() + "' - [" + index + "]: " + fieldName + ":" + String.valueOf(values));
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.databind.ser.CodecGeneratorBase#doWriteBoolean(int, java.lang.String, boolean)
	 */
	@Override
	public void doWriteBoolean(int index, String fieldName, boolean value) throws IOException {
		EObject parent = (EObject) getOutputContext().getParent().getCurrentValue();
		EStructuralFeature feature = getOutputContext().getFeature();
		fieldName = "feature: " + (feature != null ? feature.getName() : "null") + " field: " + fieldName;
		System.out.println("[FIELD] Write '" + parent.eClass().getName() + "' - [" + index + "]: " + fieldName + ":" + value);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.databind.ser.CodecGeneratorBase#doWriteStringNumber(int, java.lang.String, java.lang.String)
	 */
	@Override
	public void doWriteStringNumber(int index, String fieldName, String value) throws IOException {
		EObject parent = (EObject) getOutputContext().getParent().getCurrentValue();
		EStructuralFeature feature = getOutputContext().getFeature();
		fieldName = "feature: " + (feature != null ? feature.getName() : "null") + " field: " + fieldName;
		System.out.println("[FIELD] Write '" + parent.eClass().getName() + "' - [" + index + "]: " + fieldName + ":" + value);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.databind.ser.CodecGeneratorBase#doWriteNull(int, java.lang.String)
	 */
	@Override
	public void doWriteNull(int index, String fieldName) throws IOException {
		EObject parent = (EObject) getOutputContext().getParent().getCurrentValue();
		EStructuralFeature feature = getOutputContext().getFeature();
		fieldName = "feature: " + (feature != null ? feature.getName() : "null") + " field: " + fieldName;
		System.out.println("[FIELD] Write '" + parent.eClass().getName() + "' - [" + index + "]: " + fieldName + ":NULL");
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.databind.ser.CodecGeneratorBase#doWriteBinary(int, java.lang.String, com.fasterxml.jackson.core.Base64Variant, byte[], int, int)
	 */
	@Override
	public void doWriteBinary(int index, String fieldName, Base64Variant b64variant, byte[] values, int offset,
			int len) throws IOException {
		EObject parent = (EObject) getOutputContext().getParent().getCurrentValue();
		EStructuralFeature feature = getOutputContext().getFeature();
		fieldName = "feature: " + (feature != null ? feature.getName() : "null") + " field: " + fieldName;
		System.out.println("[FIELD] Write '" + parent.eClass().getName() + "' - [" + index + "]: " + fieldName + ":NULL");

	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.databind.ser.CodecGeneratorBase#doStartWriteEObject(int, java.lang.String, org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public void doStartWriteEObject(int index, String fieldName, EObject object) throws IOException {
		EStructuralFeature feature = getOutputContext().getFeature();
		fieldName = "feature: " + (feature != null ? feature.getName() : "null") + " field: " + fieldName;
		System.out.println("[START] Write EObject [" + index + "]: " + fieldName + ": " + object);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.databind.ser.CodecGeneratorBase#doStartWriteRootEObject(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public void doStartWriteRootEObject(EObject object) throws IOException {
		System.out.println("[START] Write Root: " + object);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.databind.ser.CodecGeneratorBase#doEndWriteEObject(int, java.lang.String, org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public void doEndWriteEObject(int index, String fieldName, EObject object) throws IOException {
		EStructuralFeature feature = getOutputContext().getFeature();
		fieldName = "feature: " + (feature != null ? feature.getName() : "null") + " field: " + fieldName;
		System.out.println("[END]   Write EObject [" + index + "]: " + fieldName + ": " + object);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.databind.ser.CodecGeneratorBase#doEndWriteRootEObject(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public void doEndWriteRootEObject(EObject object) throws IOException {
		System.out.println("[END]   Write Root: " + object);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.databind.ser.CodecGeneratorBase#doWriteObjectId(int, java.lang.String, java.lang.Object)
	 */
	@Override
	public void doWriteObjectId(int index, String fieldName, Object object) throws IOException {
		EStructuralFeature feature = getOutputContext().getFeature();
		fieldName = "feature: " + (feature != null ? feature.getName() : "null") + " field: " + fieldName;
		EObject parent = (EObject) getOutputContext().getParent().getCurrentValue();
		System.out.println("[ID]    Write '" + parent.eClass().getName() + "' - [" + index + "]: " + fieldName + ": " + object);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.databind.ser.CodecGeneratorBase#doWriteTypeId(int, java.lang.String, java.lang.Object)
	 */
	@Override
	public void doWriteType(int index, String fieldName, Object object) throws IOException {
		EObject parent = (EObject) getOutputContext().getParent().getCurrentValue();
		String superTypes = object.toString();
		if (object.getClass().isArray()) {
			superTypes = String.join(", ", Arrays.asList((Object[])object).stream().
					map(Object::toString).
					collect(Collectors.toList()).
					toArray(new String[0]));
		}
		EStructuralFeature feature = getOutputContext().getFeature();
		fieldName = "feature: " + (feature != null ? feature.getName() : "null") + " field: " + fieldName;
		System.out.println("[TYPE]  Write '" + parent.eClass().getName() + "' - [" + index + "]: " + fieldName + ": " + superTypes);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.databind.ser.CodecGeneratorBase#doStartWriteArray(int, java.lang.String, java.lang.Object)
	 */
	@Override
	public void doStartWriteArray(int index, String fieldName, Object object) throws IOException {
		EStructuralFeature feature = getOutputContext().getFeature();
		fieldName = "feature: " + (feature != null ? feature.getName() : "null") + " field: " + fieldName;
		if (Objects.nonNull(object) && object.getClass().isArray()) {
			String[] array = Arrays.asList((Object[])object).stream().filter(Objects::nonNull).map(Object::toString).collect(Collectors.toList()).toArray(new String[0]);
			System.out.println("[START] Write array - [" + index + "]: " + fieldName + ": " + String.join(", ", array));
		}
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.databind.ser.CodecGeneratorBase#doEndWriteArray(int, java.lang.String, java.lang.Object)
	 */
	@Override
	public void doEndWriteArray(int index, String fieldName, Object object) throws IOException {
		EStructuralFeature feature = getOutputContext().getFeature();
		fieldName = "feature: " + (feature != null ? feature.getName() : "null") + " field: " + fieldName;
		if (Objects.nonNull(object) && object.getClass().isArray()) {
			String[] array = Arrays.asList((Object[])object).stream().filter(Objects::nonNull).map(Object::toString).collect(Collectors.toList()).toArray(new String[0]);
			System.out.println("[END]   Write array [" + index + "]: " + fieldName + ": " + String.join(", ", array));
		}
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.CodecGenerator#doWriteSuperTypes(int, java.lang.String, java.lang.String[])
	 */
	@Override
	public void doWriteSuperTypes(int index, String fieldName, String[] superTypes) throws IOException {
		doWriteType(index, fieldName, superTypes);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.CodecGenerator#doWriteArray(java.lang.Object[], int, int, java.lang.Class)
	 */
	@Override
	public <T> void doWriteArray(T[] array, int offset, int length, Class<T> clazz) throws IOException {
		// TODO Auto-generated method stub

	}

}
