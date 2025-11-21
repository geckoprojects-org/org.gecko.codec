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
package org.eclipse.fennec.codec.csv.config;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Configuration for CSV parsing with validation and security features.
 * This class encapsulates all FastCSV configuration options for secure
 * and validated CSV parsing.
 *
 * Security Features:
 * - Max field length (prevents DoS attacks with huge fields)
 * - Max row length (prevents memory exhaustion)
 * - Character encoding validation
 *
 * Validation Features:
 * - Skip empty rows
 * - Ignore leading/trailing whitespace
 * - Comment line detection
 * - Field count validation
 * - Error on different field count
 *
 * @author Claude Code
 * @since Nov 21, 2025
 */
public class CSVReaderConfig {

    // Default configuration
    public static final char DEFAULT_FIELD_SEPARATOR = ',';
    public static final char DEFAULT_QUOTE_CHARACTER = '"';
    public static final char DEFAULT_COMMENT_CHARACTER = '#';
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    public static final int DEFAULT_MAX_FIELD_LENGTH = 10_000_000; // 10MB per field
    public static final int DEFAULT_MAX_ROW_LENGTH = 100_000_000; // 100MB per row
    public static final boolean DEFAULT_SKIP_EMPTY_ROWS = true;
    public static final boolean DEFAULT_IGNORE_LEADING_WHITESPACE = false;
    public static final boolean DEFAULT_IGNORE_TRAILING_WHITESPACE = false;
    public static final boolean DEFAULT_ERROR_ON_DIFFERENT_FIELD_COUNT = false;
    public static final boolean DEFAULT_ACCEPT_CHAINED_EXCEPTIONS = true;

    // Configuration fields
    private char fieldSeparator = DEFAULT_FIELD_SEPARATOR;
    private char quoteCharacter = DEFAULT_QUOTE_CHARACTER;
    private char commentCharacter = DEFAULT_COMMENT_CHARACTER;
    private boolean commentEnabled = false;
    private Charset charset = DEFAULT_CHARSET;
    private int maxFieldLength = DEFAULT_MAX_FIELD_LENGTH;
    private int maxRowLength = DEFAULT_MAX_ROW_LENGTH;
    private boolean skipEmptyRows = DEFAULT_SKIP_EMPTY_ROWS;
    private boolean ignoreLeadingWhitespace = DEFAULT_IGNORE_LEADING_WHITESPACE;
    private boolean ignoreTrailingWhitespace = DEFAULT_IGNORE_TRAILING_WHITESPACE;
    private boolean errorOnDifferentFieldCount = DEFAULT_ERROR_ON_DIFFERENT_FIELD_COUNT;
    private boolean acceptChainedExceptions = DEFAULT_ACCEPT_CHAINED_EXCEPTIONS;
    private Integer expectedFieldCount = null;

    /**
     * Creates a default configuration.
     */
    public CSVReaderConfig() {
    }

    /**
     * Creates a configuration from the default.
     *
     * @return default configuration
     */
    public static CSVReaderConfig defaultConfig() {
        return new CSVReaderConfig();
    }

    /**
     * Creates a strict configuration with validation enabled.
     *
     * @return strict configuration
     */
    public static CSVReaderConfig strictConfig() {
        CSVReaderConfig config = new CSVReaderConfig();
        config.errorOnDifferentFieldCount = true;
        config.skipEmptyRows = true;
        config.ignoreLeadingWhitespace = true;
        config.ignoreTrailingWhitespace = true;
        return config;
    }

    /**
     * Creates a secure configuration with limits to prevent DoS attacks.
     *
     * @return secure configuration
     */
    public static CSVReaderConfig secureConfig() {
        CSVReaderConfig config = new CSVReaderConfig();
        config.maxFieldLength = 1_000_000; // 1MB per field
        config.maxRowLength = 10_000_000;  // 10MB per row
        config.skipEmptyRows = true;
        config.errorOnDifferentFieldCount = true;
        return config;
    }

    /**
     * Creates a permissive configuration that accepts various CSV formats.
     *
     * @return permissive configuration
     */
    public static CSVReaderConfig permissiveConfig() {
        CSVReaderConfig config = new CSVReaderConfig();
        config.errorOnDifferentFieldCount = false;
        config.skipEmptyRows = true;
        config.ignoreLeadingWhitespace = true;
        config.ignoreTrailingWhitespace = true;
        return config;
    }

    // Getters and setters

    public char getFieldSeparator() {
        return fieldSeparator;
    }

    public CSVReaderConfig setFieldSeparator(char fieldSeparator) {
        this.fieldSeparator = fieldSeparator;
        return this;
    }

    public char getQuoteCharacter() {
        return quoteCharacter;
    }

    public CSVReaderConfig setQuoteCharacter(char quoteCharacter) {
        this.quoteCharacter = quoteCharacter;
        return this;
    }

    public char getCommentCharacter() {
        return commentCharacter;
    }

    public CSVReaderConfig setCommentCharacter(char commentCharacter) {
        this.commentCharacter = commentCharacter;
        return this;
    }

