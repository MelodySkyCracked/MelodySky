/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMHTMLElement;
import org.mozilla.interfaces.nsIDOMHTMLFormElement;

public interface nsIDOMHTMLLegendElement
extends nsIDOMHTMLElement {
    public static final String NS_IDOMHTMLLEGENDELEMENT_IID = "{a6cf9098-15b3-11d2-932e-00805f8add32}";

    public nsIDOMHTMLFormElement getForm();

    public String getAccessKey();

    public void setAccessKey(String var1);

    public String getAlign();

    public void setAlign(String var1);
}

