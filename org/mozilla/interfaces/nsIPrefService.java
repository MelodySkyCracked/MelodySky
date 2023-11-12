/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIFile;
import org.mozilla.interfaces.nsIPrefBranch;
import org.mozilla.interfaces.nsISupports;

public interface nsIPrefService
extends nsISupports {
    public static final String NS_IPREFSERVICE_IID = "{decb9cc7-c08f-4ea5-be91-a8fc637ce2d2}";

    public void readUserPrefs(nsIFile var1);

    public void resetPrefs();

    public void resetUserPrefs();

    public void savePrefFile(nsIFile var1);

    public nsIPrefBranch getBranch(String var1);

    public nsIPrefBranch getDefaultBranch(String var1);
}

