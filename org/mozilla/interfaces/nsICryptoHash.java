/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIInputStream;
import org.mozilla.interfaces.nsISupports;

public interface nsICryptoHash
extends nsISupports {
    public static final String NS_ICRYPTOHASH_IID = "{1e5b7c43-4688-45ce-92e1-77ed931e3bbe}";
    public static final short MD2 = 1;
    public static final short MD5 = 2;
    public static final short SHA1 = 3;
    public static final short SHA256 = 4;
    public static final short SHA384 = 5;
    public static final short SHA512 = 6;

    public void init(long var1);

    public void initWithString(String var1);

    public void update(byte[] var1, long var2);

    public void updateFromStream(nsIInputStream var1, long var2);

    public String finish(boolean var1);
}

