/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDirectoryEnumerator;
import org.mozilla.interfaces.nsIFile;
import org.mozilla.interfaces.nsISupports;

public interface nsIInstallLocation
extends nsISupports {
    public static final String NS_IINSTALLLOCATION_IID = "{d3d4dada-c6eb-11d9-a68f-001124787b2e}";
    public static final long PRIORITY_APP_PROFILE = 0L;
    public static final long PRIORITY_APP_SYSTEM_USER = 10L;
    public static final long PRIORITY_XRE_SYSTEM_USER = 100L;
    public static final long PRIORITY_APP_SYSTEM_GLOBAL = 1000L;
    public static final long PRIORITY_XRE_SYSTEM_GLOBAL = 10000L;

    public String getName();

    public nsIDirectoryEnumerator getItemLocations();

    public nsIFile getLocation();

    public boolean getRestricted();

    public boolean getCanAccess();

    public int getPriority();

    public nsIFile getItemLocation(String var1);

    public String getIDForLocation(nsIFile var1);

    public nsIFile getItemFile(String var1, String var2);

    public boolean itemIsManagedIndependently(String var1);
}

