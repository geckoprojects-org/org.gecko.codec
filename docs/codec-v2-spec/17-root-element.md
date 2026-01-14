# 17. Root Element and Multiple Contents

## Overview

This section defines how the codec handles root-level serialization, including:
- Single root object vs. multiple root objects
- JSON array as root element
- `CODEC_ROOT_OBJECT` type hint for deserialization

## 1. EMF Resource Contents

An EMF Resource can contain multiple root-level EObjects in its `contents` list:

```java
Resource resource = ...;
resource.getContents().add(person1);  // First root object
resource.getContents().add(person2);  // Second root object
resource.getContents().add(person3);  // Third root object
```

The codec must handle both single and multiple root objects.

---

## 2. Serialization

### 2.1 Single Root Object

When the resource contains exactly one root object, it is serialized as a JSON object:

```json
{
  "_type": "Person",
  "name": "John Doe",
  "age": 30
}
```

### 2.2 Multiple Root Objects

When the resource contains multiple root objects, they are serialized as a JSON array:

```json
[
  {
    "_type": "Person",
    "name": "John Doe",
    "age": 30
  },
  {
    "_type": "Person",
    "name": "Jane Smith",
    "age": 25
  }
]
```

---

## 3. Deserialization

### 3.1 Automatic Root Detection

The deserializer automatically detects whether the root is an object or array:

| JSON Root Token | Behavior |
|-----------------|----------|
| `{` (START_OBJECT) | Deserialize single root object |
| `[` (START_ARRAY) | Deserialize each array element as a root object |

### 3.2 Type Resolution for Root Objects

Each root object needs type information for deserialization. Type can be determined by:

1. **Explicit `_type` field** in JSON (standard approach)
2. **`CODEC_ROOT_OBJECT` load option** (type hint)
3. **Discriminator path** (MAPPED type strategy)

---

## 4. CODEC_ROOT_OBJECT Option

### 4.1 Purpose

When JSON does not contain type information (no `_type` field), the `CODEC_ROOT_OBJECT` option provides a type hint for deserialization.

### 4.2 Usage

```java
Map<String, Object> options = new HashMap<>();
options.put(CodecResource.CODEC_ROOT_OBJECT, personEClass);

resource.load(inputStream, options);
```

### 4.3 Behavior with Arrays

When the root is a JSON array and `CODEC_ROOT_OBJECT` is specified:
- The type hint applies to **each element** in the array
- All elements are deserialized using the specified EClass
- Each deserialized EObject is added to `resource.getContents()`

**Example:**

JSON (no type information):
```json
[
  { "name": "John Doe", "age": 30 },
  { "name": "Jane Smith", "age": 25 }
]
```

Java:
```java
options.put(CodecResource.CODEC_ROOT_OBJECT, personEClass);
resource.load(inputStream, options);

// Result: resource.getContents() contains 2 Person objects
assertEquals(2, resource.getContents().size());
assertTrue(resource.getContents().get(0).eClass() == personEClass);
```

### 4.4 Type Hint Priority

When both `_type` field and `CODEC_ROOT_OBJECT` are present:

| Priority | Source | Behavior |
|----------|--------|----------|
| 1 (highest) | `_type` field in JSON | Used for type resolution |
| 2 | `CODEC_ROOT_OBJECT` option | Fallback if no `_type` field |

The `_type` field always takes precedence. This allows:
- Default type via `CODEC_ROOT_OBJECT`
- Override per-object via `_type` field

---

## 5. Implementation Details

### 5.1 CodecResource.doLoad()

The `CodecResource.doLoad()` method handles root detection:

```java
JsonToken firstToken = parser.nextToken();

if (firstToken == JsonToken.START_ARRAY) {
    // Multiple root objects - read each element
    while (parser.nextToken() != JsonToken.END_ARRAY) {
        EObject result = reader.readValue(parser);
        if (nonNull(result)) {
            getContents().add(result);
        }
    }
} else if (firstToken == JsonToken.START_OBJECT) {
    // Single root object
    EObject result = reader.readValue(parser);
    if (nonNull(result)) {
        getContents().add(result);
    }
}
```

### 5.2 Type Hint Propagation

The `CODEC_ROOT_OBJECT` hint is propagated to the deserializer via:

1. **Jackson context attribute**: `ContextHelper.EXPECTED_TYPE`
2. **EMF read context**: `ctx.setCurrentTypeHint(rootEClassHint)`

### 5.3 Deferred Property Processing

When no `_type` field exists:
1. Properties are deferred until type is resolved
2. After loop ends, `CODEC_ROOT_OBJECT` hint is applied
3. EObject is created with the hint EClass
4. Deferred properties are replayed through proper deserialization entries

**Important:** Deferred values are replayed through `TokenBuffer` to ensure proper type conversion (e.g., `Long` → `BigInteger`).

---

## 6. Configuration

### 6.1 Load Options

| Option | Type | Description |
|--------|------|-------------|
| `CODEC_ROOT_OBJECT` | `EClass` | Type hint for root object(s) |

### 6.2 EAnnotation

There is no EAnnotation equivalent for `CODEC_ROOT_OBJECT` since it's a runtime load option that depends on the JSON being loaded.

---

## 7. Examples

### 7.1 Loading External API Data

Common use case: Loading JSON from an external API that doesn't include EMF type information.

**JSON (sites.json):**
```json
[
  {
    "id": 100046725,
    "name": "Jena-Goldbergrampe",
    "location": { "lat": 50.888, "lon": 11.612 }
  },
  {
    "id": 100046726,
    "name": "Jena-Oberaue",
    "location": { "lat": 50.923, "lon": 11.589 }
  }
]
```

**Java:**
```java
// Load the Ecore model
EPackage bikePackage = loadEcore("bike.ecore");
EClass siteClass = (EClass) bikePackage.getEClassifier("site");

// Configure codec with metadata
MetadataService metadataService = MetadataServiceFactory.create();
metadataService.registerPackage(bikePackage);

// Load JSON with type hint
CodecResource resource = new CodecResource(uri, metadataService, config, null);
Map<String, Object> options = new HashMap<>();
options.put(CodecResource.CODEC_ROOT_OBJECT, siteClass);

resource.load(new FileInputStream("sites.json"), options);

// Access loaded sites
for (EObject site : resource.getContents()) {
    System.out.println(site.eGet(siteClass.getEStructuralFeature("name")));
}
```

### 7.2 Mixed Types in Array

If different types are needed in the same array, each element must have a `_type` field:

```json
[
  { "_type": "Person", "name": "John" },
  { "_type": "Company", "name": "Acme Corp" }
]
```

---

## 8. Error Handling

| Scenario | Behavior |
|----------|----------|
| No `_type` and no `CODEC_ROOT_OBJECT` | ERROR: "Cannot deserialize: no type information found and no CODEC_ROOT_OBJECT hint" |
| Invalid `CODEC_ROOT_OBJECT` (not an EClass) | ERROR: Type hint must be an EClass |
| Abstract EClass as hint | ERROR: Cannot instantiate abstract class |
| JSON property not in EClass | WARNING: Unknown property (skipped) |
| Type mismatch (e.g., string where object expected) | WARNING: Expected START_OBJECT, got VALUE_STRING |

---

## 9. Test Coverage

| Test | Description |
|------|-------------|
| `CodecResourceArrayRootTest` | Tests for array root handling |
| `BikeSitesArrayLoadTest` | Real-world test loading external JSON array |

See [15-test-coverage.md](15-test-coverage.md) for complete test matrix.
