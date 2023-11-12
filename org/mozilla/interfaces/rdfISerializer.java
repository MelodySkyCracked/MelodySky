/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIOutputStream;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.rdfIDataSource;

public interface rdfISerializer
extends nsISupports {
    public static final String RDFISERIALIZER_IID = "{f0edfcdd-8bca-4d32-9226-7421001396a4}";

    public void serialize(rdfIDataSource var1, nsIOutputStream var2);
}

