/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.Utils.hodgepodge.object;

import java.util.concurrent.ThreadLocalRandom;

public final class StringUtils {
    public static boolean isNullOrEmpty(String string) {
        return string == null || string == false;
    }

    public static String buildString(Object ... objectArray) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Object object : objectArray) {
            stringBuilder.append(object);
        }
        return stringBuilder.toString();
    }

    public static String compileString(String string, Object ... objectArray) {
        for (int i = 0; i < objectArray.length; ++i) {
            string = string.replace(StringUtils.buildString("{", i, "}"), objectArray[i].toString());
        }
        return string;
    }

    public static String duplicateCopy(String string, int n) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < n; ++i) {
            stringBuilder.append(string);
        }
        return stringBuilder.toString();
    }

    public static boolean isNumber(String string) {
        int n;
        int n2;
        if (string == false) {
            return false;
        }
        char[] cArray = string.toCharArray();
        int n3 = cArray.length;
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = false;
        int n4 = n2 = cArray[0] == '-' ? 1 : 0;
        if (n3 > n2 + 1 && cArray[n2] == '0') {
            if (cArray[n2 + 1] == 'x' || cArray[n2 + 1] == 'X') {
                int n5 = n2 + 2;
                if (n5 == n3) {
                    return false;
                }
                while (n5 < cArray.length) {
                    if (!(cArray[n5] >= '0' && cArray[n5] <= '9' || cArray[n5] >= 'a' && cArray[n5] <= 'f' || cArray[n5] >= 'A' && cArray[n5] <= 'F')) {
                        return false;
                    }
                    ++n5;
                }
                return true;
            }
            if (Character.isDigit(cArray[n2 + 1])) {
                for (int i = n2 + 1; i < cArray.length; ++i) {
                    if (cArray[i] >= '0' && cArray[i] <= '7') continue;
                    return false;
                }
                return true;
            }
        }
        --n3;
        for (n = n2; n < n3 || n < n3 + 1 && bl3 && !bl4; ++n) {
            if (cArray[n] >= '0' && cArray[n] <= '9') {
                bl4 = true;
                bl3 = false;
                continue;
            }
            if (cArray[n] == '.') {
                if (bl2 || bl) {
                    return false;
                }
                bl2 = true;
                continue;
            }
            if (cArray[n] != 'e' && cArray[n] != 'E') {
                if (cArray[n] != '+' && cArray[n] != '-') {
                    return false;
                }
                if (!bl3) {
                    return false;
                }
                bl3 = false;
                bl4 = false;
                continue;
            }
            if (bl) {
                return false;
            }
            if (!bl4) {
                return false;
            }
            bl = true;
            bl3 = true;
        }
        if (n < cArray.length) {
            if (cArray[n] >= '0' && cArray[n] <= '9') {
                return true;
            }
            if (cArray[n] != 'e' && cArray[n] != 'E') {
                if (cArray[n] == '.') {
                    return !bl2 && !bl ? bl4 : false;
                }
                if (!(bl3 || cArray[n] != 'd' && cArray[n] != 'D' && cArray[n] != 'f' && cArray[n] != 'F')) {
                    return bl4;
                }
                if (cArray[n] != 'l' && cArray[n] != 'L') {
                    return false;
                }
                return bl4 && !bl && !bl2;
            }
            return false;
        }
        return !bl3 && bl4;
    }

    public static String randomString(int n, String string) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < n; ++i) {
            stringBuilder.append(string.charAt(ThreadLocalRandom.current().nextInt(0, string.length() - 1)));
        }
        return stringBuilder.toString();
    }
}

