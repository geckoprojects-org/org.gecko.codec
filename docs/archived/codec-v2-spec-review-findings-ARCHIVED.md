# Codec V2 Specification Review Findings

**Created:** 2026-01-20
**Status:** Open - Needs clarification and resolution

This document tracks gaps, contradictions, and unclear areas in the codec-v2 specification identified during a comprehensive review. Each item needs clarification before implementation can proceed confidently.

---

## How to Use This Document

1. Review each finding
2. Discuss and decide resolution
3. Update spec accordingly
4. Mark item as RESOLVED with date and spec section updated
5. Create tests if needed

---

## 1. Contradictions (Need Decision)

### 1.1 SCHEMA_AND_TYPE Inheritance - Does schema context propagate to nested objects?

**Status:** IN PROGRESS - Proposal created

**Files:** `05-type.md` Section 1.5.2, `12-scenarios.md` Section 2

**Contradiction:**
- `05-type.md` states: "SCHEMA_AND_TYPE is a **root-only strategy**. It establishes a context schema at the root level but does not propagate to contained objects."
- But children "fall back to default URI" - is this the same schema context or not?
- `12-scenarios.md` shows STRUCTURED output with `_schema` inside nested `_type` objects

**Question:** When using SCHEMA_AND_TYPE at root level, what type strategy do nested/contained objects use?

**Options:**
- A) Nested objects inherit schema context, use NAME within that context
- B) Nested objects fall back to URI strategy completely (ignore schema)
- C) Nested objects use configured default strategy (separate from root)

**Resolution:**

This evolved into a larger architectural discussion about **Unified Configuration Architecture** with:
- Global scope options: `ALL`, `ROOT_ONLY`, `ROOT_CONTAINMENT`, `ROOT_NON_CONTAINMENT`
- Configuration hierarchy: EReference → EClass → Global
- Per-reference type strategy override

**Working Document:** [codec-v2-spec-working/type-strategy-scope-proposal.md](codec-v2-spec-working/type-strategy-scope-proposal.md)

**Key Decisions Made:**
- SCHEMA_AND_TYPE + Smart Compression OFF = Option B (children use full URI)
- SCHEMA_AND_TYPE + Smart Compression ON = Option A (children use simple names)
- Option C evolved into per-reference/per-class override capability
- Scope is configurable at global level only
- Default scope is ALL (backward compatible)
- Validation errors for misplaced annotations

---

### 1.2 Reference Type Key in STRUCTURED Format - `type` vs `_type`?

**Status:** RESOLVED

**Files:** `08-reference.md` Section 1.2, `02-key-configuration.md` Section 4.5

**Contradiction:**
- `08-reference.md` shows: `"employer": { "type": "http://...", "ref": "..." }`
- `02-key-configuration.md` shows: `"employer": { "_type": {...}, "ref": "..." }`

**Question:** Inside a reference object in STRUCTURED format, is the type key `type` or `_type`?

**Resolution:**

The underscore convention depends on FORMAT, not location:
- **PLAIN format**: Use `_` prefix for metadata fields (`_type`, `_schema`, `_id`)
- **STRUCTURED format**: Outer key has `_` (`_type`), inner keys don't (`type`, `schema`)

Reference serialization should use the configured `typeKey` and `typeNameKey` from Type configuration, respecting the format. This is now part of the unified configuration architecture.

**Working Document:** [codec-v2-spec-working/type-strategy-scope-proposal.md](codec-v2-spec-working/type-strategy-scope-proposal.md) - Section 7 (Underscore Convention)

---

### 1.3 SuperType Format - Tied to Type format or independent?

**Status:** RESOLVED

**Files:** `06-supertype.md` Section 1, `01-strategies.md` Section 2.3

**Contradiction:**
- `06-supertype.md` says: "SuperType format (PLAIN or STRUCTURED) follows the Type format configuration. There is no independent format setting."
- `01-strategies.md` Section 4 says: "each target has its own independent format setting"

**Resolution:**

**SuperType is an extension of Type, NOT an independent feature.**

SuperType inherits from Type:
- `typeFormat` → SuperType uses same format
- `typeFormatScope` → SuperType uses same format scope
- `typeScope` → SuperType uses same scope
- `typeStrategy` → SuperType uses compatible subset (URI, NAME, SCHEMA_AND_TYPE; MAPPED/NUMERIC fall back to URI)

SuperType has its own properties:
- `superTypeEnabled` (boolean)
- `superTypeKey` (string)
- `superTypeSelection` (ALL, SINGLE, NONE)
- `superTypePresentation` (ARRAY, STRING)
- `superTypeSeparator` (string, default ",")

SuperType is NOT configurable at EReference level (only EClass and Global) because it describes the class inheritance, not how the class is accessed.

