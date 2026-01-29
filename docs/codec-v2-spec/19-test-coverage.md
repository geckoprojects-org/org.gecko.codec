# Test Coverage & Expectations

[← Scenarios](18-scenarios.md) | [Next: Code Conventions →](20-code-conventions.md)

---

## 1. Test Architecture

The codec-v2 testing is organized into **three layers**, each testing a specific component:

```
┌─────────────────────────────────────────────────────────────┐
│                    Codec Runtime Tests                       │
│  (org.eclipse.fennec.codec.v2 - serialization/deserialization) │
│  Tests: End-to-end JSON ↔ EObject roundtrip                   │
└─────────────────────────────────────────────────────────────┘
                              ↓ uses
┌─────────────────────────────────────────────────────────────┐
│               Configuration Merger Tests                     │
│  (org.eclipse.fennec.codec.v2 - ConfigurationMerger)         │
│  Tests: Merging configs from Global → EClass → EReference    │
└─────────────────────────────────────────────────────────────┘
                              ↓ uses
┌─────────────────────────────────────────────────────────────┐
│                 Aspect Provider Tests                        │
│  (org.eclipse.fennec.codec.metadata - CodecAspectProvider)   │
│  Tests: Parsing EAnnotations → Aspect objects                │
└─────────────────────────────────────────────────────────────┘
```

### 1.1 Layer Responsibilities

| Layer | Project | What It Tests | Test Focus |
|-------|---------|---------------|------------|
| **AspectProvider** | `codec.metadata` | EAnnotation parsing | Each annotation key parsed correctly into Aspect EMF objects |
| **ConfigurationMerger** | `codec.v2` | Config hierarchy merging | EReference > EClass > Global > Default override chain |
| **Codec Runtime** | `codec.v2` | Serialization/Deserialization | JSON output/input matches spec for all config combinations |

### 1.2 Test Commands

```bash
# AspectProvider tests (annotation parsing)
./gradlew :org.eclipse.fennec.codec.metadata:test

# Model metadata tests
./gradlew :org.eclipse.fennec.model.metadata:test

# Codec v2 tests (merging + runtime)
./gradlew :org.eclipse.fennec.codec.v2:test

# All v2 tests together
./gradlew :org.eclipse.fennec.codec.v2:test :org.eclipse.fennec.codec.metadata:test :org.eclipse.fennec.model.metadata:test
```

---

## 1.3 Model Metadata Tests

**Test Class:** `DiagnosticContainerTest.java`
**Project:** `org.eclipse.fennec.model.metadata`
**Spec Reference:** [15-error-handling.md](15-error-handling.md) (Section 6.10)

Tests for the `DiagnosticContainer` interface and `MetadataDiagnostic` model.

| Test | Description | Status |
|------|-------------|--------|
| `testDiagnosticsContainmentOnFeatureMetadata` | Verify diagnostics can be added to FeatureMetadata | ✅ |
| `testDiagnosticsContainmentOnClassMetadata` | Verify diagnostics can be added to ClassMetadata | ✅ |
| `testDiagnosticsContainmentOnPackageMetadata` | Verify diagnostics can be added to PackageMetadata | ✅ |
| `testAllDiagnosticsOnFeatureMetadata` | allDiagnostics equals diagnostics (no children) | ✅ |
| `testAllDiagnosticsOnClassMetadata` | allDiagnostics = own + all feature diagnostics | ✅ |
| `testAllDiagnosticsOnPackageMetadata` | allDiagnostics = own + all class allDiagnostics | ✅ |
| `testDiagnosticContainerIdentifiesSource` | Container of diagnostic is the source element | ✅ |

---

## 1.4 Config Spec Tests (NEW)

**Project:** `org.eclipse.fennec.codec.api`
**Package:** `test/org/eclipse/fennec/codec/config/spec/`
**Purpose:** Verify configuration classes match spec defaults, constraints, and validation rules.

These tests verify that the immutable config classes (`TypeConfig`, `SuperTypeConfig`, `DiscriminatorConfig`, etc.) correctly implement the spec-defined behavior for:
- Default values
- Property merging
- Validation rules (cross-property constraints)

### 1.4.1 Test Commands

```bash
# Run all codec.api tests (includes config spec tests)
./gradlew :org.eclipse.fennec.codec.api:test
```

### 1.4.2 TypeConfig Spec Tests

**Test Class:** `TypeConfigSpecTest.java`
**Spec Reference:** [06-type.md](06-type.md)

