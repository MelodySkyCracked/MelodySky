/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.module.modules.others;

import xyz.Melody.Utils.MouseUtils;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class UnGrabMouse
extends Module {
    public UnGrabMouse() {
        super("UngrabMouse", new String[]{"ugm", "ungrabm", "ungrabmouse"}, ModuleType.Others);
    }

    @Override
    public void onEnable() {
        MouseUtils.ungrabMouse();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        MouseUtils.regrabMouse();
        super.onDisable();
    }
}

