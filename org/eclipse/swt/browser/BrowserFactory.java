/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.browser;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.browser.Edge;
import org.eclipse.swt.browser.IE;
import org.eclipse.swt.browser.WebBrowser;

class BrowserFactory {
    private Class chromiumClass;

    BrowserFactory() {
    }

    WebBrowser createWebBrowser(int n) {
        WebBrowser webBrowser = null;
        if ((n & 0x20000) != 0 && (webBrowser = this.createChromium()) != null) {
            return webBrowser;
        }
        if ((n & 0x40000) != 0) {
            try {
                return new Edge();
            }
            catch (SWTError sWTError) {
                System.err.println(sWTError);
            }
        }
        return new IE();
    }

    private WebBrowser createChromium() {
        if (this.chromiumClass == null) {
            try {
                this.chromiumClass = Class.forName("org.eclipse.swt.browser.ChromiumImpl");
                return (WebBrowser)this.chromiumClass.newInstance();
            }
            catch (ClassNotFoundException classNotFoundException) {
                System.err.println("SWT.CHROMIUM style was used but chromium.swt fragment/jar is missing from classpath.");
            }
            catch (IllegalAccessException | InstantiationException | NoClassDefFoundError throwable) {
            }
            catch (UnsatisfiedLinkError unsatisfiedLinkError) {
                System.err.println("SWT.CHROMIUM style was used but chromium.swt " + SWT.getPlatform() + " (or CEF binaries) fragment/jar is missing.");
            }
            catch (SWTError sWTError) {
                System.err.println(sWTError.getMessage());
            }
        }
        return null;
    }
}

