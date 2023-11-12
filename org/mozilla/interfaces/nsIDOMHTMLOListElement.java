/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMHTMLElement;

public interface nsIDOMHTMLOListElement
extends nsIDOMHTMLElement {
    public static final String NS_IDOMHTMLOLISTELEMENT_IID = "{a6cf909a-15b3-11d2-932e-00805f8add32}";

    public boolean getCompact();

    public void setCompact(boolean var1);

    public int getStart();

    public void setStart(int var1);

    public String getType();

    public void setType(String var1);
}

