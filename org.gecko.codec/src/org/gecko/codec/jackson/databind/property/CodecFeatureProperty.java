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
package org.gecko.codec.jackson.databind.property;

import static java.util.Objects.nonNull;

import java.io.IOException;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emfcloud.jackson.databind.property.EObjectFeatureProperty;
import org.gecko.codec.jackson.databind.CodecWriteContext;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * 
 * @author mark
 * @since 26.01.2024
 */
public class CodecFeatureProperty extends EObjectFeatureProperty {

	private final EStructuralFeature feature;

	/**
	 * Creates a new instance.
	 * @param feature
	 * @param type
	 * @param features
	 */
	public CodecFeatureProperty(EStructuralFeature feature, JavaType type, int features) {
		super(feature, type, features);
		this.feature = feature;
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.emfcloud.jackson.databind.property.EObjectFeatureProperty#serialize(org.eclipse.emf.ecore.EObject, com.fasterxml.jackson.core.JsonGenerator, com.fasterxml.jackson.databind.SerializerProvider)
	 */
	@Override
	public void serialize(EObject bean, JsonGenerator jg, SerializerProvider provider) throws IOException {
		if (nonNull(feature) && 
				jg.getOutputContext() instanceof CodecWriteContext) {
			((CodecWriteContext)jg.getOutputContext()).setFeature(feature);
		}
		super.serialize(bean, jg, provider);
	}

}
