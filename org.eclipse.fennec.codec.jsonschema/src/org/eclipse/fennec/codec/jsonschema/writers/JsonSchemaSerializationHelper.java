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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.fennec.codec.jackson.module.CodecModule;

import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.SerializationContext;

/**
 * Shared utility class for JSON Schema serialization logic.
 * Contains reusable methods for serializing EMF model elements to JSON Schema.
 *
 * @author Claude Code
 * @since Dec 12, 2025
 */
public class JsonSchemaSerializationHelper {

	private static final String GEN_MODEL_ANNOTATION_SOURCE = "http://www.eclipse.org/emf/2002/GenModel";
	private static final String JSONSCHEMA_ANNOTATION_SOURCE = "http://fennec.eclipse.org/jsonschema";
	private static final String EXTENDED_METADATA_ANNOTATION_SOURCE = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData";

	private final String schemaFeature;
	private final CodecModule codecModule;
	private final boolean allFieldsRequired;

	public JsonSchemaSerializationHelper(String schemaFeature, CodecModule codecModule) {
		this(schemaFeature, codecModule, false);
	}

	public JsonSchemaSerializationHelper(String schemaFeature, CodecModule codecModule, boolean allFieldsRequired) {
		this.schemaFeature = schemaFeature != null ? schemaFeature : "definitions";
		this.codecModule = codecModule;
		this.allFieldsRequired = allFieldsRequired;
	}

	/**
	 * Serializes an EClass
	 */
	public void serializeEClass(EClass eClass, JsonGenerator gen, SerializationContext ctxt) {
		gen.writeStartObject();

		String description = extractAnnotationDetail(eClass, GEN_MODEL_ANNOTATION_SOURCE, "documentation");
		if (description != null) {
			gen.writeStringProperty("description", description);
		}

		gen.writeStringProperty("type", "object");

		// Write properties
		if (!eClass.getEStructuralFeatures().isEmpty()) {
			gen.writeObjectPropertyStart("properties");
			for (EStructuralFeature feature : eClass.getEStructuralFeatures()) {
				if (feature instanceof EAttribute) {
					serializeEAttribute((EAttribute) feature, gen, ctxt);
				} else if (feature instanceof EReference) {
					serializeEReference((EReference) feature, gen, ctxt, eClass.getEPackage());
				}
			}
			gen.writeEndObject(); // End properties
		}

		// Collect and write required properties
		List<String> requiredProps = new ArrayList<>();
		for (EStructuralFeature feature : eClass.getEStructuralFeatures()) {
			if (allFieldsRequired || feature.getLowerBound() >= 1) {
				requiredProps.add(getFeatureName(feature));
			}
		}
		if (!requiredProps.isEmpty()) {
			gen.writeArrayPropertyStart("required");
			for (String propName : requiredProps) {
				gen.writeString(propName);
			}
			gen.writeEndArray();
		}

		// Write additionalProperties
		String additionalProperties = extractAnnotationDetail(eClass, JSONSCHEMA_ANNOTATION_SOURCE, "additionalProperties");
		if (additionalProperties != null) {
			writeAdditionalProperties(additionalProperties, gen);
		}

		// Write anyOf constraint if present
		String anyOf = extractAnnotationDetail(eClass, JSONSCHEMA_ANNOTATION_SOURCE, "anyOf");
		if (anyOf != null) {
			writeJsonArrayOrObject(anyOf, "anyOf", gen);
		}

		// Write allOf constraint if present
		String allOf = extractAnnotationDetail(eClass, JSONSCHEMA_ANNOTATION_SOURCE, "allOf");
		if (allOf != null) {
			writeJsonArrayOrObject(allOf, "allOf", gen);
		}

		gen.writeEndObject(); // End object
	}

	/**
	 * Serializes an EAttribute
	 */
	public void serializeEAttribute(EAttribute eAttribute, JsonGenerator gen, SerializationContext ctxt) {
		gen.writeName(getFeatureName(eAttribute));
		gen.writeStartObject();

		String description = extractAnnotationDetail(eAttribute, GEN_MODEL_ANNOTATION_SOURCE, "documentation");
		if (description != null) {
			gen.writeStringProperty("description", description);
		}

		EDataType type = eAttribute.getEAttributeType();

		if (type instanceof EEnum) {
			EEnum eEnum = (EEnum) type;
			// If the attribute itself has no description, fall back to enum-level description
			if (description == null) {
				String enumDescription = getEnumDescription(eEnum);
				if (enumDescription != null) {
					gen.writeStringProperty("description", enumDescription);
				}
			}
			serializeEEnumLiterals(eEnum.getELiterals(), gen);
			gen.writeStringProperty("type", "string");
		} else {
			String jsonType = mapEDataTypeToJsonType(type);
			gen.writeStringProperty("type", jsonType);
		}

		// Write additional JSON Schema properties
		writeAdditionalSchemaProperties(eAttribute, gen);

		gen.writeEndObject(); // End attribute
	}

