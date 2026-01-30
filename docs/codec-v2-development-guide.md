# Codec V2 Development Guide

This document provides context for continuing codec.v2 development across sessions. It captures the goals, current state, and links to detailed architecture documentation.

**Last Updated:** 2026-01-29 (Resolver spec tests for SuperType + Discriminator; refactored into per-config-type files; Feature deserialization gate analysis)

---

## 0. Active Task Hierarchy (SESSION CONTINUITY)

This section tracks the current task hierarchy to prevent context loss during nested investigations.

### 0.1 How to Use This Section

**When starting work:** Check this section first to understand where we are.

**When a new issue arises during work:**
1. **ASK:** "Is this a child task (fix now, return to parent) or independent task (add to TODO, continue)?"
2. **If child task:** Add it to the hierarchy below with proper indentation
3. **If independent task:** Add to §5.4 "Remaining Work" or §10.4 "Current TODO List"

**When completing a task:** Mark it ✅ and return to the parent task.

**Format:**
```
MAIN TASK: [description] - [status: ACTIVE/PAUSED/✅]
├── CHILD: [description] - [status]
│   ├── CHILD: [sub-issue] - [status]
│   └── CHILD: [sub-issue] - [status]
└── RETURN TO: [next step after children complete]
```

### 0.2 Current Task Hierarchy

