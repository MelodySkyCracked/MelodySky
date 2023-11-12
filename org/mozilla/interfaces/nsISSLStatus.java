/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIX509Cert;

public interface nsISSLStatus
extends nsISupports {
    public static final String NS_ISSLSTATUS_IID = "{7b2ca1ca-1dd2-11b2-87ec-d217dbe22b85}";

    public nsIX509Cert getServerCert();

    public String getCipherName();

    public long getKeyLength();

    public long getSecretKeyLength();
}

