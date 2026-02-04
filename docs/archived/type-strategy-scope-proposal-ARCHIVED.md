# Type Strategy Scope Proposal - Working Document

**Status:** DRAFT - Under refinement
**Created:** 2026-01-20
**Last Discussion:** 2026-01-20 (Type+SuperType unified model, separate scopes for strategy/format)

---

## 1. Problem Statement

Current implementation has type strategy configured per-EClass only. There's no concept of:
- Applying a strategy to all objects vs root only
- Per-reference override of type strategy
- Unified configuration architecture across all codec features

Real-world JSON formats often have different type conventions at different levels (root vs nested, containment vs non-containment).

---

## 2. Proposed Solution: Unified Configuration Architecture

### 2.1 Configuration Hierarchy

All codec features share the same configuration hierarchy (most specific wins):

```
EReference → EClass → Global (with Scope)
```

| Level | Scope | Overrides |
|-------|-------|-----------|
| 1. **EReference** | Single reference | EClass config |
| 2. **EClass** | All instances of that class | Global config |
| 3. **Global** | Entire codec (with scope control) | Built-in default |

### 2.2 Scope Options

```java
enum StrategyScope {
    ALL,                  // Root + all containments + all non-containments (DEFAULT)
    ROOT_ONLY,            // Root object only, children use defaults
    ROOT_CONTAINMENT,     // Root + containment references (ref.isContainment=true)
    ROOT_NON_CONTAINMENT  // Root + non-containment references (ref.isContainment=false)
}
```

**Scope interpretation:**
- `ALL` = applies everywhere
- `ROOT_ONLY` = applies to root object only
- `ROOT_CONTAINMENT` = applies to root OR when serializing via a containment reference
- `ROOT_NON_CONTAINMENT` = applies to root OR when serializing via a non-containment reference

**Important:** Scope is configurable at **global level only** (not per-class or per-reference).

### 2.3 Separate Scopes for Strategy and Format

Strategy and Format are **independent configurations** with their own scopes:

| Configuration | What it controls | Scope Property |
|---------------|------------------|----------------|
| `typeStrategy` | HOW to identify the type (URI, NAME, SCHEMA_AND_TYPE, etc.) | `typeScope` |
| `typeFormat` | WHERE to put type info (PLAIN = field, STRUCTURED = nested object) | `typeFormatScope` |
| `idStrategy` | HOW to build ID (ID_FIELD, COMBINED, NONE) | `idScope` |
| `idFormat` | WHERE to put ID info (PLAIN = field, STRUCTURED = nested object) | `idFormatScope` |

**Why separate scopes?**

This enables powerful combinations:

```java
ConfigBuilder
    .typeStrategy(SCHEMA_AND_TYPE)
    .typeScope(ROOT_ONLY)              // Strategy only at root
    .typeFormat(STRUCTURED)
    .typeFormatScope(ROOT_CONTAINMENT) // Format at root + containments
    .build();
```

**Result:**

| Object Level | Strategy | Format | Source |
|--------------|----------|--------|--------|
| Root | SCHEMA_AND_TYPE | STRUCTURED | global config |
| Containment | URI (fallback) | STRUCTURED | format scope includes containment |
| Non-containment | URI (fallback) | PLAIN (fallback) | both scopes exclude non-containment |

With a single bound scope, this flexibility would not be possible.

### 2.4 Default Behavior (Zero Configuration)

```java
// Implicit defaults - no configuration needed
typeStrategy = URI
typeScope = ALL
typeFormat = PLAIN
typeFormatScope = ALL
typeKey = "_type"
typeNameKey = "type"      // Inner key in STRUCTURED format
typeSchemaKey = "schema"  // Schema key in STRUCTURED format
```

This means out-of-the-box behavior applies the strategy and format to all objects uniformly.

---

## 3. Features Using This Architecture

### 3.1 Feature Applicability Matrix

| Feature | EReference | EClass | Global | Scope Property | Notes |
|---------|------------|--------|--------|----------------|-------|
| **Type Configuration** |
| `typeStrategy` | Yes | Yes | Yes | `typeScope` | |
| `typeFormat` | Yes | Yes | Yes | `typeFormatScope` | Independent from typeScope |
| `typeKey` | Yes | Yes | Yes | - | Outer key (default: `_type`) |
| `typeNameKey` | Yes | Yes | Yes | - | Inner key in STRUCTURED (default: `type`) |
| `typeSchemaKey` | Yes | Yes | Yes | - | Schema key in STRUCTURED (default: `schema`) |
| **SuperType Configuration (Extension of Type)** |
| `superTypeEnabled` | **No** | Yes | Yes | (follows `typeScope`) | See Section 3.3 |
| `superTypeKey` | **No** | Yes | Yes | - | Default: `_supertype` (PLAIN) / `supertype` (STRUCTURED) |
| `superTypeSelection` | **No** | Yes | Yes | - | ALL, SINGLE, NONE |
| `superTypePresentation` | **No** | Yes | Yes | - | ARRAY, STRING (default: ARRAY) |
| `superTypeSeparator` | **No** | Yes | Yes | - | Separator for STRING (default: `,`) |
| **ID Configuration** |
| `idStrategy` | **No** | Yes | Yes | `idScope` | EClass-level only |
| `idFormat` | Yes | Yes | Yes | `idFormatScope` | |
| `idKey` | Yes | Yes | Yes | - | Outer key (default: `_id`) |
| `idNameKey` | Yes | Yes | Yes | - | Inner key in STRUCTURED |
| `idSeparator` | **No** | Yes | Yes | - | For combined IDs |
| **Reference Configuration** |
| `referenceFormat` | Yes | **No** | Yes | - | PLAIN, STRUCTURED |
| `referenceExpand` | Yes | **No** | Yes | - | |
| `referenceRefKey` | Yes | **No** | Yes | - | Default: `_ref` |

### 3.2 Validation Rules

**Errors for misplaced configuration:**
- `codec.supertype.*` on EReference → ERROR (see Section 3.3 for rationale)
- `codec.id.strategy` on EReference → ERROR
- `codec.id.separator` on EReference → ERROR
- `codec.reference.*` on EClass → ERROR
- `codec.type.scope` / `codec.type.formatScope` on EClass or EReference → ERROR (global only)
- `codec.id.scope` / `codec.id.formatScope` on EClass or EReference → ERROR (global only)

### 3.3 SuperType as Extension of Type

SuperType is NOT an independent feature - it's an **extension of Type** that describes the inheritance hierarchy of an EClass.

**Inherited from Type (not configurable separately for SuperType):**

| Type Property | SuperType Behavior |
|---------------|-------------------|
| `typeScope` | SuperType uses same scope |
| `typeFormat` | SuperType uses same format (PLAIN or STRUCTURED) |
| `typeFormatScope` | SuperType uses same format scope |
| `typeStrategy` | SuperType uses compatible subset (URI, NAME, SCHEMA_AND_TYPE) |

**SuperType Strategy Compatibility:**

| Type Strategy | SuperType Behavior |
|---------------|-------------------|
| URI | SuperType as URI(s) |
| NAME | SuperType as simple name(s) |
| SCHEMA_AND_TYPE | SuperType as name(s) within schema context |
| MAPPED | Falls back to URI |
| NUMERIC | Falls back to URI |
| NONE | SuperType disabled (no type info = no supertype info) |

### 3.4 Why SuperType is NOT Configurable at EReference Level

**Decision:** SuperType configuration is available at Global and EClass level only, NOT at EReference level.

**Rationale:**

1. **SuperType describes the CLASS, not the reference** - Supertypes describe the inheritance hierarchy of the EClass itself. A `Person` has the same supertypes whether accessed via `company.employees` or `order.customer`.

2. **Semantic mismatch** - Unlike `typeKey` (presentation: "how to name the field"), `superTypeEnabled` is about content ("what information to include"). Content should be consistent for a class regardless of how it's accessed.

3. **Complexity reduction** - Adding SuperType config at reference level increases configuration complexity without clear use cases.

4. **Contrast with Type config at reference level:**
   - `typeKey` on reference = "how to name the type field when serializing via this reference" → presentation concern, varies by context
   - `superTypeEnabled` on reference = "whether to include supertypes when serializing via this reference" → content concern, should be consistent

**If a use case emerges** where SuperType config at reference level is needed, this decision can be revisited. Document the use case and add to this section.

---

## 4. Configuration Methods

### 4.1 Annotation Syntax (one key per detail)

**EClass level:**
```xml
<eClassifiers xsi:type="ecore:EClass" name="Person">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="codec.type.strategy" value="NAME"/>
    <details key="codec.type.key" value="_type"/>
  </eAnnotations>
</eClassifiers>
```

**EReference level:**
```xml
<eStructuralFeatures xsi:type="ecore:EReference" name="externalCustomer">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="codec.type.strategy" value="URI"/>
    <details key="codec.type.key" value="@type"/>
    <details key="codec.reference.expand" value="true"/>
  </eAnnotations>
</eStructuralFeatures>
```

