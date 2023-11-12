/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.EnumChatFormatting
 */
package xyz.Melody.System.Commands.commands;

import net.minecraft.util.EnumChatFormatting;
import xyz.Melody.Client;
import xyz.Melody.System.Commands.Command;
import xyz.Melody.Utils.Helper;
import xyz.Melody.module.Module;

public final class Toggle
extends Command {
    public Toggle() {
        super("t", new String[]{"toggle", "togl", "turnon", "enable"}, "", "Toggles a specified Module");
    }

    @Override
    public String execute(String[] stringArray) {
        if (stringArray.length <= 1 && stringArray.length < 1) {
            Helper.sendMessageWithoutPrefix("\u00a7bCorrect usage:\u00a77 .t <module>");
        }
        boolean bl = false;
        Module module = Client.instance.getModuleManager().getAlias(stringArray[0]);
        if (module != null) {
            if (!module.isEnabled()) {
                module.setEnabled(true);
            } else {
                module.setEnabled(false);
            }
            bl = true;
            if (module.isEnabled()) {
                Helper.sendMessage("> " + module.getName() + EnumChatFormatting.GRAY + " was" + EnumChatFormatting.GREEN + " enabled");
            } else {
                Helper.sendMessage("> " + module.getName() + EnumChatFormatting.GRAY + " was" + EnumChatFormatting.RED + " disabled");
            }
        }
        if (!bl) {
            Helper.sendMessage("> Module name " + EnumChatFormatting.RED + stringArray[0] + EnumChatFormatting.GRAY + " is invalid");
        }
        return null;
    }
}

