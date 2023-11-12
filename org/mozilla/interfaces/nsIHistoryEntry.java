/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;

public interface nsIHistoryEntry
extends nsISupports {
    public static final String NS_IHISTORYENTRY_IID = "{a41661d4-1417-11d5-9882-00c04fa02f40}";

    public nsIURI getURI();

    public String getTitle();

    public boolean getIsSubFrame();
}