### 4.2 Config Builder

```java
CodecConfiguration.builder()
    // Global defaults (scope ALL is implicit default)
    .typeStrategy(TypeStrategy.URI)
    .typeKey("_type")

    // Change scope for type strategy (global only)
    .typeStrategyScope(StrategyScope.ROOT_CONTAINMENT)

    // Per-class override
    .forClass(PersonPackage.Literals.PERSON)
        .typeStrategy(TypeStrategy.NAME)

    // Per-reference override
    .forReference(CompanyPackage.Literals.COMPANY__EXTERNAL_CUSTOMER)
        .typeStrategy(TypeStrategy.URI)
        .typeKey("@type")
        .expand(true)

    .build();
```

### 4.3 Property Map

```java
Map<String, Object> options = Map.of(
    // Global
    "codec.type.strategy", "URI",
    "codec.type.key", "_type",
    "codec.type.scope", "ROOT_CONTAINMENT",

    // Per-class (qualified name pattern)
    "Person.codec.type.strategy", "NAME",

    // Per-reference (qualified name pattern)
    "Company.externalCustomer.codec.type.strategy", "URI",
    "Company.externalCustomer.codec.type.key", "@type",
    "Company.externalCustomer.codec.reference.expand", "true"
);
```

---

## 5. Resolution Algorithm

```
resolveConfig(property, EClass eClass, EReference ref):

    1. If ref != null AND ref has codec.{property} set
       → return reference config value

    2. Else if eClass has codec.{property} set
       → return class config value

    3. Else if global has codec.{property} set:
       - Get the scope for this property (e.g., typeScope for typeStrategy)
       - If scope == ALL
           → return global config value
       - If scope == ROOT_ONLY AND isRoot
           → return global config value
       - If scope == ROOT_CONTAINMENT AND (isRoot OR ref.isContainment())
           → return global config value
       - If scope == ROOT_NON_CONTAINMENT AND (isRoot OR !ref.isContainment())
           → return global config value
       - Otherwise (scope excludes this context)
           → continue to step 4

    4. Return built-in default
```

**Key:** Always fall through the full chain (Reference → EClass → Global → Default). Never stop early.

---

## 6. Annotation Key Registry

| Annotation Key | Applies To | Type | Default |
|----------------|------------|------|---------|
| **Type Configuration** |
| `codec.type.strategy` | EClass, EReference | TypeStrategy | URI |

**TypeStrategy enum values:**
- `URI` - Full EClass URI (e.g., `http://example.org#//Person`)
- `NAME` - Simple class name (e.g., `Person`) - requires schema context
- `SCHEMA_AND_TYPE` - Separate schema and type fields
- `NUMERIC` - Classifier ID number
- `NONE` - No type information written/expected

**Note:** `MAPPED` was removed from TypeStrategy. Discriminator mapping is now a separate optional layer. See Section 11.8.
| `codec.type.format` | EClass, EReference | SerializationFormat | PLAIN |
| `codec.type.key` | EClass, EReference | String | "_type" |
| `codec.type.nameKey` | EClass, EReference | String | "type" |
| `codec.type.schemaKey` | EClass, EReference | String | "schema" |
| `codec.type.scope` | **Global only** | StrategyScope | ALL |
| `codec.type.formatScope` | **Global only** | StrategyScope | ALL |
| **SuperType Configuration (Extension of Type)** |
| `codec.supertype.enabled` | EClass | boolean | false |
| `codec.supertype.key` | EClass | String | "_supertype" (PLAIN) / "supertype" (STRUCTURED) |
| `codec.supertype.selection` | EClass | SuperTypeSelection | ALL |
| `codec.supertype.presentation` | EClass | SuperTypePresentation | ARRAY |
| `codec.supertype.separator` | EClass | String | "," |
| **ID Configuration** |
| `codec.id.strategy` | EClass | IdStrategy | ID_FIELD |
| `codec.id.format` | EClass, EReference | SerializationFormat | PLAIN |
| `codec.id.key` | EClass, EReference | String | "_id" |
| `codec.id.nameKey` | EClass, EReference | String | (feature name) |
| `codec.id.separator` | EClass | String | "/" |
| `codec.id.keyMode` | EClass | IdKeyMode | ID_ONLY |
| `codec.id.scope` | **Global only** | StrategyScope | ALL |
| `codec.id.formatScope` | **Global only** | StrategyScope | ALL |
| **Reference Configuration** |
| `codec.reference.format` | EReference | SerializationFormat | PLAIN |
| `codec.reference.expand` | EReference | boolean | false |
| `codec.reference.refKey` | EReference | String | "_ref" |

---

## 7. The Underscore (`_`) Convention

The underscore prefix distinguishes **metadata fields** from **data fields**.

### PLAIN Format

Metadata fields at root level of the object use `_` prefix:

```json
{
  "_schema": "http://example.org",
  "_type": "Person",
  "_supertype": "Human,LivingThing",
  "_id": "123",
  "name": "Claude"
}
```

Here `_type`, `_schema`, `_supertype`, `_id` are metadata; `name` is data.

### STRUCTURED Format

Metadata is in a nested object. Outer key has `_`, inner keys don't:

```json
{
  "_type": {
    "schema": "http://example.org",
    "type": "Person",
    "supertype": ["Human", "LivingThing"]
  },
  "_id": {
    "value": "123"
  },
  "name": "Claude"
}
```

- `_type`, `_id` (outer keys) have `_` because they're at root level among data fields
- `schema`, `type`, `supertype`, `value` (inner keys) don't need `_` because they're inside a metadata object

### Key Configuration by Format

| Key Property | PLAIN Format Default | STRUCTURED Format Default |
|--------------|---------------------|--------------------------|
| `typeKey` | `_type` | `_type` (outer) |
| `typeNameKey` | N/A | `type` (inner) |
| `typeSchemaKey` | `_schema` | `schema` (inner) |
| `superTypeKey` | `_supertype` | `supertype` (inner, within `_type` object) |
| `idKey` | `_id` | `_id` (outer) |
| `idNameKey` | N/A | feature name (inner) |

---

## 8. Examples

### 8.1 Type + SuperType Examples

#### PLAIN + SuperType ARRAY (default presentation)

```json
{
  "_type": "http://example.org#//Person",
  "_supertype": ["http://example.org#//Human", "http://example.org#//LivingThing"],
  "_id": "123",
  "name": "Claude"
}
```

#### PLAIN + SuperType STRING + default separator `,`

```json
{
  "_type": "http://example.org#//Person",
  "_supertype": "http://example.org#//Human,http://example.org#//LivingThing",
  "_id": "123",
  "name": "Claude"
}
```

#### STRUCTURED + SuperType ARRAY

```json
{
  "_type": {
    "schema": "http://example.org",
    "type": "Person",
    "supertype": ["Human", "LivingThing"]
  },
  "_id": "123",
  "name": "Claude"
}
```

#### STRUCTURED + SuperType STRING + separator `:`

```json
{
  "_type": {
    "schema": "http://example.org",
    "type": "Person",
    "supertype": "Human:LivingThing"
  },
  "_id": "123",
  "name": "Claude"
}
```

#### STRUCTURED + NAME strategy + SuperType STRING + default separator

```json
{
  "_type": {
    "type": "Person",
    "supertype": "Human,LivingThing"
  },
  "_id": "123",
  "name": "Claude"
}
```

Note: No `schema` field because NAME strategy doesn't include schema.

### 8.2 Strategy and Format Scope Examples

#### SCHEMA_AND_TYPE for root only (scope = ROOT_ONLY)

```java
CodecConfiguration.builder()
    .typeStrategy(TypeStrategy.SCHEMA_AND_TYPE)
    .typeStrategyScope(StrategyScope.ROOT_ONLY)
    .build();
```

**Output:**
```json
{
  "_schema": "http://example.org",
  "_type": "Company",
  "employees": [
    { "_type": "http://example.org#//Person" }
  ]
}
```
- Root: SCHEMA_AND_TYPE (from global)
- Nested: URI (built-in default, because scope is ROOT_ONLY)

#### NAME strategy for everything (scope = ALL, default)

```java
CodecConfiguration.builder()
    .typeStrategy(TypeStrategy.NAME)
    // .typeStrategyScope(StrategyScope.ALL)  // implicit default
    .build();
```

**Output:**
```json
{
  "_type": "Company",
  "employees": [
    { "_type": "Person" }
  ]
}
```

#### URI globally, but external reference uses different convention

```java
CodecConfiguration.builder()
    .typeStrategy(TypeStrategy.URI)
    .forReference(CompanyPackage.Literals.COMPANY__EXTERNAL_CUSTOMER)
        .typeStrategy(TypeStrategy.NAME)
        .typeKey("@type")
    .build();
```

**Output:**
```json
{
  "_type": "http://example.org#//Company",
  "employees": [
    { "_type": "http://example.org#//Person" }
  ],
  "externalCustomer": {
    "@type": "Customer"
  }
}
```

#### Containment uses NAME, non-containment uses URI

