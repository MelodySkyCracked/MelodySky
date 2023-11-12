/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMHTMLElement;

public interface nsIDOMHTMLImageElement
extends nsIDOMHTMLElement {
    public static final String NS_IDOMHTMLIMAGEELEMENT_IID = "{a6cf90ab-15b3-11d2-932e-00805f8add32}";

    public String getName();

    public void setName(String var1);

    public String getAlign();

    public void setAlign(String var1);

    public String getAlt();

    public void setAlt(String var1);

    public String getBorder();

    public void setBorder(String var1);

    public int getHeight();

    public void setHeight(int var1);

    public int getHspace();

    public void setHspace(int var1);

    public boolean getIsMap();

    public void setIsMap(boolean var1);

    public String getLongDesc();

    public void setLongDesc(String var1);

    public String getSrc();

    public void setSrc(String var1);

    public String getUseMap();

    public void setUseMap(String var1);

    public int getVspace();

    public void setVspace(int var1);

    public int getWidth();

    public void setWidth(int var1);
}

