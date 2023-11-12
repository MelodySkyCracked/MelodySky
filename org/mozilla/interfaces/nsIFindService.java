/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIFindService
extends nsISupports {
    public static final String NS_IFINDSERVICE_IID = "{5060b801-340e-11d5-be5b-b3e063ec6a3c}";

    public String getSearchString();

    public void setSearchString(String var1);

    public String getReplaceString();

    public void setReplaceString(String var1);

    public boolean getFindBackwards();

    public void setFindBackwards(boolean var1);

    public boolean getWrapFind();

    public void setWrapFind(boolean var1);

    public boolean getEntireWord();

    public void setEntireWord(boolean var1);

    public boolean getMatchCase();

    public void setMatchCase(boolean var1);
}