```java
CodecConfiguration.builder()
    .typeStrategy(TypeStrategy.NAME)
    .typeStrategyScope(StrategyScope.ROOT_CONTAINMENT)
    .build();
```

**Output:**
```json
{
  "_type": "Company",
  "departments": [
    { "_type": "Department" }
  ],
  "ceo": {
    "_type": "http://example.org#//Person",
    "_ref": "/persons/123"
  }
}
```
- Root: NAME (global)
- departments (containment): NAME (scope includes containment)
- ceo (non-containment): URI (default, scope excludes non-containment)

#### Separate Strategy Scope and Format Scope

```java
ConfigBuilder
    .typeStrategy(SCHEMA_AND_TYPE)
    .typeScope(ROOT_ONLY)              // Strategy only at root
    .typeFormat(STRUCTURED)
    .typeFormatScope(ROOT_CONTAINMENT) // Format at root + containments
    .build();
```

**Result:**

| Object Level | Strategy | Format |
|--------------|----------|--------|
| Root | SCHEMA_AND_TYPE | STRUCTURED |
| Containment | URI (fallback) | STRUCTURED |
| Non-containment | URI (fallback) | PLAIN (fallback) |

### 8.3 Complex Multi-Level Configuration Example

This example demonstrates the full power of the configuration hierarchy:

**JSON Output:**
```json
{
  "_type": {
    "schema": "http://example.org",
    "type": "Person",
    "supertype": "Human"
  },
  "_id": "123",
  "name": "Claude",
  "address": {
    "myType": "http://example.org#//Address",
    "myId": {
      "addressId": "123"
    },
    "street": "Sesamstreet"
  },
  "bro": {
    "myType": {
      "friendKind": "Friend"
    },
    "friendId": "f2",
    "name": "Ursel"
  },
  "friends": [
    {
      "fType": {
        "friendKind": "Friend"
      },
      "friendId": "f1",
      "name": "Mark"
    },
    {
      "fType": "http://example.org#//BestFriend",
      "friendId": "f2",
      "name": "Jane"
    }
  ]
}
```

**Configuration:**

```java
// EClass configuration for Friend
Config friendClassConfig = ClassConfigBuilder
    .forEClass(ExamplePackage.Literals.FRIEND)
    .typeStrategy(NAME)           // Use NAME strategy for this EClass
    .typeFormat(STRUCTURED)       // Serialize type in STRUCTURED format
    .typeKey("myType")            // Outer type key will be "myType"
    .typeNameKey("friendKind")    // Inner type key will be "friendKind"
    .build();

// EReference configuration for Person.friends
Config friendsRefConfig = ReferenceConfigBuilder
    .forReference(ExamplePackage.Literals.PERSON__FRIENDS)
    .typeKey("fType")             // Custom type key for this reference
    // typeFormat not set → inherits from EClass (STRUCTURED for Friend)
    // typeStrategy not set → inherits from EClass (NAME for Friend)
    .idKey("friendId")            // Custom ID key
    .build();

// EReference configuration for Person.address
Config addrRefConfig = ReferenceConfigBuilder
    .forReference(ExamplePackage.Literals.PERSON__ADDRESS)
    .typeKey("myType")            // Custom type key
    .idFormat(STRUCTURED)         // Serialize ID in STRUCTURED format
    .idKey("myId")                // Outer ID key
    .idNameKey("addressId")       // Inner ID key
    .build();

// Global configuration
CodecConfiguration config = ConfigBuilder
    .typeStrategy(SCHEMA_AND_TYPE)    // Root uses SCHEMA_AND_TYPE
    .typeScope(ROOT_ONLY)             // Only at root
    .typeFormat(STRUCTURED)           // Root uses STRUCTURED format
    .typeFormatScope(ROOT_ONLY)       // Only at root
    // ID format is PLAIN by default, scope ALL by default
    .withClassConfig(friendClassConfig)
    .withReferenceConfig(friendsRefConfig)
    .withReferenceConfig(addrRefConfig)
    .build();
```

**Resolution Flow for `friends[0]` (Friend object via Person.friends reference):**

```
1. Check EReference (Person.friends):
   - typeKey = "fType" ✓ (use this)
   - typeStrategy = not set → check EClass
   - typeFormat = not set → check EClass

2. Check EClass (Friend):
   - typeStrategy = NAME ✓ (use this)
   - typeFormat = STRUCTURED ✓ (use this)
   - typeNameKey = "friendKind" ✓ (use this)

3. Result for Friend in friends list:
   {
     "fType": {                    // from reference config
       "friendKind": "Friend"      // from class config
     },
     "friendId": "f1",             // from reference config
     "name": "Mark"
   }
```

---

## 9. Migration / Transition Strategy

**Current state:** codec.v2 with per-class configuration only

**Options:**
1. **Extend v2** - Add scope + per-reference as backward-compatible additions
   - Pro: No new project
   - Con: May have subtle breaking changes

2. **Create v3** - Clone codec.v2 into codec.v3 with new architecture
   - Pro: Clean break, v2 remains stable
   - Con: More projects to maintain

**Decision:** _To be decided_

**Considerations:**
- How many users currently depend on v2 behavior?
- Is the default behavior (scope=ALL) backward compatible?
- Can we detect and warn about behavior changes?

---

## 10. Resolved Questions

1. ~~Scope at global level only?~~ **YES** - confirmed
2. ~~Scope default is ALL?~~ **YES** - confirmed
3. ~~Document which features make sense at which level?~~ **YES** - with validation errors (Section 3.2)
4. ~~Breaking change handling?~~ **Transition strategy needed** - v2 extend or v3 clone
5. ~~Do we need separate `typeScope` and `typeFormatScope`?~~ **YES** - enables powerful combinations (Section 2.3)
6. ~~Does SuperType follow Type format?~~ **YES** - SuperType is extension of Type, not independent (Section 3.3)
7. ~~SuperType at EReference level?~~ **NO** - SuperType describes the class, not how it's accessed (Section 3.4)
8. ~~SuperType strategy for MAPPED/NUMERIC?~~ **Falls back to URI** - default fallback strategy
9. ~~SuperType presentation options?~~ **ARRAY or STRING** - with configurable separator (Section 3.1)
10. ~~Underscore convention?~~ **PLAIN = `_` prefix, STRUCTURED = no `_` for inner keys** (Section 7)

### Open Questions (to refine)

11. Should `codec.type.scope` affect `codec.type.key` as well, or just strategy?
12. If EReference has `codec.type.key` but no `codec.type.strategy`, does it inherit strategy from EClass/global? **Proposed: YES** - resolution algorithm falls through (Section 5)
13. How does this interact with Smart Compression? (kept separate for now)

---

## 11. Action Items / Reminders

### 10.1 Review Annotation Key Semantics

**Status:** TODO

Review and refine the annotation key naming and semantics in Section 6 (Annotation Key Registry):
- Naming consistency (e.g., `codec.type.strategy` vs `codec.typeStrategy`)
- Grouping logic (by feature: type, id, reference, supertype)
- Value formats and allowed values
- Which keys are required vs optional
- Default value handling when annotation is partial

### 10.2 Review Property Map Semantics

**Status:** TODO

Review and refine the property map key naming in Section 4.3:
- Key naming pattern for global options (e.g., `codec.type.strategy`)
- Key naming pattern for per-class options (e.g., `Person.codec.type.strategy` or `codec.type.strategy.Person`?)
- Key naming pattern for per-reference options (e.g., `Company.externalCustomer.codec.type.strategy`)
- Qualified name resolution (simple name vs full URI vs package-qualified)
- Consistency between annotation keys and property keys
- Type coercion (String to enum, String to boolean, etc.)

### 11.3 TypeStrategy.NONE - No Type Information

**Status:** TO BE IMPLEMENTED

The `NONE` strategy indicates that no type information should be written or expected.

**Serialization with NONE:**
- No type field (`_type`) is written
- Useful for:
  - Homogeneous collections where type is known from context
  - APIs that don't expect type metadata
  - Compact JSON output

**Deserialization with NONE:**
- Deserializer does NOT look for type field in JSON
- Type resolution relies entirely on:
  1. `CODEC_ROOT_TYPE` load option (for root object)
  2. `EReference.getEReferenceType()` (for nested objects)
- **ERROR** if reference type is abstract and no concrete type can be determined

**Example:**

```java
// Configuration
CodecConfiguration config = CodecConfiguration.builder()
    .typeStrategy(TypeStrategy.NONE)
    .build();

// Serialization output - no _type field
{
  "name": "John",
  "age": 30,
  "address": {
    "street": "Main St",
    "city": "Springfield"
  }
}

// Deserialization - MUST provide CODEC_ROOT_TYPE
Map<String, Object> options = new HashMap<>();
options.put(CodecResource.CODEC_ROOT_TYPE, PersonPackage.Literals.PERSON);
resource.load(input, options);
```

**Scope Interaction:**

