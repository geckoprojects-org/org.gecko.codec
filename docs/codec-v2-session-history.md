# Codec V2 Session History

This file contains completed task entries from the development guide's "Active Task Hierarchy" section.
Only the latest entry is kept in the main `codec-v2-development-guide.md`; older entries are archived here.

---

COMPLETED: Dependent Projects API Migration + forceWrite/forceRead Fixes - ✅ (2026-02-04)
│
│  BUG FIXES:
│  │  1. forceWrite two-gate model (AttributeSerializationEntry, ReferenceSerializationEntry)
│  │     - forceWrite now ONLY affects visibility gate, NOT value gate
│  │     - serializeNull/serializeEmpty/serializeDefault still apply with forceWrite=true
│  │     - Matches spec section 12 "Feature Serialization Flow"
│  │
│  │  2. forceRead not working for volatile features (ConfigurationResolver:420)
│  │     - Only checked isForceWrite(), now also checks isForceRead()
│  │     - volatile/transient/derived features with forceRead=true now deserialize correctly
│  │
│  NEW TEST FILE: ForceReadWriteTest.java
│  │  - Tests for forceWrite, forceRead, and toBuilder() preservation
│  │  - Renamed from ForceWriteSerializationTest.java
│  │  - Verifies two-gate model behavior
│
│  Migrated codec.geojson, codec.jsonschema.v2, and codec.openapi from deprecated
│  API (org.eclipse.fennec.codec.api.value.*, codec.v2.*) to new non-deprecated API
│  (org.eclipse.fennec.codec.value.*, codec.*).
│
│  PROJECTS MIGRATED:
│  │
│  │  codec.jsonschema.v2:
│  │  - EPackageValueReader: codec.api.value → codec.value, added getName()
│  │  - EPackageValueWriter: codec.api.value → codec.value, added getName()
│  │  - Tests updated to use CodecReaderContext/CodecWriterContext wrappers
│  │
│  │  codec.openapi:
│  │  - OpenApiResourceFactoryImpl: codec.v2.util.MetadataServiceFactory → codec.util
│  │  - OpenApiResourceImpl: codec.v2.resource.CodecResource → codec.resource.CodecResource
│  │                         codec.v2.config.CodecConfiguration → codec.config.ConfigurationResolver
│  │                         codec.api.value.CodecValueRegistry → codec.value.CodecValueRegistry
│  │  - OperationValueReader: codec.v2.context.ContextHelper → codec.context.ContextHelper
│  │                          codec.api.value → codec.value, fixed getName() = "operation"
│  │  - All test files: CODEC_ROOT_OBJECT → CODEC_ROOT_TYPE
│  │
│  API CHANGES SUMMARY:
│  │  - ReferenceValueReader.read(JsonParser, EReference, DeserializationContext)
│  │    → read(CodecReaderContext, EReference)
│  │  - ReferenceValueWriter.write(value, reference, generator, context)
│  │    → write(value, reference, CodecWriterContext)
│  │  - CodecValueReader/Writer now require getName() method
│  │  - CodecConfiguration → ConfigurationResolver (with .typeInclude() etc.)
│  │  - CodecResource.CODEC_ROOT_OBJECT → CODEC_ROOT_TYPE
│
│  TEST RESULTS: All tests passing in jsonschema.v2 (19 tests) and openapi (54 tests)
│
---

