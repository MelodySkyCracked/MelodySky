/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISimpleEnumerator;
import org.mozilla.interfaces.nsISupports;

public interface nsIPasswordManager
extends nsISupports {
    public static final String NS_IPASSWORDMANAGER_IID = "{173562f0-2173-11d5-a54c-0010a401eb10}";

    public void addUser(String var1, String var2, String var3);

    public void removeUser(String var1, String var2);

    public void addReject(String var1);

    public void removeReject(String var1);

    public nsISimpleEnumerator getEnumerator();

    public nsISimpleEnumerator getRejectEnumerator();
}