**Working Document:** [codec-v2-spec-working/type-strategy-scope-proposal.md](codec-v2-spec-working/type-strategy-scope-proposal.md) - Sections 3.3, 3.4

---

## 2. Serialization vs Deserialization Clarity

### 2.1 Smart Compression - Serialization only or both directions?

**Status:** IN PROGRESS - Detailed spec needed

**Files:** `04-global-options.md` Sections 1.4-1.6, `05-type.md` Section 5.4.4

**Issue:** Smart Compression decides whether to write simple names vs full URIs during serialization. But Section 5.4.4 discusses it in deserialization context.

**Resolution:**

**Smart Compression is a SERIALIZATION-only setting:**
- When enabled: writes simple names when type belongs to same schema as root
- When disabled: always writes full URIs

**Deserialization handles both formats, but with requirements:**
- Full URIs: Always resolvable
- Simple names: REQUIRE schema context to resolve

**Schema Context Sources (priority order):**
1. `CODEC_ROOT_SCHEMA` load option (explicit)
2. `CODEC_ROOT_TYPE` load option (implicit from EClass package)
3. Content `_schema` field
4. Parsed from content `_type` URI

**Important:** When deserializing Smart Compression output (simple names), you MUST provide schema context via load options or JSON content. Without it, deserialization FAILS.

**TODOs added to working document:**
- Rename `CODEC_ROOT_OBJECT` → `CODEC_ROOT_TYPE`
- Detailed type resolution algorithm
- `CODEC_ROOT_TYPE` vs `CODEC_ROOT_SCHEMA` clarification
- Error cases for deserialization

**Working Document:** [codec-v2-spec-working/type-strategy-scope-proposal.md](codec-v2-spec-working/type-strategy-scope-proposal.md) - Sections 11.3, 11.4, 11.5

---

### 2.2 Global Feature Ignore List - Symmetric for ser/deser?

**Status:** RESOLVED

**Files:** `04-global-options.md` Section 4, `09-feature.md`

**Issue:** Config says "skip during both serialization and deserialization" but doesn't clarify edge cases.

**Questions:**
1. During serialization: feature omitted from JSON - clear
2. During deserialization: if feature appears in JSON, is it silently ignored or error?
3. During deserialization: if feature NOT in JSON, use default value or leave unset?

**Resolution:**
- Serialization: feature omitted from JSON
- Deserialization: if feature appears in JSON → silently skip (intentional exclusion, no warning)
- Deserialization: if feature NOT in JSON → use EMF default (normal EMF behavior)

Configurable at Global, EClass, and EReference levels.

**Working Document:** [codec-v2-spec-working/type-strategy-scope-proposal.md](codec-v2-spec-working/type-strategy-scope-proposal.md) - Section 11.9

---

### 2.3 Field Ordering - Relevant for deserialization?

**Status:** DEFERRED

**Files:** `04-global-options.md` Section 3, `14-implementation.md` line 94

**Issue:** Field ordering (DECLARATION, ALPHABETICAL) is clearly for serialization output. But what about deserialization?

**Analysis:**

Possible serialization orders:
| Order | Description |
|-------|-------------|
| `ALPHABETICAL_FEATURE` | By EStructuralFeature name |
| `ALPHABETICAL` | By effective JSON field name (after key remapping) |
| `ECORE` | By EStructuralFeature order in EPackage (classifier IDs) |
| `ANY` | No guaranteed order (default, current behavior) |

Deserialization considerations:
- JSON spec says objects are unordered
- Real-world JSON from external APIs won't follow our ordering
- Knowing expected order could enable performance optimizations
- Adds complexity for marginal performance gain

**Current Behavior:**
- Serialization: `ANY` (no guaranteed order)
- Deserialization: accepts fields in any order

**Decision:** DEFERRED - current `ANY` behavior is correct and sufficient.

If implemented later:
1. Start with serialization only (easier, more useful for diffs/testing)
2. Add deserialization optimization only if profiling shows need
3. Configurable at Global, EClass, EReference levels

**Note:** Metadata field ordering (`_type`, `_id` position) is a separate topic - see Finding 2.4.

**Resolution:** _Deferred to future implementation_

---

### 2.4 Metadata Field Ordering (_type, _id position)

**Status:** RESOLVED

**Issue:** Where should metadata fields (`_type`, `_id`, `_supertype`) appear in the serialized JSON?

**Resolution:**

Already implemented as `idOnTop` (not `idFirst` as originally proposed). The implementation was done but documentation was incomplete.

**Actual Behavior (implemented):**

| Setting | Serialization Order | Example |
|---------|---------------------|---------|
| `idOnTop=true` (default) | `_id` → `_type` → `_supertype` → features | `{"_id":"john","_type":"Person","name":"John"}` |
| `idOnTop=false` | `_type` → `_supertype` → `_id` → features | `{"_type":"Person","_id":"john","name":"John"}` |