```
ACTIVE: Feature Config Spec Tests + Deserialization Flow Review - IN PROGRESS (2026-01-29)
│
│  CONTEXT: Before writing FeatureConfigSpecTest and FeatureConfigResolverSpecTest,
│  we need to review the deserialization flow because Feature config is about
│  gating (visibility control) and key customization, NOT strategy/format combinations.
│
│  KEY FINDING: v2 Deserialization Gate Bug
│  │  LOCATION: CodecEObjectDeserializer.java:649 (buildDeserializationEntries)
│  │  BUG: Uses `featureConfig.isSerialize()` for deserialization gate
│  │  SHOULD USE: `featureConfig.shouldDeserialize()` which respects:
│  │    - ignoreRead (directional ignore for deserialization)
│  │    - forceRead (directional force for deserialization)
│  │    - Separate from shouldSerialize() which uses ignoreWrite/forceWrite
│  │
│  │  IMPACT: Features with ignoreRead=true are still deserialized
│  │          Features with forceRead=true but ignore=true are incorrectly gated
│  │
│  │  SPEC REFERENCE: 11-feature.md §1.2 (Visibility Control)
│  │    - FeatureConfig has separate shouldSerialize() and shouldDeserialize()
│  │    - shouldDeserialize() = forceRead overrides (ignore OR ignoreRead)
│  │    - shouldSerialize() = forceWrite overrides (ignore OR ignoreWrite)
│
│  SPEC GAPS IDENTIFIED:
│  │  1. Spec §1.2 doesn't document the entry-build pattern (buildDeserializationEntries)
│  │  2. Spec §1.2 doesn't explicitly state deserialization gate should use shouldDeserialize()
│  │  3. Spec doesn't document the isChangeable() pre-check for non-changeable features
│
│  NEXT STEPS:
│  │  1. Review v2 deserialization code (buildDeserializationEntries) for gate bug fix
│  │  2. Verify key→feature mapping handles directional ignore flags correctly
│  │  3. Check if spec §1.2 needs updating for entry-build pattern
│  │  4. Create FeatureConfigSpecTest
│  │  5. Create FeatureConfigResolverSpecTest
│  │  6. Create ReferenceConfigSpecTest + ReferenceConfigResolverSpecTest
│
---

COMPLETED: SuperType + Discriminator Resolver Spec Tests + Refactoring - ✅ (2026-01-29)
│
│  GOAL: Add resolver spec tests for SuperType and Discriminator config types,
│        then refactor the growing ConfigurationResolverSpecTest into per-config-type files.
│
│  PHASE 1: Added tests to ConfigurationResolverSpecTest
│  │  - SuperType section: 17 tests (ST.1-ST.17)
│  │  - Discriminator section: 17 tests (DM.1-DM.17)
│  │  - Total grew to 75 tests (~1700 lines)
│
│  PHASE 2: Refactored into per-config-type files
│  │  - ConfigurationResolverSpecTest.java — 20 tests (shared resolution mechanics)
│  │    Source hierarchy (7), Scope chain (4), Combined (3), EffectiveConfig (4), Validation (2)
│  │  - IdConfigResolverSpecTest.java — 17 tests (ID resolution)
│  │    ID.1-ID.6 source hierarchy, ID.7-ID.8 scope chain, ID.9-ID.10 combined,
│  │    ID.11-ID.12 caching, ID.13-ID.14 validation, ID.15-ID.17 ID-specific
│  │  - SuperTypeConfigResolverSpecTest.java — 21 tests (SuperType + Cross-Config)
│  │    ST.1-ST.17 SuperType resolution + 4 Cross-Config validation tests
│  │    (STRUCTURED + NONE + superTypeSerialize, custom reader/writer conflicts)
│  │  - DiscriminatorConfigResolverSpecTest.java — 17 tests (Discriminator resolution)
│  │    DM.1-DM.6 source hierarchy, DM.7-DM.8 scope chain, DM.9-DM.10 combined,
│  │    DM.11-DM.12 caching, DM.13-DM.14 validation, DM.15-DM.17 specific properties
│
│  FILES CREATED:
│  - org.eclipse.fennec.codec.api/test/.../config/spec/IdConfigResolverSpecTest.java
│  - org.eclipse.fennec.codec.api/test/.../config/spec/SuperTypeConfigResolverSpecTest.java
│  - org.eclipse.fennec.codec.api/test/.../config/spec/DiscriminatorConfigResolverSpecTest.java
│
│  FILES MODIFIED:
│  - ConfigurationResolverSpecTest.java (reduced from 75 to 20 tests — shared only)
│
│  ALL 75 TESTS PASSING
│
│  SPEC TEST COVERAGE STATUS:
│  │  | Config Class       | Unit Tests | Spec Tests | Resolver Spec Tests |
│  │  |--------------------|------------|------------|---------------------|
│  │  | TypeConfig         | 41         | 33         | 20 (shared)         |
│  │  | IdConfig           | 27         | 56         | 17                  |
│  │  | SuperTypeConfig    | 31         | 28         | 21                  |
│  │  | DiscriminatorConfig| 61         | 29         | 17                  |
│  │  | FeatureConfig      | 52         | MISSING    | MISSING             |
│  │  | ReferenceConfig    | 43         | MISSING    | MISSING             |
│
---

COMPLETED: Layer 1 Annotation Validation for Type Config - ✅ (2026-01-29)
│
│  LOCATION: org.eclipse.fennec.codec.metadata/src/.../provider/CodecAspectProvider.java
│
│  DONE:
│  - typeMapId on EReference → WARNING ✅ (D-3, pre-existing)
│  - typeDiscriminatorPath on EReference → WARNING ✅ (D-2, pre-existing)
│  - T-V1: typeValueReaderName on EReference → ERROR ✅
│  - T-V2: typeValueWriterName on EReference → ERROR ✅
│  - T-V3: typeScope in EAnnotation → WARNING (runtime-only) ✅
│  - T-V4: typeFormatScope in EAnnotation → WARNING (runtime-only) ✅
│  - T-V5: Any type* key on EAttribute → ERROR ✅
│  - T-V7: typeDiscriminator on EReference → ERROR ✅
│  - T-V30: typeInclude deprecation → WARNING ✅ (removed from spec 2026-01-29, typeInclude fully deleted)
│  - T-V31: Both typeInclude + typeStrategy → WARNING ✅ (removed from spec 2026-01-29, typeInclude fully deleted)
│
│  NEW METHODS ADDED:
│  - checkForRuntimeOnlyKeys() (class + feature overloads)
│  - checkForTypeKeysOnAttribute()
│  - checkForDeprecatedTypeInclude()
│  - isTypeConfigKey()
│  - addClassDiagnostic()
│
│  NEW CONSTANTS:
│  - KEY_TYPE_SCOPE, KEY_TYPE_FORMAT_SCOPE, KEY_TYPE_INCLUDE
│
│  TESTS: 6 new Layer1ValidationTests + 2 updated existing tests
│
│  SPEC REFERENCES:
│  - 06-type.md Section 7 "Configuration Validation Rules"
│  - 15-error-handling.md Section 6.10 "Annotation Parsing Errors"
│
---

COMPLETED: Layer 1 Annotation Validation for SuperType, ID, Reference - ✅ (2026-01-29)
│
│  ── SuperType: DONE ──
│  │  - checkForSuperTypeKeysOnReference() → ERROR diagnostics (ST-V1)
│  │  - checkForSuperTypeKeysOnAttribute() → ERROR diagnostics (ST-V2)
│  │  - isSuperTypeConfigKey() helper
│  │  - Updated existing tests with diagnostic verification
│  │
│  ── ID: DONE ──
│  │  - Spec: Added §11 "Configuration Validation Rules" to 09-id.md (ID-V1..V13)
│  │  - Constants: KEY_ID_SCOPE, KEY_ID_FORMAT_SCOPE
│  │  - checkForIdClassOnlyKeysOnReference() → ERROR (ID-V1..V10)
│  │  - checkForIdKeysOnAttribute() → ERROR (ID-V13)
│  │  - checkForIdRuntimeOnlyKeys() (class + feature overloads) → WARNING (ID-V11/V12)
│  │  - isIdConfigKey(), isIdClassOnlyKey() helpers
│  │  - Note: idFormat and idKey ARE valid on EReference (presentation varies by context)
│  │
│  ── Reference: DONE ──
│  │  - Spec: Added §10 "Configuration Validation Rules" to 10-reference.md (R-V1..V4)
│  │  - Spec: Added §10.2 "Ownership Boundaries" documenting that inlineMapping.*,
│  │    fallbackStrategy, fallbackEClass belong to Discriminator Mapping (08-discriminator-mapping.md)
│  │  - checkForReferenceOnlyKeysOnClass() → WARNING (R-V1/R-V3)
│  │  - checkForReferenceOnlyKeysOnAttribute() → ERROR (R-V2/R-V4)
│  │  - isRefConfigKey() helper
│  │
│  ALL TESTS PASSING
│
---

COMPLETED: 09-id.md vs 16-annotation-reference.md Gap Analysis & Fixes - ✅ (2026-01-29)
│
│  GOAL: Verify 09-id.md covers all properties from 16-annotation-reference.md ID section,
│        fix contradictions, add ser/deser flow diagrams (like 06-type.md).
│
│  FINDINGS & FIXES:
│  1. Severity mismatch: idScope/idFormatScope in EAnnotation
│     - 09-id.md said WARNING, 16-annotation-reference.md said ERROR
│     - Fix: Aligned 16-annotation-reference.md to WARNING (matches type spec pattern + implementation)
│
│  2. IdKeyMode.NONE missing from 09-id.md
│     - 16-annotation-reference.md had NONE; 09-id.md §2 did not
│     - Fix: Added NONE to metadata.ecore IdKeyMode enum (value=3)
│     - Fix: Added NONE to 09-id.md §2 table + note
│     - Fix: Added test model entry (PersonWithIdKeyModeNone) + parsing test
│
│  3. STRUCTURED format example contradiction
│     - 09-id.md §3.3 showed feature name ("myId") as inner key
│     - 16-annotation-reference.md showed idValueKey default ("id") as inner key
│     - Fix: Corrected §3.3, §3.4, §6 Example 2, §7 Complete Examples Summary
│     - Added notes explaining idValueKey role + IdKeyMode interaction
│
│  4. §7 expanded to full IdKeyMode × Format matrix (matching 16-annotation-reference.md)
│     - All 4 IdKeyMode values × PLAIN/STRUCTURED for both single and multiple features
│     - Added NONE rows
│
│  5. Created §10 "ID Serialization Flow" (ASCII diagram, 6 steps)
│     - Steps: useId check → NONE check → resolve features → determine value → apply format → apply keyMode → field ordering
│
│  6. Created §11 "ID Deserialization Flow" (ASCII diagram, 4 steps)
│     - Steps: locate ID field → detect format → resolve features → parse value (PLAIN/STRUCTURED)
│
│  7. Renumbered sections: §10→Ser Flow, §11→Deser Flow, §12→Defaults, §13→Validation Rules
│
│  8. Added §6 Example 4 (NONE mode)
│
│  FILES MODIFIED:
│  - docs/codec-v2-spec/09-id.md (major update)
│  - docs/codec-v2-spec/16-annotation-reference.md (severity fix)
│  - org.eclipse.fennec.model.metadata/model/metadata.ecore (IdKeyMode.NONE)
│  - test-codec-annotations.ecore (PersonWithIdKeyModeNone)
│  - CodecAspectProviderValidConfigTest.java (NONE parsing test)
│
│  ALL TESTS PASSING
│
---

COMPLETED: Remove useId/useType boolean toggles from spec - ✅ (2026-01-29)
│
│  RATIONALE: useId=false is semantically identical to IdKeyMode.NONE.
│             useType=false is semantically identical to TypeStrategy.NONE.
│             Having both a boolean toggle AND an enum value for the same behavior
│             is redundant. The enum approach is more explicit and avoids confusion.
│
│  CHANGES:
│  - 09-id.md: Removed useId from §8.3 scope table (replaced with idKeyMode),
│    §8.6 dependency table (now uses idKeyMode=NONE), §10 ser flow (step 1 only checks NONE)
│  - 09-id.md §8 alignment table: "useType/useId" row → "TypeStrategy.NONE/IdKeyMode.NONE"
│  - 02-config-resolution.md: Builder example .useId(true)→.idKeyMode(ID_ONLY),
│    removed .useType(true) (redundant with .typeStrategy(NAME))
│  - 17-format-abstraction.md: Builder example .useId(false)→.idKeyMode(IdKeyMode.NONE)
│  - Added migration note in §8.6 explaining the v1→v2 change
│
│  NOTE: Old v2 CodecConfiguration.java still has useId field (not modified per
│        refactoring plan rules — new code uses IdKeyMode.NONE instead)
│
---

COMPLETED: ValueWriter/ValueReader Priority in Ser/Deser Flows - ✅ (2026-01-29)
│
│  RATIONALE: Custom ValueWriter/ValueReader should be full delegation — when set,
│             they take over entirely (have the generator/parser + config).
│             NONE is an early break from effective config (known before JSON parsing).
│             ValueReader/Writer comes right after NONE, before any built-in logic.
│
│  PRIORITY CHAINS (all flows updated):
│
│  ── TYPE SERIALIZATION (06-type.md §5.0) ──
│  1. Discriminator Mapping (on EClass)
│  2. Inline Mapping (on EReference)
│  3. NONE check → early break
│  3a. ValueWriter (if set → full delegation → DONE)
│  3b-3c. Built-in logic (strategy → value → format → write)
│
│  ── TYPE DESERIALIZATION (06-type.md §6.3.0) ──
│  1. Discriminator Mapping (on EClass)
│  2. Inline Mapping (on EReference)
│  3. NONE check → early break (skip to fallback)
│  3a. ValueReader (if set → full delegation → DONE)
│  3b. Format detection
│  3c. Built-in extraction (strategy-dependent)
│  3d. Resolve EClass
│  4. Fallback hints
│
│  ── ID SERIALIZATION (09-id.md §10) ──
│  1. NONE check → early break
│  2. ValueWriter (if set → full delegation → DONE)
│  3. Resolve features
│  4-5. Built-in logic (strategy → value → format → keyMode)
│  6. Field ordering (idOnTop)
│
│  ── ID DESERIALIZATION (09-id.md §11) ──
│  1. NONE check → early break
│  2. ValueReader (if set → full delegation → DONE)
│  3. Locate ID field (idKey)
│  4. Format detection
│  5. Resolve features + parse value
│
│  NOTE: NONE + ValueWriter/Reader configured = NONE wins (disabled is disabled).
│
│  TESTS ADDED: TypeConfigSpecTest Section 7 (9 tests for ValueWriter/ValueReader
│  flow effectiveness at config level) - all passing
│
---

COMPLETED: idOnTop Runtime Ordering Constraint Documentation - ✅ (2026-01-29)
│
│  RATIONALE: idOnTop is configured on ID config but controls the orchestrator
│             (CodecEObjectSerializer) invocation order of both Type and ID entries.
│             This was only documented in 09-id.md §8.7 but had no mention in
│             06-type.md, 01-architecture.md, or 05-global-options.md.
│
│  CHANGES:
│  - 06-type.md: Added §5.0.2 "Runtime Ordering Constraint (idOnTop)"
│  - 09-id.md §8.7: Renamed to "Runtime Orchestration Constraint", updated table
│  - 01-architecture.md §4: Added metadata field ordering as orchestrator concern
│  - 05-global-options.md §3: Fixed ordering list for idOnTop awareness
│
---

COMPLETED: Cross-Verification of Spec Docs 06-09 vs 16-annotation-reference.md - ✅ (2026-01-29)
│
│  GOAL: Verify docs 06-type.md, 07-supertype.md, 08-discriminator-mapping.md,
│        09-id.md are consistent with 16-annotation-reference.md (leader doc).
│
│  FINDINGS & FIXES:
│
│  1. CONTRADICTION: idOnTop default
│     - 06-type.md §5.0.2 and 09-id.md §8.7 said "true (default)"
│     - 09-id.md §5.0, §12, and 16-ref said "false"
│     - Fix: Aligned ALL to "false" across 06-type.md, 09-id.md, 01-architecture.md,
│       05-global-options.md (16-ref was already correct)
│
│  2. GAP: 06-type.md §3.1 missing typeValueReaderName/typeValueWriterName
│     - Flow diagrams referenced them but config keys table did not
│     - Fix: Added both keys to §3.1 with cross-references to flow steps
│
│  3. GAP: 07-supertype.md §6.1 missing superTypeValueReaderName/superTypeValueWriterName
│     - Flow sections referenced them but config keys table did not
│     - Fix: Added both keys to §6.1 with cross-references to flow steps and §6.0.3
│
│  4. MINOR: 08-discriminator-mapping.md §1.3 broken anchor
│     - Referenced #530-type-resolution-flow (nonexistent)
│     - Fix: Corrected to #630-type-resolution-flow and fixed link text
│
│  5. CLEANUP: Removed deprecated typeInclude entirely from ALL spec docs
│     - Primary: 06-type.md (§1.4 note, §3.1 row, §4 note, §7.4 rules, §8 section)
│     - Primary: 16-annotation-reference.md (config table, invalid configs, impl status)
│     - Secondary: 02-config-resolution.md, 19-test-coverage.md, 21-type-config-validation-rules.md
│     - Zero typeInclude references remain in entire spec
│
│  CLEAN MATCHES (no issues found):
│  - All TypeStrategy/IdStrategy/IdKeyMode/SuperTypeStrategy values
│  - All scope levels (Global/EClass/ERef/EAttr)
│  - All invalid configuration tables
│  - All flow diagrams internally consistent
│  - Discriminator Mapping properties, annotation sources, fallback handling
│  - ValueWriter/ValueReader full delegation pattern across all flows
│  - NONE as hard disable consistent across Type and ID
│
│  FILES MODIFIED:
│  - 06-type.md, 07-supertype.md, 08-discriminator-mapping.md, 09-id.md
│  - 01-architecture.md, 05-global-options.md
│  - 16-annotation-reference.md
│  - 02-config-resolution.md, 19-test-coverage.md, 21-type-config-validation-rules.md

---

COMPLETED: ID Spec Tests + Bug Fix (codec.api + codec.metadata audit) - ✅ (2026-01-29)
│
│  GOAL: Create comprehensive ID spec tests at codec.api level and verify
│        codec.metadata level coverage is complete.
│
│  BUG FIX: IdConfig.java Builder had swapped ConfigProperty defaults
│  │  - Line 325: valueWriterName was initialized from ID_VALUE_READER_NAME (wrong)
│  │  - Line 326: valueReaderName was initialized from ID_VALUE_WRITER_NAME (wrong)
│  │  - Impact: None (both defaults are null), but semantically incorrect
│  │  - Fix: Swapped to correct ConfigProperty references
│
│  NEW TEST FILE: IdConfigSpecTest.java (56 tests)
│  │  Location: org.eclipse.fennec.codec.api/test/.../config/spec/IdConfigSpecTest.java
│  │  Sections:
│  │  1. Default Values (spec §12) — 12 tests
│  │  2. IdStrategy Values (spec §5.0) — 3 tests
│  │  3. IdKeyMode Values (spec §2) — 4 tests
│  │  4. Format × Strategy Combinations (spec §3) — 9 tests
│  │  5. Validation Rules (spec §13.1) — 6 tests
│  │  6. Merge Behavior — 8 tests
│  │  7. ValueWriter/ValueReader Flow Effectiveness (spec §10/§11) — 9 tests
│  │  8. idOnTop Ordering Constraint (spec §8.7) — 5 tests
│
│  ENHANCED: ConfigurationResolverSpecTest.java (ID tests later extracted to IdConfigResolverSpecTest)
│  │  Note: Originally added as section in ConfigurationResolverSpecTest, later extracted
│  │  to standalone IdConfigResolverSpecTest.java (17 tests) during per-config-type refactoring.
│  │  See "SuperType + Discriminator Resolver Spec Tests + Refactoring" task for details.
│
│  CODEC.METADATA AUDIT: Already comprehensive ✅
│  │  - CodecAspectProvider.buildIdConfig() parses all 14 annotation keys
│  │  - CodecAspectProviderValidConfigTest → IdConfigTests: 7 tests (all strategies, formats,
│  │    key modes, value key, custom reader/writer, structured config)
│  │  - CodecAspectProviderMisconfigTest: 5+ ID validation tests (ID-V1..V13)
│  │  - test-codec-annotations.ecore: 8+ EClasses with ID annotation fixtures
│  │  - No gaps found — Level 1 annotation parsing fully covered
│
│  TOTAL ID TEST COVERAGE:
│  │  codec.api:
│  │  - IdConfigSpecTest: 56 spec tests (NEW)
│  │  - IdConfigTest: 27 unit tests (existing)
│  │  - ConfigurationResolverSpecTest: 17 ID tests (NEW)
│  │  - ConfigurationResolverTest: 2 ID tests (existing)
│  │  codec.metadata:
│  │  - CodecAspectProviderValidConfigTest: 7 ID tests (existing)
│  │  - CodecAspectProviderMisconfigTest: 5+ ID validation tests (existing)
│  │
│  ALL TESTS PASSING (both codec.api and codec.metadata)
│
---

COMPLETED: Prepare spec for TCK test creation (Type + SuperType configuration) - ✅
│
│  GOAL: Verify configuration merging works correctly by:
│        1. Clarifying spec until Claude can explain ser/deser behavior (and WHY)
│        2. Documenting property constraints and dependencies
│        3. Creating ser/deser flows that are testable
│        4. Then create spec tests that verify the effective configuration
│
│  KEY DOCUMENTS:
│  - 06-type.md (ser/deser flows created)
│  - 07-supertype.md (coupled to type, needs flow integration)
│  - 08-discriminator-mapping.md (referenced)
│  - 16-annotation-reference.md (property definitions)
│  - 21-type-config-validation-rules.md (constraints)
│
├── CHILD: Type spec clarification (06-type.md) - ✅
│   ├── Created serialization flow (Section 5) - ✅
│   ├── Created deserialization flow (Section 6) - ✅
│   ├── Fix: Remove deprecated typeInclude property - ✅ (fully removed from spec 2026-01-29)
│   ├── Fix: TypeStrategy.NONE semantics - ✅
│   └── Fix: fallbackStrategy default (FALLBACK → SKIP) - ✅
│
├── CHILD: SuperType spec clarification (07-supertype.md) - ✅ COMPLETE
│   ├── Fix: SuperTypeSelection default (SINGLE → ALL) - ✅
│   ├── Fix: Chapter references (09 → 07) - ✅
│   ├── Fix: Remove superTypeSchemaKey (inherits from TypeConfig) - ✅
│   ├── Fix: Remove superTypeNameKey (superTypeKey has format-dependent default) - ✅
│   ├── Fix: Replace superTypeValidate with DeserializationMode.STRICT - ✅
│   ├── Verify: SuperTypeConfig completeness - ✅
│   │
│   └── DONE: Integrate SuperType into Type ser/deser flows - ✅
│       - Serialization: step 3d added to 06-type.md (after type, respects format)
│       - Constraint: typeStrategy=NONE + typeFormat=STRUCTURED + superTypeSerialize=true → ERROR
│       - Deserialization: step 5 added to 06-type.md (after type resolution)
│       - Smart compression: uses ROOT schema (same as type)
│       - superTypeKey format-dependent default: PLAIN→"_supertype", STRUCTURED→"supertype"
│
├── CHILD: Validation rules documentation - ✅ COMPLETE
│   ├── Added Section 7 "Configuration Validation Rules" to 06-type.md - ✅
│   ├── Cross-references to 07-supertype.md §6.0 and 08-discriminator-mapping.md - ✅
│   └── Deprecated document 21 (redirects to authoritative sources) - ✅
│
└── NEXT STEP: Create TCK/spec tests - READY TO START
    │
    │  Test Categories:
    │  1. Configuration-level tests (no serialization needed)
    │     - Default values match spec
    │     - Invalid config combinations → correct ERROR/WARNING
    │     - Property applicability (wrong levels → correct diagnostic)
    │     - TypeStrategy.NONE disabling behavior
    │  2. Serialization tests (later phase)
    │     - Strategy × Format matrix output verification
    │     - SuperType integration with Type
    │     - Discriminator mapping behavior
    │
    │  Start with: TypeConfig + SuperTypeConfig validation tests
```

