# Conversation Summary: Issue #48 Discussion

**Initial Date:** 2025-12-03
**Last Updated:** 2025-12-16
**Topic:** TYPE_STRATEGY=URI without ROOT_OBJECT
**GitHub Issue:** https://github.com/geckoprojects-org/org.gecko.codec/issues/48

---

## Current Status: Phase 1 Complete ✅

As of 2025-12-16, the codec.v2 implementation has achieved **Phase 1 completion** with full round-trip serialization/deserialization support, including:

- **Attributes**: String, Integer, Boolean, Double, and multi-valued (lists)
- **Containment references**: Single and multi-valued nested objects
- **Non-containment references**: Single and multi-valued with `$ref` format
- **Reference resolution**: Post-deserialization resolution within the same resource

See `docs/codec-v2-development-guide.md` for architectural details and `docs/codec-v2-serialization-spec.md` Section 22 for the full implementation checklist.

---

## What We Discussed

### 1. Issue #48 Analysis
- Goal: Deserialize EMF objects using `_type` URI without explicit `CODEC_ROOT_OBJECT`
- Current limitation: `doLoad()` requires `CODEC_ROOT_OBJECT` to get `PackageCodecInfo`

### 2. Architectural Analysis
- Identified 8 architectural issues in current codebase
- 3 blocking issues for Issue #48:
  - PackageCodecInfo chicken-and-egg problem
  - URIReader OSGi dependency
  - Type key needed before type is known
- **Document:** `docs/issue-48-architectural-analysis.md`

### 3. Initial Strategy (TypeInfoService)
- Proposed `TypeInfoService` to decouple type resolution
- **Document:** `docs/type-info-service-architecture.md`

### 4. Broader Vision (Model Metadata Service)
- User's idea: General-purpose metadata service using **Aspect Pattern**
- Applicable beyond codec: ORM, GDPR, OpenAPI, Units of Measurement
- **User's document:** `org.eclipse.fennec.model.metadata/model-metadata-architecture.md`

### 5. Decision: New Project Approach
- Refactoring existing code too risky/complicated
- Create new projects instead:
  - `org.eclipse.fennec.model.metadata` - Core metadata framework
  - `org.eclipse.fennec.codec.metadata` - Codec aspects (replaces codec.info)
  - `org.eclipse.fennec.codec.v2` - Clean codec implementation
- Migrate features incrementally with tests
- **Document:** `docs/codec-v2-migration-strategy.md`

### 6. My Additions to Metadata Architecture
- EClass-level aspects (not just features)
- Aspect inheritance support
- Contextual aspects (profiles)
- AspectProvider SPI for pluggable loading
- Package-level info

### 7. Open Design Decision
- User prefers NOT using `Class<?>` as AspectType identifier
- Alternatives: String keys, custom AspectType interface, URIs

### 8. codec.metadata as First Aspect Implementation
- `org.eclipse.fennec.codec.info` → `org.eclipse.fennec.codec.metadata`
- Codec metadata becomes the first aspect implementation
- Validates the model.metadata architecture with real use case
- Dependency chain: `codec.v2 → codec.metadata → model.metadata`
- **Document:** `org.eclipse.fennec.codec.metadata/codec-metadata-architecture.md`

---

## Documents Created

| Document | Purpose |
|----------|---------|
| `docs/issue-48-architectural-analysis.md` | Analysis of current architecture issues |
| `docs/type-info-service-architecture.md` | Initial TypeInfoService proposal |
| `docs/codec-v2-migration-strategy.md` | Migration strategy for new projects |
| `org.eclipse.fennec.model.metadata/model-metadata-architecture.md` | User's metadata service vision |
| `org.eclipse.fennec.codec.metadata/codec-metadata-architecture.md` | Codec aspects architecture |

---

## Completed Steps ✅

1. ~~**Decide on AspectType identifier**~~ - Using String-based aspect names
2. ~~**Create `org.eclipse.fennec.model.metadata` project structure**~~ - Complete
3. ~~**Implement core interfaces**~~ - MetadataService, PackageMetadata, ClassMetadata, etc.
4. ~~**Create `org.eclipse.fennec.codec.metadata` project structure**~~ - Complete
5. ~~**Implement codec aspects**~~ - ClassCodecAspect, FeatureCodecAspect, etc.
6. ~~**Create codec.v2 project structure**~~ - Complete
7. ~~**Implement serialization**~~ - CodecEObjectSerializer with entry pattern
8. ~~**Implement deserialization**~~ - CodecEObjectDeserializer with entry pattern
9. ~~**Create round-trip tests**~~ - Full integration tests passing

## Next Steps (Phase 2+)

1. **EAnnotation-based configuration** - Read annotations to customize behavior
2. **Additional TypeStrategies** - NAME, MAPPED, SCHEMA_AND_TYPE
3. **Cross-resource references** - ResourceSet-based resolution
4. **Custom value readers/writers** - CodecValueRegistry integration
5. **OSGi integration** - ResourceFactory registration

---

## Files Modified (Recent Sessions)

### 2025-12-16: Phase 1 Completion
- Created full serialization framework (`org.eclipse.fennec.codec.v2.ser.*`)
- Created full deserialization framework (`org.eclipse.fennec.codec.v2.deser.*`)
- Created effective configuration pattern (`org.eclipse.fennec.codec.v2.config.effective.*`)
- Implemented `CodecResource` with `doSave()`, `doLoad()`, and reference resolution
- Created comprehensive round-trip integration tests
- Fixed containment reference deserialization (removed unnecessary `CODEC_ROOT_OBJECT` hint)
- Implemented within-resource reference resolution for non-containment refs
- Added test model with non-containment references (`test-roundtrip.ecore`)

---

## Current Branch

`issue#48` - Contains complete codec.v2 Phase 1 implementation
