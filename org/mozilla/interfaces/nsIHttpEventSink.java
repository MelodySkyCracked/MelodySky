/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIChannel;
import org.mozilla.interfaces.nsIHttpChannel;
import org.mozilla.interfaces.nsISupports;

public interface nsIHttpEventSink
extends nsISupports {
    public static final String NS_IHTTPEVENTSINK_IID = "{9475a6af-6352-4251-90f9-d65b1cd2ea15}";

    public void onRedirect(nsIHttpChannel var1, nsIChannel var2);
}

