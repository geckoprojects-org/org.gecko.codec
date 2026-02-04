# Codec V2 Migration Strategy

## Overview

This document outlines the strategy for creating a new codec implementation (`o.e.f.codec.v2`) based on the new Model Metadata Service (`o.e.f.model.metadata`), migrating features incrementally from the existing codec.

---

## Project Structure

```
org.eclipse.fennec.model.metadata        (NEW - core metadata framework)
    ├── ModelInfoService (registry)
    ├── RuntimePackageInfo / RuntimeClassInfo / RuntimeFeatureInfo
    ├── Aspect pattern interfaces
    └── AspectProvider SPI

org.eclipse.fennec.codec.metadata        (NEW - codec aspects, replaces codec.info)
    ├── ClassCodecAspect, FeatureCodecAspect
    ├── TypeResolutionInfo, IdentityInfo
    ├── CodecAspectProvider (reads codec EAnnotations)
    └── CodecValueReader/Writer

org.eclipse.fennec.codec.v2              (NEW - clean codec implementation)
    ├── Uses codec.metadata for all metadata needs
    ├── Clean architecture from start
    └── Incremental feature migration from v1

org.eclipse.fennec.codec                 (EXISTING - v1, maintained during transition)
    ├── Continues to work as-is
    ├── No breaking changes
    └── Eventually deprecated

org.eclipse.fennec.codec.info            (EXISTING - v1 metadata, replaced by codec.metadata)
    ├── Maintained during transition
    └── Eventually deprecated
```

**Dependency Chain:**
```
codec.v2 ──→ codec.metadata ──→ model.metadata
                  │
                  └── (replaces codec.info)
```

---

## Principles

### 1. Incremental Migration
- Migrate one feature at a time
- Each feature must have tests before migration
- V1 and V2 can coexist

### 2. Test-Driven
- Only migrate features that have test coverage in V1
- Write tests for V2 first, then implement
- Ensure feature parity before marking complete

### 3. Clean Slate
- No legacy patterns in V2
- Apply lessons learned from V1
- Follow architectural decisions from metadata service

### 4. Backward Compatibility
- V1 remains functional throughout migration
- Users can adopt V2 incrementally
- Clear migration guide for each feature

---

## Phase 1: Foundation (model.metadata)

### 1.1 Core Interfaces

Create the foundational interfaces for the metadata service:

```
org.eclipse.fennec.model.metadata/
├── api/
│   ├── ModelInfoService.java
│   ├── RuntimePackageInfo.java
│   ├── RuntimeClassInfo.java
│   ├── RuntimeFeatureInfo.java
│   ├── Aspect.java
│   └── AspectType.java (not Class-based)
├── provider/
│   ├── AspectProvider.java
│   └── AspectProviderRegistry.java
└── impl/
    └── DefaultModelInfoService.java
```

**Key Design Decisions:**
- `AspectType` as interface/identifier, not `Class<?>`
- Immutable runtime info objects
- Lazy aspect resolution with caching

### 1.2 Built-in AspectProviders

```
├── provider/
│   ├── EAnnotationAspectProvider.java    (reads EAnnotations)
│   ├── ConventionAspectProvider.java     (default conventions)
│   └── ExternalConfigAspectProvider.java (external .modelinfo files)
```

**Priority cascade:**
1. External Config (highest)
2. EAnnotations
3. Conventions (fallback)

### 1.3 Testing (model.metadata)

- Unit tests for each interface
- Integration test: register package, retrieve aspects
- Performance test: O(1) lookup verification

---

## Phase 1b: Codec Metadata (codec.metadata)

### 1b.1 Project Setup

```
org.eclipse.fennec.codec.metadata/
├── api/
│   ├── ClassCodecAspect.java
│   ├── FeatureCodecAspect.java
│   ├── TypeResolutionInfo.java
│   ├── IdentityInfo.java
│   └── CodecAspectTypes.java        (aspect type constants)
├── provider/
│   ├── CodecAspectProvider.java     (reads codec EAnnotations)
│   └── CodecConventionProvider.java (default conventions)
├── readers/
│   ├── CodecValueReader.java
│   ├── URIReader.java
│   ├── EClassNameReader.java
│   └── InstanceClassNameReader.java
└── writers/
    ├── CodecValueWriter.java
    ├── URIWriter.java
    └── ...
```

