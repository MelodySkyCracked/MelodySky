/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIX509Cert;

public interface nsICMSSecureMessage
extends nsISupports {
    public static final String NS_ICMSSECUREMESSAGE_IID = "{14b4394a-1dd2-11b2-b4fd-ba4a194fe97e}";

    public String getCertByPrefID(String var1);

    public nsIX509Cert decodeCert(String var1);

    public String sendMessage(String var1, String var2);

    public String receiveMessage(String var1);
}

