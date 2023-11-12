/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIException;
import org.mozilla.interfaces.nsIStackFrame;
import org.mozilla.interfaces.nsISupports;

public interface nsIXPCException
extends nsIException {
    public static final String NS_IXPCEXCEPTION_IID = "{b2a34010-3983-11d3-9888-006008962422}";

    public void initialize(String var1, long var2, String var4, nsIStackFrame var5, nsISupports var6, nsIException var7);
}

