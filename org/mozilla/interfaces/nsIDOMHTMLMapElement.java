/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMHTMLCollection;
import org.mozilla.interfaces.nsIDOMHTMLElement;

public interface nsIDOMHTMLMapElement
extends nsIDOMHTMLElement {
    public static final String NS_IDOMHTMLMAPELEMENT_IID = "{a6cf90af-15b3-11d2-932e-00805f8add32}";

    public nsIDOMHTMLCollection getAreas();

    public String getName();

    public void setName(String var1);
}

