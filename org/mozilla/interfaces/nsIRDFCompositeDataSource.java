/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIRDFDataSource;
import org.mozilla.interfaces.nsISimpleEnumerator;

public interface nsIRDFCompositeDataSource
extends nsIRDFDataSource {
    public static final String NS_IRDFCOMPOSITEDATASOURCE_IID = "{96343820-307c-11d2-bc15-00805f912fe7}";

    public boolean getAllowNegativeAssertions();

    public void setAllowNegativeAssertions(boolean var1);

    public boolean getCoalesceDuplicateArcs();

    public void setCoalesceDuplicateArcs(boolean var1);

    public void addDataSource(nsIRDFDataSource var1);

    public void removeDataSource(nsIRDFDataSource var1);

    public nsISimpleEnumerator getDataSources();
}