COMPLETED: CodecEntryContext Pattern Migration - ✅ (2026-02-03)
│
│  Migrated all serialization/deserialization entry classes to use a unified
│  CodecEntryContext pattern instead of passing CodecValueRegistry directly.
│
│  CHANGES:
│  │  - Created CodecEntryContext: unified context holding valueRegistry,
│  │    effectiveConfig, and diagnostics
│  │  - Migrated AttributeSerializationEntry, ReferenceSerializationEntry,
│  │    AttributeDeserializationEntry, ReferenceDeserializationEntry
│  │  - Updated CodecEObjectSerializer and CodecEObjectDeserializer to create
│  │    CodecEntryContext once per operation
│  │  - Updated all test files to use new interfaces (getName() required on
│  │    CodecValueReader/Writer, CodecEntryContext in entry constructors)
│  │  - Made context implementations (CodecWriterContextImpl, CodecReaderContextImpl)
│  │    more lenient for tests (allow null config/diagnostics)
│
│  BENEFITS:
│  │  - Single context object passed to entries (cleaner API)
│  │  - Entries can create writer/reader contexts on-demand
│  │  - Consistent access to effectiveConfig and diagnostics
│  │  - Better separation between stable config and request-scoped state
│
│  TEST RESULTS: 1042 tests, 0 failures, 141 skipped
│
---

COMPLETED: 8-Step Package Migration (codec.v2.* → codec.*) - ✅ (2026-02-01)
│
│  All 8 migration steps complete. New spec-compliant classes live in
│  org.eclipse.fennec.codec.* packages (within the same org.eclipse.fennec.codec.v2
│  Gradle project). Old codec.v2.* classes are deprecated + tests disabled.
│
│  MIGRATION PLAN: ~/.claude/plans/functional-toasting-simon.md
│
│  STEPS COMPLETED:
│  │  Step 1: Utilities + Diagnostics → codec.util, codec.diagnostic ✅
│  │  Step 2: Context System → codec.context ✅
│  │  Step 3: Configuration Bridge → codec.config.effective.EffectiveCodecConfig ✅
│  │  Step 4: Jackson Integration → codec.jackson, codec.buffer ✅
│  │  Step 5: Serialization Entries → codec.ser ✅
│  │  Step 6: Deserialization Entries → codec.deser ✅
│  │  Step 7: Orchestrators → codec.ser/codec.deser (CodecEObjectSerializer/Deserializer) ✅
│  │  Step 8: Module + Resource → codec.module, codec.resource ✅
│
│  FINAL TEST COUNTS: 1461 tests, 0 failures, 458 skipped
│
│  KEY ARCHITECTURAL CHANGES:
│  │  - EffectiveClassConfig ELIMINATED — configs resolved individually via
│  │    EffectiveCodecConfig.resolveTypeConfig(), resolveIdConfig(), etc.
│  │  - CodecConfiguration + ConfigurationMerger REPLACED by ConfigurationResolver
│  │  - CodecModule now wraps ConfigurationResolver directly
│  │  - CODEC_ROOT_OBJECT renamed to CODEC_ROOT_TYPE
│  │  - Default refKey changed from "_ref" to "$ref" (ConfigProperty defaults)
│  │  - DiagnosticCollector from codec.diagnostic (API) instead of codec.v2.util
│
│  ALL TESTS MIGRATED TO NEW codec.* PACKAGES (2026-02-03):
│  │
│  │  ── Config tests ── ✅ COMPLETE
│  │  Old config classes (codec.v2.config.*) deprecated. Tests for old config system
│  │  no longer needed - replaced by ConfigurationResolver + EffectiveCodecConfig.
│  │
│  │  ── Resource integration tests (23 files, 193 tests) ── ✅ COMPLETE
│  │  All tests now in org.eclipse.fennec.codec.resource package, using:
│  │  - codec.resource.CodecResource
│  │  - codec.config.ConfigurationResolver
│  │  - codec.util.MetadataServiceFactory
│  │
│  │  ── Deferred ser/deser/type integration tests (9 files) ── ✅ COMPLETE
│  │  All tests migrated to new packages with old v2.* versions disabled:
│  │  - codec.ser.ArrayAttributeSerializationTest, EnumSerializationTest,
│  │    ExtendedMetaDataTest, ExpandReferenceTest
│  │  - codec.deser.ArrayAttributeDeserializationTest, DeferredPropertiesDeserializationTest,
│  │    GeoJsonLikeDeserializationTest
│  │  - codec.type.TypeResolutionHintTest, TypeResolutionUriTest
│  │
│  OLD v2.* TESTS DISABLED (9 files with @Disabled annotation):
│  │  - v2/ser/: ArrayAttributeSerializationTest, EnumSerializationTest,
│  │    ExtendedMetaDataTest, ExpandReferenceTest
│  │  - v2/deser/: ArrayAttributeDeserializationTest, DeferredPropertiesDeserializationTest,
│  │    GeoJsonLikeDeserializationTest
│  │  - v2/type/: TypeResolutionHintTest, TypeResolutionUriTest
│  │
│  REMAINING WORK:
│  │  1. Delete old codec.v2.* packages entirely (optional - can keep as reference)
│  │  2. Consider removing @Deprecated annotations once v2.* packages deleted
│
---

