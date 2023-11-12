/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIXPIProgressDialog
extends nsISupports {
    public static final String NS_IXPIPROGRESSDIALOG_IID = "{ce8f744e-d5a5-41b3-911f-0fee3008b64e}";
    public static final short DOWNLOAD_START = 0;
    public static final short DOWNLOAD_DONE = 1;
    public static final short INSTALL_START = 2;
    public static final short INSTALL_DONE = 3;
    public static final short DIALOG_CLOSE = 4;

    public void onStateChange(long var1, short var3, int var4);

    public void onProgress(long var1, double var3, double var5);
}

