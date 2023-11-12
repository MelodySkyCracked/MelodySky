/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMWindow;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIUpdate;

public interface nsIUpdatePrompt
extends nsISupports {
    public static final String NS_IUPDATEPROMPT_IID = "{22b00276-ec23-4034-a764-395da539b4be}";

    public void checkForUpdates();

    public void showUpdateAvailable(nsIUpdate var1);

    public void showUpdateDownloaded(nsIUpdate var1);

    public void showUpdateInstalled(nsIUpdate var1);

    public void showUpdateError(nsIUpdate var1);

    public void showUpdateHistory(nsIDOMWindow var1);
}

