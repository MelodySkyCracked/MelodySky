/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMDocument;
import org.mozilla.interfaces.nsIDownload;
import org.mozilla.interfaces.nsIRequest;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;
import org.mozilla.interfaces.nsIWebProgress;

public interface nsIDownloadProgressListener
extends nsISupports {
    public static final String NS_IDOWNLOADPROGRESSLISTENER_IID = "{8b193f0a-cf0c-4b5f-b4e3-a388df6f07b2}";

    public nsIDOMDocument getDocument();

    public void setDocument(nsIDOMDocument var1);

    public void onStateChange(nsIWebProgress var1, nsIRequest var2, long var3, long var5, nsIDownload var7);

    public void onProgressChange(nsIWebProgress var1, nsIRequest var2, long var3, long var5, long var7, long var9, nsIDownload var11);

    public void onStatusChange(nsIWebProgress var1, nsIRequest var2, long var3, String var5, nsIDownload var6);

    public void onLocationChange(nsIWebProgress var1, nsIRequest var2, nsIURI var3, nsIDownload var4);

    public void onSecurityChange(nsIWebProgress var1, nsIRequest var2, long var3, nsIDownload var5);
}

