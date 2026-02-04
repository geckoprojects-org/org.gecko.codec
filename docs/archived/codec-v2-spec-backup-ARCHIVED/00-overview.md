# Codec V2 Serialization Feature Specification

## Overview

This document defines the serialization features for codec.v2, including configurable strategies for type information, identity, references, and supertype serialization.

## Table of Contents

| # | Document | Description |
|---|----------|-------------|
| 00 | [Overview](00-overview.md) | This document - TOC and Serialization Targets |
| 01 | [Serialization Strategies](01-strategies.md) | PLAIN vs STRUCTURED format, strategy classification |
| 02 | [Key Configuration](02-key-configuration.md) | Key naming conventions for PLAIN/STRUCTURED formats |
| 03 | [Configuration Hierarchy](03-config-hierarchy.md) | Configuration levels and resolution order |
| 04 | [Global Configuration Options](04-global-options.md) | Smart Compression, Numeric IDs, Field Ordering, Global Ignore |
| 05 | [Type Serialization](05-type.md) | Type strategies (URI, NAME, CLASS, etc.) and configuration |
| 06 | [SuperType Serialization](06-supertype.md) | SuperType hierarchy serialization |
| 07 | [ID Serialization](07-id.md) | ID strategies and combined ID configuration |
| 08 | [Reference Serialization](08-reference.md) | Non-containment references and cross-document containment |
| 09 | [Feature Serialization](09-feature.md) | EAttribute and EReference feature handling |
| 10 | [Custom Value Readers/Writers](10-custom-values.md) | Custom serialization for specific types |
| 11 | [Polymorphism and Inheritance](11-polymorphism.md) | Type resolution and inheritance handling |
| 12 | [Configuration Scenarios](12-scenarios.md) | Complete configuration examples and test scenarios |
| 13 | [Architecture](13-architecture.md) | Serialization architecture and design |
| 14 | [Implementation](14-implementation.md) | Implementation strategy and checklist |
| 15 | [Test Coverage](15-test-coverage.md) | Test coverage matrix and spec-to-test mapping |
| 16 | [Format Abstraction](16-format-abstraction.md) | Multi-format support (JSON, BSON, CSV) and custom parsers/generators |
| 17 | [Root Element](17-root-element.md) | Root element handling, JSON arrays, CODEC_ROOT_OBJECT option |
| 18 | [Feature Type Hints and Value Readers](18-feature-type-hints.md) | CODEC_FEATURE_TYPE_HINTS, CODEC_FEATURE_VALUE_READERS/WRITERS |
| 19 | [Annotation & Configuration Reference](19-annotation-reference.md) | **Definitive reference** for all configuration options, scope matrix, implementation status |
| 99 | [Open Questions](99-open-questions.md) | Open questions and issues to resolve |

---

## 1. Serialization Targets

The codec serializes the following metadata alongside EObject features:

| Target | Purpose | Applies To |
|--------|---------|------------|
| **Type** | Identifies the EClass of an object | All EObjects |
| **ID** | Unique identifier for an object | EObjects with identity |
| **Reference** | Points to non-contained objects | EReference (non-containment) |
| **Cross-Doc Containment** | Points to contained objects in other documents | EReference (containment, cross-document) |
| **SuperType** | Lists supertypes for querying | EObjects (optional) |

### 1.1 EAnnotation and Configuration Parity

Every codec feature that can be configured declaratively via **EAnnotations** on the EMF model should also be configurable programmatically via the **Config Builder** at runtime, and vice versa.

**Configuration Levels (Resolution Order, dynamic before static):**

| Priority | Level | Scope | Description |
|----------|-------|-------|-------------|
| 1 (highest) | **Load/Save options** | Per-operation | Options passed to `resource.save(options)` / `resource.load(options)` |
| 2 | **ResourceFactory defaults** | Per-factory | `defaultSaveOptions`/`defaultLoadOptions` on EMF ResourceFactory |
| 3 | **Codec module config** | Per-codec | Jackson module / codec instance configuration |
| 4 | **Configuration properties** | External | System properties, config files |
| 5 | **EAnnotations** | Per-model | Declared in .ecore model (static) |
| 6 (lowest) | **Built-in defaults** | Global | Hardcoded codec defaults |

