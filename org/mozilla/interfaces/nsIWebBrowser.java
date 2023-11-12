/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMWindow;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURIContentListener;
import org.mozilla.interfaces.nsIWebBrowserChrome;

public interface nsIWebBrowser
extends nsISupports {
    public static final String NS_IWEBBROWSER_IID = "{69e5df00-7b8b-11d3-af61-00a024ffc08c}";

    public void addWebBrowserListener(nsISupports var1, String var2);

    public void removeWebBrowserListener(nsISupports var1, String var2);

    public nsIWebBrowserChrome getContainerWindow();

    public void setContainerWindow(nsIWebBrowserChrome var1);

    public nsIURIContentListener getParentURIContentListener();

    public void setParentURIContentListener(nsIURIContentListener var1);

    public nsIDOMWindow getContentDOMWindow();
}

