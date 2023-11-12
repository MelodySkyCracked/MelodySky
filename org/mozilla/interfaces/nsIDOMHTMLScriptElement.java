/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMHTMLElement;

public interface nsIDOMHTMLScriptElement
extends nsIDOMHTMLElement {
    public static final String NS_IDOMHTMLSCRIPTELEMENT_IID = "{a6cf90b1-15b3-11d2-932e-00805f8add32}";

    public String getText();

    public void setText(String var1);

    public String getHtmlFor();

    public void setHtmlFor(String var1);

    public String getEvent();

    public void setEvent(String var1);

    public String getCharset();

    public void setCharset(String var1);

    public boolean getDefer();

    public void setDefer(boolean var1);

    public String getSrc();

    public void setSrc(String var1);

    public String getType();

    public void setType(String var1);
}

