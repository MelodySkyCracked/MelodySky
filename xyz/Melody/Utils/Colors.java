/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.Utils;

import java.awt.Color;

public enum Colors {
    BLACK(-16711423),
    BLUE(-12028161),
    DARKBLUE(-12621684),
    GREEN(-9830551),
    DARKGREEN(-9320847),
    WHITE(-65794),
    AQUA(-7820064),
    DARKAQUA(-12621684),
    GRAY(-9868951),
    DARKGREY(-14342875),
    RED(-65536),
    DARKRED(-8388608),
    ORANGE(-29696),
    DARKORANGE(-2263808),
    YELLOW(-256),
    DARKYELLOW(-2702025),
    MAGENTA(-18751),
    SLOWLY(-13220000),
    SLOWLY2(-106165216),
    SLOWLY3(-17791255),
    SLOWLY4(-15425034),
    DARKMAGENTA(-2252579);

    public int c;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private Colors() {
        void var3_1;
        this.c = var3_1;
    }

    public static int getColor(Color color) {
        return Colors.getColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public static int getColor(int n) {
        return Colors.getColor(n, n, n, 255);
    }

    public static int getColor(int n, int n2) {
        return Colors.getColor(n, n, n, n2);
    }

    public static int getColor(int n, int n2, int n3) {
        return Colors.getColor(n, n2, n3, 255);
    }

    public static int getColor(int n, int n2, int n3, int n4) {
        int n5 = 0;
        n5 |= n4 << 24;
        n5 |= n << 16;
        n5 |= n2 << 8;
        return n5 |= n3;
    }
}

