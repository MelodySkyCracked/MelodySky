/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.internal;

import java.lang.reflect.Constructor;

public class NativeCoreObjectFactory {
    private static NativeCoreObjectFactory factory;
    private ClassLoader classLoader;

    public static void setDefaultFactory(NativeCoreObjectFactory nativeCoreObjectFactory) {
        Class<NativeCoreObjectFactory> clazz = NativeCoreObjectFactory.class;
        synchronized (NativeCoreObjectFactory.class) {
            factory = nativeCoreObjectFactory;
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return;
        }
    }

    public static Object create(Class clazz, String string, Class[] classArray, Object[] objectArray) {
        Class clazz2 = NativeCoreObjectFactory.class;
        synchronized (NativeCoreObjectFactory.class) {
            ClassLoader classLoader = factory != null ? NativeCoreObjectFactory.factory.classLoader : null;
            // ** MonitorExit[var5_4] (shouldn't be in output)
            if (classLoader == null) {
                classLoader = NativeCoreObjectFactory.class.getClassLoader();
            }
            try {
                clazz2 = classLoader.loadClass(string);
                Constructor constructor = clazz2.getDeclaredConstructor(classArray);
                constructor.setAccessible(true);
                return constructor.newInstance(objectArray);
            }
            catch (RuntimeException runtimeException) {
                runtimeException.printStackTrace();
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
            return null;
        }
    }

    public NativeCoreObjectFactory(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
}

