/**
 */
package org.eclipse.fennec.lorawan.uplink2.model.lorawan2.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.fennec.lorawan.uplink2.model.lorawan2.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class Lorawan2FactoryImpl extends EFactoryImpl implements Lorawan2Factory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static Lorawan2Factory init() {
		try {
			Lorawan2Factory theLorawan2Factory = (Lorawan2Factory)EPackage.Registry.INSTANCE.getEFactory(Lorawan2Package.eNS_URI);
			if (theLorawan2Factory != null) {
				return theLorawan2Factory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new Lorawan2FactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Lorawan2FactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case Lorawan2Package.DEVICE_INFO: return createDeviceInfo();
			case Lorawan2Package.TAGS: return createTags();
			case Lorawan2Package.RX_INFO: return createRxInfo();
			case Lorawan2Package.LOCATION: return createLocation();
			case Lorawan2Package.METADATA: return createMetadata();
			case Lorawan2Package.TX_INFO: return createTxInfo();
			case Lorawan2Package.LORA_INFO: return createLoraInfo();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DeviceInfo createDeviceInfo() {
		DeviceInfoImpl deviceInfo = new DeviceInfoImpl();
		return deviceInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Tags createTags() {
		TagsImpl tags = new TagsImpl();
		return tags;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public RxInfo createRxInfo() {
		RxInfoImpl rxInfo = new RxInfoImpl();
		return rxInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Location createLocation() {
		LocationImpl location = new LocationImpl();
		return location;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Metadata createMetadata() {
		MetadataImpl metadata = new MetadataImpl();
		return metadata;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TxInfo createTxInfo() {
		TxInfoImpl txInfo = new TxInfoImpl();
		return txInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LoraInfo createLoraInfo() {
		LoraInfoImpl loraInfo = new LoraInfoImpl();
		return loraInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Lorawan2Package getLorawan2Package() {
		return (Lorawan2Package)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static Lorawan2Package getPackage() {
		return Lorawan2Package.eINSTANCE;
	}

} //Lorawan2FactoryImpl
