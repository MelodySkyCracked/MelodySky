/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMStorage;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMStorageList
extends nsISupports {
    public static final String NS_IDOMSTORAGELIST_IID = "{f2166929-91b6-4372-8d5f-c366f47a5f54}";

    public nsIDOMStorage namedItem(String var1);
}

