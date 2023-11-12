/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.play.client.C02PacketUseEntity
 */
package xyz.Melody.injection.mixins.packets;

import net.minecraft.network.play.client.C02PacketUseEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={C02PacketUseEntity.class})
public interface C02Accessor {
    @Accessor(value="entityId")
    public int getEntityID();
}

