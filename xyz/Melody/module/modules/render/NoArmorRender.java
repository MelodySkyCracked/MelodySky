/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.module.modules.render;

import xyz.Melody.Client;
import xyz.Melody.Event.value.Option;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class NoArmorRender
extends Module {
    public Option selfOnly = new Option("SelfOnly", true);
    public Option chead = new Option("HideCustomHead", true);
    public Option armor = new Option("HideArmor", true);
    public Option arrows = new Option("HideArrows", true);
    private static NoArmorRender INSTANCE;

    public NoArmorRender() {
        super("NoArmorRender", new String[]{"armor"}, ModuleType.Render);
        this.addValues(this.selfOnly, this.chead, this.armor, this.arrows);
        this.setModInfo("Armor Invisible, Hide Arrows on Your Body.");
    }

    public static NoArmorRender getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = (NoArmorRender)Client.instance.getModuleManager().getModuleByClass(NoArmorRender.class);
        }
        return INSTANCE;
    }
}

