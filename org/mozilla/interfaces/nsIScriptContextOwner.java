/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIScriptContextOwner
extends nsISupports {
    public static final String NS_ISCRIPTCONTEXTOWNER_IID = "{a94ec640-0bba-11d2-b326-00805f8a3859}";

    public nsISupports getScriptContext();

    public nsISupports getScriptGlobalObject();

    public void releaseScriptContext(nsISupports var1);

    public void reportScriptError(String var1, String var2, int var3, String var4);
}

