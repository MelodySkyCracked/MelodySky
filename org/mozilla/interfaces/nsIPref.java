/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIFile;
import org.mozilla.interfaces.nsIFileSpec;
import org.mozilla.interfaces.nsILocalFile;
import org.mozilla.interfaces.nsIObserver;
import org.mozilla.interfaces.nsIPrefBranch;
import org.mozilla.interfaces.nsISupports;

public interface nsIPref
extends nsISupports {
    public static final String NS_IPREF_IID = "{a22ad7b0-ca86-11d1-a9a4-00805f8a7ac4}";
    public static final int ePrefInvalid = 0;
    public static final int ePrefLocked = 1;
    public static final int ePrefUserset = 2;
    public static final int ePrefConfig = 4;
    public static final int ePrefRemote = 8;
    public static final int ePrefLilocal = 16;
    public static final int ePrefString = 32;
    public static final int ePrefInt = 64;
    public static final int ePrefBool = 128;
    public static final int ePrefValuetypeMask = 224;

    public void readUserPrefs(nsIFile var1);

    public void resetPrefs();

    public void resetUserPrefs();

    public void savePrefFile(nsIFile var1);

    public nsIPrefBranch getBranch(String var1);

    public nsIPrefBranch getDefaultBranch(String var1);

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

    public boolean prefIsLocked(String var1);

    public void lockPref(String var1);

    public void unlockPref(String var1);

    public void resetBranch(String var1);

    public void deleteBranch(String var1);

    public String[] getChildList(String var1, long[] var2);

    public void addObserver(String var1, nsIObserver var2, boolean var3);

    public void removeObserver(String var1, nsIObserver var2);

    public String copyCharPref(String var1);

    public String copyDefaultCharPref(String var1);

    public boolean getDefaultBoolPref(String var1);

    public int getDefaultIntPref(String var1);

    public void setDefaultBoolPref(String var1, boolean var2);

    public void setDefaultCharPref(String var1, String var2);

    public void setDefaultIntPref(String var1, int var2);

    public String copyUnicharPref(String var1);

    public String copyDefaultUnicharPref(String var1);

    public void setUnicharPref(String var1, String var2);

    public void setDefaultUnicharPref(String var1, String var2);

    public String getLocalizedUnicharPref(String var1);

    public String getDefaultLocalizedUnicharPref(String var1);

    public nsIFileSpec getFilePref(String var1);

    public void setFilePref(String var1, nsIFileSpec var2, boolean var3);

    public nsILocalFile getFileXPref(String var1);

    public void setFileXPref(String var1, nsILocalFile var2);
}

