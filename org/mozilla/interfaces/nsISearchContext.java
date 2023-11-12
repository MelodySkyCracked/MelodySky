/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMWindowInternal;
import org.mozilla.interfaces.nsISupports;

public interface nsISearchContext
extends nsISupports {
    public static final String NS_ISEARCHCONTEXT_IID = "{31aba0f0-2d93-11d3-8069-00600811a9c3}";

    public String getSearchString();

    public void setSearchString(String var1);

    public String getReplaceString();

    public void setReplaceString(String var1);

    public boolean getSearchBackwards();

    public void setSearchBackwards(boolean var1);

    public boolean getCaseSensitive();

    public void setCaseSensitive(boolean var1);

    public boolean getWrapSearch();

    public void setWrapSearch(boolean var1);

    public nsIDOMWindowInternal getTargetWindow();

    public nsIDOMWindowInternal getFindDialog();

    public void setFindDialog(nsIDOMWindowInternal var1);

    public nsIDOMWindowInternal getReplaceDialog();

    public void setReplaceDialog(nsIDOMWindowInternal var1);
}

