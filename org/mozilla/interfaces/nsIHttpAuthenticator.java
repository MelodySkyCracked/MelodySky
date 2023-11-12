/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIHttpChannel;
import org.mozilla.interfaces.nsISupports;

public interface nsIHttpAuthenticator
extends nsISupports {
    public static final String NS_IHTTPAUTHENTICATOR_IID = "{0f331436-8bc8-4c68-a124-d0253a19d06f}";
    public static final long REQUEST_BASED = 1L;
    public static final long CONNECTION_BASED = 2L;
    public static final long REUSABLE_CREDENTIALS = 4L;
    public static final long REUSABLE_CHALLENGE = 8L;
    public static final long IDENTITY_IGNORED = 1024L;
    public static final long IDENTITY_INCLUDES_DOMAIN = 2048L;

    public void challengeReceived(nsIHttpChannel var1, String var2, boolean var3, nsISupports[] var4, nsISupports[] var5, boolean[] var6);

    public String generateCredentials(nsIHttpChannel var1, String var2, boolean var3, String var4, String var5, String var6, nsISupports[] var7, nsISupports[] var8);

    public long getAuthFlags();
}

