/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsIDOMNode;

public interface nsIDOMXPathNamespace
extends nsIDOMNode {
    public static final String NS_IDOMXPATHNAMESPACE_IID = "{75506f87-b504-11d5-a7f2-ca108ab8b6fc}";
    public static final int XPATH_NAMESPACE_NODE = 13;

    public nsIDOMElement getOwnerElement();
}

