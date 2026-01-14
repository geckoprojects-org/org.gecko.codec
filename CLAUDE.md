# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

### Build and Testing
- `./gradlew build` - Build all projects
- `./gradlew clean` - Clean build artifacts
- `./gradlew testOSGi` - Run OSGi integration tests for all projects
- `./gradlew codeCoverageReport` - Generate Jacoco coverage report (outputs to `build/reports/jacoco/codeCoverageReport/`)

### Single Test Execution
Individual test projects can be tested using their `.bndrun` files:
- Test bundles are located in `*/test.bndrun` files
- Generated test runners are in `*/generated/test.bndrun`
- Use `./gradlew :project-name:testOSGi` for specific project tests

### Quality Checks
- `./gradlew sonar` - Run SonarCloud analysis (requires SONAR_TOKEN)
- License check: `docker run -it --rm -v $(pwd):/github/workspace ghcr.io/apache/skywalking-eyes/license-eye header check`

### Release Commands
- `./gradlew release` - Create release build (main branch only)
- Release artifacts go to `cnf/release/`

## Project Architecture

### Core Framework Structure
This is an Eclipse EMF codec framework built on Jackson, providing a common abstraction layer for serializing/deserializing EMF EObjects to various formats (JSON, MongoDB, etc.). The architecture follows a modular OSGi design.

#### Key Components
- **org.eclipse.fennec.codec** - Core codec framework and base classes
- **org.eclipse.fennec.codec.json** - JSON implementation using Jackson
- **org.eclipse.fennec.codec.mongo** - MongoDB persistence implementation
- **org.eclipse.fennec.codec.jsonschema** - JSON Schema to/from EMF EPackage conversion
- **org.eclipse.fennec.codec.csv** - CSV format serialization
- **org.eclipse.fennec.codec.info** - Model annotation processing and codec metadata
- **org.eclipse.fennec.codec.constants** - Shared constants and annotations

#### Configuration System
The framework uses three main configurators:
1. **CodecFactoryConfigurator** - Sets up JsonFactory with streaming features
2. **ObjectMapperConfigurator** - Configures Jackson ObjectMapper features
3. **CodecModuleConfigurator** - Manages codec-specific serialization options

#### Serialization Flow
1. `CodecResource` combines all configurators with runtime options
2. `CodecEObjectSerializer`/`CodecEObjectDeserializer` handle EMF object processing
3. Multiple specialized `CodecInfoSerializer` implementations handle different aspects (ID, type, features, references)
4. `CodecGeneratorBaseImpl`/`CodecParserBaseImpl` provide persistence-agnostic base layer

### EMF Model Projects
- **org.eclipse.fennec.codec.demo.model** - Example Person model
- **org.eclipse.fennec.codec.test.models** - Test models with annotations, arrays, generics
- **org.eclipse.fennec.dragino.message.model** - LoRaWAN Dragino device models
- **org.eclipse.fennec.em310udl.mesage.model** - EM310 UDL sensor models
- **org.eclipse.fennec.lorawan.uplink.model** - LoRaWAN uplink message models

### Testing Infrastructure
- **org.eclipse.fennec.codec.test.helper** - Common test utilities and base classes
- Each implementation has corresponding `.test` project with OSGi integration tests
- Tests use `.bndrun` files to define OSGi runtime requirements

## Development Notes

### Build System
- Uses BND/Gradle with OSGi bundle management
- Maven dependencies are managed through `cnf/central.mvn`
- Local artifacts cached in `cnf/local/`
- Bundle versions follow semantic versioning with SNAPSHOT suffixes

### Code Annotations
The framework supports extensive codec annotations for customizing serialization:
- `@CODEC_TRANSIENT` - Skip serialization of features
- `@CODEC_ID_STRATEGY` - Configure ID generation (ID_FIELD, COMBINED)
- `@CODEC_TYPE_INCLUDE`/`@CODEC_TYPE_USE` - Control type information serialization
- See `CodecAnnotations.java` for complete list

### Testing Approach
- Unit tests in `*/test/` directories using JUnit 5
- OSGi integration tests via `.bndrun` runtime definitions
- Test data in `*/test-data/` directories
- Use TestOSGi gradle task for full integration testing

### Jackson 3.x Migration
The codebase has been migrated to Jackson 3.x (tools.jackson.*) - be aware of package name changes from com.fasterxml.jackson.* in older versions.

### Key Options Classes
When working with save/load operations, the main option classes are:
- `org.eclipse.fennec.codec.constants.ObjectMapperOptions` - Jackson ObjectMapper settings
- `org.eclipse.fennec.codec.constants.CodecModuleOptions` - Codec module serialization settings
- `org.eclipse.fennec.codec.constants.CodecModelInfoOptions` - Per-EClass codec settings
- `org.eclipse.fennec.codec.constants.CodecResourceOptions` - Resource-level options (e.g., CODEC_ROOT_OBJECT for deserialization)

Use `CodecOptionsBuilder` to construct options maps:
```java
Map<String, Object> options = CodecOptionsBuilder
    .create()
    .forClass(PersonPackage.eINSTANCE.getPerson())
    .idStrategy("ID_FIELD")
    .build();
```

---

## Codec V2 Development (Active)

We are building a new codec implementation (codec.v2). For full context, see:

- **`docs/codec-v2-development-guide.md`** - Current state, next steps, and session continuity
- **`docs/codec-v2-serialization-spec.md`** - Complete specification (source of truth)
- **`org.eclipse.fennec.model.metadata/model-metadata-architecture.md`** - MetadataService, Aspect pattern
- **`org.eclipse.fennec.codec.metadata/codec-metadata-architecture.md`** - Codec aspects, EAnnotation mapping

### New Projects (codec.v2 related)

| Project | Purpose |
|---------|---------|
| `org.eclipse.fennec.model.metadata` | Generic MetadataService infrastructure |
| `org.eclipse.fennec.codec.metadata` | Codec-specific aspects and annotation parsing |
| `org.eclipse.fennec.codec.v2` | New codec implementation (not started) |

### Key Concepts

1. **MetadataService**: Dynamic component that parses EAnnotations when EPackages are registered and creates pre-computed aspect objects
2. **Aspects**: EMF-based configuration objects attached to ClassMetadata/FeatureMetadata
3. **Configuration Hierarchy**: 6 levels from Load/Save options (highest) to Built-in defaults (lowest)

### Annotation Source

All codec annotations use source: `http://eclipse.org/fennec/codec` with markers:
- `codec.type`, `codec.id`, `codec.reference`, `codec.supertype`

### Current Focus

Building annotation parsing in `org.eclipse.fennec.codec.metadata` that:
1. Reads EAnnotations from EPackage/EClass/EStructuralFeature
2. Applies spec-defined defaults when annotations are missing
3. Creates ClassCodecAspect, FeatureCodecAspect, ReferenceCodecAspect objects
- java comments annotation with @claude or @CLAUDE are instructions for you, similar like a code review comment. please look at them
- dont use full qualified class names in code. we use import instead
- always look in the spec first, if you dont know how to use the codec. the spec should be our source of truth / user manual. if it isnt, we have to make it more clearer