| Test Category | Tests | Description |
|---------------|-------|-------------|
| Defaults | 4 | Verify spec-defined defaults (URI strategy, PLAIN format, etc.) |
| Validation | 6 | Invalid strategy/format combinations produce errors |
| Merge | 4 | Property merging and cascade behavior |
| Strategy values | 6 | Each TypeStrategy value accepted correctly |
| Format combinations | 4 | Strategy × Format matrix validation |

### 1.4.3 SuperTypeConfig Spec Tests

**Test Class:** `SuperTypeConfigSpecTest.java`
**Spec Reference:** [07-supertype.md](07-supertype.md)

| Test Category | Tests | Description |
|---------------|-------|-------------|
| Defaults | 4 | Verify spec-defined defaults (disabled, ALL selection, etc.) |
| Validation | 5 | Invalid combinations produce appropriate diagnostics |
| Merge | 4 | Property merging and cascade behavior |
| Selection values | 4 | Each SuperTypeSelection value accepted correctly |
| Format combinations | 5 | Format-dependent default keys |

### 1.4.4 DiscriminatorConfig Spec Tests

**Test Class:** `DiscriminatorConfigSpecTest.java`
**Spec Reference:** [08-discriminator-mapping.md](08-discriminator-mapping.md)

| Test Category | Tests | Description |
|---------------|-------|-------------|
| Defaults | 5 | Verify spec-defined defaults (SKIP fallback, empty mappings) |
| Validation | 8 | Invalid fallback configurations, duplicate mappings |
| Merge | 6 | Type mappings and inline mappings merge correctly |
| FallbackStrategy | 6 | Each fallback strategy behavior verified |
| Mapping operations | 4 | Add/remove/lookup type mappings |

### 1.4.5 Test Naming Convention

Spec tests use the naming pattern: `{category}_{whatIsTested}_{expectation}`

```java
@Test
void defaults_typeStrategyIsUri()
@Test
void validation_noneWithStructuredFormat_producesError()
@Test
void merge_overridesTakesPrecedence()
```

---

## 1.5 Package Migration & Test Structure

The codec.v2 migration uses a consistent package structure. Tests follow the same pattern as source code.

### 1.5.1 Package Structure

| Old Package (deprecated) | New Package | Status |
|--------------------------|-------------|--------|
| `o.e.f.codec.api.value` | `o.e.f.codec.value` | ✅ Migrated |
| `o.e.f.codec.api.diagnostic` | `o.e.f.codec.diagnostic` | ✅ Migrated |
| `o.e.f.codec.api.config` | `o.e.f.codec.config` | ✅ Already in new location |

### 1.5.2 Test Location Pattern

```
Source: src/org/eclipse/fennec/codec/{package}/
Tests:  test/org/eclipse/fennec/codec/{package}/
Spec:   test/org/eclipse/fennec/codec/{package}/spec/  (optional)
```

### 1.5.3 Deprecated Test Handling

Deprecated tests are kept as migration reference but disabled:

```java
@Deprecated
@Disabled("Migrated to org.eclipse.fennec.codec.{package} - kept for migration reference")
@SuppressWarnings("deprecation")
class OldTest { ... }
```

### 1.5.4 Current Test Counts (codec.api)

| Category | Active Tests | Ignored Tests |
|----------|--------------|---------------|
| Config (impl + spec) | ~280 | 0 |
| Value (new) | 54 | 0 |
| Diagnostic (new) | 76 | 0 |
| Value (old, deprecated) | 0 | 40 |
| Diagnostic (old, deprecated) | 0 | 76 |
| **Total** | ~603 | 116 |

---

## 2. AspectProvider Test Expectations

**Test Class:** `CodecAspectProviderTest.java`
**Test Ecore:** `test-codec-annotations.ecore`
**Spec Reference:** [16-annotation-reference.md](16-annotation-reference.md)

The AspectProvider tests verify that EAnnotations are correctly parsed into Aspect EMF objects. These tests do NOT test configuration merging or runtime behavior.

### 2.0 Scope Clarification (IMPORTANT)

**AspectProvider tests ONLY verify:**
- Each annotation key is parsed correctly into Aspect EMF objects
- Default values are applied when annotations are missing
- Invalid keys at wrong EMF levels are ignored

**AspectProvider tests do NOT verify:**
- Configuration level merging (Global → EClass → ERef) → belongs in `ConfigurationMergerTest`
- Source hierarchy merging (LoadOptions → Resource → Annotation) → belongs in `ConfigurationMergerTest`
- Runtime serialization/deserialization behavior → belongs in `codec.v2` runtime tests

The AspectProvider is a pure **parsing layer** - it converts EAnnotations to Aspect objects. The **merging logic** that combines aspects from different levels is handled by the ConfigurationMerger in `codec.v2`.

