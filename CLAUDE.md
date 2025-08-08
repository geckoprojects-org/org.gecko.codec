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
- **org.eclipse.fennec.codec.jsonschema** - JSON Schema support (current branch focus)
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

### Current Branch: jsonschema
Working on JSON Schema serialization/deserialization functionality. Key files:
- `JsonSchemaToEPackageDeserializer.java:*` - Converts JSON Schema to EMF EPackage
- `EPackageToJsonSchemaSerializer.java:*` - Converts EMF EPackage to JSON Schema  
- `CodecJsonSchemaSerializationTest.java:*` - Test coverage for schema operations

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