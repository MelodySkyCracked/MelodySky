/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsISupports;

public interface nsIPopupBoxObject
extends nsISupports {
    public static final String NS_IPOPUPBOXOBJECT_IID = "{33c60e14-5150-4876-9a96-2732557e6895}";

    public void showPopup(nsIDOMElement var1, nsIDOMElement var2, int var3, int var4, String var5, String var6, String var7);

    public void hidePopup();

    public boolean getAutoPosition();

    public void setAutoPosition(boolean var1);

    public void enableKeyboardNavigator(boolean var1);

    public void enableRollup(boolean var1);

    public void sizeTo(int var1, int var2);

    public void moveTo(int var1, int var2);
}

