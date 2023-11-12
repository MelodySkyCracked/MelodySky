/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIKeyObject;
import org.mozilla.interfaces.nsISupports;

public interface nsIKeyObjectFactory
extends nsISupports {
    public static final String NS_IKEYOBJECTFACTORY_IID = "{264eb54d-e20d-49a0-890c-1a5986ea81c4}";

    public nsIKeyObject lookupKeyByName(String var1);

    public nsIKeyObject unwrapKey(short var1, byte[] var2, long var3);

    public nsIKeyObject keyFromString(short var1, String var2);
}

