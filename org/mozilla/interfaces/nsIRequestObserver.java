/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIRequest;
import org.mozilla.interfaces.nsISupports;

public interface nsIRequestObserver
extends nsISupports {
    public static final String NS_IREQUESTOBSERVER_IID = "{fd91e2e0-1481-11d3-9333-00104ba0fd40}";

    public void onStartRequest(nsIRequest var1, nsISupports var2);

    public void onStopRequest(nsIRequest var1, nsISupports var2, long var3);
}

