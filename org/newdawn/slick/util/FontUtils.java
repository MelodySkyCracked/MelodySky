/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.util;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;

public class FontUtils {
    public static void drawLeft(Font font, String string, int n, int n2) {
        FontUtils.drawString(font, string, 1, n, n2, 0, Color.white);
    }

    public static void drawCenter(Font font, String string, int n, int n2, int n3) {
        FontUtils.drawString(font, string, 2, n, n2, n3, Color.white);
    }

    public static void drawCenter(Font font, String string, int n, int n2, int n3, Color color) {
        FontUtils.drawString(font, string, 2, n, n2, n3, color);
    }

    public static void drawRight(Font font, String string, int n, int n2, int n3) {
        FontUtils.drawString(font, string, 3, n, n2, n3, Color.white);
    }

    public static void drawRight(Font font, String string, int n, int n2, int n3, Color color) {
        FontUtils.drawString(font, string, 3, n, n2, n3, color);
    }

    public static final int drawString(Font font, String string, int n, int n2, int n3, int n4, Color color) {
        int n5 = 0;
        if (n == 1) {
            font.drawString(n2, n3, string, color);
        } else if (n == 2) {
            font.drawString(n2 + n4 / 2 - font.getWidth(string) / 2, n3, string, color);
        } else if (n == 3) {
            font.drawString(n2 + n4 - font.getWidth(string), n3, string, color);
        } else if (n == 4) {
            int n6 = n4 - font.getWidth(string);
            if (n6 <= 0) {
                font.drawString(n2, n3, string, color);
            }
            return FontUtils.drawJustifiedSpaceSeparatedSubstrings(font, string, n2, n3, FontUtils.calculateWidthOfJustifiedSpaceInPixels(font, string, n6));
        }
        return n5;
    }

    private static int calculateWidthOfJustifiedSpaceInPixels(Font font, String string, int n) {
        int n2 = 0;
        int n3 = 0;
        while (n3 < string.length()) {
            if (string.charAt(n3++) != ' ') continue;
            ++n2;
        }
        if (n2 > 0) {
            n2 = (n + font.getWidth(" ") * n2) / n2;
        }
        return n2;
    }

    private static int drawJustifiedSpaceSeparatedSubstrings(Font font, String string, int n, int n2, int n3) {
        int n4 = 0;
        int n5 = 0;
        int n6 = n;
        while (n4 < string.length()) {
            n5 = string.indexOf(32, n4);
            if (n5 == -1) {
                n5 = string.length();
            }
            String string2 = string.substring(n4, n5);
            font.drawString(n6, n2, string2);
            n6 += font.getWidth(string2) + n3;
            n4 = n5 + 1;
        }
        return n6;
    }

    public class Alignment {
        public static final int LEFT = 1;
        public static final int CENTER = 2;
        public static final int RIGHT = 3;
        public static final int JUSTIFY = 4;
        final FontUtils this$0;

        public Alignment(FontUtils fontUtils) {
            this.this$0 = fontUtils;
        }
    }
}