    public boolean isCommentEnabled() {
        return commentEnabled;
    }

    public CSVReaderConfig setCommentEnabled(boolean commentEnabled) {
        this.commentEnabled = commentEnabled;
        return this;
    }

    public Charset getCharset() {
        return charset;
    }

    public CSVReaderConfig setCharset(Charset charset) {
        this.charset = charset;
        return this;
    }

    public int getMaxFieldLength() {
        return maxFieldLength;
    }

    /**
     * Sets the maximum field length. This is a security feature to prevent
     * DoS attacks with huge fields that could exhaust memory.
     *
     * @param maxFieldLength maximum field length in bytes
     * @return this config for chaining
     */
    public CSVReaderConfig setMaxFieldLength(int maxFieldLength) {
        if (maxFieldLength <= 0) {
            throw new IllegalArgumentException("maxFieldLength must be positive");
        }
        this.maxFieldLength = maxFieldLength;
        return this;
    }

    public int getMaxRowLength() {
        return maxRowLength;
    }

    /**
     * Sets the maximum row length. This is a security feature to prevent
     * DoS attacks with huge rows that could exhaust memory.
     *
     * @param maxRowLength maximum row length in bytes
     * @return this config for chaining
     */
    public CSVReaderConfig setMaxRowLength(int maxRowLength) {
        if (maxRowLength <= 0) {
            throw new IllegalArgumentException("maxRowLength must be positive");
        }
        this.maxRowLength = maxRowLength;
        return this;
    }

    public boolean isSkipEmptyRows() {
        return skipEmptyRows;
    }

    public CSVReaderConfig setSkipEmptyRows(boolean skipEmptyRows) {
        this.skipEmptyRows = skipEmptyRows;
        return this;
    }

    public boolean isIgnoreLeadingWhitespace() {
        return ignoreLeadingWhitespace;
    }

    public CSVReaderConfig setIgnoreLeadingWhitespace(boolean ignoreLeadingWhitespace) {
        this.ignoreLeadingWhitespace = ignoreLeadingWhitespace;
        return this;
    }

    public boolean isIgnoreTrailingWhitespace() {
        return ignoreTrailingWhitespace;
    }

    public CSVReaderConfig setIgnoreTrailingWhitespace(boolean ignoreTrailingWhitespace) {
        this.ignoreTrailingWhitespace = ignoreTrailingWhitespace;
        return this;
    }

    public boolean isErrorOnDifferentFieldCount() {
        return errorOnDifferentFieldCount;
    }

    /**
     * If enabled, an exception is thrown when a row has a different number
     * of fields than expected. This is a validation feature.
     *
     * @param errorOnDifferentFieldCount whether to error on different field count
     * @return this config for chaining
     */
    public CSVReaderConfig setErrorOnDifferentFieldCount(boolean errorOnDifferentFieldCount) {
        this.errorOnDifferentFieldCount = errorOnDifferentFieldCount;
        return this;
    }

    public boolean isAcceptChainedExceptions() {
        return acceptChainedExceptions;
    }

    public CSVReaderConfig setAcceptChainedExceptions(boolean acceptChainedExceptions) {
        this.acceptChainedExceptions = acceptChainedExceptions;
        return this;
    }

    public Integer getExpectedFieldCount() {
        return expectedFieldCount;
    }

    /**
     * Sets the expected number of fields per row. If errorOnDifferentFieldCount
     * is enabled, an exception will be thrown if a row has a different count.
     *
     * @param expectedFieldCount expected number of fields
     * @return this config for chaining
     */
    public CSVReaderConfig setExpectedFieldCount(Integer expectedFieldCount) {
        this.expectedFieldCount = expectedFieldCount;
        return this;
    }

    /**
     * Enables semicolon as field separator (common in European CSVs).
     *
     * @return this config for chaining
     */
    public CSVReaderConfig useSemicolonSeparator() {
        return setFieldSeparator(';');
    }

    /**
     * Enables tab as field separator (TSV format).
     *
     * @return this config for chaining
     */
    public CSVReaderConfig useTabSeparator() {
        return setFieldSeparator('\t');
    }

    /**
     * Enables pipe as field separator.
     *
     * @return this config for chaining
     */
    public CSVReaderConfig usePipeSeparator() {
        return setFieldSeparator('|');
    }

    @Override
    public String toString() {
        return "CSVReaderConfig{" +
                "fieldSeparator=" + fieldSeparator +
                ", quoteCharacter=" + quoteCharacter +
                ", commentCharacter=" + commentCharacter +
                ", commentEnabled=" + commentEnabled +
                ", charset=" + charset +
                ", maxFieldLength=" + maxFieldLength +
                ", maxRowLength=" + maxRowLength +
                ", skipEmptyRows=" + skipEmptyRows +
                ", ignoreLeadingWhitespace=" + ignoreLeadingWhitespace +
                ", ignoreTrailingWhitespace=" + ignoreTrailingWhitespace +
                ", errorOnDifferentFieldCount=" + errorOnDifferentFieldCount +
                ", expectedFieldCount=" + expectedFieldCount +
                '}';
    }
}