### 0.3 Task History (Completed Hierarchies)

*Move completed main tasks here for reference.*

---

## 1. Development Workflow (MUST FOLLOW)

> **Note:** Previous section numbering started at 0. This section was previously "Section 0".

### Testing Commands (CRITICAL)

**Codec V2 uses JUnit 5 tests, NOT OSGi tests!**

```bash
# Codec V2 projects - use these:
./gradlew :org.eclipse.fennec.codec.api:test           # Config classes (~530+ tests)
./gradlew :org.eclipse.fennec.codec.v2:test
./gradlew :org.eclipse.fennec.codec.metadata:test
./gradlew :org.eclipse.fennec.model.metadata:test
./gradlew :org.eclipse.fennec.codec.jsonschema.v2:test
./gradlew :org.eclipse.fennec.codec.openapi:test

# Do NOT use testOSGi for v2 projects!
```

### Workflow Rules

1. **Specification First**
   - Check spec (`docs/codec-v2-spec/`) before implementing
   - Spec is the source of truth - clarify gaps before coding
   - Never implement something that contradicts the spec

2. **Test-Driven Development**
   - Create tests BEFORE implementation
   - Tests must FAIL before implementation (proves test validity)
   - Tests must PASS after implementation

3. **Failing Tests = Investigation Required**
   - **NEVER change tests just to make them pass**
   - Investigate root cause: implementation bug? side effect? spec gap?
   - Even "unrelated" failures may reveal important side effects

4. **Spec Completeness Requirements**
   - Serialization vs Deserialization: clarify when each config applies
   - Examples required for: property maps, config builder, annotations
   - Default behavior must be documented
   - Override behavior must be explicit
   - Error cases must be documented

5. **Configuration Hierarchy Consistency**
   - Every ANNOTATION feature → MUST have property/config builder equivalent
   - NOT every property → needs annotation (some are runtime-only)
   - Ask about contradictions or gaps - don't assume

6. **Code Quality**
   - Use imports, NEVER fully qualified class names
   - Look for `@claude`/`@CLAUDE` comments - these are instructions

### Available Agents

Agents in `.claude/agents/` help enforce this workflow:

| Agent | Purpose |
|-------|---------|
| `spec-validator` | Validate changes against spec, reveal gaps/contradictions |
| `test-runner` | Run JUnit tests (not OSGi!), investigate failures |
| `example-curator` | Identify good test examples for documentation |
| `error-hardening` | Find misconfiguration cases, harden error handling |
| `doc-updater` | Update docs at session end |
| `code-reviewer` | Check imports, @claude comments, quality |

---

## 1. Project Overview

We are building **codec.v2**, a new EMF serialization codec based on the specification in the [`codec-v2-spec/`](codec-v2-spec/) folder. The goal is to create a clean, well-structured implementation that follows the configuration hierarchy defined in the spec.

**Specification Documents:**
- [Overview & TOC](codec-v2-spec/00-overview.md) - Start here
- [Architecture](codec-v2-spec/01-architecture.md) - Component overview (foundational)
- [Configuration Resolution](codec-v2-spec/02-config-resolution.md) - How config is resolved
- [Common Types](codec-v2-spec/04-common-types.md) - SerializationFormat, TypeStrategy, etc.
- [Global Options](codec-v2-spec/05-global-options.md) - Smart compression, field ordering
- [Type](codec-v2-spec/06-type.md), [SuperType](codec-v2-spec/07-supertype.md), [ID](codec-v2-spec/09-id.md), [Reference](codec-v2-spec/10-reference.md) - Core serialization targets
- [Custom Values](codec-v2-spec/15-custom-values.md) - Custom value readers/writers
- [Format Abstraction](codec-v2-spec/17-format-abstraction.md) - Multi-format support (JSON, BSON, CSV, OpenAPI)

### Key Projects

| Project | Purpose | Status | Architecture Doc |
|---------|---------|--------|------------------|
| `org.eclipse.fennec.codec.api` | Config classes (Mergeable), diagnostic API | ✅ Complete | See §10 below |
| `org.eclipse.fennec.model.metadata` | Generic MetadataService infrastructure | ✅ Complete | [model-metadata-architecture.md](../org.eclipse.fennec.model.metadata/model-metadata-architecture.md) |
| `org.eclipse.fennec.codec.metadata` | Codec-specific aspects and annotation parsing | ✅ Complete | [codec-metadata-architecture.md](../org.eclipse.fennec.codec.metadata/codec-metadata-architecture.md) |
| `org.eclipse.fennec.codec.v2` | New codec implementation | ✅ Phase 3 Complete | See [00-overview.md](codec-v2-spec/00-overview.md) |
| `org.eclipse.fennec.codec.jsonschema.v2` | JSON Schema ↔ EPackage converters | ✅ Complete | See [17-format-abstraction.md](codec-v2-spec/17-format-abstraction.md) |
| `org.eclipse.fennec.codec.openapi` | OpenAPI 3.x resource with JSON Schema support | ✅ Complete | See [17-format-abstraction.md](codec-v2-spec/17-format-abstraction.md) |
| `org.eclipse.fennec.codec.v2.example` | Examples and integration tests | Not started | - |
| `org.eclipse.fennec.codec.*` (other) | Old codec implementations (reference only) | Existing | - |

### What's Old vs New

- **OLD** (`org.eclipse.fennec.codec.info*`): Old ModelInfoService that parses models - partially spec-compliant
- **NEW** (`org.eclipse.fennec.model.metadata` + `org.eclipse.fennec.codec.metadata`): Clean implementation following spec

---

## 2. Architecture

For detailed architecture, see:
- **[model-metadata-architecture.md](../org.eclipse.fennec.model.metadata/model-metadata-architecture.md)** - Core MetadataService, Aspect pattern, resolution strategy
- **[codec-metadata-architecture.md](../org.eclipse.fennec.codec.metadata/codec-metadata-architecture.md)** - Codec aspects, EAnnotation mapping, value readers/writers

### 2.1 Two-Layer Design

```
┌─────────────────────────────────────────────────────────────────┐
│                    org.eclipse.fennec.codec.v2                   │
│                  (Codec implementation - uses metadata)          │
└───────────────────────────────┬─────────────────────────────────┘
                                │
┌───────────────────────────────▼─────────────────────────────────┐
│                 org.eclipse.fennec.codec.metadata                │
│          (Codec-specific aspects and annotation parsing)         │
│                                                                  │
│   - ClassCodecAspect, FeatureCodecAspect, ReferenceCodecAspect  │
│   - TypeSerializationConfig, IdSerializationConfig, etc.        │
│   - Annotation parsing (codec.type, codec.id, codec.reference)  │
│   - Default value handling from spec                            │
└───────────────────────────────┬─────────────────────────────────┘
                                │
┌───────────────────────────────▼─────────────────────────────────┐
│                 org.eclipse.fennec.model.metadata                │
│              (Generic MetadataService infrastructure)            │
│                                                                  │
│   - PackageMetadata, ClassMetadata, FeatureMetadata             │
│   - Aspect, ClassAspect, FeatureAspect (abstract bases)         │
│   - MetadataService API                                         │
│   - TypeDiscriminatorRegistry                                    │
└─────────────────────────────────────────────────────────────────┘
```

### 2.2 MetadataService Role

The MetadataService is a **dynamic component** that:
1. Listens to EMF model registry for EPackage changes
2. Parses EAnnotations from registered EPackages
3. Creates and caches metadata (aspects) for each EClass/EStructuralFeature
4. Provides pre-computed metadata to CodecResourceFactory

```
EPackage registered → MetadataService triggered →
  Parse EAnnotations → Apply defaults → Create Aspects → Cache
```

### 2.3 EMF Aspect Pattern (now in Ecore)

Aspects are now defined as EMF classes in the Ecore models:

