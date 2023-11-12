/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIHTTPHeaderListener
extends nsISupports {
    public static final String NS_IHTTPHEADERLISTENER_IID = "{8b246748-1dd2-11b2-9512-9dc84a95fc2f}";

    public void newResponseHeader(String var1, String var2);
}

