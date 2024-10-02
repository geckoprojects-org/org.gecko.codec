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
package org.gecko.codec.info.helper;

import static org.eclipse.emfcloud.jackson.databind.EMFContext.findEClass;
import static org.eclipse.emfcloud.jackson.databind.EMFContext.getURI;

import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emfcloud.jackson.databind.EMFContext;
import org.eclipse.emfcloud.jackson.resource.JsonResource;
import org.gecko.codec.info.codecinfo.CodecValueReader;
import org.gecko.codec.info.codecinfo.CodecValueWriter;

import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * 
 * @author ilenia
 * @since Aug 2, 2024
 */
public class CodecIOHelper {
	
	
	
	public static final CodecValueReader<Object, String> DEFAULT_ID_VALUE_READER = new CodecValueReader<>() {

		@Override
		public String getName() {
			return "DEFAULT_ID_READER";
		}

		@Override
		public String readValue(Object value, DeserializationContext context) {
			return value.toString();	
		}
	};


	public static final CodecValueWriter<EObject, Object> DEFAULT_ID_VALUE_WRITER = new CodecValueWriter<>() {
		@Override
		public String getName() {
			return "DEFAULT_ID_WRITER";
		}

		@Override
		public Object writeValue(EObject value, SerializerProvider provider) {
			Resource resource = EMFContext.getResource(provider, value);
			Object id;
			if (resource instanceof JsonResource) {
				id = ((JsonResource) resource).getID(value);
			} else {
				id = EMFContext.getURI(provider, value).fragment();
			}
			return id;
		}
	};
			

	public static final CodecValueWriter<EObject, Object> IDFIELD_VALUE_WRITER = new CodecValueWriter<>() {
		@Override
		public String getName() {
			return "ID_FIELD_WRITER";
		}

		@Override
		public Object writeValue(EObject value, SerializerProvider provider) {
			return EcoreUtil.getID(value);
		}
	};
			

	public static final CodecValueReader<String, EClass> DEFAULT_ECLASS_READER = new CodecValueReader<>() {

		@Override
		public String getName() {
			return "DEFAULT_ECLASS_READER";
		}

		@Override
		public EClass readValue(String value, DeserializationContext context) {
			return findEClass(context, value);
		}
	};
	
	
	public static final CodecValueWriter<EClass, String[]> URIS_WRITER = new CodecValueWriter<>() {

		@Override
		public String getName() {
			return "URIS_WRITER";
		}

		@Override
		public String[] writeValue(EClass value, SerializerProvider provider) {
			return getURIs(provider, value);
		}		
	};
			
			

	public static final CodecValueWriter<EClass, String> URI_WRITER = new CodecValueWriter<>() {

		@Override
		public String getName() {
			return "URI_WRITER";
		}

		@Override
		public String writeValue(EClass value, SerializerProvider provider) {
			return getURI(provider, value).toString();
		}		
	};
			
	public static final CodecValueReader<String, EClass> READ_BY_NAME = new CodecValueReader<>() {

		@Override
		public String getName() {
			return "READ_BY_NAME";
		}

		@Override
		public EClass readValue(String value, DeserializationContext context) {
			return EMFContext.findEClassByName(context, value);
		}
	};
			

	public static final CodecValueWriter<EClass, String> WRITE_BY_NAME = new CodecValueWriter<>() {

		@Override
		public String getName() {
			return "WRITE_BY_NAME";
		}

		@Override
		public String writeValue(EClass value, SerializerProvider provider) {
			return value != null ? value.getName() : null;
		}		
	};
			
	
	public static final CodecValueReader<String, EClass> READ_BY_CLASS = new CodecValueReader<>() {

		@Override
		public String getName() {
			return "READ_BY_CLASS";
		}

		@Override
		public EClass readValue(String value, DeserializationContext context) {
			return EMFContext.findEClassByQualifiedName(context, value);
		}
	};
			

	public static final CodecValueWriter<EClass, String> WRITE_BY_CLASS_NAME = new CodecValueWriter<>() {

		@Override
		public String getName() {
			return "WRITE_BY_CLASS_NAME";
		}

		@Override
		public String writeValue(EClass value, SerializerProvider provider) {
			return value != null ? value.getInstanceClassName() : null;
		}
		
	};
	
	private static String[] getURIs(final DatabindContext ctxt, final EObject object) {
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
