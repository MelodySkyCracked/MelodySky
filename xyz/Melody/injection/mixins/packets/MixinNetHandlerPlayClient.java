/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.network.NetHandlerPlayClient
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.server.S2FPacketSetSlot
 */
package xyz.Melody.injection.mixins.packets;

import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.Melody.System.PacketHandler;
import xyz.Melody.module.modules.QOL.AutoEnchantTable;

@Mixin(value={NetHandlerPlayClient.class})
public class MixinNetHandlerPlayClient {
    @Inject(method="handleSetSlot", at={@At(value="RETURN")})
    public void handleSetSlot(S2FPacketSetSlot s2FPacketSetSlot, CallbackInfo callbackInfo) {
        AutoEnchantTable.getINSTANCE().processInventoryContents();
    }

    @Inject(method="addToSendQueue", at={@At(value="HEAD")}, cancellable=true)
    public void sendPacket(Packet packet, CallbackInfo callbackInfo) {
        if (!PacketHandler.onPacketSend(packet)) {
            callbackInfo.cancel();
        }
    }
}

