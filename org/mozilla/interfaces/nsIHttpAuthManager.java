/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIHttpAuthManager
extends nsISupports {
    public static final String NS_IHTTPAUTHMANAGER_IID = "{7ce8e9d1-8b4b-4883-a307-66fe12a50153}";

    public void getAuthIdentity(String var1, String var2, int var3, String var4, String var5, String var6, String[] var7, String[] var8, String[] var9);

    public void setAuthIdentity(String var1, String var2, int var3, String var4, String var5, String var6, String var7, String var8, String var9);

    public void clearAll();
}

