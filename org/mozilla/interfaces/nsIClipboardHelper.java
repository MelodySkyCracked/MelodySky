/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIClipboardHelper
extends nsISupports {
    public static final String NS_ICLIPBOARDHELPER_IID = "{44073a98-1dd2-11b2-8600-d0ae854dbe93}";

    public void copyStringToClipboard(String var1, int var2);

    public void copyString(String var1);
}

