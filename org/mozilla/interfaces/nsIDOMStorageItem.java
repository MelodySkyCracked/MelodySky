/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIDOMStorageItem
extends nsISupports {
    public static final String NS_IDOMSTORAGEITEM_IID = "{0cc37c78-4c5f-48e1-adfc-7480b8fe9dc4}";

    public boolean getSecure();

    public void setSecure(boolean var1);

    public String getValue();

    public void setValue(String var1);
}