### 1b.2 Mapping from V1 (codec.info → codec.metadata)

| V1 `codec.info` | V2 `codec.metadata` |
|-----------------|---------------------|
| `PackageCodecInfo` | Aspects on `RuntimePackageInfo` |
| `EClassCodecInfo` | `ClassCodecAspect` on `RuntimeClassInfo` |
| `FeatureCodecInfo` | `FeatureCodecAspect` on `RuntimeFeatureInfo` |
| `TypeInfo` | `TypeResolutionInfo` (part of `ClassCodecAspect`) |
| `IdentityInfo` | `IdentityInfo` (part of `ClassCodecAspect`) |
| `CodecModelInfo` service | `ModelInfoService` + aspect queries |
| `CodecInfoHolderHelper` | `CodecAspectProvider` |
| `CodecValueReader/Writer` | Migrated to `readers/` and `writers/` |

### 1b.3 EAnnotation Compatibility

The `CodecAspectProvider` reads existing V1 EAnnotations:
- `codec.id` → `IdentityInfo`
- `codec.type` → `TypeResolutionInfo`
- `codec.feature` → `FeatureCodecAspect`
- `codec.transient` → `FeatureCodecAspect.ignore`

This ensures backward compatibility with existing EMF models.

### 1b.4 Testing

- Unit tests for each interface
- Integration test: register package, retrieve aspects
- Performance test: O(1) lookup verification

---

## Phase 2: Codec V2 Core

### 2.1 Project Setup

```
org.eclipse.fennec.codec.v2/
├── api/
│   ├── CodecResource.java
│   ├── CodecResourceFactory.java
│   └── CodecOptions.java
├── jackson/
│   ├── module/
│   │   └── CodecModule.java
│   ├── ser/
│   │   └── (serializers)
│   └── deser/
│       └── (deserializers)
└── impl/
    └── (implementations)
```

### 2.2 Key Architectural Changes

**From V1:**
```java
// V1: Tight coupling, requires EClassCodecInfo upfront
CodecModule module = new CodecModule.Builder()
    .bindCodecModelInfo(packageCodecInfo)  // Required!
    .build();
```

**To V2:**
```java
// V2: Loose coupling, uses ModelInfoService
CodecModule module = new CodecModule.Builder()
    .withModelInfoService(modelInfoService)  // Service, not data
    .build();

// Type resolution happens dynamically via aspects
```

### 2.3 Initial Features (Minimal Viable Codec)

1. **Basic Serialization**
   - Serialize EObject to JSON
   - Use FeatureCodecAspect for property names

2. **Basic Deserialization**
   - Deserialize JSON to EObject
   - Requires CODEC_ROOT_OBJECT (like V1)

3. **Type Serialization**
   - Write `_type` field using ClassCodecAspect

---

## Phase 3: Feature Migration

Migrate features from V1 to V2 one by one. Each feature follows this process:

### Migration Process per Feature

```
1. Identify V1 tests for feature
2. Create equivalent V2 tests (should fail initially)
3. Implement feature in V2
4. Verify V2 tests pass
5. Compare V1/V2 output for same input
6. Document any behavioral differences
7. Mark feature as migrated
```

### Feature Migration Order

Priority based on: dependencies, complexity, usage frequency.

#### Wave 1: Core Serialization
| Feature | V1 Location | V2 Target | Tests |
|---------|-------------|-----------|-------|
| EAttribute serialization | `FeatureCodecInfoSerializer` | `FeatureSerializer` | ✓ |
| EReference (contained) | `FeatureCodecInfoSerializer` | `FeatureSerializer` | ✓ |
| Type field (`_type`) | `TypeCodecInfoSerializer` | `TypeSerializer` | ✓ |
| ID field (`_id`) | `IdCodecInfoSerializer` | `IdSerializer` | ✓ |

