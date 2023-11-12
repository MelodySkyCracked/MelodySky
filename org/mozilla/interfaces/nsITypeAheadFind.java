/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsIDOMWindow;
import org.mozilla.interfaces.nsIDocShell;
import org.mozilla.interfaces.nsISupports;

public interface nsITypeAheadFind
extends nsISupports {
    public static final String NS_ITYPEAHEADFIND_IID = "{376da416-e6b3-4bac-98f3-6aa408742751}";
    public static final int FIND_FOUND = 0;
    public static final int FIND_NOTFOUND = 1;
    public static final int FIND_WRAPPED = 2;

    public void init(nsIDocShell var1);

    public int find(String var1, boolean var2);

    public int findNext();

    public int findPrevious();

    public void setDocShell(nsIDocShell var1);

    public String getSearchString();

    public boolean getFocusLinks();

    public void setFocusLinks(boolean var1);

    public boolean getCaseSensitive();

    public void setCaseSensitive(boolean var1);

    public nsIDOMElement getFoundLink();

    public nsIDOMWindow getCurrentWindow();
}

