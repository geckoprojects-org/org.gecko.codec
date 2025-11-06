# Comprehensive Type Handling Guide

**Last Updated:** 2025-11-06
**Version:** 2.0

## Table of Contents
1. [Overview](#overview)
2. [The TypeInfo Model](#the-typeinfo-model)
3. [How TypeInfo is Created](#how-typeinfo-is-created)
4. [Type Discrimination Modes](#type-discrimination-modes)
5. [Type Annotation Inheritance](#type-annotation-inheritance)
6. [Configuration Methods](#configuration-methods)
7. [Examples](#examples)
8. [Best Practices](#best-practices)

---

## Overview

The codec framework provides flexible type discrimination for serialization and deserialization of EMF EObjects. This guide covers:

- **Value-based discrimination**: Uses a field's value to determine type (e.g., `deviceType: "sensor"`)
- **Feature-based discrimination**: Uses property presence to determine type (e.g., presence of `kafka` property)
- **Type annotation inheritance**: EReferences automatically inherit type info from their target EClass
- **Runtime configuration**: Override model annotations via load/save options

---

## The TypeInfo Model

The `TypeInfo` object is defined in the `codec-info` model (`org.eclipse.fennec.codec.info.model`):

![TypeInfo Model](./TypeInfoModel.png)

### Properties

| Property | Type | Description | Default |
|----------|------|-------------|---------|
| `typeStrategy` | String | Strategy for type serialization: `NAME`, `CLASS`, or `URI` | `URI` |
| `ignoreType` | Boolean | Whether to ignore type information | `false` |
| `typeKey` | String | Property name for type discrimination | `_type` |
| `typeMap` | Map<String,String> | Maps discriminator values to type names | `{}` |
| `typeValueReaderName` | String | Custom CodecValueReader for deserialization | Based on strategy |
| `typeValueWriterName` | String | Custom CodecValueWriter for serialization | Based on strategy |

### Type Strategies

| Strategy | Example Value | Use Case |
|----------|--------------|----------|
| **NAME** | `"Person"` | Simple class name, human-readable |
| **CLASS** | `"org.example.model.Person"` | Fully qualified class name |
| **URI** | `"http://example.de/person/1.0#//Person"` | EClass URI, unambiguous |

---

## How TypeInfo is Created

### 1. Model Annotation Processing

**Location:** `CodecModelInfoImpl.getTypeInfo()` in `org.eclipse.fennec.codec.info`

The `CodecModelInfo` service scans EMF models for `codec.type` annotations and creates TypeInfo objects:

```java
private TypeInfo getTypeInfo(EModelElement modelElement) {
    TypeInfo typeInfo = CodecInfoFactory.eINSTANCE.createTypeInfo();

    // Get annotation details (with inheritance for EReferences)
    Map<String, String> typeAnnotationDetails =
        getTypeAnnotationDetailsWithInheritance(modelElement);

    // Parse annotation details
    String typeValue = typeAnnotationDetails.getOrDefault("include", "true");
    typeInfo.setIgnoreType("false".equalsIgnoreCase(typeValue));

    typeInfo.setTypeKey(typeAnnotationDetails.getOrDefault("typeKey", "_type"));
    String typeStrategy = typeAnnotationDetails.getOrDefault("strategy", "");

    // Build typeMap from remaining annotation details
    Map<String, String> typeMap = new HashMap<>();
    typeAnnotationDetails.forEach((k,v) -> {
        if(!TYPE_ANNOTATION_KEYS.contains(k)) {
            typeMap.put(k, v);
        }
    });
    typeInfo.getTypeMap().putAll(typeMap);

    // Set readers/writers based on strategy
    switch(typeStrategy) {
        case "NAME":
            typeInfo.setTypeValueWriterName(WRITER_BY_ECLASS_NAME);
            typeInfo.setTypeValueReaderName(READER_BY_ECLASS_NAME);
            break;
        case "CLASS":
            typeInfo.setTypeValueWriterName(WRITER_BY_INSTANCE_CLASS_NAME);
            typeInfo.setTypeValueReaderName(READER_BY_INSTANCE_CLASS_NAME);
            break;
        case "URI": default:
            typeInfo.setTypeValueWriterName(URI_WRITER);
            typeInfo.setTypeValueReaderName(URI_READER);
            break;
    }

    return typeInfo;
}
```

### 2. Runtime Option Processing

**Location:** `CodecResource.updateCodecModelInfoFromOptions()` in `org.eclipse.fennec.codec`

Load/save options can override model annotations:

```java
private void updateCodecModelInfoFromOptions(TypedCodecInfo codecInfo, Map<?, ?> options) {
    // Override strategy
    if(options.containsKey(CODEC_TYPE_STRATEGY)) {
        String strategy = (String) options.get(CODEC_TYPE_STRATEGY);
        codecInfo.getTypeInfo().setTypeStrategy(strategy);
        // Update readers/writers accordingly
    }

    // Override typeKey
    if(options.containsKey(CODEC_TYPE_KEY)) {
        codecInfo.getTypeInfo().setTypeKey((String) options.get(CODEC_TYPE_KEY));
    }

    // Override/merge typeMap
    if(options.containsKey(CODEC_TYPE_MAP)) {
        Map<String, String> typeMap = (Map<String, String>) options.get(CODEC_TYPE_MAP);
        if(TypeMapStrategyType.OVERWRITE.equals(codecInfo.getTypeInfo().getTypeMapStrategy())) {
            codecInfo.getTypeInfo().getTypeMap().clear();
        }
        codecInfo.getTypeInfo().getTypeMap().putAll(typeMap);
    }

    // Handle inheritance override
    if(options.containsKey(CODEC_TYPE_INHERITS_FROM_PARENT)) {
        Boolean inheritsFromParent = (Boolean) options.get(CODEC_TYPE_INHERITS_FROM_PARENT);
        if(!inheritsFromParent && codecInfo instanceof FeatureCodecInfo) {
            // Clear inherited type mappings
            codecInfo.getTypeInfo().getTypeMap().clear();
        }
    }
}
```

---

## Type Discrimination Modes

### Value-Based Discrimination (Default)

**Uses:** The **value** of a specific field to determine the type.

**Configuration:**
- Set `typeKey` to a field path (e.g., `"deviceType"` or `"deviceInfo.profileName"`)
- Provide a `typeMap` that maps field values to type names

**Example:**

```java
// Model annotation
<eAnnotations source="codec.type">
  <details key="typeKey" value="deviceInfo.deviceProfileName"/>
  <details key="strategy" value="NAME"/>
  <details key="Dragino_LSE01" value="DraginoLSE01Uplink"/>
  <details key="EM310-UDL" value="EM310UDLUplink"/>
</eAnnotations>
```

**JSON:**
```json
{
  "deviceInfo": {
    "deviceProfileName": "Dragino_LSE01"
  },
  "data": "..."
}
```
→ Deserializes as `DraginoLSE01Uplink`

### Feature-Based Discrimination (NEW)

**Uses:** The **presence** of specific properties to determine the type.

**Configuration:**
- Set `typeKey` to `"*"` (or `null`) - use constant `CODEC_TYPE_KEY_FEATURE_BASED`
- Provide a `typeMap` that maps property names to type names

**Example:**

```java
// Model annotation
<eAnnotations source="codec.type">
  <details key="typeKey" value="*"/>
  <details key="strategy" value="NAME"/>
  <details key="kafka" value="KafkaInputNode"/>
  <details key="file" value="FileInputNode"/>
  <details key="mqtt" value="MqttInputNode"/>
</eAnnotations>
```

**JSON:**
```json
{
  "kafka": {
    "addresses": ["localhost:9092"],
    "topics": ["input-topic"]
  }
}
```
→ Deserializes as `KafkaInputNode` (because "kafka" property is present)

**Use Cases:**
- JSON Schema `oneOf` patterns
- Discriminated unions where the discriminator is the property name itself
- API responses with variant structures

### Type Map Semantics Comparison

| Aspect | Value-Based | Feature-Based |
|--------|-------------|---------------|
| **typeKey** | Field path (e.g., `"type"`, `"model.kind"`) | `"*"` or `null` |
| **typeMap keys** | Field **values** (e.g., `"sensor"`, `"actuator"`) | Property **names** (e.g., `"kafka"`, `"file"`) |
| **typeMap values** | Type names (e.g., `"SensorDevice"`) | Type names (e.g., `"KafkaInputNode"`) |
| **Discrimination point** | Content of a specific field | Existence of a property |

---

## Type Annotation Inheritance

**NEW FEATURE** (v2.0) - EReferences automatically inherit `codec.type` annotations from their target EClass.

### How It Works

When `CodecModelInfo` processes an `EReference`:

1. **Checks the reference** for a `codec.type` annotation
2. **If not found or `inherits.from.parent ≠ "false"`**:
   - Gets the target EClass
   - Retrieves its `codec.type` annotation
   - Merges with reference's annotation (reference takes precedence)
3. **If `inherits.from.parent="false"`**: Uses only reference's annotation

### Implementation

**Location:** `CodecModelInfoImpl.getTypeAnnotationDetailsWithInheritance()`

```java
private Map<String, String> getTypeAnnotationDetailsWithInheritance(EModelElement modelElement) {
    // Get reference's own annotation
    Map<String, String> referenceDetails = getAnnotationDetailsMap(modelElement, "codec.type", false);

    if (!(modelElement instanceof EReference reference)) {
        return getAnnotationDetailsMap(modelElement, "codec.type", true);
    }

    // Check if inheritance is disabled
    String inheritsFromParent = referenceDetails.get("inherits.from.parent");
    if ("false".equalsIgnoreCase(inheritsFromParent)) {
        return referenceDetails; // Use only reference annotation
    }

    // Get target EClass annotation
    if (reference.getEType() instanceof EClass targetClass) {
        Map<String, String> targetDetails = getAnnotationDetailsMap(targetClass, "codec.type", true);

        if (!targetDetails.isEmpty()) {
            // Merge: target first, reference overrides
            Map<String, String> merged = new HashMap<>(targetDetails);
            merged.putAll(referenceDetails);
            return merged;
        }
    }

    return referenceDetails;
}
```

### Benefits

1. **DRY Principle**: Define type mappings once on the discriminated union EClass
2. **Cleaner Models**: No need to duplicate annotations on every EReference
3. **Maintainability**: Change type mappings in one place
4. **JSON Schema Integration**: Works seamlessly with generated EPackages from JSON Schema

### Example

**Model:**
```xml
<!-- Person EClass with type discrimination -->
<eClassifiers xsi:type="ecore:EClass" name="Person">
  <eAnnotations source="codec.type">
    <details key="strategy" value="NAME"/>
    <details key="typeKey" value="*"/>
    <details key="companyId" value="BusinessPerson"/>
  </eAnnotations>
</eClassifiers>

<!-- Meeting EClass with reference to Person -->
<eClassifiers xsi:type="ecore:EClass" name="Meeting">
  <eStructuralFeatures xsi:type="ecore:EReference"
                       name="responsiblePerson"
                       eType="#//Person"
                       containment="true"/>
  <!-- NO codec.type annotation needed! -->
</eClassifiers>
```

**JSON:**
```json
{
  "id": "meeting-001",
  "responsiblePerson": {
    "name": "Alice",
    "companyId": "ACME-123"
  }
}
```

**Result:** `responsiblePerson` is correctly deserialized as `BusinessPerson` (not just `Person`) because the reference inherited the type mapping from the `Person` EClass.

### Disabling Inheritance

**Via Model Annotation:**
```xml
<eStructuralFeatures xsi:type="ecore:EReference" name="responsiblePerson">
  <eAnnotations source="codec.type">
    <details key="inherits.from.parent" value="false"/>
  </eAnnotations>
</eStructuralFeatures>
```

**Via Runtime Option:**
```java
CodecOptionsBuilder.create()
  .forClass(meetingClass)
    .forReference("responsiblePerson")
      .inheritsTypeFromParent(false)
    .and()
  .build();
```

---

## Configuration Methods

### 1. Model Annotations

**Annotation Source:** `codec.type`

**Placement:** On `EClass` or `EReference`

**Details Keys:**

| Key | Type | Description | Example |
|-----|------|-------------|---------|
| `typeKey` | String | Discriminator field path or `"*"` | `"deviceType"`, `"*"` |
| `strategy` | String | Type strategy: NAME, CLASS, URI | `"NAME"` |
| `include` | String | Whether to include type info | `"true"` |
| `inherits.from.parent` | String | (EReference only) Inherit from target EClass | `"true"` |
| *custom* | String | Type mappings (key → type name) | `"kafka"` → `"KafkaInput"` |

**Example:**
```xml
<eAnnotations source="codec.type">
  <details key="strategy" value="NAME"/>
  <details key="typeKey" value="*"/>
  <details key="kafka" value="KafkaInputNode"/>
  <details key="file" value="FileInputNode"/>
</eAnnotations>
```

### 2. Load/Save Options (Low-Level)

**Options Map Structure:**
```java
Map<String, Object> options = new HashMap<>();
Map<EClass, Map<String, Object>> codecOptions = new HashMap<>();
Map<String, Object> classOptions = new HashMap<>();

// Class-level options
classOptions.put(CODEC_TYPE_KEY, "*");
classOptions.put(CODEC_TYPE_STRATEGY, "NAME");
classOptions.put(CODEC_TYPE_MAP, Map.of("kafka", "KafkaInputNode"));

// Reference-level options
Map<EReference, Map<String, Object>> refOptions = new HashMap<>();
refOptions.put(referenceObj, Map.of(
    CODEC_TYPE_KEY, "*",
    CODEC_TYPE_INHERITS_FROM_PARENT, false
));
classOptions.put(CODEC_OPTIONS, refOptions);

codecOptions.put(eClassObj, classOptions);
options.put(CODEC_OPTIONS, codecOptions);
options.put(CODEC_ROOT_OBJECT, eClassObj);

resource.load(inputStream, options);
```

### 3. CodecOptionsBuilder (High-Level)

**Fluent API for building options:**

```java
Map<String, Object> options = CodecOptionsBuilder.create()
    .rootObject(rootEClass)
    .serializeType(false)

    // Class-level configuration
    .forClass(meetingClass)
        .typeKey("_type")
        .typeStrategy("NAME")

        // Reference-level configuration
        .forReference("responsiblePerson")
            .typeKey("*")
            .typeMap(Map.of("companyId", "BusinessPerson"))
            .inheritsTypeFromParent(false)
        .and()
    .and()
    .build();
```

### Configuration Precedence

1. **Runtime options** (highest priority)
2. **EReference annotations**
3. **Target EClass annotations** (via inheritance)
4. **Default values** (lowest priority)

---

## Examples

### Example 1: Default Configuration

**No annotations, default settings:**

**JSON:**
```json
{
  "_type": "http://example.de/person/1.0#//Person",
  "name": "Mario",
  "address": {
    "_type": "http://example.de/person/1.0#//Address",
    "street": "Via Giuseppe Garibaldi"
  }
}
```

### Example 2: Custom Type Key

**Override typeKey via options:**

```java
CodecOptionsBuilder.create()
    .rootObject(personClass)
    .forClass(personClass)
        .typeKey("eClass")
    .and()
    .build();
```

**JSON:**
```json
{
  "eClass": "http://example.de/person/1.0#//Person",
  "name": "Mario",
  "address": {
    "_type": "http://example.de/person/1.0#//Address",
    "street": "Via Giuseppe Garibaldi"
  }
}
```

### Example 3: NAME Strategy

**Change strategy to NAME:**

```java
CodecOptionsBuilder.create()
    .rootObject(personClass)
    .forClass(personClass)
        .typeStrategy("NAME")
    .and()
    .build();
```

**JSON:**
```json
{
  "_type": "Person",
  "name": "Mario"
}
```

### Example 4: Value-Based Type Mapping

**Multiple address types with discriminator field:**

```java
CodecOptionsBuilder.create()
    .rootObject(personClass)
    .forClass(personClass)
        .forReference("address")
            .typeKey("addressType")
            .typeStrategy("URI")
            .typeMap(Map.of(
                "company", "http://example.de/person/1.0#//CompanyAddress",
                "personal", "http://example.de/person/1.0#//PersonalAddress",
                "holiday", "http://example.de/person/1.0#//HolidayAddress"
            ))
        .and()
    .and()
    .build();
```

**JSON:**
```json
{
  "name": "Mario",
  "addresses": [
    {
      "addressType": "personal",
      "street": "Via Giuseppe Garibaldi"
    },
    {
      "addressType": "company",
      "street": "Via dei Lavoratori"
    }
  ]
}
```

### Example 5: Feature-Based Type Mapping

**JSON Schema oneOf pattern:**

```java
CodecOptionsBuilder.create()
    .rootObject(pipelineClass)
    .forClass(pipelineClass)
        .forReference("input")
            .typeKey("*")  // Feature-based!
            .typeStrategy("NAME")
            .typeMap(Map.of(
                "kafka", "KafkaInputNode",
                "file", "FileInputNode",
                "mqtt", "MqttInputNode"
            ))
        .and()
    .and()
    .build();
```

**JSON:**
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
→ Deserializes `input` as `KafkaInputNode`

### Example 6: Nested Type Key

**Use nested field for discrimination:**

```java
CodecOptionsBuilder.create()
    .rootObject(personClass)
    .forClass(personClass)
        .typeKey("model.type")
        .typeMap(Map.of(
            "person", "http://example.de/person/1.0#//Person",
            "business", "http://example.de/person/1.0#//BusinessPerson"
        ))
    .and()
    .build();
```

**JSON:**
```json
{
  "name": "Mario",
  "model": {
    "type": "business"
  },
  "companyId": "ACME-001"
}
```

### Example 7: Type Annotation Inheritance

**Model with inheritance:**

```xml
<!-- Person with type annotation -->
<eClassifiers xsi:type="ecore:EClass" name="Person">
  <eAnnotations source="codec.type">
    <details key="typeKey" value="*"/>
    <details key="strategy" value="NAME"/>
    <details key="companyId" value="BusinessPerson"/>
  </eAnnotations>
</eClassifiers>

<!-- Meeting references Person (inherits annotation) -->
<eClassifiers xsi:type="ecore:EClass" name="Meeting">
  <eStructuralFeatures xsi:type="ecore:EReference"
                       name="responsiblePerson"
                       eType="#//Person"/>
</eClassifiers>
```

**No options needed!** The reference automatically inherits.

**JSON:**
```json
{
  "id": "meeting-001",
  "responsiblePerson": {
    "name": "Alice",
    "companyId": "TECH-123"
  }
}
```
→ `responsiblePerson` is `BusinessPerson`

### Example 8: Disable Inheritance at Runtime

**Override inheritance via options:**

```java
CodecOptionsBuilder.create()
    .rootObject(meetingClass)
    .forClass(meetingClass)
        .forReference("responsiblePerson")
            .inheritsTypeFromParent(false)
        .and()
    .and()
    .build();
```

**JSON:**
```json
{
  "responsiblePerson": {
    "name": "Bob",
    "companyId": "ACME-999"
  }
}
```
→ `responsiblePerson` is `Person` (not `BusinessPerson`) because inheritance is disabled

### Example 9: Custom CodecValueReader/Writer

**Custom reader for special type format:**

```java
public static final CodecValueReader<String, EClass> CUSTOM_READER =
    new CodecValueReader<>() {
        @Override
        public String getName() {
            return "CUSTOM_TYPE_READER";
        }

        @Override
        public EClass readValue(String value, DeserializationContext ctxt) {
            if(value.startsWith("test.")) {
                value = value.substring(5);
            }
            return CodecIOHelper.findEClassByName(value, null);
        }
    };

CodecOptionsBuilder.create()
    .rootObject(personClass)
    .forClass(personClass)
        .typeValueReader(CUSTOM_READER)
    .and()
    .build();
```

**JSON:**
```json
{
  "_type": "test.Person",
  "name": "Mario"
}
```

---

## Best Practices

### 1. Choose the Right Discrimination Mode

**Use Value-Based when:**
- You have a dedicated discriminator field (e.g., `"type": "sensor"`)
- The JSON structure has a consistent type field across variants
- You control the JSON format

**Use Feature-Based when:**
- Working with JSON Schema `oneOf` patterns
- The discriminator is the property name itself
- Different variants have mutually exclusive top-level properties
- You're deserializing from external APIs with variant structures

### 2. Leverage Type Annotation Inheritance

**DO:**
```xml
<!-- Define once on parent -->
<eClassifiers name="Person">
  <eAnnotations source="codec.type">
    <details key="typeKey" value="*"/>
    <details key="companyId" value="BusinessPerson"/>
  </eAnnotations>
</eClassifiers>

<!-- References inherit automatically -->
<eClassifiers name="Meeting">
  <eStructuralFeatures name="organizer" eType="#//Person"/>
  <eStructuralFeatures name="attendee" eType="#//Person"/>
</eClassifiers>
```

**DON'T:**
```xml
<!-- Duplicate on every reference -->
<eStructuralFeatures name="organizer" eType="#//Person">
  <eAnnotations source="codec.type">
    <details key="typeKey" value="*"/>
    <details key="companyId" value="BusinessPerson"/>
  </eAnnotations>
</eStructuralFeatures>
```

### 3. Use NAME Strategy for Human-Readable Output

```java
.typeStrategy("NAME")  // "Person" instead of "http://..."
```

Smaller JSON, easier debugging, but ensure class names are unique.

### 4. Prefer Model Annotations Over Runtime Options

**Model annotations:**
- Version controlled with your model
- Self-documenting
- Applied automatically
- Testable

**Runtime options:**
- Useful for one-off overrides
- Dynamic behavior
- Testing different configurations

### 5. Use CodecOptionsBuilder for Runtime Configuration

**Prefer:**
```java
CodecOptionsBuilder.create()
    .forClass(personClass)
        .typeKey("*")
    .and()
    .build();
```

**Over:**
```java
Map<String, Object> options = new HashMap<>();
Map<EClass, Map<String, Object>> codecOptions = new HashMap<>();
// ... lots of map nesting
```

### 6. Document Type Mappings

Add documentation annotations to your .ecore file:

```xml
<eAnnotations source="http://www.eclipse.org/emf/2002/GenModel">
  <details key="documentation" value="Type discrimination based on presence of companyId field"/>
</eAnnotations>
<eAnnotations source="codec.type">
  <details key="typeKey" value="*"/>
  <details key="companyId" value="BusinessPerson"/>
</eAnnotations>
```

### 7. Test Both Serialization and Deserialization

Ensure round-trip consistency:
```java
// Serialize
Person person = new BusinessPersonImpl();
person.setCompanyId("ACME-001");
resource.save(outputStream, options);

// Deserialize
resource.load(inputStream, options);
EObject result = resource.getContents().get(0);
assertThat(result).isInstanceOf(BusinessPerson.class);
```

### 8. Handle Missing Discriminators Gracefully

When using feature-based discrimination, the deserializer will:
1. Scan all properties for a match in typeMap
2. If no match found, use the declared type (e.g., Person)
3. Log a warning for debugging

Ensure your typeMap covers all expected variants.

---

## Troubleshooting

### Issue: Type discrimination not working

**Check:**
1. Is `typeKey` set correctly? (`"*"` for feature-based, field path for value-based)
2. Does `typeMap` contain the correct mappings?
3. Is the `strategy` compatible with typeMap values?
4. For EReferences: Is inheritance enabled?

### Issue: Wrong type deserialized

**Debug:**
```java
// Enable detailed logging
codecModelInfo.getCodecInfoForEClass(targetClass).ifPresent(info -> {
    System.out.println("TypeInfo: " + info.getTypeInfo());
    System.out.println("TypeKey: " + info.getTypeInfo().getTypeKey());
    System.out.println("TypeMap: " + info.getTypeInfo().getTypeMap());
});
```

### Issue: Inheritance not working

**Verify:**
1. Target EClass has `codec.type` annotation
2. Reference doesn't have `inherits.from.parent="false"`
3. Runtime options don't override with `.inheritsTypeFromParent(false)`

### Issue: Custom reader/writer not found

**Ensure:**
1. Reader/writer is registered in CodecModelInfo
2. Name matches exactly
3. Service is available in OSGi context (if using OSGi)

---

## Migration Guide

### From Previous Versions

**If you manually copied type annotations to every reference:**
1. Remove duplicate annotations from EReferences
2. Keep only the annotation on the target EClass
3. Inheritance will apply automatically

**If you manually built typeMap in code:**
1. Consider moving to model annotations
2. Use CodecOptionsBuilder for cleaner syntax

### JSON Schema Integration

When generating EPackages from JSON Schema with `oneOf`:
1. The generator automatically creates discriminated union structures
2. Type annotations with `typeKey="*"` are added automatically
3. All references inherit automatically
4. No manual configuration needed!

---

## Related Documentation

- [CodecTypeInfo.md](./CodecTypeInfo.md) - Original type info documentation (archived)
- [CodecTypeInfoStrategies.md](./CodecTypeInfoStrategies.md) - Type strategies reference (archived)
- [FeatureBasedTypeDiscrimination.md](./FeatureBasedTypeDiscrimination.md) - Feature-based implementation details
- [CodecModelInfo API](../org.eclipse.fennec.codec.info/src/org/eclipse/fennec/codec/info/CodecModelInfo.java)

---

**For questions or issues, please refer to the test cases:**
- `CodecJsonDeserializeTypeInheritanceTest.java` - Type inheritance tests
- `CodecJsonDeserializeTypeTest.java` - General type discrimination tests
- `CodecModelInfoTest.java` - Model annotation processing tests
