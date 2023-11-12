/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMHTMLElement;

public interface nsIDOMHTMLBaseElement
extends nsIDOMHTMLElement {
    public static final String NS_IDOMHTMLBASEELEMENT_IID = "{a6cf908b-15b3-11d2-932e-00805f8add32}";

    public String getHref();

    public void setHref(String var1);

    public String getTarget();

    public void setTarget(String var1);
}

