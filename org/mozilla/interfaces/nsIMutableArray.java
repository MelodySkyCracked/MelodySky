/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIArray;
import org.mozilla.interfaces.nsISupports;

public interface nsIMutableArray
extends nsIArray {
    public static final String NS_IMUTABLEARRAY_IID = "{af059da0-c85b-40ec-af07-ae4bfdc192cc}";

    public void appendElement(nsISupports var1, boolean var2);

    public void removeElementAt(long var1);

    public void insertElementAt(nsISupports var1, long var2, boolean var4);

    public void replaceElementAt(nsISupports var1, long var2, boolean var4);

    public void clear();
}

