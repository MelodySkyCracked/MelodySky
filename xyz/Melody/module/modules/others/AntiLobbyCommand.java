/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.EnumChatFormatting
 */
package xyz.Melody.module.modules.others;

import net.minecraft.util.EnumChatFormatting;
import xyz.Melody.Client;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.misc.EventChat;
import xyz.Melody.Event.value.Option;
import xyz.Melody.Utils.Helper;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class AntiLobbyCommand
extends Module {
    private Option od = new Option("DungeonOnly", true);

    public AntiLobbyCommand() {
        super("AntiLobbyCommands", new String[]{"alc", "asc", "lobby"}, ModuleType.Others);
        this.addValues(this.od);
        this.setModInfo("Prevents You using /l or /spawn.");
    }

    @EventHandler
    private void onChat(EventChat eventChat) {
        if (!Client.inDungeons && ((Boolean)this.od.getValue()).booleanValue()) {
            return;
        }
        String string = eventChat.getMessage();
        if (string.toLowerCase().contains("/lobby")) {
            Helper.sendMessage(EnumChatFormatting.GREEN + "[AntiLobbyCommand] " + EnumChatFormatting.DARK_GREEN + "Prevented you from using " + EnumChatFormatting.RED + "/lobby " + EnumChatFormatting.DARK_GREEN + "Command.");
            eventChat.setCancelled(true);
            return;
        }
        if (string.toLowerCase().equals("/l")) {
            Helper.sendMessage(EnumChatFormatting.GREEN + "[AntiLobbyCommand] " + EnumChatFormatting.DARK_GREEN + "Prevented you from using " + EnumChatFormatting.RED + "/l " + EnumChatFormatting.DARK_GREEN + "Command.");
            eventChat.setCancelled(true);
            return;
        }
        if (string.toLowerCase().contains("/spawn")) {
            Helper.sendMessage(EnumChatFormatting.GREEN + "[AntiLobbyCommand] " + EnumChatFormatting.DARK_GREEN + "Prevented you from using " + EnumChatFormatting.RED + "/spawn " + EnumChatFormatting.DARK_GREEN + "Command.");
            eventChat.setCancelled(true);
            return;
        }
    }
}

