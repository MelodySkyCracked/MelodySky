/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIInputStream;
import org.mozilla.interfaces.nsIMemory;
import org.mozilla.interfaces.nsIOutputStream;
import org.mozilla.interfaces.nsISupports;

public interface nsIStorageStream
extends nsISupports {
    public static final String NS_ISTORAGESTREAM_IID = "{604ad9d0-753e-11d3-90ca-34278643278f}";

    public void init(long var1, long var3, nsIMemory var5);

    public nsIOutputStream getOutputStream(int var1);

    public nsIInputStream newInputStream(int var1);

    public long getLength();

    public void setLength(long var1);

    public boolean getWriteInProgress();
}

