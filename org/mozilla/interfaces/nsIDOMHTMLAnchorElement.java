/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMHTMLElement;

public interface nsIDOMHTMLAnchorElement
extends nsIDOMHTMLElement {
    public static final String NS_IDOMHTMLANCHORELEMENT_IID = "{a6cf90aa-15b3-11d2-932e-00805f8add32}";

    public String getAccessKey();

    public void setAccessKey(String var1);

    public String getCharset();

    public void setCharset(String var1);

    public String getCoords();

    public void setCoords(String var1);

    public String getHref();

    public void setHref(String var1);

    public String getHreflang();

    public void setHreflang(String var1);

    public String getName();

    public void setName(String var1);

    public String getRel();

    public void setRel(String var1);

    public String getRev();

    public void setRev(String var1);

    public String getShape();

    public void setShape(String var1);

    public int getTabIndex();

    public void setTabIndex(int var1);

    public String getTarget();

    public void setTarget(String var1);

    public String getType();

    public void setType(String var1);

    public void blur();

    public void focus();
}

