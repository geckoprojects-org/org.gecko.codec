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
package org.eclipse.fennec.codec.csv.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;

import org.eclipse.fennec.codec.CodecReaderProvider;
import org.eclipse.fennec.codec.jackson.databind.deser.CodecParserBaseImpl;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonToken;
import tools.jackson.core.Version;
import tools.jackson.core.exc.InputCoercionException;
import tools.jackson.core.io.IOContext;
import tools.jackson.core.util.VersionUtil;

/**
 * Indexed CSV Parser using FastCSV's IndexedCsvReader for lazy loading.
 * This parser enables lazy loading of ECore objects from CSV rows and
 * on-demand resolution of references between objects.
 *
 * Features:
 * - Lazy loading: Only parses rows when accessed
 * - Random access: Can jump to any row by index
 * - Memory efficient: Suitable for large CSV files
 * - Reference resolution: Can resolve ECore references on demand
 *
 * @author Claude Code
 * @since Nov 21, 2025
 */
public class CodecCSVParserIndexed extends CodecParserBaseImpl {

	private enum State {
		BEGIN,
		ARRAY_START,
		OBJECT_START,
		NAME,
		VALUE,
		OBJECT_END,
		ARRAY_END,
		END;
	}

	private IndexedCSVParser indexedParser;
	private Map<String, Object> currentRow;
	private String currentName = null;
	private State state = State.BEGIN;
	private long currentRowIndex = 0;
	private long totalRows = 0;

	/**
	 * Creates a new instance.
	 *
	 * @param context
	 * @param reader
	 */
	public CodecCSVParserIndexed(IOContext context, CodecReaderProvider<InputStream> reader) {
		super(null, context, -1, -1, reader.getObjectCodec());
		try {
			this.indexedParser = new IndexedCSVParser(reader.getReader());
			this.totalRows = indexedParser.getRowCount();
			this.currentRow = Collections.emptyMap();
		} catch (Exception e) {
			this.indexedParser = null;
			this.currentRow = Collections.emptyMap();
		}
	}

	/**
	 * Creates a new instance.
	 *
	 * @param context
	 * @param is
	 */
	public CodecCSVParserIndexed(IOContext context, InputStream is) {
		super(null, context, -1, -1);
		try {
			this.indexedParser = new IndexedCSVParser(is);
			this.totalRows = indexedParser.getRowCount();
			this.currentRow = Collections.emptyMap();
		} catch (Exception e) {
			this.indexedParser = null;
			this.currentRow = Collections.emptyMap();
		}
	}

	/**
	 * Lazily loads a specific row by index for on-demand access.
	 *
	 * @param rowIndex The zero-based index of the row to load
	 * @throws IOException when error during reading happen
	 */
	public void loadRow(long rowIndex) throws IOException {
		if (indexedParser != null && rowIndex >= 0 && rowIndex < totalRows) {
			this.currentRow = indexedParser.getRow(rowIndex);
			this.currentRowIndex = rowIndex;
		} else {
			this.currentRow = Collections.emptyMap();
		}
	}

