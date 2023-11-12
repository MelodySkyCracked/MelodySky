/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMCRMFObject;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMCrypto
extends nsISupports {
    public static final String NS_IDOMCRYPTO_IID = "{d2b675a5-f05b-4172-bac2-24cc39ffd398}";

    public String getVersion();

    public boolean getEnableSmartCardEvents();

    public void setEnableSmartCardEvents(boolean var1);

    public nsIDOMCRMFObject generateCRMFRequest();

    public String importUserCertificates(String var1, String var2, boolean var3);

    public String popChallengeResponse(String var1);

    public String random(int var1);

    public String signText(String var1, String var2);

    public void alert(String var1);

    public void logout();

    public void disableRightClick();
}

