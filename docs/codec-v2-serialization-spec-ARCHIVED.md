# Codec V2 Serialization Feature Specification

> **⚠️ ARCHIVED**: This document has been superseded by the modular spec in [`codec-v2-spec/`](codec-v2-spec/00-overview.md). Please refer to the new location for the current specification.

---

## Overview

This document defines the serialization features for codec.v2, including configurable strategies for type information, identity, references, and supertype serialization.

---

## 1. Serialization Targets

The codec serializes the following metadata alongside EObject features:

| Target | Purpose | Applies To |
|--------|---------|------------|
| **Type** | Identifies the EClass of an object | All EObjects |
| **ID** | Unique identifier for an object | EObjects with identity |
| **Reference** | Points to non-contained objects | EReference (non-containment) |
| **Cross-Doc Containment** | Points to contained objects in other documents | EReference (containment, cross-document) |
| **SuperType** | Lists supertypes for querying | EObjects (optional) |

### 1.1 EAnnotation and Configuration Parity

Every codec feature that can be configured declaratively via **EAnnotations** on the EMF model should also be configurable programmatically via the **Config Builder** at runtime, and vice versa.

**Configuration Levels (Resolution Order, dynamic before static):**

| Priority | Level | Scope | Description |
|----------|-------|-------|-------------|
| 1 (highest) | **Load/Save options** | Per-operation | Options passed to `resource.save(options)` / `resource.load(options)` |
| 2 | **ResourceFactory defaults** | Per-factory | `defaultSaveOptions`/`defaultLoadOptions` on EMF ResourceFactory |
| 3 | **Codec module config** | Per-codec | Jackson module / codec instance configuration |
| 4 | **Configuration properties** | External | System properties, config files |
| 5 | **EAnnotations** | Per-model | Declared in .ecore model (static) |
| 6 (lowest) | **Built-in defaults** | Global | Hardcoded codec defaults |

**Key Principle:** Every configuration setting (EAnnotation detail keys, Jackson module features) must have a corresponding runtime option key. This allows any setting to be overridden at load/save time without modifying the model or codec configuration.

**Core Annotations (source: `http://eclipse.org/fennec/codec`):**

| Detail Key | Applies To | Purpose |
|------------|------------|---------|
| `codec.type` | EClass, EReference | Marker for type serialization config |
| `codec.id` | EClass | Marker for ID serialization config |
| `codec.reference` | EReference | Marker for reference serialization config |
| `codec.supertype` | EClass | Marker for supertype serialization config |
| `serialize` | EStructuralFeature | Skip feature during serialization (false = transient) |
| `key` | EStructuralFeature | Custom JSON property name |
| `valueWriterName` | EStructuralFeature | Custom value writer reference |
| `valueReaderName` | EStructuralFeature | Custom value reader reference |

**Design Principle:** When documenting a configuration option, always show both:
- The **EAnnotation** approach (for model designers)
- The **Config Builder** approach (for runtime customization)

This ensures flexibility: models can define sensible defaults that integrators can override at runtime without modifying the model.

---

## 2. Serialization Strategies

### 2.1 SerializationFormat: The Codec-Wide Format Strategy

This is a **fundamental, codec-wide concept** that applies uniformly to all serialization targets (type, ID, reference, supertype). Instead of defining separate format enums per target, we use a single `SerializationFormat` enum.

```java
public enum SerializationFormat {
    /**
     * Simple value output - string, number, or array of simple values.
     * The value itself is directly usable without parsing.
     */
    PLAIN,

    /**
     * Nested object output with configurable keys.
     * The value is a map/object containing structured information.
     */
    STRUCTURED
}
```

**Definition:**
- **PLAIN**: The property value is a simple type (string, number, boolean) or an array of simple values
- **STRUCTURED**: The property value is a nested object/map (or an array of objects)

**The test:** Is the value (or each array element) a nested JSON object?

| Format | Single Value | Array Value |
|--------|--------------|-------------|
| PLAIN | `"_type": "Person"` | `"_supertype": ["Entity", "Auditable"]` |
| STRUCTURED | `"_type": { "schema": "...", "name": "..." }` | `"_supertype": [{"schema": "...", "name": "Entity"}, {"name": "Auditable"}]` |

**Format applies to each element, not the container:**

The format determines how each *individual value* is represented, independent of whether it's a single value or part of an array:

```json
// PLAIN format (array of strings)
"_supertype": ["Entity", "Auditable"]

// STRUCTURED format (array of objects)
"_supertype": [
  { "schema": "http://example.org/base/1.0", "name": "Entity" },
  { "name": "Auditable" }
]
```

**Codec-wide default with per-target override:**

```java
// Set codec-wide default
CodecConfig.builder()
    .format(SerializationFormat.STRUCTURED)  // default for all targets
    .build();

// Override for specific target
IdSerializationConfig.builder()
    .format(SerializationFormat.PLAIN)  // IDs stay plain
    .build();
```

**Why this matters for the serializer:**

When the serializer builds the property map:
- **PLAIN**: Write simple value(s) directly to the output
- **STRUCTURED**: Create a nested map/object as the property value

**Example - Type with supertypes enabled:**

*PLAIN (SCHEMA_AND_TYPE) - each property has a simple value:*
```json
{
  "_schema": "http://example.org/person/1.0",
  "_type": "Person",
  "_supertype": ["Entity", "Auditable"]
}
```

*STRUCTURED - `_type` value is a nested object containing all info:*
```json
{
  "_type": {
    "schema": "http://example.org/person/1.0",
    "name": "Person",
    "supertype": ["Entity", "Auditable"]
  }
}
```

### 2.2 Numeric IDs (Optional Optimization)

NUMERIC is an optimization that can be applied **on top of** STRUCTURED format. It uses EMF classifier IDs instead of names for maximum compactness.

| Setting | Output |
|---------|--------|
| STRUCTURED | `"_type": { "schema": "...", "name": "Person" }` |
| STRUCTURED + numeric | `"_type": { "s": "...", "c": 3 }` |

**Configuration:**
```java
CodecConfig.builder()
    .format(SerializationFormat.STRUCTURED)
    .useNumericIds(true)  // applies to type and reference
    .build();
```

**WARNING:** Classifier IDs are assigned based on declaration order and can change when the model evolves. Only use for transient data, not persistent storage.

### 2.3 Strategy Classification

Strategies are classified by their output format:

| Strategy | Output Format | Description |
|----------|---------------|-------------|
| `NAME` | Plain | EClass simple name as string |
| `CLASS` | Plain | Instance class name as string |
| `URI` | Plain | Full EClass URI as string |
| `MAPPED` | Plain | Discriminator value as string |
| `SCHEMA_AND_TYPE` | Plain | Separate top-level fields, each with simple values |
| `STRUCTURED` | Structured | Nested object with schema, name, etc. |
| `NUMERIC` | Structured | Nested object with numeric IDs |

### 2.4 Format Applicability per Target

All targets use the unified `SerializationFormat` enum (PLAIN, STRUCTURED). The table below shows what each format produces:

| Target | PLAIN Output | STRUCTURED Output | Numeric Option |
|--------|--------------|-------------------|----------------|
| Type | `"_type": "Person"` | `"_type": {"schema": "...", "name": "..."}` | `{"s": "...", "c": 3}` |
| ID | `"_id": "John-Doe"` | `"_id": {"firstName": "John", ...}` | N/A |
| Reference | `"employer": "acme-corp"` | `"employer": {"_type": "...", "_ref": "..."}` | Uses numeric type |
| SuperType | `["Entity", "Auditable"]` | `[{"schema": "...", "name": "Entity"}, ...]` | N/A |

**Type strategies vs SerializationFormat:**

For **Type**, the specific strategy (NAME, CLASS, URI, MAPPED, SCHEMA_AND_TYPE) determines *what* plain string to use. These are all PLAIN format variations. STRUCTURED format uses schema+name objects.

| Strategy | Format | Output |
|----------|--------|--------|
| NAME | PLAIN | `"_type": "Person"` |
| CLASS | PLAIN | `"_type": "org.example.Person"` |
| URI | PLAIN | `"_type": "http://...#//Person"` |
| SCHEMA_AND_TYPE | PLAIN | `"_schema": "...", "_type": "Person"` |
| STRUCTURED | STRUCTURED | `"_type": {"schema": "...", "name": "..."}` |

---

## 3. Type Serialization

### 3.1 Type Strategies

#### Plain Strategies (string value for `_type`)

| Strategy | Value | Example |
|----------|-------|---------|
| `NAME` | EClass name | `"_type": "Person"` |
| `CLASS` | Instance class name | `"_type": "org.example.Person"` |
| `URI` | Full EClass URI | `"_type": "http://example.org/person/1.0#//Person"` |
| `MAPPED` | Discriminator value | `"_type": "customer"` |
| `SCHEMA_AND_TYPE` | Name + separate schema field | `"_schema": "...", "_type": "Person"` |

#### MAPPED Strategy (Discriminator-Based Polymorphism)

The MAPPED strategy enables type discrimination based on a value found within the data itself, rather than an explicit type field. This is particularly useful for:
- **IoT/LoRaWAN devices** - where device type is embedded in payload metadata
- **JSON Schema oneOf patterns** - where type is determined by feature presence or values
- **Legacy APIs** - where type information is encoded in application-specific fields

**How it works:**

1. **Base class** defines the discriminator feature path (which field contains the type indicator)
2. **Concrete classes** define their discriminator value (what value identifies them)
3. **ModelInfoService** builds a reverse lookup map from values → EClasses

**Configuration (via annotations or CodecConfig):**

```java
// On base class (e.g., LoRaWANUplink)
TypeResolutionConfig.builder()
    .strategy(TypeStrategy.MAPPED)
    .typeFeaturePath("deviceInfo.deviceProfileName")  // Path to discriminator
    .build();

// On concrete class (e.g., DraginoLSE01Uplink)
TypeResolutionConfig.builder()
    .strategy(TypeStrategy.MAPPED)
    .discriminatorValue("Dragino_LSE01")  // Value that identifies this class
    .build();
```

**Example Input JSON (no explicit type field):**
```json
{
  "deviceInfo": {
    "deviceProfileName": "Dragino_LSE01",
    "devEui": "A84041..."
  },
  "temperature": 23.5,
  "humidity": 65
}
```

The deserializer reads `deviceInfo.deviceProfileName`, finds `"Dragino_LSE01"`, and uses the reverse lookup to instantiate `DraginoLSE01Uplink`.

**Feature-Based Type Discrimination (JSON Schema oneOf):**

For cases where type is determined by feature presence rather than a single discriminator value:

```java
TypeResolutionConfig.builder()
    .strategy(TypeStrategy.MAPPED)
    .typeFeaturePath("*")  // Special marker for feature-based detection
    .typeMap(Map.of(
        "temperature,humidity", "EnvironmentSensor",
        "latitude,longitude", "GPSTracker"
    ))
    .build();
```

The deserializer checks which features are present and matches against the type map.

#### SCHEMA_AND_TYPE Strategy (Plain)

Split into separate top-level fields (each value is a simple string):

```json
{
  "_schema": "http://example.org/person/1.0",
  "_type": "Person"
}
```

**Configurable keys:**
- `schemaKey`: schema field (default: `_schema`)
- `typeKey`: type field (default: `_type`)

#### Structured Strategies (object value for `_type`)

##### STRUCTURED Strategy

Nested object containing type details:

```json
{
  "_type": {
    "schema": "http://example.org/person/1.0",
    "name": "Person",
    "supertype": ["Entity", "Auditable"]
  }
}
```

**Configurable keys:**
- `typeKey`: outer key (default: `_type`)
- `schemaKey`: schema field inside object (default: `schema`)
- `nameKey`: name field inside object (default: `name`)
- `supertypeKey`: supertype field inside object (default: `supertype`, optional)

##### NUMERIC Strategy

Nested object with schema URI and numeric classifier ID:

```json
{
  "_type": { "s": "http://example.org/person/1.0", "c": 3 }
}
```

**Configurable keys:**
- `typeKey`: outer key (default: `_type`)
- `schemaKey`: schema field (default: `s`)
- `classifierKey`: classifier ID field (default: `c`)

> **Warning:** Classifier IDs are positional and can change when the model evolves. See Section 12.3 for details.

### 3.2 Mixed Supertype Format

In STRUCTURED format, supertypes use a **smart format**:
- Supertypes from **same schema**: plain name only
- Supertypes from **different schema**: full URI

```json
{
  "_type": {
    "schema": "http://example.org/person/1.0",
    "name": "Person",
    "supertype": ["Entity", "http://audit.org/1.0#//Auditable"]
  }
}
```

This is compact yet unambiguous - `Entity` is from `http://example.org/person/1.0`, while `Auditable` includes its full URI.

### 3.3 Type Configuration

The configuration defines **keys and format**, not actual values. Values come from the EObject at runtime.

#### EAnnotation (on EClass)

```xml
<eClassifiers xsi:type="ecore:EClass" name="Person">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="codec.type"/>
    <details key="strategy" value="STRUCTURED"/>
    <details key="typeKey" value="_type"/>
    <details key="include" value="true"/>
  </eAnnotations>
</eClassifiers>
```

**Annotation Details:**
| Key | Values | Default | Description |
|-----|--------|---------|-------------|
| `strategy` | NAME, CLASS, URI, MAPPED, STRUCTURED, SCHEMA_AND_TYPE, NUMERIC | URI | Type serialization strategy |
| `typeKey` | any string | `_type` | JSON property name for type |
| `include` | true, false | true | Whether to include type info |
| `schemaKey` | any string | `schema` | Key for schema in STRUCTURED |
| `nameKey` | any string | `name` | Key for name in STRUCTURED |

#### Java Builder (Runtime Override)

**Minimal (default: URI strategy):**
```java
TypeSerializationConfig config = TypeSerializationConfig.builder().build();
```
**Resulting JSON:**
```json
{
  "_type": "http://example.org/person/1.0#//Person"
}
```

**STRUCTURED format:**
```java
TypeSerializationConfig config = TypeSerializationConfig.builder()
    .structured()
    .build();
```
**Resulting JSON:**
```json
{
  "_type": {
    "schema": "http://example.org/person/1.0",
    "name": "Person"
  }
}
```

**STRUCTURED with supertypes:**
```java
TypeSerializationConfig config = TypeSerializationConfig.builder()
    .structured()
    .includeSupertypes(true)
    .build();
```
**Resulting JSON:**
```json
{
  "_type": {
    "schema": "http://example.org/person/1.0",
    "name": "Person",
    "supertype": ["Entity", "Auditable"]
  }
}
```

**With custom keys:**
```java
TypeSerializationConfig config = TypeSerializationConfig.builder()
    .structured()
    .typeKey("$type")
    .schemaKey("ns")
    .nameKey("class")
    .build();
```
**Resulting JSON:**
```json
{
  "$type": {
    "ns": "http://example.org/person/1.0",
    "class": "Person"
  }
}
```

**At serialization time:**
1. Serializer reads `TypeSerializationConfig` from options (Java or JSON)
2. Extracts actual values from EObject's EClass (schema URI, name, supertypes)
3. Combines config (keys/format) + EObject data (values) → JSON output

---

## 4. ID Serialization

### 4.1 ID Feature Resolution

The serializer determines which features to use as ID:

1. **If `idFeatures` is specified** (via annotation or config) → use those features in the specified order
2. **Otherwise** → use all features with `eID="true"` in the EClass, in declaration order

When `idFeatures` points to an **EReference** (containment), the serializer follows the reference and uses the contained object's ID definition.

**Feature Ordering:** When multiple features form a combined ID, the order is preserved as specified:
- For `idFeatures="lastName, firstName"` → combined ID is `"Doe-John"` (not `"John-Doe"`)
- For `idFeatures="firstName, lastName"` → combined ID is `"John-Doe"`

This ordering applies to both PLAIN format (concatenation order) and STRUCTURED format (property order in JSON object).

### 4.2 ID Key Mode

Controls how ID is represented in the JSON output:

| Mode | Description | Example (single) | Example (multiple) |
|------|-------------|------------------|-------------------|
| `ID_ONLY` (default) | Only `_id` key | `"_id": "john"` | `"_id": "John-Doe"` |
| `BOTH` | `_id` + original feature names | `"_id": "john", "myId": "john"` | `"_id": "John-Doe", "firstName": "John", "lastName": "Doe"` |
| `FEATURE_ONLY` | Only original feature names | `"myId": "john"` | `"firstName": "John", "lastName": "Doe"` |

### 4.3 ID Format (Plain vs Structured)

Per Section 2.1, ID values can be plain or structured.

#### Plain Format (default) - Single Feature

**EAnnotation:**
```xml
<eClassifiers xsi:type="ecore:EClass" name="Person">
  <eStructuralFeatures xsi:type="ecore:EAttribute" name="myId" eType=".../EString" iD="true"/>
</eClassifiers>
```

**Config Builder:**
```java
// No config needed - uses defaults (PLAIN format, ID_ONLY mode)
IdSerializationConfig config = IdSerializationConfig.builder().build();
```

**Resulting JSON:**
```json
{
  "_id": "john"
}
```

#### Plain Format - Multiple Features

**EAnnotation:**
```xml
<eClassifiers xsi:type="ecore:EClass" name="Person">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="codec.id"/>
    <details key="idFeatures" value="firstName, lastName, sequence"/>
    <details key="separator" value="-"/>
  </eAnnotations>
  <eStructuralFeatures xsi:type="ecore:EAttribute" name="firstName" eType=".../EString"/>
  <eStructuralFeatures xsi:type="ecore:EAttribute" name="lastName" eType=".../EString"/>
  <eStructuralFeatures xsi:type="ecore:EAttribute" name="sequence" eType=".../EString"/>
</eClassifiers>
```

**Config Builder:**
```java
IdSerializationConfig config = IdSerializationConfig.builder()
    .idFeatures("firstName", "lastName", "sequence")
    .separator("-")
    .build();
```

**Resulting JSON:**
```json
{
  "_id": "John-Doe-1"
}
```

#### Structured Format - Single Feature

**EAnnotation:**
```xml
<eClassifiers xsi:type="ecore:EClass" name="Person">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="codec.id"/>
    <details key="format" value="STRUCTURED"/>
  </eAnnotations>
  <eStructuralFeatures xsi:type="ecore:EAttribute" name="myId" eType=".../EString" iD="true"/>
</eClassifiers>
```

**Config Builder:**
```java
IdSerializationConfig config = IdSerializationConfig.builder()
    .format(SerializationFormat.STRUCTURED)
    .build();
```

**Resulting JSON:**
```json
{
  "_id": {
    "myId": "john"
  }
}
```

#### Structured Format - Multiple Features

