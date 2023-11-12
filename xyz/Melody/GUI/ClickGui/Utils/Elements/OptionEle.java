/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.GUI.ClickGui.Utils.Elements;

import java.awt.Color;
import xyz.Melody.Event.value.Option;
import xyz.Melody.GUI.Animate.ColorTranslator;
import xyz.Melody.GUI.Animate.NonlinearOpacity;
import xyz.Melody.GUI.ClickGui.Utils.Elements.Element;
import xyz.Melody.GUI.Font.FontLoaders;
import xyz.Melody.Utils.render.RenderUtil;

public class OptionEle
implements Element {
    private float xStart = 0.0f;
    private float yStart = 0.0f;
    private float xEnd = 0.0f;
    private float yEnd = 0.0f;
    private Option option;
    private String name;
    private float circleX = 0.0f;
    private NonlinearOpacity circleTranslator = new NonlinearOpacity(0);
    private ColorTranslator ct;

    public OptionEle(Option option) {
        this.option = option;
        this.name = option.getName();
        Color color = new Color(100, 100, 100);
        Color color2 = new Color(80, 80, 180);
        this.ct = new ColorTranslator((Boolean)this.option.getValue() != false ? color2.getRGB() : color.getRGB());
    }

    public Option getOption() {
        return this.option;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public void reset() {
        this.circleX = 0.0f;
        this.circleTranslator = new NonlinearOpacity(0);
    }

    @Override
    public void draw(float f, float f2, float f3, int n, float f4) {
        float f5 = f2 + 75.0f + (float)n + f4;
        this.xStart = f3 - 65.0f;
        this.yStart = f5 - 3.0f;
        this.xEnd = f3 - 35.0f;
        this.yEnd = f5 + 14.0f;
        FontLoaders.NMSL24.drawString(this.name, f + 20.0f, f5, -1);
        Color color = new Color(100, 100, 100);
        Color color2 = new Color(240, 240, 240);
        Color color3 = new Color(80, 80, 180);
        boolean bl = (Boolean)this.option.getValue();
        RenderUtil.drawSwitcherBG(this.xEnd - 15.0f, f5 + 6.0f, 6.3f, this.ct.interpolate(bl ? color3 : color, 6.0f));
        RenderUtil.drawFilledCircle(this.xEnd - 22.0f + this.circleX, f5 + 6.0f, 5.0f, bl ? color2 : color2);
        if (bl) {
            this.circleTranslator.interp(14.0f, 2.0f);
        } else {
            this.circleTranslator.interp(0.0f, 2.0f);
        }
        this.circleX = this.circleTranslator.getOpacity();
    }

    @Override
    public void handleMouseActions(float f, float f2) {
        if (f > this.xStart && f < this.xEnd && f2 > this.yStart && f2 < this.yEnd) {
            this.setOption((Boolean)this.option.getValue() == false);
        }
    }

    public void setOption(boolean bl) {
        this.option.setValue(bl);
    }
}

