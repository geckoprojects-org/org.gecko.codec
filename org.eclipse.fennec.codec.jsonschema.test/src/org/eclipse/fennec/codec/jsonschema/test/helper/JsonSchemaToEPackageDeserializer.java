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
package org.eclipse.fennec.codec.jsonschema.test.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.fennec.codec.info.codecinfo.CodecDeserializer;

import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.node.ArrayNode;

/**
 * 
 * @author ilenia
 * @since Jun 17, 2025
 */
public class JsonSchemaToEPackageDeserializer implements CodecDeserializer<EPackage> {

	private final EcoreFactory ecoreFactory = EcoreFactory.eINSTANCE;
	private static final String GEN_MODEL_ANNOTATION_SOURCE = "http://www.eclipse.org/emf/2002/GenModel";
	private static final String JSONSCHEMA_ANNOTATION_SOURCE = "http://fennec.eclipse.org/jsonschema";

	private Map<String, EClassifier> classifierMap = new HashMap<>();
	private Map<EReference, String> missingRefMap = new HashMap<>();
	int artificialClassifierCounter = 0;
	private static final String ARTIFICIAL_CLASSIFIER_PREFIX = "ArtificialClassifier";
	private Map<EClassifier, List<String>> anyOfRefMap = new HashMap<>();
	private Map<String, EClassifier> cachedClassifiers = new HashMap<>();
	private Map<Map<String, JsonNode>, EClass> parentClassMaps = new HashMap<>(); 

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.fennec.codec.info.codecinfo.CodecDeserializer#getName()
	 */
	@Override
	public String getName() {
		return "JSON_SCHEMA_TO_EPACKAGE_DES";
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.fennec.codec.info.codecinfo.CodecDeserializer#deserialize(tools.jackson.core.JsonParser, tools.jackson.databind.DeserializationContext)
	 */
	@Override
	public EPackage deserialize(JsonParser parser, DeserializationContext ctxt) {
		JsonNode node = ctxt.readTree(parser);
		JsonNode defNode = node.get("definitions");
		if(defNode == null) {
			throw new IllegalArgumentException("Expecting document to have a \"definitions\" node");
		} 
		if(!defNode.isObject()) {
			throw new IllegalArgumentException("Expecting \"definitions\" node to be an object node");
		}
		EPackage ePackage = ecoreFactory.createEPackage();
		for(String property : defNode.propertyNames()) {
			JsonNode classifierNode = defNode.get(property);
			EClassifier eClassifier = null;
			//			1. EEnum
			if(classifierNode.get("enum") != null) {
				eClassifier = createEEnum(classifierNode, property);
			} 
			//			2. EClass/Array/EDataType
			else if(classifierNode.get("type") != null) {
				JsonNode typeNode = classifierNode.get("type");
				//				2.1 EClass
				if(typeNode.isString() && "object".equals(typeNode.asString())) {
					eClassifier = createEClass(classifierNode, property, defNode, Collections.emptyList());
				}
				//				2.2. Array
				else if(typeNode.isString() && "array".equals(typeNode.asString())) {
					eClassifier = createFakeArrayClass(classifierNode, property, defNode);
				} 
				//				2.3 EDataType
				else {
					eClassifier = createEDataTypeFromJsonType(classifierNode, property);
				}
			} 
			if(eClassifier != null) {
				classifierMap.put(eClassifier.getName(), eClassifier);
			}
		}
		missingRefMap.forEach((ref, refTypeName) -> {
			if(classifierMap.containsKey(refTypeName)) {
				ref.setEType(classifierMap.get(refTypeName));
			} else {
				System.out.println(String.format("No EClassifier Name for Reference %s and Classifier %s", ref.getName(), refTypeName));
			}
		});

		anyOfRefMap.forEach((superType, subTypesClassNames) -> {
			subTypesClassNames.forEach(clName -> {
				if(classifierMap.containsKey(clName) && classifierMap.get(clName) instanceof EClass cl) {
					cl.getESuperTypes().add((EClass)superType);
				} else {
					System.out.println(String.format("No EClassifier Name for anyOf %s and Classifier %s", clName, superType.getName()));

				}
			});
		});

		ePackage.getEClassifiers().addAll(classifierMap.values());
		return ePackage;
	}


	private EClass createFakeArrayClass(JsonNode classNode, String name, JsonNode rootNode) {
		if(cachedClassifiers.containsKey(classNode.toString())) {
			return (EClass)cachedClassifiers.get(classNode.toString());
		} 
		EClass eClass = ecoreFactory.createEClass();
		eClass.setName(name);
		eClass.getEAnnotations().add(createEAnnotation(JSONSCHEMA_ANNOTATION_SOURCE, Map.of("source", "TopLevelArray", "artificial", "true")));
		if(classNode.get("description") != null) {
			eClass.getEAnnotations().add(createEAnnotation(GEN_MODEL_ANNOTATION_SOURCE, "documentation", classNode.get("description").asString()));
		}
		if(classNode.get("const") != null) {
			//			TODO: const as array

		} else if(classNode.get("items") != null) {
			EStructuralFeature feature = createEStructuralFeature(classNode, "items", rootNode);
			if(feature != null) {				
				eClass.getEStructuralFeatures().add(feature);
			}
		}
		cachedClassifiers.put(classNode.toString(), eClass);
		return eClass;
	}

	private EAnnotation createEAnnotation(String source, String key, String value) {
		EAnnotation eAnnotation = ecoreFactory.createEAnnotation();
		eAnnotation.setSource(source);
		eAnnotation.getDetails().put(key, value);
		return eAnnotation;
	}

	private EAnnotation createEAnnotation(String source, Map<String, String> details) {
		EAnnotation eAnnotation = ecoreFactory.createEAnnotation();
		eAnnotation.setSource(source);
		details.forEach((k,v)-> {
			eAnnotation.getDetails().put(k, v);
		});
		return eAnnotation;
	}

	private EClass createEClass(JsonNode classNode, String name, JsonNode rootNode, List<EAnnotation> additionalAnnotations) {
		if(cachedClassifiers.containsKey(classNode.toString())) {
			System.out.println("Found existing class");
			return (EClass)cachedClassifiers.get(classNode.toString());
		} 
		EClass eClass = ecoreFactory.createEClass();
		eClass.setName(name);
		if(classNode.get("description") != null) {
			eClass.getEAnnotations().add(createEAnnotation(GEN_MODEL_ANNOTATION_SOURCE, "documentation", classNode.get("description").asString()));
		}
		for(EAnnotation annotation : additionalAnnotations) {
			eClass.getEAnnotations().add(annotation);
		}
		JsonNode requiredNode = classNode.get("required");
		JsonNode propertiesNode = classNode.get("properties");
		if(propertiesNode != null) {
			for(String property : propertiesNode.propertyNames()) {
				EStructuralFeature feature = createEStructuralFeature(propertiesNode.get(property), property, rootNode);
				if(feature != null) {
					if(requiredNode != null && arrayNodeContains(requiredNode, feature.getName())) {
						feature.setLowerBound(1);
					}
					eClass.getEStructuralFeatures().add(feature);
				}
			}
		}
		cachedClassifiers.put(classNode.toString(), eClass);
		classifierMap.put(eClass.getName(), eClass);			
		return eClass;		
	}

	private EClass createEClass(JsonNode classNode, String name, JsonNode rootNode, List<String> ignorePropertiesList,  List<EAnnotation> additionalAnnotations) {
		if(cachedClassifiers.containsKey(classNode.toString())) {
			System.out.println("Found existing class");
			return (EClass)cachedClassifiers.get(classNode.toString());
		} 
		if(ignorePropertiesList == null) ignorePropertiesList = Collections.emptyList();
		EClass eClass = ecoreFactory.createEClass();
		eClass.setName(name);
		if(classNode.get("description") != null) {
			eClass.getEAnnotations().add(createEAnnotation(GEN_MODEL_ANNOTATION_SOURCE, "documentation", classNode.get("description").asString()));
		}
		for(EAnnotation annotation : additionalAnnotations) {
			eClass.getEAnnotations().add(annotation);
		}
		JsonNode requiredNode = classNode.get("required");
		JsonNode propertiesNode = classNode.get("properties");
		if(propertiesNode != null) {
			for(String property : propertiesNode.propertyNames()) {
				if(ignorePropertiesList.contains(property)) continue;
				EStructuralFeature feature = createEStructuralFeature(propertiesNode.get(property), property, rootNode);
				if(feature != null) {
					if(requiredNode != null && arrayNodeContains(requiredNode, feature.getName())) {
						feature.setLowerBound(1);
					}
					eClass.getEStructuralFeatures().add(feature);
				}
			}
		}
		cachedClassifiers.put(classNode.toString(), eClass);
		classifierMap.put(eClass.getName(), eClass);			
		return eClass;		
	}

	private EStructuralFeature createEStructuralFeature(JsonNode propertyNode, String name, JsonNode rootNode) {
		EStructuralFeature feature = null;
		//		1. Non-contained EReference
		if(propertyNode.get("$ref") != null) {
			feature = ecoreFactory.createEReference();
			feature.setName(name);
			((EReference)feature).setContainment(false);
			String refName = propertyNode.get("$ref").asString().replaceFirst("#/definitions/", "");
			if(classifierMap.containsKey(refName.toUpperCase())) {
				feature.setEType(classifierMap.get(refName));
			} else {
				missingRefMap.put(((EReference)feature), refName);
			}
		} 
		else if(propertyNode.get("type") != null) {
			String type = propertyNode.get("type").asString();
			feature = createEStructuralFeatureFromJsonType(type, name, propertyNode, rootNode);
		} 
		else if(propertyNode.get("const") != null) {
			//if there is no type info but a const we should be able to infer the type from the value of const
			String inferredType = getJsonTypeFromConstNode(propertyNode.get("const"));
			feature = createEStructuralFeatureFromJsonType(inferredType, name, propertyNode, rootNode);
		}
		else if(propertyNode.get("anyOf") != null) {
			feature = createMultiValueReference(propertyNode.get("anyOf"), name, rootNode);
			feature.getEAnnotations().add(createEAnnotation(JSONSCHEMA_ANNOTATION_SOURCE, "source", "anyOf"));
		} 
		else if(propertyNode.get("oneOf") != null) {
			feature = createMultiValueReference(propertyNode.get("oneOf"), name, rootNode);
			feature.getEAnnotations().add(createEAnnotation(JSONSCHEMA_ANNOTATION_SOURCE, "source", "oneOf"));
		} else {
			//			throw new IllegalArgumentException(String.format("Not supported case for property %s", name));
			return null;
		}
		if(feature != null) {
			if(propertyNode.get("description") != null) {
				feature.getEAnnotations().add(createEAnnotation(GEN_MODEL_ANNOTATION_SOURCE, "documentation", propertyNode.get("description").asString()));
			}
			if(propertyNode.get("format") != null) {
				feature.getEAnnotations().add(createEAnnotation(JSONSCHEMA_ANNOTATION_SOURCE, "format", propertyNode.get("format").asString()));
			}
			if(propertyNode.get("const") != null) {
				if(feature.getEAnnotation(JSONSCHEMA_ANNOTATION_SOURCE) != null) {
					feature.getEAnnotation(JSONSCHEMA_ANNOTATION_SOURCE).getDetails().put("const", propertyNode.get("const").asString());
				} else {
					feature.getEAnnotations().add(createEAnnotation(JSONSCHEMA_ANNOTATION_SOURCE, "const", propertyNode.get("const").isString() ? propertyNode.get("const").asString() : propertyNode.get("const").toPrettyString()));
				}
			}
		}
		return feature;
	}
	
	String getJsonTypeFromConstNode(JsonNode jsonNode) {
		if(jsonNode.isString()) return "string";
		if(jsonNode.isBigDecimal()) return "bigDecimal";
		if(jsonNode.isBigInteger()) return "bigInteger";
		if(jsonNode.isBinary()) return "binary"; 
		if(jsonNode.isBoolean()) return "boolean";
		if(jsonNode.isDouble()) return "number";
		if(jsonNode.isInt()) return "integer";
		if(jsonNode.isArray()) return "array";
		return "javaObject";
	}

	private EStructuralFeature createEStructuralFeatureFromJsonType(String type, String name, JsonNode propertyNode, JsonNode rootNode) {
		EStructuralFeature feature = null;
		switch(type) {
		case "array":
			//				We have an items schema
			if(propertyNode.get("items") != null) {
				feature = createEStructuralFeature(propertyNode.get("items"), name, rootNode);
				if(propertyNode.get("minItems") != null) {
					feature.setLowerBound(propertyNode.get("minItems").asInt());
				} else {
					feature.setLowerBound(0);
				}
				if(propertyNode.get("maxItems") != null) {
					feature.setUpperBound(propertyNode.get("maxItems").asInt());
				} else {
					feature.setUpperBound(-1);
				}
			}
			//				We have const wo items: so we need to infer the type of the items from const
			else if(propertyNode.get("const") != null) {
				if(!propertyNode.get("const").isArray()) {
					throw new IllegalArgumentException(String.format("Expected ArrayNode for const attribute in array property %s. Instead got %s ", name, propertyNode.get("const").getNodeType()));
				}
//				We look into the first element to try to infer the type
				String inferredArrayType = getJsonTypeFromConstNode(propertyNode.get("const").get(0));
				feature = createEStructuralFeatureFromJsonType(inferredArrayType, name, propertyNode.get("const"), rootNode);
				
			} else {
				throw new IllegalArgumentException(String.format("Not supported case for property %s", name));
			}
			break;
		case "string":
			//				EAttribute of type Enum
			if(propertyNode.get("enum") != null) {
				System.out.println("Creating artificial class " + artificialClassifierCounter + " for property " + name);

				EEnum eEnum = createEEnum(propertyNode, ARTIFICIAL_CLASSIFIER_PREFIX+(artificialClassifierCounter++));

				feature = ecoreFactory.createEAttribute();
				feature.setName(name);
				feature.setEType(eEnum);
			} else {
				//					EAttribute of type EString 
				feature = ecoreFactory.createEAttribute();
				feature.setName(name);
				feature.setEType(getEcoreTypeForJsonType("string"));
			}
			break;
		case "object":
			//				We have a contained EReference and we have to create the EClass for the EDataType of the reference
			EClass eClass = createEClass(propertyNode, ARTIFICIAL_CLASSIFIER_PREFIX+(artificialClassifierCounter++), rootNode, 
					List.of(createEAnnotation(JSONSCHEMA_ANNOTATION_SOURCE, "artificial", "true")));									
			feature = ecoreFactory.createEReference();
			feature.setName(name);
			feature.setEType(eClass);
			((EReference)feature).setContainment(true);
			break;
		default:
			//				EAttribute
			feature = ecoreFactory.createEAttribute();
			feature.setName(name);
			feature.setEType(getEcoreTypeForJsonType(type));
			break;
		}
		return feature;
	}

	//	private EReference createMultiValueReference(JsonNode jsonNode, String name) {
	//		//		We have to create the super class for the types that are listed here
	//		EClass eClass = null;
	//		if(cachedClassifiers.containsKey(jsonNode.toString())) {
	//			System.out.println("Found existing anyOf Class");
	//			eClass = (EClass) cachedClassifiers.get(jsonNode.toString());
	//		} else {
	//			eClass = ecoreFactory.createEClass();
	//			System.out.println("Creating artificial class " + artificialClassifierCounter + " for property " + name);
	//
	//			eClass.setName(ARTIFICIAL_CLASSIFIER_PREFIX+(artificialClassifierCounter++));
	//			classifierMap.put(eClass.getName(), eClass);
	//		}		
	//		if(!anyOfRefMap.containsKey(eClass)) {
	//			anyOfRefMap.put(eClass, new LinkedList<>());
	//		}
	//
	//		for(int af = 0; af < jsonNode.size(); af++) {
	//			JsonNode anyOfItemNode = jsonNode.get(af);
	//			if(anyOfItemNode.get("$ref") != null) {
	//				anyOfRefMap.get(eClass).add(anyOfItemNode.get("$ref").asString().replaceFirst("#/definitions/", ""));
	//			} else if(anyOfItemNode.get("type") != null && "object".equals(anyOfItemNode.get("type").asString())) {
	//				//				we need to create an EClass
	//				System.out.println("Creating artificial class " + artificialClassifierCounter + " for property " + name);
	//
	//				EClass anyOfEclass = createEClass(anyOfItemNode, ARTIFICIAL_CLASSIFIER_PREFIX+(artificialClassifierCounter++));
	//				classifierMap.put(anyOfEclass.getName(), anyOfEclass);
	//				anyOfEclass.getESuperTypes().add(eClass);
	//			} else {
	//				//				Not Supported
	//				throw new IllegalArgumentException("Not supported case for anyOf!");
	//			}				
	//		}
	//		//		Now we create the actual EReference
	//		EReference ref = ecoreFactory.createEReference();
	//		ref.setName(name);
	//		ref.setEType(eClass);
	//		ref.setContainment(false);
	//		return ref;
	//	}

	private EReference createMultiValueReference(JsonNode jsonNode, String name, JsonNode rootNode) {

		//		jsonNode is the anyOf node -> we expect that to be an array
		if(!jsonNode.isArray()) throw new IllegalArgumentException(String.format("anyOf node for property %s expected to be an ArrayNode. Instead is %s!", name, jsonNode.getNodeType().toString()));


		Map<String, JsonNode> refClassesNodes = new HashMap<>();
		for(int i = 0; i < jsonNode.size(); i++) {
			JsonNode subNode = jsonNode.get(i);
			//			We have a reference to another class
			if(subNode.get("$ref") != null) {
				String refClassName = subNode.get("$ref").asString().replace("#/definitions/", "");
				if(rootNode.get(refClassName) != null) {
					refClassesNodes.put(refClassName, rootNode.get(refClassName));
				} else {
					System.out.println(String.format("Cannot find $ref class %s in root schema", refClassName));
				}
			} else {
				System.out.println(String.format("anyOf with no $ref element for property %s. Case not supported yet!", name));
			}
		}

		//		Now we check if all the ref classes have a properties fields. If not we just create a marker interface
		boolean haveAllProperties = true;
		List<String> commonRequiredProperties = getCommonRequiredFields(refClassesNodes.values().stream().map(jn-> jn.get("required")).map(n -> (ArrayNode) n).toList());
		for(JsonNode jn : refClassesNodes.values()) {
			if(jn.get("properties") == null) haveAllProperties = false;
		}
		Map<String, JsonNode> commonProperties = Collections.emptyMap();
		if(haveAllProperties) {
			commonProperties = getCommonSubNodes(refClassesNodes.values().stream().map(jn-> jn.get("properties")).toList());
		} 
		EClass parent = null;
		if(!commonProperties.isEmpty()) {
			if(parentClassMaps.containsKey(commonProperties)) {
				parent = parentClassMaps.get(commonProperties);
			} 
		}
		if(parent == null) {
			EClass newParent = ecoreFactory.createEClass();			
			String parentName = getCommonSuffix(refClassesNodes.keySet().toArray(new String[0]));
			if(parentName == null) parentName = ARTIFICIAL_CLASSIFIER_PREFIX+(artificialClassifierCounter++);
			newParent.setName(parentName);
			newParent.getEAnnotations().add(createEAnnotation(JSONSCHEMA_ANNOTATION_SOURCE, "artificial", "true"));
			if(!commonProperties.isEmpty()) {
				commonProperties.forEach((k,v) -> {
					EStructuralFeature feature = createEStructuralFeature(v, k, rootNode);
					if(feature != null) {
						if(commonRequiredProperties.contains(feature.getName())) {
							feature.setLowerBound(1);
						}
						newParent.getEStructuralFeatures().add(feature);
					}

				});
			}
			if(!commonProperties.isEmpty()) {
				parentClassMaps.put(commonProperties, newParent);
			}
			classifierMap.put(newParent.getName(), newParent);
			parent = newParent;
		}

		//		Now we have to remove the common properties from the ref classes
		if(!commonProperties.isEmpty()) {
			EClass p = parent;
			List<String> commonPropertiesNames = commonProperties.keySet().stream().toList();
			refClassesNodes.forEach((k,v) ->  {
				EClass subClass = createEClass(v, k, rootNode, commonPropertiesNames, Collections.emptyList());
				if(subClass != null) {
					subClass.getESuperTypes().add(p);
				}
			});
		}

		EReference reference = ecoreFactory.createEReference();
		reference.setName(name);
		reference.setEType(parent);
		reference.setContainment(false);		
		return reference;
	}

	public String getCommonSuffix(String... strings) {
		if (strings == null || strings.length == 0) {
			return null; // no input
		}

		String first = strings[0];
		if (first == null) {
			return null; // if first string is null, can't process
		}

		int minLength = first.length();

		// Find the shortest string length
		for (String str : strings) {
			if (str == null) {
				return null; // or handle as empty if you prefer
			}
			minLength = Math.min(minLength, str.length());
		}

		// Check from the end character by character
		int suffixLength = 0;
		while (suffixLength < minLength) {
			char currentChar = first.charAt(first.length() - 1 - suffixLength);
			boolean allMatch = true;

			for (String str : strings) {
				if (str.charAt(str.length() - 1 - suffixLength) != currentChar) {
					allMatch = false;
					break;
				}
			}

			if (!allMatch) {
				break;
			}
			suffixLength++;
		}

		if (suffixLength == 0) {
			return null; // no common suffix
		}

		return first.substring(first.length() - suffixLength);
	}

	public static List<String> getCommonRequiredFields(List<ArrayNode> requiredNodes) {
		List<String> commonRequired = new ArrayList<>();

		if (requiredNodes == null || requiredNodes.isEmpty()) {
			return commonRequired; // empty input = empty output
		}

		Set<String> commonSet = null;

		for (ArrayNode arrayNode : requiredNodes) {
			Set<String> currentSet = new HashSet<>();

			for (JsonNode fieldNode : arrayNode) {
				if (fieldNode.isString()) {
					currentSet.add(fieldNode.asString());
				}
			}

			if (commonSet == null) {
				// First node: initialize the set
				commonSet = currentSet;
			} else {
				// Intersect with previous sets
				commonSet.retainAll(currentSet);
			}
		}

		if (commonSet != null) {
			commonRequired.addAll(commonSet);
		}

		return commonRequired;
	}

	public static Map<String, JsonNode> getCommonSubNodes(List<JsonNode> nodes) {
		Map<String, JsonNode> commonFields = new HashMap<>();

		if (nodes == null || nodes.isEmpty()) {
			return commonFields; // empty result
		}

		// Take the first node as reference
		JsonNode reference = nodes.get(0);
		for(String fieldName : reference.propertyNames()) {

			JsonNode referenceValue = reference.get(fieldName);
			boolean isCommon = true;
			for (int i = 1; i < nodes.size(); i++) {
				JsonNode otherValue = nodes.get(i).get(fieldName);
				if (otherValue == null || !referenceValue.equals(otherValue)) {
					isCommon = false;
					break;
				}
			}

			if (isCommon) {
				commonFields.put(fieldName, referenceValue);
			}
		}

		return commonFields;
	}

	private EEnum createEEnum(JsonNode enumNode, String name) {
		if(cachedClassifiers.containsKey(enumNode.toString())) {
			System.out.println("Found existing EEnum");
			return (EEnum)cachedClassifiers.get(enumNode.toString());
		} 
		EEnum eEnum = ecoreFactory.createEEnum();
		eEnum.setName(name);
		for(int e = 0; e < enumNode.get("enum").size(); e++) {
			EEnumLiteral literal = ecoreFactory.createEEnumLiteral();
			literal.setLiteral(enumNode.get("enum").get(e).asString());
			literal.setName(enumNode.get("enum").get(e).asString());
			literal.setValue(e);
			eEnum.getELiterals().add(literal);
		}	
		if(enumNode.get("description") != null) {
			eEnum.getEAnnotations().add(createEAnnotation(GEN_MODEL_ANNOTATION_SOURCE, "documentation", enumNode.get("description").asString()));
		}
		cachedClassifiers.put(enumNode.toString(), eEnum);
		classifierMap.put(eEnum.getName(), eEnum);
		return eEnum;
	}

	public boolean arrayNodeContains(JsonNode jsonNode, String value) {
		if(!jsonNode.isArray()) return false;
		ArrayNode arrayNode = (ArrayNode) jsonNode;
		for (JsonNode node : arrayNode) {
			if (node.isString() && value.equals(node.asString())) {
				return true;
			}
		}
		return false;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.fennec.codec.info.codecinfo.CodecDeserializer#deserializeInto(tools.jackson.core.JsonParser, tools.jackson.databind.DeserializationContext, java.lang.Object)
	 */
	@Override
	public void deserializeInto(JsonParser parser, DeserializationContext ctxt, EPackage value) {
		// TODO Auto-generated method stub

	}

	private EDataType createEDataTypeFromJsonType(JsonNode dtNode, String name) {
		if(cachedClassifiers.containsKey(dtNode.toString())) {
			return (EDataType)cachedClassifiers.get(dtNode.toString());
		}

		EDataType dt = ecoreFactory.createEDataType();
		dt.setName(name);
		if(dtNode.get("description") != null) {
			dt.getEAnnotations().add(createEAnnotation(GEN_MODEL_ANNOTATION_SOURCE, "documentation", dtNode.get("description").asString()));
		}
		if(dtNode.get("type").isArray()) {
			dt.setInstanceClass(Object.class);
			dt.setInstanceClassName("java.lang.Object");
		} else {
			String jsonType = dtNode.get("type").asString();
			switch(jsonType) {
			case "string":
				dt.setInstanceClass(String.class);
				dt.setInstanceClassName("java.lang.String");
				break;
			case "integer":
				dt.setInstanceClass(Integer.class);
				dt.setInstanceClassName("java.lang.Integer");
				break;
			case "number":
				dt.setInstanceClass(Double.class);
				dt.setInstanceClassName("java.lang.Double");
				break;
			case "boolean":
				dt.setInstanceClass(Boolean.class);
				dt.setInstanceClassName("java.lang.Boolean");
				break;
			default:
				dt.setInstanceClass(Object.class);
				dt.setInstanceClassName("java.lang.Object");
				break;
			}
		}

		return dt;
	}

	private EDataType getEcoreTypeForJsonType(String jsonType) {
		EcorePackage ecorePackage = EcorePackage.eINSTANCE;
		switch(jsonType) {
		case "string":
			return ecorePackage.getEString();
		case "integer":
			return ecorePackage.getEInt();
		case "number":
			return ecorePackage.getEDouble();
		case "boolean":
			return ecorePackage.getEBoolean();
		case "bigDecimal":
			return ecorePackage.getEBigDecimal();
		case "bigInteger":
			return ecorePackage.getEBigInteger();
		case "binary":
			return ecorePackage.getEByte(); //TODO: double check that
		default:
			return ecorePackage.getEJavaObject();
		}
	}
	
}
