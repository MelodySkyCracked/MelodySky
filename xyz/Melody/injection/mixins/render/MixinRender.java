/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.entity.Render
 *  net.minecraft.entity.Entity
 */
package xyz.Melody.injection.mixins.render;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value={Render.class})
public abstract class MixinRender {
    @Shadow
    public void func_76986_a(Entity entity, double d, double d2, double d3, float f, float f2) {
    }
}

