/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIFile;
import org.mozilla.interfaces.nsISupports;

public interface nsIProfileStartup
extends nsISupports {
    public static final String NS_IPROFILESTARTUP_IID = "{048e5ca1-0eb7-4bb1-a9a2-a36f7d4e0e3c}";

    public nsIFile getDirectory();

    public void doStartup();
}

