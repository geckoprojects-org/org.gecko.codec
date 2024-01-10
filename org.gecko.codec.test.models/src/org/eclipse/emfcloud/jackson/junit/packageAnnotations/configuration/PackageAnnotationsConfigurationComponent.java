/*
 */
package org.eclipse.emfcloud.jackson.junit.packageAnnotations.configuration;

import java.util.Hashtable;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emfcloud.jackson.junit.packageAnnotations.PackageAnnotationsFactory;
import org.eclipse.emfcloud.jackson.junit.packageAnnotations.PackageAnnotationsPackage;

import org.eclipse.emfcloud.jackson.junit.packageAnnotations.impl.PackageAnnotationsPackageImpl;

import org.gecko.emf.osgi.configurator.EPackageConfigurator;

import org.osgi.annotation.bundle.Capability;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

import org.osgi.service.condition.Condition;
/**
 * The <b>PackageConfiguration</b> for the model.
 * The package will be registered into a OSGi base model registry.
 * 
 * @generated
 */
@Component(name = "PackageAnnotationsConfigurator")
@Capability( namespace = "osgi.service", attribute = { "objectClass:List<String>=\"org.eclipse.emfcloud.jackson.junit.packageAnnotations.util.PackageAnnotationsResourceFactoryImpl, org.eclipse.emf.ecore.resource.Resource.Factory\"" , "uses:=\"org.eclipse.emf.ecore.resource,org.eclipse.emfcloud.jackson.junit.packageAnnotations.util\"" })
@Capability( namespace = "osgi.service", attribute = { "objectClass:List<String>=\"org.eclipse.emfcloud.jackson.junit.packageAnnotations.PackageAnnotationsFactory, org.eclipse.emf.ecore.EFactory\"" , "uses:=\"org.eclipse.emf.ecore,org.eclipse.emfcloud.jackson.junit.packageAnnotations\"" })
@Capability( namespace = "osgi.service", attribute = { "objectClass:List<String>=\"org.eclipse.emfcloud.jackson.junit.packageAnnotations.PackageAnnotationsPackage, org.eclipse.emf.ecore.EPackage\"" , "uses:=\"org.eclipse.emf.ecore,org.eclipse.emfcloud.jackson.junit.packageAnnotations\"" })
@Capability( namespace = "osgi.service", attribute = { "objectClass:List<String>=\"org.gecko.emf.osgi.configurator.EPackageConfigurator\"" , "uses:=\"org.eclipse.emf.ecore,org.eclipse.emfcloud.jackson.junit.packageAnnotations\"" })
@Capability( namespace = "osgi.service", attribute = { "objectClass:List<String>=\"org.osgi.service.condition.Condition\"" , "uses:=org.osgi.service.condition" })
public class PackageAnnotationsConfigurationComponent {
	
	private ServiceRegistration<?> packageRegistration = null;
	private ServiceRegistration<EPackageConfigurator> ePackageConfiguratorRegistration = null;
	private ServiceRegistration<?> eFactoryRegistration = null;
	private ServiceRegistration<?> conditionRegistration = null;

	/**
	 * Activates the Configuration Component.
	 *
	 * @generated
	 */
	@Activate
	public void activate(BundleContext ctx) {
		PackageAnnotationsPackage ePackage = PackageAnnotationsPackageImpl.eINSTANCE;
		
		PackageAnnotationsEPackageConfigurator packageConfigurator = registerEPackageConfiguratorService(ePackage, ctx);
		registerEPackageService(ePackage, packageConfigurator, ctx);
		registerEFactoryService(ePackage, packageConfigurator, ctx);
		registerConditionService(packageConfigurator, ctx);
	}
	
	/**
	 * Registers the PackageAnnotationsEPackageConfigurator as a service.
	 *
	 * @generated
	 */
	private PackageAnnotationsEPackageConfigurator registerEPackageConfiguratorService(PackageAnnotationsPackage ePackage, BundleContext ctx){
		PackageAnnotationsEPackageConfigurator packageConfigurator = new PackageAnnotationsEPackageConfigurator(ePackage);
		// register the EPackageConfigurator
		Hashtable<String, Object> properties = new Hashtable<String, Object>();
		properties.putAll(packageConfigurator.getServiceProperties());
		ePackageConfiguratorRegistration = ctx.registerService(EPackageConfigurator.class, packageConfigurator, properties);

		return packageConfigurator;
	}


	/**
	 * Registers the PackageAnnotationsPackage as a service.
	 *
	 * @generated
	 */
	private void registerEPackageService(PackageAnnotationsPackage ePackage, PackageAnnotationsEPackageConfigurator packageConfigurator, BundleContext ctx){
		Hashtable<String, Object> properties = new Hashtable<String, Object>();
		properties.putAll(packageConfigurator.getServiceProperties());
		String[] serviceClasses = new String[] {PackageAnnotationsPackage.class.getName(), EPackage.class.getName()};
		packageRegistration = ctx.registerService(serviceClasses, ePackage, properties);
	}

	/**
	 * Registers the PackageAnnotationsFactory as a service.
	 *
	 * @generated
	 */
	private void registerEFactoryService(PackageAnnotationsPackage ePackage, PackageAnnotationsEPackageConfigurator packageConfigurator, BundleContext ctx){
		Hashtable<String, Object> properties = new Hashtable<String, Object>();
		properties.putAll(packageConfigurator.getServiceProperties());
		String[] serviceClasses = new String[] {PackageAnnotationsFactory.class.getName(), EFactory.class.getName()};
		eFactoryRegistration = ctx.registerService(serviceClasses, ePackage.getPackageAnnotationsFactory(), properties);
	}

	private void registerConditionService(PackageAnnotationsEPackageConfigurator packageConfigurator, BundleContext ctx){
		// register the EPackage
		Hashtable<String, Object> properties = new Hashtable<String, Object>();
		properties.putAll(packageConfigurator.getServiceProperties());
		properties.put(Condition.CONDITION_ID, PackageAnnotationsPackage.eNS_URI);
		conditionRegistration = ctx.registerService(Condition.class, Condition.INSTANCE, properties);
	}

	/**
	 * Deactivates and unregisters everything.
	 *
	 * @generated
	 */
	@Deactivate
	public void deactivate() {
		conditionRegistration.unregister();
		eFactoryRegistration.unregister();
		packageRegistration.unregister();

		ePackageConfiguratorRegistration.unregister();
		EPackage.Registry.INSTANCE.remove(PackageAnnotationsPackage.eNS_URI);
	}
}
