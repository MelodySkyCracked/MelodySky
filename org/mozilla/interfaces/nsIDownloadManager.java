/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsICancelable;
import org.mozilla.interfaces.nsIDOMWindow;
import org.mozilla.interfaces.nsIDownload;
import org.mozilla.interfaces.nsIDownloadProgressListener;
import org.mozilla.interfaces.nsILocalFile;
import org.mozilla.interfaces.nsIMIMEInfo;
import org.mozilla.interfaces.nsIRDFDataSource;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsISupportsArray;
import org.mozilla.interfaces.nsIURI;

public interface nsIDownloadManager
extends nsISupports {
    public static final String NS_IDOWNLOADMANAGER_IID = "{1f280341-30f4-4009-bb0d-a78f2936d1fb}";
    public static final short DOWNLOAD_NOTSTARTED = -1;
    public static final short DOWNLOAD_DOWNLOADING = 0;
    public static final short DOWNLOAD_FINISHED = 1;
    public static final short DOWNLOAD_FAILED = 2;
    public static final short DOWNLOAD_CANCELED = 3;
    public static final short DOWNLOAD_PAUSED = 4;
    public static final short DOWNLOAD_TYPE_DOWNLOAD = 0;

    public nsIDownload addDownload(short var1, nsIURI var2, nsIURI var3, String var4, String var5, nsIMIMEInfo var6, double var7, nsILocalFile var9, nsICancelable var10);

    public nsIDownload getDownload(String var1);

    public void cancelDownload(String var1);

    public void removeDownload(String var1);

    public void pauseDownload(String var1);

    public void resumeDownload(String var1);

    public void open(nsIDOMWindow var1, String var2);

    public nsIDownloadProgressListener getListener();

    public void setListener(nsIDownloadProgressListener var1);

    public void startBatchUpdate();

    public void endBatchUpdate();

    public boolean getCanCleanUp();

    public void cleanUp();

    public int getActiveDownloadCount();

    public nsISupportsArray getActiveDownloads();

    public void saveState();

    public void flush();

    public nsIRDFDataSource getDatasource();
}

