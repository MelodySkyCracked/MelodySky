/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMDocument;
import org.mozilla.interfaces.nsIDOMDocumentType;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMDOMImplementation
extends nsISupports {
    public static final String NS_IDOMDOMIMPLEMENTATION_IID = "{a6cf9074-15b3-11d2-932e-00805f8add32}";

    public boolean hasFeature(String var1, String var2);

    public nsIDOMDocumentType createDocumentType(String var1, String var2, String var3);

    public nsIDOMDocument createDocument(String var1, String var2, nsIDOMDocumentType var3);
}

