/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 */
package xyz.Melody.injection.mixins.entity;

import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import xyz.Melody.injection.mixins.entity.MixinEntityLivingBase;

@Mixin(value={EntityPlayer.class})
public abstract class MixinEntityPlayer
extends MixinEntityLivingBase {
    @Shadow
    protected void func_70626_be() {
    }
}

