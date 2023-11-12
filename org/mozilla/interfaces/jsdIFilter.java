/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface jsdIFilter
extends nsISupports {
    public static final String JSDIFILTER_IID = "{05593438-1b83-4517-864f-3cea3d37a266}";
    public static final long FLAG_RESERVED_MASK = 255L;
    public static final long FLAG_ENABLED = 1L;
    public static final long FLAG_PASS = 2L;

    public long getFlags();

    public void setFlags(long var1);

    public nsISupports getGlobalObject();

    public void setGlobalObject(nsISupports var1);

    public String getUrlPattern();

    public void setUrlPattern(String var1);

    public long getStartLine();

    public void setStartLine(long var1);

    public long getEndLine();

    public void setEndLine(long var1);
}