COMPLETED: ReferenceConfig Spec Tests - ✅ (2026-02-02)
│
│  Both test files already existed (created in a previous session). Verified
│  all tests pass on 2026-02-02.
│
│  FILES:
│  │  - ReferenceConfigSpecTest.java — 56 tests across 8 sections
│  │    1. Default Values (12) — all 11 properties + ConfigProperty alignment
│  │    2. Format and Key Customization (6) — PLAIN/STRUCTURED, refKey, refTypeKey, proxyKey
│  │    3. Expansion Control (5) — expand, expandGlobal, expandDepth, expandIgnoreBidirectional, serializeInstanceType
│  │    4. Computed Properties shouldExpand() (5) — full truth table
│  │    5. Validation Rules (11) — R-V5, R-V6, R-V7, multiple diagnostics
│  │    6. Custom Value Reader/Writer (4)
│  │    7. Merge Behavior (9) — override, preserve, cascading, enum/boolean/integer merge
│  │    8. Format × Expand Combinations (4)
│  │
│  │  - ReferenceConfigResolverSpecTest.java — 23 tests across 6 sections
│  │    1. Source Hierarchy (6) — R.1-R.6: Options→Resource→Factory→Module→Annotation→Built-in
│  │    2. Scope Chain (4) — R.7-R.10: Global→Class→Feature, per-reference independence
│  │    3. Combined Resolution (2) — R.11-R.12: mixed sources, dynamic overrides static
│  │    4. Caching (3) — R.13-R.15: same feature cached, clearCaches, separate entries
│  │    5. Validation Integration (2) — R.16-R.17: R-V5 during resolution, clean config
│  │    6. Reference-Specific Properties (4) — R.18-R.21: expand, expandGlobal, valueWriter/Reader, serializeInstanceType
│  │    7. Global Reference Config (2) — R.22-R.23: global resolve + caching
│
│  SPEC TEST COVERAGE STATUS (updated 2026-02-02) — ALL COMPLETE:
│  │  | Config Class       | Unit Tests | Spec Tests | Resolver Spec Tests |
│  │  |--------------------|------------|------------|---------------------|
│  │  | TypeConfig         | 41         | 54         | 24 + 20 (shared)    |
│  │  | IdConfig           | 27         | 78         | 17                  |
│  │  | SuperTypeConfig    | 31         | 48         | 21                  |
│  │  | DiscriminatorConfig| 61         | 38         | 17                  |
│  │  | FeatureConfig      | 52         | 64         | 30                  |
│  │  | ReferenceConfig    | 43         | 56         | 23                  |
│
│  NEXT STEPS:
│  │  - All config spec tests complete (6/6) ✅
│  │  - Ready for Phase 2: Serialization/Deserialization integration tests
│  │  - Known bug: v2 deserialization gate uses isSerialize() instead of shouldDeserialize()
│  │    (see completed Feature task below for details)
│
---

