/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.Utils.hodgepodge.io;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Objects;
import xyz.Melody.Utils.hodgepodge.io.IOUtils;
import xyz.Melody.Utils.hodgepodge.object.ObjectUtils;

public final class FileUtils {
    private FileUtils() {
    }

    public static void createNew(String string, String string2) throws IOException {
        ObjectUtils.makeSureNotNull((Object)string, string2);
        new File(string2).mkdirs();
        new File(string).createNewFile();
    }

    public static boolean deleteFile(File file) {
        if (!file.exists()) {
            return false;
        }
        if (file.isFile()) {
            return file.delete();
        }
        for (File file2 : Objects.requireNonNull(file.listFiles())) {
            FileUtils.deleteFile(file2);
        }
        return file.delete();
    }

    public static void moveFile(File file, File file2, byte[] byArray) throws IOException {
        FileUtils.copyFile(file, file2, byArray);
        FileUtils.deleteFile(file);
    }

    public static void copyFile(File file, File file2, byte[] byArray) throws IOException {
        if (!file.exists()) {
            throw new FileNotFoundException("Copied File");
        }
        if (!file2.exists()) {
            if (file2.isDirectory()) {
                file2.mkdirs();
            } else if (file2.isFile()) {
                FileUtils.createNew(file2.getName(), file2.getPath());
            }
        }
        FileInputStream fileInputStream = IOUtils.getFileInputStream(file);
        FileUtils.copyInputStream(fileInputStream, file2, byArray);
        IOUtils.close((Closeable)fileInputStream);
    }

    public static void copyInputStream(InputStream inputStream, File file, byte[] byArray) throws IOException {
        FileUtils.writeInputStreamToFile(file, inputStream, byArray);
    }

    public static String readFileAsString(File file, String string) throws IOException {
        return FileUtils.readFileAsString(file, Charset.forName(string));
    }

    public static String readFileAsString(File file, Charset charset) throws IOException {
        ObjectUtils.makeSureNotNull(file, charset);
        return IOUtils.inputStreamToString((InputStream)IOUtils.getFileInputStream(file), charset);
    }

    public static List readFileAsStringList(File file, Charset charset) throws IOException {
        ObjectUtils.makeSureNotNull(file, charset);
        return IOUtils.inputStreamToStringLines(IOUtils.getFileInputStream(file), charset);
    }

    public static byte[] readFileAsByteArray(File file, byte[] byArray) throws IOException {
        ObjectUtils.makeSureNotNull((Object)file);
        return IOUtils.toByteArray(IOUtils.getFileInputStream(file), byArray);
    }

    public static void writeStringToFile(File file, String string, Charset charset) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter((OutputStream)new FileOutputStream(file), charset));
        bufferedWriter.write(string);
        bufferedWriter.close();
    }

    public static void writeByteArrayToFile(File file, byte[] byArray) throws IOException {
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
        bufferedOutputStream.write(byArray);
        bufferedOutputStream.close();
    }

    public static void writeInputStreamToFile(File file, InputStream inputStream, byte[] byArray) throws IOException {
        int n;
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
        while ((n = inputStream.read(byArray)) != -1) {
            bufferedOutputStream.write(byArray, 0, n);
        }
        inputStream.close();
        bufferedOutputStream.close();
    }
}

