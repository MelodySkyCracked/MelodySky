/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIRDFDataSource;
import org.mozilla.interfaces.nsIRDFNode;
import org.mozilla.interfaces.nsIRDFResource;
import org.mozilla.interfaces.nsISupports;

public interface nsIRDFObserver
extends nsISupports {
    public static final String NS_IRDFOBSERVER_IID = "{3cc75360-484a-11d2-bc16-00805f912fe7}";

    public void onAssert(nsIRDFDataSource var1, nsIRDFResource var2, nsIRDFResource var3, nsIRDFNode var4);

    public void onUnassert(nsIRDFDataSource var1, nsIRDFResource var2, nsIRDFResource var3, nsIRDFNode var4);

    public void onChange(nsIRDFDataSource var1, nsIRDFResource var2, nsIRDFResource var3, nsIRDFNode var4, nsIRDFNode var5);

    public void onMove(nsIRDFDataSource var1, nsIRDFResource var2, nsIRDFResource var3, nsIRDFResource var4, nsIRDFNode var5);

    public void onBeginUpdateBatch(nsIRDFDataSource var1);

    public void onEndUpdateBatch(nsIRDFDataSource var1);
}

