/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsICertVerificationListener;
import org.mozilla.interfaces.nsISupports;

public interface nsIX509Cert3
extends nsISupports {
    public static final String NS_IX509CERT3_IID = "{402aee39-653c-403f-8be1-6d1824223bf9}";

    public void requestUsagesArrayAsync(nsICertVerificationListener var1);
}

