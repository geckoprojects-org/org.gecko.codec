# 18. Feature Type Hints and Value Reader Options

[← Back to Overview](00-overview.md) | [← Root Element](17-root-element.md)

---

## Overview

This section defines how the codec handles type hints and value reader configuration for individual features via Load/Save options. This is particularly important for `EObject`-typed features where the concrete type cannot be determined from the JSON alone.

### Configuration Principle

As stated in the overview, every configuration that can be set via EAnnotations must also be available as:
1. **EAnnotation** - Declarative, static configuration in the .ecore model
2. **CodecOptionsBuilder** - Programmatic configuration for code-based setup
3. **Load/Save Options** - Map-based properties for Spring/OSGi integration

This chapter focuses on the **Load/Save Options** for feature-specific configuration.

---

## 1. Problem Statement

Some EMF models have features typed as `EObject` to allow arbitrary content:

```xml
<!-- OpenAPI Example model -->
<eStructuralFeatures xsi:type="ecore:EReference" name="value"
    eType="ecore:EClass http://www.eclipse.org/emf/2002/Ecore#//EObject"
    containment="true"/>
```

During deserialization, when the codec encounters such a feature:
1. The declared type is `EObject` (abstract, cannot be instantiated)
2. The JSON may not contain `_type` information
3. The codec needs a hint to determine the concrete type

**Use Case: OpenAPI**

The OpenAPI model has generic `EObject` features (e.g., `Example.value`, `Extension.value`) where the concrete type depends on the application context. EAnnotations don't make sense here because:
- The OpenAPI model itself is fixed
- The concrete type comes from the application's domain model
- Different applications need different types for the same feature

---

## 2. Load Options

### 2.1 CODEC_FEATURE_TYPE_HINTS

Provides EClass type hints for specific features.

```java
/**
 * Load option key for feature-specific type hints.
 * Value: Map<EStructuralFeature, EClass>
 */
public static final String CODEC_FEATURE_TYPE_HINTS = "CODEC_FEATURE_TYPE_HINTS";
```

**Usage:**

```java
Map<EStructuralFeature, EClass> typeHints = new HashMap<>();
typeHints.put(
    OpenAPIPackage.eINSTANCE.getExample_Value(),
    PersonPackage.eINSTANCE.getPerson()
);
typeHints.put(
    OpenAPIPackage.eINSTANCE.getExtension_Value(),
    ConfigPackage.eINSTANCE.getConfiguration()
);

Map<String, Object> options = new HashMap<>();
options.put(CodecOptions.CODEC_FEATURE_TYPE_HINTS, typeHints);

resource.load(inputStream, options);
```

### 2.2 CODEC_FEATURE_VALUE_READERS

Provides ValueReader names for specific features. This is the Load-Option equivalent of the `valueReaderName` EAnnotation.

```java
/**
 * Load option key for feature-specific value readers.
 * Value: Map<EStructuralFeature, String> where String is the registered reader name
 */
public static final String CODEC_FEATURE_VALUE_READERS = "CODEC_FEATURE_VALUE_READERS";
```

**Usage:**

```java
Map<EStructuralFeature, String> readerHints = new HashMap<>();
readerHints.put(
    OpenAPIPackage.eINSTANCE.getExample_Value(),
    "dynamicJsonReader"
);
readerHints.put(
    OpenAPIPackage.eINSTANCE.getComponents_Schemas(),
    "jsonSchemaToEPackage"
);

Map<String, Object> options = new HashMap<>();
options.put(CodecOptions.CODEC_FEATURE_VALUE_READERS, readerHints);

resource.load(inputStream, options);
```

### 2.3 CODEC_FEATURE_VALUE_WRITERS

Provides ValueWriter names for specific features. This is the Save-Option equivalent of the `valueWriterName` EAnnotation.

```java
/**
 * Save option key for feature-specific value writers.
 * Value: Map<EStructuralFeature, String> where String is the registered writer name
 */
public static final String CODEC_FEATURE_VALUE_WRITERS = "CODEC_FEATURE_VALUE_WRITERS";
```

**Usage:**

