/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIBinaryOutputStream;
import org.mozilla.interfaces.nsISupports;

public interface nsIObjectOutputStream
extends nsIBinaryOutputStream {
    public static final String NS_IOBJECTOUTPUTSTREAM_IID = "{92c898ac-5fde-4b99-87b3-5d486422094b}";

    public void writeObject(nsISupports var1, boolean var2);

    public void writeSingleRefObject(nsISupports var1);

    public void writeCompoundObject(nsISupports var1, String var2, boolean var3);

    public void writeID(String var1);
}

