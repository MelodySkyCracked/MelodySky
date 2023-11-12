/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIAutoCompleteResult;
import org.mozilla.interfaces.nsIAutoCompleteSearch;
import org.mozilla.interfaces.nsISupports;

public interface nsIAutoCompleteObserver
extends nsISupports {
    public static final String NS_IAUTOCOMPLETEOBSERVER_IID = "{18c36504-9a4c-4ac3-8494-bd05e00ae27f}";

    public void onSearchResult(nsIAutoCompleteSearch var1, nsIAutoCompleteResult var2);
}

