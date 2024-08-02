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
package org.gecko.codec.info;

import java.util.List;
import java.util.Optional;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.gecko.codec.info.codecinfo.CodecInfoHolder;
import org.gecko.codec.info.codecinfo.EClassCodecInfo;
import org.gecko.codec.info.codecinfo.InfoType;
import org.gecko.codec.info.codecinfo.PackageCodecInfo;

/**
 * 
 * @author mark
 * @since 30.07.2024
 */
public interface CodecModelInfo extends EPackage.Registry {
	
	/**
	 * Looks through all known Packages as returns  and {@link Optional} with the  {@link EClass} for the given Class
	 * @param clazz the {@link Class} to look for
	 * @return an {@link Optional} for the {@link EClass}
	 */
	public Optional<EClassifier> getEClassifierForClass(Class<?> clazz);

	/**
	 * Looks through all known Packages as returns  and {@link Optional} with the  {@link EClass} for the given Class
	 * @param fullQualifiedClassName the full qualified class name to look for
	 * @return an {@link Optional} for the {@link EClass}
	 */
	public Optional<EClassifier> getEClassifierForClass(String fullQualifiedClassName);
	
	/**
	 * It might be necessary to know, that class can inherit from the given class. This method will return a List of all known upper {@link EClass}es.
	 * @param eClass the EClass to get the hirachy for
	 * @return The {@link List} of upper elements. 
	 */
	public List<EClass> getUpperTypeHierarchyForEClass(EClass eClass);	
	
	/**
	 * Retrieves the {@link PackageCodecInfo} for the {@link EPackage} with the given namespace URI, if available
	 * @param uri the namespace URI of the {@link EPackage}
	 * @return an {@link Optional} for the {@link PackageCodecInfo} for the {@link EPackage} with the given namespace URI, if available
	 */
	public Optional<PackageCodecInfo> getCodecInfoForPackage(String uri);
	
	
	/**
	 * Retrieves the {@link EClassCodecInfo} for the given {@link EClass} 
	 * @param eClass the EClass to look for
	 * @return an {@link Optional} for the {@link EClassCodecInfo}
	 */
	public Optional<EClassCodecInfo> getCodecInfoForEClass(EClass eClass);
	
	/**
	 * Retrieves the {@link CodecInfoHolder}, if available, for a given {@link InfoType}
	 * @param infoType the {@link InfoType} for which we want to retrieve the {@link CodecInfoHolder}
	 * @return an {@link Optional} for the {@link CodecInfoHolder}
	 */
	public Optional<CodecInfoHolder> getCodecInfoHolderByType(InfoType infoType);

}
