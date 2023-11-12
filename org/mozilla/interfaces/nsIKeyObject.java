/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIKeyObject
extends nsISupports {
    public static final String NS_IKEYOBJECT_IID = "{4b31f4ed-9424-4710-b946-79b7e33cf3a8}";
    public static final short SYM_KEY = 1;
    public static final short PRIVATE_KEY = 2;
    public static final short PUBLIC_KEY = 3;
    public static final short RC4 = 1;
    public static final short AES_CBC = 2;

    public short getType();
}