### 2.1 Parsing Tests by Feature

Each annotation key should have a test verifying it's parsed correctly.

#### Type Configuration ([06-type.md](06-type.md))

| Annotation Key | Test Method | Status |
|----------------|-------------|--------|
| `typeStrategy` | `testBuildClassAspectWithTypeUriStrategy`, `testBuildClassAspectWithNameTypeStrategy` | ✅ |
| `typeFormat` | `testBuildClassAspectWithStructuredTypeConfig` | ✅ |
| `typeKey` | `testBuildClassAspectWithTypeUriStrategy` | ✅ |
| `typeNameKey` | `testBuildClassAspectWithStructuredTypeConfig` | ✅ |
| `typeSchemaKey` | `testBuildClassAspectWithStructuredTypeConfig` | ✅ |
| `typeInclude` | `testBuildClassAspectWithTypeIncludeFalse` | ✅ |
| `typeMapId` | `testBuildClassAspectWithTypeMapId` | ✅ |

#### SuperType Configuration ([07-supertype.md](07-supertype.md))

| Annotation Key | Test Method | Status |
|----------------|-------------|--------|
| `superTypeSerialize` | `testBuildClassAspectWithSuperTypeConfig` | ✅ |
| `superTypeKey` | `testBuildClassAspectWithSuperTypeConfig` | ✅ |
| `superTypeStrategy` | `testBuildClassAspectWithSingleSuperTypeStrategy` | ✅ |
| `superTypeAsArray` | `testBuildClassAspectWithSuperTypeAsArrayFalse`, `testBuildClassAspectWithSuperTypeDefaultAsArrayTrue` | ✅ |
| `superTypeSeparator` | `testBuildClassAspectWithSuperTypeSeparator`, `testBuildClassAspectWithSuperTypeDefaultSeparator` | ✅ |
| `superTypeFormat` | `testBuildClassAspectWithStructuredSuperTypeConfig` | ✅ |
| `superTypeSchemaKey` | `testBuildClassAspectWithStructuredSuperTypeConfig` | ✅ |
| `superTypeNameKey` | `testBuildClassAspectWithStructuredSuperTypeConfig` | ✅ |

#### Discriminator Mapping ([08-discriminator-mapping.md](08-discriminator-mapping.md))

| Annotation Key | Test Method | Status |
|----------------|-------------|--------|
| `typeDiscriminatorPath` | `testBuildClassAspectWithDiscriminatorPath` | ✅ |
| `typeDiscriminator` | `testBuildClassAspectWithDiscriminatorValue` | ✅ |
| `inlineMapping.*` | `testBuildReferenceAspectWithInlineTypeMappings` | ✅ |
| `fallbackStrategy` | `testBuildClassAspectWithFallbackError`, `testBuildClassAspectWithFallbackSkip`, `testBuildClassAspectWithExplicitFallbackEClass` | ✅ |
| `fallbackEClass` | `testBuildClassAspectWithExplicitFallbackEClass`, `testBuildReferenceAspectWithFallbackConfig` | ✅ |

#### ID Configuration ([09-id.md](09-id.md))

| Annotation Key | Test Method | Status |
|----------------|-------------|--------|
| `idStrategy` | `testBuildClassAspectWithIdFieldStrategy`, `testBuildClassAspectWithCombinedIdStrategy`, `testBuildClassAspectWithIdNoneStrategy` | ✅ |
| `idFormat` | `testBuildClassAspectWithStructuredIdConfig` | ✅ |
| `idKey` | `testBuildClassAspectWithIdFieldStrategy` | ✅ |
| `idValueKey` | `testBuildClassAspectWithIdValueKey` | ✅ |
| `idFeatures` | `testBuildClassAspectWithCombinedIdStrategy` | ✅ |
| `idSeparator` | `testBuildClassAspectWithCombinedIdStrategy` | ✅ |
| `idSeparatorKey` | `testBuildClassAspectWithStructuredIdConfig` | ✅ |
| `idSeparatorSerialize` | `testBuildClassAspectWithStructuredIdConfig` | ✅ |
| `idKeyMode` | `testBuildClassAspectWithStructuredIdConfig` | ✅ |
| `idOnTop` | `testBuildClassAspectWithStructuredIdConfig` | ✅ |
| `idValueReaderName` | `testBuildClassAspectWithCustomIdReaderWriter` | ✅ |
| `idValueWriterName` | `testBuildClassAspectWithCustomIdReaderWriter` | ✅ |

#### Reference Configuration ([10-reference.md](10-reference.md))