#### Wave 2: Core Deserialization
| Feature | V1 Location | V2 Target | Tests |
|---------|-------------|-----------|-------|
| EAttribute deserialization | `FeatureCodecInfoDeserializer` | `FeatureDeserializer` | ✓ |
| EReference (contained) | `FeatureCodecInfoDeserializer` | `FeatureDeserializer` | ✓ |
| Type resolution (with ROOT_OBJECT) | `CodecEObjectDeserializer` | `EObjectDeserializer` | ✓ |
| ID deserialization | `IdCodecInfoDeserializer` | `IdDeserializer` | ✓ |

#### Wave 3: Advanced Features
| Feature | V1 Location | V2 Target | Tests |
|---------|-------------|-----------|-------|
| Non-contained references | `ReferenceCodecInfoSerializer` | `ReferenceSerializer` | ? |
| SuperType serialization | `SuperTypeCodecInfoSerializer` | `SuperTypeSerializer` | ? |
| Custom value readers/writers | `CodecValueReader/Writer` | Aspect-based | ? |
| Extended metadata names | `CodecModule` option | Aspect config | ? |

#### Wave 4: Issue #48 (Dynamic Type Resolution)
| Feature | V1 Location | V2 Target | Tests |
|---------|-------------|-----------|-------|
| URI type strategy | `URIReader/Writer` | `TypeResolutionAspect` | NEW |
| Deserialize without ROOT_OBJECT | Not supported | `EObjectDeserializer` | NEW |
| Dynamic PackageCodecInfo | Not supported | Via `ModelInfoService` | NEW |

#### Wave 5: Format Support
| Feature | V1 Location | V2 Target | Tests |
|---------|-------------|-----------|-------|
| JSON format | `codec.json` | `codec.v2.json` | ✓ |
| CSV format | `codec.csv` | `codec.v2.csv` | ? |
| MongoDB format | `codec.mongo` | `codec.v2.mongo` | ? |

---

## Phase 4: Integration & Testing

### 4.1 Comparison Testing

Run same test data through V1 and V2, compare outputs:

```java
@Test
void compareV1andV2Output() {
    EObject testObject = createTestObject();

    String v1Json = serializeWithV1(testObject);
    String v2Json = serializeWithV2(testObject);

    assertJsonEquals(v1Json, v2Json);
}
```

### 4.2 Performance Testing

Ensure V2 is at least as fast as V1:

```java
@Test
void v2PerformanceNotWorse() {
    long v1Time = benchmarkV1();
    long v2Time = benchmarkV2();

    assertThat(v2Time).isLessThanOrEqualTo(v1Time * 1.1); // 10% tolerance
}
```

### 4.3 Non-OSGi Testing

Dedicated tests for plain Java usage:

```java
@Test
void v2WorksWithoutOSGi() {
    ModelInfoService service = new DefaultModelInfoService();
    service.registerPackage(TestPackage.eINSTANCE);

    CodecSetup setup = new CodecSetup(service);
    // ... test serialization/deserialization
}
```

---

## Phase 5: Documentation & Deprecation

### 5.1 Migration Guide

Create user-facing migration guide:

```markdown
# Migrating from Codec V1 to V2

## Maven/Gradle Dependencies
- Remove: org.eclipse.fennec.codec
- Remove: org.eclipse.fennec.codec.info
- Add: org.eclipse.fennec.codec.v2
- Add: org.eclipse.fennec.codec.metadata
- Add: org.eclipse.fennec.model.metadata

## Code Changes
- `JsonResourceFactory` → `JsonResourceFactoryV2`
- `CodecModelInfo` → `ModelInfoService` + codec aspects
- `CodecOptionsBuilder` → (new API)
- EAnnotations remain compatible (read by CodecAspectProvider)
```

### 5.2 Deprecation Timeline

```
Month 1-3:  V2 development, V1 unchanged
Month 4-6:  V2 feature-complete, V1 deprecated
Month 7-9:  V1 maintenance only (security fixes)
Month 10+:  V1 removed in next major version
```

---

## Success Criteria

