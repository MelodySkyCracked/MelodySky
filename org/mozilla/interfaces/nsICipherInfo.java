/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsICipherInfo
extends nsISupports {
    public static final String NS_ICIPHERINFO_IID = "{028e2b2a-1f0b-43a4-a1a7-365d2d7f35d0}";

    public String getLongName();

    public boolean getIsSSL2();

    public boolean getIsFIPS();

    public boolean getIsExportable();

    public boolean getNonStandard();

    public String getSymCipherName();

    public String getAuthAlgorithmName();

    public String getKeaTypeName();

    public String getMacAlgorithmName();

    public int getEffectiveKeyBits();
}

