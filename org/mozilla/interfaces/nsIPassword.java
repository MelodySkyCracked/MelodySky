/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIPassword
extends nsISupports {
    public static final String NS_IPASSWORD_IID = "{cf39c2b0-1e4b-11d5-a549-0010a401eb10}";

    public String getHost();

    public String getUser();

    public String getPassword();
}

