/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMAttr;
import org.mozilla.interfaces.nsIDOMCDATASection;
import org.mozilla.interfaces.nsIDOMComment;
import org.mozilla.interfaces.nsIDOMDOMImplementation;
import org.mozilla.interfaces.nsIDOMDocumentFragment;
import org.mozilla.interfaces.nsIDOMDocumentType;
import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsIDOMEntityReference;
import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsIDOMNodeList;
import org.mozilla.interfaces.nsIDOMProcessingInstruction;
import org.mozilla.interfaces.nsIDOMText;

public interface nsIDOMDocument
extends nsIDOMNode {
    public static final String NS_IDOMDOCUMENT_IID = "{a6cf9075-15b3-11d2-932e-00805f8add32}";

    public nsIDOMDocumentType getDoctype();

    public nsIDOMDOMImplementation getImplementation();

    public nsIDOMElement getDocumentElement();

    public nsIDOMElement createElement(String var1);

    public nsIDOMDocumentFragment createDocumentFragment();

    public nsIDOMText createTextNode(String var1);

    public nsIDOMComment createComment(String var1);

    public nsIDOMCDATASection createCDATASection(String var1);

    public nsIDOMProcessingInstruction createProcessingInstruction(String var1, String var2);

    public nsIDOMAttr createAttribute(String var1);

    public nsIDOMEntityReference createEntityReference(String var1);

    public nsIDOMNodeList getElementsByTagName(String var1);

    public nsIDOMNode importNode(nsIDOMNode var1, boolean var2);

    public nsIDOMElement createElementNS(String var1, String var2);

    public nsIDOMAttr createAttributeNS(String var1, String var2);

    public nsIDOMNodeList getElementsByTagNameNS(String var1, String var2);

    public nsIDOMElement getElementById(String var1);
}

