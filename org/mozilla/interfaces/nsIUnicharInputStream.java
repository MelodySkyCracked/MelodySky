/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIUnicharInputStream
extends nsISupports {
    public static final String NS_IUNICHARINPUTSTREAM_IID = "{d5e3bd80-6723-4b92-b0c9-22f6162fd94f}";

    public long readString(long var1, String[] var3);

    public void close();
}

