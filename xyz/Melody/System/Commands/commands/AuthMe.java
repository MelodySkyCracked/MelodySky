/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.System.Commands.commands;

import xyz.Melody.Client;
import xyz.Melody.System.Commands.Command;
import xyz.Melody.Utils.Helper;

public final class AuthMe
extends Command {
    @Override
    public String execute(String[] stringArray) {
        if (Client.instance.authManager.verified) {
            Helper.sendMessage("[AUTHENTICATION] Already Verified.");
            return null;
        }
        if (Client.instance.authManager.authMe(this.mc.func_110432_I().func_148256_e().getId().toString(), this.mc.func_110432_I().func_111285_a())) {
            Helper.sendMessage("[AUTHENTICATION] Success.");
        } else {
            Helper.sendMessage("[AUTHENTICATION] Failed.");
        }
        return null;
    }

    public AuthMe() {
        super("authme", new String[]{"verifyme", "auth"}, "", "sketit");
    }
}

