/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIEventTarget
extends nsISupports {
    public static final String NS_IEVENTTARGET_IID = "{ea99ad5b-cc67-4efb-97c9-2ef620a59f2a}";

    public boolean isOnCurrentThread();
}

