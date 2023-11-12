/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDownloader;
import org.mozilla.interfaces.nsIFile;
import org.mozilla.interfaces.nsIRequest;
import org.mozilla.interfaces.nsISupports;

public interface nsIDownloadObserver
extends nsISupports {
    public static final String NS_IDOWNLOADOBSERVER_IID = "{44b3153e-a54e-4077-a527-b0325e40924e}";

    public void onDownloadComplete(nsIDownloader var1, nsIRequest var2, nsISupports var3, long var4, nsIFile var6);
}

