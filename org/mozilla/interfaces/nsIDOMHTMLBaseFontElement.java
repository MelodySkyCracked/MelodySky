/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMHTMLElement;

public interface nsIDOMHTMLBaseFontElement
extends nsIDOMHTMLElement {
    public static final String NS_IDOMHTMLBASEFONTELEMENT_IID = "{a6cf90a6-15b3-11d2-932e-00805f8add32}";

    public String getColor();

    public void setColor(String var1);

    public String getFace();

    public void setFace(String var1);

    public int getSize();

    public void setSize(int var1);
}

