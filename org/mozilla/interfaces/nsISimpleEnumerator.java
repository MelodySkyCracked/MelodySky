/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsISimpleEnumerator
extends nsISupports {
    public static final String NS_ISIMPLEENUMERATOR_IID = "{d1899240-f9d2-11d2-bdd6-000064657374}";

    public boolean hasMoreElements();

    public nsISupports getNext();
}

