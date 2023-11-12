/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMStorage;
import org.mozilla.interfaces.nsIDOMStorageList;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMStorageWindow
extends nsISupports {
    public static final String NS_IDOMSTORAGEWINDOW_IID = "{55e9c181-2476-47cf-97f8-efdaaf7b6f7a}";

    public nsIDOMStorage getSessionStorage();

    public nsIDOMStorageList getGlobalStorage();
}

