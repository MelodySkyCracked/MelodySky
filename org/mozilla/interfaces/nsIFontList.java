/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISimpleEnumerator;
import org.mozilla.interfaces.nsISupports;

public interface nsIFontList
extends nsISupports {
    public static final String NS_IFONTLIST_IID = "{702909c6-1dd2-11b2-b833-8a740f643539}";

    public nsISimpleEnumerator availableFonts(String var1, String var2);
}

