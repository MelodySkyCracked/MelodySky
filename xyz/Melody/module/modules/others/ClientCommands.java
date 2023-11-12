/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.module.modules.others;

import xyz.Melody.Client;
import xyz.Melody.Event.value.Mode;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class ClientCommands
extends Module {
    public Mode mode = new Mode("Mode", commandMode.values(), commandMode.dot);
    private static ClientCommands INSTANCE;

    public ClientCommands() {
        super("ClientCommands", new String[]{"cc", "ccm", "command"}, ModuleType.Others);
        this.addValues(this.mode);
        this.setModInfo("dot: .xxx | bar: -xxx | wavy_line: ~xxx");
        this.setEnabled(true);
    }

    public static ClientCommands getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = (ClientCommands)Client.instance.getModuleManager().getModuleByClass(ClientCommands.class);
        }
        return INSTANCE;
    }

    static enum commandMode {
        dot,
        bar,
        wavy_line;

    }
}

