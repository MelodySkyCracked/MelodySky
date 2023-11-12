/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupportsPrimitive;

public interface nsISupportsPRTime
extends nsISupportsPrimitive {
    public static final String NS_ISUPPORTSPRTIME_IID = "{e2563630-4a1c-11d3-9890-006008962422}";

    public double getData();

    public void setData(double var1);

    public String toString();
}