**Note:** The original finding had the default inverted. The actual default puts ID first (useful for MongoDB indexing).

**Documentation Added:**
- `07-id.md` Section 8.7 "Metadata Field Ordering (idOnTop)" - full explanation with use cases, configuration examples
- `00-overview.md` - Added `codec.id.onTop` to annotation key registry

**Configuration:**
```java
CodecConfiguration config = CodecConfiguration.builder()
    .idOnTop(false)  // Put _type before _id
    .build();
```

**EAnnotation (on EClass):**
```xml
<eClassifiers xsi:type="ecore:EClass" name="Person">
  <eAnnotations source="http://eclipse.org/fennec/codec">
    <details key="codec.id.onTop" value="false"/>
  </eAnnotations>
</eClassifiers>
```

**Scope:** EClass level (not per-reference as originally proposed - ID ordering is a class-level concern)

**Deserialization:** Not affected - deserializer handles fields in any order.

**Resolution Date:** 2026-01-21

---

## 3. Configuration Hierarchy Gaps

### 3.1 Load/Save Option Keys - No Complete Registry

**Status:** RESOLVED

**Files:** `03-config-hierarchy.md` Section 9

**Issue:** Examples show different key naming conventions:
- `Person.firstName.key` (class.feature.property pattern)
- `CODEC_FEATURE_TYPE_HINTS` (constant name)
- `CODEC_ROOT_SCHEMA` (constant name)

**Resolution:**

Added comprehensive Section 9 "Load/Save Option Keys Registry" to `03-config-hierarchy.md`:

- **9.1 Resource-Level Options** - `CODEC_ROOT_OBJECT`, `CODEC_ROOT_SCHEMA` with usage examples
- **9.2 Context Options** - `CODEC_FEATURE_TYPE_HINTS`, `CODEC_FEATURE_VALUE_READERS`, `CODEC_FEATURE_VALUE_WRITERS`
- **9.3 Internal Context Keys** - Read-only keys used by codec internally (documented for debugging)
- **9.4 Property Map Format** - `codec.<target>.<property>` pattern with examples for global, type, ID, per-class, per-feature
- **9.5 Java Constants** - Import statements and constant references
- **9.6 Type-Safe Configuration** - Recommends `CodecConfiguration.Builder` as preferred approach

**Key Decisions:**
1. Config Builder is preferred (type-safe, IDE support)
2. Property maps use `codec.` prefix pattern
3. Java constants available in `CodecResource` and `ContextHelper`
4. Internal keys documented but marked as read-only

**Resolution Date:** 2026-01-21

---

### 3.2 ResourceFactory Defaults - How to configure?

**Status:** RESOLVED

**Files:** `CodecResourceFactory.java`, `03-config-hierarchy.md` Section 8

**Issue:** Hierarchy mentions ResourceFactory level (level 2) but no documentation on how to set these defaults.

**Resolution:**

**Implementation Changes:**
- Added parameterless constructor for DI frameworks ✓
- Added `setMetadataService(MetadataService)` setter ✓
- Added `setConfiguration(CodecConfiguration)` setter ✓
- Added `setMapperBuilder(JsonMapper.Builder)` setter ✓
- Added validation in `createResource()` to fail fast if MetadataService not set ✓
- Changed fields from `final` to mutable for setter support ✓

**Documentation Added:**
Added `03-config-hierarchy.md` Section 8 "ResourceFactory Configuration":
- **8.1 Constructor-Based Configuration** - Recommended for programmatic use
- **8.2 Setter-Based Configuration** - For DI frameworks
- **8.3 OSGi Declarative Services Example** - Complete DS component example
- **8.4 Spring Configuration Example** - `@Bean` factory method
- **8.5 Default Options** - How factory defaults work with per-operation overrides

**API Summary:**
```java
// DI-friendly constructor
public CodecResourceFactory() {}

// Setters for injection
public void setMetadataService(MetadataService metadataService)
public void setConfiguration(CodecConfiguration configuration)
public void setMapperBuilder(JsonMapper.Builder mapperBuilder)
public void setDefaultLoadOptions(Map<Object, Object> options)
public void setDefaultSaveOptions(Map<Object, Object> options)
```

**Resolution Date:** 2026-01-21

---

### 3.3 Configuration Properties / System Properties - Format?

**Status:** RESOLVED

**Files:** `03-config-hierarchy.md` level 4

**Issue:** Mentions "System properties, config files" but:
- No property name format specified
- No example properties
- No list of supported properties

**Decision:**

1. **No Java system properties** - rely on environment config mechanisms (OSGi Config Admin, Spring properties)

2. **Ser/Deser prefix semantic** for direction-specific configuration:

