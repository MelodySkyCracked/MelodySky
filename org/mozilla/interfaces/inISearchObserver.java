/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.inISearchProcess;
import org.mozilla.interfaces.nsISupports;

public interface inISearchObserver
extends nsISupports {
    public static final String INISEARCHOBSERVER_IID = "{46226d9b-e398-4106-8d9b-225d4d0589f5}";
    public static final short SUCCESS = 1;
    public static final short INTERRUPTED = 2;
    public static final short ERROR = 3;

    public void onSearchStart(inISearchProcess var1);

    public void onSearchResult(inISearchProcess var1);

    public void onSearchEnd(inISearchProcess var1, short var2);

    public void onSearchError(inISearchProcess var1, String var2);
}

