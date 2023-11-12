/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISchema;
import org.mozilla.interfaces.nsIWebServiceErrorHandler;

public interface nsISchemaLoadListener
extends nsIWebServiceErrorHandler {
    public static final String NS_ISCHEMALOADLISTENER_IID = "{8d9aa9ce-e191-11d8-9f31-000393b6661a}";

    public void onLoad(nsISchema var1);
}

