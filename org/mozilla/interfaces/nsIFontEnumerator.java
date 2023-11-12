/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIFontEnumerator
extends nsISupports {
    public static final String NS_IFONTENUMERATOR_IID = "{a6cf9114-15b3-11d2-932e-00805f8add32}";

    public String[] enumerateAllFonts(long[] var1);

    public String[] enumerateFonts(String var1, String var2, long[] var3);

    public boolean haveFontFor(String var1);

    public String getDefaultFont(String var1, String var2);

    public boolean updateFontList();
}

