/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIFontPackageHandler;
import org.mozilla.interfaces.nsISupports;

public interface nsIFontPackageService
extends nsISupports {
    public static final String NS_IFONTPACKAGESERVICE_IID = "{6712fdd2-f978-11d4-a144-005004832142}";

    public void setHandler(nsIFontPackageHandler var1);

    public void fontPackageHandled(boolean var1, boolean var2, String var3);
}

