/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMDocument;
import org.mozilla.interfaces.nsIInputStream;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMParser
extends nsISupports {
    public static final String NS_IDOMPARSER_IID = "{4f45513e-55e5-411c-a844-e899057026c1}";

    public nsIDOMDocument parseFromString(String var1, String var2);

    public nsIDOMDocument parseFromBuffer(byte[] var1, long var2, String var4);

    public nsIDOMDocument parseFromStream(nsIInputStream var1, String var2, int var3, String var4);
}

