# TypeInfoService Architecture Proposal

## Overview

This document proposes an architectural refactoring to decouple the codec from the modelinfo system by introducing a `TypeInfoService` abstraction. This addresses the tight coupling identified in Issue #48 and provides a more flexible, extensible architecture.

---

## Problem Statement

### Current Architecture

The current system is tightly coupled to `EClassCodecInfo`:

```
CODEC_ROOT_OBJECT → EClass → PackageCodecInfo → EClassCodecInfo → TypeInfo
                                    ↓
                              CodecModule (bound at setup)
                                    ↓
                              Deserializer
```

**Issues:**
1. `EClassCodecInfo` must be known before deserialization starts
2. `PackageCodecInfo` is bound to `CodecModule` at configuration time
3. Type resolution requires knowing the type upfront (chicken-and-egg)
4. Adding new type resolution strategies requires modifying existing code
5. Tight coupling between codec and modelinfo packages

### Limitations

| Scenario | Current Support |
|----------|-----------------|
| CODEC_ROOT_OBJECT provided | ✅ Works |
| TYPE_STRATEGY=URI without ROOT_OBJECT | ❌ Not supported |
| Type by JSON Schema $ref | ❌ Not supported |
| Type by content inspection | ❌ Not supported |
| Custom type resolution | ❌ Not supported |

---

## Proposed Architecture

### Core Concept

Introduce a `TypeInfoService` that provides `TypeInfo` dynamically based on context, rather than requiring `EClassCodecInfo` upfront.

```
Context (options, JSON content, etc.)
          ↓
    TypeInfoService.resolveTypeInfo(context)
          ↓
       TypeInfo
          ↓
    Deserializer (resolves EClass from JSON)
          ↓
    EClassCodecInfo (fetched dynamically for feature deserialization)
```

### Key Interfaces

#### TypeInfoService

```java
/**
 * Service for resolving TypeInfo based on context.
 * Decouples type resolution from static EClassCodecInfo binding.
 */
public interface TypeInfoService {

    /**
     * Resolves the appropriate TypeInfo for the given context.
     *
     * @param context the resolution context containing options, hints, and state
     * @return the resolved TypeInfo, never null
     */
    TypeInfo resolveTypeInfo(TypeResolutionContext context);

    /**
     * Checks if this service can handle the given context.
     *
     * @param context the resolution context
     * @return true if this service can resolve TypeInfo for the context
     */
    boolean canResolve(TypeResolutionContext context);
}
```

#### TypeResolutionContext

```java
/**
 * Context for type resolution, containing all information needed
 * to determine how to resolve the type.
 */
public interface TypeResolutionContext {

    /**
     * @return the load/save options map
     */
    Map<?, ?> getOptions();

    /**
     * @return the ResourceSet for URI resolution
     */
    ResourceSet getResourceSet();

    /**
     * @return true if resolving root object type
     */
    boolean isRootObject();

    /**
     * @return the current EStructuralFeature if resolving nested object, null for root
     */
    EStructuralFeature getCurrentFeature();

    /**
     * @return the parent EClass if resolving nested object, null for root
     */
    EClass getParentEClass();

    /**
     * @return explicitly configured EClass from CODEC_ROOT_OBJECT, may be null
     */
    Optional<EClass> getExplicitRootClass();

    /**
     * @return the CodecModelInfo service for accessing EClassCodecInfo
     */
    CodecModelInfo getCodecModelInfo();
}
```

### Resolution Strategy

The `TypeInfoService` implementation would use a chain of responsibility or strategy pattern:

```java
public class DefaultTypeInfoService implements TypeInfoService {

    private final List<TypeInfoResolver> resolvers;
    private final TypeInfo fallbackTypeInfo;

    @Override
    public TypeInfo resolveTypeInfo(TypeResolutionContext context) {
        for (TypeInfoResolver resolver : resolvers) {
            if (resolver.canResolve(context)) {
                return resolver.resolve(context);
            }
        }
        return fallbackTypeInfo;
    }
}
```

### Built-in Resolvers

#### 1. ExplicitRootObjectResolver

Handles the case when `CODEC_ROOT_OBJECT` is explicitly provided.

```java
public class ExplicitRootObjectResolver implements TypeInfoResolver {

    @Override
    public boolean canResolve(TypeResolutionContext context) {
        return context.getExplicitRootClass().isPresent();
    }

    @Override
    public TypeInfo resolve(TypeResolutionContext context) {
        EClass eClass = context.getExplicitRootClass().get();
        EClassCodecInfo codecInfo = context.getCodecModelInfo()
            .getCodecInfoForEClass(eClass)
            .orElseThrow();
        return codecInfo.getTypeInfo();
    }
}
```

#### 2. URIStrategyResolver

Handles URI-based type resolution when no explicit root is provided.

```java
public class URIStrategyResolver implements TypeInfoResolver {

    private static final TypeInfo URI_TYPE_INFO = createURITypeInfo();

    @Override
    public boolean canResolve(TypeResolutionContext context) {
        // Check if URI strategy is configured in options
        Object strategy = context.getOptions()
            .get(CodecModelInfoOptions.CODEC_TYPE_STRATEGY);
        return "URI".equals(strategy) && context.isRootObject();
    }

    @Override
    public TypeInfo resolve(TypeResolutionContext context) {
        return URI_TYPE_INFO;
    }

    private static TypeInfo createURITypeInfo() {
        // Create TypeInfo with:
        // - typeKey = "_type"
        // - typeValueReaderName = URI_READER
        // - typeValueWriterName = URI_WRITER
        // - ignoreType = false
    }
}
```

#### 3. FeatureTypeResolver

Handles type resolution for nested objects based on their containing feature.

