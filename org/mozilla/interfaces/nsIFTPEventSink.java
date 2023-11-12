/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIFTPEventSink
extends nsISupports {
    public static final String NS_IFTPEVENTSINK_IID = "{455d4234-0330-43d2-bbfb-99afbecbfeb0}";

    public void onFTPControlLog(boolean var1, String var2);
}

