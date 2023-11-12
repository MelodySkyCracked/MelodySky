/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.browser;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.browser.AuthenticationListener;
import org.eclipse.swt.browser.BrowserFactory;
import org.eclipse.swt.browser.CloseWindowListener;
import org.eclipse.swt.browser.LocationListener;
import org.eclipse.swt.browser.OpenWindowListener;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.browser.StatusTextListener;
import org.eclipse.swt.browser.TitleListener;
import org.eclipse.swt.browser.VisibilityWindowListener;
import org.eclipse.swt.browser.WebBrowser;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class Browser
extends Composite {
    WebBrowser webBrowser;
    int userStyle;
    boolean isClosing;
    static int DefaultType = -1;
    static final String NO_INPUT_METHOD = "org.eclipse.swt.internal.gtk.noInputMethod";
    static final String PACKAGE_PREFIX = "org.eclipse.swt.browser.";
    static final String PROPERTY_DEFAULTTYPE = "org.eclipse.swt.browser.DefaultType";

    public Browser(Composite composite, int n) {
        super(Browser.checkParent(composite), Browser.checkStyle(n));
        String string;
        this.userStyle = n;
        String string2 = SWT.getPlatform();
        if ("gtk".equals(string2)) {
            composite.getDisplay().setData(NO_INPUT_METHOD, null);
        }
        n = this.getStyle();
        this.webBrowser = new BrowserFactory().createWebBrowser(n);
        if (this.webBrowser != null) {
            this.webBrowser.setBrowser(this);
            this.webBrowser.create(composite, n);
            return;
        }
        this.dispose();
        String string3 = " because there is no underlying browser available.\n";
        switch (string = SWT.getPlatform()) {
            case "gtk": {
                string3 = string3 + "Please ensure that WebKit with its GTK 3.x bindings is installed (WebKit2 API level is preferred). Additionally, please note that GTK4 does not currently have Browser support.\n";
                break;
            }
            case "cocoa": {
                string3 = string3 + "SWT failed to load the WebKit library.\n";
                break;
            }
            case "win32": {
                string3 = string3 + "SWT uses either IE or WebKit. Either the SWT.WEBKIT flag is passed and the WebKit library was not loaded properly by SWT, or SWT failed to load IE.\n";
            }
        }
        SWT.error(2, null, string3);
    }

    static Composite checkParent(Composite composite) {
        Display display;
        String string = SWT.getPlatform();
        if (!"gtk".equals(string)) {
            return composite;
        }
        if (composite != null && !composite.isDisposed() && (display = composite.getDisplay()) != null && display.getThread() == Thread.currentThread()) {
            display.setData(NO_INPUT_METHOD, "true");
        }
        return composite;
    }

    static int checkStyle(int n) {
        String string = SWT.getPlatform();
        if (DefaultType == -1) {
            try {
                Class.forName("org.eclipse.swt.browser.BrowserInitializer");
            }
            catch (ClassNotFoundException classNotFoundException) {
                // empty catch block
            }
            String string2 = System.getProperty(PROPERTY_DEFAULTTYPE);
            if (string2 != null) {
                int n2;
                int n3 = 0;
                int n4 = string2.length();
                do {
                    String string3;
                    if ((n2 = string2.indexOf(44, n3)) == -1) {
                        n2 = n4;
                    }
                    if ((string3 = string2.substring(n3, n2).trim()).equalsIgnoreCase("webkit")) {
                        DefaultType = 65536;
                        break;
                    }
                    if (string3.equalsIgnoreCase("edge") && "win32".equals(string)) {
                        DefaultType = 262144;
                        continue;
                    }
                    if (!string3.equalsIgnoreCase("ie") || !"win32".equals(string)) continue;
                    DefaultType = 0;
                    break;
                } while ((n3 = n2 + 1) < n4);
            }
            if (DefaultType == -1) {
                DefaultType = 0;
            }
        }
        if ((n & 0x50000) == 0) {
            n |= DefaultType;
        }
        if ("win32".equals(string) && (n & 0x40000) != 0) {
            n |= 0x1000000;
        }
        return n;
    }

    @Override
    protected void checkWidget() {
        super.checkWidget();
    }

    public static void clearSessions() {
        WebBrowser.clearSessions();
    }

    public static String getCookie(String string, String string2) {
        if (string == null) {
            SWT.error(4);
        }
        if (string2 == null) {
            SWT.error(4);
        }
        return WebBrowser.GetCookie(string, string2);
    }

    public static boolean setCookie(String string, String string2) {
        if (string == null) {
            SWT.error(4);
        }
        if (string2 == null) {
            SWT.error(4);
        }
        return WebBrowser.SetCookie(string, string2, true);
    }

    public void addAuthenticationListener(AuthenticationListener authenticationListener) {
        this.checkWidget();
        if (authenticationListener == null) {
            SWT.error(4);
        }
        this.webBrowser.addAuthenticationListener(authenticationListener);
    }

    public void addCloseWindowListener(CloseWindowListener closeWindowListener) {
        this.checkWidget();
        if (closeWindowListener == null) {
            SWT.error(4);
        }
        this.webBrowser.addCloseWindowListener(closeWindowListener);
    }

    public void addLocationListener(LocationListener locationListener) {
        this.checkWidget();
        if (locationListener == null) {
            SWT.error(4);
        }
        this.webBrowser.addLocationListener(locationListener);
    }

    public void addOpenWindowListener(OpenWindowListener openWindowListener) {
        this.checkWidget();
        if (openWindowListener == null) {
            SWT.error(4);
        }
        this.webBrowser.addOpenWindowListener(openWindowListener);
    }

    public void addProgressListener(ProgressListener progressListener) {
        this.checkWidget();
        if (progressListener == null) {
            SWT.error(4);
        }
        this.webBrowser.addProgressListener(progressListener);
    }

    public void addStatusTextListener(StatusTextListener statusTextListener) {
        this.checkWidget();
        if (statusTextListener == null) {
            SWT.error(4);
        }
        this.webBrowser.addStatusTextListener(statusTextListener);
    }

    public void addTitleListener(TitleListener titleListener) {
        this.checkWidget();
        if (titleListener == null) {
            SWT.error(4);
        }
        this.webBrowser.addTitleListener(titleListener);
    }

    public void addVisibilityWindowListener(VisibilityWindowListener visibilityWindowListener) {
        this.checkWidget();
        if (visibilityWindowListener == null) {
            SWT.error(4);
        }
        this.webBrowser.addVisibilityWindowListener(visibilityWindowListener);
    }

    public boolean back() {
        this.checkWidget();
        return this.webBrowser.back();
    }

    @Override
    protected void checkSubclass() {
        int n;
        String string = this.getClass().getName();
        if (!string.substring(0, (n = string.lastIndexOf(46)) + 1).equals(PACKAGE_PREFIX)) {
            SWT.error(43);
        }
    }

    public boolean execute(String string) {
        this.checkWidget();
        if (string == null) {
            SWT.error(4);
        }
        return this.webBrowser.execute(string);
    }

    public boolean close() {
        this.checkWidget();
        if (this.webBrowser.close()) {
            this.isClosing = true;
            this.dispose();
            this.isClosing = false;
            return true;
        }
        return false;
    }

    public Object evaluate(String string) throws SWTException {
        this.checkWidget();
        return this.evaluate(string, false);
    }

    public Object evaluate(String string, boolean bl) throws SWTException {
        this.checkWidget();
        if (string == null) {
            SWT.error(4);
        }
        return this.webBrowser.evaluate(string, bl);
    }

    public boolean forward() {
        this.checkWidget();
        return this.webBrowser.forward();
    }

    public String getBrowserType() {
        this.checkWidget();
        return this.webBrowser.getBrowserType();
    }

    public boolean getJavascriptEnabled() {
        this.checkWidget();
        return this.webBrowser.jsEnabledOnNextPage;
    }

    @Override
    public int getStyle() {
        return super.getStyle() | this.userStyle & 0x800;
    }

    public String getText() {
        this.checkWidget();
        return this.webBrowser.getText();
    }

    public String getUrl() {
        this.checkWidget();
        return this.webBrowser.getUrl();
    }

    @Deprecated
    public Object getWebBrowser() {
        this.checkWidget();
        return this.webBrowser.getWebBrowser();
    }

    public boolean isBackEnabled() {
        this.checkWidget();
        return this.webBrowser.isBackEnabled();
    }

    public boolean isFocusControl() {
        this.checkWidget();
        return this.webBrowser.isFocusControl() || super.isFocusControl();
    }

    public boolean isForwardEnabled() {
        this.checkWidget();
        return this.webBrowser.isForwardEnabled();
    }

    public void refresh() {
        this.checkWidget();
        this.webBrowser.refresh();
    }

    public void removeAuthenticationListener(AuthenticationListener authenticationListener) {
        this.checkWidget();
        if (authenticationListener == null) {
            SWT.error(4);
        }
        this.webBrowser.removeAuthenticationListener(authenticationListener);
    }

    public void removeCloseWindowListener(CloseWindowListener closeWindowListener) {
        this.checkWidget();
        if (closeWindowListener == null) {
            SWT.error(4);
        }
        this.webBrowser.removeCloseWindowListener(closeWindowListener);
    }

    public void removeLocationListener(LocationListener locationListener) {
        this.checkWidget();
        if (locationListener == null) {
            SWT.error(4);
        }
        this.webBrowser.removeLocationListener(locationListener);
    }

    public void removeOpenWindowListener(OpenWindowListener openWindowListener) {
        this.checkWidget();
        if (openWindowListener == null) {
            SWT.error(4);
        }
        this.webBrowser.removeOpenWindowListener(openWindowListener);
    }

    public void removeProgressListener(ProgressListener progressListener) {
        this.checkWidget();
        if (progressListener == null) {
            SWT.error(4);
        }
        this.webBrowser.removeProgressListener(progressListener);
    }

    public void removeStatusTextListener(StatusTextListener statusTextListener) {
        this.checkWidget();
        if (statusTextListener == null) {
            SWT.error(4);
        }
        this.webBrowser.removeStatusTextListener(statusTextListener);
    }

    public void removeTitleListener(TitleListener titleListener) {
        this.checkWidget();
        if (titleListener == null) {
            SWT.error(4);
        }
        this.webBrowser.removeTitleListener(titleListener);
    }

    public void removeVisibilityWindowListener(VisibilityWindowListener visibilityWindowListener) {
        this.checkWidget();
        if (visibilityWindowListener == null) {
            SWT.error(4);
        }
        this.webBrowser.removeVisibilityWindowListener(visibilityWindowListener);
    }

    public void setJavascriptEnabled(boolean bl) {
        this.checkWidget();
        this.webBrowser.jsEnabledOnNextPage = bl;
    }

    public boolean setText(String string) {
        this.checkWidget();
        return this.setText(string, true);
    }

    public boolean setText(String string, boolean bl) {
        this.checkWidget();
        if (string == null) {
            SWT.error(4);
        }
        return this.webBrowser.setText(string, bl);
    }

    public boolean setUrl(String string) {
        this.checkWidget();
        return this.setUrl(string, null, null);
    }

    public boolean setUrl(String string, String string2, String[] stringArray) {
        this.checkWidget();
        if (string == null) {
            SWT.error(4);
        }
        return this.webBrowser.setUrl(string, string2, stringArray);
    }

    public void stop() {
        this.checkWidget();
        this.webBrowser.stop();
    }
}

