/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIInputStream;
import org.mozilla.interfaces.nsISupports;

public interface nsIOutputStream
extends nsISupports {
    public static final String NS_IOUTPUTSTREAM_IID = "{0d0acd2a-61b4-11d4-9877-00c04fa0cf4a}";

    public void close();

    public void flush();

    public long write(String var1, long var2);

    public long writeFrom(nsIInputStream var1, long var2);

    public boolean isNonBlocking();
}