**Key Principle:** Every configuration setting (EAnnotation detail keys, Jackson module features) must have a corresponding runtime option key. This allows any setting to be overridden at load/save time without modifying the model or codec configuration.

**Complete Annotation Key Registry (source: `http://eclipse.org/fennec/codec`):**

#### Type Configuration

| Detail Key | Applies To | Type | Default |
|------------|------------|------|---------|
| `codec.type.strategy` | EClass, EReference | TypeStrategy | `URI` |
| `codec.type.format` | EClass, EReference | SerializationFormat | `PLAIN` |
| `codec.type.key` | EClass, EReference | String | `_type` |
| `codec.type.nameKey` | EClass, EReference | String | `type` |
| `codec.type.schemaKey` | EClass, EReference | String | `schema` |
| `codec.type.scope` | **Global only** | StrategyScope | `ALL` |
| `codec.type.formatScope` | **Global only** | StrategyScope | `ALL` |

**TypeStrategy values:** `URI`, `NAME`, `SCHEMA_AND_TYPE`, `NUMERIC`, `NONE`

#### SuperType Configuration (Extension of Type)

| Detail Key | Applies To | Type | Default |
|------------|------------|------|---------|
| `codec.supertype.enabled` | EClass | boolean | `false` |
| `codec.supertype.key` | EClass | String | `_supertype` (PLAIN) / `supertype` (STRUCTURED) |
| `codec.supertype.selection` | EClass | SuperTypeSelection | `SINGLE` |
| `codec.supertype.presentation` | EClass | SuperTypePresentation | `ARRAY` |
| `codec.supertype.separator` | EClass | String | `,` |

**SuperTypeSelection values:** `SINGLE`, `ALL`, `ALL_EMF`, `NONE`

#### ID Configuration

| Detail Key | Applies To | Type | Default |
|------------|------------|------|---------|
| `codec.id.enabled` | EClass | boolean | `true` |
| `codec.id.strategy` | EClass | IdStrategy | `ID_FIELD` |
| `codec.id.format` | EClass | SerializationFormat | `PLAIN` |
| `codec.id.key` | EClass | String | `_id` |
| `codec.id.features` | EClass | String (comma-separated) | (eID attributes) |
| `codec.id.separator` | EClass | String | `-` |
| `codec.id.keyMode` | EClass | IdKeyMode | `ID_ONLY` |
| `codec.id.onTop` | EClass | boolean | `true` |
| `codec.id.scope` | **Global only** | StrategyScope | `ALL` |
| `codec.id.formatScope` | **Global only** | StrategyScope | `ALL` |

**IdStrategy values:** `ID_FIELD`, `COMBINED`
**IdKeyMode values:** `ID_ONLY`, `BOTH`, `FEATURE_ONLY`

#### Reference Configuration

| Detail Key | Applies To | Type | Default |
|------------|------------|------|---------|
| `codec.reference.format` | EReference | SerializationFormat | `PLAIN` |
| `codec.reference.expand` | EReference | boolean | `false` |
| `codec.reference.refKey` | EReference | String | `_ref` |

#### Feature Configuration

| Detail Key | Applies To | Type | Default |
|------------|------------|------|---------|
| `serialize` | EStructuralFeature | boolean | `true` |
| `key` | EStructuralFeature | String | (feature name) |
| `serializeNull` | EStructuralFeature | boolean | `false` |
| `serializeDefaults` | EStructuralFeature | boolean | `false` |
| `serializeEmpty` | EStructuralFeature | boolean | `false` |
| `valueWriterName` | EStructuralFeature | String | (none) |
| `valueReaderName` | EStructuralFeature | String | (none) |

#### Global Scope Values

**StrategyScope values:** `ALL`, `ROOT_ONLY`, `ROOT_CONTAINMENT`, `ROOT_NON_CONTAINMENT`

**SerializationFormat values:** `PLAIN`, `STRUCTURED`

**Design Principle:** When documenting a configuration option, always show both:
- The **EAnnotation** approach (for model designers)
- The **Config Builder** approach (for runtime customization)

This ensures flexibility: models can define sensible defaults that integrators can override at runtime without modifying the model.

---

## 2. Error and Warning Handling

The codec uses EMF's standard diagnostic mechanism for reporting errors and warnings during serialization and deserialization.

