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
package org.eclipse.fennec.codec.jsonschema.readers;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fennec.codec.info.CodecModelInfo;
import org.eclipse.fennec.codec.jackson.databind.CodecReadContext;
import org.eclipse.fennec.codec.jackson.databind.CodecTokenBuffer;
import org.eclipse.fennec.codec.jackson.databind.deser.CodecEObjectDeserializer;
import org.eclipse.fennec.codec.jackson.module.CodecModule;

import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;

/**
 * Smart deserializer that detects JSON Schema content and routes to appropriate deserializer
 * 
 * @author ilenia
 * @since Aug 8, 2025
 */
public class SmartJsonSchemaDeserializer extends ValueDeserializer<EObject> {
    
    private final Class<?> targetClass;
    private final CodecModule module;
    private final CodecModelInfo codecModelInfoService;
    
    public SmartJsonSchemaDeserializer(Class<?> targetClass, CodecModule module, CodecModelInfo codecModelInfoService) {
        this.targetClass = targetClass;
        this.module = module;
        this.codecModelInfoService = codecModelInfoService;
    }

    @Override
    public EObject deserialize(JsonParser parser, DeserializationContext ctxt) {
        // Use CodecTokenBuffer to read ahead while preserving the codec context
//        CodecTokenBuffer buffer = CodecTokenBuffer.forBuffering(parser, ctxt);
    	CodecTokenBuffer buffer = CodecTokenBuffer.forGeneration();
        // Read the content into the buffer while detecting JSON Schema patterns
        boolean isJsonSchema = readAndDetectJsonSchema(parser, buffer);
        
        // Create a new parser from the buffered content that preserves the codec context
        JsonParser bufferedParser = buffer.asParser();
        
        if (isJsonSchema) {
            return new JsonSchemaToEPackageDeserializer().deserialize(bufferedParser, ctxt);
        } else {
            return new CodecEObjectDeserializer(targetClass, module, codecModelInfoService).deserialize(bufferedParser, ctxt);
        }
    }
    
    private JsonToken getAndSaveNextToken(JsonParser jp, CodecTokenBuffer buffer) {
		JsonToken nextToken = jp.nextToken();	
		if(nextToken != null) {
			System.out.println(nextToken + " -> " + jp.getString());
			buffer.copyCurrentEvent(jp);
			
		} else {
			System.out.println("Null token in getAndSave");
			
		}
		return nextToken;
	}
    
    private int updateDepth(JsonToken nextToken, int depth) {
		if (nextToken == JsonToken.START_OBJECT || nextToken == JsonToken.START_ARRAY) {
	        depth = depth + 1;
	    } else if (nextToken == JsonToken.END_OBJECT || nextToken == JsonToken.END_ARRAY) {
	    	depth = depth - 1;
	    }
		return depth;
	}
    
    /**
     * Read the JSON content into the buffer while detecting JSON Schema patterns
     */
    private boolean readAndDetectJsonSchema(JsonParser parser, CodecTokenBuffer buffer) {
        try {
            JsonToken token = parser.currentToken();
            buffer.copyCurrentEvent(parser);
            Integer depth = 1;
            if (token == null) {
                token = getAndSaveNextToken(parser, buffer);
                depth = updateDepth(token, depth);
            }
            
            // Ensure we're at the start of an object
            if (token != JsonToken.START_OBJECT) {
                // Copy the token and return false
                return false;
            }
           
            // Look for JSON Schema indicators in the first few fields
            boolean hasSchemas = false;
            boolean hasTypeAndProperties = false;
            boolean hasSchemaKeywords = false;
            String typeValue = null;
            
            while ((token = getAndSaveNextToken(parser, buffer)) != JsonToken.END_OBJECT) {
            	 depth = updateDepth(token, depth);
                // Copy each token to the buffer
//            	getAndSaveNextToken(parser, buffer);
                
                if (token == JsonToken.PROPERTY_NAME) {
                    String fieldName = parser.currentName();
                    
                    // Move to the field value and copy it
                    token = getAndSaveNextToken(parser, buffer);
                    depth = updateDepth(token, depth);
                    // Detect schema patterns
                    switch (fieldName) {
                        case "schemas":
                            if (token == JsonToken.START_OBJECT && depth == 2) {
                                hasSchemas = true;
                                // Copy the entire schemas object
//                                buffer.copyCurrentStructure(parser);
                            } else {
                            	System.out.println("schemas found but depth was " + depth);
                            }
                            break;
                        case "type":
                            if (token == JsonToken.VALUE_STRING && depth == 1)  {
                                typeValue = parser.getString();
                                if ("object".equals(typeValue)) {
                                    hasTypeAndProperties = true;
                                }
                            }
                            break;
                        case "properties":
                            if ("object".equals(typeValue) && token == JsonToken.START_OBJECT && depth == 1)  {
                                hasTypeAndProperties = true;
                                // Copy the entire properties object
//                                buffer.copyCurrentStructure(parser);
                            }
                            break;
                        case "$schema":
                        case "definitions":
                            if(depth == 1) hasSchemaKeywords = true;
//                            if (token == JsonToken.START_OBJECT || token == JsonToken.START_ARRAY) {
//                                buffer.copyCurrentStructure(parser);
//                            }
                            break;
                        default:
                            // Copy any nested structures for unknown fields
//                            if (token == JsonToken.START_OBJECT || token == JsonToken.START_ARRAY) {
//                                buffer.copyCurrentStructure(parser);
//                            }
                            break;
                    }
                }
            }
            
            // Copy remaining tokens until end of object
            while (depth > 1 && token != null) {
                token = getAndSaveNextToken(parser, buffer);
                depth = updateDepth(token, depth);
                
//                if (token != null) {
//                	getAndSaveNextToken(parser, buffer);
//                    if (token == JsonToken.START_OBJECT || token == JsonToken.START_ARRAY) {
//                        buffer.copyCurrentStructure(parser);
//                    }
//                }
            }
            
            return hasSchemas || hasTypeAndProperties || hasSchemaKeywords;
            
        } catch (Exception e) {
            // If we can't parse, assume it's not a JSON Schema
            return false;
        }
    }
}