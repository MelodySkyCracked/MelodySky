/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIX509Cert;

public interface nsIDOMCryptoDialogs
extends nsISupports {
    public static final String NS_IDOMCRYPTODIALOGS_IID = "{1f8fe77e-1dd2-11b2-8dd2-e55f8d3465b8}";

    public boolean confirmKeyEscrow(nsIX509Cert var1);
}

