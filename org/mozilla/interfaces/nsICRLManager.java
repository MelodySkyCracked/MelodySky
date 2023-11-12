/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIArray;
import org.mozilla.interfaces.nsICRLInfo;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;

public interface nsICRLManager
extends nsISupports {
    public static final String NS_ICRLMANAGER_IID = "{486755db-627a-4678-a21b-f6a63bb9c56a}";
    public static final long TYPE_AUTOUPDATE_TIME_BASED = 1L;
    public static final long TYPE_AUTOUPDATE_FREQ_BASED = 2L;

    public void importCrl(byte[] var1, long var2, nsIURI var4, long var5, boolean var7, String var8);

    public boolean updateCRLFromURL(String var1, String var2);

    public nsIArray getCrls();

    public void deleteCrl(long var1);

    public void rescheduleCRLAutoUpdate();

    public String computeNextAutoUpdateTime(nsICRLInfo var1, long var2, double var4);
}

