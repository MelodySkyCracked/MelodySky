/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;

public interface nsIChromeRegistry
extends nsISupports {
    public static final String NS_ICHROMEREGISTRY_IID = "{68389281-f6d0-4533-841d-344a2018140c}";
    public static final int NONE = 0;
    public static final int PARTIAL = 1;
    public static final int FULL = 2;

    public nsIURI convertChromeURL(nsIURI var1);

    public void checkForNewChrome();
}

