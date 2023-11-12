/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIXULBrowserWindow
extends nsISupports {
    public static final String NS_IXULBROWSERWINDOW_IID = "{46b4015c-0121-11d4-9877-00c04fa0d27a}";

    public void setJSStatus(String var1);

    public void setJSDefaultStatus(String var1);

    public void setOverLink(String var1);
}

