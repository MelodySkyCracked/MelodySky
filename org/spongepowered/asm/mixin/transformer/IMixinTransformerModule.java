/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.mixin.transformer.TargetClassContext;

public interface IMixinTransformerModule {
    public void preApply(TargetClassContext var1);

    public void postApply(TargetClassContext var1);
}

