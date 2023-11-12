/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsICiter
extends nsISupports {
    public static final String NS_ICITER_IID = "{a6cf9102-15b3-11d2-932e-00805f8add32}";

    public String getCiteString(String var1);

    public String stripCites(String var1);

    public String rewrap(String var1, long var2, long var4, boolean var6);
}

