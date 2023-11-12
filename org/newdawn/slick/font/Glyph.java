/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.font;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.font.GlyphMetrics;
import java.awt.font.GlyphVector;
import org.newdawn.slick.Image;
import org.newdawn.slick.UnicodeFont;

public class Glyph {
    private int codePoint;
    private short width;
    private short height;
    private short yOffset;
    private boolean isMissing;
    private Shape shape;
    private Image image;

    public Glyph(int n, Rectangle rectangle, GlyphVector glyphVector, int n2, UnicodeFont unicodeFont) {
        int n3;
        this.codePoint = n;
        GlyphMetrics glyphMetrics = glyphVector.getGlyphMetrics(n2);
        int n4 = (int)glyphMetrics.getLSB();
        if (n4 > 0) {
            n4 = 0;
        }
        if ((n3 = (int)glyphMetrics.getRSB()) > 0) {
            n3 = 0;
        }
        int n5 = rectangle.width - n4 - n3;
        int n6 = rectangle.height;
        if (n5 > 0 && n6 > 0) {
            int n7 = unicodeFont.getPaddingTop();
            int n8 = unicodeFont.getPaddingRight();
            int n9 = unicodeFont.getPaddingBottom();
            int n10 = unicodeFont.getPaddingLeft();
            int n11 = 1;
            this.width = (short)(n5 + n10 + n8 + n11);
            this.height = (short)(n6 + n7 + n9 + n11);
            this.yOffset = (short)(unicodeFont.getAscent() + rectangle.y - n7);
        }
        this.shape = glyphVector.getGlyphOutline(n2, -rectangle.x + unicodeFont.getPaddingLeft(), -rectangle.y + unicodeFont.getPaddingTop());
        this.isMissing = !unicodeFont.getFont().canDisplay((char)n);
    }

    public int getCodePoint() {
        return this.codePoint;
    }

    public boolean isMissing() {
        return this.isMissing;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public Shape getShape() {
        return this.shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public Image getImage() {
        return this.image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getYOffset() {
        return this.yOffset;
    }
}

