/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsICacheDeviceInfo;
import org.mozilla.interfaces.nsICacheEntryInfo;
import org.mozilla.interfaces.nsISupports;

public interface nsICacheVisitor
extends nsISupports {
    public static final String NS_ICACHEVISITOR_IID = "{f8c08c4b-d778-49d1-a59b-866fdc500d95}";

    public boolean visitDevice(String var1, nsICacheDeviceInfo var2);

    public boolean visitEntry(String var1, nsICacheEntryInfo var2);
}

