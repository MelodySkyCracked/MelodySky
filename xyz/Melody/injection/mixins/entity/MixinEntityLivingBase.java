/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.potion.Potion
 */
package xyz.Melody.injection.mixins.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.Melody.injection.mixins.entity.MixinEntity;
import xyz.Melody.module.modules.render.Cam;

@Mixin(value={EntityLivingBase.class})
public abstract class MixinEntityLivingBase
extends MixinEntity {
    @Shadow
    protected boolean field_70703_bu;
    @Shadow
    public float field_70702_br;
    @Shadow
    public float field_70701_bs;

    @Inject(method="isPotionActive(Lnet/minecraft/potion/Potion;)Z", at={@At(value="HEAD")}, cancellable=true)
    private void isPotionActive(Potion potion, CallbackInfoReturnable callbackInfoReturnable) {
        if ((potion == Potion.field_76431_k || potion == Potion.field_76440_q) && Cam.getINSTANCE().isEnabled() && ((Boolean)Cam.getINSTANCE().noBlindness.getValue()).booleanValue()) {
            callbackInfoReturnable.setReturnValue(false);
        }
    }
}

