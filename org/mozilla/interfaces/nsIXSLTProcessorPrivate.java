/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIXSLTProcessorPrivate
extends nsISupports {
    public static final String NS_IXSLTPROCESSORPRIVATE_IID = "{b8d727f7-67f4-4dc1-a318-ec0c87280816}";
    public static final long DISABLE_ALL_LOADS = 1L;

    public long getFlags();

    public void setFlags(long var1);
}

