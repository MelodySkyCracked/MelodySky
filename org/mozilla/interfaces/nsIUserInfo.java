/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIUserInfo
extends nsISupports {
    public static final String NS_IUSERINFO_IID = "{6c1034f0-1dd2-11b2-aa14-e6657ed7bb0b}";

    public String getFullname();

    public String getEmailAddress();

    public String getUsername();

    public String getDomain();
}

