/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIHistoryEntry;
import org.mozilla.interfaces.nsISHistoryListener;
import org.mozilla.interfaces.nsISimpleEnumerator;
import org.mozilla.interfaces.nsISupports;

public interface nsISHistory
extends nsISupports {
    public static final String NS_ISHISTORY_IID = "{7294fe9b-14d8-11d5-9882-00c04fa02f40}";

    public int getCount();

    public int getIndex();

    public int getMaxLength();

    public void setMaxLength(int var1);

    public nsIHistoryEntry getEntryAtIndex(int var1, boolean var2);

    public void purgeHistory(int var1);

    public void addSHistoryListener(nsISHistoryListener var1);

    public void removeSHistoryListener(nsISHistoryListener var1);

    public nsISimpleEnumerator getSHistoryEnumerator();
}

