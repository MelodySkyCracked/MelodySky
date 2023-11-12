/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMDocument;
import org.mozilla.interfaces.nsIDOMHTMLElement;

public interface nsIDOMHTMLIFrameElement
extends nsIDOMHTMLElement {
    public static final String NS_IDOMHTMLIFRAMEELEMENT_IID = "{a6cf90ba-15b3-11d2-932e-00805f8add32}";

    public String getAlign();

    public void setAlign(String var1);

    public String getFrameBorder();

    public void setFrameBorder(String var1);

    public String getHeight();

    public void setHeight(String var1);

    public String getLongDesc();

    public void setLongDesc(String var1);

    public String getMarginHeight();

    public void setMarginHeight(String var1);

    public String getMarginWidth();

    public void setMarginWidth(String var1);

    public String getName();

    public void setName(String var1);

    public String getScrolling();

    public void setScrolling(String var1);

    public String getSrc();

    public void setSrc(String var1);

    public String getWidth();

    public void setWidth(String var1);

    public nsIDOMDocument getContentDocument();
}

