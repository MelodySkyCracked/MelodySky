/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIEnvironment
extends nsISupports {
    public static final String NS_IENVIRONMENT_IID = "{101d5941-d820-4e85-a266-9a3469940807}";

    public void set(String var1, String var2);

    public String get(String var1);

    public boolean exists(String var1);
}

