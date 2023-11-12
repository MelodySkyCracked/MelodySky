/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.Utils.hodgepodge.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import xyz.Melody.Utils.hodgepodge.io.FileUtils;

public final class NativeLoader {
    public static void loadAndWriteToCacheDirectory(InputStream inputStream, byte[] byArray) throws IOException {
        File file = new File(System.getProperty("java.io.tmpdir"));
        NativeLoader.loadAndWrite(file, inputStream, byArray);
        file.deleteOnExit();
    }

    public static void loadAndWrite(File file, InputStream inputStream, byte[] byArray) throws IOException {
        FileUtils.writeInputStreamToFile(file, inputStream, byArray);
        System.load(file.getAbsolutePath());
    }
}

