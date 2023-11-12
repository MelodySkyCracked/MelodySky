/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIActiveXSecurityPolicy
extends nsISupports {
    public static final String NS_IACTIVEXSECURITYPOLICY_IID = "{0a3928d2-76c8-4c25-86a9-9c005ad832f4}";
    public static final long HOSTING_FLAGS_HOST_NOTHING = 0L;
    public static final long HOSTING_FLAGS_HOST_SAFE_OBJECTS = 1L;
    public static final long HOSTING_FLAGS_HOST_ALL_OBJECTS = 2L;
    public static final long HOSTING_FLAGS_DOWNLOAD_CONTROLS = 4L;
    public static final long HOSTING_FLAGS_SCRIPT_SAFE_OBJECTS = 8L;
    public static final long HOSTING_FLAGS_SCRIPT_ALL_OBJECTS = 16L;

    public long getHostingFlags(String var1);
}

