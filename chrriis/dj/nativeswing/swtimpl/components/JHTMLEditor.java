/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components;

import chrriis.dj.nativeswing.NSOption;
import chrriis.dj.nativeswing.NSSystemProperty;
import chrriis.dj.nativeswing.common.ObjectRegistry;
import chrriis.dj.nativeswing.common.Utils;
import chrriis.dj.nativeswing.common.WebServer;
import chrriis.dj.nativeswing.swtimpl.NSPanelComponent;
import chrriis.dj.nativeswing.swtimpl.components.HTMLEditorDirtyStateEvent;
import chrriis.dj.nativeswing.swtimpl.components.HTMLEditorListener;
import chrriis.dj.nativeswing.swtimpl.components.JHTMLEditorCKeditor;
import chrriis.dj.nativeswing.swtimpl.components.JHTMLEditorFCKeditor;
import chrriis.dj.nativeswing.swtimpl.components.JHTMLEditorTinyMCE;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.lIIIlI;
import chrriis.dj.nativeswing.swtimpl.components.lIlIIlI;
import chrriis.dj.nativeswing.swtimpl.components.llI;
import chrriis.dj.nativeswing.swtimpl.components.llIlIl;
import chrriis.dj.nativeswing.swtimpl.components.llIll;
import chrriis.dj.nativeswing.swtimpl.components.lll;
import chrriis.dj.nativeswing.swtimpl.components.llll;
import chrriis.dj.nativeswing.swtimpl.components.llllIl;
import java.awt.Component;
import java.io.File;
import java.net.MalformedURLException;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.event.EventListenerList;

