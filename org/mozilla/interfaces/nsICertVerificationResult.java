/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsICertVerificationResult
extends nsISupports {
    public static final String NS_ICERTVERIFICATIONRESULT_IID = "{2fd0a785-9f2d-4327-8871-8c3e0783891d}";

    public void getUsagesArrayResult(long[] var1, long[] var2, String[][] var3);
}

