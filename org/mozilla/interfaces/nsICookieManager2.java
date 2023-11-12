/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsICookie2;
import org.mozilla.interfaces.nsICookieManager;

public interface nsICookieManager2
extends nsICookieManager {
    public static final String NS_ICOOKIEMANAGER2_IID = "{3e73ff5f-154e-494f-b640-3c654ba2cc2b}";

    public void add(String var1, String var2, String var3, String var4, boolean var5, boolean var6, long var7);

    public boolean findMatchingCookie(nsICookie2 var1, long[] var2);
}

