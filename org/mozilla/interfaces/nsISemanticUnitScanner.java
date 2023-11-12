/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsISemanticUnitScanner
extends nsISupports {
    public static final String NS_ISEMANTICUNITSCANNER_IID = "{9f620be4-e535-11d6-b254-00039310a47a}";

    public void start(String var1);

    public boolean next(String var1, int var2, int var3, boolean var4, int[] var5, int[] var6);
}