| Annotation Key | Test Method | Status |
|----------------|-------------|--------|
| `refFormat` | `testBuildReferenceAspectWithRefConfig` | ✅ |
| `refKey` | `testBuildReferenceAspectWithRefConfig` | ✅ |
| `refTypeKey` | `testBuildReferenceAspectWithRefConfig` | ✅ |
| `expand` | `testBuildReferenceAspectWithRefConfig` | ✅ |

#### Feature Configuration ([11-feature.md](11-feature.md))

| Annotation Key | Test Method | Status |
|----------------|-------------|--------|
| `key` | `testBuildAttributeAspectWithCustomKey`, `testBuildReferenceAspectWithCustomKey` | ✅ |
| `transient` | `testBuildAttributeAspectWithTransient`, `testBuildReferenceAspectWithTransient` | ✅ |
| `serialize` | `testBuildAttributeAspectWithExplicitSerialize`, `testBuildAttributeAspectWithSerializeFalse`, `testBuildAttributeAspectSerializeOverridesTransient` | ✅ |
| `serializeNull` | `testBuildAttributeAspectWithSerializeNull` | ✅ |
| `serializeEmpty` | `testBuildAttributeAspectWithSerializeEmpty` | ✅ |
| `serializeDefaults` | `testBuildAttributeAspectWithSerializeDefaults` | ✅ |
| `enumSerialization` | `testBuildAttributeAspectWithEnumLiteralStrategy`, `testBuildAttributeAspectWithEnumValueStrategy`, `testBuildAttributeAspectWithEnumNameStrategy` | ✅ |
| `valueReaderName` | `testBuildAttributeAspectWithValueReader`, `testBuildAttributeAspectWithBothReaderAndWriter` | ✅ |
| `valueWriterName` | `testBuildAttributeAspectWithValueWriter`, `testBuildAttributeAspectWithBothReaderAndWriter` | ✅ |

### 2.2 Invalid Configuration Tests (Keys at Wrong Levels)

These tests verify that annotation keys placed at wrong EMF element levels are **ignored** (not causing errors, just not applied).

**Spec Reference:** [16-annotation-reference.md - Invalid Configurations](16-annotation-reference.md)

| Misconfiguration | Test Method | Expected Behavior | Status |
|------------------|-------------|-------------------|--------|
| `ref*` keys on EClass | `testClassIgnoresRefConfigKeys` | Ignored - refConfig is reference-only | ✅ |
| `idStrategy/idFeatures` on EReference | `testReferenceIgnoresIdConfigKeys` | Ignored - idConfig is class-only | ✅ |
| `superType*` keys on EReference | `testReferenceIgnoresSuperTypeConfigKeys` | Ignored - superType is class-only | ✅ |
| `enumSerialization` on EReference | `testReferenceIgnoresEnumSerializationKey` | Ignored - enum is attribute-only | ✅ |
| `inlineMapping.*` on EClass | `testClassIgnoresInlineMappingKeys` | Ignored - inlineMapping is reference-only | ✅ |
| `typeDiscriminator` on EReference | `testReferenceIgnoresTypeDiscriminatorKey` | Ignored - discriminator value is class-only | ✅ |

#### Missing Invalid Configuration Tests

The following misconfigurations from the spec are NOT yet tested:

| Misconfiguration | Spec Section | Priority |
|------------------|--------------|----------|
| `typeValueReaderName/WriterName` on EReference | Type Config | Medium |
| `type*` keys on EAttribute | Type Config | Medium |
| `superType*` keys on EAttribute | SuperType Config | Medium |
| `typeDiscriminatorPath` on EReference | Discriminator | Medium |
| `typeMapId` on EReference | Discriminator | Medium |
| `id*` keys on EAttribute | ID Config | Medium |
| `idSeparator/Key/Mode/OnTop/ValueKey` on EReference | ID Config | Medium |
| `metadataMerge/Key` on EReference | Metadata | Low |
| `metadata*` on EAttribute | Metadata | Low |
| `ref*` on EAttribute | Reference | Medium |
| `expand` on EAttribute | Reference | Medium |
| `ignore*/force*` on EClass | Feature | Low |
| `key` on EClass | Feature | Low |

---

## 3. Configuration Hierarchy Test Expectations

**Test Class:** `ConfigurationMergerTest.java` (to be created in `codec.v2`)
**Spec Reference:** [02-config-resolution.md](02-config-resolution.md)

### 3.0 Scope Clarification (IMPORTANT)

**ConfigurationMerger tests belong in `codec.v2`, NOT in `codec.metadata`.**

The ConfigurationMerger is responsible for:
1. **Scope Chain Merging (Horizontal):** EReference > EClass > Global > Default
2. **Source Hierarchy Merging (Vertical):** Load/Save Options > Resource > Factory > Module > EAnnotation > Default