```
metadata.ecore (generic):
  Aspect (abstract)
  ├── ClassAspect (abstract)     - attached to ClassMetadata.aspects
  └── FeatureAspect (abstract)   - attached to FeatureMetadata.aspects

codec.ecore (codec-specific):
  ClassCodecAspect extends ClassAspect
    - typeConfig: TypeSerializationConfig
    - idConfig: IdSerializationConfig
    - superTypeConfig: SuperTypeSerializationConfig
    - discriminatorValue, inheritFromParent

  FeatureCodecAspect extends FeatureAspect
    - effectiveKey, serialize, serializeNull/Empty/Defaults
    - valueWriterName, valueReaderName

  ReferenceCodecAspect extends FeatureCodecAspect
    - referenceConfig: ReferenceSerializationConfig
    - typeConfig: TypeSerializationConfig
    - inheritTypeFromTarget, expand
```

---

## 3. Configuration Hierarchy

From the spec (highest to lowest priority):

| Priority | Level | Scope | Description |
|----------|-------|-------|-------------|
| 1 (highest) | **Load/Save options** | Per-operation | `resource.save(options)` / `resource.load(options)` |
| 2 | **ResourceFactory defaults** | Per-factory | `defaultSaveOptions`/`defaultLoadOptions` |
| 3 | **Codec module config** | Per-codec | Jackson module configuration |
| 4 | **Configuration properties** | External | System properties, config files |
| 5 | **EAnnotations** | Per-model | Declared in .ecore model (static) |
| 6 (lowest) | **Built-in defaults** | Global | Hardcoded codec defaults |

**Current focus**: Implementing Level 5 (EAnnotation parsing) and Level 6 (built-in defaults).

---

## 4. EAnnotation Specification

### 4.1 Annotation Source

All codec annotations use source: `http://eclipse.org/fennec/codec`

### 4.2 Annotation Markers (Detail Keys)

| Marker | Applies To | Purpose |
|--------|------------|---------|
| `codec.type` | EClass, EReference | Type serialization config |
| `codec.id` | EClass | ID serialization config |
| `codec.reference` | EReference | Reference serialization config |
| `codec.supertype` | EClass | Supertype serialization config |
| `serialize` | EStructuralFeature | Whether to serialize (false = transient) |
| `key` | EStructuralFeature | Custom JSON property name |
| `valueWriterName` | EStructuralFeature | Custom value writer |
| `valueReaderName` | EStructuralFeature | Custom value reader |

### 4.3 Built-in Defaults (from spec)

**Type Configuration:**
```
strategy = URI
typeKey = "_type"
format = PLAIN
schemaKey = "schema"
nameKey = "type"
```

**ID Configuration:**
```
strategy = ID_FIELD  (or NONE if no ID attribute)
idKey = "_id"
separator = "/"
keyMode = ID_ONLY
format = PLAIN
```

**Reference Configuration:**
```
format = PLAIN
typeKey = "_type"
refKey = "_ref"
includeType = true
```

**SuperType Configuration:**
```
enabled = false
selection = ALL
superTypeKey = "_supertype"
format = PLAIN
```

**Feature Configuration:**
```
serialize = true
serializeNull = false
serializeDefaults = false
serializeEmpty = false
```

---

## 5. Current State

> **For test coverage details, see [15-test-coverage.md](codec-v2-spec/19-test-coverage.md)**

### 5.1 Implementation Phases

| Phase | Description | Status |
|-------|-------------|--------|
| **Phase 1** | Core Serialization - End-to-end round-trip, basic type/ID/reference handling, Jackson integration | ✅ Complete |
| **Phase 2** | Configuration - EAnnotation-based config, discriminator mapping, smart compression, array roots | ✅ Complete |
| **Phase 3** | Advanced Features - Custom value readers/writers, additional TypeStrategies, polymorphic lists, circular references | ✅ Complete |
| **Phase 4** | Production Readiness - Performance optimization, OSGi integration, cross-resource references | In Progress |

### 5.2 Detailed Implementation Checklist

**Core**
- [x] CodecResource implementation
- [x] ConfigurationMerger
- [x] EffectiveCodecConfig with lazy caching
- [x] EMFContextHolder using EffectiveCodecConfig
- [x] Jackson context integration

**Type Serialization (Format × Strategy Matrix)**

PLAIN format:
- [x] URI strategy (default) - ser + deser
- [x] NAME strategy - ser + deser
- [x] CLASS strategy - ser only
- [x] NUMERIC strategy - ser + deser
- [x] SCHEMA_AND_TYPE strategy - two separate fields (`_schema` + `_type`) - ser + deser
- [x] NONE strategy - no type field

STRUCTURED format:
- [x] SCHEMA_AND_TYPE - `{"schema":"...","type":"..."}` - ser + deser
- [x] URI - `{"uri":"<uri>"}` - ser + deser
- [x] NAME - `{"type":"<name>"}` - ser + deser
- [x] CLASS - `{"class":"<class>"}` - ser only
- [x] NUMERIC - `{"schema":"...","classifier":N}` - ser + deser

**ID Serialization**
- [x] Single ID field
- [x] Multiple ID fields with separator
- [x] PLAIN format
- [x] STRUCTURED format
- [x] ID_ONLY, BOTH, FEATURE_ONLY modes
- [x] Separator serialization (both formats)

**Reference Serialization**
- [x] Non-containment references
- [x] STRUCTURED format (default)
- [x] PLAIN format
- [x] Cross-document containment detection
- [x] Proxy creation
- [x] Expand feature (serialization + deserialization)
- [x] Proxy with projection

**Features**
- [x] EAttribute serialization
- [x] EReference serialization
- [x] Null/default/empty handling
- [x] Key customization
- [x] Enum serialization options (LITERAL, NAME, VALUE strategies)
- [x] Extended metadata names
- [x] EJavaObject attributes (any JSON value → native Java types)
- [x] JSON-to-String conversion for EString attributes (deserialization only)

**Global Options**
- [x] Smart compression (type omission)
- [x] Smart compression (same-schema names)
- [ ] Field ordering
- [x] Global feature ignore list

**SuperType Serialization**
- [x] ALL, SINGLE, ALL_EMF, NONE selection modes
- [x] ARRAY presentation (default)
- [x] STRING presentation (configurable separator)
- [x] PLAIN format (standalone `_supertype` field)
- [x] STRUCTURED format (inside `_type` object as `supertype` field)
- [x] Deserialization with optional validation

**Advanced**
- [ ] Cross-resource references (ResourceSet-based resolution)
- [x] Custom value readers/writers (unified interface)
- [x] Annotation inheritance

### 5.3 OpenAPI/JSON Schema Support

**Implementation Complete ✅**

| Feature | Status |
|---------|--------|
| JSON Schema → EPackage conversion | ✅ |
| EPackage → JSON Schema conversion | ✅ |
| OpenAPI 3.x resource implementation | ✅ |
| Real-world roundtrip tests | ✅ |

**OpenAPI Roundtrip Results:**

| File | Size | Schemas | Paths | Operations | Field Preservation |
|------|------|---------|-------|------------|-------------------|
| petstore.json | 45 KB | 6 → 6 (100%) | 13 | 19 | 92.6% |
| bike.json | 99 KB | 43 → 43 (100%) | 13 | 14 | 90.8% |
| sevdesk.json | 677 KB | 74 → 74 (100%) | 117 | 151 | 87.6% |
| kubernetes-api.json | 1944 KB | 249 → 249 (100%) | 113 | 248 | 86.7% |

### 5.4 Remaining Work (Phase 4)

| Feature | Priority | Description |
|---------|----------|-------------|
| OSGi Integration | High | CodecResourceFactory service registration |
| Cross-Resource References | Medium | ResourceSet-based resolution |
| Feature Type Hints | Medium | `CODEC_FEATURE_TYPE_HINTS` load option |
| Feature Value Readers | Medium | `CODEC_FEATURE_VALUE_READERS` load option |
| Feature Value Writers | Medium | `CODEC_FEATURE_VALUE_WRITERS` save option |
| Performance Optimization | Low | Large object graph handling |

### 5.5 Deferred

- Expand Depth > 1
- Field ordering options
- Streaming serialization

### 5.6 Spec Review TODOs (from 2026-01-25 session) - ✅ COMPLETED

The following items were identified during the Type Serialization/Deserialization flow documentation work:

| TODO | Description | Status |
|------|-------------|--------|
| **Update codec.ecore** | Change `fallbackStrategy` default from `FALLBACK` to `SKIP` in the EMF model | ✅ Done (2026-01-26) |
| **Spec Text Review** | Verify all text in `06-type.md`, `08-discriminator-mapping.md` aligns with the new serialization/deserialization flowcharts | ✅ Done (2026-01-26) |
| **Cross-reference Check** | Ensure `16-annotation-reference.md` and `21-type-config-validation-rules.md` reflect the corrected fallbackStrategy default | ✅ Done (2026-01-26) |
| **Smart Compression in Deserialization** | Added step 3b' to deserialization flow documenting smart compression expansion | ✅ Done (2026-01-26) |

**Changes made (2026-01-26):**
- Updated `codec.ecore`: FallbackStrategy enum reordered (SKIP=0, ERROR=1, FALLBACK=2), defaults changed to SKIP
- Updated `16-annotation-reference.md`: Corrected all fallbackStrategy defaults and documentation
- Added step 3b' to `06-type.md` deserialization flow for smart compression expansion
- All spec documents now aligned with flowcharts

**Context (from 2026-01-25):**
- Added comprehensive serialization flow (Section 5 in `06-type.md`)
- Updated deserialization flow with corrected fallbackStrategy behavior (Section 6 in `06-type.md`)
- Changed resolution priority: Type Mapping Registry → Inline Mapping → Type Strategy → Fallback
- Changed fallbackStrategy default from FALLBACK to SKIP
- FALLBACK now requires `fallbackEClass` to be set (else ERROR)
- Added typeValueReaderName/typeValueWriterName hooks to the flows

---

## 6. Key Files Reference

### Specifications and Documentation
- `docs/codec-v2-serialization-spec.md` - **Source of truth** for all configuration
- `docs/codec-v2-development-guide.md` - This document

### EMF Models
- `org.eclipse.fennec.model.metadata/model/metadata.ecore` - Generic metadata infrastructure
- `org.eclipse.fennec.codec.metadata/model/codec.ecore` - Codec-specific aspects and configs

### Codec V2 Implementation

**Serialization:**
- `org.eclipse.fennec.codec.v2.ser.CodecEObjectSerializer` - Main EObject serializer
- `org.eclipse.fennec.codec.v2.ser.SerializationEntry` - Entry interface
- `org.eclipse.fennec.codec.v2.ser.*SerializationEntry` - Specific entry implementations

**Deserialization:**
- `org.eclipse.fennec.codec.v2.deser.CodecEObjectDeserializer` - Main EObject deserializer
- `org.eclipse.fennec.codec.v2.deser.DeserializationEntry` - Entry interface
- `org.eclipse.fennec.codec.v2.deser.*DeserializationEntry` - Specific entry implementations
- `org.eclipse.fennec.codec.v2.deser.DeserializationState` - Tracks deserialization state

**Configuration:**
- `org.eclipse.fennec.codec.v2.config.CodecConfiguration` - Module configuration builder
- `org.eclipse.fennec.codec.v2.config.effective.*` - Pre-merged immutable configs
- `org.eclipse.fennec.codec.v2.config.effective.ConfigurationMerger` - Config resolution

**Resource:**
- `org.eclipse.fennec.codec.v2.resource.CodecResource` - EMF Resource implementation

