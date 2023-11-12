/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.channel.Channel
 *  net.minecraft.network.EnumPacketDirection
 *  net.minecraft.network.NetworkManager
 */
package xyz.Melody.Utils.fakemc;

import io.netty.channel.Channel;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.NetworkManager;
import xyz.Melody.Utils.fakemc.I;

public final class FakeNetworkManager
extends NetworkManager {
    public FakeNetworkManager(EnumPacketDirection enumPacketDirection) {
        super(enumPacketDirection);
    }

    public Channel channel() {
        return new I(this);
    }
}

