package org.gecko.codec.jpa.punit.configurator;

import org.eclipse.emf.ecore.EPackage;
import org.osgi.annotation.versioning.ProviderType;

import jakarta.persistence.EntityManagerFactory;


/**
 * <p>
 * This is an example of an interface that is expected to be implemented by Providers of the API. Adding methods to this
 * interface is a minor change, because only Providers will be affected.
 * </p>
 * 
 * @see ProviderType
 * @since 1.0
 */
@ProviderType
public interface EntityManagerFactoryConfigurator extends EPackage.Registry {

	EntityManagerFactory getEntityManagerForEPakcage(String ePackageURI);

}
