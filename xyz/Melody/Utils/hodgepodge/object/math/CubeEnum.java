/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.Utils.hodgepodge.object.math;

import xyz.Melody.Utils.hodgepodge.object.math.I;
import xyz.Melody.Utils.hodgepodge.object.math.l;
import xyz.Melody.Utils.hodgepodge.object.math.lI;

public abstract class CubeEnum
extends Enum {
    public static final /* enum */ CubeEnum CUBIC_CENTIMETER = new I();
    public static final /* enum */ CubeEnum CUBIC_DECIMETER = new lI();
    public static final /* enum */ CubeEnum CUBIC_METER = new l();
    private static final CubeEnum[] $VALUES = new CubeEnum[]{CUBIC_CENTIMETER, CUBIC_DECIMETER, CUBIC_METER};

    public static CubeEnum[] values() {
        return (CubeEnum[])$VALUES.clone();
    }

    public static CubeEnum valueOf(String string) {
        return Enum.valueOf(CubeEnum.class, string);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private CubeEnum() {
        void var2_-1;
        void var1_-1;
    }

    public abstract double toCubicMeter(double var1);

    public abstract double toCubicDecimeter(double var1);

    public abstract double toCubicCentimeter(double var1);

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    CubeEnum() {
        this((String)var1_-1, (int)var2_1);
        void var2_1;
        void var1_-1;
    }
}

