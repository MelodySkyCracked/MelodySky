/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.module.modules.QOL.Swappings;

import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.misc.EventKey;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.Utils.Item.ItemUtils;
import xyz.Melody.Utils.timer.TimerUtil;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class AOTS
extends Module {
    private boolean shouldSwitch;
    private TimerUtil timer = new TimerUtil();

    public AOTS() {
        super("AOTS", new String[]{"aots"}, ModuleType.Swapping);
        this.setModInfo("Auto Swap Axe Of the Shredded.");
    }

    @EventHandler
    private void onTick(EventTick eventTick) {
        if (this.shouldSwitch && this.timer.hasReached(200.0)) {
            ItemUtils.useSBItem("AXE_OF_THE_SHREDDED");
            this.timer.reset();
        }
        this.shouldSwitch = false;
    }

    @EventHandler
    private void onKey(EventKey eventKey) {
        if (eventKey.getKey() == this.getKey()) {
            this.shouldSwitch = true;
            this.setEnabled(true);
        }
    }
}

