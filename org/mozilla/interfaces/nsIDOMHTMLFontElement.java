/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMHTMLElement;

public interface nsIDOMHTMLFontElement
extends nsIDOMHTMLElement {
    public static final String NS_IDOMHTMLFONTELEMENT_IID = "{a6cf90a7-15b3-11d2-932e-00805f8add32}";

    public String getColor();

    public void setColor(String var1);

    public String getFace();

    public void setFace(String var1);

    public String getSize();

    public void setSize(String var1);
}

