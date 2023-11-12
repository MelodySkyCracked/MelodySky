/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIRandomAccessIterator
extends nsISupports {
    public static final String NS_IRANDOMACCESSITERATOR_IID = "{9bd6fdb0-1dd1-11b2-9101-d15375968230}";

    public nsISupports getElement();

    public nsISupports getElementAt(int var1);

    public void putElement(nsISupports var1);

    public void putElementAt(int var1, nsISupports var2);

    public void stepForward();

    public void stepForwardBy(int var1);

    public void stepBackward();

    public void stepBackwardBy(int var1);

    public boolean isEqualTo(nsISupports var1);

    public nsISupports _clone();
}