```java
// NONE only at root, children use URI
CodecConfiguration.builder()
    .typeStrategy(TypeStrategy.NONE)
    .typeScope(StrategyScope.ROOT_ONLY)
    .build();

// Output:
{
  "name": "John",           // Root: no _type (NONE)
  "friends": [
    { "_type": "http://example.org#//Person", "name": "Jane" }  // Nested: URI
  ]
}
```

**Constraints:**
- When `typeStrategy=NONE` and deserializing:
  - Root object: `CODEC_ROOT_TYPE` is REQUIRED
  - Nested via concrete reference: Uses reference type
  - Nested via abstract reference: ERROR (cannot determine concrete type)
- SuperType is automatically disabled when `typeStrategy=NONE`

### 11.4 Rename CODEC_ROOT_OBJECT to CODEC_ROOT_TYPE

**Status:** TODO

Consider renaming for clarity:
- `CODEC_ROOT_OBJECT` → `CODEC_ROOT_TYPE` (better reflects purpose: type hint, not object)
- Ensure backward compatibility or migration path

### 11.5 Detailed Type Resolution for Deserialization

**Status:** TODO - Needs detailed specification

**Key Principle:** Deserialization uses the SAME configuration as serialization. The deserializer doesn't auto-detect the format/strategy - it must be configured.

**Configuration is resolved via hierarchy (same for serialization and deserialization):**
1. Load/Save options (per-operation) - highest priority
2. ResourceFactory defaults (per-factory)
3. Codec module config (per-codec)
4. Configuration properties (external)
5. EAnnotations (per-model)
6. Built-in defaults - lowest priority

**Context Variables (resolved before type resolution):**
- `config.typeStrategy` - from configuration hierarchy
- `config.typeFormat` - from configuration hierarchy
- `config.typeKey` - from configuration hierarchy
- `contextSchemaUri` - from CODEC_ROOT_SCHEMA or CODEC_ROOT_TYPE or content
- `hintEClass` - from CODEC_ROOT_TYPE or containing reference

**Type Resolution Algorithm:**

```
resolveType(parser, config, contextSchemaUri, hintEClass, containingRef):

    // Step 1: Based on configured strategy, read and interpret type value
    switch (config.typeStrategy):

        case NONE:
            // No type field expected - use hints only
            return resolveFromHints(hintEClass, containingRef)

        case URI:
            typeValue = readTypeField(parser, config.typeKey, config.typeFormat)
            if typeValue != null:
                resolvedClass = lookupByURI(typeValue)
                if resolvedClass == null:
                    ERROR: Unknown type URI: typeValue
                return validateAndReturn(resolvedClass, hintEClass)
            return resolveFromHints(hintEClass, containingRef)

        case NAME:
            typeValue = readTypeField(parser, config.typeKey, config.typeFormat)
            if typeValue != null:
                resolvedClass = lookupByNameInSchema(typeValue, contextSchemaUri)
                if resolvedClass == null:
                    // Fallback to hint's package
                    if hintEClass != null:
                        resolvedClass = lookupByNameInSchema(typeValue, hintEClass.getEPackage().getNsURI())
                if resolvedClass == null:
                    ERROR: Cannot resolve type name: typeValue
                return validateAndReturn(resolvedClass, hintEClass)
            return resolveFromHints(hintEClass, containingRef)

        case SCHEMA_AND_TYPE:
            schemaValue = readSchemaField(parser, config.schemaKey, config.typeFormat)
            typeValue = readTypeField(parser, config.typeKey/nameKey, config.typeFormat)
            if typeValue != null:
                effectiveSchema = schemaValue ?? contextSchemaUri
                resolvedClass = lookupByNameInSchema(typeValue, effectiveSchema)
                if resolvedClass == null:
                    ERROR: Cannot resolve type in schema
                return validateAndReturn(resolvedClass, hintEClass)
            return resolveFromHints(hintEClass, containingRef)

        case MAPPED:
            typeValue = readTypeField(parser, config.typeKey, config.typeFormat)
            if typeValue != null:
                resolvedClass = lookupByDiscriminator(typeValue)
                if resolvedClass == null:
                    ERROR: Unknown discriminator value: typeValue
                return validateAndReturn(resolvedClass, hintEClass)
            return resolveFromHints(hintEClass, containingRef)

        case NUMERIC:
            typeValue = readTypeField(parser, config.typeKey, config.typeFormat)
            if typeValue != null:
                classifierId = parseInteger(typeValue)
                resolvedClass = lookupByClassifierId(classifierId, contextSchemaUri)
                if resolvedClass == null:
                    ERROR: Unknown classifier ID: classifierId
                return validateAndReturn(resolvedClass, hintEClass)
            return resolveFromHints(hintEClass, containingRef)


resolveFromHints(hintEClass, containingRef):
    // No type in JSON - use hints
    if hintEClass != null:
        if hintEClass is abstract or interface:
            ERROR: Cannot instantiate abstract type without concrete type in JSON
        return hintEClass

    if containingRef != null:
        refType = containingRef.getEReferenceType()
        if refType is not abstract:
            return refType
        ERROR: Abstract reference type requires type information in JSON

    ERROR: No type information and no CODEC_ROOT_TYPE hint


validateAndReturn(resolvedClass, hintEClass):
    if hintEClass == null:
        return resolvedClass
    if resolvedClass == hintEClass:
        return resolvedClass
    if resolvedClass is subtype of hintEClass:
        return resolvedClass  // Content is more specific, use it
    if resolvedClass is supertype of hintEClass:
        WARNING: Content type is more general than hint
        return resolvedClass  // Still use content
    // Incompatible types
    WARNING: Type collision - CODEC_ROOT_TYPE=%s but content type=%s
    return resolvedClass  // Content wins
```

**Cases That Require CODEC_ROOT_TYPE:**
1. JSON has NO type information at all
2. JSON has abstract type, need concrete type hint
3. User wants to deserialize as different (compatible) type
4. Simple names used but no schema context available

**Cases That Require CODEC_ROOT_SCHEMA:**
1. JSON uses simple type names without `_schema` field
2. Cross-package types with simple names
3. Smart compression output without embedded schema

**Error Cases to Handle:**
1. Simple name but no schema context → ERROR
2. Unknown type URI → ERROR
3. Abstract type without concrete hint → ERROR
4. Type not found in specified schema → ERROR (or WARNING + fallback?)

**Interaction with Smart Compression:**
- Serialization with Smart Compression: writes simple names when same schema
- Deserialization of Smart Compression output: REQUIRES schema context
- If schema context missing: deserialization FAILS for simple names
- Recommendation: When using Smart Compression, always provide CODEC_ROOT_SCHEMA or CODEC_ROOT_TYPE for load

### 11.6 CODEC_ROOT_TYPE and CODEC_ROOT_SCHEMA Semantics

**Status:** TODO - Needs implementation update

#### The Options

| Option | Purpose | Provides |
|--------|---------|----------|
| `CODEC_ROOT_TYPE` | Type hint/override for root object | EClass + implicit schema (from EClass package) |
| `CODEC_ROOT_SCHEMA` | Schema hint/override for type name resolution | Package namespace URI |
| `CODEC_TYPE_MODE` | Controls hint behavior | `HINT` (default) or `OVERRIDE` |

#### Deviation from Configuration Hierarchy

**IMPORTANT:** By default, `CODEC_ROOT_TYPE` and `CODEC_ROOT_SCHEMA` behave as **hints/fallbacks**, NOT as highest-priority overrides.

This is an **intentional deviation** from the standard configuration hierarchy (where Load/Save options have highest priority).

**Rationale:**
- Most common use case is "help deserialize when type info is missing"
- Users expect content type information to be respected
- Forcing override by default would cause unexpected behavior

**Default Behavior (HINT mode):**

```
Type Resolution Priority (when CODEC_TYPE_MODE = HINT, default):
1. Content type information (from JSON _type field) - wins when present
2. CODEC_ROOT_TYPE / CODEC_ROOT_SCHEMA - used as fallback when content has no type
3. Reference type (EReference.getEReferenceType()) - for nested objects
4. ERROR - if no type determinable
```

**Override Behavior (OVERRIDE mode):**

```
Type Resolution Priority (when CODEC_TYPE_MODE = OVERRIDE):
1. CODEC_ROOT_TYPE - always wins (follows strict configuration hierarchy)
2. Content type information - ignored for root object
3. Reference type - for nested objects (CODEC_ROOT_TYPE only affects root)
```

#### Usage Examples

**Example 1: Hint/Fallback (default)**

```java
// JSON has no type info - hint is used
String json = """
    { "name": "John", "age": 30 }
    """;

options.put(CODEC_ROOT_TYPE, PersonPackage.Literals.PERSON);
// Result: Deserializes as Person (hint used because no type in content)
```

```java
// JSON has type info - content wins, hint ignored
String json = """
    { "_type": "http://example.org#//Employee", "name": "John" }
    """;

options.put(CODEC_ROOT_TYPE, PersonPackage.Literals.PERSON);
// Result: Deserializes as Employee (content wins)
// Note: If Employee is subtype of Person, no warning
// Note: If incompatible, WARNING logged but content still wins
```