**EAnnotation:**
```xml
<eClassifiers xsi:type="ecore:EClass" name="Person">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="codec.id"/>
    <details key="idFeatures" value="firstName, lastName, sequence"/>
    <details key="separator" value="-"/>
    <details key="format" value="STRUCTURED"/>
  </eAnnotations>
  <eStructuralFeatures xsi:type="ecore:EAttribute" name="firstName" eType=".../EString"/>
  <eStructuralFeatures xsi:type="ecore:EAttribute" name="lastName" eType=".../EString"/>
  <eStructuralFeatures xsi:type="ecore:EAttribute" name="sequence" eType=".../EString"/>
</eClassifiers>
```

**Config Builder:**
```java
IdSerializationConfig config = IdSerializationConfig.builder()
    .idFeatures("firstName", "lastName", "sequence")
    .separator("-")
    .format(SerializationFormat.STRUCTURED)
    .build();
```

**Resulting JSON:**
```json
{
  "_id": {
    "separator": "-",
    "firstName": "John",
    "lastName": "Doe",
    "sequence": "1"
  }
}
```

### 4.4 ID with EReference (Contained ID Object)

When `idFeatures` points to a containment EReference, the ID is resolved from the contained object.

**Example: ExampleIDClass01** (contained IdClass01 has no eID, uses idFeatures)

The contained class IdClass01:
```xml
<eClassifiers xsi:type="ecore:EClass" name="IdClass01">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="separator" value="_"/>
    <details key="idFeatures" value="userGroup, userId"/>
  </eAnnotations>
  <eStructuralFeatures xsi:type="ecore:EAttribute" name="userId" eType="ecore:EDataType .../ELong"/>
  <eStructuralFeatures xsi:type="ecore:EAttribute" name="userGroup" eType="ecore:EDataType .../EString"/>
</eClassifiers>
```

The parent class pointing to it:
```xml
<eClassifiers xsi:type="ecore:EClass" name="ExampleIDClass01">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="idFeatures" value="myId"/>
  </eAnnotations>
  <eStructuralFeatures xsi:type="ecore:EReference" name="myId" eType="#//IdClass01" containment="true"/>
</eClassifiers>
```

Or via Java Builder:
```java
IdSerializationConfig config = IdSerializationConfig.builder()
    .idFeatures("myId")  // Points to EReference
    .build();
```

**Resulting Output:**
```json
{
  "_type": "...#//ExampleIDClass01",
  "_id": "sales_42",
  "test": "...",
  "myId": {
    "userId": 42,
    "userGroup": "sales"
  }
}
```

**Example: ExampleIDClass02** (contained IdClass02 has eID on userId)

```xml
<eClassifiers xsi:type="ecore:EClass" name="IdClass02">
  <eStructuralFeatures xsi:type="ecore:EAttribute" name="userId" eType="ecore:EDataType .../ELong" iD="true"/>
  <eStructuralFeatures xsi:type="ecore:EAttribute" name="userGroup" eType="ecore:EDataType .../EString"/>
</eClassifiers>
```

**Resulting Output:**
```json
{
  "_type": "...#//ExampleIDClass02",
  "_id": "42",
  "test": "...",
  "myId": {
    "userId": 42,
    "userGroup": "sales"
  }
}
```

### 4.5 ID Configuration

**Annotation Details:**
| Key | Values | Default | Description |
|-----|--------|---------|-------------|
| `idFeatures` | comma-separated feature names | All `eID="true"` features | Features to use as ID |
| `separator` | any string | `/` | Separator when combining multiple features |
| `keyMode` | ID_ONLY, BOTH, FEATURE_ONLY | ID_ONLY | How to represent ID in JSON |
| `format` | PLAIN, STRUCTURED | PLAIN | SerializationFormat (Section 2.1) |
| `key` | any string | `_id` | JSON property name for ID |
| `idValueWriterName` | writer name | - | Custom value writer for ID |
| `idValueReaderName` | reader name | - | Custom value reader for ID |

#### Example 1: Minimal Configuration (Defaults)

**EAnnotation:**
```xml
<eClassifiers xsi:type="ecore:EClass" name="Person">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="codec.id"/>
    <details key="idFeatures" value="firstName, lastName"/>
    <details key="separator" value="-"/>
  </eAnnotations>
  <eStructuralFeatures xsi:type="ecore:EAttribute" name="firstName" eType=".../EString"/>
  <eStructuralFeatures xsi:type="ecore:EAttribute" name="lastName" eType=".../EString"/>
</eClassifiers>
```

**Config Builder:**
```java
IdSerializationConfig config = IdSerializationConfig.builder()
    .idFeatures("firstName", "lastName")
    .separator("-")
    .build();

saveOptions.put(CodecOptions.ID_CONFIG, config);
```

**Resulting JSON:** (keyMode=ID_ONLY, format=PLAIN, key="_id" are defaults)
```json
{
  "_id": "John-Doe",
  "firstName": "John",
  "lastName": "Doe"
}
```

#### Example 2: Non-Default Options (BOTH, STRUCTURED, Custom Key)

**EAnnotation:**
```xml
<eClassifiers xsi:type="ecore:EClass" name="Person">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="codec.id"/>
    <details key="idFeatures" value="firstName, lastName"/>
    <details key="separator" value="-"/>
    <details key="keyMode" value="BOTH"/>
    <details key="format" value="STRUCTURED"/>
    <details key="key" value="id"/>
  </eAnnotations>
  <eStructuralFeatures xsi:type="ecore:EAttribute" name="firstName" eType=".../EString"/>
  <eStructuralFeatures xsi:type="ecore:EAttribute" name="lastName" eType=".../EString"/>
</eClassifiers>
```

**Config Builder:**
```java
IdSerializationConfig config = IdSerializationConfig.builder()
    .idFeatures("firstName", "lastName")
    .separator("-")
    .keyMode(IdKeyMode.BOTH)
    .format(SerializationFormat.STRUCTURED)
    .idKey("id")
    .build();

saveOptions.put(CodecOptions.ID_CONFIG, config);
```

**Resulting JSON:**
```json
{
  "id": {
    "separator": "-",
    "firstName": "John",
    "lastName": "Doe"
  },
  "firstName": "John",
  "lastName": "Doe"
}
```

#### Example 3: FEATURE_ONLY Mode

**EAnnotation:**
```xml
<eClassifiers xsi:type="ecore:EClass" name="Person">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="codec.id"/>
    <details key="idFeatures" value="firstName, lastName"/>
    <details key="keyMode" value="FEATURE_ONLY"/>
  </eAnnotations>
  <eStructuralFeatures xsi:type="ecore:EAttribute" name="firstName" eType=".../EString"/>
  <eStructuralFeatures xsi:type="ecore:EAttribute" name="lastName" eType=".../EString"/>
</eClassifiers>
```

**Config Builder:**
```java
IdSerializationConfig config = IdSerializationConfig.builder()
    .idFeatures("firstName", "lastName")
    .keyMode(IdKeyMode.FEATURE_ONLY)
    .build();

saveOptions.put(CodecOptions.ID_CONFIG, config);
```

**Resulting JSON:** (no `_id` property, features are used directly for identification)
```json
{
  "firstName": "John",
  "lastName": "Doe"
}
```

### 4.6 Complete Examples

**Example 1: Single ID field (ExampleSingleID)**

EClass with `myId` marked `eID="true"`:

| Config | Output |
|--------|--------|
| Default (ID_ONLY, PLAIN) | `{"_id": "john", "test": "..."}` |
| BOTH, PLAIN | `{"_id": "john", "myId": "john", "test": "..."}` |
| FEATURE_ONLY, PLAIN | `{"myId": "john", "test": "..."}` |
| ID_ONLY, STRUCTURED | `{"_id": {"myId": "john"}, "test": "..."}` |

**Example 2: Multiple ID fields (ExampleMultipleIDs)**

EClass with `myId`, `mySecondId`, `myThirdId` all marked `eID="true"`, separator="-":

| Config | Output |
|--------|--------|
| ID_ONLY, PLAIN | `{"_id": "John-Doe-1", "test": "..."}` |
| BOTH, PLAIN | `{"_id": "John-Doe-1", "myId": "John", "mySecondId": "Doe", "myThirdId": "1", "test": "..."}` |
| ID_ONLY, STRUCTURED | `{"_id": {"separator": "-", "myId": "John", "mySecondId": "Doe", "myThirdId": "1"}, "test": "..."}` |

**Example 3: idFeatures annotation (ExampleMultiple)**

EClass with `idFeatures="firstName, lastName"`, `separator="."`:

| Config | Output |
|--------|--------|
| Default | `{"_id": "John.Doe", "firstName": "John", "lastName": "Doe", "test": "..."}` |
| STRUCTURED | `{"_id": {"separator": ".", "firstName": "John", "lastName": "Doe"}, "firstName": "John", ...}` |

**At serialization time:**
1. Resolve ID features (from `idFeatures` config/annotation or `eID="true"` attributes)
2. If feature is EReference → follow and use contained object's ID definition
3. Extract values from EObject
4. Apply format (plain: combine with separator, structured: create object)
5. Apply keyMode (ID_ONLY, BOTH, or FEATURE_ONLY)

---

## 5. Reference Serialization (Non-Containment)

### 5.1 Reference Strategies

#### PLAIN Strategy

Single value (URI or ID):

```json
{
  "employer": "datainmotion"
}
```

Or as URI:
```json
{
  "employer": "http://example.org/data#//@companies.0"
}
```

#### STRUCTURED Strategy

Nested object with type and reference:

```json
{
  "employer": {
    "_type": "http://example.org/company/1.0#//Company",
    "_ref": "datainmotion"
  }
}
```

**Configurable keys:**
- `typeKey`: type field (default: `_type`)
- `refKey`: reference field (default: `_ref`)

### 5.2 Multi-valued References

#### PLAIN Strategy
```json
{
  "employees": ["john-doe", "jane-smith", "bob-wilson"]
}
```

#### STRUCTURED Strategy
```json
{
  "employees": [
    { "_type": "http://example.org/person/1.0#//Employee", "_ref": "john-doe" },
    { "_type": "http://example.org/person/1.0#//Employee", "_ref": "jane-smith" },
    { "_type": "http://example.org/person/1.0#//Manager", "_ref": "bob-wilson" }
  ]
}
```

### 5.3 Reference Configuration

The configuration defines **keys and format**, not actual values.

#### EAnnotation (on EReference)

```xml
<!-- Switch to PLAIN format (STRUCTURED is default) -->
<eStructuralFeatures xsi:type="ecore:EReference" name="employer" eType="#//Company">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="codec.reference"/>
    <details key="format" value="PLAIN"/>
  </eAnnotations>
</eStructuralFeatures>
```

**Annotation Details:**
| Key | Values | Default | Description |
|-----|--------|---------|-------------|
| `format` | PLAIN, STRUCTURED | STRUCTURED | SerializationFormat (Section 2.1) |
| `typeKey` | any string | `_type` | Key for type in STRUCTURED |
| `refKey` | any string | `_ref` | Key for reference value |
| `expand` | true, false | false | Serialize full object instead of proxy |

**Note:** Type information in STRUCTURED format follows the type configuration (Section 3). With smart compression enabled, type is omitted when instance type equals reference type (see Section 12.1).

**Note:** For numeric type IDs in references, use the codec-wide `useNumericIds` option (Section 2.2).

#### Java Builder (Runtime Override)

**Minimal (default: STRUCTURED):**
```java
ReferenceSerializationConfig config = ReferenceSerializationConfig.builder().build();
```
**Resulting JSON:**
```json
{
  "employer": {
    "_type": "http://example.org/company/1.0#//Company",
    "_ref": "acme-corp"
  }
}
```

**PLAIN format:**
```java
ReferenceSerializationConfig config = ReferenceSerializationConfig.builder()
    .plain()
    .build();
```
**Resulting JSON:**
```json
{
  "employer": "acme-corp"
}
```

**STRUCTURED with custom keys:**
```java
ReferenceSerializationConfig config = ReferenceSerializationConfig.builder()
    .structured()
    .typeKey("$type")
    .refKey("$ref")
    .build();
```
**Resulting JSON:**
```json
{
  "employer": {
    "$type": "http://example.org/company/1.0#//Company",
    "$ref": "acme-corp"
  }
}
```

**Expand (full object instead of proxy):**
```java
ReferenceSerializationConfig config = ReferenceSerializationConfig.builder()
    .expand(true)
    .build();
```
**Resulting JSON:**
```json
{
  "employer": {
    "_type": "http://example.org/company/1.0#//Company",
    "_id": "acme-corp",
    "name": "Acme Corporation",
    "industry": "Manufacturing"
  }
}
```

**At serialization time:**
1. Serializer reads `ReferenceSerializationConfig` from options (Java or JSON)
2. Extracts reference target's type and ID/URI from EObject
3. Combines config (keys/format) + reference data → JSON output

### 5.4 Proxy and Expand Handling

#### Default Behavior: Proxy Serialization

