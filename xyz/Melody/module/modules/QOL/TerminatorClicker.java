/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Mouse
 */
package xyz.Melody.module.modules.QOL;

import java.awt.Color;
import org.lwjgl.input.Mouse;
import xyz.Melody.Client;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.Event.value.Numbers;
import xyz.Melody.Event.value.Option;
import xyz.Melody.Utils.Item.ItemUtils;
import xyz.Melody.Utils.math.MathUtil;
import xyz.Melody.Utils.timer.TimerUtil;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class TerminatorClicker
extends Module {
    private TimerUtil timer = new TimerUtil();
    private Option juju = new Option("Juju", false);
    private Numbers cps = new Numbers("CPS", 14.0, 10.0, 20.0, 0.1);

    public TerminatorClicker() {
        super("TerminatorClicker", new String[]{"tc"}, ModuleType.QOL);
        this.addValues(this.juju, this.cps);
        this.setColor(new Color(191, 191, 191).getRGB());
        this.setModInfo("Auto Right Click Terminator.");
    }

    @EventHandler
    private void onTick(EventTick eventTick) {
        if (this.mc.field_71439_g.func_70694_bm() == null) {
            return;
        }
        String string = ItemUtils.getSkyBlockID(this.mc.field_71439_g.func_70694_bm());
        if (string.equals("TERMINATOR") || ((Boolean)this.juju.getValue()).booleanValue() && string.equals("JUJU_SHORTBOW")) {
            float f = (float)(1000.0 / ((double)((Double)this.cps.getValue()).floatValue() + MathUtil.randomDouble(-2.0, 2.0)));
            if (Mouse.isButtonDown((int)1) && this.timer.hasReached(f) && this.mc.field_71462_r == null) {
                Client.rightClick();
                this.timer.reset();
            }
        }
    }
}

