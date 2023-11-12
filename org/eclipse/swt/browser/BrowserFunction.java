/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.browser;

import java.util.Random;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;

public class BrowserFunction {
    Browser browser;
    String name;
    String functionString;
    int index;
    boolean isEvaluate;
    boolean top;
    String token;
    String[] frameNames;

    public BrowserFunction(Browser browser, String string) {
        this(browser, string, true, null, true);
    }

    public BrowserFunction(Browser browser, String string, boolean bl, String[] stringArray) {
        this(browser, string, bl, stringArray, true);
    }

    BrowserFunction(Browser browser, String string, boolean bl, String[] stringArray, boolean bl2) {
        if (browser == null) {
            SWT.error(4);
        }
        if (string == null) {
            SWT.error(4);
        }
        if (browser.isDisposed()) {
            SWT.error(24);
        }
        browser.checkWidget();
        this.browser = browser;
        this.name = string;
        this.top = bl;
        this.frameNames = stringArray;
        Random random = new Random();
        byte[] byArray = new byte[16];
        random.nextBytes(byArray);
        StringBuilder stringBuilder = new StringBuilder();
        for (byte by : byArray) {
            stringBuilder.append(Integer.toHexString(by & 0xFF));
        }
        this.token = stringBuilder.toString();
        if (bl2) {
            browser.webBrowser.createFunction(this);
        }
    }

    public void dispose() {
        this.dispose(true);
    }

    void dispose(boolean bl) {
        if (this.index < 0) {
            return;
        }
        if (bl) {
            this.browser.webBrowser.destroyFunction(this);
        }
        this.browser = null;
        Object var2_2 = null;
        this.functionString = var2_2;
        this.name = var2_2;
        this.index = -1;
    }

    public Object function(Object[] objectArray) {
        if (this.index < 0) {
            SWT.error(49);
        }
        this.browser.checkWidget();
        return null;
    }

    public Browser getBrowser() {
        if (this.index < 0) {
            SWT.error(49);
        }
        this.browser.checkWidget();
        return this.browser;
    }

    public String getName() {
        if (this.index < 0) {
            SWT.error(49);
        }
        this.browser.checkWidget();
        return this.name;
    }

    public boolean isDisposed() {
        return this.index < 0;
    }
}

