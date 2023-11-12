/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIAutoCompleteInput;
import org.mozilla.interfaces.nsISupports;

public interface nsIAutoCompletePopup
extends nsISupports {
    public static final String NS_IAUTOCOMPLETEPOPUP_IID = "{65f6cd46-22ec-4329-bb3b-bcd1103f2204}";

    public nsIAutoCompleteInput getInput();

    public String getOverrideValue();

    public int getSelectedIndex();

    public void setSelectedIndex(int var1);

    public boolean getPopupOpen();

    public void openPopup(nsIAutoCompleteInput var1, int var2, int var3, int var4);

    public void closePopup();

    public void invalidate();

    public void selectBy(boolean var1, boolean var2);
}

