/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMDocument;
import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsIDOMNodeList;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMDocumentXBL
extends nsISupports {
    public static final String NS_IDOMDOCUMENTXBL_IID = "{c7c0ae9b-a0ba-4f4e-9f2c-c18deb62ee8b}";

    public nsIDOMNodeList getAnonymousNodes(nsIDOMElement var1);

    public nsIDOMElement getAnonymousElementByAttribute(nsIDOMElement var1, String var2, String var3);

    public void addBinding(nsIDOMElement var1, String var2);

    public void removeBinding(nsIDOMElement var1, String var2);

    public nsIDOMElement getBindingParent(nsIDOMNode var1);

    public nsIDOMDocument loadBindingDocument(String var1);
}