| Prefix | Applies To | Example |
|--------|------------|---------|
| `codec.` | Both | `codec.typeStrategy=NAME` |
| `codec.ser.` | Serialization only | `codec.ser.typeStrategy=NAME` |
| `codec.deser.` | Deserialization only | `codec.deser.typeStrategy=URI` |

3. **ConfigBuilder methods:**
```java
ConfigBuilder.fromMap(properties).buildSerializationConfig();   // uses codec. and codec.ser.
ConfigBuilder.fromMap(properties).buildDeserializationConfig(); // uses codec. and codec.deser.
```

4. **Precedence:** Specific prefix (`codec.ser.`) overrides general prefix (`codec.`)

**Working Document:** [type-strategy-scope-proposal.md](codec-v2-spec-working/type-strategy-scope-proposal.md) - Section 11.11

**Resolution:** Semantic defined, to be implemented in ConfigBuilder

---

## 4. Missing Examples

### 4.1 SuperType - Load/Save Options Examples

**Status:** DEFERRED

**Files:** `06-supertype.md` Section 6

**Issue:** EAnnotation examples shown (6.2), Java builder examples shown (6.3), but no Load/Save options examples.

**Resolution:** _Deferred - documentation task for final spec_

---

### 4.2 Reference Expand - All Three Config Methods

**Status:** DEFERRED

**Files:** `08-reference.md` Section 4.2

**Issue:** Java builder examples shown but missing:
- EAnnotation examples for `@expand`
- Load/Save options for expand
- Side-by-side comparison: proxy output vs expanded output

**Resolution:** _Deferred - documentation task for final spec_

---

### 4.3 Custom Value Readers/Writers - Registration Examples

**Status:** DEFERRED

**Files:** `10-custom-values.md` Sections 3.2, 4.2

**Issue:** Good implementation examples but missing:
- EAnnotation examples for `valueReaderName` / `valueWriterName`
- Load/Save options examples
- Registration examples using CodecValueRegistry
- Lookup/resolution flow example

**Resolution:** _Deferred - documentation task for final spec_

---

### 4.4 NUMERIC Type Strategy - Round-Trip Example

**Status:** DEFERRED

**Files:** `04-global-options.md` Section 2, `05-type.md` Section 1.6

**Issue:** Format shown but no complete round-trip example showing:
- Model with multiple EClasses
- Serialized JSON with numeric type IDs
- Deserialization back to correct types

**Resolution:** _Deferred - documentation task for final spec_

---

## 5. Default Behavior Gaps

### 5.1 Null vs Missing Values in Deserialization

**Status:** RESOLVED

**Files:** `09-feature.md` Section 1.3

**Issue:** Serialization defaults clear (serializeNull=false, serializeDefaults=false). But deserialization algorithm unclear.

**Questions:**
1. JSON field is `null` → set feature to null or use default?
2. JSON field missing entirely → set feature to null or use default?
3. Does `serializeNull` setting affect deserialization behavior?

**Resolution:**

Added Section 1.3 "Deserialization Behavior" to `09-feature.md` with:

1. **Object types (EString, etc.)**: Explicit `null` → sets to `null`
2. **Primitive types (EInt, EBoolean, etc.)**: Explicit `null` → resets to EMF default (via `eSet(attr, null)`)
3. **Missing field** → feature retains EMF default value
4. **`serializeNull`/`serializeDefaults` do NOT affect deserialization** - these are output controls only

**Key Discovery:** For primitive types, EMF's `eSet(attribute, null)` does NOT set to Java primitive default (0, false). Instead, it resets to the `defaultValueLiteral`. This is standard EMF behavior where null = "unset" for primitives.

**Tests Added:** `CodecResourceAdvancedTest.NullDefaultValueTests`:
- `distinguishesExplicitNullFromMissingField` - Tests object type null handling
- `explicitNullForPrimitiveResetsToEmfDefault` - Tests primitive type null handling
- `serializeNullDoesNotAffectDeserialization` - Tests that serialize* settings are output-only

**Resolution Date:** 2026-01-21

---

### 5.2 Proxy Serialization - Complete Algorithm

**Status:** RESOLVED

**Files:** `08-reference.md` Section 4.1.1

**Issue:** Says "Proxies are serialized by default" but unclear:
1. What if reference is unresolved (is-a-proxy)?
2. How is proxy URI determined?
3. What if target object has no ID?

**Resolution:**

Added Section 4.1.1 "Serialization Algorithm" to `08-reference.md` with:

1. **Decision tree** for reference serialization (containment, cross-document, expand, proxy)
2. **URI determination table** - same-document (fragment), cross-document (relative), proxy (preserved), no-resource (fallback)
3. **Proxy detection** - when `eIsProxy() == true`, use proxy URI, don't expand
4. **Edge cases table** - unresolved proxy, no resource, bidirectional, multi-valued

