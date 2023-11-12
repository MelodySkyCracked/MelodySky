/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsICipherInfo;
import org.mozilla.interfaces.nsISupports;

public interface nsICipherInfoService
extends nsISupports {
    public static final String NS_ICIPHERINFOSERVICE_IID = "{766d47cb-6d8c-4e71-b6b7-336917629a69}";

    public nsICipherInfo getCipherInfoByPrefString(String var1);
}

