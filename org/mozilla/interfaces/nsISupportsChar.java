/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupportsPrimitive;

public interface nsISupportsChar
extends nsISupportsPrimitive {
    public static final String NS_ISUPPORTSCHAR_IID = "{e2b05e40-4a1c-11d3-9890-006008962422}";

    public char getData();

    public void setData(char var1);

    public String toString();
}

