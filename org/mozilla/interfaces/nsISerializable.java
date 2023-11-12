/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIObjectInputStream;
import org.mozilla.interfaces.nsIObjectOutputStream;
import org.mozilla.interfaces.nsISupports;

public interface nsISerializable
extends nsISupports {
    public static final String NS_ISERIALIZABLE_IID = "{91cca981-c26d-44a8-bebe-d9ed4891503a}";

    public void read(nsIObjectInputStream var1);

    public void write(nsIObjectOutputStream var1);
}

