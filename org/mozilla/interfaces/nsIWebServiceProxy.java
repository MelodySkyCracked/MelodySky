/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIScriptableInterfaces;
import org.mozilla.interfaces.nsISimpleEnumerator;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIWSDLPort;

public interface nsIWebServiceProxy
extends nsISupports {
    public static final String NS_IWEBSERVICEPROXY_IID = "{2122421c-1326-41db-87f8-25519d8a12cb}";

    public nsIWSDLPort getPort();

    public boolean getIsAsync();

    public String getQualifier();

    public nsISimpleEnumerator getPendingCalls();

    public String getPrimaryInterfaceName();

    public String getPrimaryAsyncListenerInterfaceName();

    public nsIScriptableInterfaces getInterfaces();
}

