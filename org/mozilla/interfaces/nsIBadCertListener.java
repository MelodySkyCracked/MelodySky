/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIInterfaceRequestor;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIX509Cert;

public interface nsIBadCertListener
extends nsISupports {
    public static final String NS_IBADCERTLISTENER_IID = "{86960956-edb0-11d4-998b-00b0d02354a0}";
    public static final short UNINIT_ADD_FLAG = -1;
    public static final short ADD_TRUSTED_FOR_SESSION = 1;
    public static final short ADD_TRUSTED_PERMANENTLY = 2;

    public boolean confirmUnknownIssuer(nsIInterfaceRequestor var1, nsIX509Cert var2, short[] var3);

    public boolean confirmMismatchDomain(nsIInterfaceRequestor var1, String var2, nsIX509Cert var3);

    public boolean confirmCertExpired(nsIInterfaceRequestor var1, nsIX509Cert var2);

    public void notifyCrlNextupdate(nsIInterfaceRequestor var1, String var2, nsIX509Cert var3);
}

