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
package org.gecko.codec.jackson.databind;

import org.eclipse.emf.ecore.EStructuralFeature;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.json.DupDetector;

/**
 * 
 * @author ilenia
 * @since Oct 28, 2024
 */
public class CodecReadContext extends JsonStreamContext {

	/**
	 * Parent context for this context; null for root context.
	 */
	protected final CodecReadContext _parent;

	// // // Optional duplicate detection
	protected DupDetector _dups;

	/*
    /**********************************************************
    /* Simple instance reuse slots; speeds up things a bit (10-15%)
    /* for docs with lots of small arrays/objects (for which
    /* allocation was visible in profile stack frames)
    /**********************************************************
	 */

	protected CodecReadContext _child;

	/*
    /**********************************************************
    /* Location/state information (minus source reference)
    /**********************************************************
	 */

	protected String _currentName;

	/**
	 * @since 2.5
	 */
	protected Object _currentValue;

	protected int _lineNr;
	protected int _columnNr;

	private EStructuralFeature feature;

	public CodecReadContext(CodecReadContext parent, int nestingDepth,
			DupDetector dups, int type, int lineNr, int colNr) {
		super();
		_parent = parent;
		_dups = dups;
		_type = type;
		_lineNr = lineNr;
		_columnNr = colNr;
		_index = -1;
		_nestingDepth = nestingDepth;
	}

	public CodecReadContext(int type, CodecReadContext parent,
			DupDetector dups) {
		super();
		_parent = parent;
		_dups = dups;
		_type = type;
		_index = -1;
		_nestingDepth = parent == null ? 0 : parent._nestingDepth + 1;
	}
	
	public CodecReadContext(int type, CodecReadContext parent,
			DupDetector dups, Object currentValue) {
		super();
		_parent = parent;
		_currentValue = currentValue;
		_dups = dups;
		_type = type;
		_index = -1;
		_nestingDepth = parent == null ? 0 : parent._nestingDepth + 1;
	}

	public void setFeature(EStructuralFeature feature) {
		this.feature = feature;
	}

	public EStructuralFeature getFeature() {
		return feature;
	}

	public static void resetFeature(JsonStreamContext ctx) {
		if (isCodecContext(ctx)) {
			((CodecWriteContext)ctx).setFeature(null);
		}
	}
	
	
	public static CodecReadContext createRootContext(int lineNr, int colNr, DupDetector dups) {
        return new CodecReadContext(null, 0, dups, TYPE_ROOT, lineNr, colNr);
    }

    public static CodecReadContext createRootContext(DupDetector dups) {
        return new CodecReadContext(null, 0, dups, TYPE_ROOT, 1, 0);
    }
    
  

    public CodecReadContext createChildArrayContext(int lineNr, int colNr) {
    	CodecReadContext ctxt = _child;
        if (ctxt == null) {
            _child = ctxt = new CodecReadContext(this, _nestingDepth+1,
                    (_dups == null) ? null : _dups.child(), TYPE_ARRAY, lineNr, colNr);
        } else {
            ctxt.reset(TYPE_ARRAY, lineNr, colNr);
        }
        return ctxt;
    }

    public CodecReadContext createChildObjectContext(int lineNr, int colNr) {
    	CodecReadContext ctxt = _child;
        if (ctxt == null) {
            _child = ctxt = new CodecReadContext(this, _nestingDepth+1,
                    (_dups == null) ? null : _dups.child(), TYPE_OBJECT, lineNr, colNr);
            return ctxt;
        }
        ctxt.reset(TYPE_OBJECT, lineNr, colNr);
        return ctxt;
    }
	
    public void reset(int type, int lineNr, int colNr) {
        _type = type;
        _index = -1;
        _lineNr = lineNr;
        _columnNr = colNr;
        _currentName = null;
        _currentValue = null;
        if (_dups != null) {
            _dups.reset();
        }
    }
    
    public DupDetector getDupDetector() {
        return _dups;
    }
    
    public CodecReadContext withDupDetector(DupDetector dups) {
        _dups = dups;
        return this;
    }

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonStreamContext#getParent()
	 */
	@Override
	public CodecReadContext getParent() {
		return _parent;
	}

	/* 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.core.JsonStreamContext#getCurrentName()
	 */
	@Override
	public String getCurrentName() {
		return _currentName;
	}
	
	public void setCurrentName(String name) throws JsonProcessingException {
        _currentName = name;
        if (_dups != null) { _checkDup(_dups, name); }
    }
	
	@Override
    public void setCurrentValue(Object v) {
        _currentValue = v;
    }
	
	public CodecReadContext clearAndGetParent() {
        _currentValue = null;
        // could also clear the current name, but seems cheap enough to leave?
        return _parent;
    }
	
	 public boolean expectComma() {
	        /* Assumption here is that we will be getting a value (at least
	         * before calling this method again), and
	         * so will auto-increment index to avoid having to do another call
	         */
	        int ix = ++_index; // starts from -1
	        return (_type != TYPE_ROOT && ix > 0);
	    }


	public static boolean isCodecContext(JsonStreamContext ctx) {
		return ctx == null ? false : ctx instanceof CodecReadContext;
	}
	
	private void _checkDup(DupDetector dd, String name) throws JsonProcessingException {
        if (dd.isDup(name)) {
            Object src = dd.getSource();
            throw new JsonParseException(((src instanceof JsonParser) ? ((JsonParser) src) : null),
                    "Duplicate field '"+name+"'");
        }
    }
}
