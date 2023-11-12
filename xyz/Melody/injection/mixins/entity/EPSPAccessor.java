/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 */
package xyz.Melody.injection.mixins.entity;

import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={EntityPlayer.class})
public interface EPSPAccessor {
    @Accessor(value="itemInUseCount")
    public void setItemInUseCount(int var1);
}

