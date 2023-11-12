/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsISAXDTDHandler
extends nsISupports {
    public static final String NS_ISAXDTDHANDLER_IID = "{4d01f225-6cc5-11da-be43-001422106990}";

    public void notationDecl(String var1, String var2, String var3);

    public void unparsedEntityDecl(String var1, String var2, String var3, String var4);
}

