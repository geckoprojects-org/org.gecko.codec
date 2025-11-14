# Feature-Based Type Discrimination for JSON Schema oneOf

**Date:** 2025-11-05
**Status:** 🟡 Implementation complete, debugging test case

## Overview

This document describes the implementation of **feature-based type discrimination** for the codec framework. This feature enables deserialization of JSON structures that use property presence (rather than property values) to discriminate between types, which is common in JSON Schema `oneOf` patterns.

## Motivation

### The Problem

When deserializing JSON that conforms to a JSON Schema with `oneOf`, the codec needs to determine which variant type to instantiate. Consider this pipeline schema:

```json
{
  "input": {
    "kafka": {
      "addresses": ["localhost:9092"],
      "topics": ["input-topic"]
    }
  }
}
```

The JSON Schema defines:
```json
"inputNode": {
  "oneOf": [
    { "required": ["kafka"], "properties": { "kafka": {...} } },
    { "required": ["file"], "properties": { "file": {...} } },
    { "required": ["mqtt"], "properties": { "mqtt": {...} } }
  ]
}
```

The type discriminator is **which property exists** ("kafka" vs "file" vs "mqtt"), not a specific field's value.

### Existing Solution (Value-Based)

The codec already supported **value-based type discrimination**:
- Looks at a specific field's **value** (e.g., `deviceInfo.deviceProfileName = "Dragino_LSE01"`)
- Maps the value to a type using `typeMap`
- Example: `"Dragino_LSE01"` → `"DraginoLSE01Uplink"`

This doesn't work for JSON Schema oneOf because there's no discriminator field - the **property name itself** is the discriminator.

## Solution: Feature-Based Type Discrimination

### How It Works

**Trigger:** Set `typeKey` to `null` or `"*"` (the special constant `CODEC_TYPE_KEY_FEATURE_BASED`)

**Behavior:**
- Scan all property names at the top level of the JSON object
- Find the first property name that exists in the `typeMap`
- Use the mapped type name to instantiate the correct EClass

**Example Configuration:**
```java
typeKey = "*"  // or null
typeMap = {
  "kafka": "KafkaInputNode",
  "file": "FileInputNode",
  "mqtt": "MqttInputNode"
}

// JSON: {"kafka": {"addresses": [...]}}
// Result: Instantiates KafkaInputNode because "kafka" property is present
```

### Type Map Semantics

| Mode | typeKey Value | typeMap Keys | typeMap Values |
|------|--------------|--------------|----------------|
| **Value-based** | Field path (e.g., "deviceInfo.type") | Field **values** | Type names |
| **Feature-based** | `"*"` or `null` | Property **names** | Type names |

## Implementation Details

### 1. CodecEObjectDeserializer.java
**Location:** `org.eclipse.fennec.codec/src/org/eclipse/fennec/codec/jackson/databind/deser/CodecEObjectDeserializer.java`

#### Modified Methods

**`determineType()` (line 203-262)**
- Added check: if `typeKey == null || typeKey.equals("*")`, route to feature-based discrimination
- Otherwise, use existing value-based discrimination

**`determineTypeByFeaturePresence()` (NEW, line 264-328)**
- Buffers all JSON tokens while scanning for discriminating properties
- Scans property names at depth 1 (top-level of object)
- When a property name matches a key in `typeMap`:
  - Retrieves the type name from the map
  - Uses `CodecValueReader` to resolve the type name to an `EClass`
  - Sets the `type` field
  - Continues buffering remaining tokens
- Returns the buffer for replay during actual deserialization

#### Key Implementation Points

1. **Token Buffering:** All tokens are saved to `CodecTokenBuffer` during type determination, then replayed for deserialization
2. **Depth Tracking:** Only checks property names at depth 1 to avoid nested objects
3. **First Match Wins:** Returns immediately after finding the first matching property
4. **Graceful Degradation:** Logs warning if no match found, allows fallback to default type

