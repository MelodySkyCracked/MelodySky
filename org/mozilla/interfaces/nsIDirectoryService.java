/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDirectoryServiceProvider;
import org.mozilla.interfaces.nsISupports;

public interface nsIDirectoryService
extends nsISupports {
    public static final String NS_IDIRECTORYSERVICE_IID = "{57a66a60-d43a-11d3-8cc2-00609792278c}";

    public void init();

    public void registerProvider(nsIDirectoryServiceProvider var1);

    public void unregisterProvider(nsIDirectoryServiceProvider var1);
}