```java
Map<EStructuralFeature, String> writerHints = new HashMap<>();
writerHints.put(
    OpenAPIPackage.eINSTANCE.getComponents_Schemas(),
    "ePackageToJsonSchema"
);

Map<String, Object> options = new HashMap<>();
options.put(CodecOptions.CODEC_FEATURE_VALUE_WRITERS, writerHints);

resource.save(outputStream, options);
```

---

## 3. Resolution Priority

When deserializing a feature, the codec resolves configuration in this order:

| Priority | Source | Description |
|----------|--------|-------------|
| 1 (highest) | `_type` field in JSON | Explicit type information in the data |
| 2 | `CODEC_FEATURE_VALUE_READERS` | Load option - ValueReader name |
| 3 | `CODEC_FEATURE_TYPE_HINTS` | Load option - EClass hint |
| 4 | EAnnotation `valueReaderName` | Static model configuration |
| 5 | Standard type resolution | Uses declared reference type |

### 3.1 Resolution Flow

```
Deserializing EObject-typed feature
    │
    ├─ JSON contains _type field?
    │       │
    │       └─ YES: Use _type for type resolution
    │
    ├─ CODEC_FEATURE_VALUE_READERS contains entry for this feature?
    │       │
    │       └─ YES: Delegate to registered ValueReader
    │
    ├─ CODEC_FEATURE_TYPE_HINTS contains entry for this feature?
    │       │
    │       └─ YES: Use EClass as concrete type
    │
    ├─ EAnnotation specifies valueReaderName?
    │       │
    │       └─ YES: Delegate to registered reader
    │
    └─ No hint available:
            │
            ├─ Try standard type resolution from JSON
            │
            └─ If fails: Log WARNING, skip feature
```

### 3.2 Why ValueReader has Priority over EClass Hint

When both `CODEC_FEATURE_VALUE_READERS` and `CODEC_FEATURE_TYPE_HINTS` have entries for the same feature, the ValueReader takes priority because:

1. **Full Control**: A ValueReader provides complete control over deserialization
2. **Complex Types**: Some features need custom parsing logic, not just type instantiation
3. **Explicit Intent**: Specifying a reader indicates intent to handle the feature specially

---

## 4. Behavior Without Hints

When no type hint is provided and the JSON lacks `_type` information:

### 4.1 Warning Logged

```
WARNING: Cannot deserialize feature 'value' of type EObject:
         no type information found and no CODEC_FEATURE_TYPE_HINTS provided.
         Feature will be skipped.
```

### 4.2 Feature Skipped

The feature value remains `null` (or empty for multi-valued features).

### 4.3 No Exception Thrown

Deserialization continues with other features. This is intentional to allow partial loading.

---

## 5. Combining Options

### 5.1 Using Both Options Together

You can use `CODEC_FEATURE_TYPE_HINTS` and `CODEC_FEATURE_VALUE_READERS` for different features:

```java
// Type hints for simple cases
Map<EStructuralFeature, EClass> typeHints = new HashMap<>();
typeHints.put(
    OpenAPIPackage.eINSTANCE.getExample_Value(),
    PersonPackage.eINSTANCE.getPerson()
);

// ValueReaders for complex cases
Map<EStructuralFeature, String> readerHints = new HashMap<>();
readerHints.put(
    OpenAPIPackage.eINSTANCE.getComponents_Schemas(),
    "jsonSchemaToEPackage"
);

Map<String, Object> options = new HashMap<>();
options.put(CodecOptions.CODEC_FEATURE_TYPE_HINTS, typeHints);
options.put(CodecOptions.CODEC_FEATURE_VALUE_READERS, readerHints);

resource.load(inputStream, options);
```

### 5.2 Same Feature in Both Options

If the same feature appears in both options, `CODEC_FEATURE_VALUE_READERS` wins:

```java
// Both options have entry for Example_Value
typeHints.put(OpenAPIPackage.eINSTANCE.getExample_Value(), PersonEClass);
readerHints.put(OpenAPIPackage.eINSTANCE.getExample_Value(), "customReader");

// Result: "customReader" is used, PersonEClass is ignored
```

### 5.3 Type Hint Available to ValueReaders

