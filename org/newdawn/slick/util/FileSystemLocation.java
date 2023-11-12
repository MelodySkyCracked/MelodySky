/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import org.newdawn.slick.util.ResourceLocation;

public class FileSystemLocation
implements ResourceLocation {
    private File root;

    public FileSystemLocation(File file) {
        this.root = file;
    }

    @Override
    public URL getResource(String string) {
        try {
            File file = new File(this.root, string);
            if (!file.exists()) {
                file = new File(string);
            }
            if (!file.exists()) {
                return null;
            }
            return file.toURI().toURL();
        }
        catch (IOException iOException) {
            return null;
        }
    }

    @Override
    public InputStream getResourceAsStream(String string) {
        try {
            File file = new File(this.root, string);
            if (!file.exists()) {
                file = new File(string);
            }
            return new FileInputStream(file);
        }
        catch (IOException iOException) {
            return null;
        }
    }
}