The AspectProvider in `codec.metadata` only parses individual EAnnotations into Aspect objects. The ConfigurationMerger in `codec.v2` then combines these aspects with runtime options to produce the effective configuration.

```
┌─────────────────────────────────────────────────────────────────────┐
│                          codec.v2                                    │
│  ┌─────────────────────────────────────────────────────────────┐    │
│  │                  ConfigurationMerger                         │    │
│  │  - Merges Global + EClass + ERef aspects                     │    │
│  │  - Applies Load/Save options overrides                       │    │
│  │  - Produces EffectiveConfig for serialization               │    │
│  └─────────────────────────────────────────────────────────────┘    │
│                              ↑ uses                                  │
└──────────────────────────────┼──────────────────────────────────────┘
                               │
┌──────────────────────────────┼──────────────────────────────────────┐
│                          codec.metadata                              │
│  ┌─────────────────────────────────────────────────────────────┐    │
│  │                  CodecAspectProvider                         │    │
│  │  - Parses EAnnotations from EClass/EReference/EAttribute     │    │
│  │  - Creates ClassCodecAspect, FeatureCodecAspect objects      │    │
│  │  - Does NOT merge across levels                              │    │
│  └─────────────────────────────────────────────────────────────┘    │
└─────────────────────────────────────────────────────────────────────┘
```

### 3.1 Scope Chain (Horizontal) Tests

Tests must verify the override chain: **EReference > EClass > Global > Default**

#### Test Scenario: Type Configuration Override

```
Given:
  - Global config: typeStrategy=URI
  - EClass (Person) annotation: typeStrategy=NAME
  - EReference (Person.address) annotation: typeStrategy=SCHEMA_AND_TYPE

Expected effective config:
  - For Person root object: NAME (from EClass)
  - For Person.address objects: SCHEMA_AND_TYPE (from EReference)
  - For other objects: URI (from Global)
```

| Test Case | Config Levels | Expected Result | Status |
|-----------|---------------|-----------------|--------|
| Global only | Global: NAME | All use NAME | ❌ TODO |
| EClass overrides Global | Global: URI, EClass: NAME | EClass instances use NAME | ❌ TODO |
| EReference overrides EClass | EClass: URI, ERef: NAME | Referenced objects use NAME | ❌ TODO |
| EReference overrides Global | Global: URI, ERef: NAME | Referenced objects use NAME | ❌ TODO |
| Partial override (some keys) | EClass: strategy=NAME, ERef: format=STRUCTURED | Merges both | ❌ TODO |
| No annotation uses default | None | Uses built-in default | ❌ TODO |

### 3.2 Source Hierarchy (Vertical) Tests

Tests must verify: **Load/Save Options > Resource > Factory > Module > EAnnotation > Default**

| Test Case | Config Sources | Expected Result | Status |
|-----------|----------------|-----------------|--------|
| Load options override annotation | Annotation: URI, LoadOption: NAME | NAME | ❌ TODO |
| Resource config override annotation | Annotation: URI, Resource: NAME | NAME | ❌ TODO |
| Annotation provides value when no runtime | Annotation: NAME, no runtime | NAME | ❌ TODO |

### 3.3 Configuration Hierarchy Test Matrix

For each config property that supports multiple levels (per [16-annotation-reference.md](16-annotation-reference.md) matrices), test:

| Property | Global | EClass | ERef | EAttr | Override Tests Needed |
|----------|:------:|:------:|:----:|:-----:|----------------------|
| `typeStrategy` | ✅ | ✅ | ✅ | ❌ | Global→EClass, EClass→ERef |
| `typeFormat` | ✅ | ✅ | ✅ | ❌ | Global→EClass, EClass→ERef |
| `typeKey` | ✅ | ✅ | ✅ | ❌ | Global→EClass, EClass→ERef |
| `idStrategy` | ✅ | ✅ | ❌ | ❌ | Global→EClass only |
| `idFormat` | ✅ | ✅ | ✅ | ❌ | Global→EClass, EClass→ERef |
| `idKey` | ✅ | ✅ | ✅ | ❌ | Global→EClass, EClass→ERef |
| `refFormat` | ✅ | ❌ | ✅ | ❌ | Global→ERef only |
| `refKey` | ✅ | ❌ | ✅ | ❌ | Global→ERef only |
| `serializeNull` | ✅ | ❌ | ✅ | ✅ | Global→ERef, Global→EAttr |
| `serializeEmpty` | ✅ | ❌ | ✅ | ✅ | Global→ERef, Global→EAttr |
| `enumSerialization` | ✅ | ❌ | ❌ | ✅ | Global→EAttr only |

