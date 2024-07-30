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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.osgi.framework.BundleContext;
import org.osgi.test.common.annotation.InjectBundleContext;
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
public class ExampleTest {
	
//	  private ServiceRegistration<EMFRepository> repoRegistration;
//	  
//	  @BeforeEach
//	  public void before(@InjectBundleContext BundleContext ctx, @Mock
//	                     EMFRepository repoMock) {
//	                       repoRegistration = ctx.registerService(EMFRepository.class, new PrototypeServiceFactory<EMFRepository>()
//	                       {
//
//	                         @Override
//	                         public EMFRepository getService(Bundle bundle,
//	                                                         ServiceRegistration<EMFRepository> registration)
//	                         {
//	                           return repoMock;
//	                         }
//
//	                         @Override
//	                         public void ungetService(Bundle bundle,
//	                                                  ServiceRegistration<EMFRepository> registration,
//	                                                  EMFRepository service)
//	                         {
//	                         }
//	                       }, Dictionaries.dictionaryOf(Constants.SERVICE_RANKING, Integer.valueOf(1000)));
//	  }
//
//	  @AfterEach
//	  public void after() {
//	    repoRegistration.unregister();
//	  }
	
//	 @Test
//	  public void testLoadGroup(@InjectService EMFRepository repo, @InjectService GroupsService groupService, @InjectService ResourceSet rs) {
//	    assertNotNull(repo);
//	    assertNotNull(groupService);
//	    assertThrows(NullPointerException.class, ()->groupService.loadGroup(null));
//	    verify(repo, never()).getEObject(any(EClass.class), any(Object.class));
//
//	    Group g = groupService.loadGroup("test");
//	    assertNull(g);
//	    verify(repo, times(1)).getEObject(any(EClass.class), eq("test"));
//
//	    reset(repo);
//	    Group dbg = createGroup(rs, "test");
//	    dbg.setId("myGroup");
//	    when(repo.getEObject(RuntimePackage.Literals.GROUP, "test")).thenReturn(dbg);
//	    g = groupService.loadGroup("test");
//	    assertNotNull(g);
//	    assertEquals(dbg.getId(), g.getId());
//	    verify(repo, times(1)).getEObject(eq(RuntimePackage.Literals.GROUP), eq("test"));
//	  }
	
	@Test
	public void test() {
//		fail("Not yet implemented");
	}

}
