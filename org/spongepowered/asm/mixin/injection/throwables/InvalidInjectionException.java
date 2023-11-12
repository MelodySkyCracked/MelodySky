/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.mixin.injection.throwables;

import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.refmap.IReferenceMapperContext;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;

public class InvalidInjectionException
extends InvalidMixinException {
    private static final long serialVersionUID = 2L;
    private final InjectionInfo info;

    public InvalidInjectionException(IReferenceMapperContext iReferenceMapperContext, String string) {
        super(iReferenceMapperContext, string);
        this.info = null;
    }

    public InvalidInjectionException(InjectionInfo injectionInfo, String string) {
        super((IReferenceMapperContext)injectionInfo.getContext(), string);
        this.info = injectionInfo;
    }

    public InvalidInjectionException(IReferenceMapperContext iReferenceMapperContext, Throwable throwable) {
        super(iReferenceMapperContext, throwable);
        this.info = null;
    }

    public InvalidInjectionException(InjectionInfo injectionInfo, Throwable throwable) {
        super((IReferenceMapperContext)injectionInfo.getContext(), throwable);
        this.info = injectionInfo;
    }

    public InvalidInjectionException(IReferenceMapperContext iReferenceMapperContext, String string, Throwable throwable) {
        super(iReferenceMapperContext, string, throwable);
        this.info = null;
    }

    public InvalidInjectionException(InjectionInfo injectionInfo, String string, Throwable throwable) {
        super(injectionInfo.getContext(), string, throwable);
        this.info = injectionInfo;
    }

    public InjectionInfo getInjectionInfo() {
        return this.info;
    }
}

