/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.components.core;

import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Shell;

class NativeJSBrowserDetection {
    public final String browserName;
    public final String browserVersion;

    public NativeJSBrowserDetection(Browser browser) {
        Shell shell = new Shell(browser.getDisplay());
        shell.setLayout(new FillLayout());
        Browser browser2 = new Browser(shell, browser.getStyle());
        browser2.setText("<html></html>");
        String string = null;
        String string2 = null;
        String string3 = (String)browser2.evaluate("return navigator.userAgent");
        String string4 = (String)browser2.evaluate("return navigator.vendor");
        if (string == null && string3 != null && string3.indexOf("Chrome") != -1) {
            string = "Chrome";
            string2 = "Chrome";
        }
        if (string == null && string3 != null && string3.indexOf("OmniWeb") != -1) {
            string = "OmniWeb";
            string2 = "OmniWeb/";
        }
        if (string == null && string4 != null && string4.indexOf("Apple") != -1) {
            string = "Safari";
            string2 = "AppleWebKit";
        }
        if (string == null && (String)browser2.evaluate("return window.opera") != null) {
            string = "Opera";
            string2 = "Opera";
        }
        if (string == null && string4 != null && string4.indexOf("iCab") != -1) {
            string = "iCab";
            string2 = "iCab";
        }
        if (string == null && string4 != null && string4.indexOf("KDE") != -1) {
            string = "Konqueror";
            string2 = "Konqueror";
        }
        if (string == null && string3 != null && string3.indexOf("Firefox") != -1) {
            string = "Firefox";
            string2 = "Firefox";
        }
        if (string == null && string4 != null && string4.indexOf("Camino") != -1) {
            string = "Camino";
            string2 = "Camino";
        }
        if (string == null && string3 != null && string3.indexOf("Netscape") != -1) {
            string = "Netscape";
            string2 = "Netscape";
        }
        if (string == null && string3 != null && string3.indexOf("MSIE") != -1) {
            string = "IE";
            string2 = "MSIE";
        }
        if (string == null && string3 != null && string3.indexOf("Gecko") != -1) {
            string = "Mozilla";
            string2 = "rv";
        }
        if (string == null && string3 != null && string3.indexOf("Mozilla") != -1) {
            string = "Netscape";
            string2 = "Mozilla";
        }
        String string5 = null;
        if (string != null) {
            int n;
            String string6;
            int n2;
            if (string3 != null && (n2 = string3.indexOf(string2)) >= 0) {
                string5 = string3.substring(n2 + string2.length() + 1);
            }
            if (string5 == null && (string6 = (String)browser2.evaluate("return navigator.appVersion")) != null && (n = string6.indexOf(string2)) >= 0) {
                string5 = string6.substring(n + string2.length() + 1);
            }
            if (string5 != null) {
                n2 = -1;
                for (n = 0; n < string5.length(); ++n) {
                    char c = string5.charAt(n);
                    if (Character.isDigit(c) || c == '.' || c == '_' || c == '-' || c >= 'a' && c <= 'z' || c >= 'A' || c >= 'Z') continue;
                    n2 = n;
                    break;
                }
                if (n2 > 0) {
                    string5 = string5.substring(0, n2);
                }
            }
        }
        this.browserName = string;
        this.browserVersion = string5;
    }
}

