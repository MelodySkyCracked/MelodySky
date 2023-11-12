/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsITimer;

public interface nsITimerCallback
extends nsISupports {
    public static final String NS_ITIMERCALLBACK_IID = "{a796816d-7d47-4348-9ab8-c7aeb3216a7d}";

    public void _notify(nsITimer var1);
}

