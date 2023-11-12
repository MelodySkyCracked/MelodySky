/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIWebNavigation;

public interface nsIWebNavigationInfo
extends nsISupports {
    public static final String NS_IWEBNAVIGATIONINFO_IID = "{62a93afb-93a1-465c-84c8-0432264229de}";
    public static final long UNSUPPORTED = 0L;
    public static final long IMAGE = 1L;
    public static final long PLUGIN = 2L;
    public static final long OTHER = 32768L;

    public long isTypeSupported(String var1, nsIWebNavigation var2);
}