**Principle:**
- All errors and warnings are collected in the EMF Resource's diagnostics (`resource.getErrors()`, `resource.getWarnings()`)
- Errors cause the load/save operation to fail after all diagnostics are collected
- Warnings do not cause failure but are reported for user awareness
- All diagnostics are also logged via the standard logging mechanism

**Error Severity:**

| Severity | Behavior | Examples |
|----------|----------|----------|
| **ERROR** | Operation fails, diagnostic added | Cannot instantiate abstract type, unresolved type URI, missing required type info |
| **WARNING** | Operation continues, diagnostic added | Type collision (content type differs from hint), deprecated option usage |

**Diagnostic Information:**

Each diagnostic includes:
- Message describing the issue
- Location (resource URI, line/column if available)
- Source (codec component that raised the issue)

**Example - Error during load:**
```java
resource.load(inputStream, options);
if (!resource.getErrors().isEmpty()) {
    for (Diagnostic error : resource.getErrors()) {
        System.err.println("Error: " + error.getMessage());
    }
    // Operation failed - handle appropriately
}
```

**Implementation Details:**

The codec uses a `DiagnosticCollector` internally to aggregate errors and warnings during serialization/deserialization:

1. **Initialization:** A `DiagnosticCollector` is created at the start of each load/save operation
2. **Propagation:** The collector is passed through Jackson's context attributes and accessible via `ContextHelper`
3. **Collection:** Each deserialization/serialization entry adds diagnostics via `ContextHelper.addError()`/`addWarning()`
4. **Finalization:** After the operation completes, `collector.addToResource(resource)` transfers all diagnostics to the EMF Resource

**Diagnostic Sources:**

| Component | Error Examples | Warning Examples |
|-----------|----------------|------------------|
| `CodecEObjectDeserializer` | No type info and no hint, failed EObject creation | Unexpected token |
| `TypeDeserializationEntry` | - | Could not resolve EClass, unexpected token |
| `IdDeserializationEntry` | EObject not yet created | STRUCTURED ID format mismatch, parse errors |
| `ReferenceDeserializationEntry` | EObject not yet created, no deserializer found, deserialization exception | Unexpected token |
| `AttributeDeserializationEntry` | EObject not yet created | Value conversion failure, unexpected token |
| `CodecResource` | Proxy creation failure | Unexpected root token |

**Null-Safety:**

All diagnostic methods are null-safe. When context is null (e.g., in unit tests), diagnostics are silently skipped:
```java
// Safe to call even if ctxt is null
ContextHelper.addWarning(ctxt, "message", parser, "Source");
```

### 2.1 Complete Error Scenarios

The following table lists all error and warning scenarios:

#### Type Resolution Errors

| Scenario | Severity | Message Template | Recovery |
|----------|----------|------------------|----------|
| No type info and no hint | ERROR | `Cannot deserialize: no type information found and no CODEC_ROOT_OBJECT hint` | Operation fails |
| Unknown type value (with hint) | WARNING | `Could not resolve EClass from type value: {value}` | Falls back to hint |
| Unknown type value (no hint) | ERROR | `Cannot deserialize: no type information found and no CODEC_ROOT_OBJECT hint` | Operation fails |
| Abstract type without concrete hint | ERROR | `Cannot instantiate abstract type: {type}` | Operation fails |
| Type collision (incompatible) | WARNING | `Type collision: CODEC_ROOT_OBJECT={hint} but content type={actual}` | Content type used |
| Unknown classifier ID (with hint) | WARNING | `Could not resolve EClass from type value: {id}` | Falls back to hint |
| Unknown classifier ID (no hint) | ERROR | `Cannot deserialize: no type information found and no CODEC_ROOT_OBJECT hint` | Operation fails |

#### ID Errors

| Scenario | Severity | Message Template | Recovery |
|----------|----------|------------------|----------|
| Missing ID attribute | WARNING | `No ID attribute found for EClass {class}` | ID not set |
| Invalid separator in combined ID | WARNING | `Cannot split ID value '{value}' with separator '{sep}'` | Partial ID set |
| STRUCTURED format mismatch | WARNING | `Expected STRUCTURED ID format but found {actual}` | Best-effort parse |
| ID feature not found | WARNING | `ID feature '{feature}' not found in EClass {class}` | Feature skipped |