```java
public class FeatureTypeResolver implements TypeInfoResolver {

    @Override
    public boolean canResolve(TypeResolutionContext context) {
        return !context.isRootObject() && context.getCurrentFeature() != null;
    }

    @Override
    public TypeInfo resolve(TypeResolutionContext context) {
        EClass parentEClass = context.getParentEClass();
        EStructuralFeature feature = context.getCurrentFeature();

        EClassCodecInfo parentCodecInfo = context.getCodecModelInfo()
            .getCodecInfoForEClass(parentEClass)
            .orElseThrow();

        FeatureCodecInfo featureCodecInfo = parentCodecInfo
            .getReferenceCodecInfo().stream()
            .filter(f -> f.getFeature().equals(feature))
            .findFirst()
            .orElseThrow();

        return featureCodecInfo.getTypeInfo();
    }
}
```

---

## Integration Points

### CodecModule Changes

```java
public class CodecModule extends SimpleModule {

    // Remove: private PackageCodecInfo codecModelInfo;
    // Keep: private CodecModelInfo codecModelInfoService;

    // Add:
    private TypeInfoService typeInfoService;

    public TypeInfoService getTypeInfoService() {
        return typeInfoService;
    }
}
```

### CodecResource Changes

```java
@Override
protected void doLoad(InputStream inputStream, Map<?, ?> options) throws IOException {

    // No longer require CODEC_ROOT_OBJECT
    EClass eClass = null;
    if (options.containsKey(CodecResourceOptions.CODEC_ROOT_OBJECT)) {
        eClass = (EClass) options.get(CodecResourceOptions.CODEC_ROOT_OBJECT);
    }
    // eClass can be null - TypeInfoService will handle it

    // Build module without requiring PackageCodecInfo
    // TypeInfoService will resolve TypeInfo dynamically
}
```

### CodecEObjectDeserializer Changes

```java
@Override
public EObject deserialize(final JsonParser jp, final DeserializationContext ctxt) {

    EMFCodecReadContext codecReadCtxt = extractCodecContext(jp);

    // Build resolution context
    TypeResolutionContext resolutionContext = new TypeResolutionContextImpl(
        ctxt,
        codecReadCtxt,
        isRootObject(codecReadCtxt),
        getCurrentFeature(codecReadCtxt)
    );

    // Resolve TypeInfo dynamically
    TypeInfo typeInfo = codecModule.getTypeInfoService()
        .resolveTypeInfo(resolutionContext);

    // Determine type from JSON using resolved TypeInfo
    CodecTokenBuffer buffer = determineType(jp, ctxt, typeInfo);

    // Continue with deserialization...
}
```

---

## Benefits

### 1. Decoupling

- Codec package no longer depends on having `EClassCodecInfo` upfront
- Type resolution logic is isolated in dedicated resolvers
- Clear separation of concerns

### 2. Extensibility

Adding new type resolution strategies:

```java
// Example: JSON Schema $ref resolver
public class JsonSchemaRefResolver implements TypeInfoResolver {
    @Override
    public boolean canResolve(TypeResolutionContext context) {
        return context.getOptions().containsKey("jsonSchema");
    }

    @Override
    public TypeInfo resolve(TypeResolutionContext context) {
        // Resolve type from JSON Schema $ref
    }
}

// Register with service
typeInfoService.addResolver(new JsonSchemaRefResolver());
```

### 3. Testability

- Each resolver can be unit tested independently
- Mock `TypeInfoService` for codec tests
- Clear contracts via interfaces

### 4. Open/Closed Principle

- Open for extension: Add new resolvers without modifying existing code
- Closed for modification: Core codec logic remains stable

---

## Migration Path

### Phase 1: Introduce Interfaces

1. Create `TypeInfoService` interface
2. Create `TypeResolutionContext` interface
3. Create `TypeInfoResolver` interface

### Phase 2: Implement Default Service

1. Implement `DefaultTypeInfoService`
2. Implement `ExplicitRootObjectResolver` (current behavior)
3. Implement `FeatureTypeResolver` (current behavior)

### Phase 3: Add URI Strategy Support

1. Implement `URIStrategyResolver`
2. Create default URI `TypeInfo`
3. Ensure `URIReader` has ResourceSet access

### Phase 4: Refactor CodecModule

1. Add `TypeInfoService` to `CodecModule`
2. Make `PackageCodecInfo` optional
3. Update `CodecModule.Builder`

### Phase 5: Refactor Deserializer

1. Use `TypeInfoService` in `CodecEObjectDeserializer`
2. Remove direct `EClassCodecInfo` dependency for type resolution
3. Keep `EClassCodecInfo` usage for feature deserialization (after type is known)

### Phase 6: Update CodecResource

1. Make `CODEC_ROOT_OBJECT` optional
2. Pass options to `TypeResolutionContext`
3. Remove requirement for upfront `PackageCodecInfo`

---

## Open Questions

1. **Where should `TypeInfoService` live?**
   - New package `org.eclipse.fennec.codec.type`?
   - In existing `org.eclipse.fennec.codec.info`?

2. **How to handle ResourceSet access in resolvers?**
   - Pass via `TypeResolutionContext`?
   - Inject into resolvers?

3. **Should resolvers be OSGi services?**
   - Allows dynamic registration
   - But adds complexity for non-OSGi usage

4. **How to configure resolver priority?**
   - Ordered list?
   - Priority annotation/property?

---

## Conclusion

The `TypeInfoService` architecture provides a clean, extensible solution for type resolution that:

- Solves the Issue #48 requirement (URI strategy without ROOT_OBJECT)
- Decouples codec from modelinfo
- Follows SOLID principles
- Enables future extension without modification
- Maintains backward compatibility with existing behavior
