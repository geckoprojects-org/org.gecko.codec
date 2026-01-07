# Serialization Architecture

[← Back to Overview](00-overview.md) | [← Configuration Scenarios](11-scenarios.md)

---

## 1. Component Overview

```
┌─────────────────────────────────────────────────────────────────┐
│                      CodecResource                               │
│  (EMF Resource implementation)                                   │
├─────────────────────────────────────────────────────────────────┤
│                                                                  │
│  ┌──────────────────┐    ┌──────────────────┐                   │
│  │ ConfigurationMerger │  │ EffectiveCodecConfig │               │
│  │ (merges all levels) │──▶│ (immutable snapshot) │              │
│  └──────────────────┘    └──────────────────┘                   │
│                                  │                               │
│                                  ▼                               │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │                    Jackson Integration                     │   │
│  │  ┌────────────────┐  ┌────────────────┐                   │   │
│  │  │ CodecJsonFactory │ │ CodecJsonParser │                  │   │
│  │  └────────────────┘  └────────────────┘                   │   │
│  │  ┌────────────────────────────────────────────────────┐   │   │
│  │  │ CodecJsonReadContext / CodecJsonWriteContext        │   │   │
│  │  │ (carries EMF state during parsing/generation)       │   │   │
│  │  └────────────────────────────────────────────────────┘   │   │
│  └──────────────────────────────────────────────────────────┘   │
│                                                                  │
└─────────────────────────────────────────────────────────────────┘
```

---

## 2. Configuration Merging

The `ConfigurationMerger` combines all configuration levels into a single `EffectiveCodecConfig`:

```
Load/Save Options (highest priority)
        ↓
ResourceFactory Defaults
        ↓
Codec Module Config
        ↓
Configuration Properties
        ↓
EAnnotations (MetadataService)
        ↓
Built-in Defaults (lowest priority)
        ↓
    ═══════════════════════════
    │ EffectiveCodecConfig    │
    │ (immutable, lazy-cached) │
    ═══════════════════════════
```

---

## 3. Context Hierarchy

During serialization/deserialization, context objects carry EMF state:

```
EMFContextHolder (internal holder)
    │
    ├── EffectiveCodecConfig (single source of truth)
    │       ├── getClassConfig(EClass) → EffectiveClassConfig
    │       └── getFeatureConfig(EStructuralFeature) → EffectiveFeatureConfig
    │
    ├── Current EObject
    ├── Current EStructuralFeature
    ├── Current Type Hint (for deserialization)
    └── EMF Resource
```

---

## 4. Serialization Flow

1. `CodecResource.doSave()` creates `EffectiveCodecConfig` via `ConfigurationMerger`
2. `CodecJsonFactory` creates generator with `CodecJsonWriteContext`
3. `CodecEObjectSerializer` iterates features:
   - Get `EffectiveFeatureConfig` for each feature
   - Delegate to `SerializationEntry` (Attribute, Reference, etc.)
4. Each entry uses pre-merged config (no fallback logic needed)

---

## 5. Deserialization Flow

1. `CodecResource.doLoad()` creates `EffectiveCodecConfig` via `ConfigurationMerger`
2. `CodecJsonFactory` creates parser with `CodecJsonReadContext`
3. `CodecEObjectDeserializer`:
   - Resolve type (from content or hint)
   - Create EObject
   - Iterate JSON fields, match to `DeserializationEntry`
4. Post-processing: resolve references

---

## 6. Key Classes

| Class | Purpose |
|-------|---------|
| `CodecResource` | EMF Resource implementation |
| `ConfigurationMerger` | Merges configuration levels |
| `EffectiveCodecConfig` | Immutable merged config |
| `EffectiveClassConfig` | Per-EClass effective config |
| `EffectiveFeatureConfig` | Per-feature effective config |
| `CodecJsonFactory` | Creates parser/generator |
| `CodecJsonReadContext` | Read-side EMF context |
| `CodecJsonWriteContext` | Write-side EMF context |
| `EMFContextHolder` | Internal state holder |
| `SerializationEntry` | Per-feature serializer |
| `DeserializationEntry` | Per-feature deserializer |

---

[Next: Implementation →](13-implementation.md)
