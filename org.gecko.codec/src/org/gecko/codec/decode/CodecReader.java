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
package org.gecko.codec.decode;

import java.nio.ByteBuffer;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.gecko.codec.CodecType;

/**
 * 
 * @author mark
 * @since 21.03.2023
 */
public interface CodecReader {
	
	String readType();
	String readType(String featureName);
	List<String> readSuperTypes();
	List<String> readSuperTypes(String featureName);
	
	CodecType getCurrentType();
	String getCurrentName();
	
	void readNull();
	void readNull(String featureName);
	
	Object readId();
	Object readId(String featureName);
	
	Object readProxy();
	Object readProxy(String featureName);
	
	int readInt();
	int readInt(String featureName);
	
	long readLong();
	long readLong(String featureName);
	
	float readFloat();
	float readFloat(String featureName);
	
	double readDouble();
	double readDouble(String featureName);
	
	byte readByte();
	byte readByte(String featureName);
	
	short readShort();
	short readShort(String featureName);
	
	char readChar();
	char readChar(String featureName);
	
	boolean readBoolean();
	boolean readBoolean(String featureName);
	
	String readString();
	String readString(String featureName);
	
	byte[] readByteArray();
	byte[] readByteArray(String featureName);
	
	ByteBuffer readByteBuffer();
	ByteBuffer readByteBuffer(String featureName);
	
	Date readDateTime();
	Date readDateTime(String featureName);
	
	long readDateTimeLong();
	long readDateTimeLong(String featureName);
	
	Calendar readCalendar();
	Calendar readCalendar(String featureName);
	
	void readStartList();
	void readStartList(String featureName);
	void readEndList();
	
	Object readStartObject();
	Object readStartObject(String featureName);
	void readEndObject();
	
	void skipName();
	void skipValue();
	
	String readName();
	String readName(String featureName);

}
