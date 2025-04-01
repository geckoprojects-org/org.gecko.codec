/*
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