### Phase 1 Complete When (model.metadata): ✅ COMPLETE
- [x] ModelInfoService can register EPackage
- [x] RuntimeClassInfo and RuntimeFeatureInfo accessible
- [x] Aspect interface and AspectProvider SPI defined
- [x] TypeDiscriminatorRegistry for mapId-based discrimination
- [x] Unit tests passing (22+ tests)

### Phase 1b Complete When (codec.metadata): ✅ COMPLETE
- [x] ClassCodecAspect and FeatureCodecAspect defined
- [x] CodecAspectProvider reads V1 EAnnotations
- [x] TypeResolutionInfo, IdentityInfo, SuperTypeInfo implemented
- [x] MapId-based discriminator support (static + dynamic)
- [x] Integration with model.metadata working
- [x] Unit tests passing (80+ tests across integration and unit)

### Phase 2 Complete When (codec.v2 core): 🔲 NOT STARTED
- [ ] CodecReaderWriterRegistry implemented
- [ ] Basic serialization works
- [ ] Basic deserialization works (with ROOT_OBJECT)
- [ ] Integration with ModelInfoService complete

### Phase 3 Complete When:
- [ ] All Wave 1-3 features migrated
- [ ] Comparison tests passing
- [ ] Performance acceptable

### Phase 4 (Issue #48) Complete When:
- [ ] URI type strategy works without ROOT_OBJECT
- [ ] Dynamic type resolution via aspects
- [ ] Tests for all scenarios

### Migration Complete When:
- [ ] All V1 features available in V2
- [ ] Documentation complete
- [ ] No V1 dependencies in new code

---

## Open Questions

1. ~~**AspectType identifier:** String, enum, URI, or custom type?~~
   **RESOLVED:** Using `AspectType` interface with `SimpleAspectType` implementation. Codec defines `CodecAspectTypes` constants.

2. ~~**Aspect inheritance:** How to handle EClass inheritance for aspects?~~
   **RESOLVED:** `ClassCodecAspect.isInheritFromParent()` flag controls inheritance. Providers can walk supertype chain.

3. **Contextual aspects:** Support multiple profiles/contexts?
   **OPEN:** Not yet implemented. Could add context parameter to aspect queries if needed.

4. ~~**OSGi vs Plain Java:** Same API for both, or separate modules?~~
   **RESOLVED:** Same API works for both. OSGi uses component services; plain Java uses direct instantiation.

5. ~~**Backward compatibility:** Can V2 read V1 EAnnotations directly?~~
   **RESOLVED:** Yes. `CodecAspectProvider` reads all V1 EAnnotations (`codec.id`, `codec.type`, `codec.feature`, etc.)

---

## Current Status (December 2025)

### Completed

#### Phase 1: model.metadata ✅
- [x] `ModelInfoService` interface and `DefaultModelInfoService` implementation
- [x] `RuntimePackageInfo`, `RuntimeClassInfo`, `RuntimeFeatureInfo` interfaces
- [x] `Aspect` and `AspectType` pattern interfaces
- [x] `AspectProvider` SPI with priority-based resolution
- [x] `TypeDiscriminatorRegistry` for mapId-based type discrimination
- [x] Comprehensive unit tests

#### Phase 1b: codec.metadata ✅
- [x] `ClassCodecAspect` with TypeResolutionInfo, IdentityInfo, SuperTypeInfo
- [x] `FeatureCodecAspect` with ignore flags, key mapping, reader/writer names
- [x] `CodecAspectProvider` reads all V1 EAnnotations
- [x] `TypeDiscriminatorContributor` for static/dynamic discriminator registration
- [x] Strategy enums: `IdStrategy`, `TypeStrategy`, `SuperTypeStrategy`
- [x] MapId-based type discrimination (`codec.type.{mapId}` pattern)
- [x] Comprehensive integration tests with ecore files

### Remaining Gaps (codec.info → codec.metadata)

The following functionality from V1 `codec.info` is **intentionally not replicated** in `codec.metadata` because it belongs in the codec layer (codec.v2):

#### Critical Gaps (Must Address in codec.v2)

