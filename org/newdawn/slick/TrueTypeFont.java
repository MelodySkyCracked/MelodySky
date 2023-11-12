/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.lIll;
import org.newdawn.slick.opengl.GLUtils;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;
import org.newdawn.slick.util.BufferedImageUtil;

public class TrueTypeFont
implements Font {
    private static final SGL GL = Renderer.get();
    private IntObject[] charArray = new IntObject[256];
    private Map customChars = new HashMap();
    private boolean antiAlias;
    private int fontSize = 0;
    private int fontHeight = 0;
    private Texture fontTexture;
    private int textureWidth = 512;
    private int textureHeight = 512;
    private java.awt.Font font;
    private FontMetrics fontMetrics;

    public TrueTypeFont(java.awt.Font font, boolean bl, char[] cArray) {
        GLUtils.checkGLContext();
        this.font = font;
        this.fontSize = font.getSize();
        this.antiAlias = bl;
        this.createSet(cArray);
    }

    public TrueTypeFont(java.awt.Font font, boolean bl) {
        this(font, bl, null);
    }

    private BufferedImage getFontImage(char c) {
        int n;
        BufferedImage bufferedImage = new BufferedImage(1, 1, 2);
        Graphics2D graphics2D = (Graphics2D)bufferedImage.getGraphics();
        if (this.antiAlias) {
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        graphics2D.setFont(this.font);
        this.fontMetrics = graphics2D.getFontMetrics();
        int n2 = this.fontMetrics.charWidth(c);
        if (n2 <= 0) {
            n2 = 1;
        }
        if ((n = this.fontMetrics.getHeight()) <= 0) {
            n = this.fontSize;
        }
        BufferedImage bufferedImage2 = new BufferedImage(n2, n, 2);
        Graphics2D graphics2D2 = (Graphics2D)bufferedImage2.getGraphics();
        if (this.antiAlias) {
            graphics2D2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        graphics2D2.setFont(this.font);
        graphics2D2.setColor(java.awt.Color.WHITE);
        int n3 = 0;
        int n4 = 0;
        graphics2D2.drawString(String.valueOf(c), n3, n4 + this.fontMetrics.getAscent());
        return bufferedImage2;
    }

    private void createSet(char[] cArray) {
        if (cArray != null && cArray.length > 0) {
            this.textureWidth *= 2;
        }
        try {
            BufferedImage bufferedImage = new BufferedImage(this.textureWidth, this.textureHeight, 2);
            Graphics2D graphics2D = (Graphics2D)bufferedImage.getGraphics();
            graphics2D.setColor(new java.awt.Color(255, 255, 255, 1));
            graphics2D.fillRect(0, 0, this.textureWidth, this.textureHeight);
            int n = 0;
            int n2 = 0;
            int n3 = 0;
            int n4 = cArray != null ? cArray.length : 0;
            for (int i = 0; i < 256 + n4; ++i) {
                char c = i < 256 ? (char)i : cArray[i - 256];
                BufferedImage bufferedImage2 = this.getFontImage(c);
                IntObject intObject = new IntObject(this, null);
                intObject.width = bufferedImage2.getWidth();
                intObject.height = bufferedImage2.getHeight();
                if (n2 + intObject.width >= this.textureWidth) {
                    n2 = 0;
                    n3 += n;
                    n = 0;
                }
                intObject.storedX = n2;
                intObject.storedY = n3;
                if (intObject.height > this.fontHeight) {
                    this.fontHeight = intObject.height;
                }
                if (intObject.height > n) {
                    n = intObject.height;
                }
                graphics2D.drawImage((Image)bufferedImage2, n2, n3, null);
                n2 += intObject.width;
                if (i < 256) {
                    this.charArray[i] = intObject;
                } else {
                    this.customChars.put(new Character(c), intObject);
                }
                bufferedImage2 = null;
            }
            this.fontTexture = BufferedImageUtil.getTexture(this.font.toString(), bufferedImage);
        }
        catch (IOException iOException) {
            System.err.println("Failed to create font.");
            iOException.printStackTrace();
        }
    }

    private void drawQuad(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        float f9 = f3 - f;
        float f10 = f4 - f2;
        float f11 = f5 / (float)this.textureWidth;
        float f12 = f6 / (float)this.textureHeight;
        float f13 = f7 - f5;
        float f14 = f8 - f6;
        float f15 = f13 / (float)this.textureWidth;
        float f16 = f14 / (float)this.textureHeight;
        GL.glTexCoord2f(f11, f12);
        GL.glVertex2f(f, f2);
        GL.glTexCoord2f(f11, f12 + f16);
        GL.glVertex2f(f, f2 + f10);
        GL.glTexCoord2f(f11 + f15, f12 + f16);
        GL.glVertex2f(f + f9, f2 + f10);
        GL.glTexCoord2f(f11 + f15, f12);
        GL.glVertex2f(f + f9, f2);
    }

    @Override
    public int getWidth(String string) {
        int n = 0;
        IntObject intObject = null;
        char c = '\u0000';
        for (int i = 0; i < string.length(); ++i) {
            c = string.charAt(i);
            intObject = c < '\u0100' ? this.charArray[c] : (IntObject)this.customChars.get(new Character(c));
            if (intObject == null) continue;
            n += intObject.width;
        }
        return n;
    }

    public int getHeight() {
        return this.fontHeight;
    }

    @Override
    public int getHeight(String string) {
        return this.fontHeight;
    }

    @Override
    public int getLineHeight() {
        return this.fontHeight;
    }

    @Override
    public void drawString(float f, float f2, String string, Color color) {
        this.drawString(f, f2, string, color, 0, string.length() - 1);
    }

    @Override
    public void drawString(float f, float f2, String string, Color color, int n, int n2) {
        color.bind();
        this.fontTexture.bind();
        IntObject intObject = null;
        GL.glBegin(7);
        int n3 = 0;
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            intObject = c < '\u0100' ? this.charArray[c] : (IntObject)this.customChars.get(new Character(c));
            if (intObject == null) continue;
            if (i >= n || i <= n2) {
                this.drawQuad(f + (float)n3, f2, f + (float)n3 + (float)intObject.width, f2 + (float)intObject.height, intObject.storedX, intObject.storedY, intObject.storedX + intObject.width, intObject.storedY + intObject.height);
            }
            n3 += intObject.width;
        }
        GL.glEnd();
    }

    @Override
    public void drawString(float f, float f2, String string) {
        this.drawString(f, f2, string, Color.white);
    }

    private class IntObject {
        public int width;
        public int height;
        public int storedX;
        public int storedY;
        final TrueTypeFont this$0;

        private IntObject(TrueTypeFont trueTypeFont) {
            this.this$0 = trueTypeFont;
        }

        IntObject(TrueTypeFont trueTypeFont, lIll lIll2) {
            this(trueTypeFont);
        }
    }
}

