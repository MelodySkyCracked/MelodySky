/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIChromeRegistry;
import org.mozilla.interfaces.nsIURI;

public interface nsIXULChromeRegistry
extends nsIChromeRegistry {
    public static final String NS_IXULCHROMEREGISTRY_IID = "{3e51f40b-b4b0-4e60-ac45-6c63477ebe41}";

    public void reloadChrome();

    public String getSelectedLocale(String var1);

    public void refreshSkins();

    public boolean allowScriptsForPackage(nsIURI var1);
}

