/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.util;

public class FastTrig {
    private static double reduceSinAngle(double d) {
        double d2 = d;
        if (Math.abs(d %= Math.PI * 2) > Math.PI) {
            d -= Math.PI * 2;
        }
        if (Math.abs(d) > 1.5707963267948966) {
            d = Math.PI - d;
        }
        return d;
    }

    public static double sin(double d) {
        if (Math.abs(d = FastTrig.reduceSinAngle(d)) <= 0.7853981633974483) {
            return Math.sin(d);
        }
        return Math.cos(1.5707963267948966 - d);
    }

    public static double cos(double d) {
        return FastTrig.sin(d + 1.5707963267948966);
    }
}

