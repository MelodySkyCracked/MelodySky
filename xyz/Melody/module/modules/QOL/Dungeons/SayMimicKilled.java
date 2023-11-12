/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.module.modules.QOL.Dungeons;

import xyz.Melody.Client;
import xyz.Melody.Event.value.Mode;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class SayMimicKilled
extends Module {
    public Mode mode = new Mode("Mode", Chats.values(), Chats.Party);
    private static SayMimicKilled INSTANCE;

    public SayMimicKilled() {
        super("SayMimicKilled", new String[]{"as"}, ModuleType.Dungeons);
        this.addValues(this.mode);
        this.setModInfo("Say Mimic Killed When Mimic dead.");
    }

    public static SayMimicKilled getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = (SayMimicKilled)Client.instance.getModuleManager().getModuleByClass(SayMimicKilled.class);
        }
        return INSTANCE;
    }

    public static enum Chats {
        Party,
        All;

    }
}