	/**
	 * Serializes an EReference
	 */
	public void serializeEReference(EReference eReference, JsonGenerator gen, SerializationContext ctxt, EPackage contextPackage) {
		gen.writeName(getFeatureName(eReference));

		EClassifier type = eReference.getEReferenceType();

		// Check if this references an artificial classifier that should be expanded inline
		if (type instanceof EClass) {
			EClass eClass = (EClass) type;
			String artificial = extractAnnotationDetail(eClass, JSONSCHEMA_ANNOTATION_SOURCE, "artificial");

			if ("true".equals(artificial)) {
				// Check if this is a multi-type union base
				String multiType = extractAnnotationDetail(eClass, JSONSCHEMA_ANNOTATION_SOURCE, "multiType");
				if ("true".equals(multiType)) {
					serializeMultiTypeProperty(eClass, gen, ctxt);
					return;
				}

				// Check if this is a variant (child of a oneOf) - should reference parent instead
				String variant = extractAnnotationDetail(eClass, JSONSCHEMA_ANNOTATION_SOURCE, "variant");
				if (variant != null) {
					// This is a variant class - should serialize as the parent's oneOf
					// Find the parent class
					if (!eClass.getESuperTypes().isEmpty()) {
						EClass parentClass = eClass.getESuperTypes().get(0);
						// Serialize the parent's oneOf structure
						serializePropertyLevelOneOf(parentClass, gen, ctxt, contextPackage);
						return;
					} else {
						System.err.println("ERROR: Variant class " + eClass.getName() + " has no parent!");
						return;
					}
				}

				// Check if this is a variant parent (oneOf base)
				if (isArtificialVariantParent(eClass, contextPackage)) {
					// Serialize as inline oneOf
					serializePropertyLevelOneOf(eClass, gen, ctxt, contextPackage);
					return;
				} else {
					// Regular artificial classifier - expand inline as object
					if (eReference.isMany()) {
						gen.writeStartObject();
						gen.writeStringProperty("type", "array");
						gen.writeObjectPropertyStart("items");
						serializeEClassInline(eClass, null, gen, ctxt, contextPackage);
						gen.writeEndObject(); // End items
						gen.writeEndObject(); // End array
					} else {
						serializeEClassInline(eClass, eReference, gen, ctxt, contextPackage);
					}
					return;
				}
			}
		}

		String refAnnotation = extractAnnotationDetail(eReference, JSONSCHEMA_ANNOTATION_SOURCE, "ref");

		// When no explicit $ref annotation is set, check for concrete descendants to
		// produce a oneOf instead of a plain $ref.
		List<EClass> oneOfClasses = null;
		if (refAnnotation == null && type instanceof EClass) {
			oneOfClasses = buildOneOfClasses((EClass) type, contextPackage);
		}

		if (eReference.isMany()) {
			gen.writeStartObject();
			gen.writeStringProperty("type", "array");
			gen.writeObjectPropertyStart("items");

			if (refAnnotation != null) {
				gen.writeStringProperty("$ref", refAnnotation);
			} else if (oneOfClasses != null && !oneOfClasses.isEmpty()) {
				writeOneOfRefs(oneOfClasses, gen);
			} else {
				// Build the $ref path, using original name if available
				String refName = getOriginalName(type);

				// Get namespace path if present
				String namespacePath = extractAnnotationDetail(type, JSONSCHEMA_ANNOTATION_SOURCE, "namespacePath");
				String refPath;
				if (namespacePath != null && !namespacePath.isEmpty()) {
					refPath = "#/" + schemaFeature + "/" + namespacePath + "/" + refName;
				} else {
					refPath = "#/" + schemaFeature + "/" + refName;
				}

				gen.writeStringProperty("$ref", refPath);
			}

			gen.writeEndObject(); // End items

			// Write array-specific properties (minItems, maxItems, etc.)
			writeAdditionalSchemaProperties(eReference, gen);

			gen.writeEndObject(); // End array
		} else {
			gen.writeStartObject();

			if (refAnnotation != null) {
				gen.writeStringProperty("$ref", refAnnotation);
			} else if (oneOfClasses != null && !oneOfClasses.isEmpty()) {
				writeOneOfRefs(oneOfClasses, gen);
			} else {
				// Build the $ref path, using original name if available
				String refName = getOriginalName(type);

				// Get namespace path if present
				String namespacePath = extractAnnotationDetail(type, JSONSCHEMA_ANNOTATION_SOURCE, "namespacePath");
				String refPath;
				if (namespacePath != null && !namespacePath.isEmpty()) {
					refPath = "#/" + schemaFeature + "/" + namespacePath + "/" + refName;
				} else {
					refPath = "#/" + schemaFeature + "/" + refName;
				}

				gen.writeStringProperty("$ref", refPath);
			}

			gen.writeEndObject(); // End reference
		}
	}

