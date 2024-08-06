package org.gecko.codec.info.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.gecko.codec.info.CodecAnnotations;
import org.gecko.codec.info.CodecModelInfo;
import org.gecko.codec.info.codecinfo.CodecInfoFactory;
import org.gecko.codec.info.codecinfo.CodecInfoHolder;
import org.gecko.codec.info.codecinfo.EClassCodecInfo;
import org.gecko.codec.info.codecinfo.FeatureCodecInfo;
import org.gecko.codec.info.codecinfo.IdentityInfo;
import org.gecko.codec.info.codecinfo.InfoType;
import org.gecko.codec.info.codecinfo.PackageCodecInfo;
import org.gecko.codec.info.codecinfo.ReferenceInfo;
import org.gecko.codec.info.codecinfo.TypeInfo;
import org.gecko.codec.info.helper.CodecInfoHolderHelper;
import org.gecko.emf.osgi.configurator.EPackageConfigurator;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

@Component(service = CodecModelInfo.class)
public class CodecModelInfoImpl extends HashMap<String, Object> implements CodecModelInfo {

	/** serialVersionUID */
	private static final long serialVersionUID = 7749336016374647599L;
	
	private static final String CODEC_MODEL_ANNOTATION = "codec";

	private transient Map<Class<?>, EClassifier> classes = new ConcurrentHashMap<>();
	private transient Map<EClass, List<EClass>> upperHierarchy = new ConcurrentHashMap<>();
	private transient Map<EClass, List<EClass>> needsRevisiting = new ConcurrentHashMap<>();
	private transient List<EPackageConfigurator> list = new ArrayList<>();
	
	private Map<String, PackageCodecInfo> ePackageCodecInfoMap = new HashMap<>();
	private Map<InfoType, CodecInfoHolder> codecInfoHolderMap = new HashMap<>();
	
	private final ReadWriteLock lock = new ReentrantReadWriteLock();


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.gecko.emf.osgi.model.info.EMFModelInfo#getEClassifierForClass(java.
	 * lang.Class)
	 */
	@Override
	public Optional<EClassifier> getEClassifierForClass(Class<?> clazz) {
		return classes.entrySet().stream().filter(e -> e.getKey().equals(clazz)).map(Entry::getValue).findFirst();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.gecko.emf.osgi.model.info.EMFModelInfo#getEClassifierForClass(java.
	 * lang.String)
	 */
	@Override
	public Optional<EClassifier> getEClassifierForClass(String fullQualifiedClassName) {
		lock.readLock().lock();
		try {
			return classes.entrySet().stream().filter(e -> e.getKey().getName().equals(fullQualifiedClassName))
					.map(Entry::getValue).findFirst();
		} finally {
			lock.readLock().unlock();
		}
	}

	@Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC, policyOption = ReferencePolicyOption.GREEDY)
	public void bindEPackageConfigurator(EPackageConfigurator configurator) {
		lock.writeLock().lock();
		try {
			list.add(configurator);
			refresh();
		} finally {
			lock.writeLock().unlock();
		}
	}

	private synchronized void refresh() {
		classes = new ConcurrentHashMap<>();
		upperHierarchy = new ConcurrentHashMap<>();
		needsRevisiting = new ConcurrentHashMap<>();

		list.forEach(c -> c.configureEPackage(this));

	}

	public void unbindEPackageConfigurator(EPackageConfigurator configurator) {
		lock.writeLock().lock();
		try {
			list.remove(configurator);
			configurator.unconfigureEPackage(this);
			refresh();
		} finally {
			lock.writeLock().unlock();
		}
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
			addEClassesOfEPackage(ePackage);
		}
		return null;
	}

