/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.mozIStorageValueArray;
import org.mozilla.interfaces.nsISupports;

public interface mozIStorageFunction
extends nsISupports {
    public static final String MOZISTORAGEFUNCTION_IID = "{898d4189-7012-4ae9-a2af-435491cfa114}";

    public void onFunctionCall(mozIStorageValueArray var1);
}

