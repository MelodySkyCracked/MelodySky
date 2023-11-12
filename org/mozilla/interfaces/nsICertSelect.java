/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIX509Cert;

public interface nsICertSelect
extends nsISupports {
    public static final String NS_ICERTSELECT_IID = "{3cac403c-edb3-11d4-998b-00b0d02354a0}";

    public nsIX509Cert selectClientAuthCert(nsISupports var1, long var2, nsIX509Cert[] var4);
}

