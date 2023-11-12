/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick;

import java.io.UnsupportedEncodingException;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.util.Log;

public class SpriteSheetFont
implements Font {
    private SpriteSheet font;
    private char startingCharacter;
    private int charWidth;
    private int charHeight;
    private int horizontalCount;
    private int numChars;

    public SpriteSheetFont(SpriteSheet spriteSheet, char c) {
        this.font = spriteSheet;
        this.startingCharacter = c;
        this.horizontalCount = spriteSheet.getHorizontalCount();
        int n = spriteSheet.getVerticalCount();
        this.charWidth = spriteSheet.getWidth() / this.horizontalCount;
        this.charHeight = spriteSheet.getHeight() / n;
        this.numChars = this.horizontalCount * n;
    }

    @Override
    public void drawString(float f, float f2, String string) {
        this.drawString(f, f2, string, Color.white);
    }

    @Override
    public void drawString(float f, float f2, String string, Color color) {
        this.drawString(f, f2, string, color, 0, string.length() - 1);
    }

    @Override
    public void drawString(float f, float f2, String string, Color color, int n, int n2) {
        try {
            byte[] byArray = string.getBytes("US-ASCII");
            for (int i = 0; i < byArray.length; ++i) {
                int n3 = byArray[i] - this.startingCharacter;
                if (n3 >= this.numChars) continue;
                int n4 = n3 % this.horizontalCount;
                int n5 = n3 / this.horizontalCount;
                if (i < n && i > n2) continue;
                this.font.getSprite(n4, n5).draw(f + (float)(i * this.charWidth), f2, color);
            }
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            Log.error(unsupportedEncodingException);
        }
    }

    @Override
    public int getHeight(String string) {
        return this.charHeight;
    }

    @Override
    public int getWidth(String string) {
        return this.charWidth * string.length();
    }

    @Override
    public int getLineHeight() {
        return this.charHeight;
    }
}

