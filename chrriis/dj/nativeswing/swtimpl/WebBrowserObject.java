/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl;

import chrriis.dj.nativeswing.common.ObjectRegistry;
import chrriis.dj.nativeswing.common.Utils;
import chrriis.dj.nativeswing.common.WebServer;
import chrriis.dj.nativeswing.swtimpl.I;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.swtimpl.l;
import chrriis.dj.nativeswing.swtimpl.lIl;
import chrriis.dj.nativeswing.swtimpl.lIlI;
import chrriis.dj.nativeswing.swtimpl.lIll;
import chrriis.dj.nativeswing.swtimpl.llI;
import chrriis.dj.nativeswing.swtimpl.llII;
import chrriis.dj.nativeswing.swtimpl.llIl;
import chrriis.dj.nativeswing.swtimpl.lll;
import java.awt.Color;
import java.io.File;
import java.util.EventListener;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.SwingUtilities;
import javax.swing.event.EventListenerList;

public abstract class WebBrowserObject {
    private final JWebBrowser webBrowser;
    private volatile int instanceID;
    private volatile String resourcePath;
    private volatile String backgroundColor;
    private static final String LS = Utils.LINE_SEPARATOR;
    private EventListenerList listenerList = new EventListenerList();

    public WebBrowserObject(JWebBrowser jWebBrowser) {
        this.webBrowser = jWebBrowser;
        jWebBrowser.getNativeComponent().setBackground(Color.WHITE);
        jWebBrowser.setDefaultPopupMenuRegistered(false);
        jWebBrowser.setBarsVisible(false);
        jWebBrowser.addWebBrowserListener(new lIlI(this));
    }

    public String getLoadedResource() {
        return "".equals(this.resourcePath) ? null : this.resourcePath;
    }

    public boolean hasContent() {
        return this.resourcePath != null;
    }

    protected void finalize() throws Throwable {
        ObjectRegistry.getInstance().remove(this.instanceID);
        super.finalize();
    }

    public void load(String string) {
        this.backgroundColor = WebBrowserObject.getHexStringColor(this.webBrowser.getNativeComponent().getBackground());
        this.resourcePath = string;
        ObjectRegistry.getInstance().remove(this.instanceID);
        if (string == null) {
            if (!this.webBrowser.isNativePeerDisposed()) {
                this.webBrowser.setHTMLContent("");
            }
            return;
        }
        this.instanceID = ObjectRegistry.getInstance().add(this);
        String string2 = WebServer.getDefaultWebServer().getDynamicContentURL(WebBrowserObject.class.getName(), String.valueOf(this.instanceID), "html");
        AtomicBoolean atomicBoolean = new AtomicBoolean();
        lll lll2 = new lll(this, atomicBoolean);
        this.addInitializationListener(lll2);
        this.webBrowser.navigate(string2);
        this.webBrowser.getNativeComponent().runSync(new llIl(this), lll2, atomicBoolean);
    }

    public String getLocalFileURL(File file) {
        try {
            return file.toURI().toURL().toExternalForm();
        }
        catch (Exception exception) {
            return WebServer.getDefaultWebServer().getResourcePathURL(file.getParent(), file.getName());
        }
    }

    public static String getEmbeddedObjectJavascriptName() {
        return "myEmbeddedObject";
    }

    protected static WebServer.WebServerContent getWebServerContent(WebServer.HTTPRequest hTTPRequest) {
        String string = hTTPRequest.getResourcePath();
        int n = string.indexOf(47);
        int n2 = Integer.parseInt(string.substring(0, n));
        WebBrowserObject webBrowserObject = (WebBrowserObject)ObjectRegistry.getInstance().get(n2);
        if (webBrowserObject == null) {
            return null;
        }
        String string2 = string = string.substring(n + 1);
        if ("html".equals(string2)) {
            WebBrowserObject webBrowserObject2 = (WebBrowserObject)ObjectRegistry.getInstance().get(n2);
            if (webBrowserObject2 == null) {
                return new I();
            }
            return new llII(webBrowserObject2, webBrowserObject, n2);
        }
        if ("js".equals(string2)) {
            String string3 = webBrowserObject.resourcePath;
            File file = Utils.getLocalFile(string3);
            if (file != null) {
                string3 = webBrowserObject.getLocalFileURL(file);
            }
            String string4 = Utils.escapeXML(string3);
            String string5 = Utils.encodeURL(string3);
            return new llI(webBrowserObject, string5, string4);
        }
        if ("postCommand".equals(string2)) {
            WebServer.HTTPData hTTPData = hTTPRequest.getHTTPPostDataArray()[0];
            Map map = hTTPData.getHeaderMap();
            int n3 = map.size();
            String string6 = (String)map.get("j_command");
            String[] stringArray = new String[n3 - 1];
            for (int i = 0; i < stringArray.length; ++i) {
                stringArray[i] = (String)map.get("j_arg" + i);
            }
            SwingUtilities.invokeLater(new lIll(webBrowserObject, string6, stringArray));
            return new lIl();
        }
        String string7 = string;
        return new l(webBrowserObject, string7);
    }

