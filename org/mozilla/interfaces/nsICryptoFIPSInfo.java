/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsICryptoFIPSInfo
extends nsISupports {
    public static final String NS_ICRYPTOFIPSINFO_IID = "{99e81922-7318-4431-b3aa-78b3cb4119bb}";

    public boolean getIsFIPSModeActive();
}

