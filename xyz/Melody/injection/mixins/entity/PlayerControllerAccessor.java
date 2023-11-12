/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.multiplayer.PlayerControllerMP
 */
package xyz.Melody.injection.mixins.entity;

import net.minecraft.client.multiplayer.PlayerControllerMP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={PlayerControllerMP.class})
public interface PlayerControllerAccessor {
    @Accessor(value="isHittingBlock")
    public boolean isHittingBlock();
}

