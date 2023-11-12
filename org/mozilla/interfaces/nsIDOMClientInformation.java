/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIDOMClientInformation
extends nsISupports {
    public static final String NS_IDOMCLIENTINFORMATION_IID = "{abbb51a4-be75-4d7f-bd4c-373fd7b52f85}";

    public void registerContentHandler(String var1, String var2, String var3);

    public void registerProtocolHandler(String var1, String var2, String var3);
}

