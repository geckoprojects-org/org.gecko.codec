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
package org.gecko.codec.scope;

/**
 * 
 * @author mark
 * @since 22.03.2023
 */
public interface HeaderScope extends Scope {
	
	/**
	 * Return <code>true</code>, if namespace informationen needs to be written, otherwise <code>false</code>
	 * @return <code>true</code>, if namespace informationen needs to be written, otherwise <code>false</code>
	 */
	boolean isWriteNamespace();
	
	/**
	 * Returns the key for namespace identifier value
	 * @return the key for namespace identifier value
	 */
	String getNamespaceKey();
	
	/**
	 * Returns the namespace value
	 * @return 
	 */
	String getNamespace();

	/**
	 * @return
	 */
	boolean isWriteSuperNamespaces();

	/**
	 * @return
	 */
	String getSuperNamespaceKey();

	/**
	 * @return
	 */
	Object getSuperNamespace();
	
	boolean isWriteDefaultValues();
	
	boolean isWriteEnumLiteral();
	
	boolean isIdFieldAsPrimaryKey();
	
	String getIdFieldKey();
	
	Object getIdFieldValue();
	
	boolean isWriteTimestamp();
	
	/**
	 * This option may be used when you wish to provide the feature name for timestamps
	 * 
	 * Value type: {@link String}
	 */
	String getTimestampKey();
	
	Long getTimestamp();
	
	/**
	 * If set to <code>true</code> the proxy refernces will be encoded as String instead of a complex type
	 * Value type: {@link Boolean}, default is <code>false</code>
	 */
	boolean isProxyUriAsString();
	
	String getProxyUriKey();
	
	String getProxyUri();
	
	

}
