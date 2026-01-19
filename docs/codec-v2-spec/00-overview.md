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
| 05 | [Type Serialization](05-type.md) | Type strategies (URI, NAME, MAPPED, etc.) and configuration |
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

**Core Annotations (source: `http://eclipse.org/fennec/codec`):**

| Detail Key | Applies To | Purpose |
|------------|------------|---------|
| `codec.type` | EClass, EReference | Marker for type serialization config |
| `codec.id` | EClass | Marker for ID serialization config |
| `codec.reference` | EReference | Marker for reference serialization config |
| `codec.supertype` | EClass | Marker for supertype serialization config |
| `serialize` | EStructuralFeature | Skip feature during serialization (false = transient) |
| `key` | EStructuralFeature | Custom JSON property name |
| `valueWriterName` | EStructuralFeature | Custom value writer reference |
| `valueReaderName` | EStructuralFeature | Custom value reader reference |

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
