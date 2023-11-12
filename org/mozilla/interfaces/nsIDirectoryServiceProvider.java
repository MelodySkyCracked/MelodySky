/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIFile;
import org.mozilla.interfaces.nsISupports;

public interface nsIDirectoryServiceProvider
extends nsISupports {
    public static final String NS_IDIRECTORYSERVICEPROVIDER_IID = "{bbf8cab0-d43a-11d3-8cc2-00609792278c}";

    public nsIFile getFile(String var1, boolean[] var2);
}