**Answers to original questions:**
1. Unresolved proxy → Serialized using its existing proxy URI from `InternalEObject.eProxyURI()`
2. Proxy URI → Same-document uses fragment only; cross-document uses relative URI
3. No ID/no resource → Falls back to EClass URI: `nsURI#//EClassName`

**Resolution Date:** 2026-01-21

---

### 5.3 Unknown Type Handling - Fail or Skip?

**Status:** RESOLVED

**Files:** `05-type.md` Section 5.3.1, `00-overview.md` Section 2.1

**Issue:** Type resolution section mentions lookup but doesn't specify failure behavior.

**Questions:**
1. EClass not found in MetadataService → error or skip object?
2. URI is malformed → error or skip?
3. Does it fail entire deserialization or just that object?
4. Is there a lenient/strict mode option?

**Resolution:**

Added `05-type.md` Section 5.3.1 "Unknown Type Handling" documenting:

**Behavior:**
- **Unknown type WITH hint** → WARNING, use hint (recovery)
- **Unknown type WITHOUT hint** → ERROR, fail

**Key Points:**
1. EClass not found → WARNING if hint available (use hint), ERROR if no hint
2. Malformed URI → Same behavior as unknown type (fallback to hint or error)
3. Fails just that object, not entire deserialization (if in a list, other objects proceed)
4. Lenient behavior is default when hint available; strict behavior when no hint

**Resolution Order Documented:**
1. Full URI (contains `#//`)
2. Java class name (contains `.`)
3. Discriminator mapping
4. Simple name in context schema
5. Numeric classifier ID
6. Fallback to hint or ERROR

**Updated Error Scenarios:**
Also updated `00-overview.md` Section 2.1 to accurately reflect the implementation:
- Unknown type value (with hint) → WARNING, falls back to hint
- Unknown type value (no hint) → ERROR, operation fails

**Resolution Date:** 2026-01-21

---

## 6. Annotation ↔ Property Symmetry

### 6.1 Complete Annotation Detail Keys Table

**Status:** RESOLVED

**Files:** `00-overview.md` Section 1.1

**Issue:** Table shows annotation markers but incomplete. Missing keys like:
- `format`, `strategy`, `schemaKey`, `typeKey`, `separator`
- These are mentioned in individual sections but not in central table

**Resolution:**

Expanded `00-overview.md` Section 1.1 with complete annotation key registry:
- **Type Configuration** - 7 keys including `codec.type.strategy`, `codec.type.format`, `codec.type.scope`, etc.
- **SuperType Configuration** - 5 keys including `codec.supertype.enabled`, `codec.supertype.selection`, etc.
- **ID Configuration** - 9 keys including `codec.id.strategy`, `codec.id.format`, `codec.id.scope`, etc.
- **Reference Configuration** - 3 keys including `codec.reference.format`, `codec.reference.expand`
- **Feature Configuration** - 7 keys including `serialize`, `key`, `serializeNull`, etc.
- **Enum value definitions** - TypeStrategy, IdStrategy, StrategyScope, SerializationFormat

**Resolution Date:** 2026-01-21

---

### 6.2 Per-Feature Reference Format - Allowed?

**Status:** RESOLVED

**Files:** `08-reference.md` Section 4

**Issue:** Type strategy per-feature is explicitly forbidden. But what about reference FORMAT per-feature?

**Question:** Can I serialize one reference in STRUCTURED format and another in PLAIN format on the same class?

**Resolution:** **YES, per-reference format is supported.**

Added `08-reference.md` Section 4 "Per-Reference Format Configuration" explaining:
- Reference format is per-reference (unlike Type strategy which is per-class)
- Comparison table showing why: Type applies to object identity, Reference format applies to how reference is written
- Example showing `employer` in STRUCTURED, `friends` in PLAIN on same class
- Configuration inheritance: per-reference → global → default (STRUCTURED)

**Key insight:** Type strategy and Reference format have different semantic bindings:
- Type: "Object has one type identity" → per-class
- Reference: "Different references may need different formats" → per-reference

**Resolution Date:** 2026-01-21

---

## 7. Error Handling Specification

### 7.1 Complete Error Scenarios Table

**Status:** RESOLVED

**Files:** `00-overview.md` Section 2.1

**Issue:** ERROR vs WARNING defined but many scenarios not listed:
- Invalid separator in ID
- Incompatible type hint with reference type
- Circular reference in expand
- Invalid discriminator path
- Missing required ID feature

**Resolution:**

Added Section 2.1 "Complete Error Scenarios" to `00-overview.md` with comprehensive tables:

