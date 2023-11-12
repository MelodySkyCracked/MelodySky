/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIUnicharOutputStream
extends nsISupports {
    public static final String NS_IUNICHAROUTPUTSTREAM_IID = "{2d00b1bb-8b21-4a63-bcc6-7213f513ac2e}";

    public boolean write(long var1, int[] var3);

    public boolean writeString(String var1);

    public void flush();

    public void close();
}

