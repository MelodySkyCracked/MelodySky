/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIChannel;
import org.mozilla.interfaces.nsISupports;

public interface nsIChannelEventSink
extends nsISupports {
    public static final String NS_ICHANNELEVENTSINK_IID = "{6757d790-2916-498e-aaca-6b668a956875}";
    public static final long REDIRECT_TEMPORARY = 1L;
    public static final long REDIRECT_PERMANENT = 2L;
    public static final long REDIRECT_INTERNAL = 4L;

    public void onChannelRedirect(nsIChannel var1, nsIChannel var2, long var3);
}

