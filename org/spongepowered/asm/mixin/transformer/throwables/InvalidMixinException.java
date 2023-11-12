/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.mixin.transformer.throwables;

import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.refmap.IReferenceMapperContext;
import org.spongepowered.asm.mixin.throwables.MixinException;

public class InvalidMixinException
extends MixinException {
    private static final long serialVersionUID = 2L;
    private final IMixinInfo mixin;

    public InvalidMixinException(IMixinInfo iMixinInfo, String string) {
        super(string);
        this.mixin = iMixinInfo;
    }

    public InvalidMixinException(IReferenceMapperContext iReferenceMapperContext, String string) {
        this(iReferenceMapperContext.getMixin(), string);
    }

    public InvalidMixinException(IMixinInfo iMixinInfo, Throwable throwable) {
        super(throwable);
        this.mixin = iMixinInfo;
    }

    public InvalidMixinException(IReferenceMapperContext iReferenceMapperContext, Throwable throwable) {
        this(iReferenceMapperContext.getMixin(), throwable);
    }

    public InvalidMixinException(IMixinInfo iMixinInfo, String string, Throwable throwable) {
        super(string, throwable);
        this.mixin = iMixinInfo;
    }

    public InvalidMixinException(IReferenceMapperContext iReferenceMapperContext, String string, Throwable throwable) {
        super(string, throwable);
        this.mixin = iReferenceMapperContext.getMixin();
    }

    public IMixinInfo getMixin() {
        return this.mixin;
    }
}

