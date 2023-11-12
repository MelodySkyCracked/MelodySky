/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIChannel;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIUnicharStreamLoaderObserver;

public interface nsIUnicharStreamLoader
extends nsISupports {
    public static final String NS_IUNICHARSTREAMLOADER_IID = "{8a3eca16-167e-443d-9485-7e84ed822e95}";
    public static final long DEFAULT_SEGMENT_SIZE = 4096L;

    public void init(nsIChannel var1, nsIUnicharStreamLoaderObserver var2, nsISupports var3, long var4);

    public nsIChannel getChannel();

    public String getCharset();
}

