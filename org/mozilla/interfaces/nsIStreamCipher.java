/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIInputStream;
import org.mozilla.interfaces.nsIKeyObject;
import org.mozilla.interfaces.nsISupports;

public interface nsIStreamCipher
extends nsISupports {
    public static final String NS_ISTREAMCIPHER_IID = "{1d507cd6-1630-4710-af1b-4012dbcc514c}";

    public void init(nsIKeyObject var1);

    public void initWithIV(nsIKeyObject var1, byte[] var2, long var3);

    public void update(byte[] var1, long var2);

    public void updateFromStream(nsIInputStream var1, int var2);

    public void updateFromString(String var1);

    public String finish(boolean var1);

    public void discard(int var1);
}