| Gap | V1 Location | Resolution Strategy |
|-----|-------------|---------------------|
| **Value Reader/Writer Registry** | `CodecModelInfo.addCodecValueWriterForType()` | Create `CodecReaderWriterRegistry` in codec.v2 |
| **CodecInfoHolder Pattern** | `getCodecInfoHolderByType(InfoType)` | Replace with aspect queries + registry |
| **InfoType Classification** | `InfoType` enum | Not needed - use aspect types instead |

#### Important Gaps (Address in codec.v2)

| Gap | V1 Location | Resolution Strategy |
|-----|-------------|---------------------|
| **codecExtraProperties Map** | `EClassCodecInfo.codecExtraProperties` | Add extensibility mechanism in codec.v2 |
| **Feature Filtering Collections** | `getReferenceCodecInfo()` etc. | Helper methods in codec.v2 to filter by feature type |
| **EClassCodecInfo Complete Structure** | Single object with all info | Aggregate aspects into CodecClassInfo in codec.v2 |

#### Design Decisions

These are intentional architectural changes, not gaps:

| Change | Rationale |
|--------|-----------|
| **IdentityInfo: feature names vs references** | Names allow lazy resolution; actual features resolved when needed |
| **Separate aspects vs single object** | Cleaner separation of concerns; aspects are composable |
| **No PackageCodecInfo hierarchy** | Flat structure is simpler; hierarchy rarely needed |
| **AspectProvider SPI** | Allows multiple metadata sources (annotations, external config, conventions) |

---

## Gap Resolution Strategy

### Option A: Bridge Layer in codec.v2

Create a bridge that adapts the new metadata API to V1 expectations:

```java
// In codec.v2
public class CodecInfoBridge {
    private final ModelInfoService modelInfoService;
    private final CodecReaderWriterRegistry readerWriterRegistry;

    // Provides V1-compatible API using V2 internals
    public CodecInfoHolder getCodecInfoHolderByType(InfoType type) {
        // Map InfoType to aspect queries + registry lookup
    }
}
```

**Pros:** Existing serializers work with minimal changes
**Cons:** Maintains V1 patterns in V2 codebase

### Option B: Rewrite Serializers (Recommended)

Rewrite serializers to use aspects directly:

```java
// V2 serializer pattern
public class FeatureSerializer {
    public void serialize(EObject obj, JsonGenerator gen) {
        RuntimeClassInfo classInfo = modelInfoService.getClassInfo(obj.eClass()).get();

        for (EStructuralFeature feature : obj.eClass().getEAllStructuralFeatures()) {
            Optional<FeatureCodecAspect> aspect = classInfo.getAspect(feature, FEATURE_CODEC);
            if (aspect.isPresent() && !aspect.get().isIgnore()) {
                String key = aspect.get().getKey().orElse(feature.getName());
                // ... serialize
            }
        }
    }
}
```

**Pros:** Clean architecture, no legacy patterns
**Cons:** More work upfront, but cleaner long-term

### Option C: Hybrid Approach

1. Create minimal `CodecReaderWriterRegistry` for custom readers/writers
2. Rewrite serializers to use aspects directly
3. Use registry only for custom reader/writer lookup

---

## Next Steps

### Immediate (codec.v2 Foundation)

1. Create `org.eclipse.fennec.codec.v2` project structure
2. Implement `CodecReaderWriterRegistry` for custom readers/writers
3. Define `CodecValueReader<S,T>` and `CodecValueWriter<T,S>` interfaces
4. Port standard readers/writers (URI, EClassName, etc.)

### Short-term (Core Serialization)

5. Implement `CodecEObjectSerializer` using aspects
6. Implement `CodecEObjectDeserializer` using aspects
7. Create `CodecModule` for Jackson integration
8. Write comparison tests (V1 vs V2 output)

### Medium-term (Feature Parity)

9. Migrate Wave 1 features (attributes, contained references)
10. Migrate Wave 2 features (type/id serialization)
11. Migrate Wave 3 features (non-contained refs, supertypes)

### Long-term (Issue #48)

12. Implement dynamic type resolution without ROOT_OBJECT
13. Implement URI-based type strategy
14. Full discriminator-based deserialization
