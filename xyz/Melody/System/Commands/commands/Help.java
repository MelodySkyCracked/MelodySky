/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.System.Commands.commands;

import xyz.Melody.System.Commands.Command;
import xyz.Melody.Utils.Helper;

public final class Help
extends Command {
    public Help() {
        super("Help", new String[]{"list"}, "", "sketit");
    }

    @Override
    public String execute(String[] stringArray) {
        if (stringArray.length == 0) {
            Helper.sendMessageWithoutPrefix("\u00a77\u00a7m\u00a7l----------------------------------");
            Helper.sendMessageWithoutPrefix("                    \u00a7b\u00a7lMelodySky");
            Helper.sendMessageWithoutPrefix("\u00a7b.help >\u00a77 list commands");
            Helper.sendMessageWithoutPrefix("\u00a7b.bind >\u00a77 bind a module to a key");
            Helper.sendMessageWithoutPrefix("\u00a7b.t >\u00a77 toggle a module on/off");
            Helper.sendMessageWithoutPrefix("\u00a7b.friend >\u00a77 friend a player");
            Helper.sendMessageWithoutPrefix("\u00a7b.authme >\u00a77 Re-Authenticate Current User");
            Helper.sendMessageWithoutPrefix("\u00a7b.is ITEM_ID >\u00a77 set Custom Item Swapping(ItemSwitcher Custom Mode)");
            Helper.sendMessageWithoutPrefix("\u00a7b.irc >\u00a77 Toggle Client Chat Feature.");
            Helper.sendMessageWithoutPrefix("\u00a7b.CustomLbinColor <number_value> >\u00a77 Set Lbin Color in Tooltips.");
            Helper.sendMessageWithoutPrefix("\u00a7b.sbid >\u00a77 Show Holding Item's Skyblock ID.");
            Helper.sendMessageWithoutPrefix("\u00a7b.gn <UUID> >\u00a77 Check Player's BlackList Info.");
            Helper.sendMessageWithoutPrefix("\u00a7b.nick <name> >\u00a77 Set Your Own NickName.");
            Helper.sendMessageWithoutPrefix("\u00a7b.replacetext <source> to <target> >\u00a77 Set Text Replacement.");
            Helper.sendMessageWithoutPrefix("\u00a7b.chattrigger For more helps of ChatTrigger Commands.");
            Helper.sendMessageWithoutPrefix("\u00a77\u00a7m\u00a7l----------------------------------");
        } else {
            Helper.sendMessage("invalid syntax Valid .help");
        }
        return null;
    }
}

