/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsISearchableInputStream
extends nsISupports {
    public static final String NS_ISEARCHABLEINPUTSTREAM_IID = "{8c39ef62-f7c9-11d4-98f5-001083010e9b}";

    public void search(String var1, boolean var2, boolean[] var3, long[] var4);
}

