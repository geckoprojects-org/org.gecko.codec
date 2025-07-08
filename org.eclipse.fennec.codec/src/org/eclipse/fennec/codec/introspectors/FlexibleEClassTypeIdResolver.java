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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.DatabindContext;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.jsontype.impl.TypeIdResolverBase;

/**
 * 
 * @author ilenia
 * @since Jul 2, 2025
 */
public class FlexibleEClassTypeIdResolver extends TypeIdResolverBase {

	/** serialVersionUID */
	private static final long serialVersionUID = -5382647198018087755L;

	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.databind.jsontype.TypeIdResolver#idFromValue(tools.jackson.databind.DatabindContext, java.lang.Object)
	 */
	@Override
	public String idFromValue(DatabindContext ctxt, Object value) throws JacksonException {
		if (value instanceof EObject eObj) {
            // Optional: decide whether to output URI or just name
            return EcoreUtil.getURI(eObj.eClass()).toString(); // or eClass().getName()
        }
        return null;
	}

	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.databind.jsontype.TypeIdResolver#idFromValueAndType(tools.jackson.databind.DatabindContext, java.lang.Object, java.lang.Class)
	 */
	@Override
	public String idFromValueAndType(DatabindContext ctxt, Object value, Class<?> suggestedType)
			throws JacksonException {
		return idFromValue(ctxt, value);
	}
	
	@Override
    public JavaType typeFromId(DatabindContext context, String id) {
		System.out.println("Resolving ID: " + id);
        EClassifier classifier = null;

//        if (id.contains("://")) {
//            // Looks like a URI
//            classifier = EPackage.Registry.INSTANCE.getEClassifier(id);
//        } else {
            // It's probably a simple name like "Child"
            for (Object ePackageObj : EPackage.Registry.INSTANCE.values()) {
                if (ePackageObj instanceof EPackage ePackage) {
                    classifier = ePackage.getEClassifier(id);
                    if (classifier != null) break;
                }
            }
//        }

        if (!(classifier instanceof EClass eClass)) {
            throw new IllegalArgumentException("Unknown EClass for type: " + id);
        }

        EObject instance = eClass.getEPackage().getEFactoryInstance().create(eClass);
        return context.constructType(instance.getClass()); // Typically DynamicEObjectImpl
    }

	/* 
	 * (non-Javadoc)
	 * @see tools.jackson.databind.jsontype.TypeIdResolver#getMechanism()
	 */
	@Override
	public Id getMechanism() {
		return Id.CUSTOM;
	}

}
