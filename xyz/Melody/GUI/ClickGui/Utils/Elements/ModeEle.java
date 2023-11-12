/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.GUI.ClickGui.Utils.Elements;

import java.awt.Color;
import xyz.Melody.Event.value.Mode;
import xyz.Melody.GUI.ClickGui.Utils.Elements.Element;
import xyz.Melody.GUI.Font.FontLoaders;
import xyz.Melody.Utils.render.RenderUtil;

public class ModeEle
implements Element {
    private float xStart1 = 0.0f;
    private float yStart1 = 0.0f;
    private float xEnd1 = 0.0f;
    private float yEnd1 = 0.0f;
    private float xStart2 = 0.0f;
    private float yStart2 = 0.0f;
    private float xEnd2 = 0.0f;
    private float yEnd2 = 0.0f;
    private Mode mode;
    private String name;

    public ModeEle(Mode mode) {
        this.mode = mode;
        this.name = mode.getName();
    }

    public String getName() {
        return this.name;
    }

    @Override
    public void reset() {
        this.name = this.mode.getName();
    }

    @Override
    public void draw(float f, float f2, float f3, int n, float f4) {
        float f5 = f2 + 75.0f + (float)n + f4;
        this.xStart1 = f3 - 120.0f;
        this.yStart1 = f5 - 3.0f;
        this.xEnd1 = f3 - 105.0f;
        this.yEnd1 = f5 + 14.0f;
        this.xStart2 = f3 - 40.0f;
        this.yStart2 = f5 - 3.0f;
        this.xEnd2 = f3 - 25.0f;
        this.yEnd2 = f5 + 14.0f;
        FontLoaders.NMSL24.drawString(this.name, f + 20.0f, f5, -1);
        RenderUtil.drawFastRoundedRect(this.xStart1, f5 - 3.0f, this.xEnd1, f5 + 14.0f, 3.0f, new Color(80, 80, 80, 110).getRGB());
        RenderUtil.drawFastRoundedRect(this.xStart2, f5 - 3.0f, this.xEnd2, f5 + 14.0f, 3.0f, new Color(80, 80, 80, 80).getRGB());
        RenderUtil.drawFastRoundedRect(this.xStart1, f5 - 3.0f, this.xEnd2, f5 + 14.0f, 3.0f, new Color(80, 80, 80, 80).getRGB());
        FontLoaders.NMSL22.drawCenteredString(((Enum)this.mode.getValue()).toString(), (this.xStart1 + this.xEnd2) / 2.0f, f5 + 1.0f, -1);
        FontLoaders.NMSL22.drawCenteredString("<", this.xStart1 + 8.0f, f5 + 1.0f, -1);
        FontLoaders.NMSL22.drawCenteredString(">", this.xStart2 + 8.0f, f5 + 1.0f, -1);
    }

    @Override
    public void handleMouseActions(float f, float f2) {
        if (f > this.xStart1 && f < this.xEnd1 && f2 > this.yStart1 && f2 < this.yEnd1) {
            this.set(this.previous());
        }
        if (f > this.xStart2 && f < this.xEnd2 && f2 > this.yStart2 && f2 < this.yEnd2) {
            this.set(this.next());
        }
    }

    private Enum next() {
        Enum enum_ = (Enum)this.mode.getValue();
        int n = enum_.ordinal() + 1 >= this.mode.getModes().length ? 0 : enum_.ordinal() + 1;
        return this.mode.getModes()[n];
    }

    private Enum previous() {
        Enum enum_ = (Enum)this.mode.getValue();
        int n = enum_.ordinal() == 0 ? this.mode.getModes().length - 1 : enum_.ordinal() - 1;
        return this.mode.getModes()[n];
    }

    private void set(Enum enum_) {
        this.mode.setValue(enum_);
    }
}