A `ReferenceValueReader` can access the type hint as additional context:

```java
public class DynamicObjectReader implements ReferenceValueReader<EObject> {

    @Override
    public EObject read(JsonParser parser, EReference ref, DeserializationContext ctxt)
            throws IOException {

        // Check if there's a type hint available (from CODEC_FEATURE_TYPE_HINTS)
        EClass typeHint = ctxt.getAttribute(ContextHelper.FEATURE_TYPE_HINT);

        if (typeHint != null) {
            // Use hint to create properly typed object
            EObject result = EcoreUtil.create(typeHint);
            // ... populate from JSON
            return result;
        }

        // Fallback: create dynamic object
        return createDynamicEObject(parser);
    }
}
```

This allows a generic reader to use the type hint when available, while still providing custom parsing logic.

---

## 6. Multi-Valued Features

For `EObject[*]` features (upperBound = -1), the type hint applies to **all elements**:

```java
typeHints.put(
    OpenAPIPackage.eINSTANCE.getCallback_Paths(),  // EObject[*]
    PathItemPackage.eINSTANCE.getPathItem()
);
```

**JSON:**
```json
{
  "paths": [
    { "get": { ... } },
    { "post": { ... } }
  ]
}
```

Each array element is deserialized as `PathItem`.

### 6.1 Mixed Types in Arrays

If different types are needed, each element must have a `_type` field:

```json
{
  "items": [
    { "_type": "Person", "name": "John" },
    { "_type": "Company", "name": "Acme" }
  ]
}
```

The `_type` field overrides the type hint per element.

---

## 7. Map Entry Features (AnyEntry)

For `AnyEntry` map features where values can be arbitrary:

```java
typeHints.put(
    OpenAPIPackage.eINSTANCE.getLink_Parameters(),  // AnyEntry[*]
    StringPackage.eINSTANCE.getString()  // Hint: values are strings
);
```

The hint applies to the `value` part of each map entry.

---

## 8. Configuration via CodecOptionsBuilder

```java
Map<String, Object> options = CodecOptionsBuilder.create()
    // EClass type hints
    .featureTypeHint(
        OpenAPIPackage.eINSTANCE.getExample_Value(),
        PersonPackage.eINSTANCE.getPerson()
    )
    // ValueReader names
    .featureValueReader(
        OpenAPIPackage.eINSTANCE.getComponents_Schemas(),
        "jsonSchemaToEPackage"
    )
    // ValueWriter names
    .featureValueWriter(
        OpenAPIPackage.eINSTANCE.getComponents_Schemas(),
        "ePackageToJsonSchema"
    )
    .build();

resource.load(inputStream, options);
```

---

## 9. Implementation Details

### 9.1 Option Constants

```java
public class CodecOptions {
    /**
     * Load option: Map of EStructuralFeature to EClass type hint.
     */
    public static final String CODEC_FEATURE_TYPE_HINTS = "CODEC_FEATURE_TYPE_HINTS";

    /**
     * Load option: Map of EStructuralFeature to ValueReader name.
     */
    public static final String CODEC_FEATURE_VALUE_READERS = "CODEC_FEATURE_VALUE_READERS";

    /**
     * Save option: Map of EStructuralFeature to ValueWriter name.
     */
    public static final String CODEC_FEATURE_VALUE_WRITERS = "CODEC_FEATURE_VALUE_WRITERS";
}
```

### 9.2 Context Propagation

The options are propagated to deserializers via Jackson context:

```java
// In CodecResource.doLoad()
Map<EStructuralFeature, EClass> typeHints =
    (Map<EStructuralFeature, EClass>) options.get(CODEC_FEATURE_TYPE_HINTS);
Map<EStructuralFeature, String> readerHints =
    (Map<EStructuralFeature, String>) options.get(CODEC_FEATURE_VALUE_READERS);

if (typeHints != null) {
    ctxt.setAttribute(ContextHelper.FEATURE_TYPE_HINTS, typeHints);
}
if (readerHints != null) {
    ctxt.setAttribute(ContextHelper.FEATURE_VALUE_READERS, readerHints);
}
```

### 9.3 Lookup in Deserializer

