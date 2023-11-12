/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIURI;
import org.mozilla.interfaces.nsIWebBrowserChrome;
import org.mozilla.interfaces.nsIWindowCreator;

public interface nsIWindowCreator2
extends nsIWindowCreator {
    public static final String NS_IWINDOWCREATOR2_IID = "{f673ec81-a4b0-11d6-964b-eb5a2bf216fc}";
    public static final long PARENT_IS_LOADING_OR_RUNNING_TIMEOUT = 1L;

    public nsIWebBrowserChrome createChromeWindow2(nsIWebBrowserChrome var1, long var2, long var4, nsIURI var6, boolean[] var7);
}

