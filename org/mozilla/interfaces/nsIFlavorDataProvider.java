/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsITransferable;

public interface nsIFlavorDataProvider
extends nsISupports {
    public static final String NS_IFLAVORDATAPROVIDER_IID = "{7e225e5f-711c-11d7-9fae-000393636592}";

    public void getFlavorData(nsITransferable var1, String var2, nsISupports[] var3, long[] var4);
}

