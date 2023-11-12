/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIInterfaceRequestor;
import org.mozilla.interfaces.nsIRequest;
import org.mozilla.interfaces.nsIRequestObserver;
import org.mozilla.interfaces.nsISimpleEnumerator;
import org.mozilla.interfaces.nsISupports;

public interface nsILoadGroup
extends nsIRequest {
    public static final String NS_ILOADGROUP_IID = "{3de0a31c-feaf-400f-9f1e-4ef71f8b20cc}";

    public nsIRequestObserver getGroupObserver();

    public void setGroupObserver(nsIRequestObserver var1);

    public nsIRequest getDefaultLoadRequest();

    public void setDefaultLoadRequest(nsIRequest var1);

    public void addRequest(nsIRequest var1, nsISupports var2);

    public void removeRequest(nsIRequest var1, nsISupports var2, long var3);

    public nsISimpleEnumerator getRequests();

    public long getActiveCount();

    public nsIInterfaceRequestor getNotificationCallbacks();

    public void setNotificationCallbacks(nsIInterfaceRequestor var1);
}

