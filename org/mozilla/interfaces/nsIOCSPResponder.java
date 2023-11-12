/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIOCSPResponder
extends nsISupports {
    public static final String NS_IOCSPRESPONDER_IID = "{96b2f5ae-4334-11d5-ba27-00108303b117}";

    public String getResponseSigner();

    public String getServiceURL();
}

