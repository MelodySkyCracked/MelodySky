/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIHelperAppLauncher;
import org.mozilla.interfaces.nsILocalFile;
import org.mozilla.interfaces.nsISupports;

public interface nsIHelperAppLauncherDialog
extends nsISupports {
    public static final String NS_IHELPERAPPLAUNCHERDIALOG_IID = "{64355793-988d-40a5-ba8e-fcde78cac631}";
    public static final long REASON_CANTHANDLE = 0L;
    public static final long REASON_SERVERREQUEST = 1L;
    public static final long REASON_TYPESNIFFED = 2L;

    public void show(nsIHelperAppLauncher var1, nsISupports var2, long var3);

    public nsILocalFile promptForSaveToFile(nsIHelperAppLauncher var1, nsISupports var2, String var3, String var4);
}