- **Type Resolution Errors** - No type info, unknown URI/name, abstract type, type collision, unknown classifier ID
- **ID Errors** - Missing ID attribute, invalid separator, STRUCTURED format mismatch, feature not found
- **Reference Errors** - Circular reference, unresolved proxy URI, invalid format, missing ref key
- **Feature Errors** - Unknown feature, value conversion failure, required feature missing, type mismatch
- **Configuration Errors** - Invalid discriminator path, unknown discriminator value, invalid scope value

Each error includes severity (ERROR/WARNING), message template, and recovery behavior.

**Resolution Date:** 2026-01-21

---

### 7.2 Diagnostic API - How to Access

**Status:** RESOLVED

**Files:** `00-overview.md` Section 2.2

**Issue:** Says diagnostics go to `Resource.diagnostics()` but:
- How to access line/column numbers?
- How to distinguish error types programmatically?
- How to suppress warnings?

**Resolution:**

Added Section 2.2 "Diagnostic API" to `00-overview.md` with comprehensive code examples:

- **Accessing Diagnostics** - Iterating errors/warnings with location info (line/column)
- **Programmatic Error Type Detection** - Using `error.getSource()` to switch on diagnostic source component
- **Suppressing Warnings** - `SUPPRESS_WARNINGS` and `SUPPRESS_WARNING_SOURCES` options
- **Fail-Fast Mode** - `FAIL_FAST` option to throw on first error
- **Custom Diagnostic Handler** - `DiagnosticHandler` interface for custom error/warning handling

**Resolution Date:** 2026-01-21

---

## 8. Implementation Gaps (Deferred Features)

### 8.1 Cross-Resource References - Workaround Needed

**Status:** RESOLVED

**Files:** `08-reference.md` Section 8.3

**Issue:** Deserialization not implemented. Spec should document:
1. Current limitation clearly
2. Workaround guidance (manual ResourceSet resolution?)
3. Expected future behavior

**Resolution:**

Added comprehensive documentation to `08-reference.md` Section 8.3 "Cross-Resource References":

- **8.3.1 Supported Behavior** - Clarifies serialization is fully supported, deserialization creates proxies
- **8.3.2 Workaround: Manual Resolution** - Three options with code examples:
  - Option 1: Pre-load resources and use `EcoreUtil.resolveAll()`
  - Option 2: Lazy resolution via EMF's automatic proxy resolution
  - Option 3: Explicit resolution with `resourceSet.getEObject()`
- **8.3.3 Expected Future Behavior** - Documents planned full resolution with configuration options

**Resolution Date:** 2026-01-21

---

### 8.2 Expand Depth > 1 - Remove or Implement?

**Status:** RESOLVED (Option B)

**Files:** `08-reference.md` Section 5.2

**Issue:** Config allows `expandDepth` integer but only depth=1 supported.

**Options:**
- A) Remove depth parameter until fully implemented
- B) Keep parameter but document limitation clearly ✓
- C) Implement depth > 1

**Resolution:**

**Decision: Option B - Keep parameter, document limitation.**

Rationale:
- API stability: Removing and re-adding would break consumers
- Clear documentation: The spec already contains an Implementation Status note
- Forward compatibility: depth=1 is a reasonable default that can be extended later
- Use case clarity: Most expand scenarios only need depth=1 anyway

The spec (`08-reference.md` Section 5.2) already documents:
> **Implementation Status:** `expandDepth` values greater than 1 are not yet implemented. Currently, expansion only works for direct references (depth=1). Nested expansion (where expanded objects also expand their references) is planned for a future release.

**Resolution Date:** 2026-01-21

---

## 9. Code Quality Issues (From Code Review)

### 9.1 Resource Leaks in ReferenceDeserializationEntry

**Status:** RESOLVED

**Files:** `ReferenceDeserializationEntry.java`

**Issue:** `JsonParser` not closed if exception occurs. Needs try-with-resources.

**Resolution:**

Refactored both `deserializeNonContainmentObject` and `deserializeNonContainmentElement` methods:
1. Declared resources (`buffer`, `bufferParser`, `replayParser`) at method scope
2. Added `finally` block with `closeQuietly()` calls
3. Set variables to `null` after explicit close to avoid double-close
4. Added helper method `closeQuietly(AutoCloseable)` for safe resource cleanup

**Resolution Date:** 2026-01-21

---

### 9.2 FQCNs Instead of Imports (12 instances)

**Status:** RESOLVED

**Files:**
- `CodecEObjectDeserializer.java` - Added `LinkedHashMap` import, replaced FQCNs
- `AttributeDeserializationEntry.java` - Added `Date`, `UUID`, `SimpleDateFormat`, `ParseException`, `LinkedHashMap`, `Map` imports, replaced FQCNs
- `ReferenceDeserializationEntry.java` - Added `IOException`, `UncheckedIOException` imports, replaced FQCNs
- `CodecModule.java` - Added `Map` import, replaced FQCN

