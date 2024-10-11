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
package org.gecko.codec.info.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.gecko.codec.demo.jackson.CodecModule;
import org.gecko.codec.demo.jackson.CodecModuleConfigurator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.common.annotation.Property;
import org.osgi.test.common.annotation.Property.Scalar;
import org.osgi.test.common.annotation.config.WithFactoryConfiguration;
import org.osgi.test.junit5.cm.ConfigurationExtension;
import org.osgi.test.junit5.context.BundleContextExtension;
import org.osgi.test.junit5.service.ServiceExtension;

//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;

/**
 * See documentation here: 
 * 	https://github.com/osgi/osgi-test
 * 	https://github.com/osgi/osgi-test/wiki
 * Examples: https://github.com/osgi/osgi-test/tree/main/examples
 */
@ExtendWith(BundleContextExtension.class)
@ExtendWith(ServiceExtension.class)
@ExtendWith(MockitoExtension.class)
@ExtendWith(ConfigurationExtension.class)
public class CodecModuleConfiguratorTest {

	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test")
	@Test
	public void testCodecModuleConfigCodecDefault(@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator) throws InterruptedException, IOException {
	
		assertNotNull(codecModuleConfigurator);
		CodecModule module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertEquals("json", module.getCodecType());	
		assertEquals("_id", module.getIdKey());		
		assertEquals("_type", module.getTypeKey());		
		assertEquals("_supertype", module.getSuperTypeKey());		
		assertEquals("$ref", module.getRefKey());		
		assertEquals("_proxy", module.getProxyKey());		
		assertEquals("_timestamp", module.getTimestampKey());		
		assertEquals("gecko-codec-module", module.getModuleName());		
		assertFalse(module.isSerializeDefaultValue());		
		assertFalse(module.isSerializeNullValue());		
		assertFalse(module.isSerializeEmptyValue());		
		assertFalse(module.isSerializeArrayBatched());
		assertTrue(module.isUseNamesFromExtendedMetaData());
		assertTrue(module.isUseId());
		assertTrue(module.isIdOnTop());
		assertFalse(module.isSerializeIdField());
		assertTrue(module.isIdFeatureAsPrimaryKey());
		assertTrue(module.isSerializeType());
		assertFalse(module.isSerializeSuperTypes());
		assertTrue(module.isSerializeSuperTypesAsArray());		
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test", properties = {
			@Property(key = "codecType", value="test")
	})
	@Test
	public void testCodecModuleConfigCodecType(@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator) throws InterruptedException, IOException {
	
		assertNotNull(codecModuleConfigurator);
		CodecModule module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertEquals("test", module.getCodecType());		
	}
	
		
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test", properties = {
			@Property(key = "idKey", value="test")
	})
	@Test
	public void testCodecModuleConfigCodecIdKey(@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator) throws InterruptedException, IOException {
	
		assertNotNull(codecModuleConfigurator);
		CodecModule module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertEquals("test", module.getIdKey());		
	}
	
	
	
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test", properties = {
			@Property(key = "typeKey", value="test")
	})
	@Test
	public void testCodecModuleConfigCodecTypeKey(@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator) throws InterruptedException, IOException {
	
		assertNotNull(codecModuleConfigurator);
		CodecModule module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertEquals("test", module.getTypeKey());		
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test", properties = {
			@Property(key = "superTypeKey", value="test")
	})
	@Test
	public void testCodecModuleConfigCodecSuperTypeKey(@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator) throws InterruptedException, IOException {
	
		assertNotNull(codecModuleConfigurator);
		CodecModule module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertEquals("test", module.getSuperTypeKey());		
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test", properties = {
			@Property(key = "refKey", value="test")
	})
	@Test
	public void testCodecModuleConfigCodecRefKey(@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator) throws InterruptedException, IOException {
	
		assertNotNull(codecModuleConfigurator);
		CodecModule module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertEquals("test", module.getRefKey());		
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test", properties = {
			@Property(key = "proxyKey", value="test")
	})
	@Test
	public void testCodecModuleConfigCodecProxyKey(@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator) throws InterruptedException, IOException {
	
		assertNotNull(codecModuleConfigurator);
		CodecModule module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertEquals("test", module.getProxyKey());		
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test", properties = {
			@Property(key = "timestampKey", value="test")
	})
	@Test
	public void testCodecModuleConfigCodecTimestampKey(@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator) throws InterruptedException, IOException {
	
		assertNotNull(codecModuleConfigurator);
		CodecModule module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertEquals("test", module.getTimestampKey());		
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test", properties = {
			@Property(key = "codecModuleName", value="test")
	})
	@Test
	public void testCodecModuleConfigCodecModuleName(@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator) throws InterruptedException, IOException {
	
		assertNotNull(codecModuleConfigurator);
		CodecModule module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertEquals("test", module.getModuleName());
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test", properties = {
			@Property(key = "serializeDefaultValue", value="true", scalar = Scalar.Boolean)
	})
	@Test
	public void testCodecModuleConfigSerDefValue(@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator) throws InterruptedException, IOException {
	
		assertNotNull(codecModuleConfigurator);
		CodecModule module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertTrue(module.isSerializeDefaultValue());
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test", properties = {
			@Property(key = "serializeEmptyValue", value="true", scalar = Scalar.Boolean)
	})
	@Test
	public void testCodecModuleConfigSerEmptyValue(@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator) throws InterruptedException, IOException {
	
		assertNotNull(codecModuleConfigurator);
		CodecModule module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertTrue(module.isSerializeEmptyValue());
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test", properties = {
			@Property(key = "serializeNullValue", value="true", scalar = Scalar.Boolean)
	})
	@Test
	public void testCodecModuleConfigSerNullValue(@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator) throws InterruptedException, IOException {
	
		assertNotNull(codecModuleConfigurator);
		CodecModule module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertTrue(module.isSerializeNullValue());
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test", properties = {
			@Property(key = "serializeArrayBatched", value="true", scalar = Scalar.Boolean)
	})
	@Test
	public void testCodecModuleConfigSerArrayBatched(@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator) throws InterruptedException, IOException {
	
		assertNotNull(codecModuleConfigurator);
		CodecModule module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertTrue(module.isSerializeArrayBatched());
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test", properties = {
			@Property(key = "serializeIdField", value="true", scalar = Scalar.Boolean)
	})
	@Test
	public void testCodecModuleConfigSerIdField(@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator) throws InterruptedException, IOException {
	
		assertNotNull(codecModuleConfigurator);
		CodecModule module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertTrue(module.isSerializeIdField());
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test", properties = {
			@Property(key = "serializeSuperTypes", value="true", scalar = Scalar.Boolean)
	})
	@Test
	public void testCodecModuleConfigSerSuperTypes(@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator) throws InterruptedException, IOException {
	
		assertNotNull(codecModuleConfigurator);
		CodecModule module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertTrue(module.isSerializeSuperTypes());
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test", properties = {
			@Property(key = "useNamesFromExtendedMetaData", value="false", scalar = Scalar.Boolean)
	})
	@Test
	public void testCodecModuleConfigUseNamesFromExtendedMetadata(@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator) throws InterruptedException, IOException {
	
		assertNotNull(codecModuleConfigurator);
		CodecModule module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertFalse(module.isUseNamesFromExtendedMetaData());
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test", properties = {
			@Property(key = "useId", value="false", scalar = Scalar.Boolean)
	})
	@Test
	public void testCodecModuleConfigUseId(@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator) throws InterruptedException, IOException {
	
		assertNotNull(codecModuleConfigurator);
		CodecModule module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertFalse(module.isUseId());
	}

	
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test", properties = {
			@Property(key = "idOnTop", value="false", scalar = Scalar.Boolean)
	})
	@Test
	public void testCodecModuleConfigIdOnTop(@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator) throws InterruptedException, IOException {
	
		assertNotNull(codecModuleConfigurator);
		CodecModule module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertFalse(module.isIdOnTop());
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test", properties = {
			@Property(key = "idFeatureAsPrimaryKey", value="false", scalar = Scalar.Boolean)
	})
	@Test
	public void testCodecModuleConfigIdFeatureAsPrimaryKey(@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator) throws InterruptedException, IOException {
	
		assertNotNull(codecModuleConfigurator);
		CodecModule module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertFalse(module.isIdFeatureAsPrimaryKey());
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test", properties = {
			@Property(key = "serializeType", value="false", scalar = Scalar.Boolean)
	})
	@Test
	public void testCodecModuleConfigSerType(@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator) throws InterruptedException, IOException {
	
		assertNotNull(codecModuleConfigurator);
		CodecModule module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertFalse(module.isSerializeType());
	}
	
	@WithFactoryConfiguration(factoryPid = "CodecModuleConfigurator", location = "?", name = "test", properties = {
			@Property(key = "serailizeSuperTypesAsArray", value="false", scalar = Scalar.Boolean)
	})
	@Test
	public void testCodecModuleConfigSerSuperTypesAsArray(@InjectService(timeout = 2000l) CodecModuleConfigurator codecModuleConfigurator) throws InterruptedException, IOException {
	
		assertNotNull(codecModuleConfigurator);
		CodecModule module = codecModuleConfigurator.getCodecModuleBuilder().build();
		assertFalse(module.isSerializeSuperTypesAsArray());
	}
}
