/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIDOMPkcs11
extends nsISupports {
    public static final String NS_IDOMPKCS11_IID = "{9fd42950-25e7-11d4-8a7d-006008c844c3}";

    public int deletemodule(String var1);

    public int addmodule(String var1, String var2, int var3, int var4);
}

