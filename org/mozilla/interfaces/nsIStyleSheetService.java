/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;

public interface nsIStyleSheetService
extends nsISupports {
    public static final String NS_ISTYLESHEETSERVICE_IID = "{41d979dc-ea03-4235-86ff-1e3c090c5630}";
    public static final long AGENT_SHEET = 0L;
    public static final long USER_SHEET = 1L;

    public void loadAndRegisterSheet(nsIURI var1, long var2);

    public boolean sheetRegistered(nsIURI var1, long var2);

    public void unregisterSheet(nsIURI var1, long var2);
}

