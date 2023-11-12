/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.launch.platform;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

class MainAttributes {
    private static final Map instances = new HashMap();
    protected final Attributes attributes;

    private MainAttributes() {
        this.attributes = new Attributes();
    }

    private MainAttributes(File file) {
        this.attributes = MainAttributes.getAttributes(file);
    }

    public final String get(String string) {
        if (this.attributes != null) {
            return this.attributes.getValue(string);
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static Attributes getAttributes(File file) {
        JarFile jarFile;
        block10: {
            Attributes attributes;
            if (file == null) {
                return null;
            }
            jarFile = null;
            try {
                jarFile = new JarFile(file);
                Manifest manifest = jarFile.getManifest();
                if (manifest == null) break block10;
                attributes = manifest.getMainAttributes();
                if (jarFile == null) return attributes;
            }
            catch (IOException iOException) {
                if (jarFile == null) return new Attributes();
                try {
                    jarFile.close();
                    return new Attributes();
                }
                catch (IOException iOException2) {
                    // empty catch block
                    return new Attributes();
                }
            }
            try {
                jarFile.close();
                return attributes;
            }
            catch (IOException iOException) {
                // empty catch block
            }
            return attributes;
        }
        if (jarFile == null) return new Attributes();
        try {
            jarFile.close();
            return new Attributes();
        }
        catch (IOException iOException) {
            return new Attributes();
        }
    }

    public static MainAttributes of(File file) {
        return MainAttributes.of(file.toURI());
    }

    public static MainAttributes of(URI uRI) {
        MainAttributes mainAttributes = (MainAttributes)instances.get(uRI);
        if (mainAttributes == null) {
            mainAttributes = new MainAttributes(new File(uRI));
            instances.put(uRI, mainAttributes);
        }
        return mainAttributes;
    }
}

