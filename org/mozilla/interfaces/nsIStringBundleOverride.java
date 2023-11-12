/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISimpleEnumerator;
import org.mozilla.interfaces.nsISupports;

public interface nsIStringBundleOverride
extends nsISupports {
    public static final String NS_ISTRINGBUNDLEOVERRIDE_IID = "{965eb278-5678-456b-82a7-20a0c86a803c}";

    public String getStringFromName(String var1, String var2);

    public nsISimpleEnumerator enumerateKeysInBundle(String var1);
}

