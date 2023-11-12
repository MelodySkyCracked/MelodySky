/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIWebBrowserFind
extends nsISupports {
    public static final String NS_IWEBBROWSERFIND_IID = "{2f977d44-5485-11d4-87e2-0010a4e75ef2}";

    public boolean findNext();

    public String getSearchString();

    public void setSearchString(String var1);

    public boolean getFindBackwards();

    public void setFindBackwards(boolean var1);

    public boolean getWrapFind();

    public void setWrapFind(boolean var1);

    public boolean getEntireWord();

    public void setEntireWord(boolean var1);

    public boolean getMatchCase();

    public void setMatchCase(boolean var1);

    public boolean getSearchFrames();

    public void setSearchFrames(boolean var1);
}