**Resolution Date:** 2026-01-21

---

### 9.3 Missing Configuration Validation

**Status:** RESOLVED (by design)

**Files:** `CodecConfiguration.java`

**Issue:** Builder doesn't validate contradictory configurations:
- `useId=false` + `idOnTop=true` → silently ignored
- `serializeType=false` + `serializeSuperTypes=true` → silently ignored

**Resolution:**

The configuration already handles these dependencies correctly through **getter logic**:
- `isIdOnTop()` returns `useId && idOnTop` (lines 340-342)
- `isSerializeSuperTypes()` returns `serializeType && serializeSuperTypes` (lines 512-514)
- `isSerializeAllSuperTypes()` returns `isSerializeSuperTypes() && serializeAllSuperTypes` (lines 526-528)

This is a deliberate design choice documented in the spec:
- Spec 16.4.1: Type and SuperType Dependency
- Spec 16.4.2: SuperType Sub-Options Dependency
- Spec 16.4.3: ID Sub-Options Dependency

**Rationale:** This pattern is more user-friendly than emitting warnings:
1. Users can freely set configuration values without worrying about dependencies
2. The system does the right thing automatically
3. If the primary option is later enabled, sub-options take effect immediately
4. No need for error handling or conditional configuration building

**Resolution Date:** 2026-01-21

---

## 10. Discriminator Mapping Refactoring

### 10.1 Remove MAPPED from TypeStrategy Enum

**Status:** DEFERRED (pending refactoring strategy)

**Files:**
- `org.eclipse.fennec.model.metadata/model/metadata.ecore` (source)
- `org.eclipse.fennec.model.metadata/src-gen/.../TypeStrategy.java` (generated)
- All usages in serialization/deserialization code
- Tests using `TypeStrategy.MAPPED`

**Issue:** MAPPED is not a type representation strategy - it's a type translation layer. It should be removed from TypeStrategy enum and treated as a separate discriminator mapping configuration.

**Decision:** See working document `type-strategy-scope-proposal.md` Section 11.8

**Action:**
1. Remove `MAPPED` from TypeStrategy enum in Ecore model
2. Regenerate code
3. Update all usages to use discriminator mapping configuration instead
4. Update tests

**Resolution:** Deferred until a comprehensive refactoring strategy is created. This change affects the Ecore model and requires coordination with other potential model changes.

**Deferred Date:** 2026-01-21

---

### 10.2 Inline Mapping for References (NEW FEATURE)

**Status:** DEFERRED (pending refactoring strategy)

**Files:** To be created/modified

**Feature:** Simple, static discriminator mapping at per-reference level without requiring `TypeDiscriminatorService`.

**Use Case:** External API uses `"test": "FooBar"` to indicate type. Map this directly in annotation/property.

**Annotation Configuration:**
```xml
<eStructuralFeatures xsi:type="ecore:EReference" name="partners">
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
```

**Spec:** See working document `type-strategy-scope-proposal.md` Section 11.8

**Resolution:** _To be implemented_

---

### 10.3 Property-Based Discriminator Configuration

**Status:** DEFERRED (pending refactoring strategy)

**Files:** `TypeDiscriminatorService.java`, load options handling

**Issue:** Currently discriminator mappings can only be configured via EAnnotations. Need to support adding mappings via load/save options for runtime flexibility.

**Example:**
```java
Map<String, Object> options = Map.of(
    "codec.type.map.lorawan-devices.Dragino_LSE01", "http://example.org#//DraginoLSE01Uplink",
    "codec.type.map.lorawan-devices.Dragino_LHT65", "http://example.org#//DraginoLHT65Uplink"
);
```

**Resolution:** _To be implemented_

---

### 10.4 STRUCTURED Format Warning for Discriminator Mapping

**Status:** DEFERRED (pending refactoring strategy)

**Files:** Serialization code

**Issue:** If discriminator mapping is configured with `typeFormat=STRUCTURED`, should emit WARNING (not error) and treat as PLAIN.

**Rationale:** Discriminator is a simple string value, nesting it in structured format adds no value.

**Resolution:** _To be implemented_

---

## 11. Feature Handling

### 11.1 Feature Strictness Mode (STRICT/LENIENT)

**Status:** DOCUMENTED (Feature Request Open)

**Files:** `09-feature.md` Section 10

**Feature Request:** Controls how the deserializer handles unexpected mismatches between JSON content and EMF model.

**Situations:**
- JSON field has no matching EMF feature → STRICT: error, LENIENT: warning + skip
- Required EMF feature missing from JSON → STRICT: error, LENIENT: warning + use default

**Current Behavior (documented):**

Added `09-feature.md` Section 10 "Unknown Field Handling" documenting:
- **Default behavior:** Unknown fields generate WARNING and are skipped (LENIENT)
- Diagnostic message format and source component
- Code example for accessing warnings
- Note about planned STRICT mode as future feature

