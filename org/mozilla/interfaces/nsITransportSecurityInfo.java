/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsITransportSecurityInfo
extends nsISupports {
    public static final String NS_ITRANSPORTSECURITYINFO_IID = "{68e21b66-1dd2-11b2-aa67-e2b87175e792}";

    public long getSecurityState();

    public String getShortSecurityDescription();
}