### 2. CodecModelInfoOptions.java
**Location:** `org.eclipse.fennec.codec.constants/src/org/eclipse/fennec/codec/options/CodecModelInfoOptions.java`

#### Added Constants

**`CODEC_TYPE_KEY_FEATURE_BASED = "*"`** (line 144)
- Special marker value for feature-based mode
- User can set `typeKey` to this value or `null`

#### Enhanced Documentation (lines 110-155)
- Explains both discrimination modes
- Provides usage examples
- Clarifies typeMap semantics for each mode

### 3. Test Case
**Location:** `org.eclipse.fennec.codec.jsonschema.test/src/org/eclipse/fennec/codec/jsonschema/test/CodecJsonSchemaSerializationTest.java`

#### Test: `pipelineDataDeserializationWithOneOf()` (line 249-446)

**Test Flow:**
1. Load JSON Schema → convert to dynamic EPackage
2. Find root EClass and oneOf wrapper classes (InputNode, ProcessorNode, OutputNode)
3. **Build type mappings** using `buildTypeMapForOneOf()` helper
4. Configure load options with feature-based type mapping
5. Load JSON data and verify deserialization

**Helper Method: `buildTypeMapForOneOf()`** (line 896-947)
- Finds all child EClasses of an abstract parent
- Identifies unique features (appear in only one child)
- Maps unique feature names to child EClass names

## Current Status & Known Issues

### ✅ Completed
- [x] Core deserialization logic for feature-based discrimination
- [x] Constants and documentation
- [x] Test case structure
- [x] Integration with CodecOptionsBuilder

### 🟡 In Progress - Debugging Type Map Building

**Current Problem:** The `buildTypeMapForOneOf()` helper is returning empty type maps.

**Symptoms:**
```
Input Node Class: InputNode
  Features: []

Input type map: {}
Processor type map: {}
Output type map: {}
```

**Analysis:**
The abstract parent classes (InputNode, ProcessorNode, OutputNode) have no features, which is expected. The variants should be child EClasses with unique features.

**Two debugging attempts:**

1. **First attempt:** Map all features from all children
   - **Problem:** All variants had a "config" feature, creating ambiguous mappings
   - Output: `Mapping: config -> BranchProcessorNode`, `Mapping: config -> LogProcessorNode`, etc.

2. **Second attempt (current):** Only map unique features (appear in exactly one child)
   - **Problem:** Returns empty map - suggests no unique features found
   - **Hypothesis:** Either:
     - Child EClasses don't have the expected structure
     - The feature we're looking for isn't directly on the child class
     - The JSON Schema → EPackage conversion creates a different structure than expected

**What needs investigation:**
- Print out the full EPackage structure to understand how oneOf is actually modeled
- Examine what features exist on each child EClass
- Possibly the discriminating property (like "kafka", "mapping") is nested differently

### 🔴 Next Steps

1. **Debug EPackage structure:**
   ```java
   // Add detailed logging to see actual structure
   for (EClassifier classifier : ePackage.getEClassifiers()) {
       if (classifier instanceof EClass eClass) {
           System.out.println("\nEClass: " + eClass.getName());
           System.out.println("  Abstract: " + eClass.isAbstract());
           System.out.println("  SuperTypes: " + eClass.getESuperTypes());
           System.out.println("  Direct Features: " + eClass.getEStructuralFeatures());
           System.out.println("  All Features: " + eClass.getEAllStructuralFeatures());
       }
   }
   ```

2. **Understand JSON Schema oneOf → EPackage mapping:**
   - How does `JsonSchemaToEPackageDeserializer` model oneOf?
   - Are discriminating properties represented as features or differently?
   - Look at the existing meter-reading test for comparison

3. **Fix `buildTypeMapForOneOf()`:**
   - Adjust based on actual EPackage structure
   - May need to look at nested properties or use a different approach

