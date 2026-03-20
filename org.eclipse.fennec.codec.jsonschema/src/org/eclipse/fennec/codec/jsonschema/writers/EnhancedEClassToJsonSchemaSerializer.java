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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.fennec.codec.jackson.module.CodecModule;

import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

/**
 * Enhanced EClass to JSON Schema serializer with support for:
 * - Single EClass serialization at root level
 * - Recursive collection of referenced EClasses
 * - Inheritance flattening (includes all eSuperTypes features)
 * - Self-contained schemas with all dependencies in definitions
 *
 * @author Claude Code
 * @since Dec 12, 2025
 */
public class EnhancedEClassToJsonSchemaSerializer extends ValueSerializer<EClass> {

	private static final String GEN_MODEL_ANNOTATION_SOURCE = "http://www.eclipse.org/emf/2002/GenModel";
	private static final String JSONSCHEMA_ANNOTATION_SOURCE = "http://fennec.eclipse.org/jsonschema";
	private static final String EXTENDED_METADATA_ANNOTATION_SOURCE = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData";

	private String schemaFeature;
	private JsonSchemaSerializationHelper helper;

	public EnhancedEClassToJsonSchemaSerializer() {
		this("definitions", null, false);
	}

	public EnhancedEClassToJsonSchemaSerializer(String schemaFeature) {
		this(schemaFeature, null, false);
	}

	public EnhancedEClassToJsonSchemaSerializer(String schemaFeature, CodecModule codecModule) {
		this(schemaFeature, codecModule, false);
	}

	public EnhancedEClassToJsonSchemaSerializer(String schemaFeature, CodecModule codecModule, boolean allFieldsRequired) {
		this.schemaFeature = schemaFeature != null ? schemaFeature : "definitions";
		this.helper = new JsonSchemaSerializationHelper(this.schemaFeature, codecModule, allFieldsRequired);
	}

	@Override
	public void serialize(EClass eClass, JsonGenerator gen, SerializationContext ctxt) {
		gen.writeStartObject();

		// Write metadata
		writeEClassMetadata(eClass, gen);

		// Write top-level properties (flattened from hierarchy)
		writeTopLevelProperties(eClass, gen, ctxt);

		// Collect and write definitions
		Set<EClassifier> referencedClassifiers = collectReferencedClassifiers(eClass);
		writeDefinitions(referencedClassifiers, gen, ctxt);

		gen.writeEndObject();
	}

	/**
	 * Writes EClass-level metadata ($schema, $id, title, description)
	 */
	private void writeEClassMetadata(EClass eClass, JsonGenerator gen) {
		// $schema - check EClass annotation first, then package, then default
		String schema = helper.extractAnnotationDetail(eClass, JSONSCHEMA_ANNOTATION_SOURCE, "schema");
		if (schema == null && eClass.getEPackage() != null) {
			schema = helper.extractAnnotationDetail(eClass.getEPackage(), JSONSCHEMA_ANNOTATION_SOURCE, "schema");
		}
		if (schema == null) {
			schema = "https://json-schema.org/draft/2020-12/schema";
		}
		gen.writeStringProperty("$schema", schema);

		// $id - check EClass annotation first, then build from package nsURI
		String id = helper.extractAnnotationDetail(eClass, JSONSCHEMA_ANNOTATION_SOURCE, "id");
		if (id == null && eClass.getEPackage() != null && eClass.getEPackage().getNsURI() != null) {
			id = eClass.getEPackage().getNsURI() + "#" + eClass.getName();
		}
		if (id != null) {
			gen.writeStringProperty("$id", id);
		}

		// title - check ExtendedMetaData, then originalName, then class name
		String title = helper.extractAnnotationDetail(eClass, EXTENDED_METADATA_ANNOTATION_SOURCE, "name");
		if (title == null) {
			title = helper.extractAnnotationDetail(eClass, JSONSCHEMA_ANNOTATION_SOURCE, "originalName");
		}
		if (title == null) {
			title = eClass.getName();
		}
		if (title != null) {
			gen.writeStringProperty("title", title);
		}

		// description - from GenModel documentation
		String description = helper.extractAnnotationDetail(eClass, GEN_MODEL_ANNOTATION_SOURCE, "documentation");
		if (description != null) {
			gen.writeStringProperty("description", description);
		}
	}

