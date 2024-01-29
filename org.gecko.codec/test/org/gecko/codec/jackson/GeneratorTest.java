/**
 * Copyright (c) 2012 - 2024 Data In Motion and others.
 * All rights reserved. 
 * 
 * This program and the accompanying materials are made available under the terms of the 
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Data In Motion - initial API and implementation
 */
package org.gecko.codec.jackson;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.DataOutput;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emfcloud.jackson.databind.EMFContext;
import org.eclipse.emfcloud.jackson.junit.model.Address;
import org.eclipse.emfcloud.jackson.junit.model.ModelFactory;
import org.eclipse.emfcloud.jackson.junit.model.Sex;
import org.eclipse.emfcloud.jackson.junit.model.User;
import org.eclipse.emfcloud.jackson.resource.JsonResource;
import org.gecko.codec.jackson.databind.CodecFactory;
import org.gecko.codec.jackson.databind.deser.CodecOutputStream;
import org.gecko.codec.jackson.databind.ser.CodecGeneratorBaseImpl;
import org.gecko.codec.jackson.module.CodecModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;

/**
 * 
 * @author grune
 * @since Jan 25, 2024
 */

@RunWith(MockitoJUnitRunner.class)
class GeneratorTest {

	private CodecGeneratorBaseImpl generator;
	@Mock
	private JsonResource jsonResource;
	private AutoCloseable closeable;

	@BeforeEach
	public void openMocks() {
		closeable = MockitoAnnotations.openMocks(this);
	}

	@AfterEach
	public void releaseMocks() throws Exception {
		closeable.close();
	}

	@Test
	void test() throws StreamWriteException, DatabindException, IOException {
		generator = createAbstractMock(CodecGeneratorBaseImpl.class);
		ObjectMapper mapper = CodecModule.setupDefaultMapper(new CodecFactory() {

			private static final long serialVersionUID = 1L;

			@Override
			public CodecGeneratorBaseImpl createGenerator(DataOutput out) throws IOException {
				return generator;
			}

			@Override
			public JsonGenerator createGenerator(DataOutput out, JsonEncoding enc) throws IOException {
				return generator;
			}

			@Override
			protected JsonGenerator _createUTF8Generator(OutputStream out, IOContext ctxt) throws IOException {
				return generator;
			}
		});
		
		
		User user = createUser();
		
		Map<?, ?> options = new HashMap<>();
		mapper.writer()//
				.with(EMFContext.from(options))//
				.writeValue(new CodecOutputStream(), user);

		fail("Not yet implemented");
	}

	private User createUser() {
		User u = ModelFactory.eINSTANCE.createUser();
		u.setUserId("u1");
		u.setName("Mark");
		u.setSex(Sex.MALE);
		Address a1 = ModelFactory.eINSTANCE.createAddress();
		a1.setAddId("a1");
		a1.setCity("Jena");
		a1.setStreet("Kahlaische Strasse");
		u.setAddress(a1);
		return u;
	}

	private <T> T createAbstractMock(Class<T> mockClass) {
		return Mockito.mock(mockClass,
				Mockito.withSettings().useConstructor(-1, null, null).defaultAnswer(Mockito.CALLS_REAL_METHODS));
	}
}
