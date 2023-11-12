/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;

public interface nsISHistoryListener
extends nsISupports {
    public static final String NS_ISHISTORYLISTENER_IID = "{3b07f591-e8e1-11d4-9882-00c04fa02f40}";

    public void onHistoryNewEntry(nsIURI var1);

    public boolean onHistoryGoBack(nsIURI var1);

    public boolean onHistoryGoForward(nsIURI var1);

    public boolean onHistoryReload(nsIURI var1, long var2);

    public boolean onHistoryGotoIndex(int var1, nsIURI var2);

    public boolean onHistoryPurge(int var1);
}

