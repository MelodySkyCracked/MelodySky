/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIWebServiceErrorHandler
extends nsISupports {
    public static final String NS_IWEBSERVICEERRORHANDLER_IID = "{068e20e0-df59-11d8-869f-000393b6661a}";

    public void onError(long var1, String var3);
}

