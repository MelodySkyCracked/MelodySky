/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIInterfaceInfoToIDL
extends nsISupports {
    public static final String NS_IINTERFACEINFOTOIDL_IID = "{b01eb40c-026b-49c9-af55-25e8c9d034c8}";

    public String generateIDL(String var1, boolean var2, boolean var3);

    public String[] getReferencedInterfaceNames(String var1, long[] var2);
}

