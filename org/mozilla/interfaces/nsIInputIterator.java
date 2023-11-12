/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIInputIterator
extends nsISupports {
    public static final String NS_IINPUTITERATOR_IID = "{85585e12-1dd2-11b2-a930-f6929058269a}";

    public nsISupports getElement();

    public void stepForward();

    public boolean isEqualTo(nsISupports var1);

    public nsISupports _clone();
}

