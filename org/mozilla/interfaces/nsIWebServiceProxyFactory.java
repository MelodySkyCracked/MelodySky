/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIWebServiceProxy;
import org.mozilla.interfaces.nsIWebServiceProxyCreationListener;

public interface nsIWebServiceProxyFactory
extends nsISupports {
    public static final String NS_IWEBSERVICEPROXYFACTORY_IID = "{693611be-bb38-40e0-a98e-b46ff8a5bcca}";

    public nsIWebServiceProxy createProxy(String var1, String var2, String var3, boolean var4);

    public void createProxyAsync(String var1, String var2, String var3, boolean var4, nsIWebServiceProxyCreationListener var5);
}