**Example 2: Override (explicit)**

```java
// Force deserialize as specific type, ignore content type
String json = """
    { "_type": "http://example.org#//OldType", "name": "John" }
    """;

options.put(CODEC_ROOT_TYPE, PersonPackage.Literals.PERSON);
options.put(CODEC_TYPE_MODE, TypeHintMode.OVERRIDE);
// Result: Deserializes as Person (override mode, content type ignored)
```

**Example 3: Schema hint for simple names**

```java
// JSON uses simple names, need schema context
String json = """
    { "_type": "Person", "name": "John" }
    """;

options.put(CODEC_ROOT_SCHEMA, "http://example.org/mymodel");
// Result: Resolves "Person" in the specified schema
```

#### TypeHintMode Enum

```java
public enum TypeHintMode {
    /**
     * Default behavior. CODEC_ROOT_TYPE/SCHEMA are used as fallbacks
     * when content has no type information. Content type wins when present.
     *
     * NOTE: This deviates from standard configuration hierarchy where
     * Load/Save options have highest priority.
     */
    HINT,

    /**
     * Strict configuration hierarchy. CODEC_ROOT_TYPE always wins
     * for root object, ignoring any type information in content.
     */
    OVERRIDE
}
```

#### Schema Resolution Priority

Schema context (for resolving simple type names) follows similar logic:

**HINT mode (default):**
1. Content `_schema` field - wins when present
2. Parsed from content `_type` URI (if full URI)
3. `CODEC_ROOT_SCHEMA` option - fallback
4. Implicit from `CODEC_ROOT_TYPE` EClass package - fallback

**OVERRIDE mode:**
1. `CODEC_ROOT_SCHEMA` option - wins when set
2. Implicit from `CODEC_ROOT_TYPE` - if CODEC_ROOT_SCHEMA not set
3. Content `_schema` / parsed from `_type` - fallback

#### Documentation Note

This behavior MUST be clearly documented in the spec because it violates the general principle that Load/Save options (Level 1) have highest priority. The deviation exists for practical usability reasons.

Suggested spec wording:
```
CODEC_ROOT_TYPE and CODEC_ROOT_SCHEMA are "smart" options that behave
differently from other Load options:

- By default (HINT mode): They act as fallbacks, used only when content
  lacks type information. This allows deserializing JSON that may or may
  not contain type fields.

- With CODEC_TYPE_MODE=OVERRIDE: They follow strict configuration hierarchy,
  always overriding content type information.

This default behavior is intentional - most users expect content type
information to be respected. Use OVERRIDE mode when you need to force
a specific type regardless of content.
```

### 11.7 DeserializationMode - Type Resolution Strictness

**Status:** TO BE IMPLEMENTED

When deserializing JSON, the codec needs to determine the concrete type of each object. The `DeserializationMode` controls how strictly the deserializer follows the configured type strategy.

#### The Problem

Consider deserializing JSON with `typeStrategy=SCHEMA_AND_TYPE`:

```json
{
  "_type": "Person",
  "name": "John"
}
```

The JSON uses a simple name (`Person`) but doesn't include a `_schema` field. Options:

1. **Strict**: Fail immediately - config says SCHEMA_AND_TYPE but no schema found
2. **Lenient**: Try to resolve anyway using available context (CODEC_ROOT_SCHEMA, reference type package)
3. **Auto-detect**: Ignore config, probe JSON structure to determine format

#### DeserializationMode Enum

```java
public enum DeserializationMode {
    /**
     * Strict configuration compliance.
     * - Type field MUST match configured strategy exactly
     * - Missing or malformed type info → ERROR
     * - Use when you need guaranteed format compliance
     */
    STRICT,

    /**
     * Lenient resolution (DEFAULT).
     * - Try configured strategy first
     * - If that fails, try fallback resolution using available context
     * - Enables handling of slight format variations
     * - Use for maximum interoperability
     */
    LENIENT,

    /**
     * Automatic format detection.
     * - Ignores configured strategy
     * - Probes JSON structure to determine type format
     * - Most flexible but least predictable
     * - Use when dealing with unknown/mixed formats
     */
    AUTO_DETECT
}
```

#### Default: LENIENT

**Rationale for LENIENT as default:**

1. **Pragmatic interoperability** - Real-world JSON often has minor variations
2. **Better user experience** - "It just works" in most cases
3. **Explicit strictness** - Users who need guarantees can opt-in to STRICT
4. **Auto-detect available** - Users who need maximum flexibility can opt-in

**Arguments considered for STRICT:**
- Fail-fast catches configuration mismatches early
- More predictable behavior

**Why LENIENT wins:**
- Most users want deserialization to succeed when possible
- STRICT is still available for those who need it
- LENIENT + warnings provides best of both worlds

#### Resolution Behavior by Mode

**STRICT Mode:**

```
resolveType(parser, config):
    typeValue = readTypeField(parser, config.typeKey, config.typeFormat)

    if typeValue == null AND config.typeStrategy != NONE:
        ERROR: Missing type field (config requires typeStrategy=%s)

    resolvedClass = resolveByConfiguredStrategy(typeValue, config)

    if resolvedClass == null:
        ERROR: Cannot resolve type using configured strategy

    return resolvedClass
```

**LENIENT Mode:**

```
resolveType(parser, config, contextSchemaUri, hintEClass, containingRef):
    // Step 1: Try configured strategy
    typeValue = readTypeField(parser, config.typeKey, config.typeFormat)

    if typeValue != null:
        resolvedClass = resolveByConfiguredStrategy(typeValue, config)
        if resolvedClass != null:
            return resolvedClass
        // Strategy failed - try fallbacks

    // Step 2: Fallback resolution
    if typeValue != null:
        // Try alternative interpretations of the type value
        resolvedClass = tryAsURI(typeValue)
        if resolvedClass != null:
            WARNING: Resolved type using URI fallback (config: %s)
            return resolvedClass

        resolvedClass = tryAsNameInContext(typeValue, contextSchemaUri, hintEClass)
        if resolvedClass != null:
            WARNING: Resolved type using context fallback
            return resolvedClass

    // Step 3: Use hints
    return resolveFromHints(hintEClass, containingRef)
```

**AUTO_DETECT Mode:**

```
resolveType(parser, contextSchemaUri, hintEClass, containingRef):
    // Ignore config.typeStrategy - probe JSON structure

    // Check for structured format
    if hasField(parser, "_type") AND isObject(parser.get("_type")):
        structuredType = parseStructuredType(parser)
        return resolveStructuredType(structuredType)

    // Check for plain format
    if hasField(parser, "_type") AND isString(parser.get("_type")):
        typeValue = parser.get("_type")

        // Try as URI first
        if looksLikeURI(typeValue):
            resolvedClass = resolveByURI(typeValue)
            if resolvedClass != null:
                return resolvedClass

        // Try as simple name
        resolvedClass = resolveByName(typeValue, contextSchemaUri)
        if resolvedClass != null:
            return resolvedClass

    // Check for MAPPED-style discriminator
    // ... additional probing logic ...

    // Fallback to hints
    return resolveFromHints(hintEClass, containingRef)
```

#### Configuration

```java
// Via load options
Map<String, Object> options = new HashMap<>();
options.put(CodecResource.DESERIALIZATION_MODE, DeserializationMode.STRICT);

// Via config builder
CodecConfiguration.builder()
    .deserializationMode(DeserializationMode.LENIENT)  // explicit default
    .build();
```

#### Interaction with Other Features

| Feature | STRICT | LENIENT | AUTO_DETECT |
|---------|--------|---------|-------------|
| CODEC_ROOT_TYPE | Fallback only | Fallback + warning | Fallback |
| CODEC_ROOT_SCHEMA | Required for NAME | Fallback if missing | Auto-resolved |
| Missing type field | ERROR | Use hints | Use hints |
| Wrong format | ERROR | Try fallbacks | Probe format |
| SuperType parsing | Must match config | Best effort | Auto-detect |

#### Examples

**Example 1: STRICT fails, LENIENT succeeds**

Config: `typeStrategy=SCHEMA_AND_TYPE`

```json
{ "_type": "Person", "name": "John" }
```

- STRICT: ERROR - no `_schema` field
- LENIENT: Try to resolve "Person" using CODEC_ROOT_SCHEMA or hint package
- AUTO_DETECT: Detect as NAME format, resolve using context

**Example 2: AUTO_DETECT handles mixed formats**

Config: `typeStrategy=URI`

```json
[
  { "_type": "http://example.org#//Person", "name": "John" },
  { "_type": "Employee", "name": "Jane" }
]
```

- STRICT: ERROR on second element - not a URI
- LENIENT: First succeeds, second tries NAME fallback
- AUTO_DETECT: First detected as URI, second detected as NAME

**Example 3: All modes succeed**

Config: `typeStrategy=URI`

```json
{ "_type": "http://example.org#//Person", "name": "John" }
```

- All modes: Success - format matches configuration

#### Warning Messages

