/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIArray;
import org.mozilla.interfaces.nsIInterfaceRequestor;
import org.mozilla.interfaces.nsILocalFile;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIX509Cert;

public interface nsIX509CertDB
extends nsISupports {
    public static final String NS_IX509CERTDB_IID = "{da48b3c0-1284-11d5-ac67-000064657374}";
    public static final long UNTRUSTED = 0L;
    public static final long TRUSTED_SSL = 1L;
    public static final long TRUSTED_EMAIL = 2L;
    public static final long TRUSTED_OBJSIGN = 4L;

    public nsIX509Cert findCertByNickname(nsISupports var1, String var2);

    public nsIX509Cert findCertByDBKey(String var1, nsISupports var2);

    public void findCertNicknames(nsISupports var1, long var2, long[] var4, String[][] var5);

    public nsIX509Cert findEmailEncryptionCert(String var1);

    public nsIX509Cert findEmailSigningCert(String var1);

    public nsIX509Cert findCertByEmailAddress(nsISupports var1, String var2);

    public void importCertificates(byte[] var1, long var2, long var4, nsIInterfaceRequestor var6);

    public void importEmailCertificate(byte[] var1, long var2, nsIInterfaceRequestor var4);

    public void importServerCertificate(byte[] var1, long var2, nsIInterfaceRequestor var4);

    public void importUserCertificate(byte[] var1, long var2, nsIInterfaceRequestor var4);

    public void deleteCertificate(nsIX509Cert var1);

    public void setCertTrust(nsIX509Cert var1, long var2, long var4);

    public boolean isCertTrusted(nsIX509Cert var1, long var2, long var4);

    public void importCertsFromFile(nsISupports var1, nsILocalFile var2, long var3);

    public void importPKCS12File(nsISupports var1, nsILocalFile var2);

    public void exportPKCS12File(nsISupports var1, nsILocalFile var2, long var3, nsIX509Cert[] var5);

    public nsIArray getOCSPResponders();

    public boolean getIsOcspOn();

    public nsIX509Cert constructX509FromBase64(String var1);
}

