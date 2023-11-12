/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIFile;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIZipReader;

public interface nsIZipReaderCache
extends nsISupports {
    public static final String NS_IZIPREADERCACHE_IID = "{52c45d86-0cc3-11d4-986e-00c04fa0cf4a}";

    public void init(long var1);

    public nsIZipReader getZip(nsIFile var1);
}

