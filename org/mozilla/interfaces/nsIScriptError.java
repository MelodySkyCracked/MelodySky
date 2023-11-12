/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIConsoleMessage;

public interface nsIScriptError
extends nsIConsoleMessage {
    public static final String NS_ISCRIPTERROR_IID = "{b0196fc7-1913-441a-882a-453c0d8b89b8}";
    public static final long errorFlag = 0L;
    public static final long warningFlag = 1L;
    public static final long exceptionFlag = 2L;
    public static final long strictFlag = 4L;

    public String getErrorMessage();

    public String getSourceName();

    public String getSourceLine();

    public long getLineNumber();

    public long getColumnNumber();

    public long getFlags();

    public String getCategory();

    public void init(String var1, String var2, String var3, long var4, long var6, long var8, String var10);

    public String toString();
}

