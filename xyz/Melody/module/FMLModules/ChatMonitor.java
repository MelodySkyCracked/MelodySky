/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.ChatComponentText
 *  net.minecraft.util.StringUtils
 *  net.minecraftforge.client.event.ClientChatReceivedEvent
 *  net.minecraftforge.event.world.WorldEvent$Load
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package xyz.Melody.module.FMLModules;

import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.Melody.Client;
import xyz.Melody.module.FMLModules.Utils.CTObject;
import xyz.Melody.module.modules.QOL.Dungeons.LeverAura;
import xyz.Melody.module.modules.macros.PowderChestMacro;

public class ChatMonitor {
    @SubscribeEvent(receiveCanceled=true)
    public void onNecron(ClientChatReceivedEvent clientChatReceivedEvent) {
        String string = StringUtils.func_76338_a((String)clientChatReceivedEvent.message.func_150260_c());
        String string2 = clientChatReceivedEvent.message.func_150254_d();
        for (CTObject cTObject : Client.chatTriggerMap) {
            String string3 = cTObject.getTarget();
            if (cTObject.isStrip()) {
                if (!string.equals(string3)) continue;
                Client.mc.field_71439_g.func_71165_d(cTObject.getMessage());
                continue;
            }
            if (!string.contains(string3)) continue;
            Client.mc.field_71439_g.func_71165_d(cTObject.getMessage());
        }
        if (string.equals("[BOSS] Necron: Goodbye.")) {
            clientChatReceivedEvent.message = new ChatComponentText(clientChatReceivedEvent.message.func_150254_d().replaceAll("Goodbye.", "Goldor, Fuck You!"));
        } else if (string.equals("[BOSS] Necron: ARGH!")) {
            clientChatReceivedEvent.message = new ChatComponentText(clientChatReceivedEvent.message.func_150254_d().replaceAll("ARGH!", "NMSL!"));
        } else if (string.equals("[BOSS] Necron: All this, for nothing...")) {
            clientChatReceivedEvent.message = new ChatComponentText(clientChatReceivedEvent.message.func_150254_d().replaceAll("All this, for nothing...", "No handle for you..."));
        } else if (string.contains("Mining Speed Boost is now available!")) {
            Client.pickaxeAbilityReady = true;
        } else if (string.contains("You used your Mining Speed Boost Pickaxe Ability!")) {
            Client.pickaxeAbilityReady = false;
        } else if (string.contains("You have successfully picked the lock on this chest!")) {
            PowderChestMacro.nextRotation = null;
            PowderChestMacro.done.add(PowderChestMacro.chestPos);
            PowderChestMacro.chest = null;
        } else if (!string.contains("XJC") && !string.contains("Guild >") && (string.startsWith("PUZZLE FAIL") || string.contains("You were killed by") || string.contains("You were crushed") || string.contains("You fell into the void") || string.contains("You suffocated") || string.contains("You burnt to death") || string.contains("You died"))) {
            Client.instance.irc.sendPrefixMsg(string2, true);
        } else if (!string.contains("XJC") && !string.contains("Guild >") && (string.startsWith("RARE REWARD") || string.startsWith("PET DROP"))) {
            Client.instance.irc.sendPrefixMsg(string2, true);
        }
    }

    @SubscribeEvent
    public void clear(WorldEvent.Load load) {
        PowderChestMacro.done.clear();
        LeverAura.allLevers.clear();
        LeverAura.clicked.clear();
        LeverAura.blockPos = null;
        Client.pickaxeAbilityReady = false;
    }
}

