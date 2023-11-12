/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMHTMLElement;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMHTMLCanvasElement
extends nsIDOMHTMLElement {
    public static final String NS_IDOMHTMLCANVASELEMENT_IID = "{0583a2ea-ab19-40e1-8be4-5e9b2f275560}";

    public int getWidth();

    public void setWidth(int var1);

    public int getHeight();

    public void setHeight(int var1);

    public nsISupports getContext(String var1);

    public String toDataURL();
}

