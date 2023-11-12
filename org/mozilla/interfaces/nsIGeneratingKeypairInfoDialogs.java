/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIInterfaceRequestor;
import org.mozilla.interfaces.nsIKeygenThread;
import org.mozilla.interfaces.nsISupports;

public interface nsIGeneratingKeypairInfoDialogs
extends nsISupports {
    public static final String NS_IGENERATINGKEYPAIRINFODIALOGS_IID = "{11bf5cdc-1dd2-11b2-ba6a-c76afb326fa1}";

    public void displayGeneratingKeypairInfo(nsIInterfaceRequestor var1, nsIKeygenThread var2);
}