COMPLETED: Reference Spec Flows + Spec Property Gap Analysis - ✅ (2026-01-30)
│
│  SPEC UPDATES (10-reference.md):
│  │  - §5.1.1: Updated decision tree — ValueWriter check added as step 1 (before null)
│  │  - §5.1.2: Reference Serialization Flow — 6-step ASCII diagram
│  │    Step order: ValueWriter → Null check → Containment/Expand → URI → Format → Type+Ref
│  │    ValueWriter is step 1 (full delegation, before null check — consistent with all flows)
│  │  - §5.1.3: Serialization Summary table + edge cases
│  │  - §9.2.4: Reference Deserialization Flow — 6-step ASCII diagram
│  │    ValueReader → Format detection (auto from JSON) → Parse (PLAIN/STRUCTURED) →
│  │    Kind decision (has _ref→proxy, no _ref→orphan) → Create → Set on parent
│  │  - §10.3: Config-level validation rules (R-V5, R-V6, R-V7)
│  │  - §10.4: Serialization/Deserialization symmetry table
│  │  - §1.1: PLAIN format limitation documented (no type info, fallback chain)
│  │  - §1.2: "Why STRUCTURED is default" rationale added
│  │  - Both flows: Prerequisite callouts for feature-layer visibility gate
│  │  - Deser flow: Cross-document containment "not yet supported" note
│  │  - Step 6a (STRUCTURED type field): serializeInstanceType logic corrected
│  │    (true→instance type, false→declared type; smart compression as separate concern)
│  │  - Both PLAIN and STRUCTURED deser paths: CODEC_FEATURE_TYPE_HINTS fallback chain
│
│  SPEC UPDATES (16-annotation-reference.md):
│  │  - CODEC_FEATURE_TYPE_HINTS: Rewrote with problem→solution rationale
│  │    Explains WHY hints are needed (PLAIN format, missing _type, abstract types)
│  │    Full priority chain: _type → discriminator → hints → declared type
│  │    Clarified as Load-only (serializer has real object)
│
│  SPEC UPDATES (11-feature.md):
│  │  - §11.6: New section "Relationship to Other Strictness Concepts"
│  │    Three orthogonal mechanisms: strictOnUnknown/Missing (feature-level),
│  │    DeserializationMode (type-level), failFast (diagnostic-level)
│  │    Combination table + "common confusion" callout
│  │  - Feature Deser Flow step 1 (UNKNOWN FIELD): Updated with concrete
│  │    strictOnUnknown decision tree (true→ERROR, false→WARNING)
│  │  - Feature Deser Flow step 4b (MISSING FIELD): Updated with concrete
│  │    strictOnMissing decision tree (true + required→ERROR, false→silent)
│  │  - Implementation status note added
│
│  SPEC PROPERTY GAP ANALYSIS:
│  │  Cross-referenced ALL properties in 16-annotation-reference.md against
│  │  flow diagrams in 06-type, 09-id, 10-reference, 11-feature.
│  │  GAPS IDENTIFIED:
│  │  - Scope properties (typeScope etc.) — config resolution concern, not flow gap
│  │  - strictOnMissing — NOW FIXED in deser flow step 4b
│  │  - Value reader/writer registration — infrastructure, not flow-related
│  │  - failFast vs DeserializationMode — NOW CLARIFIED in §11.6
│
---

