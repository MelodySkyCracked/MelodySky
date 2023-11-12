/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIXPIProgressDialog;

public interface nsIXPInstallManager
extends nsISupports {
    public static final String NS_IXPINSTALLMANAGER_IID = "{566689cb-9926-4bec-a66e-a034e364ad2c}";

    public void initManagerFromChrome(String[] var1, long var2, nsIXPIProgressDialog var4);

    public void initManagerWithHashes(String[] var1, String[] var2, long var3, nsIXPIProgressDialog var5);
}

