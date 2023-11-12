/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.common;

import chrriis.dj.nativeswing.NSSystemProperty;
import chrriis.dj.nativeswing.common.Base64;
import chrriis.dj.nativeswing.common.SystemProperty;
import chrriis.dj.nativeswing.common.lI;
import chrriis.dj.nativeswing.common.lII;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

public class Utils {
    public static final boolean IS_JAVA_6_OR_GREATER = SystemProperty.JAVA_VERSION.get().compareTo("1.6") >= 0;
    public static final boolean IS_JAVA_7_OR_GREATER = SystemProperty.JAVA_VERSION.get().compareTo("1.7") >= 0;
    public static final boolean IS_MAC;
    public static final boolean IS_WINDOWS;
    public static final boolean IS_32_BIT;
    public static final boolean IS_64_BIT;
    public static final boolean IS_WEBSTART;
    public static final boolean IS_WINDOWS_VISTA_OR_GREATER;
    public static final boolean IS_WINDOWS_7_OR_GREATER;
    public static final String LINE_SEPARATOR;
    private static String localHostAddress;

    private Utils() {
    }

    public static String decodeURL(String string) {
        try {
            return URLDecoder.decode(string, "UTF-8");
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            unsupportedEncodingException.printStackTrace();
            return null;
        }
    }

    public static String encodeURL(String string) {
        String string2;
        try {
            string2 = URLEncoder.encode(string, "UTF-8");
        }
        catch (Exception exception) {
            string2 = URLEncoder.encode(string);
        }
        return string2.replaceAll("\\+", "%20");
    }

    public static String encodeBase64(String string, boolean bl) {
        return Base64.encode(string, bl);
    }

    public static String decodeBase64(String string) {
        return Base64.decode(string);
    }

    public static String escapeXML(String string) {
        if (string == null || string.length() == 0) {
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder((int)((double)string.length() * 1.1));
        block7: for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            switch (c) {
                case '<': {
                    stringBuilder.append("&lt;");
                    continue block7;
                }
                case '>': {
                    stringBuilder.append("&gt;");
                    continue block7;
                }
                case '&': {
                    stringBuilder.append("&amp;");
                    continue block7;
                }
                case '\'': {
                    stringBuilder.append("&apos;");
                    continue block7;
                }
                case '\"': {
                    stringBuilder.append("&quot;");
                    continue block7;
                }
                default: {
                    stringBuilder.append(c);
                }
            }
        }
        return stringBuilder.toString();
    }

    public static File getLocalFile(String string) {
        File file;
        if (string == null) {
            return null;
        }
        if (string.startsWith("file:") && (file = new File(Utils.decodeURL(string.substring(5)))).exists()) {
            return Utils.simplifyLocalFile(file);
        }
        file = new File(string);
        if (file.exists()) {
            return Utils.simplifyLocalFile(file);
        }
        return null;
    }

