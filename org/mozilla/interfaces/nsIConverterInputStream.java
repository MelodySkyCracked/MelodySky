/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIInputStream;
import org.mozilla.interfaces.nsIUnicharInputStream;

public interface nsIConverterInputStream
extends nsIUnicharInputStream {
    public static final String NS_ICONVERTERINPUTSTREAM_IID = "{fc66ffb6-5404-4908-a4a3-27f92fa0579d}";
    public static final int DEFAULT_REPLACEMENT_CHARACTER = 65533;

    public void init(nsIInputStream var1, String var2, int var3, int var4);
}

