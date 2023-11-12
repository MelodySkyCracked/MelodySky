/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.Utils.hodgepodge.object.math;

import java.math.BigDecimal;

public final class NumberUtils {
    public static int toInteger(double d) {
        return (int)d;
    }

    public static int toInteger(float f) {
        return (int)f;
    }

    public static int toInteger(long l2) {
        return (int)l2;
    }

    public static int toInteger(boolean bl) {
        return bl ? 1 : 0;
    }

    public static long toLong(double d) {
        return (long)d;
    }

    public static long toLong(float f) {
        return (long)f;
    }

    public static short toShort(double d) {
        return (short)d;
    }

    public static short toShort(float f) {
        return (short)f;
    }

    public static short toShort(int n) {
        return (short)n;
    }

    public static short toShort(long l2) {
        return (short)l2;
    }

    public static boolean isNaN(double d) {
        return d != d;
    }

    public static boolean isNaN(float f) {
        return f != f;
    }

    public static String originalNumber(double d) {
        return new BigDecimal(d).toString();
    }

    public static String originalNumber(float f) {
        return new BigDecimal(f).toString();
    }

    public static String originalNumber(long l2) {
        return new BigDecimal(l2).toString();
    }

    public static String originalNumber(int n) {
        return new BigDecimal(n).toString();
    }
}

