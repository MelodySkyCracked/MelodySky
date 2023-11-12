/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.StringUtils
 *  net.minecraftforge.client.event.ClientChatReceivedEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package xyz.Melody.module.modules.others;

import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.Utils.timer.TimerUtil;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class TransferBack
extends Module {
    private TimerUtil timer = new TimerUtil();
    private String playerName = null;

    public TransferBack() {
        super("TransferBack", new String[]{"TransferBack", "tb"}, ModuleType.Others);
    }

    @EventHandler
    private void onTick(EventTick eventTick) {
        if (this.playerName == null) {
            return;
        }
        if (this.timer.hasReached(250.0)) {
            this.mc.field_71439_g.func_71165_d("/p transfer " + this.playerName);
            this.playerName = null;
            this.timer.reset();
        }
    }

    @SubscribeEvent
    public void chat(ClientChatReceivedEvent clientChatReceivedEvent) {
        String string;
        String string2 = StringUtils.func_76338_a((String)clientChatReceivedEvent.message.func_150260_c()).toLowerCase();
        if (!string2.contains(this.mc.field_71439_g.func_70005_c_().toLowerCase())) {
            return;
        }
        if (string2.contains("the party was transferred to") && (string = this.stripRank(string2.substring(string2.indexOf("by") + 3))) != this.mc.field_71439_g.func_70005_c_().toLowerCase()) {
            this.playerName = string;
        }
        if (string2.contains("has promoted") && (string = this.stripRank(string2.substring(0, string2.indexOf("has promoted") - 1))) != this.mc.field_71439_g.func_70005_c_().toLowerCase()) {
            this.playerName = string;
        }
    }

    private String stripRank(String string) {
        if (!string.contains("]")) {
            System.out.println("non detected");
            return string;
        }
        return string.substring(string.indexOf("]") + 2);
    }
}

