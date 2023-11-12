/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsICRLInfo;
import org.mozilla.interfaces.nsIInterfaceRequestor;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIX509Cert;

public interface nsICertificateDialogs
extends nsISupports {
    public static final String NS_ICERTIFICATEDIALOGS_IID = "{a03ca940-09be-11d5-ac5d-000064657374}";

    public boolean confirmDownloadCACert(nsIInterfaceRequestor var1, nsIX509Cert var2, long[] var3);

    public void notifyCACertExists(nsIInterfaceRequestor var1);

    public boolean setPKCS12FilePassword(nsIInterfaceRequestor var1, String[] var2);

    public boolean getPKCS12FilePassword(nsIInterfaceRequestor var1, String[] var2);

    public void viewCert(nsIInterfaceRequestor var1, nsIX509Cert var2);

    public void crlImportStatusDialog(nsIInterfaceRequestor var1, nsICRLInfo var2);
}

