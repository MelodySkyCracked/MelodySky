/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.Utils.hodgepodge.object;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class ReflectUtils {
    public static Field getField(Object object, String string) throws NoSuchFieldException {
        Field field = object.getClass().getDeclaredField(string);
        return ReflectUtils.setFieldAccessible(field, true);
    }

    public static Field setFieldAccessible(Field field, boolean bl) {
        field.setAccessible(bl);
        return field;
    }

    public static void setField(Object object, String string, Object object2) throws NoSuchFieldException, IllegalAccessException {
        ReflectUtils.getField(object, string).set(object, object2);
    }

    public static Method getMethod(Object object, String string, Class ... classArray) throws NoSuchMethodException {
        Method method = object.getClass().getDeclaredMethod(string, classArray);
        return ReflectUtils.setMethodAccessible(method, true);
    }

    public static Method getMethodFromClass(Class clazz, String string, Class ... classArray) throws NoSuchMethodException {
        Method method = clazz.getDeclaredMethod(string, classArray);
        return ReflectUtils.setMethodAccessible(method, true);
    }

    public static Class[] getParameterTypes(Object[] objectArray) {
        Class[] classArray = new Class[objectArray.length];
        for (int i = 0; i < objectArray.length; ++i) {
            classArray[i] = objectArray[i].getClass();
        }
        return classArray;
    }

    public static Method setMethodAccessible(Method method, boolean bl) {
        method.setAccessible(bl);
        return method;
    }

    public static Object invokeMethodFromName(Object object, Class clazz, String string, Object[] objectArray) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return ReflectUtils.invokeMethod(object, ReflectUtils.getMethodFromClass(clazz, string, ReflectUtils.getParameterTypes(objectArray)), objectArray);
    }

    public static Object invokeMethod(Object object, Method method, Object[] objectArray) throws InvocationTargetException, IllegalAccessException {
        return method.invoke(object, objectArray);
    }
}

