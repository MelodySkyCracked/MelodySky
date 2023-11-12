/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIJVMManager
extends nsISupports {
    public static final String NS_IJVMMANAGER_IID = "{a1e5ed50-aa4a-11d1-85b2-00805f0e4dfe}";

    public void showJavaConsole();

    public boolean isAllPermissionGranted(String var1, String var2, String var3, String var4);

    public boolean getJavaEnabled();
}

