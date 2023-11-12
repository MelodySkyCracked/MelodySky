/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsISupportsArray;

public interface nsIFormatConverter
extends nsISupports {
    public static final String NS_IFORMATCONVERTER_IID = "{948a0023-e3a7-11d2-96cf-0060b0fb9956}";

    public nsISupportsArray getInputDataFlavors();

    public nsISupportsArray getOutputDataFlavors();

    public boolean canConvert(String var1, String var2);

    public void convert(String var1, nsISupports var2, long var3, String var5, nsISupports[] var6, long[] var7);
}

