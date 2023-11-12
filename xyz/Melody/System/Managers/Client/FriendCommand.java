/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.EnumChatFormatting
 */
package xyz.Melody.System.Managers.Client;

import net.minecraft.util.EnumChatFormatting;
import xyz.Melody.Client;
import xyz.Melody.System.Commands.Command;
import xyz.Melody.System.Managers.Client.FriendManager;
import xyz.Melody.Utils.Helper;

class FriendCommand
extends Command {
    public FriendCommand(String string, String[] stringArray) {
        super(string, stringArray, "", "");
    }

    @Override
    public String execute(String[] stringArray) {
        if (stringArray.length >= 3) {
            if (stringArray[0].equalsIgnoreCase("add")) {
                FriendManager.getFriends().put(stringArray[1], stringArray[2]);
                Helper.sendMessage("> " + String.format("%s has been added as %s", stringArray[1], stringArray[2]));
                Client.instance.config.miscConfig.save();
            } else if (stringArray[0].equalsIgnoreCase("del")) {
                FriendManager.getFriends().remove(stringArray[1]);
                Helper.sendMessage("> " + String.format("%s has been removed from your friends list", stringArray[1]));
            } else if (stringArray[0].equalsIgnoreCase("list")) {
                if (FriendManager.getFriends().size() > 0) {
                    int n = 1;
                    for (String string : FriendManager.getFriends().values()) {
                        Helper.sendMessage("> " + String.format("%s. %s", n, string));
                        ++n;
                    }
                } else {
                    Helper.sendMessage("> get some friends fag lmao");
                }
            }
        } else if (stringArray.length == 2) {
            if (stringArray[0].equalsIgnoreCase("add")) {
                FriendManager.getFriends().put(stringArray[1], stringArray[1]);
                Helper.sendMessage("> " + String.format("%s has been added as %s", stringArray[1], stringArray[1]));
                Client.instance.config.miscConfig.save();
            } else if (stringArray[0].equalsIgnoreCase("del")) {
                FriendManager.getFriends().remove(stringArray[1]);
                Helper.sendMessage("> " + String.format("%s has been removed from your friends list", stringArray[1]));
            } else if (stringArray[0].equalsIgnoreCase("list")) {
                if (FriendManager.getFriends().size() > 0) {
                    int n = 1;
                    for (String string : FriendManager.getFriends().values()) {
                        Helper.sendMessage("> " + String.format("%s. %s", n, string));
                        ++n;
                    }
                } else {
                    Helper.sendMessage("> you dont have any you lonely fuck");
                }
            }
        } else if (stringArray.length == 1) {
            if (stringArray[0].equalsIgnoreCase("list")) {
                if (FriendManager.getFriends().size() > 0) {
                    int n = 1;
                    for (String string : FriendManager.getFriends().values()) {
                        Helper.sendMessage(String.format("%s. %s", n, string));
                        ++n;
                    }
                } else {
                    Helper.sendMessage("you dont have any you lonely fuck");
                }
            } else if (!stringArray[0].equalsIgnoreCase("add") && !stringArray[0].equalsIgnoreCase("del")) {
                Helper.sendMessage("> Correct usage: " + EnumChatFormatting.GRAY + "Valid .f add/del <player>");
            } else {
                Helper.sendMessage("> " + EnumChatFormatting.GRAY + "Please enter a players name");
            }
        } else if (stringArray.length == 0) {
            Helper.sendMessage("> Correct usage: " + EnumChatFormatting.GRAY + "Valid .f add/del <player>");
        }
        return null;
    }
}