---

## 4. Codec Runtime Test Expectations

**Test Classes:** `*SerializationEntryTest.java`, `*DeserializationEntryTest.java`
**Spec Reference:** Chapters [06-type.md](06-type.md) through [14-custom-values.md](14-custom-values.md)

### 4.1 Serialization Tests

For each feature chapter, verify JSON output matches spec examples.

#### Type Serialization ([06-type.md](06-type.md))

| Strategy | Format | Expected Output | Test Status |
|----------|--------|-----------------|-------------|
| URI | PLAIN | `"_type": "http://...#//Person"` | ✅ |
| NAME | PLAIN | `"_type": "Person"` | ✅ |
| SCHEMA_AND_TYPE | PLAIN | `"_type": "...", "_schema": "..."` | ✅ |
| SCHEMA_AND_TYPE | STRUCTURED | `"_type": { "schema": "...", "type": "..." }` | ✅ |
| NONE | - | No `_type` field | ✅ |

#### ID Serialization ([09-id.md](09-id.md))

| Strategy | KeyMode | Format | Expected Output | Test Status |
|----------|---------|--------|-----------------|-------------|
| ID_FIELD | ID_ONLY | PLAIN | `"_id": "value"` | ✅ |
| ID_FIELD | BOTH | PLAIN | `"_id": "value", "idFeature": "value"` | ✅ |
| COMBINED | ID_ONLY | PLAIN | `"_id": "a-b-c"` | ✅ |
| COMBINED | ID_ONLY | STRUCTURED | `"_id": { "id": "a-b-c", "separator": "-" }` | ✅ |

#### Reference Serialization ([10-reference.md](10-reference.md))

| Format | Expand | Expected Output | Test Status |
|--------|--------|-----------------|-------------|
| PLAIN | false | `"_ref": "/path/to/object"` | ✅ |
| STRUCTURED | false | `"ref": { "_ref": "...", "_type": "..." }` | ✅ |
| - | true | Full object inline | ✅ ser, ❌ deser |

### 4.2 Deserialization Tests

For each feature chapter, verify EObjects are correctly created from JSON.

| Feature | Test Focus | Test Status |
|---------|------------|-------------|
| Type resolution | URI, NAME, SCHEMA_AND_TYPE strategies | ✅ |
| ID deserialization | Populate ID features from `_id` | ✅ |
| Reference resolution | Proxy creation, later resolution | ✅ |
| Discriminator mapping | Type lookup from discriminator value | ✅ |
| Fallback resolution | ERROR, SKIP, FALLBACK strategies | ❌ TODO |

---

## 5. Test Models

| Ecore Model | Project | Purpose |
|-------------|---------|---------|
| `test-codec-annotations.ecore` | `codec.metadata` | AspectProvider parsing tests |
| `test-roundtrip.ecore` | `codec.v2` | Basic roundtrip tests |
| `test-advanced.ecore` | `codec.v2` | Polymorphism, bidirectional, circular refs |
| `test-discriminator.ecore` | `codec.v2` | Discriminator mapping tests |

---

## 6. Coverage Gaps & Priorities

### 6.1 High Priority (Blocking v2 completion)

| Gap | Component | Spec Section | Action |
|-----|-----------|--------------|--------|
| Configuration hierarchy tests | `codec.v2` | [02-config-resolution.md](02-config-resolution.md) | Create `ConfigurationMergerTest.java` |
| Fallback strategy tests | `codec.v2` | [08-discriminator-mapping.md](08-discriminator-mapping.md) | Add ERROR/SKIP/FALLBACK deser tests |
| Missing invalid config tests | `codec.metadata` | [16-annotation-reference.md](16-annotation-reference.md) | Add tests per Section 2.2 |

### 6.2 Medium Priority

| Gap | Component | Spec Section | Action |
|-----|-----------|--------------|--------|
| `expand=true` deserialization | `codec.v2` | [10-reference.md](10-reference.md) | Implement + test detached object creation |
| Feature strictness tests | `codec.v2` | [16-annotation-reference.md](16-annotation-reference.md) | Add `strictOnUnknown/Missing` tests |
| `ignore*/force*` tests | `codec.metadata` + `codec.v2` | [11-feature.md](11-feature.md) | Replace deprecated `transient/serialize` |

### 6.3 Low Priority

| Gap | Notes |
|-----|-------|
| Large object graphs (1000+ elements) | Performance testing |
| Concurrent access / thread-safety | Multi-threaded scenarios |
| Cross-resource references | Multiple EMF resources |

---

## 7. Test Naming & Intent Tagging Conventions