	/**
	 * Builds the ordered list of EClasses for a polymorphic {@code oneOf}.
	 *
	 * <p>If {@code baseClass} has concrete descendants (found by searching in its
	 * own package and in {@code contextPackage}), the list is:
	 * <ul>
	 *   <li>the base class itself, if it is concrete (non-abstract)</li>
	 *   <li>all concrete descendants, in package-declaration order</li>
	 * </ul>
	 * Returns an empty list when no concrete descendants exist (caller falls
	 * back to a plain {@code $ref}).
	 */
	public List<EClass> buildOneOfClasses(EClass baseClass, EPackage contextPackage) {
		List<EClass> concreteDescendants = findConcreteDescendants(baseClass, contextPackage);
		if (concreteDescendants.isEmpty()) {
			return List.of();
		}
		List<EClass> result = new ArrayList<>();
		if (!baseClass.isAbstract()) {
			result.add(baseClass);
		}
		result.addAll(concreteDescendants);
		return result;
	}

	/**
	 * Returns all concrete (non-abstract) descendants of {@code baseClass},
	 * searching in the base class's own package and, if different, in
	 * {@code contextPackage}.  Only direct or indirect subclasses that are
	 * themselves concrete are included.
	 *
	 * @param baseClass      the class whose descendants to find
	 * @param contextPackage additional package to search (may be {@code null})
	 * @return ordered list of concrete descendants; empty if none
	 */
	public List<EClass> findConcreteDescendants(EClass baseClass, EPackage contextPackage) {
		Set<EClass> result = new LinkedHashSet<>();
		collectConcreteDescendantsFromPackage(baseClass, baseClass.getEPackage(), result);
		if (contextPackage != null && !contextPackage.equals(baseClass.getEPackage())) {
			collectConcreteDescendantsFromPackage(baseClass, contextPackage, result);
		}
		return new ArrayList<>(result);
	}

	private void collectConcreteDescendantsFromPackage(EClass baseClass, EPackage pkg, Set<EClass> result) {
		if (pkg == null) {
			return;
		}
		pkg.getEClassifiers().stream()
				.filter(c -> c instanceof EClass)
				.map(c -> (EClass) c)
				.filter(c -> !c.isAbstract() && c.getEAllSuperTypes().contains(baseClass))
				.forEach(result::add);
	}

	/**
	 * Writes a {@code oneOf} JSON array whose entries are {@code $ref} objects
	 * pointing to the supplied EClasses.
	 */
	private void writeOneOfRefs(List<EClass> classes, JsonGenerator gen) {
		gen.writeArrayPropertyStart("oneOf");
		for (EClass ec : classes) {
			gen.writeStartObject();
			String refName = getOriginalName(ec);
			String namespacePath = extractAnnotationDetail(ec, JSONSCHEMA_ANNOTATION_SOURCE, "namespacePath");
			String refPath;
			if (namespacePath != null && !namespacePath.isEmpty()) {
				refPath = "#/" + schemaFeature + "/" + namespacePath + "/" + refName;
			} else {
				refPath = "#/" + schemaFeature + "/" + refName;
			}
			gen.writeStringProperty("$ref", refPath);
			gen.writeEndObject();
		}
		gen.writeEndArray();
	}

	/**
	 * Serializes an EClass inline (for artificial classifiers)
	 * @param eClass The class to serialize
	 * @param eReference Optional reference that points to this class (for extracting default values)
	 */
	public void serializeEClassInline(EClass eClass, EReference eReference, JsonGenerator gen, SerializationContext ctxt, EPackage contextPackage) {
		gen.writeStartObject();

		String description = extractAnnotationDetail(eClass, GEN_MODEL_ANNOTATION_SOURCE, "documentation");
		if (description != null) {
			gen.writeStringProperty("description", description);
		}

		gen.writeStringProperty("type", "object");

		// Write properties
		if (!eClass.getEStructuralFeatures().isEmpty()) {
			gen.writeObjectPropertyStart("properties");
			for (EStructuralFeature feature : eClass.getEStructuralFeatures()) {
				if (feature instanceof EAttribute) {
					serializeEAttribute((EAttribute) feature, gen, ctxt);
				} else if (feature instanceof EReference) {
					serializeEReference((EReference) feature, gen, ctxt, contextPackage);
				}
			}
			gen.writeEndObject(); // End properties
		}

		// Collect and write required properties
		List<String> requiredProps = new ArrayList<>();
		for (EStructuralFeature feature : eClass.getEStructuralFeatures()) {
			if (allFieldsRequired || feature.getLowerBound() >= 1) {
				requiredProps.add(getFeatureName(feature));
			}
		}
		if (!requiredProps.isEmpty()) {
			gen.writeArrayPropertyStart("required");
			for (String propName : requiredProps) {
				gen.writeString(propName);
			}
			gen.writeEndArray();
		}

		// Write additionalProperties
		String additionalProperties = extractAnnotationDetail(eClass, JSONSCHEMA_ANNOTATION_SOURCE, "additionalProperties");
		if (additionalProperties != null) {
			writeAdditionalProperties(additionalProperties, gen);
		}

		// Write default value from the reference if present
		if (eReference != null) {
			String defaultValue = extractAnnotationDetail(eReference, JSONSCHEMA_ANNOTATION_SOURCE, "default");
			if (defaultValue != null) {
				writeDefaultValue(defaultValue, gen);
			}
		}

		gen.writeEndObject(); // End object
	}

