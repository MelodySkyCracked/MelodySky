/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIEnumerator;
import org.mozilla.interfaces.nsIPK11Token;
import org.mozilla.interfaces.nsISupports;

public interface nsIPK11TokenDB
extends nsISupports {
    public static final String NS_IPK11TOKENDB_IID = "{4ee28c82-1dd2-11b2-aabf-bb4017abe395}";

    public nsIPK11Token getInternalKeyToken();

    public nsIPK11Token findTokenByName(String var1);

    public nsIEnumerator listTokens();
}