COMPLETED: Feature Visibility Model + Spec Tests - ✅ (2026-01-30)
│
│  PHASE 1: Model Change — replaced `serialize` with 5 directional visibility flags
│  │  - codec.ecore: FeatureCodecAspect — removed serialize (EBoolean, default=true),
│  │    added ignore/ignoreRead/ignoreWrite/forceRead/forceWrite (EBoolean, default=false)
│  │  - metadata.ecore: BaseFeatureConfig — same 5 fields as EBooleanObject (nullable)
│  │  - CodecAnnotationConstants: Added KEY_IGNORE, KEY_IGNORE_READ, KEY_IGNORE_WRITE,
│  │    KEY_FORCE_READ, KEY_FORCE_WRITE; deprecated KEY_SERIALIZE, KEY_TRANSIENT
│  │  - CodecAspectProvider: Updated populateFeatureAspect() and buildFeatureConfig()
│  │  - User removed deprecated transient/serialize legacy parsing
│  │  - All test ecore files migrated: transient=true → ignore=true, serialize → ignore
│  │  - All tests fixed: isSerialize() → isIgnore() with inverted boolean logic
│  │
│  PHASE 2: Spec Tests
│  │  - FeatureConfigSpecTest.java — 64 tests across 9 sections
│  │    1. Default Values (12) — all 12 fields verified
│  │    2. Directional Visibility Flags (6)
│  │    3. Computed Properties shouldSerialize/shouldDeserialize (12) — full truth tables
│  │    4. Validation Rules (9) — 3 contradictory combos, multiple warnings, clean
│  │    5. Serialize Value Flags (4)
│  │    6. Enum Serialization Strategy (4)
│  │    7. Key Customization (3)
│  │    8. Custom Value Reader/Writer (5)
│  │    9. Merge Behavior (9)
│  │
│  │  - FeatureConfigResolverSpecTest.java — 30 tests across 8 sections
│  │    1. Source Hierarchy (6) — Options→Resource→Factory→Module→Annotation
│  │    2. Scope Chain (6) — Feature→Class→Global (3-level)
│  │    3. Combined Resolution (4)
│  │    4. Directional Visibility through Resolver (3)
│  │    5. Feature-specific Properties (4)
│  │    6. Caching (3)
│  │    7. Validation Integration (2)
│  │    8. Global Feature Config (2)
│  │
│  KEY FINDING: v2 Deserialization Gate Bug - ✅ FIXED (2026-02-04)
│  │  LOCATION: ConfigurationResolver.java:420 (resolveFeatureConfig)
│  │  BUG: Only checked isForceWrite() for volatile features, not isForceRead()
│  │  FIX: Changed to `if (!resolved.isForceWrite() && !resolved.isForceRead())`
│  │  IMPACT: forceRead now correctly enables deserialization of volatile features
│  │  ALSO FIXED: forceWrite two-gate model - forceWrite affects visibility gate only,
│  │              not value gate (serializeNull/Empty/Default still apply)
│  │
│  SPEC GAPS IDENTIFIED (NOT YET ADDRESSED):
│  │  1. Spec §1.2 doesn't document the entry-build pattern (buildDeserializationEntries)
│  │  2. Spec §1.2 doesn't explicitly state deserialization gate should use shouldDeserialize()
│  │  3. Spec doesn't document the isChangeable() pre-check for non-changeable features
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
│  SPEC TEST COVERAGE STATUS (updated 2026-02-02) — ALL COMPLETE:
│  │  | Config Class       | Unit Tests | Spec Tests | Resolver Spec Tests |
│  │  |--------------------|------------|------------|---------------------|
│  │  | TypeConfig         | 41         | 54         | 24 + 20 (shared)    |
│  │  | IdConfig           | 27         | 78         | 17                  |
│  │  | SuperTypeConfig    | 31         | 48         | 21                  |
│  │  | DiscriminatorConfig| 61         | 38         | 17                  |
│  │  | FeatureConfig      | 52         | 64         | 30                  |
│  │  | ReferenceConfig    | 43         | 56         | 23                  |
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
│  GOAL: Define and document priority for ValueWriter/ValueReader in serialization
│        and deserialization flows. ValueWriter/Reader should be step 1 in ALL flows,
│        taking priority over all default behaviors.
│
│  SPEC UPDATES:
│  - 06-type.md:
│    §5.1.1: Type Serialization Flow — ValueWriter is step 1 (before NONE check)
│    §5.1.2: "ValueWriter registered for this EClass? → Use it (bypass all steps)"
│    §9.1: Type Deserialization Flow — ValueReader is step 1 (before format detection)
│    §9.2: "ValueReader registered? → Use it (bypass all steps)"
│
│  - 09-id.md:
│    §10: ID Serialization Flow — ValueWriter is step 1 (before idKeyMode check)
│    §11: ID Deserialization Flow — ValueReader is step 1 (before field location)
│    Both flows: "ValueWriter/Reader = full delegation, bypass all ID logic"
│
│  - 10-reference.md:
│    §5.1.2: Reference Serialization Flow — ValueWriter is step 1 (before null check)
│    §9.2.4: Reference Deserialization Flow — ValueReader is step 1 (before format detection)
│    Both flows: Full delegation with bypass semantics
│
│  - 11-feature.md (Attribute):
│    §4.1: Attribute Serialization Flow — ValueWriter is step 1
│    §8.1: Attribute Deserialization Flow — ValueReader is step 1
│    Both flows: Full delegation documented
│
│  CONSISTENCY: All 4 config domains (Type, ID, Reference, Attribute) now have
│                consistent ValueWriter/ValueReader as step 1 with full delegation.
│
---

