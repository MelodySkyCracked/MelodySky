/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.module.modules.QOL.Swappings;

import xyz.Melody.Event.value.Option;
import xyz.Melody.Utils.Item.ItemUtils;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class StrengthStack
extends Module {
    private Option WEIRD_TUBA = new Option("Weird_Tuba", true);
    private Option RAGNAROCK_AXE = new Option("Ragnarock_Axe", true);
    private Option SWORD_OF_BAD_HEALTH = new Option("Sword_of_bad_health", true);

    public StrengthStack() {
        super("StrengthStack", new String[]{"strs"}, ModuleType.Swapping);
        this.setModInfo("Auto Swap Axe Of the Shredded.");
        this.addValues(this.WEIRD_TUBA, this.RAGNAROCK_AXE, this.SWORD_OF_BAD_HEALTH);
    }

    @Override
    public void onEnable() {
        if (((Boolean)this.WEIRD_TUBA.getValue()).booleanValue()) {
            ItemUtils.useSBItem("WEIRD_TUBA");
        }
        if (((Boolean)this.RAGNAROCK_AXE.getValue()).booleanValue()) {
            ItemUtils.useSBItem("RAGNAROCK_AXE");
        }
        if (((Boolean)this.SWORD_OF_BAD_HEALTH.getValue()).booleanValue()) {
            ItemUtils.useSBItem("SWORD_OF_BAD_HEALTH");
        }
        this.setEnabled(false);
        super.onEnable();
    }
}

