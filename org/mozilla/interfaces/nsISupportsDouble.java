/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupportsPrimitive;

public interface nsISupportsDouble
extends nsISupportsPrimitive {
    public static final String NS_ISUPPORTSDOUBLE_IID = "{b32523a0-4ac0-11d3-baea-00805f8a5dd7}";

    public double getData();

    public void setData(double var1);

    public String toString();
}