#### Reference Errors

| Scenario | Severity | Message Template | Recovery |
|----------|----------|------------------|----------|
| Circular reference in expand | WARNING | `Circular reference detected during expand: {path}` | Reference skipped |
| Unresolved proxy URI | WARNING | `Cannot resolve proxy URI: {uri}` | Proxy created |
| Invalid reference format | WARNING | `Expected {expected} reference format but found {actual}` | Best-effort parse |
| Missing ref key | WARNING | `Reference object missing '_ref' key` | Reference skipped |

#### Feature Errors

| Scenario | Severity | Message Template | Recovery |
|----------|----------|------------------|----------|
| Unknown feature in JSON | WARNING | `Unknown feature '{name}' for EClass {class}` | Field skipped |
| Value conversion failure | WARNING | `Cannot convert value '{value}' to type {type}` | Default used |
| Required feature missing | WARNING | `Required feature '{name}' not found in JSON` | Default used |
| Type mismatch | WARNING | `Expected {expected} but found {actual} for feature '{name}'` | Best-effort conversion |

#### Configuration Errors

| Scenario | Severity | Message Template | Recovery |
|----------|----------|------------------|----------|
| Invalid discriminator path | ERROR | `Invalid discriminator path: {path}` | Operation fails |
| Unknown discriminator value | WARNING | `Unknown discriminator value '{value}' for map '{mapId}'` | Falls back to type strategy |
| Invalid scope value | ERROR | `Invalid StrategyScope value: {value}` | Operation fails |

### 2.2 Diagnostic API

#### Accessing Diagnostics

```java
// Load with potential errors
resource.load(inputStream, options);

// Check for errors (operation may have partially succeeded)
for (Diagnostic error : resource.getErrors()) {
    System.err.println("ERROR: " + error.getMessage());

    // Access location if available
    if (error instanceof ResourceDiagnostic rd) {
        System.err.println("  Location: " + rd.getLocation());
        System.err.println("  Line: " + rd.getLine() + ", Column: " + rd.getColumn());
    }
}

// Check for warnings
for (Diagnostic warning : resource.getWarnings()) {
    System.out.println("WARNING: " + warning.getMessage());
}
```

#### Programmatic Error Type Detection

Diagnostics include a source identifier for programmatic handling:

```java
for (Diagnostic error : resource.getErrors()) {
    String source = error.getSource();

    switch (source) {
        case "TypeDeserializationEntry" -> handleTypeError(error);
        case "IdDeserializationEntry" -> handleIdError(error);
        case "ReferenceDeserializationEntry" -> handleRefError(error);
        case "AttributeDeserializationEntry" -> handleAttrError(error);
        default -> handleGenericError(error);
    }
}
```

#### Suppressing Warnings

Warnings can be suppressed via load/save options:

```java
Map<String, Object> options = new HashMap<>();

// Suppress all warnings
options.put(CodecResource.SUPPRESS_WARNINGS, true);

// Suppress specific warning types
options.put(CodecResource.SUPPRESS_WARNING_SOURCES,
    Set.of("TypeDeserializationEntry", "AttributeDeserializationEntry"));

resource.load(inputStream, options);
// resource.getWarnings() will be empty or filtered
```

#### Fail-Fast Mode

By default, the codec collects all diagnostics before reporting. For fail-fast behavior:

```java
Map<String, Object> options = new HashMap<>();
options.put(CodecResource.FAIL_FAST, true);

try {
    resource.load(inputStream, options);
} catch (CodecException e) {
    // First error encountered
    System.err.println("Failed at: " + e.getMessage());
}
```

#### Custom Diagnostic Handler

For advanced use cases, provide a custom diagnostic handler:

```java
DiagnosticHandler handler = new DiagnosticHandler() {
    @Override
    public void handleError(String message, String source, Object location) {
        // Custom error handling (logging, metrics, etc.)
        logger.error("[{}] {}", source, message);
    }

    @Override
    public void handleWarning(String message, String source, Object location) {
        // Custom warning handling
        logger.warn("[{}] {}", source, message);
    }
};

Map<String, Object> options = new HashMap<>();
options.put(CodecResource.DIAGNOSTIC_HANDLER, handler);
resource.load(inputStream, options);
```
