/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMHTMLElement;

public interface nsIDOMHTMLLinkElement
extends nsIDOMHTMLElement {
    public static final String NS_IDOMHTMLLINKELEMENT_IID = "{a6cf9088-15b3-11d2-932e-00805f8add32}";

    public boolean getDisabled();

    public void setDisabled(boolean var1);

    public String getCharset();

    public void setCharset(String var1);

    public String getHref();

    public void setHref(String var1);

    public String getHreflang();

    public void setHreflang(String var1);

    public String getMedia();

    public void setMedia(String var1);

    public String getRel();

    public void setRel(String var1);

    public String getRev();

    public void setRev(String var1);

    public String getTarget();

    public void setTarget(String var1);

    public String getType();

    public void setType(String var1);
}

