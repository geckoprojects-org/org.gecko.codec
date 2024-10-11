/**
 * Copyright (c) 2012 - 2024 Data In Motion and others.
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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;
import org.eclipse.emfcloud.jackson.module.EMFModule;
import org.eclipse.emfcloud.jackson.resource.JsonResource;
import org.osgi.service.component.annotations.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author ilenia
 * @since Oct 9, 2024
 */
@Component(service = {Resource.Factory.class, TestJsonRF.class}, configurationPid = "TestJsonRF")
public class TestJsonRF extends ResourceFactoryImpl {

  private final ObjectMapper mapper;

  public TestJsonRF() {
     this.mapper = EMFModule.setupDefaultMapper();
  }

  public TestJsonRF(final ObjectMapper mapper) {
     if (mapper == null) {
        throw new IllegalArgumentException();
     }
     this.mapper = mapper;
  }

  @Override
  public Resource createResource(final URI uri) {
     return new JsonResource(uri, mapper);
  }

  public ObjectMapper getMapper() { return mapper; }
}
