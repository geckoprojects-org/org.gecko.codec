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
package org.eclipse.fennec.models.geojson.util;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.AbstractEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * 
 * @author Juergen Albert
 * @since 29 Nov 2024
 */
public class GenericConvertingList<T,E> extends AbstractEList<T> implements EStructuralFeature.Setting{

	
	private EList<E> backingList;
	private Function<T, E> tToE;
	private Function<E, T> eToT;
	
	/**
	 * Creates a new instance.
	 */
	public GenericConvertingList(EList<E> backingList, Function<T, E> tToE, Function<E, T> eToT) {
		this.backingList = backingList;
		this.tToE = tToE;
		this.eToT = eToT;
	}
	
	
	/* 
	 * (non-Javadoc)
	 * @see java.util.List#size()
	 */
	@Override
	public int size() {
		return backingList.size();
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.emf.common.util.AbstractEList#primitiveGet(int)
	 */
	@Override
	protected T primitiveGet(int index) {
		return eToT.apply(backingList.get(index));
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.emf.common.util.AbstractEList#setUnique(int, java.lang.Object)
	 */
	@Override
	public T setUnique(int index, T object) {
		E set = backingList.set(index, tToE.apply(object));
		return set == null ? null : eToT.apply(set);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.emf.common.util.AbstractEList#addUnique(java.lang.Object)
	 */
	@Override
	public void addUnique(T object) {
		backingList.add(tToE.apply(object));
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.emf.common.util.AbstractEList#addUnique(int, java.lang.Object)
	 */
	@Override
	public void addUnique(int index, T object) {
		backingList.add(index, tToE.apply(object));
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.emf.common.util.AbstractEList#addAllUnique(java.util.Collection)
	 */
	@Override
	public boolean addAllUnique(Collection<? extends T> collection) {
		Collection<E> convert =  collection.stream().map(tToE).collect(Collectors.toList());
		return backingList.addAll(convert);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.emf.common.util.AbstractEList#addAllUnique(int, java.util.Collection)
	 */
	@Override
	public boolean addAllUnique(int index, Collection<? extends T> collection) {
		Collection<E> convert =  collection.stream().map(tToE).collect(Collectors.toList());
		return backingList.addAll(index, convert);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.emf.common.util.AbstractEList#addAllUnique(java.lang.Object[], int, int)
	 */
	@Override
	public boolean addAllUnique(Object[] objects, int start, int end) {
		throw new UnsupportedOperationException();
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.emf.common.util.AbstractEList#addAllUnique(int, java.lang.Object[], int, int)
	 */
	@Override
	public boolean addAllUnique(int index, Object[] objects, int start, int end) {
		throw new UnsupportedOperationException();
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.emf.common.util.AbstractEList#remove(int)
	 */
	@Override
	public T remove(int index) {
		E removed = backingList.remove(index);
		return removed == null ? null : eToT.apply(removed);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.emf.common.util.AbstractEList#move(int, int)
	 */
	@Override
	public T move(int targetIndex, int sourceIndex) {
		E moved = backingList.move(targetIndex, sourceIndex);
		return moved == null ? null : eToT.apply(moved);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.emf.common.util.AbstractEList#basicList()
	 */
	@Override
	protected List<T> basicList() {
		return backingList.stream().map(eToT).collect(Collectors.toList());
	}

	/* 
	 * (non-Javadoc)
	 * @see java.util.AbstractList#get(int)
	 */
	@Override
	public T get(int index) {
		E get = backingList.get(index);
		return get == null ? null : eToT.apply(get);
	}


	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecore.EStructuralFeature.Setting#getEObject()
	 */
	@Override
	public EObject getEObject() {
		return ((Setting) backingList).getEObject();
	}


	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecore.EStructuralFeature.Setting#getEStructuralFeature()
	 */
	@Override
	public EStructuralFeature getEStructuralFeature() {
		// TODO Auto-generated method stub
		return null;
	}


	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecore.EStructuralFeature.Setting#get(boolean)
	 */
	@Override
	public Object get(boolean resolve) {
		return this;
	}


	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecore.EStructuralFeature.Setting#set(java.lang.Object)
	 */
	@Override
	public void set(Object newValue) {
		GenericConvertingList<T, E> src = (GenericConvertingList<T, E>) newValue;
		backingList = src.backingList;
		eToT = src.eToT;
		tToE = src.tToE;
	}


	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecore.EStructuralFeature.Setting#isSet()
	 */
	@Override
	public boolean isSet() {
		return ((Setting) backingList).isSet();
	}


	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecore.EStructuralFeature.Setting#unset()
	 */
	@Override
	public void unset() {
		((Setting) backingList).unset();
	}

}
