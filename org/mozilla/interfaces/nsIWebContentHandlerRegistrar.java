/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMWindow;
import org.mozilla.interfaces.nsISupports;

public interface nsIWebContentHandlerRegistrar
extends nsISupports {
    public static final String NS_IWEBCONTENTHANDLERREGISTRAR_IID = "{e6a75410-c93e-42bf-84ca-a5c3ec34a2f1}";

    public void registerContentHandler(String var1, String var2, String var3, nsIDOMWindow var4);

    public void registerProtocolHandler(String var1, String var2, String var3, nsIDOMWindow var4);
}

