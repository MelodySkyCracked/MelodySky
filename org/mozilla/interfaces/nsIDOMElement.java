/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMAttr;
import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsIDOMNodeList;

public interface nsIDOMElement
extends nsIDOMNode {
    public static final String NS_IDOMELEMENT_IID = "{a6cf9078-15b3-11d2-932e-00805f8add32}";

    public String getTagName();

    public String getAttribute(String var1);

    public void setAttribute(String var1, String var2);

    public void removeAttribute(String var1);

    public nsIDOMAttr getAttributeNode(String var1);

    public nsIDOMAttr setAttributeNode(nsIDOMAttr var1);

    public nsIDOMAttr removeAttributeNode(nsIDOMAttr var1);

    public nsIDOMNodeList getElementsByTagName(String var1);

    public String getAttributeNS(String var1, String var2);

    public void setAttributeNS(String var1, String var2, String var3);

    public void removeAttributeNS(String var1, String var2);

    public nsIDOMAttr getAttributeNodeNS(String var1, String var2);

    public nsIDOMAttr setAttributeNodeNS(nsIDOMAttr var1);

    public nsIDOMNodeList getElementsByTagNameNS(String var1, String var2);

    public boolean hasAttribute(String var1);

    public boolean hasAttributeNS(String var1, String var2);
}

