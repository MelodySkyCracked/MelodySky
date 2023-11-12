/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIProfile
extends nsISupports {
    public static final String NS_IPROFILE_IID = "{02b0625a-e7f3-11d2-9f5a-006008a6efe9}";
    public static final long SHUTDOWN_PERSIST = 1L;
    public static final long SHUTDOWN_CLEANSE = 2L;

    public int getProfileCount();

    public String[] getProfileList(long[] var1);

    public boolean profileExists(String var1);

    public String getCurrentProfile();

    public void setCurrentProfile(String var1);

    public void shutDownCurrentProfile(long var1);

    public void createNewProfile(String var1, String var2, String var3, boolean var4);

    public void renameProfile(String var1, String var2);

    public void deleteProfile(String var1, boolean var2);

    public void cloneProfile(String var1);
}

