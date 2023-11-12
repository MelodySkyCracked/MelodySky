/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIPrefBranch
extends nsISupports {
    public static final String NS_IPREFBRANCH_IID = "{56c35506-f14b-11d3-99d3-ddbfac2ccf65}";
    public static final int PREF_INVALID = 0;
    public static final int PREF_STRING = 32;
    public static final int PREF_INT = 64;
    public static final int PREF_BOOL = 128;

    public String getRoot();

    public int getPrefType(String var1);

    public boolean getBoolPref(String var1);

    public void setBoolPref(String var1, int var2);

    public String getCharPref(String var1);

    public void setCharPref(String var1, String var2);

    public int getIntPref(String var1);

    public void setIntPref(String var1, int var2);

    public nsISupports getComplexValue(String var1, String var2);

    public void setComplexValue(String var1, String var2, nsISupports var3);

    public void clearUserPref(String var1);

    public void lockPref(String var1);

    public boolean prefHasUserValue(String var1);

    public boolean prefIsLocked(String var1);

    public void unlockPref(String var1);

    public void deleteBranch(String var1);

    public String[] getChildList(String var1, long[] var2);

    public void resetBranch(String var1);
}

