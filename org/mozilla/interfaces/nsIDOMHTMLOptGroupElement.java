/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMHTMLElement;

public interface nsIDOMHTMLOptGroupElement
extends nsIDOMHTMLElement {
    public static final String NS_IDOMHTMLOPTGROUPELEMENT_IID = "{a6cf9091-15b3-11d2-932e-00805f8add32}";

    public boolean getDisabled();

    public void setDisabled(boolean var1);

    public String getLabel();

    public void setLabel(String var1);
}

