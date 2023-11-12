/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDocShell;
import org.mozilla.interfaces.nsISHEntry;
import org.mozilla.interfaces.nsISHTransaction;
import org.mozilla.interfaces.nsISHistoryListener;
import org.mozilla.interfaces.nsISupports;

public interface nsISHistoryInternal
extends nsISupports {
    public static final String NS_ISHISTORYINTERNAL_IID = "{494fac3c-64f4-41b8-b209-b4ada899613b}";

    public void addEntry(nsISHEntry var1, boolean var2);

    public nsISHTransaction getRootTransaction();

    public nsIDocShell getRootDocShell();

    public void setRootDocShell(nsIDocShell var1);

    public void updateIndex();

    public void replaceEntry(int var1, nsISHEntry var2);

    public nsISHistoryListener getListener();

    public void evictContentViewers(int var1, int var2);

    public int getHistoryMaxTotalViewers();
}

