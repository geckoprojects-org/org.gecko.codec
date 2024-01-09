/*
 * Copyright (c) 2019 Guillaume Hillairet and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 *
 */

package org.eclipse.emfcloud.jackson.support;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;
import java.util.function.Predicate;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xml.namespace.XMLNamespacePackage;
import org.eclipse.emfcloud.jackson.databind.ser.NullKeySerializer;
import org.eclipse.emfcloud.jackson.junit.array.ArrayPackage;
import org.eclipse.emfcloud.jackson.junit.generics.GenericsPackage;
import org.eclipse.emfcloud.jackson.junit.model.ModelPackage;
import org.eclipse.emfcloud.jackson.module.EMFModule;
import org.eclipse.emfcloud.jackson.resource.JsonResourceFactory;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.platform.commons.support.ModifierSupport;

import com.fasterxml.jackson.databind.ObjectMapper;

public class StandardExtension implements BeforeAllCallback, BeforeEachCallback, AfterEachCallback, ParameterResolver {

	protected ObjectMapper mapper;
	protected ResourceSetImpl resourceSet;

	protected URI baseTestFilesFileDirectory = URI.createFileURI("test-data/tests/");

	protected void before() throws Exception {
		URI baseURI = URI.createURI("http://eclipselabs.org/emfjson/tests/");

		mapper = createMapper();
		mapper.registerModule(new EMFModule());

		resourceSet = new ResourceSetImpl();
		resourceSet.getURIConverter()
				.getURIMap()
				.put(baseURI, baseTestFilesFileDirectory);

		resourceSet.getPackageRegistry().put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(XMLNamespacePackage.eNS_URI, XMLNamespacePackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(ArrayPackage.eNS_URI, ArrayPackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(ModelPackage.eNS_URI, ModelPackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(GenericsPackage.eNS_URI, GenericsPackage.eINSTANCE);

		resourceSet.getResourceFactoryRegistry()
				.getExtensionToFactoryMap()
				.put("*", new JsonResourceFactory(mapper));

		resourceSet.getResourceFactoryRegistry()
				.getExtensionToFactoryMap()
				.put("xmi", new XMIResourceFactoryImpl());
	}

	protected void after() {
		resourceSet.getPackageRegistry().clear();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().clear();
	}

	private ObjectMapper createMapper() {
		final ObjectMapper mapper = new ObjectMapper();
		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
		dateFormat.setTimeZone(TimeZone.getDefault());

		mapper.setDateFormat(dateFormat);
		mapper.setTimeZone(TimeZone.getDefault());
		
		mapper.getSerializerProvider().setNullKeySerializer(new NullKeySerializer());

		return mapper;
	}

	public ObjectMapper mapper(EMFModule.Feature feature, Boolean value) {
		final ObjectMapper mapper = createMapper();
		final EMFModule module = new EMFModule();
		module.configure(feature, value);
		mapper.registerModule(module);

		return mapper;
	}

	
	/* 
	 * (non-Javadoc)
	 * @see org.junit.jupiter.api.extension.BeforeEachCallback#beforeEach(org.junit.jupiter.api.extension.ExtensionContext)
	 */
	@Override
	public void beforeEach(ExtensionContext context) throws Exception {
		before();
		Class<?> testClass = context.getRequiredTestClass();
        Object testInstance = context.getRequiredTestInstance();
        injectFields(testClass, testInstance, ModifierSupport::isNotStatic);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.junit.jupiter.api.extension.AfterEachCallback#afterEach(org.junit.jupiter.api.extension.ExtensionContext)
	 */
	@Override
	public void afterEach(ExtensionContext context) throws Exception {
		after();
	}

	/* 
	 * (non-Javadoc)
	 * @see org.junit.jupiter.api.extension.ParameterResolver#supportsParameter(org.junit.jupiter.api.extension.ParameterContext, org.junit.jupiter.api.extension.ExtensionContext)
	 */
	@Override
	public boolean supportsParameter(ParameterContext pc, ExtensionContext ec)
			throws ParameterResolutionException {
		return isResourceSet(pc) || isObjectMapper(pc);
	}
	
	private boolean isResourceSet(ParameterContext pc) {
		return pc.isAnnotated(ResourceSet.class) && pc.getParameter().getType() == org.eclipse.emf.ecore.resource.ResourceSet.class;
	}
	
	private boolean isObjectMapper(ParameterContext pc) {
		return pc.isAnnotated(org.eclipse.emfcloud.jackson.support.ObjectMapper.class) && pc.getParameter().getType() == ObjectMapper.class;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.junit.jupiter.api.extension.ParameterResolver#resolveParameter(org.junit.jupiter.api.extension.ParameterContext, org.junit.jupiter.api.extension.ExtensionContext)
	 */
	@Override
	public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException {
		if (isObjectMapper(parameterContext)) {
			Optional<org.eclipse.emfcloud.jackson.support.ObjectMapper> annotation = parameterContext.findAnnotation(org.eclipse.emfcloud.jackson.support.ObjectMapper.class);
			return getMapper(annotation.get());
		} else if (isResourceSet(parameterContext)) {
			return resourceSet;
		}
		return null;
	}
	
	private ObjectMapper getMapper(org.eclipse.emfcloud.jackson.support.ObjectMapper oma) {
		requireNonNull(oma);
		if (EMFModule.Feature.NONE.equals(oma.feature())) {
			return mapper;
		} else {
			return mapper(oma.feature(), oma.enabled());
		}
	}
	
	private void injectObjectMapper(Object instance, Field field, org.eclipse.emfcloud.jackson.support.ObjectMapper oma) {
		requireNonNull(oma);
		inject(instance, field, getMapper(oma));
	}
	
	private void inject(Object instance, Field field, Object value) {
		requireNonNull(instance);
		requireNonNull(field);
		requireNonNull(value);
		try {
			field.setAccessible(true);
			field.set(instance, value);
		}
		catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	private void injectFields(Class<?> testClass, Object testInstance,
            Predicate<Field> predicate) {

        Arrays.asList(testClass.getDeclaredFields()).stream().
        	filter(f->f.getType() == ObjectMapper.class).
        	forEach(field->{
        		org.eclipse.emfcloud.jackson.support.ObjectMapper oma = field.getAnnotation(org.eclipse.emfcloud.jackson.support.ObjectMapper.class);
        		if (nonNull(oma)) {
        			injectObjectMapper(testInstance, field, oma);
        		}
        	});
        Arrays.asList(testClass.getDeclaredFields()).stream().
    		filter(f->f.getType() == org.eclipse.emf.ecore.resource.ResourceSet.class).
    		forEach(field->{
    			ResourceSet rsa = field.getAnnotation(ResourceSet.class);
    			if (nonNull(rsa)) {
    				inject(testInstance, field, resourceSet);
                }
        	});
    }

	/* 
	 * (non-Javadoc)
	 * @see org.junit.jupiter.api.extension.BeforeAllCallback#beforeAll(org.junit.jupiter.api.extension.ExtensionContext)
	 */
	@Override
	public void beforeAll(ExtensionContext context) throws Exception {
	}
}
