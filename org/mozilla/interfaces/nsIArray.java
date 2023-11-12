/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISimpleEnumerator;
import org.mozilla.interfaces.nsISupports;

public interface nsIArray
extends nsISupports {
    public static final String NS_IARRAY_IID = "{114744d9-c369-456e-b55a-52fe52880d2d}";

    public long getLength();

    public nsISupports queryElementAt(long var1, String var3);

    public long indexOf(long var1, nsISupports var3);

    public nsISimpleEnumerator enumerate();
}