public class JHTMLEditor
extends NSPanelComponent {
    private JWebBrowser webBrowser;
    private int instanceID;
    private JHTMLEditorImplementation implementation;
    private boolean isDirty;

    JHTMLEditorImplementation getImplementation() {
        return this.implementation;
    }

    /*
     * Unable to fully structure code
     */
    public JHTMLEditor(HTMLEditorImplementation var1_1, NSOption ... var2_2) {
        super();
        if (var1_1 == null) {
            throw new NullPointerException("The editor implementation cannot be null!");
        }
        var3_3 = NSOption.createOptionMap(var2_2);
        this.webBrowser = new JWebBrowser(var2_2);
        this.initialize(this.webBrowser.getNativeComponent());
        switch (llll.$SwitchMap$chrriis$dj$nativeswing$swtimpl$components$JHTMLEditor$HTMLEditorImplementation[var1_1.ordinal()]) {
            case 1: {
                try {
                    this.implementation = new JHTMLEditorFCKeditor(this, var3_3);
                    break;
                }
                catch (RuntimeException var4_4) {
                    if (var1_1 == null) ** GOTO lbl15
                    throw var4_4;
                }
            }
lbl15:
            // 2 sources

            case 2: {
                try {
                    this.implementation = new JHTMLEditorCKeditor(this, var3_3);
                    break;
                }
                catch (RuntimeException var4_5) {
                    if (var1_1 == null) ** GOTO lbl22
                    throw var4_5;
                }
            }
lbl22:
            // 2 sources

            case 3: {
                try {
                    this.implementation = new JHTMLEditorTinyMCE(this, var3_3);
                    break;
                }
                catch (RuntimeException var4_6) {
                    if (var1_1 == null) ** GOTO lbl29
                    throw var4_6;
                }
            }
lbl29:
            // 2 sources

            default: {
                throw new IllegalStateException("A suitable HTML editor (FCKeditor, CKeditor, TinyMCE) distribution could not be found on the classpath!");
            }
        }
        this.webBrowser.addWebBrowserListener(new llIll(this));
        this.webBrowser.setBarsVisible(false);
        this.add((Component)this.webBrowser, "Center");
        this.instanceID = ObjectRegistry.getInstance().add(this);
        var4_7 = new AtomicBoolean();
        var5_8 = new llllIl(this, var4_7);
        this.addInitializationListener(var5_8);
        this.webBrowser.navigate(WebServer.getDefaultWebServer().getDynamicContentURL(JHTMLEditor.class.getName(), String.valueOf(this.instanceID), "index.html"));
        this.webBrowser.getNativeComponent().runSync(new llIlIl(this), new Object[]{var5_8, var4_7});
    }

    public JWebBrowser getWebBrowser() {
        return this.webBrowser;
    }

    protected static WebServer.WebServerContent getWebServerContent(WebServer.HTTPRequest hTTPRequest) {
        String string = hTTPRequest.getResourcePath();
        int n = string.indexOf(47);
        int n2 = Integer.parseInt(string.substring(0, n));
        JHTMLEditor jHTMLEditor = (JHTMLEditor)ObjectRegistry.getInstance().get(n2);
        if (jHTMLEditor == null) {
            return null;
        }
        String string2 = string.substring(n + 1);
        if (string2.startsWith("/")) {
            string2 = string2.substring(1);
        }
        return jHTMLEditor.getWebServerContent(hTTPRequest, string2, n2);
    }

    protected WebServer.WebServerContent getWebServerContent(WebServer.HTTPRequest hTTPRequest, String string, int n) {
        return this.implementation.getWebServerContent(hTTPRequest, string, n);
    }

    public String getHTMLContent() {
        return JHTMLEditor.convertLinksToLocal(this.implementation.getHTMLContent());
    }

    public void setHTMLContent(String string) {
        string = JHTMLEditor.convertLinksFromLocal(string.replaceAll("[\r\n]", " "));
        this.implementation.setHTMLContent(string);
        this.setDirty(false);
    }

    public boolean isDirty() {
        return this.isDirty;
    }

    private void setDirty(boolean bl) {
        if (this.isDirty == bl) {
            return;
        }
        this.isDirty = bl;
        Object[] objectArray = this.listenerList.getListenerList();
        HTMLEditorDirtyStateEvent hTMLEditorDirtyStateEvent = null;
        for (int i = objectArray.length - 2; i >= 0; i -= 2) {
            if (objectArray[i] != HTMLEditorListener.class) continue;
            if (hTMLEditorDirtyStateEvent == null) {
                hTMLEditorDirtyStateEvent = new HTMLEditorDirtyStateEvent(this, bl);
            }
            ((HTMLEditorListener)objectArray[i + 1]).notifyDirtyStateChanged(hTMLEditorDirtyStateEvent);
        }
    }

    public void clearDirtyState() {
        this.implementation.clearDirtyIndicator();
        this.setDirty(false);
    }

    static String convertLinksToLocal(String string) {
        String string2;
        String string3;
        Matcher matcher;
        if (string == null) {
            return string;
        }
        Pattern pattern = Pattern.compile("=\\s*\"(" + WebServer.getDefaultWebServer().getURLPrefix() + "/resource/)([^/]+)/([^\"]+)\"\\s");
        while ((matcher = pattern.matcher(string)).find()) {
            string3 = string.substring(matcher.start(2), matcher.end(2));
            string2 = string.substring(matcher.start(3), matcher.end(3));
            try {
                string2 = new File(Utils.decodeURL(Utils.decodeURL(string3)), string2).toURI().toURL().toExternalForm();
            }
            catch (MalformedURLException malformedURLException) {
                // empty catch block
            }
            string = string.substring(0, matcher.start(1)) + string2 + string.substring(matcher.end(3));
        }
        pattern = Pattern.compile("=\\s*\"(" + WebServer.getDefaultWebServer().getURLPrefix() + "/location/)([^/]+)/([^\"]+)\"\\s");
        while ((matcher = pattern.matcher(string)).find()) {
            string3 = string.substring(matcher.start(2), matcher.end(2));
            string2 = string.substring(matcher.start(3), matcher.end(3));
            try {
                string2 = new File(Utils.decodeBase64(string3), Utils.decodeURL(string2)).toURI().toURL().toExternalForm();
            }
            catch (MalformedURLException malformedURLException) {
                // empty catch block
            }
            string = string.substring(0, matcher.start(1)) + string2 + string.substring(matcher.end(3));
        }
        return string;
    }

    static String convertLinksFromLocal(String string) {
        Matcher matcher;
        if (string == null) {
            return string;
        }
        Pattern pattern = Pattern.compile("=\\s*\"(file:/{1,3})([^\"]+)\"\\s");
        while ((matcher = pattern.matcher(string)).find()) {
            File file;
            String string2 = string.substring(matcher.start(2), matcher.end(2));
            if (Boolean.parseBoolean(NSSystemProperty.WEBSERVER_ACTIVATEOLDRESOURCEMETHOD.get())) {
                file = new File(string2);
                string2 = WebServer.getDefaultWebServer().getResourcePathURL(Utils.encodeURL(file.getParent()), file.getName());
            } else {
                file = new File(Utils.decodeURL(string2));
                string2 = WebServer.getDefaultWebServer().getResourcePathURL(file.getParent(), file.getName());
            }
            string = string.substring(0, matcher.start(1)) + string2 + string.substring(matcher.end(2));
        }
        return string;
    }

    public void addHTMLEditorListener(HTMLEditorListener hTMLEditorListener) {
        this.listenerList.add(HTMLEditorListener.class, hTMLEditorListener);
    }

    public void removeHTMLEditorListener(HTMLEditorListener hTMLEditorListener) {
        this.listenerList.remove(HTMLEditorListener.class, hTMLEditorListener);
    }

    public HTMLEditorListener[] getHTMLEditorListeners() {
        return (HTMLEditorListener[])this.listenerList.getListeners(HTMLEditorListener.class);
    }

    private void addInitializationListener(InitializationListener initializationListener) {
        this.listenerList.add(InitializationListener.class, initializationListener);
    }

    private void removeInitializationListener(InitializationListener initializationListener) {
        this.listenerList.remove(InitializationListener.class, initializationListener);
    }

    static EventListenerList access$000(JHTMLEditor jHTMLEditor) {
        return jHTMLEditor.listenerList;
    }

    static void access$100(JHTMLEditor jHTMLEditor, boolean bl) {
        jHTMLEditor.setDirty(bl);
    }

    static void access$200(JHTMLEditor jHTMLEditor, InitializationListener initializationListener) {
        jHTMLEditor.removeInitializationListener(initializationListener);
    }

    private static interface InitializationListener
    extends EventListener {
        public void objectInitialized();
    }

    public static class CKEditorOptions {
        static final String SET_OPTIONS_OPTION_KEY = "CKEditor Options";

        private CKEditorOptions() {
        }

        public static NSOption setOptions(Map map) {
            HashMap hashMap = new HashMap(map);
            return new lll(SET_OPTIONS_OPTION_KEY, hashMap);
        }
    }

    public static class FCKEditorOptions {
        static final String SET_CUSTOM_JAVASCRIPT_CONFIGURATION_OPTION_KEY = "FCKEditor Custom Configuration Script";

        private FCKEditorOptions() {
        }

        public static NSOption setCustomJavascriptConfiguration(String string) {
            return new llI(SET_CUSTOM_JAVASCRIPT_CONFIGURATION_OPTION_KEY, string);
        }
    }

    public static class TinyMCEOptions {
        static final String SET_CUSTOM_HTML_HEADERS_OPTION_KEY = "TinyMCE Custom HTML Headers";
        static final String SET_OPTIONS_OPTION_KEY = "TinyMCE Options";

        private TinyMCEOptions() {
        }

        public static NSOption setCustomHTMLHeaders(String string) {
            return new lIlIIlI(SET_CUSTOM_HTML_HEADERS_OPTION_KEY, string);
        }

        public static NSOption setOptions(Map map) {
            HashMap hashMap = new HashMap(map);
            return new lIIIlI(SET_OPTIONS_OPTION_KEY, hashMap);
        }
    }

    public static enum HTMLEditorImplementation {
        FCKEditor,
        CKEditor,
        TinyMCE;

    }

    static interface JHTMLEditorImplementation {
        public WebServer.WebServerContent getWebServerContent(WebServer.HTTPRequest var1, String var2, int var3);

        public String getHTMLContent();

        public void setHTMLContent(String var1);

        public void setDirtyTrackingActive(boolean var1);

        public void clearDirtyIndicator();
    }
}

