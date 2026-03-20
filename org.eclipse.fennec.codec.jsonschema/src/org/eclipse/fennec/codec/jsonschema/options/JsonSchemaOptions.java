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

/**
 * Constants for JSON Schema serialization options.
 * These are used as keys in the codec extra-properties map to control
 * how an EClass or EPackage is serialized to a JSON Schema document.
 *
 * <p>Use {@link CodecJsonSchemaOptionsBuilder} instead of setting these
 * keys manually via {@code withExtraProperties}.
 *
 * @author Data In Motion
 * @since Dec 2025
 */
public interface JsonSchemaOptions {

	/**
	 * Extra-property key that activates JSON Schema serialization for the
	 * annotated EClass. Set the value to {@code "true"} to enable.
	 */
	String JSONSCHEMA_ENABLE = "jsonschema";

	/**
	 * Extra-property key that controls the name of the section that holds
	 * referenced type definitions. Typical values are {@code "definitions"}
	 * (JSON Schema draft-07 style) or {@code "$defs"} (JSON Schema 2019-09+).
	 * Defaults to {@code "definitions"} when not set.
	 */
	String JSONSCHEMA_FEATURE_KEY = "jsonschema.feature.key";

	/**
	 * Extra-property key that, when set to {@code "true"}, marks every
	 * feature of the serialized EClass as required in the JSON Schema output,
	 * regardless of the feature's {@code lowerBound} in the Ecore model.
	 */
	String JSONSCHEMA_ALL_REQUIRED = "jsonschema.all.required";

	// =========================================================================
	// Resource-level options for multi-EClass oneOf wrapper schemas
	// =========================================================================

	/**
	 * Resource-level option key for the name of the root array property in the
	 * generated wrapper schema. When this option is set, the serializer generates
	 * a wrapper object schema with a single array property whose items use a
	 * {@code oneOf} pointing to all EClasses listed in
	 * {@link #JSONSCHEMA_ONE_OF_ECLASSES}.
	 *
	 * <p>Example: setting this to {@code "results"} generates:
	 * <pre>
	 * {
	 *   "type": "object",
	 *   "properties": {
	 *     "results": {
	 *       "type": "array",
	 *       "items": { "oneOf": [ {"$ref": "#/definitions/MyClass"}, ... ] }
	 *     }
	 *   },
	 *   "required": ["results"]
	 * }
	 * </pre>
	 */
	String JSONSCHEMA_ROOT_ARRAY_NAME = "jsonschema.root.array.name";

	/**
	 * Resource-level option key holding the {@code List<EClass>} of EClasses
	 * that should appear as {@code $ref} items inside the {@code oneOf} of the
	 * wrapper array. Each EClass, together with all its transitive dependencies,
	 * is placed in the {@code definitions} (or {@code $defs"}) section.
	 *
	 * <p>Used together with {@link #JSONSCHEMA_ROOT_ARRAY_NAME}.
	 */
	String JSONSCHEMA_ONE_OF_ECLASSES = "jsonschema.oneof.eclasses";

	/**
	 * Resource-level option key for the {@code title} field written at the
	 * top level of the generated wrapper schema. Optional.
	 */
	String JSONSCHEMA_SCHEMA_TITLE = "jsonschema.schema.title";

	/**
	 * Resource-level option key for the {@code $id} field written at the
	 * top level of the generated wrapper schema. Optional.
	 */
	String JSONSCHEMA_SCHEMA_ID = "jsonschema.schema.id";

	/**
	 * Resource-level option key for the {@code description} field written at
	 * the top level of the generated wrapper schema. Optional.
	 */
	String JSONSCHEMA_SCHEMA_DESCRIPTION = "jsonschema.schema.description";
}
