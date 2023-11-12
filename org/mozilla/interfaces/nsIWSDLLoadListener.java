/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIWSDLPort;
import org.mozilla.interfaces.nsIWebServiceErrorHandler;

public interface nsIWSDLLoadListener
extends nsIWebServiceErrorHandler {
    public static final String NS_IWSDLLOADLISTENER_IID = "{c3681210-e191-11d8-949e-000393b6661a}";

    public void onLoad(nsIWSDLPort var1);
}

