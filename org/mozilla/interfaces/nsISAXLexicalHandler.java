/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsISAXLexicalHandler
extends nsISupports {
    public static final String NS_ISAXLEXICALHANDLER_IID = "{23c26a56-adff-440c-8caf-95c2dc2e399b}";

    public void comment(String var1);

    public void startDTD(String var1, String var2, String var3);

    public void endDTD();

    public void startCDATA();

    public void endCDATA();

    public void startEntity(String var1);

    public void endEntity(String var1);
}

