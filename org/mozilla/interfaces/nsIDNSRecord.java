/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIDNSRecord
extends nsISupports {
    public static final String NS_IDNSRECORD_IID = "{31c9c52e-1100-457d-abac-d2729e43f506}";

    public String getCanonicalName();

    public String getNextAddrAsString();

    public boolean hasMore();

    public void rewind();
}