**Module:**
- `org.eclipse.fennec.codec.v2.module.CodecModule` - Jackson module

**Utilities:**
- `org.eclipse.fennec.codec.v2.util.MetadataServiceFactory` - Factory for creating MetadataService with CodecAspectProvider
- `org.eclipse.fennec.codec.v2.util.CodecDiagnostic` - EMF Resource.Diagnostic implementation for error/warning reporting
- `org.eclipse.fennec.codec.v2.util.DiagnosticCollector` - Aggregates diagnostics during serialization/deserialization
- `org.eclipse.fennec.codec.v2.context.ContextHelper` - Context attribute management and diagnostic helper methods

**Tests:**
- `org.eclipse.fennec.codec.v2.resource.CodecResourceRoundTripTest` - Round-trip integration tests
- `org.eclipse.fennec.codec.v2.resource.CodecResourceAnnotationTest` - EAnnotation-based configuration tests
- `org.eclipse.fennec.codec.v2.ser.*Test` - Serialization unit tests
- `org.eclipse.fennec.codec.v2.deser.*Test` - Deserialization unit tests

### Metadata Infrastructure
- `org.eclipse.fennec.codec.metadata.provider.CodecAnnotationConstants` - Annotation keys and helper methods
- `org.eclipse.fennec.model.metadata.api.MetadataService` - Core metadata service interface
- `org.eclipse.fennec.model.metadata.service.MetadataServiceImpl` - Default implementation

### Generated EMF Code
- `org.eclipse.fennec.codec.metadata/src-gen/` - Generated interfaces and implementations for codec.ecore
- `org.eclipse.fennec.model.metadata/src-gen/` - Generated code for metadata.ecore

---

## 7. Spec Review Findings & Working Documents

A comprehensive review of the specification was conducted on 2026-01-20. The findings and ongoing work are documented in:

### 7.0 Key Reference Documents

| Document | Purpose | Status |
|----------|---------|--------|
| **[codec-v2-spec-review-findings.md](codec-v2-spec-review-findings.md)** | Comprehensive spec review results | Reference |
| **[16-annotation-reference.md](codec-v2-spec/16-annotation-reference.md)** | **Definitive reference** - all config options at all scope levels | ✅ Spec |
| **[99-open-questions.md](codec-v2-spec/99-open-questions.md)** | Design questions + implementation roadmap | ✅ Spec |
| **[epackage-scope-proposal.md](codec-v2-spec-working/epackage-scope-proposal.md)** | EPackage scope level proposal | Working |
| **[emf-configuration-model-proposal.md](codec-v2-spec-working/emf-configuration-model-proposal.md)** | EMF config model proposal | Working |

### 7.0.1 Annotation & Configuration Reference (IMPORTANT)

The **[16-annotation-reference.md](codec-v2-spec/16-annotation-reference.md)** document defines:

- **Which annotation keys are valid at each EMF element level** (EPackage, EClass, EReference, EAttribute)
- **Override behavior** (EReference → EClass → Global → Default)
- **Error cases** for misplaced annotations
- **Scope settings** (global-only, not in EAnnotations)

