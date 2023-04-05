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
package org.gecko.codec.encode;

import static java.util.Objects.nonNull;

import java.util.Arrays;

import org.gecko.codec.scope.HeaderScope;

/**
 * 
 * @author mark
 * @since 22.03.2023
 */
public abstract class BasicCodecWriter<T> implements CodecWriter {

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.encode.CodecWriter#writeHeader(org.gecko.codec.scope.HeaderScope)
	 */
	@Override
	public void writeHeader(HeaderScope scope) {
		if (nonNull(scope)) {
			return;
		}
		if (!scope.validate()) {
			return;
		}
		if (scope.isWriteNamespace()) {
			writeString(scope.getNamespaceKey(), scope.getNamespace());
		}
		if (scope.isWriteSuperNamespaces()) {
			Object superNamespace = scope.getSuperNamespace();
			if (superNamespace.getClass().isArray()) {
				Object[] superNSs = (Object[]) superNamespace;
				if (superNSs.length > 0) {
					writeStartList(scope.getSuperNamespaceKey());
					Arrays.asList(superNSs).forEach(this::writeString);
					writeEndList();
				}
			}
		}
		if (scope.isWriteTimestamp()) {
			writeDateTime(scope.getTimestampKey(), scope.getTimestamp());
		}
		if (scope.isIdFieldAsPrimaryKey()) {
			writeFeature(scope.getIdFieldKey(), scope.getIdFieldValue());
		}
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.encode.CodecWriter#writeFeature(java.lang.String, java.lang.Object)
	 */
	@Override
	public void writeFeature(String featureName, Object value) {
		
		System.out.println(String.format("Writing Feature: '%s' with content '%s'", featureName, value));
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.encode.CodecWriter#writeProxy(java.lang.String, java.lang.Object)
	 */
	@Override
	public void writeProxy(String featureName, Object value) {
		// TODO Auto-generated method stub
		
	}

}
