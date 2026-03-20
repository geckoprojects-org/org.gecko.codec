# Trend Analysis Model

This bundle contains an Eclipse Modeling Framework (EMF) model for structuring and representing the results of AI-assisted trend and risk analysis of documents. The model provides a standardized schema for capturing trends identified in policy documents, strategic reports, and other analytical materials.

## Overview

The Trend Analysis Model is designed to support automated analysis workflows where AI systems extract, categorize, and structure information about emerging trends from documents. It uses the STEMPEL taxonomy (Social, Technological, Economic, External Security, Internal Security, Political, Ecological) for classification.

## Model Structure

The model consists of four main classes:

### TrendAnalysis (Root)

The root container that represents a complete trend analysis for a single document. It includes:
- **documentMetadata**: Metadata about the analyzed document
- **executiveSummary**: High-level summary of the analysis
- **trends**: Collection of identified trends

### DocumentMetadata

Captures essential information about the source document:
- **document_id** (required): Unique identifier for the document
- **title**: Document title
- **name**: Document name
- **source**: Origin or source of the document
- **legislative_period**: Associated legislative period (for policy documents)
- **date**: Publication date of the document
- **total_pages**: Number of pages in the document
- **issuing_authority**: Authority or organization that issued the document
- **analysis_date**: Date when the AI analysis was performed

### ExecutiveSummary

Provides a high-level overview of the analysis:
- **context**: Background context for the analysis
- **scope**: Boundaries and focus of the analysis
- **time_horizon**: Temporal scope covered by the trends
- **key_focus**: Main areas of attention in the analysis

### Trend

Represents an individual structural trend identified in the document. Each trend includes:
- **id** (required): Unique identifier for the trend
- **title**: Descriptive title
- **description**: Detailed explanation of the trend
- **time_horizon**: Expected timeframe (e.g., "3-5 years", "5+ years")
- **primary_stempel_category**: Main STEMPEL classification
- **secondary_stempel_categories**: Additional relevant categories (max 3)
- **page_reference**: Page numbers where evidence is found (multiple)
- **evidence**: Supporting evidence from the document (multiple)
- **implications**: Potential impacts and consequences (multiple)
- **related_item_ids**: Links to related trends, risks, or drivers (multiple)

## STEMPEL Taxonomy

The model uses the STEMPEL framework for categorizing trends:

- **SOCIAL**: Demographic changes, cultural shifts, social movements
- **TECHNOLOGICAL**: Innovations, digital transformation, technical developments
- **ECONOMIC_GEO_ECONOMIC**: Economic trends, trade patterns, market dynamics
- **EXTERNAL_SECURITY**: International security, defense, geopolitical threats
- **INTERNAL_SECURITY**: Domestic security, law enforcement, civil protection
- **POLITICAL_GEO_POLITICAL**: Political developments, governance, international relations
- **ECOLOGICAL_PLANETARY**: Environmental issues, climate change, resource sustainability

Each trend must have one primary category and may have up to three secondary categories for cross-cutting issues.

## Technical Details

### EMF Generation

The model is defined in `model/trend-analysis.ecore` and uses EMF code generation to produce:
- Java interfaces and implementation classes
- OSGi bundle configuration
- JSON Schema (`model/trend-analysis-schema.json`)

The code generation is configured in `bnd.bnd`:
```
-generate:\
    model/trend-analysis.genmodel;\
        generate=geckoEMF;\
        genmodel=model/trend-analysis.genmodel;\
        output=src
```

### Package Information

- **Namespace URI**: `http://eclipse.org/fennec/ai/trendanalysis/1.0`
- **Namespace Prefix**: `trendanalysis`
- **Base Package**: `org.eclipse.fennec.ai.nsc.trend.analysis.model`
- **Bundle Version**: 1.0.0.SNAPSHOT

### JSON Schema

The bundle includes a JSON Schema representation (`trend-analysis-schema.json`) that allows for:
- Validation of JSON documents against the model
- Integration with AI systems that work with JSON
- Interoperability with non-EMF-based tools

## Usage in AI Workflows

This model serves as the target schema for AI-driven document analysis workflows:

1. **Document Ingestion**: Source documents (typically PDFs) are processed
2. **AI Analysis**: Large Language Models analyze content to identify trends
3. **Structured Output**: AI generates responses conforming to this model
4. **Validation**: Results are validated against the schema
5. **Storage & Processing**: Data can be serialized as XMI, JSON, or other EMF-supported formats

## Status

This is a **first draft** of the model designed as a proof of concept. It demonstrates the approach for structuring trend analysis results but requires validation and potential modification based on:

- End-user requirements and feedback
- Real-world document analysis results
- Integration experience with AI systems
- Additional metadata needs
- Extended taxonomy requirements

## Future Considerations

Areas for potential enhancement:
- Additional trend attributes (confidence scores, data quality indicators)
- Risk assessment integration
- Support for driver analysis (factors causing trends)
- Versioning and change tracking
- Multi-document analysis aggregation
- Quantitative metrics and indicators
