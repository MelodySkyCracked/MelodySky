/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMHTMLElement;

public interface nsIDOMHTMLMetaElement
extends nsIDOMHTMLElement {
    public static final String NS_IDOMHTMLMETAELEMENT_IID = "{a6cf908a-15b3-11d2-932e-00805f8add32}";

    public String getContent();

    public void setContent(String var1);

    public String getHttpEquiv();

    public void setHttpEquiv(String var1);

    public String getName();

    public void setName(String var1);

    public String getScheme();

    public void setScheme(String var1);
}

