/**
 * Copyright (c) 2012 - 2022 Data In Motion and others.
 * All rights reserved. 
 * 
 * This program and the accompanying materials are made available under the terms of the 
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Data In Motion - initial API and implementation
 */
package org.gecko.emf.codec.helper;

import static java.util.Objects.requireNonNull;
import static org.gecko.codec.CodecConstants.ANNOTATION_NAMESPACE;
import static org.gecko.codec.CodecConstants.ANNOTATION_NAME_KEY;
import static org.gecko.codec.CodecConstants.EXTENDED_METADATA_NAME_KEY;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.ExtendedMetaData;

/**
 * Some {@link EcoreUtil} extensions
 * @author Mark Hoffmann
 * @since 14.03.2012
 */
public class EMFHelper {

	/**
	 * Sets ids for the specified {@link EObject} and all its contained {@link EObject}s.
	 * @param rootObject the {@link EObject}
	 * @return the ID assigned to the given {@link EObject} or null if the {@link EObject} has no id feature
	 */
	public static Object setIds(EObject rootObject, Supplier<Object> rootIdsupplier, Supplier<Object> containedIdSupplier) {
		String id = null;
		TreeIterator<EObject> eAllContents = rootObject.eAllContents();
		while (eAllContents.hasNext()) {
			EObject eo = eAllContents.next();

			if (eo.eClass().getEIDAttribute() != null 
					&& EcoreUtil.getID(eo) == null
					&& (
							eo.eClass().getEIDAttribute().getEType().equals(EcorePackage.Literals.ESTRING) 
							|| eo.eClass().getEIDAttribute().getEType().equals(EcorePackage.Literals.EJAVA_OBJECT
									)
							)
					) {
				EcoreUtil.setID(eo, EcoreUtil.convertToString((EDataType) rootObject.eClass().getEIDAttribute().getEType(), containedIdSupplier.get()));
			}
		}
		if (rootObject.eClass().getEIDAttribute() != null 
				&& EcoreUtil.getID(rootObject) == null 
				&& (
						rootObject.eClass().getEIDAttribute().getEType().equals(EcorePackage.Literals.ESTRING) 
						|| rootObject.eClass().getEIDAttribute().getEType().equals(EcorePackage.Literals.EJAVA_OBJECT
								)
						)
				) {
			id = EcoreUtil.convertToString((EDataType) rootObject.eClass().getEIDAttribute().getEType(), rootIdsupplier.get());
			EcoreUtil.setID(rootObject, id);
		}
		return id;
	}
	/**
	 * Sets ids for the specified {@link EObject} and all its contained {@link EObject}s.
	 * @param eObject the {@link EObject}
	 * @return the ID assigned to the given {@link EObject} or null if the {@link EObject} has no id feature
	 */
	public static Object setIds(EObject eObject) {
		return setIds(eObject, () -> UUID.randomUUID().toString(), () -> UUID.randomUUID().toString()); 
	}

	/**
	 * Retrieves the name of the {@link EClass} that should be used as eclass segment in the {@link URI}.
	 *
	 * By default this is the {@link EClass#getName()}. If anywhere in the {@link EClass} or on off its super classes the annotation <code>UriHint</code> is found, a different name will be chosen.
	 * At first a possibly named reference will be used. This can reference to any {@link EClass}, even if it is not a super type of the given {@link EClass}. If found it will call {@link EMFHelper#getUriHintNameForEClass(EClass)} with the found reference.
	 * So the name returned String will be at least name of the referenced {@link EClass}.
	 * If such a reference is not present a DetailEntry with the key <code>name</code> will be taken under consideration.
	 *
	 * The first annotation found on the class hierarchy will define the hint.
	 * @param eClass
	 * @return
	 */
	public static String getNameForEClass(EClass eClass){
		return getElementCodecAnnotationName(eClass);
	}
	
	public static String getNameForEStructuralFeature(EStructuralFeature feature){
		return getElementCodecAnnotationName(feature);
	}

