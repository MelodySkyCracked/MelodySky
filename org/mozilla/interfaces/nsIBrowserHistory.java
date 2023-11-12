/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIGlobalHistory2;
import org.mozilla.interfaces.nsIURI;

public interface nsIBrowserHistory
extends nsIGlobalHistory2 {
    public static final String NS_IBROWSERHISTORY_IID = "{c43079c3-3d8d-4b7c-af14-0e30ab46865f}";

    public void addPageWithDetails(nsIURI var1, String var2, long var3);

    public String getLastPageVisited();

    public long getCount();

    public void removePage(nsIURI var1);

    public void removePagesFromHost(String var1, boolean var2);

    public void removeAllPages();

    public void hidePage(nsIURI var1);

    public void markPageAsTyped(nsIURI var1);
}

