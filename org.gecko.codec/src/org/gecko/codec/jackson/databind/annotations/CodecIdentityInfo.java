///**
// * Copyright (c) 2012 - 2024 Data In Motion and others.
// * All rights reserved. 
// * 
// * This program and the accompanying materials are made
// * available under the terms of the Eclipse Public License 2.0
// * which is available at https://www.eclipse.org/legal/epl-2.0/
// *
// * SPDX-License-Identifier: EPL-2.0
// * 
// * Contributors:
// *     Data In Motion - initial API and implementation
// */
//package org.gecko.codec.jackson.databind.annotations;
//
//import org.eclipse.emf.ecore.EObject;
//import org.eclipse.emf.ecore.util.EcoreUtil;
//import org.eclipse.emfcloud.jackson.annotations.EcoreIdentityInfo;
//import org.eclipse.emfcloud.jackson.utils.ValueWriter;
//
///**
// * Creates an info object for the ID handling. The default implementation takes either the resource id or the 
// * URI fragments identifier. 
// * This implementation also allows to write the EObject id-field value into the target
// * @author Mark Hoffmann
// * @since 11.01.2024
// */
//public class CodecIdentityInfo extends EcoreIdentityInfo {
//	
//	public static final ValueWriter<EObject, Object> IDFIELD_VALUE_WRITER = (object, context) -> EcoreUtil.getID(object);
//
//	/**
//	 * Creates a new instance.
//	 */
//	public CodecIdentityInfo() {
//		super();
//	}
//	
//	/**
//	 * Creates a new instance.
//	 */
//	public CodecIdentityInfo(String property) {
//		super(property);
//	}
//	
//	/**
//	 * Creates a new instance.
//	 */
//	public CodecIdentityInfo(String property, boolean useIdField) {
//		super(property, useIdField ? IDFIELD_VALUE_WRITER : null);
//	}
//	
//	
//}
