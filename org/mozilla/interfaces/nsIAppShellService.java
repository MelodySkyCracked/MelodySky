/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIAppShell;
import org.mozilla.interfaces.nsIDOMWindowInternal;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;
import org.mozilla.interfaces.nsIXULWindow;

public interface nsIAppShellService
extends nsISupports {
    public static final String NS_IAPPSHELLSERVICE_IID = "{93a28ba2-7e22-11d9-9b6f-000a95d535fa}";
    public static final int SIZE_TO_CONTENT = -1;

    public nsIXULWindow createTopLevelWindow(nsIXULWindow var1, nsIURI var2, long var3, int var5, int var6, nsIAppShell var7);

    public void destroyHiddenWindow();

    public nsIXULWindow getHiddenWindow();

    public nsIDOMWindowInternal getHiddenDOMWindow();

    public void registerTopLevelWindow(nsIXULWindow var1);

    public void unregisterTopLevelWindow(nsIXULWindow var1);

    public void topLevelWindowIsModal(nsIXULWindow var1, boolean var2);
}

