/**
 * Copyright (c) 2012 - 2024 Data In Motion and others.
 * All rights reserved. 
 * 
 * This program and the accompanying materials are made available under the terms of the 
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Data In Motion - initial API and implementation
 */
package org.gecko.codec.jackson.databind;

import com.fasterxml.jackson.core.json.DupDetector;
import com.fasterxml.jackson.core.json.JsonWriteContext;

/**
 * 
 * @author grune
 * @since Jan 26, 2024
 */
public class FeatureWriteContext extends JsonWriteContext {

	/**
	 * Creates a new instance.
	 * 
	 * @param typeRoot
	 * @param object
	 */
	public FeatureWriteContext(int typeRoot, Object object) {
		this(typeRoot, null, null);
	}

	/**
	 * Creates a new instance.
	 * 
	 * @param type
	 * @param parent
	 * @param dups
	 */
	protected FeatureWriteContext(int type, FeatureWriteContext parent, DupDetector dups) {
		super(type, parent, dups);
	}

	public static FeatureWriteContext createRootContext(DupDetector dups) {
		return new FeatureWriteContext(TYPE_ROOT, dups);
	}
}