COMPLETED: idOnTop Runtime Ordering Constraint Documentation - ✅ (2026-01-29)
│
│  SPEC UPDATES:
│  - 09-id.md §10 (ID Serialization Flow) — Added step 7 "Field Ordering" at bottom
│  - 09-id.md §3.1 (PLAIN format) — Updated existing NOTE about idOnTop constraint
│  - Cross-reference added: §3.1 NOTE → §10 Step 7
│
│  NO CODE CHANGES (runtime behavior already correct)
│
---

COMPLETED: Cross-Verification of Spec Docs 06-09 vs 16-annotation-reference.md - ✅ (2026-01-29)
│
│  GOAL: Verify Type (06) + SuperType (07) + Discriminator Mapping (08) + ID (09)
│        all have complete serialization/deserialization flow diagrams matching
│        the annotation reference (16).
│
│  FINDINGS:
│  ✅ 06-type.md: Has §5.1 Ser Flow + §9.1 Deser Flow (added earlier)
│  ✅ 07-supertype.md: Ser/deser flows not needed (orthogonal layer, doesn't own a flow)
│  ✅ 08-discriminator-mapping.md: Doesn't own ser/deser flows (works via TypeConfig)
│  ✅ 09-id.md: Has §10 Ser Flow + §11 Deser Flow (added 2026-01-29)
│  ✅ 10-reference.md: Has §5.1.2 Ser Flow + §9.2.4 Deser Flow (added 2026-01-30)
│  ✅ 11-feature.md: Has §4 Ser Flow + §8 Deser Flow (pre-existing, verified 2026-01-30)
│
│  ALL FLOW DIAGRAMS VERIFIED AND CONSISTENT
│
---

COMPLETED: ID Spec Tests + Bug Fix (codec.api + codec.metadata audit) - ✅ (2026-01-29)
│
│  GOAL: Create spec-based tests for ID configuration (IdConfig + IdConfigResolverSpecTest).
│
│  PHASE 1: IdConfigSpecTest.java (78 tests)
│  │  1. Default Values (13 tests) — all 10 fields + ConfigProperty alignment
│  │  2. Key Customization (6 tests) — idValueKey, idTypeKey, idKey behavior
│  │  3. IdKeyMode Behavior (13 tests) — full coverage of ID_ONLY, TYPE_ONLY, COMBINED, NONE
│  │  4. Format and Mode Combinations (8 tests) — PLAIN × STRUCTURED × 4 modes
│  │  5. ID Features Configuration (8 tests) — single/multiple features, null/empty/order
│  │  6. Validation Rules (13 tests) — ID-V5..V9 (5 rules × clean+multiple variants)
│  │  7. Custom Value Reader/Writer (4 tests)
│  │  8. ID On Top Flag (4 tests) — true/false, PLAIN requirement
│  │  9. Merge Behavior (9 tests) — override, preserve, cascading, idFeatures merge
│
│  PHASE 2: IdConfigResolverSpecTest.java (17 tests)
│  │  Already created in SuperType+Discriminator resolver refactoring task
│
│  BUG FOUND & FIXED:
│  │  - ConfigPropertyConstants.DEFAULT_ID_ON_TOP was false
│  │  - IdConfig.DEFAULT_ID_ON_TOP was true
│  │  - Spec 09-id.md §12 says default is true
│  │  - Fix: Changed ConfigPropertyConstants.DEFAULT_ID_ON_TOP = true
│  │  - Updated ConfigPropertyConstantsTest assertion
│
│  FILES CREATED:
│  - org.eclipse.fennec.codec.api/test/.../config/spec/IdConfigSpecTest.java (78 tests)
│
│  FILES MODIFIED:
│  - org.eclipse.fennec.codec.api/src/.../config/ConfigPropertyConstants.java (bug fix)
│  - org.eclipse.fennec.codec.api/test/.../config/ConfigPropertyConstantsTest.java (assertion)
│
│  RELATED TASKS:
│  - IdConfigResolverSpecTest.java (17 tests) — created in prior session
│  - 09-id.md validation section — created in Layer 1 Annotation Validation task
│
│  ALL TESTS PASSING (78 spec + 17 resolver + 27 existing unit = 122 total ID tests)
│
---

COMPLETED: Prepare spec for TCK test creation (Type + SuperType configuration) - ✅
│  (2026-01-28)
│
│  GOAL: Review 06-type.md + 07-supertype.md for TCK-readiness. Create spec-based
│        tests that verify the configuration API matches the spec. Use nested
│        classes for organization (matching discriminator pattern).
│
│  PHASE 1: TypeConfigSpecTest.java (54 tests)
│  │  1. Default Values (13) — all 10 fields + ConfigProperty alignment
│  │  2. TypeStrategy Behavior (9) — NAME/URI/NONE/null with validation
│  │  3. Key Customization (5) — typeKey, typeDiscriminatorKey (T-V6)
│  │  4. Type Discriminator (5) — value, path, fallback
│  │  5. Type Map ID (4) — valid ID + T-V8 (both together)
│  │  6. Validation Rules (9) — T-V6, T-V8, multiple diagnostics, clean config
│  │  7. Custom Value Reader/Writer (5) — registration + T-V9
│  │  8. Merge Behavior (4) — override, preserve, cascading (null stays null)
│
│  PHASE 2: SuperTypeConfigSpecTest.java (48 tests)
│  │  1. Default Values (12) — all 9 fields + ConfigProperty alignment
│  │  2. SuperTypeStrategy Behavior (13) — ALL/ROOT/NONE/PROPERTIES/null
│  │  3. Key Customization (4) — superTypeKey, superTypesKey
│  │  4. SerializationFormat Interaction (7) — PLAIN + PROPERTIES/NONE + warning
│  │  5. Validation Rules (4) — ST-V3 (FORMAT+STRATEGY), multiple, clean
│  │  6. Custom Value Reader/Writer (4) — ST-V4
│  │  7. Merge Behavior (4) — override, preserve, cascading
│
│  PHASE 3: Resolver Tests (TypeConfigResolverSpecTest — 24 tests)
│  │  Already created in prior session (2026-01-27)
│
│  PHASE 4: Resolver Tests (SuperTypeConfigResolverSpecTest — 21 tests)
│  │  Already created in refactoring task (2026-01-29)
│
│  FILES CREATED:
│  - org.eclipse.fennec.codec.api/test/.../config/spec/TypeConfigSpecTest.java
│  - org.eclipse.fennec.codec.api/test/.../config/spec/SuperTypeConfigSpecTest.java
│
│  RELATED FILES:
│  - TypeConfigResolverSpecTest.java (24 tests) — from prior session
│  - SuperTypeConfigResolverSpecTest.java (21 tests) — from refactoring task
│
│  ALL TESTS PASSING (54 + 48 + 24 + 21 = 147 total spec tests for Type + SuperType)
│
---
