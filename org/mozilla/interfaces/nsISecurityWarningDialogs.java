/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIInterfaceRequestor;
import org.mozilla.interfaces.nsISupports;

public interface nsISecurityWarningDialogs
extends nsISupports {
    public static final String NS_ISECURITYWARNINGDIALOGS_IID = "{1c399d06-1dd2-11b2-bc58-c87cbcacdb78}";

    public boolean confirmEnteringSecure(nsIInterfaceRequestor var1);

    public boolean confirmEnteringWeak(nsIInterfaceRequestor var1);

    public boolean confirmLeavingSecure(nsIInterfaceRequestor var1);

    public boolean confirmMixedMode(nsIInterfaceRequestor var1);

    public boolean confirmPostToInsecure(nsIInterfaceRequestor var1);

    public boolean confirmPostToInsecureFromSecure(nsIInterfaceRequestor var1);
}

