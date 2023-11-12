/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIChannel;
import org.mozilla.interfaces.nsIRequest;
import org.mozilla.interfaces.nsIStreamLoaderObserver;
import org.mozilla.interfaces.nsISupports;

public interface nsIStreamLoader
extends nsISupports {
    public static final String NS_ISTREAMLOADER_IID = "{31d37360-8e5a-11d3-93ad-00104ba0fd40}";

    public void init(nsIChannel var1, nsIStreamLoaderObserver var2, nsISupports var3);

    public long getNumBytesRead();

    public nsIRequest getRequest();
}

