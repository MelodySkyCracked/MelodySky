/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMHTMLElement;

public interface nsIDOMHTMLTableCellElement
extends nsIDOMHTMLElement {
    public static final String NS_IDOMHTMLTABLECELLELEMENT_IID = "{a6cf90b7-15b3-11d2-932e-00805f8add32}";

    public int getCellIndex();

    public String getAbbr();

    public void setAbbr(String var1);

    public String getAlign();

    public void setAlign(String var1);

    public String getAxis();

    public void setAxis(String var1);

    public String getBgColor();

    public void setBgColor(String var1);

    public String getCh();

    public void setCh(String var1);

    public String getChOff();

    public void setChOff(String var1);

    public int getColSpan();

    public void setColSpan(int var1);

    public String getHeaders();

    public void setHeaders(String var1);

    public String getHeight();

    public void setHeight(String var1);

    public boolean getNoWrap();

    public void setNoWrap(boolean var1);

    public int getRowSpan();

    public void setRowSpan(int var1);

    public String getScope();

    public void setScope(String var1);

    public String getVAlign();

    public void setVAlign(String var1);

    public String getWidth();

    public void setWidth(String var1);
}

