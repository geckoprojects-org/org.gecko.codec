# Codec V2 Serialization Feature Specification

## Overview

This document defines the serialization features for codec.v2, including configurable strategies for type information, identity, references, and supertype serialization.

## Table of Contents

| # | Document | Description |
|---|----------|-------------|
| 00 | [Overview](00-overview.md) | This document - TOC and Serialization Targets |
| 01 | [Serialization Strategies](01-strategies.md) | PLAIN vs STRUCTURED format, strategy classification |
| 02 | [Configuration Hierarchy](02-config-hierarchy.md) | Configuration levels and resolution order |
| 03 | [Global Configuration Options](03-global-options.md) | Smart Compression, Numeric IDs, Field Ordering, Global Ignore |
| 04 | [Type Serialization](04-type.md) | Type strategies (URI, NAME, MAPPED, etc.) and configuration |
| 05 | [SuperType Serialization](05-supertype.md) | SuperType hierarchy serialization |
| 06 | [ID Serialization](06-id.md) | ID strategies and combined ID configuration |
| 07 | [Reference Serialization](07-reference.md) | Non-containment references and cross-document containment |
| 08 | [Feature Serialization](08-feature.md) | EAttribute and EReference feature handling |
| 09 | [Custom Value Readers/Writers](09-custom-values.md) | Custom serialization for specific types |
| 10 | [Polymorphism and Inheritance](10-polymorphism.md) | Type resolution and inheritance handling |
| 11 | [Configuration Scenarios](11-scenarios.md) | Complete configuration examples and test scenarios |
| 12 | [Architecture](12-architecture.md) | Serialization architecture and design |
| 13 | [Implementation](13-implementation.md) | Implementation strategy and checklist |
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
