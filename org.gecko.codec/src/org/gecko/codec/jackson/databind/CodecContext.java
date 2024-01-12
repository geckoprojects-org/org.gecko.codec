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
package org.gecko.codec.jackson.databind;

import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.fasterxml.jackson.databind.DatabindContext;

/**
 * Codec Context with helper methods
 * @author Mark Hoffmann
 * @since 12.01.2024
 */
public class CodecContext {

	   public static String[] getURIs(final DatabindContext ctxt, final EObject object) {
	      if (object == null) {
	         return null;
	      }

	      if (object instanceof EClass) {
	    	  EClass eclass = (EClass) object;
	    	  return eclass.getEAllSuperTypes().
	    			  stream().
	    			  map(EcoreUtil::getURI).
	    			  map(Object::toString).
	    			  collect(Collectors.toList()).
	    			  toArray(new String[0]);
	      }
	      return new String[0];
	   }

}
