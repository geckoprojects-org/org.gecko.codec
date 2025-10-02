/*
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
 * 
 */
package org.eclipse.emfcloud.jackson.junit.model;

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Book</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.Book#getAuthor <em>Author</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.Book#getAuthorName <em>Author Name</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.Book#getTitle <em>Title</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getBook()
 * @model
 * @generated
 */
@ProviderType
public interface Book extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) 2012 - 2024 Data In Motion and others.\nAll rights reserved.\n\nThis program and the accompanying materials are made\navailable under the terms of the Eclipse Public License 2.0\nwhich is available at https://www.eclipse.org/legal/epl-2.0/\n\nSPDX-License-Identifier: EPL-2.0\n\nContributors:\n    Data In Motion - initial API and implementation\n";

	/**
	 * Returns the value of the '<em><b>Author</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Author</em>' containment reference.
	 * @see #setAuthor(Author)
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getBook_Author()
	 * @model containment="true"
	 * @generated
	 */
	Author getAuthor();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.Book#getAuthor <em>Author</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Author</em>' containment reference.
	 * @see #getAuthor()
	 * @generated
	 */
	void setAuthor(Author value);

	/**
	 * Returns the value of the '<em><b>Author Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Author Name</em>' attribute.
	 * @see #setAuthorName(String)
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getBook_AuthorName()
	 * @model unique="false"
	 *        extendedMetaData="name='author'"
	 * @generated
	 */
	String getAuthorName();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.Book#getAuthorName <em>Author Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Author Name</em>' attribute.
	 * @see #getAuthorName()
	 * @generated
	 */
	void setAuthorName(String value);

	/**
	 * Returns the value of the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Title</em>' attribute.
	 * @see #setTitle(String)
	 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getBook_Title()
	 * @model unique="false"
	 * @generated
	 */
	String getTitle();

	/**
	 * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.model.Book#getTitle <em>Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Title</em>' attribute.
	 * @see #getTitle()
	 * @generated
	 */
	void setTitle(String value);

} // Book
