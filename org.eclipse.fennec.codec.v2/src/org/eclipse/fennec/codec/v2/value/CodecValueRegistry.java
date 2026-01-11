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
package org.eclipse.fennec.codec.v2.value;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Registry for custom value readers and writers.
 * <p>
 * This registry manages named value readers and writers that can be referenced
 * by name in codec configuration (via EAnnotations or Java builders).
 * </p>
 * <p>
 * Example usage:
 * </p>
 * <pre>
 * CodecValueRegistry registry = new CodecValueRegistry();
 * registry.registerWriter("dateWriter", new ISO8601DateWriter());
 * registry.registerReader("dateReader", new ISO8601DateReader());
 *
 * // Later, retrieve by name
 * Optional&lt;CodecValueWriter&lt;?&gt;&gt; writer = registry.getWriter("dateWriter");
 * </pre>
 *
 * @see <a href="docs/codec-v2-serialization-spec.md#10-custom-value-readerswriters">Spec 10: Custom Value Readers/Writers</a>
 */
public class CodecValueRegistry {

    private final Map<String, CodecValueWriter<?, ?>> writers = new ConcurrentHashMap<>();
    private final Map<String, CodecValueReader<?, ?>> readers = new ConcurrentHashMap<>();

    /**
     * Creates an empty registry.
     */
    public CodecValueRegistry() {
    }

    /**
     * Creates a registry initialized with the given writers and readers.
     *
     * @param writers the initial writers (name -> writer)
     * @param readers the initial readers (name -> reader)
     */
    public CodecValueRegistry(Map<String, CodecValueWriter<?, ?>> writers,
                               Map<String, CodecValueReader<?, ?>> readers) {
        if (writers != null) {
            this.writers.putAll(writers);
        }
        if (readers != null) {
            this.readers.putAll(readers);
        }
    }

    /**
     * Registers a value writer with the given name.
     *
     * @param name the writer name (used to reference in configuration)
     * @param writer the writer implementation
     * @return this registry for chaining
     * @throws IllegalArgumentException if name or writer is null
     */
    public CodecValueRegistry registerWriter(String name, CodecValueWriter<?, ?> writer) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Writer name must not be null or empty");
        }
        if (writer == null) {
            throw new IllegalArgumentException("Writer must not be null");
        }
        writers.put(name, writer);
        return this;
    }

    /**
     * Registers a value reader with the given name.
     *
     * @param name the reader name (used to reference in configuration)
     * @param reader the reader implementation
     * @return this registry for chaining
     * @throws IllegalArgumentException if name or reader is null
     */
    public CodecValueRegistry registerReader(String name, CodecValueReader<?, ?> reader) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Reader name must not be null or empty");
        }
        if (reader == null) {
            throw new IllegalArgumentException("Reader must not be null");
        }
        readers.put(name, reader);
        return this;
    }

    /**
     * Unregisters a value writer.
     *
     * @param name the writer name
     * @return this registry for chaining
     */
    public CodecValueRegistry unregisterWriter(String name) {
        if (name != null) {
            writers.remove(name);
        }
        return this;
    }

    /**
     * Unregisters a value reader.
     *
     * @param name the reader name
     * @return this registry for chaining
     */
    public CodecValueRegistry unregisterReader(String name) {
        if (name != null) {
            readers.remove(name);
        }
        return this;
    }

    /**
     * Gets a value writer by name.
     *
     * @param name the writer name
     * @return the writer if found, or empty if not found
     */
    public Optional<CodecValueWriter<?, ?>> getWriter(String name) {
        if (name == null || name.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(writers.get(name));
    }

    /**
     * Gets a value writer by name, cast to the expected types.
     *
     * @param <T> the expected value type
     * @param <F> the expected feature type
     * @param name the writer name
     * @param valueType the expected value type (for type safety)
     * @param featureType the expected feature type (for type safety)
     * @return the writer if found, or empty if not found
     */
    @SuppressWarnings("unchecked")
    public <T, F extends org.eclipse.emf.ecore.EStructuralFeature> Optional<CodecValueWriter<T, F>> getWriter(
            String name, Class<T> valueType, Class<F> featureType) {
        return getWriter(name).map(w -> (CodecValueWriter<T, F>) w);
    }

    /**
     * Gets a value reader by name.
     *
     * @param name the reader name
     * @return the reader if found, or empty if not found
     */
    public Optional<CodecValueReader<?, ?>> getReader(String name) {
        if (name == null || name.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(readers.get(name));
    }

    /**
     * Gets a value reader by name, cast to the expected types.
     *
     * @param <T> the expected value type
     * @param <F> the expected feature type
     * @param name the reader name
     * @param valueType the expected value type (for type safety)
     * @param featureType the expected feature type (for type safety)
     * @return the reader if found, or empty if not found
     */
    @SuppressWarnings("unchecked")
    public <T, F extends org.eclipse.emf.ecore.EStructuralFeature> Optional<CodecValueReader<T, F>> getReader(
            String name, Class<T> valueType, Class<F> featureType) {
        return getReader(name).map(r -> (CodecValueReader<T, F>) r);
    }

    /**
     * Checks if a writer with the given name is registered.
     *
     * @param name the writer name
     * @return true if a writer is registered with this name
     */
    public boolean hasWriter(String name) {
        return name != null && writers.containsKey(name);
    }

    /**
     * Checks if a reader with the given name is registered.
     *
     * @param name the reader name
     * @return true if a reader is registered with this name
     */
    public boolean hasReader(String name) {
        return name != null && readers.containsKey(name);
    }

    /**
     * Returns an unmodifiable view of all registered writers.
     *
     * @return map of writer name to writer
     */
    public Map<String, CodecValueWriter<?, ?>> getWriters() {
        return Collections.unmodifiableMap(writers);
    }

    /**
     * Returns an unmodifiable view of all registered readers.
     *
     * @return map of reader name to reader
     */
    public Map<String, CodecValueReader<?, ?>> getReaders() {
        return Collections.unmodifiableMap(readers);
    }

    /**
     * Clears all registered writers and readers.
     */
    public void clear() {
        writers.clear();
        readers.clear();
    }

    /**
     * Creates a copy of this registry.
     *
     * @return a new registry with the same writers and readers
     */
    public CodecValueRegistry copy() {
        return new CodecValueRegistry(writers, readers);
    }
}
