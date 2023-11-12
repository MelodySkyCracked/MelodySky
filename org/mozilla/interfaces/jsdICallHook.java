/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.jsdIStackFrame;
import org.mozilla.interfaces.nsISupports;

public interface jsdICallHook
extends nsISupports {
    public static final String JSDICALLHOOK_IID = "{f102caf6-1dd1-11b2-bd43-c1dbacb95a98}";
    public static final long TYPE_TOPLEVEL_START = 0L;
    public static final long TYPE_TOPLEVEL_END = 1L;
    public static final long TYPE_FUNCTION_CALL = 2L;
    public static final long TYPE_FUNCTION_RETURN = 3L;

    public void onCall(jsdIStackFrame var1, long var2);
}

