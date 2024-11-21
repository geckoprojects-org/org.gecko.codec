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
package org.gecko.codec.jpa;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.persistence.dynamic.DynamicEntity;
import org.eclipse.persistence.dynamic.DynamicTypeBuilder;
import org.gecko.codec.jackson.databind.CodecWriteContext;
import org.gecko.codec.jackson.databind.ser.CodecGeneratorBaseImpl;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.ObjectCodec;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

/**
 * 
 * @author ilenia
 * @since Nov 21, 2024
 */
public class JPACodecGenerator extends CodecGeneratorBaseImpl {

	private EntityManagerFactory emf;

	public JPACodecGenerator(EntityManagerFactory emf, ObjectCodec objectCodec) {
		super(-1, objectCodec, null);
		this.emf = emf;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.CodecGenerator#doStartWriteRootEObject(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public void doStartWriteRootEObject(EObject object) throws IOException {

		Class<?> objectClass = object.getClass();
		ClassLoader classLoader = objectClass.getClassLoader();
		DynamicTypeBuilder objBuilder = 
				new DynamicTypeBuilder(objectClass, null /*no parent type*/, object.eClass().getName().toUpperCase());
		
		DynamicEntity rootEntity = objBuilder.getType().newDynamicEntity();
		((CodecWriteContext)_writeContext).getDataMap().put("ROOT_ENTITY", rootEntity);
		((CodecWriteContext)_writeContext).getDataMap().put("ROOT_BUILDER", objBuilder);

	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.CodecGenerator#doEndWriteRootEObject(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public void doEndWriteRootEObject(EObject object) throws IOException {
		EntityManager em = emf.createEntityManager();
		DynamicEntity rootEntity = (DynamicEntity) ((CodecWriteContext)_writeContext).getDataMap().get("ROOT_ENTITY");
		em.getTransaction().begin();
		em.persist(rootEntity);
		em.getTransaction().commit();
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.CodecGenerator#doWriteType(int, java.lang.String, java.lang.Object)
	 */
	@Override
	public void doWriteType(int index, String fieldName, Object object) throws IOException {
		// TODO Auto-generated method stub

	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.CodecGenerator#doWriteSuperTypes(int, java.lang.String, java.lang.String[])
	 */
	@Override
	public void doWriteSuperTypes(int index, String fieldName, String[] superTypes) throws IOException {
		// TODO Auto-generated method stub

	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.CodecGenerator#doWriteObjectId(int, java.lang.String, java.lang.Object)
	 */
	@Override
	public void doWriteObjectId(int index, String fieldName, Object object) throws IOException {
		// TODO Auto-generated method stub

	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.CodecGenerator#doStartWriteEObject(int, java.lang.String, org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public void doStartWriteEObject(int index, String fieldName, EObject object) throws IOException {
		// TODO Auto-generated method stub

	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.CodecGenerator#doEndWriteEObject(int, java.lang.String, org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public void doEndWriteEObject(int index, String fieldName, EObject object) throws IOException {
		// TODO Auto-generated method stub

	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.CodecGenerator#doStartWriteArray(int, java.lang.String, java.lang.Object)
	 */
	@Override
	public void doStartWriteArray(int index, String fieldName, Object object) throws IOException {
		// TODO Auto-generated method stub

	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.CodecGenerator#doEndWriteArray(int, java.lang.String, java.lang.Object)
	 */
	@Override
	public void doEndWriteArray(int index, String fieldName, Object object) throws IOException {
		// TODO Auto-generated method stub

	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.CodecGenerator#doWriteString(int, java.lang.String, java.lang.String)
	 */
	@Override
	public void doWriteString(int index, String fieldName, String value) throws IOException {
		DynamicTypeBuilder objBuilder;
		DynamicEntity objEntity;
		if(_writeContext.inRoot()) {
			objBuilder = (DynamicTypeBuilder) ((CodecWriteContext)_writeContext).getDataMap().get("ROOT_BUILDER");
			objBuilder.addDirectMapping(fieldName, String.class, fieldName.toUpperCase());
			
			objEntity = (DynamicEntity) ((CodecWriteContext)_writeContext).getDataMap().get("ROOT_ENTITY");
			objEntity.set(fieldName, value);
		}
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.CodecGenerator#doWriteShort(int, java.lang.String, short)
	 */
	@Override
	public void doWriteShort(int index, String fieldName, short value) throws IOException {
		// TODO Auto-generated method stub

	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.CodecGenerator#doWriteLong(int, java.lang.String, long)
	 */
	@Override
	public void doWriteLong(int index, String fieldName, long value) throws IOException {
		// TODO Auto-generated method stub

	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.CodecGenerator#doWriteInt(int, java.lang.String, int)
	 */
	@Override
	public void doWriteInt(int index, String fieldName, int value) throws IOException {
		// TODO Auto-generated method stub

	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.CodecGenerator#doWriteBigInt(int, java.lang.String, java.math.BigInteger)
	 */
	@Override
	public void doWriteBigInt(int index, String fieldName, BigInteger value) throws IOException {
		// TODO Auto-generated method stub

	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.CodecGenerator#doWriteBigDecimal(int, java.lang.String, java.math.BigDecimal)
	 */
	@Override
	public void doWriteBigDecimal(int index, String fieldName, BigDecimal value) throws IOException {
		// TODO Auto-generated method stub

	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.CodecGenerator#doWriteFloat(int, java.lang.String, float)
	 */
	@Override
	public void doWriteFloat(int index, String fieldName, float value) throws IOException {
		// TODO Auto-generated method stub

	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.CodecGenerator#doWriteDouble(int, java.lang.String, double)
	 */
	@Override
	public void doWriteDouble(int index, String fieldName, double value) throws IOException {
		// TODO Auto-generated method stub

	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.CodecGenerator#doWriteChar(int, java.lang.String, char)
	 */
	@Override
	public void doWriteChar(int index, String fieldName, char value) throws IOException {
		// TODO Auto-generated method stub

	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.CodecGenerator#doWriteChars(int, java.lang.String, char[])
	 */
	@Override
	public void doWriteChars(int index, String fieldName, char[] values) throws IOException {
		// TODO Auto-generated method stub

	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.CodecGenerator#doWriteBoolean(int, java.lang.String, boolean)
	 */
	@Override
	public void doWriteBoolean(int index, String fieldName, boolean value) throws IOException {
		// TODO Auto-generated method stub

	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.CodecGenerator#doWriteStringNumber(int, java.lang.String, java.lang.String)
	 */
	@Override
	public void doWriteStringNumber(int index, String fieldName, String value) throws IOException {
		// TODO Auto-generated method stub

	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.CodecGenerator#doWriteNull(int, java.lang.String)
	 */
	@Override
	public void doWriteNull(int index, String fieldName) throws IOException {
		// TODO Auto-generated method stub

	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.CodecGenerator#doWriteBinary(int, java.lang.String, com.fasterxml.jackson.core.Base64Variant, byte[], int, int)
	 */
	@Override
	public void doWriteBinary(int index, String fieldName, Base64Variant b64variant, byte[] values, int offset, int len)
			throws IOException {
		// TODO Auto-generated method stub

	}

}
