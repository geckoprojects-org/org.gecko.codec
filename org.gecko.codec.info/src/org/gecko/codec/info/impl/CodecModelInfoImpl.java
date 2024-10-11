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
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.gecko.codec.info.CodecAnnotations;
import org.gecko.codec.info.CodecModelInfo;
import org.gecko.codec.info.codecinfo.CodecInfoFactory;
import org.gecko.codec.info.codecinfo.CodecInfoHolder;
import org.gecko.codec.info.codecinfo.CodecValueReader;
import org.gecko.codec.info.codecinfo.CodecValueWriter;
import org.gecko.codec.info.codecinfo.EClassCodecInfo;
import org.gecko.codec.info.codecinfo.FeatureCodecInfo;
import org.gecko.codec.info.codecinfo.IdentityInfo;
import org.gecko.codec.info.codecinfo.InfoType;
import org.gecko.codec.info.codecinfo.PackageCodecInfo;
import org.gecko.codec.info.codecinfo.SuperTypeInfo;
import org.gecko.codec.info.codecinfo.TypeInfo;
import org.gecko.codec.info.helper.CodecInfoHolderHelper;
import org.gecko.emf.osgi.configurator.EPackageConfigurator;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

@Component(name = "CodecModelInfoService", service = CodecModelInfo.class)
public class CodecModelInfoImpl extends HashMap<String, Object> implements CodecModelInfo {

	/** serialVersionUID */
	private static final long serialVersionUID = 7749336016374647599L;

	private transient Map<Class<?>, EClassifier> classes = new ConcurrentHashMap<>();
	private transient Map<EClass, List<EClass>> upperHierarchy = new ConcurrentHashMap<>();
	private transient Map<EClass, List<EClass>> needsRevisiting = new ConcurrentHashMap<>();
	private transient List<EPackageConfigurator> list = new ArrayList<>();

	private Map<String, PackageCodecInfo> ePackageCodecInfoMap = new HashMap<>();
	private Map<InfoType, CodecInfoHolder> codecInfoHolderMap = new HashMap<>();

	private final ReadWriteLock lock = new ReentrantReadWriteLock();
	
	@Activate
	public void activate() {
		PackageCodecInfo packageInfo = createCodecInfo(EcorePackage.eINSTANCE);
		ePackageCodecInfoMap.put(EcorePackage.eINSTANCE.getNsURI(), packageInfo);
	}


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


		String valueReaderName = getAnnotationDetails(ec, "codec", CodecAnnotations.CODEC_ID_VALUE_READER_NAME);
		if(valueReaderName != null) identityInfo.setValueReaderName(valueReaderName);

		String valueWriterName = getAnnotationDetails(ec, "codec", CodecAnnotations.CODEC_ID_VALUE_WRITER_NAME);
		if(valueWriterName != null) identityInfo.setValueWriterName(valueWriterName);	

		String identityStrategy = getAnnotationDetails(ec, "codec.id", "strategy", true);
		if(identityStrategy != null) identityInfo.setIdStrategy(identityStrategy);
		else identityInfo.setIdStrategy("ID_FIELD");

		String identitySeparator = getAnnotationDetails(ec, "codec.id", "separator", true);
		if(identitySeparator != null) identityInfo.setIdSeparator(identitySeparator);

		if(ec instanceof EClass eClass) {
			eClass.getEAllAttributes().forEach(att -> 
				eClassCodecInfo.getFeatureInfo().add(createCodecFeatureInfo(att, eClassCodecInfo)));
			eClass.getEAllReferences().forEach(ref -> 				
				eClassCodecInfo.getFeatureInfo().add(createCodecFeatureInfo(ref, eClassCodecInfo)));
			eClass.getEAllOperations().forEach(op -> {
				eClassCodecInfo.getFeatureInfo().add(createCodecFeatureInfo(op, eClassCodecInfo));
			});
		};

