/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.Utils.hodgepodge.io;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

public final class ExternalClassLoader {
    private static final CustomClassLoader Instance = new CustomClassLoader();

    public static void loadJar(File file) throws IOException {
        Instance.addURLFile(file.toURI().toURL());
    }

    public static void loadJar(URL uRL) {
        Instance.addURLFile(uRL);
    }

    public static Class getClassFromName(String string) throws ClassNotFoundException {
        return Class.forName(string, true, Instance);
    }

    private static final class CustomClassLoader
    extends URLClassLoader {
        public CustomClassLoader() {
            super(new URL[0], CustomClassLoader.findParentClassLoader());
        }

        public void addURLFile(URL uRL) {
            this.addURL(uRL);
        }

        private static ClassLoader findParentClassLoader() {
            ClassLoader classLoader = CustomClassLoader.class.getClassLoader();
            if (classLoader == null) {
                classLoader = CustomClassLoader.class.getClassLoader();
            }
            if (classLoader == null) {
                classLoader = ClassLoader.getSystemClassLoader();
            }
            return classLoader;
        }
    }
}

