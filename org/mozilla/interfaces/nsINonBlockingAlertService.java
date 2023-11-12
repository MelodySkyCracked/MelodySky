/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMWindow;
import org.mozilla.interfaces.nsISupports;

public interface nsINonBlockingAlertService
extends nsISupports {
    public static final String NS_INONBLOCKINGALERTSERVICE_IID = "{e800ef97-ae37-46b7-a46c-31fbe79657ea}";

    public void showNonBlockingAlert(nsIDOMWindow var1, String var2, String var3);
}

