/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIExpatSink
extends nsISupports {
    public static final String NS_IEXPATSINK_IID = "{1deea160-c661-11d5-84cc-0010a4e0c706}";

    public void handleStartElement(String var1, String[] var2, long var3, int var5, long var6);

    public void handleEndElement(String var1);

    public void handleComment(String var1);

    public void handleCDataSection(String var1, long var2);

    public void handleDoctypeDecl(String var1, String var2, String var3, String var4, nsISupports var5);

    public void handleCharacterData(String var1, long var2);

    public void handleProcessingInstruction(String var1, String var2);

    public void handleXMLDeclaration(String var1, String var2, int var3);

    public void reportError(String var1, String var2);
}

