/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIURI;
import org.mozilla.interfaces.nsIUTF8StringEnumerator;
import org.mozilla.interfaces.nsIXULChromeRegistry;

public interface nsIToolkitChromeRegistry
extends nsIXULChromeRegistry {
    public static final String NS_ITOOLKITCHROMEREGISTRY_IID = "{94490b3f-f094-418e-b1b9-73878d29bff3}";

    public void processContentsManifest(nsIURI var1, nsIURI var2, nsIURI var3, boolean var4, boolean var5);

    public void checkForOSAccessibility();

    public nsIUTF8StringEnumerator getLocalesForPackage(String var1);
}

