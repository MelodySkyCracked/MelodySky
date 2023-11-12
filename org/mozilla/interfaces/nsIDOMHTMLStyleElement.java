/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMHTMLElement;

public interface nsIDOMHTMLStyleElement
extends nsIDOMHTMLElement {
    public static final String NS_IDOMHTMLSTYLEELEMENT_IID = "{a6cf908d-15b3-11d2-932e-00805f8add32}";

    public boolean getDisabled();

    public void setDisabled(boolean var1);

    public String getMedia();

    public void setMedia(String var1);

    public String getType();

    public void setType(String var1);
}

