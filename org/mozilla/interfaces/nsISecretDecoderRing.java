/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsISecretDecoderRing
extends nsISupports {
    public static final String NS_ISECRETDECODERRING_IID = "{0ec80360-075c-11d4-9fd4-00c04f1b83d8}";

    public String encryptString(String var1);

    public String decryptString(String var1);

    public void changePassword();

    public void logout();

    public void logoutAndTeardown();
}

