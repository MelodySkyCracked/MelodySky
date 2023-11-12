/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIInputStream;
import org.mozilla.interfaces.nsIStreamListener;
import org.mozilla.interfaces.nsISupports;

public interface nsIStreamConverterService
extends nsISupports {
    public static final String NS_ISTREAMCONVERTERSERVICE_IID = "{e086e1e2-40ff-4193-8b8c-bd548babe70d}";

    public nsIInputStream convert(nsIInputStream var1, String var2, String var3, nsISupports var4);

    public nsIStreamListener asyncConvertData(String var1, String var2, nsIStreamListener var3, nsISupports var4);
}