	/**
	 * Writes top-level properties from the target EClass (with inheritance flattened)
	 */
	private void writeTopLevelProperties(EClass eClass, JsonGenerator gen, SerializationContext ctxt) {
		gen.writeStringProperty("type", "object");

		// Write additionalProperties if annotated
		String additionalProperties = helper.extractAnnotationDetail(eClass, JSONSCHEMA_ANNOTATION_SOURCE, "additionalProperties");
		if (additionalProperties != null) {
			helper.writeAdditionalProperties(additionalProperties, gen);
		}

		// Use getEAllStructuralFeatures() to automatically flatten inheritance
		List<EStructuralFeature> allFeatures = eClass.getEAllStructuralFeatures();

		// Write properties
		if (!allFeatures.isEmpty()) {
			gen.writeObjectPropertyStart("properties");
			for (EStructuralFeature feature : allFeatures) {
				if (feature instanceof org.eclipse.emf.ecore.EAttribute) {
					helper.serializeEAttribute((org.eclipse.emf.ecore.EAttribute) feature, gen, ctxt);
				} else if (feature instanceof EReference) {
					helper.serializeEReference((EReference) feature, gen, ctxt, eClass.getEPackage());
				}
			}
			gen.writeEndObject(); // End properties
		}

		// Collect and write required properties (using getFeatureName to respect extended metadata)
		List<String> requiredProps = new ArrayList<>();
		for (EStructuralFeature feature : allFeatures) {
			if (helper.isAllFieldsRequired() || feature.getLowerBound() >= 1) {
				requiredProps.add(helper.getFeatureName(feature));
			}
		}
		if (!requiredProps.isEmpty()) {
			gen.writeArrayPropertyStart("required");
			for (String propName : requiredProps) {
				gen.writeString(propName);
			}
			gen.writeEndArray();
		}
	}

	/**
	 * Collects all referenced classifiers recursively using BFS traversal
	 */
	private Set<EClassifier> collectReferencedClassifiers(EClass rootClass) {
		Set<EClassifier> collected = new LinkedHashSet<>();
		Set<EClass> visitedClasses = new HashSet<>();
		Queue<EClass> toProcess = new LinkedList<>();

		toProcess.add(rootClass);

		while (!toProcess.isEmpty()) {
			EClass current = toProcess.poll();

			if (visitedClasses.contains(current)) {
				continue; // Avoid circular references
			}
			visitedClasses.add(current);

			// Process all features to find references
			for (EStructuralFeature feature : current.getEAllStructuralFeatures()) {
				if (feature instanceof EReference) {
					EReference ref = (EReference) feature;
					EClass refType = ref.getEReferenceType();

					// Check if this is an artificial classifier (handled inline)
					String artificial = helper.extractAnnotationDetail(refType, JSONSCHEMA_ANNOTATION_SOURCE, "artificial");
					if ("true".equals(artificial)) {
						// Artificial classifiers are expanded inline, not in definitions
						// But we still need to process their references
						if (!visitedClasses.contains(refType)) {
							toProcess.add(refType);
						}
						continue;
					}

					// Check if this is a variant classifier (handled inline with parent)
					String variant = helper.extractAnnotationDetail(refType, JSONSCHEMA_ANNOTATION_SOURCE, "variant");
					if (variant != null) {
						// Variant classifiers are not added to definitions
						continue;
					}

					// Add to collected set (unless it's the root class)
					if (!refType.equals(rootClass)) {
						collected.add(refType);
					}

					// Also collect concrete descendants for polymorphic oneOf generation
					List<EClass> concreteDescendants = helper.findConcreteDescendants(refType, refType.getEPackage());
					for (EClass desc : concreteDescendants) {
						if (!desc.equals(rootClass)) {
							collected.add(desc);
						}
						if (!visitedClasses.contains(desc)) {
							toProcess.add(desc);
						}
					}

					// Continue traversing the reference type itself
					if (!visitedClasses.contains(refType)) {
						toProcess.add(refType);
					}
				} else if (feature instanceof org.eclipse.emf.ecore.EAttribute) {
					// Check for EEnum attributes
					org.eclipse.emf.ecore.EAttribute attr = (org.eclipse.emf.ecore.EAttribute) feature;
					if (attr.getEAttributeType() instanceof org.eclipse.emf.ecore.EEnum) {
						org.eclipse.emf.ecore.EEnum eEnum = (org.eclipse.emf.ecore.EEnum) attr.getEAttributeType();
						// Add enum to definitions
						if (!collected.contains(eEnum)) {
							collected.add(eEnum);
						}
					}
				}
			}
		}

		return collected;
	}

