# EPackage as Configuration Scope Level - Proposal

[← Back to Annotation Reference](annotation-scope-reference.md)

---

## Status: TODO / Under Discussion

This document captures the proposal to add **EPackage** as a distinct configuration scope level, along with the edge cases and complexities that need resolution.

---

## Motivation

Currently, the configuration scope chain is:

```
EReference/EAttribute → EClass → Global → Default
```

However, EPackage could be a meaningful intermediate level:

```
EReference/EAttribute → EClass → EPackage → Global → Default
```

**Why EPackage makes sense:**

1. **Package-wide defaults** - A model author may want consistent settings for all classes in a package
2. **Cross-package models** - When EClasses reference EClasses from other packages, package-level config provides a natural boundary
3. **Reduces repetition** - Instead of annotating every EClass, annotate the package once

---

## Proposed Scope Chain

```
EReference annotation (most specific)
        ↓
EClass annotation
        ↓
EPackage annotation (of the EClass's package)
        ↓
Global (runtime config)
        ↓
Built-in Default
```

---

## Edge Cases and Complexities

### 1. Cross-Package References

```
PersonPackage
├── Person
│   └── address: Address (from AddressPackage)
│   └── employer: Company (from CompanyPackage)

AddressPackage (typeStrategy=NAME)
├── Address

CompanyPackage (typeStrategy=URI)
├── Company
```

**Question:** When serializing `Person.address`, which package's config applies?

| Option | Description | Pros | Cons |
|--------|-------------|------|------|
| **A: Target class's package** | `Address` uses `AddressPackage` config | Object owns its serialization | Reference context ignored |
| **B: Reference's package** | Uses `PersonPackage` config | Consistent within document | Same class serializes differently |
| **C: Both with priority** | ERef > EClass > Target EPackage > Source EPackage | Most flexible | Complex to understand |

**Recommendation:** Option A - The object's own package defines how it serializes. The reference can override specific presentation aspects (typeKey, refKey).

### 2. Nested Packages

EMF supports nested EPackages:

```
org.example (typeStrategy=URI)
├── person (typeStrategy=NAME)
│   └── Person
├── address
│   └── Address
```

**Question:** Does `Address` inherit from `org.example` or only from `org.example.address`?

| Option | Description |
|--------|-------------|
| **A: Direct parent only** | `Address` uses only `org.example.address` config |
| **B: Ancestor chain** | `Address` inherits: `address` → `org.example` → Global |

**Recommendation:** Start with Option A (simpler). Ancestor chain adds complexity.

### 3. Dynamic Package Registration

In OSGi, EPackages can be registered/unregistered at runtime. If package-level config is cached:

- What happens when a package is re-registered?
- Should config be re-read?

**Recommendation:** Config should be read when MetadataService processes the package. Re-registration triggers re-processing.

### 4. Multiple Packages in Same Resource

A single JSON document might contain objects from multiple packages:

```json
{
  "_type": "http://example.org/person#//Person",
  "name": "John",
  "address": {
    "_type": "http://example.org/address#//Address",
    "city": "Berlin"
  }
}
```

Each object follows its own package's config. This should work naturally with Option A (target class's package).

### 5. Package-Level vs Global Runtime Config

```
AddressPackage (EAnnotation: typeStrategy=NAME)
Runtime Global: typeStrategy=URI
```

**Resolution:** Runtime Global > EPackage (dynamic overrides static)

But what about:
```
Runtime config for AddressPackage specifically?
```

Do we need `codec.ePackageConfig` similar to `codec.eClassConfig`?

```java
Map<EPackage, Map<String, Object>> ePackageConfig = new HashMap<>();
ePackageConfig.put(AddressPackage.eINSTANCE, Map.of("codec.typeStrategy", "NAME"));
options.put("codec.ePackageConfig", ePackageConfig);
```

**Recommendation:** Yes, for consistency. But this is lower priority.

### 6. Scope Properties at Package Level

Which properties make sense at EPackage level?

| Property | EPackage? | Rationale |
|----------|:---------:|-----------|
| `typeStrategy` | ✅ | Package-wide type strategy |
| `typeFormat` | ✅ | Package-wide format |
| `typeKey` | ✅ | Package-wide key |
| `typeScope` | ❓ | Scope within package? Unclear semantics |
| `idStrategy` | ✅ | Package-wide ID strategy |
| `refFormat` | ✅ | Package-wide reference format |

**Open question:** `typeScope` at package level - does it mean "apply typeStrategy only to root objects *from this package*"? This gets confusing with cross-package references.

---

## Implementation Considerations

### MetadataService Changes

Currently `MetadataService` provides:
- `ClassMetadata` for EClass
- `FeatureMetadata` for EStructuralFeature

Need to add:
- `PackageMetadata` for EPackage

### CodecAspectProvider Changes

Need to parse EAnnotations on EPackage and create aspects:
- `PackageCodecAspect` (new)

### Configuration Resolution

The resolver needs to check package level:

```java
public TypeStrategy resolveTypeStrategy(EClass eClass, EStructuralFeature feature) {
    // 1. Check feature-level (if applicable)
    if (feature != null) {
        TypeStrategy featureStrategy = getFeatureConfig(feature).getTypeStrategy();
        if (featureStrategy != null) return featureStrategy;
    }

    // 2. Check class-level
    TypeStrategy classStrategy = getClassConfig(eClass).getTypeStrategy();
    if (classStrategy != null) return classStrategy;

    // 3. Check package-level (NEW)
    TypeStrategy packageStrategy = getPackageConfig(eClass.getEPackage()).getTypeStrategy();
    if (packageStrategy != null) return packageStrategy;

    // 4. Check global runtime config
    TypeStrategy globalStrategy = getGlobalConfig().getTypeStrategy();
    if (globalStrategy != null) return globalStrategy;

    // 5. Built-in default
    return TypeStrategy.URI;
}
```

---

## Decision Log

| Date | Decision |
|------|----------|
| 2026-01-23 | Created proposal document |
| 2026-01-23 | Decided to keep `typeScope`/`typeFormatScope` as runtime-only (🔧) for now |

---

## TODO

- [ ] Decide on cross-package reference resolution (Option A recommended)
- [ ] Decide on nested package inheritance (Option A - direct parent only - recommended)
- [ ] Define which properties are valid at EPackage level
- [ ] Clarify `typeScope` semantics at package level (or exclude it)
- [ ] Design `PackageMetadata` and `PackageCodecAspect`
- [ ] Design `codec.ePackageConfig` for runtime package-level config
- [ ] Update annotation-scope-reference with EPackage column (after decisions)
- [ ] Update EMF configuration model proposal with package-level config
- [ ] Add tests for cross-package scenarios

---

## References

- [Annotation Scope Reference](annotation-scope-reference.md) - Main configuration reference
- [EMF Configuration Model Proposal](emf-configuration-model-proposal.md) - EMF-based configuration

---

## Revision History

| Date | Changes |
|------|---------|
| 2026-01-23 | Initial proposal with edge cases |
