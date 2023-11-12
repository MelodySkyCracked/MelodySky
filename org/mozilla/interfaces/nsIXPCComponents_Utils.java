/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIXPCComponents_utils_Sandbox;

public interface nsIXPCComponents_Utils
extends nsISupports {
    public static final String NS_IXPCCOMPONENTS_UTILS_IID = "{bcd54a63-34d9-468c-9a55-0fb5d2d8c677}";

    public void reportError();

    public void lookupMethod();

    public nsIXPCComponents_utils_Sandbox getSandbox();

    public void evalInSandbox(String var1);
}

