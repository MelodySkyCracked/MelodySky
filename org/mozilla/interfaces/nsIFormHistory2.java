/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIFormHistory2
extends nsISupports {
    public static final String NS_IFORMHISTORY2_IID = "{a61f0a62-ae0a-4382-b474-d259442ca80c}";

    public boolean getHasEntries();

    public void addEntry(String var1, String var2);

    public void removeEntry(String var1, String var2);

    public void removeEntriesForName(String var1);

    public void removeAllEntries();

    public boolean nameExists(String var1);

    public boolean entryExists(String var1, String var2);
}

