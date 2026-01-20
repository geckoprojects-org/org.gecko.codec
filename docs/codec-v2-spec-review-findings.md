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

**Status:** OPEN

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

**Resolution:** _To be decided_

---

### 1.2 Reference Type Key in STRUCTURED Format - `type` vs `_type`?

**Status:** OPEN

**Files:** `08-reference.md` Section 1.2, `02-key-configuration.md` Section 4.5

**Contradiction:**
- `08-reference.md` shows: `"employer": { "type": "http://...", "ref": "..." }`
- `02-key-configuration.md` shows: `"employer": { "_type": {...}, "ref": "..." }`

**Question:** Inside a reference object in STRUCTURED format, is the type key `type` or `_type`?

**Options:**
- A) Use `type` (no underscore) inside reference objects
- B) Use `_type` (with underscore) consistently everywhere
- C) Configurable via `typeKey` setting (applies to both class type and reference type)

**Resolution:** _To be decided_

---

### 1.3 SuperType Format - Tied to Type format or independent?

**Status:** OPEN

**Files:** `06-supertype.md` Section 1, `01-strategies.md` Section 2.3

**Contradiction:**
- `06-supertype.md` says: "SuperType format (PLAIN or STRUCTURED) follows the Type format configuration. There is no independent format setting."
- `01-strategies.md` Section 4 says: "each target has its own independent format setting"

**Question:** Does SuperType have its own format setting or does it always follow Type format?

**Options:**
- A) SuperType always follows Type format (simplicity)
- B) SuperType has independent format setting (flexibility)
- C) SuperType defaults to Type format but can be overridden

**Resolution:** _To be decided_

---

## 2. Serialization vs Deserialization Clarity

### 2.1 Smart Compression - Serialization only or both directions?

**Status:** OPEN

**Files:** `04-global-options.md` Sections 1.4-1.6, `05-type.md` Section 5.4.4

**Issue:** Smart Compression decides whether to write simple names vs full URIs during serialization. But Section 5.4.4 discusses it in deserialization context.

**Question:**
- Is Smart Compression a serialization-only setting?
- Does deserialization automatically handle both simple and full names regardless of setting?

**Expected Clarification:**
```
Smart Compression affects SERIALIZATION only:
- When enabled: writes simple names when unambiguous
- When disabled: always writes full URIs

DESERIALIZATION always handles both formats automatically.
The smartCompression setting has no effect during load.
```

**Resolution:** _To be decided_

---

### 2.2 Global Feature Ignore List - Symmetric for ser/deser?

**Status:** OPEN

**Files:** `04-global-options.md` Section 4, `09-feature.md`

**Issue:** Config says "skip during both serialization and deserialization" but doesn't clarify edge cases.

**Questions:**
1. During serialization: feature omitted from JSON - clear
2. During deserialization: if feature appears in JSON, is it silently ignored or error?
3. During deserialization: if feature NOT in JSON, use default value or leave unset?

**Resolution:** _To be decided_

---

### 2.3 Field Ordering - Relevant for deserialization?

**Status:** OPEN

**Files:** `04-global-options.md` Section 3, `14-implementation.md` line 94

**Issue:** Field ordering (DECLARATION, ALPHABETICAL) is clearly for serialization output. But what about deserialization?

**Questions:**
1. Does field ordering affect deserialization at all?
2. Can JSON have fields in any order regardless of this setting?
3. If yes, this should be explicitly stated

**Expected Clarification:**
```
Field Ordering affects SERIALIZATION only.
DESERIALIZATION accepts fields in any order.
```

**Resolution:** _To be decided_

---

## 3. Configuration Hierarchy Gaps

### 3.1 Load/Save Option Keys - No Complete Registry

**Status:** OPEN

**Files:** `03-config-hierarchy.md`, various feature files

**Issue:** Examples show different key naming conventions:
- `Person.firstName.key` (class.feature.property pattern)
- `CODEC_FEATURE_TYPE_HINTS` (constant name)
- `CODEC_ROOT_SCHEMA` (constant name)

**Questions:**
1. What is the standard key naming convention?
2. Should developers use string keys or symbolic constants?
3. Where is the complete list of all valid option keys?

**Action Needed:** Create complete registry table in `03-config-hierarchy.md`:

```markdown
| Option Key | Type | Applies To | Description |
|------------|------|------------|-------------|
| codec.type.strategy | TypeStrategy | Serialization | Type strategy to use |
| codec.type.key | String | Both | JSON key for type field |
| ... | ... | ... | ... |
```

**Resolution:** _To be decided_

---

### 3.2 ResourceFactory Defaults - How to configure?

**Status:** OPEN

**Files:** `03-config-hierarchy.md` line 16, `13-architecture.md`

**Issue:** Hierarchy mentions ResourceFactory level (level 2) but no documentation on how to set these defaults.

**Questions:**
1. Is it via constructor parameter?
2. Via setter methods?
3. Where is `CodecResourceFactory` API documented?