```java
// In ReferenceDeserializationEntry
private Object resolveDeserializationStrategy(EReference reference, DeserializationContext ctxt) {
    // 1. Check for ValueReader hint (highest priority among options)
    Map<EStructuralFeature, String> readerHints =
        ctxt.getAttribute(ContextHelper.FEATURE_VALUE_READERS);

    if (readerHints != null && readerHints.containsKey(reference)) {
        String readerName = readerHints.get(reference);
        return registry.getReader(readerName).orElse(null);
    }

    // 2. Check for EClass type hint
    Map<EStructuralFeature, EClass> typeHints =
        ctxt.getAttribute(ContextHelper.FEATURE_TYPE_HINTS);

    if (typeHints != null && typeHints.containsKey(reference)) {
        return typeHints.get(reference);  // Return EClass
    }

    // 3. Fall back to EAnnotation / declared type
    return null;
}
```

---

## 10. Error Handling and Diagnostics

### 10.1 Error and Warning Scenarios

| Scenario | Severity | Behavior |
|----------|----------|----------|
| EObject-typed feature without hint and without `_type` | WARNING | Feature skipped, value is `null`, warning diagnostic raised |
| `CODEC_FEATURE_TYPE_HINTS` map is empty | WARNING | Treated as no option provided, warning diagnostic raised |
| `null` value in `CODEC_FEATURE_TYPE_HINTS` map | WARNING | Treated as no hint for that feature, warning diagnostic raised |
| `CODEC_FEATURE_TYPE_HINTS` value is not EClass | ERROR | Invalid type hint for feature X - expected EClass |
| `CODEC_FEATURE_VALUE_READERS` value is not String | ERROR | Invalid reader hint for feature X - expected String |
| Hint EClass is abstract | ERROR | Cannot instantiate abstract class |
| Hint EClass incompatible with reference type | WARNING | Type hint does not match reference type hierarchy, hint ignored |
| Reader name not found in registry | WARNING | ValueReader 'X' not found, falling back to type hint or skip |
| Writer name not found in registry | WARNING | ValueWriter 'X' not found, falling back to default serialization |
| JSON value incompatible with hint | WARNING | Type mismatch, feature skipped |

### 10.2 Type Compatibility Validation

When a type hint is provided, it must be compatible with the reference's declared type:

```java
// Reference type: EObject (or any supertype)
// Valid hints: any concrete EClass (Person, Address, etc.)

// Reference type: Person (concrete or abstract)
// Valid hints: Person or any subclass of Person
// Invalid hints: Address (not in Person hierarchy) -> WARNING raised
```

If the hint EClass is not assignment-compatible with the reference's eType, the codec:
1. Raises a WARNING diagnostic
2. Ignores the hint
3. Falls back to default type resolution (which may result in feature being skipped)

### 10.3 `_type` Field Requirements

When using `_type` in JSON without a type hint, the `_type` value should be:

1. **Preferred**: Full EClass URI (e.g., `"http://example.org/model#//Person"`)
2. **With ROOT_HINT + smart-compression**: Simple class name may work if unambiguous

Using simple class names without proper context configuration is **not recommended** as it requires:
- `CODEC_ROOT_OBJECT` option set
- Smart compression enabled
- Unambiguous class name within registered packages

```json
// Recommended: Full URI
{ "_type": "http://example.org/model#//Person", "name": "John" }

// Requires context: Simple name (may fail without proper setup)
{ "_type": "Person", "name": "John" }
```

---

## 11. Examples

### 11.1 OpenAPI Example with Person Value

**OpenAPI JSON:**
```json
{
  "openapi": "3.0.3",
  "info": { "title": "My API", "version": "1.0" },
  "paths": {},
  "components": {
    "examples": {
      "personExample": {
        "summary": "A person example",
        "value": {
          "name": "John Doe",
          "age": 30
        }
      }
    }
  }
}
```

