/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.Utils.hodgepodge.object;

import xyz.Melody.Utils.hodgepodge.function.CatchHandler;
import xyz.Melody.Utils.hodgepodge.function.ThrowsVoidFunction;
import xyz.Melody.Utils.hodgepodge.function.VoidFunction;

public final class ObjectUtils {
    public static int hash(Object object) {
        return object.hashCode();
    }

    public static boolean checkNull(Object ... objectArray) {
        if (objectArray == null || objectArray.length == 0) {
            return true;
        }
        for (Object object : objectArray) {
            if (object != null) continue;
            return true;
        }
        return false;
    }

    public static boolean notNull(Object object) {
        return object != null;
    }

    public static boolean notNull(Object ... objectArray) {
        if (objectArray == null || objectArray.length == 0) {
            return false;
        }
        for (Object object : objectArray) {
            if (object != null) continue;
            return false;
        }
        return true;
    }

    public static Object makeSureNotNull(Object object) {
        if (object == null) {
            throw new NullPointerException();
        }
        return object;
    }

    public static Object makeSureNotNull(Object object, String string) {
        if (object == null) {
            throw new NullPointerException(string);
        }
        return object;
    }

    public static Object makeSureNotNull(Object object, VoidFunction voidFunction) {
        if (object == null) {
            voidFunction.handle();
        }
        return object;
    }

    public static Object[] makeSureNotNull(Object ... objectArray) {
        for (Object object : objectArray) {
            ObjectUtils.makeSureNotNull(object);
        }
        return objectArray;
    }

    public static Object[] makeSureNotNull(String string, Object ... objectArray) {
        for (Object object : objectArray) {
            ObjectUtils.makeSureNotNull(object, string);
        }
        return objectArray;
    }

    public static Object[] makeSureNotNull(VoidFunction voidFunction, Object ... objectArray) {
        for (Object object : objectArray) {
            ObjectUtils.makeSureNotNull(object, voidFunction);
        }
        return objectArray;
    }

    public static void trySomeThing(ThrowsVoidFunction throwsVoidFunction, CatchHandler catchHandler) {
        try {
            throwsVoidFunction.handle();
        }
        catch (Throwable throwable) {
            catchHandler.onCatchException(throwable);
        }
    }

    public static Object forcedConversion(Object object) {
        return object;
    }

    public static boolean reverseBoolean(boolean bl) {
        return !bl;
    }
}