LENIENT mode should log warnings when using fallbacks:

```
WARNING: Type resolved via fallback. Config: typeStrategy=SCHEMA_AND_TYPE,
         but type "Person" resolved using CODEC_ROOT_SCHEMA hint.
         Consider updating config or JSON to match.

WARNING: Type field format mismatch. Expected STRUCTURED format,
         found PLAIN. Proceeding with PLAIN format parsing.
```

These warnings help users identify configuration/content mismatches without failing the operation.

### 11.8 Discriminator Mapping - Type Translation Layer

**Status:** CLARIFICATION / REFACTORING NEEDED

#### The Insight: MAPPED is NOT a Strategy

During review, we identified that `MAPPED` is fundamentally different from other TypeStrategy values:

| Aspect | URI, NAME, SCHEMA_AND_TYPE, NUMERIC | MAPPED |
|--------|-------------------------------------|--------|
| **What it describes** | HOW to represent the type (format of value) | HOW to resolve/lookup the type (mapping mechanism) |
| **Serialization output** | Deterministic from EClass | Requires external mapping definition |
| **Deserialization input** | Self-describing (URI resolvable, NAME in schema context) | Requires registry lookup |
| **Standalone?** | Yes - works without additional config | No - needs discriminator definitions |
| **Orthogonal to format?** | Yes (PLAIN/STRUCTURED both valid) | No (only PLAIN makes sense) |

**Key Realization:**
- Other strategies answer: **"What value represents this type?"**
  - URI → `http://example.org#//Person`
  - NAME → `Person`
  - NUMERIC → `3`
- MAPPED answers: **"How do I translate an arbitrary string to a type?"**
  - `"temp-sensor"` → `TemperatureSensor`
  - `"FooBar"` → `Friend`

**MAPPED is not a type representation strategy - it's a type translation/resolution layer.**

#### Decision: Remove MAPPED from TypeStrategy Enum

**TypeStrategy values (after change):**
```java
public enum TypeStrategy {
    URI,            // Full EClass URI
    NAME,           // EClass simple name (requires schema context)
    SCHEMA_AND_TYPE,// Schema URI + type name (two pieces)
    NUMERIC,        // EMF classifier ID
    NONE            // No type information written/expected
}
```

**MAPPED becomes a separate configuration layer: Discriminator Mapping**

#### Discriminator Mapping as Optional Overlay

```
┌─────────────────────────────────────────────────────────┐
│                   Type Configuration                     │
├─────────────────────────────────────────────────────────┤
│  Strategy (HOW to represent):                           │
│    URI | NAME | SCHEMA_AND_TYPE | NUMERIC | NONE        │
│                                                         │
│  Format (WHERE to put):                                 │
│    PLAIN | STRUCTURED                                   │
│                                                         │
│  Discriminator Mapping (OPTIONAL translation layer):    │
│    - Named registry (mapId + featurePath)               │
│    - Inline mapping (per-reference convenience)         │
│    - Has PRIORITY over strategy for type resolution     │
└─────────────────────────────────────────────────────────┘
```

**Discriminator mapping can work WITH any strategy:**

| Combination | Behavior |
|-------------|----------|
| `typeStrategy=NAME` + discriminator mapping | Write simple name, deserialize via mapping lookup first |
| `typeStrategy=NONE` + discriminator mapping | No `_type` field, type from featurePath only |
| `typeStrategy=URI` + discriminator mapping | Mapping has priority, URI as fallback |

#### Named Registry Configuration (Existing)

**Concept:** Named type maps allow multiple discriminator configurations per package.

**EAnnotation - Base class:** defines `mapId` (name) + `discriminatorPath` (featurePath)
```xml
<eClassifiers xsi:type="ecore:EClass" name="UplinkMessage">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="mapId" value="lorawan-devices"/>
    <details key="discriminatorPath" value="info.profileName"/>
  </eAnnotations>
</eClassifiers>
```

**EAnnotation - Concrete classes:** reference `mapId` via `discriminator.{mapId}` key
```xml
<eClassifiers xsi:type="ecore:EClass" name="TemperatureMessage">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="discriminator.lorawan-devices" value="temperature-profile"/>
  </eAnnotations>
</eClassifiers>

<eClassifiers xsi:type="ecore:EClass" name="HumidityMessage">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="discriminator.lorawan-devices" value="humidity-profile"/>
  </eAnnotations>
</eClassifiers>
```

**Config Builder:**
```java
// Define the named map on base class (string-based path)
ClassConfig uplinkConfig = ClassConfigBuilder
    .forEClass(LorawanPackage.Literals.UPLINK_MESSAGE)
    .withDiscriminatorMap("lorawan-devices")
    .withDiscriminatorPath("info.profileName")
    .build();

// Alternative: type-safe path using EStructuralFeatures (varargs)
ClassConfig uplinkConfigTyped = ClassConfigBuilder
    .forEClass(LorawanPackage.Literals.UPLINK_MESSAGE)
    .withDiscriminatorMap("lorawan-devices")
    .withDiscriminatorPath(
        LorawanPackage.Literals.UPLINK_MESSAGE__INFO,
        LorawanPackage.Literals.INFO__PROFILE_NAME)
    .build();

// Register concrete classes with the map
ClassConfig tempConfig = ClassConfigBuilder
    .forEClass(LorawanPackage.Literals.TEMPERATURE_MESSAGE)
    .withDiscriminator("lorawan-devices", "temperature-profile")
    .build();

ClassConfig humidConfig = ClassConfigBuilder
    .forEClass(LorawanPackage.Literals.HUMIDITY_MESSAGE)
    .withDiscriminator("lorawan-devices", "humidity-profile")
    .build();

// Build complete config
CodecConfiguration config = ConfigBuilder
    .withClassConfig(uplinkConfig)
    .withClassConfig(tempConfig)
    .withClassConfig(humidConfig)
    .build();

resource.load(inputStream, Map.of("codecConfig", config));
```

**Pure property-based:** Add mappings at runtime
```java
// Define the map and path
Map<String, Object> uplinkMap = new HashMap<>();
uplinkMap.put("mapId", "lorawan-devices");
uplinkMap.put("discriminatorPath", "info.profileName");

// Define discriminator values for concrete classes
Map<String, Object> tempMap = Map.of("discriminator.lorawan-devices", "temperature-profile");
Map<String, Object> humidMap = Map.of("discriminator.lorawan-devices", "humidity-profile");

// Combine into options
Map<String, Object> options = Map.of(
    "UplinkMessage", uplinkMap,
    "TemperatureMessage", tempMap,
    "HumidityMessage", humidMap
);

resource.load(inputStream, options);
```

**Service role:** `TypeDiscriminatorService` maintains registries per mapId, updates when packages are registered/unregistered from global EPackage registry.

#### Inline Mapping Configuration (NEW - TO BE IMPLEMENTED)

**Use case:** Simple, static discriminator mapping at reference level without requiring `TypeDiscriminatorService`.

**Scenario:** External API uses `"test": "FooBar"` to indicate type. Map this directly in annotation/property.

**EReference annotation:**
```xml
<eStructuralFeatures xsi:type="ecore:EReference" name="partners" upperBound="-1">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="type.key" value="test"/>
    <details key="inlineMapping.FooBar" value="http://example.org#//Friend"/>
    <details key="inlineMapping.BarBaz" value="http://example.org#//Enemy"/>
  </eAnnotations>
</eStructuralFeatures>
```

**Config Builder:**
```java
Config partnersRefConfig = ReferenceConfigBuilder
    .forReference(CompanyPackage.Literals.COMPANY__PARTNERS)
    .typeKey("test")
    .withInlineTypeMapping("FooBar", ExamplePackage.Literals.FRIEND)
    .withInlineTypeMapping("BarBaz", ExamplePackage.Literals.ENEMY)
    .build();

// Option 1: Pass config object via options map
resource.load(inputStream, Map.of(
    "codecConfig", ConfigBuilder.withReferenceConfig(partnersRefConfig).build()
));

// Option 2: Build options map directly from config
resource.load(inputStream, ConfigBuilder.withReferenceConfig(partnersRefConfig).buildMap());
```

**Pure property-based:**
```java
Map<String, EClass> inlineMapping = new HashMap<>();
inlineMapping.put("FooBar", ExamplePackage.Literals.FRIEND);
inlineMapping.put("BarBaz", ExamplePackage.Literals.ENEMY);

Map<String, Object> partnersRefMap = new HashMap<>();
partnersRefMap.put("typeKey", "test");
partnersRefMap.put("typeInlineMapping", inlineMapping);

// Pass as reference-specific options
Map<String, Object> options = Map.of(
    "Company.partners", partnersRefMap
);
resource.load(inputStream, options);
```

**JSON:**
```json
{
  "partners": [
    { "test": "FooBar", "name": "Alice" },
    { "test": "BarBaz", "name": "Bob" }
  ]
}
```

**Deserialization:** First partner → `Friend`, second partner → `Enemy`

**Key differences from named registries:**

