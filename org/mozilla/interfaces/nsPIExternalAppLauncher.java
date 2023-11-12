/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIFile;
import org.mozilla.interfaces.nsISupports;

public interface nsPIExternalAppLauncher
extends nsISupports {
    public static final String NS_PIEXTERNALAPPLAUNCHER_IID = "{d0b5d7d3-9565-403d-9fb5-e5089c4567c6}";

    public void deleteTemporaryFileOnExit(nsIFile var1);
}

