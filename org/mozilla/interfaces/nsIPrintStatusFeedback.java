/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIPrintStatusFeedback
extends nsISupports {
    public static final String NS_IPRINTSTATUSFEEDBACK_IID = "{19855dff-3248-4902-b196-93ee4c477880}";

    public void showStatusString(String var1);

    public void startMeteors();

    public void stopMeteors();

    public void showProgress(int var1);

    public void closeWindow();
}