Clear test naming and tagging is **critical** for understanding test intent. This helps both humans and AI agents quickly understand whether a test verifies:
- **Valid configuration** (happy path)
- **Misconfiguration** (error handling - values should be ignored with diagnostics)
- **Edge cases** (boundary conditions)

### 7.1 Test Class Naming by Intent

Separate tests into classes based on their intent:

| Class Name Pattern | Intent | Example |
|-------------------|--------|---------|
| `*ValidConfigTest` | Tests for correctly configured annotations | `CodecAspectProviderValidConfigTest.java` |
| `*MisconfigTest` | Tests for misplaced/invalid annotation keys | `CodecAspectProviderMisconfigTest.java` |
| `*DiagnosticsTest` | Tests for diagnostic/warning collection | `AspectDiagnosticsTest.java` |
| `*EdgeCaseTest` | Tests for boundary conditions | `IdSerializationEdgeCaseTest.java` |

### 7.2 Test Method Naming Pattern

Use a naming pattern that immediately reveals intent:

**For valid configuration tests:**
```java
void validConfig_typeStrategyUri_parsedCorrectly()
void validConfig_idFieldStrategy_createsIdConfig()
```

**For misconfiguration tests:**
```java
void misconfig_typeDiscriminatorPathOnReference_ignoredWithDiagnostic()
void misconfig_typeMapIdOnReference_ignoredWithDiagnostic()
void misconfig_enumSerializationOnReference_ignored()
```

**General pattern:**
```
{intent}_{whatIsTested}_{expectedBehavior}
```

### 7.3 Model Tagging with @MISCONFIG and @VALID

Use XML comments with clear tags in test `.ecore` models:

```xml
<!-- @VALID: Demonstrates correct inline mapping configuration on EReference -->
<eClassifiers xsi:type="ecore:EClass" name="PersonWithValidConfig">
  ...
</eClassifiers>

<!-- @MISCONFIG: typeDiscriminatorPath is class-only per spec section 7 -->
<!-- Expected: Value is IGNORED, diagnostic WARNING is added -->
<eClassifiers xsi:type="ecore:EClass" name="ReferenceWithClassOnlyKey">
  <eStructuralFeatures xsi:type="ecore:EReference" name="contacts">
    <eAnnotations source="http://eclipse.org/fennec/codec">
      <details key="typeDiscriminatorPath" value="contactType"/>  <!-- INVALID on EReference -->
    </eAnnotations>
  </eStructuralFeatures>
</eClassifiers>
```

**Tag definitions:**

| Tag | Meaning | Test Expectations |
|-----|---------|-------------------|
| `@VALID` | Correctly configured annotation | Values should be parsed and applied to aspect |
| `@MISCONFIG` | Intentionally misconfigured for testing | Values should be **ignored**, diagnostic should be **added** |
| `@EDGE` | Edge case or boundary condition | Behavior depends on test case |
| `@SPEC(section)` | Tests specific spec functionality | Combine with `@VALID` or `@MISCONFIG`, reference spec section |
| `@HELPER` | Tests for helper/utility code | Unit tests for utility methods, no spec reference needed |

**Combining tags:**

Tags can be combined to provide full context:

```xml
<!-- @SPEC(08-discriminator-mapping.md#7) @MISCONFIG: typeDiscriminatorPath is class-only -->
<!-- Expected: Value is IGNORED, diagnostic WARNING is added -->
<eClassifiers xsi:type="ecore:EClass" name="ReferenceWithClassOnlyKey">
  ...
</eClassifiers>

<!-- @SPEC(06-type.md#2) @VALID: URI type strategy configuration -->
<eClassifiers xsi:type="ecore:EClass" name="PersonWithTypeUri">
  ...
</eClassifiers>
```

**In Java test classes:**

```java
/**
 * @SPEC(08-discriminator-mapping.md#7) @MISCONFIG
 * Tests that typeDiscriminatorPath on EReference is ignored with diagnostic.
 */
@Test
void misconfig_typeDiscriminatorPathOnReference_ignoredWithDiagnostic() { ... }

/**
 * @HELPER Tests enum parsing utility.
 */
@Test
void parseEnumValue_validInput_returnsEnum() { ... }
```

### 7.4 Misconfiguration Test Assertions

Every `@MISCONFIG` test MUST verify **two things**:

1. **Value is NOT applied:**
   ```java
   assertNull(aspect.getTypeConfig(),
       "typeConfig should be null - typeDiscriminatorPath is class-only");
   ```

