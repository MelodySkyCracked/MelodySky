/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.entity.layers.LayerArrow
 *  net.minecraft.client.renderer.entity.layers.LayerRenderer
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 */
package xyz.Melody.injection.mixins.render;

import net.minecraft.client.renderer.entity.layers.LayerArrow;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.Melody.module.modules.render.NoArmorRender;

@Mixin(value={LayerArrow.class})
public abstract class MixinLayerArrow
implements LayerRenderer {
    @Inject(method="doRenderLayer", at={@At(value="HEAD")}, cancellable=true)
    public void doRenderLayer(EntityLivingBase entityLivingBase, float f, float f2, float f3, float f4, float f5, float f6, float f7, CallbackInfo callbackInfo) {
        if (NoArmorRender.getINSTANCE().isEnabled() && entityLivingBase instanceof EntityPlayer && ((Boolean)NoArmorRender.getINSTANCE().arrows.getValue()).booleanValue()) {
            callbackInfo.cancel();
        }
    }
}

