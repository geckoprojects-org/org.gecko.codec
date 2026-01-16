# Codec JSON Schema Support

> **⚠️ ARCHIVED DOCUMENT**
>
> This document has been archived and is no longer maintained.
> The authoritative documentation for JSON Schema support is now in:
> - **`docs/codec-v2-spec/16-format-abstraction.md`** - Sections 12.2 (JSON Schema Extension) and 13 (JSON Schema Version Support and Feature Coverage)
>
> This file is preserved for historical reference only.

---

The `org.eclipse.fennec.codec.jsonschema` bundle provides comprehensive support for converting between JSON Schema definitions and Eclipse Modeling Framework (EMF) EPackages. This enables two key workflows:

1. **Schema → Model**: Convert JSON Schema documents to EMF EPackages for runtime model generation
2. **Model → Schema**: Generate JSON Schema from existing EMF EPackages for validation and documentation

## Overview

This functionality has been achieved by adding the ability to customize the `CodecEMFSerializers` and `CodecEMFDeserializers` which are linked to the `CodecModule` when calling `setupModule`.

These are now service components, allowing you to inject custom implementations. However, for the JSON Schema use case, we provide a static configuration since loading a JSON schema is not a common use case and the general setup is unlikely to change. This way, users are not forced to provide configuration every time to make JSON Schema deserialization work.

We instantiate the component classes ourselves and only the dedicated `JsonSchemaResourceFactory` remains a service component. Adding the jsonschema bundle (together with the json bundle) to the runtime should be sufficient—no further configuration is needed from the user.

[!WARNING]

If both the json and the jsonschema `ResourceFactory` are needed we cannot guarantee that 

```java
Resource jsonSchemaRes = resourceSet.createResource(URI.createURI("test.json"), "application/schema+json");
```

would be a `CodecJsonSchemaResource`. This is due to how the `Resource.Factory.Registry` work. It always checks first whether a `Resource.Factory` with the specified file extension is present and, if so, it does not check the compatibility with the content-type. This means, that, if it finds the json `Resource.Factory` it will return it and we are going to end up with a `CodecJsonResource` and not with a `CodecJsonSchemaResource`.

That's why in such cases, we have to create the `Resource` directly via the proper `Resource.Factory`

```java
@Reference(filter = "(" + EMFNamespaces.EMF_MODEL_CONTENT_TYPE + "=application/schema+json)")
Resource.Factory resourceFactory;

Resource jsonSchemaRes = resourceFactory.createResource(URI.createURI("test.json"));
```

This will guarantee that the resource created will be of type `CodecJsonSchemaResource`.



## The JsonSchema Serializers/Deserializers

In the `JsonSchemaCodecEMFSerializers` and `JsonSchemaCodecEMFDeserializers`  we call, when the type to be (de-)serialized is an `EObject` a `SmartJsonSchemaDeserializer` or `SmartJsonSchemaSerializer`, that checks, based on the information retrieved by the `CodecEMFContext`, whether the current object or the current feature (if we are not in the root object) contain the proper `jsonschema` extra annotation. 

This can be set either via annotations, under source `codec.extras`, or via load/save options using the `CodecModelInfoOptions.CODEC_EXTRAS`, both at the level of an `EClassifier` or of an `EReference`.

Together with `jsonschema`, one can specify another details entry for the `codec.extras`, namely `jsonschema.feature.key`, so that to specify whether the jsonschema is contained within an inner node. 

```json
"components": {
		"schemas": {
			"Alarm": {
				"type": "object",
				"properties": {
					"flags": {
						"type": "integer",
						"format": "int32"
						}
                	}
				}
			}
		}
			
```

If this is part of a model, in which the `schemas` reference of the `components` object is annotated with `jsonschema`, no `jsonschema.feature.key` has to be provided.

Instead, if we want to deserialize a root object directly into an `EPackage`, like:

```json
"schemas": {
			"Alarm": {
				"type": "object",
				"properties": {
					"flags": {
						"type": "integer",
						"format": "int32"
						}
                	}
				}
			}
```

we have to specify that the `jsonschema.feature.key` is `schemas`.

## Supported JSON Schema Features

The JSON Schema codec supports a comprehensive set of JSON Schema draft specifications, with focus on Draft 7, Draft 2019-09, and Draft 2020-12. Below is a detailed overview of supported features:

### Core Type Mappings

| JSON Schema Type | EMF Type | Notes |
|-----------------|----------|-------|
| `string` | EString | Basic string type |
| `number` | EDouble | Floating-point numbers |
| `integer` | EInt | Integer values |
| `boolean` | EBoolean | Boolean values |
| `object` | EClass | Mapped to EMF classes with features |
| `array` | EAttribute/EReference | With `upperBound = -1` (many) |
| `null` | N/A | Handled via nullability |

### Advanced Features

#### Object Properties
- **properties**: Mapped to EAttributes or EReferences
- **required**: Sets `lowerBound = 1` on the corresponding EStructuralFeature
- **additionalProperties**: Partially supported
- **patternProperties**: Not directly supported

#### Type Composition
- **allOf**: Creates inheritance hierarchy with EClass supertypes
- **oneOf**: Creates abstract EClass with concrete variants (see limitations below)
- **anyOf**: Similar to oneOf handling
- **not**: Not supported

#### String Formats
Common formats are recognized and preserved in annotations:
- `date-time`, `date`, `time`
- `email`, `hostname`, `uri`, `uri-reference`
- `uuid`, `ipv4`, `ipv6`

#### Numeric Constraints
- `minimum`, `maximum`: Stored in annotations
- `exclusiveMinimum`, `exclusiveMaximum`: Stored in annotations
- `multipleOf`: Stored in annotations

