/**

 * Copyright (c) 2012 - 2023 Data In Motion and others.
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
package org.gecko.codec.encode;

import java.util.Calendar;
import java.util.Date;

import org.gecko.codec.scope.HeaderScope;

/**
 * 
 * @author mark
 * @since 21.03.2023
 */
public interface CodecWriter {
	
	void writeHeader(HeaderScope scope);
	
	void writeFeature(String featureName, Object value);
	
	void writeNull();
	void writeNull(String featureName);
	
	void writeProxy(Object value);
	void writeProxy(String featureName, Object value);
	
	void writeInt(Integer value);
	void writeInt(String featureName, Integer value);
	
	void writeLong(Long value);
	void writeLong(String featureName, Long value);
	
	void writeFloat(Float value);
	void writeFloat(String featureName, Float value);
	
	void writeDouble(Double value);
	void writeDouble(String featureName, Double value);
	
	void writeByte(Byte value);
	void writeByte(String featureName, Byte value);
	
	void writeShort(Short value);
	void writeShort(String featureName, Short value);
	
	void writeChar(Character value);
	void writeChar(String featureName, Character value);
	
	void writeBoolean(Boolean value);
	void writeBoolean(String featureName, Boolean value);
	
	void writeString(String value);
	void writeString(String featureName, String value);
	
	void writeString(Object value);
	void writeString(String featureName, Object value);
	
	void writeByteArray(byte[] value);
	void writeByteArray(String featureName, byte[] value);
	
	void writeDateTime(Date value);
	void writeDateTime(String featureName, Date value);
	void writeDateTime(long value);
	void writeDateTime(String featureName, long value);
	
	void writeCalendar(long value);
	void writeCalendar(String featureName, long value);
	
	void writeCalendar(Calendar value);
	void writeCalendar(String featureName, Calendar value);
	
	void writeStartList();
	void writeStartList(String featureName);
	void writeEndList();
	
	void writeStartObject();
	void writeStartObject(String featureName);
	void writeEndObject();
}
