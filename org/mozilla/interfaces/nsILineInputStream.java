/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsILineInputStream
extends nsISupports {
    public static final String NS_ILINEINPUTSTREAM_IID = "{c97b466c-1e6e-4773-a4ab-2b2b3190a7a6}";

    public boolean readLine(String[] var1);
}

