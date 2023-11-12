/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIAuthPrompt;
import org.mozilla.interfaces.nsIDOMWindow;
import org.mozilla.interfaces.nsIObserver;
import org.mozilla.interfaces.nsIPrompt;
import org.mozilla.interfaces.nsISimpleEnumerator;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIWebBrowserChrome;
import org.mozilla.interfaces.nsIWindowCreator;

public interface nsIWindowWatcher
extends nsISupports {
    public static final String NS_IWINDOWWATCHER_IID = "{002286a8-494b-43b3-8ddd-49e3fc50622b}";

    public nsIDOMWindow openWindow(nsIDOMWindow var1, String var2, String var3, String var4, nsISupports var5);

    public void registerNotification(nsIObserver var1);

    public void unregisterNotification(nsIObserver var1);

    public nsISimpleEnumerator getWindowEnumerator();

    public nsIPrompt getNewPrompter(nsIDOMWindow var1);

    public nsIAuthPrompt getNewAuthPrompter(nsIDOMWindow var1);

    public void setWindowCreator(nsIWindowCreator var1);

    public nsIWebBrowserChrome getChromeForWindow(nsIDOMWindow var1);

    public nsIDOMWindow getWindowByName(String var1, nsIDOMWindow var2);

    public nsIDOMWindow getActiveWindow();

    public void setActiveWindow(nsIDOMWindow var1);
}

