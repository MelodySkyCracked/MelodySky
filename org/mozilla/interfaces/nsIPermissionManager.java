/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISimpleEnumerator;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;

public interface nsIPermissionManager
extends nsISupports {
    public static final String NS_IPERMISSIONMANAGER_IID = "{4f6b5e00-0c36-11d5-a535-0010a401eb10}";
    public static final long UNKNOWN_ACTION = 0L;
    public static final long ALLOW_ACTION = 1L;
    public static final long DENY_ACTION = 2L;

    public void add(nsIURI var1, String var2, long var3);

    public void remove(String var1, String var2);

    public void removeAll();

    public long testPermission(nsIURI var1, String var2);

    public nsISimpleEnumerator getEnumerator();
}