	/**
	 * Finds and loads a row by searching for a specific value in a column.
	 * This enables on-demand reference resolution by ID or other unique identifier.
	 *
	 * @param columnName The column to search in
	 * @param value The value to search for
	 * @throws IOException when error during reading happen
	 */
	public void loadRowByReference(String columnName, String value) throws IOException {
		if (indexedParser != null) {
			this.currentRow = indexedParser.findRowByValue(columnName, value);
		} else {
			this.currentRow = Collections.emptyMap();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.fennec.codec.jackson.databind.deser.CodecParserBaseImpl#closeInput()
	 */
	@Override
	public void closeInput() {
		if (indexedParser != null) {
			try {
				indexedParser.close();
			} catch (IOException e) {
				// Ignore close errors
			}
		}
		currentRow.clear();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.fennec.codec.jackson.databind.deser.CodecParserBaseImpl#isEndDocument()
	 */
	@Override
	public boolean isEndDocument() {
		return State.END.equals(state);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.fennec.codec.jackson.databind.deser.CodecParserBaseImpl#isBeginDocument()
	 */
	@Override
	public boolean isBeginDocument() {
		return State.BEGIN.equals(state);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.fennec.codec.jackson.databind.deser.CodecParserBaseImpl#isBeginArray()
	 */
	@Override
	public boolean isBeginArray() {
		return State.ARRAY_START.equals(state);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.fennec.codec.jackson.databind.deser.CodecParserBaseImpl#doBeginArray()
	 */
	@Override
	public void doBeginArray() {
		state = State.ARRAY_START;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.fennec.codec.jackson.databind.deser.CodecParserBaseImpl#doEndArray()
	 */
	@Override
	public void doEndArray() {
		state = State.ARRAY_END;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.fennec.codec.jackson.databind.deser.CodecParserBaseImpl#doEndDocument()
	 */
	@Override
	public void doEndDocument() {
		state = State.END;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.fennec.codec.jackson.databind.deser.CodecParserBaseImpl#doReadName()
	 */
	@Override
	public String doReadName() {
		if (!currentRow.isEmpty()) {
			currentName = currentRow.keySet().iterator().next();
			state = State.VALUE;
			return currentName;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.fennec.codec.jackson.databind.deser.CodecParserBaseImpl#doBeginDocument()
	 */
	@Override
	public void doBeginDocument() {
		state = State.BEGIN;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.fennec.codec.jackson.databind.deser.CodecParserBaseImpl#doGetCurrentToken()
	 */
	@Override
	public JsonToken doGetCurrentToken() {
		return switch (state) {
			case BEGIN -> JsonToken.START_ARRAY;
			case ARRAY_START -> JsonToken.START_ARRAY;
			case OBJECT_START -> JsonToken.START_OBJECT;
			case NAME -> JsonToken.PROPERTY_NAME;
			case VALUE -> JsonToken.VALUE_STRING;
			case OBJECT_END -> JsonToken.END_OBJECT;
			case ARRAY_END -> JsonToken.END_ARRAY;
			case END -> JsonToken.END_ARRAY;
		};
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.fennec.codec.jackson.databind.deser.CodecParserBaseImpl#doGetNextToken()
	 */
	@Override
	public JsonToken doGetNextToken() {
		switch (state) {
			case BEGIN:
				state = State.ARRAY_START;
				// Load first row lazily
				if (totalRows > 0) {
					try {
						loadRow(currentRowIndex);
						state = State.OBJECT_START;
					} catch (IOException e) {
						state = State.END;
					}
				} else {
					state = State.END;
				}
				break;
			case ARRAY_START:
				if (currentRowIndex < totalRows) {
					try {
						loadRow(currentRowIndex);
						state = State.OBJECT_START;
					} catch (IOException e) {
						state = State.END;
					}
				} else {
					state = State.ARRAY_END;
				}
				break;
			case OBJECT_START:
				state = State.NAME;
				break;
			case NAME:
				state = State.VALUE;
				break;
			case VALUE:
				if (currentRow.isEmpty()) {
					state = State.OBJECT_END;
				} else {
					state = State.NAME;
				}
				break;
			case OBJECT_END:
				currentRowIndex++;
				if (currentRowIndex < totalRows) {
					state = State.ARRAY_START;
				} else {
					state = State.ARRAY_END;
				}
				break;
			case ARRAY_END:
			case END:
				state = State.END;
				break;
		}
		return doGetCurrentToken();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.fennec.codec.jackson.databind.deser.CodecParserBaseImpl#doGetCurrentValue()
	 */
	@Override
	public Object doGetCurrentValue() {
		return getCurrentValue(currentName);
	}

	/*
	 * (non-Javadoc)
	 * @see tools.jackson.core.JsonParser#canReadObjectId()
	 */
	@Override
	public boolean canReadObjectId() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see tools.jackson.core.JsonParser#getObjectId()
	 */
	@Override
	public Object getObjectId() {
		return currentValue();
	}

	private Object getCurrentValue(String name) {
		return currentRow.remove(name);
	}

	/**
	 * Returns the total number of rows available for lazy loading.
	 *
	 * @return Number of data rows
	 */
	public long getTotalRows() {
		return totalRows;
	}

	/**
	 * Returns the current row index being processed.
	 *
	 * @return Current row index
	 */
	public long getCurrentRowIndex() {
		return currentRowIndex;
	}

	/**
	 * Returns the underlying indexed parser for advanced operations
	 * like reference resolution.
	 *
	 * @return The IndexedCSVParser instance
	 */
	public IndexedCSVParser getIndexedParser() {
		return indexedParser;
	}

	/*
	 * (non-Javadoc)
	 * @see tools.jackson.core.JsonParser#version()
	 */
	@Override
	public Version version() {
		return VersionUtil.parseVersion(
				"1.0.0-SNAPSHOT", "org.eclipse.fennec.codec", "codec-csv-indexed");
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.fennec.codec.jackson.databind.deser.CodecParserBaseImpl#getStringValueObject()
	 */
	@Override
	public Object getStringValueObject() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see tools.jackson.core.base.ParserBase#_parseNumericValue(int)
	 */
	@Override
	protected void _parseNumericValue(int expType) throws JacksonException, InputCoercionException {
	}

	/*
	 * (non-Javadoc)
	 * @see tools.jackson.core.base.ParserBase#_parseIntValue()
	 */
	@Override
	protected int _parseIntValue() throws JacksonException {
		return 0;
	}
}
