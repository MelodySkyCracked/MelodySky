/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;

public interface nsIPopupWindowManager
extends nsISupports {
    public static final String NS_IPOPUPWINDOWMANAGER_IID = "{3210a6aa-b464-4f57-9335-b22815567cf1}";
    public static final long ALLOW_POPUP = 1L;
    public static final long DENY_POPUP = 2L;
    public static final long ALLOW_POPUP_WITH_PREJUDICE = 3L;

    public long testPermission(nsIURI var1);
}

