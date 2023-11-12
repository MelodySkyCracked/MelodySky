/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIRDFDataSource;
import org.mozilla.interfaces.nsIStreamListener;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;

public interface nsIRDFXMLParser
extends nsISupports {
    public static final String NS_IRDFXMLPARSER_IID = "{1831dd2e-1dd2-11b2-bdb3-86b7b50b70b5}";

    public nsIStreamListener parseAsync(nsIRDFDataSource var1, nsIURI var2);

    public void parseString(nsIRDFDataSource var1, nsIURI var2, String var3);
}

