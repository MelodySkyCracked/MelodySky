/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.network.NetHandlerPlayClient
 *  net.minecraft.client.network.NetworkPlayerInfo
 *  net.minecraft.network.EnumPacketDirection
 *  net.minecraft.network.NetworkManager
 */
package xyz.Melody.Utils.fakemc;

import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.NetworkManager;
import xyz.Melody.Utils.fakemc.FakeNetworkManager;

public final class FakeNetHandlerPlayClient
extends NetHandlerPlayClient {
    private NetworkPlayerInfo playerInfo;

    public FakeNetHandlerPlayClient(Minecraft minecraft) {
        super(minecraft, minecraft.field_71462_r, (NetworkManager)new FakeNetworkManager(EnumPacketDirection.CLIENTBOUND), minecraft.func_110432_I().func_148256_e());
        this.playerInfo = new NetworkPlayerInfo(minecraft.func_110432_I().func_148256_e());
    }

    public NetworkPlayerInfo func_175102_a(UUID uUID) {
        return this.playerInfo;
    }

    public NetworkPlayerInfo func_175104_a(String string) {
        return this.playerInfo;
    }
}

