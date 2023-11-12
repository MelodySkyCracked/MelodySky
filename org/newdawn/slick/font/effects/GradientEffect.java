/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.font.effects;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.Glyph;
import org.newdawn.slick.font.effects.ConfigurableEffect;
import org.newdawn.slick.font.effects.EffectUtil;

public class GradientEffect
implements ConfigurableEffect {
    private Color topColor = Color.cyan;
    private Color bottomColor = Color.blue;
    private int offset = 0;
    private float scale = 1.0f;
    private boolean cyclic;

    public GradientEffect() {
    }

    public GradientEffect(Color color, Color color2, float f) {
        this.topColor = color;
        this.bottomColor = color2;
        this.scale = f;
    }

    @Override
    public void draw(BufferedImage bufferedImage, Graphics2D graphics2D, UnicodeFont unicodeFont, Glyph glyph) {
        int n = unicodeFont.getAscent();
        float f = (float)n * this.scale;
        float f2 = (float)(-glyph.getYOffset() + unicodeFont.getDescent() + this.offset + n / 2) - f / 2.0f;
        graphics2D.setPaint(new GradientPaint(0.0f, f2, this.topColor, 0.0f, f2 + f, this.bottomColor, this.cyclic));
        graphics2D.fill(glyph.getShape());
    }

    public Color getTopColor() {
        return this.topColor;
    }

    public void setTopColor(Color color) {
        this.topColor = color;
    }

    public Color getBottomColor() {
        return this.bottomColor;
    }

    public void setBottomColor(Color color) {
        this.bottomColor = color;
    }

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int n) {
        this.offset = n;
    }

    public float getScale() {
        return this.scale;
    }

    public void setScale(float f) {
        this.scale = f;
    }

    public boolean isCyclic() {
        return this.cyclic;
    }

    public void setCyclic(boolean bl) {
        this.cyclic = bl;
    }

    public String toString() {
        return "Gradient";
    }

    @Override
    public List getValues() {
        ArrayList<ConfigurableEffect.Value> arrayList = new ArrayList<ConfigurableEffect.Value>();
        arrayList.add(EffectUtil.colorValue("Top color", this.topColor));
        arrayList.add(EffectUtil.colorValue("Bottom color", this.bottomColor));
        arrayList.add(EffectUtil.intValue("Offset", this.offset, "This setting allows you to move the gradient up or down. The gradient is normally centered on the glyph."));
        arrayList.add(EffectUtil.floatValue("Scale", this.scale, 0.0f, 1.0f, "This setting allows you to change the height of the gradient by apercentage. The gradient is normally the height of most glyphs in the font."));
        arrayList.add(EffectUtil.booleanValue("Cyclic", this.cyclic, "If this setting is checked, the gradient will repeat."));
        return arrayList;
    }

    @Override
    public void setValues(List list) {
        for (ConfigurableEffect.Value value : list) {
            if (value.getName().equals("Top color")) {
                this.topColor = (Color)value.getObject();
                continue;
            }
            if (value.getName().equals("Bottom color")) {
                this.bottomColor = (Color)value.getObject();
                continue;
            }
            if (value.getName().equals("Offset")) {
                this.offset = (Integer)value.getObject();
                continue;
            }
            if (value.getName().equals("Scale")) {
                this.scale = ((Float)value.getObject()).floatValue();
                continue;
            }
            if (!value.getName().equals("Cyclic")) continue;
            this.cyclic = (Boolean)value.getObject();
        }
    }
}

