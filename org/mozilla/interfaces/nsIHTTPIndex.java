/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIRDFDataSource;
import org.mozilla.interfaces.nsISupports;

public interface nsIHTTPIndex
extends nsISupports {
    public static final String NS_IHTTPINDEX_IID = "{6f2bdbd0-58c3-11d3-be36-00104bde6048}";

    public String getBaseURL();

    public nsIRDFDataSource getDataSource();

    public String getEncoding();

    public void setEncoding(String var1);
}

