/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIObserver
extends nsISupports {
    public static final String NS_IOBSERVER_IID = "{db242e01-e4d9-11d2-9dde-000064657374}";

    public void observe(nsISupports var1, String var2, String var3);
}

