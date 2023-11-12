/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIRDFResource;
import org.mozilla.interfaces.nsISupports;

public interface nsIRDFInMemoryDataSource
extends nsISupports {
    public static final String NS_IRDFINMEMORYDATASOURCE_IID = "{17c4e0aa-1dd2-11b2-8029-bf6f668de500}";

    public void ensureFastContainment(nsIRDFResource var1);
}

