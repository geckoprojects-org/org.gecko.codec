/**
 * Copyright (c) 2012 - 2025 Data In Motion and others.
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
package org.eclipse.fennec.codec.info.value.readers;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.fennec.codec.info.codecinfo.CodecValueReader;

import tools.jackson.databind.DeserializationContext;

/**
 * 
 * @author ilenia
 * @since Jul 18, 2025
 */
public class DefaultEClassReader implements CodecValueReader<String, EClass>{
	
	private ResourceSet resourceSet;

	public DefaultEClassReader() {
		
	}
	
	public DefaultEClassReader(ResourceSet resourceSet) {
		this.resourceSet = resourceSet;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.fennec.codec.info.codecinfo.CodecValueReader#getName()
	 */
	@Override
	public String getName() {
		return "DEFAULT_ECLASS_READER";
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.fennec.codec.info.codecinfo.CodecValueReader#readValue(java.lang.Object, tools.jackson.databind.DeserializationContext)
	 */
	@Override
	public EClass readValue(String value, DeserializationContext context) {		
		return (EClass) resourceSet.getEObject(URI.createURI(value), true);
//		Set<EClass> types = CodecIOHelper.getAllTypes(resourceSet);			
//		return types.stream().filter(CodecIOHelper.findByURI(value)).findFirst().orElse(null);
	}

}
