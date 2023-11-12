/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIASN1Object;
import org.mozilla.interfaces.nsIArray;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIX509CertValidity;

public interface nsIX509Cert
extends nsISupports {
    public static final String NS_IX509CERT_IID = "{f0980f60-ee3d-11d4-998b-00b0d02354a0}";
    public static final long UNKNOWN_CERT = 0L;
    public static final long CA_CERT = 1L;
    public static final long USER_CERT = 2L;
    public static final long EMAIL_CERT = 4L;
    public static final long SERVER_CERT = 8L;
    public static final long VERIFIED_OK = 0L;
    public static final long NOT_VERIFIED_UNKNOWN = 1L;
    public static final long CERT_REVOKED = 2L;
    public static final long CERT_EXPIRED = 4L;
    public static final long CERT_NOT_TRUSTED = 8L;
    public static final long ISSUER_NOT_TRUSTED = 16L;
    public static final long ISSUER_UNKNOWN = 32L;
    public static final long INVALID_CA = 64L;
    public static final long USAGE_NOT_ALLOWED = 128L;
    public static final long CERT_USAGE_SSLClient = 0L;
    public static final long CERT_USAGE_SSLServer = 1L;
    public static final long CERT_USAGE_SSLServerWithStepUp = 2L;
    public static final long CERT_USAGE_SSLCA = 3L;
    public static final long CERT_USAGE_EmailSigner = 4L;
    public static final long CERT_USAGE_EmailRecipient = 5L;
    public static final long CERT_USAGE_ObjectSigner = 6L;
    public static final long CERT_USAGE_UserCertImport = 7L;
    public static final long CERT_USAGE_VerifyCA = 8L;
    public static final long CERT_USAGE_ProtectedObjectSigner = 9L;
    public static final long CERT_USAGE_StatusResponder = 10L;
    public static final long CERT_USAGE_AnyCA = 11L;

    public String getNickname();

    public String getEmailAddress();

    public String[] getEmailAddresses(long[] var1);

    public boolean containsEmailAddress(String var1);

    public String getSubjectName();

    public String getCommonName();

    public String getOrganization();

    public String getOrganizationalUnit();

    public String getSha1Fingerprint();

    public String getMd5Fingerprint();

    public String getTokenName();

    public String getIssuerName();

    public String getSerialNumber();

    public String getIssuerCommonName();

    public String getIssuerOrganization();

    public String getIssuerOrganizationUnit();

    public nsIX509Cert getIssuer();

    public nsIX509CertValidity getValidity();

    public String getDbKey();

    public String getWindowTitle();

    public nsIArray getChain();

    public void getUsagesArray(boolean var1, long[] var2, long[] var3, String[][] var4);

    public void getUsagesString(boolean var1, long[] var2, String[] var3);

    public long verifyForUsage(long var1);

    public nsIASN1Object getASN1Structure();

    public byte[] getRawDER(long[] var1);

    public boolean _equals(nsIX509Cert var1);
}

