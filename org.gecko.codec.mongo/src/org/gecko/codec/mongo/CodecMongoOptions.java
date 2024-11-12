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
package org.gecko.codec.mongo;

/**
 * 
 * @author ilenia
 * @since Nov 12, 2024
 */
public interface CodecMongoOptions {
	
	/**
	 * If set to an {@link EClass} or a {@link String} EMF Mongo uses the given value as collection
	 * 
	 * <code>
	 * resourceSet.getSaveOptions().put(CodecMongoOptions.CODEC_MONGO_COLLECTION_NAME, BasicPackage.Literals.MYTEST));
	 * resourceSet.getSaveOptions().put(CodecMongoOptions.CODEC_MONGO_COLLECTION_NAME, "mycollection"));
	 * resourceSet.getLoadOptions().put(CodecMongoOptions.CODEC_MONGO_COLLECTION_NAME, BasicPackage.Literals.MYTEST));
	 * resourceSet.getLoadOptions().put(CodecMongoOptions.CODEC_MONGO_COLLECTION_NAME, "mycollection"));
	 * </code>
	 */
	String CODEC_MONGO_COLLECTION_NAME = "codec.mongo.collection.name";

}
