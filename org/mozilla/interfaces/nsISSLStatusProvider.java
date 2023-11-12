/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsISSLStatusProvider
extends nsISupports {
    public static final String NS_ISSLSTATUSPROVIDER_IID = "{8de811f0-1dd2-11b2-8bf1-e9aa324984b2}";

    public nsISupports getSSLStatus();
}

