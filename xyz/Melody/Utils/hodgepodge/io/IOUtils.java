/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.Utils.hodgepodge.io;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class IOUtils {
    private static final byte[] NORMAL_BUFFER = new byte[4096];
    private static final String lineSeparator = System.lineSeparator();

    private IOUtils() {
    }

    public static byte[] toByteArray(InputStream inputStream, byte[] byArray) throws IOException {
        int n;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while ((n = inputStream.read(byArray)) != -1) {
            byteArrayOutputStream.write(byArray, 0, n);
        }
        return byteArrayOutputStream.toByteArray();
    }

    public static void close(Closeable closeable) throws IOException {
        closeable.close();
    }

    public static void close(Closeable ... closeableArray) throws IOException {
        for (Closeable closeable : closeableArray) {
            IOUtils.close(closeable);
        }
    }

    public static void closeQuietly(Closeable closeable) {
        try {
            IOUtils.close(closeable);
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    public static void closeQuietly(Closeable ... closeableArray) {
        try {
            IOUtils.close(closeableArray);
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    public static void flush(Flushable flushable) throws IOException {
        flushable.flush();
    }

    public static void flush(Flushable ... flushableArray) throws IOException {
        for (Flushable flushable : flushableArray) {
            IOUtils.flush(flushable);
        }
    }

    public static void flushQuietly(Flushable flushable) {
        try {
            IOUtils.flush(flushable);
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    public static void flushQuietly(Flushable ... flushableArray) {
        try {
            IOUtils.flush(flushableArray);
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    public static InputStream getFileInputStreamByURL(File file) throws IOException {
        return file.toURI().toURL().openStream();
    }

    public static FileInputStream getFileInputStream(File file) throws IOException {
        return new FileInputStream(file);
    }

    public static URL getResource(String string) {
        return IOUtils.class.getResource("/" + string);
    }

    public static InputStream getResourceAsStream(String string) {
        return IOUtils.class.getResourceAsStream("/" + string);
    }

    public static String inputStreamToString(InputStream inputStream, String string) throws IOException {
        return IOUtils.inputStreamToString(inputStream, Charset.forName(string));
    }

    public static String inputStreamToString(InputStream inputStream, Charset charset) throws IOException {
        String string;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charset));
        StringBuilder stringBuilder = new StringBuilder();
        while ((string = bufferedReader.readLine()) != null) {
            stringBuilder.append(string).append(lineSeparator);
        }
        inputStream.close();
        bufferedReader.close();
        return stringBuilder.toString();
    }

    public static List inputStreamStringLines(InputStream inputStream, String string) throws IOException {
        return IOUtils.inputStreamToStringLines(inputStream, Charset.forName(string));
    }

    public static List inputStreamToStringLines(InputStream inputStream, Charset charset) throws IOException {
        String string;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charset));
        ArrayList<String> arrayList = new ArrayList<String>();
        while ((string = bufferedReader.readLine()) != null) {
            arrayList.add(string);
        }
        inputStream.close();
        bufferedReader.close();
        return arrayList;
    }

    public static int getInputStreamSize(InputStream inputStream, byte[] byArray) throws IOException {
        int n;
        int n2 = 0;
        while ((n = inputStream.read(byArray)) != -1) {
            n2 += n;
        }
        return n2;
    }

    public static boolean inputStreamEquals(InputStream inputStream, InputStream inputStream2, byte[] byArray) throws IOException {
        byte[] byArray2;
        if (inputStream == inputStream2) {
            return true;
        }
        if (inputStream == null || inputStream2 == null) {
            return false;
        }
        byte[] byArray3 = IOUtils.toByteArray(inputStream, byArray);
        if (byArray3.length != (byArray2 = IOUtils.toByteArray(inputStream2, byArray)).length) {
            return false;
        }
        for (int i = 0; i < byArray3.length; ++i) {
            if (byArray3[i] == byArray2[i]) continue;
            return false;
        }
        return true;
    }

    public static byte[] normalBuffer() {
        return NORMAL_BUFFER;
    }

    public static byte[] newBuffer() {
        return IOUtils.newBuffer(4096);
    }

    public static byte[] newBuffer(int n) {
        return new byte[n];
    }
}

