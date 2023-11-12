/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIMutableArray;
import org.mozilla.interfaces.nsISupports;

public interface nsIDialogParamBlock
extends nsISupports {
    public static final String NS_IDIALOGPARAMBLOCK_IID = "{f76c0901-437a-11d3-b7a0-e35db351b4bc}";

    public int getInt(int var1);

    public void setInt(int var1, int var2);

    public void setNumberStrings(int var1);

    public String getString(int var1);

    public void setString(int var1, String var2);

    public nsIMutableArray getObjects();

    public void setObjects(nsIMutableArray var1);
}

