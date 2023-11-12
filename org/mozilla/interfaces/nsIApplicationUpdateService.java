/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIRequestObserver;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIUpdate;
import org.mozilla.interfaces.nsIUpdateChecker;

public interface nsIApplicationUpdateService
extends nsISupports {
    public static final String NS_IAPPLICATIONUPDATESERVICE_IID = "{9849c4bf-5197-4d22-baa8-e3b44a1703d2}";

    public nsIUpdateChecker getBackgroundChecker();

    public nsIUpdate selectUpdate(nsIUpdate[] var1, long var2);

    public void addDownloadListener(nsIRequestObserver var1);

    public void removeDownloadListener(nsIRequestObserver var1);

    public String downloadUpdate(nsIUpdate var1, boolean var2);

    public void pauseDownload();

    public boolean getIsDownloading();

    public boolean getCanUpdate();
}

