/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Mouse
 */
package xyz.Melody.module.modules.QOL.Swappings;

import org.lwjgl.input.Mouse;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.misc.EventKey;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.Event.value.Mode;
import xyz.Melody.Event.value.Option;
import xyz.Melody.Utils.Item.ItemUtils;
import xyz.Melody.Utils.timer.TimerUtil;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class SoulWhip
extends Module {
    private boolean shouldSwitch;
    private TimerUtil timer = new TimerUtil();
    private Mode mode = new Mode("Mouse", ddd.values(), ddd.Right);
    private Option mouse = new Option("MouseClick", false);
    private Option both = new Option("Both", false);

    public SoulWhip() {
        super("SoulWhip", new String[]{"aots"}, ModuleType.Swapping);
        this.addValues(this.mode, this.mouse, this.both);
        this.setModInfo("Auto Swap And Use Soul Whip.");
    }

    @EventHandler
    private void onMouse(EventTick eventTick) {
        int n;
        if (this.mc.field_71462_r != null) {
            return;
        }
        int n2 = n = this.mode.getValue() == ddd.Left ? 0 : 1;
        if (Mouse.isButtonDown((int)n) && (((Boolean)this.mouse.getValue()).booleanValue() || ((Boolean)this.both.getValue()).booleanValue())) {
            this.shouldSwitch = true;
        }
    }

    @EventHandler
    private void onTick(EventTick eventTick) {
        if (this.shouldSwitch && this.timer.hasReached(200.0)) {
            ItemUtils.useSBItem("SOUL_WHIP");
            this.timer.reset();
        }
        this.shouldSwitch = false;
    }

    @EventHandler
    private void onKey(EventKey eventKey) {
        if (eventKey.getKey() == this.getKey() && (!((Boolean)this.mouse.getValue()).booleanValue() || ((Boolean)this.both.getValue()).booleanValue())) {
            this.shouldSwitch = true;
            this.setEnabled(true);
        }
    }

    static enum ddd {
        Left,
        Right;

    }
}

