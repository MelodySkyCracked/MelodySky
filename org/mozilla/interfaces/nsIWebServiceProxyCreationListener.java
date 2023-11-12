/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIException;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIWebServiceProxy;

public interface nsIWebServiceProxyCreationListener
extends nsISupports {
    public static final String NS_IWEBSERVICEPROXYCREATIONLISTENER_IID = "{a711250b-47da-4f16-a1fd-593de16375a1}";

    public void onLoad(nsIWebServiceProxy var1);

    public void onError(nsIException var1);
}

