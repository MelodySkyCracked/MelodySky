/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.mixin.injection.throwables;

import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.mixin.refmap.IReferenceMapperContext;

public class InvalidSliceException
extends InvalidInjectionException {
    private static final long serialVersionUID = 1L;

    public InvalidSliceException(IReferenceMapperContext iReferenceMapperContext, String string) {
        super(iReferenceMapperContext, string);
    }

    public InvalidSliceException(InjectionInfo injectionInfo, String string) {
        super(injectionInfo, string);
    }

    public InvalidSliceException(IReferenceMapperContext iReferenceMapperContext, Throwable throwable) {
        super(iReferenceMapperContext, throwable);
    }

    public InvalidSliceException(InjectionInfo injectionInfo, Throwable throwable) {
        super(injectionInfo, throwable);
    }

    public InvalidSliceException(IReferenceMapperContext iReferenceMapperContext, String string, Throwable throwable) {
        super(iReferenceMapperContext, string, throwable);
    }

    public InvalidSliceException(InjectionInfo injectionInfo, String string, Throwable throwable) {
        super(injectionInfo, string, throwable);
    }
}

