/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsICookie
extends nsISupports {
    public static final String NS_ICOOKIE_IID = "{e9fcb9a4-d376-458f-b720-e65e7df593bc}";
    public static final int STATUS_UNKNOWN = 0;
    public static final int STATUS_ACCEPTED = 1;
    public static final int STATUS_DOWNGRADED = 2;
    public static final int STATUS_FLAGGED = 3;
    public static final int STATUS_REJECTED = 4;
    public static final int POLICY_UNKNOWN = 0;
    public static final int POLICY_NONE = 1;
    public static final int POLICY_NO_CONSENT = 2;
    public static final int POLICY_IMPLICIT_CONSENT = 3;
    public static final int POLICY_EXPLICIT_CONSENT = 4;
    public static final int POLICY_NO_II = 5;

    public String getName();

    public String getValue();

    public boolean getIsDomain();

    public String getHost();

    public String getPath();

    public boolean getIsSecure();

    public double getExpires();

    public int getStatus();

    public int getPolicy();
}

