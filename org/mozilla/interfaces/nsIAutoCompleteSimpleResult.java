/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIAutoCompleteResult;

public interface nsIAutoCompleteSimpleResult
extends nsIAutoCompleteResult {
    public static final String NS_IAUTOCOMPLETESIMPLERESULT_IID = "{cc79f293-7114-4287-870b-d28aa61aa7df}";

    public void setSearchString(String var1);

    public void setErrorDescription(String var1);

    public void setDefaultIndex(int var1);

    public void setSearchResult(int var1);

    public void appendMatch(String var1, String var2);
}