	/**
	 * Serializes a multi-type property (e.g., "type": ["integer", "string"])
	 */
	public void serializeMultiTypeProperty(EClass unionBase, JsonGenerator gen, SerializationContext ctxt) {
		gen.writeStartObject();

		// Read the type array from annotation
		String typeArrayStr = extractAnnotationDetail(unionBase, JSONSCHEMA_ANNOTATION_SOURCE, "typeArray");
		if (typeArrayStr != null) {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode typeArrayNode = mapper.readTree(typeArrayStr);
			gen.writeName("type");
			gen.writeTree(typeArrayNode);
		}

		// Write default value if present
		String defaultValue = extractAnnotationDetail(unionBase, JSONSCHEMA_ANNOTATION_SOURCE, "default");
		if (defaultValue != null) {
			writeDefaultValue(defaultValue, gen);
		}

		// Write other schema properties that were preserved
		String[] schemaProperties = {
			"minLength", "maxLength", "pattern", "format",
			"minimum", "maximum", "exclusiveMinimum", "exclusiveMaximum",
			"multipleOf", "const", "title", "description", "examples"
		};

		for (String propertyName : schemaProperties) {
			String value = extractAnnotationDetail(unionBase, JSONSCHEMA_ANNOTATION_SOURCE, propertyName);
			if (value != null) {
				try {
					ObjectMapper mapper = new ObjectMapper();
					JsonNode node = mapper.readTree(value);
					gen.writeName(propertyName);
					gen.writeTree(node);
				} catch (Exception e) {
					// If parsing fails, write as string
					gen.writeStringProperty(propertyName, value);
				}
			}
		}

		gen.writeEndObject(); // End multi-type property
	}

	/**
	 * Serializes a property-level oneOf (artificial variants)
	 */
	public void serializePropertyLevelOneOf(EClass parent, JsonGenerator gen, SerializationContext ctxt, EPackage contextPackage) {
		gen.writeStartObject();

		List<EClass> variants = findVariantSubclasses(parent, contextPackage);

		// Write oneOf array
		gen.writeArrayPropertyStart("oneOf");
		for (EClass variant : variants) {
			serializeVariantInline(variant, gen, ctxt, contextPackage);
		}
		gen.writeEndArray(); // End oneOf

		// Write default if present on parent
		String defaultValue = extractAnnotationDetail(parent, JSONSCHEMA_ANNOTATION_SOURCE, "default");
		if (defaultValue != null) {
			writeDefaultValue(defaultValue, gen);
		}

		gen.writeEndObject(); // End oneOf container
	}

	/**
	 * Serializes a variant inline (not as a $ref)
	 */
	public void serializeVariantInline(EClass variant, JsonGenerator gen, SerializationContext ctxt, EPackage contextPackage) {
		gen.writeStartObject();

		// Check if this variant represents a primitive type or object
		if (variant.getEStructuralFeatures().size() == 1) {
			EStructuralFeature feature = variant.getEStructuralFeatures().get(0);

			if ("value".equals(feature.getName()) && feature instanceof EAttribute) {
				// This is a primitive type variant
				EAttribute attr = (EAttribute) feature;
				String jsonType = mapEDataTypeToJsonType(attr.getEAttributeType());
				gen.writeStringProperty("type", jsonType);
			} else if ("entries".equals(feature.getName())) {
				// This is an object with additionalProperties
				gen.writeStringProperty("type", "object");
				String mapEntryType = extractAnnotationDetail(feature, JSONSCHEMA_ANNOTATION_SOURCE, "mapEntryType");
				if (mapEntryType != null) {
					writeAdditionalProperties(mapEntryType, gen);
				}
			} else if ("items".equals(feature.getName())) {
				// This is an array type variant
				String arrayType = extractAnnotationDetail(variant, JSONSCHEMA_ANNOTATION_SOURCE, "arrayType");
				if ("true".equals(arrayType)) {
					gen.writeStringProperty("type", "array");
					String arrayItems = extractAnnotationDetail(variant, JSONSCHEMA_ANNOTATION_SOURCE, "arrayItems");
					if (arrayItems != null) {
						ObjectMapper mapper = new ObjectMapper();
						JsonNode itemsNode = mapper.readTree(arrayItems);
						gen.writeName("items");
						gen.writeTree(itemsNode);
					}
				} else {
					// Regular variant with properties
					serializeVariantWithProperties(variant, gen, ctxt, contextPackage);
				}
			} else {
				// Regular variant with properties
				serializeVariantWithProperties(variant, gen, ctxt, contextPackage);
			}
		} else if (variant.getEStructuralFeatures().isEmpty()) {
			// Empty variant - just write type object
			gen.writeStringProperty("type", "object");
		} else {
			// Variant with multiple properties
			serializeVariantWithProperties(variant, gen, ctxt, contextPackage);
		}

		gen.writeEndObject(); // End variant
	}

