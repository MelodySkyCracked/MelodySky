/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.util;

import java.io.InputStream;
import java.net.URL;
import org.newdawn.slick.util.ResourceLoader;
import org.newdawn.slick.util.ResourceLocation;

public class ClasspathLocation
implements ResourceLocation {
    @Override
    public URL getResource(String string) {
        String string2 = string.replace('\\', '/');
        return ResourceLoader.class.getClassLoader().getResource(string2);
    }

    @Override
    public InputStream getResourceAsStream(String string) {
        String string2 = string.replace('\\', '/');
        return ResourceLoader.class.getClassLoader().getResourceAsStream(string2);
    }
}