| Aspect | Named Registry | Inline Mapping |
|--------|----------------|----------------|
| Scope | Global/EClass | Per-reference |
| Configuration | Requires `TypeDiscriminatorService` | Static in annotation/property |
| Dynamic updates | Yes (packages come/go) | No (static at config time) |
| Feature path | Supports nested paths | Simple type key field |
| Use case | IoT devices, dynamic models | External APIs, simple polymorphism |

#### Resolution Priority

When resolving type during deserialization:

```
1. Discriminator Mapping (if configured)
   a. Inline mapping (per-reference) - highest priority
   b. Named registry lookup (via TypeDiscriminatorService)

2. Type Strategy Resolution
   a. Read type field based on configured strategy
   b. Resolve EClass from type value

3. Fallback
   a. CODEC_ROOT_TYPE hint
   b. Reference type (EReference.getEReferenceType())
   c. ERROR if abstract and no concrete type determinable
```

#### STRUCTURED Format Warning

If discriminator mapping is configured with `typeFormat=STRUCTURED`:
- **WARNING** logged (not error)
- Treated as PLAIN format
- Rationale: Discriminator is a simple string value, nesting adds no value

#### Migration from MAPPED Strategy

Existing code using `TypeStrategy.MAPPED`:

**Before:**
```java
TypeSerializationConfig.builder()
    .strategy(TypeStrategy.MAPPED)
    .discriminatorValue("temp-sensor")
    .build();
```

**After:**
```java
TypeSerializationConfig.builder()
    .strategy(TypeStrategy.NONE)  // or URI/NAME depending on fallback needs
    .discriminatorValue("temp-sensor")
    .mapId("my-devices")
    .build();
```

Or via annotation:
```xml
<!-- Before -->
<details key="codec.type.strategy" value="MAPPED"/>
<details key="codec.type.discriminatorValue" value="temp-sensor"/>

<!-- After -->
<details key="codec.type.strategy" value="NONE"/>
<details key="codec.type.mapId" value="my-devices"/>
<details key="codec.type.discriminatorValue" value="temp-sensor"/>
```

### 11.9 Feature Ignore List

**Status:** TO BE CLARIFIED

Features on the ignore list are intentionally excluded from serialization and deserialization. This is different from strictness mode (11.10) which handles unexpected mismatches.

#### Behavior

| Direction | Behavior |
|-----------|----------|
| **Serialization** | Feature omitted from JSON output |
| **Deserialization** | If feature appears in JSON → silently skip (no warning - intentional) |
| **Deserialization** | If feature NOT in JSON → use EMF default (normal EMF behavior) |

#### Configuration Scope

| Level | Use Case |
|-------|----------|
| **Global** | Ignore these features everywhere |
| **EClass** | Ignore these features for this class |
| **EReference** | Ignore this specific reference |

#### EAnnotation

```xml
<!-- On EClass - ignore specific features of this class -->
<eClassifiers xsi:type="ecore:EClass" name="UplinkMessage">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="ignoreFeatures" value="internalId,debugInfo"/>
  </eAnnotations>
</eClassifiers>

<!-- On EStructuralFeature - ignore this feature -->
<eStructuralFeatures xsi:type="ecore:EReference" name="description">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="ignore" value="true"/>
  </eAnnotations>
</eStructuralFeatures>
```

#### Config Builder

```java
// Global: ignore these features everywhere
CodecConfiguration globalConfig = ConfigBuilder
    .ignoreFeatures(
        LorawanPackage.Literals.UPLINK_MESSAGE__INFO,
        LorawanPackage.Literals.INFO__PROFILE_NAME)
    .build();

// EClass level: ignore specific features for this class
ClassConfig uplinkConfig = ClassConfigBuilder
    .forEClass(LorawanPackage.Literals.UPLINK_MESSAGE)
    .ignoreFeatures(LorawanPackage.Literals.UPLINK_MESSAGE__ID)
    .build();

// EReference level: ignore this reference
ReferenceConfig descConfig = ReferenceConfigBuilder
    .forReference(LorawanPackage.Literals.INFO__DESCRIPTION)
    .ignore()
    .build();

// Combined: all ignored features merged
// Result: UPLINK_MESSAGE__INFO, INFO__PROFILE_NAME, UPLINK_MESSAGE__ID, INFO__DESCRIPTION
CodecConfiguration config = ConfigBuilder
    .ignoreFeatures(
        LorawanPackage.Literals.UPLINK_MESSAGE__INFO,
        LorawanPackage.Literals.INFO__PROFILE_NAME)
    .withClassConfig(uplinkConfig)
    .withReferenceConfig(descConfig)
    .build();

resource.save(outputStream, Map.of("codecConfig", config));
```

#### Pure Property-Based

```java
// Global ignore list
Map<String, Object> options = Map.of(
    "ignoreFeatures", List.of("UplinkMessage.info", "Info.profileName")
);

// Per-class ignore
Map<String, Object> uplinkOptions = Map.of(
    "ignoreFeatures", List.of("id", "debugInfo")
);
Map<String, Object> options = Map.of(
    "UplinkMessage", uplinkOptions
);
```

---

### 11.10 Feature Strictness

**Status:** TO BE IMPLEMENTED

Controls how the deserializer handles unexpected mismatches between JSON content and EMF model. This is separate from the Feature Ignore List (11.9) which handles intentional exclusions.

#### Two Separate Concerns

| Concern | Description | Default |
|---------|-------------|---------|
| **Unknown Features** | JSON field has no matching EMF feature | LENIENT |
| **Missing Features** | Required EMF feature not in JSON | LENIENT |

These are configured independently, allowing fine-grained control:
- Strict on unknown, lenient on missing
- Lenient on unknown, strict on missing
- Both strict or both lenient

#### Behavior

| Situation | STRICT | LENIENT (default) |
|-----------|--------|-------------------|
| JSON field, no EMF feature | ERROR - stop | WARNING diagnostic, skip field |
| Required EMF feature missing | ERROR - stop | WARNING diagnostic, use EMF default |
| Optional EMF feature missing | OK | OK |

#### Configuration Scope

| Level | Use Case |
|-------|----------|
| **Global** | Default behavior for entire codec |
| **EClass** | "This class requires strict validation" |
| **EReference** | "When deserializing via this reference, be lenient" |

#### EAnnotation

```xml
<!-- On EClass - strict on unknown, lenient on missing -->
<eClassifiers xsi:type="ecore:EClass" name="UplinkMessage">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="strictOnUnknownFeatures" value="true"/>
    <details key="strictOnMissingFeatures" value="false"/>
  </eAnnotations>
</eClassifiers>

<!-- On EReference - lenient for both (external API data) -->
<eStructuralFeatures xsi:type="ecore:EReference" name="externalData">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="strictOnUnknownFeatures" value="false"/>
    <details key="strictOnMissingFeatures" value="false"/>
  </eAnnotations>
</eStructuralFeatures>
```

#### Config Builder

```java
// EClass level: strict on unknown, lenient on missing
ClassConfig uplinkConfig = ClassConfigBuilder
    .forEClass(LorawanPackage.Literals.UPLINK_MESSAGE)
    .strictOnUnknownFeatures()
    // .strictOnMissingFeatures()  // not called = lenient (default)
    .build();

// EReference level: lenient for both (external API data)
ReferenceConfig descConfig = ReferenceConfigBuilder
    .forReference(LorawanPackage.Literals.INFO__DESCRIPTION)
    // .strictOnUnknownFeatures()  // not called = lenient
    // .strictOnMissingFeatures()  // not called = lenient
    .build();

// Global: strict on missing, lenient on unknown (default)
CodecConfiguration config = ConfigBuilder
    .strictOnMissingFeatures()
    // .strictOnUnknownFeatures()  // not needed, lenient is default
    .withClassConfig(uplinkConfig)
    .withReferenceConfig(descConfig)
    .build();

resource.load(inputStream, Map.of("codecConfig", config));
```

#### Pure Property-Based

```java
// Global: strict on missing, lenient on unknown
Map<String, Object> options = Map.of(
    "strictOnMissingFeatures", true,
    "strictOnUnknownFeatures", false  // default, can be omitted
);

// Per-class strictness
Map<String, Object> uplinkOptions = Map.of(
    "strictOnUnknownFeatures", true,
    "strictOnMissingFeatures", false
);
Map<String, Object> options = Map.of(
    "strictOnMissingFeatures", true,
    "UplinkMessage", uplinkOptions
);
```

#### Configuration Inheritance

When a setting is not explicitly configured at a level, it inherits from the parent:

```
Global (strictOnMissingFeatures=true, strictOnUnknownFeatures=false)
  └── UplinkMessage (strictOnUnknownFeatures=true)
        → effective: missing=true (inherited), unknown=true (overridden)
        └── INFO__DESCRIPTION reference (no config)
              → effective: missing=true (inherited), unknown=true (inherited from class)
```

#### Interaction with DeserializationMode

`DeserializationMode` (Section 11.7) controls **type resolution** strictness.
`strictOnUnknownFeatures` / `strictOnMissingFeatures` (this section) controls **feature handling** strictness.

