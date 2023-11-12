/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIInputStream;
import org.mozilla.interfaces.nsIStreamListener;
import org.mozilla.interfaces.nsISupports;

public interface nsIStreamConverter
extends nsIStreamListener {
    public static final String NS_ISTREAMCONVERTER_IID = "{0b6e2c69-5cf5-48b0-9dfd-c95950e2cc7b}";

    public nsIInputStream convert(nsIInputStream var1, String var2, String var3, nsISupports var4);

    public void asyncConvertData(String var1, String var2, nsIStreamListener var3, nsISupports var4);
}

