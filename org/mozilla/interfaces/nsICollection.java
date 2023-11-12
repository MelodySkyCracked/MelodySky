/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIEnumerator;
import org.mozilla.interfaces.nsISerializable;
import org.mozilla.interfaces.nsISupports;

public interface nsICollection
extends nsISerializable {
    public static final String NS_ICOLLECTION_IID = "{83b6019c-cbc4-11d2-8cca-0060b0fc14a3}";

    public long count();

    public nsISupports getElementAt(long var1);

    public nsISupports queryElementAt(long var1, String var3);

    public void setElementAt(long var1, nsISupports var3);

    public void appendElement(nsISupports var1);

    public void removeElement(nsISupports var1);

    public nsIEnumerator enumerate();

    public void clear();
}

