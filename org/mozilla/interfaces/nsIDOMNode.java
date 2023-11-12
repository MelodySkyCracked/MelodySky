/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMDocument;
import org.mozilla.interfaces.nsIDOMNamedNodeMap;
import org.mozilla.interfaces.nsIDOMNodeList;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMNode
extends nsISupports {
    public static final String NS_IDOMNODE_IID = "{a6cf907c-15b3-11d2-932e-00805f8add32}";
    public static final int ELEMENT_NODE = 1;
    public static final int ATTRIBUTE_NODE = 2;
    public static final int TEXT_NODE = 3;
    public static final int CDATA_SECTION_NODE = 4;
    public static final int ENTITY_REFERENCE_NODE = 5;
    public static final int ENTITY_NODE = 6;
    public static final int PROCESSING_INSTRUCTION_NODE = 7;
    public static final int COMMENT_NODE = 8;
    public static final int DOCUMENT_NODE = 9;
    public static final int DOCUMENT_TYPE_NODE = 10;
    public static final int DOCUMENT_FRAGMENT_NODE = 11;
    public static final int NOTATION_NODE = 12;

    public String getNodeName();

    public String getNodeValue();

    public void setNodeValue(String var1);

    public int getNodeType();

    public nsIDOMNode getParentNode();

    public nsIDOMNodeList getChildNodes();

    public nsIDOMNode getFirstChild();

    public nsIDOMNode getLastChild();

    public nsIDOMNode getPreviousSibling();

    public nsIDOMNode getNextSibling();

    public nsIDOMNamedNodeMap getAttributes();

    public nsIDOMDocument getOwnerDocument();

    public nsIDOMNode insertBefore(nsIDOMNode var1, nsIDOMNode var2);

    public nsIDOMNode replaceChild(nsIDOMNode var1, nsIDOMNode var2);

    public nsIDOMNode removeChild(nsIDOMNode var1);

    public nsIDOMNode appendChild(nsIDOMNode var1);

    public boolean hasChildNodes();

    public nsIDOMNode cloneNode(boolean var1);

    public void normalize();

    public boolean isSupported(String var1, String var2);

    public String getNamespaceURI();

    public String getPrefix();

    public void setPrefix(String var1);

    public String getLocalName();

    public boolean hasAttributes();
}

