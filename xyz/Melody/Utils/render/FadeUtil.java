/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.Utils.render;

import java.awt.Color;
import java.util.function.Supplier;

public enum FadeUtil {
    GREEN(FadeUtil::lambda$static$0),
    WHITE(FadeUtil::lambda$static$1),
    PURPLE(FadeUtil::lambda$static$2),
    DARK_PURPLE(FadeUtil::lambda$static$3),
    BLUE(FadeUtil::lambda$static$4),
    RED(FadeUtil::lambda$static$5);

    private final Supplier colorSupplier;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private FadeUtil() {
        void var3_1;
        this.colorSupplier = var3_1;
    }

    public static Color fade(Color color) {
        return FadeUtil.fade(color, 2, 100);
    }

    public static Color fade(Color color, int n, int n2) {
        float[] fArray = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), fArray);
        float f = Math.abs(((float)(System.currentTimeMillis() % 2000L) / 1000.0f + (float)n / (float)n2 * 2.0f) % 2.0f - 1.0f);
        f = 0.4f + 0.5f * f;
        fArray[2] = f % 2.0f;
        return new Color(Color.HSBtoRGB(fArray[0], fArray[1], fArray[2]));
    }

    public Color getColor() {
        return (Color)this.colorSupplier.get();
    }

    private static Color lambda$static$5() {
        return new Color(255, 50, 50);
    }

    private static Color lambda$static$4() {
        return new Color(116, 202, 255);
    }

    private static Color lambda$static$3() {
        return new Color(133, 46, 215);
    }

    private static Color lambda$static$2() {
        return new Color(198, 139, 255);
    }

    private static Color lambda$static$1() {
        return Color.WHITE;
    }

    private static Color lambda$static$0() {
        return new Color(0, 255, 138);
    }
}