	/**
	 * Serializes a variant that has normal properties
	 */
	public void serializeVariantWithProperties(EClass variant, JsonGenerator gen, SerializationContext ctxt, EPackage contextPackage) {
		gen.writeStringProperty("type", "object");

		// Write properties
		if (!variant.getEStructuralFeatures().isEmpty()) {
			gen.writeObjectPropertyStart("properties");
			for (EStructuralFeature feature : variant.getEStructuralFeatures()) {
				if (feature instanceof EAttribute) {
					serializeEAttribute((EAttribute) feature, gen, ctxt);
				} else if (feature instanceof EReference) {
					serializeEReference((EReference) feature, gen, ctxt, contextPackage);
				}
			}
			gen.writeEndObject(); // End properties
		}

		// Collect and write required properties
		List<String> requiredProps = new ArrayList<>();
		for (EStructuralFeature feature : variant.getEStructuralFeatures()) {
			if (allFieldsRequired || feature.getLowerBound() >= 1) {
				requiredProps.add(getFeatureName(feature));
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
	 * Serializes a discriminated union as a oneOf schema
	 */
	public void serializeDiscriminatedUnion(EClass abstractBase, JsonGenerator gen, SerializationContext ctxt, EPackage ePackage) {
		String name = getOriginalName(abstractBase);
		gen.writeName(name);
		gen.writeStartObject();

		String description = extractAnnotationDetail(abstractBase, GEN_MODEL_ANNOTATION_SOURCE, "documentation");
		if (description != null) {
			gen.writeStringProperty("description", description);
		}

		gen.writeStringProperty("type", "object");
		gen.writeNumberProperty("minProperties", 1);
		gen.writeNumberProperty("maxProperties", 1);
		gen.writeBooleanProperty("unevaluatedProperties", false);

		// Find all concrete subclasses
		List<EClass> subclasses = ePackage.getEClassifiers().stream()
				.filter(c -> c instanceof EClass)
				.map(c -> (EClass) c)
				.filter(c -> c.getESuperTypes().contains(abstractBase))
				.collect(Collectors.toList());

		// Write oneOf array
		gen.writeArrayPropertyStart("oneOf");
		for (EClass subclass : subclasses) {
			String discriminatorKey = extractAnnotationDetail(subclass, JSONSCHEMA_ANNOTATION_SOURCE, "discriminatorKey");
			String configRef = extractAnnotationDetail(subclass, JSONSCHEMA_ANNOTATION_SOURCE, "configRef");

			if (discriminatorKey != null && configRef != null) {
				gen.writeStartObject();
				gen.writeArrayPropertyStart("required");
				gen.writeString(discriminatorKey);
				gen.writeEndArray();

				gen.writeObjectPropertyStart("properties");
				gen.writeObjectPropertyStart(discriminatorKey);
				gen.writeStringProperty("$ref", configRef);
				gen.writeEndObject(); // End discriminatorKey property
				gen.writeEndObject(); // End properties

				gen.writeEndObject(); // End oneOf option
			}
		}
		gen.writeEndArray(); // End oneOf

		gen.writeEndObject(); // End schema
	}

	/**
	 * Serializes a common base class and its variants as a oneOf structure
	 */
	public void serializeContextSpecificVariantsAsOneOf(EClass baseClass, JsonGenerator gen, SerializationContext ctxt, EPackage ePackage) {
		gen.writeStartObject();

		String description = extractAnnotationDetail(baseClass, GEN_MODEL_ANNOTATION_SOURCE, "documentation");
		if (description != null) {
			gen.writeStringProperty("description", description);
		}

		// Find all variant subclasses
		List<EClass> variants = ePackage.getEClassifiers().stream()
				.filter(c -> c instanceof EClass)
				.map(c -> (EClass) c)
				.filter(c -> {
					String variant = extractAnnotationDetail(c, JSONSCHEMA_ANNOTATION_SOURCE, "variant");
					return variant != null && c.getESuperTypes().contains(baseClass);
				})
				.collect(Collectors.toList());

		// Write oneOf array with variants inline
		gen.writeArrayPropertyStart("oneOf");
		for (EClass variant : variants) {
			serializeVariantAsFullSchema(variant, baseClass, gen, ctxt, ePackage);
		}
		gen.writeEndArray(); // End oneOf

		gen.writeEndObject(); // End schema
	}

	/**
	 * Serializes a variant as a full schema (with all properties from base + variant)
	 */
	public void serializeVariantAsFullSchema(EClass variant, EClass baseClass, JsonGenerator gen, SerializationContext ctxt, EPackage contextPackage) {
		gen.writeStartObject();

		// Write title if present
		String title = extractAnnotationDetail(variant, JSONSCHEMA_ANNOTATION_SOURCE, "variant");
		if (title != null) {
			gen.writeStringProperty("title", title);
		}

		String description = extractAnnotationDetail(variant, GEN_MODEL_ANNOTATION_SOURCE, "documentation");
		if (description != null) {
			gen.writeStringProperty("description", description);
		}

		gen.writeStringProperty("type", "object");

		// Collect all properties (from base + variant)
		List<EStructuralFeature> allFeatures = new ArrayList<>();
		allFeatures.addAll(baseClass.getEStructuralFeatures());
		allFeatures.addAll(variant.getEStructuralFeatures());

		// Write properties
		if (!allFeatures.isEmpty()) {
			gen.writeObjectPropertyStart("properties");
			for (EStructuralFeature feature : allFeatures) {
				if (feature instanceof EAttribute) {
					serializeEAttribute((EAttribute) feature, gen, ctxt);
				} else if (feature instanceof EReference) {
					serializeEReference((EReference) feature, gen, ctxt, contextPackage);
				}
			}
			gen.writeEndObject(); // End properties
		}

		// Collect and write required properties
		List<String> requiredProps = new ArrayList<>();
		for (EStructuralFeature feature : allFeatures) {
			if (allFieldsRequired || feature.getLowerBound() >= 1) {
				requiredProps.add(getFeatureName(feature));
			}
		}
		if (!requiredProps.isEmpty()) {
			gen.writeArrayPropertyStart("required");
			for (String propName : requiredProps) {
				gen.writeString(propName);
			}
			gen.writeEndArray();
		}

		// Write additionalProperties
		String additionalProperties = extractAnnotationDetail(variant, JSONSCHEMA_ANNOTATION_SOURCE, "additionalProperties");
		if (additionalProperties != null) {
			writeAdditionalProperties(additionalProperties, gen);
		}

		gen.writeEndObject(); // End variant schema
	}

	/**
	 * Serializes an EEnum
	 */
	public void serializeEEnum(EEnum eEnum, JsonGenerator gen, SerializationContext ctxt) {
		gen.writeStartObject();

		String description = getEnumDescription(eEnum);
		if (description != null) {
			gen.writeStringProperty("description", description);
		}

		serializeEEnumLiterals(eEnum.getELiterals(), gen);
		gen.writeStringProperty("type", "string");

		gen.writeEndObject();
	}

	/**
	 * Returns the description to use for an EEnum in JSON Schema.
	 *
	 * <p>Strategy:
	 * <ol>
	 *   <li>If the EEnum type itself has a GenModel {@code documentation} annotation, use it.</li>
	 *   <li>Otherwise, collect GenModel {@code documentation} from each literal and build a
	 *       combined string of the form {@code "LITERAL1=description1, LITERAL2=description2, ..."}.
	 *       Literals without documentation are omitted.</li>
	 *   <li>If neither source provides any text, returns {@code null}.</li>
	 * </ol>
	 *
	 * @param eEnum the enum to describe
	 * @return a description string, or {@code null} if no documentation is available
	 */
	public String getEnumDescription(EEnum eEnum) {
		// 1. Type-level documentation takes priority
		String typeDoc = extractAnnotationDetail(eEnum, GEN_MODEL_ANNOTATION_SOURCE, "documentation");
		if (typeDoc != null) {
			return typeDoc;
		}

		// 2. Fall back to per-literal documentation
		StringBuilder sb = new StringBuilder();
		for (EEnumLiteral literal : eEnum.getELiterals()) {
			String literalDoc = extractAnnotationDetail(literal, GEN_MODEL_ANNOTATION_SOURCE, "documentation");
			if (literalDoc != null) {
				if (sb.length() > 0) {
					sb.append(", ");
				}
				sb.append(literal.getLiteral()).append("=").append(literalDoc);
			}
		}
		return sb.length() > 0 ? sb.toString() : null;
	}

	/**
	 * Serializes enum literals
	 */
	public void serializeEEnumLiterals(List<EEnumLiteral> literals, JsonGenerator gen) {
		gen.writeArrayPropertyStart("enum");
		for (EEnumLiteral literal : literals) {
			gen.writeString(literal.getLiteral());
		}
		gen.writeEndArray();
	}

	/**
	 * Serializes an EDataType
	 */
	public void serializeEDataType(EDataType eDataType, JsonGenerator gen, SerializationContext ctxt) {
		gen.writeStartObject();

		String description = extractAnnotationDetail(eDataType, GEN_MODEL_ANNOTATION_SOURCE, "documentation");
		if (description != null) {
			gen.writeStringProperty("description", description);
		}

		String jsonType = mapEDataTypeToJsonType(eDataType);
		gen.writeStringProperty("type", jsonType);

		gen.writeEndObject();
	}

	/**
	 * Maps EDataType to JSON Schema type
	 */
	public String mapEDataTypeToJsonType(EDataType eDataType) {
		if (EcorePackage.Literals.ESTRING.equals(eDataType)) {
			return "string";
		} else if (EcorePackage.Literals.EINT.equals(eDataType)
				|| EcorePackage.Literals.EINTEGER_OBJECT.equals(eDataType)
				|| EcorePackage.Literals.EBIG_INTEGER.equals(eDataType)) {
			return "integer";
		} else if (EcorePackage.Literals.EDOUBLE.equals(eDataType)
				|| EcorePackage.Literals.EDOUBLE_OBJECT.equals(eDataType)
				|| EcorePackage.Literals.EFLOAT.equals(eDataType)
				|| EcorePackage.Literals.EFLOAT_OBJECT.equals(eDataType)
				|| EcorePackage.Literals.EBIG_DECIMAL.equals(eDataType)) {
			return "number";
		} else if (EcorePackage.Literals.EBOOLEAN.equals(eDataType)
				|| EcorePackage.Literals.EBOOLEAN_OBJECT.equals(eDataType)) {
			return "boolean";
		}
		return "string"; // Default fallback
	}

	/**
	 * Writes additionalProperties (can be boolean or object)
	 */
	public void writeAdditionalProperties(String additionalProperties, JsonGenerator gen) {
		if (additionalProperties.contains("{")) {
			// It's a JSON object
			ObjectMapper mapper = new ObjectMapper();
			JsonNode node = mapper.readTree(additionalProperties);
			gen.writeName("additionalProperties");
			gen.writeTree(node);
		} else {
			// It's a boolean
			gen.writeBooleanProperty("additionalProperties", Boolean.parseBoolean(additionalProperties));
		}
	}

	/**
	 * Writes a JSON array or object property (used for anyOf, allOf, etc.)
	 */
	public void writeJsonArrayOrObject(String jsonValue, String propertyName, JsonGenerator gen) {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(jsonValue);
		gen.writeName(propertyName);
		gen.writeTree(node);
	}

	/**
	 * Writes a default value (can be string, number, boolean, or object)
	 */
	public void writeDefaultValue(String defaultValue, JsonGenerator gen) {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(defaultValue);
		gen.writeName("default");
		gen.writeTree(node);
	}

	/**
	 * Writes additional JSON Schema properties from annotations
	 * (minItems, maxItems, minLength, maxLength, pattern, format, minimum, maximum, etc.)
	 */
	public void writeAdditionalSchemaProperties(EStructuralFeature feature, JsonGenerator gen) {
		// List of JSON Schema properties to write back
		String[] schemaProperties = {
			"default", "minItems", "maxItems", "uniqueItems",
			"minLength", "maxLength", "pattern", "format",
			"minimum", "maximum", "exclusiveMinimum", "exclusiveMaximum",
			"multipleOf", "const", "examples"
		};

		// Special handling for properties that need parsing
		String[] jsonProperties = {"default", "const", "examples"};
		String[] booleanProperties = {"uniqueItems", "exclusiveMinimum", "exclusiveMaximum"};
		String[] numberProperties = {"minItems", "maxItems", "minLength", "maxLength",
									 "minimum", "maximum", "multipleOf"};

		for (String propertyName : schemaProperties) {
			String value = extractAnnotationDetail(feature, JSONSCHEMA_ANNOTATION_SOURCE, propertyName);
			if (value != null) {
				try {
					if (contains(jsonProperties, propertyName)) {
						// Parse as JSON and write as tree
						ObjectMapper mapper = new ObjectMapper();
						JsonNode node = mapper.readTree(value);
						gen.writeName(propertyName);
						gen.writeTree(node);
					} else if (contains(booleanProperties, propertyName)) {
						// Write as boolean
						gen.writeBooleanProperty(propertyName, Boolean.parseBoolean(value));
					} else if (contains(numberProperties, propertyName)) {
						// Parse as number (could be int or double)
						if (value.contains(".")) {
							gen.writeNumberProperty(propertyName, Double.parseDouble(value));
						} else {
							gen.writeNumberProperty(propertyName, Integer.parseInt(value));
						}
					} else {
						// Write as string
						gen.writeStringProperty(propertyName, value);
					}
				} catch (Exception e) {
					// If parsing fails, write as string
					gen.writeStringProperty(propertyName, value);
				}
			}
		}

		// Special handling for title and description from json schema annotations
		String title = extractAnnotationDetail(feature, JSONSCHEMA_ANNOTATION_SOURCE, "title");
		if (title != null) {
			try {
				ObjectMapper mapper = new ObjectMapper();
				JsonNode node = mapper.readTree(title);
				if (node.isString()) {
					gen.writeStringProperty("title", node.asString());
				} else {
					gen.writeName("title");
					gen.writeTree(node);
				}
			} catch (Exception e) {
				gen.writeStringProperty("title", title);
			}
		}

		String description = extractAnnotationDetail(feature, JSONSCHEMA_ANNOTATION_SOURCE, "description");
		if (description != null && extractAnnotationDetail(feature, GEN_MODEL_ANNOTATION_SOURCE, "documentation") == null) {
			try {
				ObjectMapper mapper = new ObjectMapper();
				JsonNode node = mapper.readTree(description);
				if (node.isString()) {
					gen.writeStringProperty("description", node.asString());
				} else {
					gen.writeName("description");
					gen.writeTree(node);
				}
			} catch (Exception e) {
				gen.writeStringProperty("description", description);
			}
		}
	}

	/**
	 * Checks if an EClass is an artificial classifier that serves as a parent for variants
	 */
	public boolean isArtificialVariantParent(EClass eClass, EPackage contextPackage) {
		// Check if this is an artificial classifier (oneOf parent)
		String artificial = extractAnnotationDetail(eClass, JSONSCHEMA_ANNOTATION_SOURCE, "artificial");
		if (!"true".equals(artificial)) {
			return false;
		}

		// Check if there are variant subclasses
		List<EClass> variants = findVariantSubclasses(eClass, contextPackage);
		return !variants.isEmpty();
	}

	/**
	 * Finds all variant subclasses of a parent class
	 */
	public List<EClass> findVariantSubclasses(EClass parent, EPackage ePackage) {
		return ePackage.getEClassifiers().stream()
				.filter(c -> c instanceof EClass)
				.map(c -> (EClass) c)
				.filter(c -> {
					String variant = extractAnnotationDetail(c, JSONSCHEMA_ANNOTATION_SOURCE, "variant");
					return variant != null && c.getESuperTypes().contains(parent);
				})
				.collect(Collectors.toList());
	}

	/**
	 * Returns whether all fields should be treated as required, regardless of lowerBound.
	 */
	public boolean isAllFieldsRequired() {
		return allFieldsRequired;
	}

	/**
	 * Extracts annotation detail from model element
	 */
	public String extractAnnotationDetail(EModelElement modelElement, String source, String detailKey) {
		EAnnotation annotation = modelElement.getEAnnotation(source);
		if (annotation == null) {
			return null;
		}
		return annotation.getDetails().get(detailKey);
	}

	/**
	 * Gets the name to use for a feature when serializing to JSON Schema.
	 * Respects the CodecModule option to use names from extended metadata.
	 *
	 * @param feature The structural feature
	 * @return The name to use in the JSON Schema
	 */
	public String getFeatureName(EStructuralFeature feature) {
		// If the option is enabled, check for extended metadata name
		if (codecModule != null && codecModule.isUseNamesFromExtendedMetaData()) {
			String extendedName = extractAnnotationDetail(feature, EXTENDED_METADATA_ANNOTATION_SOURCE, "name");
			if (extendedName != null && !extendedName.isEmpty()) {
				return extendedName;
			}
		}

		// Fall back to the feature's regular name
		return feature.getName();
	}

	/**
	 * Gets the original name from annotation or falls back to classifier name.
	 * Checks ExtendedMetaData first (EMF standard), then JSONSCHEMA annotation (backwards compatibility).
	 */
	public String getOriginalName(EClassifier classifier) {
		// Check ExtendedMetaData first (EMF standard way)
		String originalName = extractAnnotationDetail(classifier, EXTENDED_METADATA_ANNOTATION_SOURCE, "name");
		if (originalName != null) {
			return originalName;
		}

		// Fall back to JSONSCHEMA annotation for backwards compatibility
		originalName = extractAnnotationDetail(classifier, JSONSCHEMA_ANNOTATION_SOURCE, "originalName");
		if (originalName != null) {
			return originalName;
		}

		// Finally fall back to the classifier name itself
		return classifier.getName();
	}

	/**
	 * Helper method to check if an array contains a value
	 */
	private boolean contains(String[] array, String value) {
		for (String item : array) {
			if (item.equals(value)) {
				return true;
			}
		}
		return false;
	}
}
