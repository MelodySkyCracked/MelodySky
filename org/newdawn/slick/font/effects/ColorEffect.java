/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.font.effects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.Glyph;
import org.newdawn.slick.font.effects.ConfigurableEffect;
import org.newdawn.slick.font.effects.EffectUtil;

public class ColorEffect
implements ConfigurableEffect {
    private Color color = Color.white;

    public ColorEffect() {
    }

    public ColorEffect(Color color) {
        this.color = color;
    }

    @Override
    public void draw(BufferedImage bufferedImage, Graphics2D graphics2D, UnicodeFont unicodeFont, Glyph glyph) {
        graphics2D.setColor(this.color);
        graphics2D.fill(glyph.getShape());
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        if (color == null) {
            throw new IllegalArgumentException("color cannot be null.");
        }
        this.color = color;
    }

    public String toString() {
        return "Color";
    }

    @Override
    public List getValues() {
        ArrayList<ConfigurableEffect.Value> arrayList = new ArrayList<ConfigurableEffect.Value>();
        arrayList.add(EffectUtil.colorValue("Color", this.color));
        return arrayList;
    }

    @Override
    public void setValues(List list) {
        for (ConfigurableEffect.Value value : list) {
            if (!value.getName().equals("Color")) continue;
            this.setColor((Color)value.getObject());
        }
    }
}

