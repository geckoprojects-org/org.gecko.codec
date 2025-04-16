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

import tools.jackson.core.TokenStreamContext;

import tools.jackson.core.json.DupDetector;
import tools.jackson.core.json.JsonWriteContext;

/**
 * Write context that provides codec information. We want to give information about the underlying structure
 * @author Mark Hoffmann
 * @since 26.01.2024
 */
public class CodecWriteContext extends JsonWriteContext {
	
	private EStructuralFeature feature;
	
	protected CodecWriteContext(int type, CodecWriteContext parent, DupDetector dups, Object currentObject) {
		super(type, parent, dups, currentObject);
	}
	
    public static CodecWriteContext createRootCodecContext(DupDetector dd) {
        return new CodecWriteContext(TYPE_ROOT, null, dd, null);
    }
    
    public static boolean isCodecContext(TokenStreamContext ctx) {
    	return ctx == null ? false : ctx instanceof CodecWriteContext;
    }
    
    public static int writeFeatureAndFieldName(TokenStreamContext ctx, EStructuralFeature feature, String fieldName)  {
    	if (isNull(ctx) || !isCodecContext(ctx)) {
    		if (ctx instanceof JsonWriteContext) {
    			return ((JsonWriteContext)ctx).writeName(fieldName);
    		}
    		throw new IllegalArgumentException("A non null CodecGeneratorWriteContext must be provided");
    	}
    	if (nonNull(feature) && nonNull(fieldName)) {
    		return ((CodecWriteContext)ctx).writeFeatureAndFieldName(feature, fieldName);
    	} else {
    		return JsonWriteContext.STATUS_EXPECT_NAME;
    	}
    }
    
    public static void resetFeature(TokenStreamContext ctx) {
    	if (isCodecContext(ctx)) {
    		((CodecWriteContext)ctx).setFeature(null);
    	}
    }
	
    /* 
     * (non-Javadoc)
     * @see tools.jackson.core.json.JsonWriteContext#reset(int, java.lang.Object)
     */
    @Override
    public JsonWriteContext reset(int type, Object currValue) {
    	return super.reset(type, currValue);
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
	
	public int writeFeatureAndFieldName(EStructuralFeature feature, String name) {
		int r = super.writeName(name);
		if (r == STATUS_OK_AS_IS || r == STATUS_OK_AFTER_COMMA) {
			this.feature = feature;
		}
		return r;
	}
	
	public CodecWriteContext createChildArrayContext() {
		CodecWriteContext ctxt = (CodecWriteContext) _child;
        if (ctxt == null) {
            _child = ctxt = new CodecWriteContext(TYPE_ARRAY, this,
                    (_dups == null) ? null : _dups.child(), null);
            return ctxt;
        }
        return (CodecWriteContext) ctxt.reset(TYPE_ARRAY, null);
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
    	CodecWriteContext ctxt = (CodecWriteContext) _child;
        if (ctxt == null) {
            _child = ctxt = new CodecWriteContext(TYPE_OBJECT, this,
                    (_dups == null) ? null : _dups.child(), null);
            return (CodecWriteContext) ctxt;
        }
        return (CodecWriteContext) ctxt.reset(TYPE_OBJECT, null);
    }

    /* @since 2.10 */
    public CodecWriteContext createChildObjectContext(Object currValue) {
    	CodecWriteContext ctxt = (CodecWriteContext) _child;
        if (ctxt == null) {
            _child = ctxt = new CodecWriteContext(TYPE_OBJECT, this,
                    (_dups == null) ? null : _dups.child(), currValue);
            return (CodecWriteContext) ctxt;
        }
        return (CodecWriteContext) ctxt.reset(TYPE_OBJECT, currValue);
    }
    
}
