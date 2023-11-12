/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIProxyAutoConfig
extends nsISupports {
    public static final String NS_IPROXYAUTOCONFIG_IID = "{a42619df-0a1c-46fb-8154-0e9b8f8f1ea8}";

    public void init(String var1, String var2);

    public String getProxyForURI(String var1, String var2);
}

