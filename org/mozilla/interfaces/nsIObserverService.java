/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIObserver;
import org.mozilla.interfaces.nsISimpleEnumerator;
import org.mozilla.interfaces.nsISupports;

public interface nsIObserverService
extends nsISupports {
    public static final String NS_IOBSERVERSERVICE_IID = "{d07f5192-e3d1-11d2-8acd-00105a1b8860}";

    public void addObserver(nsIObserver var1, String var2, boolean var3);

    public void removeObserver(nsIObserver var1, String var2);

    public void notifyObservers(nsISupports var1, String var2, String var3);

    public nsISimpleEnumerator enumerateObservers(String var1);
}

