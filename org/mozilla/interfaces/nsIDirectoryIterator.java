/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIFileSpec;
import org.mozilla.interfaces.nsISupports;

public interface nsIDirectoryIterator
extends nsISupports {
    public static final String NS_IDIRECTORYITERATOR_IID = "{d8c0a083-0868-11d3-915f-d9d889d48e3c}";

    public void init(nsIFileSpec var1, boolean var2);

    public boolean exists();

    public void next();

    public nsIFileSpec getCurrentSpec();
}