2. **Diagnostic is added:**
   ```java
   assertEquals(1, aspect.getDiagnostics().size(),
       "Should have one diagnostic for ignored typeDiscriminatorPath");
   assertEquals("typeDiscriminatorPath", aspect.getDiagnostics().get(0).getKey());
   assertEquals(DiagnosticSeverity.WARNING, aspect.getDiagnostics().get(0).getSeverity());
   ```

### 7.5 Valid/Misconfig Test Pairing

**For every `@VALID` test, verify corresponding `@MISCONFIG` tests exist.**

This ensures complete coverage: if a feature works correctly when configured properly, we must also verify it's handled correctly when misconfigured.

| Valid Test | Required Misconfig Tests |
|------------|-------------------------|
| `@VALID typeStrategy on EClass` | `@MISCONFIG typeStrategy on EAttribute` (if invalid there) |
| `@VALID typeDiscriminatorPath on EClass` | `@MISCONFIG typeDiscriminatorPath on EReference` |
| `@VALID enumSerialization on EAttribute` | `@MISCONFIG enumSerialization on EReference` |
| `@VALID refFormat on EReference` | `@MISCONFIG refFormat on EClass` |

**Review checklist:**
- [ ] For each `@VALID` annotation key test, check if the key is level-restricted (see [16-annotation-reference.md](16-annotation-reference.md) matrices)
- [ ] If level-restricted, verify `@MISCONFIG` tests exist for invalid levels
- [ ] Report missing misconfig coverage in test strategy feedback

### 7.6 Why This Matters

Without clear intent markers:
- Tests like `testBuildReferenceAspectWithInlineTypeMappings` are **ambiguous** - is it testing valid config or misconfiguration?
- When tests fail, it's unclear if the fix should make the value appear or verify it's ignored
- AI agents may "fix" tests incorrectly by making invalid values pass

With clear intent markers:
- `misconfig_typeDiscriminatorPathOnReference_ignoredWithDiagnostic` immediately reveals: this tests error handling
- Model comment `<!-- @MISCONFIG: ... -->` confirms the annotation is intentionally wrong
- Test failure investigation knows to check: is the value ignored? is the diagnostic added?

---

## 8. Adding New Tests

### 7.1 AspectProvider Tests (annotation parsing)

1. Add test class to `test-codec-annotations.ecore`
2. Add test method to `CodecAspectProviderTest.java`
3. Follow naming: `testBuild[Class|Attribute|Reference]AspectWith[Feature]`
4. Update this document's Section 2

### 7.2 Configuration Merger Tests

1. Add test to `ConfigurationMergerTest.java`
2. Test both scope chain and source hierarchy
3. Update this document's Section 3

### 7.3 Codec Runtime Tests

1. Add test to appropriate `*EntryTest.java` class
2. Reference spec section in test Javadoc
3. Update this document's Section 4

---

## 8. Test Agent Instructions

**For test-runner agent:** When investigating test failures, consult this document to understand:
- Which component the test belongs to (Section 1)
- What the test is supposed to verify (Sections 2-4)
- Whether the failure indicates a spec gap or implementation bug

**For spec-validator agent:** Use Sections 2-4 to verify implementation matches spec expectations.

---

## 9. Test Strategy Feedback Loop

This document is a **living document** that evolves based on feedback from test runs.

### 9.1 Feedback Flow

```
┌─────────────────────┐
│  test-runner agent  │
│  runs tests         │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│  Evaluates strategy │
│  against this doc   │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│  Reports findings   │
│  + suggestions      │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│  User reviews &     │
│  approves changes   │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│  This document is   │
│  updated            │
└─────────────────────┘
```

### 9.2 Types of Feedback Expected

| Feedback Type | Example | Action |
|---------------|---------|--------|
| **Missing Coverage** | "Spec defines fallbackStrategy but no test exists" | Add to Section 2/3/4 tables |
| **Redundant Tests** | "Tests A and B verify the same thing" | Consolidate in tables |
| **Unclear Expectations** | "What should happen when X?" | Clarify in spec + add test expectation |
| **Better Organization** | "These tests should be grouped by feature" | Restructure tables |
| **New Invalid Configs** | "Spec says X is invalid but not tested" | Add to Section 2.2 |

### 9.3 Changelog

Track major changes to test expectations here:

| Date | Change | Reason |
|------|--------|--------|
| 2026-01-24 | Initial restructure | Separated test layers, added matrices |
| 2026-01-24 | Added fallback tests | New attributes idValueKey, fallbackStrategy, fallbackEClass |
| 2026-01-24 | Added scope clarifications | Clarified that level merging tests belong in ConfigurationMerger (codec.v2), not AspectProvider (codec.metadata) |

---

[Next: Code Conventions →](20-code-conventions.md)
