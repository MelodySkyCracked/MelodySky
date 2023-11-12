/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIContentViewer;
import org.mozilla.interfaces.nsIDocShellTreeItem;
import org.mozilla.interfaces.nsIHistoryEntry;
import org.mozilla.interfaces.nsIInputStream;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsISupportsArray;
import org.mozilla.interfaces.nsIURI;

public interface nsISHEntry
extends nsIHistoryEntry {
    public static final String NS_ISHENTRY_IID = "{542a98b9-2889-4922-aaf4-02b6056f4136}";

    public void setURI(nsIURI var1);

    public nsIURI getReferrerURI();

    public void setReferrerURI(nsIURI var1);

    public nsIContentViewer getContentViewer();

    public void setContentViewer(nsIContentViewer var1);

    public boolean getSticky();

    public void setSticky(boolean var1);

    public nsISupports getWindowState();

    public void setWindowState(nsISupports var1);

    public void addChildShell(nsIDocShellTreeItem var1);

    public nsIDocShellTreeItem childShellAt(int var1);

    public void clearChildShells();

    public nsISupportsArray getRefreshURIList();

    public void setRefreshURIList(nsISupportsArray var1);

    public void syncPresentationState();

    public void setTitle(String var1);

    public nsIInputStream getPostData();

    public void setPostData(nsIInputStream var1);

    public nsISupports getLayoutHistoryState();

    public void setLayoutHistoryState(nsISupports var1);

    public nsISHEntry getParent();

    public void setParent(nsISHEntry var1);

    public long getLoadType();

    public void setLoadType(long var1);

    public long getID();

    public void setID(long var1);

    public long getPageIdentifier();

    public void setPageIdentifier(long var1);

    public nsISupports getCacheKey();

    public void setCacheKey(nsISupports var1);

    public boolean getSaveLayoutStateFlag();

    public void setSaveLayoutStateFlag(boolean var1);

    public boolean getExpirationStatus();

    public void setExpirationStatus(boolean var1);

    public String getContentType();

    public void setContentType(String var1);

    public void setScrollPosition(int var1, int var2);

    public void getScrollPosition(int[] var1, int[] var2);

    public void create(nsIURI var1, String var2, nsIInputStream var3, nsISupports var4, nsISupports var5, String var6);

    public nsISHEntry _clone();

    public void setIsSubFrame(boolean var1);

    public nsIContentViewer getAnyContentViewer(nsISHEntry[] var1);
}

