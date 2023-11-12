/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIAutoCompleteInput;
import org.mozilla.interfaces.nsISupports;

public interface nsIAutoCompleteController
extends nsISupports {
    public static final String NS_IAUTOCOMPLETECONTROLLER_IID = "{cf2aca0c-4fb1-42e4-8a54-23e832cb2a98}";
    public static final int STATUS_NONE = 1;
    public static final int STATUS_SEARCHING = 2;
    public static final int STATUS_COMPLETE_NO_MATCH = 3;
    public static final int STATUS_COMPLETE_MATCH = 4;
    public static final int KEY_UP = 1;
    public static final int KEY_DOWN = 2;
    public static final int KEY_LEFT = 3;
    public static final int KEY_RIGHT = 4;
    public static final int KEY_PAGE_UP = 5;
    public static final int KEY_PAGE_DOWN = 6;
    public static final int KEY_HOME = 7;
    public static final int KEY_END = 8;

    public nsIAutoCompleteInput getInput();

    public void setInput(nsIAutoCompleteInput var1);

    public int getSearchStatus();

    public long getMatchCount();

    public void startSearch(String var1);

    public void handleText(boolean var1);

    public boolean handleEnter();

    public boolean handleEscape();

    public void handleStartComposition();

    public void handleEndComposition();

    public void handleTab();

    public boolean handleKeyNavigation(int var1);

    public boolean handleDelete();

    public String getValueAt(int var1);

    public String getCommentAt(int var1);

    public String getStyleAt(int var1);

    public void setSearchString(String var1);
}

