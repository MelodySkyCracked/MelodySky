/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIErrorService
extends nsISupports {
    public static final String NS_IERRORSERVICE_IID = "{e72f94b2-5f85-11d4-9877-00c04fa0cf4a}";

    public void registerErrorStringBundle(short var1, String var2);

    public void unregisterErrorStringBundle(short var1);

    public String getErrorStringBundle(short var1);

    public void registerErrorStringBundleKey(long var1, String var3);

    public void unregisterErrorStringBundleKey(long var1);

    public String getErrorStringBundleKey(long var1);
}

