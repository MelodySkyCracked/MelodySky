/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.mozIStorageConnection;
import org.mozilla.interfaces.nsIFile;
import org.mozilla.interfaces.nsISupports;

public interface mozIStorageService
extends nsISupports {
    public static final String MOZISTORAGESERVICE_IID = "{a4a0cad9-e0da-4379-bee4-2feef3dddc7e}";

    public mozIStorageConnection openSpecialDatabase(String var1);

    public mozIStorageConnection openDatabase(nsIFile var1);
}

