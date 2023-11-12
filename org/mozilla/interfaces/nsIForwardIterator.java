/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIForwardIterator
extends nsISupports {
    public static final String NS_IFORWARDITERATOR_IID = "{8da01646-1dd2-11b2-98a7-c7009045be7e}";

    public nsISupports getElement();

    public void putElement(nsISupports var1);

    public void stepForward();

    public boolean isEqualTo(nsISupports var1);

    public nsISupports _clone();
}

