/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.jsdIStackFrame;
import org.mozilla.interfaces.jsdIValue;
import org.mozilla.interfaces.nsISupports;

public interface jsdIExecutionHook
extends nsISupports {
    public static final String JSDIEXECUTIONHOOK_IID = "{9a7b6ad0-1dd1-11b2-a789-fcfae96356a2}";
    public static final long TYPE_INTERRUPTED = 0L;
    public static final long TYPE_BREAKPOINT = 1L;
    public static final long TYPE_DEBUG_REQUESTED = 2L;
    public static final long TYPE_DEBUGGER_KEYWORD = 3L;
    public static final long TYPE_THROW = 4L;
    public static final long RETURN_HOOK_ERROR = 0L;
    public static final long RETURN_CONTINUE = 1L;
    public static final long RETURN_ABORT = 2L;
    public static final long RETURN_RET_WITH_VAL = 3L;
    public static final long RETURN_THROW_WITH_VAL = 4L;
    public static final long RETURN_CONTINUE_THROW = 5L;

    public long onExecute(jsdIStackFrame var1, long var2, jsdIValue[] var4);
}

