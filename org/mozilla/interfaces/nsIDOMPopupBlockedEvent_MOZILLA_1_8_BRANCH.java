/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMWindow;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMPopupBlockedEvent_MOZILLA_1_8_BRANCH
extends nsISupports {
    public static final String NS_IDOMPOPUPBLOCKEDEVENT_MOZILLA_1_8_BRANCH_IID = "{45d7d2b4-2d98-4cdb-850f-860bc45deeae}";

    public nsIDOMWindow getRequestingWindow();
}

