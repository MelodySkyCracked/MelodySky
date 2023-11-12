/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.module.balance;

import xyz.Melody.Client;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class NoHitDelay
extends Module {
    private static NoHitDelay INSTANCE;

    public NoHitDelay() {
        super("NoHitDelay", new String[]{"hitdelay"}, ModuleType.Balance);
        this.setModInfo("1.8 Mouse Clicking QOL.");
    }

    public static NoHitDelay getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = (NoHitDelay)Client.instance.getModuleManager().getModuleByClass(NoHitDelay.class);
        }
        return INSTANCE;
    }
}

