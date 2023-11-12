/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIExpatSink;

public interface nsIExtendedExpatSink
extends nsIExpatSink {
    public static final String NS_IEXTENDEDEXPATSINK_IID = "{0c2dc80f-7aa4-467a-9454-b89dba0e0779}";

    public void handleStartDTD(String var1, String var2, String var3);

    public void handleStartNamespaceDecl(String var1, String var2);

    public void handleEndNamespaceDecl(String var1);

    public void handleNotationDecl(String var1, String var2, String var3);

    public void handleUnparsedEntityDecl(String var1, String var2, String var3, String var4);
}

