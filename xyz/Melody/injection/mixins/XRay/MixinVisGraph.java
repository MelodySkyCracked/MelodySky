/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.chunk.VisGraph
 */
package xyz.Melody.injection.mixins.XRay;

import net.minecraft.client.renderer.chunk.VisGraph;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.Melody.module.modules.render.XRay;

@Mixin(value={VisGraph.class})
public class MixinVisGraph {
    @Inject(method="func_178606_a", at={@At(value="HEAD")}, cancellable=true)
    private void func_178606_a(CallbackInfo callbackInfo) {
        if (XRay.getINSTANCE().isEnabled()) {
            callbackInfo.cancel();
        }
    }
}

