/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIArray;
import org.mozilla.interfaces.nsISimpleEnumerator;
import org.mozilla.interfaces.nsISupports;

public interface mozIStorageDataSet
extends nsISupports {
    public static final String MOZISTORAGEDATASET_IID = "{57826606-3c8a-4243-9f2f-cb3fe6e91148}";

    public nsIArray getDataRows();

    public nsISimpleEnumerator getRowEnumerator();
}

