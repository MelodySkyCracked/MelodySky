/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIAutoCompleteResult
extends nsISupports {
    public static final String NS_IAUTOCOMPLETERESULT_IID = "{eb43e1dc-2060-4d8e-aebf-3efec4e21cf8}";
    public static final int RESULT_IGNORED = 1;
    public static final int RESULT_FAILURE = 2;
    public static final int RESULT_NOMATCH = 3;
    public static final int RESULT_SUCCESS = 4;

    public String getSearchString();

    public int getSearchResult();

    public int getDefaultIndex();

    public String getErrorDescription();

    public long getMatchCount();

    public String getValueAt(int var1);

    public String getCommentAt(int var1);

    public String getStyleAt(int var1);

    public void removeValueAt(int var1, boolean var2);
}

