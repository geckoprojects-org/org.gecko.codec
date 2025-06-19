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

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.fennec.codec.info.codecinfo.CodecSerializer;

import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;

/**
 * 
 * @author ilenia
 * @since Jun 19, 2025
 */
public class EPackageToJsonSchemaSerializer implements CodecSerializer<EPackage> {

	private static final String GEN_MODEL_ANNOTATION_SOURCE = "http://www.eclipse.org/emf/2002/GenModel";
	private static final String JSONSCHEMA_ANNOTATION_SOURCE = "http://fennec.eclipse.org/jsonschema";

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.fennec.codec.info.codecinfo.CodecSerializer#getName()
	 */
	@Override
	public String getName() {
		return "EPACKAGE_TO_JSONSCHEMA_SER";
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.fennec.codec.info.codecinfo.CodecSerializer#serialize(java.lang.Object, tools.jackson.core.JsonGenerator, tools.jackson.databind.SerializationContext)
	 */
	@Override
	public void serialize(EPackage ePackage, JsonGenerator gen, SerializationContext ctxt) {
		gen.writeStartObject();
		if(extractAnnotationDetail(ePackage, JSONSCHEMA_ANNOTATION_SOURCE, "schema") != null) {
			gen.writeStringProperty("$schema", extractAnnotationDetail(ePackage, JSONSCHEMA_ANNOTATION_SOURCE, "schema"));
		}
		if(ePackage.getNsURI() != null) gen.writeStringProperty("$id", ePackage.getNsURI()); // Or a more suitable ID
		if(ePackage.getName() != null) gen.writeStringProperty("title", ePackage.getName());
		if(extractAnnotationDetail(ePackage, GEN_MODEL_ANNOTATION_SOURCE, "documentation") != null) {
			gen.writeStringProperty("description", extractAnnotationDetail(ePackage, GEN_MODEL_ANNOTATION_SOURCE, "documentation"));
		}


		gen.writeObjectPropertyStart("definitions"); // For EClasses

		for (EClassifier classifier : ePackage.getEClassifiers()) {
			serializeEClassifier(classifier, gen, ctxt);
		}
		gen.writeEndObject(); // End definitions


	}

	private void serializeEClassifier(EClassifier classifier, JsonGenerator gen, SerializationContext ctxt) {
		if("true".equals(extractAnnotationDetail(classifier, JSONSCHEMA_ANNOTATION_SOURCE, "artificial"))) return;
		gen.writeName(classifier.getName()); // EClassifier name
		if(classifier instanceof EClass eClass) {
			serializeEClass(eClass, gen, ctxt);
		} else if(classifier instanceof EEnum eEnum) {
			serializeEEnum(eEnum, gen, ctxt);
		} else if(classifier instanceof EDataType eDataType) {
			serializeEDataType(eDataType, gen, ctxt);
		}
	}

	private void serializeEClass(EClass eClass, JsonGenerator gen, SerializationContext ctxt) {
		gen.writeStartObject();
		if(extractAnnotationDetail(eClass, GEN_MODEL_ANNOTATION_SOURCE, "documentation") != null) {
			gen.writeStringProperty("description", extractAnnotationDetail(eClass, GEN_MODEL_ANNOTATION_SOURCE, "documentation"));
		}
		gen.writeStringProperty("type", "object");

		if(eClass.getEStructuralFeatures().isEmpty()) {
			gen.writeEndObject();
			return;
		}
		gen.writeObjectPropertyStart("properties");
		List<String> requiredProperties = new LinkedList<>();
		for(EStructuralFeature feature : eClass.getEStructuralFeatures()) {
			String documentation = extractAnnotationDetail(feature, GEN_MODEL_ANNOTATION_SOURCE, "documentation");
			String noTypeInfo = extractAnnotationDetail(feature, GEN_MODEL_ANNOTATION_SOURCE, "noTypeInfo");
			if(feature.isRequired()) requiredProperties.add(feature.getName());
			if(feature instanceof EAttribute eAttribute) {
				EDataType type = eAttribute.getEAttributeType();
				gen.writeName(eAttribute.getName());
				if(eAttribute.isMany()) {
					gen.writeStartObject();
					if(documentation != null) {
						gen.writeStringProperty("description", documentation);
					}					
					if(noTypeInfo != null && "true".equals(noTypeInfo)) gen.writeStringProperty("type", "array");
					serializeConstValue(eAttribute, true, gen, ctxt);
					String itemsAnnotation = extractAnnotationDetail(eAttribute, JSONSCHEMA_ANNOTATION_SOURCE, "items");
					if(itemsAnnotation != null && "true".equals(itemsAnnotation)) {
						serializeArrayItems(eAttribute, type, gen, ctxt);
					}
					gen.writeEndObject();
				} else {
					if(type instanceof EEnum eEnum) {
						gen.writeStartObject();
						if(documentation != null) {
							gen.writeStringProperty("description", documentation);
						}
						serializeEEnumLiterals(eEnum.getELiterals(), gen, ctxt);
						if(noTypeInfo != null && "true".equals(noTypeInfo)) gen.writeStringProperty("type", "string");
						serializeConstValue(eAttribute, false, gen, ctxt);
						gen.writeEndObject();
					} else {
						gen.writeStartObject();
						if(documentation != null) {
							gen.writeStringProperty("description", documentation);
						}
						serializeConstValue(eAttribute, false, gen, ctxt);
						if(noTypeInfo != null && "true".equals(noTypeInfo)) {
							String jsonType = getJsonTypeFromEDataType(type);
							if("javaObject".equals(jsonType)) {
								String dataTypeStr = extractAnnotationDetail(eAttribute, JSONSCHEMA_ANNOTATION_SOURCE, "dataType");
								if(dataTypeStr != null) {
									String[] dataTypeSplit = dataTypeStr.split(",");
									gen.writeArrayPropertyStart("type");
									for(String dt : dataTypeSplit) {
										gen.writeString(dt);
									}
									gen.writeEndArray();
								}						
							} else {
								gen.writeStringProperty("type", jsonType);
							}
						}
					
						gen.writeEndObject();
					}
				}



			} else if(feature instanceof EReference eReference) {

			}
		}
		gen.writeEndObject();
		if(!requiredProperties.isEmpty()) {
			gen.writeArrayPropertyStart("required");
			requiredProperties.forEach(r -> {
				gen.writeString(r);
			});
			gen.writeEndArray();
		}
		gen.writeEndObject();
	}
	
	private void serializeConstValue(ENamedElement element, boolean isInArray, JsonGenerator gen, SerializationContext ctxt) {
		String constAnnotation = extractAnnotationDetail(element, JSONSCHEMA_ANNOTATION_SOURCE, "const");
		String constType = extractAnnotationDetail(element, JSONSCHEMA_ANNOTATION_SOURCE, "constType");
		if(constAnnotation != null && constType != null) {
			if(isInArray) {
				gen.writeArrayPropertyStart("const");
				String[] parts = constAnnotation.replaceAll("\\[|\\]", "").split(",");
				for(String part : parts) {
					part = part.trim();
					switch(constType) {
					case "NUMBER":
						try {
							gen.writeNumber(Integer.parseInt(part));
						} catch(Exception e) {
							gen.writeNumber(Float.parseFloat(part));
						}								
						break;
					case "STRING":
						gen.writeString(part);
						break;
					case "BOOLEAN":
						gen.writeBoolean(Boolean.parseBoolean(part));
						break;
					default:
						throw new IllegalArgumentException(String.format("constType %s found in EAttribute %s currently not supported", constType, element.getName()));
					
					}
				}
				gen.writeEndArray();
			} else {
				gen.writeName("const");
				switch(constType) {
				case "NUMBER":
					try {
						gen.writeNumber(Integer.parseInt(constAnnotation));
					} catch(Exception e) {
						gen.writeNumber(Float.parseFloat(constAnnotation));
					}								
					break;
				case "STRING":
					gen.writeString(constAnnotation);
					break;
				case "BOOLEAN":
					gen.writeBoolean(Boolean.parseBoolean(constAnnotation));
					break;
				default:
					throw new IllegalArgumentException(String.format("constType %s found in EAttribute %s currently not supported", constType, element.getName()));
				
				}
			}			
		}
	}
	
	private void serializeArrayItems(EStructuralFeature feature, EDataType type, JsonGenerator gen, SerializationContext ctxt) {
		gen.writeName("items");
		if(type instanceof EEnum eEnum) {
			serializeEEnumLiterals(eEnum.getELiterals(), gen, ctxt);
		} else {
			gen.writeStartObject();
			String jsonType = getJsonTypeFromEDataType(type);
			if("javaObject".equals(jsonType)) {
				String dataTypeStr = extractAnnotationDetail(feature, JSONSCHEMA_ANNOTATION_SOURCE, "dataType");
				if(dataTypeStr != null) {
					String[] dataTypeSplit = dataTypeStr.split(",");
					gen.writeArrayPropertyStart("type");
					for(String dt : dataTypeSplit) {
						gen.writeString(dt);
					}
					gen.writeEndArray();
				}						
			} else {
				gen.writeStringProperty("type", jsonType);
			}
		}
		gen.writeEndObject();
	}

	private String getJsonTypeFromEDataType(EDataType eDataType) {
		if(EcorePackage.Literals.ESTRING.equals(eDataType)) return "string";
		if(EcorePackage.Literals.EFLOAT.equals(eDataType) || EcorePackage.Literals.EFLOAT_OBJECT.equals(eDataType)) return "number";
		if(EcorePackage.Literals.EDOUBLE.equals(eDataType) || EcorePackage.Literals.EDOUBLE_OBJECT.equals(eDataType)) return "number";
		if(EcorePackage.Literals.EINT.equals(eDataType) || EcorePackage.Literals.EINTEGER_OBJECT.equals(eDataType)) return "integer";
		if(EcorePackage.Literals.EBIG_DECIMAL.equals(eDataType)) return "number";
		if(EcorePackage.Literals.EBIG_INTEGER.equals(eDataType)) return "integer";
		if(EcorePackage.Literals.EBYTE.equals(eDataType)) return "bynary";
		if(EcorePackage.Literals.EBOOLEAN.equals(eDataType) || EcorePackage.Literals.EBOOLEAN_OBJECT.equals(eDataType)) return "bynary";
		return "javaObject";
	}

	private void serializeEEnum(EEnum eEnum, JsonGenerator gen, SerializationContext ctxt) {
		gen.writeStartObject();
		if(extractAnnotationDetail(eEnum, GEN_MODEL_ANNOTATION_SOURCE, "documentation") != null) {
			gen.writeStringProperty("description", extractAnnotationDetail(eEnum, GEN_MODEL_ANNOTATION_SOURCE, "documentation"));
		}
		serializeEEnumLiterals(eEnum.getELiterals(), gen, ctxt);
		gen.writeStringProperty("type", "string");
		gen.writeEndObject();
	}
	
	private void serializeEEnumLiterals(List<EEnumLiteral> literals, JsonGenerator gen, SerializationContext ctxt) {
		gen.writeArrayPropertyStart("enum");
		for(EEnumLiteral literal : literals) {
			gen.writeString(literal.getName());
		}
		gen.writeEndArray();
	}

	private void serializeEDataType(EDataType eDataType, JsonGenerator gen, SerializationContext ctxt) {

	}

	private String extractAnnotationDetail(EModelElement modelElement, String source, String detailKey) {
		EAnnotation annotation = modelElement.getEAnnotation(source);
		if(annotation == null) return null;
		if(!annotation.getDetails().containsKey(detailKey)) return null;
		return annotation.getDetails().get(detailKey);
	}
}
