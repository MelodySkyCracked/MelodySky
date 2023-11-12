/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.Utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class OptifineCompatible {
    private static Class OPTIFINE_LANG_CLASS;
    private static Method OPTIFINE_LANG_RELOADED_METHOD;

    public static void callOptifineReload() {
        if (OPTIFINE_LANG_CLASS != null && OPTIFINE_LANG_RELOADED_METHOD != null) {
            try {
                OPTIFINE_LANG_RELOADED_METHOD.invoke(null, new Object[0]);
            }
            catch (IllegalAccessException | InvocationTargetException reflectiveOperationException) {
                throw new RuntimeException(reflectiveOperationException);
            }
        }
    }

    static {
        try {
            OPTIFINE_LANG_CLASS = Class.forName("net.optifine.Lang");
            try {
                OPTIFINE_LANG_RELOADED_METHOD = OPTIFINE_LANG_CLASS.getDeclaredMethod("resourcesReloaded", new Class[0]);
                OPTIFINE_LANG_RELOADED_METHOD.setAccessible(true);
            }
            catch (NoSuchMethodException noSuchMethodException) {
                OPTIFINE_LANG_RELOADED_METHOD = null;
            }
        }
        catch (ClassNotFoundException classNotFoundException) {
            OPTIFINE_LANG_CLASS = null;
        }
    }
}

