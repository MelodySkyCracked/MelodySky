/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.ScaledResolution
 */
package xyz.Melody.GUI.CustomUI.Functions;

import java.awt.Color;
import net.minecraft.client.gui.ScaledResolution;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.rendering.EventRender2D;
import xyz.Melody.GUI.CustomUI.HUDApi;
import xyz.Melody.GUI.CustomUI.HUDScreen;

public final class BigRat
extends HUDApi {
    private float xxx = 0.0f;
    public final String[] rat = new String[]{"                   ..----.._                  ", "                .' .--.    '-.(O)_            ", "    '-.__.-'''=:|  ,  _)_ |__ . c'-..        ", "                 ''------'---''---'-'         "};

    public BigRat() {
        super("RainbowRat", 10, 200);
        this.setEnabled(true);
    }

    @EventHandler
    public void onRender(EventRender2D eventRender2D) {
        if (this.mc.field_71462_r instanceof HUDScreen) {
            return;
        }
        this.RAT();
    }

    @Override
    public void InScreenRender() {
        this.RAT();
    }

    private void RAT() {
        float f = new ScaledResolution(this.mc).func_78326_a();
        if (this.xxx < f) {
            this.xxx += 1.0f;
        }
        if (this.xxx >= f) {
            this.xxx = -200.0f;
        }
        if (this.mc.field_71462_r instanceof HUDScreen) {
            this.xxx = 0.0f;
        }
        this.mc.field_71466_p.func_78276_b("\u00a7l" + this.rat[0], (int)this.xxx, this.y + 2, this.rainbow());
        this.mc.field_71466_p.func_78276_b("\u00a7l" + this.rat[1], (int)this.xxx, this.y + 14, this.rainbow());
        this.mc.field_71466_p.func_78276_b("\u00a7l" + this.rat[2], (int)this.xxx, this.y + 26, this.rainbow());
        this.mc.field_71466_p.func_78276_b("\u00a7l" + this.rat[3], (int)this.xxx, this.y + 38, this.rainbow());
    }

    private int rainbow() {
        float f = (float)(System.currentTimeMillis() % 3000L) / 3000.0f;
        return Color.getHSBColor(f, 0.75f, 1.0f).getRGB();
    }
}

