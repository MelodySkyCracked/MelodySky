/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIXPIProgressDialog;

public interface nsIXPInstallManagerUI
extends nsISupports {
    public static final String NS_IXPINSTALLMANAGERUI_IID = "{087f52a4-8fd8-40ab-ae52-c3e161810141}";
    public static final short INSTALL_DOWNLOADING = 5;
    public static final short INSTALL_INSTALLING = 6;
    public static final short INSTALL_FINISHED = 7;
    public static final short DOWNLOAD_TYPE_INSTALL = 1;

    public nsIXPIProgressDialog getXpiProgress();

    public boolean getHasActiveXPIOperations();
}

