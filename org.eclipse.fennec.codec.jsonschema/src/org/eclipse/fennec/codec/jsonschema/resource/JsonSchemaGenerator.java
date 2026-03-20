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
package org.eclipse.fennec.codec.jsonschema.resource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fennec.codec.jsonschema.options.CodecJsonSchemaOptionsBuilder;
import org.gecko.emf.osgi.constants.EMFNamespaces;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * OSGi service facade that generates a JSON Schema string from an EClass, EPackage, or URI.
 *
 * <p>Wires together the existing JSON Schema serialization infrastructure with sensible
 * defaults so callers do not have to set up ResourceSets, options maps, or output streams
 * manually.</p>
 *
 * @author Data In Motion
 * @since Feb 2026
 */
@Component(name = "JsonSchemaGenerator", service = JsonSchemaGenerator.class)
public class JsonSchemaGenerator {

	@Reference(target = "(" + EMFNamespaces.EMF_MODEL_CONTENT_TYPE + "=application/schema+json)")
	private ResourceSet resourceSet;

	/**
	 * Generates a JSON Schema string for the given EClass.
	 *
	 * @param eClass the EClass to generate a schema for
	 * @return the JSON Schema as a UTF-8 string
	 * @throws IOException if serialization fails
	 */
	public String generateSchema(EClass eClass) throws IOException {
		Map<String, Object> options = CodecJsonSchemaOptionsBuilder.create()
				.rootObject(EcorePackage.Literals.ECLASS)
				.serializeType(false)
				.forClass(EcorePackage.Literals.ECLASS)
					.schemaFeatureKey("definitions")
				.build();

		Resource resource = resourceSet.createResource(URI.createURI("memory://schema.jsonschema"), "application/schema+json");
		resource.getContents().add(EcoreUtil.copy(eClass));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			resource.save(baos, options);
		} finally {
			resourceSet.getResources().remove(resource);
		}
		return baos.toString(StandardCharsets.UTF_8);
	}

	/**
	 * Generates a JSON Schema string for the given EPackage.
	 *
	 * @param ePackage the EPackage to generate a schema for
	 * @return the JSON Schema as a UTF-8 string
	 * @throws IOException if serialization fails
	 */
	public String generateSchema(EPackage ePackage) throws IOException {
		Map<String, Object> options = CodecJsonSchemaOptionsBuilder.create()
				.rootObject(EcorePackage.Literals.EPACKAGE)
				.serializeType(false)
				.forClass(EcorePackage.Literals.EPACKAGE)
					.schemaFeatureKey("definitions")
				.build();

		Resource resource = resourceSet.createResource(URI.createURI("memory://schema.jsonschema"), "application/schema+json");
		resource.getContents().add(EcoreUtil.copy(ePackage));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			resource.save(baos, options);
		} finally {
			resourceSet.getResources().remove(resource);
		}
		return baos.toString(StandardCharsets.UTF_8);
	}

	/**
	 * Generates a JSON Schema string for the given URI.
	 *
	 * <p>If the URI contains {@code #}, it is treated as {@code nsURI#ClassName} and an
	 * EClass schema is generated. Otherwise it is treated as a plain package nsURI and an
	 * EPackage schema is generated.</p>
	 *
	 * @param uri the nsURI of an EPackage, optionally followed by {@code #ClassName}
	 * @return the JSON Schema as a UTF-8 string
	 * @throws IOException if serialization fails
	 * @throws IllegalArgumentException if the EPackage or EClass cannot be resolved
	 */
	public String generateSchema(String uri) throws IOException {
		if (uri.contains("#")) {
			int hashIdx = uri.indexOf('#');
			String nsUri = uri.substring(0, hashIdx);
			String className = uri.substring(hashIdx + 1);
			EPackage pkg = resourceSet.getPackageRegistry().getEPackage(nsUri);
			if (pkg == null) {
				throw new IllegalArgumentException("Cannot resolve EPackage for nsURI: " + nsUri);
			}
			EClassifier classifier = pkg.getEClassifier(className);
			if (!(classifier instanceof EClass)) {
				throw new IllegalArgumentException("Cannot resolve EClass '" + className + "' in package: " + nsUri);
			}
			return generateSchema((EClass) classifier);
		} else {
			EPackage pkg = resourceSet.getPackageRegistry().getEPackage(uri);
			if (pkg == null) {
				throw new IllegalArgumentException("Cannot resolve EPackage for nsURI: " + uri);
			}
			return generateSchema(pkg);
		}
	}
}
