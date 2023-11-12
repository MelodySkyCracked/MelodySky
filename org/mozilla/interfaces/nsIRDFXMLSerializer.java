/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIAtom;
import org.mozilla.interfaces.nsIRDFDataSource;
import org.mozilla.interfaces.nsISupports;

public interface nsIRDFXMLSerializer
extends nsISupports {
    public static final String NS_IRDFXMLSERIALIZER_IID = "{8ae1fbf8-1dd2-11b2-bd21-d728069cca92}";

    public void init(nsIRDFDataSource var1);

    public void addNameSpace(nsIAtom var1, String var2);
}

