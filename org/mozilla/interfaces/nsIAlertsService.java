/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIObserver;
import org.mozilla.interfaces.nsISupports;

public interface nsIAlertsService
extends nsISupports {
    public static final String NS_IALERTSSERVICE_IID = "{647248fd-f925-4e30-93dd-cde26d7e3a90}";

    public void showAlertNotification(String var1, String var2, String var3, boolean var4, String var5, nsIObserver var6);
}

