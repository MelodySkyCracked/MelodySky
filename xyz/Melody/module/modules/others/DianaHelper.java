/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.module.modules.others;

import xyz.Melody.Client;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class DianaHelper
extends Module {
    private static DianaHelper INSTANCE;

    public DianaHelper() {
        super("DianaHelper", new String[]{"dh", "diana", "dhelper"}, ModuleType.Others);
        this.setModInfo("==== WIP ====");
    }

    public static DianaHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = (DianaHelper)Client.instance.getModuleManager().getModuleByClass(DianaHelper.class);
        }
        return INSTANCE;
    }
}

