/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.mixin.transformer.throwables;

import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.refmap.IReferenceMapperContext;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;

public class InvalidInterfaceMixinException
extends InvalidMixinException {
    private static final long serialVersionUID = 2L;

    public InvalidInterfaceMixinException(IMixinInfo iMixinInfo, String string) {
        super(iMixinInfo, string);
    }

    public InvalidInterfaceMixinException(IReferenceMapperContext iReferenceMapperContext, String string) {
        super(iReferenceMapperContext, string);
    }

    public InvalidInterfaceMixinException(IMixinInfo iMixinInfo, Throwable throwable) {
        super(iMixinInfo, throwable);
    }

    public InvalidInterfaceMixinException(IReferenceMapperContext iReferenceMapperContext, Throwable throwable) {
        super(iReferenceMapperContext, throwable);
    }

    public InvalidInterfaceMixinException(IMixinInfo iMixinInfo, String string, Throwable throwable) {
        super(iMixinInfo, string, throwable);
    }

    public InvalidInterfaceMixinException(IReferenceMapperContext iReferenceMapperContext, String string, Throwable throwable) {
        super(iReferenceMapperContext, string, throwable);
    }
}

