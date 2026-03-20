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
package org.eclipse.fennec.codec.jsonschema.writers;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.fennec.codec.jackson.module.CodecModule;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

/**
 * Serializer that produces a JSON Schema wrapper document containing a single
 * array property whose {@code items} use a {@code oneOf} pointing (via
 * {@code $ref}) to a caller-supplied list of EClasses. All EClasses and their
 * transitive dependencies are placed in the {@code definitions} (or
 * {@code $defs}) section.
 *
 * <p>The generated structure looks like:
 * <pre>
 * {
 *   "$schema": "https://json-schema.org/draft/2020-12/schema",
 *   "$id": "...",
 *   "title": "...",
 *   "description": "...",
 *   "type": "object",
 *   "properties": {
 *     "&lt;rootArrayName&gt;": {
 *       "type": "array",
 *       "items": {
 *         "oneOf": [
 *           { "$ref": "#/definitions/EClass1" },
 *           { "$ref": "#/definitions/EClass2" }
 *         ]
 *       }
 *     }
 *   },
 *   "required": ["&lt;rootArrayName&gt;"],
 *   "definitions": {
 *     "EClass1": { ... },
 *     "EClass2": { ... },
 *     ... transitive deps ...
 *   }
 * }
 * </pre>
 *
 * @author Data In Motion
 * @since Feb 2026
 */
public class MultiEClassToJsonSchemaSerializer extends ValueSerializer<EObject> {

	private static final String JSONSCHEMA_ANNOTATION_SOURCE = "http://fennec.eclipse.org/jsonschema";

	private final List<EClass> oneOfEClasses;
	private final String rootArrayName;
	private final String schemaTitle;
	private final String schemaId;
	private final String schemaDescription;
	private final String schemaFeature;
	private final JsonSchemaSerializationHelper helper;

	/**
	 * Creates a new multi-EClass serializer.
	 *
	 * @param oneOfEClasses   the EClasses to include in the oneOf array
	 * @param rootArrayName   the name of the root array property (e.g. "results")
	 * @param schemaTitle     optional title for the schema document
	 * @param schemaId        optional $id for the schema document
	 * @param schemaDescription optional description for the schema document
	 * @param schemaFeature   the definitions section name ("definitions" or "$defs")
	 * @param codecModule     the codec module for name resolution
	 * @param allFieldsRequired whether to mark all fields as required
	 */
	public MultiEClassToJsonSchemaSerializer(List<EClass> oneOfEClasses, String rootArrayName,
			String schemaTitle, String schemaId, String schemaDescription,
			String schemaFeature, CodecModule codecModule, boolean allFieldsRequired) {
		this.oneOfEClasses = oneOfEClasses;
		this.rootArrayName = rootArrayName;
		this.schemaTitle = schemaTitle;
		this.schemaId = schemaId;
		this.schemaDescription = schemaDescription;
		this.schemaFeature = schemaFeature != null ? schemaFeature : "definitions";
		this.helper = new JsonSchemaSerializationHelper(this.schemaFeature, codecModule, allFieldsRequired);
	}

