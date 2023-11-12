/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.util;

public abstract class ObfuscationUtil {
    private ObfuscationUtil() {
    }

    public static String mapDescriptor(String string, IClassRemapper iClassRemapper) {
        return ObfuscationUtil.remapDescriptor(string, iClassRemapper, false);
    }

    public static String unmapDescriptor(String string, IClassRemapper iClassRemapper) {
        return ObfuscationUtil.remapDescriptor(string, iClassRemapper, true);
    }

    private static String remapDescriptor(String string, IClassRemapper iClassRemapper, boolean bl) {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilder2 = null;
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (stringBuilder2 != null) {
                if (c == ';') {
                    stringBuilder.append('L').append(ObfuscationUtil.remap(stringBuilder2.toString(), iClassRemapper, bl)).append(';');
                    stringBuilder2 = null;
                    continue;
                }
                stringBuilder2.append(c);
                continue;
            }
            if (c == 'L') {
                stringBuilder2 = new StringBuilder();
                continue;
            }
            stringBuilder.append(c);
        }
        if (stringBuilder2 != null) {
            throw new IllegalArgumentException("Invalid descriptor '" + string + "', missing ';'");
        }
        return stringBuilder.toString();
    }

    private static Object remap(String string, IClassRemapper iClassRemapper, boolean bl) {
        String string2 = bl ? iClassRemapper.unmap(string) : iClassRemapper.map(string);
        return string2 != null ? string2 : string;
    }

    public static interface IClassRemapper {
        public String map(String var1);

        public String unmap(String var1);
    }
}

