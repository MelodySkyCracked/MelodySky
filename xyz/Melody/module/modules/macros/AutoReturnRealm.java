/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.event.ClickEvent
 *  net.minecraft.event.ClickEvent$Action
 *  net.minecraft.util.IChatComponent
 *  net.minecraft.util.StringUtils
 *  net.minecraftforge.client.event.ClientChatReceivedEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package xyz.Melody.module.modules.macros;

import net.minecraft.event.ClickEvent;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.Melody.Utils.Helper;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class AutoReturnRealm
extends Module {
    public AutoReturnRealm() {
        super("AutoReturnRealm", new String[]{""}, ModuleType.Macros);
        this.setModInfo("Auto Click 'HERE' while AFK.");
    }

    @SubscribeEvent(receiveCanceled=true)
    public void checks(ClientChatReceivedEvent clientChatReceivedEvent) {
        if (StringUtils.func_76338_a((String)clientChatReceivedEvent.message.func_150260_c()).equals("Click HERE to return to the playing realm!")) {
            for (IChatComponent iChatComponent : clientChatReceivedEvent.message.func_150253_a()) {
                ClickEvent clickEvent = iChatComponent.func_150256_b().func_150235_h();
                if (clickEvent == null || clickEvent.func_150669_a() != ClickEvent.Action.RUN_COMMAND) continue;
                Helper.sendMessage(clickEvent.func_150668_b());
                this.mc.field_71439_g.func_71165_d(clickEvent.func_150668_b());
            }
        }
    }
}

