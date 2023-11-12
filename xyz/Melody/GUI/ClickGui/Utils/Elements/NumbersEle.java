/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Mouse
 */
package xyz.Melody.GUI.ClickGui.Utils.Elements;

import java.awt.Color;
import java.text.DecimalFormat;
import org.lwjgl.input.Mouse;
import xyz.Melody.Event.value.Numbers;
import xyz.Melody.GUI.Animate.NonlinearOpacity;
import xyz.Melody.GUI.ClickGui.Utils.Elements.Element;
import xyz.Melody.GUI.Font.FontLoaders;
import xyz.Melody.Utils.render.RenderUtil;

public class NumbersEle
implements Element {
    private float xStart = 0.0f;
    private float yStart = 0.0f;
    private float xEnd = 0.0f;
    private float yEnd = 0.0f;
    private float xStart1 = 0.0f;
    private float yStart1 = 0.0f;
    private float xEnd1 = 0.0f;
    private float yEnd1 = 0.0f;
    private float xStart2 = 0.0f;
    private float yStart2 = 0.0f;
    private float xEnd2 = 0.0f;
    private float yEnd2 = 0.0f;
    private float circleX = 0.0f;
    private NonlinearOpacity circleTranslator = new NonlinearOpacity(0);
    private float current = 0.0f;
    private Numbers numbers;
    private String name;

    public NumbersEle(Numbers numbers) {
        this.numbers = numbers;
        this.name = this.numbers.getName();
    }

    public Numbers getNumbers() {
        return this.numbers;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public void reset() {
        this.circleX = 0.0f;
        this.current = 0.0f;
        this.circleTranslator = new NonlinearOpacity(0);
    }

    @Override
    public void draw(float f, float f2, float f3, int n, float f4) {
        float f5 = f2 + 75.0f + (float)n + f4;
        this.handleTunings(f3 - 150.0f, f2, f5);
        this.xStart = this.xEnd1;
        this.yStart = f5;
        this.xEnd = this.xStart2;
        this.yEnd = f5 + 10.0f;
        float f6 = ((Number)this.numbers.getValue()).floatValue();
        float f7 = this.numbers.getMinimum().floatValue();
        float f8 = this.numbers.getMaximum().floatValue();
        this.current = 95.0f * (f6 - f7) / (f8 - f7) + 5.0f;
        FontLoaders.NMSL24.drawString(this.name + ": " + ((Number)this.numbers.getValue()).toString(), f + 20.0f, f5, -1);
        Color color = new Color(140, 140, 180);
        Color color2 = new Color(140, 140, 140, 120);
        RenderUtil.drawLine(this.xEnd1, f5 + 5.0f, this.xStart2, f5 + 6.0f, 2.0f);
        RenderUtil.drawFastRoundedRect(this.xStart + this.circleX - 5.0f, f5 + 0.0f, this.xStart + 5.0f + this.circleX, f5 + 10.0f, 4.0f, color.getRGB());
        RenderUtil.drawFastRoundedRect(this.xStart1, f5 - 0.5f, this.xEnd1, f5 + 10.5f, 3.0f, color2.getRGB());
        RenderUtil.drawFastRoundedRect(this.xStart2, f5 - 0.5f, this.xEnd2, f5 + 10.5f, 3.0f, color2.getRGB());
        FontLoaders.NMSL22.drawCenteredString("<", this.xStart1 + 6.0f, f5 + 0.5f, -1);
        FontLoaders.NMSL22.drawCenteredString(">", this.xStart2 + 6.0f, f5 + 0.5f, -1);
        this.circleTranslator.interp(this.current, 4.0f);
        this.circleX = this.circleTranslator.getOpacity();
    }

    @Override
    public void handleMouseActions(float f, float f2) {
        float f3 = this.numbers.getIncrement().floatValue();
        float f4 = this.numbers.getMinimum().floatValue();
        float f5 = this.numbers.getMaximum().floatValue();
        if (f > this.xStart && f < this.xEnd && f2 > this.yStart && f2 < this.yEnd && Mouse.isButtonDown((int)0)) {
            float f6 = f4;
            double d = f - this.xEnd1 - 1.0f;
            double d2 = d / 102.0;
            d2 = Math.min(Math.max(0.0, d2), 1.0);
            double d3 = (double)(f5 - f6) * d2;
            double d4 = (double)f6 + d3;
            d4 = (float)Math.round(d4 * (double)(1.0f / f3)) / (1.0f / f3);
            DecimalFormat decimalFormat = new DecimalFormat("#.0");
            this.setNumbers(Double.parseDouble(decimalFormat.format(d4)));
        }
    }

    public void onPlusMinus(float f, float f2) {
        double d;
        DecimalFormat decimalFormat;
        float f3;
        float f4 = ((Number)this.numbers.getValue()).floatValue();
        float f5 = this.numbers.getIncrement().floatValue();
        float f6 = this.numbers.getMinimum().floatValue();
        float f7 = this.numbers.getMaximum().floatValue();
        if (f > this.xStart1 && f < this.xEnd1 && f2 > this.yStart1 && f2 < this.yEnd1 && f4 > f6) {
            f3 = f5;
            decimalFormat = new DecimalFormat("0.0");
            d = Double.parseDouble(decimalFormat.format(f4 - f3));
            this.setNumbers(d);
        }
        if (f > this.xStart2 && f < this.xEnd2 && f2 > this.yStart2 && f2 < this.yEnd2 && f4 < f7) {
            f3 = f5;
            decimalFormat = new DecimalFormat("0.0");
            d = Double.parseDouble(decimalFormat.format(f4 + f3));
            this.setNumbers(d);
        }
    }

    private void handleTunings(float f, float f2, float f3) {
        this.xStart1 = f;
        this.yStart1 = f3;
        this.xEnd1 = f + 11.0f;
        this.yEnd1 = f3 + 11.0f;
        this.xStart2 = f + 115.0f;
        this.yStart2 = f3;
        this.xEnd2 = f + 115.0f + 11.0f;
        this.yEnd2 = f3 + 11.0f;
    }

    public void setNumbers(double d) {
        this.numbers.setValue(d);
    }
}