//	private synchronized void addEClassesOfEPackage(EPackage ePackage) {
//		ePackage.getEClassifiers().stream().filter(ec -> ec.getInstanceClass() != null).forEach(ec -> {
//			analyseHierarchy(ec);
//			Class<?> instanceClass = ec.getInstanceClass();
//			if (instanceClass != DynamicEObjectImpl.class) {
//				classes.put(instanceClass, ec);
//			}
//		});
//	}
	
	private synchronized void addEClassesOfEPackage(EPackage ePackage) {
		
		PackageCodecInfo packageInfo = createCodecInfo(ePackage);
		ePackageCodecInfoMap.put(ePackage.getNsURI(), packageInfo);

		createCodecInfoHolderMap();
		
		ePackage.getEClassifiers().stream().filter(ec -> ec.getInstanceClass() != null).forEach(ec -> {
			analyseHierarchy(ec);
			Class<?> instanceClass = ec.getInstanceClass();
			if (instanceClass != DynamicEObjectImpl.class) {
				classes.put(instanceClass, ec);
			}
		});
	}
	
	private PackageCodecInfo createCodecInfo(EPackage ePackage) {
		PackageCodecInfo ePackageCodecInfo = CodecInfoFactory.eINSTANCE.createPackageCodecInfo();
		ePackageCodecInfo.setId(ePackage.getNsURI());
		ePackageCodecInfo.setEPackage(ePackage);
		for(EPackage subPackage : ePackage.getESubpackages()) {
			ePackageCodecInfo.getSubPackageCodecInfo().add(createCodecInfo(subPackage));
		}
		ePackage.getEClassifiers().stream().filter(ec -> ec.getInstanceClass() != null).forEach(ec -> {
			ePackageCodecInfo.getEClassCodecInfo().add(createCodecEClassInfo(ec));
		});
		return ePackageCodecInfo;
	}

	private EClassCodecInfo createCodecEClassInfo(EClassifier ec) {
		EClassCodecInfo eClassCodecInfo = CodecInfoFactory.eINSTANCE.createEClassCodecInfo();
		eClassCodecInfo.setId(ec.getInstanceClassName());
		eClassCodecInfo.setClassifier(ec);
		
		IdentityInfo identityInfo = CodecInfoFactory.eINSTANCE.createIdentityInfo();
		identityInfo.setType(InfoType.IDENTITY);
		identityInfo.setId(UUID.randomUUID().toString());
		eClassCodecInfo.setIdentityInfo(identityInfo);
		
		Object valueReaderName = getAnnotationDetails(ec, CodecAnnotations.CODEC_VALUE_READER_NAME);
		if(valueReaderName != null && valueReaderName instanceof String name) {
			identityInfo.setValueReaderName(name);
		}
		else identityInfo.setValueReaderName("DEFAULT_ID_READER");
		
		Object valueWriterName = getAnnotationDetails(ec, CodecAnnotations.CODEC_VALUE_WRITER_NAME);
		if(valueWriterName != null && valueWriterName instanceof String name) {
			identityInfo.setValueWriterName(name);
		}
		else identityInfo.setValueWriterName("DEFAULT_ID_WRITER");		
		
		Object identityStrategy = getAnnotationDetails(ec, CodecAnnotations.CODEC_ID_STRATEGY);
		if(identityStrategy != null) {
			identityInfo.setIdStrategy((String) identityStrategy);
		}
		Object identitySeparator = getAnnotationDetails(ec, CodecAnnotations.CODEC_ID_SEPARATOR);
		if(identitySeparator != null && identitySeparator instanceof String separator) {
			identityInfo.setIdSeparator(separator);
		}
		
		if(ec instanceof EClass eClass) {
			eClass.getEAllAttributes().forEach(att -> 
				eClassCodecInfo.getFeatureInfo().add(createCodecFeatureInfo(att, eClassCodecInfo)));
		}
	
		
		
		TypeInfo typeInfo = CodecInfoFactory.eINSTANCE.createTypeInfo();
		typeInfo.setId(UUID.randomUUID().toString());
		typeInfo.setType(InfoType.TYPE);
		typeInfo.setValueReaderName("DEFAULT_ECLASS_READER");
		typeInfo.setValueWriterName("URI_WRITER");
		Object typeStrategy = getAnnotationDetails(ec, CodecAnnotations.CODEC_TYPE_STRATEGY);
		if(typeStrategy != null && typeStrategy instanceof String strategy) {
			typeInfo.setTypeStrategy(strategy);
		}
		Object typeValue = getAnnotationDetails(ec, CodecAnnotations.CODEC_TYPE_VALUE);
		if(typeValue != null && typeValue instanceof String value) {
			typeInfo.setTypeValue(value);
		}
//		TODO: what should I put in the features list...?
		eClassCodecInfo.setTypeInfo(typeInfo);
		
		
		ReferenceInfo refInfo = CodecInfoFactory.eINSTANCE.createReferenceInfo();
		refInfo.setId(UUID.randomUUID().toString());
		refInfo.setType(InfoType.REFERENCE);
		refInfo.setValueReaderName("DEFAULT_ECLASS_READER");
		refInfo.setValueWriterName("URIS_WRITER");
		if(ec instanceof EClass eClass) {
			eClass.getEAllReferences().forEach(ref -> refInfo.getFeatures().add(ref));
		}
		eClassCodecInfo.setReferenceInfo(refInfo);
		
		return eClassCodecInfo;
	}
	
	/**
	 * @param att
	 * @return
	 */
	private FeatureCodecInfo createCodecFeatureInfo(EAttribute att, EClassCodecInfo eClassCodecInfo) {
		FeatureCodecInfo featureInfo = CodecInfoFactory.eINSTANCE.createFeatureCodecInfo();
		featureInfo.setId(UUID.randomUUID().toString());
		
//		Retrieve id info from model annotations
		Object idField = getAnnotationDetails(att, CodecAnnotations.CODEC_ID_FIELD);
		if(idField != null) {
			Boolean isIdField = Boolean.valueOf((String) idField);
			
			Object idOrder = getAnnotationDetails(att, CodecAnnotations.CODEC_ID_ORDER);
			if(idOrder != null) {
				Integer order = Integer.valueOf((String) idOrder);
				eClassCodecInfo.getIdentityInfo().getFeatures().add(order, att);
			} else eClassCodecInfo.getIdentityInfo().getFeatures().add(att);
		}
		
//		Retrieve id info from model properties
		if(att.isID()) {
			eClassCodecInfo.getIdentityInfo().getFeatures().add(att);
		}
		
//		Set value reader/writer from annotation
		Object valueReaderName = getAnnotationDetails(att, CodecAnnotations.CODEC_VALUE_READER_NAME);
		if(valueReaderName != null && valueReaderName instanceof String name) {
			featureInfo.setValueReaderName(name);
		}
		
		Object valueWriterName = getAnnotationDetails(att, CodecAnnotations.CODEC_VALUE_WRITER_NAME);
		if(valueWriterName != null && valueWriterName instanceof String name) {
			featureInfo.setValueWriterName(name);
		}
		
//		Type info
		
		
		return featureInfo;
	}

	private Object getAnnotationDetails(EModelElement element, String annotationKey) {
		EAnnotation annotation = element.getEAnnotation(CODEC_MODEL_ANNOTATION);
		if(annotation != null) return annotation.getDetails().get(annotationKey);
		return null;
	}
	
	private void createCodecInfoHolderMap() {
		for(InfoType type : InfoType.values()) {
			if(!codecInfoHolderMap.containsKey(type)) {
				codecInfoHolderMap.put(type, CodecInfoHolderHelper.createCodecInfoHolderForType(type));
			}
		}
	}
	
	/**
	 * Here we create the {@link TypeInfo} instance ... 
	 * @param ec EClass to analyze the Hierarchy for
	 */
	private void analyseHierarchy(EClassifier ec) {
		if (!(ec instanceof EClass) || ec.getEPackage().equals(EcorePackage.eINSTANCE)) {
			return;
		}
		EClass eClass = (EClass) ec;
		List<EClass> thisHierarchy = needsRevisiting.remove(eClass);
		if (thisHierarchy == null) {
			thisHierarchy = Collections.synchronizedList(new LinkedList<EClass>());
		}
		upperHierarchy.put(eClass, thisHierarchy);
		eClass.getEAllSuperTypes().forEach(superEClass -> {
			if (superEClass.equals(EcorePackage.Literals.ECLASS)) {
				return;
			}
			if (upperHierarchy.containsKey(superEClass)) {
				List<EClass> hierarchy = upperHierarchy.get(superEClass);
				if (!hierarchy.contains(superEClass)) {
					hierarchy.add(eClass);
				}
			} else {
				List<EClass> otherHierachy = needsRevisiting.getOrDefault(superEClass,
						Collections.synchronizedList(new LinkedList<EClass>()));
				otherHierachy.add(eClass);
				needsRevisiting.put(superEClass, otherHierachy);
			}
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.ecore.EPackage.Registry#getEFactory(java.lang.String)
	 */
	@Override
	public EFactory getEFactory(String uri) {
		throw new UnsupportedOperationException("This method must not be called");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.ecore.EPackage.Registry#getEPackage(java.lang.String)
	 */
	@Override
	public EPackage getEPackage(String nsUri) {
		throw new UnsupportedOperationException("This method must not be called");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.gecko.emf.osgi.model.info.EMFModelInfo#getUpperTypeHierarchyForEClass(
	 * org.eclipse.emf.ecore.EClass)
	 */
	@Override
	public List<EClass> getUpperTypeHierarchyForEClass(EClass eClass) {
		lock.readLock().lock();
		try {
			if (!upperHierarchy.containsKey(eClass)) {
				return Collections.emptyList();
			}
			
			return upperHierarchy.get(eClass);
		} finally {
			lock.readLock().unlock();
		}
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.info.CodecModelInfo#getCodecInfoForPackage(java.lang.String)
	 */
	@Override
	public Optional<PackageCodecInfo> getCodecInfoForPackage(String uri) {
		return Optional.ofNullable(ePackageCodecInfoMap.getOrDefault(uri, null))
				.map(ci -> (PackageCodecInfo) EcoreUtil.copy((EObject) ci));	
	}

	
	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.info.CodecModelInfo#getCodecInfoForEClass(org.eclipse.emf.ecore.EClass)
	 */
	@Override
	public Optional<EClassCodecInfo> getCodecInfoForEClass(EClass eClass) {		
		PackageCodecInfo ePackageCodecInfo = ePackageCodecInfoMap.getOrDefault(eClass.getEPackage().getNsURI(), null);
		if(ePackageCodecInfo == null) return Optional.empty();
		return ePackageCodecInfo.getEClassCodecInfo().stream().filter(ecci -> ecci.getId() == eClass.getInstanceClassName())
				.findFirst().map(ecci -> (EClassCodecInfo) EcoreUtil.copy((EObject) ecci));		
	}


	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.info.CodecModelInfo#getCodecInfoHolderByType(org.gecko.codec.info.codecinfo.InfoType)
	 */
	@Override
	public Optional<CodecInfoHolder> getCodecInfoHolderByType(InfoType infoType) {
		return Optional.ofNullable(codecInfoHolderMap.getOrDefault(infoType, null));	
	}
}