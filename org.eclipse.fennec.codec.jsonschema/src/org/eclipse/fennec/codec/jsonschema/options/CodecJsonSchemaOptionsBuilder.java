/**
 * Copyright (c) 2012 - 2025 Data In Motion and others.
 * All rights reserved.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Data In Motion - initial API and implementation
 */
package org.eclipse.fennec.codec.jsonschema.options;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.fennec.codec.options.CodecOptionsBuilder;

/**
 * Fluent options builder for JSON Schema serialization of EMF EClass instances.
 *
 * <p>Extends {@link CodecOptionsBuilder} with typed methods for JSON Schema
 * specific options, so that callers do not have to work with raw string keys.
 *
 * <p>All resource-level methods inherited from {@link CodecOptionsBuilder}
 * (e.g. {@code rootObject}, {@code serializeType}, {@code useNamesFromExtendedMetadata})
 * are overridden with covariant return types so the fluent chain returns
 * {@code CodecJsonSchemaOptionsBuilder} at every step.
 *
 * <p>Note: the {@code @SafeVarargs final} methods on the parent
 * ({@code serializationFeaturesWith}, {@code mapperFeaturesWith}, etc.)
 * cannot be overridden due to Java language constraints. If you need to call
 * those methods you can either call {@code forClass()} first and then use
 * {@code and()}, or cast the result yourself.
 *
 * <p>Usage example:
 * <pre>
 * Map&lt;String, Object&gt; options = CodecJsonSchemaOptionsBuilder.create()
 *     .rootObject(EcorePackage.Literals.ECLASS)
 *     .useNamesFromExtendedMetadata(true)
 *     .serializeType(false)
 *     .forClass(EcorePackage.Literals.ECLASS)
 *         .schemaFeatureKey("$defs")
 *         .allFieldsRequired(true)
 *     .build();
 *
 * resource.save(options);
 * </pre>
 *
 * @author Data In Motion
 * @since Dec 2025
 */
public class CodecJsonSchemaOptionsBuilder extends CodecOptionsBuilder {

	protected CodecJsonSchemaOptionsBuilder() {
	}

	/**
	 * Creates a new JSON Schema options builder.
	 *
	 * @return a new {@code CodecJsonSchemaOptionsBuilder} instance
	 */
	public static CodecJsonSchemaOptionsBuilder create() {
		return new CodecJsonSchemaOptionsBuilder();
	}

	// =========================================================================
	// Covariant overrides of all non-final parent methods
	// =========================================================================

	@Override
	public CodecJsonSchemaOptionsBuilder rootObject(EClass rootObject) {
		return (CodecJsonSchemaOptionsBuilder) super.rootObject(rootObject);
	}

	@Override
	public CodecJsonSchemaOptionsBuilder dateFormat(DateFormat dateFormat) {
		return (CodecJsonSchemaOptionsBuilder) super.dateFormat(dateFormat);
	}

	@Override
	public CodecJsonSchemaOptionsBuilder locale(Locale locale) {
		return (CodecJsonSchemaOptionsBuilder) super.locale(locale);
	}

	@Override
	public CodecJsonSchemaOptionsBuilder timeZone(TimeZone timeZone) {
		return (CodecJsonSchemaOptionsBuilder) super.timeZone(timeZone);
	}

	@Override
	public CodecJsonSchemaOptionsBuilder serializeDefaultValue(boolean serialize) {
		return (CodecJsonSchemaOptionsBuilder) super.serializeDefaultValue(serialize);
	}

	@Override
	public CodecJsonSchemaOptionsBuilder serializeEmptyValue(boolean serialize) {
		return (CodecJsonSchemaOptionsBuilder) super.serializeEmptyValue(serialize);
	}

	@Override
	public CodecJsonSchemaOptionsBuilder serializeNullValue(boolean serialize) {
		return (CodecJsonSchemaOptionsBuilder) super.serializeNullValue(serialize);
	}

	@Override
	public CodecJsonSchemaOptionsBuilder useNamesFromExtendedMetadata(boolean useNames) {
		return (CodecJsonSchemaOptionsBuilder) super.useNamesFromExtendedMetadata(useNames);
	}

	@Override
	public CodecJsonSchemaOptionsBuilder useId(boolean useId) {
		return (CodecJsonSchemaOptionsBuilder) super.useId(useId);
	}

	@Override
	public CodecJsonSchemaOptionsBuilder idOnTop(boolean onTop) {
		return (CodecJsonSchemaOptionsBuilder) super.idOnTop(onTop);
	}

