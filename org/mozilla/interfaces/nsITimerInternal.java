/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsITimerInternal
extends nsISupports {
    public static final String NS_ITIMERINTERNAL_IID = "{6dd8f185-ceb8-4878-8e38-2d13edc2d079}";

    public boolean getIdle();

    public void setIdle(boolean var1);
}

