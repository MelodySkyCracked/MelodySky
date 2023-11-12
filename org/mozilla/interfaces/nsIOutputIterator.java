/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIOutputIterator
extends nsISupports {
    public static final String NS_IOUTPUTITERATOR_IID = "{7330650e-1dd2-11b2-a0c2-9ff86ee97bed}";

    public void putElement(nsISupports var1);

    public void stepForward();
}

