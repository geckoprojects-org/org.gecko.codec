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

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;
import static org.gecko.codec.helper.PropertiesHelper.getTypedValue;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

import org.gecko.codec.CodecConstants;
import org.gecko.codec.diagnostic.Diagnostic;
import org.gecko.codec.diagnostic.Diagnostics;
import org.gecko.codec.helper.PropertiesHelper;

/**
 * Basic scope implementation
 * @author mark
 * @since 22.03.2023
 */
public abstract class BasicScope implements HeaderScope {
	
	private final Map<String, Object> properties = new HashMap<>();
	private final List<Diagnostic> diagnostics = new LinkedList<>();
	
	public BasicScope() {
	}
	
	public BasicScope(Map<String, Object> properties) {
		if (properties != null) {
			this.properties.putAll(properties);
		}
	}
	
	/**
	 * Returns the properties
	 * @return the properties
	 */
	Map<String, Object> getProperties() {
		return properties;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.scope.Scope#validate()
	 */
	@Override
	public boolean validate() {
		if (isWriteNamespace()) {
			validateWithDiagnostic(this::getNamespaceKey);
			validateWithDiagnostic(this::getNamespace);
		}
		if (isWriteSuperNamespaces()) {
			validateWithDiagnostic(this::getSuperNamespaceKey);
			validateWithDiagnostic(this::getSuperNamespace);
		}
		if (isWriteTimestamp()) {
			validateWithDiagnostic(this::getTimestampKey);
			validateWithDiagnostic(this::getTimestamp);
		}
		if (isIdFieldAsPrimaryKey()) {
			validateWithDiagnostic(this::getIdFieldKey);
		}
		return getDiagnostics().isEmpty();
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.scope.Scope#getDiagnostics()
	 */
	@Override
	public final List<Diagnostic> getDiagnostics() {
		return diagnostics;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.scope.HeaderScope#writeNamespace()
	 */
	@Override
	public boolean isWriteNamespace() {
		return nonNull(getNamespace()) && nonNull(getNamespaceKey());
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.scope.HeaderScope#getNamespaceKey()
	 */
	@Override
	public String getNamespaceKey() {
		return (String) properties.getOrDefault(CodecConstants.KEY_CLASSIFIER_TYPE, CodecConstants.FEATURE_TYPE_DEFAULT);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.scope.HeaderScope#getNamespace()
	 */
	@Override
	public String getNamespace() {
		return PropertiesHelper.getStringValue(properties, CodecConstants.ENCODE_TYPE);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.scope.HeaderScope#isWriteSuperNamespaces()
	 */
	@Override
	public boolean isWriteSuperNamespaces() {
		return nonNull(getSuperNamespace()) && nonNull(getSuperNamespaceKey());
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.scope.HeaderScope#getSuperNamespaceKey()
	 */
	@Override
	public String getSuperNamespaceKey() {
		return (String) properties.getOrDefault(CodecConstants.KEY_CLASSIFIER_SUPERTYPE, CodecConstants.FEATURE_CLASSIFIER_SUPERTYPE_DEFAULT);
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.scope.HeaderScope#getSuperNamespace()
	 */
	@Override
	public Object getSuperNamespace() {
		return PropertiesHelper.getStringPlusValue(properties, CodecConstants.ENCODE_SUPERTYPES);
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.scope.HeaderScope#isWriteTimestamp()
	 */
	@Override
	public boolean isWriteTimestamp() {
		return nonNull(getTimestampKey()) && nonNull(getTimestamp());
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.scope.HeaderScope#getTimestampKey()
	 */
	@Override
	public String getTimestampKey() {
		return (String) properties.getOrDefault(CodecConstants.KEY_FEATURE_TIMESTAMP, CodecConstants.FEATURE_TIMESTAMP_DEFAULT);
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.scope.HeaderScope#getTimestamp()
	 */
	@Override
	public Long getTimestamp() {
		try {
			return getTypedValue(properties, CodecConstants.ENCODE_TIMESTAMP, Long.class);
		} catch (Exception e) {
			getDiagnostics().add(Diagnostics.createDiagnostic(e));
			return null;
		}
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.scope.HeaderScope#isIdFieldAsPrimaryKey()
	 */
	@Override
	public boolean isIdFieldAsPrimaryKey() {
		return PropertiesHelper.getBooleanValue(properties, CodecConstants.OPTION_ID_FEATURE_AS_PRIMARY_KEY, true);
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.scope.HeaderScope#getIdFieldKey()
	 */
	@Override
	public String getIdFieldKey() {
		return (String) properties.getOrDefault(CodecConstants.KEY_FEATURE_ID, CodecConstants.FEATURE_IDFIELD_DEFAULT);
	}

	/**
	 * Validates the return value of the supplier and creates an diagnostic, if the return value is <code>null</code>
	 * @param supplier the supplier to be checked for its return value
	 */
	private void validateWithDiagnostic(Supplier<?> supplier) {
		Objects.requireNonNull(supplier);
		try {
			requireNonNull(supplier.get());
		} catch (Exception e) {
			getDiagnostics().add(Diagnostics.createDiagnostic(e));
		}
	}

}
