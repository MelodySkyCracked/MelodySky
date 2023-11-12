/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick;

import java.io.Serializable;
import java.nio.FloatBuffer;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;

public class Color
implements Serializable {
    private static final long serialVersionUID = 1393939L;
    protected transient SGL GL = Renderer.get();
    public static final Color transparent = new Color(0.0f, 0.0f, 0.0f, 0.0f);
    public static final Color white = new Color(1.0f, 1.0f, 1.0f, 1.0f);
    public static final Color yellow = new Color(1.0f, 1.0f, 0.0f, 1.0f);
    public static final Color red = new Color(1.0f, 0.0f, 0.0f, 1.0f);
    public static final Color blue = new Color(0.0f, 0.0f, 1.0f, 1.0f);
    public static final Color green = new Color(0.0f, 1.0f, 0.0f, 1.0f);
    public static final Color black = new Color(0.0f, 0.0f, 0.0f, 1.0f);
    public static final Color gray = new Color(0.5f, 0.5f, 0.5f, 1.0f);
    public static final Color cyan = new Color(0.0f, 1.0f, 1.0f, 1.0f);
    public static final Color darkGray = new Color(0.3f, 0.3f, 0.3f, 1.0f);
    public static final Color lightGray = new Color(0.7f, 0.7f, 0.7f, 1.0f);
    public static final Color pink = new Color(255, 175, 175, 255);
    public static final Color orange = new Color(255, 200, 0, 255);
    public static final Color magenta = new Color(255, 0, 255, 255);
    public float r;
    public float g;
    public float b;
    public float a = 1.0f;

    public Color(Color color) {
        this.r = color.r;
        this.g = color.g;
        this.b = color.b;
        this.a = color.a;
    }

    public Color(FloatBuffer floatBuffer) {
        this.r = floatBuffer.get();
        this.g = floatBuffer.get();
        this.b = floatBuffer.get();
        this.a = floatBuffer.get();
    }

    public Color(float f, float f2, float f3) {
        this.r = f;
        this.g = f2;
        this.b = f3;
        this.a = 1.0f;
    }

    public Color(float f, float f2, float f3, float f4) {
        this.r = Math.min(f, 1.0f);
        this.g = Math.min(f2, 1.0f);
        this.b = Math.min(f3, 1.0f);
        this.a = Math.min(f4, 1.0f);
    }

    public Color(int n, int n2, int n3) {
        this.r = (float)n / 255.0f;
        this.g = (float)n2 / 255.0f;
        this.b = (float)n3 / 255.0f;
        this.a = 1.0f;
    }

    public Color(int n, int n2, int n3, int n4) {
        this.r = (float)n / 255.0f;
        this.g = (float)n2 / 255.0f;
        this.b = (float)n3 / 255.0f;
        this.a = (float)n4 / 255.0f;
    }

    public Color(int n) {
        int n2 = (n & 0xFF0000) >> 16;
        int n3 = (n & 0xFF00) >> 8;
        int n4 = n & 0xFF;
        int n5 = (n & 0xFF000000) >> 24;
        if (n5 < 0) {
            n5 += 256;
        }
        if (n5 == 0) {
            n5 = 255;
        }
        this.r = (float)n2 / 255.0f;
        this.g = (float)n3 / 255.0f;
        this.b = (float)n4 / 255.0f;
        this.a = (float)n5 / 255.0f;
    }

    public static Color decode(String string) {
        return new Color(Integer.decode(string));
    }

    public void bind() {
        this.GL.glColor4f(this.r, this.g, this.b, this.a);
    }

    public int hashCode() {
        return (int)(this.r + this.g + this.b + this.a) * 255;
    }

    public boolean equals(Object object) {
        if (object instanceof Color) {
            Color color = (Color)object;
            return color.r == this.r && color.g == this.g && color.b == this.b && color.a == this.a;
        }
        return false;
    }

    public String toString() {
        return "Color (" + this.r + "," + this.g + "," + this.b + "," + this.a + ")";
    }

    public Color darker() {
        return this.darker(0.5f);
    }

    public Color darker(float f) {
        f = 1.0f - f;
        Color color = new Color(this.r * f, this.g * f, this.b * f, this.a);
        return color;
    }

    public Color brighter() {
        return this.brighter(0.2f);
    }

    public int getRed() {
        return (int)(this.r * 255.0f);
    }

    public int getGreen() {
        return (int)(this.g * 255.0f);
    }

    public int getBlue() {
        return (int)(this.b * 255.0f);
    }

    public int getAlpha() {
        return (int)(this.a * 255.0f);
    }

    public int getRedByte() {
        return (int)(this.r * 255.0f);
    }

    public int getGreenByte() {
        return (int)(this.g * 255.0f);
    }

    public int getBlueByte() {
        return (int)(this.b * 255.0f);
    }

    public int getAlphaByte() {
        return (int)(this.a * 255.0f);
    }

    public Color brighter(float f) {
        Color color = new Color(this.r * (f += 1.0f), this.g * f, this.b * f, this.a);
        return color;
    }

    public Color multiply(Color color) {
        return new Color(this.r * color.r, this.g * color.g, this.b * color.b, this.a * color.a);
    }

    public void add(Color color) {
        this.r += color.r;
        this.g += color.g;
        this.b += color.b;
        this.a += color.a;
    }

    public void scale(float f) {
        this.r *= f;
        this.g *= f;
        this.b *= f;
        this.a *= f;
    }

    public Color addToCopy(Color color) {
        Color color2 = new Color(this.r, this.g, this.b, this.a);
        color2.r += color.r;
        color2.g += color.g;
        color2.b += color.b;
        color2.a += color.a;
        return color2;
    }

    public Color scaleCopy(float f) {
        Color color = new Color(this.r, this.g, this.b, this.a);
        color.r *= f;
        color.g *= f;
        color.b *= f;
        color.a *= f;
        return color;
    }
}

