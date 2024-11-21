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
package org.gecko.codec.jpa.punit.configurator.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.gecko.codec.jpa.punit.configurator.EntityManagerFactoryConfigurator;
import org.gecko.emf.osgi.configurator.EPackageConfigurator;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

import jakarta.persistence.EntityManagerFactory;

/**
 * 
 * @author ilenia
 * @since Nov 20, 2024
 */
//@Component(immediate = true, name = "EntityManagerFactoryConfigurator", service = EntityManagerFactoryConfigurator.class)
public class EntityManagerFactoryConfiguratorImpl extends HashMap<String, Object> implements EntityManagerFactoryConfigurator {

	
	ConfigurationAdmin configAdmin;
	
	/** serialVersionUID */
	private static final long serialVersionUID = -1484953952647371964L;

	private transient List<EPackageConfigurator> list = new ArrayList<>();
	private final ReadWriteLock ePackageLock = new ReentrantReadWriteLock();
	private final ReadWriteLock entityManagerFactoryLock = new ReentrantReadWriteLock();

	private Map<String, EntityManagerFactory> entityManagerFactoryMap = new ConcurrentHashMap<>();
	
	@Activate
	public EntityManagerFactoryConfiguratorImpl(@Reference ConfigurationAdmin configAdmin) {
		this.configAdmin = configAdmin;
		System.out.println("EntityManagerFactoryConfigurator is active!");
		createEntityManagerFactory(EcorePackage.eINSTANCE);
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.HashMap#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public Object put(String uri, Object value) {
		if (value instanceof EPackage) {
			EPackage ePackage = (EPackage) value;
			createEntityManagerFactory(ePackage);
		}
		return null;
	}

	@Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC, policyOption = ReferencePolicyOption.GREEDY)
	public void bindEPackageConfigurator(EPackageConfigurator configurator) {
		ePackageLock.writeLock().lock();
		try {
			list.add(configurator);
			refresh();
		} finally {
			ePackageLock.writeLock().unlock();
		}
	}

	private synchronized void refresh() {
		list.forEach(c -> c.configureEPackage(this));
	}

	public void unbindEPackageConfigurator(EPackageConfigurator configurator) {
		ePackageLock.writeLock().lock();
		try {
			list.remove(configurator);
			configurator.unconfigureEPackage(this);
			refresh();
		} finally {
			ePackageLock.writeLock().unlock();
		}
	}
	
	@Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC, policyOption = ReferencePolicyOption.GREEDY)
	public void bindEntityManagerFactory(EntityManagerFactory entityManagerFactory, Dictionary<String, Object> properties) {
		entityManagerFactoryLock.writeLock().lock();
		try {
			entityManagerFactoryMap.put((String)properties.get("gemini.jpa.punit.name"), entityManagerFactory);
		} finally {
			entityManagerFactoryLock.writeLock().unlock();
		}
	}
	
	public void unbindEntityManagerFactory(EntityManagerFactory entityManagerFactory, Dictionary<String, Object> properties) {
		entityManagerFactoryLock.writeLock().lock();
		try {
			entityManagerFactoryMap.remove((String)properties.get("gemini.jpa.punit.name"));
		} finally {
			entityManagerFactoryLock.writeLock().unlock();
		}
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jpa.punit.configurator.EntityManagerFactoryConfigurator#getEntityManagerForEPakcage(java.lang.String)
	 */
	@Override
	public EntityManagerFactory getEntityManagerForEPakcage(String ePackageURI) {
		return entityManagerFactoryMap.getOrDefault(ePackageURI, null);
	}
	/**
	 * @param ePackage
	 */
	private void createEntityManagerFactory(EPackage ePackage) {
		try {
			Configuration config = configAdmin.getFactoryConfiguration("gemini.jpa.punit", ePackage.getName(), "?");
			Dictionary<String, Object> properties = new Hashtable<>();
			properties.put("gemini.jpa.punit.name", ePackage.getName());
			String ePackagePackage = ePackage.getClass().getPackageName();
			if(ePackagePackage.endsWith(".impl")) {
				ePackagePackage = ePackagePackage.replace(".impl", "");
			}
//			if(ePackagePackage.endsWith(ePackage.getName())) {
//				ePackagePackage = ePackagePackage.replace("." + ePackage.getName(), "");
//			}
			properties.put("gemini.jpa.punit.bsn", ePackagePackage);
			properties.put("jakarta.persistence.jdbc.driver", "org.apache.derby.jdbc.EmbeddedDriver");
			properties.put("jakarta.persistence.jdbc.url", "jdbc:derby:Employee;create=true");
			properties.put("jakarta.persistence.jdbc.user", "app");
			properties.put("jakarta.persistence.jdbc.password", "app");
			properties.put("eclipselink.target-database", "Derby");
			properties.put("eclipselink.logging.level", "FINE");
			properties.put("eclipselink.logging.timestamp", "false");
			properties.put("eclipselink.logging.thread", "false");
			properties.put("eclipselink.logging.exceptions", "true");
			properties.put("eclipselink.orm.throw.exceptions", "true");
			properties.put("eclipselink.jdbc.read-connections.min", "1");
			properties.put("eclipselink.jdbc.write-connections.min", "1");
			properties.put("eclipselink.ddl-generation", "drop-and-create-tables");
			properties.put("eclipselink.weaving", "true");
			config.update(properties);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}


	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecore.EPackage.Registry#getEPackage(java.lang.String)
	 */
	@Override
	public EPackage getEPackage(String nsURI) {
		throw new UnsupportedOperationException("This method must not be called");
	}


	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecore.EPackage.Registry#getEFactory(java.lang.String)
	 */
	@Override
	public EFactory getEFactory(String nsURI) {
		throw new UnsupportedOperationException("This method must not be called");
	}

}
