/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.common;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class NetworkURLClassLoader
extends ClassLoader {
    private final URL codebaseURL;

    public NetworkURLClassLoader(String string) throws MalformedURLException {
        this.codebaseURL = new URL(string);
    }

    @Override
    protected URL findResource(String string) {
        try {
            return new URL(this.codebaseURL, string);
        }
        catch (MalformedURLException malformedURLException) {
            malformedURLException.printStackTrace();
            return null;
        }
    }

    protected Class findClass(String string) throws ClassNotFoundException {
        Exception exception = null;
        for (int i = 0; i < 2; ++i) {
            String string2 = string.replace('.', '/') + ".class";
            URL uRL = this.getResource(string2);
            InputStream inputStream = null;
            Class<?> clazz = null;
            exception = null;
            try {
                int n;
                URLConnection uRLConnection = uRL.openConnection();
                uRLConnection.setReadTimeout(4000);
                inputStream = uRLConnection.getInputStream();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] byArray = new byte[1024];
                while ((n = inputStream.read(byArray)) != -1) {
                    byteArrayOutputStream.write(byArray, 0, n);
                }
                byArray = byteArrayOutputStream.toByteArray();
                clazz = this.defineClass(string, byArray, 0, byArray.length);
            }
            catch (Exception exception2) {
                exception = exception2;
            }
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            }
            catch (Exception exception3) {
                // empty catch block
            }
            if (clazz == null) continue;
            return clazz;
        }
        throw new ClassNotFoundException(string, exception);
    }

    public static void main(String[] stringArray) throws Exception {
        Method method;
        String string = stringArray[0];
        String string2 = stringArray[1];
        String[] stringArray2 = new String[stringArray.length - 2];
        System.arraycopy(stringArray, 2, stringArray2, 0, stringArray2.length);
        try {
            Class<?> clazz = new NetworkURLClassLoader(string).loadClass(string2);
            method = clazz.getDeclaredMethod("main", String[].class);
            method.setAccessible(true);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            System.exit(-1);
            return;
        }
        method.invoke(null, new Object[]{stringArray2});
    }
}

