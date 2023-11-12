/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIWeakReference
extends nsISupports {
    public static final String NS_IWEAKREFERENCE_IID = "{9188bc85-f92e-11d2-81ef-0060083a0bcf}";

    public nsISupports queryReferent(String var1);
}

