/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMWindow;
import org.mozilla.interfaces.nsIObserver;
import org.mozilla.interfaces.nsIPrintProgressParams;
import org.mozilla.interfaces.nsIPrintSettings;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIWebBrowserPrint;
import org.mozilla.interfaces.nsIWebProgressListener;

public interface nsIPrintingPromptService
extends nsISupports {
    public static final String NS_IPRINTINGPROMPTSERVICE_IID = "{75d1553d-63bf-4b5d-a8f7-e4e4cac21ba4}";

    public void showPrintDialog(nsIDOMWindow var1, nsIWebBrowserPrint var2, nsIPrintSettings var3);

    public void showProgress(nsIDOMWindow var1, nsIWebBrowserPrint var2, nsIPrintSettings var3, nsIObserver var4, boolean var5, nsIWebProgressListener[] var6, nsIPrintProgressParams[] var7, boolean[] var8);

    public void showPageSetup(nsIDOMWindow var1, nsIPrintSettings var2, nsIObserver var3);

    public void showPrinterProperties(nsIDOMWindow var1, String var2, nsIPrintSettings var3);
}

