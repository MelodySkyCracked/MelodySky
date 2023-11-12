/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsIDOMNode;

public interface nsIDOMAttr
extends nsIDOMNode {
    public static final String NS_IDOMATTR_IID = "{a6cf9070-15b3-11d2-932e-00805f8add32}";

    public String getName();

    public boolean getSpecified();

    public String getValue();

    public void setValue(String var1);

    public nsIDOMElement getOwnerElement();
}

