/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.jsdICallHook;
import org.mozilla.interfaces.jsdIContextEnumerator;
import org.mozilla.interfaces.jsdIErrorHook;
import org.mozilla.interfaces.jsdIExecutionHook;
import org.mozilla.interfaces.jsdIFilter;
import org.mozilla.interfaces.jsdIFilterEnumerator;
import org.mozilla.interfaces.jsdINestCallback;
import org.mozilla.interfaces.jsdIScriptEnumerator;
import org.mozilla.interfaces.jsdIScriptHook;
import org.mozilla.interfaces.jsdIValue;
import org.mozilla.interfaces.nsISupports;

public interface jsdIDebuggerService
extends nsISupports {
    public static final String JSDIDEBUGGERSERVICE_IID = "{9dd9006a-4e5e-4a80-ac3d-007fb7335ca4}";
    public static final int VERSION_1_0 = 100;
    public static final int VERSION_1_1 = 110;
    public static final int VERSION_1_2 = 120;
    public static final int VERSION_1_3 = 130;
    public static final int VERSION_1_4 = 140;
    public static final int VERSION_1_5 = 150;
    public static final int VERSION_DEFAULT = 0;
    public static final int VERSION_UNKNOWN = -1;
    public static final long ENABLE_NATIVE_FRAMES = 1L;
    public static final long PROFILE_WHEN_SET = 2L;
    public static final long DEBUG_WHEN_SET = 4L;
    public static final long COLLECT_PROFILE_DATA = 8L;
    public static final long HIDE_DISABLED_FRAMES = 16L;
    public static final long MASK_TOP_FRAME_ONLY = 32L;
    public static final long DISABLE_OBJECT_TRACE = 64L;

    public jsdIErrorHook getErrorHook();

    public void setErrorHook(jsdIErrorHook var1);

    public jsdIScriptHook getScriptHook();

    public void setScriptHook(jsdIScriptHook var1);

    public jsdIExecutionHook getBreakpointHook();

    public void setBreakpointHook(jsdIExecutionHook var1);

    public jsdIExecutionHook getDebuggerHook();

    public void setDebuggerHook(jsdIExecutionHook var1);

    public jsdIExecutionHook getDebugHook();

    public void setDebugHook(jsdIExecutionHook var1);

    public jsdIExecutionHook getInterruptHook();

    public void setInterruptHook(jsdIExecutionHook var1);

    public jsdIExecutionHook getThrowHook();

    public void setThrowHook(jsdIExecutionHook var1);

    public jsdICallHook getTopLevelHook();

    public void setTopLevelHook(jsdICallHook var1);

    public jsdICallHook getFunctionHook();

    public void setFunctionHook(jsdICallHook var1);

    public long getFlags();

    public void setFlags(long var1);

    public long getImplementationMajor();

    public long getImplementationMinor();

    public String getImplementationString();

    public boolean getInitAtStartup();

    public void setInitAtStartup(boolean var1);

    public boolean getIsOn();

    public void on();

    public void off();

    public long getPauseDepth();

    public long pause();

    public long unPause();

    public void gC();

    public void clearProfileData();

    public void insertFilter(jsdIFilter var1, jsdIFilter var2);

    public void appendFilter(jsdIFilter var1);

    public void removeFilter(jsdIFilter var1);

    public void swapFilters(jsdIFilter var1, jsdIFilter var2);

    public void enumerateFilters(jsdIFilterEnumerator var1);

    public void refreshFilters();

    public void clearFilters();

    public void enumerateContexts(jsdIContextEnumerator var1);

    public void enumerateScripts(jsdIScriptEnumerator var1);

    public void clearAllBreakpoints();

    public jsdIValue wrapValue();

    public long enterNestedEventLoop(jsdINestCallback var1);

    public long exitNestedEventLoop();
}

