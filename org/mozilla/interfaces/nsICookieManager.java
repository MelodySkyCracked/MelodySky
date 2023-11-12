/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISimpleEnumerator;
import org.mozilla.interfaces.nsISupports;

public interface nsICookieManager
extends nsISupports {
    public static final String NS_ICOOKIEMANAGER_IID = "{aaab6710-0f2c-11d5-a53b-0010a401eb10}";

    public void removeAll();

    public nsISimpleEnumerator getEnumerator();

    public void remove(String var1, String var2, String var3, boolean var4);
}

