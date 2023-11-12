/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIBidirectionalIterator
extends nsISupports {
    public static final String NS_IBIDIRECTIONALITERATOR_IID = "{948defaa-1dd1-11b2-89f6-8ce81f5ebda9}";

    public nsISupports getElement();

    public void putElement(nsISupports var1);

    public void stepForward();

    public void stepBackward();

    public boolean isEqualTo(nsISupports var1);

    public nsISupports _clone();
}

