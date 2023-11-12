/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMDocument;
import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsISupports;

public interface nsIXMLContentBuilder
extends nsISupports {
    public static final String NS_IXMLCONTENTBUILDER_IID = "{e9c4cd4f-cd41-43d0-bf3b-48abb9cde90f}";

    public void clear(nsIDOMElement var1);

    public void setDocument(nsIDOMDocument var1);

    public void setElementNamespace(String var1);

    public void beginElement(String var1);

    public void endElement();

    public void attrib(String var1, String var2);

    public void textNode(String var1);

    public nsIDOMElement getRoot();

    public nsIDOMElement getCurrent();
}

