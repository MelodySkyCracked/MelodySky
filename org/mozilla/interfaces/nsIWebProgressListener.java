/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIRequest;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;
import org.mozilla.interfaces.nsIWebProgress;

public interface nsIWebProgressListener
extends nsISupports {
    public static final String NS_IWEBPROGRESSLISTENER_IID = "{570f39d1-efd0-11d3-b093-00a024ffc08c}";
    public static final long STATE_START = 1L;
    public static final long STATE_REDIRECTING = 2L;
    public static final long STATE_TRANSFERRING = 4L;
    public static final long STATE_NEGOTIATING = 8L;
    public static final long STATE_STOP = 16L;
    public static final long STATE_IS_REQUEST = 65536L;
    public static final long STATE_IS_DOCUMENT = 131072L;
    public static final long STATE_IS_NETWORK = 262144L;
    public static final long STATE_IS_WINDOW = 524288L;
    public static final long STATE_RESTORING = 0x1000000L;
    public static final long STATE_IS_INSECURE = 4L;
    public static final long STATE_IS_BROKEN = 1L;
    public static final long STATE_IS_SECURE = 2L;
    public static final long STATE_SECURE_HIGH = 262144L;
    public static final long STATE_SECURE_MED = 65536L;
    public static final long STATE_SECURE_LOW = 131072L;

    public void onStateChange(nsIWebProgress var1, nsIRequest var2, long var3, long var5);

    public void onProgressChange(nsIWebProgress var1, nsIRequest var2, int var3, int var4, int var5, int var6);

    public void onLocationChange(nsIWebProgress var1, nsIRequest var2, nsIURI var3);

    public void onStatusChange(nsIWebProgress var1, nsIRequest var2, long var3, String var5);

    public void onSecurityChange(nsIWebProgress var1, nsIRequest var2, long var3);
}

