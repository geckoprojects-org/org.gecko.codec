# Issue #48 Architectural Analysis

## Overview

This document analyzes the architectural challenges and improvements needed to implement **TYPE_STRATEGY=URI without ROOT_OBJECT** (GitHub Issue #48).

**Goal:** Allow deserialization of EMF objects when `_type` contains a full EClass URI, without requiring explicit `CODEC_ROOT_OBJECT` configuration.

---

## Architectural Issues & Difficulties

### 1. Chicken-and-Egg Problem with PackageCodecInfo

**Location:** `CodecResource.doLoad()` → `CodecModule.Builder.bindCodecModelInfo()`

**Problem:** The `CodecModule` is built and configured with a `PackageCodecInfo` **before** deserialization starts. But for dynamic type resolution, we don't know which package until we read the `_type` field.

```
Current: doLoad() → get PackageCodecInfo → build CodecModule → deserialize
Needed:  doLoad() → build CodecModule (no PackageCodecInfo) → deserialize → resolve type → get PackageCodecInfo dynamically
```

**Impact:** This is the **core architectural challenge**. The `CodecModule` stores `PackageCodecInfo` as a field and `getCodecModelInfo()` is called during deserialization.

**Suggestion:** Either:
- Allow `CodecModule` to work without `PackageCodecInfo` initially and resolve it lazily
- Or pass `CodecModelInfoService` and resolve per-object during deserialization (already available)

---

### 2. URIReader Depends on OSGi ComponentServiceObjects

**Location:** `URIReader.java:33-38`

**Problem:** `URIReader` requires `ComponentServiceObjects<ResourceSet>` which is OSGi-specific. The commented-out constructor (lines 40-42) suggests this was recognized.

```java
private ComponentServiceObjects<ResourceSet> rsFactory;

public URIReader(ComponentServiceObjects<ResourceSet> rsFactory) {
    this.rsFactory = rsFactory;
}

// Commented out alternative:
// public URIReader(ResourceSet resourceSet) {
//     this.resourceSet = resourceSet;
// }
```

**Impact:** For non-OSGi usage and for the deserializer context, we need an alternative way to provide ResourceSet access.

**Suggestion:**
- Add alternative constructor accepting `Supplier<ResourceSet>` or direct `ResourceSet`
- Or retrieve ResourceSet from `DeserializationContext` attributes (already done for Resource in `EMFCodecReadContext`)

---

### 3. Instance Variable Side Effect in Deserializer

**Location:** `CodecEObjectDeserializer.java:62`

```java
private EClass type = null;
```

**Problem:** The `determineType()` method sets the instance variable `type` as a side effect while also returning a buffer. This pattern is:
- Not thread-safe
- Hard to reason about
- Mixes return value with side effect

**Impact:** Could cause issues if deserializer is reused or in concurrent scenarios.

**Suggestion:** Return a record/tuple containing both the buffer and resolved type:
```java
record TypeResolutionResult(CodecTokenBuffer buffer, EClass resolvedType) {}
```

---

### 4. Debug System.out.println Statements

**Locations:**
- `CodecEObjectDeserializer.java:135, 140, 347`
- `URIReader.java:63`

**Problem:** Debug print statements in production code.

**Suggestion:** Replace with proper `Logger` calls or remove.

---

### 5. Empty CodecInfo Interface

**Location:** `CodecInfo.java` (new file in `org.eclipse.fennec.codec.info.model`)

**Problem:** The new `CodecInfo` interface is a marker interface with no methods:

```java
public interface CodecInfo {
} // CodecInfo
```

While marker interfaces have uses, it's unclear what behavior this enables.

**Question:** Should `CodecInfo` define common methods like `getId()` or configuration accessors that all codec info types share?

---

### 6. Inconsistent Error Handling

**Locations:** Throughout `CodecResource.java` and `CodecEObjectDeserializer.java`

**Problem:** Mixed strategies:
- Some places throw `IllegalArgumentException`
- Some log warnings and continue
- Some log severe and return null

**Examples:**
| Location | Behavior |
|----------|----------|
| `doLoad()` line 231 | Missing `ROOT_OBJECT` throws exception |
| `determineType()` | Failure to resolve type logs warning, continues |
| `extractModelInfo()` | Returns null if not found |

**Suggestion:** Define a clear error handling strategy:
- What should be fatal (throw)?
- What should be recoverable (log + fallback)?

---

### 7. CodecModule Requires Both PackageCodecInfo AND CodecModelInfoService

**Location:** `CodecModule.java:81-82`

```java
private PackageCodecInfo codecModelInfo;
private CodecModelInfo codecModelInfoService;
```

**Problem:** Redundancy - `CodecModelInfoService` can provide `PackageCodecInfo` for any package. Having both creates confusion about which to use.

**Impact:** In `CodecEObjectDeserializer.extractModelInfo()` (lines 100-116), it first tries `codecModule.getCodecModelInfo()` then falls back to `codecModelInfoService`. This is the right pattern but shows the redundancy.

```java
private EClassCodecInfo extractModelInfo(EClass type) {
    PackageCodecInfo codecModelInfo = codecModule.getCodecModelInfo();
    EClassCodecInfo eObjCodecInfo = null;
    if(type != null) {
        for(EClassCodecInfo eci : codecModelInfo.getEClassCodecInfo()) {
            if(eci.getClassifier().equals(type)) {
                eObjCodecInfo = eci;
                break;
            }
        }
    }
    // Fallback to service
    if(eObjCodecInfo == null) {
        eObjCodecInfo = codecModelInfoService.getCodecInfoForEClass(type).orElse(null);
    }
    return eObjCodecInfo;
}
```

**Suggestion:** For dynamic type resolution, rely on `CodecModelInfoService` only, making `PackageCodecInfo` optional in `CodecModule`.

---

### 8. Type Key is Per-EClass but Needed Before EClass is Known

**Location:** `CodecEObjectDeserializer.determineType()` requires `TypeInfo` which comes from `EClassCodecInfo`

**Problem:** To determine the type, we need to know which `_type` key to look for. But different EClasses could theoretically have different type keys. For root object without `ROOT_OBJECT`, we don't know which TypeInfo to use.

**Current flow:**
```
ROOT_OBJECT → EClassCodecInfo → TypeInfo → typeKey → read JSON
```

**Needed flow:**
```
Default TypeInfo → typeKey (_type) → read JSON → resolve URI → EClassCodecInfo
```

**Suggestion:** Define a **default/global TypeInfo** for root object type resolution:
- Default type key: `_type`
- Default reader: `URI_READER`
- This allows type discovery before EClass-specific configuration is applied

---

## Summary Table

| Issue | Severity | Blocks Issue #48? | Suggested Action |
|-------|----------|-------------------|------------------|
| PackageCodecInfo chicken-egg | High | **Yes** | Make optional in CodecModule |
| URIReader OSGi dependency | Medium | **Yes** | Add ResourceSet from context |
| Instance variable side effect | Low | No | Refactor to return type |
| Debug print statements | Low | No | Remove/replace with Logger |
| Empty CodecInfo interface | Low | No | Clarify purpose |
| Inconsistent error handling | Medium | No | Define strategy |
| Redundant PackageCodecInfo | Medium | Partial | Rely on Service |
| Type key needed before type known | High | **Yes** | Default TypeInfo |

---

## Blocking Issues for Issue #48

The following must be addressed to implement TYPE_STRATEGY=URI without ROOT_OBJECT:

1. **PackageCodecInfo chicken-egg** - CodecModule must work without pre-bound PackageCodecInfo
2. **URIReader OSGi dependency** - Need ResourceSet access in deserialization context
3. **Default TypeInfo** - Need fallback type resolution configuration for unknown root objects

---

## Next Steps

1. Design default TypeInfo mechanism
2. Ensure ResourceSet is accessible in URIReader during deserialization
3. Make PackageCodecInfo optional in CodecModule
4. Implement fallback logic in CodecEObjectDeserializer
5. Add tests for TYPE_STRATEGY=URI without ROOT_OBJECT
