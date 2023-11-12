/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMWindow;
import org.mozilla.interfaces.nsISupports;

public interface nsIJSConsoleService
extends nsISupports {
    public static final String NS_IJSCONSOLESERVICE_IID = "{1b86a0a6-1dd2-11b2-a85c-e3f42b4dcceb}";

    public void open(nsIDOMWindow var1);
}

