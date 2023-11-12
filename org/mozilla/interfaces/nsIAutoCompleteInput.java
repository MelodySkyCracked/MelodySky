/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIAutoCompleteController;
import org.mozilla.interfaces.nsIAutoCompletePopup;
import org.mozilla.interfaces.nsISupports;

public interface nsIAutoCompleteInput
extends nsISupports {
    public static final String NS_IAUTOCOMPLETEINPUT_IID = "{e312267f-8f57-43e8-a904-ff9b5d3f5aef}";

    public nsIAutoCompletePopup getPopup();

    public nsIAutoCompleteController getController();

    public boolean getPopupOpen();

    public void setPopupOpen(boolean var1);

    public boolean getDisableAutoComplete();

    public void setDisableAutoComplete(boolean var1);

    public boolean getCompleteDefaultIndex();

    public void setCompleteDefaultIndex(boolean var1);

    public boolean getCompleteSelectedIndex();

    public void setCompleteSelectedIndex(boolean var1);

    public boolean getForceComplete();

    public void setForceComplete(boolean var1);

    public long getMinResultsForPopup();

    public void setMinResultsForPopup(long var1);

    public long getMaxRows();

    public void setMaxRows(long var1);

    public long getShowCommentColumn();

    public void setShowCommentColumn(long var1);

    public long getTimeout();

    public void setTimeout(long var1);

    public String getSearchParam();

    public void setSearchParam(String var1);

    public long getSearchCount();

    public String getSearchAt(long var1);

    public String getTextValue();

    public void setTextValue(String var1);

    public int getSelectionStart();

    public int getSelectionEnd();

    public void selectTextRange(int var1, int var2);

    public void onSearchComplete();

    public boolean onTextEntered();

    public boolean onTextReverted();
}

