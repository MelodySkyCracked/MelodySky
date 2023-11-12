/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIUpdateItem;

public interface nsIAddonUpdateCheckListener
extends nsISupports {
    public static final String NS_IADDONUPDATECHECKLISTENER_IID = "{c946119f-9e7c-41aa-a794-803148045350}";
    public static final long STATUS_NONE = 0L;
    public static final long STATUS_UPDATE = 1L;
    public static final long STATUS_VERSIONINFO = 2L;
    public static final long STATUS_DATA_FOUND = 3L;
    public static final long STATUS_FAILURE = 4L;
    public static final long STATUS_NO_UPDATE = 8L;
    public static final long STATUS_DISABLED = 16L;
    public static final long STATUS_APP_MANAGED = 32L;
    public static final long STATUS_READ_ONLY = 64L;
    public static final long STATUS_PENDING_OP = 128L;
    public static final long STATUS_NOT_MANAGED = 256L;
    public static final long STATUS_DISALLOWED = 496L;

    public void onUpdateStarted();

    public void onUpdateEnded();

    public void onAddonUpdateStarted(nsIUpdateItem var1);

    public void onAddonUpdateEnded(nsIUpdateItem var1, int var2);
}

