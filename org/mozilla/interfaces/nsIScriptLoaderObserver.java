/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;

public interface nsIScriptLoaderObserver
extends nsISupports {
    public static final String NS_ISCRIPTLOADEROBSERVER_IID = "{501209d3-7edf-437d-9948-3c6d1c08ef7f}";

    public void scriptAvailable(long var1, nsISupports var3, boolean var4, boolean var5, nsIURI var6, int var7, String var8);

    public void scriptEvaluated(long var1, nsISupports var3, boolean var4, boolean var5);
}