**Key Principles:**
1. **ID Configuration** → EClass only (identity doesn't change based on access path)
2. **Type Configuration** → EClass + EReference (strategy is class-level, format/keys can vary per-reference)
3. **SuperType Configuration** → EClass only (inheritance is intrinsic to the class)
4. **Reference Configuration** → EReference only (presentation concern for that specific reference)
5. **Feature Configuration** → EReference + EAttribute (per-feature settings)
6. **Scope Settings** → Global/CodecConfig only (NOT in EAnnotations)

### Session 2026-01-28: ConfigurationResolver Created

**Gap Closed: The missing ConfigurationMerger replacement is now complete.**

The deprecated `ConfigurationMerger` in `org.eclipse.fennec.codec.v2.config.effective` has been replaced by:

| New Class | Purpose |
|-----------|---------|
| `org.eclipse.fennec.codec.config.ConfigurationResolver` | Orchestrates cascading merge from all 6 configuration levels |

**What ConfigurationResolver does:**
1. Takes all 6 property map layers as input (OPTIONS → RESOURCE → FACTORY → MODULE → ANNOTATION → DEFAULT)
2. For each config type (TypeConfig, IdConfig, etc.), calls `.defaults().mergeWith(...).validate(...)`
3. Handles the two-dimensional resolution (Source × Level): vertical source priority + horizontal scope chain
4. Caches resolved configs per EClass/EStructuralFeature for efficiency

**Test Coverage:**
- `ConfigurationResolverTest.java` - 25+ unit tests
- `ConfigurationResolverSpecTest.java` - 20 spec tests (shared resolution mechanics)
- `IdConfigResolverSpecTest.java` - 17 spec tests (ID resolution)
- `SuperTypeConfigResolverSpecTest.java` - 21 spec tests (SuperType + Cross-Config validation)
- `DiscriminatorConfigResolverSpecTest.java` - 17 spec tests (Discriminator resolution)
- Total: 75 resolver spec tests validating spec section 02-config-resolution.md

**Usage:**
```java
ConfigurationResolver resolver = ConfigurationResolver.builder()
    .annotationProperties(annotationMap)      // Level 5
    .moduleProperties(moduleMap)              // Level 4
    .factoryProperties(factoryMap)            // Level 3
    .resourceProperties(resourceMap)          // Level 2
    .optionsProperties(optionsMap)            // Level 1 (highest)
    .build();

TypeConfig config = resolver.resolveTypeConfig(personClass, diagnostics);
FeatureConfig featureConfig = resolver.resolveFeatureConfig(firstNameAttr, diagnostics);
```

**Validation Gap Analysis (Spec vs Implementation):**

See [15-error-handling.md §0 "Validation Layers"](docs/codec-v2-spec/15-error-handling.md) for the complete validation architecture.

| Layer | Current Status | Where Implemented |
|-------|----------------|-------------------|
| **1. Annotation Parsing** | ⚠️ Partial | `CodecAspectProvider.checkForClassOnlyKeys()` |
| **2. Config Resolution (Self-Contained)** | ✅ Done | `Config.validate()` methods |
| **3. Cross-Config Validation** | ✅ Done | `ConfigurationResolver.validateCrossConfig()` |
| **4. Runtime (Ser/Deser)** | ❌ Not done | Serializer/Deserializer entries |

**Layer 1 Status (Annotation Parsing in `CodecAspectProvider`):**

| Validation | Status | Rule ID |
|------------|--------|---------|
| `typeMapId` on EReference → WARNING | ✅ Done | D-3 |
| `typeDiscriminatorPath` on EReference → WARNING | ✅ Done | D-2 |
| `typeValueReaderName` on EReference → ERROR | ✅ Done (2026-01-29) | T-V1 |
| `typeValueWriterName` on EReference → ERROR | ✅ Done (2026-01-29) | T-V2 |
| `typeScope` in EAnnotation → WARNING | ✅ Done (2026-01-29) | T-V3 |
| `typeFormatScope` in EAnnotation → WARNING | ✅ Done (2026-01-29) | T-V4 |
| Any `type*` key on EAttribute → ERROR | ✅ Done (2026-01-29) | T-V5 |
| `typeDiscriminator` on EReference → ERROR | ✅ Done (2026-01-29) | T-V7 |
| ~~`typeInclude` deprecation → WARNING~~ | ~~✅ Done~~ → Removed from spec (2026-01-29) | ~~T-V30~~ |
| ~~Both `typeInclude` + `typeStrategy` → WARNING~~ | ~~✅ Done~~ → Removed from spec (2026-01-29) | ~~T-V31~~ |
| `superType*` on EReference → ERROR | ✅ Done (2026-01-29) | ST-V1 |
| `superType*` on EAttribute → ERROR | ✅ Done (2026-01-29) | ST-V2 |
| `id*` class-only keys on EReference → ERROR | ✅ Done (2026-01-29) | ID-V1..V10 |
| `idScope`/`idFormatScope` in EAnnotation → WARNING | ✅ Done (2026-01-29) | ID-V11/V12 |
| `id*` on EAttribute → ERROR | ✅ Done (2026-01-29) | ID-V13 |
| `ref*` on EClass → WARNING | ✅ Done (2026-01-29) | R-V1 |
| `ref*` on EAttribute → ERROR | ✅ Done (2026-01-29) | R-V2 |
| `expand` on EClass → WARNING | ✅ Done (2026-01-29) | R-V3 |
| `expand` on EAttribute → ERROR | ✅ Done (2026-01-29) | R-V4 |

**Layer 1 Annotation Validation: COMPLETE ✅**

**Remaining Work (Layer 4 - Runtime):**
- Runtime serializer: CLASS strategy + instanceClassName null (T-V10)
- Runtime format-dependent warnings (T-V20, T-V21)
- SuperType cross-config constraints (07-supertype.md §6.0) - already in ConfigurationResolver

**Architecture:**
1. **Config classes** → Self-contained validation ✅
2. **ConfigurationResolver** → Merge orchestration + caching + cross-config validation ✅
3. **Annotation Parser** → Context-dependent validation (when EAnnotations → property maps) ❌
4. **Cross-Config validation** → Add to ConfigurationResolver ❌

**Next Steps:**
- Add cross-config validation to ConfigurationResolver (STRUCTURED + NONE + superTypeSerialize)
- Annotation parser will handle feature-level applicability when built

### Session 2026-01-20: Major Clarifications

| Topic | Decision | Section |
|-------|----------|---------|
| **Type Strategy Scope** | EReference → EClass → Global hierarchy with Scope options | §2, §3 |
| **SuperType** | Extension of Type (follows type format/scope) | §3.3 |
| **Underscore Convention** | PLAIN: `_type`, STRUCTURED inner: `type` (no underscore) | §7 |
| **TypeStrategy.NONE** | Added for no type information scenarios | §11.3 |
| **CODEC_TYPE_MODE** | HINT (default) vs OVERRIDE for type hints | §11.6 |
| **DeserializationMode** | STRICT, LENIENT (default), AUTO_DETECT | §11.7 |
| **MAPPED removed from TypeStrategy** | Now a separate Discriminator Mapping layer | §11.8 |
| **Inline Mapping** | Per-reference discriminator mapping (NEW) | §11.8 |
| **Feature Ignore List** | Silently skip, configurable at Global/EClass/EReference | §11.9 |
| **Feature Strictness** | Separate `strictOnUnknownFeatures` / `strictOnMissingFeatures` | §11.10 |
| **Property Map Configuration** | `codec.` prefix, `codec.ser.*`, `codec.deser.*` for direction | §11.11 |

### Session 2026-01-21: Spec Clarification + Code Quality Fixes

| Topic | Decision | Section |
|-------|----------|---------|
| **Null vs Missing** | Clarified deserialization behavior | 09-feature.md §1.3 |
| **Object type null** | Explicit JSON `null` → sets feature to `null` | §1.3 |
| **Primitive type null** | Explicit JSON `null` → resets to EMF default (not Java primitive default) | §1.3 |
| **Missing field** | Retains EMF default value | §1.3 |
| **serializeNull/Defaults** | Confirmed as serialization-only settings | §1.3 |
| **Proxy serialization algorithm** | Documented complete decision tree | 08-reference.md §4.1.1 |
| **URI determination** | Same-doc=fragment, cross-doc=relative, proxy=preserved | §4.1.1 |

**Key Discoveries:**
1. EMF's `eSet(attribute, null)` for primitives resets to `defaultValueLiteral`, NOT Java primitive default (0, false)
2. Unresolved proxies serialize using their existing proxy URI from `InternalEObject.eProxyURI()`

**Tests Added:** `CodecResourceAdvancedTest.NullDefaultValueTests` - 3 new tests for null/missing behavior.

**Code Quality Fixes (9.x):**
1. **9.1 Resource leaks** - Added `finally` blocks with `closeQuietly()` in `ReferenceDeserializationEntry`
2. **9.2 FQCN violations** - Replaced all FQCNs with imports across 4 files
3. **9.3 Config validation** - Confirmed as resolved by design (getters handle dependencies)

**ID Spec Alignment:**
Updated `07-id.md` to align with Type handling patterns:
- **Section 8.1: StrategyScope Values** - `ALL`, `ROOT_ONLY`, `ROOT_CONTAINMENT`, `ROOT_NON_CONTAINMENT`
- **Section 8.2: Separate Scopes** - Independent `idScope` and `idFormatScope` (like Type)
- **Section 8.3: Setting Scope Rules** - Which settings use which scope property
- **Section 8.4: Why No Per-Reference** - Explicit rationale (like Type section 1.5.4)
- **Section 8.5-8.6: Containment Behavior, Dependencies** - Consistent with Type patterns
- **Section 9: Deserialization** - Key recognition, format detection, PLAIN/STRUCTURED deserialization, edge cases
- **Section 10: Default ID Settings** - Updated with complete serialization/deserialization algorithms

**Annotation/Property Documentation (6.x):**
- **6.1** - Expanded `00-overview.md` §1.1 with complete annotation key registry (Type, SuperType, ID, Reference, Feature)
- **6.2** - Clarified per-reference format is supported in `08-reference.md` §4 (unlike Type which is per-class)

**Error Handling Documentation (7.x):**
- **7.1** - Added complete error scenarios table to `00-overview.md` §2.1 (Type, ID, Reference, Feature, Configuration errors)
- **7.2** - Added diagnostic API examples to `00-overview.md` §2.2 (accessing, programmatic detection, suppressing warnings, fail-fast, custom handlers)

**Implementation Gaps (8.x):**
- **8.1** - Added cross-resource workaround documentation to `08-reference.md` §8.3 with three resolution options
- **8.2** - Decided Option B (keep parameter, document limitation) - limitation already documented in spec

**Feature Handling (11.x):**
- **11.1** - Added unknown field handling documentation to `09-feature.md` §10 (current: WARNING + skip; STRICT mode as future feature)

### Session 2026-01-21 (continued): Spec Review Completion

**Unknown Type Handling (5.3):**
- Added `05-type.md` §5.3.1 "Unknown Type Handling" documenting fallback behavior
- Unknown type WITH hint → WARNING, use hint
- Unknown type WITHOUT hint → ERROR, fail
- Documented resolution order (URI → Java class → Discriminator → Simple name → Numeric → Fallback)
- Updated `00-overview.md` §2.1 error scenarios to match actual implementation

**Metadata Field Ordering (2.4):**
- Documented existing `idOnTop` feature in `09-id.md` §8.7
- Default `idOnTop=false` puts `_type` before `_id` (human-readable, API conventions)
- Added `codec.id.onTop` to annotation key registry in `00-overview.md`

**Option Keys Registry (3.1):**
- Added comprehensive `03-config-hierarchy.md` §10 "Load/Save Option Keys Registry"
- Documented Resource-Level, Context, and Internal options
- Added property map format (`codec.<target>.<property>`)
- Documented Java constants locations

**ResourceFactory DI Support (3.2):**
- Added parameterless constructor to `CodecResourceFactory` for DI frameworks
- Added setters: `setMetadataService()`, `setConfiguration()`, `setMapperBuilder()`
- Added `03-config-hierarchy.md` §8 "ResourceFactory Configuration" with examples for OSGi DS and Spring

### Session 2026-01-21 (final): Working Document Integration + ID Alignment

**Working Document Integration:**
Integrated remaining items from `type-strategy-scope-proposal.md` into main spec:

| Item | Integrated Into | Description |
|------|-----------------|-------------|
| **CODEC_TYPE_MODE** | `05-type.md` §5.5.1 | HINT vs OVERRIDE semantics for type hints |
| **DeserializationMode** | `05-type.md` §5.5.2 | STRICT/LENIENT/AUTO_DETECT type resolution strictness |
| **Direction-specific config** | `03-config-hierarchy.md` §10.7 | `codec.ser.*`, `codec.deser.*` prefixes |

**ID-Type Alignment Verification:**
Verified ID handling aligns with Type handling patterns:

| Feature | Type | ID | Notes |
|---------|------|-----|-------|
| **StrategyScope enum** | ✅ `typeScope` | ✅ `idScope` | Same enum, same semantics |
| **Separate format scope** | ✅ `typeFormatScope` | ✅ `idFormatScope` | Independent from strategy scope |
| **Per-reference config** | ✅ (limited) | ❌ | ID identifies object, not access path |
| **DeserializationMode** | ✅ STRICT/LENIENT/AUTO | ❌ (always lenient) | ID parsing is simpler |
| **Type hint mode** | ✅ HINT/OVERRIDE | ❌ | ID has no "hint" concept |

Added to ID spec:
- **`07-id.md` §8 "Type and ID Configuration Alignment"** - Comparison table with rationale
- **`07-id.md` §9.7 "ID Deserialization Error Handling"** - Error scenarios matching Type patterns

**Remaining in Working Document (DEFERRED):**
- Discriminator refactoring (10.x) - Major change, needs coordinated implementation
- Feature Strictness full implementation - `strictOnUnknownFeatures`, `strictOnMissingFeatures`

### Resolution Status Summary (FINAL)

| Category | Total | Resolved | Deferred | Documented |
|----------|-------|----------|----------|------------|
| 1. Contradictions | 3 | 3 | 0 | 0 |
| 2. Ser/Deser Clarity | 4 | 4 | 0 | 0 |
| 3. Config Hierarchy | 3 | 3 | 0 | 0 |
| 4. Missing Examples | 4 | 0 | 4 | 0 |
| 5. Default Behavior | 3 | 3 | 0 | 0 |
| 6. Annotation/Property | 2 | 2 | 0 | 0 |
| 7. Error Handling | 2 | 2 | 0 | 0 |
| 8. Implementation Gaps | 2 | 2 | 0 | 0 |
| 9. Code Quality | 3 | 3 | 0 | 0 |
| 10. Discriminator Refactoring | 4 | 0 | 4 | 0 |
| 11. Feature Handling | 1 | 0 | 0 | 1 |
| **TOTAL** | **31** | **22** | **8** | **1** |

### Remaining Work

**Deferred Items (Future Work):**
- **2.3** Field ordering direction (LOW priority)
- **4.1-4.4** Documentation examples for SuperType, Reference Expand, Custom Values, NUMERIC
- **10.1-10.4** Discriminator refactoring - needs coordinated strategy:
  - Remove MAPPED from TypeStrategy enum
  - Inline mapping for references
  - Property-based discriminator config
  - STRUCTURED format warning

**Documented (Feature Request):**
- **11.1** STRICT mode for unknown field handling (future feature)

### Session 2026-01-26: MetadataIndex API + Deprecation Cleanup

**MetadataIndex Implementation:**

Added indexed lookup support to `MetadataService` for fast CLASS/NAME TypeStrategy resolution:

| Interface | Purpose |
|-----------|---------|
| `MetadataIndexReader` | Query interface: `findByInstanceClassName`, `findByClassName`, `findClassByURI`, etc. |
| `MetadataIndexWriter` | Index maintenance: `indexPackage`, `removeClass`, `clear` |
| `MetadataIndex` | Combined interface extending both |
| `MapBasedMetadataIndex` | In-memory ConcurrentHashMap implementation |

**Key Features:**
- Context-aware lookup: `findByInstanceClassName(nsURI, className)` for specific package
- Global search: `findAllByInstanceClassName(className)` for cross-package queries
- Handles `java.util.Map$Entry` pattern (multiple EClasses with same instanceClassName)
- Automatic indexing on package registration via `MetadataServiceImpl`

**API Access:**
```java
MetadataIndexReader index = metadataService.getIndexReader();
ClassMetadata meta = index.findByInstanceClassName(nsURI, "org.example.PersonImpl");
```

**Deprecated `typeInclude` Removed (code + spec):**

The deprecated `typeInclude` has been completely removed from both code (2026-01-26) and spec (2026-01-29):

| Location | Change |
|----------|--------|
| `metadata.ecore` | Removed `include` from `BaseTypeConfig` |
| `CodecAnnotationConstants` | Removed `KEY_TYPE_INCLUDE` |
| `CodecAspectProvider` | Removed `typeInclude` parsing |
| `ConfigurationMerger` | Updated `resolveTypeEnabled()` to derive from `TypeStrategy.NONE` |
| Tests | Updated to use `TypeStrategy.NONE` instead |
| **Spec docs** (2026-01-29) | Removed all `typeInclude` references from 06-type.md, 16-annotation-reference.md, 02-config-resolution.md, 19-test-coverage.md, 21-type-config-validation-rules.md |

**Spec Updated:**
- `06-type.md` section 6.4.5: CLASS Strategy Resolution with MetadataIndex API examples
- Added cross-reference from NAME strategy to MetadataIndex API

### Session 2026-01-24: Configuration Infrastructure Complete

**New Config Classes in `org.eclipse.fennec.codec.api`:**

| Class | Purpose | Test Count |
|-------|---------|------------|
| `IdConfig` | ID serialization configuration | 83+ tests (27 unit + 56 spec) |
| `TypeConfig` | Type serialization configuration | 41 tests |
| `FeatureConfig` | Feature serialization configuration | 52 tests |
| `SuperTypeConfig` | SuperType serialization configuration | 35 tests |
| `ReferenceConfig` | Reference serialization configuration (NEW) | 43 tests |
| `DiscriminatorConfig` | Discriminator mapping configuration (NEW) | 58 tests |
| `ConfigMergeHelper` | Utility for type-safe property merging | 66 tests |
| `ConfigProperty` | Enum defining all config properties | 71 tests |

**Key Design Pattern - Cascading Merge:**
```java
// Each config implements Mergeable<T> for cascading configuration
defaults.mergeWith(annotation)
        .mergeWith(module)
        .mergeWith(factory)
        .mergeWith(resource)
        .mergeWith(options)
        .validate(diagnostics);  // Returns immutable final config
```

**New Classes Created:**

1. **`ReferenceConfig.java`** - Reference serialization with:
   - Properties: format, refKey, refTypeKey, proxyKey, expand, expandGlobal, expandDepth, expandIgnoreBidirectional, serializeInstanceType, valueReaderName, valueWriterName
   - Computed property: `shouldExpand()` returns true when expand or expandGlobal is true

2. **`DiscriminatorConfig.java`** - Discriminator mapping with:
   - Type Mapping Registry: typeMapId, typeDiscriminatorPath, typeDiscriminator, typeMappings (Map)
   - Inline Mapping: inlineMappings (Map)
   - Fallback: fallbackStrategy (ERROR/SKIP/FALLBACK enum), fallbackEClass
   - Map merging support in `mergeWith()` - override entries merge into base
   - Computed properties: hasTypeMappingRegistry(), hasInlineMapping(), isRegisteredWithRegistry()

**Diagnostic System Enhanced:**

Added INFO severity to `CodecDiagnostic`:
```java
public enum Severity { ERROR, WARNING, INFO }

// New methods
CodecDiagnostic.info(message, source);
diagnostics.addInfo(message, source);
```

**Deprecated Classes (in `codec.v2.config.effective`):**

| Class | Replacement |
|-------|-------------|
| `EffectiveIdConfig` | `org.eclipse.fennec.codec.config.IdConfig` |
| `EffectiveTypeConfig` | `org.eclipse.fennec.codec.config.TypeConfig` |
| `EffectiveFeatureConfig` | `org.eclipse.fennec.codec.config.FeatureConfig` |
| `EffectiveSuperTypeConfig` | `org.eclipse.fennec.codec.config.SuperTypeConfig` |
| `EffectiveClassConfig` | Use new config classes directly |
| `EffectiveCodecConfig` | Use new config classes directly |
| `ConfigurationMerger` | `org.eclipse.fennec.codec.config.ConfigurationResolver` |

**Test Coverage Summary (~530+ tests in codec.api):**

All config tests follow TDD principles and validate:
- Default values from spec
- Builder pattern with chaining
- `mergeWith(Map)` for property map overrides
- `mergeWith(T)` for cascading config
- `validate(DiagnosticCollector)` for constraint checking
- Computed properties where applicable

**Fixed Issues:**

1. `TypeConfig.validate()` - Removed invalid `TypeStrategy.MAPPED` reference (discriminator mapping is separate layer)
2. Added schemaKey validation constraint for STRUCTURED format / SCHEMA_AND_TYPE strategy

### Session 2026-01-23: Spec Reorganization Complete

**Spec Structure Finalized:**

The specification reorganization is now complete. Final structure:

| Part | Chapters | Content |
|------|----------|---------|
| I: Foundations | 00-05 | Overview, Architecture, Config Resolution, Naming, Common Types, Global Options |
| II: Features | 06-12 | Type, SuperType, Discriminator, ID, Reference, Feature, Polymorphism |
| III: Runtime | 13-15 | Load/Save Options, Custom Values, Error Handling |
| IV: Reference | 16-19, 99 | Annotation Reference, Format Abstraction, Scenarios, Test Coverage, Open Questions |

**Key Chapters Revised:**

| Chapter | Changes |
|---------|---------|
| **13-load-save-options** | Option key tables, value reader/writer registration |
| **14-custom-values** | New `CodecReaderContext`/`CodecWriterContext` interfaces, `getName()` method, diagnostic support |
| **15-error-handling** | Serialization + deserialization sources, option tables, custom reader/writer integration |
| **16-annotation-reference** | Diagnostic Options section, cross-references, implementation status |
| **17-format-abstraction** | Aligned with new context interfaces |
| **99-open-questions** | Consolidated design questions + implementation roadmap |

**New Design Decisions:**

| Decision | Details |
|----------|---------|
| Value reader/writer context | `CodecReaderContext`/`CodecWriterContext` with config access and diagnostic reporting |
| Reader/writer registration | `getName()` on interfaces, builder `.valueReader(instance)`, load options `codec.valueReaders` |
| Diagnostic sources | Custom readers/writers can report via `ctx.addError()`/`ctx.addWarning()` |

**Working Documents:**

| Document | Status |
|----------|--------|
| `spec-reorganization-plan.md` | ✅ Archived (completed) |
| `annotation-scope-reference.md` | ✅ Archived (integrated into 16-annotation-reference) |
| `type-strategy-scope-proposal.md` | ✅ Archived (integrated into spec) |
| `epackage-scope-proposal.md` | Active - under review |
| `emf-configuration-model-proposal.md` | Active - under review |

### Next Session: Suggested Tasks

See **[99-open-questions.md](codec-v2-spec/99-open-questions.md)** for full implementation roadmap.

**Configuration Infrastructure Complete ✅** - All config classes now implement `Mergeable<T>`.

**⚠️ IMMEDIATE: Test Reorganization (TDD Correction)**

The existing ~510 tests were generated from implementation, NOT from spec. This is a TDD violation.

**Plan:**
1. Move existing tests to `test/.../config/impl/` subpackage (implementation-derived tests)
2. Create new `test/.../config/spec/` subpackage for spec-based tests
3. Write spec-based tests using examples from spec documents
4. When spec is unclear → ASK before implementing
5. Spec tests will reveal validation constraint questions (e.g., "does typeMappings require typeMapId?")

**Structure:**
```
test/org/eclipse/fennec/codec/config/
├── impl/                          # Existing tests (implementation-derived)
│   ├── IdConfigTest.java
│   ├── TypeConfigTest.java
│   └── ...
└── spec/                          # NEW: Spec-based tests
    ├── IdConfigSpecTest.java
    ├── TypeConfigSpecTest.java
    └── ...
```

**After Test Reorganization:**

**High Priority:**
1. **Wire DiscriminatorConfig** - Connect new DiscriminatorConfig to Type resolution in codec.v2
2. **Feature Visibility Refactor** - Add `ignore*`, `force*` annotations to replace `transient`/`serialize`
3. **Metadata Merge** - Implement `metadataMerge`/`metadataKey` for STRUCTURED format

**Medium Priority:**
4. **Feature Strictness** - Implement `strictOnUnknown`/`strictOnMissing` using DiagnosticCollector
5. **Diagnostic Options** - Complete `failFast`, `suppressWarnings` implementation
6. **Type/SuperType Value Reader/Writer** - Wire existing constants

**Design Decisions Needed:**
- Review EPackage Scope Level proposal
- Review EMF Configuration Model proposal

---

## 8. Testing Strategy

Tests should NOT be trivial getter/setter tests. Instead:

1. **Model-Based Testing**
   - Create Ecore test models with various annotation configurations
   - Run annotation parser on these models
   - Assert resulting aspects match expected values

2. **Default Value Testing**
   - Test that missing annotations fall back to spec defaults
   - Test partial annotations (some values set, others defaulted)

3. **Inheritance Testing**
   - Test annotation inheritance within same EPackage
   - Test cross-package inheritance with `codec.inherit`

4. **Integration Testing**
   - Full round-trip: Model → Parse → Serialize → Deserialize → Verify

---

## 8. Quick Reference: Enums

### SerializationFormat (codec-wide)
- `PLAIN` - Simple value (string, number, array of simple values)
- `STRUCTURED` - Nested object/map

### TypeStrategy
- `URI` - Full EClass URI (default)
- `NAME` - EClass simple name
- `SCHEMA_AND_TYPE` - Separate schema and type fields
- `NUMERIC` - Numeric classifier IDs
- `NONE` - No type information written/expected

**Note:** `MAPPED` was removed from TypeStrategy. Discriminator mapping is now a separate optional configuration layer. See working document Section 11.8.

### IdStrategy
- `ID_FIELD` - Single designated ID field (default)
- `COMBINED` - Multiple features combined
- `NONE` - No ID serialization

### IdKeyMode
- `ID_ONLY` - Only ID in output key
- `BOTH` - Both ID and feature name
- `FEATURE_ONLY` - Only feature name

### SuperTypeSelection
- `ALL` - All supertypes in hierarchy
- `ALL_EMF` - All EMF supertypes (skip Java interfaces)
- `SINGLE` - Only direct supertype
- `NONE` - No supertypes

---

## 9. Detailed Feature Documentation

For detailed feature documentation, see:
- [00-overview.md](codec-v2-spec/00-overview.md) - Spec overview and TOC
- [19-test-coverage.md](codec-v2-spec/19-test-coverage.md) - Test coverage mapping
- [18-annotation-reference.md](codec-v2-spec/18-annotation-reference.md) - Definitive annotation reference

---

## 10. Configuration Transition Work (Active)

This section tracks the ongoing transition from the old configuration approach to the new unified configuration architecture defined in the spec.

> **📋 Active Refactoring Plan:** See [`~/.claude/plans/compiled-dreaming-acorn.md`](/home/mark/.claude/plans/compiled-dreaming-acorn.md) for the detailed working plan including:
> - 3D Configuration Matrix architecture (Sources × Levels × Direction)
> - GAP tracking (GAP-001 through GAP-014)
> - Phase-by-phase implementation order
> - Acceptance criteria
>
> This development guide remains the **source of truth** for project architecture and history. The plan file is a **temporary working document** for the current refactoring effort.

### 10.1 Transition Goals

1. **Complete annotation-scope-reference document** → becomes source of truth for all properties
2. **Design fluent builder API** for programmatic configuration (GlobalConfig, EClass, Reference builders)
3. **Ensure codec.metadata returns correctly merged properties** from MetadataService
4. **Adjust effective properties** according to merging rules at each level
5. **Update tests** to align with new spec (comment out invalid tests, adjust others)

### 10.2 Transition Log

| Date | Change | Files Modified | Notes |
|------|--------|----------------|-------|
| 2026-01-23 | Fixed compile error: removed `case MAPPED:` from switch | `TypeDeserializationEntry.java:446-458` | MAPPED removed from TypeStrategy enum per spec §11.8. Added `case SCHEMA_AND_TYPE:` (was missing). Discriminator mapping is now a separate layer. |
| 2026-01-23 | Commented out test using `TypeStrategy.MAPPED` | `TypeSerializationEntryTest.java:324-347` | Test is still valid conceptually - needs update to use new discriminator config approach |
| 2026-01-23 | Commented out test asserting `TypeStrategy.MAPPED` | `CodecResourceMappedTypeTest.java:135-150` | Test data file may need updating for new annotation structure |
| 2026-01-23 | Updated spec: removed MAPPED from TypeStrategy | `docs/codec-v2-spec/05-type.md` | Added NONE strategy, discriminator mapping as separate layer (§1.3), updated strategy tables |
| 2026-01-23 | Integrated migration plan into dev-guide | `docs/codec-v2-development-guide.md` | Added §10.5 Ecore Model Changes, §10.6 MAPPED→Discriminator Migration |
| 2026-01-23 | Deleted redundant document | `docs/codec-v2-spec-working/model-migration-plan.md` | Content integrated into dev-guide §10.5-10.6 |

### 10.3 Commented Out Tests (To Be Revisited)

These tests were commented out due to the `TypeStrategy.MAPPED` removal. They test **valid functionality** that still exists, but needs to be configured differently:

| Test Class | Test Method | Reason | Action Needed |
|------------|-------------|--------|---------------|
| `TypeSerializationEntryTest` | `mappedStrategy_serializesTypeWithDiscriminator()` | Uses `TypeStrategy.MAPPED` | Update to use discriminator mapping layer with different TypeStrategy |
| `CodecResourceMappedTypeTest` | `parsesMappedStrategyFromAnnotation()` | Asserts `TypeStrategy.MAPPED` from annotation | Update test data (ecore) and assertion to use new config structure |

**Note:** The discriminator mapping functionality is still valid! The tests need adjustment to:
1. Use a different TypeStrategy (e.g., `NAME`, `URI`, or `NONE`)
2. Configure discriminator mapping via the new separate layer (mapId, discriminatorValue)
3. Verify discriminator values are still output/resolved correctly

### 10.4 Current TODO List

**Phase 1: Documentation (Current)**
- [ ] Complete annotation-scope-reference document
  - [ ] Verify all properties against `CodecAspectProvider` implementation
  - [ ] Document direction-specific properties (`codec.ser.*`, `codec.deser.*`)
  - [ ] Add merging rules for each property at each level
  - [ ] Review EEnum-level annotation support

**Phase 2: Builder API Design**
- [ ] Design `GlobalConfigBuilder` for codec-wide settings
- [ ] Design `EClassConfigBuilder` for per-class settings
- [ ] Design `ReferenceConfigBuilder` for per-reference settings
- [ ] Design `TypeConfigBuilder`, `IdConfigBuilder` for sub-configs
- [ ] Support instantiation from property map
- [ ] Support fluent API chaining across builders

**Phase 3: Test Transition**
- [ ] Review each test class against spec
- [ ] Re-enable and fix commented tests one by one
- [ ] Add helper classes for better testability
- [ ] Order: Configuration → Serialization → Deserialization

**Phase 4: Implementation Alignment**
- [ ] Apply documented merging rules to `codec.metadata`
- [ ] Ensure `MetadataService` returns correctly merged properties
- [ ] Integrate with static/load-save property merging

### 10.5 Ecore Model Changes (Reference)

This section summarizes the Ecore model changes required for the new configuration architecture.

#### 10.5.1 metadata.ecore (org.eclipse.fennec.model.metadata)

| Change | Type | Element | Status |
|--------|------|---------|--------|
| Remove `MAPPED` | DELETE | `TypeStrategy.MAPPED` literal | ✅ Done |
| Add `onTop` | ADD | `BaseIdConfig.onTop : EBoolean = false` | Pending |
| Add `serializeSeparator` | ADD | `BaseIdConfig.serializeSeparator : EBoolean = true` | Pending |
| Add `separatorKey` | ADD | `BaseIdConfig.separatorKey : EString = "separator"` | Pending |

#### 10.5.2 codec.ecore (org.eclipse.fennec.codec.metadata)

| Change | Type | Element | Status |
|--------|------|---------|--------|
| Add `StrategyScope` | ADD | New EEnum (ALL, ROOT_ONLY, ROOT_CONTAINMENT, ROOT_NON_CONTAINMENT) | ✅ Done |
| Add `TypeHintMode` | ADD | New EEnum (HINT, OVERRIDE) | ✅ Done |
| Add `DeserializationMode` | ADD | New EEnum (LENIENT, STRICT, AUTO_DETECT) | ✅ Done |
| Add `strategyScope` | ADD | `TypeSerializationConfig.strategyScope : StrategyScope = ALL` | Pending |
| Add `formatScope` | ADD | `TypeSerializationConfig.formatScope : StrategyScope = ALL` | Pending |
| Add `strategyScope` | ADD | `IdSerializationConfig.strategyScope : StrategyScope = ALL` | Pending |
| Add `formatScope` | ADD | `IdSerializationConfig.formatScope : StrategyScope = ALL` | Pending |
| Add `typeHintMode` | ADD | `CodecConfig.typeHintMode : TypeHintMode = HINT` | Pending |
| Add `deserializationMode` | ADD | `CodecConfig.deserializationMode : DeserializationMode = LENIENT` | Pending |

#### 10.5.3 Test Requirements After Model Changes

**For org.eclipse.fennec.model.metadata:**
| Test | Description |
|------|-------------|
| `BaseIdConfigDefaultsTest` | Verify `onTop=false`, `serializeSeparator=true`, `separatorKey="separator"` defaults |
| `TypeStrategyEnumTest` | Verify remaining values: NAME, CLASS, URI, SCHEMA_AND_TYPE, NUMERIC, NONE |

**For org.eclipse.fennec.codec.metadata:**
| Test | Description |
|------|-------------|
| `StrategyScopeEnumTest` | Verify ALL, ROOT_ONLY, ROOT_CONTAINMENT, ROOT_NON_CONTAINMENT values |
| `TypeHintModeEnumTest` | Verify HINT, OVERRIDE values |
| `DeserializationModeEnumTest` | Verify LENIENT, STRICT, AUTO_DETECT values |
| `TypeSerializationConfigScopeTest` | Verify `strategyScope` and `formatScope` defaults |
| `IdSerializationConfigScopeTest` | Verify `strategyScope` and `formatScope` defaults |
| `CodecConfigDeserializationTest` | Verify `typeHintMode` and `deserializationMode` defaults |
| `CodecAnnotationParserScopeTest` | Verify annotation parsing for scope settings |

**For org.eclipse.fennec.codec.v2:**
| Test | Description |
|------|-------------|
| `TypeScopeSerializationTest` | Verify type info respects scope during serialization |
| `IdScopeSerializationTest` | Verify ID respects scope during serialization |
| `TypeHintModeDeserializationTest` | Verify HINT vs OVERRIDE behavior |
| `DeserializationModeTest` | Verify STRICT/LENIENT/AUTO_DETECT behavior |
| `IdOnTopSerializationTest` | Verify `_id` appears before `_type` when onTop=true (default is false → `_type` first) |

### 10.6 MAPPED → Discriminator Migration

The discriminator mapping functionality (previously `TypeStrategy.MAPPED`) is being refactored into a separate layer.

**Current infrastructure (still valid):**
- `TypeDiscriminatorRegistry` - per-mapId registry for discriminator ↔ EClass mappings
- `TypeDiscriminatorService` - service managing registries across mapIds
- `CodecAspectProvider` - parses discriminatorValue and registers mappings

**Key change:** Discriminator is NOT a parallel to URI/NAME/CLASS strategies - it's an additional resolution mechanism that works alongside them.

**New behavior:**
```
Type resolution order (any strategy):
1. Try discriminator lookup (if configured) - inline mapping first, then named registry
2. Try configured strategy (URI, NAME, CLASS, etc.)
3. If both fail → use fallback/hints
4. If all fail → ERROR

Serialization:
1. Write type using configured strategy
2. ALSO write discriminator if configured (for interop)
```

**Files requiring updates:**
| File | Current Usage | Action |
|------|---------------|--------|
| `CodecAspectProvider.java` | Sets discriminatorValue | Keep parsing, don't set strategy to MAPPED |
| `TypeDeserializationEntry.java` | `case MAPPED:` removed | Use discriminator as fallback for all strategies |
| `TypeSerializationEntry.java` | Writes discriminator | Keep writing, independent of strategy |
| Test files | Various MAPPED references | Update to test discriminator layer separately |

### 10.7 Key Documents for Transition

| Document | Purpose | Status |
|----------|---------|--------|
| `docs/codec-v2-spec/18-annotation-reference.md` | Definitive property/annotation/level reference | ✅ Published |
| `docs/codec-v2-spec-working/type-strategy-scope-proposal.md` | Detailed scope/discriminator decisions | ✅ Reference |
| `docs/codec-v2-spec/02-config-resolution.md` | Configuration hierarchy spec | ✅ Published |
| `docs/codec-v2-spec/06-type.md` | Type serialization spec | ✅ Published |
| `docs/codec-v2-spec/08-discriminator-mapping.md` | Discriminator mapping spec | ✅ Published |

---

## 11. Migration Policy

This section documents key decisions about how we handle the migration from old codec to new codec.v2.

### 11.1 No Backward Compatibility Required

**Decision:** We do NOT need backward compatibility between old and new codec APIs.

**Rationale:** This is a major version upgrade (v1 → v2). The old and new implementations coexist during development, but:
- Users will migrate to v2 wholesale, not incrementally
- API changes are expected and acceptable
- Breaking changes are documented but not prevented

### 11.2 Old Tests as Migration Checklist

**Decision:** Keep old tests as documentation/checklist, deprecate AND disable them after migration.

**Pattern:**
```java
@Deprecated
@Disabled("Migrated to org.eclipse.fennec.codec.value - kept for migration reference")
@SuppressWarnings("deprecation")
class OldFeatureTest {
    // Original test code preserved for reference
}
```

**Rationale:**
- Old tests document what the old code did
- They serve as a checklist during migration (ensure new code covers same scenarios)
- Deprecation indicates WHY they are disabled
- Disabling prevents noise in test reports
- Keeping them (vs. deleting) allows future reference if questions arise

### 11.3 Migration Workflow

1. **Create new implementation** in `org.eclipse.fennec.codec.*` package
2. **Create new spec tests** in `test/.../spec/` subpackage
3. **Mark old interfaces** with `@Deprecated` annotation
4. **Mark old tests** with `@Deprecated` AND `@Disabled`
5. **Keep old code** for reference until migration is complete

### 11.4 Example: Value Reader/Writer Migration

**Old package:** `org.eclipse.fennec.codec.api.value`
- Interfaces without context objects
- Tests in `test/org/eclipse/fennec/codec/api/value/`

**New package:** `org.eclipse.fennec.codec.value`
- Interfaces with `CodecReaderContext` / `CodecWriterContext`
- Auto-registration via `getName()` method
- Tests in `test/org/eclipse/fennec/codec/value/`

**Migration steps taken:**
1. Created new interfaces in `org.eclipse.fennec.codec.value`
2. Created new tests in `test/org/eclipse/fennec/codec/value/`
3. Marked old interfaces with `@Deprecated`
4. Marked all 12 old test classes with `@Deprecated` + `@Disabled`
5. Old tests now show as "40 ignored" in test report

### 11.5 Example: Diagnostic Migration

**Old package:** `org.eclipse.fennec.codec.api.diagnostic`
- `CodecDiagnostic`, `DiagnosticCollector`
- Tests in `test/org/eclipse/fennec/codec/api/diagnostic/`

**New package:** `org.eclipse.fennec.codec.diagnostic`
- Same classes, updated spec references
- Tests in `test/org/eclipse/fennec/codec/diagnostic/`

**Migration steps taken:**
1. Created new classes in `org.eclipse.fennec.codec.diagnostic`
2. Created new tests in `test/org/eclipse/fennec/codec/diagnostic/`
3. Marked old classes with `@Deprecated`
4. Marked old tests with `@Deprecated` + `@Disabled`
5. Updated all usages in `codec.config`, `codec.value` packages to use new package

### 11.6 Completed Migrations

| Component | Old Package | New Package | Status |
|-----------|-------------|-------------|--------|
| Value Reader/Writer | `codec.api.value` | `codec.value` | ✅ Complete |
| Diagnostic | `codec.api.diagnostic` | `codec.diagnostic` | ✅ Complete |
| Config classes | `codec.api.config` | `codec.config` | ✅ Already in new package |

---

## 12. Session Continuity Tips

If context is lost:

1. Read this document first: `docs/codec-v2-development-guide.md`
2. Check spec for details: `docs/codec-v2-serialization-spec.md`
3. Review current TODO state (if available)
4. Examine recent git commits for context
5. Ask user for clarification if needed

The key insight: **MetadataService parses EAnnotations at EPackage registration time and creates pre-computed aspect objects that the codec uses at serialization time.**

**For Discriminator Mapping:** All packages are registered with MetadataService before serialization/deserialization. When registering an EPackage, the MetadataService runs through codec aspects and updates the TypeDiscriminatorService. By the time deserialization happens, there's already a mapping from discriminator values (like "temp-sensor") to EClasses. Note: Discriminator mapping is now a separate layer that works alongside any TypeStrategy (not a strategy itself).
