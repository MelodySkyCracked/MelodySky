/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIFactory
extends nsISupports {
    public static final String NS_IFACTORY_IID = "{00000001-0000-0000-c000-000000000046}";

    public nsISupports createInstance(nsISupports var1, String var2);

    public void lockFactory(boolean var1);
}