	/**
	 * Writes definitions section with all referenced classifiers
	 */
	private void writeDefinitions(Set<EClassifier> classifiers, JsonGenerator gen, SerializationContext ctxt) {
		if (classifiers.isEmpty()) {
			// Write empty definitions
			gen.writeObjectPropertyStart(schemaFeature);
			gen.writeEndObject();
			return;
		}

		gen.writeObjectPropertyStart(schemaFeature);

		// Group classifiers by namespace for organizational structure
		Map<String, List<EClassifier>> namespaceMap = groupClassifiersByNamespace(classifiers);

		// Build a tree structure from flat namespace map
		NamespaceNode root = new NamespaceNode("");
		for (Map.Entry<String, List<EClassifier>> entry : namespaceMap.entrySet()) {
			String namespacePath = entry.getKey();
			List<EClassifier> classifiersInNamespace = entry.getValue();

			if (namespacePath.isEmpty()) {
				// Root level classifiers
				root.classifiers.addAll(classifiersInNamespace);
			} else {
				// Nested namespace - split path and build tree
				String[] pathParts = namespacePath.split("/");
				NamespaceNode current = root;
				for (String part : pathParts) {
					if (!current.children.containsKey(part)) {
						current.children.put(part, new NamespaceNode(part));
					}
					current = current.children.get(part);
				}
				current.classifiers.addAll(classifiersInNamespace);
			}
		}

		// Write the tree structure
		writeNamespaceNode(root, gen, ctxt);

		gen.writeEndObject(); // End definitions
	}

	/**
	 * Writes a namespace node and its children recursively
	 */
	private void writeNamespaceNode(NamespaceNode node, JsonGenerator gen, SerializationContext ctxt) {
		// Write classifiers at this level
		for (EClassifier classifier : node.classifiers) {
			String discriminatedUnion = helper.extractAnnotationDetail(classifier, JSONSCHEMA_ANNOTATION_SOURCE, "discriminatedUnion");
			if ("true".equals(discriminatedUnion)) {
				// This is a discriminated union - serialize as oneOf
				helper.serializeDiscriminatedUnion((EClass) classifier, gen, ctxt, classifier.getEPackage());
			} else {
				// Regular classifier
				serializeClassifier(classifier, gen, ctxt);
			}
		}

		// Write child namespaces
		for (Map.Entry<String, NamespaceNode> entry : node.children.entrySet()) {
			String namespaceName = entry.getKey();
			NamespaceNode childNode = entry.getValue();

			gen.writeObjectPropertyStart(namespaceName);
			writeNamespaceNode(childNode, gen, ctxt);
			gen.writeEndObject(); // End namespace
		}
	}

	/**
	 * Helper class to represent namespace tree structure
	 */
	private static class NamespaceNode {
		String name;
		List<EClassifier> classifiers = new ArrayList<>();
		Map<String, NamespaceNode> children = new LinkedHashMap<>();

		NamespaceNode(String name) {
			this.name = name;
		}
	}

	/**
	 * Groups classifiers by namespace (extracted from namespacePath annotations)
	 */
	private Map<String, List<EClassifier>> groupClassifiersByNamespace(Set<EClassifier> classifiers) {
		Map<String, List<EClassifier>> namespaceMap = new LinkedHashMap<>();
		namespaceMap.put("", new ArrayList<>()); // Default namespace

		for (EClassifier classifier : classifiers) {
			// Extract the namespace path from annotations
			String namespacePath = helper.extractAnnotationDetail(classifier, JSONSCHEMA_ANNOTATION_SOURCE, "namespacePath");

			if (namespacePath != null && !namespacePath.isEmpty()) {
				// Add to the specific namespace
				if (!namespaceMap.containsKey(namespacePath)) {
					namespaceMap.put(namespacePath, new ArrayList<>());
				}
				namespaceMap.get(namespacePath).add(classifier);
			} else {
				// Add to default namespace (root level)
				namespaceMap.get("").add(classifier);
			}
		}

		return namespaceMap;
	}

	/**
	 * Serializes a regular classifier
	 */
	private void serializeClassifier(EClassifier classifier, JsonGenerator gen, SerializationContext ctxt) {
		String name = helper.getOriginalName(classifier);
		gen.writeName(name);

		if (classifier instanceof EClass) {
			EClass eClass = (EClass) classifier;

			// Check if this is a common base class (context-specific variants)
			String commonBase = helper.extractAnnotationDetail(eClass, JSONSCHEMA_ANNOTATION_SOURCE, "commonBase");
			if ("true".equals(commonBase)) {
				// Serialize as oneOf with variants inline
				helper.serializeContextSpecificVariantsAsOneOf(eClass, gen, ctxt, eClass.getEPackage());
			} else {
				helper.serializeEClass(eClass, gen, ctxt);
			}
		} else if (classifier instanceof org.eclipse.emf.ecore.EEnum) {
			helper.serializeEEnum((org.eclipse.emf.ecore.EEnum) classifier, gen, ctxt);
		} else if (classifier instanceof org.eclipse.emf.ecore.EDataType) {
			helper.serializeEDataType((org.eclipse.emf.ecore.EDataType) classifier, gen, ctxt);
		}
	}
}
