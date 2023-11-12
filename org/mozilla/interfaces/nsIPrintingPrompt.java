/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIObserver;
import org.mozilla.interfaces.nsIPrintProgressParams;
import org.mozilla.interfaces.nsIPrintSettings;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIWebBrowserPrint;
import org.mozilla.interfaces.nsIWebProgressListener;

public interface nsIPrintingPrompt
extends nsISupports {
    public static final String NS_IPRINTINGPROMPT_IID = "{44e314ca-75b1-4f3d-9553-9b3507912108}";

    public void showPrintDialog(nsIWebBrowserPrint var1, nsIPrintSettings var2);

    public void showProgress(nsIWebBrowserPrint var1, nsIPrintSettings var2, nsIObserver var3, boolean var4, nsIWebProgressListener[] var5, nsIPrintProgressParams[] var6, boolean[] var7);

    public void showPageSetup(nsIPrintSettings var1, nsIObserver var2);

    public void showPrinterProperties(String var1, nsIPrintSettings var2);
}

