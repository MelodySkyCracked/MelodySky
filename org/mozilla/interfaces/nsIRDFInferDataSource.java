/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIRDFDataSource;

public interface nsIRDFInferDataSource
extends nsIRDFDataSource {
    public static final String NS_IRDFINFERDATASOURCE_IID = "{2b04860f-4017-40f6-8a57-784a1e35077a}";

    public nsIRDFDataSource getBaseDataSource();

    public void setBaseDataSource(nsIRDFDataSource var1);
}

