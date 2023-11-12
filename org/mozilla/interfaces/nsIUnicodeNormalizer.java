/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIUnicodeNormalizer
extends nsISupports {
    public static final String NS_IUNICODENORMALIZER_IID = "{b43a461f-1bcf-4329-820b-66e48c979e14}";

    public void normalizeUnicodeNFD(String var1, String[] var2);

    public void normalizeUnicodeNFC(String var1, String[] var2);

    public void normalizeUnicodeNFKD(String var1, String[] var2);

    public void normalizeUnicodeNFKC(String var1, String[] var2);
}

