/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.fills;

import org.newdawn.slick.Color;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class GradientFill
implements ShapeFill {
    private Vector2f none = new Vector2f(0.0f, 0.0f);
    private Vector2f start;
    private Vector2f end;
    private Color startCol;
    private Color endCol;
    private boolean local = false;

    public GradientFill(float f, float f2, Color color, float f3, float f4, Color color2) {
        this(f, f2, color, f3, f4, color2, false);
    }

    public GradientFill(float f, float f2, Color color, float f3, float f4, Color color2, boolean bl) {
        this(new Vector2f(f, f2), color, new Vector2f(f3, f4), color2, bl);
    }

    public GradientFill(Vector2f vector2f, Color color, Vector2f vector2f2, Color color2, boolean bl) {
        this.start = new Vector2f(vector2f);
        this.end = new Vector2f(vector2f2);
        this.startCol = new Color(color);
        this.endCol = new Color(color2);
        this.local = bl;
    }

    public GradientFill getInvertedCopy() {
        return new GradientFill(this.start, this.endCol, this.end, this.startCol, this.local);
    }

    public void setLocal(boolean bl) {
        this.local = bl;
    }

    public Vector2f getStart() {
        return this.start;
    }

    public Vector2f getEnd() {
        return this.end;
    }

    public Color getStartColor() {
        return this.startCol;
    }

    public Color getEndColor() {
        return this.endCol;
    }

    public void setStart(float f, float f2) {
        this.setStart(new Vector2f(f, f2));
    }

    public void setStart(Vector2f vector2f) {
        this.start = new Vector2f(vector2f);
    }

    public void setEnd(float f, float f2) {
        this.setEnd(new Vector2f(f, f2));
    }

    public void setEnd(Vector2f vector2f) {
        this.end = new Vector2f(vector2f);
    }

    public void setStartColor(Color color) {
        this.startCol = new Color(color);
    }

    public void setEndColor(Color color) {
        this.endCol = new Color(color);
    }

    @Override
    public Color colorAt(Shape shape, float f, float f2) {
        if (this.local) {
            return this.colorAt(f - shape.getCenterX(), f2 - shape.getCenterY());
        }
        return this.colorAt(f, f2);
    }

    public Color colorAt(float f, float f2) {
        float f3;
        float f4;
        float f5 = this.end.getX() - this.start.getX();
        float f6 = f5;
        float f7 = f6 * f5 - (f4 = -(f3 = this.end.getY() - this.start.getY())) * f3;
        if (f7 == 0.0f) {
            return Color.black;
        }
        float f8 = f4 * (this.start.getY() - f2) - f6 * (this.start.getX() - f);
        float f9 = f8 /= f7;
        if (f9 < 0.0f) {
            f9 = 0.0f;
        }
        if (f9 > 1.0f) {
            f9 = 1.0f;
        }
        float f10 = 1.0f - f9;
        Color color = new Color(1, 1, 1, 1);
        color.r = f9 * this.endCol.r + f10 * this.startCol.r;
        color.b = f9 * this.endCol.b + f10 * this.startCol.b;
        color.g = f9 * this.endCol.g + f10 * this.startCol.g;
        color.a = f9 * this.endCol.a + f10 * this.startCol.a;
        return color;
    }

    @Override
    public Vector2f getOffsetAt(Shape shape, float f, float f2) {
        return this.none;
    }
}

