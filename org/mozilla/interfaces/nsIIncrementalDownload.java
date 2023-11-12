/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIFile;
import org.mozilla.interfaces.nsIRequest;
import org.mozilla.interfaces.nsIRequestObserver;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;

public interface nsIIncrementalDownload
extends nsIRequest {
    public static final String NS_IINCREMENTALDOWNLOAD_IID = "{6687823f-56c4-461d-93a1-7f6cb7dfbfba}";

    public void init(nsIURI var1, nsIFile var2, int var3, int var4);

    public nsIURI getURI();

    public nsIURI getFinalURI();

    public nsIFile getDestination();

    public long getTotalSize();

    public long getCurrentSize();

    public void start(nsIRequestObserver var1, nsISupports var2);
}