**Java:**
```java
Map<EStructuralFeature, Object> typeHints = new HashMap<>();
typeHints.put(
    OpenAPIPackage.eINSTANCE.getExample_Value(),
    PersonPackage.eINSTANCE.getPerson()
);

Map<String, Object> options = new HashMap<>();
options.put(CodecOptions.CODEC_FEATURE_TYPE_HINTS, typeHints);

resource.load(inputStream, options);

// Access the example value as Person
OpenAPI api = (OpenAPI) resource.getContents().get(0);
Example example = api.getComponents().getExamples().get("personExample").getValue();
Person person = (Person) example.getValue();
assertEquals("John Doe", person.getName());
```

### 11.2 Using ValueReader for Dynamic Content

**Java:**
```java
// Register a dynamic reader
registry.registerReader("dynamicJson", new DynamicJsonReader());

Map<EStructuralFeature, String> readerHints = new HashMap<>();
readerHints.put(
    OpenAPIPackage.eINSTANCE.getExtension_Value(),
    "dynamicJson"
);

Map<String, Object> options = new HashMap<>();
options.put(CodecOptions.CODEC_FEATURE_VALUE_READERS, readerHints);

resource.load(inputStream, options);
```

---

## 12. Relationship to Other Options

| Option | Type | Scope | Purpose |
|--------|------|-------|---------|
| `CODEC_ROOT_OBJECT` | Load | Root element(s) | Type hint for root objects |
| `CODEC_FEATURE_TYPE_HINTS` | Load | Individual features | EClass hints for specific features |
| `CODEC_FEATURE_VALUE_READERS` | Load | Individual features | ValueReader names for specific features |
| `CODEC_FEATURE_VALUE_WRITERS` | Save | Individual features | ValueWriter names for specific features |
| `valueReaderName` (EAnnotation) | Static | Individual features | Custom reader (model config) |
| `valueWriterName` (EAnnotation) | Static | Individual features | Custom writer (model config) |

These options can be combined:

```java
Map<String, Object> loadOptions = new HashMap<>();
loadOptions.put(CodecResource.CODEC_ROOT_OBJECT, openApiEClass);
loadOptions.put(CodecOptions.CODEC_FEATURE_TYPE_HINTS, typeHints);
loadOptions.put(CodecOptions.CODEC_FEATURE_VALUE_READERS, readerHints);

resource.load(inputStream, loadOptions);

Map<String, Object> saveOptions = new HashMap<>();
saveOptions.put(CodecOptions.CODEC_FEATURE_VALUE_WRITERS, writerHints);

resource.save(outputStream, saveOptions);
```

---

## 13. Test Coverage

| Test | Description |
|------|-------------|
| `FeatureTypeHintTest` | Basic EClass type hint resolution |
| `FeatureTypeHintArrayTest` | Type hints for multi-valued features |
| `FeatureValueReaderHintTest` | ValueReader hint via load option |
| `FeatureValueWriterHintTest` | ValueWriter hint via save option |
| `FeatureHintPriorityTest` | Priority: _type > reader > type hint > annotation |
| `FeatureHintWarningTest` | Warning when no hint and no _type |
| `FeatureHintCombinedTest` | Using both type and reader hints together |

---

## 14. Summary

### Three Separate Options

| Option | Map Type | Purpose |
|--------|----------|---------|
| `CODEC_FEATURE_TYPE_HINTS` | `Map<EStructuralFeature, EClass>` | Simple type instantiation |
| `CODEC_FEATURE_VALUE_READERS` | `Map<EStructuralFeature, String>` | Custom deserialization logic |
| `CODEC_FEATURE_VALUE_WRITERS` | `Map<EStructuralFeature, String>` | Custom serialization logic |

### Why Separate Options?

1. **Type Safety**: Clear types for map values (EClass vs String)
2. **Different Use Cases**: Type hints for simple cases, readers/writers for complex cases
3. **Independent Configuration**: Can set type hints without readers, or vice versa
4. **Clearer Intent**: Explicit about what kind of customization is needed

### Priority When Both Set

If both `CODEC_FEATURE_VALUE_READERS` and `CODEC_FEATURE_TYPE_HINTS` contain the same feature:
- **ValueReader wins** - it provides full control over deserialization
- The type hint is still available to the reader via context (optional use)

---

[Next: OpenAPI Support →](17-openapi-support.md)
