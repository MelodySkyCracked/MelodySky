/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupportsPrimitive;

public interface nsISupportsPRInt64
extends nsISupportsPrimitive {
    public static final String NS_ISUPPORTSPRINT64_IID = "{e3cb0ff0-4a1c-11d3-9890-006008962422}";

    public long getData();

    public void setData(long var1);

    public String toString();
}

