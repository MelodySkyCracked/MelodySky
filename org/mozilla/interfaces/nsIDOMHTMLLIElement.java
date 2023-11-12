/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMHTMLElement;

public interface nsIDOMHTMLLIElement
extends nsIDOMHTMLElement {
    public static final String NS_IDOMHTMLLIELEMENT_IID = "{a6cf909e-15b3-11d2-932e-00805f8add32}";

    public String getType();

    public void setType(String var1);

    public int getValue();

    public void setValue(int var1);
}

