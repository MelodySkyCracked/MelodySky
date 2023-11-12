/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIByteRangeRequest
extends nsISupports {
    public static final String NS_IBYTERANGEREQUEST_IID = "{c1b1f426-7e83-4759-9f88-0e1b17f49366}";

    public boolean getIsByteRangeRequest();

    public long getStartRange();

    public long getEndRange();
}

