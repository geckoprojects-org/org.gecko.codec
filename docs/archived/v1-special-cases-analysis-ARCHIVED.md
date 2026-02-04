# V1 Special Cases Analysis

This document maps special cases found in the v1 codec implementation to their coverage in the v2 spec (`codec-v2-serialization-spec.md`).

## Special Cases Status

| # | Special Case | Spec Coverage | Action Required |
|---|-------------|---------------|-----------------|
| 1 | Type Key as Feature Path | ✅ Covered | See Section 3 (MAPPED strategy, typeFeaturePath) |
| 2 | Type Mapping (discriminator values) | ✅ Covered | See Section 3 (MAPPED strategy, discriminatorValue, typeMap) |
| 3 | Global Ignore Feature Names | ✅ Covered | See Section 8.6 (globalIgnoreFeatures) |
| 4 | ID Field Skip (serializeIdField) | ✅ Covered | See Section 4.2 (IdKeyMode: ID_ONLY, BOTH, FEATURE_ONLY) |
| 5 | ID Feature Skip during Feature Serialization | ✅ Covered | Implicit in IdKeyMode - ID_ONLY means skip feature serialization |
| 6 | Non-Containment References | ✅ Covered | See Section 5 (Reference Serialization) |
| 7 | Proxy Handling | ✅ Covered | See Section 5.4 (Proxy and Expand Handling) |
| 8 | Value Writers/Readers | ✅ Covered | See Section 10 (Custom Value Readers/Writers) |
| 9 | Field Ordering | ✅ Covered | See Section 8.5 (fieldOrder, metadataFieldsFirst) |
| 10 | EMFCodecContext | ✅ Covered | See Section 18.11 (Custom Generators/Parsers) |

---

## Detailed Analysis

All special cases from v1 are now covered in the v2 spec:

| Case | V1 Location | V2 Spec Section |
|------|-------------|-----------------|
| Type Key as Feature Path | `TypeCodecInfoSerializer:75-97` | Section 3 - MAPPED strategy with `typeFeaturePath` |
| Type Mapping | `TypeCodecInfoSerializer:102-105` | Section 3 - MAPPED strategy with `discriminatorValue`, `typeMap` |
| Global Ignore | `CodecModule:72`, `FeatureCodecInfoSerializer:74-77` | Section 8.6 - `globalIgnoreFeatures` |
| ID Field Skip | `FeatureCodecInfoSerializer:81-85` | Section 4.2 - IdKeyMode (ID_ONLY, BOTH, FEATURE_ONLY) |
| Non-Containment Refs | `ReferenceCodecInfoSerializer:204-266` | Section 5 - Reference Serialization |
| Proxy Handling | `ReferenceCodecInfoSerializer:323-336` | Section 5.4 - Proxy and Expand Handling |
| Value Writers/Readers | `FeatureCodecInfoSerializer:143-148` | Section 10 - Custom Value Readers/Writers |
| Field Ordering | `CodecEObjectSerializer:120-146` | Section 8.5 - `fieldOrder`, `metadataFieldsFirst` |
| EMFCodecContext | `EMFCodecContext`, `EMFCodecWriteContext` | Section 18.11 - EMF Codec Context |

---

## Spec Changes Made (December 2025)

### 1. Section 8.5 - Field Ordering (NEW)
Added configuration for:
- `FieldOrder` enum: `DECLARATION` (default), `ALPHABETICAL`
- `metadataFieldsFirst` option (default: true)
- Order: `_id`, `_type`, `_supertype`, then features

### 2. Section 8.6 - Global Feature Ignore List (NEW)
Added `globalIgnoreFeatures` option:
```java
CodecConfig config = CodecConfig.builder()
    .globalIgnoreFeatures("createdAt", "updatedAt", "version")
    .build();
```

### 3. Section 18.11 - EMF Codec Context (NEW)
Documented the internal context mechanism for custom Jackson generators/parsers:
- `EMFCodecWriteContext` interface for serialization
- `EMFCodecReadContext` interface for deserialization
- Usage examples for custom serializers/deserializers
- Custom generator/parser implementation guide

---

## Next Steps

1. ~~Update spec with the three changes above~~ ✅ DONE
2. Implement `EMFCodecWriteContext` / `EMFCodecReadContext` in codec.v2
3. Implement `CodecReaderWriterRegistry`
4. Implement `CodecModule`
5. Implement serializers using aspects and context
