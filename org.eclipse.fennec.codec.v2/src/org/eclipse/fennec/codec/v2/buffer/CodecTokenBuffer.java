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
package org.eclipse.fennec.codec.v2.buffer;

import tools.jackson.core.JsonParser;
import tools.jackson.core.ObjectReadContext;
import tools.jackson.core.TokenStreamContext;
import tools.jackson.databind.util.TokenBuffer;

/**
 * Token buffer for codec serialization that supports buffering and replay of JSON content.
 * <p>
 * This class extends Jackson's {@link TokenBuffer} to provide:
 * <ul>
 *   <li>Public factory methods for buffer creation</li>
 *   <li>Extension point for format-specific handling (JSON, MongoDB, etc.)</li>
 *   <li>EMF-aware token copying when needed</li>
 * </ul>
 * </p>
 * <p>
 * The buffer is used for scenarios like featurePath-based type resolution where
 * we need to scan ahead to find a discriminator value, then replay the content
 * for actual deserialization.
 * </p>
 *
 * @author Mark Hoffmann
 * @since 2025-12-28
 */
public class CodecTokenBuffer extends TokenBuffer {

    /**
     * Creates a new CodecTokenBuffer for buffering from a parser.
     *
     * @param parser the source parser
     * @param readContext the object read context
     */
    protected CodecTokenBuffer(JsonParser parser, ObjectReadContext readContext) {
        super(parser, readContext);
    }

    /**
     * Creates a new CodecTokenBuffer with a parent context.
     *
     * @param parentContext the parent token stream context
     */
    protected CodecTokenBuffer(TokenStreamContext parentContext) {
        super(false);
        _parentContext = parentContext;
    }

    /**
     * Creates a new empty CodecTokenBuffer.
     *
     * @param hasNativeIds whether to track native IDs
     */
    protected CodecTokenBuffer(boolean hasNativeIds) {
        super(hasNativeIds);
    }

    /**
     * Creates a buffer for capturing tokens from a parser.
     * <p>
     * Use this when you need to scan ahead in the content while
     * preserving all tokens for later replay.
     * </p>
     *
     * @param parser the source parser
     * @param readContext the object read context
     * @return a new token buffer
     */
    public static CodecTokenBuffer forBuffering(JsonParser parser, ObjectReadContext readContext) {
        return new CodecTokenBuffer(parser, readContext);
    }

    /**
     * Creates a buffer with a specific parent context.
     *
     * @param parentContext the parent token stream context
     * @return a new token buffer
     */
    public static CodecTokenBuffer forBuffering(TokenStreamContext parentContext) {
        return new CodecTokenBuffer(parentContext);
    }

    /**
     * Creates an empty buffer for token generation.
     *
     * @return a new empty token buffer
     */
    public static CodecTokenBuffer forGeneration() {
        return new CodecTokenBuffer(false);
    }

    /**
     * Returns a parser to replay the buffered content.
     * <p>
     * The parser is positioned before the first token.
     * </p>
     *
     * @param readContext the object read context
     * @return a parser for the buffered content
     */
    public JsonParser asParser(ObjectReadContext readContext) {
        return super.asParser(readContext);
    }

    /**
     * Returns a parser positioned at the first token.
     *
     * @param readContext the object read context
     * @return a parser positioned at the first token
     */
    public JsonParser asParserOnFirstToken(ObjectReadContext readContext) {
        return super.asParserOnFirstToken(readContext);
    }

    /**
     * Returns a parser positioned at the first token, using source parser for location info.
     *
     * @param readContext the object read context
     * @param sourceParser the original source parser
     * @return a parser positioned at the first token
     */
    public JsonParser asParserOnFirstToken(ObjectReadContext readContext, JsonParser sourceParser) {
        return super.asParserOnFirstToken(readContext, sourceParser);
    }
}
