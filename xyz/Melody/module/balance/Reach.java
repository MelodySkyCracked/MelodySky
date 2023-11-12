/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.module.balance;

import xyz.Melody.Client;
import xyz.Melody.Event.value.Numbers;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class Reach
extends Module {
    private static Reach INSTANCE;
    public Numbers size = new Numbers("Size", 3.5, 0.0, 6.0, 0.1);

    public Reach() {
        super("Reach", new String[]{"hitBox"}, ModuleType.Balance);
        this.addValues(this.size);
    }

    public static Reach getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = (Reach)Client.instance.getModuleManager().getModuleByClass(Reach.class);
        }
        return INSTANCE;
    }
}