#### Array Constraints
- `items`: Defines array element type
- `minItems`, `maxItems`: Stored in annotations
- `uniqueItems`: Stored in annotations

#### References
- **$ref**: Resolved to EReferences pointing to other EClasses
- **$defs** / **definitions**: Mapped to EClassifiers in the EPackage

### Annotations and Metadata

JSON Schema metadata is preserved in EMF EAnnotations:
- `title` and `description` are stored in annotations
- Custom properties are preserved under `http://fennec.eclipse.org/jsonschema` annotation source
- Constraints (min/max, patterns, etc.) are stored for potential validation

## Working with Generated EPackages

Once you've converted a JSON Schema to an EPackage, you can use it for deserializing JSON data that conforms to the schema.

### Example: Simple Schema

```java
// Step 1: Load JSON Schema and convert to EPackage
Resource schemaRes = resourceSet.createResource(
    URI.createURI("meter-reading.json"),
    "application/schema+json"
);

Map<String, Object> schemaOptions = CodecOptionsBuilder.create()
    .rootObject(EcorePackage.Literals.EPACKAGE)
    .serializeType(false)
    .serializeEmptyValue(true)
    .serializeNullValue(true)
    .forClass(EcorePackage.Literals.EPACKAGE)
    .withExtraProperties(Map.of(
        "jsonschema", "true",
        "jsonschema.feature.key", "definitions"
    ))
    .build();

schemaRes.load(schemaOptions);
EPackage ePackage = (EPackage) schemaRes.getContents().get(0);

// Step 2: Register the generated EPackage
resourceSet.getPackageRegistry().put(ePackage.getNsURI(), ePackage);
codecModelInfo.put(ePackage.getNsURI(), ePackage); // Important for codec metadata

// Step 3: Find the target EClass
EClass meterReadingClass = (EClass) ePackage.getEClassifier("MeterReading");

// Step 4: Deserialize JSON data using the generated EPackage
Resource dataRes = resourceSet.createResource(URI.createURI("meter-reading-data.json"));
Map<String, Object> dataOptions = CodecOptionsBuilder.create()
    .rootObject(meterReadingClass)
    .serializeType(false)
    .build();

dataRes.load(dataOptions);
EObject meterReading = dataRes.getContents().get(0);

// Access attributes dynamically
EAttribute idAttr = (EAttribute) meterReadingClass.getEStructuralFeature("id");
Object id = meterReading.eGet(idAttr);
```

### Important: CodecModelInfo Registration

When working with dynamically generated EPackages, you must register them in **both** locations:

1. **ResourceSet's package registry** - so the ResourceSet can find the EPackage by URI:
   ```java
   resourceSet.getPackageRegistry().put(ePackage.getNsURI(), ePackage);
   ```

2. **CodecModelInfo service** - to generate the codec metadata (readers/writers) needed for serialization:
   ```java
   codecModelInfo.put(ePackage.getNsURI(), ePackage);
   ```

Without the `CodecModelInfo` registration, the codec framework won't have the necessary metadata to properly serialize/deserialize EObjects of the dynamically generated types.

## Limitations and Known Issues

### oneOf / Union Types

JSON Schema's `oneOf` construct creates challenges for deserialization:

**The Problem:**
- When converting `oneOf` to EMF, an abstract EClass is typically generated with concrete variant subclasses
- During deserialization, the codec needs to determine which concrete subclass to instantiate
- JSON data doesn't always contain explicit type discriminators

**Example:**
```json
{
  "input": {
    "oneOf": [
      { "required": ["kafka"], "properties": { "kafka": {...} } },
      { "required": ["file"], "properties": { "file": {...} } }
    ]
  }
}
```

This generates an abstract `InputNode` EClass with concrete variants. When deserializing:
```json
{
  "input": {
    "kafka": {
      "addresses": ["localhost:9092"],
      "topics": ["my-topic"]
    }
  }
}
```

The deserializer encounters an abstract `InputNode` type and cannot determine which concrete variant to instantiate.

**Current Workaround:**

You must provide explicit **type mapping** via load options using the codec's type info strategies:

```java
Map<String, Object> options = CodecOptionsBuilder.create()
    .rootObject(rootEClass)
    .forClass(inputNodeClass)
        .typeKey("_type")  // Field name for type discriminator
        .typeStrategy("URI")  // or "NAME"
        .typeMap(Map.of(
            "kafka", "http://example.com/schema#//KafkaInputNode",
            "file", "http://example.com/schema#//FileInputNode"
        ))
    .build();
```

**Known Limitation:**

Type mapping based solely on **property name presence** (discriminating based on which property is set, e.g., "kafka" vs "file") is **not currently supported**. You must:

1. Either add explicit type discriminator fields to your JSON data
2. Or pre-determine the type through other means and specify it in options

This is an area for future enhancement where the codec could automatically detect which oneOf variant matches based on the properties present in the JSON.

### anyOf Handling

Similar limitations apply to `anyOf` constructs as they also create union types.

### Complex Nested References

Deeply nested `$ref` chains may have performance implications during schema conversion. Consider flattening complex reference structures where possible.

## Testing

The JSON Schema support has been validated with:

1. **Simple schemas** with basic types, required fields, and straightforward object structures
2. **Complex schemas** with nested objects, arrays, and multiple `$defs`
3. **Round-trip conversion** (Schema → EPackage → Schema) to ensure fidelity
4. **Data deserialization** from JSON conforming to converted schemas

Test cases include:
- Meter reading data with timestamps and numeric values
- Pipeline configurations with multiple processor types
- Nested object structures with references

For complex structures involving `oneOf`, additional configuration via type mapping is currently required (see limitations above).