		TypeInfo typeInfo = CodecInfoFactory.eINSTANCE.createTypeInfo();
		typeInfo.setId(UUID.randomUUID().toString());
		typeInfo.setType(InfoType.TYPE);		
		String typeValue = getAnnotationDetails(ec, "codec.type", "include", true);
		if(typeValue != null && "false".equalsIgnoreCase(typeValue)) {
			typeInfo.setIgnoreType(true);
		}
		if(!typeInfo.isIgnoreType()) {
			String typeStrategy = getAnnotationDetails(ec, "codec.type", "use", true);
			if(typeStrategy != null) {
				typeInfo.setTypeStrategy(typeStrategy);
				switch(typeStrategy) {
				case "NAME":
					typeInfo.setValueWriterName("WRITE_BY_NAME");
					typeInfo.setValueReaderName("READ_BY_NAME");
					break;
				case "CLASS":
					typeInfo.setValueWriterName("WRITE_BY_CLASS_NAME");
					typeInfo.setValueReaderName("READ_BY_CLASS");
					break;
				case "URI": default:
					typeInfo.setValueWriterName("URI_WRITER");
					typeInfo.setValueReaderName("DEFAULT_ECLASS_READER");
					break;				
				}
			} else {
				valueReaderName = getAnnotationDetails(ec, "codec", CodecAnnotations.CODEC_TYPE_VALUE_READER_NAME);
				valueWriterName = getAnnotationDetails(ec, "codec", CodecAnnotations.CODEC_TYPE_VALUE_WRITER_NAME);

				if(valueReaderName != null) {
					typeInfo.setValueReaderName(valueReaderName);
				} else {
					typeInfo.setValueReaderName("DEFAULT_ECLASS_READER");
				}

				if(valueWriterName != null) {
					typeInfo.setValueWriterName(valueWriterName);
				} else {
					typeInfo.setValueWriterName("URI_WRITER");
				}
			}
		}

		eClassCodecInfo.setTypeInfo(typeInfo);
		
		SuperTypeInfo superTypeInfo = CodecInfoFactory.eINSTANCE.createSuperTypeInfo();
		superTypeInfo.setId(UUID.randomUUID().toString());
		superTypeInfo.setType(InfoType.SUPER_TYPE);		
		String supertypeValue = getAnnotationDetails(ec, "codec.supertype", "include", true);
		if(supertypeValue != null && "false".equalsIgnoreCase(supertypeValue)) {
			superTypeInfo.setIgnoreSuperType(true);
		}
//		SUPPORT FOR DIFFERENT SUPERTYPE SERIALIZATION STRATEGIES...?
//		if(!superTypeInfo.isIgnoreSuperType()) {
//			String superTypeStrategy = getAnnotationDetails(ec, "codec.supertype", "use", true);
//			if(superTypeStrategy != null) {
//				superTypeInfo.setSuperTypeStrategy(superTypeStrategy);
//				switch(superTypeStrategy) {
//				case "ARRAY": default:
//					superTypeInfo.setValueWriterName("URIS_WRITER");
//					superTypeInfo.setValueReaderName("DEFAULT_ECLASS_READER");
//					break;				
//				}
//			} else {
//				valueReaderName = getAnnotationDetails(ec, "codec", CodecAnnotations.CODEC_SUPERTYPE_VALUE_READER_NAME);
//				valueWriterName = getAnnotationDetails(ec, "codec", CodecAnnotations.CODEC_SUPERTYPE_VALUE_WRITER_NAME);
//
//				if(valueReaderName != null) {
//					superTypeInfo.setValueReaderName(valueReaderName);
//				} else {
//					superTypeInfo.setValueReaderName("DEFAULT_ECLASS_READER");
//				}
//
//				if(valueWriterName != null) {
//					superTypeInfo.setValueWriterName(valueWriterName);
//				} else {
//					superTypeInfo.setValueWriterName("URIS_WRITER");
//				}
//			}
//		}

