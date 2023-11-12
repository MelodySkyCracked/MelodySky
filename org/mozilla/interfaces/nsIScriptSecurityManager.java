/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIURI;
import org.mozilla.interfaces.nsIXPCSecurityManager;

public interface nsIScriptSecurityManager
extends nsIXPCSecurityManager {
    public static final String NS_ISCRIPTSECURITYMANAGER_IID = "{f4d74511-2b2d-4a14-a3e4-a392ac5ac3ff}";
    public static final long STANDARD = 0L;
    public static final long DISALLOW_FROM_MAIL = 1L;
    public static final long ALLOW_CHROME = 2L;
    public static final long DISALLOW_SCRIPT_OR_DATA = 4L;
    public static final long DISALLOW_SCRIPT = 8L;

    public void checkLoadURI(nsIURI var1, nsIURI var2, long var3);

    public void checkLoadURIStr(String var1, String var2, long var3);

    public boolean isCapabilityEnabled(String var1);

    public void enableCapability(String var1);

    public void revertCapability(String var1);

    public void disableCapability(String var1);

    public void setCanEnableCapability(String var1, String var2, short var3);

    public void checkSameOriginURI(nsIURI var1, nsIURI var2);
}

