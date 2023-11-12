/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIXULAppInfo
extends nsISupports {
    public static final String NS_IXULAPPINFO_IID = "{a61ede2a-ef09-11d9-a5ce-001124787b2e}";

    public String getVendor();

    public String getName();

    public String getID();

    public String getVersion();

    public String getAppBuildID();

    public String getPlatformVersion();

    public String getPlatformBuildID();
}

