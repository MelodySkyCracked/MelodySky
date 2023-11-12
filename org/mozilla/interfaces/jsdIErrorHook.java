/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.jsdIValue;
import org.mozilla.interfaces.nsISupports;

public interface jsdIErrorHook
extends nsISupports {
    public static final String JSDIERRORHOOK_IID = "{b7dd3c1c-1dd1-11b2-83eb-8a857d199e0f}";
    public static final long REPORT_ERROR = 0L;
    public static final long REPORT_WARNING = 1L;
    public static final long REPORT_EXCEPTION = 2L;
    public static final long REPORT_STRICT = 4L;

    public boolean onError(String var1, String var2, long var3, long var5, long var7, long var9, jsdIValue var11);
}

