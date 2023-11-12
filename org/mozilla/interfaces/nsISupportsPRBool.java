/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupportsPrimitive;

public interface nsISupportsPRBool
extends nsISupportsPrimitive {
    public static final String NS_ISUPPORTSPRBOOL_IID = "{ddc3b490-4a1c-11d3-9890-006008962422}";

    public boolean getData();

    public void setData(boolean var1);

    public String toString();
}

