/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;

public interface nsIStandardURL
extends nsISupports {
    public static final String NS_ISTANDARDURL_IID = "{8793370a-311f-11d4-9876-00c04fa0cf4a}";
    public static final long URLTYPE_STANDARD = 1L;
    public static final long URLTYPE_AUTHORITY = 2L;
    public static final long URLTYPE_NO_AUTHORITY = 3L;

    public void init(long var1, int var3, String var4, String var5, nsIURI var6);

    public boolean getMutable();

    public void setMutable(boolean var1);
}

