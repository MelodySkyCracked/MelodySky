/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIXmlRpcClient;
import org.mozilla.interfaces.nsIXmlRpcFault;

public interface nsIXmlRpcClientListener
extends nsISupports {
    public static final String NS_IXMLRPCCLIENTLISTENER_IID = "{27e60cd8-6d63-4d87-b7d1-82c09e0c7363}";

    public void onResult(nsIXmlRpcClient var1, nsISupports var2, nsISupports var3);

    public void onFault(nsIXmlRpcClient var1, nsISupports var2, nsIXmlRpcFault var3);

    public void onError(nsIXmlRpcClient var1, nsISupports var2, long var3, String var5);
}