	@Override
	public void serialize(EObject value, JsonGenerator gen, SerializationContext ctxt) throws JacksonException {
		gen.writeStartObject();

		// Schema metadata
		gen.writeStringProperty("$schema", "https://json-schema.org/draft/2020-12/schema");
		if (schemaId != null) {
			gen.writeStringProperty("$id", schemaId);
		}
		if (schemaTitle != null) {
			gen.writeStringProperty("title", schemaTitle);
		}
		if (schemaDescription != null) {
			gen.writeStringProperty("description", schemaDescription);
		}

		gen.writeStringProperty("type", "object");

		// Root array property with oneOf items
		gen.writeObjectPropertyStart("properties");
		gen.writeObjectPropertyStart(rootArrayName);
		gen.writeStringProperty("type", "array");
		gen.writeObjectPropertyStart("items");
		gen.writeArrayPropertyStart("oneOf");
		for (EClass eClass : oneOfEClasses) {
			gen.writeStartObject();
			gen.writeStringProperty("$ref", "#/" + schemaFeature + "/" + helper.getOriginalName(eClass));
			gen.writeEndObject();
		}
		gen.writeEndArray(); // oneOf
		gen.writeEndObject(); // items
		gen.writeEndObject(); // rootArrayName property
		gen.writeEndObject(); // properties

		// required: [rootArrayName]
		gen.writeArrayPropertyStart("required");
		gen.writeString(rootArrayName);
		gen.writeEndArray();

		// Collect all classifiers (oneOf EClasses + transitive deps) for definitions
		Set<EClassifier> allDefinitions = collectAllDefinitions();
		writeDefinitions(allDefinitions, gen, ctxt);

		gen.writeEndObject();
	}

	/**
	 * Collects all classifiers that must appear in the definitions section:
	 * the oneOf EClasses themselves, plus all classifiers reachable from them
	 * via EReferences (BFS traversal, excluding artificial/variant classifiers).
	 */
	private Set<EClassifier> collectAllDefinitions() {
		Set<EClassifier> collected = new LinkedHashSet<>();
		Set<EClass> visited = new HashSet<>();
		Queue<EClass> toProcess = new LinkedList<>();

		// Seed with all oneOf EClasses
		for (EClass ec : oneOfEClasses) {
			collected.add(ec);
			toProcess.add(ec);
		}

		while (!toProcess.isEmpty()) {
			EClass current = toProcess.poll();
			if (visited.contains(current)) {
				continue;
			}
			visited.add(current);

			for (EStructuralFeature feature : current.getEAllStructuralFeatures()) {
				if (feature instanceof EReference) {
					EReference ref = (EReference) feature;
					EClass refType = ref.getEReferenceType();

					// Artificial classifiers are expanded inline – traverse but don't define
					String artificial = helper.extractAnnotationDetail(refType, JSONSCHEMA_ANNOTATION_SOURCE, "artificial");
					if ("true".equals(artificial)) {
						if (!visited.contains(refType)) {
							toProcess.add(refType);
						}
						continue;
					}

					// Variant classifiers are handled inline with their parent
					String variant = helper.extractAnnotationDetail(refType, JSONSCHEMA_ANNOTATION_SOURCE, "variant");
					if (variant != null) {
						continue;
					}

					collected.add(refType);
					if (!visited.contains(refType)) {
						toProcess.add(refType);
					}

					// Also collect concrete descendants for polymorphic oneOf generation
					for (EClass desc : helper.findConcreteDescendants(refType, refType.getEPackage())) {
						collected.add(desc);
						if (!visited.contains(desc)) {
							toProcess.add(desc);
						}
					}
				} else if (feature instanceof EAttribute) {
					EAttribute attr = (EAttribute) feature;
					if (attr.getEAttributeType() instanceof EEnum) {
						collected.add(attr.getEAttributeType());
					}
				}
			}
		}

		return collected;
	}

	/**
	 * Writes the definitions section containing all collected classifiers.
	 */
	private void writeDefinitions(Set<EClassifier> classifiers, JsonGenerator gen, SerializationContext ctxt) {
		gen.writeObjectPropertyStart(schemaFeature);

		for (EClassifier classifier : classifiers) {
			String name = helper.getOriginalName(classifier);
			gen.writeName(name);
			if (classifier instanceof EClass) {
				helper.serializeEClass((EClass) classifier, gen, ctxt);
			} else if (classifier instanceof EEnum) {
				helper.serializeEEnum((EEnum) classifier, gen, ctxt);
			} else if (classifier instanceof EDataType) {
				helper.serializeEDataType((EDataType) classifier, gen, ctxt);
			}
		}

		gen.writeEndObject(); // definitions
	}
}
