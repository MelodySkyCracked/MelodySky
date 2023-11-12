/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.mixin.gen.throwables;

import org.spongepowered.asm.mixin.gen.AccessorInfo;
import org.spongepowered.asm.mixin.refmap.IReferenceMapperContext;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;

public class InvalidAccessorException
extends InvalidMixinException {
    private static final long serialVersionUID = 2L;
    private final AccessorInfo info;

    public InvalidAccessorException(IReferenceMapperContext iReferenceMapperContext, String string) {
        super(iReferenceMapperContext, string);
        this.info = null;
    }

    public InvalidAccessorException(AccessorInfo accessorInfo, String string) {
        super((IReferenceMapperContext)accessorInfo.getContext(), string);
        this.info = accessorInfo;
    }

    public InvalidAccessorException(IReferenceMapperContext iReferenceMapperContext, Throwable throwable) {
        super(iReferenceMapperContext, throwable);
        this.info = null;
    }

    public InvalidAccessorException(AccessorInfo accessorInfo, Throwable throwable) {
        super((IReferenceMapperContext)accessorInfo.getContext(), throwable);
        this.info = accessorInfo;
    }

    public InvalidAccessorException(IReferenceMapperContext iReferenceMapperContext, String string, Throwable throwable) {
        super(iReferenceMapperContext, string, throwable);
        this.info = null;
    }

    public InvalidAccessorException(AccessorInfo accessorInfo, String string, Throwable throwable) {
        super(accessorInfo.getContext(), string, throwable);
        this.info = accessorInfo;
    }

    public AccessorInfo getAccessorInfo() {
        return this.info;
    }
}

