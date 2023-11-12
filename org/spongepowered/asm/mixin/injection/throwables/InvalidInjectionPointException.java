/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.mixin.injection.throwables;

import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.mixin.refmap.IReferenceMapperContext;

public class InvalidInjectionPointException
extends InvalidInjectionException {
    private static final long serialVersionUID = 2L;

    public InvalidInjectionPointException(IReferenceMapperContext iReferenceMapperContext, String string, Object ... objectArray) {
        super(iReferenceMapperContext, String.format(string, objectArray));
    }

    public InvalidInjectionPointException(InjectionInfo injectionInfo, String string, Object ... objectArray) {
        super(injectionInfo, String.format(string, objectArray));
    }

    public InvalidInjectionPointException(IReferenceMapperContext iReferenceMapperContext, Throwable throwable, String string, Object ... objectArray) {
        super(iReferenceMapperContext, String.format(string, objectArray), throwable);
    }

    public InvalidInjectionPointException(InjectionInfo injectionInfo, Throwable throwable, String string, Object ... objectArray) {
        super(injectionInfo, String.format(string, objectArray), throwable);
    }
}

