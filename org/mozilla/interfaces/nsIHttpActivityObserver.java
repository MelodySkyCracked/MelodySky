/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIHttpActivityObserver
extends nsISupports {
    public static final String NS_IHTTPACTIVITYOBSERVER_IID = "{412880c8-6c36-48d8-bf8f-84f91f892503}";
    public static final long ACTIVITY_TYPE_SOCKET_TRANSPORT = 1L;
    public static final long ACTIVITY_TYPE_HTTP_TRANSACTION = 2L;
    public static final long ACTIVITY_SUBTYPE_REQUEST_HEADER = 20481L;
    public static final long ACTIVITY_SUBTYPE_REQUEST_BODY_SENT = 20482L;
    public static final long ACTIVITY_SUBTYPE_RESPONSE_START = 20483L;
    public static final long ACTIVITY_SUBTYPE_RESPONSE_HEADER = 20484L;
    public static final long ACTIVITY_SUBTYPE_RESPONSE_COMPLETE = 20485L;
    public static final long ACTIVITY_SUBTYPE_TRANSACTION_CLOSE = 20486L;

    public void observeActivity(nsISupports var1, long var2, long var4, double var6, double var8, String var10);

    public boolean getIsActive();
}