**Resolution:** _To be decided_

---

### 3.3 Configuration Properties / System Properties - Format?

**Status:** OPEN

**Files:** `03-config-hierarchy.md` level 4

**Issue:** Mentions "System properties, config files" but:
- No property name format specified
- No example properties
- No list of supported properties

**Questions:**
1. What is the property naming convention? (e.g., `fennec.codec.type.strategy`?)
2. Which properties are supported at this level?
3. Is there a config file format? (e.g., `.fennec-codec.properties`?)

**Resolution:** _To be decided - or explicitly state "not yet implemented"_

---

## 4. Missing Examples

### 4.1 SuperType - Load/Save Options Examples

**Status:** OPEN

**Files:** `06-supertype.md` Section 6

**Issue:** EAnnotation examples shown (6.2), Java builder examples shown (6.3), but no Load/Save options examples.

**Action Needed:** Add example like:
```java
Map<String, Object> options = Map.of(
    "codec.supertype.enabled", true,
    "codec.supertype.selection", "ALL",
    "codec.supertype.key", "_extends"
);
resource.save(outputStream, options);
```

**Resolution:** _Add to spec_

---

### 4.2 Reference Expand - All Three Config Methods

**Status:** OPEN

**Files:** `08-reference.md` Section 4.2

**Issue:** Java builder examples shown but missing:
- EAnnotation examples for `@expand`
- Load/Save options for expand
- Side-by-side comparison: proxy output vs expanded output

**Resolution:** _Add to spec_

---

### 4.3 Custom Value Readers/Writers - Registration Examples

**Status:** OPEN

**Files:** `10-custom-values.md` Sections 3.2, 4.2

**Issue:** Good implementation examples but missing:
- EAnnotation examples for `valueReaderName` / `valueWriterName`
- Load/Save options examples
- Registration examples using CodecValueRegistry
- Lookup/resolution flow example

**Resolution:** _Add to spec_

---

### 4.4 NUMERIC Type Strategy - Round-Trip Example

**Status:** OPEN

**Files:** `04-global-options.md` Section 2, `05-type.md` Section 1.6

**Issue:** Format shown but no complete round-trip example showing:
- Model with multiple EClasses
- Serialized JSON with numeric type IDs
- Deserialization back to correct types

**Resolution:** _Add to spec_

---

## 5. Default Behavior Gaps

### 5.1 Null vs Missing Values in Deserialization

**Status:** OPEN

**Files:** `09-feature.md` Section 1, `00-overview.md`

**Issue:** Serialization defaults clear (serializeNull=false, serializeDefaults=false). But deserialization algorithm unclear.

**Questions:**
1. JSON field is `null` → set feature to null or use default?
2. JSON field missing entirely → set feature to null or use default?
3. Does `serializeNull` setting affect deserialization behavior?

**Expected Clarification:**
```
Deserialization Behavior:
- Field with explicit `null` value → sets feature to null
- Field missing from JSON → feature retains EMF default value
- serializeNull/serializeDefaults settings do NOT affect deserialization
```

**Resolution:** _To be decided_

---

### 5.2 Proxy Serialization - Complete Algorithm

**Status:** OPEN

**Files:** `08-reference.md` Section 4.1

**Issue:** Says "Proxies are serialized by default" but unclear:
1. What if reference is unresolved (is-a-proxy)?
2. How is proxy URI determined?
3. What if target object has no ID?

**Resolution:** _Document complete algorithm_

---

### 5.3 Unknown Type Handling - Fail or Skip?

**Status:** OPEN

**Files:** `05-type.md` Section 5.3

**Issue:** Type resolution section mentions lookup but doesn't specify failure behavior.

**Questions:**
1. EClass not found in MetadataService → error or skip object?
2. URI is malformed → error or skip?
3. Does it fail entire deserialization or just that object?
4. Is there a lenient/strict mode option?

**Resolution:** _To be decided_

---

## 6. Annotation ↔ Property Symmetry

### 6.1 Complete Annotation Detail Keys Table

**Status:** OPEN

**Files:** `00-overview.md` Section 1.1

**Issue:** Table shows annotation markers but incomplete. Missing keys like:
- `format`, `strategy`, `schemaKey`, `typeKey`, `separator`
- These are mentioned in individual sections but not in central table

**Action Needed:** Expand table in `00-overview.md` to list ALL annotation detail keys.

**Resolution:** _Update spec_

---

### 6.2 Per-Feature Reference Format - Allowed?

**Status:** OPEN

**Files:** `05-type.md` Section 1.5.4, `08-reference.md`, `09-feature.md`

**Issue:** Type strategy per-feature is explicitly forbidden. But what about reference FORMAT per-feature?

**Question:** Can I serialize one reference in STRUCTURED format and another in PLAIN format on the same class?

**Resolution:** _Clarify in spec_

---

## 7. Error Handling Specification

### 7.1 Complete Error Scenarios Table

**Status:** OPEN

