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
package org.gecko.codec.jackson.databind.context;

import org.eclipse.emf.ecore.EObject;

import com.fasterxml.jackson.core.json.DupDetector;
import com.fasterxml.jackson.core.json.JsonWriteContext;

/**
 * Write context that holds the parent EObject and its parent, if it exists
 * @author Mark Hoffmann
 * @since 10.01.2024
 */
@Deprecated
public class CodecWriteContext extends JsonWriteContext implements CodecContext {

	private EObject currentObject;
	private EObject parent;
	protected CodecWriteContext _child;

	public static CodecWriteContext createRootContext(DupDetector dd) {
		return new CodecWriteContext(TYPE_ROOT, null, dd);
	}

	/**
	 * Creates a new instance.
	 * @param type
	 * @param parent
	 * @param dups
	 */
	protected CodecWriteContext(int type, JsonWriteContext parent, DupDetector dups) {
		super(type, parent, dups);
	}
	protected CodecWriteContext(int type, JsonWriteContext parent, DupDetector dups, Object currentValue) {
		super(type, parent, dups, currentValue);
	}

	public CodecWriteContext createChildArrayContext() {
		CodecWriteContext ctxt = (CodecWriteContext)_child;
		if (ctxt == null) {
			_child = ctxt = new CodecWriteContext(TYPE_ARRAY, this,
					(_dups == null) ? null : _dups.child());
			return ctxt;
		}
		return ctxt.reset(TYPE_ARRAY);
	}

	/* @since 2.10 */
	public CodecWriteContext createChildArrayContext(Object currValue) {
		CodecWriteContext ctxt = _child;
		if (ctxt == null) {
			_child = ctxt = new CodecWriteContext(TYPE_ARRAY, this, (_dups == null) ? null : _dups.child(), currValue);
			return ctxt;
		}
		return ctxt.reset(TYPE_ARRAY, currValue);
	}

	public CodecWriteContext createChildObjectContext() {
		CodecWriteContext ctxt = _child;
		if (ctxt == null) {
			_child = ctxt = new CodecWriteContext(TYPE_OBJECT, this,
					(_dups == null) ? null : _dups.child());
			return ctxt;
		}
		return ctxt.reset(TYPE_OBJECT);
	}

	/* @since 2.10 */
	public CodecWriteContext createChildObjectContext(Object currValue) {
		CodecWriteContext ctxt = _child;
		if (ctxt == null) {
			_child = ctxt = new CodecWriteContext(TYPE_OBJECT, this,
					(_dups == null) ? null : _dups.child(), currValue);
			return ctxt;
		}
		return ctxt.reset(TYPE_OBJECT, currValue);
	}
	
	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.json.JsonWriteContext#reset(int)
	 */
	@Override
	public CodecWriteContext reset(int type) {
		return (CodecWriteContext) super.reset(type);
	}
	
	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.json.JsonWriteContext#reset(int, java.lang.Object)
	 */
	@Override
	public CodecWriteContext reset(int type, Object currValue) {
		return (CodecWriteContext) super.reset(type, currValue);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.databind.context.CodecContext#getEObject()
	 */
	@Override
	public EObject getEObject() {
		return currentObject;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.databind.context.CodecContext#setEObject(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public void setEObject(EObject object) {
		this.currentObject = object;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.databind.context.CodecContext#getParentEObject()
	 */
	@Override
	public EObject getParentEObject() {
		return parent;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.databind.context.CodecContext#setParentEObject(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public void setParentEObject(EObject parent) {
		this.parent = parent;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.gecko.codec.jackson.databind.context.CodecContext#getParentContext()
	 */
	@Override
	public CodecContext getParentContext() {
		JsonWriteContext parentContext = getParent();
		if (parentContext instanceof CodecContext) {
			return ((CodecContext) parentContext);
		}
		return null;
	}

}
