/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.channel.ChannelHandlerContext
 *  net.minecraft.network.NetworkManager
 *  net.minecraft.network.Packet
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.Event
 */
package xyz.Melody.injection.mixins.client;

import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.Melody.Event.EventBus;
import xyz.Melody.Event.events.FML.PacketEvent;
import xyz.Melody.Event.events.world.EventPacketRecieve;

@Mixin(value={NetworkManager.class})
public abstract class MixinNetworkManager {
    @Inject(method="channelRead0", at={@At(value="HEAD")}, cancellable=true)
    private void read(ChannelHandlerContext channelHandlerContext, Packet packet, CallbackInfo callbackInfo) {
        if (MinecraftForge.EVENT_BUS.post((Event)new PacketEvent.ReceiveEvent(packet))) {
            callbackInfo.cancel();
        }
        EventPacketRecieve eventPacketRecieve = new EventPacketRecieve(packet);
        EventBus.getInstance().call(eventPacketRecieve);
        if (eventPacketRecieve.isCancelled()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method="sendPacket(Lnet/minecraft/network/Packet;)V", at={@At(value="HEAD")}, cancellable=true)
    private void send(Packet packet, CallbackInfo callbackInfo) {
        if (MinecraftForge.EVENT_BUS.post((Event)new PacketEvent.SendEvent(packet))) {
            callbackInfo.cancel();
        }
    }
}

