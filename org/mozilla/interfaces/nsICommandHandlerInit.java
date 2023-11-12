/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMWindow;
import org.mozilla.interfaces.nsISupports;

public interface nsICommandHandlerInit
extends nsISupports {
    public static final String NS_ICOMMANDHANDLERINIT_IID = "{731c6c50-67d6-11d4-9529-0020183bf181}";

    public nsIDOMWindow getWindow();

    public void setWindow(nsIDOMWindow var1);
}

