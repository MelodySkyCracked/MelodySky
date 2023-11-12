/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISOAPEncoding;
import org.mozilla.interfaces.nsISchemaCollection;
import org.mozilla.interfaces.nsISupports;

public interface nsISOAPEncodingRegistry
extends nsISupports {
    public static final String NS_ISOAPENCODINGREGISTRY_IID = "{9fe91a61-3048-40e3-99ef-e39ab946ae0b}";

    public nsISOAPEncoding getAssociatedEncoding(String var1, boolean var2);

    public nsISchemaCollection getSchemaCollection();

    public void setSchemaCollection(nsISchemaCollection var1);
}

