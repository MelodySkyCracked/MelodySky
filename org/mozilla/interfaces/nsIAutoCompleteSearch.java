/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIAutoCompleteObserver;
import org.mozilla.interfaces.nsIAutoCompleteResult;
import org.mozilla.interfaces.nsISupports;

public interface nsIAutoCompleteSearch
extends nsISupports {
    public static final String NS_IAUTOCOMPLETESEARCH_IID = "{de8db85f-c1de-4d87-94ba-7844890f91fe}";

    public void startSearch(String var1, String var2, nsIAutoCompleteResult var3, nsIAutoCompleteObserver var4);

    public void stopSearch();
}

