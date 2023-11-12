/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupportsPrimitive;

public interface nsISupportsFloat
extends nsISupportsPrimitive {
    public static final String NS_ISUPPORTSFLOAT_IID = "{abeaa390-4ac0-11d3-baea-00805f8a5dd7}";

    public float getData();

    public void setData(float var1);

    public String toString();
}

