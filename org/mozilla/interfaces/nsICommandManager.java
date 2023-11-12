/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsICommandParams;
import org.mozilla.interfaces.nsIDOMWindow;
import org.mozilla.interfaces.nsIObserver;
import org.mozilla.interfaces.nsISupports;

public interface nsICommandManager
extends nsISupports {
    public static final String NS_ICOMMANDMANAGER_IID = "{080d2001-f91e-11d4-a73c-f9242928207c}";

    public void addCommandObserver(nsIObserver var1, String var2);

    public void removeCommandObserver(nsIObserver var1, String var2);

    public boolean isCommandSupported(String var1, nsIDOMWindow var2);

    public boolean isCommandEnabled(String var1, nsIDOMWindow var2);

    public void getCommandState(String var1, nsIDOMWindow var2, nsICommandParams var3);

    public void doCommand(String var1, nsICommandParams var2, nsIDOMWindow var3);
}

