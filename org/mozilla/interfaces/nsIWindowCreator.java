/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIWebBrowserChrome;

public interface nsIWindowCreator
extends nsISupports {
    public static final String NS_IWINDOWCREATOR_IID = "{30465632-a777-44cc-90f9-8145475ef999}";

    public nsIWebBrowserChrome createChromeWindow(nsIWebBrowserChrome var1, long var2);
}

