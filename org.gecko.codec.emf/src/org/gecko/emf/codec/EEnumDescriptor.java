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
package org.gecko.emf.codec;

import java.util.Map;

import org.eclipse.emf.ecore.EEnum;
import org.gecko.emf.codec.cache.EMFDescriptorCache;

/**
 * Descriptor for {@link EEnum}
 * @author Mark Hoffmann
 * @since 23.03.2023
 */
public class EEnumDescriptor extends EClassifierDescriptor<EEnum> {


	public EEnumDescriptor(EEnum eenum, Map<String, Object> properties, EMFDescriptorCache cache) {
		super(eenum, properties, cache);
	}

	public EEnumDescriptor(EEnum eenum, Map<String, Object> properties) {
		super(eenum, properties, new EMFDescriptorCache());
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.descriptors.ClassifierDescriptor#isSimpleType()
	 */
	@Override
	public boolean isSimpleType() {
		return true;
	}

}
