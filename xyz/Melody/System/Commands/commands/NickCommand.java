/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.System.Commands.commands;

import xyz.Melody.Client;
import xyz.Melody.System.Commands.Command;
import xyz.Melody.Utils.Helper;

public final class NickCommand
extends Command {
    public NickCommand() {
        super(".nick", new String[]{"name", "nick"}, "", "sketit");
    }

    @Override
    public String execute(String[] stringArray) {
        if (stringArray.length >= 1) {
            if (stringArray[0].toLowerCase().equals("reset")) {
                Client.instance.setNickForCur(null);
                Helper.sendMessage("Reset Nick Name to " + Client.instance.getNickForCur() + ".");
                Client.instance.config.miscConfig.save();
                return null;
            }
            String string = "";
            for (int i = 0; i < stringArray.length; ++i) {
                string = i == stringArray.length - 1 ? string + stringArray[i] : string + stringArray[i] + " ";
            }
            String string2 = string.replaceAll("&", "\u00a7");
            Client.instance.setNickForCur(string2);
            Helper.sendMessage("Set Nick Name to " + Client.instance.getNickForCur() + ".");
            Client.instance.config.miscConfig.save();
            return null;
        }
        Helper.sendMessage("Correct Useage: .nick [name] / .nick reset");
        return null;
    }
}

