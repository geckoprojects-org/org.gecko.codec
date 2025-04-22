///**
// * Copyright (c) 2012 - 2024 Data In Motion and others.
// * All rights reserved. 
// * 
// * This program and the accompanying materials are made available under the terms of the 
// * Eclipse Public License v2.0 which accompanies this distribution, and is available at
// * http://www.eclipse.org/legal/epl-v20.html
// * 
// * Contributors:
// *     Data In Motion - initial API and implementation
// */
//package org.gecko.codec;
//
//import tools.jackson.core.TreeCodec;
//import tools.jackson.core.io.DataOutputAsStream;
//
///**
// * Simple provider for the writer and ObjectCodec used for generating.
// * 
// * @author grune
// * @since Apr 10, 2024
// */
//public class CodecWriterProvider<W> extends DataOutputAsStream implements ObjectCodecProvider{
//	
//	private final W writer;
//	private TreeCodec objectCodec;
//
//	/**
//	 * Creates a new instance.
//	 */
//	public CodecWriterProvider(W writer, TreeCodec objectCodec) {
//		super(new CodecDataOutput<>(writer, objectCodec));
//		this.writer = writer;
//		this.objectCodec = objectCodec;
//	}
//
//	public W getWriter() {
//		return writer;
//	}
//
//	/* 
//	 * (non-Javadoc)
//	 * @see org.gecko.codec.ObjectCodecProvider#getObjectCodec()
//	 */
//	@Override
//	public TreeCodec getObjectCodec() {
//		return objectCodec;
//	}
//	
//	public CodecDataOutput getDataOutput() {
//		return (CodecDataOutput)_output;
//	}
//	
//}
