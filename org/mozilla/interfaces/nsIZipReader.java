/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIFile;
import org.mozilla.interfaces.nsIInputStream;
import org.mozilla.interfaces.nsISimpleEnumerator;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIZipEntry;

public interface nsIZipReader
extends nsISupports {
    public static final String NS_IZIPREADER_IID = "{6ff6a966-9632-11d3-8cd9-0060b0fc14a3}";

    public void init(nsIFile var1);

    public nsIFile getFile();

    public void open();

    public void close();

    public void test(String var1);

    public void extract(String var1, nsIFile var2);

    public nsIZipEntry getEntry(String var1);

    public nsISimpleEnumerator findEntries(String var1);

    public nsIInputStream getInputStream(String var1);
}

