/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIInputStream;

public interface nsIMultiplexInputStream
extends nsIInputStream {
    public static final String NS_IMULTIPLEXINPUTSTREAM_IID = "{a076fd12-1dd1-11b2-b19a-d53b5dffaade}";

    public long getCount();

    public void appendStream(nsIInputStream var1);

    public void insertStream(nsIInputStream var1, long var2);

    public void removeStream(long var1);

    public nsIInputStream getStream(long var1);
}

