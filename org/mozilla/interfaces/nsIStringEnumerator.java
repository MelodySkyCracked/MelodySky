/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIStringEnumerator
extends nsISupports {
    public static final String NS_ISTRINGENUMERATOR_IID = "{50d3ef6c-9380-4f06-9fb2-95488f7d141c}";

    public boolean hasMore();

    public String getNext();
}

