/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.mixin.refmap;

import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.refmap.ReferenceMapper;

public interface IReferenceMapperContext {
    public IMixinInfo getMixin();

    public String getClassRef();

    public ReferenceMapper getReferenceMapper();
}

