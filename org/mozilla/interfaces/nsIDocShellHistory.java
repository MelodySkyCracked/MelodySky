/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISHEntry;
import org.mozilla.interfaces.nsISupports;

public interface nsIDocShellHistory
extends nsISupports {
    public static final String NS_IDOCSHELLHISTORY_IID = "{89caa9f0-8b1c-47fb-b0d3-f0aef0bff749}";

    public nsISHEntry getChildSHEntry(int var1);

    public void addChildSHEntry(nsISHEntry var1, nsISHEntry var2, int var3);

    public boolean getUseGlobalHistory();

    public void setUseGlobalHistory(boolean var1);
}

