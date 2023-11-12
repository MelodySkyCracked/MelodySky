/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIFile;
import org.mozilla.interfaces.nsISupports;

public interface nsIXULAppInstall
extends nsISupports {
    public static final String NS_IXULAPPINSTALL_IID = "{800ace15-6b38-48c4-b057-7928378f6cd2}";

    public void installApplication(nsIFile var1, nsIFile var2, String var3);
}

