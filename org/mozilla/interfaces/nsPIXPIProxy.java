/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsPIXPIProxy
extends nsISupports {
    public static final String NS_PIXPIPROXY_IID = "{6f9d2890-167d-11d5-8daf-000064657374}";

    public void refreshPlugins(boolean var1);

    public void notifyRestartNeeded();

    public void alert(String var1, String var2);

    public int confirmEx(String var1, String var2, long var3, String var5, String var6, String var7, String var8, boolean[] var9);
}

