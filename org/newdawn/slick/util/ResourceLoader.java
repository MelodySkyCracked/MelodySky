/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import org.newdawn.slick.util.ClasspathLocation;
import org.newdawn.slick.util.FileSystemLocation;
import org.newdawn.slick.util.ResourceLocation;

public class ResourceLoader {
    private static ArrayList locations = new ArrayList();

    public static void addResourceLocation(ResourceLocation resourceLocation) {
        locations.add(resourceLocation);
    }

    public static void removeResourceLocation(ResourceLocation resourceLocation) {
        locations.remove(resourceLocation);
    }

    public static void removeAllResourceLocations() {
        locations.clear();
    }

    public static InputStream getResourceAsStream(String string) {
        ResourceLocation resourceLocation;
        InputStream inputStream = null;
        for (int i = 0; i < locations.size() && (inputStream = (resourceLocation = (ResourceLocation)locations.get(i)).getResourceAsStream(string)) == null; ++i) {
        }
        if (inputStream == null) {
            throw new RuntimeException("Resource not found: " + string);
        }
        return new BufferedInputStream(inputStream);
    }

    public static boolean resourceExists(String string) {
        URL uRL = null;
        for (int i = 0; i < locations.size(); ++i) {
            ResourceLocation resourceLocation = (ResourceLocation)locations.get(i);
            uRL = resourceLocation.getResource(string);
            if (uRL == null) continue;
            return true;
        }
        return false;
    }

    public static URL getResource(String string) {
        ResourceLocation resourceLocation;
        URL uRL = null;
        for (int i = 0; i < locations.size() && (uRL = (resourceLocation = (ResourceLocation)locations.get(i)).getResource(string)) == null; ++i) {
        }
        if (uRL == null) {
            throw new RuntimeException("Resource not found: " + string);
        }
        return uRL;
    }

    static {
        locations.add(new ClasspathLocation());
        locations.add(new FileSystemLocation(new File(".")));
    }
}