4. **Run the test:**
   - Once type map is correct, test the actual deserialization
   - Verify that feature-based discrimination works end-to-end

## Usage Examples

### Example 1: JSON Schema oneOf (Dynamic EPackage)

```java
// After generating EPackage from JSON Schema with oneOf
Map<String, String> typeMap = Map.of(
    "kafka", "InputNode_kafka",
    "file", "InputNode_file",
    "mqtt", "InputNode_mqtt"
);

Map<String, Object> options = CodecOptionsBuilder.create()
    .rootObject(rootEClass)
    .forClass(rootEClass)
        .forReference("input")
            .typeKey(CodecModelInfoOptions.CODEC_TYPE_KEY_FEATURE_BASED)
            .typeMap(typeMap)
        .up()
    .up()
    .build();

resource.load(options);
```

### Example 2: Static Model with Annotations

For static models, users could add annotations to the .ecore file:

```xml
<eReference name="input" eType="#//InputNode">
  <eAnnotations source="codec.type">
    <details key="typeKey" value="*"/>
    <details key="kafka" value="InputNode_kafka"/>
    <details key="file" value="InputNode_file"/>
  </eAnnotations>
</eReference>
```

## Related Code Locations

### Core Implementation
- `CodecEObjectDeserializer.java:203-328` - Type determination logic
- `CodecModelInfoOptions.java:110-155` - Constants and documentation

### Tests
- `CodecJsonSchemaSerializationTest.java:249-446` - Main test case
- `CodecJsonSchemaSerializationTest.java:896-947` - Type map builder (NEEDS FIX)

### Test Data
- `pipeline_schema.json` - JSON Schema with oneOf structures
- `pipeline-data.json` - Test data conforming to the schema

### Related Features
- Value-based discrimination: `CodecEObjectDeserializer.java:214-261`
- LoRaWAN example: `lorawan-uplink.ecore` - Working value-based example

## Technical Notes

### Token Buffering Strategy
The implementation uses `CodecTokenBuffer` to save all tokens during type determination:
1. Create buffer before scanning
2. Copy each token to buffer using `copyCurrentEvent()`
3. After type is determined, return buffer
4. Caller replays buffer using `buffer.asParser()` for actual deserialization

This is necessary because Jackson's parser is forward-only - you can't "rewind" after reading tokens.

### Depth Tracking
```java
depth = updateDepth(nextToken, depth);
// START_OBJECT/START_ARRAY: depth++
// END_OBJECT/END_ARRAY: depth--
```

Only properties at depth 1 are considered discriminators. This prevents matching nested properties with the same name.

### Type Resolution
The type name from `typeMap` goes through a `CodecValueReader`:
```java
CodecValueReader<String, EClass> typeReader = infoHolder.getReaderByName(typeReaderName);
EClass deserializedType = typeReader.readValue(typeName, ctxt);
```

This handles different type naming strategies (NAME, URI, CLASS).

## Questions for Tomorrow's Session

1. **EPackage Structure:** How exactly does `JsonSchemaToEPackageDeserializer` model oneOf?
   - Are there wrapper classes for each variant?
   - Where do discriminating properties appear?

2. **Type Map Building:** What's the correct way to build the type map?
   - Should we look at feature types instead of feature names?
   - Is there metadata we should be using?

3. **Alternative Approaches:** Should we consider:
   - Looking at reference types instead of features?
   - Using a different pattern for dynamic models?
   - Pre-processing the EPackage to extract mappings?

## References

- **JSON Schema oneOf spec:** https://json-schema.org/understanding-json-schema/reference/combining#oneOf
- **EMF Dynamic Models:** https://www.eclipse.org/modeling/emf/
- **Jackson Token Streaming:** https://github.com/FasterXML/jackson-core

---

**For Next Session:** Start by running the test with detailed EPackage structure logging to understand how oneOf is actually modeled in the generated EPackage.