	@Override
	public CodecJsonSchemaOptionsBuilder serializeIdField(boolean serialize) {
		return (CodecJsonSchemaOptionsBuilder) super.serializeIdField(serialize);
	}

	@Override
	public CodecJsonSchemaOptionsBuilder idFeatureAsPrimaryKey(boolean usePrimaryKey) {
		return (CodecJsonSchemaOptionsBuilder) super.idFeatureAsPrimaryKey(usePrimaryKey);
	}

	@Override
	public CodecJsonSchemaOptionsBuilder deserializeType(boolean deserialize) {
		return (CodecJsonSchemaOptionsBuilder) super.deserializeType(deserialize);
	}

	@Override
	public CodecJsonSchemaOptionsBuilder serializeType(boolean serialize) {
		return (CodecJsonSchemaOptionsBuilder) super.serializeType(serialize);
	}

	@Override
	public CodecJsonSchemaOptionsBuilder serializeSuperTypes(boolean serialize) {
		return (CodecJsonSchemaOptionsBuilder) super.serializeSuperTypes(serialize);
	}

	@Override
	public CodecJsonSchemaOptionsBuilder serializeAllSuperTypes(boolean serialize) {
		return (CodecJsonSchemaOptionsBuilder) super.serializeAllSuperTypes(serialize);
	}

	@Override
	public CodecJsonSchemaOptionsBuilder serializeSuperTypesAsArray(boolean serialize) {
		return (CodecJsonSchemaOptionsBuilder) super.serializeSuperTypesAsArray(serialize);
	}

	@Override
	public CodecJsonSchemaOptionsBuilder referenceKey(String key) {
		return (CodecJsonSchemaOptionsBuilder) super.referenceKey(key);
	}

	@Override
	public CodecJsonSchemaOptionsBuilder proxyKey(String key) {
		return (CodecJsonSchemaOptionsBuilder) super.proxyKey(key);
	}

	@Override
	public CodecJsonSchemaOptionsBuilder timestampKey(String key) {
		return (CodecJsonSchemaOptionsBuilder) super.timestampKey(key);
	}

	@Override
	public CodecJsonSchemaOptionsBuilder supertypeKey(String key) {
		return (CodecJsonSchemaOptionsBuilder) super.supertypeKey(key);
	}

	@Override
	public CodecJsonSchemaOptionsBuilder writeEnumLiteral(boolean write) {
		return (CodecJsonSchemaOptionsBuilder) super.writeEnumLiteral(write);
	}

	@Override
	public CodecJsonSchemaOptionsBuilder referenceDeserializer(Object deserializer) {
		return (CodecJsonSchemaOptionsBuilder) super.referenceDeserializer(deserializer);
	}

	@Override
	public CodecJsonSchemaOptionsBuilder proxyFactory(Object factory) {
		return (CodecJsonSchemaOptionsBuilder) super.proxyFactory(factory);
	}

	// =========================================================================
	// JSON Schema multi-EClass wrapper options
	// =========================================================================

	/**
	 * Sets the name of the root array property in the generated wrapper schema.
	 * When set, a wrapper object schema is generated with this property as a
	 * {@code "type": "array"} whose items use a {@code oneOf} pointing to
	 * the EClasses supplied via {@link #oneOfEClasses}.
	 *
	 * @param name the property name (e.g. {@code "results"})
	 * @return this builder
	 */
	public CodecJsonSchemaOptionsBuilder rootArrayName(String name) {
		options.put(JsonSchemaOptions.JSONSCHEMA_ROOT_ARRAY_NAME, name);
		return this;
	}

	/**
	 * Sets the list of EClasses to include as {@code $ref} variants inside the
	 * {@code oneOf} of the root array's items. Each EClass and all its transitive
	 * dependencies are placed in the {@code definitions} section.
	 *
	 * @param eClasses the EClasses to include
	 * @return this builder
	 */
	public CodecJsonSchemaOptionsBuilder oneOfEClasses(EClass... eClasses) {
		options.put(JsonSchemaOptions.JSONSCHEMA_ONE_OF_ECLASSES, Arrays.asList(eClasses));
		return this;
	}

	/**
	 * Sets the list of EClasses to include as {@code $ref} variants inside the
	 * {@code oneOf} of the root array's items.
	 *
	 * @param eClasses the EClasses to include
	 * @return this builder
	 */
	public CodecJsonSchemaOptionsBuilder oneOfEClasses(List<EClass> eClasses) {
		options.put(JsonSchemaOptions.JSONSCHEMA_ONE_OF_ECLASSES, eClasses);
		return this;
	}

