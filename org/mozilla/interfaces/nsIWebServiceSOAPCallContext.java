/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISOAPResponse;
import org.mozilla.interfaces.nsIWebServiceCallContext;

public interface nsIWebServiceSOAPCallContext
extends nsIWebServiceCallContext {
    public static final String NS_IWEBSERVICESOAPCALLCONTEXT_IID = "{1ef83ece-b645-4b55-a501-df42c3333b47}";

    public nsISOAPResponse getSoapResponse();
}

