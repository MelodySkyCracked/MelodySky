/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsICertVerificationResult;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIX509Cert3;

public interface nsICertVerificationListener
extends nsISupports {
    public static final String NS_ICERTVERIFICATIONLISTENER_IID = "{6684bce9-50db-48e1-81b7-98102bf81357}";

    public void _notify(nsIX509Cert3 var1, nsICertVerificationResult var2);
}

