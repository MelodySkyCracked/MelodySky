/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMStorageItem;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMStorage
extends nsISupports {
    public static final String NS_IDOMSTORAGE_IID = "{95cc1383-3b62-4b89-aaef-1004a513ef47}";

    public long getLength();

    public String key(long var1);

    public nsIDOMStorageItem getItem(String var1);

    public void setItem(String var1, String var2);

    public void removeItem(String var1);
}