These are orthogonal - you can have:
- `DeserializationMode.STRICT` + lenient features (strict on types, lenient on features)
- `DeserializationMode.LENIENT` + strict features (lenient on types, strict on features)

### 11.11 Property Map Configuration

**Status:** TO BE DOCUMENTED

The Config Builder is the preferred approach for type-safety and IDE support. However, property maps are needed for integration with:
- OSGi Config Admin
- Spring Configurations
- Java Annotations (convertible via OSGi converter)

#### Builder from Map

```java
// Create builder from existing property map
Map<String, Object> properties = loadFromConfigAdmin();
CodecConfiguration config = ConfigBuilder.fromMap(properties).build();
```

#### Root Property Map Structure

Root-level keys use `codec.` prefix. Nested maps don't need the prefix.

```java
// Reference configuration - Map<EReference, Object>
Map<String, EClass> inlineMapping = Map.of(
    "FooBar", ExamplePackage.Literals.FRIEND,
    "BarBaz", ExamplePackage.Literals.ENEMY
);
Map<String, Object> addrRefMap = Map.of(
    "typeKey", "test",
    "typeInlineMapping", inlineMapping,
    "strictOnUnknownFeatures", true
);
Map<String, Object> genderRefMap = Map.of("ignore", true);

Map<EReference, Object> referenceConfig = new HashMap<>();
referenceConfig.put(ExamplePackage.Literals.PERSON__ADDRESS, addrRefMap);
referenceConfig.put(ExamplePackage.Literals.PERSON__GENDER, genderRefMap);

// Class configuration - Map<EClass, Object>
Map<String, Object> uplinkMsgMap = Map.of(
    "strictOnUnknownFeatures", true,
    "ignoreFeatures", List.of("info", "profileName")
);

Map<EClass, Object> classConfig = new HashMap<>();
classConfig.put(LorawanPackage.Literals.UPLINK_MESSAGE, uplinkMsgMap);

// Root option map with codec. prefix
Map<String, Object> loadOptions = new HashMap<>();
loadOptions.put("codec.classConfig", classConfig);
loadOptions.put("codec.referenceConfig", referenceConfig);
loadOptions.put("codec.rootType", ExamplePackage.Literals.PERSON);

resource.load(inputStream, loadOptions);
```

#### Flexible Value Types

Values like `codec.rootType` accept multiple formats:

| Format | Example |
|--------|---------|
| EClass instance | `ExamplePackage.Literals.PERSON` |
| EClass URI String | `"http://example.org#//Person"` |
| EMF URI object | `URI.createURI("http://example.org#//Person")` |

#### Serialization vs Deserialization Prefix

Properties can be scoped to serialization, deserialization, or both using prefixes:

| Prefix | Applies To | Example |
|--------|------------|---------|
| `codec.` | Both ser and deser | `codec.typeStrategy=NAME` |
| `codec.ser.` | Serialization only | `codec.ser.typeStrategy=NAME` |
| `codec.deser.` | Deserialization only | `codec.deser.typeStrategy=NUMERIC` |

**Example: Different strategies for ser/deser**
```java
Map<String, Object> properties = Map.of(
    "codec.ser.typeStrategy", "NAME",      // Serialize with simple names
    "codec.deser.typeStrategy", "URI"      // Deserialize expects full URIs
);

// Build separate configs
CodecConfiguration serConfig = ConfigBuilder.fromMap(properties).buildSerializationConfig();
// serConfig.typeStrategy = NAME

CodecConfiguration deserConfig = ConfigBuilder.fromMap(properties).buildDeserializationConfig();
// deserConfig.typeStrategy = URI
```

**Precedence:** Specific prefix overrides general prefix
```java
Map<String, Object> properties = Map.of(
    "codec.typeStrategy", "URI",           // Default for both
    "codec.ser.typeStrategy", "NAME"       // Override for serialization only
);
// Serialization: NAME (specific override)
// Deserialization: URI (general default)
```

#### Property Key Registry

| Root Key | Type | Description |
|----------|------|-------------|
| `codec.classConfig` | `Map<EClass, Object>` | Per-class configuration |
| `codec.referenceConfig` | `Map<EReference, Object>` | Per-reference configuration |
| `codec.rootType` | EClass / String / URI | Root type hint for deserialization |
| `codec.rootSchema` | String / URI | Schema hint for simple name resolution |
| `codec.strictOnMissingFeatures` | Boolean | Global: strict on missing features |
| `codec.strictOnUnknownFeatures` | Boolean | Global: strict on unknown features |
| `codec.ignoreFeatures` | `List<EStructuralFeature>` / `List<String>` | Global feature ignore list |

**Note:** All keys support `codec.ser.` and `codec.deser.` prefixes for direction-specific configuration.

#### Class Config Keys (nested, no prefix)

| Key | Type | Description |
|-----|------|-------------|
| `strictOnMissingFeatures` | Boolean | Strict on missing features for this class |
| `strictOnUnknownFeatures` | Boolean | Strict on unknown features for this class |
| `ignoreFeatures` | `List<String>` | Feature names to ignore |
| `mapId` | String | Discriminator map ID |
| `discriminatorPath` | String | Path to discriminator value |
| `discriminator.{mapId}` | String | Discriminator value for named map |

#### Reference Config Keys (nested, no prefix)

| Key | Type | Description |
|-----|------|-------------|
| `ignore` | Boolean | Ignore this reference entirely |
| `strictOnMissingFeatures` | Boolean | Strict on missing features |
| `strictOnUnknownFeatures` | Boolean | Strict on unknown features |
| `typeKey` | String | JSON key for type field |
| `typeInlineMapping` | `Map<String, EClass>` | Inline type discriminator mapping |
| `idFirst` | Boolean | Serialize `_id` before `_type` |

#### Annotation to Property Mapping

| Annotation Key | Property Key | Scope |
|----------------|--------------|-------|
| `ignore` | `ignore` | Feature |
| `strictOnUnknownFeatures` | `strictOnUnknownFeatures` | Class, Reference |
| `strictOnMissingFeatures` | `strictOnMissingFeatures` | Class, Reference |
| `mapId` | `mapId` | Class |
| `discriminatorPath` | `discriminatorPath` | Class |
| `discriminator.{mapId}` | `discriminator.{mapId}` | Class |
| `type.key` | `typeKey` | Reference |
| `inlineMapping.{value}` | `typeInlineMapping` (as Map) | Reference |
| `idFirst` | `idFirst` | Reference |

---

## 12. Related Documents

- [codec-v2-spec-review-findings.md](codec-v2-spec-review-findings.md) - Issue 1.1 triggered this discussion
- [05-type.md](codec-v2-spec/05-type.md) - Current type specification
- [02-config-hierarchy.md](codec-v2-spec/02-config-hierarchy.md) - Configuration hierarchy

---

## 13. Revision History

| Date | Changes |
|------|---------|
| 2026-01-20 | Initial draft from discussion session |
| 2026-01-20 | Added separate scopes for strategy and format (`typeScope`, `typeFormatScope`) |
| 2026-01-20 | Defined SuperType as extension of Type (follows type format/scope) |
| 2026-01-20 | Added SuperType presentation options (ARRAY, STRING with separator) |
| 2026-01-20 | Documented why SuperType is NOT at EReference level (Section 3.4) |
| 2026-01-20 | Added underscore convention explanation (Section 7) |
| 2026-01-20 | Added comprehensive multi-level configuration example (Section 8.3) |
| 2026-01-20 | Updated resolution algorithm with full fallthrough chain |
| 2026-01-20 | Added TypeStrategy.NONE for no type information (Section 11.3) |
| 2026-01-20 | Refined type resolution algorithm - config-driven, not auto-detect (Section 11.5) |
| 2026-01-20 | Added CODEC_TYPE_MODE (HINT vs OVERRIDE) - documented deviation from config hierarchy (Section 11.6) |
| 2026-01-20 | Added DeserializationMode (STRICT, LENIENT, AUTO_DETECT) with LENIENT as default (Section 11.7) |
| 2026-01-20 | MAPPED removed from TypeStrategy - now a separate Discriminator Mapping layer (Section 11.8) |
| 2026-01-20 | Added inline mapping feature for per-reference discriminator configuration (Section 11.8) |
| 2026-01-20 | Updated named registry config with correct annotation keys (no `codec.` prefix) and all three config methods |
| 2026-01-20 | Added type-safe discriminatorPath builder method using EStructuralFeature varargs |
| 2026-01-20 | Added Feature Ignore List configuration (Section 11.9) |
| 2026-01-20 | Added Feature Strictness - separate `strictOnUnknownFeatures` and `strictOnMissingFeatures` (Section 11.10) |
| 2026-01-20 | Added Property Map Configuration with key registry and annotation mapping (Section 11.11) |
| 2026-01-20 | Added ser/deser prefix semantic (codec.ser.*, codec.deser.*) for direction-specific config (Section 11.11) |
