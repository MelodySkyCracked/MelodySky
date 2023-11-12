/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIBinaryInputStream;
import org.mozilla.interfaces.nsISupports;

public interface nsIObjectInputStream
extends nsIBinaryInputStream {
    public static final String NS_IOBJECTINPUTSTREAM_IID = "{6c248606-4eae-46fa-9df0-ba58502368eb}";

    public nsISupports readObject(boolean var1);
}

