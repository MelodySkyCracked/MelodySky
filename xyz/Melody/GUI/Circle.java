/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.GUI;

import java.awt.Color;
import xyz.Melody.GUI.Animate.Opacity;
import xyz.Melody.Utils.render.RenderUtil;

public final class Circle {
    public Opacity op = new Opacity(0);
    public float hmx = 0.0f;
    public float hmy = 0.0f;
    public float width;

    public Circle(int n, int n2, int n3) {
        this.hmx = n;
        this.hmy = n2;
        this.width = n3;
    }

    public void draw() {
        if (this.op.getOpacity() < this.width + 50.0f) {
            this.op.interp(this.width + 50.0f, 2.5f);
        } else {
            this.op.interp(0.0f, 0.0f);
        }
        if (this.op.getOpacity() > 0.0f) {
            int n = (int)(255.0f - this.op.getOpacity() * 2.5f / this.width * 70.0f);
            RenderUtil.drawFilledCircle(this.hmx, this.hmy, this.op.getOpacity(), new Color(255, 255, 255, n > 0 ? n : 0));
        }
    }
}

