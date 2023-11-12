/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIEnumerator
extends nsISupports {
    public static final String NS_IENUMERATOR_IID = "{ad385286-cbc4-11d2-8cca-0060b0fc14a3}";

    public void first();

    public void next();

    public nsISupports currentItem();

    public void isDone();
}

