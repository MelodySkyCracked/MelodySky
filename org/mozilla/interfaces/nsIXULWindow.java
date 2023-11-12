/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIAppShell;
import org.mozilla.interfaces.nsIDocShell;
import org.mozilla.interfaces.nsIDocShellTreeItem;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIXULBrowserWindow;

public interface nsIXULWindow
extends nsISupports {
    public static final String NS_IXULWINDOW_IID = "{b6c2f9e1-53a0-45f2-a2b8-fe37861fe8a8}";
    public static final long lowestZ = 0L;
    public static final long loweredZ = 4L;
    public static final long normalZ = 5L;
    public static final long raisedZ = 6L;
    public static final long highestZ = 9L;

    public nsIDocShell getDocShell();

    public boolean getIntrinsicallySized();

    public void setIntrinsicallySized(boolean var1);

    public nsIDocShellTreeItem getPrimaryContentShell();

    public nsIDocShellTreeItem getContentShellById(String var1);

    public void addChildWindow(nsIXULWindow var1);

    public void removeChildWindow(nsIXULWindow var1);

    public void center(nsIXULWindow var1, boolean var2, boolean var3);

    public void showModal();

    public long getZLevel();

    public void setZLevel(long var1);

    public long getContextFlags();

    public void setContextFlags(long var1);

    public long getChromeFlags();

    public void setChromeFlags(long var1);

    public nsIXULWindow createNewWindow(int var1, nsIAppShell var2);

    public nsIXULBrowserWindow getXULBrowserWindow();

    public void setXULBrowserWindow(nsIXULBrowserWindow var1);
}