**Configuration (planned):** Global, EClass, and EReference levels.

**Spec Reference:** See working document `type-strategy-scope-proposal.md` Section 11.10

**Resolution:** Current LENIENT behavior documented. STRICT mode remains as future feature request.

**Resolution Date:** 2026-01-21

---

## Resolution Tracking

| # | Finding | Priority | Status | Resolved Date | Spec Section Updated |
|---|---------|----------|--------|---------------|---------------------|
| 1.1 | SCHEMA_AND_TYPE inheritance | HIGH | RESOLVED | 2026-01-20 | working/type-strategy-scope-proposal.md |
| 1.2 | Reference type key | MEDIUM | RESOLVED | 2026-01-20 | working/type-strategy-scope-proposal.md §7 |
| 1.3 | SuperType format independence | MEDIUM | RESOLVED | 2026-01-20 | working/type-strategy-scope-proposal.md §3.3 |
| 2.1 | Smart Compression direction | HIGH | RESOLVED | 2026-01-20 | working/type-strategy-scope-proposal.md §11.5 |
| 2.2 | Global feature ignore | MEDIUM | RESOLVED | 2026-01-20 | working/type-strategy-scope-proposal.md §11.9 |
| 2.3 | Field ordering direction | LOW | DEFERRED | 2026-01-20 | findings (deferred) |
| 2.4 | Metadata field ordering (idOnTop) | MEDIUM | RESOLVED | 2026-01-21 | 07-id.md §8.7, 00-overview.md |
| 3.1 | Option keys registry | HIGH | RESOLVED | 2026-01-21 | 03-config-hierarchy.md §9 |
| 3.2 | ResourceFactory API | MEDIUM | RESOLVED | 2026-01-21 | 03-config-hierarchy.md §8, CodecResourceFactory.java |
| 3.3 | System properties format | LOW | RESOLVED | 2026-01-20 | working/type-strategy-scope-proposal.md §11.11 |
| 4.1 | SuperType options example | MEDIUM | DEFERRED | 2026-01-20 | Documentation task |
| 4.2 | Reference expand examples | HIGH | DEFERRED | 2026-01-20 | Documentation task |
| 4.3 | Custom value examples | MEDIUM | DEFERRED | 2026-01-20 | Documentation task |
| 4.4 | NUMERIC round-trip example | MEDIUM | DEFERRED | 2026-01-20 | Documentation task |
| 5.1 | Null/missing deserialization | HIGH | RESOLVED | 2026-01-21 | 09-feature.md §1.3 |
| 5.2 | Proxy serialization algorithm | MEDIUM | RESOLVED | 2026-01-21 | 08-reference.md §4.1.1 |
| 5.3 | Unknown type handling | HIGH | RESOLVED | 2026-01-21 | 05-type.md §5.3.1, 00-overview.md §2.1 |
| 6.1 | Annotation keys table | MEDIUM | RESOLVED | 2026-01-21 | 00-overview.md §1.1 |
| 6.2 | Per-feature reference format | MEDIUM | RESOLVED | 2026-01-21 | 08-reference.md §4 |
| 7.1 | Error scenarios table | HIGH | RESOLVED | 2026-01-21 | 00-overview.md §2.1 |
| 7.2 | Diagnostic API examples | MEDIUM | RESOLVED | 2026-01-21 | 00-overview.md §2.2 |
| 8.1 | Cross-resource workaround | LOW | RESOLVED | 2026-01-21 | 08-reference.md §8.3 |
| 8.2 | Expand depth > 1 | LOW | RESOLVED | 2026-01-21 | Decision: Option B (keep, document) |
| 9.1 | Resource leaks | HIGH | RESOLVED | 2026-01-21 | finally+closeQuietly |
| 9.2 | FQCN violations | MEDIUM | RESOLVED | 2026-01-21 | Added imports |
| 9.3 | Config validation | HIGH | RESOLVED | 2026-01-21 | By design (getter logic) |
| 10.1 | Remove MAPPED from TypeStrategy | HIGH | DEFERRED | 2026-01-21 | Pending refactoring strategy |
| 10.2 | Inline mapping for references | MEDIUM | DEFERRED | 2026-01-21 | Pending refactoring strategy |
| 10.3 | Property-based discriminator config | MEDIUM | DEFERRED | 2026-01-21 | Pending refactoring strategy |
| 10.4 | STRUCTURED format warning | LOW | DEFERRED | 2026-01-21 | Pending refactoring strategy |
| 11.1 | Feature Strictness Mode (STRICT/LENIENT) | MEDIUM | DOCUMENTED | 2026-01-21 | 09-feature.md §10 (future: STRICT mode) |
