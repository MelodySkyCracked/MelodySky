/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.module.modules.render;

import xyz.Melody.Client;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class XRay
extends Module {
    private static XRay INSTANCE;

    public XRay() {
        super("XRay", new String[]{"xray"}, ModuleType.Render);
        this.setModInfo("Make the blocks seems transparent.");
    }

    public static XRay getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = (XRay)Client.instance.getModuleManager().getModuleByClass(XRay.class);
        }
        return INSTANCE;
    }

    @Override
    public void onEnable() {
        this.mc.field_71438_f.func_72712_a();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.mc.field_71438_f.func_72712_a();
        super.onDisable();
    }
}

