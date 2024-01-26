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

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import org.eclipse.emf.ecore.EStructuralFeature;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.json.DupDetector;
import com.fasterxml.jackson.core.json.JsonWriteContext;

/**
 * Write context that provides codec information. We want to give information about the underlying structure
 * @author Mark Hoffmann
 * @since 26.01.2024
 */
public class CodecWriteContext extends JsonWriteContext {
	
	private EStructuralFeature feature;

	/**
	 * Creates a new instance.
	 * @param type
	 * @param parent
	 * @param dups
	 */
	protected CodecWriteContext(int type, CodecWriteContext parent, DupDetector dups) {
		super(type, parent, dups);
	}
	
	protected CodecWriteContext(int type, CodecWriteContext parent, DupDetector dups, Object currentObject) {
		super(type, parent, dups, currentObject);
	}
	
    public static CodecWriteContext createRootCodecContext(DupDetector dd) {
        return new CodecWriteContext(TYPE_ROOT, null, dd);
    }
    
    public static boolean isCodecContext(JsonStreamContext ctx) {
    	return ctx == null ? false : ctx instanceof CodecWriteContext;
    }
    
    public static int writeFeatureAndFieldName(JsonStreamContext ctx, EStructuralFeature feature, String fieldName) throws JsonProcessingException {
    	if (isNull(ctx) || !isCodecContext(ctx)) {
    		if (ctx instanceof JsonWriteContext) {
    			return ((JsonWriteContext)ctx).writeFieldName(fieldName);
    		}
    		throw new IllegalArgumentException("A non null CodecGeneratorWriteContext must be provided");
    	}
    	if (nonNull(feature) && nonNull(fieldName)) {
    		return ((CodecWriteContext)ctx).writeFeatureAndFieldName(feature, fieldName);
    	} else {
    		return JsonWriteContext.STATUS_EXPECT_NAME;
    	}
    }
    
    public static void resetFeature(JsonStreamContext ctx) {
    	if (nonNull(ctx) && ctx instanceof CodecWriteContext) {
    		((CodecWriteContext)ctx).setFeature(null);
    	}
    }
	
	/**
	 * Sets the feature.
	 * @param feature the feature to set
	 */
	public void setFeature(EStructuralFeature feature) {
		this.feature = feature;
	}
	
	/**
	 * Returns the feature.
	 * @return the feature
	 */
	public EStructuralFeature getFeature() {
		return feature;
	}
	
	public int writeFeatureAndFieldName(EStructuralFeature feature, String name) throws JsonProcessingException {
		int r = super.writeFieldName(name);
		if (r == STATUS_OK_AS_IS || r == STATUS_OK_AFTER_COMMA) {
			this.feature = feature;
		}
		return r;
	}
	
	public CodecWriteContext createChildArrayContext() {
		CodecWriteContext ctxt = (CodecWriteContext) _child;
        if (ctxt == null) {
            _child = ctxt = new CodecWriteContext(TYPE_ARRAY, this,
                    (_dups == null) ? null : _dups.child());
            return ctxt;
        }
        return (CodecWriteContext) ctxt.reset(TYPE_ARRAY);
    }

    /* @since 2.10 */
    public CodecWriteContext createChildArrayContext(Object currValue) {
    	CodecWriteContext ctxt = (CodecWriteContext) _child;
        if (ctxt == null) {
            _child = ctxt = new CodecWriteContext(TYPE_ARRAY, this,
                    (_dups == null) ? null : _dups.child(), currValue);
            return ctxt;
        }
        return (CodecWriteContext) ctxt.reset(TYPE_ARRAY, currValue);
    }

    public CodecWriteContext createChildObjectContext() {
        JsonWriteContext ctxt = _child;
        if (ctxt == null) {
            _child = ctxt = new CodecWriteContext(TYPE_OBJECT, this,
                    (_dups == null) ? null : _dups.child());
            return (CodecWriteContext) ctxt;
        }
        return (CodecWriteContext) ctxt.reset(TYPE_OBJECT);
    }

    /* @since 2.10 */
    public CodecWriteContext createChildObjectContext(Object currValue) {
        JsonWriteContext ctxt = _child;
        if (ctxt == null) {
            _child = ctxt = new CodecWriteContext(TYPE_OBJECT, this,
                    (_dups == null) ? null : _dups.child(), currValue);
            return (CodecWriteContext) ctxt;
        }
        return (CodecWriteContext) ctxt.reset(TYPE_OBJECT, currValue);
    }
    
}