	/**
	 * Returns the annotation with the given 'source' with detail 'detailsKey' value for the {@link ENamedElement}. If the element is an {@link EClass}
	 * the super type hierarchy will be analyzed as well 
	 * @param element the {@link ENamedElement}. Must not be <code>null</code>
	 * @param source the annotation source. Must not be <code>null</code>
	 * @param detailsKey the annotation details key. Must not be <code>null</code>
	 * @return
	 */
	public static final List<String> getElementAnnotationValues(ENamedElement element, String source, String detailsKey) {
		requireNonNull(element);
		requireNonNull(source);
		requireNonNull(detailsKey);
		String value = EcoreUtil.getAnnotation(element, source, detailsKey);
		List<String> values = new LinkedList<String>(); 
		if (value != null) {
			values.add(value);
		}
		if (element instanceof EClass) {
			EClass classifier = (EClass) element;
			List<String> superValues = classifier.
					getESuperTypes().
					stream().
					map(sec->EcoreUtil.getAnnotation(sec, source, detailsKey)).
					filter(Objects::nonNull).
					collect(Collectors.toList());
			if (!superValues.isEmpty()) {
				values.addAll(superValues);
			}
		}
		return values;
	}
	
	/**
	 * Retrieves the annotation alias name of the {@link ENamedElement}.
	 *
	 * By default this is the {@link ENamedElement#getName()}. If anywhere in the {@link ENamedElement} or on off its super classes the codec annotation 
	 * with detail <code>name</code> is found, the value from that annotation is taken.
	 * This annotation is expected on {@link EPackage}, {@link EClass} or {@link EStructuralFeature} level.
	 *
	 * The first annotation found on the class hierarchy will define the name.
	 * @param element the {@link ENamedElement}
	 * @param source the annotation source. Must not be <code>null</code>
	 * @param detailsKey the annotation details key. Must not be <code>null</code>
	 * @return the alternative name or <code>null</code>
	 * @throws IllegalStateException in case that multiple names exist, where it is not clear which one to take
	 */
	public static String getElementAnnotationValue(ENamedElement element, String source, String detailsKey){
		List<String> names = getElementAnnotationValues(element, source, detailsKey);
		switch (names.size()) {
		case 0:	
			return null;
		case 1:	
			return names.get(0);
		default:
			throw new IllegalStateException(String.format("At least two colliding uri-hints are defined for the ENamedElement '%s'and the super type hierarchy: '%s'", element.getName(), String.join(",", names)));
		}
	}
	
	/**
	 * Returns the {@link ExtendedMetaData} annotation 'name' detail value for the {@link ENamedElement}
	 * @param element the {@link ENamedElement}
	 * @return the alternative name or <code>null</code>
	 * @throws IllegalStateException in case that multiple names exist, where it is not clear which one to take
	 */
	public static String getElementEMDAnnotationName(ENamedElement element) {
		return getElementAnnotationValue(element, ExtendedMetaData.ANNOTATION_URI, EXTENDED_METADATA_NAME_KEY);
	}
	
	/**
	 * Returns the {@link ExtendedMetaData} annotation 'name' detail values for the {@link ENamedElement}
	 * @param element the {@link ENamedElement}
	 * @return the {@link List} of alternative names or an empty {@link List}
	 */
	public static List<String> getElementEMDAnnotationNames(ENamedElement element) {
		return getElementAnnotationValues(element, ExtendedMetaData.ANNOTATION_URI, EXTENDED_METADATA_NAME_KEY);
	}
	
	/**
	 * Returns the codec annotation 'name' detail value for the {@link ENamedElement}
	 * @param element the {@link ENamedElement}
	 * @return the alternative name or <code>null</code>
	 * @throws IllegalStateException in case that multiple names exist, where it is not clear which one to take
	 */
	public static String getElementCodecAnnotationName(ENamedElement element){
		return getElementAnnotationValue(element, ANNOTATION_NAMESPACE, ANNOTATION_NAME_KEY);
	}
	
	/**
	 * Returns the codec annotation 'name' detail values for the {@link ENamedElement}
	 * @param element the {@link ENamedElement}
	 * @return the {@link List} of alternative names or an empty {@link List}
	 */
	public static List<String> getElementCodecAnnotationNames(ENamedElement element) {
		return getElementAnnotationValues(element, ANNOTATION_NAMESPACE, ANNOTATION_NAME_KEY);
	}

}
