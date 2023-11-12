/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.module.modules.QOL.Swappings;

import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.misc.EventKey;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.Event.value.Mode;
import xyz.Melody.Event.value.Numbers;
import xyz.Melody.Event.value.Option;
import xyz.Melody.Event.value.TextValue;
import xyz.Melody.Utils.Helper;
import xyz.Melody.Utils.Item.ItemUtils;
import xyz.Melody.Utils.timer.TimerUtil;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class ItemSwitcher
extends Module {
    private boolean shouldSwitch;
    private TextValue customItemID = new TextValue("CustomSBID", (Object)"TERMINATOR");
    private TimerUtil timer = new TimerUtil();
    private Option auto = new Option("Auto", false);
    private Option custom = new Option("Custom", false);
    private Option left = new Option("LeftClick", false);
    private Mode type = new Mode("Item", itemType.values(), itemType.AOTS);
    private Numbers delay = new Numbers("Delay/ms", 600.0, 50.0, 3000.0, 10.0);

    public ItemSwitcher() {
        super("ItemSwitcher", new String[]{"is", "1tick", "itemmacro"}, ModuleType.Swapping);
        this.addValues(this.auto, this.customItemID, this.custom, this.left, this.type, this.delay);
        this.setModInfo("Custom Item Swapping.");
    }

    @Override
    public void onEnable() {
        this.timer.reset();
    }

    @EventHandler
    private void underTick(EventTick eventTick) {
        if (!((Boolean)this.custom.getValue()).booleanValue()) {
            return;
        }
        if (this.customItemID == null && this.shouldSwitch) {
            Helper.sendMessage("Use <.is [ItemID]> to set value.");
            this.shouldSwitch = false;
            return;
        }
        if (((Boolean)this.auto.getValue()).booleanValue() && this.timer.hasReached(((Double)this.delay.getValue()).longValue())) {
            ItemUtils.useSBItem(((String)this.customItemID.getValue()).toUpperCase(), (Boolean)this.left.getValue());
            this.timer.reset();
            return;
        }
        if (this.shouldSwitch && !((Boolean)this.auto.getValue()).booleanValue()) {
            ItemUtils.useSBItem(((String)this.customItemID.getValue()).toUpperCase(), (Boolean)this.left.getValue());
            this.shouldSwitch = false;
        }
    }

    @EventHandler
    private void onTick(EventTick eventTick) {
        if (((Boolean)this.custom.getValue()).booleanValue()) {
            return;
        }
        String string = ((Enum)this.type.getValue()).toString();
        if (((Boolean)this.auto.getValue()).booleanValue() && this.timer.hasReached(((Double)this.delay.getValue()).longValue())) {
            switch (string) {
                case "AOTS": {
                    ItemUtils.useSBItem("AXE_OF_THE_SHREDDED", (Boolean)this.left.getValue());
                    break;
                }
                case "Ice_Spray": {
                    ItemUtils.useSBItem("ICE_SPRAY_WAND", (Boolean)this.left.getValue());
                    break;
                }
                case "Soul_Whip": {
                    ItemUtils.useSBItem("SOUL_WHIP", (Boolean)this.left.getValue());
                    break;
                }
                case "Juju": {
                    ItemUtils.useSBItem("JUJU_SHORTBOW", (Boolean)this.left.getValue());
                    break;
                }
                case "Terminator": {
                    ItemUtils.useSBItem("TERMINATOR", (Boolean)this.left.getValue());
                    break;
                }
                case "AOTV": {
                    ItemUtils.useSBItem("ASPECT_OF_THE_VOID", (Boolean)this.left.getValue());
                }
            }
            this.timer.reset();
        }
        if (this.shouldSwitch && !((Boolean)this.auto.getValue()).booleanValue()) {
            switch (string) {
                case "AOTS": {
                    ItemUtils.useSBItem("AXE_OF_THE_SHREDDED", (Boolean)this.left.getValue());
                    break;
                }
                case "Ice_Spray": {
                    ItemUtils.useSBItem("ICE_SPRAY_WAND", (Boolean)this.left.getValue());
                    break;
                }
                case "Soul_Whip": {
                    ItemUtils.useSBItem("SOUL_WHIP", (Boolean)this.left.getValue());
                    break;
                }
                case "Juju": {
                    ItemUtils.useSBItem("JUJU_SHORTBOW", (Boolean)this.left.getValue());
                    break;
                }
                case "Terminator": {
                    ItemUtils.useSBItem("TERMINATOR", (Boolean)this.left.getValue());
                    break;
                }
                case "AOTV": {
                    ItemUtils.useSBItem("ASPECT_OF_THE_VOID", (Boolean)this.left.getValue());
                }
            }
            this.shouldSwitch = false;
        }
    }

    @EventHandler
    private void onKey(EventKey eventKey) {
        if (eventKey.getKey() == this.getKey() && !((Boolean)this.auto.getValue()).booleanValue()) {
            this.shouldSwitch = true;
            this.setEnabled(true);
        }
    }

    @Override
    public void onDisable() {
        this.timer.reset();
    }

    static enum itemType {
        Ice_Spray,
        AOTS,
        Soul_Whip,
        Juju,
        Terminator,
        AOTV;

    }
}

