/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsICollection;
import org.mozilla.interfaces.nsISupports;

public interface nsISupportsArray
extends nsICollection {
    public static final String NS_ISUPPORTSARRAY_IID = "{791eafa0-b9e6-11d1-8031-006008159b5a}";

    public int getIndexOf(nsISupports var1);

    public int getIndexOfStartingAt(nsISupports var1, long var2);

    public int getLastIndexOf(nsISupports var1);

    public void deleteLastElement(nsISupports var1);

    public void deleteElementAt(long var1);

    public void compact();

    public nsISupportsArray _clone();
}

