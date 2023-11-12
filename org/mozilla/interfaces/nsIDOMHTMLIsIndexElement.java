/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMHTMLElement;
import org.mozilla.interfaces.nsIDOMHTMLFormElement;

public interface nsIDOMHTMLIsIndexElement
extends nsIDOMHTMLElement {
    public static final String NS_IDOMHTMLISINDEXELEMENT_IID = "{a6cf908c-15b3-11d2-932e-00805f8add32}";

    public nsIDOMHTMLFormElement getForm();

    public String getPrompt();

    public void setPrompt(String var1);
}

