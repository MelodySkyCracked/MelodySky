/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDownloadObserver;
import org.mozilla.interfaces.nsIFile;
import org.mozilla.interfaces.nsIStreamListener;

public interface nsIDownloader
extends nsIStreamListener {
    public static final String NS_IDOWNLOADER_IID = "{fafe41a9-a531-4d6d-89bc-588a6522fb4e}";

    public void init(nsIDownloadObserver var1, nsIFile var2);
}

