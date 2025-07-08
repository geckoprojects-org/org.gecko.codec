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
package org.eclipse.fennec.codec.introspectors;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Value;

import tools.jackson.core.Version;
import tools.jackson.databind.AnnotationIntrospector;
import tools.jackson.databind.cfg.MapperConfig;
import tools.jackson.databind.introspect.Annotated;
import tools.jackson.databind.jsontype.TypeIdResolver;

/**
 * 
 * @author ilenia
 * @since Jul 1, 2025
 */
public class DynamicTypeInfoIntrospector extends AnnotationIntrospector {

	/** serialVersionUID */
	private static final long serialVersionUID = 7979836181318008771L;

	private final Class<?> baseType;
	private final String typeProperty;
	private final Class<? extends TypeIdResolver> resolverClass;

	public DynamicTypeInfoIntrospector(Class<?> baseType,  Class<? extends TypeIdResolver> resolverClass, String typeProperty) {
		this.baseType = baseType;
		this.resolverClass = resolverClass;
		this.typeProperty = typeProperty;
	}
	
	
	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.databind.AnnotationIntrospector#findPolymorphicTypeInfo(tools.jackson.databind.cfg.MapperConfig, tools.jackson.databind.introspect.Annotated)
	 */
	@Override
	public Value findPolymorphicTypeInfo(MapperConfig<?> config, Annotated ann) {
		if(ann.getType() == null) return super.findPolymorphicTypeInfo(config, ann);
		if (ann.getType().getRawClass().equals(baseType)) {
			return JsonTypeInfo.Value.construct(
					JsonTypeInfo.Id.CUSTOM,
					JsonTypeInfo.As.PROPERTY,
					typeProperty,
					null,
					true,
					true
					);
		}
		return super.findPolymorphicTypeInfo(config, ann);
	}

	 @Override
	 public Object findTypeIdResolver(MapperConfig<?> config, Annotated ann) {
		 if (baseType.isAssignableFrom(ann.getRawType())) {
	            return resolverClass;
	        }
		 return super.findTypeIdResolver(config, ann);
	 }
	 

	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.databind.AnnotationIntrospector#version()
	 */
	@Override
	public Version version() {
		return new Version(1, 0, 0, "SNAPSHOT", "org.eclipse.fennec", "org.eclipse.fennec.codec");
	}

}
