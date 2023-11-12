/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMWindowInternal;
import org.mozilla.interfaces.nsISupports;

public interface nsIBrowserInstance
extends nsISupports {
    public static final String NS_IBROWSERINSTANCE_IID = "{8af0fa40-598d-11d3-806a-00600811a9c3}";

    public boolean startPageCycler();

    public boolean getCmdLineURLUsed();

    public void setCmdLineURLUsed(boolean var1);

    public void setWebShellWindow(nsIDOMWindowInternal var1);

    public void close();
}

