/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMHTMLElement;

public interface nsIDOMHTMLAreaElement
extends nsIDOMHTMLElement {
    public static final String NS_IDOMHTMLAREAELEMENT_IID = "{a6cf90b0-15b3-11d2-932e-00805f8add32}";

    public String getAccessKey();

    public void setAccessKey(String var1);

    public String getAlt();

    public void setAlt(String var1);

    public String getCoords();

    public void setCoords(String var1);

    public String getHref();

    public void setHref(String var1);

    public boolean getNoHref();

    public void setNoHref(boolean var1);

    public String getShape();

    public void setShape(String var1);

    public int getTabIndex();

    public void setTabIndex(int var1);

    public String getTarget();

    public void setTarget(String var1);
}

