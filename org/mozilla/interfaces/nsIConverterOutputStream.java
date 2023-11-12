/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIOutputStream;
import org.mozilla.interfaces.nsIUnicharOutputStream;

public interface nsIConverterOutputStream
extends nsIUnicharOutputStream {
    public static final String NS_ICONVERTEROUTPUTSTREAM_IID = "{4b71113a-cb0d-479f-8ed5-01daeba2e8d4}";

    public void init(nsIOutputStream var1, String var2, long var3, int var5);
}

