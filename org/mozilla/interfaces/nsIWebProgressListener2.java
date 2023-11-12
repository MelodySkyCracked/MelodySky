/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIRequest;
import org.mozilla.interfaces.nsIWebProgress;
import org.mozilla.interfaces.nsIWebProgressListener;

public interface nsIWebProgressListener2
extends nsIWebProgressListener {
    public static final String NS_IWEBPROGRESSLISTENER2_IID = "{3f24610d-1e1f-4151-9d2e-239884742324}";

    public void onProgressChange64(nsIWebProgress var1, nsIRequest var2, long var3, long var5, long var7, long var9);
}

