/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.GUI.Animate;

import java.awt.Color;
import xyz.Melody.GUI.Animate.Opacity;

public class ColorTranslator {
    private Color color = new Color(0);
    private Opacity rT = new Opacity(0);
    private Opacity gT = new Opacity(0);
    private Opacity bT = new Opacity(0);
    private Opacity aT = new Opacity(0);

    public ColorTranslator(int n) {
        this.color = new Color(n);
    }

    public ColorTranslator(Color color) {
        this.color = color;
    }

    public Color interpolate(int n, float f) {
        return this.interpolate(new Color(n), f);
    }

    public Color interpolate(Color color, float f) {
        Color color2 = color;
        this.rT.interp(color2.getRed(), f);
        this.gT.interp(color2.getGreen(), f);
        this.bT.interp(color2.getBlue(), f);
        this.aT.interp(color2.getAlpha(), f);
        this.color = new Color((int)this.rT.getOpacity(), (int)this.gT.getOpacity(), (int)this.bT.getOpacity(), (int)this.aT.getOpacity());
        return this.color;
    }

    public Color getColor() {
        return this.color;
    }
}

