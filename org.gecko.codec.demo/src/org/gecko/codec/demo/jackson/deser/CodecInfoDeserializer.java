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
package org.gecko.codec.demo.jackson.deser;

import java.io.IOException;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;

/**
 * 
 * @author ilenia
 * @since Sep 26, 2024
 */
public interface CodecInfoDeserializer {
	
	public abstract EObject deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException;

	public abstract void deserializeAndSet(JsonParser jp, EObject current, DeserializationContext ctxt, Resource resource) throws IOException;

}
