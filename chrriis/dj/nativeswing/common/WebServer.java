/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.common;

import chrriis.dj.nativeswing.NSSystemProperty;
import chrriis.dj.nativeswing.common.I;
import chrriis.dj.nativeswing.common.MimeTypes;
import chrriis.dj.nativeswing.common.ObjectRegistry;
import chrriis.dj.nativeswing.common.SystemProperty;
import chrriis.dj.nativeswing.common.Utils;
import chrriis.dj.nativeswing.common.l;
import chrriis.dj.nativeswing.common.lIIl;
import chrriis.dj.nativeswing.common.lIlI;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class WebServer {
    private int port;
    private volatile boolean isRunning;
    private volatile ServerSocket serverSocket;
    private volatile int instanceID;
    private List referenceClassLoaderList = new ArrayList(1);
    private List contentProviderList = new ArrayList();
    private static WebServer webServer;
    private static Object LOCK;
    private static String hostAddress;

    public WebServer() {
        this(0);
    }

    public WebServer(int n) {
        this.port = n;
    }

    public void stop() {
        this.isRunning = false;
        if (this.serverSocket != null) {
            ServerSocket serverSocket = this.serverSocket;
            this.serverSocket = null;
            try {
                serverSocket.close();
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    public void start() throws IOException {
        this.start(true);
    }

    public void start(boolean bl) throws IOException {
        if (this.isRunning) {
            return;
        }
        this.isRunning = true;
        this.instanceID = ObjectRegistry.getInstance().add(this);
        this.serverSocket = new ServerSocket();
        this.serverSocket.bind(new InetSocketAddress(InetAddress.getByName(WebServer.getHostAddress()), this.port));
        this.port = this.serverSocket.getLocalPort();
        if (Boolean.parseBoolean(NSSystemProperty.WEBSERVER_DEBUG_PRINTPORT.get())) {
            System.err.println("Web Server port: " + this.port);
        }
        lIIl lIIl2 = new lIIl(this, "WebServer");
        lIIl2.setDaemon(bl);
        lIIl2.start();
    }

    public int getPort() {
        return this.port;
    }

    public String getURLPrefix() {
        if (hostAddress.indexOf(58) >= 0) {
            return "http://[" + hostAddress + "]:" + this.port;
        }
        return "http://" + hostAddress + ":" + this.port;
    }

    public String getDynamicContentURL(String string, String string2) {
        return this.getURLPrefix() + "/class/" + this.instanceID + "/" + string + "/" + Utils.encodeURL(string2);
    }

    public String getDynamicContentURL(String string, String string2, String string3) {
        return this.getURLPrefix() + "/class/" + this.instanceID + "/" + string + "/" + string2 + "/" + Utils.encodeURL(string3);
    }

    public String getClassPathResourceURL(String string, String string2) {
        if (!string2.startsWith("/")) {
            String string3 = string.replace('.', '/');
            string3 = string3.substring(0, string3.lastIndexOf(47) + 1);
            string2 = "/" + string3 + string2;
        }
        return this.getURLPrefix() + "/classpath/" + this.instanceID + Utils.simplifyPath(string2);
    }

    public String getResourcePathURL(String string, String string2) {
        if (string == null) {
            string = new File(SystemProperty.USER_DIR.get()).getAbsolutePath();
        }
        if (Boolean.parseBoolean(NSSystemProperty.WEBSERVER_ACTIVATEOLDRESOURCEMETHOD.get())) {
            if (Utils.IS_WINDOWS) {
                string = string.replace('\\', '/');
                string2 = string2.replace('\\', '/');
            }
            return this.getURLPrefix() + "/resource/" + Utils.encodeURL(string) + "/" + Utils.encodeURL(string2);
        }
        return this.getURLPrefix() + "/location/" + Utils.encodeBase64(string, true) + "/" + Utils.encodeURL(string2);
    }

    public WebServerContent getURLContent(String string) {
        try {
            HTTPRequest hTTPRequest = new HTTPRequest(new URL(string).getPath(), null);
            return WebServer.getWebServerContent(hTTPRequest);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public void addReferenceClassLoader(ClassLoader classLoader) {
        if (classLoader == null || classLoader == this.getClass().getClassLoader()) {
            return;
        }
        this.referenceClassLoaderList.add(0, classLoader);
    }

    public void removeReferenceClassLoader(ClassLoader classLoader) {
        if (classLoader == null || classLoader == this.getClass().getClassLoader()) {
            return;
        }
        this.referenceClassLoaderList.remove(classLoader);
    }

    public void addContentProvider(WebServerContentProvider webServerContentProvider) {
        this.contentProviderList.add(webServerContentProvider);
    }

    public void removeContentProvider(WebServerContentProvider webServerContentProvider) {
        this.contentProviderList.remove(webServerContentProvider);
    }

    protected static WebServerContent getWebServerContent(HTTPRequest hTTPRequest) {
        int n;
        String string = hTTPRequest.getResourcePath();
        if (string.startsWith("/")) {
            string = string.substring(1);
        }
        if ((n = string.indexOf(47)) != -1) {
            String string2 = string.substring(0, n);
            string = string.substring(n + 1);
            if ("class".equals(string2)) {
                n = string.indexOf(47);
                WebServer webServer = (WebServer)ObjectRegistry.getInstance().get(Integer.parseInt(string.substring(0, n)));
                if (webServer == null) {
                    return null;
                }
                string = string.substring(n + 1);
                n = string.indexOf(47);
                String string3 = string.substring(0, n);
                string = Utils.decodeURL(string.substring(n + 1));
                hTTPRequest = hTTPRequest.clone();
                try {
                    Class<?> clazz = null;
                    for (ClassLoader classLoader : webServer.referenceClassLoaderList) {
                        try {
                            clazz = Class.forName(string3, true, classLoader);
                            break;
                        }
                        catch (Exception exception) {
                        }
                    }
                    if (clazz == null) {
                        clazz = Class.forName(string3);
                    }
                    Method method = clazz.getDeclaredMethod("getWebServerContent", HTTPRequest.class);
                    method.setAccessible(true);
                    hTTPRequest.setResourcePath(string);
                    return (WebServerContent)method.invoke(null, hTTPRequest);
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                    return null;
                }
            }
            if ("classpath".equals(string2)) {
                n = string.indexOf(47);
                WebServer webServer = (WebServer)ObjectRegistry.getInstance().get(Integer.parseInt(string.substring(0, n)));
                if (webServer == null) {
                    return null;
                }
                string = string.substring(n + 1);
                String string4 = Utils.decodeURL(WebServer.removeHTMLAnchor(string));
                return new lIlI(string4, webServer);
            }
            if ("location".equals(string2)) {
                String string5;
                Object object;
                n = string.indexOf(47);
                String string6 = Utils.decodeBase64(string.substring(0, n));
                string = Utils.decodeURL(WebServer.removeHTMLAnchor(string.substring(n + 1)));
                try {
                    object = new URL(string6);
                    int n2 = ((URL)object).getPort();
                    string5 = ((URL)object).getProtocol() + "://" + ((URL)object).getHost() + (n2 != -1 ? ":" + n2 : "");
                    if (string.startsWith("/")) {
                        string5 = string5 + string;
                    } else {
                        String string7 = ((URL)object).getPath();
                        string7 = string7.substring(0, string7.lastIndexOf(47) + 1) + string;
                        string5 = string5 + (string7.startsWith("/") ? string7 : "/" + string7);
                    }
                }
                catch (Exception exception) {
                    File file = Utils.getLocalFile(new File(string6, string).getAbsolutePath());
                    string5 = file != null ? new File(string6, string).toURI().toString() : string6 + "/" + string;
                }
                object = string5;
                return new l((String)object);
            }
            if ("resource".equals(string2)) {
                String string8;
                Object object;
                String string9;
                n = string.indexOf(47);
                if (n > 0 && (string9 = string.substring(n - 1)).startsWith("://")) {
                    n = string.indexOf(47, n + 2);
                }
                string9 = Utils.decodeURL(string.substring(0, n));
                string = Utils.decodeURL(string.substring(n + 1));
                try {
                    object = new URL(string9);
                    int n3 = ((URL)object).getPort();
                    string8 = ((URL)object).getProtocol() + "://" + ((URL)object).getHost() + (n3 != -1 ? ":" + n3 : "");
                    if (string.startsWith("/")) {
                        string8 = string8 + WebServer.removeHTMLAnchor(string);
                    } else {
                        String string10 = ((URL)object).getPath();
                        string10 = string10.substring(0, string10.lastIndexOf(47) + 1) + string;
                        string8 = string8 + (string10.startsWith("/") ? string10 : "/" + string10);
                    }
                }
                catch (Exception exception) {
                    File file = Utils.getLocalFile(new File(string9, WebServer.removeHTMLAnchor(string)).getAbsolutePath());
                    string8 = file != null ? new File(string9, WebServer.removeHTMLAnchor(string)).toURI().toString() : string9 + "/" + WebServer.removeHTMLAnchor(string);
                }
                object = string8;
                return new I((String)object);
            }
        }
        for (WebServerContentProvider webServerContentProvider : WebServer.webServer.contentProviderList) {
            WebServerContent webServerContent = webServerContentProvider.getWebServerContent(hTTPRequest);
            if (webServerContent == null) continue;
            return webServerContent;
        }
        return null;
    }

    private static String removeHTMLAnchor(String string) {
        int n = string.indexOf(35);
        if (n > 0) {
            string = string.substring(0, n);
        }
        return string;
    }

    private static String getHostAddress() {
        return hostAddress;
    }

    public static void stopDefaultWebServer() {
        Object object = LOCK;
        synchronized (object) {
            if (webServer != null) {
                webServer.stop();
                webServer = null;
            }
        }
    }

    public static WebServer getDefaultWebServer() {
        Object object = LOCK;
        synchronized (object) {
            if (webServer != null) {
                return webServer;
            }
            webServer = new WebServer();
            try {
                boolean bl = "applet".equals(NSSystemProperty.DEPLOYMENT_TYPE.get());
                webServer.start(!bl);
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
            return webServer;
        }
    }

    static boolean access$000(WebServer webServer) {
        return webServer.isRunning;
    }

    static ServerSocket access$100(WebServer webServer) {
        return webServer.serverSocket;
    }

    static ServerSocket access$102(WebServer webServer, ServerSocket serverSocket) {
        webServer.serverSocket = serverSocket;
        return webServer.serverSocket;
    }

    static int access$300(WebServer webServer) {
        return webServer.instanceID;
    }

    static List access$400(WebServer webServer) {
        return webServer.referenceClassLoaderList;
    }

    static {
        LOCK = new Object();
        String string = Utils.getLocalHostAddress();
        if (string == null) {
            string = "127.0.0.1";
        }
        hostAddress = string;
    }

    public static interface WebServerContentProvider {
        public WebServerContent getWebServerContent(HTTPRequest var1);
    }

    private static class WebServerConnectionThread
    extends Thread {
        private static int threadInitNumber;
        private static Semaphore semaphore;
        private Socket socket;
        private static final String LS;

        private static synchronized int nextThreadNumber() {
            return threadInitNumber++;
        }

        public WebServerConnectionThread(Socket socket) {
            super("WebServer Connection-" + WebServerConnectionThread.nextThreadNumber());
            this.socket = socket;
            this.setDaemon(true);
        }

        static void writeHTTPHeaders(BufferedOutputStream bufferedOutputStream, int n, String string, long l2, long l3) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("HTTP/1.0 " + n + " OK" + LS);
            stringBuilder.append("Content-Type: " + string + LS);
            stringBuilder.append("Server: WebServer/1.0" + LS);
            stringBuilder.append("Date: " + new Date() + LS);
            if (l2 != -1L) {
                stringBuilder.append("Content-Length: " + l2 + LS);
            }
            stringBuilder.append(LS);
            try {
                bufferedOutputStream.write(stringBuilder.toString().getBytes("UTF-8"));
            }
            catch (IOException iOException) {
                iOException.printStackTrace();
            }
        }

        static void writeHTTPError(BufferedOutputStream bufferedOutputStream, int n, String string) {
            WebServerConnectionThread.writeHTTPHeaders(bufferedOutputStream, n, "text/html", string.length(), System.currentTimeMillis());
            try {
                bufferedOutputStream.write(string.getBytes("UTF-8"));
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }

        @Override
        public void run() {
            long l2;
            boolean bl;
            boolean bl2;
            WebServerContent webServerContent;
            ArrayList<Integer> arrayList;
            Object object;
            String string;
            boolean bl3;
            BufferedOutputStream bufferedOutputStream;
            HTTPInputStream hTTPInputStream;
            block39: {
                Object object2;
                String string2;
                block38: {
                    block37: {
                        hTTPInputStream = new HTTPInputStream(new BufferedInputStream(this.socket.getInputStream()));
                        bufferedOutputStream = new BufferedOutputStream(this.socket.getOutputStream());
                        string2 = hTTPInputStream.readAsciiLine();
                        if (string2 != null && (string2.endsWith(" HTTP/1.0") || string2.endsWith("HTTP/1.1"))) break block37;
                        WebServerConnectionThread.writeHTTPError(bufferedOutputStream, 500, "Invalid Method.");
                        bufferedOutputStream.flush();
                        bufferedOutputStream.close();
                        hTTPInputStream.close();
                        this.socket.close();
                        semaphore.release();
                        return;
                    }
                    bl3 = false;
                    if (string2.startsWith("POST ")) {
                        bl3 = true;
                        break block38;
                    }
                    if (string2.startsWith("GET ")) break block38;
                    WebServerConnectionThread.writeHTTPError(bufferedOutputStream, 500, "Invalid Method.");
                    bufferedOutputStream.flush();
                    bufferedOutputStream.close();
                    hTTPInputStream.close();
                    this.socket.close();
                    semaphore.release();
                    return;
                }
                string = string2.substring((bl3 ? "POST " : "GET ").length(), string2.length() - 9);
                HashMap<String, String> hashMap = new HashMap<String, String>();
                while (((String)(object2 = hTTPInputStream.readAsciiLine())).length() > 0) {
                    int n = ((String)object2).indexOf(": ");
                    if (n <= 0) continue;
                    hashMap.put(((String)object2).substring(0, n), ((String)object2).substring(n + 2));
                }
                object2 = new HTTPRequest(string, hashMap);
                ((HTTPRequest)object2).setPostMethod(bl3);
                if (bl3) {
                    Object object3;
                    Object object4;
                    HTTPData[] hTTPDataArray;
                    Object object5;
                    Object object6;
                    Object object7;
                    int n;
                    object = (String)hashMap.get("Content-Type");
                    String string3 = (String)hashMap.get("Content-Length");
                    int n2 = n = string3 == null ? -1 : Integer.parseInt(string3);
                    if (object != null && ((String)object).startsWith("multipart/")) {
                        int n3;
                        int n4;
                        if (n > 0) {
                            object7 = new byte[n];
                            hTTPInputStream.read((byte[])object7);
                        } else {
                            int n5;
                            object6 = new ByteArrayOutputStream();
                            object5 = new byte[1024];
                            while ((n5 = hTTPInputStream.read((byte[])object5)) != -1) {
                                ((ByteArrayOutputStream)object6).write((byte[])object5, 0, n5);
                            }
                            object7 = ((ByteArrayOutputStream)object6).toByteArray();
                        }
                        object6 = "--" + ((String)object).substring(((String)object).indexOf("boundary=") + 9);
                        object5 = ((String)object6).getBytes("UTF-8");
                        arrayList = new ArrayList<Integer>();
                        for (n4 = 0; n4 < ((Object)object7).length - ((char[])object5).length; ++n4) {
                            boolean bl4 = true;
                            for (n3 = 0; n3 < ((char[])object5).length; ++n3) {
                                if (object7[n4 + n3] == object5[n3]) continue;
                                bl4 = false;
                                break;
                            }
                            if (!bl4) continue;
                            arrayList.add(n4);
                            n4 += ((char[])object5).length;
                        }
                        hTTPDataArray = new HTTPData[arrayList.size() - 1];
                        for (n4 = 0; n4 < hTTPDataArray.length; ++n4) {
                            int n5;
                            HTTPData hTTPData;
                            hTTPDataArray[n4] = hTTPData = new HTTPData();
                            n3 = (Integer)arrayList.get(n4);
                            ByteArrayInputStream object8 = new ByteArrayInputStream((byte[])object7, n3, (Integer)arrayList.get(n4 + 1) - n3 - hTTPInputStream.getLineSeparator().length());
                            HTTPInputStream hTTPInputStream2 = new HTTPInputStream(object8);
                            hTTPInputStream2.readAsciiLine();
                            object4 = hTTPData.getHeaderMap();
                            while (((String)(object3 = hTTPInputStream2.readAsciiLine())).length() > 0) {
                                String string4 = ((String)object3).substring(((String)object3).indexOf(": "));
                                String string5 = ((String)object3).substring(string4.length() + 2);
                                object4.put(string4, string5);
                            }
                            object3 = new ByteArrayOutputStream();
                            while ((n5 = hTTPInputStream2.read()) != -1) {
                                ((ByteArrayOutputStream)object3).write(n5);
                            }
                            hTTPData.setBytes(((ByteArrayOutputStream)object3).toByteArray());
                        }
                    } else {
                        int n7;
                        object7 = new InputStreamReader((InputStream)hTTPInputStream, "UTF-8");
                        if (n > 0) {
                            object5 = new char[n];
                            int n8 = 0;
                            while (((char[])object5).length > n8) {
                                n7 = ((InputStreamReader)object7).read((char[])object5, n8, ((char[])object5).length - n8);
                                n8 = n7 == -1 ? ((Object)object5).length : n8 + n7;
                            }
                            object6 = new String((char[])object5);
                        } else {
                            object5 = new StringBuilder();
                            char[] cArray = new char[1024];
                            while ((n7 = ((Reader)object7).read(cArray)) != -1) {
                                object5.append(cArray, 0, n7);
                            }
                            object6 = object5.toString();
                        }
                        object5 = new HTTPData();
                        arrayList = object5.getHeaderMap();
                        for (String string6 : ((String)object6).split("&")) {
                            int n6 = string6.indexOf(61);
                            if (n6 > 0) {
                                object4 = string6.substring(0, n6);
                                object3 = Utils.decodeURL(string6.substring(n6 + 1));
                                arrayList.put(object4, object3);
                                continue;
                            }
                            arrayList.put(string6, "");
                        }
                        hTTPDataArray = new HTTPData[]{object5};
                    }
                    ((HTTPRequest)object2).setHTTPPostDataArray(hTTPDataArray);
                }
                webServerContent = WebServer.getWebServerContent((HTTPRequest)object2);
                object = null;
                if (webServerContent != null) {
                    try {
                        object = webServerContent.getInputStream();
                    }
                    catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
                bl2 = Boolean.parseBoolean(NSSystemProperty.WEBSERVER_DEBUG_PRINTREQUESTS.get());
                String string6 = NSSystemProperty.WEBSERVER_DEBUG_PRINTDATA.get();
                bl = false;
                l2 = -1L;
                if (string6 != null) {
                    try {
                        l2 = Long.parseLong(string6);
                        bl = true;
                    }
                    catch (Exception exception) {
                        bl = Boolean.parseBoolean(string6);
                        l2 = Integer.MAX_VALUE;
                    }
                }
                if (object != null) break block39;
                if (bl2) {
                    System.err.println("Web Server " + (bl3 ? "POST" : "GET") + ": " + string + " -> 404 (not found)");
                }
                WebServerConnectionThread.writeHTTPError(bufferedOutputStream, 404, "File Not Found.");
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
                hTTPInputStream.close();
                this.socket.close();
                semaphore.release();
                return;
            }
            try {
                int n;
                if (bl2 || bl) {
                    System.err.println("Web Server " + (bl3 ? "POST" : "GET") + ": " + string + " -> 200 (OK)");
                }
                arrayList = new BufferedInputStream((InputStream)object);
                WebServerConnectionThread.writeHTTPHeaders(bufferedOutputStream, 200, webServerContent.getContentType(), webServerContent.getContentLength(), webServerContent.getLastModified());
                byte[] byArray = new byte[4096];
                while ((n = ((FilterInputStream)((Object)arrayList)).read(byArray)) != -1) {
                    if (bl && n > 0 && l2 > 0L) {
                        System.err.print(new String(byArray, 0, (int)Math.min((long)n, l2), "UTF-8"));
                        l2 -= (long)n;
                    }
                    bufferedOutputStream.write(byArray, 0, n);
                }
                if (bl) {
                    System.err.println();
                }
                try {
                    ((BufferedInputStream)((Object)arrayList)).close();
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
                hTTPInputStream.close();
                this.socket.close();
                semaphore.release();
            }
            catch (Exception exception) {
                semaphore.release();
            }
        }

        static Semaphore access$200() {
            return semaphore;
        }

        static {
            semaphore = new Semaphore(10);
            LS = Utils.LINE_SEPARATOR;
        }

        private static class HTTPInputStream
        extends InputStream {
            private InputStream inputStream;
            private LineSeparator lineSeparator;
            private int lastByte = -1;

            public HTTPInputStream(InputStream inputStream) {
                this.inputStream = inputStream;
            }

            public String getLineSeparator() {
                switch (this.lineSeparator) {
                    case CR: {
                        return "\r";
                    }
                    case LF: {
                        return "\n";
                    }
                    case CRLF: {
                        return "\r\n";
                    }
                }
                return null;
            }

            public String readAsciiLine() throws IOException {
                if (this.lineSeparator == null) {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    while (true) {
                        int n;
                        if ((n = this.read()) == -1) {
                            return null;
                        }
                        if (n == 10) {
                            this.lineSeparator = LineSeparator.LF;
                            return new String(byteArrayOutputStream.toByteArray(), "UTF-8");
                        }
                        if (n == 13) {
                            int n2 = this.read();
                            if (n2 == 10) {
                                this.lineSeparator = LineSeparator.CRLF;
                            } else {
                                this.lineSeparator = LineSeparator.CR;
                                if (n2 != -1) {
                                    this.lastByte = n2;
                                }
                            }
                            return new String(byteArrayOutputStream.toByteArray(), "UTF-8");
                        }
                        byteArrayOutputStream.write(n);
                    }
                }
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                if (this.lastByte != -1) {
                    byteArrayOutputStream.write(this.lastByte);
                    this.lastByte = -1;
                }
                switch (this.lineSeparator) {
                    case CR: {
                        int n;
                        while ((n = this.read()) != 13 && n != -1) {
                            byteArrayOutputStream.write(n);
                        }
                        break;
                    }
                    case LF: {
                        int n;
                        while ((n = this.read()) != 10 && n != -1) {
                            byteArrayOutputStream.write(n);
                        }
                        break;
                    }
                    case CRLF: {
                        int n;
                        while ((n = this.read()) != 13 && n != -1) {
                            byteArrayOutputStream.write(n);
                        }
                        this.read();
                    }
                }
                return new String(byteArrayOutputStream.toByteArray(), "UTF-8");
            }

            @Override
            public void close() throws IOException {
                this.inputStream.close();
            }

            @Override
            public int read(byte[] byArray) throws IOException {
                return this.inputStream.read(byArray);
            }

            @Override
            public int read(byte[] byArray, int n, int n2) throws IOException {
                return this.inputStream.read(byArray, n, n2);
            }

            @Override
            public int read() throws IOException {
                int n = this.inputStream.read();
                return n;
            }

            static enum LineSeparator {
                CR,
                LF,
                CRLF;

            }
        }
    }

    public static abstract class WebServerContent {
        private static final String MIME_APPLICATION_OCTET_STREAM = "application/octet-stream";

        public static String getDefaultMimeType(String string) {
            String string2 = MimeTypes.getMimeType(string);
            return string2 == null ? MIME_APPLICATION_OCTET_STREAM : string2;
        }

        public abstract InputStream getInputStream();

        public static InputStream getInputStream(String string) {
            if (string == null) {
                return null;
            }
            try {
                return new ByteArrayInputStream(string.getBytes("UTF-8"));
            }
            catch (Exception exception) {
                exception.printStackTrace();
                return null;
            }
        }

        public String getContentType() {
            return WebServerContent.getDefaultMimeType(".html");
        }

        public long getContentLength() {
            return -1L;
        }

        public long getLastModified() {
            return System.currentTimeMillis();
        }
    }

    public static class HTTPData {
        private Map headerMap = new HashMap();
        private byte[] bytes;

        HTTPData() {
        }

        public Map getHeaderMap() {
            return this.headerMap;
        }

        public byte[] getBytes() {
            return this.bytes;
        }

        void setBytes(byte[] byArray) {
            this.bytes = byArray;
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class HTTPRequest
    implements Cloneable {
        private Map headerMap;
        private String endQuery = "";
        private String urlPath;
        private String resourcePath;
        private String anchor;
        private Map queryParameterMap = new HashMap();
        private boolean isPostMethod;
        private HTTPData[] httpPostDataArray;

        HTTPRequest(String string, Map map) {
            this.headerMap = map == null ? new HashMap() : map;
            this.setURLPath(string);
        }

        public Map getHeaderMap() {
            return this.headerMap;
        }

        void setURLPath(String string) {
            this.urlPath = string;
            this.resourcePath = string;
            int n = this.resourcePath.indexOf(63);
            if (n != -1) {
                String string2 = this.resourcePath.substring(n + 1);
                this.endQuery = '?' + string2;
                this.resourcePath = this.resourcePath.substring(0, n);
                for (String string3 : string2.split("&")) {
                    int n2 = string3.indexOf(61);
                    if (n2 > 0) {
                        String string4 = string3.substring(0, n2);
                        String string5 = Utils.decodeURL(string3.substring(n2 + 1));
                        this.queryParameterMap.put(string4, string5);
                        continue;
                    }
                    this.queryParameterMap.put(string3, "");
                }
            }
            if ((n = this.resourcePath.indexOf(35)) != -1) {
                this.anchor = this.resourcePath.substring(n + 1);
                this.endQuery = '#' + this.anchor + this.endQuery;
                this.resourcePath = this.resourcePath.substring(0, n);
            }
        }

        public String getURLPath() {
            return this.urlPath;
        }

        void setResourcePath(String string) {
            this.resourcePath = string;
            this.urlPath = string + this.endQuery;
        }

        public String getResourcePath() {
            return this.resourcePath;
        }

        public String getAnchor() {
            return this.anchor;
        }

        public Map getQueryParameterMap() {
            return this.queryParameterMap;
        }

        void setPostMethod(boolean bl) {
            this.isPostMethod = bl;
        }

        public boolean isPostMethod() {
            return this.isPostMethod;
        }

        void setHTTPPostDataArray(HTTPData[] hTTPDataArray) {
            this.httpPostDataArray = hTTPDataArray;
        }

        public HTTPData[] getHTTPPostDataArray() {
            return this.httpPostDataArray;
        }

        protected HTTPRequest clone() {
            try {
                HTTPRequest hTTPRequest = (HTTPRequest)super.clone();
                hTTPRequest.queryParameterMap = new HashMap(this.queryParameterMap);
                return hTTPRequest;
            }
            catch (CloneNotSupportedException cloneNotSupportedException) {
                throw new RuntimeException(cloneNotSupportedException);
            }
        }

        protected Object clone() throws CloneNotSupportedException {
            return this.clone();
        }
    }
}