    protected abstract ObjectHTMLConfiguration getObjectHtmlConfiguration();

    protected String getJavascriptDefinitions() {
        return null;
    }

    protected String getAdditionalHeadDefinitions() {
        return null;
    }

    private void addInitializationListener(InitializationListener initializationListener) {
        this.listenerList.add(InitializationListener.class, initializationListener);
    }

    private void removeInitializationListener(InitializationListener initializationListener) {
        this.listenerList.remove(InitializationListener.class, initializationListener);
    }

    public void setObjectProperty(String string, Object object) {
        this.webBrowser.executeJavascript("try {getEmbeddedObject()." + string + " = " + JWebBrowser.convertJavaObjectToJavascript(object) + ";} catch(exxxxx) {}");
    }

    public Object getObjectProperty(String string) {
        return this.webBrowser.executeJavascriptWithResult("return getEmbeddedObject()." + string);
    }

    public void invokeObjectFunction(String string, Object ... objectArray) {
        this.webBrowser.executeJavascript("try {getEmbeddedObject()." + JWebBrowser.createJavascriptFunctionCall(string, objectArray) + ";} catch(exxxxx) {}");
    }

    public Object invokeObjectFunctionWithResult(String string, Object ... objectArray) {
        return this.webBrowser.executeJavascriptWithResult("return getEmbeddedObject()." + JWebBrowser.createJavascriptFunctionCall(string, objectArray));
    }

    public void setBackground(Color color) {
        this.backgroundColor = WebBrowserObject.getHexStringColor(color);
        this.webBrowser.executeJavascript("document.bgColor = '" + this.backgroundColor + "';");
    }

    private static String getHexStringColor(Color color) {
        String string = Integer.toHexString(color.getRGB() & 0xFFFFFF).toUpperCase(Locale.ENGLISH);
        string = '#' + "000000".substring(string.length()) + string;
        return string;
    }

    static EventListenerList access$000(WebBrowserObject webBrowserObject) {
        return webBrowserObject.listenerList;
    }

    static void access$100(WebBrowserObject webBrowserObject, InitializationListener initializationListener) {
        webBrowserObject.removeInitializationListener(initializationListener);
    }

    static String access$200() {
        return LS;
    }

    static String access$300(WebBrowserObject webBrowserObject) {
        return webBrowserObject.backgroundColor;
    }

    static JWebBrowser access$400(WebBrowserObject webBrowserObject) {
        return webBrowserObject.webBrowser;
    }

    static String access$500(WebBrowserObject webBrowserObject) {
        return webBrowserObject.resourcePath;
    }

    private static interface InitializationListener
    extends EventListener {
        public void objectInitialized();
    }

    public static class ObjectHTMLConfiguration {
        private String windowsClassID;
        private String windowsInstallationURL;
        private String installationURL;
        private String version;
        private String windowsParamName;
        private String paramName;
        private Map htmlParameters;
        private String mimeType;

        public void setWindowsClassID(String string) {
            this.windowsClassID = string;
        }

        public String getWindowsClassID() {
            return this.windowsClassID;
        }

        public String getWindowsInstallationURL() {
            return this.windowsInstallationURL;
        }

        public void setWindowsInstallationURL(String string) {
            this.windowsInstallationURL = string;
        }

        public String getInstallationURL() {
            return this.installationURL;
        }

        public void setInstallationURL(String string) {
            this.installationURL = string;
        }

        public String getVersion() {
            return this.version;
        }

        public void setVersion(String string) {
            this.version = string;
        }

        public String getWindowsParamName() {
            return this.windowsParamName;
        }

        public void setWindowsParamName(String string) {
            this.windowsParamName = string;
        }

        public String getParamName() {
            return this.paramName;
        }

        public void setParamName(String string) {
            this.paramName = string;
        }

        public Map getHTMLParameters() {
            return this.htmlParameters;
        }

        public void setHTMLParameters(Map map) {
            this.htmlParameters = map;
        }

        public String getMimeType() {
            return this.mimeType;
        }

        public void setMimeType(String string) {
            this.mimeType = string;
        }
    }
}

