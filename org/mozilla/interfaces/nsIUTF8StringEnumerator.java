/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIUTF8StringEnumerator
extends nsISupports {
    public static final String NS_IUTF8STRINGENUMERATOR_IID = "{9bdf1010-3695-4907-95ed-83d0410ec307}";

    public boolean hasMore();

    public String getNext();
}

