/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsINSSCertCache;
import org.mozilla.interfaces.nsITreeView;
import org.mozilla.interfaces.nsIX509Cert;

public interface nsICertTree
extends nsITreeView {
    public static final String NS_ICERTTREE_IID = "{4ea60761-31d6-491d-9e34-4b53a26c416c}";

    public void loadCerts(long var1);

    public void loadCertsFromCache(nsINSSCertCache var1, long var2);

    public nsIX509Cert getCert(long var1);

    public void removeCert(long var1);
}

