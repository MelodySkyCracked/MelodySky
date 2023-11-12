/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIGlobalHistory
extends nsISupports {
    public static final String NS_IGLOBALHISTORY_IID = "{9491c383-e3c4-11d2-bdbe-0050040a9b44}";

    public void addPage(String var1);

    public boolean isVisited(String var1);
}

