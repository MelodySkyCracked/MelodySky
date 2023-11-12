/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIInputStream
extends nsISupports {
    public static final String NS_IINPUTSTREAM_IID = "{fa9c7f6c-61b3-11d4-9877-00c04fa0cf4a}";

    public void close();

    public long available();

    public boolean isNonBlocking();
}