    private static File simplifyLocalFile(File file) {
        try {
            File file2 = file.getCanonicalFile();
            if (file2.exists()) {
                return file2;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return file;
    }

    public static File getClassPathFile(String string) {
        File file = Utils.getJARFile(string);
        return file != null ? file : Utils.getDirectory(string);
    }

    public static File getClassPathFile(Class clazz) {
        File file = Utils.getJARFile(clazz);
        return file != null ? file : Utils.getDirectory(clazz);
    }

    public static File getJARFile(String string) {
        if (!string.startsWith("/")) {
            string = '/' + string;
        }
        return Utils.getJARFile(Utils.class, string);
    }

    public static File getJARFile(Class clazz) {
        return Utils.getJARFile(clazz, "/" + clazz.getName().replace('.', '/') + ".class");
    }

    private static File getJARFile(Class clazz, String string) {
        URL uRL = Utils.getResourceWithinJavaModules(clazz, string);
        if (uRL == null) {
            return null;
        }
        String string2 = uRL.toExternalForm();
        if (string2 != null && string2.startsWith("jar:file:") && (string2 = string2.substring(9)).endsWith("!" + string)) {
            return new File(Utils.decodeURL(string2.substring(0, string2.length() - 1 - string.length()).replace("+", "%2B")));
        }
        return null;
    }

    public static File getDirectory(String string) {
        if (!string.startsWith("/")) {
            string = '/' + string;
        }
        return Utils.getDirectory(Utils.class, string);
    }

    public static File getDirectory(Class clazz) {
        return Utils.getDirectory(clazz, "/" + clazz.getName().replace('.', '/') + ".class");
    }

    private static File getDirectory(Class clazz, String string) {
        URL uRL;
        String string2 = string;
        if (string2.startsWith("/")) {
            string2 = string2.substring(1);
        }
        if ((uRL = Utils.getResourceWithinJavaModules(clazz, string)) == null) {
            return null;
        }
        String string3 = uRL.toExternalForm();
        if (string3 != null && string3.startsWith("file:")) {
            File file = new File(Utils.decodeURL(string3.substring(5))).getParentFile();
            for (int i = 0; i < string2.length(); ++i) {
                if (string2.charAt(i) != '/') continue;
                file = file.getParentFile();
            }
            return file;
        }
        return null;
    }

    public static URL getResourceWithinJavaModules(Class clazz, String string) {
        URL uRL = clazz.getResource(string);
        if (uRL == null && string.startsWith("/")) {
            uRL = clazz.getClassLoader().getResource(string.substring(1));
        }
        return uRL;
    }

    public static InputStream getResourceAsStreamWithinJavaModules(Class clazz, String string) {
        InputStream inputStream = clazz.getResourceAsStream(string);
        if (inputStream == null && string.startsWith("/")) {
            inputStream = clazz.getClassLoader().getResourceAsStream(string.substring(1));
        }
        return inputStream;
    }

    public static void deleteAll(File file) {
        if (!file.delete() && file.isDirectory()) {
            for (File file2 : file.listFiles()) {
                Utils.deleteAll(file2);
            }
            file.delete();
        }
    }

    public static boolean equals(Object object, Object object2) {
        return object == null ? object2 == null : object.equals(object2);
    }

    public static String arrayDeepToString(Object object) {
        if (object == null) {
            return null;
        }
        Class<?> clazz = object.getClass();
        if (!clazz.isArray()) {
            return null;
        }
        if (clazz == boolean[].class) {
            return Arrays.toString((boolean[])object);
        }
        if (clazz == byte[].class) {
            return Arrays.toString((byte[])object);
        }
        if (clazz == short[].class) {
            return Arrays.toString((short[])object);
        }
        if (clazz == char[].class) {
            return Arrays.toString((char[])object);
        }
        if (clazz == int[].class) {
            return Arrays.toString((int[])object);
        }
        if (clazz == long[].class) {
            return Arrays.toString((long[])object);
        }
        if (clazz == float[].class) {
            return Arrays.toString((float[])object);
        }
        if (clazz == double[].class) {
            return Arrays.toString((double[])object);
        }
        return Arrays.deepToString((Object[])object);
    }

    public static String simplifyPath(String string) {
        if (string.indexOf("//") != -1) {
            throw new IllegalArgumentException("The path is invalid: " + string);
        }
        String[] stringArray = string.split("/");
        ArrayList<String> arrayList = new ArrayList<String>(stringArray.length);
        for (String string2 : stringArray) {
            if ("".equals(string2) || ".".equals(string2)) continue;
            if ("..".equals(string2)) {
                int n = arrayList.size() - 1;
                if (n == -1) {
                    throw new IllegalArgumentException("The path is invalid: " + string);
                }
                arrayList.remove(n);
                continue;
            }
            arrayList.add(string2);
        }
        StringBuilder stringBuilder = new StringBuilder(string.length());
        if (string.startsWith("/")) {
            stringBuilder.append('/');
        }
        int n = arrayList.size();
        for (int i = 0; i < n; ++i) {
            if (i > 0) {
                stringBuilder.append('/');
            }
            stringBuilder.append((String)arrayList.get(i));
        }
        if (string.length() > 1 && string.endsWith("/")) {
            stringBuilder.append('/');
        }
        return stringBuilder.toString();
    }

    public static void printStackTraces() {
        Utils.printStackTraces(System.err);
    }

    public static void printStackTraces(PrintStream printStream) {
        printStream.print(Utils.getStackTracesAsString());
    }

    public static void printStackTraces(PrintWriter printWriter) {
        printWriter.print(Utils.getStackTracesAsString());
    }

    private static String getStackTracesAsString() {
        Map<Thread, StackTraceElement[]> map = Thread.getAllStackTraces();
        Thread[] threadArray = map.keySet().toArray(new Thread[0]);
        Arrays.sort(threadArray, new lI());
        StringBuilder stringBuilder = new StringBuilder();
        for (Thread thread : threadArray) {
            StackTraceElement[] stackTraceElementArray;
            stringBuilder.append(thread.isDaemon() ? "Daemon Thread [" : "Thread [").append(thread.getName()).append("] (").append((Object)thread.getState()).append(")").append(LINE_SEPARATOR);
            for (StackTraceElement stackTraceElement : stackTraceElementArray = map.get(thread)) {
                stringBuilder.append("\tat ").append(stackTraceElement).append(LINE_SEPARATOR);
            }
        }
        return stringBuilder.toString();
    }

    public static String getLocalHostAddress() {
        Class<Utils> clazz = Utils.class;
        synchronized (Utils.class) {
            if (localHostAddress != null) {
                // ** MonitorExit[var0] (shouldn't be in output)
                return "".equals(localHostAddress) ? null : localHostAddress;
            }
            String string = NSSystemProperty.LOCALHOSTADDRESS.get();
            if ("_localhost_".equals(string)) {
                try {
                    string = InetAddress.getLocalHost().getHostAddress();
                }
                catch (Exception exception) {
                    string = null;
                }
            }
            if (string == null) {
                boolean bl = Boolean.parseBoolean(NSSystemProperty.LOCALHOSTADDRESS_DEBUG_PRINTDETECTION.get());
                string = Utils.getLocalHostAddress(0, bl);
            }
            if (Boolean.parseBoolean(NSSystemProperty.LOCALHOSTADDRESS_DEBUG_PRINT.get())) {
                System.err.println("Local host address: " + string);
            }
            localHostAddress = string == null ? "" : string;
            // ** MonitorExit[var0] (shouldn't be in output)
            return string;
        }
    }

    public static String getLocalHostAddress(int n) {
        return Utils.getLocalHostAddress(n, false);
    }

    private static String getLocalHostAddress(int n, boolean bl) {
        Object object;
        Object object2;
        if (bl) {
            System.err.println("Local host address detection using " + (n == 0 ? "an automatic port" : "port " + n) + ":");
        }
        String string = "127.0.0.1";
        if (bl) {
            System.err.print("  Trying 127.0.0.1: ");
        }
        if (Utils.isLocalHostAddressReachable(string, n)) {
            if (bl) {
                System.err.println("success.");
            }
            return string;
        }
        if (bl) {
            System.err.println("failed.");
        }
        ArrayList<InetAddress> arrayList = new ArrayList<InetAddress>();
        try {
            object2 = NetworkInterface.getNetworkInterfaces();
            while (object2.hasMoreElements()) {
                NetworkInterface object3 = object2.nextElement();
                object = object3.getInetAddresses();
                while (object.hasMoreElements()) {
                    InetAddress inetAddress = object.nextElement();
                    if (string.equals(inetAddress.getHostAddress())) continue;
                    arrayList.add(inetAddress);
                }
            }
        }
        catch (SocketException socketException) {
            // empty catch block
        }
        Collections.sort(arrayList, new lII());
        if (bl) {
            System.err.println("  Trying addresses: " + arrayList);
        }
        for (InetAddress inetAddress : arrayList) {
            object = inetAddress.getHostAddress();
            if (bl) {
                System.err.print("    " + (String)object + ": ");
            }
            if (Utils.isLocalHostAddressReachable((String)object, n)) {
                if (bl) {
                    System.err.println("success.");
                }
                return object;
            }
            if (!bl) continue;
            System.err.println("failed.");
        }
        try {
            if (bl) {
                System.err.print("  Trying LocalHost: ");
            }
            object2 = InetAddress.getLocalHost().getHostAddress();
            if (bl) {
                System.err.print("success (" + (String)object2 + ").");
            }
            return object2;
        }
        catch (Exception exception) {
            if (bl) {
                System.err.println("failed.");
                System.err.println("  Failed to find a suitable local host address!");
            }
            return null;
        }
    }

    private static boolean isLocalHostAddressReachable(String string, int n) {
        boolean bl = false;
        try {
            ServerSocket serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(InetAddress.getByName(string), n));
            n = serverSocket.getLocalPort();
            try {
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(string, n), 500);
                bl = true;
                socket.close();
            }
            catch (Exception exception) {
                try {
                    serverSocket.close();
                }
                catch (IOException iOException) {
                    // empty catch block
                }
            }
            serverSocket.close();
        }
        catch (Exception exception) {
            // empty catch block
        }
        return bl;
    }

    static {
        IS_WEBSTART = SystemProperty.JAVAWEBSTART_VERSION.get() != null;
        String string = SystemProperty.OS_NAME.get();
        IS_MAC = string.startsWith("Mac") || string.startsWith("Darwin");
        IS_WINDOWS = string.startsWith("Windows");
        String string2 = SystemProperty.OS_ARCH.get();
        IS_64_BIT = "x86_64".equals(string2) || "x64".equals(string2) || "amd64".equals(string2) || "ia64".equals(string2) || "ppc64".equals(string2) || "IA64N".equals(string2) || "64".equals(SystemProperty.SUN_ARCH_DATA_MODEL.get()) || "64".equals(SystemProperty.COM_IBM_VM_BITMODE.get());
        IS_32_BIT = !IS_64_BIT;
        IS_WINDOWS_VISTA_OR_GREATER = IS_WINDOWS && SystemProperty.OS_VERSION.get().compareTo("6.0") >= 0;
        IS_WINDOWS_7_OR_GREATER = IS_WINDOWS && SystemProperty.OS_VERSION.get().compareTo("6.1") >= 0;
        LINE_SEPARATOR = SystemProperty.LINE_SEPARATOR.get();
    }
}