Non-containment references are serialized as **proxies by default**. This requires:
- `_type`: Type information (proxy URI doesn't always indicate type)
- `_ref`: Proxy URI for resolution

Only objects with a URI can be serialized as references (standard EMF behavior).

**Default proxy output:**
```json
{
  "employer": {
    "_type": "http://example.org/company/1.0#//Company",
    "_ref": "companies.json#//@companies.0"
  }
}
```

#### Expand: Inline Serialization

When `expand=true` and the reference is **resolved** (not a proxy), the referenced object is serialized inline instead of as a proxy reference.

**Expanded output:**
```json
{
  "employer": {
    "_type": "http://example.org/company/1.0#//Company",
    "_id": "acme-corp",
    "name": "Acme Corporation",
    "industry": "Manufacturing"
  }
}
```

**Expand Configuration:**

| Level | Option | Default | Description |
|-------|--------|---------|-------------|
| Codec | `expand` | `false` | Enable inline serialization for resolved refs |
| Codec | `expandDepth` | `1` | Max depth for nested expansion |
| Codec | `expandIgnoreBidirectional` | `true` | Skip opposite/bi-directional references |
| Feature | `expand` | (codec default) | Override expand for this feature |

**EAnnotation (on EReference):**
```xml
<eStructuralFeatures xsi:type="ecore:EReference" name="employer" eType="#//Company">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="codec.reference"/>
    <details key="expand" value="true"/>
  </eAnnotations>
</eStructuralFeatures>
```

**Java Builder:**

**Codec level (all non-containments):**
```java
CodecConfig.builder()
    .expand(true)
    .expandDepth(2)
    .expandIgnoreBidirectional(true)
    .build();
```
**Resulting JSON:** (employer expanded, but employer.employees skipped if bi-directional)
```json
{
  "employer": {
    "_type": "http://example.org/company/1.0#//Company",
    "_id": "acme-corp",
    "name": "Acme Corporation"
  }
}
```

**Feature level override:**
```java
// Expand all refs, but keep "manager" as proxy
CodecConfig.builder()
    .expand(true)
    .build();

FeatureSerializationConfig.builder()
    .feature("manager")
    .expand(false)
    .build();
```
**Resulting JSON:**
```json
{
  "employer": {
    "_type": "...",
    "_id": "acme-corp",
    "name": "Acme Corporation"
  },
  "manager": {
    "_type": "...",
    "_ref": "employees.json#//@employees.5"
  }
}
```

#### Type Configuration per Context

Type serialization can be configured differently for each context:

| Context | Config | Use Case |
|---------|--------|----------|
| Root objects | `type(...)` | Main serialization |
| Containments | `containmentType(...)` | Inline contained objects |
| References (proxy + expanded) | `referenceType(...)` | Non-containment refs |
| Per-feature | `feature(...).type(...)` | Specific reference |

**Java Builder:**
```java
CodecConfig.builder()
    .type(TypeSerializationConfig.builder().build())  // default: URI
    .containmentType(TypeSerializationConfig.builder()
        .strategy(TypeStrategy.NAME)  // contained: just name (context clear)
        .build())
    .referenceType(TypeSerializationConfig.builder()
        .strategy(TypeStrategy.URI)   // refs: full URI for resolution
        .build())
    .build();
```
**Resulting JSON:**
```json
{
  "_type": "http://example.org/person/1.0#//Person",
  "_id": "john-doe",
  "name": "John Doe",
  "address": {
    "_type": "Address",
    "street": "123 Main St"
  },
  "employer": {
    "_type": "http://example.org/company/1.0#//Company",
    "_ref": "companies.json#//@companies.0"
  }
}
```

#### Bi-directional Reference Handling

When expanding, bi-directional (opposite) references are **ignored by default** to prevent cycles:

```
Person.employer ←→ Company.employees (opposite)
```

If expanding `employer`, the `employees` back-reference in Company is skipped.

**Configuration:**
- `expandIgnoreBidirectional=true` (default): Skip opposite references
- `expandIgnoreBidirectional=false`: Include (use with caution, may cause cycles)

---

## 6. Cross-Document Containment

When a contained object is stored in a different document, it is serialized similarly to a reference.

### 6.1 Cross-Document Containment Strategies

#### PLAIN Strategy

```json
{
  "address": "addresses.json#//@addresses.0"
}
```

#### STRUCTURED Strategy

```json
{
  "address": {
    "_type": "http://example.org/address/1.0#//Address",
    "_ref": "addresses.json#//@addresses.0"
  }
}
```

Or with STRUCTURED type:

```json
{
  "address": {
    "_type": {
      "schema": "http://example.org/address/1.0",
      "name": "Address"
    },
    "_ref": "addresses.json#//@addresses.0"
  }
}
```

### 6.2 Cross-Document Containment Configuration

Cross-document containments use the same configuration as non-containment references (see Section 5.3 and 5.4). This includes:
- **Format**: PLAIN or STRUCTURED (default: STRUCTURED)
- **Type configuration**: Same type strategy options
- **Smart compression**: Type omitted when instance type equals declared reference type (see Section 12.1)
- **Expand**: Serialize full object instead of reference (if resolved and in memory)

**Detection:** The serializer detects cross-document containment when:
- The EReference is containment (`isContainment() == true`)
- The contained object's resource differs from the container's resource

**Type configuration:** Use `containmentType(...)` for cross-document containments:
```java
CodecConfig.builder()
    .containmentType(TypeSerializationConfig.builder()
        .strategy(TypeStrategy.URI)  // full URI for cross-doc resolution
        .build())
    .build();
```
**Resulting JSON:**
```json
{
  "address": {
    "_type": "http://example.org/address/1.0#//Address",
    "_ref": "addresses.json#//@addresses.0"
  }
}
```

**With smart compression (when instance type equals declared reference type):**
```json
{
  "address": {
    "_ref": "addresses.json#//@addresses.0"
  }
}
```

---

## 7. SuperType Serialization

SuperType serialization has two orthogonal dimensions:
- **Selection** (ALL, SINGLE, NONE) - which supertypes to include
- **Format** (PLAIN, STRUCTURED) - per Section 2.1, how to represent each entry

### 7.1 SuperType Format

#### PLAIN Format

Array of plain type identifiers (strings):

```json
{
  "_supertype": ["Entity", "Auditable", "Timestamped"]
}
```

Or as URIs:
```json
{
  "_supertype": [
    "http://example.org/base/1.0#//Entity",
    "http://example.org/audit/1.0#//Auditable"
  ]
}
```

#### STRUCTURED Format

Array of structured type objects:

```json
{
  "_supertype": [
    { "schema": "http://example.org/base/1.0", "name": "Entity" },
    { "schema": "http://example.org/audit/1.0", "name": "Auditable" }
  ]
}
```

### 7.2 Mixed Format (Smart Compression)

Similar to type info, supertypes use a **smart format**:
- Supertypes from **same schema** as the object: plain name only
- Supertypes from **different schema**: full URI

```json
{
  "_supertype": ["Entity", "NamedElement", "http://audit.org/1.0#//Auditable"]
}
```

### 7.3 SuperType Configuration

The configuration defines **keys and format**, not actual values.

#### EAnnotation (on EClass)

```xml
<eClassifiers xsi:type="ecore:EClass" name="Person">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="codec.supertype"/>
    <details key="enabled" value="true"/>
    <details key="selection" value="ALL"/>
    <details key="supertypeKey" value="_supertype"/>
  </eAnnotations>
</eClassifiers>
```

**Annotation Details:**
| Key | Values | Default | Description |
|-----|--------|---------|-------------|
| `enabled` | true, false | false | Enable supertype serialization |
| `selection` | ALL, ALL_EMF, SINGLE, NONE | ALL (when enabled) | Which supertypes to include |
| `supertypeKey` | any string | `_supertype` | JSON property name |
| `format` | PLAIN, STRUCTURED | PLAIN | SerializationFormat (Section 2.1) |
| `schemaKey` | any string | `schema` | Key for schema in STRUCTURED |
| `nameKey` | any string | `name` | Key for name in STRUCTURED |

**Selection Values:**
| Value | Description |
|-------|-------------|
| `ALL` | All domain model supertypes (excludes EMF base classes like EObject) |
| `ALL_EMF` | All supertypes including EMF base classes (EObject, EModelElement, etc.) |
| `SINGLE` | Only the immediate/direct supertype |
| `NONE` | No supertypes (equivalent to enabled=false) |

#### Java Builder (Runtime Override)

**Minimal (all defaults: PLAIN format, ALL selection):**
```java
SuperTypeSerializationConfig config = SuperTypeSerializationConfig.builder()
    .enabled(true)
    .build();
```
**Resulting JSON:**
```json
{
  "_supertype": ["Entity", "Auditable"]
}
```

**STRUCTURED format:**
```java
SuperTypeSerializationConfig config = SuperTypeSerializationConfig.builder()
    .enabled(true)
    .structured()
    .build();
```
**Resulting JSON:**
```json
{
  "_supertype": [
    { "schema": "http://example.org/base/1.0", "name": "Entity" },
    { "schema": "http://example.org/audit/1.0", "name": "Auditable" }
  ]
}
```

**With custom keys:**
```java
SuperTypeSerializationConfig config = SuperTypeSerializationConfig.builder()
    .enabled(true)
    .structured()
    .schemaKey("ns")
    .nameKey("type")
    .build();
```
**Resulting JSON:**
```json
{
  "_supertype": [
    { "ns": "http://example.org/base/1.0", "type": "Entity" },
    { "ns": "http://example.org/audit/1.0", "type": "Auditable" }
  ]
}
```

**Smart compression (same-schema supertypes as plain names):**
```java
SuperTypeSerializationConfig config = SuperTypeSerializationConfig.builder()
    .enabled(true)
    .useSmartCompression(true)
    .build();
```
**Resulting JSON:** (Entity from same schema, Auditable from different)
```json
{
  "_supertype": ["Entity", "http://audit.org/1.0#//Auditable"]
}
```

**Complete example output (for Person extending Entity and Auditable):**
```json
{
  "_supertype": [
    { "schema": "http://example.org/base/1.0", "name": "Entity" },
    { "schema": "http://example.org/audit/1.0", "name": "Auditable" }
  ]
}
```

**At serialization time:**
1. Serializer reads `SuperTypeSerializationConfig` from options (Java or JSON)
2. Extracts supertypes from EObject's EClass
3. Applies smart compression (same schema → name only)
4. Combines config (keys/format) + supertype data → JSON output

---

## 8. Default Configuration

### 8.1 Default Strategy Settings

| Target | Default Strategy | Notes |
|--------|------------------|-------|
| **Type** | `URI` | Full EClass URI: `http://example.org/person/1.0#//Person` |
| **ID** | `ID_FIELD` | Use EClass's designated ID attribute |
| **SuperType** | `NONE` | Disabled by default |
| **Reference** | `STRUCTURED` | With `_type` and `_ref` fields |

### 8.2 SuperType Defaults (when enabled)

When supertype serialization is activated:

| Setting | Default |
|---------|---------|
| Strategy | `ALL` (full hierarchy) |
| Format | `ARRAY` (list of values) |
| Value format | `PLAIN` (URI strings) |

### 8.3 Default Keys

| Purpose | Default Key |
|---------|-------------|
| Type | `_type` |
| ID | `_id` |
| Reference | `_ref` |
| Schema | `_schema` |
| SuperTypes | `_supertype` |

### 8.4 Default Flags

| Flag | Default |
|------|---------|
| Smart Compression | `OFF` |
| NUMERIC mode | `OFF` |
| Include type in references | `true` |

### 8.5 Field Ordering

Controls the order of properties in serialized output.

#### Field Order Mode

| Mode | Description |
|------|-------------|
| `DECLARATION` (default) | Features in EClass declaration order |
| `ALPHABETICAL` | Features sorted alphabetically by key |

#### Metadata Fields Position

| Option | Default | Description |
|--------|---------|-------------|
| `metadataFieldsFirst` | `true` | Place `_type`, `_id`, `_supertype` before features |

When `metadataFieldsFirst=true`, the output order is:
1. `_id` (if enabled)
2. `_type` (if enabled)
3. `_supertype` (if enabled)
4. Features (in configured order)

**Java Builder:**
```java
CodecConfig config = CodecConfig.builder()
    .fieldOrder(FieldOrder.DECLARATION)    // default
    .metadataFieldsFirst(true)             // default: metadata fields first
    .build();

// Alphabetical ordering with ID always first
CodecConfig alphabetical = CodecConfig.builder()
    .fieldOrder(FieldOrder.ALPHABETICAL)
    .metadataFieldsFirst(true)             // _id, _type, _supertype first, then alphabetical
    .build();
```

**EAnnotation (on EPackage):**
```xml
<eAnnotations source="http://eclipse.org/fennec/codec">
  <details key="fieldOrder" value="ALPHABETICAL"/>
  <details key="metadataFieldsFirst" value="true"/>
</eAnnotations>
```

### 8.6 Global Feature Ignore List

A codec-wide list of feature names to skip during serialization, regardless of individual feature configuration.

| Option | Type | Default | Description |
|--------|------|---------|-------------|
| `globalIgnoreFeatures` | `List<String>` | empty | Feature names to always skip |

This is useful for:
- Skipping audit fields (`createdAt`, `updatedAt`, `version`) across all EClasses
- Excluding internal/technical features from API output
- Temporary exclusion without modifying model annotations

**Java Builder:**
```java
CodecConfig config = CodecConfig.builder()
    .globalIgnoreFeatures("createdAt", "updatedAt", "version", "internalId")
    .build();
```

**EAnnotation (on EPackage):**
```xml
<eAnnotations source="http://eclipse.org/fennec/codec">
  <details key="globalIgnoreFeatures" value="createdAt,updatedAt,version"/>
</eAnnotations>
```

**Precedence:** Global ignore list takes precedence over feature-level `serialize=true`. If a feature name is in the global ignore list, it will not be serialized even if explicitly enabled.

### 8.7 Default Output Example

With default settings, a Person object serializes as:

```json
{
  "_id": "john-doe-123",
  "_type": "http://example.org/person/1.0#//Person",
  "firstName": "John",
  "lastName": "Doe",
  "employer": {
    "_type": "http://example.org/company/1.0#//Company",
    "_ref": "acme-corp"
  },
  "addresses": [
    {
      "_type": "http://example.org/person/1.0#//Address",
      "_ref": "addr-home"
    },
    {
      "_type": "http://example.org/person/1.0#//Address",
      "_ref": "addr-work"
    }
  ]
}
```

### 8.8 Default Configuration Code

```java
// Built-in defaults - this is what you get with no configuration
CodecConfig defaults = CodecConfig.builder().build();

// Equivalent to:
// - Type: URI strategy, key="_type"
// - ID: from eID features, key="_id", PLAIN format
// - SuperType: disabled
// - Reference: PLAIN format
// - Expand: disabled
// - serializeNull=false, serializeDefaults=false, serializeEmpty=true
```

**To customize, only specify non-defaults:**
```java
CodecConfig config = CodecConfig.builder()
    .type(TypeSerializationConfig.builder()
        .structured()  // non-default: STRUCTURED instead of URI
        .build())
    .supertype(SuperTypeSerializationConfig.builder()
        .enabled(true)  // non-default: enable supertypes
        .build())
    .reference(ReferenceSerializationConfig.builder()
        .structured()  // non-default: STRUCTURED instead of PLAIN
        .build())
    .expand(true)       // non-default: expand resolved refs
    .expandDepth(2)     // non-default: 2 levels deep
    .build();
```

---

## 9. Feature Serialization

Features (EStructuralFeatures) have additional serialization options beyond the metadata targets.

### 9.1 Null, Default, and Empty Value Handling

#### Codec-Wide Defaults

Global settings that apply to all features unless overridden:

| Option | Description | Default |
|--------|-------------|---------|
| `serializeDefaults` | Include fields with default values | `false` |
| `serializeNull` | Include fields with explicit null values | `false` |
| `serializeEmpty` | Include empty collections | `true` |

**Java Builder (Codec-Wide):**
```java
CodecConfig config = CodecConfig.builder()
    .serializeDefaults(false)  // Default: omit default values
    .serializeNull(false)      // Default: omit null values
    .serializeEmpty(true)      // Default: include empty collections
    .build();
```

**Default Behavior (serializeDefaults=false, serializeNull=false):**
```json
{
  "_type": "http://example.org/person/1.0#//Person",
  "_id": "john-doe",
  "firstName": "John",
  "lastName": "Doe"
  // "middleName" omitted (null)
  // "age" omitted (default value 0)
}
```

**With serializeNull=true:**
```json
{
  "_type": "http://example.org/person/1.0#//Person",
  "_id": "john-doe",
  "firstName": "John",
  "middleName": null,
  "lastName": "Doe"
}
```

#### Feature-Specific Overrides

Override codec-wide settings for individual features:

| Option | Default | Description |
|--------|---------|-------------|
| `serialize` | `true` | Serialize this feature (false = skip/transient) |
| `serializeNull` | `false` | Include null values in output |
| `serializeDefaults` | `false` | Include default values in output |
| `serializeEmpty` | `true` | Include empty collections in output |

**Semantics:** `true` = include in output, `false` = omit from output. Feature-level overrides codec-wide.

**EAnnotation (on EStructuralFeature):**

```xml
<!-- Mark feature as transient (never serialize) -->
<eStructuralFeatures xsi:type="ecore:EAttribute" name="internalCache">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="serialize" value="false"/>
  </eAnnotations>
</eStructuralFeatures>

<!-- Force include null values for this feature (override codec default) -->
<eStructuralFeatures xsi:type="ecore:EAttribute" name="middleName">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="serializeNull" value="true"/>
  </eAnnotations>
</eStructuralFeatures>

<!-- Skip default values for this feature -->
<eStructuralFeatures xsi:type="ecore:EAttribute" name="counter">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="serializeDefaults" value="false"/>
  </eAnnotations>
</eStructuralFeatures>
```

**Annotation Details:**
| Key | Values | Default | Description |
|-----|--------|---------|-------------|
| `serialize` | true, false | true | Serialize this feature (false = transient) |
| `serializeNull` | true, false | false | Include null values |
| `serializeDefaults` | true, false | false | Include default values |
| `serializeEmpty` | true, false | true | Include empty collections |

**Java Builder (Runtime Override):**
```java
// Always skip a feature (transient)
FeatureSerializationConfig transientConfig = FeatureSerializationConfig.builder()
    .feature("internalCache")
    .serialize(false)  // Never serialize
    .build();

// Force serialize null for specific feature (override codec default)
FeatureSerializationConfig nullConfig = FeatureSerializationConfig.builder()
    .feature("middleName")
    .serializeNull(true)  // Include even if null
    .build();

// Skip default values for specific feature
FeatureSerializationConfig defaultConfig = FeatureSerializationConfig.builder()
    .feature("counter")
    .serializeDefaults(false)  // Skip if value equals default
    .build();
```

**Resolution Order:**
1. Feature-specific setting (if defined)
2. Codec-wide setting
3. Built-in default

### 9.2 Feature Key Customization

Override the JSON property name for a feature. Names can be customized at multiple levels.

**Resolution Order (highest to lowest priority):**
1. **Config Builder** - Runtime configuration override
2. **Model Annotation** - `@CODEC_KEY` annotation on EStructuralFeature
3. **Feature Name** - Default EMF feature name

#### Model Annotation

Customize feature names directly in the EMF model using codec annotations:

```java
// In .ecore model or generated interface
@CODEC_KEY("first_name")
String getFirstName();
```

Or in the .ecore file:
```xml
<eStructuralFeatures xsi:type="ecore:EAttribute" name="firstName">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="key" value="first_name"/>
  </eAnnotations>
</eStructuralFeatures>
```

**Resulting Output (from annotation):**
```json
{
  "first_name": "John",
  "lastName": "Doe"
}
```

#### Config Builder Override

The config builder can override annotation-defined names at runtime:

```java
// Override the annotation-defined key
FeatureSerializationConfig config = FeatureSerializationConfig.builder()
    .feature("firstName")
    .key("givenName")  // Overrides @CODEC_KEY("first_name")
    .build();
```

**Resulting Output (config overrides annotation):**
```json
{
  "givenName": "John",
  "lastName": "Doe"
}
```

This allows models to define sensible defaults via annotations while still permitting runtime customization for specific use cases (e.g., different API versions, external system integration).

### 9.3 Extended Metadata Names

Use names from XSD extended metadata annotations instead of EMF feature names:

**Java Builder:**
```java
CodecConfig config = CodecConfig.builder()
    .useNamesFromExtendedMetadata(true)
    .build();
```

This is useful when EMF models are generated from XSD and you want to preserve the original XML element/attribute names.

### 9.4 Enum Serialization

Control how enum values are serialized:

| Option | Description | Example |
|--------|-------------|---------|
| `LITERAL` | Use enum literal name | `"status": "ACTIVE"` |
| `VALUE` | Use enum ordinal value | `"status": 1` |
| `NAME` | Use enum name | `"status": "Active"` |

#### EAnnotation (on EEnum or EAttribute)

On the EEnum itself (applies to all usages):
```xml
<eClassifiers xsi:type="ecore:EEnum" name="Status">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="enumSerialization" value="LITERAL"/>
  </eAnnotations>
  <eLiterals name="ACTIVE" value="0"/>
  <eLiterals name="INACTIVE" value="1"/>
</eClassifiers>
```

Or on a specific EAttribute (overrides EEnum setting):
```xml
<eStructuralFeatures xsi:type="ecore:EAttribute" name="status" eType="#//Status">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="enumSerialization" value="VALUE"/>
  </eAnnotations>
</eStructuralFeatures>
```

#### Java Builder (Codec-Wide or Feature-Specific)

```java
// Codec-wide default
CodecConfig config = CodecConfig.builder()
    .enumSerialization(EnumSerializationStrategy.LITERAL)
    .build();

// Feature-specific override
FeatureSerializationConfig featureConfig = FeatureSerializationConfig.builder()
    .feature("status")
    .enumSerialization(EnumSerializationStrategy.VALUE)
    .build();
```

---

## 10. Custom Value Readers/Writers

Extensibility hooks for custom serialization logic.

### 10.1 Overview

Custom value readers/writers allow you to:
- Transform values during serialization/deserialization
- Handle special data types (dates, binary, custom formats)
- Implement domain-specific encoding

### 10.2 Registration

**Java Builder:**
```java
CodecConfig config = CodecConfig.builder()
    .valueWriter("dateWriter", new ISO8601DateWriter())
    .valueReader("dateReader", new ISO8601DateReader())
    .build();
```

### 10.3 Usage per Target

#### EAnnotation (on EStructuralFeature)

```xml
<eStructuralFeatures xsi:type="ecore:EAttribute" name="createdAt" eType="ecore:EDataType...">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="codec.value.writer.name" value="dateWriter"/>
    <details key="codec.value.reader.name" value="dateReader"/>
  </eAnnotations>
</eStructuralFeatures>
```

The writer/reader must be registered in the codec configuration or be an auto-registered built-in.

#### Java Builder (Runtime Override)

**For Type:**
```java
TypeSerializationConfig.builder()
    .typeWriterName("customTypeWriter")
    .typeReaderName("customTypeReader")
    .build();
```

**For ID:**
```java
IdSerializationConfig.builder()
    .idWriterName("uuidWriter")
    .idReaderName("uuidReader")
    .build();
```

**For Features:**
```java
FeatureSerializationConfig.builder()
    .feature("createdAt")
    .valueWriterName("dateWriter")
    .valueReaderName("dateReader")
    .build();
```

### 10.4 Example: Custom Date Formatting

**Custom Writer:**
```java
public class ISO8601DateWriter implements CodecValueWriter<Date> {
    @Override
    public void write(Date value, JsonGenerator gen) throws IOException {
        gen.writeString(ISO8601_FORMAT.format(value));
    }
}
```

**Resulting Output:**
```json
{
  "createdAt": "2025-12-06T10:30:00Z"
}
```

---

## 11. Polymorphism and Inheritance

### 11.1 Reference Type vs Instance Type

When serializing references, the serializer decides which type information to write based on the actual instance type and smart compression setting.

**Core Rule:** The serializer always uses the **concrete instance type** (from `eObject.eClass()`), never the declared reference type.

**Smart Compression Behavior:**

| Smart Compression | Instance Type == Reference Type | Action |
|-------------------|--------------------------------|--------|
| ON | Yes | Omit `_type` (can be inferred) |
| ON | No | Write instance type |
| OFF | Yes | Write instance type |
| OFF | No | Write instance type |

**Examples:**

```java
// Reference typed as Person, contains FancyPerson instance
EReference employeeRef;  // type = Person
EObject instance;        // eClass = FancyPerson
```

**Smart Compression ON, instance type differs from reference type:**
```json
{
  "employee": {
    "_type": "http://example.org/person/1.0#//FancyPerson",
    "_ref": "john-doe"
  }
}
```

**Smart Compression ON, instance type equals reference type:**
```json
{
  "employee": {
    "_ref": "john-doe"
  }
}
```
(Type omitted because it can be inferred from the reference declaration)

**Smart Compression OFF (always writes instance type):**
```json
{
  "employee": {
    "_type": "http://example.org/person/1.0#//FancyPerson",
    "_ref": "john-doe"
  }
}
```

#### Option: Always Write Reference Type (Serialization-Only)

An optional setting to write the **declared reference type** instead of the instance type:

```java
ReferenceSerializationConfig.builder()
    .writeReferenceType(true)  // Write declared type, not instance type
    .build();
```

**Result (reference typed as Person, instance is FancyPerson):**
```json
{
  "employee": {
    "_type": "http://example.org/person/1.0#//Person",
    "_ref": "john-doe"
  }
}
```

> **⚠️ Warning:** This is a **serialization-only option**. Using `writeReferenceType=true` may cause deserialization failures if:
> - The reference type is an interface (cannot instantiate)
> - The reference type is abstract (cannot instantiate)
> - Instance-specific features from subclass are lost
>
> Use only when the consuming system specifically requires the declared type.

### 11.2 Annotation Inheritance Levels

Controls how codec annotations are inherited across the EClass hierarchy.

| Level | Description | Inherits From |
|-------|-------------|---------------|
| `DIRECT` | Only direct parent | Immediate superclass/interface |
| `ALL` | Full hierarchy | All ancestors up to EObject |
| `NONE` | No inheritance | Only concrete class annotations |

**Default:** `DIRECT`

**Rationale for DIRECT as default:**
- Avoids accidentally inheriting annotations from external library base classes
- Predictable behavior - only looks one level up
- Matches OSGi DS component annotation inheritance semantics

#### EAnnotation (on EClass)

```xml
<!-- Force inheritance from parent even if parent is from different EPackage -->
<eClassifiers xsi:type="ecore:EClass" name="Employee" eSuperTypes="#//Person">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="codec.inherit" value="ALL"/>
  </eAnnotations>
</eClassifiers>
```

**Annotation Details:**
| Key | Values | Default | Description |
|-----|--------|---------|-------------|
| `codec.inherit` | DIRECT, ALL, NONE | DIRECT | Annotation inheritance level |

#### Java Builder (Runtime Override)

```java
CodecConfig config = CodecConfig.builder()
    .annotationInheritance(AnnotationInheritance.DIRECT)  // Default
    .build();

// Or per-class override
ClassCodecConfig config = ClassCodecConfig.builder()
    .eClass(EmployeeClass)
    .annotationInheritance(AnnotationInheritance.ALL)
    .build();
```

**Example Hierarchy:**
```
EObject (EMF base)
  └── Entity (library class, has @CODEC_ID)
        └── Person (your model, has @CODEC_TYPE)
              └── Employee (your model, no annotations)
```

| Inheritance Level | Employee sees annotations from |
|-------------------|--------------------------------|
| `NONE` | Employee only (none) |
| `DIRECT` | Employee + Person (`@CODEC_TYPE`) |
| `ALL` | Employee + Person + Entity (`@CODEC_TYPE`, `@CODEC_ID`) |

### 11.3 Inheritance Resolution Order

When the same annotation exists at multiple levels, the most specific (closest to concrete class) wins:

1. Concrete class annotations (highest priority)
2. Direct parent annotations
3. Grandparent annotations (only if `ALL`)
4. Global codec defaults (lowest priority)

---

## 12. Global Configuration Options

### 12.1 Smart Compression

Smart Compression reduces redundancy by omitting type information when it can be derived from context. This applies consistently across all serialization features.

#### 12.1.1 Core Principle

When smart compression is enabled, type information is omitted whenever the **instance type equals the declared type**. The deserializer can infer the type from the model definition.

| Feature | Declared Type | Instance Type | Smart Compression Action |
|---------|---------------|---------------|--------------------------|
| Root Object | `CODEC_ROOT_OBJECT` hint | `eObject.eClass()` | Omit `_type` if equal |
| Containment Reference | `reference.getEReferenceType()` | `target.eClass()` | Omit `_type` if equal |
| Non-Containment Reference | `reference.getEReferenceType()` | `target.eClass()` | Omit `_type` if equal |
| Cross-Document Containment | `reference.getEReferenceType()` | `target.eClass()` | Omit `_type` if equal |

**Key Rule:** Always serialize the **instance type** (from `eObject.eClass()`), never the declared type. Smart compression only affects whether to write it, not what value to write.

#### 12.1.2 Smart Compression Behavior Matrix

| Smart Compression | Instance Type == Declared Type | Action |
|-------------------|-------------------------------|--------|
| ON | Yes | Omit `_type` (inferable from context) |
| ON | No | Write `_type` with instance type |
| OFF | Yes | Write `_type` with instance type |
| OFF | No | Write `_type` with instance type |

#### 12.1.3 Additional Compression: Same-Schema Names

When using STRUCTURED type format with smart compression, schema information can also be compressed:

- **Same-package references**: Use simple name instead of full URI
- **Same-schema supertypes**: Use simple name instead of full URI
- **Cross-package references**: Always include full URI

#### 12.1.4 Configuration

```java
// Global - applies to ALL serialization targets
CodecConfig.builder()
    .smartCompression(true)  // Global default
    .build();

// Per-target override
CodecConfig.builder()
    .smartCompression(false)  // Global default OFF
    .type(TypeSerializationConfig.builder()
        .smartCompression(true)  // But ON for type info
        .build())
    .reference(ReferenceSerializationConfig.builder()
        .smartCompression(true)  // And ON for references
        .build())
    .build();
```

#### 12.1.5 Examples

**Containment reference with smart compression ON:**

```java
// EReference employees: Person[*]  (declared type = Person)
// Contains: Person, Person, FancyPerson instances
```
```json
{
  "employees": [
    { "name": "John" },
    { "name": "Jane" },
    { "_type": "http://example.org/1.0#//FancyPerson", "name": "Bob", "fancyLevel": 5 }
  ]
}
```
Note: First two omit `_type` (Person == Person), third includes it (FancyPerson != Person).

**Non-containment reference with smart compression ON:**

```json
{
  "employees": [
    { "_ref": "john" },
    { "_ref": "jane" },
    { "_type": "http://external.org/hr/1.0#//Contractor", "_ref": "bob" }
  ]
}
```

**Cross-document containment with smart compression ON:**

```json
{
  "address": {
    "_ref": "addresses.json#//@addresses.0"
  }
}
```
Note: `_type` omitted because instance type equals declared reference type.

### 12.2 NUMERIC/INDEXED Strategy

Uses EClass classifier IDs and EStructuralFeature IDs instead of names for maximum compactness.

**Configuration:**

```java
// Global - applies to ALL serialization targets
CodecConfig.builder()
    .useNumericIds(true)  // Global default
    .build();

// Per-target
TypeSerializationConfig.builder()
    .strategy(NUMERIC)
    .schemaKey("s")       // Short keys for compactness
    .classKey("c")
    .build();

ReferenceSerializationConfig.builder()
    .strategy(NUMERIC)
    .classKey("c")
    .refKey("r")
    .build();
```

**Example with NUMERIC strategy:**

```json
{
  "_type": { "s": "http://example.org/person/1.0", "c": 3 },
  "5": "John",
  "6": "Doe",
  "12": { "c": 1, "r": "acme" }
}
```

With smart compression (same package context):

```json
{
  "_type": { "s": "http://example.org/person/1.0", "c": 3 },
  "5": "John",
  "6": "Doe",
  "12": { "c": 1, "r": "acme" },
  "15": { "s": "http://external.org/audit/1.0", "c": 2, "r": "audit-123" }
}
```

### 12.3 NUMERIC Strategy - Compatibility Warning

> **⚠️ IMPORTANT: Model Evolution Risk**
>
> The NUMERIC strategy uses EMF's internal classifier and feature IDs. These IDs are **assigned based on declaration order** in the Ecore model and **can change** when:
>
> - A new EClass is added before existing classes
> - A new EStructuralFeature is added before existing features
> - Classes or features are reordered in the model
> - Classes or features are removed (IDs of subsequent elements shift)
>
> **Impact:**
> - Serialized data using NUMERIC strategy may become **unreadable** after model changes
> - The same numeric ID may refer to a **different** class/feature after model evolution
> - Unlike names, numeric IDs have **no semantic stability**
>
> **Recommendations:**
> 1. Only use NUMERIC for **transient data** (caches, message queues)
> 2. Do NOT use NUMERIC for **persistent storage** unless model is frozen
> 3. Consider using **@EClassifier(id=N)** annotations to stabilize IDs
> 4. Always version your serialized data format
> 5. For long-term storage, prefer NAME or URI strategies
>
> **Comparison:**
>
> | Aspect | NAME/URI | NUMERIC |
> |--------|----------|---------|
> | Stability | High (semantic) | Low (positional) |
> | Compactness | Low | High |
> | Human readable | Yes | No |
> | Model refactoring safe | Mostly* | No |
> | Recommended for persistence | Yes | No |
>
> *Names can also change via refactoring, but this is explicit and intentional.

---

## 13. Configuration Builder Design

### 13.1 Core Principle

**Builders configure FORMAT and KEYS, not actual data values.**

The configuration builders define:
- Which **keys** to use (`_type`, `@type`, `_id`, etc.)
- Which **format/strategy** to use (PLAIN, STRUCTURED, SCHEMA_AND_TYPE)
- Which **options** apply (include supertypes, use smart compression, etc.)

The **actual values** (schema URI, class name, ID value, etc.) come from the **EObject** at serialization time.

### 13.2 Configuration Object Pattern

Each serialization target has a configuration object. Only non-defaults need to be specified:

```java
// Type: switch to STRUCTURED (default is URI)
TypeSerializationConfig typeConfig = TypeSerializationConfig.builder()
    .structured()
    .includeSupertypes(true)
    .useSmartCompression(true)
    .build();

// ID: default is PLAIN, only change if needed
IdSerializationConfig idConfig = IdSerializationConfig.builder()
    .structured()  // switch to STRUCTURED
    .build();

// Reference: switch to STRUCTURED (default is PLAIN)
ReferenceSerializationConfig refConfig = ReferenceSerializationConfig.builder()
    .structured()
    .build();
```

### 13.3 Setting Configuration in Options

Configuration objects are passed via EMF's standard save/load options:

```java
Map<String, Object> saveOptions = new HashMap<>();
saveOptions.put(CodecOptions.TYPE_CONFIG, typeConfig);
saveOptions.put(CodecOptions.ID_CONFIG, idConfig);
saveOptions.put(CodecOptions.REFERENCE_CONFIG, refConfig);

resource.save(outputStream, saveOptions);
```

### 13.4 Serializer Flow

At serialization time:

```
1. Serializer receives EObject
2. Serializer reads configuration from options (keys, format)
3. Serializer extracts values from EObject (schema, name, id, etc.)
4. Serializer combines: config.keys + eObject.values → JSON output
```

Example for type serialization:

```java
// Serializer pseudocode
void serializeType(EObject obj, JsonGenerator gen, TypeSerializationConfig config) {
    EClass eClass = obj.eClass();

    // Extract VALUES from EObject
    String schemaUri = eClass.getEPackage().getNsURI();
    String className = eClass.getName();
    List<String> supertypes = getSupertypes(eClass, config.useSmartCompression());

    // Use KEYS from config
    if (config.strategy() == STRUCTURED) {
        gen.writeObjectFieldStart(config.typeKey());      // "_type"
        gen.writeStringField(config.schemaKey(), schemaUri);  // "schema"
        gen.writeStringField(config.nameKey(), className);    // "name"
        if (config.includeSupertypes()) {
            gen.writeArrayFieldStart(config.supertypeKey()); // "supertype"
            supertypes.forEach(gen::writeString);
            gen.writeEndArray();
        }
        gen.writeEndObject();
    } else {
        // PLAIN: just write URI
        gen.writeStringField(config.typeKey(), eClass.getURI());
    }
}
```

### 13.5 Default Configurations

Codec provides factory methods for common presets:

```java
// Default (URI strategy) - what you get with no config
TypeSerializationConfig.builder().build();

// JSON-LD preset
TypeSerializationConfig.builder()
    .schemaAndType()
    .typeKey("@type")
    .schemaKey("@context")
    .build();

// Fully structured preset
TypeSerializationConfig.builder()
    .structured()
    .includeSupertypes(true)
    .build();
```

---

## 14. Complete Configuration Scenarios

This section shows 4 complete scenarios with varying complexity. Each scenario includes:
- The `CodecConfig` builder configuration (only non-defaults shown)
- The resulting serialized JSON output for a sample Person object

**Sample EObject for all scenarios:**
- Person (from `http://example.org/person/1.0`) extends Entity (from `http://example.org/base/1.0`)
- ID: "john-doe-123"
- firstName: "John", lastName: "Doe"
- employer: reference to Company "acme-corp"
- addresses: list of Address references ["addr-home", "addr-work"]

### 14.1 Scenario 1: Minimal (Default Configuration)

Uses all default settings - no configuration needed.

**Java Builder:**
```java
CodecConfig config = CodecConfig.builder().build();
```

**Resulting Output:** (Type=URI, ID=PLAIN, Reference=PLAIN, SuperType=disabled)
```json
{
  "_type": "http://example.org/person/1.0#//Person",
  "_id": "john-doe-123",
  "firstName": "John",
  "lastName": "Doe",
  "employer": "acme-corp",
  "addresses": ["addr-home", "addr-work"]
}
```

---

### 14.2 Scenario 2: Standard API with SuperTypes

Standard JSON API style with supertype information and STRUCTURED references.

**Java Builder:**
```java
CodecConfig config = CodecConfig.builder()
    .reference(ReferenceSerializationConfig.builder()
        .structured()  // STRUCTURED references
        .build())
    .supertype(SuperTypeSerializationConfig.builder()
        .enabled(true)  // Enable supertypes
        .build())
    .build();
```

**Resulting Output:**
```json
{
  "_type": "http://example.org/person/1.0#//Person",
  "_id": "john-doe-123",
  "_supertype": ["http://example.org/base/1.0#//Entity"],
  "firstName": "John",
  "lastName": "Doe",
  "employer": {
    "_type": "http://example.org/company/1.0#//Company",
    "_ref": "acme-corp"
  },
  "addresses": [
    { "_type": "http://example.org/person/1.0#//Address", "_ref": "addr-home" },
    { "_type": "http://example.org/person/1.0#//Address", "_ref": "addr-work" }
  ]
}
```

---

### 14.3 Scenario 3: JSON-LD / Semantic Web Style

Optimized for JSON-LD compatibility with `@` prefixed keys and schema/type separation.

**Java Builder:**
```java
CodecConfig config = CodecConfig.builder()
    .type(TypeSerializationConfig.builder()
        .schemaAndType()  // separate schema + type keys
        .schemaKey("@context")
        .typeKey("@type")
        .build())
    .id(IdSerializationConfig.builder()
        .idKey("@id")
        .build())
    .reference(ReferenceSerializationConfig.builder()
        .structured()
        .typeKey("@type")
        .refKey("@ref")
        .build())
    .supertype(SuperTypeSerializationConfig.builder()
        .enabled(true)
        .supertypeKey("@supertype")
        .useSmartCompression(true)
        .build())
    .build();
```

**Resulting Output:**
```json
{
  "@context": "http://example.org/person/1.0",
  "@type": "Person",
  "@id": "john-doe-123",
  "@supertypes": ["http://example.org/base/1.0#//Entity"],
  "firstName": "John",
  "lastName": "Doe",
  "employer": {
    "@context": "http://example.org/company/1.0",
    "@type": "Company",
    "@ref": "acme-corp"
  },
  "addresses": [
    { "@type": "Address", "@ref": "addr-home" },
    { "@type": "Address", "@ref": "addr-work" }
  ]
}
```

---

### 14.4 Scenario 4: Fully Structured with Smart Compression

Maximum detail with structured objects and smart compression for same-schema references.

**Java Builder:**
```java
CodecConfig config = CodecConfig.builder()
    .type(TypeSerializationConfig.builder()
        .structured()
        .build())
    .id(IdSerializationConfig.builder()
        .structured()
        .build())
    .reference(ReferenceSerializationConfig.builder()
        .structured()
        .build())
    .supertype(SuperTypeSerializationConfig.builder()
        .enabled(true)
        .structured()
        .build())
    .useSmartCompression(true)
    .build();
```

**Resulting Output:**
```json
{
  "_type": {
    "schema": "http://example.org/person/1.0",
    "name": "Person"
  },
  "_id": {
    "type": "http://example.org/person/1.0#//Person",
    "value": "john-doe-123"
  },
  "_supertype": [
    { "schema": "http://example.org/base/1.0", "name": "Entity" }
  ],
  "firstName": "John",
  "lastName": "Doe",
  "employer": {
    "_type": {
      "schema": "http://example.org/company/1.0",
      "name": "Company"
    },
    "_ref": "acme-corp"
  },
  "addresses": [
    { "_type": "Address", "_ref": "addr-home" },
    { "_type": "Address", "_ref": "addr-work" }
  ]
}
```

Note: Smart compression applies to `addresses` - since Address is from the same schema as Person, only the name is used instead of the full structured type.

---

## 15. Deserialization Requirements

### 15.0 Error and Warning Handling

The codec uses EMF's standard diagnostic mechanism for reporting errors and warnings during serialization and deserialization.

**Principle:**
- All errors and warnings are collected in the EMF Resource's diagnostics (`resource.getErrors()`, `resource.getWarnings()`)
- Errors cause the load/save operation to fail after all diagnostics are collected
- Warnings do not cause failure but are reported for user awareness
- All diagnostics are also logged via the standard logging mechanism

**Error Severity:**

| Severity | Behavior | Examples |
|----------|----------|----------|
| **ERROR** | Operation fails, diagnostic added | Cannot instantiate abstract type, unresolved type URI, missing required type info |
| **WARNING** | Operation continues, diagnostic added | Type collision (content type differs from hint), deprecated option usage |

**Diagnostic Information:**

Each diagnostic includes:
- Message describing the issue
- Location (resource URI, line/column if available)
- Source (codec component that raised the issue)

**Example - Error during load:**
```java
resource.load(inputStream, options);
if (!resource.getErrors().isEmpty()) {
    for (Diagnostic error : resource.getErrors()) {
        System.err.println("Error: " + error.getMessage());
    }
    // Operation failed - handle appropriately
}
```

**Example - Warning during load:**
```java
resource.load(inputStream, options);
// Check warnings even on success
for (Diagnostic warning : resource.getWarnings()) {
    System.out.println("Warning: " + warning.getMessage());
}
```

### 15.1 Format Detection

Deserializers MUST detect the format used and handle accordingly:

| Input | Detection |
|-------|-----------|
| `"_type": "string"` | PLAIN (detect NAME/CLASS/URI/MAPPED by content) |
| `"_type": { ... }` | STRUCTURED |
| `"_schema": ..., "_type": ...` | SCHEMA_AND_TYPE |

### 15.2 Backward Compatibility

Deserializers MUST support reading:
- V1 format (always PLAIN)
- V2 PLAIN format
- V2 STRUCTURED format
- V2 SCHEMA_AND_TYPE format

The deserializer should auto-detect the format regardless of codec configuration.

### 15.3 Type Resolution

**Default Behavior (URI strategy):**

When type information IS present in the content (the default case with `include=true` and `strategy=URI`), the deserializer resolves the EClass automatically:

1. Read type information (any format: PLAIN, STRUCTURED, SCHEMA_AND_TYPE)
2. Extract schema/namespace URI and class name
3. Lookup EPackage in MetadataService by namespace URI
4. Resolve EClass by name within the package
5. Create and populate EObject

**Example with URI strategy (default):**
```json
{
  "_type": "http://example.org/person/1.0#//Person",
  "name": "John"
}
```
The deserializer extracts `http://example.org/person/1.0` as namespace URI and `Person` as class name.

### 15.4 CODEC_ROOT_OBJECT Option

The `CODEC_ROOT_OBJECT` option specifies the expected root EClass for deserialization. This option is:

**Required when:**
- Type information is NOT present in the content (`include=false` was used during serialization)
- Type serialization is globally disabled
- The content was produced by a system that doesn't include type metadata

**Optional when:**
- Type information IS present in the content (default behavior)
- Can be used as a hint or validation even when type info exists

**Configuration Levels:**

| Level | How to Configure |
|-------|------------------|
| **Load options** | `resource.load(inputStream, Map.of(CODEC_ROOT_OBJECT, MyPackage.Literals.MY_CLASS))` |
| **ResourceFactory defaults** | `factory.setDefaultLoadOptions(Map.of(CODEC_ROOT_OBJECT, ...))` |

**Supported Value Types:**

| Type | Example | Description |
|------|---------|-------------|
| `EClass` | `PersonPackage.Literals.PERSON` | Direct EClass reference (preferred) |
| `String` (URI) | `"http://example.org/person/1.0#//Person"` | EClass URI for cross-bundle scenarios |

**Unregistered Package Handling:**

When `CODEC_ROOT_OBJECT` references an EClass whose package is not registered in the MetadataService:

| Scenario | Content Type | Behavior |
|----------|--------------|----------|
| Unregistered package + type info in content | Type in content | WARNING + use content type (fallback) |
| Unregistered package + no type info in content | none | ERROR: cannot resolve type |

**Example - Unregistered with fallback:**
```java
// CODEC_ROOT_OBJECT = "http://unregistered.org/1.0#//Person" (not in MetadataService)
// Content _type = "http://known.org/1.0#//Employee" (registered)
// Result: WARNING - unregistered hint, deserialize as Employee (fallback)
```

**Example - Unregistered without fallback:**
```java
// CODEC_ROOT_OBJECT = "http://unregistered.org/1.0#//Person" (not in MetadataService)
// Content has no _type
// Result: ERROR - cannot resolve type from unregistered package
```

**Example:**
```java
// Using EClass directly (preferred)
Map<String, Object> options = Map.of(
    CODEC_ROOT_OBJECT, PersonPackage.Literals.PERSON
);
resource.load(inputStream, options);

// Using URI string (for dynamic/cross-bundle scenarios)
Map<String, Object> options = Map.of(
    CODEC_ROOT_OBJECT, "http://example.org/person/1.0#//Person"
);
resource.load(inputStream, options);
```

**Abstract and Interface EClass Handling:**

When `CODEC_ROOT_OBJECT` points to an abstract EClass or interface:

| Scenario | Content Type | Behavior |
|----------|--------------|----------|
| Abstract/Interface + compatible content type | Subtype in content | Use content type (no error) |
| Abstract/Interface + incompatible content type | Unrelated type | WARNING + use content type |
| Abstract/Interface + no content type | none | ERROR: cannot instantiate abstract type |

**Example - Abstract with compatible subtype:**
```java
// CODEC_ROOT_OBJECT = AbstractEntity (abstract)
// Content _type = Person (concrete subtype)
// Result: deserialize as Person (valid)
```

**Example - Abstract without content type:**
```java
// CODEC_ROOT_OBJECT = AbstractEntity (abstract)
// Content has no _type
// Result: ERROR - cannot instantiate abstract EClass
```

### 15.5 Global Type Disable

When type serialization is completely disabled, **both serialization and deserialization** of type information are affected:

**Configuration:**
```java
// Codec-wide disable
CodecConfiguration.builder()
    .serializeType(false)    // Don't write _type on save
    .deserializeType(false)  // Don't read _type on load (CODEC_ROOT_OBJECT required!)
    .build();

// Or via EAnnotation on EClass
@codec.type(include=false)
```

**Implications:**

| serializeType | deserializeType | Behavior |
|---------------|-----------------|----------|
| `true` | `true` | Normal: write and read `_type` |
| `true` | `false` | Write `_type` but ignore during load (use CODEC_ROOT_OBJECT) |
| `false` | `true` | Don't write `_type` but try to read it (may fail if missing) |
| `false` | `false` | No type handling - CODEC_ROOT_OBJECT **required** for load |

**Note:** When `serializeType=false`, supertype serialization (`serializeSuperTypes`) is also implicitly disabled, as supertypes depend on type information.

### 15.6 Type Resolution Priority

When both `CODEC_ROOT_OBJECT` AND type information in content are present:

| Scenario | Behavior |
|----------|----------|
| **No collision** (content type equals or is subtype of CODEC_ROOT_OBJECT) | Use `CODEC_ROOT_OBJECT` as the target type |
| **Collision** (content type differs from CODEC_ROOT_OBJECT) | Raise warning, use content type info (content gets precedence) |
| **Only CODEC_ROOT_OBJECT** (no type in content) | Use `CODEC_ROOT_OBJECT` |
| **Only content type** (no CODEC_ROOT_OBJECT) | Use content type info |

**Rationale:**
- `CODEC_ROOT_OBJECT` acts as a hint/expectation of what type to deserialize
- If content type matches or is compatible (subtype), the hint is valid and used
- If content type differs, the actual content takes precedence to avoid data corruption, but a warning is raised since this may indicate a configuration mismatch

**Example - No collision (hint is valid):**
```java
// CODEC_ROOT_OBJECT = Person (supertype)
// Content _type = Employee (subtype of Person)
// Result: deserialize as Employee (no collision, hint compatible)
```

**Example - Collision (content takes precedence):**
```java
// CODEC_ROOT_OBJECT = Person
// Content _type = Address (unrelated type)
// Result: WARNING raised, deserialize as Address (content type)
```

### 15.7 Internal Type Resolution Flow

This section describes the internal implementation details for type resolution during deserialization.

#### 15.7.1 Context Attributes

The deserializer uses Jackson context attributes to pass type hints through the deserialization tree:

| Attribute Key | Type | Purpose |
|---------------|------|---------|
| `CODEC_EXPECTED_TYPE` | `EClass` (required) | Expected type hint for current object |
| `CODEC_UNRESOLVED_REFERENCES` | `List<UnresolvedReference>` | Collector for cross-references |

**Contract for `EXPECTED_TYPE`:**
- **MUST** always be of type `EClass` when set (never a URI string)
- Resolution from URI string to `EClass` happens **before** setting the attribute
- If a non-`EClass` value is set, `ContextHelper.getExpectedType()` throws `IllegalStateException`
- Can be `null` if no hint is available

#### 15.7.2 ContextHelper

The `ContextHelper` class (`org.eclipse.fennec.codec.v2.context.ContextHelper`) provides type-safe access to context attributes:

```java
// Get expected type (returns null if not set, throws if wrong type)
EClass hint = ContextHelper.getExpectedType(ctxt);

// Set expected type (throws if null)
ContextHelper.setExpectedType(ctxt, eClass);

// Clear expected type
ContextHelper.clearExpectedType(ctxt);
```

Works with both `DeserializationContext` and `SerializationContext`.

#### 15.7.3 Type Resolution Flow

```
┌─────────────────────────────────────────────────────────────────┐
│ CodecResource.doLoad()                                          │
│                                                                 │
│  1. Resolve CODEC_ROOT_OBJECT option:                          │
│     - If EClass: use directly                                  │
│     - If URI String: resolve to EClass via EPackage.Registry   │
│                                                                 │
│  2. Set EXPECTED_TYPE context attribute (if hint available)    │
│     reader.withAttribute(EXPECTED_TYPE, resolvedEClass)        │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│ CodecEObjectDeserializer.deserialize()                          │
│                                                                 │
│  1. Get hint: ContextHelper.getExpectedType(ctxt)              │
│                                                                 │
│  2. Read JSON properties:                                       │
│     - If _type field found: resolve EClass from _type value    │
│     - If no _type: use hint as fallback                        │
│                                                                 │
│  3. Create EObject from resolved EClass                        │
│                                                                 │
│  4. Deserialize properties (including containment references)  │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼ (for each containment reference)
┌─────────────────────────────────────────────────────────────────┐
│ ReferenceDeserializationEntry.deserializeContainedObject()      │
│                                                                 │
│  1. Save current: previousHint = getExpectedType(ctxt)         │
│                                                                 │
│  2. Set EReference.eType as hint for nested object:            │
│     setExpectedType(ctxt, reference.getEReferenceType())       │
│                                                                 │
│  3. Delegate to CodecEObjectDeserializer                       │
│                                                                 │
│  4. Restore previous hint (or clear if was null)               │
└─────────────────────────────────────────────────────────────────┘
```

#### 15.7.4 EReference.eType Fallback

For nested containment objects, the deserializer automatically uses `EReference.getEReferenceType()` as the type hint. This enables:

- **Concrete reference types**: Nested objects don't need `_type` if the reference type is concrete
- **Abstract reference types**: Nested objects need `_type` to specify which concrete subtype

**Example - Concrete reference type (no _type needed):**
```java
// EClass Order has: EReference customer -> Customer (concrete)
// JSON:
{
  "_type": "http://example.org#//Order",
  "customer": {
    "name": "Alice"   // No _type needed - Customer is concrete
  }
}
```

**Example - Abstract reference type (_type required):**
```java
// EClass Garage has: EReference vehicles -> Vehicle (abstract)
// JSON:
{
  "_type": "http://example.org#//Garage",
  "vehicles": [
    { "_type": "http://example.org#//Car", "doors": 4 },
    { "_type": "http://example.org#//Motorcycle", "engineCC": 750 }
  ]
}
```

---

## 16. Configuration Hierarchy

### 16.1 Levels (Dynamic before Static)

| Priority | Level | Scope |
|----------|-------|-------|
| 1 (highest) | **Load/Save options** | Per-operation (`resource.save(options)`) |
| 2 | **ResourceFactory defaults** | Per-factory (`defaultSaveOptions`/`defaultLoadOptions`) |
| 3 | **Codec module config** | Per-codec (Jackson module configuration) |
| 4 | **Configuration properties** | External (system props, config files) |
| 5 | **EAnnotations** | Per-model (declared in .ecore) |
| 6 (lowest) | **Built-in defaults** | Global (hardcoded) |

### 16.2 Override Rules

- Dynamic/runtime settings override static/declared settings
- Every EAnnotation detail key has a corresponding runtime option key
- Every Jackson module feature has a corresponding runtime option key
- This allows complete override capability at load/save time

### 16.3 JsonMapper.Builder Configuration

The `JsonMapper.Builder` (Jackson's mapper builder) can be provided at different levels:

| Level | How to Provide | Scope |
|-------|----------------|-------|
| **ResourceFactory** | Constructor parameter | All resources from this factory |
| **CodecResource** | Constructor parameter | Single resource |
| **Default** | `null` → `JsonMapper.builder()` | Built-in default |

**Configuration Flow:**

```
ResourceFactory(metadataService, config, mapperBuilder)
       ↓
    creates
       ↓
CodecResource(uri, metadataService, config, mapperBuilder)
       ↓
    uses mapperBuilder (or creates default if null)
       ↓
    configures based on CodecConfiguration + aspects + options
       ↓
    builds ObjectMapper for serialization/deserialization
```

**Important:** The `mapperBuilder` provides base Jackson configuration (features, modules, etc.). The codec then applies its own configuration (serializers, deserializers) on top of this base. This allows users to:
- Pre-configure Jackson features (e.g., pretty printing, date format)
- Register custom Jackson modules
- Set up Jackson-level serialization/deserialization settings

**Example - Custom mapper at factory level:**
```java
JsonMapper.Builder customBuilder = JsonMapper.builder()
    .enable(SerializationFeature.INDENT_OUTPUT)
    .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

CodecResourceFactory factory = new CodecResourceFactory(
    metadataService,
    config,
    customBuilder  // All resources use this base configuration
);
```

### 16.4 Configuration Dependencies

Certain configuration settings have logical dependencies. When a parent setting is disabled, dependent settings are implicitly disabled as well.

#### 16.4.1 Type and SuperType Dependency

| `serializeType` | `serializeSuperTypes` (configured) | `serializeSuperTypes` (effective) |
|-----------------|-----------------------------------|-----------------------------------|
| `true` | `true` | `true` |
| `true` | `false` | `false` |
| `false` | `true` | `false` (implicitly disabled) |
| `false` | `false` | `false` |

**Rationale:** Supertypes are a form of type information. If type serialization is disabled, serializing supertypes would be inconsistent and potentially confusing for consumers.

**Implementation Note:** The effective value of `isSerializeSuperTypes()` should return `serializeType && serializeSuperTypes`. This ensures:
- No warning/error needed when both are explicitly set
- Consistent behavior: disabling type info disables all type-related serialization
- Clear semantics: `serializeSuperTypes` acts as "serialize supertypes **in addition to** type"

#### 16.4.2 SuperType Sub-Options Dependency

`serializeAllSuperTypes` depends on `serializeSuperTypes` (which in turn depends on `serializeType`):

| `serializeSuperTypes` (effective) | `serializeAllSuperTypes` (configured) | `serializeAllSuperTypes` (effective) |
|-----------------------------------|---------------------------------------|--------------------------------------|
| `true` | `true` | `true` |
| `true` | `false` | `false` |
| `false` | `true` | `false` (implicitly disabled) |
| `false` | `false` | `false` |

**Implementation Note:** The effective value of `isSerializeAllSuperTypes()` should return `isSerializeSuperTypes() && serializeAllSuperTypes`.

The `serializeSuperTypesAsArray` setting does **not** have dependency enforcement - it simply has no effect when supertypes aren't serialized. This is a format preference that can be pre-configured.

#### 16.4.3 ID Sub-Options Dependency

All ID-related sub-options depend on `useId`:

| `useId` | Sub-option (configured) | Sub-option (effective) |
|---------|-------------------------|------------------------|
| `true` | `true` | `true` |
| `true` | `false` | `false` |
| `false` | `true` | `false` (implicitly disabled) |
| `false` | `false` | `false` |

This applies to:
- `idOnTop` - Whether ID should appear first in output
- `serializeIdField` - Whether to serialize ID as a regular feature too
- `idFeatureAsPrimaryKey` - Whether ID feature acts as primary key

**Implementation Note:** The effective values should return `useId && <configured_value>`.

#### 16.4.4 Summary Table

| Setting | Depends On | Enforcement |
|---------|-----------|-------------|
| `serializeSuperTypes` | `serializeType` | Effective value logic |
| `serializeAllSuperTypes` | `serializeSuperTypes` | Effective value logic |
| `serializeSuperTypesAsArray` | `serializeSuperTypes` | No enforcement (format preference) |
| `idOnTop` | `useId` | Effective value logic |
| `serializeIdField` | `useId` | Effective value logic |
| `idFeatureAsPrimaryKey` | `useId` | Effective value logic |

---

## 17. Test Scenarios

### 17.1 Serialization Tests

| ID | Scenario | Input | Expected Output |
|----|----------|-------|-----------------|
| S1 | Type PLAIN/URI | Person object | `"_type": "http://...#//Person"` |
| S2 | Type PLAIN/NAME | Person object | `"_type": "Person"` |
| S3 | Type STRUCTURED | Person object | `"_type": { "schema": "...", "name": "Person" }` |
| S4 | Type SCHEMA_AND_TYPE | Person object | `"_schema": "...", "_type": "Person"` |
| S5 | ID PLAIN | Person with id | `"_id": "john"` |
| S6 | ID STRUCTURED | Person with id | `"_id": { "type": "...", "value": "john" }` |
| S7 | Ref PLAIN | Person with employer | `"employer": "acme"` |
| S8 | Ref STRUCTURED | Person with employer | `"employer": { "_type": "...", "_ref": "acme" }` |
| S9 | SuperType PLAIN | Person extends Entity | `"_supertype": ["...#//Entity"]` |
| S10 | SuperType STRUCTURED | Person extends Entity | `"_supertype": [{ "schema": "...", "name": "Entity" }]` |
| S11 | JSON-LD keys | Person with @type/@id | `"@type": "Person", "@id": "john"` |
| S12 | Mixed strategies | Type STRUCTURED, Ref PLAIN | Combination |

### 17.2 Deserialization Tests

| ID | Scenario | Input | Expected |
|----|----------|-------|----------|
| D1 | Type PLAIN/URI | `"_type": "http://...#//Person"` | Person EObject |
| D2 | Type STRUCTURED | `"_type": { "schema": "...", "name": "Person" }` | Person EObject |
| D3 | Type SCHEMA_AND_TYPE | `"_schema": "...", "_type": "Person"` | Person EObject |
| D4 | Auto-detect format | Any valid format | Correct EObject |
| D5 | Unknown package | Type with unregistered schema | Error or lazy load |
| D6 | Ref resolution | `"employer": { "_ref": "acme" }` | Resolved reference |

### 17.3 Round-trip Tests

| ID | Scenario | Verify |
|----|----------|--------|
| R1 | PLAIN round-trip | serialize → deserialize → equals |
| R2 | STRUCTURED round-trip | serialize → deserialize → equals |
| R3 | Cross-format | serialize PLAIN → deserialize (auto) → equals |
| R4 | V1 compatibility | V1 output → V2 deserialize → equals |

### 17.4 Configuration Hierarchy Tests

Tests for configuration override behavior as specified in Section 16.

| ID | Scenario | Configuration | Expected |
|----|----------|---------------|----------|
| C1 | Built-in defaults only | No configuration | Default values applied |
| C2 | CodecConfiguration overrides defaults | `serializeType=false` | Type not serialized |
| C3 | Factory defaults override CodecConfiguration | Factory: `typeKey="_t"`, Config: `typeKey="_type"` | `_t` used |
| C4 | Load/Save options override all | Options: `typeKey="@type"`, Factory: `typeKey="_t"` | `@type` used |
| C5 | EAnnotation provides base | `@codec.type(key="_T")` on EClass | `_T` used unless overridden |
| C6 | Runtime overrides EAnnotation | EAnnotation: `key="_T"`, Options: `key="_type"` | `_type` used |

### 17.5 Type Resolution Tests

Tests for type resolution and collision detection as specified in Section 15.6.

| ID | Scenario | CODEC_ROOT_OBJECT | Content Type | Expected |
|----|----------|-------------------|--------------|----------|
| T1 | Only hint, no content type | `Person` | none | Use `Person` |
| T2 | Only content type, no hint | none | `Person` | Use `Person` |
| T3 | Same type (no collision) | `Person` | `Person` | Use `Person` |
| T4 | Subtype compatible (no collision) | `Person` | `Employee` | Use `Employee` |
| T5 | Supertype collision | `Employee` | `Person` | WARNING, use `Person` |
| T6 | Unrelated collision | `Person` | `Address` | WARNING, use `Address` |
| T7 | Both null | none | none | ERROR: no type info |
| T8 | Abstract hint, content subtype | `AbstractEntity` | `Person` | Use `Person` |
| T9 | Abstract hint, no content type | `AbstractEntity` | none | ERROR: cannot instantiate |
| T10 | Interface hint, content impl | `Named` (interface) | `Person` | Use `Person` |
| T11 | Interface hint, no content type | `Named` (interface) | none | ERROR: cannot instantiate |

### 17.6 CODEC_ROOT_OBJECT Value Type Tests

Tests for CODEC_ROOT_OBJECT option value handling as specified in Section 15.4.

| ID | Scenario | Option Value | Expected |
|----|----------|--------------|----------|
| O1 | EClass directly | `PersonPackage.Literals.PERSON` | Resolved to Person EClass |
| O2 | URI string | `"http://example.org/1.0#//Person"` | Resolved via MetadataService |
| O3 | Unknown URI | `"http://unknown.org/1.0#//X"` | ERROR: unresolved type |
| O4 | Invalid type | `Integer.valueOf(42)` | ERROR: invalid option type |
| O5 | Null value | `null` | No hint (rely on content type) |
| O6 | Empty string | `""` | ERROR: invalid URI |
| O7 | Unregistered package + content type | Unregistered URI + content `Person` | WARNING, use content type |
| O8 | Unregistered package + no content | Unregistered URI + no content type | ERROR: cannot resolve |

---

## 18. Additional Serialization Features

### 18.1 Collection and Array Handling

#### Ordered vs Unordered Collections

| EMF Type | JSON Representation | Notes |
|----------|---------------------|-------|
| `EList` | JSON array | Order preserved |
| `ESet` (unique) | JSON array | Order preserved, uniqueness enforced on load |

#### Multi-Dimensional Arrays

For complex data like GeoJSON coordinates, multi-dimensional arrays are supported via EDataTypes:

```java
// EDataType for 2D coordinates
EDataType coordinateArray = EcoreFactory.eINSTANCE.createEDataType();
coordinateArray.setInstanceClass(double[][].class);
```

**Resulting Output:**
```json
{
  "type": "Polygon",
  "coordinates": [
    [[0, 0], [10, 0], [10, 10], [0, 10], [0, 0]]
  ]
}
```

### 18.2 EMap Serialization

EMF Maps (`EMap<K,V>`) are serialized as JSON objects when keys are strings, or as arrays of key-value pairs for complex keys:

**String Keys (default):**
```json
{
  "properties": {
    "name": "John",
    "age": "30"
  }
}
```

**Complex Keys (EObject keys):**
```json
{
  "associations": [
    { "_key": { "_type": "...", "_ref": "person1" }, "_value": "manager" },
    { "_key": { "_type": "...", "_ref": "person2" }, "_value": "employee" }
  ]
}
```

**Configurable keys:**
- `mapKeyKey`: key field name (default: `_key`)
- `mapValueKey`: value field name (default: `_value`)

### 18.3 Null vs Absent vs Default

See **Section 9.1** for complete documentation of null, default, and empty value handling.

**Summary:**
- Codec-wide defaults: `serializeDefaults=false`, `serializeNull=false`, `serializeEmpty=true`
- Feature-specific overrides: `serialize`, `serializeNull`, `serializeDefaults`, `serializeEmpty`

### 18.4 Proxy and Reference Expansion

#### Default Behavior: Serialize as Proxy

Non-containment references are serialized as **proxies by default**. At deserialization, they remain proxies until explicitly resolved.

**Rationale:**
- At deserialization time, we don't know if resolution is needed
- Referenced data may not be accessible
- Lazy loading is more efficient

**Default Output:**
```json
{
  "employer": {
    "_type": "http://example.org/company/1.0#//Company",
    "_ref": "acme-corp"
  }
}
```

#### Expand Option: Full Object Serialization

For specific features, expand non-containment references to full objects:

**EAnnotation (on EReference):**
```xml
<eStructuralFeatures xsi:type="ecore:EReference" name="employer" eType="#//Company">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="codec.reference"/>
    <details key="expand" value="true"/>
  </eAnnotations>
</eStructuralFeatures>
```

**Java Builder (Runtime Override):**
```java
FeatureSerializationConfig config = FeatureSerializationConfig.builder()
    .feature("employer")
    .expand(true)  // Serialize full object, not just reference
    .build();
```

**With expand=true:**
```json
{
  "employer": {
    "_type": "http://example.org/company/1.0#//Company",
    "_id": "acme-corp",
    "name": "ACME Corporation",
    "address": "123 Main St"
  }
}
```

> **Note:** Expanded objects create a copy in the JSON. Changes to the expanded object don't affect the original. Use with caution.

### 18.5 Unknown Fields Handling

Options for handling fields in JSON that don't match EClass features:

| Option | Behavior |
|--------|----------|
| `IGNORE` | Silently skip unknown fields |
| `STORE` | Collect in map, return via Resource options |
| `FAIL` | Throw exception |

**Default:** `IGNORE`

**Java Builder:**
```java
CodecConfig config = CodecConfig.builder()
    .unknownFieldHandling(UnknownFieldHandling.STORE)
    .build();
```

**Retrieving Stored Unknown Fields:**
```java
Resource resource = resourceSet.getResource(uri, true);
Map<String, Object> loadOptions = resource.getDefaultLoadOptions();

// After loading, unknown fields are available in options
@SuppressWarnings("unchecked")
Map<EObject, Map<String, Object>> unknownFields =
    (Map<EObject, Map<String, Object>>) loadOptions.get(CodecOptions.UNKNOWN_FIELDS);
```

### 18.6 Field Ordering

See **Section 8.5** for full field ordering configuration.

**Summary:**
- `fieldOrder`: `DECLARATION` (default) or `ALPHABETICAL`
- `metadataFieldsFirst`: `true` (default) - places `_id`, `_type`, `_supertype` before features

**Performance Note:** When field order matches expected order, the deserializer can use direct mapping without cache lookups, significantly improving throughput for large datasets.

### 18.7 Root Element / Multiple Contents

EMF Resources support multiple root elements in the contents list. This maps to JSON arrays:

**Single Root (default for single content):**
```json
{
  "_type": "http://example.org/person/1.0#//Person",
  "_id": "john",
  "firstName": "John"
}
```

**Multiple Roots (for multiple contents):**
```json
[
  {
    "_type": "http://example.org/person/1.0#//Person",
    "_id": "john",
    "firstName": "John"
  },
  {
    "_type": "http://example.org/person/1.0#//Person",
    "_id": "jane",
    "firstName": "Jane"
  }
]
```

**Deserialization:** Automatically detects array vs object at root.

### 18.8 Circular References (Future)

> **Note:** This feature is designed but not actively implemented in v2.0.

For bidirectional references that create cycles, the metadata service can track this:

```java
// In metadata aspect
FeatureCodecAspect aspect = ...;
aspect.isBidirectional();  // true if this creates potential cycles
```

**Handling strategies (future):**
- First occurrence: full object
- Subsequent occurrences: reference only
- Detection via object identity tracking during serialization

### 18.9 Custom EDataType Serialization

For custom EDataTypes beyond standard primitives, register Jackson serializers/deserializers:

**Via Codec API:**
```java
CodecConfig config = CodecConfig.builder()
    .registerSerializer(BigDecimal.class, new BigDecimalSerializer())
    .registerDeserializer(BigDecimal.class, new BigDecimalDeserializer())
    .build();
```

**Via Metadata API (for model-wide defaults):**
```java
// On EDataType annotation
@CODEC_SERIALIZER("bigDecimalSerializer")
@CODEC_DESERIALIZER("bigDecimalDeserializer")
EDataType myBigDecimal;
```

### 18.10 Jackson 3 Integration

The following features delegate to Jackson 3 configuration and are not duplicated in codec config:

- **Binary data encoding** (Base64)
- **Date/Time formatting** (ISO8601, timestamps)
- **Pretty printing** (indentation, formatting)
- **Number precision** (BigDecimal scale)

**Configuration via ObjectMapperConfigurator:**
```java
ObjectMapperConfigurator config = ObjectMapperConfigurator.builder()
    .enable(SerializationFeature.INDENT_OUTPUT)
    .dateFormat(new ISO8601DateFormat())
    .build();
```

Only features NOT supported by Jackson 3 need codec-specific handling.

### 18.11 EMF Codec Context (Custom Generators/Parsers)

This section documents the internal context mechanism used during serialization and deserialization. This is relevant for developers creating **custom Jackson generators and parsers** for the codec framework (e.g., for MongoDB, CSV, or other formats).

#### Overview

The codec uses custom Jackson `StreamWriteContext` and `StreamReadContext` implementations to track EMF state during serialization/deserialization. This allows serializers and deserializers to access:

- The current EObject being processed
- The current EStructuralFeature being processed
- The EMF Resource
- **EffectiveCodecConfig** - merged configuration from all sources (the single source of truth)
- Type resolution via the MetadataService (accessible through EffectiveCodecConfig)

#### Configuration Architecture

The context uses `EffectiveCodecConfig` as the single source of truth for all configuration:

```
Load/Save Options (Level 3)     ─┐
Static Config (Level 2)          ├──► ConfigurationMerger ──► EffectiveCodecConfig
MetadataService (Level 1)       ─┘                                    │
                                                                      ▼
                                                              EMFContextHolder
                                                                      │
                                                        ┌─────────────┴─────────────┐
                                                        ▼                           ▼
                                            CodecJsonReadContext         CodecWriteContext
```

**Benefits:**
- **Single source of truth**: One merged config instead of multiple scattered sources
- **Flexibility**: Live MetadataService OR immutable snapshot
- **Cleaner API**: Contexts ask the config, not multiple services
- **Testability**: Easy to mock one config object
- **Consistency**: Same pattern for read and write contexts

#### EMFCodecContext (Base Interface)

Common interface for both read and write contexts:

```java
public interface EMFCodecContext {

    /**
     * Returns the EffectiveCodecConfig - the single source of truth for all merged configuration.
     */
    EffectiveCodecConfig getEffectiveConfig();

    /**
     * Returns the MetadataService for aspect lookups.
     * Convenience method that delegates to EffectiveCodecConfig.
     */
    default MetadataService getMetadataService() {
        EffectiveCodecConfig config = getEffectiveConfig();
        return config != null ? config.getMetadataService() : null;
    }

    /**
     * Convenience method to get ClassMetadata for an EClass.
     */
    default ClassMetadata getClassMetadata(EClass eClass) {
        EffectiveCodecConfig config = getEffectiveConfig();
        return config != null ? config.getClassMetadata(eClass) : null;
    }

    // ... EObject, Feature, Resource accessors
}
```

#### EMFCodecWriteContext (Serialization)

Used during serialization to track state and provide access to configuration.

```java
public interface EMFCodecWriteContext extends EMFCodecContext {

    /**
     * Returns the EObject currently being serialized.
     */
    EObject getCurrentEObject();

    /**
     * Sets the EObject currently being serialized.
     */
    void setCurrentEObject(EObject eObject);

    /**
     * Returns the EStructuralFeature currently being serialized.
     */
    EStructuralFeature getCurrentFeature();

    /**
     * Sets the EStructuralFeature currently being serialized.
     */
    void setCurrentFeature(EStructuralFeature feature);

    /**
     * Returns the EMF Resource being serialized.
     */
    Resource getResource();

    /**
     * Sets the EMF Resource being serialized.
     */
    void setResource(Resource resource);
}
```

#### EMFCodecReadContext (Deserialization)

Used during deserialization to track state, resolve types, and manage nested context.

```java
public interface EMFCodecReadContext extends EMFCodecContext {

    /**
     * Returns the EObject currently being populated.
     */
    EObject getCurrentEObject();

    /**
     * Sets the EObject currently being populated.
     */
    void setCurrentEObject(EObject eObject);

    /**
     * Returns the EStructuralFeature currently being populated.
     */
    EStructuralFeature getCurrentFeature();

    /**
     * Sets the EStructuralFeature currently being populated.
     */
    void setCurrentFeature(EStructuralFeature feature);

    /**
     * Returns the target EMF Resource.
     */
    Resource getResource();

    /**
     * Sets the target EMF Resource.
     */
    void setResource(Resource resource);

    /**
     * Returns the type hint for the current context.
     * Used for nested object deserialization when featurePath resolution needs context.
     */
    EClass getCurrentTypeHint();

    /**
     * Sets the type hint for the current context.
     */
    void setCurrentTypeHint(EClass typeHint);

    /**
     * Creates a child context for nested array deserialization.
     * Child inherits EffectiveCodecConfig but gets fresh EMF state.
     */
    EMFCodecReadContext createChildArrayContext(int lineNr, int colNr);

    /**
     * Creates a child context for nested object deserialization.
     * Child inherits EffectiveCodecConfig but gets fresh EMF state.
     */
    EMFCodecReadContext createChildObjectContext(int lineNr, int colNr);

    /**
     * Resolves an EClass from a type value (URI, name, discriminator, etc.).
     *
     * @param typeValue the type value from JSON
     * @return the resolved EClass, or null if not found
     */
    EClass resolveEClass(String typeValue);

    /**
     * Resolves an EClass from a type value with a hint.
     *
     * @param typeValue the type value from JSON (may be null)
     * @param hint the type hint from parent context or CODEC_ROOT_OBJECT option
     * @return the resolved EClass
     */
    EClass resolveEClass(String typeValue, EClass hint);
}
```

#### EMFContextHolder

Internal holder class that stores EMF state for a context. Used by context implementations to delegate state management:

```java
public class EMFContextHolder {
    private EStructuralFeature currentFeature;
    private EObject currentEObject;
    private Resource resource;
    private EffectiveCodecConfig effectiveConfig;
    private EClass currentTypeHint;

    public EMFContextHolder(EffectiveCodecConfig effectiveConfig) {
        this.effectiveConfig = effectiveConfig;
    }

    // Getters and setters for all fields
    // MetadataService is accessed via effectiveConfig.getMetadataService()
}
```

#### JSON-Specific Implementation

For JSON format, the codec provides specialized context classes:

- **CodecJsonFactory**: Creates CodecJsonParser instances with EffectiveCodecConfig
- **CodecJsonParser**: Extends UTF8StreamJsonParser, uses CodecJsonReadContext
- **CodecJsonReadContext**: Extends JsonReadContext, implements EMFCodecReadContext
- **CodecTokenBufferReadContext**: For token replay scenarios (featurePath resolution)

```java
// Creating a parser with codec context
EffectiveCodecConfig config = ConfigurationMerger.merge(
    moduleConfig, metadataService, factoryDefaults, loadOptions);

CodecJsonFactory factory = new CodecJsonFactory(config);
try (JsonParser parser = factory.createParser(inputStream)) {
    if (parser.streamReadContext() instanceof CodecJsonReadContext ctx) {
        ctx.setResource(resource);
        ctx.setCurrentTypeHint(rootEClassHint);
    }
    // Parse...
}
```

#### Child Context Pattern

Jackson pools child contexts for performance. When deserializing nested objects:

1. Parent creates child context via `createChildObjectContext()`
2. Child inherits `EffectiveCodecConfig` from parent (same instance)
3. Child gets fresh EMF state (null EObject, Feature, TypeHint)
4. When reused (pooling), child's EMF state is reset but config remains

```java
// In CodecJsonReadContext.createChildObjectContext()
@Override
public CodecJsonReadContext createChildObjectContext(int lineNr, int colNr) {
    CodecJsonReadContext ctxt = (CodecJsonReadContext) _child;
    if (ctxt == null) {
        _child = ctxt = new CodecJsonReadContext(this, ...);
        ctxt.holder = new EMFContextHolder(this.holder.getEffectiveConfig());
    } else {
        ctxt.reset(TYPE_OBJECT, lineNr, colNr);
        // Reset EMF state but keep config
        ctxt.holder.setCurrentEObject(null);
        ctxt.holder.setCurrentFeature(null);
        ctxt.holder.setCurrentTypeHint(null);
    }
    return ctxt;
}
```

#### Usage in Custom Serializers

Custom serializers can access the context via the `JsonGenerator`:

```java
public class MyCustomSerializer extends ValueSerializer<EObject> {

    @Override
    public void serialize(EObject value, JsonGenerator gen, SerializationContext provider) {
        if (gen.streamWriteContext() instanceof EMFCodecWriteContext ctx) {
            // Access current state
            EObject parent = ctx.getCurrentEObject();
            Resource resource = ctx.getResource();

            // Access effective config for merged settings
            EffectiveCodecConfig config = ctx.getEffectiveConfig();
            EffectiveClassConfig classConfig = config.getClassConfig(value.eClass());

            // Access metadata (convenience method)
            ClassMetadata metadata = ctx.getClassMetadata(value.eClass());

            // Serialize with config
            if (classConfig.getTypeConfig().isEnabled()) {
                gen.writeStringProperty("_type", classConfig.getTypeConfig().getTypeKey());
            }
            // ...
        }
    }
}
```

#### Usage in Custom Deserializers

Custom deserializers can access the context via the `JsonParser`:

```java
public class MyCustomDeserializer extends ValueDeserializer<EObject> {

    @Override
    public EObject deserialize(JsonParser p, DeserializationContext ctxt) {
        if (p.streamReadContext() instanceof EMFCodecReadContext ctx) {
            // Get type hint from context (for nested objects)
            EClass typeHint = ctx.getCurrentTypeHint();

            // Read type value from JSON
            String typeValue = // ... read from JSON

            // Resolve EClass using context (considers hint)
            EClass eClass = ctx.resolveEClass(typeValue, typeHint);

            // Create EObject
            EObject result = EcoreUtil.create(eClass);
            ctx.setCurrentEObject(result);

            // For nested object deserialization, create child context
            EMFCodecReadContext childCtx = ctx.createChildObjectContext(
                p.currentLocation().lineNr(), p.currentLocation().columnNr());
            childCtx.setCurrentTypeHint(nestedType);

            // Access effective config for deserialization settings
            EffectiveCodecConfig config = ctx.getEffectiveConfig();
            EffectiveClassConfig classConfig = config.getClassConfig(eClass);
            // ...

            return result;
        }
        throw new IllegalStateException("Expected EMFCodecReadContext");
    }
}
```

#### Custom Generator/Parser Implementation

When creating a custom Jackson generator (e.g., for MongoDB BSON), implement the context interfaces using `EMFContextHolder` for state management:

```java
public class BsonCodecWriteContext extends JsonWriteContext implements EMFCodecWriteContext {

    private final EMFContextHolder holder;

    public BsonCodecWriteContext(EffectiveCodecConfig config, int type, JsonWriteContext parent) {
        super(type, parent);
        this.holder = new EMFContextHolder(config);
    }

    @Override
    public EffectiveCodecConfig getEffectiveConfig() {
        return holder.getEffectiveConfig();
    }

    @Override
    public EObject getCurrentEObject() {
        return holder.getCurrentEObject();
    }

    @Override
    public void setCurrentEObject(EObject eObject) {
        holder.setCurrentEObject(eObject);
    }

    // ... delegate other methods to holder
}
```

This allows your custom format to integrate seamlessly with the codec's serialization pipeline while maintaining access to the merged configuration.

---

## 19. Open Questions

1. **Supertype in STRUCTURED type** ✓ ANSWERED
   → Separate config via `includeSupertypes(true)` on TypeSerializationConfig. Not automatic.

2. **Reference type inference** ✓ ANSWERED
   → Configurable via `useSmartCompression(true)`. If all refs in collection have same type, omit type info.

3. **Null handling in STRUCTURED format** ✓ ANSWERED
   → Follow `serializeNull` setting. If included, output `"employer": null` (not a structured null object).

4. **Proxy references** ✓ ANSWERED → See Section 5.4 (Proxy and Expand Handling)
   → Covers proxy serialization, expand options, type config per context, bi-directional handling.

5. **Cross-document references** ✓ ANSWERED
   → Use URIs as-is. No special treatment (full vs relative) for now.

---

## 20. Spec Contradictions and Issues to Resolve

### 20.1 PLAIN Strategy Terminology ✓ RESOLVED

**Issue:** The term "PLAIN" was used inconsistently - sometimes as a strategy, sometimes as a category.

**Resolution:** Added Section 2.1 "Plain vs Structured Output Format" as the central definition:
- **Plain**: Property value is a simple type (string, number, boolean, array)
- **Structured**: Property value is a nested object/map

Section 2.2 now classifies all strategies by output format. Section 3.1 now groups type strategies as "Plain Strategies" and "Structured Strategies" with clear headings.

### 20.2 ID Strategy vs Format Confusion ✓ RESOLVED

**Issue:** Section 4 conflated ID source (ID_FIELD vs COMBINED) with ID format (PLAIN vs STRUCTURED).

**Resolution:** Simplified to two orthogonal dimensions:
- **ID Key Mode**: ID_ONLY | BOTH | FEATURE_ONLY - how to represent in JSON
- **ID Format**: PLAIN | STRUCTURED - per Section 2.1

The "COMBINED" strategy is now implicit - when `idFeatures` has multiple entries, values are automatically combined with separator. Section 4 completely rewritten with examples from `org.eclipse.fennec.codec.v2.example/model/test.ecore`.

### 20.3 ignoreNull vs serializeNull Semantics ✓ RESOLVED

**Issue:** Confusing opposite naming:
- Codec-wide: `serializeNull=false` → don't serialize nulls
- Feature-level: `ignoreNull=false` → don't ignore = DO serialize nulls

Same boolean value has opposite meanings!

**Resolution:** Use `serializeX` consistently everywhere:
- `serialize` (default: true) - false = transient/skip feature
- `serializeNull` (default: false) - true = include null values
- `serializeDefaults` (default: false) - true = include default values
- `serializeEmpty` (default: true) - true = include empty collections

**Semantics:** `true` = include in output, `false` = omit from output. Feature-level overrides codec-wide. See Section 9.1 for examples.

### 20.4 Feature Key Annotation Name ✓ RESOLVED

**Issue:**
- Section 1.1 lists `codec.key` as the annotation
- Section 9.2 shows `<details key="key" value="first_name"/>`

**Resolution:** Section 1.1 table updated. Within the EAnnotation (source: `http://eclipse.org/fennec/codec`), the detail key is simply `key`, not `codec.key`. The `codec.` prefix is only used for marker annotations like `codec.type`, `codec.id`, etc.

### 20.5 SuperType strategy vs format ✓ RESOLVED

**Issue:** Section 7.3 Java Builder shows both `.strategy(...)` and `.format(...)` but their relationship is unclear.

**Resolution:** Section 7 updated with clear terminology:
- `selection`: ALL | ALL_EMF | SINGLE | NONE - which supertypes to include
- `format`: PLAIN | STRUCTURED - per Section 2.1, how each entry is represented

Added `ALL_EMF` to include EMF base classes (EObject, etc.) when needed. Default `ALL` excludes EMF internal classes. Builder uses `.structured()` method to switch to STRUCTURED format (PLAIN is default).

### 20.6 Configuration Hierarchy Inconsistency ✓ RESOLVED

**Issue:**
- Section 1.1: "Runtime Config Builder > Model EAnnotations > Codec Defaults" (3 levels)
- Section 16.1: "Global defaults > Codec instance > Per-EClass > Runtime options" (4 levels)

**Resolution:** Consolidated to a 6-level hierarchy (dynamic before static):
1. Load/Save options (per-operation)
2. ResourceFactory defaults (per-factory)
3. Codec module config (per-codec)
4. Configuration properties (external)
5. EAnnotations (per-model, static)
6. Built-in defaults (global)

Key principle: Every configuration setting (EAnnotation keys, Jackson features) must have a corresponding runtime option key for override capability.

### 20.7 Implementation Checklist Outdated ✓ RESOLVED

**Issue:** Section 21 checklist shows items already completed (TypeStrategy enum update).

**Resolution:** Section 21 now contains implementation strategy and phased checklist.

---

## 21. Implementation Strategy

### 21.1 Approach: Minimal Pipeline First

The implementation follows this approach:
1. **Minimal working pipeline** - ResourceFactory → Resource → ObjectMapper → JSON
2. **Test-driven** - Write tests as we build each layer
3. **Feature-by-feature** - Add one feature at a time, fully tested
4. **Reference v1** - Always compare with existing codec behavior

### 21.2 Implementation Phases

#### Phase 1: Infrastructure (Minimal Pipeline)

**Goal:** Get a minimal JSON output from an EObject with hardcoded defaults.

**Steps:**
1. Create `org.eclipse.fennec.codec.v2` project structure
2. Define `CodecResourceFactory` interface
3. Create `CodecResource` that wraps Jackson ObjectMapper
4. Implement basic `CodecEObjectSerializer` (type + features only)
5. **Test:** Serialize simple Person → JSON with `_type` and feature values

**Checklist per step:**
- [ ] Does v1 have this? How?
- [ ] What configuration does v1 support here?
- [ ] Test written before implementation?
- [ ] Test passes?

#### Phase 2: Deserialization Pipeline

**Goal:** Round-trip - serialize and deserialize back to equal EObject.

**Steps:**
1. Implement `CodecEObjectDeserializer`
2. Implement type resolution (lookup EClass from `_type`)
3. Implement feature deserialization
4. **Test:** Round-trip Person → JSON → Person with EMF equals

#### Phase 3: Configuration System

**Goal:** Make the pipeline configurable.

**Steps:**
1. Define `CodecConfig` and target-specific config builders
2. Wire config into serializers/deserializers
3. Implement strategy selection (use config to pick serialization approach)
4. **Test:** Same EObject with different configs → different JSON

#### Phase 4: Type Strategies

**Goal:** Support all type serialization strategies.

**Steps (one strategy at a time):**
1. URI (default) - already in Phase 1
2. NAME - simple class name
3. CLASS - instance class name
4. STRUCTURED - nested object
5. SCHEMA_AND_TYPE - separate fields
6. MAPPED - discriminator-based
7. NUMERIC - classifier IDs

**For each strategy:**
- [ ] Serialization test
- [ ] Deserialization test (with auto-detection)
- [ ] Round-trip test

#### Phase 5: ID Strategies

**Goal:** Support all ID serialization strategies.

**Steps:**
1. ID_FIELD source + PLAIN format (default)
2. COMBINED source + PLAIN format
3. STRUCTURED format
4. NUMERIC format
5. Custom ID value readers/writers

#### Phase 6: Reference Strategies

**Goal:** Support all reference serialization strategies.

**Steps:**
1. PLAIN (default - ID only)
2. STRUCTURED (type + ref)
3. NUMERIC
4. Smart compression (omit type when inferable)
5. Expand option (full object)

#### Phase 7: SuperType Serialization

**Goal:** Support supertype serialization (disabled by default).

**Steps:**
1. Enable/disable flag
2. ALL vs SINGLE strategy
3. PLAIN format (URIs)
4. STRUCTURED format
5. Smart compression for same-schema

#### Phase 8: Feature Options

**Goal:** Complete feature-level customization.

**Steps:**
1. Null/Default/Empty handling
2. Feature key customization
3. Transient features
4. Custom value readers/writers
5. Enum serialization strategies
6. Extended metadata names

#### Phase 9: Advanced Features

**Goal:** Complete remaining features.

**Steps:**
1. Cross-document containment
2. EMap serialization
3. Multi-dimensional arrays
4. Unknown fields handling
5. Field ordering
6. Multiple root elements

### 21.3 Feature Checklist Template

For each feature implementation, verify:

| Check | Description |
|-------|-------------|
| **V1 Behavior** | How does v1 handle this? Document any differences |
| **Spec Alignment** | Does implementation match spec? Note any deviations |
| **EAnnotation** | Can it be configured via annotation? |
| **Config Builder** | Can it be configured at runtime? |
| **Default Value** | What's the default? Matches spec? |
| **Serialization Test** | Written and passing? |
| **Deserialization Test** | Written and passing (with auto-detect)? |
| **Round-trip Test** | Written and passing? |
| **Edge Cases** | Null, empty, missing values handled? |

### 21.4 V1 Reference Points

Key v1 classes to reference during implementation:

| Component | V1 Location | Purpose |
|-----------|-------------|---------|
| ResourceFactory | `org.eclipse.fennec.codec.json` | How factory creates resources |
| Resource | `CodecResource` | How resource wraps ObjectMapper |
| ObjectMapper setup | Configurators | How Jackson is configured |
| EObject serializer | `CodecEObjectSerializer` | Main serialization logic |
| Type serializer | `CodecTypeInfoSerializer` | Type info handling |
| ID serializer | `CodecIdInfoSerializer` | ID handling |
| Reference serializer | Various | Reference handling |
| Feature serializer | `CodecFeatureInfoSerializer` | Feature handling |
| Deserializers | `*Deserializer` classes | Deserialization logic |

---

## 22. Implementation Checklist

**Last Updated:** 2025-12-16

### Metadata Layer (codec.metadata) - Complete ✅

- [x] Add `STRUCTURED` to TypeStrategy enum
- [x] Add `SCHEMA_AND_TYPE` to TypeStrategy enum
- [x] Add `NUMERIC` to TypeStrategy enum
- [x] Add `MAPPED` to TypeStrategy enum
- [x] Add `SuperTypeSelection` enum (ALL, ALL_EMF, SINGLE, NONE)
- [x] Add IdKeyMode enum (ID_ONLY, BOTH, FEATURE_ONLY)
- [x] Add IdFormat/SerializationFormat enum (PLAIN, STRUCTURED)
- [x] Add ReferenceFormat enum (via SerializationFormat)
- [x] Define ClassCodecAspect in codec.ecore (EMF-generated)
- [x] Define FeatureCodecAspect in codec.ecore (EMF-generated)
- [x] Define ReferenceCodecAspect in codec.ecore (EMF-generated)

### Phase 1: Core Infrastructure - Complete ✅

- [x] Create `org.eclipse.fennec.codec.v2` project with Gradle setup
- [x] Create `CodecResource` implementation with `doSave()` and `doLoad()`
- [x] Create Jackson `CodecModule` with serializers and deserializers
- [x] Implement `CodecEObjectSerializer` with entry-based pattern
- [x] Implement `CodecEObjectDeserializer` with entry-based pattern
- [x] Implement type resolution (URI → EClass via EPackage.Registry)
- [x] Implement reference resolution (within-resource non-containment refs)
- [x] Create effective configuration pattern (`config.effective.*` package)
- [x] **TEST:** Full round-trip serialization/deserialization

### Phase 1 Tested Capabilities ✅

| Feature | Serialization | Deserialization | Round-trip |
|---------|---------------|-----------------|------------|
| String attributes | ✅ | ✅ | ✅ |
| Integer attributes | ✅ | ✅ | ✅ |
| Boolean attributes | ✅ | ✅ | ✅ |
| Double attributes | ✅ | ✅ | ✅ |
| Multi-valued attributes (lists) | ✅ | ✅ | ✅ |
| Containment references (single) | ✅ | ✅ | ✅ |
| Containment references (multi) | ✅ | ✅ | ✅ |
| Non-containment references (single) | ✅ | ✅ | ✅ |
| Non-containment references (multi) | ✅ | ✅ | ✅ |
| Reference resolution (within resource) | ✅ | ✅ | ✅ |

### Phase 2: Configuration System - Partial

- [x] Define `CodecConfiguration` builder interface
- [x] Define `EffectiveCodecConfig`, `EffectiveClassConfig`, `EffectiveFeatureConfig`
- [x] Create `ConfigurationMerger` for resolution
- [x] Wire config into serializers/deserializers
- [ ] **TEST:** Same EObject + different config → different JSON

### Phase 3: Type Strategies - Partial

| Strategy | Serialization | Deserialization | Round-trip |
|----------|--------------|-----------------|------------|
| URI | ✅ | ✅ | ✅ |
| NAME | [ ] | [ ] | [ ] |
| CLASS | [ ] | [ ] | [ ] |
| STRUCTURED | [ ] | [ ] | [ ] |
| SCHEMA_AND_TYPE | [ ] | [ ] | [ ] |
| MAPPED | [ ] | [ ] | [ ] |
| NUMERIC | [ ] | [ ] | [ ] |

### Phase 4: ID Strategies - Not Started

- [ ] `IdSerializationConfig` usage in serialization
- [ ] ID_FIELD source + PLAIN format
- [ ] COMBINED source + PLAIN format
- [ ] STRUCTURED format
- [ ] NUMERIC format
- [ ] Custom ID value readers/writers
- [ ] **TESTS:** All combinations

### Phase 5: Reference Strategies - Partial

- [x] PLAIN format (default) - `$ref` with URI fragment
- [x] Within-resource resolution via fragment paths
- [ ] STRUCTURED format
- [ ] NUMERIC format
- [ ] Smart compression
- [ ] Expand option
- [ ] Cross-resource references (ResourceSet-based)
- [ ] **TESTS:** All combinations

### Phase 6: SuperType Serialization - Not Started

- [ ] `SuperTypeSerializationConfig` usage in serialization
- [ ] Enable/disable flag
- [ ] ALL vs SINGLE strategy
- [ ] PLAIN format
- [ ] STRUCTURED format
- [ ] Smart compression
- [ ] **TESTS:** All combinations

### Phase 7: Feature Options - Partial

- [x] Basic feature serialization (attributes and references)
- [x] Feature key from feature name (effectiveKey)
- [ ] Null handling (codec-wide + feature-specific)
- [ ] Default value handling
- [ ] Empty collection handling
- [ ] Feature key customization from EAnnotations
- [ ] Transient features (serialize=false)
- [ ] Custom value readers/writers
- [ ] Enum serialization strategies
- [ ] Extended metadata names
- [ ] **TESTS:** All options

### Phase 8: EAnnotation Parsing - Not Started

- [ ] Parse `codec.type` annotations
- [ ] Parse `codec.id` annotations
- [ ] Parse `codec.reference` annotations
- [ ] Parse `codec.supertype` annotations
- [ ] Parse feature-level annotations (`serialize`, `key`, etc.)
- [ ] Override default configs based on annotations
- [ ] **TEST:** Annotated Ecore model serialization

### Phase 9: Advanced Features - Not Started

- [ ] Cross-document containment
- [ ] EMap serialization (string keys + complex keys)
- [ ] Multi-dimensional arrays
- [ ] Unknown fields handling (IGNORE, STORE, FAIL)
- [ ] Field ordering
- [ ] Multiple root elements (array serialization)
- [ ] **TESTS:** All features

### Phase 10: OSGi Integration - Not Started

- [ ] Create `CodecResourceFactory` for OSGi registration
- [ ] Resource factory registration via DS
- [ ] Test with OSGi runtime
- [ ] Document OSGi setup

### Compatibility & Performance - Not Started

- [ ] V1 format compatibility tests
- [ ] Auto-detect format on deserialization
- [ ] Model evolution tests (NUMERIC stability)
- [ ] Performance benchmarks (size comparison)
- [ ] Performance benchmarks (throughput)

---

## 23. Serialization Architecture

This section documents the implemented serialization architecture, including class responsibilities and the serialization flow.

### 23.1 Architecture Overview

The codec.v2 serialization follows an **entry-based pattern** that separates concerns into independent, testable components:

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                         CodecEObjectSerializer                              │
│                     (Jackson ValueSerializer<EObject>)                      │
│                                                                             │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │                    buildSerializationEntries()                       │   │
│  │                                                                      │   │
│  │   ┌──────────────────┐  ┌───────────────────┐  ┌─────────────────┐  │   │
│  │   │ IdSerialization  │  │ TypeSerialization │  │ SuperTypeSer.   │  │   │
│  │   │     Entry        │  │      Entry        │  │    Entry        │  │   │
│  │   └──────────────────┘  └───────────────────┘  └─────────────────┘  │   │
│  │                                                                      │   │
│  │   ┌──────────────────┐  ┌───────────────────┐                       │   │
│  │   │ AttributeSer.    │  │ ReferenceSer.     │   (per feature)       │   │
│  │   │     Entry        │  │     Entry         │                       │   │
│  │   └──────────────────┘  └───────────────────┘                       │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                    │                                        │
│                                    ▼                                        │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │                        applyOrdering()                               │   │
│  │              (alphabetical sort, idOnTop positioning)                │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                    │                                        │
│                                    ▼                                        │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │                     SerializationState                               │   │
│  │                   (value caching per EObject)                        │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                    │                                        │
│                                    ▼                                        │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │              for each entry: shouldSerialize() → serialize()         │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────────────────┘
```

### 23.2 Core Classes

#### 23.2.1 SerializationEntry Interface

The fundamental abstraction for serialization concerns:

```java
public interface SerializationEntry {
    /**
     * Returns the JSON property key for this entry.
     */
    String getKey();

    /**
     * Serializes this entry's value to the JSON output.
     *
     * @param state the serialization state with cached values
     * @param gen the Jackson JSON generator
     * @param ctxt the serialization context
     */
    void serialize(SerializationState state, JsonGenerator gen, SerializationContext ctxt);

    /**
     * Determines if this entry should be serialized.
     * Use for conditional serialization (null checks, config flags, etc.)
     *
     * @param state the serialization state
     * @return true if this entry should be written to the output
     */
    default boolean shouldSerialize(SerializationState state) {
        return true;
    }
}
```

#### 23.2.2 SerializationEntry Implementations

| Class | Responsibility | Key |
|-------|----------------|-----|
| `IdSerializationEntry` | Serializes object ID (from ID attribute or resource URI fragment) | `_id` (configurable) |
| `TypeSerializationEntry` | Serializes EClass type information | `_type` (configurable) |
| `SuperTypeSerializationEntry` | Serializes supertype information | `_supertype` (configurable) |
| `AttributeSerializationEntry` | Serializes EAttribute values | Feature name (configurable) |
| `ReferenceSerializationEntry` | Serializes EReference values (containment inline, non-containment as refs) | Feature name (configurable) |

#### 23.2.3 SerializationState

Provides value caching to avoid duplicate `eGet()` calls between `shouldSerialize()` and `serialize()`:

```java
public class SerializationState {
    private final EObject eObject;
    private final Map<EStructuralFeature, Object> valueCache;

    public SerializationState(EObject eObject) {
        this.eObject = Objects.requireNonNull(eObject);
        this.valueCache = new HashMap<>();
    }

    /**
     * Gets the value of a feature, using cache if available.
     * First call invokes eGet() and caches; subsequent calls return cached value.
     */
    public Object getValue(EStructuralFeature feature) {
        if (valueCache.containsKey(feature)) {
            return cached value (handles null via marker);
        }
        Object value = eObject.eGet(feature);
        valueCache.put(feature, value);
        return value;
    }

    public EObject getEObject() { return eObject; }
    public boolean isCached(EStructuralFeature feature) { ... }
    public void clearCache() { ... }
}
```

**Benefits:**
- Avoids duplicate `eGet()` calls (performance for derived/computed features)
- Single source of truth for feature values during serialization
- Handles proxy resolution only once

#### 23.2.4 FeatureKeyResolver

Centralizes all JSON key resolution logic:

```java
public class FeatureKeyResolver {
    private final CodecModule codecModule;

    // Feature key resolution (aspect → ExtendedMetaData → feature name)
    public String resolveFeatureKey(EStructuralFeature feature, FeatureCodecAspect aspect);

    // ID key resolution (aspect config → module default)
    public String resolveIdKey(ClassCodecAspect aspect);

    // Type key resolution (aspect config → module default)
    public String resolveTypeKey(ClassCodecAspect aspect);

    // Type value resolution (discriminator → EClass URI)
    public String resolveTypeValue(EClass eClass, ClassCodecAspect aspect);

    // SuperType key resolution (aspect config → module default)
    public String resolveSuperTypeKey(ClassCodecAspect aspect);

    // URI generation
    public String getEClassUri(EClass eClass);      // nsURI#//className
    public String getReferenceUri(EObject target);  // resourceURI#fragment or #className
}
```

**Resolution Order (per spec 1.1):**
1. Aspect configuration (highest priority)
2. ExtendedMetaData (for feature keys)
3. Module defaults (lowest priority)

### 23.3 Serialization Flow

The complete serialization flow for an EObject:

```
serialize(EObject value, JsonGenerator gen, SerializationContext ctxt)
│
├─1─► Null check → write null and return if value is null
│
├─2─► Update EMFCodecWriteContext if available
│     └─► emfContext.setCurrentEObject(value)
│
├─3─► Get EClass and metadata
│     ├─► eClass = value.eClass()
│     ├─► classMetadata = metadataService.getClassMetadata(eClass)
│     └─► classAspect = extract ClassCodecAspect from metadata
│
├─4─► buildSerializationEntries()
│     │
│     ├─► if (useId) → add IdSerializationEntry
│     ├─► if (serializeType) → add TypeSerializationEntry
│     ├─► if (serializeSuperTypes) → add SuperTypeSerializationEntry
│     │
│     └─► for each EStructuralFeature in eClass:
│         ├─► get FeatureMetadata and FeatureCodecAspect
│         ├─► if EAttribute → add AttributeSerializationEntry
│         └─► if EReference → add ReferenceSerializationEntry
│
├─5─► applyOrdering(entries)
│     ├─► if (sortAlphabetically) → TreeMap sort
│     └─► if (idOnTop) → move ID entry to first position
│
├─6─► Create SerializationState(value)
│     └─► Initializes empty value cache
│
├─7─► gen.writeStartObject(value)
│
├─8─► for each entry in ordered entries:
│     ├─► if entry.shouldSerialize(state):
│     │   └─► entry.serialize(state, gen, ctxt)
│     └─► else: skip this entry
│
└─9─► gen.writeEndObject()
```

### 23.4 Entry Serialization Details

#### IdSerializationEntry Flow

```
shouldSerialize(state):
├─► if (!useId) → return false
├─► Find ID attribute (eIDAttribute or first with isID=true)
├─► if (idAttribute exists):
│   └─► return state.getValue(idAttribute) != null
└─► else: return eResource != null (use URI fragment)

serialize(state, gen, ctxt):
├─► if (idAttribute exists):
│   └─► gen.writeStringProperty(key, state.getValue(idAttribute))
└─► else:
    └─► gen.writeStringProperty(key, resource.getURIFragment(eObject))
```

#### AttributeSerializationEntry Flow

```
shouldSerialize(state):
├─► if (globallyIgnored(name)) → return false
├─► if (derived || transient) → return false
├─► if (aspect.serialize == false) → return false
├─► value = state.getValue(attribute)
├─► if (value == null) → return shouldSerializeNull()
├─► if (isMany && empty) → return shouldSerializeEmpty()
└─► return true

serialize(state, gen, ctxt):
├─► value = state.getValue(attribute)  // uses cache
├─► if (value == null):
│   └─► gen.writeNullProperty(key)
├─► else if (isMany):
│   └─► gen.writeArray(key, values)
└─► else:
    └─► gen.writeProperty(key, value)
```

#### ReferenceSerializationEntry Flow

```
shouldSerialize(state):
├─► if (globallyIgnored(name)) → return false
├─► if (derived || transient) → return false
├─► if (aspect.serialize == false) → return false
├─► value = state.getValue(reference)
├─► if (value == null) → return shouldSerializeNull()
├─► if (isMany && empty) → return shouldSerializeEmpty()
└─► return true

serialize(state, gen, ctxt):
├─► value = state.getValue(reference)  // uses cache
├─► if (isMany):
│   ├─► gen.writeName(key)
│   ├─► gen.writeStartArray()
│   ├─► for each target in list:
│   │   └─► serializeReference(target, gen, ctxt)
│   └─► gen.writeEndArray()
└─► else:
    ├─► gen.writeName(key)
    └─► serializeReference(target, gen, ctxt)

serializeReference(target, gen, ctxt):
├─► if (containment):
│   └─► ctxt.writeValue(gen, target)  // inline serialization
└─► else:
    ├─► gen.writeStartObject()
    ├─► gen.writeStringProperty(refKey, getReferenceUri(target))
    └─► gen.writeEndObject()
```

### 23.5 Configuration Integration

Configuration flows through `CodecModule` which wraps `CodecConfiguration`:

```
CodecModule
├─► CodecConfiguration (immutable, builder pattern)
│   ├─► useId, idOnTop, idKey
│   ├─► serializeType, typeKey
│   ├─► serializeSuperTypes, superTypeKey
│   ├─► serializeNullValue, serializeEmptyValue
│   ├─► sortPropertiesAlphabetically
│   ├─► globalIgnoreFeatureNames
│   └─► ... other settings
│
├─► MetadataService (aspect-based metadata)
│   ├─► ClassMetadata → ClassCodecAspect
│   └─► FeatureMetadata → FeatureCodecAspect
│
└─► CodecValueRegistry (custom value writers/readers)
```

**Configuration Resolution (per spec 1.1):**

Each setting is resolved in priority order:
1. Aspect configuration (from MetadataService)
2. CodecConfiguration settings
3. Built-in defaults

Example for ID key:
```java
// In FeatureKeyResolver
public String resolveIdKey(ClassCodecAspect classAspect) {
    // 1. Check aspect config
    if (classAspect != null) {
        IdSerializationConfig config = classAspect.getIdConfig();
        if (config != null && isNonEmpty(config.getIdKey())) {
            return config.getIdKey();
        }
    }
    // 2. Fall back to module default
    return codecModule.getIdKey();  // defaults to "_id"
}
```

### 23.6 Package Structure

```
org.eclipse.fennec.codec.v2
├── config/
│   ├── CodecConfiguration.java      # Immutable configuration holder
│   └── effective/                   # Pre-resolved effective configs
│       ├── ConfigurationMerger.java     # Merges all config sources
│       ├── EffectiveCodecConfig.java    # Top-level config with caching
│       ├── EffectiveClassConfig.java    # Per-EClass config
│       ├── EffectiveIdConfig.java       # Resolved ID settings
│       ├── EffectiveTypeConfig.java     # Resolved type settings
│       ├── EffectiveSuperTypeConfig.java # Resolved supertype settings
│       └── EffectiveFeatureConfig.java  # Per-feature config
│
├── context/
│   └── EMFCodecWriteContext.java    # Jackson write context extension
│
├── module/
│   ├── CodecModule.java             # Jackson SimpleModule extension
│   └── CodecModuleBuilder.java      # Builder for CodecModule
│
├── ser/
│   ├── CodecEObjectSerializer.java  # Main EObject serializer (orchestrator)
│   ├── CodecSerializers.java        # Jackson Serializers provider
│   │
│   ├── SerializationEntry.java      # Entry interface
│   ├── SerializationState.java      # Value caching state holder
│   ├── FeatureKeyResolver.java      # Centralized key resolution
│   │
│   ├── IdSerializationEntry.java    # ID field serialization
│   ├── TypeSerializationEntry.java  # Type information serialization
│   ├── SuperTypeSerializationEntry.java  # Supertype serialization
│   ├── AttributeSerializationEntry.java  # EAttribute serialization
│   └── ReferenceSerializationEntry.java  # EReference serialization
│
├── deser/
│   └── (TODO: deserializers)
│
└── value/
    ├── CodecValueRegistry.java      # Custom value reader/writer registry
    ├── CodecValueWriter.java        # Value writer interface
    └── CodecValueReader.java        # Value reader interface
```

### 23.7 Comparison with V1 Architecture

| Aspect | V1 | V2 |
|--------|-----|-----|
| **Pattern** | Monolithic serializer creates sub-serializers | Entry-based delegation pattern |
| **Metadata** | `CodecModelInfo` with `EClassCodecInfo` | `MetadataService` with pluggable aspects |
| **Caching** | No value caching | `SerializationState` caches feature values |
| **Key Resolution** | Scattered across serializers | Centralized `FeatureKeyResolver` |
| **Configuration** | Direct properties in `CodecModule` | Structured `CodecConfiguration` with builder |
| **Testability** | Hard to test individual concerns | Each entry independently unit testable |
| **Extensibility** | Requires modifying serializer | Add new `SerializationEntry` implementation |

### 23.8 Test Coverage

Each component has dedicated unit tests:

| Class | Test Class | Test Count |
|-------|------------|------------|
| `SerializationState` | `SerializationStateTest` | 17 |
| `FeatureKeyResolver` | `FeatureKeyResolverTest` | 22 |
| `IdSerializationEntry` | `IdSerializationEntryTest` | 8 |
| `TypeSerializationEntry` | `TypeSerializationEntryTest` | 10 |
| `SuperTypeSerializationEntry` | `SuperTypeSerializationEntryTest` | 10 |
| `AttributeSerializationEntry` | `AttributeSerializationEntryTest` | 12 |
| `ReferenceSerializationEntry` | `ReferenceSerializationEntryTest` | 14 |

All tests use JUnit 5 with Mockito for mocking Jackson generators and real EMF objects created from dynamically loaded ecore models (via `EcoreHelper`).

### 23.9 Effective Configuration Pattern

The codec.v2 serialization architecture uses an **Effective Configuration** pattern that pre-merges all configuration sources into immutable, cached configuration objects. This eliminates runtime resolution overhead and ensures consistent configuration throughout serialization.

#### 23.9.1 Design Goals

1. **Pre-computed Resolution**: Merge all config sources once at module setup, not during serialization
2. **Immutability**: Effective configs are final and thread-safe
3. **Caching**: Class and feature configs are cached per EClass for reuse
4. **Single Responsibility**: Each entry receives only the config it needs

#### 23.9.2 Effective Configuration Classes

```
EffectiveCodecConfig (top-level, caches all class/feature configs)
├── EffectiveClassConfig (per EClass)
│   ├── EffectiveIdConfig
│   ├── EffectiveTypeConfig
│   └── EffectiveSuperTypeConfig
└── EffectiveFeatureConfig (per EStructuralFeature)
```

**EffectiveIdConfig** - Resolved ID serialization settings:
```java
public final class EffectiveIdConfig {
    private final boolean enabled;       // Whether to serialize ID
    private final String key;            // JSON key (e.g., "_id")
    private final boolean onTop;         // Place ID first in output
    private final IdStrategy strategy;   // ID_FIELD, URI_FRAGMENT, etc.
}
```

**EffectiveTypeConfig** - Resolved type serialization settings:
```java
public final class EffectiveTypeConfig {
    private final EClass eClass;              // The EClass this applies to
    private final boolean enabled;            // Whether to serialize type
    private final String key;                 // JSON key (e.g., "_type")
    private final SerializationFormat format; // PLAIN or STRUCTURED
    private final String discriminator;       // Custom type name override
}
```

**EffectiveSuperTypeConfig** - Resolved supertype serialization settings:
```java
public final class EffectiveSuperTypeConfig {
    private final boolean enabled;            // Whether to serialize supertypes
    private final String superTypeKey;        // JSON key (e.g., "_supertype")
    private final SuperTypeSelection selection; // ALL, SINGLE, ALL_EMF, NONE
}
```

**EffectiveClassConfig** - Combines ID, type, and supertype configs:
```java
public final class EffectiveClassConfig {
    private final EClass eClass;
    private final EffectiveIdConfig idConfig;
    private final EffectiveTypeConfig typeConfig;
    private final EffectiveSuperTypeConfig superTypeConfig;
}
```

**EffectiveFeatureConfig** - Resolved feature-level settings:
```java
public final class EffectiveFeatureConfig {
    private final EStructuralFeature feature;
    private final String key;                // JSON property name
    private final boolean serialize;         // false = transient
    private final boolean serializeNull;     // Include null values
    private final boolean serializeEmpty;    // Include empty collections
    private final boolean serializeDefaults; // Include default values
    private final String valueWriterName;    // Custom writer reference
    private final String valueReaderName;    // Custom reader reference
}
```

#### 23.9.3 ConfigurationMerger

The `ConfigurationMerger` merges all configuration sources into effective configs:

```java
public class ConfigurationMerger {
    private final CodecModule codecModule;
    private final MetadataService metadataService;
    private final Map<String, Object> options;  // Load/save options

    /**
     * Creates an effective class config by merging:
     * 1. Load/save options (highest priority)
     * 2. ResourceFactory defaults
     * 3. Module configuration
     * 4. Model aspects (from MetadataService)
     * 5. Built-in defaults (lowest priority)
     */
    public EffectiveClassConfig createClassConfig(EClass eClass) {
        ClassCodecAspect aspect = getClassAspect(eClass);
        return EffectiveClassConfig.builder()
            .eClass(eClass)
            .idConfig(mergeIdConfig(eClass, aspect))
            .typeConfig(mergeTypeConfig(eClass, aspect))
            .superTypeConfig(mergeSuperTypeConfig(eClass, aspect))
            .build();
    }

    public EffectiveFeatureConfig createFeatureConfig(EStructuralFeature feature) {
        FeatureCodecAspect aspect = getFeatureAspect(feature);
        return EffectiveFeatureConfig.builder()
            .feature(feature)
            .key(resolveKey(feature, aspect))
            .serialize(resolveSerialize(feature, aspect))
            .serializeNull(resolveSerializeNull(aspect))
            .serializeEmpty(resolveSerializeEmpty(aspect))
            .serializeDefaults(resolveSerializeDefaults(aspect))
            .valueWriterName(aspect != null ? aspect.getValueWriterName() : null)
            .valueReaderName(aspect != null ? aspect.getValueReaderName() : null)
            .build();
    }
}
```

#### 23.9.4 EffectiveCodecConfig

The top-level config holder with caching:

```java
public class EffectiveCodecConfig {
    private final ConfigurationMerger merger;
    private final ConcurrentMap<EClass, EffectiveClassConfig> classCache;
    private final ConcurrentMap<EStructuralFeature, EffectiveFeatureConfig> featureCache;

    public EffectiveClassConfig getClassConfig(EClass eClass) {
        return classCache.computeIfAbsent(eClass, merger::createClassConfig);
    }

    public EffectiveFeatureConfig getFeatureConfig(EStructuralFeature feature) {
        return featureCache.computeIfAbsent(feature, merger::createFeatureConfig);
    }
}
```

#### 23.9.5 Usage in Serialization Entries

Each entry now receives effective config instead of CodecModule:

```java
// Old approach (resolves at serialization time)
public class IdSerializationEntry implements SerializationEntry {
    private final CodecModule codecModule;
    private final ClassCodecAspect aspect;

    public void serialize(...) {
        // Resolve key at serialization time
        String key = resolveIdKey(aspect, codecModule);
        // ...
    }
}

// New approach (pre-resolved effective config)
public class IdSerializationEntry implements SerializationEntry {
    private final EffectiveIdConfig config;

    public void serialize(...) {
        // Key is pre-resolved
        gen.writeStringProperty(config.getKey(), ...);
    }
}
```

#### 23.9.6 Benefits

| Aspect | Without Effective Config | With Effective Config |
|--------|-------------------------|----------------------|
| **Resolution** | Every serialize() resolves config | Resolved once at setup |
| **Thread Safety** | Mutable state concerns | Immutable, thread-safe |
| **Testability** | Need full CodecModule | Just need effective config |
| **Memory** | No caching | Cached per EClass/feature |
| **Debugging** | Hard to see resolved values | Config is explicit |

#### 23.9.7 Integration with CodecModule

```java
public class CodecModule extends SimpleModule {
    private EffectiveCodecConfig effectiveConfig;

    @Override
    public void setupModule(SetupContext context) {
        // Create effective config during module setup
        ConfigurationMerger merger = new ConfigurationMerger(this, metadataService, options);
        this.effectiveConfig = new EffectiveCodecConfig(merger);

        // Pass effective config to serializers
        context.addSerializers(new CodecSerializers(effectiveConfig));
    }
}
