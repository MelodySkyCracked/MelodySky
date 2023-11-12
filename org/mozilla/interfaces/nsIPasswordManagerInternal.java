/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIFile;
import org.mozilla.interfaces.nsISupports;

public interface nsIPasswordManagerInternal
extends nsISupports {
    public static final String NS_IPASSWORDMANAGERINTERNAL_IID = "{dc2ff152-85cb-474e-b4c2-86a3d48cf4d0}";

    public void findPasswordEntry(String var1, String var2, String var3, String[] var4, String[] var5, String[] var6);

    public void addUserFull(String var1, String var2, String var3, String var4, String var5);

    public void readPasswords(nsIFile var1);
}

