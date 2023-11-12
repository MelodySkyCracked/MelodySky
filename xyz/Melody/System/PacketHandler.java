/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.network.Packet
 */
package xyz.Melody.System;

import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import xyz.Melody.Event.EventBus;
import xyz.Melody.Event.events.world.EventPacketRecieve;
import xyz.Melody.Event.events.world.EventPacketSend;

public class PacketHandler {
    private static Minecraft mc = Minecraft.func_71410_x();
    private static ArrayList skipEventArray = new ArrayList();

    public static boolean onPacketRecieve(Packet packet) {
        EventPacketRecieve eventPacketRecieve = new EventPacketRecieve(packet);
        EventBus.getInstance().call(eventPacketRecieve);
        return !eventPacketRecieve.isCancelled();
    }

    public static boolean onPacketSend(Packet packet) {
        if (skipEventArray.contains(packet)) {
            skipEventArray.remove(packet);
            return true;
        }
        EventPacketSend eventPacketSend = new EventPacketSend(packet);
        EventBus.getInstance().call(eventPacketSend);
        return !eventPacketSend.isCancelled();
    }

    public static void sendPacket(Packet packet) {
        if (packet != null) {
            mc.func_147114_u().func_147297_a(packet);
        }
    }

    public static void sendPacketNoEvent(Packet packet) {
        if (packet != null) {
            skipEventArray.add(packet);
            mc.func_147114_u().func_147297_a(packet);
        }
    }
}

