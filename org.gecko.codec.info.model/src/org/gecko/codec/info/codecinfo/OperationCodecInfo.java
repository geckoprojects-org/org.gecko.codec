/*
 */
package org.gecko.codec.info.codecinfo;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EOperation;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Operation Codec Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.gecko.codec.info.codecinfo.OperationCodecInfo#getEOperations <em>EOperations</em>}</li>
 * </ul>
 *
 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getOperationCodecInfo()
 * @model
 * @generated
 */
@ProviderType
public interface OperationCodecInfo extends FeatureCodecInfo {
	/**
	 * Returns the value of the '<em><b>EOperations</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.EOperation}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>EOperations</em>' reference list.
	 * @see org.gecko.codec.info.codecinfo.CodecInfoPackage#getOperationCodecInfo_EOperations()
	 * @model transient="true" volatile="true" derived="true" suppressedSetVisibility="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='return &lt;%org.eclipse.emf.common.util.ECollections%&gt;.asEList(getFeatures().stream().filter(f-&gt;f instanceof EOperation).map(EOperation.class::cast).collect(&lt;%java.util.stream.Collectors%&gt;.toList()));'"
	 * @generated
	 */
	EList<EOperation> getEOperations();

} // OperationCodecInfo