		eClassCodecInfo.setSuperTypeInfo(superTypeInfo);
		return eClassCodecInfo;
	}


	private FeatureCodecInfo createCodecFeatureInfo(ETypedElement feature, EClassCodecInfo eClassCodecInfo) {
		FeatureCodecInfo featureInfo = CodecInfoFactory.eINSTANCE.createFeatureCodecInfo();
		featureInfo.setId(UUID.randomUUID().toString());
		featureInfo.setType(feature instanceof EAttribute ? InfoType.ATTRIBUTE : 
			feature instanceof EOperation ? InfoType.OPERATION : InfoType.REFERENCE);
		featureInfo.setKey(getElementName(feature));
		featureInfo.getFeatures().add(feature);
		
		if(feature instanceof EStructuralFeature f && f.isTransient()) featureInfo.setIgnore(true);
		String isIgnore = getAnnotationDetails(feature, "codec", "transient");
		if("true".equalsIgnoreCase(isIgnore)) featureInfo.setIgnore(Boolean.valueOf(isIgnore));

		//		Retrieve id info from model annotations
		if("COMBINED".equals(eClassCodecInfo.getIdentityInfo().getIdStrategy())) {
			String idField = getAnnotationDetails(feature, "codec.id", "id.field");
			if(idField != null && "true".equalsIgnoreCase(idField)) {			
				String idOrder = getAnnotationDetails(feature, "codec.id", "id.order");
				if(idOrder != null) {
					Integer order = Integer.valueOf(idOrder);
					eClassCodecInfo.getIdentityInfo().getFeatures().add(order, feature);
				} else eClassCodecInfo.getIdentityInfo().getFeatures().add(feature);
			}
		} else {
			//			Retrieve id info from model properties
			if(feature instanceof EAttribute att && att.isID()) {
				eClassCodecInfo.getIdentityInfo().getFeatures().add(feature);
			}
		}		

		//		Set value reader/writer from annotation
		String valueReaderName = getAnnotationDetails(feature, "codec", CodecAnnotations.CODEC_VALUE_READER_NAME);
		if(valueReaderName != null) featureInfo.setValueReaderName(valueReaderName);
		else if(feature instanceof EReference) featureInfo.setValueReaderName("DEFAULT_ECLASS_READER");

		String valueWriterName = getAnnotationDetails(feature, "codec", CodecAnnotations.CODEC_VALUE_WRITER_NAME);
		if(valueWriterName != null) featureInfo.setValueWriterName(valueWriterName);
		else if(feature instanceof EReference) featureInfo.setValueWriterName("URIS_WRITER");

		return featureInfo;
	}

	private String getAnnotationDetails(EModelElement element, String annotationSource, String annotationKey) {
		EAnnotation annotation = element.getEAnnotation(annotationSource);
		if(annotation != null) return annotation.getDetails().get(annotationKey);
		return null;
	}

	private String getAnnotationDetails(EModelElement element, String annotationSource, String annotationKey, boolean deriveFromParent) {
		EAnnotation annotation = element.getEAnnotation(annotationSource);
		if(annotation != null) return annotation.getDetails().get(annotationKey);
		if(deriveFromParent && element instanceof EClass ec) {
			for(EClass parent : ec.getESuperTypes()) {

				if(getAnnotationDetails(element, "codec", "inherit") == null || "false".equalsIgnoreCase(getAnnotationDetails(element, "codec", "inherit"))) {
					if(!parent.getEPackage().getNsURI().equals(ec.getEPackage().getNsURI())) {
						continue;
					}
				}
				String parentAnn = getAnnotationDetails(parent, annotationSource, annotationKey);
				if(parentAnn != null) return parentAnn;
			}
		}
		return null;
	}

	private static final String EXTENDED_METADATA = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData";
	private String getElementName(final ENamedElement element) {
		String value = getAnnotationDetails(element, EXTENDED_METADATA, "name");
		if(value != null) return value;
		return element.getName();
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
	public CodecInfoHolder getCodecInfoHolderByType(InfoType infoType) {
		return codecInfoHolderMap.get(infoType);	
	}


	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.info.CodecModelInfo#addCodecValueWriterForType(org.gecko.codec.info.codecinfo.InfoType, org.gecko.codec.info.codecinfo.CodecValueWriter)
	 */
	@Override
	public void addCodecValueWriterForType(InfoType infoType, CodecValueWriter<?, ?> writer) {
		CodecInfoHolderHelper.addCodecWriter(getCodecInfoHolderByType(infoType), writer);
		codecInfoHolderMap.put(infoType, getCodecInfoHolderByType(infoType));		
	}


	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.info.CodecModelInfo#addCodecValueReaderForType(org.gecko.codec.info.codecinfo.InfoType, org.gecko.codec.info.codecinfo.CodecValueReader)
	 */
	@Override
	public void addCodecValueReaderForType(InfoType infoType, CodecValueReader<?, ?> reader) {
		CodecInfoHolderHelper.addCodecReader(getCodecInfoHolderByType(infoType), reader);
		codecInfoHolderMap.put(infoType, getCodecInfoHolderByType(infoType));	

	}
}