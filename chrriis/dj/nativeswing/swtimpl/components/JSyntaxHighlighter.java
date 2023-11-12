/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components;

import chrriis.dj.nativeswing.NSOption;
import chrriis.dj.nativeswing.common.Utils;
import chrriis.dj.nativeswing.common.WebServer;
import chrriis.dj.nativeswing.swtimpl.NSPanelComponent;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.SyntaxHighlighterOptions;
import java.awt.Component;

public class JSyntaxHighlighter
extends NSPanelComponent {
    private static final String PACKAGE_PREFIX = "/dp.SyntaxHighlighter/";
    private JWebBrowser webBrowser;
    private static final String LS = Utils.LINE_SEPARATOR;

    public JSyntaxHighlighter(NSOption ... nSOptionArray) {
        if (this.getClass().getResource("/dp.SyntaxHighlighter/Styles/SyntaxHighlighter.css") == null) {
            throw new IllegalStateException("The SyntaxHighlighter distribution is missing from the classpath!");
        }
        this.webBrowser = new JWebBrowser(nSOptionArray);
        this.initialize(this.webBrowser.getNativeComponent());
        this.webBrowser.setDefaultPopupMenuRegistered(false);
        this.webBrowser.setBarsVisible(false);
        this.add((Component)this.webBrowser, "Center");
    }

    public JWebBrowser getWebBrowser() {
        return this.webBrowser;
    }

    public void setContent(String string, ContentLanguage contentLanguage) {
        this.setContent(string, contentLanguage, null);
    }

    public void setContent(String string, ContentLanguage contentLanguage, SyntaxHighlighterOptions syntaxHighlighterOptions) {
        if (contentLanguage == null) {
            throw new IllegalArgumentException("The language cannot be null!");
        }
        if (syntaxHighlighterOptions == null) {
            syntaxHighlighterOptions = new SyntaxHighlighterOptions();
        }
        String string2 = "<html>" + LS + "  <head>" + LS + "    <link type=\"text/css\" rel=\"stylesheet\" href=\"" + WebServer.getDefaultWebServer().getClassPathResourceURL(null, "/dp.SyntaxHighlighter/Styles/SyntaxHighlighter.css") + "\"></link>" + LS + "    <script language=\"javascript\" src=\"" + WebServer.getDefaultWebServer().getClassPathResourceURL(null, "/dp.SyntaxHighlighter/Scripts/shCore.js") + "\"></script>" + LS + "    <script language=\"javascript\" src=\"" + WebServer.getDefaultWebServer().getClassPathResourceURL(null, "/dp.SyntaxHighlighter/Scripts/shBrush" + contentLanguage.getFileName() + ".js") + "\"></script>" + LS + "    <script language=\"JavaScript\" type=\"text/javascript\">" + LS + "      <!--" + LS + "      function init() {" + LS + "        dp.SyntaxHighlighter.ClipboardSwf = '" + WebServer.getDefaultWebServer().getClassPathResourceURL(null, "/dp.SyntaxHighlighter/Scripts/clipboard.swf") + "';" + LS + "        dp.SyntaxHighlighter.HighlightAll('code');" + LS + "      }" + LS + "      //-->" + LS + "    </script>" + LS + "    <style type=\"text/css\">" + LS + "      html, body { width: 100%; height: 100%; min-height: 100%; margin: 0; padding: 0; white-space: nowrap; background-color: #FFFFFF; }" + LS + "      div.dp-highlighter { overflow: visible; }" + LS + "      div.wrapper { width: 100%; height: 100%; min-height: 100%; padding: 0; margin: -18px 0; white-space: nowrap; }" + LS + "    </style>" + LS + "  </head>" + LS + "  <body id=\"body\">    <div class=\"wrapper\">      <pre name=\"code\" class=\"" + contentLanguage.getLanguage() + "\">" + JSyntaxHighlighter.escapeXML(string) + "</pre>" + LS + "    </div>  </body>" + LS + "<script language=\"JavaScript\" type=\"text/javascript\">" + LS + "  <!--" + LS + "    init();" + LS + "  //-->" + LS + "</script>" + LS + "</html>";
        this.webBrowser.setHTMLContent(string2);
    }

    private static String escapeXML(String string) {
        if (string == null || string.length() == 0) {
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder((int)((double)string.length() * 1.1));
        block6: for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            switch (c) {
                case '<': {
                    stringBuilder.append("&lt;");
                    continue block6;
                }
                case '>': {
                    stringBuilder.append("&gt;");
                    continue block6;
                }
                case '&': {
                    stringBuilder.append("&amp;");
                    continue block6;
                }
                case '\"': {
                    stringBuilder.append("&quot;");
                    continue block6;
                }
                default: {
                    stringBuilder.append(c);
                }
            }
        }
        return stringBuilder.toString();
    }

    public static enum ContentLanguage {
        Cpp("c++", "Cpp"),
        CSharp("c#", "CSharp"),
        CSS("css", "Css"),
        Delphi("delphi", "Delphi"),
        Java("java", "Java"),
        Javascript("js", "JScript"),
        PHP("php", "Php"),
        Python("python", "Python"),
        Ruby("ruby", "Ruby"),
        SQL("sql", "Sql"),
        VB("vb", "Vb"),
        XML("xml", "Xml"),
        HTML("html", "Xml");

        private String language;
        private String fileName;

        /*
         * WARNING - void declaration
         */
        private ContentLanguage() {
            void var4_2;
            void var3_1;
            this.language = var3_1;
            this.fileName = var4_2;
        }

        String getLanguage() {
            return this.language;
        }

        String getFileName() {
            return this.fileName;
        }
    }
}

