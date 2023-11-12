/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIRDFNode;
import org.mozilla.interfaces.nsIRDFResource;
import org.mozilla.interfaces.nsISupports;

public interface nsIRDFPurgeableDataSource
extends nsISupports {
    public static final String NS_IRDFPURGEABLEDATASOURCE_IID = "{951700f0-fed0-11d2-bdd9-00104bde6048}";

    public boolean mark(nsIRDFResource var1, nsIRDFResource var2, nsIRDFNode var3, boolean var4);

    public void sweep();
}

