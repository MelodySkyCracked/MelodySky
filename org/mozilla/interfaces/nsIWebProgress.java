/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMWindow;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIWebProgressListener;

public interface nsIWebProgress
extends nsISupports {
    public static final String NS_IWEBPROGRESS_IID = "{570f39d0-efd0-11d3-b093-00a024ffc08c}";
    public static final long NOTIFY_STATE_REQUEST = 1L;
    public static final long NOTIFY_STATE_DOCUMENT = 2L;
    public static final long NOTIFY_STATE_NETWORK = 4L;
    public static final long NOTIFY_STATE_WINDOW = 8L;
    public static final long NOTIFY_STATE_ALL = 15L;
    public static final long NOTIFY_PROGRESS = 16L;
    public static final long NOTIFY_STATUS = 32L;
    public static final long NOTIFY_SECURITY = 64L;
    public static final long NOTIFY_LOCATION = 128L;
    public static final long NOTIFY_ALL = 255L;

    public void addProgressListener(nsIWebProgressListener var1, long var2);

    public void removeProgressListener(nsIWebProgressListener var1);

    public nsIDOMWindow getDOMWindow();

    public boolean getIsLoadingDocument();
}

