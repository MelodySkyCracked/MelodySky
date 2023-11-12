/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.jsdIContext;
import org.mozilla.interfaces.jsdIEphemeral;
import org.mozilla.interfaces.jsdIScript;
import org.mozilla.interfaces.jsdIValue;

public interface jsdIStackFrame
extends jsdIEphemeral {
    public static final String JSDISTACKFRAME_IID = "{b6d50784-1dd1-11b2-a932-882246c6fe45}";

    public boolean getIsNative();

    public boolean getIsDebugger();

    public boolean getIsConstructing();

    public jsdIStackFrame getCallingFrame();

    public jsdIContext getExecutionContext();

    public String getFunctionName();

    public jsdIScript getScript();

    public long getPc();

    public long getLine();

    public jsdIValue getCallee();

    public jsdIValue getScope();

    public jsdIValue getThisValue();

    public boolean eval(String var1, String var2, long var3, jsdIValue[] var5);
}

