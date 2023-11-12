/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsISidebarExternal
extends nsISupports {
    public static final String NS_ISIDEBAREXTERNAL_IID = "{4350fb73-9305-41df-a669-11d26222d420}";

    public void addSearchProvider(String var1);

    public long isSearchProviderInstalled(String var1);
}