	/**
	 * Sets the {@code title} field of the generated wrapper schema document.
	 *
	 * @param title the schema title
	 * @return this builder
	 */
	public CodecJsonSchemaOptionsBuilder schemaTitle(String title) {
		options.put(JsonSchemaOptions.JSONSCHEMA_SCHEMA_TITLE, title);
		return this;
	}

	/**
	 * Sets the {@code $id} field of the generated wrapper schema document.
	 *
	 * @param id the schema $id URI
	 * @return this builder
	 */
	public CodecJsonSchemaOptionsBuilder schemaId(String id) {
		options.put(JsonSchemaOptions.JSONSCHEMA_SCHEMA_ID, id);
		return this;
	}

	/**
	 * Sets the {@code description} field of the generated wrapper schema document.
	 *
	 * @param description the schema description
	 * @return this builder
	 */
	public CodecJsonSchemaOptionsBuilder schemaDescription(String description) {
		options.put(JsonSchemaOptions.JSONSCHEMA_SCHEMA_DESCRIPTION, description);
		return this;
	}

	// =========================================================================
	// JSON Schema forClass override
	// =========================================================================

	/**
	 * Starts configuring JSON Schema options for a specific EClass.
	 * The returned builder has JSON Schema serialization enabled by default
	 * (sets {@link JsonSchemaOptions#JSONSCHEMA_ENABLE} to {@code "true"}).
	 *
	 * @param eClass the EClass to configure (typically {@code EcorePackage.Literals.ECLASS})
	 * @return a {@code JsonSchemaClassOptionsBuilder} for the specified class
	 */
	@Override
	public JsonSchemaClassOptionsBuilder forClass(EClass eClass) {
		finalizeCurrentClassBuilder();
		JsonSchemaClassOptionsBuilder builder = new JsonSchemaClassOptionsBuilder(this, eClass);
		currentClassBuilder = builder;
		return builder;
	}

	// =========================================================================
	// Inner builder
	// =========================================================================

	/**
	 * Per-class options builder that adds JSON Schema specific configuration.
	 * JSON Schema mode is enabled automatically when this builder is created
	 * via {@link CodecJsonSchemaOptionsBuilder#forClass(EClass)}.
	 */
	public static class JsonSchemaClassOptionsBuilder extends ClassOptionsBuilder {

		private final Map<String, String> jsonschemaExtras = new HashMap<>();

		protected JsonSchemaClassOptionsBuilder(CodecOptionsBuilder parent, EClass eClass) {
			super(parent, eClass);
			// Auto-enable JSON Schema serialization for this class
			jsonschemaExtras.put(JsonSchemaOptions.JSONSCHEMA_ENABLE, "true");
		}

		/**
		 * Sets the name of the section used to hold referenced type definitions.
		 * Common values are {@code "definitions"} (draft-07) and {@code "$defs"}
		 * (2019-09 and later). Defaults to {@code "definitions"} when not set.
		 *
		 * @param key the section name
		 * @return this builder
		 */
		public JsonSchemaClassOptionsBuilder schemaFeatureKey(String key) {
			jsonschemaExtras.put(JsonSchemaOptions.JSONSCHEMA_FEATURE_KEY, key);
			return this;
		}

		/**
		 * Controls whether all features of the serialized EClass are placed in
		 * the {@code required} array of the generated JSON Schema, regardless of
		 * their {@code lowerBound} in the Ecore model.
		 *
		 * @param required {@code true} to mark every feature as required
		 * @return this builder
		 */
		public JsonSchemaClassOptionsBuilder allFieldsRequired(boolean required) {
			if (required) {
				jsonschemaExtras.put(JsonSchemaOptions.JSONSCHEMA_ALL_REQUIRED, "true");
			} else {
				jsonschemaExtras.remove(JsonSchemaOptions.JSONSCHEMA_ALL_REQUIRED);
			}
			return this;
		}

		/**
		 * Returns to the parent {@link CodecJsonSchemaOptionsBuilder} for further
		 * resource-level configuration.
		 *
		 * @return the parent builder, typed as {@code CodecJsonSchemaOptionsBuilder}
		 */
		@Override
		public CodecJsonSchemaOptionsBuilder and() {
			return (CodecJsonSchemaOptionsBuilder) parent;
		}

		@Override
		protected void finalizeClass() {
			if (!jsonschemaExtras.isEmpty()) {
				withExtraProperties(Map.copyOf(jsonschemaExtras));
			}
			super.finalizeClass();
		}
	}
}
