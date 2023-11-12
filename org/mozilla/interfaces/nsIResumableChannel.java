/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIResumableChannel
extends nsISupports {
    public static final String NS_IRESUMABLECHANNEL_IID = "{4ad136fa-83af-4a22-a76e-503642c0f4a8}";

    public void resumeAt(double var1, String var3);

    public String getEntityID();
}

