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
import chrriis.dj.nativeswing.swtimpl.components.I;
import chrriis.dj.nativeswing.swtimpl.components.JHTMLEditor;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.lIIII;
import chrriis.dj.nativeswing.swtimpl.components.lIIIII;
import chrriis.dj.nativeswing.swtimpl.components.lIIlIII;
import chrriis.dj.nativeswing.swtimpl.components.lIIlIIl;
import chrriis.dj.nativeswing.swtimpl.components.lIIlIll;
import chrriis.dj.nativeswing.swtimpl.components.lIlII;
import chrriis.dj.nativeswing.swtimpl.components.llIIIl;
import chrriis.dj.nativeswing.swtimpl.components.llIIll;
import chrriis.dj.nativeswing.swtimpl.components.llIlI;
import chrriis.dj.nativeswing.swtimpl.components.lllll;
import java.io.File;
import java.util.Map;
import javax.swing.SwingUtilities;

class JHTMLEditorFCKeditor
implements JHTMLEditor.JHTMLEditorImplementation {
    private static final String PACKAGE_PREFIX = "/fckeditor/";
    private static final String EDITOR_INSTANCE = "HTMLeditor1";
    private final JHTMLEditor htmlEditor;
    private final String customJavascriptConfiguration;
    private static final String LS = Utils.LINE_SEPARATOR;
    private volatile Object tempResult;

    public JHTMLEditorFCKeditor(JHTMLEditor jHTMLEditor, Map map) {
        if (this.getClass().getResource("/fckeditor/fckeditor.js") == null) {
            throw new IllegalStateException("The FCKEditor distribution is missing from the classpath!");
        }
        this.htmlEditor = jHTMLEditor;
        this.customJavascriptConfiguration = (String)map.get("FCKEditor Custom Configuration Script");
        jHTMLEditor.getWebBrowser().addWebBrowserListener(new llIlI(this));
    }

    @Override
    public WebServer.WebServerContent getWebServerContent(WebServer.HTTPRequest hTTPRequest, String string, int n) {
        if ("index.html".equals(string)) {
            return new llIIIl(this, string, n);
        }
        if ("customConfigurationScript.js".equals(string)) {
            return new lIIIII(this);
        }
        if ("jhtml_save".equals(string)) {
            SwingUtilities.invokeLater(new lIIlIII(this, hTTPRequest));
            return new I(this);
        }
        if ("jhtml_sendData".equals(string)) {
            String string2 = (String)hTTPRequest.getHTTPPostDataArray()[0].getHeaderMap().get(EDITOR_INSTANCE);
            this.tempResult = string2;
            return new lIIlIIl(this);
        }
        if ("editor/filemanager/connectors/php/upload.php".equals(string)) {
            return new llIIll(this);
        }
        if ("editor/filemanager/connectors/php/connector.php".equals(string)) {
            String string3;
            Map map = hTTPRequest.getQueryParameterMap();
            String string4 = (String)map.get("Command");
            String string5 = null;
            if ("GetFoldersAndFiles".equals(string4) || "GetFolders".equals(string4)) {
                string3 = (String)map.get("Type");
                String string6 = (String)map.get("CurrentFolder");
                File[] fileArray = File.listRoots();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?>");
                stringBuilder.append("<Connector command=\"").append(string4).append("\" resourceType=\"").append(string3).append("\">");
                stringBuilder.append("<CurrentFolder path=\"").append(string6).append("\" url=\"").append(WebServer.getDefaultWebServer().getResourcePathURL(string6, "")).append("\" />");
                if (("GetFoldersAndFiles".equals(string4) || "GetFolders".equals(string4)) && string6.equals("/") && fileArray.length > 1) {
                    stringBuilder.append("<Folders>");
                    for (File file : fileArray) {
                        String string7 = file.getAbsolutePath();
                        if (Utils.IS_WINDOWS && string7.endsWith("\\")) {
                            string7 = string7.substring(0, string7.length() - 1);
                        }
                        stringBuilder.append("<Folder name=\"").append(Utils.escapeXML(string7)).append("\"/>");
                    }
                    stringBuilder.append("</Folders>");
                } else {
                    if ("GetFoldersAndFiles".equals(string4) || "GetFolders".equals(string4)) {
                        stringBuilder.append("<Folders>");
                        for (File file : new File(string6).listFiles(new lIIlIll(this))) {
                            stringBuilder.append("<Folder name=\"").append(Utils.escapeXML(file.getName())).append("\"/>");
                        }
                        stringBuilder.append("</Folders>");
                    }
                    if ("GetFoldersAndFiles".equals(string4)) {
                        stringBuilder.append("<Files>");
                        for (File file : new File(string6).listFiles(new lllll(this, string3))) {
                            stringBuilder.append("<File name=\"").append(Utils.escapeXML(file.getName())).append("\" size=\"").append(file.length() / 1000L).append("\"/>");
                        }
                        stringBuilder.append("</Files>");
                    }
                }
                stringBuilder.append("</Connector>");
                string5 = stringBuilder.toString();
            }
            string3 = string5;
            return new lIlII(this, string3);
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
            EventDispatchUtils.sleepWithEventDispatch(new lIIII(this), 50);
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

    static String access$100(JHTMLEditorFCKeditor jHTMLEditorFCKeditor) {
        return jHTMLEditorFCKeditor.customJavascriptConfiguration;
    }

    static JHTMLEditor access$200(JHTMLEditorFCKeditor jHTMLEditorFCKeditor) {
        return jHTMLEditorFCKeditor.htmlEditor;
    }

    static Object access$300(JHTMLEditorFCKeditor jHTMLEditorFCKeditor) {
        return jHTMLEditorFCKeditor.tempResult;
    }
}

