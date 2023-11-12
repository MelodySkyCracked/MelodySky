/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsICancelable
extends nsISupports {
    public static final String NS_ICANCELABLE_IID = "{d94ac0a0-bb18-46b8-844e-84159064b0bd}";

    public void cancel(long var1);
}

