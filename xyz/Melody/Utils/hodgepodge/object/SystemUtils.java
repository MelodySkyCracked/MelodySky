/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.Utils.hodgepodge.object;

public class SystemUtils {
    public static SystemEnum getSystem() {
        String string = System.getProperty("os.name").toLowerCase();
        if (string.contains("windows")) {
            return SystemEnum.WINDOWS;
        }
        if (string.contains("linux")) {
            return SystemEnum.LINUX;
        }
        if (string.contains("mac")) {
            return SystemEnum.MACOS;
        }
        return SystemEnum.UNKNOWN;
    }

    public static enum SystemEnum {
        WINDOWS,
        LINUX,
        MACOS,
        UNKNOWN;

    }
}

