/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupportsPrimitive;

public interface nsISupportsID
extends nsISupportsPrimitive {
    public static final String NS_ISUPPORTSID_IID = "{d18290a0-4a1c-11d3-9890-006008962422}";

    public String getData();

    public void setData(String var1);

    public String toString();
}

