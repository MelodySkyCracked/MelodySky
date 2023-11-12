/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components;

import chrriis.dj.nativeswing.common.Utils;
import chrriis.dj.nativeswing.common.WebServer;
import chrriis.dj.nativeswing.swtimpl.EventDispatchUtils;
import chrriis.dj.nativeswing.swtimpl.Message;
import chrriis.dj.nativeswing.swtimpl.NSSystemPropertySWT;
import chrriis.dj.nativeswing.swtimpl.NativeComponent;
import chrriis.dj.nativeswing.swtimpl.components.JHTMLEditor;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.lI;
import chrriis.dj.nativeswing.swtimpl.components.lIIIIlI;
import chrriis.dj.nativeswing.swtimpl.components.lIIIlII;
import chrriis.dj.nativeswing.swtimpl.components.llIllI;
import chrriis.dj.nativeswing.swtimpl.components.llIlll;
import chrriis.dj.nativeswing.swtimpl.components.lllIII;
import java.util.Map;
import javax.swing.SwingUtilities;

class JHTMLEditorTinyMCE
implements JHTMLEditor.JHTMLEditorImplementation {
    private static final String PACKAGE_PREFIX = "/tiny_mce/";
    private static final String EDITOR_INSTANCE = "HTMLeditor1";
    private final JHTMLEditor htmlEditor;
    private final String customOptions;
    private final String customHTMLHeaders;
    private static final String LS = Utils.LINE_SEPARATOR;
    private volatile Object tempResult;

    public JHTMLEditorTinyMCE(JHTMLEditor jHTMLEditor, Map map) {
        if (this.getClass().getResource("/tiny_mce/tiny_mce.js") == null) {
            throw new IllegalStateException("The TinyMCE distribution is missing from the classpath!");
        }
        this.htmlEditor = jHTMLEditor;
        Map map2 = (Map)map.get("TinyMCE Options");
        if (map2 != null) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String string : map2.keySet()) {
                String string2 = (String)map2.get(string);
                if (string2 == null || string2.length() <= 0) continue;
                if (stringBuilder.length() > 0) {
                    stringBuilder.append(',' + LS);
                }
                stringBuilder.append("        " + string + ": " + string2);
            }
            this.customOptions = stringBuilder.length() > 0 ? stringBuilder.toString() : null;
        } else {
            this.customOptions = null;
        }
        this.customHTMLHeaders = (String)map.get("TinyMCE Custom HTML Headers");
        jHTMLEditor.getWebBrowser().addWebBrowserListener(new llIlll(this));
    }

    @Override
    public WebServer.WebServerContent getWebServerContent(WebServer.HTTPRequest hTTPRequest, String string, int n) {
        if ("index.html".equals(string)) {
            return new llIllI(this, string);
        }
        if ("jhtml_save".equals(string)) {
            SwingUtilities.invokeLater(new lIIIIlI(this, hTTPRequest));
            return new lI(this);
        }
        if ("jhtml_sendData".equals(string)) {
            String string2 = (String)hTTPRequest.getHTTPPostDataArray()[0].getHeaderMap().get(EDITOR_INSTANCE);
            this.tempResult = string2;
            return new lIIIlII(this);
        }
        return WebServer.getDefaultWebServer().getURLContent(WebServer.getDefaultWebServer().getClassPathResourceURL(JHTMLEditor.class.getName(), PACKAGE_PREFIX + string));
    }

    @Override
    public void clearDirtyIndicator() {
        this.htmlEditor.getWebBrowser().executeJavascript("JH_clearDirtyIndicator();");
    }

    @Override
    public void setDirtyTrackingActive(boolean bl) {
        this.htmlEditor.getWebBrowser().executeJavascript("JH_setDirtyTrackingActive(" + bl + ");");
    }

    @Override
    public String getHTMLContent() {
        JWebBrowser jWebBrowser = this.htmlEditor.getWebBrowser();
        if (!jWebBrowser.isNativePeerInitialized()) {
            return "";
        }
        this.tempResult = this;
        jWebBrowser.executeJavascript("JH_sendData();");
        int n = Integer.parseInt(NSSystemPropertySWT.HTMLEDITOR_GETHTMLCONTENT_TIMEOUT.get("1500"));
        long l2 = System.currentTimeMillis();
        do {
            EventDispatchUtils.sleepWithEventDispatch(new lllIII(this), 50);
            if (this.tempResult == this) continue;
            return (String)this.tempResult;
        } while (System.currentTimeMillis() - l2 <= (long)n);
        return null;
    }

    @Override
    public void setHTMLContent(String string) {
        JWebBrowser jWebBrowser = this.htmlEditor.getWebBrowser();
        NativeComponent nativeComponent = jWebBrowser.getNativeComponent();
        boolean bl = nativeComponent.isEnabled();
        nativeComponent.setEnabled(false);
        new Message().syncSend(true);
        jWebBrowser.executeJavascript("JH_setData('" + Utils.encodeURL(string) + "');");
        new Message().syncSend(true);
        nativeComponent.setEnabled(bl);
    }

    static String access$000() {
        return LS;
    }

    static String access$100(JHTMLEditorTinyMCE jHTMLEditorTinyMCE) {
        return jHTMLEditorTinyMCE.customHTMLHeaders;
    }

    static String access$200(JHTMLEditorTinyMCE jHTMLEditorTinyMCE) {
        return jHTMLEditorTinyMCE.customOptions;
    }

    static JHTMLEditor access$300(JHTMLEditorTinyMCE jHTMLEditorTinyMCE) {
        return jHTMLEditorTinyMCE.htmlEditor;
    }

    static Object access$400(JHTMLEditorTinyMCE jHTMLEditorTinyMCE) {
        return jHTMLEditorTinyMCE.tempResult;
    }
}

