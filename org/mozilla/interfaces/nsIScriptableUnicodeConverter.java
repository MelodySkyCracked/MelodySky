/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIInputStream;
import org.mozilla.interfaces.nsISupports;

public interface nsIScriptableUnicodeConverter
extends nsISupports {
    public static final String NS_ISCRIPTABLEUNICODECONVERTER_IID = "{1ea19c6c-c59f-4fd7-9fc7-151e946baca0}";

    public String convertFromUnicode(String var1);

    public String finish();

    public String convertToUnicode(String var1);

    public String convertFromByteArray(byte[] var1, long var2);

    public byte[] convertToByteArray(String var1, long[] var2);

    public nsIInputStream convertToInputStream(String var1);

    public String getCharset();

    public void setCharset(String var1);
}