**Files:** `00-overview.md` Section 2

**Issue:** ERROR vs WARNING defined but many scenarios not listed:
- Invalid separator in ID
- Incompatible type hint with reference type
- Circular reference in expand
- Invalid discriminator path
- Missing required ID feature

**Action Needed:** Add comprehensive error table with:
- Error code/ID
- Severity (ERROR vs WARNING)
- Message template
- Recovery behavior

**Resolution:** _Add to spec_

---

### 7.2 Diagnostic API - How to Access

**Status:** OPEN

**Files:** `00-overview.md` Section 2

**Issue:** Says diagnostics go to `Resource.diagnostics()` but:
- How to access line/column numbers?
- How to distinguish error types programmatically?
- How to suppress warnings?

**Action Needed:** Add example code for diagnostic iteration.

**Resolution:** _Add to spec_

---

## 8. Implementation Gaps (Deferred Features)

### 8.1 Cross-Resource References - Workaround Needed

**Status:** OPEN (Deferred)

**Files:** `08-reference.md` Section 6.2, `14-implementation.md` line 127

**Issue:** Deserialization not implemented. Spec should document:
1. Current limitation clearly
2. Workaround guidance (manual ResourceSet resolution?)
3. Expected future behavior

**Resolution:** _Document limitation and workaround_

---

### 8.2 Expand Depth > 1 - Remove or Implement?

**Status:** OPEN

**Files:** `08-reference.md` Section 4.2, `14-implementation.md` line 318

**Issue:** Config allows `expandDepth` integer but only depth=1 supported.

**Options:**
- A) Remove depth parameter until fully implemented
- B) Keep parameter but document limitation clearly
- C) Implement depth > 1

**Resolution:** _To be decided_

---

## 9. Code Quality Issues (From Code Review)

### 9.1 Resource Leaks in ReferenceDeserializationEntry

**Status:** OPEN

**Files:** `ReferenceDeserializationEntry.java` lines 446-471, 476-478, 495-497, 558-582

**Issue:** `JsonParser` not closed if exception occurs. Needs try-with-resources.

**Resolution:** _Fix in code_

---

### 9.2 FQCNs Instead of Imports (12 instances)

**Status:** OPEN

**Files:**
- `CodecEObjectDeserializer.java` lines 432, 517, 525, 569, 577
- `AttributeDeserializationEntry.java` lines 541, 544, 549, 553, 558, 753
- `ReferenceDeserializationEntry.java` line 815

**Issue:** Using `java.util.LinkedHashMap` etc. instead of imports.

**Resolution:** _Fix in code_

---

### 9.3 Missing Configuration Validation

**Status:** OPEN

**Files:** `CodecConfiguration.java`

**Issue:** Builder doesn't validate contradictory configurations:
- `useId=false` + `idOnTop=true` → silently ignored
- `serializeType=false` + `serializeSuperTypes=true` → silently ignored

**Resolution:** _Add validation with warnings/errors_

---

## Resolution Tracking

| # | Finding | Priority | Status | Resolved Date | Spec Section Updated |
|---|---------|----------|--------|---------------|---------------------|
| 1.1 | SCHEMA_AND_TYPE inheritance | HIGH | OPEN | | |
| 1.2 | Reference type key | MEDIUM | OPEN | | |
| 1.3 | SuperType format independence | MEDIUM | OPEN | | |
| 2.1 | Smart Compression direction | HIGH | OPEN | | |
| 2.2 | Global feature ignore | MEDIUM | OPEN | | |
| 2.3 | Field ordering direction | LOW | OPEN | | |
| 3.1 | Option keys registry | HIGH | OPEN | | |
| 3.2 | ResourceFactory API | MEDIUM | OPEN | | |
| 3.3 | System properties format | LOW | OPEN | | |
| 4.1 | SuperType options example | MEDIUM | OPEN | | |
| 4.2 | Reference expand examples | HIGH | OPEN | | |
| 4.3 | Custom value examples | MEDIUM | OPEN | | |
| 4.4 | NUMERIC round-trip example | MEDIUM | OPEN | | |
| 5.1 | Null/missing deserialization | HIGH | OPEN | | |
| 5.2 | Proxy serialization algorithm | MEDIUM | OPEN | | |
| 5.3 | Unknown type handling | HIGH | OPEN | | |
| 6.1 | Annotation keys table | MEDIUM | OPEN | | |
| 6.2 | Per-feature reference format | MEDIUM | OPEN | | |
| 7.1 | Error scenarios table | HIGH | OPEN | | |
| 7.2 | Diagnostic API examples | MEDIUM | OPEN | | |
| 8.1 | Cross-resource workaround | LOW | OPEN | | |
| 8.2 | Expand depth > 1 | LOW | OPEN | | |
| 9.1 | Resource leaks | HIGH | OPEN | | |
| 9.2 | FQCN violations | MEDIUM | OPEN | | |
| 9.3 | Config validation | HIGH | OPEN | | |
