/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.module.modules.others;

import xyz.Melody.Client;
import xyz.Melody.Event.value.Numbers;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class PlayerList
extends Module {
    public Numbers scanDelay = new Numbers("ScanDelay", 500.0, 100.0, 2000.0, 10.0);
    public Numbers range = new Numbers("Range", 30.0, 20.0, 100.0, 5.0);
    private static PlayerList INSTANCE;

    public PlayerList() {
        super("PlayerList", new String[]{"cc", "ccm", "command"}, ModuleType.Others);
        this.addValues(this.scanDelay, this.range);
    }

    public static PlayerList getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = (PlayerList)Client.instance.getModuleManager().getModuleByClass(PlayerList.class);
        }
        return INSTANCE;
    }
}

