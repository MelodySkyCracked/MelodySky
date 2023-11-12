/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIInputStream;
import org.mozilla.interfaces.nsISupports;

public interface nsIScriptableInputStream
extends nsISupports {
    public static final String NS_ISCRIPTABLEINPUTSTREAM_IID = "{a2a32f90-9b90-11d3-a189-0050041caf44}";

    public void close();

    public void init(nsIInputStream var1);

    public long available();

    public String read(long var1);
}

