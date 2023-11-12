/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIAutoCompletePopup;
import org.mozilla.interfaces.nsIDocShell;
import org.mozilla.interfaces.nsISupports;

public interface nsIFormFillController
extends nsISupports {
    public static final String NS_IFORMFILLCONTROLLER_IID = "{872f07f3-ed11-47c6-b7cf-246db53379fb}";

    public void attachToBrowser(nsIDocShell var1, nsIAutoCompletePopup var2);

    public void detachFromBrowser(nsIDocShell var1);
}

